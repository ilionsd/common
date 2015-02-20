package com.insign.common.linearalgebra.AbstractLinearObjects;

import com.insign.common.linearalgebra.LinearObjects.Matrix;
import com.insign.common.linearalgebra.LinearObjects.MatrixFactory;
import com.insign.common.linearalgebra.LinearObjects.Vector;
import com.insign.common.linearalgebra.LinearObjects.VectorFactory;

import java.util.Objects;

/**
 * Created by ilion on 10.01.2015.
 */
public abstract class AbstractVector implements Vector {

	protected abstract VectorFactory getVectorFactory();

	protected abstract MatrixFactory getMatrixFactory();

	@Override
	public VectorFactory getFactory() {
		return getVectorFactory();
	}

	@Override
	public abstract int getRowsCount();

	@Override
	public abstract int getColumnsCount();

	public abstract boolean isTransposed();

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector)
			return equals((Vector) obj, 1e-12);
		else return false;
	}

	@Override
	public boolean equals(Vector vector, double epsilon) {
		return Vector.Math.norm(this.negate().add(vector)) < epsilon;
	}

	@Override
	public Vector add(Vector vector) {
		Objects.requireNonNull(vector, "Vector cannot be null");
		if (getSize() != vector.getSize())
			throw new IllegalArgumentException("Vectors cannot have different size");
		Vector result = getVectorFactory().newInstance(getSize());
		for (int index = 0; index < result.getSize(); index++)
			result.set(index, get(index) + vector.get(index));
		return result;
	}

	@Override
	public Vector negate() {
		Vector result = getVectorFactory().newInstance(getSize());
		for (int k = 0; k < result.getSize(); k++)
			result.set(k, -get(k));
		return result;
	}

	@Override
	public Vector multiply(Matrix matrix) {
		Objects.requireNonNull(matrix, "Matrix cannot be null");
		if (1 != matrix.getRowsCount())
			throw new IllegalArgumentException("Cannot multiply " + getRowsCount() + "x" + getColumnsCount() + " vector and " + matrix.getRowsCount() + "x" + matrix.getColumnsCount() + " matrix");
		Vector result = getVectorFactory().newInstance(matrix.getColumnsCount());
		result.transpose();
		for (int index = 0; index < result.getColumnsCount(); index++) {
			double sum = 0;
			for (int k = 0; k < getColumnsCount(); k++)
				sum += get(k) * matrix.get(k, index);
			result.set(index, sum);
		}
		return result;
	}

	@Override
	public Matrix multiply(Vector vector) {
		Objects.requireNonNull(vector, "Vector cannot be null");
		if (getColumnsCount() != vector.getRowsCount())
			throw new IllegalArgumentException("Cannot multiply vectors " + getRowsCount() + "x" + getColumnsCount() + " and " + vector.getRowsCount() + "x" + vector.getColumnsCount());
		Matrix result = getMatrixFactory().newInstance(getRowsCount(), vector.getColumnsCount());

		if (result.getRowsCount() == 1 && result.getColumnsCount() == 1) {
			double sum = 0;
			for (int k = 0; k < getColumnsCount(); k++)
				sum += get(k) * vector.get(k);
			result.set(0, 0, sum);
		} else {
			for (int i = 0; i < result.getRowsCount(); i++)
				for (int j = 0; j < result.getColumnsCount(); j++)
					result.set(i, j, get(i) * vector.get(j));
		}
		return result;
	}

	@Override
	public Vector multiply(double multiplier) {
		Vector result = getVectorFactory().newInstance(getSize(), isTransposed());
		for (int index = 0; index < result.getSize(); index++)
			result.set(index, get(index) * multiplier);
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder()
				.append("<");
		for (int k = 0; k < getSize(); k++) {
			sb.append(get(k));
			if (k != getSize() - 1)
				sb.append("; ");
		}
		sb.append(">");
		if (isTransposed())
			sb.append("T");
		return sb.toString();
	}
}
