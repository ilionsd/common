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
		if (Double.compare(getX(), point.getX()) == 0 && Double.compare(getY(), point.getY()) == 0)
			return true;
		else return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point2D)
			return equals((Point2D) obj);
		else return false;
	}

	@Override
	public String toString() {
		return "(" + getX() + "; " + getY() + ")";
	}

	public static double distance(Point2D point1, Point2D point2) {
		Objects.requireNonNull(point1);
		Objects.requireNonNull(point2);
		double deltaX = point1.getX() - point2.getX();
		double deltaY = point1.getY() - point2.getY();
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}
}





















