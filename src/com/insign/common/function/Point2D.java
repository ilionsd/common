package com.insign.common.function;

import java.util.Objects;

/**
 * Created by ilion on 09.02.2015.
 */
public class Point2D {
	double x, y;

	public Point2D() {
		x = 0;
		y = 0;
	}

	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean equals(Point2D point) {
		Objects.requireNonNull(point);
		if (Math.abs(getX() - point.getX()) < Double.MIN_VALUE && Math.abs(getY() - point.getY()) < Double.MIN_VALUE)
			return true;
		else return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point2D)
			return equals((Point2D) obj);
		else return false;
	}
}
