package com.ecsail.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.ecsail.structures.Object_Login;


public class FileIO {
public static final String LOGO = "/ECSClogo4.png";
public static final String HOSTS = System.getProperty("user.home") + "/.ecsc/hosts.ecs";
public static final String SCRIPTS = System.getProperty("user.home") + "/.ecsc/scripts";
public static List<Object_Login> logins = new ArrayList<Object_Login>();
	
	public static void saveLoginObjects() {  // saves user file to disk
		File g = new File(HOSTS);
		System.out.println("saving " + HOSTS);
		try	{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(g));
			out.writeObject(logins); 
			out.close();
		} catch (Exception e) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static Object_Login getSelectedHost(String hostname) {
		int count = 0;
		int iterate = 0;
		for(Object_Login login: logins) {
			if(login.getHost().equals(hostname)) {
				count = iterate;
			}
			iterate++;
		}
		return logins.get(count);
	}
	
	public static void openLoginObjects() {
		System.out.println();
		File g = new File(HOSTS);
		if (g.exists()) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(g));
				Object obj = in.readObject();
				ArrayList<?> ar = (ArrayList<?>) obj;
				logins.clear();
				for (Object x : ar) {
				    logins.add((Object_Login) x);
				}
				in.close();
			} catch (Exception e) {
				System.out.println("Error occurred during reading the file");
				System.out.println( e.getMessage() );
				e.printStackTrace();
			}			  
		} else {
			System.out.println("There is no file " + HOSTS);
		}
	}
	
	public static boolean hostFileExists() {
		boolean doesExist = false;
		File g = new File(HOSTS);
		if(g.exists())
			doesExist = true;
		return doesExist;
	}
	
	public static int getDefaultLogon() {
		int count = 0;
		int iterate = 0;
		for(Object_Login login: logins) {
			if(login.isDefault()) {
				count = iterate;
			}
			iterate++;
		}
		return count;
	}
	
	
}
