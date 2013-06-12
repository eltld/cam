package edu.kpi.asu.rduboveckij.cam.utils;

import java.util.Map;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.PrimaryIndex;

public abstract class Da<T extends DomeinWithId<Long>> {
	private PrimaryIndex<Long, T> index;

	private DatabaseConfig dbConfig;

	protected Da(Class<T> classT) {
		dbConfig = DatabaseConfig.getInstance();

		index = dbConfig.getStore().getPrimaryIndex(Long.class, classT);
	}

	public abstract void save(T item);

	public void saveAll(T... items) {
		for (T item : items)
			save(item);
	}

	protected void save(T item, String sequence_name) {
		Transaction tx = dbConfig.startTransaction();
		try {
			if (item.getId() == 0) {
				long id = dbConfig.getStore().getSequence(sequence_name)
						.get(tx, 1);
				item.setId(id);
			}
			index.put(tx, item);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.abort();
				tx = null;
			}
		}
	}

	public T find(Long id) {
		return index.get(id);
	}

	public Map<Long, T> findAll() {
		return index.map();
	}

	public void remove(Long id) {
		try {
			index.delete(id);
		} catch (DatabaseException e) {
			e.printStackTrace();
			index.delete(id);
		}
	}
}
