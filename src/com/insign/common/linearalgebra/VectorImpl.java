package com.insign.common.linearalgebra;

import com.insign.common.linearalgebra.exceptions.InvalidParameterException;

/**
 * Created by ilion on 05.01.2015.
 */
public final class VectorImpl extends AbstractVector {

    double[] array;
    int size;
    boolean isTransposed = false;

    public VectorImpl(int size) {
        this.size = size;
        array = new double[getSize()];
    }

    public VectorImpl(double[] vector) {
        if (!isValid(vector))
            throw new InvalidParameterException(vector, "Not null, not empty");
        for (int index = 0; index < getSize(); index++)
            set(index, vector[index]);
    }

    @Override
    public double get(int index) {
        return array[index];
    }

    @Override
    public void set(int index, double value) {
        array[index] = value;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Vector add(Vector vector) {
        if (vector == null)
            throw new NullPointerException("Vector cannot be null");
        if (getSize() != vector.getSize())
            throw new InvalidParameterException(vector, "Vectors cannot have unequal size");
        VectorImpl result = new VectorImpl(getSize());
        for (int index = 0; index < result.getSize(); index++)
            result.set(index, get(index) + vector.get(index));
        return result;
    }

    @Override
    public Matrix multiply(Matrix matrix) {
        if (matrix == null)
            throw new NullPointerException("Matrix cannot be null");
        if (1 != matrix.getRowsCount())
            throw new InvalidParameterException(matrix, "Cannot multiply " + matrix.getRowsCount() + "x" + matrix.getColumnsCount() + " matrix");
        MatrixImpl result = new MatrixImpl(getSize(), matrix.getColumnsCount());
        for (int i = 0; i < result.getRowsCount(); i++)
            for (int j = 0; j < result.getColumnsCount(); j++)
                result.set(i, j, get(i) * matrix.get(1, j));
        return result;
    }

    @Override
    public Vector multiply(double multiplier) {
        return null;
    }

    private static boolean isValid(double[] array) {
        if (array == null || array.length == 0)
            return false;
        return true;
    }

    @Override
    public VectorImpl transpose() {
        isTransposed = !isTransposed;
        return this;
    }
}
