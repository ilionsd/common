package com.insign.common.linearalgebra;

/**
 * Created by ilion on 10.01.2015.
 */
public class VectorFactoryImpl implements VectorFactory {
	@Override
	public Vector newInstance(int size) {
		return new VectorImpl(size);
	}

	@Override
	public Vector newInstance(double[] vector) {
		return new VectorImpl(vector);
	}
}
