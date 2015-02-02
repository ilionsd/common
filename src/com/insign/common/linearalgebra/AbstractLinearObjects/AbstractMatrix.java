package com.insign.common.linearalgebra.AbstractLinearObjects;

import com.insign.common.linearalgebra.LinearObjects.Matrix;
import com.insign.common.linearalgebra.LinearObjects.MatrixFactory;
import com.insign.common.linearalgebra.LinearObjects.Vector;
import com.insign.common.linearalgebra.LinearObjects.VectorFactory;
import com.insign.common.linearalgebra.MatrixImpl;
import com.insign.common.linearalgebra.exceptions.IndexException;

import java.util.Objects;

/**
 * Created by ilion on 05.01.2015.
 */
public abstract class AbstractMatrix implements Matrix {

	protected abstract MatrixFactory getMatrixFactory();
	protected abstract VectorFactory getVectorFactory();

	@Override
	public MatrixFactory getFactory() {
		return getMatrixFactory();
	}

	@Override
	public abstract int getRowsCount();

	@Override
	public abstract int getColumnsCount();

	public abstract boolean isTransposed();

	@Override
	public Vector getRow(int row) {
		if (row < 0 || row >= getRowsCount())
			throw new IndexException(row, 0, getRowsCount(), getColumnsCount());
		Vector vector = getVectorFactory().newInstance(getColumnsCount(), true);
		for (int k = 0; k < vector.getSize(); k++)
			vector.set(k, get(row, k));
		return vector;
	}

	@Override
	public Vector getColumn(int column) {
		if (column < 0 || column >= getColumnsCount())
			throw new IndexException(0, column, getRowsCount(), getColumnsCount());
		Vector vector = getVectorFactory().newInstance(getColumnsCount());
		for (int k = 0; k < vector.getSize(); k++)
			vector.set(k, get(k, column));
		return vector;
	}

	@Override
	public void setRow(int row, Vector vector) {
		Objects.requireNonNull(vector, "Vector can not be null");
		if (getColumnsCount() != vector.getSize())
			throw new IllegalArgumentException("Vector could not be different size with columns count");
		for (int j = 0; j < getColumnsCount(); j++)
			set(row, j, vector.get(j));
	}

	@Override
	public void setColumn(int column, Vector vector) {
		Objects.requireNonNull(vector, "Vector can not be null");
		if (getRowsCount() != vector.getSize())
			throw new IllegalArgumentException("Vector could not be different size with rows count");
		for (int i = 0; i < getRowsCount(); i++)
			set(column, i, vector.get(i));
	}

	@Override
	public Matrix add(Matrix matrix) {
		Objects.requireNonNull(matrix, "Matrix cannot be null");
		if (getRowsCount() != matrix.getRowsCount() || getColumnsCount() != matrix.getColumnsCount())
			throw new IllegalArgumentException("Matrix size is not " + getRowsCount() + "x" + getColumnsCount());
		Matrix result = getMatrixFactory().newInstance(getRowsCount(), getColumnsCount());
		for (int i = 0; i < getRowsCount(); i++)
			for (int j = 0; j < getColumnsCount(); j++)
				result.set(i, j, get(i, j) + matrix.get(i, j));
		return result;
	}

	@Override
	public Matrix multiply(Matrix matrix) {
		Objects.requireNonNull(matrix, "matrix cannot be null");
		if (getColumnsCount() != matrix.getRowsCount())
			throw new IllegalArgumentException("Cannot multiply " + getRowsCount() + "x" + getColumnsCount() + " and " + matrix.getRowsCount() + "x" + matrix.getColumnsCount() + " matrix");
		MatrixImpl result = new MatrixImpl(getRowsCount(), matrix.getColumnsCount());
		for (int i = 0; i < result.getRowsCount(); i++)
			for (int j = 0; j < result.getColumnsCount(); j++) {
				double sum = 0;
				for (int k = 0; k < getColumnsCount(); k++)
					sum += get(i, k) * matrix.get(k, j);
				result.set(i, j, sum);
			}
		return result;
	}

	@Override
	public Matrix multiply(double multiplier) {
		Matrix result = getMatrixFactory().newInstance(getRowsCount(), getColumnsCount());
		for (int i = 0; i < result.getRowsCount(); i++)
			for (int j = 0; j < result.getColumnsCount(); j++)
				result.set(i, j, get(i, j) * multiplier);
		return result;
	}

	@Override
	public Vector multiply(Vector vector) {
		Objects.requireNonNull(vector, "matrix cannot be null");
		if (getColumnsCount() != vector.getSize())
			throw new IllegalArgumentException("Cannot multiply " + getRowsCount() + "x" + getColumnsCount() + " and " + vector.getSize() + " size vector");
		Vector result = getVectorFactory().newInstance(vector.getSize());
		for (int index = 0; index < result.getSize(); index++) {
			double sum = 0;
			for (int k = 0; k < vector.getSize(); k++)
				sum += get(index, k) * vector.get(k);
		}
		return result;
	}


}
