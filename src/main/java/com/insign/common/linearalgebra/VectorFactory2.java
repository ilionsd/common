package com.insign.common.linearalgebra;

import com.insign.common.linearalgebra.LinearObjects.Vector;
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
 * Created by ilion on 03.03.2015.
 */
public class VectorFactory2 extends AbstractLinearFactory {
	public VectorFactory2() {
		super();
	}

	private static final Log logger = LogFactory.getLog(VectorFactory2.class);

	public static final Class<? extends Vector> DEFAULT_VECTOR_CLASS = VectorImpl.class;

	protected static final Class VECTOR_INTERFACE = Vector.class;
	private static final Class[] SIZE_CONSTRUCTOR_PARAMS = {int.class};
	private static final Class[] REFERENCE_CONSTRUCTOR_PARAMS = {double[].class};
	protected static final Map<Constructors, Class[]> paramsMap = new HashMap<Constructors, Class[]>();
	protected static final Map<Class<? extends Vector>, Map<Constructors, Constructor>> mapping = new HashMap<Class<? extends Vector>, Map<Constructors, Constructor>>();

	static {
		paramsMap.put(Constructors.SIZE_CONSTRUCTOR, SIZE_CONSTRUCTOR_PARAMS);
		paramsMap.put(Constructors.REFERENCE_CONSTRUCTOR, REFERENCE_CONSTRUCTOR_PARAMS);

		Set<Class<? extends Vector>> clazzes = reflections.getSubTypesOf(VECTOR_INTERFACE);

		for (Class clazz : clazzes)
			if (!Modifier.isAbstract(clazz.getModifiers()) && !Modifier.isInterface(clazz.getModifiers()))
				registry(clazz);
	}

	public static void registry(Class<? extends Vector> clazz) {
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

	public static boolean isRegistered(Class<? extends Vector> clazz) {
		return mapping.containsKey(clazz);
	}

	public static Vector getVector(Class<? extends Vector> clazz, int size) {
		if (!isRegistered(clazz))
			throw new RuntimeException("Cannot create matrix instance. Class " + clazz.toString() + " is not registered.");
		Vector vector = null;
		try {
			vector = (Vector) mapping.get(clazz).get(Constructors.SIZE_CONSTRUCTOR).newInstance(size);
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
		return vector;
	}

	public static Vector getVector(Class<? extends Vector> clazz, double[] reference) {
		if (!isRegistered(clazz))
			throw new RuntimeException("Cannot create matrix instance. Class " + clazz.toString() + " is not registered.");
		Vector vector = null;
		try {
			vector = (Vector) mapping.get(clazz).get(Constructors.REFERENCE_CONSTRUCTOR).newInstance((Object) reference);
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
		return vector;
	}

}
