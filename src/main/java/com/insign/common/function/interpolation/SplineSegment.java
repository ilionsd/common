package com.insign.common.function.interpolation;

import com.insign.common.FactorialUtil;
import com.insign.common.function.Function;

import java.util.Arrays;

/**
 * Created by ilion on 05.02.2015.
 */
public class SplineSegment implements Function<Double, Double>, Cloneable {
	private int power;

	private double[] coefficients;

	private double leftBound, rightBound;

	public SplineSegment(int power, double leftBound, double rightBound) {
		if (Double.compare(leftBound, rightBound) >= 0)
			throw new IllegalArgumentException("Left bound should be less than right bound: received leftBound=" + leftBound + " and rightBound=" + rightBound + ".");
		this.power = power;
		this.leftBound = leftBound;
		this.rightBound = rightBound;
		coefficients = new double[power() + 1];
	}

	public SplineSegment(double[] coefficients, double leftBound, double rightBound) {
		if (Double.compare(leftBound, rightBound) >= 0)
			throw new IllegalArgumentException("Left bound should be less than right bound: received leftBound=" + leftBound + " and rightBound=" + rightBound + ".");
		power = coefficients.length - 1;
		this.coefficients = Arrays.copyOf(coefficients, coefficients.length);
		this.leftBound = leftBound;
		this.rightBound = rightBound;
	}

	@Override
	public SplineSegment clone() {
		SplineSegment clone = null;
		try {
			clone = (SplineSegment) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		clone.coefficients = coefficients.clone();
		return clone;
	}

	public void set(int k, double value) {
		if (k > power())
			throw new ArrayIndexOutOfBoundsException(k);
		coefficients[k] = value;
	}

	public double get(int k) {
		if (k > power())
			throw new ArrayIndexOutOfBoundsException(k);
		return coefficients[k];
	}

	public int power() {
		return power;
	}

	private double accumulation(int order, double x) {
		double xPow = 1;
		double xMul = x - leftBound;
		double result = 0;
		for (int k = order; k <= power(); k++) {
			result += xPow * coefficients[k] * FactorialUtil.fallingFactorial(k, order);
			xPow *= xMul;
		}
		return result;
	}

	@Override
	public Double valueIn(Double x) {
		return accumulation(0, x);
	}

	@Override
	public Double derivative(int order, Double x) {
		return accumulation(order, x);
	}

	private static long descendingFactorial(long n, long k) {
		long fact = 1;
		if (k > n)
			return 1;
		for (long l = n - k + 1; l <= n; l++) {
			fact *= l;
		}
		return fact;
	}

	public double getLeftBound() {
		return leftBound;
	}

	public double getRightBound() {
		return rightBound;
	}

	public void setLeftBound(double leftBound) {
		this.leftBound = leftBound;
	}

	public void setRightBound(double rightBound) {
		this.rightBound = rightBound;
	}

	public boolean isIn(double x) {
		int cmpLeft = Double.compare(getLeftBound(), x);
		int cmpRight = Double.compare(x, getRightBound());
		return (cmpLeft <= 0) && (cmpRight <= 0);
	}

	@Override
	public String toString() {
		return toString('x');
	}

	public String toString(Character variable) {
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index <= power(); index++) {
			sb.append(monomToString(index, variable)).append(' ');
		}
		sb.append(segmentToString(variable));
		return sb.toString();
	}

	private String monomToString(int index, Character variable) {
		double coeff = get(index);
		StringBuilder sb = new StringBuilder();
		sb.append(signOfCoefficient(coeff))
				.append(' ')
				.append(Double.toString(Math.abs(coeff)));
		if (index > 0)
			sb.append(variable);
		if (index > 1)
			sb.append('^').append(index);
		return sb.toString();
	}

	private static Character signOfCoefficient(double coefficient) {
		if (coefficient < 0)
			return '-';
		else return '+';
	}

	private String segmentToString(Character variable) {
		StringBuilder sb = new StringBuilder()
				.append(variable).append(' ').append('∈').append(' ')
				.append('[').append(getLeftBound()).append(" ;").append(getRightBound()).append(']');
		return sb.toString();
	}

/*	@Override
	public SplineSegment reparameterize(final SplineSegment splineSegment) {
		if (Double.compare(getLeftBound(), splineSegment.getLeftBound()) != 0 || Double.compare(getRightBound(), splineSegment.getRightBound()) != 0)
			throw new IllegalArgumentException("Cannot reparameterize spline segments on unequal interval");
		double[] result = new double[] {get(0)};
		double[] replacement = new double[] {1};
		int k = 0;
		for (k = 1; k <= power(); k++) {
			replacement = multiplySplines(replacement, splineSegment.coefficients);
			result = addupSplines(result, multiplyScalar(replacement, get(k)));
		}
		k = result.length - 1;
		double delta = getRightBound() - getLeftBound();
		while (k > 0 && Double.compare(Math.abs(result[k] * Math.pow(delta, k - 1)), 1e-3) < 0)
			k--;
		if (k != result.length - 1)
			result = Arrays.copyOf(result, k + 1);
		SplineSegment ss = new SplineSegment(result, getLeftBound(), getRightBound());
		return ss;
	}

	private static double[] multiplySplines(final double[] coefficients1, final double[] coefficients2) {
		int maxPower = (coefficients1.length - 1) + (coefficients2.length - 1);
		double[] result = new double[maxPower + 1];
		for (int idx1 = 0; idx1 < coefficients1.length; idx1++) {
			for (int idx2 = 0; idx2 < coefficients2.length; idx2++) {
				result[idx1 + idx2] += coefficients1[idx1] * coefficients2[idx2];
			}
		}
		return result;
	}

	private static double[] addupSplines(final double[] coefficients1, final double[] coefficients2) {
		double[] result = new double[Math.max(coefficients1.length, coefficients2.length)];
		for (int k = 0; k < coefficients1.length; k++)
			result[k] += coefficients1[k];
		for (int k = 0; k < coefficients2.length; k++)
			result[k] += coefficients2[k];
		return result;
	}

	private static double[] multiplyScalar(final double[] coefficients, final double scalar) {
		double[] result = new double[coefficients.length];
		for (int k = 0; k < coefficients.length; k++)
			result[k] = coefficients[k] * scalar;
		return result;
	}*/
}















