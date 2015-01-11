package com.insign.common.linearalgebra;

/**
 * Created by ilion on 10.01.2015.
 */
public interface MatrixFactory {
	Matrix newInstance(int rowsCount, int columnsCount);

	Matrix newInstance(double[][] matrix);
}
