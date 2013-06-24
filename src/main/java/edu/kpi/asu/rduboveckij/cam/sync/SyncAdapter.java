package edu.kpi.asu.rduboveckij.cam.sync;

import android.util.Log;
import edu.kpi.asu.rduboveckij.cam.CAM;
import edu.kpi.asu.rduboveckij.cam.domain.Context;
import edu.kpi.asu.rduboveckij.cam.osmonitor.OsMonitor;
import edu.kpi.asu.rduboveckij.cam.repository.ContextDAO;
import edu.kpi.asu.rduboveckij.cam.repository.LogTimeDAO;
import edu.kpi.asu.rduboveckij.cam.repository.PrecedentDAO;
import edu.kpi.asu.rduboveckij.cam.utils.CommonUtils;
import edu.kpi.asu.rduboveckij.cam.utils.DatabaseConfig;

public class SyncAdapter {
	public SyncAdapter() {
		DatabaseConfig.init(CAM.pathDb);
	}

	public void sync(String host) {
		PrecedentDAO prDao = new PrecedentDAO();
		ContextDAO caDao = new ContextDAO();
		Context context = caDao.findFirst();
		if (context.getPlatform() != OsMonitor.PLATFORM) {
			context.setPlatform(OsMonitor.PLATFORM);
			context.setDate(CommonUtils.DATE_MINVALUE);
			caDao.removeAll();
			prDao.removeAll();
			new LogTimeDAO().removeAll();
			caDao.save(context);
		}
		try {
			prDao.saveAll(new RestPrecedent(host).getPrecedent(context));
		} catch (Exception e) {
			Log.i("SyncAdapter", e.getMessage());
		}
	}
}
