package com.insign.common.function;

import java.util.Objects;

/**
 * Created by ilion on 09.02.2015.
 */
public class Point2D implements Cloneable{
	public static Point2D ORIGIN = new Point2D(0, 0);

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

	public Point2D negate() {
		return new Point2D(-getX(), -getY());
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
	public Object clone() {
		Point2D clone = null;
		try {
			clone = (Point2D)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
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


	public static class asVector {

		public static double module(Point2D point) {
			Objects.requireNonNull(point);
			return Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());
		}

		public static double dotProduct(Point2D point1, Point2D point2) {
			Objects.requireNonNull(point1);
			Objects.requireNonNull(point2);
			return point1.getX() * point2.getX() + point1.getY() * point2.getY();
		}

		public static double angle(Point2D point1, Point2D point2) {
			Objects.requireNonNull(point1);
			Objects.requireNonNull(point2);
			return asVector.dotProduct(point1, point2) / (asVector.module(point1) * asVector.module(point2));
		}
	}
}





















