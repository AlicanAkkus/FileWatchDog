package com.wora.watch.dog.test;

import org.junit.Test;

import com.wora.watch.dog.ConfigurationWatchDog;

public class WatchDogTest {

	@Test
	public void watchDogTest() throws Exception {
		String fileName = "src/WatchDog.properties";

		ConfigurationWatchDog watchDog = new ConfigurationWatchDog();
		watchDog.initialize(fileName, 60000);// 60 second
		
	}

}
