package edu.kpi.asu.rduboveckij.cam.repository;

import edu.kpi.asu.rduboveckij.cam.domain.Precedent;
import edu.kpi.asu.rduboveckij.cam.utils.Dao;

public class PrecedentDAO extends Dao<Precedent> {

	public PrecedentDAO() {
		super(Precedent.class);
	}

	@Override
	public void save(Precedent item) {
		save(item, Precedent.Sequence_Name);
	}
}