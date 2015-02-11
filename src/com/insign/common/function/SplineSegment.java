package com.insign.common.function;

import java.util.Arrays;

/**
 * Created by ilion on 05.02.2015.
 */
public class SplineSegment {
	private int power;

	private double[] coefficients;

	private double leftBound, rightBound;

	public SplineSegment(int power, double leftBound, double rightBound) {
		this.power = power;
		this.leftBound = leftBound;
		this.rightBound = rightBound;
		coefficients = new double[getPower() + 1];
	}

	public SplineSegment(double[] coefficients, double leftBound, double rightBound) {
		power = coefficients.length;
		this.coefficients = Arrays.copyOf(coefficients, coefficients.length);
		this.leftBound = leftBound;
		this.rightBound = rightBound;
	}

	public void set(int k, double value) {
		if (k > power)
			throw new ArrayIndexOutOfBoundsException(k);
		coefficients[k] = value;
	}

	public double get(int k) {
		if (k > power)
			throw new ArrayIndexOutOfBoundsException(k);
		return coefficients[k];
	}

	public int getPower() {
		return power;
	}

	public double valueIn(double x) {
		if (!isIn(x))
			throw new IllegalArgumentException("x should belong to the segment [" + leftBound + "; " + rightBound + "]");
		double xPow = 1;
		double xMul = x - leftBound;
		double result = 0;
		for (int k = 0; k < power; k++) {
			result += xPow * coefficients[k];
			xPow *= xMul;
		}
		return result;
	}

	public double getLeftBound() {
		return leftBound;
	}

	public double getRightBound() {
		return rightBound;
	}

	public boolean isIn(double x) {
		return (x + Double.MIN_VALUE > getLeftBound() && x - Double.MIN_VALUE < getRightBound());
	}
}
