package com.ecsail.main;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Paths {
	public static final String DEPOSITREPORTPATH = System.getProperty("user.home") + "/Documents/ECSC/Depost_Reports";
	public static final String SQLBACKUP = System.getProperty("user.home") + "/Documents/ECSC/SQL_Backup";
    public static final String EMAILLIST = System.getProperty("user.home") + "/Documents/ECSC";
	public static final String LOGO = "/ECSClogo4.png";
	public static final String HOSTS = System.getProperty("user.home") + "/.ecsc/hosts.ecs";
	public static final String SCRIPTS = System.getProperty("user.home") + "/.ecsc/scripts";
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
	
}
//Documents