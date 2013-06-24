package edu.kpi.asu.rduboveckij.cam.utils;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.MatchResult;

public class SystemUtils {
	private static final String CPUFREQ_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/";
	private static final String SYSCUT_PATH = "/system/bin/cat";
	private static final String MEMINFO_PATH = "/proc/meminfo";

	public static int getCPUFrequencyCurrent() throws Exception {
		return readSystemFileAsInt(CPUFREQ_PATH + "scaling_cur_freq");
	}

	public static int getCPUFrequencyMin() throws Exception {
		return readSystemFileAsInt(CPUFREQ_PATH + "cpuinfo_min_freq");
	}

	public static int getCPUFrequencyMax() throws Exception {
		return readSystemFileAsInt(CPUFREQ_PATH + "cpuinfo_max_freq");
	}

	private static int readSystemFileAsInt(final String path)
			throws IOException {
		return scannerToInteger(scannerFile(path));
	}

	private static Scanner scannerFile(final String path) throws IOException {
		final Process process = new ProcessBuilder(new String[] { SYSCUT_PATH,
				path }).start();
		return new Scanner(process.getInputStream());
	}

	private static final int scannerToInteger(final Scanner sc)
			throws IOException {
		final StringBuilder sb = new StringBuilder();
		while (sc.hasNextLine()) {
			sb.append(sc.nextLine());
		}
		return Integer.parseInt(sb.toString());
	}

	public static long getTotalMemory() throws Exception {
		return findLongByMatchInMemInfo(getPatternValueKb("MemTotal"));
	}

	private static String getPatternValueKb(final String name) {
		return String.format("%s[\\s]*:[\\s]*(\\d+)[\\s]*kB\n", name);
	}

	private static long findLongByMatchInMemInfo(final String pPattern)
			throws Exception {
		final Scanner scanner = scannerFile(MEMINFO_PATH);
		final MatchResult matchResult = scanner.match();
		if (scanner.findWithinHorizon(pPattern, 1000) != null
				&& matchResult.groupCount() > 0)
			return Long.parseLong(matchResult.group(1));
		else
			throw new Exception();
	}

	public static long getFreeMemory() throws Exception {
		return findLongByMatchInMemInfo(getPatternValueKb("MemFree"));
	}

}