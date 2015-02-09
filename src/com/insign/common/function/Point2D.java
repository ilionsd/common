package com.insign.common.function;

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
}
