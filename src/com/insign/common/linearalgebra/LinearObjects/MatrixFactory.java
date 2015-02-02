package com.insign.common.linearalgebra.LinearObjects;

import com.insign.common.linearalgebra.LinearObjects.LinearObjectFactory;
import com.insign.common.linearalgebra.LinearObjects.Matrix;

/**
 * Created by ilion on 10.01.2015.
 */
public interface MatrixFactory extends LinearObjectFactory {
	Matrix newInstance(int rowsCount, int columnsCount);

	Matrix newInstance(double[][] matrix);
}
