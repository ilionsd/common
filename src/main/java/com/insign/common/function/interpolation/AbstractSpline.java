package com.insign.common.function.interpolation;

import com.insign.common.function.Point2D;

import java.util.List;

/**
 * Created by ilion on 04.02.2015.
 */
public abstract class AbstractSpline implements Spline {

	private int power = 0;

	public AbstractSpline() {super();}

	@Override
	public AbstractSpline clone() throws CloneNotSupportedException {
		return (AbstractSpline)super.clone();
	}

	public int getSegment(final double x) {
		int segmentIndex = -1;
		if (x < getLeftBound())
			segmentIndex = 0;
		else if (x > getRightBound())
			segmentIndex = getSplineList().size() - 1;
		else {
		/*
			for (int k = 0; k < getSplineList().size(); k++)
				if (getSplineList().get(k).isIn(x)) {
					segmentIndex = k;
				}
		*/
			int middle;
			int left = 0, right = getSplineList().size() - 1;
			//-- Binary search commented because sometimes failing search and value variable stay with initial value --
			do {
				middle = left + (right - left) / 2;
				if (getSplineList().get(middle).isIn(x)) {
					segmentIndex =  middle;
					break;
				} else if (getSplineList().get(middle).getLeftBound() > x)
					right = middle - 1;
				else
					left = middle + 1;
			} while (left <= right);
		}
		return segmentIndex;
	}

	@Override
	public int getPower() { return power;}

	protected void setPower(int power) {
		this.power = power;
	}

	protected abstract List<SplineSegment> getSplineList();

	@Override
	public SplineSegment get(int k) {
		return getSplineList().get(k);
	}

	@Override
	public int segmentCount() {
		return getSplineList().size();
	}

	@Override
	public Double valueIn(final Double x) {
		return getSplineList().get(getSegment(x)).valueIn(x);
	}

	@Override
	public Double derivative(final int order, final Double x) {
		return getSplineList().get(getSegment(x)).derivative(order, x);
	}

	@Override
	public void addRight(double[] coefficients, double rightBound) {
		double existRightBound = getRightBound();
		SplineSegment ss = new SplineSegment(coefficients, existRightBound, rightBound);
		addRight(ss);
	}

	@Override
	public void addRight(SplineSegment ss) {
		if (Double.compare(getRightBound(), ss.getLeftBound()) != 0)
			throw new IllegalArgumentException("Spline segment discontinue spline");
		getSplineList().add(ss);
		if (getPower() < ss.power())
			setPower(ss.power());
	}

	@Override
	public void addLeft(double[] coefficients, double leftBound) {
		double existLeftBound = getLeftBound();
		SplineSegment ss = new SplineSegment(coefficients, leftBound, existLeftBound);
		addLeft(ss);
	}

	@Override
	public void addLeft(SplineSegment ss) {
		if (Double.compare(ss.getRightBound(), getLeftBound()) != 0)
			throw new IllegalArgumentException("Spline segment discontinue spline");
		getSplineList().add(0, ss);
		if (getPower() < ss.power())
			setPower(ss.power());
	}

	@Override
	public double getLeftBound() {
		return getSplineList().get(0).getLeftBound();
	}

	@Override
	public double getRightBound() {
		return getSplineList().get(getSplineList().size() - 1).getRightBound();
	}

	public double[] getKnots() {
		double[] knots = new double[getSplineList().size() + 1];
		for (int k = 0; k < getSplineList().size(); k++)
			knots[k] = getSplineList().get(k).getLeftBound();
		knots[knots.length - 1] = getRightBound();
		return knots;
	}

	public Point2D[] getPoints() {
		Point2D[] points = new Point2D[getSplineList().size() + 1];
		for (int k = 0; k < getSplineList().size(); k++)
			points[k] = new Point2D(getSplineList().get(k).getLeftBound(), valueIn(getSplineList().get(k).getLeftBound()));
		points[points.length - 1] = new Point2D(getRightBound(), valueIn(getRightBound()));
		return points;
	}

	public static boolean isContinuous(AbstractSpline spline, double eps) {
		for (int k = 0; k < spline.getSplineList().size() - 1; k++) {
			double bound = spline.getSplineList().get(k).getRightBound();
			double valueLeft = spline.getSplineList().get(k).valueIn(bound);
			double valueRight = spline.getSplineList().get(k + 1).valueIn(bound);
			if (Math.abs(valueRight - valueLeft) > eps)
				return false;
		}
		return true;
	}

/*	@Override
	public Spline reparameterize(final Spline spline) {
		double[] selfKnots = getKnots();
		double[] otherKnots = spline.getKnots();
		double[] overallKnots = com.insign.common.ArrayUtils.union(selfKnots, otherKnots);
		ArrayList<SplineSegment> ssList = new ArrayList<SplineSegment>();
		for (int k = 1; k < overallKnots.length; k++) {
			double middle = (overallKnots[k - 1] + overallKnots[k]) / 2.0;
			int selfIndex = getSegment(middle);
			int otherIndex = spline.getSegment(middle);
			SplineSegment ssSelf = getSplineList().get(selfIndex).clone();
			ssSelf.setLeftBound(overallKnots[k - 1]);
			ssSelf.setRightBound(overallKnots[k]);
			SplineSegment ssOther = spline.getSplineList().get(otherIndex).clone();
			ssOther.setLeftBound(overallKnots[k - 1]);
			ssOther.setRightBound(overallKnots[k]);
			ssList.add(ssSelf.reparameterize(ssOther));
		}
		Spline reparametrized = new Spline(ssList.get(0));
		for (int k = 1; k < ssList.size(); k++)
			reparametrized.addRight(ssList.get(k));
		return reparametrized;
	}*/
}
