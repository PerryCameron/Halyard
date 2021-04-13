package com.ecsail.sql;

import java.sql.SQLException;
import java.sql.Statement;

import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.structures.Object_Award;
import com.ecsail.structures.Object_Boat;
import com.ecsail.structures.Object_Email;
import com.ecsail.structures.Object_MembershipId;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Memo;
import com.ecsail.structures.Object_Officer;
import com.ecsail.structures.Object_Payment;
import com.ecsail.structures.Object_Person;
import com.ecsail.structures.Object_Phone;

public class SqlDelete {

	public static boolean deletePerson(Object_Person p) {
	    boolean noError = false;
	    	Statement stmt;
			try {
				stmt = ConnectDatabase.connection.createStatement();
				stmt.execute(Main.console.setRegexColor("delete from person where p_id='" + p.getP_id() + "';"));
				noError = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    return noError;
	}
	
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
	
	public static boolean deleteMembershipId(Object_MembershipId mid) {
		boolean noError = false;
			try {
				Statement stmt = ConnectDatabase.connection.createStatement();
				stmt.execute(Main.console.setRegexColor("delete from membership_id where mid='" + mid.getMid() + "';"));
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
	
	public static boolean deleteAward(Object_Award a) {
		boolean noError = false;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console
					.setRegexColor("delete from awards where award_id='" + a.getAwardId() + "';"));
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
	
	public static void deleteMemo(Object_Memo memo) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from memo where memo_id='" + memo.getMemo_id() + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void deleteMemos(int ms_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from memo where ms_id='" + ms_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void deleteWorkCredits(int ms_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from work_credit where ms_id='" + ms_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void deleteWorkCreditsByMoneyID(int money_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from work_credit where money_id='" + money_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void deleteMonies(int ms_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from money where ms_id='" + ms_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void deleteMoneyByMoneyID(int money_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from money where money_id='" + money_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void deletePaymentByMoneyID(int money_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from payment where money_id='" + money_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void deletePhones(int p_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from phone where p_id='" + p_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void deleteEmail(int p_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from email where p_id='" + p_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void deleteOfficer(int p_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from officer where p_id='" + p_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void deletePerson(int p_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from person where p_id='" + p_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void deleteMembership(int ms_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from membership where ms_id='" + ms_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void deleteWaitList(int ms_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from waitlist where ms_id='" + ms_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void deleteMembershipId(int ms_id) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from membership_id where ms_id='" + ms_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	public static void deletePayment(Object_Payment p) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from payment where pay_id='" + p.getPay_id() + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
