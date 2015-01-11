package com.insign.common.linearalgebra;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by ilion on 06.01.2015.
 */
public class MatrixFactoryImpl implements MatrixFactory {

	@Override
	public Matrix newInstance(int rowsCount, int columnsCount) {
		return new MatrixImpl(rowsCount, columnsCount);
	}

	@Override
	public Matrix newInstance(double[][] matrix) {
		return new MatrixImpl(matrix);
	}
}
