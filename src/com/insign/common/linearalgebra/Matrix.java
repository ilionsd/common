package com.insign.common.linearalgebra;

/**
 * Created by ilion on 05.01.2015.
 */
public class Matrix {
    double[] array;
    int rowsCount;
    int columnsCount;

    public double get(int row, int column) {
        if (row < rowsCount && column < columnsCount)
            return array[columnsCount * row + column];
        else
            throw new IndexException();
    }
}
