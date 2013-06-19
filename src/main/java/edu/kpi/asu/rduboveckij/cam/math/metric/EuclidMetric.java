package edu.kpi.asu.rduboveckij.cam.math.metric;

import static edu.kpi.asu.rduboveckij.cam.utils.CommonUtils.sqr;

public class EuclidMetric implements Metric {
	/**
	 * Euclid metric calc. distance
	 * 
	 * @param a, b is Vector point
	 * 
	 * @return sum((a - b)^2)^1/2
	 */
	public double apply(double[] a, double[] b) {
		double result = 0.0;
		for (int i = 0; i < a.length && i < b.length; i++)
			result += sqr(a[i] - b[i]);
		return Math.sqrt(result);
	}
}
