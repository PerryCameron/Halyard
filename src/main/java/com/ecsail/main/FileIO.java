package com.ecsail.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.ecsail.structures.Object_Login;
import com.ecsail.structures.Object_TupleCount;


public class FileIO {

public static List<Object_Login> logins = new ArrayList<Object_Login>();
	
	public static void saveLoginObjects() {  // saves user file to disk
		File g = new File(Paths.HOSTS);
		System.out.println("saving " + Paths.HOSTS);
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
	
	public static void saveTupleCountObjects(List<Object_TupleCount> tuples) {  // saves user file to disk
		File g = new File(Paths.TUPLECOUNTS);
		System.out.println("saving " + Paths.TUPLECOUNTS);
		try	{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(g));
			out.writeObject(tuples); 
			out.close();
		} catch (Exception e) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static ArrayList<Object_TupleCount> openTupleCountObjects() {
		ArrayList<Object_TupleCount> tuples = new ArrayList<Object_TupleCount>();
		System.out.println();
		File g = new File(Paths.TUPLECOUNTS);
		if (g.exists()) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(g));
				Object obj = in.readObject();
				ArrayList<?> ar = (ArrayList<?>) obj;
				tuples.clear();
				for (Object x : ar) {
				    tuples.add((Object_TupleCount) x);
				}
				in.close();
			} catch (Exception e) {
				System.out.println("Error occurred during reading the file");
				System.out.println( e.getMessage() );
				e.printStackTrace();
			}			  
		} else {
			System.out.println("There is no file " + Paths.TUPLECOUNTS);
		}
		return tuples;
	}
	
	public static Object_TupleCount openTupleCountObject() {
		ArrayList<Object_TupleCount> tuples = new ArrayList<Object_TupleCount>();
		System.out.println();
		File g = new File(Paths.TUPLECOUNTS);
		if (g.exists()) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(g));
				Object obj = in.readObject();
				ArrayList<?> ar = (ArrayList<?>) obj;
				tuples.clear();
				for (Object x : ar) {
				    tuples.add((Object_TupleCount) x);
				}
				in.close();
			} catch (Exception e) {
				System.out.println("Error occurred during reading the file");
				System.out.println( e.getMessage() );
				e.printStackTrace();
			}			  
		} else {
			System.out.println("There is no file " + Paths.TUPLECOUNTS);
			System.out.println("Creating file " + Paths.TUPLECOUNTS);
			List<Object_TupleCount> t = new ArrayList<Object_TupleCount>();
			t.add(new Object_TupleCount());
			saveTupleCountObjects(t);
		}
		return tuples.get(tuples.size() - 1);
	}
	
	public static int getSelectedHost(String hostname) {
		boolean error = true;
		int count = 0;
		int iterate = 0;
		for(Object_Login login: logins) {
			if(login.getHost().equals(hostname)) {
				count = iterate;
				error = false;  // make sure at least one matches
			}
			iterate++;
		}
		if(error) count = -1;
		return count;
	}
	
	public static void openLoginObjects() {
		System.out.println();
		File g = new File(Paths.HOSTS);
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
			System.out.println("There is no file " + Paths.HOSTS);
		}
	}
	
	public static boolean hostFileExists() {
		boolean doesExist = false;
		File g = new File(Paths.HOSTS);
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
