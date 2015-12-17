package by.epam.trainings.task6.dao.pool;

import java.util.ResourceBundle;

public final class DBResourceManager {

	private DBResourceManager() {
	}
	private final static String PROPERTY_FILE = "db";
	private static final ResourceBundle bundle = ResourceBundle.getBundle(PROPERTY_FILE);

	public static String getValue(String key) {
		return bundle.getString(key);
	}
}
