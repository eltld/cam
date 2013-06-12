package edu.kpi.asu.rduboveckij.cam;

import java.io.File;

import android.app.Activity;
import edu.kpi.asu.rduboveckij.cam.db.LogTime;
import edu.kpi.asu.rduboveckij.cam.db.LogTimeDA;
import edu.kpi.asu.rduboveckij.cam.db.PrecedentDA;
import edu.kpi.asu.rduboveckij.cam.math.KSuspendedNeighborhoods;
import edu.kpi.asu.rduboveckij.cam.math.Tukey;
import edu.kpi.asu.rduboveckij.cam.osmonitor.IOsMonitorAPI;
import edu.kpi.asu.rduboveckij.cam.osmonitor.OsMonitorAPI;
import edu.kpi.asu.rduboveckij.cam.utils.CommonUtils;
import edu.kpi.asu.rduboveckij.cam.utils.DatabaseConfig;
import edu.kpi.asu.rduboveckij.cam.utils.GenTestData;

public class CAMImpl implements CAM {
	private File pathDb = new File(
			android.os.Environment.getExternalStorageDirectory(), "data");
	private double maxWaitTime = 1.5;

	public CAMImpl() {
		init();
	}

	public CAMImpl(String pathDb, double maxWaitTime) {
		super();
		this.pathDb = new File(pathDb);
		this.maxWaitTime = maxWaitTime;
		init();
	}

	private void init() {
		DatabaseConfig.init(pathDb);
		if (DatabaseConfig.isEmpty) {
			GenTestData testData = new GenTestData(pathDb);
			testData.genLogTime(50);
			testData.genPrecedent(6, 4, 5);
		}
	}

	public boolean isOnClient(Activity activity) {
		boolean onclient = true;
		LogTimeDA ltDa = new LogTimeDA();

		if (Tukey.apply(ltDa.getLogTimes()) > maxWaitTime) {
			KSuspendedNeighborhoods KSN = new KSuspendedNeighborhoods();
			IOsMonitorAPI monitor = new OsMonitorAPI(activity);
			double[] state = CommonUtils.newArray(monitor.getCPULoad(),
					monitor.getMemoryLoad(), monitor.getEnergyLevel());
			PrecedentDA prDa = new PrecedentDA();
			onclient = Math.round(KSN.apply(prDa.findAll().values(), state)) == 1;
		}

		return onclient;
	}

	public void saveLogTime(double time) {
		new LogTimeDA().save(new LogTime(time));
	}

	public void printLogTime() {
		DatabaseConfig.init(pathDb);
		CommonUtils.printFor(new LogTimeDA().findAll().values());
	}

	public void printPrecedent() {
		DatabaseConfig.init(pathDb);
		CommonUtils.printFor(new PrecedentDA().findAll().values());
	}

	public File getPathDb() {
		return pathDb;
	}

	public void setPathDb(File pathDb) {
		this.pathDb = pathDb;
	}

	public double getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(double maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	@Override
	public String toString() {
		return "CAMImpl [pathDb=" + pathDb + ", maxWaitTime=" + maxWaitTime
				+ "]";
	}
}
