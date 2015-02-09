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
		int nh = n - 1;
		double[] h = new double[nh + 1];
		for (int k = 0; k <= nh; k++)
			h[k] = points[k + 1].getX() - points[k].getX();
		int nR = n - 1;
		Matrix R = MatrixImpl.FACTORY.newInstance(nR + 1, nR + 1);
		R.set(0, 0, 2 * (h[0] + h[1]));
		R.set(0, 1, h[1]);
		for (int i = 1; i <= nR - 1; i++) {
			R.set(i, i - 1, h[i]);
			R.set(i, i, 2 * (h[i] + h[i + 1]));
			R.set(i, i + 1, h[i + 1]);
		}
		R.set(nR, nR - 1, h[nR - 1]);
		R.set(nR, nR, 2 * (h[nR - 1] + h[nR]));

		int QtsizeRow = points.length - 1;
		int QtsizeCol = points.length + 1;
		Matrix Qt = MatrixImpl.FACTORY.newInstance(QtsizeRow, QtsizeCol);
		for (int i = 0; i < QtsizeRow; i++) {
			Qt.set(i, i, 3 / h[i]);
			Qt.set(i, i + 1, -(3 / h[i] + 3 / h[i + 1]));
			Qt.set(i, i + 2, 3 / h[i + 1]);
		}

		Matrix Q = MatrixImpl.FACTORY.newInstance(Qt);
		Q.transpose();

		Matrix Sigma = MatrixImpl.FACTORY.E(points.length);

		Vector y = VectorImpl.FACTORY.newInstance(points.length);
		for (int k = 0; k < y.getSize(); k++)
			y.set(k, points[k].getY());

		double mu = 2.0 / 3.0 * (1 - lambda) / lambda;

		Matrix A = Qt.multiply(Sigma).multiply(Q).multiply(mu).add(R);

		Vector B = Qt.multiply(y);

		Vector b = LAES.Gauss(A, B);

		Vector d = y.add(Sigma.multiply(Q).multiply(b).negate());

		return null;
	}
}
