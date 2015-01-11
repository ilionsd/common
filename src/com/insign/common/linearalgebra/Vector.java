package com.insign.common.linearalgebra;

/**
 * Created by ilion on 06.01.2015.
 */
public interface Vector extends LinearObject {
    double get(int index);
    void set(int index, double value);

    int getSize();

    Vector add(Vector vector);

    Vector multiply(Matrix matrix);

    Vector multiply(Vector vector);
    Vector multiply(double multiplier);
}
