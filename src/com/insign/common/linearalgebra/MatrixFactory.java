package com.insign.common.linearalgebra;

/**
 * Created by ilion on 10.01.2015.
 */
public interface MatrixFactory {
	AbstractMatrix newInstance(int rowsCount, int columnsCount);

	AbstractMatrix newInstance(double[][] matrix);
}
