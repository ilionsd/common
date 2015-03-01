package com.insign.common.function;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by ilion on 17.02.2015.
 */

public class TestCases {

	@Test
	@Ignore
	public void SplineInterpolationContinuous() {

	}

	@Test
	public void SplineInterpolationContinuousCustom() {
		Point2D[] arr = {
				new Point2D(0.0, 196.72677612304688),
				new Point2D(80.0, 196.72677612304688),
				new Point2D(212.0, 211.29629516601563),
				new Point2D(379.0, 265.4223327636719),
				new Point2D(528.0, 322.11962890625),
				new Point2D(660.0, 364.3900146484375),
				new Point2D(793.0, 409.0948791503906),
				new Point2D(942.0, 447.94354248046875),
				new Point2D(1075.0, 489.3707275390625),
				new Point2D(1206.0, 553.2316284179688)};

		Spline spline = Interpolation.Splines.Smoothing(arr, 0.5);

		Assert.assertTrue(Spline.isContinuous(spline, 1e-10));
	}

	@Test
	public void SplineInterpolationContinuousCustom2() {
		Point2D[] arr = {
				new Point2D(0, 1.231),
				new Point2D(0.1, 1.098),
				new Point2D(0.2, 0.982),
				new Point2D(0.3, 1.113),
				new Point2D(0.4, 1.347),
				new Point2D(0.5, 1.541),
				new Point2D(0.6, 1.215),
				new Point2D(0.7, 1.132),
				new Point2D(0.8, 1.073),
				new Point2D(0.9, 0.991)};

		Spline spline = Interpolation.Splines.Smoothing(arr, 1);

		Assert.assertTrue(Spline.isContinuous(spline, 1e-10));
	}
}
