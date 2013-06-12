package edu.kpi.asu.rduboveckij.cam.math;

import static edu.kpi.asu.rduboveckij.cam.utils.CommonUtils.sqr;

public class EuclidMetric implements Metric {
	public double apply(double[] vk, double[] vInput) {
		double result = 0.0;
		for (int i = 0; i < vk.length && i < vInput.length; i++)
			result += sqr(vk[i] - vInput[i]);
		return Math.sqrt(result);
	}
}
