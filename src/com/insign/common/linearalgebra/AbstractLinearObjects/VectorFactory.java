package com.insign.common.linearalgebra.AbstractLinearObjects;

import com.insign.common.linearalgebra.LinearObjects.Vector;

/**
 * Created by ilion on 10.01.2015.
 */
public interface VectorFactory {
	Vector newInstance(int size);

	Vector newInstance(int size, boolean isTransposed);

	Vector newInstance(double[] vector);
}
