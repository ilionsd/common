package com.insign.common.function.differentialgeometry;

import com.insign.common.function.interpolation.CubicSpline;

/**
 * Created by ilion on 27.02.2015.
 */
public class CubicSplineParametricCurve extends AbstractParametricCurve {

	CubicSpline xt, yt;

	public CubicSplineParametricCurve(CubicSpline xt, CubicSpline yt, double tMin, double tMax) {
		super(tMin, tMax);
		this.xt = xt.clone();
		this.yt = yt.clone();
	}

	@Override
	public CubicSplineParametricCurve clone() {
		CubicSplineParametricCurve clone = null;
		try {
			clone = (CubicSplineParametricCurve)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		clone.xt = xt.clone();
		clone.yt = yt.clone();
		return clone;
	}

	@Override
	protected CubicSpline getX() {
		return xt;
	}

	@Override
	protected CubicSpline getY() {
		return yt;
	}

	public CubicSpline getXSpline() {
		return getX().clone();
	}

	public CubicSpline getYSpline() {
		return getY().clone();
	}
}
