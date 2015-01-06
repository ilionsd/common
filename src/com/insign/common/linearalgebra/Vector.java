package com.insign.common.linearalgebra;

/**
 * Created by ilion on 06.01.2015.
 */
public interface Vector {
    double get(int index);

    Vector set(int index, double value);

    int getSize();

    Vector add(Vector vector);

    Matrix multiply(Matrix matrix);

    Vector multiply(double multiplier);
}
