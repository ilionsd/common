package com.insign.common.linearalgebra;

import com.insign.common.linearalgebra.exceptions.IndexException;

/**
 * Created by ilion on 10.01.2015.
 */
public final class MatrixImpl extends AbstractMatrix {

	public final static MatrixFactory FACTORY = new MatrixFactoryImpl();

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
			throw new IllegalArgumentException("Not null rectangular matrix expected");
		rowsCount = matrix.length;
		columnsCount = matrix[0].length;

		array = new double[rowsCount * columnsCount];
		for (int row = 0; row < getRowsCount(); row++)
			for (int column = 0; column < getColumnsCount(); column++)
				set(row, column, matrix[row][column]);
	}

	@Override
	public MatrixImpl transpose() {
		isTransposed = !isTransposed;
		int temp = rowsCount;
		rowsCount = columnsCount;
		columnsCount = temp;
		return this;
	}

	@Override
	protected MatrixFactory getMatrixFactory() {
		return FACTORY;
	}

	@Override
	protected VectorFactory getVectorFactory() {
		return VectorImpl.FACTORY;
	}

	@Override
	public double get(int row, int column) {
		if (row < 0 || column < 0 || row >= rowsCount || column >= columnsCount)
			throw new IndexException(row, column, rowsCount, columnsCount);

		if (isTransposed)
			return array[rowsCount * column + row];
		else
			return array[rowsCount * row + column];
	}

	@Override
	public void set(int row, int column, double value) {
		if (row < 0 || column < 0 || row >= rowsCount || column >= columnsCount)
			throw new IndexException(row, column, rowsCount, columnsCount);

		if (isTransposed)
			array[rowsCount * column + row] = value;
		else
			array[rowsCount * row + column] = value;
	}

	@Override
	public int getRowsCount() {
		return rowsCount;
	}

	@Override
	public int getColumnsCount() {
		return columnsCount;
	}

	protected static boolean isValid(double[][] array) {
		if (array == null || array.length == 0 || array[0] == null || array[0].length == 0)
			return false;
		else {
			int columnsCount = array[0].length;
			for (int k = 1; k < array.length; k++)
				if (array[k] == null || array[k].length != columnsCount)
					return false;
		}
		return true;
	}
}
