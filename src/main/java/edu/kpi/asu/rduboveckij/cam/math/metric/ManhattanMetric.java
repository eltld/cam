package edu.kpi.asu.rduboveckij.cam.math.metric;

public class ManhattanMetric implements Metric {
	/**
	 * Manhattan metric calc. distance
	 * 
	 * @param a, b is Vector point
	 * 
	 * @return sum(abs(a - b))
	 */
	public double apply(double[] a, double[] b) {
		double result = 0.0;
		for (int i = 0; i < a.length && i < b.length; i++)
			result += Math.abs(a[i] - b[i]);
		return result;
	}

}
