package edu.kpi.asu.rduboveckij.cam.osmonitor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

class SystemUtils {
	private static final String CPUFREQ_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/";

	public static int getCPUFrequencyCurrent() throws Exception {
		return readSystemFileAsInt(CPUFREQ_PATH + "scaling_cur_freq");
	}

	public static int getCPUFrequencyMin() throws Exception {
		return readSystemFileAsInt(CPUFREQ_PATH + "cpuinfo_min_freq");
	}

	public static int getCPUFrequencyMax() throws Exception {
		return readSystemFileAsInt(CPUFREQ_PATH + "cpuinfo_max_freq");
	}

	private static int readSystemFileAsInt(final String pSystemFile)
			throws Exception {
		InputStream in = null;
		try {
			final Process process = new ProcessBuilder(new String[] {
					"/system/bin/cat", pSystemFile }).start();

			in = process.getInputStream();
			final String content = readFully(in);
			return Integer.parseInt(content);
		} catch (final Exception e) {
			throw new Exception(e);
		}
	}

	public static final String readFully(final InputStream pInputStream)
			throws IOException {
		final StringBuilder sb = new StringBuilder();
		final Scanner sc = new Scanner(pInputStream);
		while (sc.hasNextLine()) {
			sb.append(sc.nextLine());
		}
		return sb.toString();
	}
}