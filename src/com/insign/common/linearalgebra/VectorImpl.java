package com.insign.common.linearalgebra;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by ilion on 05.01.2015.
 */
public class VectorImpl extends AbstractMatrix implements Vector {

    public VectorImpl(int size) {
        throw new NotImplementedException();
    }

    public VectorImpl(double[] array) {

    }

    @Override
    public double get(int index) {
        return 0;
    }

    @Override
    public Vector set(int index, double value) {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Vector add(Vector vector) {
        return null;
    }

    @Override
    public Matrix multiply(Matrix matrix) {
        return null;
    }

    @Override
    public Vector multiply(double multiplier) {
        return null;
    }
}
