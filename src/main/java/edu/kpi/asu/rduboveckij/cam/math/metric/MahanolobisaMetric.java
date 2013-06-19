package edu.kpi.asu.rduboveckij.cam.math.metric;

import static org.math.array.LinearAlgebra.times;
import static org.math.array.LinearAlgebra.inverse;
import static org.math.array.LinearAlgebra.transpose;

public class MahanolobisaMetric implements Metric {
	/**
	 * Mahanolobisa metric calc. distance
	 * 
	 * @param a, b is Vector point
	 * 
	 * @return (a-b)^T*W^-1*(a-b), diff = (a-b)
	 */
	public double apply(double[] a, double[] b) {
		int len = a.length;
		double[][] W = new double[len][len];
		double[][] diff = new double[1][len];
		for (int i = 0; i < len; i++) {
			diff[0][i] = a[i] - b[i];
			for (int j = 0; j < len; j++)
				W[i][j] = i == j ? 1.0 : 0.0;
		}
		// 1 / (1 + expres) 
		return Math.sqrt(times(times(diff, inverse(W)),
				transpose(diff))[0][0]);

	}
}
