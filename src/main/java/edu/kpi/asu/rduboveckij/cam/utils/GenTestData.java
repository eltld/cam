package edu.kpi.asu.rduboveckij.cam.utils;

import static edu.kpi.asu.rduboveckij.cam.utils.CommonUtils.randomBool;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import android.util.Log;
import edu.kpi.asu.rduboveckij.cam.CAMImpl;
import edu.kpi.asu.rduboveckij.cam.domain.LogTime;
import edu.kpi.asu.rduboveckij.cam.domain.Precedent;
import edu.kpi.asu.rduboveckij.cam.math.metric.EuclidMetric;
import edu.kpi.asu.rduboveckij.cam.math.metric.HemmingMetric;
import edu.kpi.asu.rduboveckij.cam.math.metric.MahanolobisaMetric;
import edu.kpi.asu.rduboveckij.cam.math.metric.ManhattanMetric;
import edu.kpi.asu.rduboveckij.cam.math.metric.Metric;
import edu.kpi.asu.rduboveckij.cam.math.metric.ZhuravlevMetric;
import edu.kpi.asu.rduboveckij.cam.math.neighbordhoods.KSuspendedNeighborhoods;
import edu.kpi.asu.rduboveckij.cam.math.neighbordhoods.Neighbordhoods;
import edu.kpi.asu.rduboveckij.cam.math.neighbordhoods.NeighborhoodsImpl;
import edu.kpi.asu.rduboveckij.cam.repository.LogTimeDAO;
import edu.kpi.asu.rduboveckij.cam.repository.PrecedentDAO;

public class GenTestData {
	private static final String EXPERIMENT_LOG = "Method: %s, Metric: %s, DB: %s, ms: %s";

	private PrecedentDAO prDa;
	private LogTimeDAO ltDa;

	public GenTestData() {
		DatabaseConfig.init(CommonUtils.DB_DIR);
		prDa = new PrecedentDAO();
		ltDa = new LogTimeDAO();
	}

	public GenTestData(File path) {
		DatabaseConfig.init(path);
		prDa = new PrecedentDAO();
		ltDa = new LogTimeDAO();
	}

	/**
	 * Generation random [0 + start, end] time
	 * 
	 * @param count
	 *            of LogTime size
	 * @param start
	 * @param end
	 */
	public void genLogTime(final int count, final double start, final double end) {
		final double N = end - start;
		Log.i("CAM->GenTestData", "Start LogTime");
		for (final int i : Range.range(0, count))
			ltDa.save(new LogTime((Math.random() * N) + start));
		Log.i("CAM->GenTestData size=", count + "");
	}

	/**
	 * Generation random Precedent rule generation this rule for test data
	 * generation if all rule >=0? 1: 0
	 * 
	 * @param divc
	 *            CPU load step in interval [0,100] rule generation: [25, 75];
	 *            2, random, -2
	 * @param divm
	 *            Memory load step in interval [50,100] rule generation: [70,
	 *            90]; 1, random * 2, -2
	 * @param dive
	 *            Energy level step in interval [0,100] rule generation: [25,
	 *            75]; -3, random, 1
	 */
	public void genPrecedent(final int divc, final int divm, final int dive) {
		Log.i("CAM->GenTestData", "Start Precedent");
		int i = 0;
		for (final int ic : Range.range(0, 100, divc)) {
			final int resultC = checkRule(25, 75, ic, 2, randomBool(), -2);
			for (final int im : Range.range(50, 100, divm)) {
				final int resultM = checkRule(70, 90, im, 1, randomBool() * 2,
						-2);
				for (final int ie : Range.range(0, 100, dive)) {
					final int result = resultM + resultC
							+ checkRule(20, 70, ie, -3, randomBool(), 1);
					final Precedent pr = new Precedent(result >= 0 ? 1.0 : 0.0,
							ic / 100.0, im / 100.0, ie / 100.0);
					prDa.save(pr);
					Log.i("CAM->GenTestData", pr.toString());
					i++;
				}
			}
		}
		Log.i("CAM->GenTestData size=", i + "");
	}

	/**
	 * Rule generation
	 * 
	 * @param min
	 *            limit
	 * @param max
	 *            limit
	 * @param i
	 *            current index
	 * @param r1
	 *            if in min limit
	 * @param r2
	 *            if in min and max limit
	 * @param r3
	 *            if in max limit
	 * @return r1 or r2 or r3
	 */
	public static int checkRule(final int min, final int max, final int i,
			final int r1, final int r2, final int r3) {
		if (i <= min)
			return r1;
		else if (i > min && i > max)
			return r3;
		else if (i >= max)
			return r3;
		return 0;
	}

	/**
	 * Gen test data base
	 * 
	 * @param db
	 *            - name db
	 * @param countLt
	 *            - genLogTime() param
	 * @param startLt
	 *            - genLogTime() param
	 * @param rangeLt
	 *            - genLogTime() param
	 * @param divcPr
	 *            - genPrecedent() param
	 * @param divmPr
	 *            - genPrecedent() param
	 * @param divePr
	 *            - genPrecedent() param
	 */
	public static void instance(final String db, final int countLt,
			final double startLt, final double endLt, final int divcPr,
			final int divmPr, final int divePr) {
		final GenTestData testData = new GenTestData(new File(
				CommonUtils.DB_DIR, "CAM" + db));
		testData.genLogTime(countLt, startLt, endLt);
		testData.genPrecedent(divcPr, divmPr, divePr);
	}

	/**
	 * Need for experiments create 5 db(instance)
	 * 
	 * @param state
	 *            OsMonitor Impl
	 */
	public static void experiments(final double state[]) {
		final List<Metric> metrics = Arrays.asList(new EuclidMetric(),
				new ManhattanMetric(), new HemmingMetric(),
				new MahanolobisaMetric(), new ZhuravlevMetric());
		int mt = 0;
		for (int i = 1, db = 1; i <= 50; i++, db++) {
			Neighbordhoods nm;
			if (i > 25) {
				nm = new KSuspendedNeighborhoods();
				mt = 0;
			} else
				nm = new NeighborhoodsImpl();
			Metric metric = metrics.get(mt);
			Log.i("Experiment:" + i, String.format(EXPERIMENT_LOG, nm, metric
					.getClass().getSimpleName(), db,
					experiment(state, metric, nm, db)));
			if (db == 5) {
				db = 0;
				mt++;
			}
		}
	}

	/**
	 * Experimant fast this work
	 * 
	 * @param state
	 *            OsMonitor Impl
	 * @param metric
	 *            for main Method
	 * @param nm
	 *            main Method
	 * @param db
	 *            name
	 * @return ms time
	 */
	public static double experiment(double[] state, Metric metric,
			Neighbordhoods nm, int db) {
		nm.setMetric(metric);
		CAMImpl cam = new CAMImpl(new File(CommonUtils.DB_DIR, "CAM" + db), nm);
		long startTime = System.nanoTime();
		cam.onClientState(state);
		return CommonUtils.getElapsed(startTime);
	}
}
