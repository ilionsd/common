package com.insign.common.linearalgebra;

import com.insign.common.linearalgebra.LinearObjects.Matrix;
import com.insign.common.linearalgebra.LinearObjects.Vector;
import com.insign.common.linearalgebra.solve.LAES;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

/**
 * Created by ilion on 03.02.2015.
 */
public class Tests {
	public static final Random RAND = new Random();

	public static double[][] randomMatrix(int n, int m) {
		double[][] arr = new double[n][];
		for (int i = 0; i < n; i++) {
			arr[i] = new double[m];
			for (int j = 0; j < m; j++) {
				arr[i][j] = RAND.nextDouble();
			}
		}
		return arr;
	}

	public static double[] randomVector(int size) {
		double[] arr = new double[size];
		for (int k = 0; k < size; k++)
			arr[k] = RAND.nextDouble();
		return arr;
	}

//	@Rule
//	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void mmMultiplication() {
		int sizeBound = 50;
		int testCount = 40;
		System.out.print("Matrix-Matrix multiplication test started" + System.lineSeparator());
		for (int testNumber = 0; testNumber < testCount; testNumber++) {
			System.out.print("Case " + (testNumber + 1) + " started" + System.lineSeparator());
			int n1 = RAND.nextInt(sizeBound) + 1;
			int m1 = RAND.nextInt(sizeBound) + 1;
			int n2 = m1;
			int m2 = RAND.nextInt(sizeBound) + 1;
			System.out.print("multiplying " + n1 + "x" + m1 + " and " + n2 + "x" + m2 + " ..." + System.lineSeparator());

			double[][] arr1 = randomMatrix(n1, m1);
			double[][] arr2 = randomMatrix(n2, m2);
			Matrix a = MatrixImpl.FACTORY.newInstance(arr1);
			Matrix b = MatrixImpl.FACTORY.newInstance(arr2);

			Matrix c = a.multiply(b);

			for (int i = 0; i < arr1.length; i++) {
				for (int j = 0; j < arr2[0].length; j++) {
					double summ = 0.0;
					for (int k = 0; k < arr1[0].length; k++)
						summ += arr1[i][k] * arr2[k][j];
					Assert.assertEquals(c.get(i, j), summ, Double.MIN_VALUE);
				}
			}
		}
		System.out.print("Test ended" + System.lineSeparator());
	}

	@Test
	public void mmMultiplicationCustom() {
		System.out.print("2x1 and 1x9 matrix multiplication test started" + System.lineSeparator());
		int n1 = 2;
		int m1 = 1;
		int n2 = 1;
		int m2 = 9;

		System.out.print("multiplying " + n1 + "x" + m1 + " and " + n2 + "x" + m2 + " ..." + System.lineSeparator());

		Matrix a = MatrixImpl.FACTORY.newInstance(randomMatrix(n1, m1));
		Matrix b = MatrixImpl.FACTORY.newInstance(randomMatrix(n2, m2));

		Matrix c = a.multiply(b);

		for (int i = 0; i < a.getRowsCount(); i++) {
			for (int j = 0; j < b.getColumnsCount(); j++) {
				double summ = 0.0;
				for (int k = 0; k < a.getColumnsCount(); k++)
					summ += a.get(i, k) * b.get(k, j);
				Assert.assertEquals(c.get(i, j), summ, Double.MIN_VALUE);
			}
		}
		System.out.print("Test ended" + System.lineSeparator());
	}

	@Test
	public void mvMultiplication() {
		int sizeBound = 50;
		int testCount = 40;
		System.out.print("Matrix-Vector multiplication test started" + System.lineSeparator());
		for (int testNumber = 0; testNumber < testCount; testNumber++) {
			System.out.print("Case " + (testNumber + 1) + " started" + System.lineSeparator());
			int n = RAND.nextInt(sizeBound) + 1,
					m = RAND.nextInt(sizeBound) + 1;
			int size = m;
			System.out.print("multiplying " + n + "x" + m + " matrix and " + size + " vector ..." + System.lineSeparator());

			double[][] arr1 = randomMatrix(n, m);
			double[] arr2 = randomVector(size);
			Matrix a = MatrixImpl.FACTORY.newInstance(arr1);
			Vector v = VectorImpl.FACTORY.newInstance(arr2);

			Vector c = a.multiply(v);

			for (int i = 0; i < arr1.length; i++) {
				double summ = 0;
				for (int j = 0; j < arr2.length; j++)
					summ += arr1[i][j] * arr2[j];
				Assert.assertEquals(c.get(i), summ, Double.MIN_VALUE);
			}
		}
		System.out.print("Test ended" + System.lineSeparator());
	}

	@Test
	public void vvMultiplication() {
		int sizeBound = 50;
		int n = RAND.nextInt(sizeBound);

		double[] arr1 = randomVector(n);
		double[] arr2 = randomVector(n);

		Vector v1 = VectorImpl.FACTORY.newInstance(arr1);
		Vector v2 = VectorImpl.FACTORY.newInstance(arr2);

		v1.transpose();

		Matrix m1 = v1.multiply(v2);

		double summ = 0;
		for (int k = 0; k < arr1.length; k++) {
			summ += arr1[k] * arr2[k];
		}
		Assert.assertEquals(summ, m1.getRow(0).get(0), Double.MIN_VALUE);

		Matrix m2 = v2.multiply(v1);

		for (int i = 0; i < arr2.length; i++) {
			for (int j = 0; j < arr1.length; j++) {
				summ = arr2[i] * arr1[j];
				Assert.assertEquals(summ, m2.get(i, j), Double.MIN_VALUE);
			}
		}
	}

	@Test
	public void mmAddition() {
		int sizeBound = 50;
		int n = RAND.nextInt(sizeBound) + 1;
		int m = RAND.nextInt(sizeBound) + 1;

		double[][] arr1 = randomMatrix(n, m);
		double[][] arr2 = randomMatrix(n, m);

		Matrix m1 = MatrixImpl.FACTORY.newInstance(arr1);
		Matrix m2 = MatrixImpl.FACTORY.newInstance(arr2);

		Matrix mrez = m1.add(m2);

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				Assert.assertEquals(arr1[i][j] + arr2[i][j], mrez.get(i, j), Double.MIN_VALUE);
	}

	@Test
	public void vvAddition() {
		int sizeBound = 50;
		int n = RAND.nextInt(sizeBound);

		double[] arr1 = randomVector(n);
		double[] arr2 = randomVector(n);

		Vector v1 = VectorImpl.FACTORY.newInstance(arr1);
		Vector v2 = VectorImpl.FACTORY.newInstance(arr2);

		Vector vrez = v1.add(v2);

		for (int k = 0; k < n; k++)
			Assert.assertEquals(arr1[k] + arr2[k], vrez.get(k), Double.MIN_VALUE);
	}

	@Test
	public void gaussMethodCustom() {
		int sizeBound = 10;
		int n = 3;//RAND.nextInt(sizeBound) + 1;
		double[][] arrA = {{4, 2, 3}, {4, 1, 6}, {7, 5, 1}};
		double[] arrX = {-48, 65, 23};
		double[] arrB = {7, 11, 12};
		Matrix a = MatrixImpl.FACTORY.newInstance(arrA);
		Vector x = VectorImpl.FACTORY.newInstance(arrX);
		Vector b = VectorImpl.FACTORY.newInstance(arrB);
		Vector xActual = LAES.Gauss(a, b);

		double norm2 = Vector.Math.norm(x.negate().add(xActual));

		Assert.assertTrue(x.equals(xActual));
	}

	@Test
	public void gaussMethod() {
		int sizeBound = 50;
		int n = RAND.nextInt(sizeBound) + 1;

		Matrix a = MatrixImpl.FACTORY.newInstance(randomMatrix(n, n));
		Vector x = VectorImpl.FACTORY.newInstance(randomVector(n));
		Vector b = a.multiply(x);
		Vector xActual = LAES.Gauss(a, b);

		double norm2 = Vector.Math.norm(x.negate().add(xActual));

		Assert.assertTrue(x.equals(xActual));
	}

	@Test
	public void gaussMethodCustom2() {
		double[][] arrA = {
				{424.0010681178792, 131.9995585955237, 7.857243728764639E-5, 0.0, 0.0, 0.0, 0.0, 0.0},
				{131.9995585955237, 598.0004801672708, 166.9997276094459, 6.960779679174043E-5, 0.0, 0.0, 0.0, 0.0},
				{7.857243728764639E-5, 166.9997276094459, 632.0004194594984, 148.99968629420889, 8.806440957742919E-5, 0.0, 0.0, 0.0},
				{0.0, 6.960779679174043E-5, 148.99968629420889, 562.0005309744801, 131.99961446489, 9.865862426343569E-5, 0.0, 0.0},
				{0.0, 0.0, 8.806440957742919E-5, 131.99961446489, 530.0005919629849, 132.9996181054444, 8.740227115955377E-5, 0.0},
				{0.0, 0.0, 0.0, 9.865862426343569E-5, 132.9996181054444, 564.0005266717873, 148.99966916187293, 8.740227115955377E-5},
				{0.0, 0.0, 0.0, 0.0, 8.740227115955377E-5, 148.99966916187293, 564.0005266717873, 132.99961735232512},
				{0.0, 0.0, 0.0, 0.0, 0.0, 8.740227115955377E-5, 132.99961735232512, 528.0005965161074}};

		double[] arrB = {0.33112543279474416, 0.6411985959056681, 0.16923226947230052, -0.18086571312299782, 0.047689817249978006, -0.2261925500162434, 0.15225993099309854, 0.5280155956297001};
		Matrix A = MatrixImpl.FACTORY.newInstance(arrA);
		Vector B = VectorImpl.FACTORY.newInstance(arrB);
		Vector x = LAES.Gauss(A, B);
		Vector B2 = A.multiply(x);
		int k = 0;
	}

	@Test
	@Ignore
	public void gaussSeidelMethod() {
		int sizeBound = 50;
		int n = RAND.nextInt(sizeBound) + 1;

		Matrix a = MatrixImpl.FACTORY.newInstance(randomMatrix(n, n));
		Vector x = VectorImpl.FACTORY.newInstance(randomVector(n));
		Vector b = a.multiply(x);
		Vector xActual = LAES.GaussSeidel(a, b, 1e-3);

		double norm2 = Vector.Math.norm(x.negate().add(xActual));

		Assert.assertTrue(x.equals(xActual, 1e-3));
	}

}
