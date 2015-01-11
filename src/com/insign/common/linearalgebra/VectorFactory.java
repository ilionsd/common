package com.insign.common.linearalgebra;

/**
 * Created by ilion on 10.01.2015.
 */
public interface VectorFactory {
	Vector newInstance(int size);
	Vector newInstance(double[] vector);
}
