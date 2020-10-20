package com.ecsail.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ecsail.structures.Object_Board;
import com.ecsail.structures.Object_Boat;
import com.ecsail.structures.Object_BoatOwner;
import com.ecsail.structures.Object_DefinedFee;
import com.ecsail.structures.Object_Email;
import com.ecsail.structures.Object_Membership;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Memo;
import com.ecsail.structures.Object_Money;
import com.ecsail.structures.Object_Officer;
import com.ecsail.structures.Object_PaidDues;
import com.ecsail.structures.Object_Payment;
import com.ecsail.structures.Object_Person;
import com.ecsail.structures.Object_Phone;
import com.ecsail.structures.Object_Slip;
import com.ecsail.structures.Object_WorkCredit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class SqlSelect {

	
	public static ObservableList<Object_WorkCredit> getWorkCredits() {
		ObservableList<Object_WorkCredit> thisWorkCredit = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from work_credit;"));
			while (rs.next()) {
				thisWorkCredit.add(new Object_WorkCredit(
						rs.getInt("MONEY_ID"), 
						rs.getInt("MS_ID"), 
						rs.getInt("RACING"),
						rs.getInt("HARBOR"),
						rs.getInt("SOCIAL"),
						rs.getInt("OTHER")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisWorkCredit;
	}
	
	public static ObservableList<Object_Payment> getPayments() {
		ObservableList<Object_Payment> thisWorkCredit = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from payment;"));
			while (rs.next()) {
				thisWorkCredit.add(new Object_Payment(
						rs.getInt("PAY_ID"), 
						rs.getInt("MONEY_ID"), 
						rs.getString("CHECKNUMBER"),
						rs.getString("PAYMENT_TYPE"),
						rs.getString("PAYMENT_DATE"),
						rs.getString("AMOUNT"),
						rs.getInt("DEPOSIT_ID")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisWorkCredit;
	}
	
	public static ObservableList<Object_Payment> getPayments(int money_id) {
		ObservableList<Object_Payment> thisWorkCredit = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from payment where money_id=" + money_id));
			while (rs.next()) {
				thisWorkCredit.add(new Object_Payment(
						rs.getInt("PAY_ID"), 
						rs.getInt("MONEY_ID"), 
						rs.getString("CHECKNUMBER"),
						rs.getString("PAYMENT_TYPE"),
						rs.getString("PAYMENT_DATE"),
						rs.getString("AMOUNT"),
						rs.getInt("DEPOSIT_ID")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisWorkCredit;
	}
	
	public static ObservableList<Object_DefinedFee> getDefinedFees() {
		ObservableList<Object_DefinedFee> thisDefinedFee = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from defined_fee;"));
			while (rs.next()) {
				thisDefinedFee.add(new Object_DefinedFee(
						rs.getInt("FISCAL_YEAR"), 
						rs.getInt("DUES_REGULAR"),
						rs.getInt("DUES_FAMILY"),
						rs.getInt("DUES_LAKE_ASSOCIATE"),
						rs.getInt("DUES_SOCIAL"),
						rs.getInt("INITIATION"),
						rs.getInt("WET_SLIP"),
						rs.getInt("BEACH"),
						rs.getInt("WINTER_STORAGE"),
						rs.getInt("MAIN_GATE_KEY"),
						rs.getInt("SAIL_LOFT"),
						rs.getInt("SAIL_LOFT_KEY"),
						rs.getInt("SAIL_SCHOOL_LASER_LOFT"),
						rs.getInt("SAIL_SCHOOL_LOFT_KEY"),
						rs.getInt("KAYAK_RACK"),
						rs.getInt("KAYAK_SHED"),
						rs.getInt("KAYAK_SHED_KEY")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisDefinedFee;
	}
	
	// to create a single defined fee object and fille it with a selected year
	public static Object_DefinedFee getDefinedFee(String year) {
		Object_DefinedFee newDefinedFee = null;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("SELECT * FROM defined_fee WHERE fiscal_year='" + year + "';"));
			while (rs.next()) {
				newDefinedFee = new Object_DefinedFee(
						rs.getInt("FISCAL_YEAR"), 
						rs.getInt("DUES_REGULAR"),
						rs.getInt("DUES_FAMILY"),
						rs.getInt("DUES_LAKE_ASSOCIATE"),
						rs.getInt("DUES_SOCIAL"),
						rs.getInt("INITIATION"),
						rs.getInt("WET_SLIP"),
						rs.getInt("BEACH"),
						rs.getInt("WINTER_STORAGE"),
						rs.getInt("MAIN_GATE_KEY"),
						rs.getInt("SAIL_LOFT"),
						rs.getInt("SAIL_LOFT_KEY"),
						rs.getInt("SAIL_SCHOOL_LASER_LOFT"),
						rs.getInt("SAIL_SCHOOL_LOFT_KEY"),
						rs.getInt("KAYAK_RACK"),
						rs.getInt("KAYAK_SHED"),
						rs.getInt("KAYAK_SHED_KEY")
						);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDefinedFee;
	}
	
	// experimental in order to get a defined fee and update already existing object
	public static Object_DefinedFee getDefinedFee(String year, Object_DefinedFee thisDefinedFee) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("SELECT * FROM defined_fee WHERE fiscal_year='" + year + "';"));
			while (rs.next()) {
				thisDefinedFee.set(rs.getInt("FISCAL_YEAR"), rs.getInt("DUES_REGULAR"), rs.getInt("DUES_FAMILY"), 
						rs.getInt("DUES_LAKE_ASSOCIATE"), rs.getInt("DUES_SOCIAL"), rs.getInt("INITIATION"), 
						rs.getInt("WET_SLIP"), rs.getInt("BEACH"), rs.getInt("WINTER_STORAGE"), rs.getInt("MAIN_GATE_KEY"),
						rs.getInt("SAIL_LOFT"), rs.getInt("SAIL_LOFT_KEY"), rs.getInt("SAIL_SCHOOL_LASER_LOFT"), 
						rs.getInt("SAIL_SCHOOL_LOFT_KEY"), rs.getInt("KAYAK_RACK"), rs.getInt("KAYAK_SHED"), rs.getInt("KAYAK_SHED_KEY"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisDefinedFee;
	}
	
	public static ObservableList<Object_Officer> getOfficers() {
		ObservableList<Object_Officer> thisOfficer = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from officer;"));
			while (rs.next()) {
				thisOfficer.add(new Object_Officer(
						rs.getInt("O_ID"), 
						rs.getInt("P_ID"), 
						rs.getString("BOARD_YEAR"), // beginning of board term
						rs.getString("OFF_TYPE"), 
						rs.getString("OFF_YEAR")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisOfficer;
	}
	
	public static ObservableList<Object_Officer> getOfficer(String field, int attribute) {  //p_id
		ObservableList<Object_Officer> thisOfficer = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select * from officer WHERE " + field + "='" + attribute + "';"));
			while (rs.next()) {
				thisOfficer.add(new Object_Officer(
						rs.getInt("O_ID"), 
						rs.getInt("P_ID"), 
						rs.getString("BOARD_YEAR"),
						rs.getString("OFF_TYPE"), 
						rs.getString("OFF_YEAR")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisOfficer;
	}
	
	// select p.P_ID, p.MS_ID, o.O_ID, p.F_NAME, p.L_NAME, o.OFF_YEAR, o.BOARD_YEAR, o.OFF_TYPE  from person p inner join officer o on p.p_id = o.p_id where o.off_year='2020';
	public static ObservableList<Object_Board> getBoard(String currentYear) {  //p_id
		ObservableList<Object_Board> thisBoardMember = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select p.P_ID, p.MS_ID, o.O_ID, p.F_NAME, p.L_NAME, o.OFF_YEAR, o.BOARD_YEAR, o.OFF_TYPE  from person p inner join officer o on p.p_id = o.p_id where o.off_year='" + currentYear + "';"));

			while (rs.next()) {
				thisBoardMember.add(new Object_Board(
						rs.getInt("P_ID"),
						rs.getInt("MS_ID"), 
						rs.getInt("O_ID"), 
						rs.getString("F_NAME"),
						rs.getString("L_NAME"),
 						rs.getString("OFF_YEAR"),
						rs.getString("BOARD_YEAR"),
						rs.getString("OFF_TYPE")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisBoardMember;
	}
	
	public static ObservableList<Object_Money> getMonies(int ms_id) { // overload
		String query = "SELECT * FROM money";
		if(ms_id != 0)
		query += " WHERE ms_id=" + ms_id;
		ObservableList<Object_Money> theseFiscals = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(query + ";"));
			while (rs.next()) {
				theseFiscals.add(new Object_Money(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),
						rs.getInt("FISCAL_YEAR"), rs.getInt("BATCH"), rs.getInt("OFFICER_CREDIT"), rs.getInt("EXTRA_KEY"),
						rs.getInt("KAYAK_SHED_KEY"), rs.getInt("SAIL_LOFT_KEY"), 
						rs.getInt("SAIL_SCHOOL_LOFT_KEY"), rs.getInt("BEACH"), 
						rs.getInt("WET_SLIP"), rs.getInt("KAYAK_RACK"), rs.getInt("KAYAK_SHED"), 
						rs.getInt("SAIL_LOFT"), rs.getInt("SAIL_SCHOOL_LASER_LOFT"), rs.getInt("WINTER_STORAGE"),
						rs.getInt("YSC_DONATION"),rs.getInt("PAID"),rs.getInt("TOTAL"),rs.getInt("CREDIT"),
						rs.getInt("BALANCE"), rs.getInt("DUES"),rs.getBoolean("COMMITED"),rs.getBoolean("CLOSED"),
						rs.getInt("OTHER"),rs.getInt("INITIATION"),rs.getBoolean("SUPPLEMENTAL")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return theseFiscals;
	}
	
	public static ObservableList<Object_PaidDues> getPaidDues(String selectedYear) { // overload
		String query = "SELECT m.*, me.MEMBERSHIP_ID, p.l_name, p.f_name FROM money m INNER JOIN membership me on m.MS_ID=me.MS_ID INNER JOIN person p ON me.P_ID=p.P_ID WHERE m.FISCAL_YEAR=" + selectedYear + " AND m.COMMITED=true";
		ObservableList<Object_PaidDues> theseFiscals = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(query + ";"));
			while (rs.next()) {
				theseFiscals.add(new Object_PaidDues(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),
						rs.getInt("FISCAL_YEAR"), rs.getInt("BATCH"), rs.getInt("OFFICER_CREDIT"), rs.getInt("EXTRA_KEY"),
						rs.getInt("KAYAK_SHED_KEY"), rs.getInt("SAIL_LOFT_KEY"), 
						rs.getInt("SAIL_SCHOOL_LOFT_KEY"), rs.getInt("BEACH"), 
						rs.getInt("WET_SLIP"), rs.getInt("KAYAK_RACK"), rs.getInt("KAYAK_SHED"), 
						rs.getInt("SAIL_LOFT"), rs.getInt("SAIL_SCHOOL_LASER_LOFT"), rs.getInt("WINTER_STORAGE"),
						rs.getInt("YSC_DONATION"),rs.getInt("PAID"),rs.getInt("TOTAL"),rs.getInt("CREDIT"),
						rs.getInt("BALANCE"), rs.getInt("DUES"), rs.getBoolean("COMMITED"),rs.getBoolean("CLOSED"),
						rs.getInt("OTHER"),rs.getInt("INITIATION"),rs.getBoolean("SUPPLEMENTAL"), rs.getString("F_NAME"),
						rs.getString("L_NAME"), rs.getInt("MEMBERSHIP_ID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return theseFiscals;
	}
	
	public static ObservableList<Object_PaidDues> getPaidDues(String selectedYear, int batch) { // overload
		String query = "SELECT m.*, me.MEMBERSHIP_ID, p.l_name, p.f_name FROM money m INNER JOIN membership me on m.MS_ID=me.MS_ID INNER JOIN person p ON me.P_ID=p.P_ID WHERE m.FISCAL_YEAR=" + selectedYear + " AND m.BATCH=" + batch;
		ObservableList<Object_PaidDues> theseFiscals = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(query + ";"));
			while (rs.next()) {
				theseFiscals.add(new Object_PaidDues(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),
						rs.getInt("FISCAL_YEAR"), rs.getInt("BATCH"), rs.getInt("OFFICER_CREDIT"), rs.getInt("EXTRA_KEY"),
						rs.getInt("KAYAK_SHED_KEY"), rs.getInt("SAIL_LOFT_KEY"), 
						rs.getInt("SAIL_SCHOOL_LOFT_KEY"), rs.getInt("BEACH"), 
						rs.getInt("WET_SLIP"), rs.getInt("KAYAK_RACK"), rs.getInt("KAYAK_SHED"), 
						rs.getInt("SAIL_LOFT"), rs.getInt("SAIL_SCHOOL_LASER_LOFT"), rs.getInt("WINTER_STORAGE"),
						rs.getInt("YSC_DONATION"),rs.getInt("PAID"),rs.getInt("TOTAL"),rs.getInt("CREDIT"),
						rs.getInt("BALANCE"), rs.getInt("DUES"), rs.getBoolean("COMMITED"),rs.getBoolean("CLOSED"),
						rs.getInt("OTHER"),rs.getInt("INITIATION"),rs.getBoolean("SUPPLEMENTAL"), rs.getString("F_NAME"),
						rs.getString("L_NAME"), rs.getInt("MEMBERSHIP_ID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return theseFiscals;
	}
	
	public static ObservableList<Object_Email> getEmail(int p_id) {
		String query = "SELECT * FROM email";
		if(p_id != 0)
			query += " WHERE p_id='" + p_id + "'";
		ObservableList<Object_Email> email = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(query + ";");
			while (rs.next()) {
				email.add(new Object_Email(
						rs.getInt("EMAIL_ID")
						,rs.getInt("P_ID")
						,rs.getBoolean("PRIMARY_USE")
						,rs.getString("EMAIL")
						,rs.getBoolean("EMAIL_LISTED")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return email;
	}	
	
	
	public static ObservableList<Object_Memo> getMemos(int ms_id) {
		String query = "SELECT * FROM memo";
		if(ms_id != 0)
			query +=  " WHERE ms_id='" + ms_id + "'";
		ObservableList<Object_Memo> theseMemos = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(query + ";");
			while (rs.next()) {
				theseMemos.add(new Object_Memo( // why do I keep gettin a nullpointer exception here?
						rs.getInt("MEMO_ID"), 
						rs.getInt("MS_ID"), 
						rs.getString("MEMO_DATE"), 
						rs.getString("MEMO")));
			}
			return theseMemos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return theseMemos;
	}
	
	public static ObservableList<Object_Slip> getSlips() {
		ObservableList<Object_Slip> slips = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from slip;");
			while (rs.next()) {
				slips.add(new Object_Slip(rs.getInt("SLIP_ID")
						, rs.getInt("MS_ID")
						, rs.getString("SLIP_NUM"),
						rs.getInt("SUBLEASED_TO")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return slips;
	}
	
	// was a list
	public static ObservableList<Object_BoatOwner> getBoatOwners() {
		ObservableList<Object_BoatOwner> thisBoatOwner = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from boat_owner;"));
			while (rs.next()) {
				thisBoatOwner.add(new Object_BoatOwner(
						rs.getInt("MS_ID"), 
						rs.getInt("BOAT_ID")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisBoatOwner;
	}
	
	public static ObservableList<Object_Boat> getBoats() {
		ObservableList<Object_Boat> thisBoat = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from boat;"));
			while (rs.next()) {
				thisBoat.add(new Object_Boat(
						rs.getInt("BOAT_ID"), 0, // because Object_Boat has an ms-id variable but database does not
						rs.getString("MANUFACTURER"),
						rs.getString("MANUFACTURE_YEAR"), 
						rs.getString("REGISTRATION_NUM"), 
						rs.getString("MODEL"),
						rs.getString("BOAT_NAME"), 
						rs.getString("SAIL_NUMBER"), 
						rs.getBoolean("HAS_TRAILER"),
						rs.getString("LENGTH"), 
						rs.getString("WEIGHT"), 
						rs.getString("KEEL"),
						rs.getString("PHRF")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisBoat;
	}
	
	public static ArrayList<Integer> getBoatIds(int ms_id) {
		ArrayList<Integer> boats = new ArrayList<Integer>();	
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select bo.BOAT_ID from boat_owner bo inner join boat b on bo.BOAT_ID=b.BOAT_ID where ms_id='" + ms_id + "';"));

			while (rs.next()) {
			boats.add(rs.getInt("BOAT_ID"));
			}
		} catch (SQLException e) {
			
		}
		return boats;
	}
	
	public static List<Object_Boat> getBoats(int ms_id) { // overload but must be separate
		List<Object_Boat> thisBoat = new ArrayList<Object_Boat>();
		try {
		Statement stmt = ConnectDatabase.connection.createStatement();
		ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select b.BOAT_ID, bo.MS_ID, b.MANUFACTURER"
				+ ", b.MANUFACTURE_YEAR, b.REGISTRATION_NUM, b.MODEL, b.BOAT_NAME, b.SAIL_NUMBER"
				+ ", b.HAS_TRAILER, b.LENGTH, b.WEIGHT, b.KEEL, b.PHRF from boat b inner join boat_owner bo using (boat_id) where ms_id='" + ms_id + "';"));
		while (rs.next()) {
			thisBoat.add(new Object_Boat(
					rs.getInt("BOAT_ID"),
					rs.getInt("MS_ID"),
					rs.getString("MANUFACTURER"),
					rs.getString("MANUFACTURE_YEAR"),
					rs.getString("REGISTRATION_NUM"),
					rs.getString("MODEL"),
					rs.getString("BOAT_NAME"),
					rs.getString("SAIL_NUMBER"),
					rs.getBoolean("HAS_TRAILER"),
					rs.getString("LENGTH"),
					rs.getString("WEIGHT"),
					rs.getString("KEEL"),
					rs.getString("PHRF")));
		}
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisBoat;
	}
	
	public static List<Object_Boat> getBoatsForPdf() {
		List<Object_Boat> thisBoat = new ArrayList<Object_Boat>();

		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select b.BOAT_ID, bo.MS_ID, b.MANUFACTURER"
					+ ", b.MANUFACTURE_YEAR, b.REGISTRATION_NUM, b.MODEL, b.BOAT_NAME, b.SAIL_NUMBER"
					+ ", b.HAS_TRAILER, b.LENGTH, b.WEIGHT, b.KEEL, b.PHRF from boat b inner join boat_owner bo using (boat_id);"));
			while (rs.next()) {

				thisBoat.add(new Object_Boat(rs.getInt("BOAT_ID"), rs.getInt("MS_ID"), rs.getString("MANUFACTURER"),
						rs.getString("MANUFACTURE_YEAR"), rs.getString("REGISTRATION_NUM"), rs.getString("MODEL"),
						rs.getString("BOAT_NAME"), rs.getString("SAIL_NUMBER"), rs.getBoolean("HAS_TRAILER"),
						rs.getString("LENGTH"), rs.getString("WEIGHT"), rs.getString("KEEL"),
						rs.getString("PHRF")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return thisBoat;
	}
	
	public static ObservableList<Object_Phone> getPhone(int p_id) {  // if p_id = 0 then select all
		String query = "select * from phone";
		if(p_id != 0)
			query += " WHERE p_id='" + p_id + "'";
		ObservableList<Object_Phone> thisPhone = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(query + ";"));
			while (rs.next()) {
				thisPhone.add(new Object_Phone(rs.getInt("PHONE_ID"), rs.getInt("P_ID"), rs.getBoolean("PHONE_LISTED"),
						rs.getString("PHONE"), rs.getString("PHONE_TYPE")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisPhone;
	}

	public static ObservableList<Object_Membership> getMemberships() {
		ObservableList<Object_Membership> memberships = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from membership;"));
			while (rs.next()) {
				memberships.add(new Object_Membership(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getBoolean("ACTIVE_MEMBERSHIP"),
						rs.getString("MEM_TYPE"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memberships;
	}
	
	// Inserts selected membership into a single membership object  // don't think this is used
	public static Object_Membership getMembership(int ms_id) {
		Object_Membership thisMembership = null;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select * from membership WHERE ms_id= '" + ms_id + "';"));
			while (rs.next()) {
				thisMembership = new Object_Membership(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getBoolean("ACTIVE_MEMBERSHIP"),
						rs.getString("MEM_TYPE"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// System.out.println(thisMembership.toString());
		return thisMembership;
	}
	
	public static Object_MembershipList getMembershipFromList(int ms_id) {
		Object_MembershipList thisMembership = null;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
					"select ms_id,m.p_id,membership_id,join_date,active_membership," 
			+ "mem_type,slip_num,l_name,f_name,subleased_to,address,city,state,zip " 
			+ "from person p right join (Select * from slip right join membership using " 
			+ "(ms_id)) m using (ms_id) where member_type='1' and ms_id='" + ms_id + "';"));
			while (rs.next()) {
				thisMembership = new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getBoolean("ACTIVE_MEMBERSHIP"),
						rs.getString("MEM_TYPE"), 
						rs.getString("SLIP_NUM"), 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// System.out.println(thisMembership.toString());
		return thisMembership;
	}
	
	public static ObservableList<Object_MembershipList> getActiveMembershipList() {
		ObservableList<Object_MembershipList> theseactivememberships = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(
					"select ms_id,m.p_id,membership_id"
					+ ",join_date,active_membership,mem_type,slip_num,l_name,f_name,subleased_to,address,city,state,zip from "
					+ "person p right join ( Select * from slip right join membership using (ms_id) where "
					+ "active_membership=true order by membership_id) m using (ms_id) where member_type='1';"));
			while (rs.next()) {
				theseactivememberships.add(new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getBoolean("ACTIVE_MEMBERSHIP"),
						rs.getString("MEM_TYPE"), 
						rs.getString("SLIP_NUM"), 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Creating List of active membership objects...");
		return theseactivememberships;
	}
	
	public static Object_DefinedFee selectDefinedFees(int year) {
		Statement stmt;
		Object_DefinedFee definedFee = null;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(
					Main.console.setRegexColor("select * from defined_fee WHERE fiscal_year='" + year + "';"));
			// if(Main.consoleVerbose) ;
			while (rs.next()) {
				definedFee = new Object_DefinedFee(
						rs.getInt("FISCAL_YEAR"), rs.getInt("DUES_REGULAR"),
						rs.getInt("DUES_FAMILY"), rs.getInt("DUES_LAKE_ASSOCIATE"), rs.getInt("DUES_SOCIAL"),
						rs.getInt("INITIATION"), rs.getInt("WET_SLIP"), rs.getInt("BEACH"), rs.getInt("WINTER_STORAGE"),
						rs.getInt("MAIN_GATE_KEY"), rs.getInt("SAIL_LOFT"), rs.getInt("SAIL_LOFT_KEY"),
						rs.getInt("SAIL_SCHOOL_LASER_LOFT"), rs.getInt("SAIL_SCHOOL_LOFT_KEY"), rs.getInt("KAYAK_RACK"),
						rs.getInt("KAYAK_SHED"), rs.getInt("kAYAK_SHED_KEY"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(thisMembership.toString());
		return definedFee;
	}
	
	// "Boolean wantAll" decides whether we want all or just active people
	// inactive people exist and are still part of an account, but they are inactive for that account
	
	public static ObservableList<Object_Person> getPeople(int ms_id) {  
		String query = "SELECT * FROM person WHERE ms_id= '" + ms_id + "'";
		ObservableList<Object_Person> thesepeople = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
		    ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(query + ";"));
		while (rs.next()) {
			if(rs.getBoolean("IS_ACTIVE")) {  // only add active people
			thesepeople.add(new Object_Person(
					rs.getInt("P_ID"),
					rs.getInt("MS_ID"),
					rs.getInt("MEMBER_TYPE"), 		
					rs.getString("F_NAME"),
					rs.getString("L_NAME"),
					rs.getString("BIRTHDAY"),
					rs.getString("OCCUPATION"),
					rs.getString("BUISNESS"),
					rs.getBoolean("IS_ACTIVE")));
			}
		}
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thesepeople;
	}
	
	public static ObservableList<Object_Person> getPeople() {  
		String query = "SELECT * FROM person";
		ObservableList<Object_Person> thesepeople = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
		    ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(query + ";"));
		while (rs.next()) {
			thesepeople.add(new Object_Person(
					rs.getInt("P_ID"),
					rs.getInt("MS_ID"),
					rs.getInt("MEMBER_TYPE"), 		
					rs.getString("F_NAME"),
					rs.getString("L_NAME"),
					rs.getString("BIRTHDAY"),
					rs.getString("OCCUPATION"),
					rs.getString("BUISNESS"),
					rs.getBoolean("IS_ACTIVE")));
		}
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thesepeople;
	}
	
	public static Object_Person getPerson(int pid) {  // nothing calling this
		Object_Person person = null;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select * from person WHERE p_id= '" + pid + "';"));

			while (rs.next()) {
				person = (new Object_Person(rs.getInt("P_ID"), rs.getInt("MS_ID"), rs.getInt("MEMBER_TYPE"),
						rs.getString("F_NAME"), rs.getString("L_NAME"), rs.getString("BIRTHDAY"),
						rs.getString("OCCUPATION"), rs.getString("BUISNESS"), rs.getBoolean("IS_ACTIVE")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(thesepeople.toString());
		return person;
	}
	
	
	public static int getCount(String table, String column) {  // example-> "email","email_id"
		int result = 0;
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + table + " ORDER BY " + column + " DESC LIMIT 1");
			rs.next();
			result =  rs.getInt(column);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static int getCount(String type) { // gives the last memo_id number
		int result = 0;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("select * from money ORDER BY " + type + " DESC LIMIT 1;");
			boolean hasResult = rs.next();
			if (hasResult)
				result = rs.getInt(type);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static Object_Slip getSlip(int ms_id) {
		Object_Slip thisSlip = null;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from slip WHERE ms_id='" + ms_id + "'");
			while (rs.next()) {
				thisSlip = new Object_Slip(rs.getInt("SLIP_ID")
						, rs.getInt("MS_ID")
						, rs.getString("SLIP_NUM"),
						rs.getInt("SUBLEASED_TO"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisSlip;
	}
	
	public static Object_Slip getSubleasedSlip(int ms_id) {
		Object_Slip thisSlip = null;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from slip WHERE subleased_to='" + ms_id + "'");
			while (rs.next()) {
				thisSlip = new Object_Slip(
						rs.getInt("SLIP_ID"), 
						rs.getInt("MS_ID"), 
						rs.getString("SLIP_NUM"),
						rs.getInt("SUBLEASED_TO"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisSlip;
	}
	
	public static int getMembershipIDfromMsid(int msid)  {
		int result = 0;
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select membership_id from membership where ms_id='" + msid + "';"));
			while(rs.next()) {
			result = rs.getInt("MEMBERSHIP_ID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static int getMsidFromMembershipID(int membership_id)  {
		int result = 0;
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select ms_id from membership where membership_id='" + membership_id + "';"));
			while(rs.next()) {
			result = rs.getInt("MS_ID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static Object_Officer getOfficer(int p_id, int i) {
		Object_Officer thisOfficer = null;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;

			rs = stmt.executeQuery(Main.console
					.setRegexColor("select * from officer WHERE p_id='" + p_id + "' and off_year='" + i + "';"));
			while (rs.next()) {
				thisOfficer = new Object_Officer(rs.getInt("O_ID"), rs.getInt("P_ID"), rs.getString("BOARD_YEAR"),
						rs.getString("OFF_TYPE"), rs.getString("OFF_YEAR"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisOfficer;
	}
	
	public static Object_WorkCredit getWorkCredit(int moneyID) {
		Statement stmt;
		Object_WorkCredit workCredits = null;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(
					Main.console.setRegexColor("select * from work_credit WHERE money_id='" + moneyID + "';"));
			// if(Main.consoleVerbose) ;
			while (rs.next()) {
				workCredits = new Object_WorkCredit(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),rs.getInt("RACING"), rs.getInt("HARBOR"),
						rs.getInt("SOCIAL"), rs.getInt("OTHER"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(thisMembership.toString());
		return workCredits;
	}
	
	public static int getActiveMembershipCount() {  // gives the last memo_id number
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			rs = stmt.executeQuery("select count(*) from membership where active_membership = true");
			rs.next();
			number = rs.getInt("count(*)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	
	//
	
	public static int getActivePeopleCount() {  // gives the last memo_id number
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			rs = stmt.executeQuery("select count(*) from membership m inner join person p on m.ms_id = p.ms_id where m.ACTIVE_MEMBERSHIP=true;");
			rs.next();
			number = rs.getInt("count(*)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	
	public static int getNonMembershipRenewalCount(String year) {  // gives the last memo_id number
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			rs = stmt.executeQuery("SELECT (SELECT Count(MS_ID) from membership "
					+ "where ACTIVE_MEMBERSHIP=true) - (SELECT Count(m.MS_ID) from membership "
					+ "m right join money mo on mo.MS_ID=m.MS_ID where mo.commited=true and "
					+ "mo.fiscal_year='2020' and m.ACTIVE_MEMBERSHIP=true and mo.SUPPLEMENTAL=false) AS INCREASE;");
			rs.next();
			number = rs.getInt("INCREASE");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	
	public static int getNumberOfNewMemberships(String year) {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) from membership WHERE JOIN_DATE >= '" + year + "-01-01' and MEMBERSHIP_ID is not NULL;");
			rs.next();
			number = rs.getInt("COUNT(*)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	
	public static int getNumberOfPayments() {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(PAY_ID) from payment;");
			rs.next();
			number = rs.getInt("COUNT(PAY_ID)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	
	public static int getMSIDCount() {  // gives the last memo_id number
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			rs = stmt.executeQuery("select ms_id from membership ORDER BY ms_id DESC LIMIT 1");
			rs.next();
			number = rs.getInt("ms_id");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}

	public static boolean isCommitted(int money_id) {
		boolean committed = false;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			rs = stmt.executeQuery("select commited from money where money_id=" + money_id + ";");
			rs.next();
			committed = rs.getBoolean("commited");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return committed;
	}
	
	public static int getBatchNumber() {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			rs = stmt.executeQuery("SELECT MAX(batch) FROM money WHERE commited=true");
			rs.next();
			number = rs.getInt("MAX(batch)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number + 1;
	}
	
	public static int getMembershipIDCount() {  // gives the last memo_id number
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			rs = stmt.executeQuery("SELECT MAX(membership_id) FROM membership;");
			rs.next();
			number = rs.getInt("MAX(membership_id)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	
	public static int getMoneyCount(String column, int batch) {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			rs = stmt.executeQuery("Select SUM(" + column + ") from money where commited=true AND batch=" + batch + ";");
			rs.next();
			number = rs.getInt("SUM(" + column + ")");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	
	public static int getMoneyCount(String column) {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			rs = stmt.executeQuery("Select SUM(" + column + ") from money where commited=true;");
			rs.next();
			number = rs.getInt("SUM(" + column + ")");
		
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
	
	public static int getTotalAmount(int money_id) {
		int number = 0;
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select SUM(amount) from payment where money_id=" + money_id));
			rs.next();
			number = rs.getInt("SUM(amount)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
		
	}
	
	public static boolean isActive(int ms_id)
	{
		boolean active = false;
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select active_membership from membership where ms_id=" + ms_id));
			rs.next();
			active = rs.getBoolean("active_membership");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return active;	
	}
}
