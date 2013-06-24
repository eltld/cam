package edu.kpi.asu.rduboveckij.cam.utils;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import android.util.Log;
import edu.kpi.asu.rduboveckij.cam.osmonitor.OsMonitor;

public class CommonUtils {
	public static final double NanoToSecond = 1000000000.0;
	public static final long MEGABYTE = 1048576L;
	// 1970 01 08
	public static final Date DATE_MINVALUE = new Date(609075864L);
	public static final File DB_DIR = android.os.Environment
			.getExternalStorageDirectory();

	/**
	 * Pow with default param
	 * 
	 * @param x
	 * @return Math.pow(x, 2)
	 */
	public static double sqr(final double x) {
		return Math.pow(x, 2);
	}

	/**
	 * Need for create double array with state device
	 * 
	 * @param OsMonitor
	 * @return array
	 * @throws Exception
	 */
	public static double[] getState(OsMonitor monitor) throws Exception {
		return CommonUtils.newArray(monitor.getCPULoad(),
				monitor.getMemoryLoad(), monitor.getEnergyLevel());
	}

	/**
	 * Need for create array not use new double[]{1,2,3,...}
	 * 
	 * @param array
	 * @return array
	 */
	public static double[] newArray(double... array) {
		return array;
	}

	/**
	 * @return random 1 or -1
	 */
	public static int randomBool() {
		return Math.random() >= 0.5 ? 1 : -1;
	}

	/**
	 * Print Collection to Log file
	 * 
	 * @param collection
	 */
	public static <T> void printFor(final Collection<T> col) {
		for (T t : col)
			Log.i("CAM->print", t.toString());
	}

	/**
	 * 
	 * 
	 * @param start_time
	 * @return get elapsed time
	 */
	public static double getElapsed(final long start_time) {
		return (System.nanoTime() - start_time) / NanoToSecond;
	}

}
