package com.insign.common.function;

import com.insign.common.linearalgebra.LinearObjects.Matrix;
import com.insign.common.linearalgebra.LinearObjects.Vector;
import com.insign.common.linearalgebra.MatrixImpl;
import com.insign.common.linearalgebra.VectorImpl;
import com.insign.common.linearalgebra.solve.LAES;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by ilion on 04.02.2015.
 */
public interface Interpolation {
	//private Interpolation(){}

	public interface Method<ResultingFunction> {
		public ResultingFunction interpolate(final Point2D[] points, Object[] params);
	}

	public static class Splines {

		public interface Coefficient extends Arrow<Integer, Double> {

		}

		public static Spline Smoothing(final Point2D[] points, final double lambda) {
			//-- x indexes changes from 0 to n  --
			int n = points.length - 1;
			//-- h indexes changes from 0 to n-1 --
			int nh = n - 1;
			final double[] h = new double[nh + 1];
			for (int k = 0; k <= nh; k++)
				h[k] = points[k + 1].getX() - points[k].getX();
			//-- R indexes changes from 0 to n-2 and from 0 to n-2 --
			int nR = n - 2;
			Matrix R = MatrixImpl.FACTORY.newInstance(nR + 1, nR + 1);
			R.set(0, 0, 2.0 * (h[0] + h[1]));
			R.set(0, 1, h[1]);
			for (int i = 1; i <= nR - 1; i++) {
				R.set(i, i - 1, h[i]);
				R.set(i, i, 2.0 * (h[i] + h[i + 1]));
				R.set(i, i + 1, h[i + 1]);
			}
			R.set(nR, nR - 1, h[nR]);
			R.set(nR, nR, 2.0 * (h[nR] + h[nR + 1]));

			//-- Qt indexes changes from 0 to n-2 and from 0 to n --
			int nQtRow = n - 2;
			int nQtCol = n;
			Matrix Qt = MatrixImpl.FACTORY.newInstance(nQtRow + 1, nQtCol + 1);
			double rRight = 0;
			double rLeft = 3.0 / h[0];
			//-- last index = n-2 --
			for (int i = 0; i <= nQtRow; i++) {
				rRight = 3.0 / h[i + 1];
				Qt.set(i, i, rLeft);
				Qt.set(i, i + 1, -(rLeft + rRight));
				Qt.set(i, i + 2, rRight);
				rLeft = rRight;
			}

			Matrix Q = MatrixImpl.FACTORY.newInstance(Qt);
			Q.transpose();

			int nSigma = n;
			Matrix Sigma = MatrixImpl.FACTORY.E(nSigma + 1);
			double sigma = Math.sqrt(1.0 / 12.0);

			Vector y = VectorImpl.FACTORY.newInstance(n + 1);
			for (int k = 0; k < y.getSize(); k++)
				y.set(k, points[k].getY());

			double mu = (2.0 / 3.0) * (1.0 - lambda) / lambda;

			double coeff = mu * sigma;

			Matrix A = Qt.multiply(Sigma).multiply(Q).multiply(coeff).add(R);

			Vector B = Qt.multiply(y);

			Vector b = LAES.Gauss(A, B);

			final Vector bExt = VectorImpl.FACTORY.newInstance(b.getSize() + 2);
			bExt.set(0, 0);
			bExt.set(bExt.getSize() - 1, 0);
			for (int k = 0; k < b.getSize(); k++)
				bExt.set(k + 1, b.get(k));

			final Vector d = y.add(Sigma.multiply(Q).multiply(b).multiply(coeff).negate());

			Coefficient a = new Coefficient() {
				@Override
				public Double valueIn(Integer k) {
					return (bExt.get(k + 1) - bExt.get(k)) / (3.0 * h[k]);
				}
			};
			Coefficient c = new Coefficient() {
				@Override
				public Double valueIn(Integer k) {
					return (d.get(k + 1) - d.get(k)) / h[k] - (1.0 / 3.0) * (bExt.get(k + 1) + 2.0 * bExt.get(k)) * h[k];
				}
			};

			double[] coefficients = new double[] {
					d.get(0),
					(d.get(1) - d.get(0)) / h[0] - (1.0 / 3.0) * (bExt.get(1) + 2.0 * bExt.get(0)) * h[0],
					bExt.get(0),
					(bExt.get(1) - bExt.get(0)) / (3.0 * h[0])};
			Spline spline = new Spline(coefficients, points[0].getX(), points[1].getX());

			for (int k = 1; k <= n - 1; k++) {
				coefficients[0] = d.get(k);
				coefficients[1] = (d.get(k + 1) - d.get(k)) / h[k] - (1.0 / 3.0) * (bExt.get(k + 1) + 2.0 * bExt.get(k)) * h[k];
				coefficients[2] = bExt.get(k);
				coefficients[3] = (bExt.get(k + 1) - bExt.get(k)) / (3.0 * h[k]);
				spline.addRight(coefficients, points[k + 1].getX());
			}

			return spline;
		}
	}

	public static class ParametricCurves {
		public static SplineParametricCurve bySmoothingSpline(final LinkedHashMap<Double, Point2D> points, final double lambda) {

			Point2D[] xtPoints = new Point2D[points.size()],
					ytPoints = new Point2D[points.size()];
			int k = 0;
			for (Map.Entry<Double, Point2D> entry : points.entrySet()) {
				xtPoints[k] = new Point2D(entry.getKey(), entry.getValue().getX());
				ytPoints[k] = new Point2D(entry.getKey(), entry.getValue().getY());
				k++;
			}

			Spline xtSpline = Interpolation.Splines.Smoothing(xtPoints, lambda);
			Spline ytSpline = Interpolation.Splines.Smoothing(ytPoints, lambda);

			return new SplineParametricCurve(xtSpline, ytSpline, xtSpline.getLeftBound(), xtSpline.getRightBound());
		}
	}

}
