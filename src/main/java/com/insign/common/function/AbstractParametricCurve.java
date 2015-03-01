package com.insign.common.function;

/**
 * Created by ilion on 18.02.2015.
 */
public abstract class AbstractParametricCurve implements Function<Double, Point2D> {
	private final static double NATURAL_PARAMETRIZATION_STEP = 0.01;
	private final static double NATURAL_PARAMETRIZATION_INTEGRATION_STEP = 0.01;
	private final static int NATURAL_PARAMETRIZATION_INTERPOLATION_POINTS_COUNT = 20;

	protected double tMin, tMax;
	protected Spline naturalParametrization = null;

	public AbstractParametricCurve(double tMin, double tMax) {
		this.tMin = tMin;
		this.tMax = tMax;
	}

	protected abstract Function<Double, Double> getX();

	protected abstract Function<Double, Double> getY();

	@Override
	public Point2D valueIn(Double t) {
		if (Double.compare(getTMin(), t) > 0 || Double.compare(t, getTMax()) > 0)
			throw new IllegalArgumentException("Parameter t should be in [" + getTMin() + "; " + getTMax() + "]. Current t = " + t);
		return new Point2D(getX().valueIn(t), getY().valueIn(t));

	}

	/*public static Spline naturalParametrization() {

		return null;
	}*/

//	public Spline naturalParametrization() {
//		if (naturalParametrization == null)
//			naturalParametrization = naturalParametrizationCircleApproximation(NATURAL_PARAMETRIZATION_STEP);
//		return naturalParametrization;
//	}
//
//	private Spline naturalParametrizationCircleApproximation(double step) {
//		Point2D xyPrev = null,
//				xy = null,
//				xyHalfStep = null;
//
//		int segmentCount = (int) Math.ceil((getTMax() - getTMin()) / step);
//		double actualStep = (getTMax() - getTMin()) / segmentCount;
//		double halfStep = actualStep / 2.0;
//		double t = getTMin();
//
//		//-- function t(s) --
//		Point2D[] ts = new Point2D[segmentCount + 1];
//		//--  Point(s0, t0)  --
//		ts[0] = new Point2D(0.0, 0.0);
//		xy = valueIn(t);
//		double L, l1, l2;
//		double alpha, cosAlpha, radius;
//		for (int k = 1; k <= segmentCount; k++) {
//			xyPrev = xy;
//			xyHalfStep = valueIn(t + halfStep);
//			t += actualStep;
//			xy = valueIn(t);
//			L = Point2D.distance(xyPrev, xy);
//			l1 = Point2D.distance(xyPrev, xyHalfStep);
//			l2 = Point2D.distance(xyHalfStep, xy);
//			cosAlpha = AbstractParametricCurve.cosAlpha(L, l1, l2);
//			radius = AbstractParametricCurve.radius(L, cosAlpha);
//			alpha = Math.acos(cosAlpha);
//			//-- Point(sk, tk) --
//			ts[k] = new Point2D(alpha * radius, t);
//		}
//		Spline tsSpline = Interpolation.Splines.Smoothing(ts, 1);
//
//		return tsSpline;
//	}
//
//	private Spline naturalParametrizationLinearApproximation(double step) {
//		Point2D xyPrev, xy;
//
//		int segmentCount = (int) Math.ceil((getTMax() - getTMin()) / step);
//		double actualStep = (getTMax() - getTMin()) / segmentCount;
//		double t = getTMin();
//
//		//-- function t(s) --
//		Point2D[] ts = new Point2D[segmentCount + 1];
//		//--  Point(s0, t0)  --
//		ts[0] = new Point2D(0, 0);
//		xy = valueIn(t);
//		double L, l1, l2;
//		double alpha, cosAlpha, radius;
//		for (int k = 1; k <= segmentCount; k++) {
//			xyPrev = xy;
//			t += actualStep;
//			xy = valueIn(t);
//			//-- Point(sk, tk) --
//			ts[k] = new Point2D(Point2D.distance(xyPrev, xy), t);
//		}
//		Spline tsSpline = Interpolation.Splines.Smoothing(ts, 1);
//
//		return tsSpline;
//	}

	@Override
	public Point2D derivative(int order, Double t) {
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

	public double getTMin() {
		return tMin;
	}

	public double getTMax() {
		return tMax;
	}
}
