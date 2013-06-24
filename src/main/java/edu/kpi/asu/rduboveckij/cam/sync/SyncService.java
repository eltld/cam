package edu.kpi.asu.rduboveckij.cam.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SyncService extends Service {
	public static final String EXTRA_HOST = "CAM_HOST";
	private static SyncAdapter syncAdapter;

	public void onCreate() {
		super.onCreate();
		if (syncAdapter == null)
			syncAdapter = new SyncAdapter();
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		final String host = intent.getStringExtra(EXTRA_HOST);
		if (host != null)
			syncAdapter.sync(host);
		return super.onStartCommand(intent, flags, startId);
	}

	public IBinder onBind(Intent intent) {
		return null;
	}
}
