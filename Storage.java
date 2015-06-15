package com.zhs.server;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * responsible for storing data in memory database
 */

public class Storage {

	private static final Storage instance = new Storage();

	private static final Logger log = Logger.getLogger(Storage.class);
	
	private final Map<String, ArrayList<String>> store = new HashMap<String, ArrayList<String>>();
	
	/**
	 *
	 * @return the singleton
	 */
	public static Storage getInstance() {
		return instance;
	}

	/**
	 * @param key
	 * @param value
	 */
	void create(String key, ArrayList<String> value) {
		if (store.containsKey(key)) {
			throw new IllegalStateException("A value for '" + key + "' is already present.");
		}
		
		if (value == null) {
			throw new IllegalArgumentException("Value cannot be null.");
		}
		
		store.put(key, value);
	}
	
	/**
	 * retrieves the content of the file with name key
	 *
	 * @param key
	 * @return the retrieved value
	 */
	public ArrayList<String> read(String key) {

		return store.get(key);
		
	}

	/**
	 * deletes the value correponding to the key key
	 *
	 * @param key
	 * @return true if a value was deleted
	 */
	public boolean delete(String key) {
		
		return store.remove(key) != null;

	}
	
	/**
	 * updates the value corresponding to the key key
	 *
	 * @param key
	 * @param value
	 *
	 */

	public void update(String key, ArrayList<String> value) {
		if (!store.containsKey(key)) {
			throw new IllegalStateException("There is no value to update for key '" + key + "'.");
		}

		if (value == null) {
			throw new IllegalArgumentException("Value cannot be null.");
		}

		store.put(key, value);
		
	}
}
