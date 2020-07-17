package com.ecsail.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Person;

public class Membership {

	public Membership() {
	}
	
	private final static int getCount(String column) {  // gives the last memo_id number
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			rs = stmt.executeQuery("select " + column + " from membership ORDER BY " + column + " DESC LIMIT 1");
			rs.next();
			number = rs.getInt(column);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	
	public static int getCount()  {  // gives the last memo_id number
		int number = 0;
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select * from person ORDER BY p_id DESC LIMIT 1"));
			rs.next();
			number = rs.getInt("P_ID");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	
	public static Object_Person createUser(int msid) {
		
		int pid = getCount() + 1;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO person () VALUES (" + pid  +"," + msid + ",1,'','',null,'','',true,null);"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Object_Person(pid,msid,1,"","",null,"","",true);
	}
	
	public static void Create() {
		
		int msid = getCount("ms_id") + 1;
		int membership_id = getCount("membership_id") +1;
		int pid = getCount() + 1;
		Note newMemNote = new Note();
		int note_id = newMemNote.getCount() + 1;
		//Membership.createUser(pid, msid);//createUser(pid,msid);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now(); 
		String date = dtf.format(now);
		
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute("INSERT INTO membership () VALUES (" + msid + ",'" + pid + "','" + membership_id + "','" + date + "','FM',1,'','','IN','');");
			newMemNote.addMemo(note_id,msid, date, "Created new membership record");  // adds a note that the membership was created.
			Main.activememberships.add(new Object_MembershipList(msid, pid, membership_id, date,true, "FM", "", "", "", 0, "", "", "", ""));
			TabLauncher.createTab(membership_id,msid);  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
