package edu.kpi.asu.rduboveckij.cam.osmonitor;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import edu.kpi.asu.rduboveckij.cam.utils.CommonUtils;
import edu.kpi.asu.rduboveckij.cam.utils.SystemUtils;

public class OsMonitorImpl implements OsMonitor {
	private int level;

	public OsMonitorImpl() {
	}

	public OsMonitorImpl(Activity activity) {
		this.level = getEnergyLevel(activity);
	}

	public int getCPULoad() throws Exception {
		final double current = SystemUtils.getCPUFrequencyCurrent();
		final double max = SystemUtils.getCPUFrequencyMax();
		return (int) (current / max * 100);
	}

	public long getTotalMemory() throws Exception {
		return SystemUtils.getTotalMemory() / CommonUtils.MEGABYTE;
	}

	public long getFreeMemory() throws Exception {
		return SystemUtils.getFreeMemory() / CommonUtils.MEGABYTE;
	}

	public int getMemoryLoad() throws Exception {
		return (int) ((double) (getTotalMemory() - getFreeMemory())
				/ getTotalMemory() * 100);
	}

	public int getEnergyLevel() {
		return this.level;
	}

	public int getEnergyLevel(Activity activity) {
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent intent = activity.getApplicationContext().registerReceiver(null,
				filter);
		double level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		double scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		return (int) (level / scale * 100);
	}
}
