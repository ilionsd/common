package com.insign.common.linearalgebra;

/**
 * Created by ilion on 10.01.2015.
 */
public class VectorFactoryImpl implements VectorFactory {
	@Override
	public AbstractVector newInstance(int size) {
		return new VectorImpl(size);
	}

	@Override
	public AbstractVector newInstance(double[] vector) {
		return new VectorImpl(vector);
	}
}
