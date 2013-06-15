package edu.kpi.asu.rduboveckij.cam;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import android.app.Activity;
import android.util.Log;
import edu.kpi.asu.rduboveckij.cam.db.LogTime;
import edu.kpi.asu.rduboveckij.cam.db.LogTimeDA;
import edu.kpi.asu.rduboveckij.cam.db.Precedent;
import edu.kpi.asu.rduboveckij.cam.db.PrecedentDA;
import edu.kpi.asu.rduboveckij.cam.math.KSuspendedNeighborhoods;
import edu.kpi.asu.rduboveckij.cam.math.Tukey;
import edu.kpi.asu.rduboveckij.cam.osmonitor.IOsMonitorAPI;
import edu.kpi.asu.rduboveckij.cam.osmonitor.OsMonitorAPI;
import edu.kpi.asu.rduboveckij.cam.utils.CommonUtils;
import edu.kpi.asu.rduboveckij.cam.utils.DatabaseConfig;

public class CAMImpl implements CAM, Serializable {
	private static final long serialVersionUID = 2332321026448536396L;

	public static File pathDb = new File(
			android.os.Environment.getExternalStorageDirectory(), "CAM");
	private double maxWaitTime = 1.5;

	private double[] logTimes;
	private Collection<Precedent> precedents;

	private long start_time;

	public CAMImpl() {
		asyncLoad();
	}

	public CAMImpl(double maxWaitTime) {
		super();
		this.maxWaitTime = maxWaitTime;
		asyncLoad();
	}

	public void asyncLoad() {
		final long calcstart = System.nanoTime();
		DatabaseConfig.init(pathDb);
		logTimes = new LogTimeDA().getLogTimes();
		precedents = new PrecedentDA().findAll().values();
		Log.i("CAM->initEnd", "" + (System.nanoTime() - calcstart)
				/ CommonUtils.NanoToSecond);
	}

	public boolean isOnClient(Activity activity) {
		boolean onclient = true;

		final long calcstart = System.nanoTime();
		double avg = Tukey.apply(logTimes);
		if (avg > maxWaitTime) {
			// android.os.Debug.startMethodTracing();
			IOsMonitorAPI monitor = new OsMonitorAPI(activity);
			double[] state = CommonUtils.newArray(monitor.getCPULoad(),
					monitor.getMemoryLoad(), monitor.getEnergyLevel());
			double ksn = new KSuspendedNeighborhoods().apply(precedents, state);
			onclient = Math.round(ksn) == 1;
			// android.os.Debug.stopMethodTracing();
			Log.i("CAM->State", "" + state[0] + "," + state[1] + "," + state[2]);
			Log.i("CAM->KSN", "" + ksn);
		}
		this.start_time = System.nanoTime();
		Log.i("CAM->CalcEnd", "" + (System.nanoTime() - calcstart)
				/ CommonUtils.NanoToSecond);
		Log.i("CAM->Tukey", "" + avg);
		Log.i("CAM", "logic need calc on" + (onclient ? "client " : "server"));
		return onclient;
	}

	public void saveCurrentLogTime() {
		this.saveLogTime((System.nanoTime() - this.start_time)
				/ CommonUtils.NanoToSecond);
	}

	public void saveLogTime(double time) {
		Log.i("CAM->saveLogTime", "" + time);
		if (time < 10) {
			new LogTimeDA().save(new LogTime(time));
			final int len = logTimes.length;
			logTimes = Arrays.copyOf(logTimes, len + 1);
			logTimes[len] = time;
		}
	}

	public void printLogTime() {
		DatabaseConfig.init(pathDb);
		CommonUtils.printFor(new LogTimeDA().findAll().values());
	}

	public void printPrecedent() {
		DatabaseConfig.init(pathDb);
		CommonUtils.printFor(new PrecedentDA().findAll().values());
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
		return "CAMImpl [maxWaitTime=" + maxWaitTime + ", logTimes="
				+ Arrays.toString(logTimes) + ", precedents=" + precedents
				+ ", start_time=" + start_time + "]";
	}
}
