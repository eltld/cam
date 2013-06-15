package edu.kpi.asu.rduboveckij.cam.db;

import edu.kpi.asu.rduboveckij.cam.utils.Da;

public class ContextDA extends Da<Context> {

	protected ContextDA() {
		super(Context.class);
	}

	@Override
	public void save(Context item) {
		save(item, Context.Sequence_Name);
	}
}
