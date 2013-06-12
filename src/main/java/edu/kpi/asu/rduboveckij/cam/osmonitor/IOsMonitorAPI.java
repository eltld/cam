package edu.kpi.asu.rduboveckij.cam.osmonitor;

public interface IOsMonitorAPI {
	String getOsVerison();
	String getDeviceName();
	
	int getCPULoad();
	long getTotalMemory();
	long getFreeMemory();
	int getMemoryLoad();
	int getEnergyLevel();
}
