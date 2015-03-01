package com.insign.common.function;


import com.insign.common.integration.Integral;
import com.insign.common.integration.Intergrate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by ilion on 25.02.2015.
 */
public class NaturalSplineParametricCurve extends AbstractParametricCurve {

	private static final int NATURAL_PARAMETRIZATION_POINTS_COUNT = 30;
	private static final double NATURAL_PARAMETRIZATION_INTEGRAL_PRECISION = 0.001;

	Spline xs, ys;

	private NaturalSplineParametricCurve(Spline xs, Spline ys, double tMin, double tMax) {
		super(tMin, tMax);
		this.xs = new Spline(xs);
		this.ys = new Spline(ys);
	}

	@Override
	protected Spline getX() {
		return xs;
	}

	@Override
	protected Spline getY() {
		return ys;
	}


	private static Spline naturalParametrization() {
		throw new NotImplementedException();
	}

	public static NaturalSplineParametricCurve fromCurve(final AbstractParametricCurve curve) {
		throw new NotImplementedException();
	}

	public static NaturalSplineParametricCurve fromCurve(final SplineParametricCurve curve) {
		Arrow<Double, Double> arcLengthFunction = new Arrow<Double, Double>() {
			@Override
			public Double valueIn(Double x) {
				Point2D point = curve.derivative(1, x);
				return Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());
			}
		};
		double[] xtKnots = curve.getX().getKnots();
		double[] ytKnots = curve.getY().getKnots();
		double[] xyKnots = com.insign.common.ArrayUtils.union(xtKnots, ytKnots);
		//-- Points format (s, t) - function t(s) --
		Point2D[] ts = new Point2D[xyKnots.length];
		ts[0] = new Point2D(0, 0);
		for (int k = 1; k < xyKnots.length; k++) {
			Integral arcLengthIntegral = new Integral(arcLengthFunction, xyKnots[k - 1], xyKnots[k]);
			//-- Returns exact value for polynoms with power less or equals than 3 --
			double integralValue = Intergrate.GaussLegendre.twoPointRule(arcLengthIntegral, NATURAL_PARAMETRIZATION_INTEGRAL_PRECISION);
			ts[k] = new Point2D(integralValue, xyKnots[k]);
		}
		Spline tsSpline = Interpolation.Splines.Smoothing(ts, 1);
		Spline xs = curve.getX().reparameterize(tsSpline);
		Spline ys = curve.getY().reparameterize(tsSpline);
		return new NaturalSplineParametricCurve(xs, ys, xs.getLeftBound(), xs.getRightBound());
	}
}













