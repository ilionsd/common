package com.insign.common.linearalgebra;

import com.insign.common.linearalgebra.LinearObjects.Matrix;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ilion on 01.03.2015.
 */
public class MatrixFactory2 extends AbstractLinearFactory {
	public MatrixFactory2() {
		super();
	}

	private static final Log logger = LogFactory.getLog(MatrixFactory2.class);

	public static final Class<? extends Matrix> DEFAULT_MATRIX_CLASS = MatrixImpl.class;

	protected static final Class MATRIX_INTERFACE = Matrix.class;
	private static final Class[] SIZE_CONSTRUCTOR_PARAMS = {int.class, int.class};
	private static final Class[] REFERENCE_CONSTRUCTOR_PARAMS = {double[][].class};
	protected static final Map<Constructors, Class[]> paramsMap = new HashMap<Constructors, Class[]>();
	protected static final Map<Class<? extends Matrix>, Map<Constructors, Constructor>> mapping = new HashMap<Class<? extends Matrix>, Map<Constructors, Constructor>>();

	static {
		paramsMap.put(Constructors.SIZE_CONSTRUCTOR, SIZE_CONSTRUCTOR_PARAMS);
		paramsMap.put(Constructors.REFERENCE_CONSTRUCTOR, REFERENCE_CONSTRUCTOR_PARAMS);

		Set<Class<? extends Matrix>> clazzes = reflections.getSubTypesOf(MATRIX_INTERFACE);

		for (Class clazz : clazzes)
			if (!Modifier.isAbstract(clazz.getModifiers()) && !Modifier.isInterface(clazz.getModifiers()))
				registry(clazz);
	}

	public static void registry(Class<? extends Matrix> clazz) {
		if (isRegistered(clazz)) {
			logger.warn("Class " + clazz.toString() + " already loaded.");
			return;
		}
		Map<Constructors, Constructor> constructorMap = new HashMap<Constructors, Constructor>();
		Constructor constructor;
		try {
			for (Map.Entry<Constructors, Class[]> entry : paramsMap.entrySet()) {
				constructor = clazz.getDeclaredConstructor(entry.getValue());
				if (Modifier.isPublic(constructor.getModifiers()))
					constructorMap.put(entry.getKey(), constructor);
				else
					throw new IllegalAccessException("Constructor " + ArrayUtils.toString(entry.getValue()) + " is not public");
			}
		} catch (NoSuchMethodException e) {
			logger.warn("Cannon load class " + clazz.toString() + ". Missing some constructor");
			return;
		} catch (IllegalAccessException e) {
			logger.warn("Cannot load class " + clazz.toString() + ". Required constructor is not public");
			return;
		}
		mapping.put(clazz, constructorMap);
	}

	protected static Constructors getConstructorType(Class[] got) {
		for (Map.Entry<Constructors, Class[]> entry : paramsMap.entrySet()) {
			Class[] need = entry.getValue();
			if (ClassUtils.isAssignable(got, need, true))
				return entry.getKey();
		}
		return Constructors.UNKNOWN_CONSTRUCTOR;
	}

	public static boolean isRegistered(Class<? extends Matrix> clazz) {
		return mapping.containsKey(clazz);
	}

	private static Matrix getMatrix(Class<? extends Matrix> clazz, Object... constructorParams) {
		if (!isRegistered(clazz))
			throw new RuntimeException("Cannot create matrix instance. Class " + clazz.toString() + " is not registered.");
		int k = 0;
		Class[] constructorParamTypes = ClassUtils.toClass(constructorParams);
		/*
		for (Object param : constructorParams) {
			Class paramClazz = param.getClass();
			//if (Number.class.isAssignableFrom(paramClazz))

			constructorParamTypes[k++] = paramClazz;
		}
		*/
		Constructors constructorType = getConstructorType(constructorParamTypes);
		if (Constructors.UNKNOWN_CONSTRUCTOR.equals(constructorType))
			throw new RuntimeException("Unknown constructor params set: " + ArrayUtils.toString(constructorParamTypes));
		//Object[] constructorParamsCasted = com.insign.common.ArrayUtils.toTypes(constructorParams, paramsMap.get(constructorType));
		Matrix matrix = null;
		try {
			matrix = (Matrix) mapping.get(clazz).get(constructorType).newInstance(constructorParams);
		} catch (InstantiationException e) {
			/*
			Thrown when an application tries to create an instance of a class using the newInstance method in class Class, but the specified class object cannot be instantiated. The instantiation can fail for a variety of reasons including but not limited to:
			* (CHECKED) the class object represents an abstract class, an interface, an array class, a primitive type, or void
			* (CHECKED) the class has no nullary constructor
			 */

			//-- Should never happen --
			throw new RuntimeException("InstantiationException was thrown, trying to instantiate new " + clazz.toString() + " exemplar. " + e.getMessage());
		} catch (IllegalAccessException e) {
			/*
			(CHECKED)
			An IllegalAccessException is thrown when an application tries to reflectively create an instance (other than an array), set or get a field, or invoke a method, but the currently executing method does not have access to the definition of the specified class, field, method or constructor.
			 */

			//-- Should never happen --
			throw new RuntimeException("InstantiationException was thrown, trying to instantiate new " + clazz.toString() + " exemplar. " + e.getMessage());
		} catch (InvocationTargetException e) {
			/*
			InvocationTargetException is a checked exception that wraps an exception thrown by an invoked method or constructor.
			*/

			//-- Something bad happen within constructor... --
			throw new RuntimeException("InstantiationException was thrown, trying to instantiate new " + clazz.toString() + " exemplar. Cause: " + e.getCause().getMessage() + ".");
		}
		return matrix;
	}

	public static Matrix getMatrix(Class<? extends Matrix> clazz, int rowsCount, int columnsCount) {
		if (!isRegistered(clazz))
			throw new RuntimeException("Cannot create matrix instance. Class " + clazz.toString() + " is not registered.");
		Matrix matrix = null;
		try {
			matrix = (Matrix) mapping.get(clazz).get(Constructors.SIZE_CONSTRUCTOR).newInstance(rowsCount, columnsCount);
		} catch (InstantiationException e) {
			/*
			Thrown when an application tries to create an instance of a class using the newInstance method in class Class, but the specified class object cannot be instantiated. The instantiation can fail for a variety of reasons including but not limited to:
			* (CHECKED) the class object represents an abstract class, an interface, an array class, a primitive type, or void
			* (CHECKED) the class has no nullary constructor
			 */

			//-- Should never happen --
			throw new RuntimeException("InstantiationException was thrown, trying to instantiate new " + clazz.toString() + " exemplar. " + e.getMessage());
		} catch (IllegalAccessException e) {
			/*
			(CHECKED)
			An IllegalAccessException is thrown when an application tries to reflectively create an instance (other than an array), set or get a field, or invoke a method, but the currently executing method does not have access to the definition of the specified class, field, method or constructor.
			 */

			//-- Should never happen --
			throw new RuntimeException("InstantiationException was thrown, trying to instantiate new " + clazz.toString() + " exemplar. " + e.getMessage());
		} catch (InvocationTargetException e) {
			/*
			InvocationTargetException is a checked exception that wraps an exception thrown by an invoked method or constructor.
			*/

			//-- Something bad happen within constructor... --
			throw new RuntimeException("InstantiationException was thrown, trying to instantiate new " + clazz.toString() + " exemplar. Cause: " + e.getCause().getMessage() + ".");
		}
		return matrix;
	}

	public static Matrix getMatrix(Class<? extends Matrix> clazz, double[][] reference) {
		if (!isRegistered(clazz))
			throw new RuntimeException("Cannot create matrix instance. Class " + clazz.toString() + " is not registered.");
		Matrix matrix = null;
		try {
			matrix = (Matrix) mapping.get(clazz).get(Constructors.REFERENCE_CONSTRUCTOR).newInstance((Object) reference);
		} catch (InstantiationException e) {
			/*
			Thrown when an application tries to create an instance of a class using the newInstance method in class Class, but the specified class object cannot be instantiated. The instantiation can fail for a variety of reasons including but not limited to:
			* (CHECKED) the class object represents an abstract class, an interface, an array class, a primitive type, or void
			* (CHECKED) the class has no nullary constructor
			 */

			//-- Should never happen --
			throw new RuntimeException("InstantiationException was thrown, trying to instantiate new " + clazz.toString() + " exemplar. " + e.getMessage());
		} catch (IllegalAccessException e) {
			/*
			(CHECKED)
			An IllegalAccessException is thrown when an application tries to reflectively create an instance (other than an array), set or get a field, or invoke a method, but the currently executing method does not have access to the definition of the specified class, field, method or constructor.
			 */

			//-- Should never happen --
			throw new RuntimeException("InstantiationException was thrown, trying to instantiate new " + clazz.toString() + " exemplar. " + e.getMessage());
		} catch (InvocationTargetException e) {
			/*
			InvocationTargetException is a checked exception that wraps an exception thrown by an invoked method or constructor.
			*/

			//-- Something bad happen within constructor... --
			throw new RuntimeException("InstantiationException was thrown, trying to instantiate new " + clazz.toString() + " exemplar. Cause: " + e.getCause().getMessage() + ".");
		}
		return matrix;
	}

	public static Matrix getDiagonalMatrix(Class<? extends Matrix> clazz, double[] diagonal) {
		Matrix diagonalMatrix = getMatrix(clazz, diagonal.length, diagonal.length);
		for (int k = 0; k < diagonal.length; k++)
			diagonalMatrix.set(k, k, diagonal[k]);
		return diagonalMatrix;
	}

	public static Matrix getE(Class<? extends Matrix> clazz, int size) {
		Matrix e = getMatrix(clazz, size, size);
		for (int k = 0; k < size; k++)
			e.set(k, k, 1);
		return e;
	}
}












