package com.insign.common.function.interpolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilion on 10.03.2015.
 */
public class CubicSpline extends AbstractSpline implements Cloneable {
	private static final int MAX_POWER = 3;

	protected List<SplineSegment> splineList = new ArrayList<SplineSegment>();

	private void initialize(SplineSegment ss) {
		getSplineList().add(ss);
		setPower(getSplineList().get(0).power());
	}

	public CubicSpline(double[] coefficients, double leftBound, double rightBound) {
		super();
		SplineSegment ss = new SplineSegment(coefficients, leftBound, rightBound);
		if (ss.power() > MAX_POWER)
			throw new IllegalArgumentException("Cubic spline can not contain segments with power more than " + MAX_POWER + ".");
		initialize(ss);
	}

	public CubicSpline(SplineSegment splineSegment) {
		super();
		if (splineSegment.power() > MAX_POWER)
			throw new IllegalArgumentException("Cubic spline can not contain segments with power more than " + MAX_POWER + ".");
		initialize(splineSegment);
	}

	@Override
	public CubicSpline clone() {
		CubicSpline clone = null;
		try {
			clone = (CubicSpline)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		clone.splineList = new ArrayList<SplineSegment>();
		for (SplineSegment ss : splineList)
			clone.splineList.add(ss.clone());
		return clone;
	}

	@Override
	protected List<SplineSegment> getSplineList() {
		return splineList;
	}

	@Override
	public void addRight(double[] coefficients, double rightBound) {
		SplineSegment ss = new SplineSegment(coefficients, getRightBound(), rightBound);
		addRight(ss);
	}

	@Override
	public void addRight(SplineSegment splineSegment) {
		if (splineSegment.power() > MAX_POWER)
			throw new IllegalArgumentException("Cubic spline can not contain segments with power more than " + MAX_POWER + ".");
		super.addRight(splineSegment);
	}

	@Override
	public void addLeft(double[] coefficients, double leftBound) {
		SplineSegment ss = new SplineSegment(coefficients, leftBound, getLeftBound());
		addLeft(ss);
	}

	@Override
	public void addLeft(SplineSegment splineSegment) {
		if (splineSegment.power() > MAX_POWER)
			throw new IllegalArgumentException("Cubic spline can not contain segments with power more than " + MAX_POWER + ".");
		super.addLeft(splineSegment);
	}
}
