package com.insign.common.linearalgebra.LinearObjects;

import java.util.Objects;

/**
 * Created by ilion on 05.01.2015.
 */
public interface Matrix extends LinearObject {

	double get(int row, int column);

	void set(int row, int column, double value);

//	int getRowsCount();
//
//	int getColumnsCount();

	Matrix add(Matrix matrix);

	Matrix multiply(Matrix matrix);

	Matrix multiply(double multiplier);

	Vector multiply(Vector vector);

	@Override
	void transpose();

	Vector getRow(int row);

	Vector getColumn(int column);

	void setRow(int row, Vector vector);

	void setColumn(int column, Vector vector);

	public static class ETransform {
		public static void swapRows(Matrix matrix, int row1, int row2) {
			Objects.requireNonNull(matrix, "Matrix cannot be null");
			if (row1 < 0 || row1 >= matrix.getRowsCount() || row2 < 0 || row2 >= matrix.getRowsCount())
				throw new IllegalArgumentException("Rows indexes is between 0 and " + matrix.getRowsCount());
			double temp = 0;
			for (int j = 0; j < matrix.getColumnsCount(); j++) {
				temp = matrix.get(row1, j);
				matrix.set(row1, j, matrix.get(row2, j));
				matrix.set(row2, j, temp);
			}
		}

		public static void swapColumns(Matrix matrix, int column1, int column2) {
			Objects.requireNonNull(matrix, "Matrix cannot be null");
			if (column1 < 0 || column1 >= matrix.getColumnsCount() || column2 < 0 || column2 >= matrix.getColumnsCount())
				throw new IllegalArgumentException("Columns indexes is between 0 and " + matrix.getColumnsCount());
			double temp = 0;
			for (int i = 0; i < matrix.getRowsCount(); i++) {
				temp = matrix.get(i, column1);
				matrix.set(i, column1, matrix.get(column2, i));
				matrix.set(i, column2, temp);
			}
		}

		public static void multiplyRow(Matrix matrix, int row, double multiplier) {
			Objects.requireNonNull(matrix, "Matrix cannot be null");
			if (row < 0 || row >= matrix.getRowsCount())
				throw new IllegalArgumentException("Rows indexes is between 0 and " + matrix.getRowsCount());
			if (java.lang.Math.abs(multiplier) < Double.MIN_VALUE)
				throw new IllegalArgumentException("Multiplier can not be 0");
			for (int j = 0; j < matrix.getColumnsCount(); j++)
				matrix.set(row, j, matrix.get(row, j) * multiplier);
		}

		public static void multiplyColumn(Matrix matrix, int column, double multiplier) {
			Objects.requireNonNull(matrix, "Matrix cannot be null");
			if (column < 0 || column >= matrix.getColumnsCount())
				throw new IllegalArgumentException("Columns indexes is between 0 and " + matrix.getColumnsCount());
			if (java.lang.Math.abs(multiplier) < Double.MIN_VALUE)
				throw new IllegalArgumentException("Multiplier can not be 0");
			for (int i = 0; i < matrix.getRowsCount(); i++)
				matrix.set(i, column, matrix.get(i, column) * multiplier);
		}

		public static void addRow(Matrix matrix, int srcRow, int dstRow, double multiplier) {
			Objects.requireNonNull(matrix, "Matrix cannot be null");
			if (srcRow < 0 || srcRow >= matrix.getRowsCount() || dstRow < 0 || dstRow >= matrix.getRowsCount())
				throw new IllegalArgumentException("Rows indexes is between 0 and " + matrix.getRowsCount());
			if (java.lang.Math.abs(multiplier) < Double.MIN_VALUE)
				throw new IllegalArgumentException("Multiplier can not be 0");
			for (int j = 0; j < matrix.getColumnsCount(); j++)
				matrix.set(dstRow, j, matrix.get(dstRow, j) + matrix.get(srcRow, j) * multiplier);
		}

		public static void addColumn(Matrix matrix, int srcColumn, int dstColumn, double multiplier) {
			Objects.requireNonNull(matrix, "Matrix cannot be null");
			if (srcColumn < 0 || srcColumn >= matrix.getColumnsCount() || dstColumn < 0 || dstColumn >= matrix.getColumnsCount())
				throw new IllegalArgumentException("Columns indexes is between 0 and " + matrix.getColumnsCount());
			if (java.lang.Math.abs(multiplier) < Double.MIN_VALUE)
				throw new IllegalArgumentException("Multiplier can not be 0");
			for (int i = 0; i < matrix.getRowsCount(); i++)
				matrix.set(i, dstColumn, matrix.get(i, dstColumn) + matrix.get(i, srcColumn) * multiplier);
		}
	}

	public static class Math {

	}

}
