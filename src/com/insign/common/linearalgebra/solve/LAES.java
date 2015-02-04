package com.insign.common.linearalgebra.solve;

import com.insign.common.linearalgebra.LinearObjects.Matrix;
import com.insign.common.linearalgebra.LinearObjects.Vector;
import com.insign.common.linearalgebra.VectorImpl;

import java.util.Objects;

/**
 * Created by ilion on 02.02.2015.
 */
public class LAES {
	private LAES() {
	}

	public static Vector Gauss(Matrix matrix, Vector freeVector) {
		Objects.requireNonNull(matrix, "Matrix can not be null!");
		Objects.requireNonNull(freeVector, "Vector can not be null");

		if (matrix.getRowsCount() != matrix.getColumnsCount())
			throw new IllegalArgumentException("Numeric solution require square matrix");
		if (matrix.getRowsCount() != freeVector.getSize())
			throw new IllegalArgumentException("Vector size must match the number of rows in matrix");

		Matrix m = matrix.getFactory().newInstance(matrix);
		Vector v = freeVector.getFactory().newInstance(freeVector);


		int[] trR = new int[m.getRowsCount()],
				trC = new int[m.getColumnsCount()];
		for (int i = 0; i < trR.length; i++)
			trR[i] = i;
		for (int j = 0; j < trC.length; j++)
			trC[j] = j;

		//-- Checked: square --
		int matrixSize = m.getRowsCount();
		double maxElement = 0;
		int iIdx = 0, jIdx = 0;
		for (int k = 0; k < matrixSize - 1; k++) {
			//-- Abs maximal element finding --
			maxElement = -1;
			iIdx = -1;
			jIdx = -1;
			for (int i = k; i < matrixSize; i++) {
				for (int j = k; j < matrixSize; j++) {
					if (maxElement < Math.abs(m.get(trR[i], trC[j]))) {
						maxElement = Math.abs(m.get(trR[i], trC[j]));
						iIdx = i;
						jIdx = j;
					}
				}
			}
			int temp = trR[k];
			trR[k] = trR[iIdx];
			trR[iIdx] = temp;
			temp = trC[k];
			trC[k] = trC[jIdx];
			trC[jIdx] = temp;

			//-- Subtractions --
			for (int i = k + 1; i < matrixSize; i++) {
				double mi = -m.get(trR[i], trC[k]) / m.get(trR[k], trC[k]);
				//-- Matrix --
				m.set(trR[i], trC[k], 0);
				for (int j = k + 1; j < matrixSize; j++) {
					double Mkj = m.get(trR[k], trC[j]),
							Mij = m.get(trR[i], trC[j]);
					m.set(trR[i], trC[j], Mij + mi * Mkj);
				}
				//-- Vector --
				double Vk = v.get(trR[k]),
						Vi = v.get(trR[i]);
				v.set(trR[i], Vi + mi * Vk);
			}
		}

		Vector x = v.getFactory().newInstance(v.getSize());
		for (int i = matrixSize - 1; i >= 0; i--) {
			double summ = v.get(trR[i]);
			for (int j = matrixSize - 1; j > i; j--) {
				double Mij = m.get(trR[i], trC[j]);
				summ -= Mij * x.get(trC[j]);
			}
			x.set(trC[i], summ / m.get(trR[i], trC[i]));
		}
		return x;
	}

	public static Vector GaussSeidel(Matrix matrix, Vector freeVector) {
		return GaussSeidel(matrix, freeVector, 1e-3);
	}

	public static Vector GaussSeidel(Matrix matrix, Vector freeVector, double epsilon) {
		Objects.requireNonNull(matrix, "Matrix can not be null!");
		Objects.requireNonNull(freeVector, "Vector can not be null");

		if (matrix.getRowsCount() != matrix.getColumnsCount())
			throw new IllegalArgumentException("Numeric solution require square matrix");
		if (matrix.getRowsCount() != freeVector.getSize())
			throw new IllegalArgumentException("Vector size must match the number of rows in matrix");

		int matrixSize = matrix.getRowsCount();
		Vector x = VectorImpl.FACTORY.newInstance(matrixSize);
		for (int k = 0; k < x.getSize(); k++)
			x.set(k, freeVector.get(k));

		Vector xPrev = null;
		do {
			xPrev = x;
			x = VectorImpl.FACTORY.newInstance(matrixSize);
			for (int index = 0; index < x.getSize(); index++) {
				double xIndex = freeVector.get(index);
				for (int k = 0; k < index; k++)
					xIndex -= matrix.get(index, k) * x.get(k);
				for (int k = index + 1; k < matrixSize; k++)
					xIndex -= matrix.get(index, k) * xPrev.get(k);
				x.set(index, xIndex / matrix.get(index, index));
			}
		}
		while (Vector.Math.norm(x.negate().add(xPrev)) > epsilon);

		return x.add(xPrev).multiply(0.5);
	}
}



































