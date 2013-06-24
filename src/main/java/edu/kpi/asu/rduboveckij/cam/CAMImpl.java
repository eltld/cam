package edu.kpi.asu.rduboveckij.cam;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import android.app.Activity;
import android.util.Log;
import edu.kpi.asu.rduboveckij.cam.domain.LogTime;
import edu.kpi.asu.rduboveckij.cam.domain.Precedent;
import edu.kpi.asu.rduboveckij.cam.math.Tukey;
import edu.kpi.asu.rduboveckij.cam.math.neighbordhoods.KSuspendedNeighborhoods;
import edu.kpi.asu.rduboveckij.cam.math.neighbordhoods.Neighbordhoods;
import edu.kpi.asu.rduboveckij.cam.osmonitor.OsMonitorImpl;
import edu.kpi.asu.rduboveckij.cam.repository.LogTimeDAO;
import edu.kpi.asu.rduboveckij.cam.repository.PrecedentDAO;
import edu.kpi.asu.rduboveckij.cam.utils.CommonUtils;
import edu.kpi.asu.rduboveckij.cam.utils.DatabaseConfig;

public class CAMImpl implements CAM {
	private static final long serialVersionUID = 2332321026448536396L;
	private static final int MaxLogTime = 10;

	private double maxWaitTime = 1.5;

	public Neighbordhoods kneighbordhoods = new KSuspendedNeighborhoods();

	private double[] logTimes;
	private Collection<Precedent> precedents;

	private long start_time;

	public CAMImpl() {
		asyncLoad(CAM.pathDb);
	}

	public CAMImpl(File db, Neighbordhoods kneighbordhoods) {
		this.kneighbordhoods = kneighbordhoods;
		asyncLoad(db);
	}

	public CAMImpl(double maxWaitTime, Neighbordhoods kneighbordhoods) {
		super();
		this.maxWaitTime = maxWaitTime;
		this.kneighbordhoods = kneighbordhoods;
		asyncLoad(CAM.pathDb);
	}

	public void asyncLoad(File db) {
		DatabaseConfig.init(db);
		logTimes = new LogTimeDAO().getLogTimes();
		precedents = new PrecedentDAO().findAll().values();
	}

	public boolean onClient(final Activity activity) {
		final long calcstart = System.nanoTime();
		try {
			return onClientState(CommonUtils.getState(new OsMonitorImpl(
					activity)));
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		} finally {
			this.start_time = System.nanoTime();
			Log.i("CAM->CalcEnd", "" + CommonUtils.getElapsed(calcstart));
		}
	}

	public boolean onClientState(final double[] state) {
		boolean onclient = true;
		if (Tukey.apply(logTimes) < maxWaitTime) {
			onclient = Math.round(kneighbordhoods.apply(precedents, state)) == 1;
		}
		return onclient;
	}

	public void saveCurrentLogTime() {
		this.saveLogTime(CommonUtils.getElapsed(this.start_time));
	}

	public void saveLogTime(final double time) {
		Log.i("CAM->saveLogTime", "" + time);
		if (time < MaxLogTime) {
			new LogTimeDAO().save(new LogTime(time));
			final int len = logTimes.length;
			logTimes = Arrays.copyOf(logTimes, len + 1);
			logTimes[len] = time;
		}
	}

	public void printLogTime() {
		DatabaseConfig.init(CAM.pathDb);
		CommonUtils.printFor(new LogTimeDAO().findAll().values());
	}

	public void printPrecedent() {
		DatabaseConfig.init(CAM.pathDb);
		CommonUtils.printFor(new PrecedentDAO().findAll().values());
	}

	public double getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(double maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public double[] getLogTimes() {
		return logTimes;
	}

	public void setLogTimes(double[] logTimes) {
		this.logTimes = logTimes;
	}

	public Collection<Precedent> getPrecedents() {
		return precedents;
	}

	public void setPrecedents(Collection<Precedent> precedents) {
		this.precedents = precedents;
	}

	public long getStart_time() {
		return start_time;
	}

	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}

	@Override
	public String toString() {
		return "CAMImpl [maxWaitTime=" + maxWaitTime + ", kneighbordhoods="
				+ kneighbordhoods + ", logTimes=" + Arrays.toString(logTimes)
				+ ", precedents=" + precedents + ", start_time=" + start_time
				+ "]";
	}
}
