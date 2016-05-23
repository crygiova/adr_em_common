package fi.aalto.itia.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public class Utility {

	public static Properties getProperties(String fileName) {
		Properties properties = new Properties();
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		try {
			InputStream resourceStream = classLoader
					.getResourceAsStream(fileName);
			properties.load(resourceStream);
		} catch (IOException e) {
			System.out.println("Property file not Found: " + fileName);
		} catch (Exception e) {
			System.out.println("Exc: " + e.getMessage());
		}
		return properties;
	}
	
	public static double getRandom() {
		Random r = new Random();
		return r.nextDouble();
	}
}
