package edu.kpi.asu.rduboveckij.cam;

import java.io.File;
import java.io.Serializable;

import android.app.Activity;
import edu.kpi.asu.rduboveckij.cam.utils.CommonUtils;

public interface CAM extends Serializable{
	public static final File pathDb = new File(CommonUtils.DB_DIR, "CAM");

	public boolean onClient(Activity activity);

	public void saveCurrentLogTime();
}
