package com.insign.common.linearalgebra.LinearObjects;

import java.util.Objects;

/**
 * Created by ilion on 06.01.2015.
 */
public interface Vector extends LinearObject {
	double get(int index);

	void set(int index, double value);

	int getSize();

	Vector add(Vector vector);

	Vector multiply(Matrix matrix);

	Matrix multiply(Vector vector);

	Vector multiply(double multiplier);

	@Override
	void transpose();

	public static class Math {
		static double scalarProduct(Vector vector1, Vector vector2) {
			Objects.requireNonNull(vector1, "Operand can not be null");
			Objects.requireNonNull(vector2, "Operand can not be null");
			if (vector1.getSize() != vector2.getSize())
				throw new IllegalArgumentException("Vectors could not be different size");
			double result = 0;
			for (int k = 0; k < vector1.getSize(); k++)
				result += vector1.get(k) * vector2.get(k);
			return result;
		}

		static double norm(Vector vector) {
			Objects.requireNonNull(vector, "Operand can not be null");
			double result = 0;
			for (int k = 0; k < vector.getSize(); k++)
				result += vector.get(k) * vector.get(k);
			return result;
		}
	}
}
