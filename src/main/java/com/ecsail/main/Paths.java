package com.ecsail.main;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Paths {
	public static final String DEPOSITREPORTPATH = System.getProperty("user.home") + "/Documents/ECSC/Depost_Reports";
	public static final String RENEWALFORM = System.getProperty("user.home") + "/Documents/ECSC/Renewal_Forms";
	public static final String SQLBACKUP = System.getProperty("user.home") + "/Documents/ECSC/SQL_Backup";
	public static final String ROSTERS = System.getProperty("user.home") + "/Documents/ECSC/Rosters";
    public static final String ECSCHOME = System.getProperty("user.home") + "/Documents/ECSC";
	public static final String LOGO = "/ECSClogo4.png";
	public static final String HOSTS = System.getProperty("user.home") + "/.ecsc/hosts.ecs";
	public static final String SCRIPTS = System.getProperty("user.home") + "/.ecsc/scripts";
	public static final String TUPLECOUNTS = System.getProperty("user.home") + "/.ecsc/tuples.ecs";
	public static final String SLIPCHART = System.getProperty("user.home") + "/Documents/ECSC/SlipCharts";
	// "C:\\Users\\pcame\\Documents\\email.xlsx"
	
	public static void checkPath(String path) {
		File recordsDir = new File(path);

		if (!recordsDir.exists()) {
			System.out.println("Creating dir: " + path); // USERFILETEMPLATE
		    recordsDir.mkdirs();
		}
	}
	
	public static String getYear() {
		return new SimpleDateFormat("yyyy").format(new Date());
	}
	
	public static String getDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
}
//Documents