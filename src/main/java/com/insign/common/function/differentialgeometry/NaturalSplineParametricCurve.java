package com.insign.common.function.differentialgeometry;


import com.insign.common.__;
import com.insign.common.function.Arrow;
import com.insign.common.function.Point2D;
import com.insign.common.function.integration.Integral;
import com.insign.common.function.integration.Intergrate;
import com.insign.common.function.interpolation.Interpolation;
import com.insign.common.function.interpolation.Spline;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by ilion on 25.02.2015.
 */
public class NaturalSplineParametricCurve extends AbstractParametricCurve implements NaturalParametricCurve, Cloneable {

	private static final double NATURAL_PARAMETRIZATION_INTEGRAL_PRECISION = 0.001;
	private static final int NATURAL_PARAMETERIZATION_KNOTS_COUNT = 100;
	private static final double NATURAL_PARAMETRIZATION_STEP = 0.01;

	Spline xs, ys;

	private NaturalSplineParametricCurve(Spline xs, Spline ys, double sMin, double sMax) {
		super(sMin, sMax);
		this.xs = new Spline(xs);
		this.ys = new Spline(ys);
	}

	@Override
	public NaturalSplineParametricCurve clone() {
		NaturalSplineParametricCurve clone = (NaturalSplineParametricCurve)super.clone();
		clone.xs = xs.clone();
		clone.ys = ys.clone();
		return clone;
	}

	@Override
	protected Spline getX() {
		return xs;
	}

	@Override
	protected Spline getY() {
		return ys;
	}

	public Spline getXSpline() {
		return getX().clone();
	}

	public Spline getYSpline() {
		return getY().clone();
	}

	private static Spline naturalParametrization() {
		throw new NotImplementedException();
	}

	public static NaturalSplineParametricCurve fromCurve(final AbstractParametricCurve curve) {
		return fromCurve(curve, null);
	}

	public static NaturalSplineParametricCurve fromCurve(final AbstractParametricCurve curve, __<Spline> naturalParametrization) {
		Arrow<Double, Double> arcLengthFunction = new Arrow<Double, Double>() {
			@Override
			public Double valueIn(Double x) {
				Point2D point = curve.derivative(1, x);
				return Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());
			}
		};
		Point2D[] ts = new Point2D[NATURAL_PARAMETERIZATION_KNOTS_COUNT];
		ts[0] = new Point2D(0, 0);
		double actualStep = (curve.getParameterMax() - curve.getParameterMin()) / (NATURAL_PARAMETERIZATION_KNOTS_COUNT - 1);

		for (int k = 1; k < NATURAL_PARAMETERIZATION_KNOTS_COUNT; k++) {
			double t = ts[k - 1].getY() + actualStep;
			Integral arcSegment = new Integral(arcLengthFunction, ts[k - 1].getY(), t);
			double value = Intergrate.GaussLegendre.fivePointRule(arcSegment, NATURAL_PARAMETRIZATION_INTEGRAL_PRECISION);
			ts[k] = new Point2D(value + ts[k - 1].getX(), t);
		}

		if (naturalParametrization != null) {
			Spline tsSpline = Interpolation.Splines.Smoothing(ts, 1);
			naturalParametrization.setRef(tsSpline);
		}

		Point2D[] xs = new Point2D[NATURAL_PARAMETERIZATION_KNOTS_COUNT],
				ys = new Point2D[NATURAL_PARAMETERIZATION_KNOTS_COUNT];

		for (int k = 0; k < ts.length; k++) {
			Point2D point = curve.valueIn(ts[k].getY());
			xs[k] = new Point2D(ts[k].getX(), point.getX());
			ys[k] = new Point2D(ts[k].getX(), point.getY());
		}

		Spline xsSpline = Interpolation.Splines.Smoothing(xs, 1);
		Spline ysSpline = Interpolation.Splines.Smoothing(ys, 1);

		NaturalSplineParametricCurve naturalParametricCurve = new NaturalSplineParametricCurve(xsSpline, ysSpline, xsSpline.getLeftBound(), xsSpline.getRightBound());

		return naturalParametricCurve;
	}

	public static NaturalSplineParametricCurve fromCurve(final SplineParametricCurve curve) {
		return fromCurve(curve, null);
	}

	public static NaturalSplineParametricCurve fromCurve(final SplineParametricCurve curve, __<Spline> naturalParametrization) {
		throw new NotImplementedException();
//		Arrow<Double, Double> arcLengthFunction = new Arrow<Double, Double>() {
//			@Override
//			public Double valueIn(Double x) {
//				Point2D point = curve.derivative(1, x);
//				return Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());
//			}
//		};
//		double[] xtKnots = curve.getX().getKnots();
//		double[] ytKnots = curve.getY().getKnots();
//		double[] xyKnots = com.insign.common.ArrayUtils.union(xtKnots, ytKnots);
//		//-- Points format (s, t) - function t(s) --
//		Point2D[] ts = new Point2D[xyKnots.length];
//		ts[0] = new Point2D(0, 0);
//		for (int k = 1; k < xyKnots.length; k++) {
//			Integral arcLengthIntegral = new Integral(arcLengthFunction, xyKnots[k - 1], xyKnots[k]);
//			//-- Returns exact value for polynoms with power less or equals than 3 --
//			double integralValue = Intergrate.GaussLegendre.twoPointRule(arcLengthIntegral, NATURAL_PARAMETRIZATION_INTEGRAL_PRECISION);
//			ts[k] = new Point2D(integralValue + ts[k - 1].getX(), xyKnots[k]);
//		}
//		Spline tsSpline = Interpolation.Splines.Smoothing(ts, 1);
//		Spline xs = curve.getX().reparameterize(tsSpline);
//		Spline ys = curve.getY().reparameterize(tsSpline);
//
//		if (naturalParametrization != null)
//			naturalParametrization.setRef(tsSpline);
//
//		return new NaturalSplineParametricCurve(xs, ys, xs.getLeftBound(), xs.getRightBound());
	}
}













