package edu.kpi.asu.rduboveckij.cam.osmonitor;

import android.os.Build;

public interface OsMonitor {
	static final String DEVICE_NAME = Build.MODEL;
	static final int PLATFORM = Build.VERSION.SDK_INT;

	int getCPULoad() throws Exception;

	long getTotalMemory() throws Exception;

	long getFreeMemory() throws Exception;

	int getMemoryLoad() throws Exception;

	int getEnergyLevel();
}
