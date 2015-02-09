package com.insign.common.function;

/**
 * Created by ilion on 05.02.2015.
 */
public class SplineSegment {
	int power;

	double[] coefficients;

	double leftBound, rightBound;

	public SplineSegment(int power, double leftBound, double rightBound) {
		this.power = power;
		this.leftBound = leftBound;
		this.rightBound = rightBound;
		coefficients = new double[getPower() + 1];
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
		if (x < leftBound || x > rightBound)
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
}
