package edu.kpi.asu.rduboveckij.cam.db;

import edu.kpi.asu.rduboveckij.cam.utils.Da;

public class PrecedentDA extends Da<Precedent> {

	public PrecedentDA() {
		super(Precedent.class);
	}

	@Override
	public void save(Precedent item) {
		save(item, Precedent.Sequence_Name);
	}
}