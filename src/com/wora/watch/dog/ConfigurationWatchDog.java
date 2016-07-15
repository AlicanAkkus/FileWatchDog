package com.wora.watch.dog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationWatchDog extends FileWatchdog {

	private String propertiesFilePath = null;

	@Override
	public void initialize(String propertiesFilePath, int watchDelay) throws Exception {
		this.propertiesFilePath = propertiesFilePath;
		delay = watchDelay;
		StringBuilder sb = new StringBuilder(".::. ConfigurationWatchDog initializing  .::.");
		sb.append("\n\tpropertiesFilePath : ").append(propertiesFilePath);
		sb.append("\n\twatchDelay : ").append(watchDelay / 1000);

		System.out.println(sb.toString());
		super.initialize(propertiesFilePath, watchDelay);
		start();
	}

	@Override
	protected void doOnChange() throws Exception {

		Properties props = loadPropertiesFile();

			try {
				String updatedAt = props.getProperty("properties.update.at", "").trim();
				String updatedBy = props.getProperty("properties.update.by", "").trim();

				StringBuilder logString = new StringBuilder("\n\n\t*************************************");
				logString.append("\n\t*     ").append(propertiesFilePath);
				logString.append("\n\t*     Updated By : ").append(updatedBy).append(" - Updated At : ").append(updatedAt);
				logString.append("\n\t*************************************\n");

				System.out.println(logString.toString());

			} catch (Exception e) {
				System.err.println(e.getMessage());
			}


	}

	private Properties loadPropertiesFile() throws Exception {
		File propertiesFile = new File(filename);
		Properties props = new Properties();

		System.out.println("Loading setings Filename: " + filename);
		if (!propertiesFile.exists()) {
			propertiesFile = new File(System.getProperty("user.dir") + File.separator + filename);
		}

		if (propertiesFile.exists() && !propertiesFile.isDirectory()) {
			try {
				FileInputStream fis = new FileInputStream(propertiesFile);
				props.load(fis);
				fis.close();

				return props;
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		} else {
			System.err.println(filename + " : File not exist");
		}
		return props;
	}

}
