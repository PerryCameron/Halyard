package com.ecsail.main;

import java.sql.SQLException;
import java.sql.Statement;

import com.ecsail.structures.Object_Boat;
import com.ecsail.structures.Object_Email;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Officer;
import com.ecsail.structures.Object_Phone;

public class SqlDelete {

	public static boolean deletePhone(Object_Phone phone) {
	    boolean noError = false;
	    	Statement stmt;
			try {
				stmt = ConnectDatabase.connection.createStatement();
				stmt.execute(Main.console.setRegexColor("delete from phone where phone_id='" + phone.getPhone_ID() + "';"));
				noError = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    return noError;
	}
	
	public static boolean deleteEmail(Object_Email email) {
		boolean noError = false;
			try {
				Statement stmt = ConnectDatabase.connection.createStatement();
				stmt.execute(Main.console.setRegexColor("delete from email where email_id='" + email.getEmail_id() + "';"));
				noError = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    return noError;
	}
	
	public static boolean deleteOfficer(Object_Officer officer) {
		boolean noError = false;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console
					.setRegexColor("delete from officer where o_id='" + officer.getOfficer_id() + "';"));
			noError = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return noError;
	}
	
	public static boolean deleteBoat(Object_Boat boat, Object_MembershipList membership) {
		boolean noError = false;
			try {
				Statement stmt = ConnectDatabase.connection.createStatement();
				stmt.execute(Main.console.setRegexColor("delete from boat_owner where ms_id='" + membership.getMsid()
						+ "' AND boat_id='" + boat.getBoat_id() + "';"));
				stmt.execute(Main.console.setRegexColor(
						"delete from boat where boat_id='" + boat.getBoat_id() + "';"));
				noError = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return noError;	
	}
	
	public static boolean deleteBoatOwner(int ms_id) {
		boolean noError = false;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from boat_owner where ms_id='" + ms_id
					 + "';"));
			noError = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return noError;	
	}
}
