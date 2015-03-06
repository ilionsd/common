package com.insign.common.function.differentialgeometry;

import com.insign.common.function.interpolation.Spline;

/**
 * Created by ilion on 27.02.2015.
 */
public class SplineParametricCurve extends AbstractParametricCurve {

	Spline xt, yt;

	public SplineParametricCurve(Spline xt, Spline yt, double tMin, double tMax) {
		super(tMin, tMax);
		this.xt = new Spline(xt);
		this.yt = new Spline(yt);
	}

	@Override
	public SplineParametricCurve clone() {
		SplineParametricCurve clone = (SplineParametricCurve)super.clone();
		clone.xt = xt.clone();
		clone.yt = yt.clone();
		return clone;
	}

	@Override
	protected Spline getX() {
		return xt;
	}

	@Override
	protected Spline getY() {
		return yt;
	}

	public Spline getXSpline() {
		return getX().clone();
	}

	public Spline getYSpline() {
		return getY().clone();
	}
}
