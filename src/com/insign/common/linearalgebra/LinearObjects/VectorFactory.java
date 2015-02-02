package com.insign.common.linearalgebra.LinearObjects;

import com.insign.common.linearalgebra.LinearObjects.LinearObjectFactory;
import com.insign.common.linearalgebra.LinearObjects.Vector;

/**
 * Created by ilion on 10.01.2015.
 */
public interface VectorFactory extends LinearObjectFactory {
	Vector newInstance(int size);

	Vector newInstance(int size, boolean isTransposed);

	Vector newInstance(double[] vector);
}
