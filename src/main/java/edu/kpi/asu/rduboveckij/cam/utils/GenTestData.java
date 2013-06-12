package edu.kpi.asu.rduboveckij.cam.utils;

import static edu.kpi.asu.rduboveckij.cam.utils.CommonUtils.randomBool;

import java.io.File;

import edu.kpi.asu.rduboveckij.cam.db.LogTime;
import edu.kpi.asu.rduboveckij.cam.db.LogTimeDA;
import edu.kpi.asu.rduboveckij.cam.db.Precedent;
import edu.kpi.asu.rduboveckij.cam.db.PrecedentDA;

public class GenTestData {
	private PrecedentDA prDa;
	private LogTimeDA ltDa;

	public GenTestData(File path) {
		DatabaseConfig.init(path);
		prDa = new PrecedentDA();
		ltDa = new LogTimeDA();
	}

	public void genLogTime(int count) {
		for (int i : Range.range(0, count))
			ltDa.save(new LogTime(Math.random() + 0.5));
	}

	public void genPrecedent(int stepC, int stepM, int stepE) {
		for (int ic : Range.range(0, 100, stepC)) {
			int resultC = checkRule(25, 75, ic, 2, randomBool(), -2);
			for (int im : Range.range(50, 100, stepM)) {
				int resultM = checkRule(70, 90, im, 1, randomBool() * 2, -2);
				for (int ie : Range.range(0, 100, stepE)) {
					int result = resultM + resultC
							+ checkRule(25, 75, ie, -3, randomBool(), 1);
					prDa.save(new Precedent(result >= 0 ? 1.0 : 0.0,
							ic / 100.0, im / 100.0, ie / 100.0));
				}
			}
		}
	}

	public static int checkRule(int min, int max, int i, int r1, int r2, int r3) {
		if (i <= min)
			return r1;
		else if (i > min && i < max)
			return r3;
		else if (i >= max)
			return r3;
		return 0;
	}

}
