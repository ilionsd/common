package com.insign.common.linearalgebra;

/**
 * Created by ilion on 05.01.2015.
 */
public interface Matrix {
    double get(int row, int column);

    Matrix set(int row, int column, double value);

    int getRowsCount();

    int getColumnsCount();


    Matrix Add(Matrix matrix);

    Matrix Multiply(Matrix matrix);

    public static class LinearSystem {

    }
}
