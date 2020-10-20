package com.ecsail.main;

import java.sql.SQLException;
import java.sql.Statement;

import com.ecsail.structures.Object_Membership;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Money;
import com.ecsail.structures.Object_Payment;
import com.ecsail.structures.Object_Person;

public class SqlInsert {
	
	///////////////  CLASS OF STATIC PURE FUNCTIONS /////////////////////////////
	
	// add phone record
	public static boolean addRecord(int phone_id, int pid ,Boolean listed, String phone, String type) {
		boolean noError = false;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT into phone () VALUES (" + phone_id + "," + pid + ",\"" + phone + "\",'" + type + "'," + listed + ");"));
			noError = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return noError;  // return true if insert performed without error
	}
	
	// add email record
	public static void addRecord(int email_id, int pid, Boolean primary, String email, Boolean listed) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT into email () VALUES (" + email_id + "," + pid + ","
					+ primary + ",\"" + email + "\"," + listed + ");"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void addRecord(int officer_id, int pid ,String board_year, String officer, int year) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT into officer () VALUES (" + officer_id + "," + pid + "," + board_year + ",\"" + officer + "\"," + year + ");"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void addRecord(Object_Payment op) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT into payment () VALUES (" 
			+ op.getPay_id() + "," 
			+ op.getMoney_id() + "," 
			+ op.getCheckNumber() + ",'" 
			+ op.getPaymentType() + "','" 
			+ op.getPaymentDate() + "','" 
			+ op.getPaymentAmount() + "','" 
			+ op.getDeposit_id() + "');"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void addRecord(int boat_id, int msid) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor(
					"INSERT into boat () VALUES (" + boat_id + ",null,null,null,null,null,null,true,null,null,null,null);"));
			stmt.execute(
					Main.console.setRegexColor("INSERT into boat_owner () VALUES (" + msid + "," + boat_id + ");"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void addRecord(Object_Person person) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO person () VALUES (" 
	    	+ person.getP_id() + "," + person.getMs_id() + ",'" + person.getMemberType() + "',\"" + person.getFname()
	    	+ "\",\"" + person.getLname() + "\"," + SqlScriptMaker.getCorrectString(person.getBirthday()) 
	    	+ ",\"" + person.getOccupation() + "\",\"" + person.getBuisness() +"\",true,null);"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void addRecord(int moneyId, Object_Membership membership) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO work_credit () VALUES (" 
					+ moneyId + "," 
					+ membership.getMsid() + ",0,0,0,0);"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void addRecord(Object_Money m) {
		//Object_DefinedFee definedFees = selectDefinedFees(year);
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO money () VALUES (" 
			+ m.getMoney_id() + "," + m.getMs_id() + "," + m.getFiscal_year() + "," + m.getBatch()
			+ "," + m.getOfficer_credit() + "," + m.getExtra_key() + "," + m.getKayac_shed_key() 
			+ "," + m.getSail_loft_key() + "," + m.getSail_school_loft_key() + "," + m.getBeach() 
			+ "," + m.getWet_slip() + "," + m.getKayac_rack() + "," + m.getKayac_shed() 
			+ "," + m.getSail_loft() + "," + m.getSail_school_laser_loft() + "," + m.getWinter_storage() 
			+ "," + m.getYsc_donation() + "," + m.getPaid() + "," + m.getTotal() 
			+ "," + m.getCredit() + "," + m.getBalance() + "," + m.getDues() 
			+  "," + m.isCommitted() + "," + m.isClosed() + "," + m.getOther() + "," + m.getInitiation() + "," + m.isSupplemental() + ");"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean addMembershipIsSucessful(Object_MembershipList nm) {
		boolean updateIsSucessful = false;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO membership () VALUES (" + nm.getMsid() + ",'" + nm.getPid() + "','" + nm.getMembershipId() + "','" + nm.getJoinDate() + "','FM',1,'','','IN','');")); 
			updateIsSucessful = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateIsSucessful;
	}
	
	public static void addMemo(int memo_id,int msid, String date, String memo) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO memo () VALUES (" + memo_id + "," + msid + ",'" + date + "',\"" + memo + "\");"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
