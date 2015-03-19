package com.insign.common.function.optimization;

import com.insign.common.function.Arrow;
import com.insign.common.function.Point2D;

/**
 * Created by ilion on 18.03.2015.
 */
public class SquareInterpolation implements OptimizationMethod {

	@Override
	public Optimum optimize(Arrow<Double, Double> arrow, double leftLimit, double rightLimit, double precision) {
		double delta, x1, x2, f1, f2, x3, f3, xmin, fmin, xintmin, fintmin;
		delta = (rightLimit - leftLimit) / 100.0;
		x1 = (rightLimit + leftLimit) / 2.0;
		int iteration = 0;

		do
		{
			iteration++;
			x2 = x1 + delta;
			f1 = arrow.valueIn(x1);
			f2 = arrow.valueIn(x2);
			if (f1 < f2)
				x3 = x2 + delta;
			else x3 = x1 - delta;
			f3 = arrow.valueIn(x3);
			if (f1 < f2 && f1 < f3)
			{
				fmin = f1;
				xmin = x1;
			}
			else if (f2 < f3)
			{
				fmin = f2;
				xmin = x2;
			}
			else
			{
				fmin = f3;
				xmin = x3;
			}

			xintmin = (((x2 * x2 - x3 * x3) * f1 + (x3 * x3 - x1 * x1) * f2 + (x1 * x1 - x2 * x2) * f3) / ((x2 - x3) * f1 + (x3 - x1) * f2 + (x1 - x2) * f3)) / 2.0;
			fintmin = arrow.valueIn(xintmin);
			if ((xmin - xintmin) / xintmin < precision && (fmin - fintmin) / fintmin < precision)
				break;
			else if ((x1 < xintmin && xintmin < x3) || (x3 < xintmin && xintmin < x1))
			{
				if (fmin < fintmin)
					x1 = xmin;
				else x1 = xintmin;
			}
			else x1 = xintmin;
		}
		while (true);
		Point2D extremum = new Point2D((xmin + xintmin) / 2.0, arrow.valueIn((xmin + xintmin) / 2.0));
		return null;
	}
}
