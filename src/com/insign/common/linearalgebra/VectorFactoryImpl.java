package com.insign.common.linearalgebra;

import com.insign.common.linearalgebra.AbstractLinearObjects.VectorFactory;
import com.insign.common.linearalgebra.LinearObjects.Vector;

/**
 * Created by ilion on 10.01.2015.
 */
public class VectorFactoryImpl implements VectorFactory {
	@Override
	public Vector newInstance(int size) {
		return newInstance(size, false);
	}

	@Override
	public Vector newInstance(int size, boolean isTransposed) {
		VectorImpl vector = new VectorImpl(size);
		return (isTransposed) ? vector.transpose() : vector;
	}

	@Override
	public Vector newInstance(double[] vector) {
		return new VectorImpl(vector);
	}
}
