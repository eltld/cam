package edu.kpi.asu.rduboveckij.cam.math;

import static edu.kpi.asu.rduboveckij.cam.utils.CommonUtils.sqr;

import java.util.Arrays;

public final class Tukey {
	/**
	 * calc Tukey method for find weighted avg
	 * 
	 * @param x Array time
	 * @return weighted avg
	 */
	public static double apply(double[] x) {
		Arrays.sort(x);
		final int len = x.length;
		final int len2 = len / 2;
		final double mediana = len % 2 == 0 ? (x[len2 - 1] + x[len2]) / 2
				: x[len2];

		double avg1 = 0, avg2 = len;
		for (final double i : x)
			if (sqr((1.0 - sqr(Math.abs(i - mediana)))) <= 1)
				avg1 += i;
			else
				avg2--;
		return avg1 / avg2;
	}
}
