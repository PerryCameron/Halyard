package com.ecsail.main;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ecsail.structures.Object_Membership;
import com.ecsail.structures.Object_Person;

public class SqlExists {
	
	public static Boolean personExists(int pid, int type, int ms_id) {
		Boolean answer = false;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from person INNER JOIN membership ON person.MS_ID = membership.MS_ID where membership.MS_ID ='" 
						+ ms_id + "' AND person.MEMBER_TYPE='" + type + "');"));
			rs.next();
		    answer = rs.getBoolean("EXISTS(select * from person INNER JOIN membership ON person.MS_ID = membership.MS_ID where membership.MS_ID ='"
		    		    + ms_id +"' AND person.MEMBER_TYPE='" + type + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answer;
	}
	
	public static boolean memberShipExists(int ms_id) {
		Boolean result = false;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from membership WHERE ms_id='" + ms_id + "')"));
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(select * from membership WHERE ms_id='" + ms_id + "')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static Boolean slipExists(int ms_id) {
		Boolean result = false;
		  // we must convert here (this is getting crazy!)
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from slip WHERE ms_id='" + ms_id + "')"));
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(select * from slip WHERE ms_id='" + ms_id + "')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static Boolean slipRentExists(int subMsid) {
		Boolean result = false;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(
					Main.console.setRegexColor("SELECT EXISTS(select * from slip WHERE subleased_to='" + subMsid + "')"));
			while (rs.next()) {
				result = rs.getBoolean("EXISTS(select * from slip WHERE subleased_to='" + subMsid + "')");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static Boolean ownsSlip(int ms_id) {
		Boolean result = false;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from slip WHERE ms_id='" + ms_id + "')"));
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(select * from slip WHERE ms_id='" + ms_id + "')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static Boolean subleasesSlip(int ms_id) {
		Boolean result = false;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from slip WHERE subleased_to='" + ms_id + "')"));
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(select * from slip WHERE subleased_to='" + ms_id + "')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static Boolean recordExists(String year, Object_Membership membership) {
		Boolean result = false;		
		  // we must convert here (this is getting crazy!)
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from money WHERE ms_id='" + membership.getMsid() + "' and fiscal_year='" + year + "')"));
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(select * from money WHERE ms_id='" + membership.getMsid() + "' and fiscal_year='" + year + "')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static Boolean isOfficer(Object_Person per,int year) {
		boolean result = false;
		try {  
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from officer WHERE p_id='"
							+ per.getP_id() + "' AND off_year='" + year + "' and OFF_TYPE != 'BM')"));
			while (rs.next()) {
				result = rs.getBoolean(
						"EXISTS(select * from officer WHERE p_id='"
							+ per.getP_id() + "' AND off_year='" + year + "' and OFF_TYPE != 'BM')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
