package com.ecsail.sql;

import java.sql.SQLException;
import java.sql.Statement;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.structures.*;
import com.ecsail.structures.MemoDTO;

public class SqlDelete {

	public static boolean deleteStatistics() {
	    boolean noError = false;
    	Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from stats"));
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}
    return noError;
	}
	
	public static boolean deletePerson(Object_Person p) {
	    boolean noError = false;
	    	Statement stmt;
			try {
				stmt = ConnectDatabase.sqlConnection.createStatement();
				stmt.execute(Main.console.setRegexColor("delete from person where p_id='" + p.getP_id() + "';"));
				noError = true;
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
			}
	    return noError;
	}
	
	public static boolean deletePhone(Object_Phone phone) {
	    boolean noError = false;
	    	Statement stmt;
			try {
				stmt = ConnectDatabase.sqlConnection.createStatement();
				stmt.execute(Main.console.setRegexColor("delete from phone where phone_id='" + phone.getPhone_ID() + "';"));
				noError = true;
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
			}
	    return noError;
	}
	
	public static boolean deleteBlankMembershipIdRow() {
	    boolean noError = false;
	    	Statement stmt;
			try {
				stmt = ConnectDatabase.sqlConnection.createStatement();
				stmt.execute(Main.console.setRegexColor("delete from membership_id where fiscal_year=0 and MEMBERSHIP_ID=0"));
				noError = true;
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to Delete Blank Membership ID Row","See below for details");
			}
			System.out.println("Deleting any rows with memId=0 and year=0");
	    return noError;
	}
	
	public static boolean deleteEmail(EmailDTO email) {
		boolean noError = false;
			try {
				Statement stmt = ConnectDatabase.sqlConnection.createStatement();
				stmt.execute(Main.console.setRegexColor("delete from email where email_id='" + email.getEmail_id() + "';"));
				noError = true;
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
			}
	    return noError;
	}
	
	public static boolean deleteMembershipId(MembershipIdDTO mid) {
		boolean noError = false;
			try {
				Statement stmt = ConnectDatabase.sqlConnection.createStatement();
				stmt.execute(Main.console.setRegexColor("delete from membership_id where mid='" + mid.getMid() + "';"));
				noError = true;
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
			}
	    return noError;
	}
	
	public static boolean deleteOfficer(Object_Officer officer) {
		boolean noError = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console
					.setRegexColor("delete from officer where o_id='" + officer.getOfficer_id() + "';"));
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}
		return noError;
	}
	
	public static boolean deleteAward(AwardDTO a) {
		boolean noError = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console
					.setRegexColor("delete from awards where award_id='" + a.getAwardId() + "';"));
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}
		return noError;
	}
	
	public static boolean deleteBoat(BoatDTO boat, MembershipListDTO membership) {
		boolean noError = false;
			try {
				Statement stmt = ConnectDatabase.sqlConnection.createStatement();
				stmt.execute(Main.console.setRegexColor("delete from boat_owner where ms_id='" + membership.getMsid()
						+ "' AND boat_id='" + boat.getBoat_id() + "';"));
				stmt.execute(Main.console.setRegexColor(
						"delete from boat where boat_id='" + boat.getBoat_id() + "';"));
				noError = true;
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
			}
			return noError;	
	}
	
	public static boolean deleteBoatOwner(int ms_id) {
		boolean noError = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from boat_owner where ms_id='" + ms_id
					 + "';"));
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}
		return noError;	
	}
	
	public static void deleteMemo(MemoDTO memo) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from memo where memo_id='" + memo.getMemo_id() + "';"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteMemos(int ms_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from memo where ms_id='" + ms_id + "';"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteWorkCredits(int ms_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from work_credit where ms_id='" + ms_id + "';"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteWorkCreditsByMoneyID(int money_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from work_credit where money_id='" + money_id + "';"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteMonies(int ms_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from money where ms_id='" + ms_id + "';"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteMoneyByMoneyID(int money_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from money where money_id='" + money_id + "';"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deletePaymentByMoneyID(int money_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from payment where money_id='" + money_id + "';"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deletePhones(int p_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from phone where p_id='" + p_id + "';"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteEmail(int p_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from email where p_id='" + p_id + "';"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteOfficer(int p_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from officer where p_id='" + p_id + "';"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deletePerson(int p_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from person where p_id='" + p_id + "';"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteMembership(int ms_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from membership where ms_id='" + ms_id + "';"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteWaitList(int ms_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from waitlist where ms_id='" + ms_id + "';"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteMembershipId(int ms_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from membership_id where ms_id='" + ms_id + "';"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	
	public static void deletePayment(Object_Payment p) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from payment where pay_id='" + p.getPay_id() + "';"));
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete Payment","See below for details");
		}	
	}
	
	public static boolean deleteBoatOwner(int boat_id, int ms_id) {
		boolean noError = false;
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from boat_owner where boat_id=" + boat_id + " and ms_id=" + ms_id));
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete Boat Owner","See below for details");
		}
		return noError;	
	}
}
