package edu.kpi.asu.rduboveckij.cam.math.neighbordhoods;

import static edu.kpi.asu.rduboveckij.cam.utils.CommonUtils.sqr;

import java.util.Collection;

import edu.kpi.asu.rduboveckij.cam.domain.Precedent;
import edu.kpi.asu.rduboveckij.cam.math.metric.Metric;

public class KSuspendedNeighborhoods extends Neighbordhoods {
	public KSuspendedNeighborhoods() {
	}

	public KSuspendedNeighborhoods(Metric metric) {
		super(metric);
	}

	/**
	 * calc K suspemnded neighborhoods method
	 * 
	 * @param v Array Precedent
	 * 
	 * @param a point for find result
	 * 
	 * @return sum(1/metric(b, a) * result) / sum(1/metric(b, a))
	 */
	public double apply(Collection<Precedent> v, double[] a) {
		double arg1 = 0.0, arg2 = 0.0;
		for (final Precedent vi : v) {
			final double w = 1.0 / sqr(metric.apply(vi.getParams(), a));
			arg1 += w * vi.getResult();
			arg2 += w;
		}

		return arg1 / arg2;
	}
}
