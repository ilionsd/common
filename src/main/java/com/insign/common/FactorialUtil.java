package com.insign.common;

/**
 * Created by ilion on 11.03.2015.
 */
public class FactorialUtil {
	public FactorialUtil(){super();}

	/**
	 * Returns falling sequential product of n elements starts from x
	 * @param x - first element in falling sequence
	 * @param n - elements count in sequence
	 * @return falling sequential product
	 */
	public static long fallingFactorial(long x, long n) {
		if (x < 0 || n < 0)
			throw new IllegalArgumentException("x should be more or equals than 0 and n should be more than 0");
		if (x < n)
			throw new IllegalArgumentException("x should be more than n");
		if (x == 0 || n == 0)
			return 1;
		long fact = 1;
		long xEnd = x - n + 1;
		for (long l = n; l >= xEnd; l--) {
			fact *= l;
		}
		return fact;
	}
}
