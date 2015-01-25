package com.insign.common.linearalgebra.LinearObjects;

import java.util.Objects;

/**
 * Created by ilion on 05.01.2015.
 */
public interface Matrix extends MatrixBase {

	Matrix add(Matrix matrix);

	Matrix multiply(Matrix matrix);

	Matrix multiply(double multiplier);

	Vector multiply(Vector vector);

	@Override
	void transpose();



	public static class Math {
		double determinant(Matrix matrix) {
			return 0;
		}

		Matrix inverse(Matrix matrix) {
			return null;
		}
	}

}
