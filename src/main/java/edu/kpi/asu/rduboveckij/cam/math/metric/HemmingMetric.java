package edu.kpi.asu.rduboveckij.cam.math.metric;

public class HemmingMetric implements Metric {
	/**
	 * Hemming metric calc. distance
	 * 
	 * @param a, b is Vector point
	 * 
	 * @return n / N, n = count(a[i] == b[i]) 
	 */
	public double apply(double[] a, double[] b) {
		int n = 0;
		for (int i = 0; i < a.length && i < b.length; i++)
			if(a[i] == b[i]) n++;
		return n / a.length;
	}

}
