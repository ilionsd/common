package com.insign.common.linearalgebra;

import com.insign.common.linearalgebra.exceptions.IndexException;
import com.insign.common.linearalgebra.exceptions.InvalidParameterException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by ilion on 05.01.2015.
 */
public class MatrixImpl implements Matrix {
    protected double[] array;
    protected int rowsCount;
    protected int columnsCount;

    public MatrixImpl(int rowsCount, int columnsCount) {
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
        array = new double[getRowsCount() * getColumnsCount()];
    }

    public MatrixImpl(double[][] matrix) {
        if (!isValid(matrix))
            throw new InvalidParameterException(matrix, "Not null rectangular matrix");
        rowsCount = matrix.length;
        columnsCount = matrix[0].length;

        array = new double[rowsCount * columnsCount];
        for (int row = 0; row < getRowsCount(); row++)
            for (int column = 0; column < getColumnsCount(); column++)
                set(row, column, matrix[row][column]);
    }

    public static Matrix E(int size) {
        Matrix e = new MatrixImpl(size, size);
        for (int k = 0; k < size; k++)
            e.set(k, k, 1.0);
        return e;
    }

    public double get(int row, int column) {
        if (row >= 0 && column >= 0 && row < rowsCount && column < columnsCount)
            return array[columnsCount * row + column];
        else
            throw new IndexException(row, column, rowsCount, columnsCount);
    }

    public Matrix set(int row, int column, double value) {
        array[columnsCount * row + column] = value;
        return this;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColumnsCount() {
        return columnsCount;
    }

    public MatrixImpl Add(Matrix matrix) {
        throw new NotImplementedException();
    }

    public MatrixImpl Multiply(Matrix matrix) {
        throw new NotImplementedException();
    }


    protected static boolean isValid(double[][] array) {
        if (array == null || array.length == 0 || array[0] == null || array[0].length == 0)
            return false;
        else {
            int columnsCount = array[0].length;
            for (int k = 1; k < array.length; k++)
                if (array[k] == null || array[k].length != columnsCount)
                    return false;
        }
        return true;
    }
}
