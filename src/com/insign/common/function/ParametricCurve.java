package com.insign.common.function;

/**
 * Created by ilion on 18.02.2015.
 */
public class ParametricCurve {
	private Function x, y;
	private double tMin, tMax;

	public ParametricCurve(Function x, Function y, double tMin, double tMax) {
		this.x = x;
		this.y = y;
		this.tMin = tMin;
		this.tMax = tMax;
	}

	public Point2D valueIn(double t) {
		if (Double.compare(getTMin(), t) > 0 || Double.compare(t, getTMax()) > 0)
			throw new IllegalArgumentException("Parameter t should be in [" + getTMin() + "; " + getTMax() + "]. Current t = " + t);
		return new Point2D(x.valueIn(t), y.valueIn(t));
	}

	public Spline naturalParametrization(double step) {
		//ArrayList<Point2D> xy = new ArrayList<Point2D>();
		Point2D xyPrev = null,
				xy = null,
				xyHalfStep = null;

		int segmentCount = (int) Math.ceil((getTMax() - getTMin()) / step);
		double actualStep = (getTMax() - getTMin()) / segmentCount;
		double halfStep = actualStep / 2.0;
		double t = getTMin();

		//-- function t(s) --
		Point2D[] ts = new Point2D[segmentCount + 1];
		//--  Point(s0, t0)  --
		ts[0] = new Point2D(0, 0);
		xy = valueIn(t);
		double L, l1, l2;
		double alpha, cosAlpha, radius;
		for (int k = 1; k <= segmentCount; k++) {
			xyPrev = xy;
			xyHalfStep = valueIn(t + halfStep);
			t += actualStep;
			xy = valueIn(t);
			L = Point2D.distance(xyPrev, xy);
			l1 = Point2D.distance(xyPrev, xyHalfStep);
			l2 = Point2D.distance(xyHalfStep, xy);
			cosAlpha = ParametricCurve.cosAlpha(L, l1, l2);
			radius = ParametricCurve.radius(L, cosAlpha);
			alpha = Math.acos(cosAlpha);
			//-- Point(sk, tk) --
			ts[k] = new Point2D(alpha * radius, t);
		}
		Spline tsSpline = Interpolation.SmoothingSpline(ts, 1);

		return tsSpline;
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
