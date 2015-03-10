package com.insign.common.function.interpolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilion on 10.03.2015.
 */
public class CubicSpline extends AbstractSpline implements Cloneable {
	private static final int MAX_POWER = 3;

	protected List<SplineSegment> splineList = new ArrayList<SplineSegment>();

	public CubicSpline(double[] coefficients, double leftBound, double rightBound) {
		super();
		SplineSegment ss = new SplineSegment(coefficients, leftBound, rightBound);
		if (ss.getPower() > MAX_POWER)
			throw new IllegalArgumentException("Cubic spline can not contain segments with power more than " + MAX_POWER + ".");
		getSplineList().add(ss);
		setPower(getSplineList().get(0).getPower());
	}

	public CubicSpline(SplineSegment splineSegment) {
		super();
		if (splineSegment.getPower() > MAX_POWER)
			throw new IllegalArgumentException("Cubic spline can not contain segments with power more than " + MAX_POWER + ".");
		getSplineList().add(new SplineSegment(splineSegment));
		setPower(getSplineList().get(0).getPower());
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
		addRight(ss, false);
	}

	@Override
	public void addRight(SplineSegment ss) {
		addRight(ss, true);
	}

	private void addRight(SplineSegment splineSegment, boolean needClone) {
		splineSegment = (needClone)? splineSegment.clone():splineSegment;
		if (splineSegment.getPower() > MAX_POWER)
			throw new IllegalArgumentException("Cubic spline can not contain segments with power more than " + MAX_POWER + ".");
		super.addRight(splineSegment);
	}

	@Override
	public void addLeft(double[] coefficients, double leftBound) {
		SplineSegment ss = new SplineSegment(coefficients, leftBound, getLeftBound());
		addLeft(ss, false);
	}

	@Override
	public void addLeft(SplineSegment ss) {
		addLeft(ss, true);
	}

	private void addLeft(SplineSegment splineSegment, boolean needClone) {
		splineSegment = (needClone)? splineSegment.clone():splineSegment;
		if (splineSegment.getPower() > MAX_POWER)
			throw new IllegalArgumentException("Cubic spline can not contain segments with power more than " + MAX_POWER + ".");
		super.addLeft(splineSegment);
	}
}
