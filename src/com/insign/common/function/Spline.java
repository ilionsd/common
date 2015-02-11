package com.insign.common.function;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilion on 04.02.2015.
 */
public class Spline implements Function {

	List<SplineSegment> splineList;

	public Spline(double[] coefficients, double leftBound, double rightBound) {
		splineList = new ArrayList<SplineSegment>();
		splineList.add(new SplineSegment(coefficients, leftBound, rightBound));
	}

	@Override
	public double valueIn(double x) {
		int left = 0;
		int right = splineList.size();
		int middle = 0;
		double value = 0;

		if (x < getLeftBound())
			value = splineList.get(0).valueIn(x);
		else if (x > getRightBound())
			value = splineList.get(splineList.size() - 1).valueIn(x);
		else do {
				middle = left + (right - left) / 2;
				if (splineList.get(middle).isIn(x)) {
					value = splineList.get(middle).valueIn(x);
					break;
				} else if (splineList.get(middle).getLeftBound() > x)
					right = middle - 1;
				else
					left = middle + 1;
			} while (left < right);

		return value;
	}

	public Spline addRight(double[] coefficients, double rightBound) {
		double existRightBound = getRightBound();
		if (existRightBound > rightBound)
			throw new RuntimeException("rightBound should be righter than exist rightBound");
		splineList.add(new SplineSegment(coefficients, existRightBound, rightBound));
		return this;
	}

	public Spline addLeft(double[] coefficients, double leftBound) {
		double existLeftBound = getLeftBound();
		if (existLeftBound < leftBound)
			throw new RuntimeException("leftBound should be lefter than exist leftBound");
		splineList.add(0, new SplineSegment(coefficients, leftBound, existLeftBound));
		return this;
	}

	public double getLeftBound() {
		return splineList.get(0).getLeftBound();
	}

	public double getRightBound() {
		return splineList.get(splineList.size() - 1).getRightBound();
	}





}
