package edu.kpi.asu.rduboveckij.cam.db;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import edu.kpi.asu.rduboveckij.cam.utils.DomeinWithId;

@Entity
public class Context implements DomeinWithId<Long> {
	public static final String Sequence_Name = "CO_ID";

	@PrimaryKey(sequence = Sequence_Name)
	private long id;
	private int platformApi;

	public Context() {
	}

	public int getPlatformApi() {
		return platformApi;
	}

	public void setPlatformApi(int platformApi) {
		this.platformApi = platformApi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Context [id=" + id + ", platformApi=" + platformApi + "]";
	}
}
