package com.insign.common.function.interpolation;

import com.insign.common.function.Function;

/**
 * Created by ilion on 10.03.2015.
 */
public interface Spline extends Function<Double, Double> {
	int getPower();

	void addRight(double[] coefficients, double rightBound);
	void addRight(SplineSegment ss);

	void addLeft(double[] coefficients, double leftBound);
	void addLeft(SplineSegment ss);

	double getLeftBound();
	double getRightBound();

	SplineSegment get(int k);

	int segmentCount();
}
