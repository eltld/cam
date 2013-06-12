package edu.kpi.asu.rduboveckij.cam.utils;

import java.util.Collection;

public class CommonUtils {
	public static double sqr(final double x) {
		return Math.pow(x, 2);
	}

	public static double[] newArray(double... array) {
		return array;
	}
	
	public static int randomBool() {
		return Math.random() >= 0.5 ? 1 : -1;
	}
	
	public static <T> void printFor(Collection<T> col){
		for(T t: col)
			System.out.println(t);
	}
}
