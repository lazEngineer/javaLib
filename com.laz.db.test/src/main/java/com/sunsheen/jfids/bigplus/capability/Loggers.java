package com.sunsheen.jfids.bigplus.capability;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Loggers {
	protected static Logger logger;

	public static void init(String driverName) {
		System.setProperty("driverName", driverName);
		PropertyConfigurator.configure(Loggers.class.getClassLoader().getResource("log4j.properties"));
		logger = Logger.getLogger(Loggers.class);
	}

	public static Logger getLogger() {
		return logger;
	}
}
