package edu.kpi.asu.rduboveckij.cam.utils;

import java.util.Map;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.PrimaryIndex;

/**
 * Use Data Access Object pattern
 * 
 * @param <T>
 *            extends DomeinWithId<Long>
 */
public abstract class Dao<T extends DomeinWithId<Long>> {
	private PrimaryIndex<Long, T> index;

	private DatabaseConfig dbConfig;

	protected Dao(Class<T> classT) {
		dbConfig = DatabaseConfig.getInstance();

		index = dbConfig.getStore().getPrimaryIndex(Long.class, classT);
	}

	public abstract void save(T item);

	/**
	 * Save many items
	 * 
	 * @param items
	 *            extends DomeinWithId<Long>
	 */
	public void saveAll(T... items) {
		for (T item : items)
			save(item);
	}

	/**
	 * Save one item
	 * 
	 * @param item
	 *            extends DomeinWithId<Long>
	 * @param sequence_name
	 *            need for auto_incremant
	 */
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

	/**
	 * Find one
	 * 
	 * @param id
	 * @return Item
	 */
	public T find(Long id) {
		return index.get(id);
	}

	/**
	 * Find All
	 * 
	 * @return Map Items with id
	 */
	public Map<Long, T> findAll() {
		return index.map();
	}

	/**
	 * Remove All
	 */
	public void removeAll() {
		for (T t : findAll().values())
			remove(t.getId());
	}

	/**
	 * Remove one Items
	 * 
	 * @param id
	 */
	public void remove(Long id) {
		try {
			index.delete(id);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
}
