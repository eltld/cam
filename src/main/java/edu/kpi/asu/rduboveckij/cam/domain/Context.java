package edu.kpi.asu.rduboveckij.cam.domain;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import edu.kpi.asu.rduboveckij.cam.utils.DomeinWithId;

@Entity
public class Context implements DomeinWithId<Long> {
	public static final String Sequence_Name = "CO_ID";

	@PrimaryKey(sequence = Sequence_Name)
	private long id;
	private int platform;

	public Context() {
	}

	public Context(int platform) {
		super();
		this.platform = platform;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Context [id=" + id + ", platform=" + platform + "]";
	}
}
