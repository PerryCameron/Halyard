package com.ecsail.sql;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.main.HalyardPaths;
import com.ecsail.structures.Object_Membership;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Person;
import com.ecsail.structures.Object_Temp;

public class SqlExists {
	
	// this may be a duplicate, for instance it dosent need int pid, and why the inner join
	// this is used on BoxAddPerson only
	public static Boolean personExists(int pid, int type, int ms_id) {
		Boolean answer = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from person INNER JOIN membership ON person.MS_ID = membership.MS_ID where membership.MS_ID ='" 
						+ ms_id + "' AND person.MEMBER_TYPE='" + type + "');"));
			rs.next();
		    answer = rs.getBoolean("EXISTS(select * from person INNER JOIN membership ON person.MS_ID = membership.MS_ID where membership.MS_ID ='"
		    		    + ms_id +"' AND person.MEMBER_TYPE='" + type + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");

		}
		return answer;
	}
	
	public static Boolean paymentExists(int money_id) {
		Boolean answer = false;
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
		Boolean answer = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(SELECT * FROM money WHERE MS_ID=" + ms_id + ");"));
			rs.next();
		    answer = rs.getBoolean("EXISTS(SELECT * FROM money WHERE MS_ID=" + ms_id + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if money record exists","See below for details");
		}
		return answer;
	}
	
	public static Boolean memoExists(int money_id) {
		Boolean answer = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(SELECT * FROM memo WHERE MONEY_ID=" + money_id + ");"));
			rs.next();
		    answer = rs.getBoolean("EXISTS(SELECT * FROM memo WHERE MONEY_ID=" + money_id + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return answer;
	}
	
	public static Boolean statRecordExists(int fiscal_year) {
		Boolean recordExists = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(SELECT * FROM stats WHERE FISCAL_YEAR='" + fiscal_year + "')"));
			rs.next();
			recordExists = rs.getBoolean("EXISTS(SELECT * FROM stats WHERE FISCAL_YEAR='" + fiscal_year + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return recordExists;
	}
	
	public static boolean memberShipExists(int ms_id) {
		Boolean result = false;
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
		Boolean result = false;
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
	
	public static boolean membershipIdBlankRowExists() {
		
		Boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from membership_id where fiscal_year=0 and MEMBERSHIP_ID=0)"));
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(select * from membership_id where fiscal_year=0 and MEMBERSHIP_ID=0)");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if a blank membership_id row exists","See below for details");
		}
		return result;
	}
	
	public static boolean memberShipIdExists(int ms_id, String year) {
		Boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from membership_id WHERE ms_id='" + ms_id + "' and FISCAL_YEAR='" + year + "')"));
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(select * from membership_id WHERE ms_id='" + ms_id + "' and FISCAL_YEAR='" + year + "')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static boolean activePersonExists(int ms_id, int member_type) {
		Boolean result = false;
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
		Boolean result = false;
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
	
	public static boolean emailExists(Object_Person p) {
		Boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select exists(select * from email where P_ID=" + p.getP_id() + " and PRIMARY_USE=true);"));
			while(rs.next()) {
			result = rs.getBoolean("exists(select * from email where P_ID=" + p.getP_id() + " and PRIMARY_USE=true)");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static boolean cellPhoneExists(Object_Person p, String type) {
		Boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select exists(select * from phone where P_ID=" + p.getP_id() + " and PHONE_LISTED=true and PHONE_TYPE='" + type + "')"));
			while(rs.next()) {
			result = rs.getBoolean("exists(select * from phone where P_ID=" + p.getP_id() + " and PHONE_LISTED=true and PHONE_TYPE='" + type + "')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static boolean fiscalRecordExists(Object_MembershipList ms, int year) {
		Boolean result = false;
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
		Boolean result = false;
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
		Boolean result = false;
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
	
	public static Boolean ownsSlip(int ms_id) {
		Boolean result = false;
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
	
	public static Boolean subleasesSlip(int ms_id) {
		Boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT EXISTS(select * from slip WHERE subleased_to='" + ms_id + "')"));
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(select * from slip WHERE subleased_to='" + ms_id + "')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static Boolean moneyExists(String year, Object_Membership membership) {
		Boolean result = false;		
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
	
	
	public static Boolean ifDepositRecordExists(String year, int batch) {
		Boolean result = false;		
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
	
	
	public static Boolean isOfficer(Object_Person per,int year) {
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
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static Boolean isThere(Object_Temp t,int year) {
		boolean result = false;
		try {  
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select Exists(select * from membership_id mi join membership m on m.MS_ID=mi.MS_ID join person p on p.P_ID=m.P_ID where mi.fiscal_year="+year+" and p.L_NAME='"+t.getLname()+"' and p.F_NAME='"+t.getFname()+"');"));
			while (rs.next()) {
				result = rs.getBoolean(
						"Exists(select * from membership_id mi join membership m on m.MS_ID=mi.MS_ID join person p on p.P_ID=m.P_ID where mi.fiscal_year="+year+" and p.L_NAME='"+t.getLname()+"' and p.F_NAME='"+t.getFname()+"')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static Boolean memberExists(int ms_id, int type) {
		boolean result = false;
		try {  
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select exists(select * from person where ms_id="+ms_id+" and member_type="+type+")"));
			while (rs.next()) {
				result = rs.getBoolean(
						"exists(select * from person where ms_id="+ms_id+" and member_type="+type+")");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
	public static Boolean paidLate(Object_MembershipList r) {
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
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");
		}
		return result;
	}
	
}
