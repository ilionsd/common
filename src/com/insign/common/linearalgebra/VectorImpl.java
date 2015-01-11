package com.insign.common.linearalgebra;


/**
 * Created by ilion on 05.01.2015.
 */
public final class VectorImpl extends AbstractVector {
    public static final VectorFactory FACTORY = new VectorFactoryImpl();

    double[] array;
    int size;
    boolean isTransposed = false;

    public VectorImpl(int size) {
        this.size = size;
        array = new double[getSize()];
    }

    public VectorImpl(double[] vector) {
        if (!isValid(vector))
            throw new IllegalArgumentException("Not null, not empty array expected");
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
