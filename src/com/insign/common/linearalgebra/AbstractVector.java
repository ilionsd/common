package com.insign.common.linearalgebra;

import java.util.Objects;

/**
 * Created by ilion on 10.01.2015.
 */
public abstract class AbstractVector implements Vector {

	protected abstract VectorFactory getFactory();

	public abstract boolean isTransposed();

	@Override
	public Vector add(Vector vector) {
		Objects.requireNonNull(vector, "Vector cannot be null");
		if (getSize() != vector.getSize())
			throw new IllegalArgumentException("Vectors cannot have different size");
		Vector result = getFactory().newInstance(getSize());
		for (int index = 0; index < result.getSize(); index++)
			result.set(index, get(index) + vector.get(index));
		return result;
	}

	@Override
	public Vector multiply(Matrix matrix) {
		return null;
	}

	@Override
	public Vector multiply(Vector vector) {
		Objects.requireNonNull(vector, "Vector cannot be null");
		if (isTransposed() && vector.is)
			return null;
	}
	@Override
	public Vector multiply(double multiplier) {
		return null;
	}
}
