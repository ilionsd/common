package com.insign.common.linearalgebra.LinearObjects;

/**
 * Created by ilion on 10.01.2015.
 */
public interface MatrixFactory extends LinearObjectFactory {


	Matrix newInstance(int rowsCount, int columnsCount);

	Matrix newInstance(double[][] matrix);

	Matrix newInstance(Matrix matrix);

	Matrix E(int n);

	Matrix diag(double[] array);


}
