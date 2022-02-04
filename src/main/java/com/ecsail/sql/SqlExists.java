package com.ecsail.sql;


import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.HalyardPaths;
import com.ecsail.main.Main;
import com.ecsail.structures.MembershipDTO;
import com.ecsail.structures.MembershipListDTO;
import com.ecsail.structures.PersonDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlExists {
	
	// this may be a duplicate, for instance it dosent need int pid, and why the inner join
	// this is used on BoxAddPerson only
	public static Boolean personExists(int type, int ms_id) {
		boolean answer = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from person INNER JOIN membership ON person.MS_ID = membership.MS_ID where membership.MS_ID ="
						+ ms_id + " AND person.MEMBER_TYPE=" + type + ") AS personexists"));
			rs.next();
		    answer = rs.getBoolean("personexists");
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");

		}
		return answer;
	}
	
	public static Boolean paymentExists(int money_id) {
		boolean answer = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(SELECT * FROM payment WHERE MONEY_ID=" + money_id + ");"));
			rs.next();
		    answer = rs.getBoolean("EXISTS(SELECT * FROM payment WHERE MONEY_ID=" + money_id + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if money record exists","See below for details");
		}
		return answer;
	}
	
	public static Boolean paymentsExistForMembership(int ms_id) {
		boolean answer = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(SELECT * FROM money WHERE MS_ID=" + ms_id + ") AS paymentexists"));
			rs.next();
		    answer = rs.getBoolean("paymentexists");
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if money record exists","See below for details");
		}
		return answer;
	}
	
	public static Boolean memoExists(int money_id, String category) {
		boolean answer = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(SELECT * FROM memo WHERE CATEGORY='" + category + "' and MONEY_ID=" + money_id + ") AS memoExists"));
			rs.next();
		    answer = rs.getBoolean("memoExists");
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return answer;
	}
	
	public static boolean memberShipExists(int ms_id) {
		boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from membership WHERE ms_id='" + ms_id + "')"));
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(select * from membership WHERE ms_id='" + ms_id + "')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to verify if a membership exists","See below for details");
		}
		return result;
	}
	
	public static boolean memberShipIdExists(int ms_id) {
		boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from membership_id WHERE ms_id='" + ms_id + "')"));
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(select * from membership_id WHERE ms_id='" + ms_id + "')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to verify if a history record exists","See below for details");
		}
		return result;
	}
	
	public static boolean membershipIdBlankRowExists(String msid) {
		boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from membership_id where fiscal_year=0 and MEMBERSHIP_ID=0 and MS_ID!="+msid+") AS newtuple"));
			while(rs.next()) {
			result = rs.getBoolean("newtuple");
			}
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if a blank membership_id row exists","See below for details");
		}
		return result;
	}
	
	public static boolean memberShipIdExists(int ms_id, String year) {
		boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from membership_id WHERE ms_id='" + ms_id + "' and FISCAL_YEAR='" + year + "')"));
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(select * from membership_id WHERE ms_id='" + ms_id + "' and FISCAL_YEAR='" + year + "')");
			}
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static boolean activePersonExists(int ms_id, int member_type) {
		boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select exists(select * from person where MS_ID=" + ms_id + " and MEMBER_TYPE=" + member_type + " and is_active=true)"));
			while(rs.next()) {
			result = rs.getBoolean("exists(select * from person where MS_ID=" + ms_id + " and MEMBER_TYPE=" + member_type + " and is_active=true)");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static boolean definedFeeExists(String year) {
		boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select exists(select * from defined_fee where FISCAL_YEAR='" + year + "')"));
			while(rs.next()) {
			result = rs.getBoolean("exists(select * from defined_fee where FISCAL_YEAR='" + year + "')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static boolean emailExists(PersonDTO p) {
		boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select exists(select * from email where P_ID=" + p.getP_id() + " and PRIMARY_USE=true) AS emailexists"));
			while(rs.next()) {
			result = rs.getBoolean("emailexists");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static boolean listedPhoneOfTypeExists(PersonDTO p, String type) {
		boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(SELECT * FROM phone WHERE P_ID=" + p.getP_id() + " AND PHONE_LISTED=true AND PHONE_TYPE='" + type + "') AS phoneexists"));
			while(rs.next()) {
			result = rs.getBoolean("phoneexists");
			}
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}

	public static boolean phoneOfTypeExists(String pid, String type) {
		boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(SELECT * FROM phone WHERE P_ID=" + pid + " AND PHONE_TYPE='" + type + "') AS phoneexists"));
			while(rs.next()) {
				result = rs.getBoolean("phoneexists");
			}
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static boolean fiscalRecordExists(MembershipListDTO ms, int year) {
		boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select Exists(select * FROM money where MS_ID=" + ms.getMsid() + " and FISCAL_YEAR='" + year + "');"));
			while(rs.next()) {
			result = rs.getBoolean("Exists(select * FROM money where MS_ID=" + ms.getMsid() + " and FISCAL_YEAR='" + year + "')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	
	
	//select exists(select * from person where MS_ID=229 and MEMBER_TYPE=2);
	public static Boolean slipExists(int ms_id) {
		boolean result = false;
		  // we must convert here (this is getting crazy!)
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from slip WHERE ms_id='" + ms_id + "')"));
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(select * from slip WHERE ms_id='" + ms_id + "')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static Boolean slipRentExists(int subMsid) {
		boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(
					Main.console.setRegexColor("SELECT EXISTS(select * from slip WHERE subleased_to='" + subMsid + "')"));
			while (rs.next()) {
				result = rs.getBoolean("EXISTS(select * from slip WHERE subleased_to='" + subMsid + "')");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static Boolean moneyExists(String year, MembershipDTO membership) {
		boolean result = false;
		  // we must convert here (this is getting crazy!)
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from money WHERE ms_id='" + membership.getMsid() + "' and fiscal_year='" + year + "')"));
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(select * from money WHERE ms_id='" + membership.getMsid() + "' and fiscal_year='" + year + "')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static Boolean moneyExists(int ms_id,String year) {
		boolean result = false;
		try {  
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select exists(select * from money where ms_id='"+ms_id+"' and fiscal_year='"+year+"');"));
			while (rs.next()) {
				result = rs.getBoolean(
						"exists(select * from money where ms_id='"+ms_id+"' and fiscal_year='"+year+"')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static Boolean moneyExists(int money_id) {
		boolean result = false;
		try {  
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select exists(select * from money where money_id=" + money_id + ")"));
			while (rs.next()) {
				result = rs.getBoolean(
						"exists(select * from money where money_id=" + money_id + ")");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	
	public static Boolean depositRecordExists(String year, int batch) {
		boolean result = false;
		  // we must convert here (this is getting crazy!)
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(SELECT * FROM deposit WHERE FISCAL_YEAR=" + year + " and BATCH=" + batch +");"));
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(SELECT * FROM deposit WHERE FISCAL_YEAR=" + year + " and BATCH="+ batch +")");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	
	public static Boolean isOfficer(PersonDTO per, int year) {
		boolean result = false;
		try {  
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from officer WHERE p_id='"
							+ per.getP_id() + "' AND off_year='" + year + "' and OFF_TYPE != 'BM')"));
			while (rs.next()) {
				result = rs.getBoolean(
						"EXISTS(select * from officer WHERE p_id='"
							+ per.getP_id() + "' AND off_year='" + year + "' and OFF_TYPE != 'BM')");
			}
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}

	
	public static Boolean workCreditExists(int money_id) {
		boolean result = false;
		try {  
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select exists(select * from work_credit where money_id=" + money_id + ");"));
			while (rs.next()) {
				result = rs.getBoolean(
						"exists(select * from work_credit where money_id=" + money_id + ")");
			}
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static Boolean currentMembershipIdExists(int ms_id) {
		boolean result = false;
		try {  
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select exists(select * from membership_id where fiscal_year='" + HalyardPaths.getYear() + "' and ms_id=" + ms_id + ")"));
			while (rs.next()) {
				result = rs.getBoolean(
						"exists(select * from membership_id where fiscal_year='" + HalyardPaths.getYear() + "' and ms_id=" + ms_id + ")");
			}
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static Boolean waitListExists(int ms_id) {
		boolean result = false;
		try {  
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select exists(select * from waitlist where ms_id="+ms_id+")"));
			while (rs.next()) {
				result = rs.getBoolean(
						"exists(select * from waitlist where ms_id="+ms_id+")");
			}
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static Boolean paidLate(MembershipListDTO r) {
		boolean result = false;
		try {  
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select exists(select * from membership_id where fiscal_year='" + r.getSelectedYear() + "' and MS_ID=" + r.getMsid() + " and LATE_RENEW=true)"));
			while (rs.next()) {
				result = rs.getBoolean(
						"exists(select * from membership_id where fiscal_year='" + r.getSelectedYear() + "' and MS_ID=" + r.getMsid() + " and LATE_RENEW=true)");
			}
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}

	public static Boolean personExistsByType(String msid, String type) {
		boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("SELECT EXISTS(SELECT * FROM person WHERE MS_ID=" + msid + " AND MEMBER_TYPE=" + type + ") AS personexist"));
			while (rs.next()) {
				result = rs.getBoolean(
						"personexist");
			}
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
}
