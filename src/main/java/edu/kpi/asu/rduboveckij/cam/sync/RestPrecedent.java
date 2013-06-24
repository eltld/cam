package edu.kpi.asu.rduboveckij.cam.sync;

import edu.kpi.asu.rduboveckij.cam.domain.Context;
import edu.kpi.asu.rduboveckij.cam.domain.Precedent;
import edu.kpi.asu.rduboveckij.cam.osmonitor.OsMonitor;
import edu.kpi.asu.rduboveckij.cam.utils.Rest;

public class RestPrecedent extends Rest {
	private static final String GET_PRECEDENT_FORMAT = "precedents/%s/%s/%s";

	public RestPrecedent(String host) {
		super(host);
	}

	public Precedent[] getPrecedent(Context context) {
		return get(String.format(GET_PRECEDENT_FORMAT, OsMonitor.DEVICE_NAME,
				context.getPlatform(), context.getDate().getTime()),
				Precedent[].class);
	}

}
