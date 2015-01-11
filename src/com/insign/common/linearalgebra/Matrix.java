package com.insign.common.linearalgebra;

/**
 * Created by ilion on 05.01.2015.
 */
public interface Matrix extends LinearObject {

	double get(int row, int column);

	void set(int row, int column, double value);

	int getRowsCount();

	int getColumnsCount();

	Matrix add(Matrix matrix);

	Matrix multiply(Matrix matrix);

	Matrix multiply(double multiplier);

	Vector multiply(Vector vector);

}
