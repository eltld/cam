package edu.kpi.asu.rduboveckij.cam.db;

import java.util.Collection;
import java.util.Iterator;

import edu.kpi.asu.rduboveckij.cam.utils.Da;

public class LogTimeDA extends Da<LogTime> {

	public LogTimeDA() {
		super(LogTime.class);
	}

	@Override
	public void save(LogTime item) {
		save(item, LogTime.Sequence_Name);
	}

	/**
	 * findAll LogTime
	 * 
	 * @return array times last run system
	 */
	public double[] getLogTimes() {
		final Collection<LogTime> items = this.findAll().values();
		double[] result = new double[items.size()];
		int i = 0;
		for (Iterator<LogTime> iterator = items.iterator(); iterator.hasNext(); i++) {
			result[i] = iterator.next().getTime();
		}
		return result;
	}

}
