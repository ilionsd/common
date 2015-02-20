package com.insign.common.linearalgebra.LinearObjects;

/**
 * Created by ilion on 06.01.2015.
 */
public interface LinearObject {
	void transpose();

	int getRowsCount();

	int getColumnsCount();

	LinearObjectFactory getFactory();
}
