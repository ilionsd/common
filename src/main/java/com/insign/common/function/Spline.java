package com.insign.common.function;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilion on 04.02.2015.
 */
public class Spline implements Function<Double, Double>, Reparameterizable<Spline> {

	private List<SplineSegment> splineList = new ArrayList<SplineSegment>();

	public Spline(double[] coefficients, double leftBound, double rightBound) {
		splineList.add(new SplineSegment(coefficients, leftBound, rightBound));
	}

	public Spline(SplineSegment splineSegment) {
		splineList.add(new SplineSegment(splineSegment));
	}

	public Spline(Spline spline) {
		for (SplineSegment ss : spline.splineList) {
			splineList.add(new SplineSegment(ss));
		}
	}

	private int getSegment(final double x) {
		int segmentIndex = -1;
		if (x < getLeftBound())
			segmentIndex = 0;
		else if (x > getRightBound())
			segmentIndex = splineList.size() - 1;
		else {
		/*
			for (int k = 0; k < splineList.size(); k++)
				if (splineList.get(k).isIn(x)) {
					segmentIndex = k;
				}
		*/
			int middle;
			int left = 0, right = splineList.size() - 1;
			//-- Binary search commented because sometimes failing search and value variable stay with initial value --
			do {
				middle = left + (right - left) / 2;
				if (splineList.get(middle).isIn(x)) {
					segmentIndex =  middle;
				} else if (splineList.get(middle).getLeftBound() > x)
					right = middle - 1;
				else
					left = middle + 1;
			} while (left <= right);
		}
		return segmentIndex;
	}

	@Override
	public Double valueIn(final Double x) {
		return splineList.get(getSegment(x)).valueIn(x);
	}

	@Override
	public Double derivative(final int order, final Double x) {
		return splineList.get(getSegment(x)).derivative(order, x);
	}

	public Spline addRight(double[] coefficients, double rightBound) {
		double existRightBound = getRightBound();
		if (existRightBound > rightBound)
			throw new RuntimeException("rightBound should be righter than exist rightBound");
		splineList.add(new SplineSegment(coefficients, existRightBound, rightBound));
		return this;
	}

	public Spline addRight(SplineSegment ss) {
		if (Double.compare(getRightBound(), ss.getLeftBound()) != 0)
			throw new IllegalArgumentException("Spline segment discontinue spline");
		splineList.add(new SplineSegment(ss));
		return this;
	}

	public Spline addLeft(double[] coefficients, double leftBound) {
		double existLeftBound = getLeftBound();
		if (existLeftBound < leftBound)
			throw new RuntimeException("leftBound should be lefter than exist leftBound");
		splineList.add(0, new SplineSegment(coefficients, leftBound, existLeftBound));
		return this;
	}

	public Spline addLeft(SplineSegment ss) {
		if (Double.compare(ss.getRightBound(), getLeftBound()) != 0)
			throw new IllegalArgumentException("Spline segment discontinue spline");
		splineList.add(0, new SplineSegment(ss));
		return this;
	}

	public double getLeftBound() {
		return splineList.get(0).getLeftBound();
	}

	public double getRightBound() {
		return splineList.get(splineList.size() - 1).getRightBound();
	}

	public double[] getKnots() {
		double[] knots = new double[splineList.size() + 1];
		for (int k = 0; k < splineList.size(); k++)
			knots[k] = splineList.get(k).getLeftBound();
		knots[knots.length - 1] = getRightBound();
		return knots;
	}

	public Point2D[] getPoints() {
		Point2D[] points = new Point2D[splineList.size() + 1];
		for (int k = 0; k < splineList.size(); k++)
			points[k] = new Point2D(splineList.get(k).getLeftBound(), valueIn(splineList.get(k).getLeftBound()));
		points[points.length - 1] = new Point2D(getRightBound(), valueIn(getRightBound()));
		return points;
	}

	public static boolean isContinuous(Spline spline, double eps) {
		for (int k = 0; k < spline.splineList.size() - 1; k++) {
			double bound = spline.splineList.get(k).getRightBound();
			double valueLeft = spline.splineList.get(k).valueIn(bound);
			double valueRight = spline.splineList.get(k + 1).valueIn(bound);
			if (Math.abs(valueRight - valueLeft) > eps)
				return false;
		}
		return true;
	}

	@Override
	public Spline reparameterize(final Spline spline) {
		double[] selfKnots = getKnots();
		double[] otherKnots = spline.getKnots();
		double[] overallKnots = com.insign.common.ArrayUtils.union(selfKnots, otherKnots);
		ArrayList<SplineSegment> ssList = new ArrayList<SplineSegment>();
		for (int k = 1; k < overallKnots.length; k++) {
			double middle = (overallKnots[k - 1] + overallKnots[k]) / 2.0;
			int selfIndex = getSegment(middle);
			int otherIndex = spline.getSegment(middle);
			ssList.add(splineList.get(selfIndex).reparameterize(spline.splineList.get(otherIndex)));
		}
		Spline reparametrized = new Spline(ssList.get(0));
		for (SplineSegment ss : ssList)
			reparametrized.addRight(ss);
		return reparametrized;
	}
}
