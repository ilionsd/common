package com.insign.common.function;

import com.insign.common.linearalgebra.LinearObjects.Matrix;
import com.insign.common.linearalgebra.LinearObjects.Vector;
import com.insign.common.linearalgebra.MatrixImpl;
import com.insign.common.linearalgebra.VectorImpl;
import com.insign.common.linearalgebra.solve.LAES;


/**
 * Created by ilion on 04.02.2015.
 */
public class Interpolation {
	private Interpolation() {
	}

	public static CubeSpline SmoothingSpline(Point2D[] points, double lambda) {
		//-- x indexes changes from 0 to n  --
		int n = points.length - 1;
		//-- h indexes changes from 0 to n-1 --
		int nh = n - 1;
		double[] h = new double[nh + 1];
		for (int k = 0; k <= nh; k++)
			h[k] = points[k + 1].getX() - points[k].getX();
		//-- R indexes changes from 0 to n-2 and from 0 to n-2 --
		int nR = n - 2;
		Matrix R = MatrixImpl.FACTORY.newInstance(nR + 1, nR + 1);
		R.set(0, 0, 2 * (h[0] + h[1]));
		R.set(0, 1, h[1]);
		for (int i = 1; i <= nR - 1; i++) {
			R.set(i, i - 1, h[i]);
			R.set(i, i, 2 * (h[i] + h[i + 1]));
			R.set(i, i + 1, h[i + 1]);
		}
		R.set(nR, nR - 1, h[nR]);
		R.set(nR, nR, 2 * (h[nR] + h[nR + 1]));

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

		Vector y = VectorImpl.FACTORY.newInstance(n + 1);
		for (int k = 0; k < y.getSize(); k++)
			y.set(k, points[k].getY());

		double mu = (2.0 / 3.0) * (1 - lambda) / lambda;

		Matrix A = Qt.multiply(Sigma).multiply(Q).multiply(mu).add(R);

		Vector B = Qt.multiply(y);

		Vector b = LAES.Gauss(A, B);

		Vector bExt = VectorImpl.FACTORY.newInstance(b.getSize() + 2);
		bExt.set(0, 0);
		bExt.set(b.getSize() - 1, 0);
		for (int k = 0; k < b.getSize(); k++)
			bExt.set(k + 1, b.get(k));

		Vector d = y.add(Sigma.multiply(Q).multiply(b).negate());

		return null;
	}
}
