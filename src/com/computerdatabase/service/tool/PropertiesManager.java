package com.computerdatabase.service.tool;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {

	public static Properties prop = new Properties();
	public static InputStream input = null;

	public static void load() {
		try {
			PropertiesManager.input = new FileInputStream("config.properties");

			// load a properties file
			PropertiesManager.prop.load(PropertiesManager.input);

		} catch (final IOException ex) {
			ex.printStackTrace();
		} finally {
			if (PropertiesManager.input != null)
				try {
					PropertiesManager.input.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
		}

	}

}
