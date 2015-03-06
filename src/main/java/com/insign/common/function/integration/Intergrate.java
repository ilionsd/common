package com.insign.common.function.integration;

import com.insign.common.function.Arrow;

/**
 * Created by ilion on 26.02.2015.
 */
public final class Intergrate {

	private static final int DEFAULT_INTERVAL_COUNT = 10;
	private static final double INTERGAL_STEP_MULTIPLIER = 1.5;

	private Intergrate() {}

	public static class GaussLegendre {

		private static final Knot[][] KNOTS = new Knot[][] {
				new Knot[0],
				//-- Exact knot coordinates --
				new Knot[] {new Knot(0, 2)},
				new Knot[] {
						new Knot(-Math.sqrt(1.0 / 3.0), 1),
						new Knot( Math.sqrt(1.0 / 3.0), 1)
				},
				new Knot[] {
						new Knot(0, 8.0 / 9.0),
						new Knot(-Math.sqrt(3.0 / 5.0), 5.0 / 9.0),
						new Knot( Math.sqrt(3.0 / 5.0), 5.0 / 9.0)
				},
				new Knot[] {
						new Knot(-Math.sqrt(3.0 / 7.0 - 2.0 / 7.0 * Math.sqrt(6.0 / 5.0)), (18.0 + Math.sqrt(30.0)) / 36.0),
						new Knot( Math.sqrt(3.0 / 7.0 - 2.0 / 7.0 * Math.sqrt(6.0 / 5.0)), (18.0 + Math.sqrt(30.0)) / 36.0),
						new Knot(-Math.sqrt(3.0 / 7.0 + 2.0 / 7.0 * Math.sqrt(6.0 / 5.0)), (18.0 - Math.sqrt(30.0)) / 36.0),
						new Knot( Math.sqrt(3.0 / 7.0 + 2.0 / 7.0 * Math.sqrt(6.0 / 5.0)), (18.0 - Math.sqrt(30.0)) / 36.0)
				},
				new Knot[] {
						new Knot(0, 128.0 / 225.0),
						new Knot(-1.0 / 3.0 * Math.sqrt(5.0 - 2.0 * Math.sqrt(10.0 / 7.0)), (322.0 + 13.0 * Math.sqrt(70.0)) / 900.0),
						new Knot( 1.0 / 3.0 * Math.sqrt(5.0 - 2.0 * Math.sqrt(10.0 / 7.0)), (322.0 + 13.0 * Math.sqrt(70.0)) / 900.0),
						new Knot(-1.0 / 3.0 * Math.sqrt(5.0 + 2.0 * Math.sqrt(10.0 / 7.0)), (322.0 - 13.0 * Math.sqrt(70.0)) / 900.0),
						new Knot( 1.0 / 3.0 * Math.sqrt(5.0 + 2.0 * Math.sqrt(10.0 / 7.0)), (322.0 - 13.0 * Math.sqrt(70.0)) / 900.0)
				}
				//-- Numeric knot coordinates --
				//-- ... --
		};

		private static class LinearVariableReplacement implements Arrow<Double, Double> {
			private double lowerLimit, upperLimit;
			public LinearVariableReplacement(double lowerLimit, double upperLimit) {
				this.lowerLimit = lowerLimit;
				this.upperLimit = upperLimit;
			}
			@Override
			public Double valueIn(Double x) {
				return (upperLimit - lowerLimit) / 2.0 * x + (upperLimit + lowerLimit) / 2.0;
			}

			public Double integralCorrectionCoefficient() {
				return (upperLimit - lowerLimit) / 2.0;
			}
		}

		private static double integrate(final Knot[] knots, final Arrow<Double, Double> arrow) {
			double integral = 0;
			for (int k = 0; k < knots.length; k++) {
				integral += knots[k].weight * arrow.valueIn(knots[k].point);
			}
			return integral;
		}

		public static double integrate(final int pointCountRule, final Integral integral, final double precision) {
			if (pointCountRule < 1 || pointCountRule > KNOTS.length - 1)
				throw new IllegalArgumentException("pointCountRule should be more or equals 1 and less or equals " + (KNOTS.length - 1));
			double integralValue = -precision * 2, integralPrevValue = 0;
			int intervalCount = DEFAULT_INTERVAL_COUNT;
			final double integrationInterval = integral.getUpperLimit() - integral.getLowerLimit();
			while (Math.abs(integralValue - integralPrevValue) > precision / 2.0) {
				integralPrevValue = integralValue;
				integralValue = 0;
				double step = integrationInterval / intervalCount;
				double xStart, xEnd = integral.getLowerLimit();
				for (int k = 0; k < intervalCount; k++) {
					xStart = xEnd;
					xEnd += step;
					final LinearVariableReplacement variableReplacement = new LinearVariableReplacement(xStart, xEnd);
					final Arrow<Double, Double> integratedArrow = new Arrow<Double, Double>() {
						@Override
						public Double valueIn(Double x) {
							return integral.getArrow().valueIn(variableReplacement.valueIn(x));
						}
					};
					integralValue += variableReplacement.integralCorrectionCoefficient() * integrate(KNOTS[pointCountRule], integratedArrow);
				}
				intervalCount *= INTERGAL_STEP_MULTIPLIER;
			}
			return (integralValue + integralPrevValue) / 2.0;
		}

		public static double twoPointRule(final Integral integral, final double precision) {
			final int knotsIndex = 2;
			return integrate(knotsIndex, integral, precision);
		}

		public static double fivePointRule(final Integral integral, final double precision) {
			final int knotsIndex = 5;
			return integrate(knotsIndex, integral, precision);
		}
	}

	public static class Knot {
		double point, weight;
		public Knot(double point, double weight) {this.point = point; this.weight = weight;}
	}
}












