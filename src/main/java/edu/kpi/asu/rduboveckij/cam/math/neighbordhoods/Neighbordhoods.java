package edu.kpi.asu.rduboveckij.cam.math.neighbordhoods;

import java.util.Collection;

import edu.kpi.asu.rduboveckij.cam.domain.Precedent;
import edu.kpi.asu.rduboveckij.cam.math.metric.EuclidMetric;
import edu.kpi.asu.rduboveckij.cam.math.metric.Metric;

public abstract class Neighbordhoods {
	// default metric Euclid
	protected Metric metric = new EuclidMetric();

	public Neighbordhoods() {
	}

	public Neighbordhoods(Metric metric) {
		this.metric = metric;
	}

	/**
	 * calc K neighborhoods method
	 * 
	 * @param v Array Precedent
	 * 
	 * @param a point for find result
	 * 
	 * @return double
	 */
	public abstract double apply(Collection<Precedent> v, double[] a);

	public Metric getMetric() {
		return metric;
	}

	public void setMetric(Metric metric) {
		this.metric = metric;
	}
}
