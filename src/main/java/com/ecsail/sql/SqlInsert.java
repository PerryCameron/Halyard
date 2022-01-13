package com.ecsail.sql;

import java.sql.SQLException;
import java.sql.Statement;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.main.SqlScriptMaker;
import com.ecsail.structures.*;

public class SqlInsert {
	
	///////////////  CLASS OF STATIC PURE FUNCTIONS /////////////////////////////
	
	// add phone record
	public static boolean addPhoneRecord(int phone_id, int pid , Boolean listed, String phone, String type) {
		boolean noError = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT into phone () VALUES (" + phone_id + "," + pid + ",\"" + phone + "\",'" + type + "'," + listed + ");"));
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
		return noError;  // return true if insert performed without error
	}
	
	// add email record
	public static boolean addEmailRecord(int email_id, int pid, Boolean primary, String email, Boolean listed) {
		boolean noError = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT into email () VALUES (" + email_id + "," + pid + ","
					+ primary + ",\"" + email + "\"," + listed + ");"));
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
		return noError;  // return true if insert performed without error
	}
	
	public static boolean addOfficerRecord(int officer_id, int pid , String board_year, String officer, int year) {
		boolean noError = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT into officer () VALUES (" + officer_id + "," + pid + "," + board_year + ",\"" + officer + "\"," + year + ");"));
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
		return noError;  // return true if insert performed without error
	}
	
	public static void addPaymentRecord(Object_Payment op) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT into payment () VALUES (" + op.getPay_id() + ","
					+ op.getMoney_id() + "," + op.getCheckNumber() + ",'" + op.getPaymentType() + "','"
					+ op.getPaymentDate() + "','" + op.getPaymentAmount() + "','" + op.getDeposit_id() + "');"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static boolean addAwardRecord(AwardDTO a) {
		boolean noError = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT into awards () VALUES (" + a.getAwardId() + ","
					+ a.getPid() + ",'" + a.getAwardYear() + "','" + a.getAwardType() + "')"));
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
		return noError;  // return true if insert performed without error
	}
	
	public static boolean addBoatRecord(BoatDTO b, int msid) {
		boolean noError = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor(
					"INSERT into boat () VALUES (" + b.getBoat_id() + ",null,null,null,null,null,null,true,null,null,null,null,null,null,null);"));
			stmt.execute(
					Main.console.setRegexColor("INSERT into boat_owner () VALUES (" + msid + "," + b.getBoat_id() + ");"));
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
		return noError;  // return true if insert performed without error
	}
	
	public static void addPersonRecord(Object_Person person) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO person () VALUES (" 
	    	+ person.getP_id() + "," + person.getMs_id() + ",'" + person.getMemberType() + "',\"" + person.getFname()
	    	+ "\",\"" + person.getLname() + "\"," + SqlScriptMaker.getCorrectString(person.getBirthday()) 
	    	+ ",\"" + person.getOccupation() + "\",\"" + person.getBuisness() +"\",true,"+person.getNname()+",'"+person.getNname()+"');"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addWorkCreditRecord(int moneyId, MembershipDTO membership) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO work_credit () VALUES (" 
					+ moneyId + "," 
					+ membership.getMsid() + ",0,0,0,0);"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addMoneyRecord(Object_Money m) {
		//Object_DefinedFee definedFees = selectDefinedFees(year);
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO money () VALUES (" 
			+ m.getMoney_id() + "," + m.getMs_id() + "," + m.getFiscal_year() + "," + m.getBatch()
			+ "," + m.getOfficer_credit() + "," + m.getExtra_key() + "," + m.getKayac_shed_key() 
			+ "," + m.getSail_loft_key() + "," + m.getSail_school_loft_key() + "," + m.getBeach() 
			+ "," + m.getWet_slip() + "," + m.getKayac_rack() + "," + m.getKayac_shed() 
			+ "," + m.getSail_loft() + "," + m.getSail_school_laser_loft() + "," + m.getWinter_storage() 
			+ "," + m.getYsc_donation() + "," + m.getPaid() + "," + m.getTotal() 
			+ "," + m.getCredit() + "," + m.getBalance() + "," + m.getDues() 
			+  "," + m.isCommitted() + "," + m.isClosed() + "," + m.getOther() + "," + m.getInitiation() + "," + m.isSupplemental() + "," + m.getWork_credit() + "," + m.getOther_credit() + ");"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addDefinedFeeRecord(DefinedFeeDTO d) {
		//Object_DefinedFee definedFees = selectDefinedFees(year);
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO defined_fee () VALUES (" 
			 + d.getFiscal_year() + "," + d.getDues_regular() + "," + d.getDues_family() + "," + d.getDues_lake_associate() 
			 + "," + d.getDues_social() + "," + d.getInitiation() + "," + d.getWet_slip() + "," + d.getBeach()
			 + "," + d.getWinter_storage() + "," + d.getMain_gate_key() + "," + d.getSail_loft() + "," + d.getSail_loft_key()
			 + "," + d.getSail_school_laser_loft() + "," + d.getSail_school_loft_key() + "," + d.getKayak_rack()
			 + "," + d.getKayak_shed() + "," + d.getKayak_shed_key() + "," + d.getWork_credit()
			 +");"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static boolean addMembershipIsSucessful(MembershipListDTO nm) {
		boolean updateIsSucessful = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO membership () VALUES (" + nm.getMsid() + ",'" + nm.getPid() + "','" + nm.getJoinDate() + "','FM','','','IN','');")); 
			updateIsSucessful = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
		return updateIsSucessful;
	}
	
	public static void addMemo(MemoDTO m) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO memo () VALUES (" + m.getMemo_id() + "," + m.getMsid() + ",'" + m.getMemo_date() + "',\"" + m.getMemo() + "\"," + m.getMoney_id() + ",\"" + m.getCategory() + "\");"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addDeposit(DepositDTO d) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO deposit () VALUES (" + d.getDeposit_id() + ",'" + d.getDepositDate() + "','" + d.getFiscalYear() + "'," + d.getBatch() + ");"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addMembershipId(MembershipIdDTO id) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO membership_id () VALUES (" + id.getMid() + ",'" + id.getFiscal_Year() + "','" + id.getMs_id() 
			+ "'," + id.getMembership_id() + "," + id.isRenew() + ",'" + id.getMem_type()+ "'," + id.isSelected() + "," + id.isLateRenew() + ")"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addWaitList(Object_WaitList w) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO waitlist () VALUES (" 
			+ w.getMs_id() + "," 
			+ w.isSlipWait() + "," 
			+ w.isKayakWait() + "," 
			+ w.isShedWait() + ","
			+ w.isWantToSublease() + "," 
			+ w.isWantsRelease() + ","
			+ w.isWantSlipChange() + ");"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addStatRecord(Object_Stats s) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO stats () VALUES (" 
			+ s.getStatId() + "," 
			+ s.getFiscalYear() + "," 
			+ s.getActiveMemberships() + "," 
			+ s.getNonRenewMemberships() + ","
			+ s.getReturnMemberships() + "," 
			+ s.getNewMemberships() + ","
			+ s.getSecondaryMembers() + ","
			+ s.getDependants() + ","
			+ s.getNumberOfBoats() + ","
			+ s.getFamily() + ","
			+ s.getRegular() + ","
			+ s.getSocial() + ","
			+ s.getLakeAssociates() + ","
			+ s.getLifeMembers() + ","
			+ s.getRaceFellows() + ","
			+ s.getStudent() + ","
			+ s.getDeposits() + ","
			+ s.getInitiation() + ");"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addBoatOwner(int boat_id,int ms_id) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO boat_owner () VALUES (" + ms_id + "," + boat_id +")"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
}
