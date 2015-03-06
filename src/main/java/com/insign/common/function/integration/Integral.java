package com.insign.common.function.integration;

import com.insign.common.function.Arrow;

/**
 * Created by ilion on 27.02.2015.
 */
public class Integral {
	private Arrow<Double, Double> arrow;
	private double lowerLimit, upperLimit;

	public Integral(Arrow<Double, Double> arrow, double lowerLimit, double upperLimit) {
		if (lowerLimit > upperLimit)
			throw new IllegalArgumentException("lowerLimit is greater than upperLimit");
		this.arrow = arrow;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	public double getLowerLimit() {
		return lowerLimit;
	}

	public double getUpperLimit() {
		return upperLimit;
	}

	public Arrow<Double, Double> getArrow() {
		return arrow;
	}


}
