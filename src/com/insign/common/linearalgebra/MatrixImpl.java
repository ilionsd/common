package com.insign.common.linearalgebra;

import com.insign.common.linearalgebra.exceptions.IndexException;
import com.insign.common.linearalgebra.exceptions.InvalidParameterException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by ilion on 05.01.2015.
 */
public class MatrixImpl extends AbstractMatrix implements Matrix {

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

    @Override
    public double get(int row, int column) {
        if (row >= 0 && column >= 0 && row < rowsCount && column < columnsCount)
            return array[columnsCount * row + column];
        else
            throw new IndexException(row, column, rowsCount, columnsCount);
    }

    @Override
    public Matrix set(int row, int column, double value) {
        array[columnsCount * row + column] = value;
        return this;
    }

    @Override
    public int getRowsCount() {
        return rowsCount;
    }

    @Override
    public int getColumnsCount() {
        return columnsCount;
    }

    @Override
    public MatrixImpl add(Matrix matrix) {
        if (matrix == null)
            throw new NullPointerException("Matrix cannot be null");
        if (getRowsCount() != matrix.getRowsCount() || getColumnsCount() != matrix.getColumnsCount())
            throw new InvalidParameterException(matrix, "Matrix size is not " + getRowsCount() + "x" + getColumnsCount());
        MatrixImpl result = new MatrixImpl(getRowsCount(), getColumnsCount());
        for (int i = 0; i < getRowsCount(); i++)
            for (int j = 0; j < getColumnsCount(); j++)
                result.set(i, j, get(i, j) + matrix.get(i, j));
        return result;
    }

    @Override
    public MatrixImpl multiply(Matrix matrix) {
        if (matrix == null)
            throw new NullPointerException("matrix cannot be null");
        if (getColumnsCount() != matrix.getRowsCount())
            throw new InvalidParameterException(matrix, "Cannot multiply " + getRowsCount() + "x" + getColumnsCount() + " and " + matrix.getRowsCount() + "x" + matrix.getColumnsCount() + " matrix");
        MatrixImpl result = new MatrixImpl(getRowsCount(), matrix.getColumnsCount());
        for (int i = 0; i < result.getRowsCount(); i++)
            for (int j = 0; j < result.getColumnsCount(); j++) {
                double sum = 0;
                for (int k = 0; k < getColumnsCount(); k++)
                    sum += get(i, k) * matrix.get(k, j);
                result.set(i, j, sum);
            }
        return result;
    }

    @Override
    public MatrixImpl multiply(double multiplier) {
        MatrixImpl result = new MatrixImpl(getRowsCount(), getColumnsCount());
        for (int k = 0; k < result.array.length; k++)
            result.array[k] = array[k] * multiplier;
        return result;
    }

    @Override
    public VectorImpl multiply(Vector vector) {
        if (vector == null)
            throw new NullPointerException("matrix cannot be null");
        if (getColumnsCount() != vector.getSize())
            throw new InvalidParameterException(vector, "Cannot multiply " + getRowsCount() + "x" + getColumnsCount() + " and " + vector.getSize() + " vector");
        VectorImpl result = new VectorImpl(vector.getSize());
        for (int index = 0; index < result.getSize(); index++) {
            double sum = 0;
            for (int k = 0; k < vector.getSize(); k++)
                sum += get(index, k) * vector.get(k);
        }
        return result;
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
