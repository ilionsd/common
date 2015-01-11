package com.insign.common.linearalgebra.AbstractLinearObjects;

import com.insign.common.linearalgebra.LinearObjects.Matrix;

/**
 * Created by ilion on 10.01.2015.
 */
public interface MatrixFactory {
	Matrix newInstance(int rowsCount, int columnsCount);

	Matrix newInstance(double[][] matrix);
}
