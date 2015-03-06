package com.insign.common.function.differentialgeometry;

import com.insign.common.function.Function;
import com.insign.common.function.Point2D;

/**
 * Created by ilion on 05.03.2015.
 */
public interface ParametricCurve extends Function<Double, Point2D> {
	double getParameterMin();
	double getParameterMax();
}
