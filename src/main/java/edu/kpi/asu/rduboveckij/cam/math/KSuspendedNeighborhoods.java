package edu.kpi.asu.rduboveckij.cam.math;

import static edu.kpi.asu.rduboveckij.cam.utils.CommonUtils.sqr;

import java.util.Collection;

import edu.kpi.asu.rduboveckij.cam.db.Precedent;

public class KSuspendedNeighborhoods {
	private Metric metric = new EuclidMetric();

	public KSuspendedNeighborhoods() {
	}

	public KSuspendedNeighborhoods(Metric metric) {
		this.metric = metric;
	}

	public double apply(Collection<Precedent> v, double[] vInput) {
		double arg1 = 0.0, arg2 = 0.0;
		for (final Precedent vi : v) {
			final double w = 1.0 / sqr(metric.apply(vi.getParams(), vInput));
			arg1 += w * vi.getResult();
			arg2 += w;
		}

		return arg1 / arg2;
	}
}
