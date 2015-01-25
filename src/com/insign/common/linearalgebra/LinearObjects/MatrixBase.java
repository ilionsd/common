package com.insign.common.linearalgebra.LinearObjects;

/**
 * Created by ilion on 25.01.2015.
 */
public interface MatrixBase extends LinearObject {
	double get(int row, int column);

	void set(int row, int column, double value);

	Vector getRow(int row);

	Vector getColumn(int column);

	void setRow(int row, Vector vector);

	void setColumn(int column, Vector vector);
}
