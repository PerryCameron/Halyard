package com.ecsail.sql;


import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.Halyard;
import com.ecsail.main.HalyardPaths;
import com.ecsail.structures.MembershipDTO;
import com.ecsail.structures.MembershipListDTO;
import com.ecsail.structures.PersonDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlExists {
	
	// this may be a duplicate, for instance it dosent need int pid, and why the inner join
	// this is used on BoxAddPerson only
	public static Boolean personExists(int type, int ms_id) {
		boolean answer = false;
		String query = "SELECT EXISTS(SELECT * FROM person INNER JOIN membership ON person.ms_id = membership.ms_id WHERE membership.ms_id ="
				+ ms_id + " AND person.member_type=" + type + ") AS personexists";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			rs.next();
		    answer = rs.getBoolean("personexists");
		Halyard.getConnect().closeResultSet(rs);
		}
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");

		}
		return answer;
	}
	
	public static Boolean paymentExists(int money_id) {
		boolean answer = false;
		String query = "SELECT EXISTS(SELECT * FROM payment WHERE MONEY_ID=" + money_id + ")";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			rs.next();
		    answer = rs.getBoolean("EXISTS(SELECT * FROM payment WHERE MONEY_ID=" + money_id + ")");
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if money record EXISTS","See below for details");
		}
		return answer;
	}

	public static Boolean memberTypeExists(int memberType, int msid) {
		boolean answer = false;
		String query = "SELECT EXISTS(SELECT P_ID FROM person WHERE member_type="+memberType+" AND ms_id="+msid+") as memberTypeExists";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			rs.next();
			answer = rs.getBoolean("memberTypeExists");
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if money record EXISTS","See below for details");
		}
		return answer;
	}
	
	public static Boolean paymentsExistForMembership(int ms_id) {
		boolean answer = false;
		String query = "SELECT EXISTS(SELECT * FROM money WHERE ms_id=" + ms_id + ") AS paymentexists";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			rs.next();
		    answer = rs.getBoolean("paymentexists");
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if money record EXISTS","See below for details");
		}
		return answer;
	}
	
	public static Boolean memoExists(int money_id, String category) {
		boolean answer = false;
		String query = "SELECT EXISTS(SELECT * FROM memo WHERE CATEGORY='" + category + "' AND MONEY_ID=" + money_id + ") AS memoExists";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			rs.next();
		    answer = rs.getBoolean("memoExists");
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return answer;
	}
	
	public static boolean memberShipExists(int ms_id) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM membership WHERE ms_id='" + ms_id + "')";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(SELECT * FROM membership WHERE ms_id='" + ms_id + "')");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to verify if a membership EXISTS","See below for details");
		}
		return result;
	}
	
	public static boolean memberShipIdExists(int ms_id) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM membership_id WHERE ms_id='" + ms_id + "')";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(SELECT * FROM membership_id WHERE ms_id='" + ms_id + "')");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to verify if a history record EXISTS","See below for details");
		}
		return result;
	}
	
	public static boolean membershipIdBlankRowExists(String msid) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM membership_id WHERE fiscal_year=0 AND MEMBERSHIP_ID=0 AND ms_id!="+msid+") AS newtuple";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("newtuple");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if a blank membership_id row EXISTS","See below for details");
		}
		return result;
	}
	
	public static boolean memberShipIdExists(int ms_id, String year) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM membership_id WHERE ms_id='" + ms_id + "' AND fiscal_year='" + year + "')";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(SELECT * FROM membership_id WHERE ms_id='" + ms_id + "' AND fiscal_year='" + year + "')");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static boolean activePersonExists(int ms_id, int member_type) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM person WHERE ms_id=" + ms_id + " AND member_type=" + member_type + " AND is_active=true)";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(SELECT * FROM person WHERE ms_id=" + ms_id + " AND member_type=" + member_type + " AND is_active=true)");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static boolean definedFeeExists(String year) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM defined_fee WHERE fiscal_year='" + year + "')";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(SELECT * FROM defined_fee WHERE fiscal_year='" + year + "')");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static boolean emailExists(PersonDTO p) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM email WHERE P_ID=" + p.getP_id() + " AND PRIMARY_USE=true) AS emailexists";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("emailexists");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static boolean listedPhoneOfTypeExists(PersonDTO p, String type) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM phone WHERE P_ID=" + p.getP_id() + " AND PHONE_LISTED=true AND PHONE_TYPE='" + type + "') AS phoneexists";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("phoneexists");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}

	public static boolean phoneOfTypeExists(String pid, String type) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM phone WHERE P_ID=" + pid + " AND PHONE_TYPE='" + type + "') AS phoneexists";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while(rs.next()) {
				result = rs.getBoolean("phoneexists");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static boolean fiscalRecordExists(MembershipListDTO ms, int year) {
		boolean result = false;
		String query = "SELECT Exists(SELECT * FROM money WHERE ms_id=" + ms.getMsid() + " AND fiscal_year='" + year + "')";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("Exists(SELECT * FROM money WHERE ms_id=" + ms.getMsid() + " AND fiscal_year='" + year + "')");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}

	//select exists(select * from person where MS_ID=229 and MEMBER_TYPE=2);
	public static Boolean slipExists(int ms_id) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM slip WHERE ms_id='" + ms_id + "')";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(SELECT * FROM slip WHERE ms_id='" + ms_id + "')");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static Boolean slipRentExists(int subMsid) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM slip WHERE subleased_to='" + subMsid + "')";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean("EXISTS(SELECT * FROM slip WHERE subleased_to='" + subMsid + "')");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static Boolean moneyExists(String year, MembershipDTO membership) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM money WHERE ms_id='" + membership.getMsid() + "' AND fiscal_year='" + year + "')";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(SELECT * FROM money WHERE ms_id='" + membership.getMsid() + "' AND fiscal_year='" + year + "')");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static Boolean moneyExists(int ms_id,String year) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM money WHERE ms_id='"+ms_id+"' AND fiscal_year='"+year+"')";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean(
						"EXISTS(SELECT * FROM money WHERE ms_id='"+ms_id+"' AND fiscal_year='"+year+"')");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static Boolean moneyExists(int money_id) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM money WHERE money_id=" + money_id + ")";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean(
						"EXISTS(SELECT * FROM money WHERE money_id=" + money_id + ")");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	
	public static Boolean depositRecordExists(String year, int batch) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM deposit WHERE fiscal_year=" + year + " AND BATCH=" + batch +")";
		  // we must convert here (this is getting crazy!)
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(SELECT * FROM deposit WHERE fiscal_year=" + year + " AND BATCH="+ batch +")");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	
	public static Boolean isOfficer(PersonDTO per, int year) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM officer WHERE p_id='"
				+ per.getP_id() + "' AND off_year='" + year + "' AND OFF_TYPE != 'BM')";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			rs.next();
				result = rs.getBoolean(
						"EXISTS(SELECT * FROM officer WHERE p_id='"
							+ per.getP_id() + "' AND off_year='" + year + "' AND OFF_TYPE != 'BM')");
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static Boolean workCreditExists(int money_id) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM work_credit WHERE money_id=" + money_id + ")";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean(
						"EXISTS(SELECT * FROM work_credit WHERE money_id=" + money_id + ")");
			}
		Halyard.getConnect().closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static Boolean currentMembershipIdExists(int ms_id) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM membership_id WHERE fiscal_year='" + HalyardPaths.getYear() + "' AND ms_id=" + ms_id + ")";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean(
						"EXISTS(SELECT * FROM membership_id WHERE fiscal_year='" + HalyardPaths.getYear() + "' AND ms_id=" + ms_id + ")");
			}
		Halyard.getConnect().closeResultSet(rs);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static Boolean waitListExists(int ms_id) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM waitlist WHERE ms_id="+ms_id+")";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean(
						"EXISTS(SELECT * FROM waitlist WHERE ms_id="+ms_id+")");
			}
		Halyard.getConnect().closeResultSet(rs);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static Boolean paidLate(MembershipListDTO r) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM membership_id WHERE fiscal_year='" + r.getSelectedYear() + "' AND ms_id=" + r.getMsid() + " AND late_renew=true)";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean(
						"EXISTS(SELECT * FROM membership_id WHERE fiscal_year='" + r.getSelectedYear() + "' AND ms_id=" + r.getMsid() + " AND late_renew=true)");
			}
		            Halyard.getConnect().closeResultSet(rs);Halyard.getConnect().closeResultSet(rs); } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}

	public static Boolean personExistsByType(String msid, String type) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM person WHERE ms_id=" + msid + " AND member_type=" + type + ") AS personexist";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean(
						"personexist");
			}
		            Halyard.getConnect().closeResultSet(rs); 
		Halyard.getConnect().closeResultSet(rs); } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
}
