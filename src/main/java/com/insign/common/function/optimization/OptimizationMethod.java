package com.insign.common.function.optimization;

import com.insign.common.function.Arrow;
import com.insign.common.function.Point2D;

/**
 * Created by ilion on 17.03.2015.
 */
public interface OptimizationMethod {

	Optimum optimize(Arrow<Double, Double> arrow, double leftLimit, double rightLimit, double precision);

}
