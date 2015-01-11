package com.insign.common.linearalgebra;

import com.insign.common.linearalgebra.AbstractLinearObjects.MatrixFactory;
import com.insign.common.linearalgebra.LinearObjects.Matrix;

/**
 * Created by ilion on 06.01.2015.
 */
public class MatrixFactoryImpl implements MatrixFactory {

	@Override
	public Matrix newInstance(int rowsCount, int columnsCount) {
		return new MatrixImpl(rowsCount, columnsCount);
	}

	@Override
	public Matrix newInstance(double[][] matrix) {
		return new MatrixImpl(matrix);
	}
}
