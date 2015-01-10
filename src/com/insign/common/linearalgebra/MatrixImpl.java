package com.insign.common.linearalgebra;

import com.insign.common.linearalgebra.exceptions.IndexException;
import com.insign.common.linearalgebra.exceptions.InvalidParameterException;

/**
 * Created by ilion on 10.01.2015.
 */
public final class MatrixImpl extends AbstractMatrix {

	private static MatrixFactory matrixFactory = new MatrixFactoryImpl();

	double[] array;
	int rowsCount;
	int columnsCount;
	boolean isTransposed = false;


	public MatrixImpl(int rowsCount, int columnsCount) {
		this.rowsCount = rowsCount;
		this.columnsCount = columnsCount;
		array = new double[getRowsCount() * getColumnsCount()];
	}

	public MatrixImpl(double[][] matrix) {
		if (!isValid(matrix))
			throw new InvalidParameterException(matrix, "Not null rectangular matrix");
		rowsCount = matrix.length;
		columnsCount = matrix[0].length;

		array = new double[rowsCount * columnsCount];
		for (int row = 0; row < getRowsCount(); row++)
			for (int column = 0; column < getColumnsCount(); column++)
				set(row, column, matrix[row][column]);
	}

	@Override
	public MatrixImpl Transpose() {
		isTransposed = !isTransposed;
		return this;
	}

	@Override
	public MatrixFactory getMatrixFactory() {
		return matrixFactory;
	}

	@Override
	public double get(int row, int column) {
		if (row >= 0 && column >= 0 && row < rowsCount && column < columnsCount)
			return array[columnsCount * row + column];
		else
			throw new IndexException(row, column, rowsCount, columnsCount);
	}

	@Override
	public void set(int row, int column, double value) {
		if (row >= 0 && column >= 0 && row < rowsCount && column < columnsCount)
			array[columnsCount * row + column] = value;
		else
			throw new IndexException(row, column, rowsCount, columnsCount);
	}

	@Override
	public int getRowsCount() {
		return rowsCount;
	}

	@Override
	public int getColumnsCount() {
		return columnsCount;
	}
}
