package com.insign.common.function.differentialgeometry;

import com.insign.common.function.Function;
import com.insign.common.function.Point2D;

/**
 * Created by ilion on 18.02.2015.
 */
public abstract class AbstractParametricCurve implements ParametricCurve {
	private static final double STEP_MULTIPLICATION_ERROR = 1e-10;

	private double parameterMin, parameterMax;

	public AbstractParametricCurve(double parameterMin, double parameterMax) {
		this.parameterMin = parameterMin;
		this.parameterMax = parameterMax;
	}

	@Override
	public AbstractParametricCurve clone() throws CloneNotSupportedException{
		return (AbstractParametricCurve)super.clone();
	}

	protected abstract Function<Double, Double> getX();

	protected abstract Function<Double, Double> getY();

	@Override
	public Point2D valueIn(Double t) {
		if (Double.compare(getParameterMin() - STEP_MULTIPLICATION_ERROR, t) > 0 || Double.compare(t, getParameterMax() + STEP_MULTIPLICATION_ERROR) > 0)
			throw new IllegalArgumentException("Parameter should be in [" + getParameterMin() + "; " + getParameterMax() + "]. Current t = " + t);
		return new Point2D(getX().valueIn(t), getY().valueIn(t));
	}

	@Override
	public Point2D derivative(int order, Double t) {
		if (Double.compare(getParameterMin(), t) > 0 || Double.compare(t, getParameterMax()) > 0)
			throw new IllegalArgumentException("Parameter should be in [" + getParameterMin() + "; " + getParameterMax() + "]. Current t = " + t);
		return new Point2D(getX().derivative(order, t), getY().derivative(order, t));
	}

	private static double cosAlpha(double L, double l1, double l2) {
		double cosb = (l1 * l1 + l2 * l2 - L * L) / (2.0 * l1 * l2);
		double cosAlpha = 2.0 * cosb * cosb - 1.0;
		return cosAlpha;
	}

	private static double radius(double L, double cosAlpha) {
		double radius = L / Math.sqrt((1 - cosAlpha) * 2.0);
		return radius;
	}

	@Override
	public double getParameterMin() {
		return parameterMin;
	}

	@Override
	public double getParameterMax() {
		return parameterMax;
	}
}
