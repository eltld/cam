package edu.kpi.asu.rduboveckij.cam.sync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SyncReceiverDestroy extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		context.stopService(new Intent(context, SyncService.class));
	}
}
