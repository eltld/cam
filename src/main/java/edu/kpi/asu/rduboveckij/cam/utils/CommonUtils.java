package edu.kpi.asu.rduboveckij.cam.utils;

import java.util.Collection;

import android.util.Log;

public class CommonUtils {
	public static final double NanoToSecond = 1000000000.0;
	/**
	 * Pow with default param 
	 * @param x
	 * @return Math.pow(x, 2)
	 */
	public static double sqr(final double x) {
		return Math.pow(x, 2);
	}
	
	/**
	 * Need for create array not use new double[]{1,2,3,...}
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
	public static <T> void printFor(Collection<T> col) {
		for (T t : col)
			Log.i("CAM->print", t.toString());
	}
}
