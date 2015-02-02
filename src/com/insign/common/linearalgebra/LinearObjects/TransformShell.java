package com.insign.common.linearalgebra.LinearObjects;

import java.util.Objects;

/**
 * Created by ilion on 25.01.2015.
 */
public interface TransformShell extends MatrixBase {
	Matrix apply();

	Matrix matrix();

	void swapRows(int row1, int row2);

	void swapColumns(int column1, int column2);

}
