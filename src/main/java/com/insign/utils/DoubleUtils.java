package com.insign.utils;

/**
 * Created by ilion on 07.05.2015.
 */
public class DoubleUtils {
	public DoubleUtils() {super();}

	public static boolean isBetween(double target, double leftLimit, double rightLimit) {
		return Double.compare(target, leftLimit) > 0 && Double.compare(target, rightLimit) < 0;
	}
}
