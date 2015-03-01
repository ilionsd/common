package com.insign.common.function;

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
	protected Spline getX() {
		return xt;
	}

	@Override
	protected Spline getY() {
		return yt;
	}
}
