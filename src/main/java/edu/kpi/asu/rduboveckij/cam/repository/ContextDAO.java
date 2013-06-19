package edu.kpi.asu.rduboveckij.cam.repository;

import edu.kpi.asu.rduboveckij.cam.domain.Context;
import edu.kpi.asu.rduboveckij.cam.utils.Dao;

public class ContextDAO extends Dao<Context> {

	protected ContextDAO() {
		super(Context.class);
	}

	@Override
	public void save(Context item) {
		save(item, Context.Sequence_Name);
	}
}
