package com.insign.common.linearalgebra.solve;

import com.insign.common.linearalgebra.LinearObjects.Matrix;
import com.insign.common.linearalgebra.LinearObjects.Vector;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by ilion on 02.02.2015.
 */
public class LAES {
	private LAES() {
	}

	;

	public static Vector gaussMethod(Matrix matrix, Vector freeVector) {
		Objects.requireNonNull(matrix, "Matrix can not be null!");
		Objects.requireNonNull(freeVector, "Vector can not be null");

		if (matrix.getRowsCount() != matrix.getColumnsCount())
			throw new IllegalArgumentException("Numeric solution require square matrix");
		if (matrix.getRowsCount() != freeVector.getSize())
			throw new IllegalArgumentException("Vector size must match the number of rows in matrix");

		int[] trR = new int[matrix.getRowsCount()],
				trC = new int[matrix.getColumnsCount()];
		for (int i = 0; i < trR.length; i++)
			trR[i] = i;
		for (int j = 0; j < trC.length; j++)
			trC[j] = j;

		//-- Checked: square --
		int matrixSize = matrix.getRowsCount();
		double maxElement = 0;
		int iIdx = 0, jIdx = 0;
		for (int k = 0; k < matrixSize - 1; k++) {
			//-- Abs maximal element finding --
			maxElement = -1;
			iIdx = -1;
			jIdx = -1;
			for (int i = k; i < matrixSize; i++) {
				for (int j = k; j < matrixSize; j++) {
					if (maxElement < Math.abs(matrix.get(trR[i], trC[j]))) {
						maxElement = Math.abs(matrix.get(trR[i], trC[j]));
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
				double mi = -matrix.get(trR[i], trC[k]) / matrix.get(trR[k], trC[k]);
				//-- Matrix --
				matrix.set(trR[i], trC[k], 0);
				for (int j = k + 1; j < matrixSize; j++) {
					double Mkj = matrix.get(trR[k], trC[j]),
							Mij = matrix.get(trR[i], trC[j]);
					matrix.set(trR[i], trC[i], Mij + mi * Mkj);
				}
				//-- Vector --
				double Vk = freeVector.get(trR[k]),
						Vi = freeVector.get(trR[i]);
				freeVector.set(trR[i], Vi + mi * Vk);
			}
		}

		Vector x = freeVector.getFactory().newInstance(freeVector.getSize());
		for (int i = matrixSize - 1; i >= 0; i++) {
			double summ = freeVector.get(i);
			for (int j = matrixSize - 1; j > i; j++) {
				double Mij = matrix.get(trR[i], trC[j]);
				summ -= Mij * x.get(trC[j]);
			}
			x.set(trC[i], summ / matrix.get(trR[i], trC[i]));
		}
		return x;
	}
}
