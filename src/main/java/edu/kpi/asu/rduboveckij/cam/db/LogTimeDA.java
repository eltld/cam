package edu.kpi.asu.rduboveckij.cam.db;

import java.util.List;

import edu.kpi.asu.rduboveckij.cam.utils.Da;

public class LogTimeDA extends Da<LogTime> {

	public LogTimeDA() {
		super(LogTime.class);
	}

	@Override
	public void save(LogTime item) {
		save(item, LogTime.Sequence_Name);
	}

	public double[] getLogTimes() {
		final List<LogTime> items = (List<LogTime>) this.findAll().values();
		double[] result = new double[items.size() - 1];
		for (int i = 0; i < result.length; i++)
			result[i] = items.get(i).getTime();
		return result;
	}

}
