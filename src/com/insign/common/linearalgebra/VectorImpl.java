package com.insign.common.linearalgebra;


import com.insign.common.linearalgebra.AbstractLinearObjects.AbstractVector;
import com.insign.common.linearalgebra.LinearObjects.MatrixFactory;
import com.insign.common.linearalgebra.LinearObjects.VectorFactory;

/**
 * Created by ilion on 05.01.2015.
 */
public final class VectorImpl extends AbstractVector {
	public static final VectorFactory FACTORY = new VectorFactoryImpl();

	double[] array;
	int size;
	boolean isTransposed = false;

	public VectorImpl(int size) {
		this.size = size;
		array = new double[getSize()];
	}

	public VectorImpl(double[] vector) {
		if (!isValid(vector))
			throw new IllegalArgumentException("Not null, not empty array expected");
		for (int index = 0; index < getSize(); index++)
			set(index, vector[index]);
	}

	@Override
	public void transpose() {
		isTransposed = !isTransposed;
	}

	@Override
	protected VectorFactory getVectorFactory() {
		return FACTORY;
	}

	@Override
	protected MatrixFactory getMatrixFactory() {
		return MatrixImpl.FACTORY;
	}

	@Override
	public double get(int index) {
		return array[index];
	}

	@Override
	public void set(int index, double value) {
		array[index] = value;
	}

	@Override
	public boolean isTransposed() {
		return isTransposed;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getRowsCount() {
		return (isTransposed) ? 1 : getSize();
	}

	@Override
	public int getColumnsCount() {
		return (isTransposed) ? getSize() : 1;
	}

	private static boolean isValid(double[] array) {
		if (array == null || array.length == 0)
			return false;
		return true;
	}


}
