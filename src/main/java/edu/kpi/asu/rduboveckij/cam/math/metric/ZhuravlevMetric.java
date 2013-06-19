package edu.kpi.asu.rduboveckij.cam.math.metric;

public class ZhuravlevMetric implements Metric {
	private double E = 0.1;

	public ZhuravlevMetric() {
	}

	public ZhuravlevMetric(double e) {
		this.E = e;
	}

	/**
	 * Zhuravlev metric calc. distance
	 * 
	 * @param a
	 *            , b is Vector point
	 * 
	 * @return sum((a-b) < e ? 1: 0)
	 */
	public double apply(double[] a, double[] b) {
		double result = 0.0;
		for (int i = 0; i < a.length && i < b.length; i++)
			if (Math.abs(a[i] - b[i]) < E)
				result++;
		return result;
	}
}
