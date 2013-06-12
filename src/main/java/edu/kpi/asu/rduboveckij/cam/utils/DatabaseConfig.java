package edu.kpi.asu.rduboveckij.cam.utils;

import java.io.File;

import android.util.Log;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.StoreConfig;
import com.sleepycat.persist.evolve.IncompatibleClassException;
/**
 * Use Singleton pattern
 */
public class DatabaseConfig {
	private static DatabaseConfig ourInstance;

	private Environment envmnt;
	private EntityStore store;
	/**
	 * Constructor Singleton
	 * @return self instance
	 */
	public static DatabaseConfig getInstance() {
		if (ourInstance == null)
			throw new IllegalArgumentException(
					"You need initialize database config previously!");
		return ourInstance;
	}
	
	/**
	 * Create db or open and self instance
	 * @param envDir db path
	 */
	public static void init(File envDir) {
		if (!envDir.exists()) {
			if (!envDir.mkdirs()) {
				Log.e("TravellerLog :: ", "Problem creating folder");
			}
		}
		ourInstance = new DatabaseConfig(envDir);
	}

	public static void init(String name) {
		init(new File(name));
	}
	/**
	 * Data base initialization
	 * @param envDir db path
	 */
	private DatabaseConfig(File envDir) {
		EnvironmentConfig envConfig = new EnvironmentConfig();
		StoreConfig storeConfig = new StoreConfig();

		envConfig.setTransactional(true);
		envConfig.setAllowCreate(true);
		storeConfig.setAllowCreate(true);
		storeConfig.setTransactional(true);
		envmnt = new Environment(envDir, envConfig);
		try {
			store = new EntityStore(envmnt, "autocalc", storeConfig);
		} catch (IncompatibleClassException e) {
		}
	}
	/**
	 * Close connection
	 */
	public static void shutdown() {
		if (ourInstance != null) {
			ourInstance.close();
		}
	}
	
	private void close() {
		store.close();
		envmnt.close();

	}

	public EntityStore getStore() {
		return store;
	}
	
	/**
	 * Begin Transaction
	 * @return Transaction
	 */
	public Transaction startTransaction() {
		return envmnt.beginTransaction(null, null);
	}

}
