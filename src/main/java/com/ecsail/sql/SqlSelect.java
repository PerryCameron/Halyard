package com.ecsail.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.main.Paths;
import com.ecsail.pdf.directory.Object_SlipInfo;
import com.ecsail.pdf.directory.Object_Sportsmen;
import com.ecsail.pdf.directory.PDF_Object_Officer;
import com.ecsail.structures.Object_Award;
import com.ecsail.structures.Object_Board;
import com.ecsail.structures.Object_Boat;
import com.ecsail.structures.Object_BoatList;
import com.ecsail.structures.Object_BoatOwner;
import com.ecsail.structures.Object_DefinedFee;
import com.ecsail.structures.Object_Deposit;
import com.ecsail.structures.Object_Email;
import com.ecsail.structures.Object_Email_Information;
import com.ecsail.structures.Object_Membership;
import com.ecsail.structures.Object_MembershipId;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Memo;
import com.ecsail.structures.Object_Memo2;
import com.ecsail.structures.Object_Money;
import com.ecsail.structures.Object_Officer;
import com.ecsail.structures.Object_OfficerWithName;
import com.ecsail.structures.Object_PaidDues;
import com.ecsail.structures.Object_Payment;
import com.ecsail.structures.Object_Person;
import com.ecsail.structures.Object_Phone;
import com.ecsail.structures.Object_Slip;
import com.ecsail.structures.Object_Stats;
import com.ecsail.structures.Object_Temp;
import com.ecsail.structures.Object_WaitList;
import com.ecsail.structures.Object_WorkCredit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class SqlSelect {

	
	
	public static ArrayList<Object_OfficerWithName> getOfficersWithNames(String type) {
		ArrayList<Object_OfficerWithName> theseOfficers = new ArrayList<Object_OfficerWithName>();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select F_NAME,L_NAME,OFF_YEAR from officer o left join person p on o.P_ID=p.P_ID where OFF_TYPE='"+type+"'"));
			while (rs.next()) {
				theseOfficers.add(new Object_OfficerWithName(
						rs.getString("L_NAME"), 
						rs.getString("F_NAME"),
						rs.getString("OFF_YEAR")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return theseOfficers;
	}
	
	public static ArrayList<Object_Sportsmen> getSportsManAwardNames() {
		ArrayList<Object_Sportsmen> theseOfficers = new ArrayList<Object_Sportsmen>();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select AWARD_YEAR,F_NAME,L_Name from awards a left join person p on a.P_ID=p.P_ID"));
			while (rs.next()) {
				theseOfficers.add(new Object_Sportsmen(
						rs.getString("AWARD_YEAR"),
						rs.getString("F_NAME"),
						rs.getString("L_NAME") 
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return theseOfficers;
	}
	
	public static ObservableList<Object_WorkCredit> getWorkCredits() {
		ObservableList<Object_WorkCredit> thisWorkCredit = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisWorkCredit;
	}
	
	public static ObservableList<Object_Payment> getPayments() {
		ObservableList<Object_Payment> thisPayments = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from payment;"));
			while (rs.next()) {
				thisPayments.add(new Object_Payment(
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
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisPayments;
	}
	
	public static ObservableList<Object_Payment> getPayments(int money_id) {
		ObservableList<Object_Payment> thisPayments = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from payment where money_id=" + money_id));
			while (rs.next()) {
				thisPayments.add(new Object_Payment(
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
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisPayments;
	}
	
	public static Object_Payment getPayment(int money_id) {
		Object_Payment thisPayment = null;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from payment where money_id=" + money_id));
			while (rs.next()) {
				thisPayment = new Object_Payment(
						rs.getInt("PAY_ID"), 
						rs.getInt("MONEY_ID"), 
						rs.getString("CHECKNUMBER"),
						rs.getString("PAYMENT_TYPE"),
						rs.getString("PAYMENT_DATE"),
						rs.getString("AMOUNT"),
						rs.getInt("DEPOSIT_ID")
						);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisPayment;
	}
	
	public static ObservableList<Object_DefinedFee> getDefinedFees() {
		ObservableList<Object_DefinedFee> thisDefinedFee = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
						rs.getInt("KAYAK_SHED_KEY"),
						rs.getInt("WORK_CREDIT")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisDefinedFee;
	}
	
	public static ObservableList<Object_Deposit> getDeposits() {
		ObservableList<Object_Deposit> thisDeposits = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from deposit;"));
			while (rs.next()) {
				thisDeposits.add(new Object_Deposit(
						rs.getInt("DEPOSIT_ID"), 
						rs.getString("DEPOSIT_DATE"),
						rs.getString("FISCAL_YEAR"),
						rs.getInt("BATCH")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisDeposits;
	}
	
	public static Object_Deposit getDeposit(String year, int batch) {
		Object_Deposit thisDeposit = null;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from deposit where fiscal_year=" + year + " and batch=" + batch));
			while (rs.next()) {
				thisDeposit = new Object_Deposit(
						rs.getInt("DEPOSIT_ID"), 
						rs.getString("DEPOSIT_DATE"),
						rs.getString("FISCAL_YEAR"),
						rs.getInt("BATCH")
						);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisDeposit;
	}
	
	// can do this with a regular object but not properties for some reason
	public static void  updateDeposit(String year, int batch, Object_Deposit thisDeposit) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from deposit where fiscal_year=" + year + " and batch=" + batch));
			while (rs.next()) {
			thisDeposit.setDeposit_id(rs.getInt("DEPOSIT_ID"));
			thisDeposit.setDepositDate(rs.getString("DEPOSIT_DATE"));
			thisDeposit.setFiscalYear(rs.getString("FISCAL_YEAR"));
			thisDeposit.setBatch(rs.getInt("BATCH"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
	}
	
	// to create a single defined fee object and fille it with a selected year
	public static Object_DefinedFee getDefinedFee(String year) {
		Object_DefinedFee newDefinedFee = null;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
						rs.getInt("KAYAK_SHED_KEY"),
						rs.getInt("WORK_CREDIT")
						);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return newDefinedFee;
	}
	
	public static ObservableList<Object_Officer> getOfficers() {
		ObservableList<Object_Officer> thisOfficer = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisOfficer;
	}
	
	public static ArrayList<PDF_Object_Officer> getOfficersByYear(String selectedYear) {
		ArrayList<PDF_Object_Officer> officers = new ArrayList<PDF_Object_Officer>();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from officer o left join person p on o.P_ID=p.P_ID where OFF_YEAR=" + selectedYear));
			while (rs.next()) {
				officers.add(new PDF_Object_Officer(
						rs.getString("F_NAME"),
						rs.getString("L_NAME"),
						rs.getString("OFF_TYPE"),
						rs.getString("BOARD_YEAR"), // beginning of board term
						rs.getString("OFF_YEAR")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return officers;
	}
	
	public static ObservableList<Object_Officer> getOfficer(String field, int attribute) {  //p_id
		ObservableList<Object_Officer> thisOfficer = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisOfficer;
	}
	
	public static ObservableList<Object_Award> getAwards(Object_Person p) {  //p_id
		ObservableList<Object_Award> thisAwards = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select * from awards where P_ID=" + p.getP_id()));
			while (rs.next()) {
				thisAwards.add(new Object_Award(
						rs.getInt("AWARD_ID"), 
						rs.getInt("P_ID"), 
						rs.getString("AWARD_YEAR"),
						rs.getString("AWARD_TYPE")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisAwards;
	}
	
	public static ObservableList<Object_Email_Information> getEmailInfo() {
		ObservableList<Object_Email_Information> thisEmailInfo = FXCollections.observableArrayList();

		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
					"select id.MEMBERSHIP_ID,m.JOIN_DATE,p.L_NAME,p.F_NAME,EMAIL,PRIMARY_USE "
					+ "from email e "
					+ "inner join person p ON p.P_ID=e.P_ID "
					+ "inner join membership m ON m.ms_id=p.ms_id "
					+ "inner join membership_id id ON id.ms_id=m.ms_id "
					+ "where id.fiscal_year='" + Paths.getYear() 
					+ "' and id.renew=true"
					
					+ " order by id.MEMBERSHIP_ID"));

			while (rs.next()) {
				thisEmailInfo.add(new Object_Email_Information(rs.getInt("MEMBERSHIP_ID"), rs.getString("JOIN_DATE"),
						rs.getString("L_NAME"), rs.getString("F_NAME"), rs.getString("EMAIL"),
						rs.getBoolean("PRIMARY_USE")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisEmailInfo;
	}
	
	// select p.P_ID, p.MS_ID, o.O_ID, p.F_NAME, p.L_NAME, o.OFF_YEAR, o.BOARD_YEAR, o.OFF_TYPE  from person p inner join officer o on p.p_id = o.p_id where o.off_year='2020';
	public static ObservableList<Object_Board> getBoard(String currentYear) {  //p_id
		ObservableList<Object_Board> thisBoardMember = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisBoardMember;
	}
	
	public static ObservableList<Object_Money> getMonies(int ms_id) { // overload
		String query = "SELECT * FROM money";
		if(ms_id != 0)
		query += " WHERE ms_id=" + ms_id;
		ObservableList<Object_Money> theseFiscals = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return theseFiscals;
	}
	
	public static Object_Money getMonies(int ms_id, String fiscalYear) { // overload
		String query = "SELECT * FROM money WHERE ms_id=" + ms_id + " and fiscal_year=" + fiscalYear;
		Object_Money thisFiscal = null;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(query + ";"));
			while (rs.next()) {
				thisFiscal = new Object_Money(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),
						rs.getInt("FISCAL_YEAR"), rs.getInt("BATCH"), rs.getInt("OFFICER_CREDIT"), rs.getInt("EXTRA_KEY"),
						rs.getInt("KAYAK_SHED_KEY"), rs.getInt("SAIL_LOFT_KEY"), 
						rs.getInt("SAIL_SCHOOL_LOFT_KEY"), rs.getInt("BEACH"), 
						rs.getInt("WET_SLIP"), rs.getInt("KAYAK_RACK"), rs.getInt("KAYAK_SHED"), 
						rs.getInt("SAIL_LOFT"), rs.getInt("SAIL_SCHOOL_LASER_LOFT"), rs.getInt("WINTER_STORAGE"),
						rs.getInt("YSC_DONATION"),rs.getInt("PAID"),rs.getInt("TOTAL"),rs.getInt("CREDIT"),
						rs.getInt("BALANCE"), rs.getInt("DUES"),rs.getBoolean("COMMITED"),rs.getBoolean("CLOSED"),
						rs.getInt("OTHER"),rs.getInt("INITIATION"),rs.getBoolean("SUPPLEMENTAL"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisFiscal;
	}
	
	public static ObservableList<Object_PaidDues> getPaidDues(Object_Deposit currentDeposit) { 
		String query = "SELECT id.MEMBERSHIP_ID, mo.*, p.l_name, p.f_name FROM money mo "
				+ "INNER JOIN membership_id id on mo.MS_ID=id.MS_ID and mo.FISCAL_YEAR=id.FISCAL_YEAR "
				+ "INNER JOIN membership me on mo.MS_ID=me.MS_ID "
				+ "INNER JOIN person p ON me.P_ID=p.P_ID  WHERE mo.FISCAL_YEAR='" + currentDeposit.getFiscalYear() 
				+ "' AND mo.COMMITED=true AND mo.BATCH=" +currentDeposit.getBatch() + " "
				+ "ORDER BY id.MEMBERSHIP_ID";
		ObservableList<Object_PaidDues> theseFiscals = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return theseFiscals;
	}
	
	public static ObservableList<Object_PaidDues> getPaidDues(String selectedYear) { 
		String query = "SELECT id.MEMBERSHIP_ID, mo .*, p.l_name, p.f_name FROM money mo "
				+ "INNER JOIN membership_id id on mo.MS_ID=id.MS_ID and mo.FISCAL_YEAR=id.FISCAL_YEAR "
				+ "INNER JOIN membership me on mo.MS_ID=me.MS_ID "
				+ "INNER JOIN person p ON me.P_ID=p.P_ID WHERE mo.FISCAL_YEAR='" + selectedYear + "' AND mo.COMMITED=true ORDER BY id.MEMBERSHIP_ID";
		ObservableList<Object_PaidDues> theseFiscals = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return theseFiscals;
	}
	
	public static ObservableList<Object_PaidDues> getPaidDues(String selectedYear, int batch) { // overload
		            //  SELECT mo.*, id.MEMBERSHIP_ID, p.l_name, p.f_name from membership_id id INNER JOIN membership m ON m.MS_ID=id.MS_ID LEFT JOIN person p ON m.P_ID=p.P_ID INNER JOIN money mo ON mo.MS_ID=m.MS_ID Where id.FISCAL_YEAR='2019' AND mo.BATCH=2 AND mo.FISCAL_YEAR='2019';      
		String query = "SELECT mo.*, id.MEMBERSHIP_ID, p.l_name, p.f_name FROM membership_id id INNER JOIN membership m ON m.MS_ID=id.MS_ID LEFT JOIN person p ON m.P_ID=p.P_ID INNER JOIN money mo ON mo.MS_ID=m.MS_ID WHERE id.FISCAL_YEAR=" + selectedYear + " AND mo.BATCH=" + batch + " AND mo.FISCAL_YEAR=" + selectedYear;
		ObservableList<Object_PaidDues> theseFiscals = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return theseFiscals;
	}
	
	public static ObservableList<Object_Email> getEmail(int p_id) {
		String query = "SELECT * FROM email";
		if(p_id != 0)
			query += " WHERE p_id='" + p_id + "'";
		ObservableList<Object_Email> email = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return email;
	}	
	
	public static String getEmail(Object_Person person) {
		//System.out.println(person);
		Object_Email email = null;
		String returnEmail = "";
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from email where P_ID=" + person.getP_id() +" and PRIMARY_USE=true");
			while (rs.next()) {
				email = new Object_Email(
						rs.getInt("EMAIL_ID")
						,rs.getInt("P_ID")
						,rs.getBoolean("PRIMARY_USE")
						,rs.getString("EMAIL")
						,rs.getBoolean("EMAIL_LISTED"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		if(email.getEmail() != null) {
			returnEmail = email.getEmail();
		} 
		return returnEmail;
	}
	
	
	public static ObservableList<Object_Memo> getMemos(int ms_id) {
		String query = "SELECT * FROM memo";
		if(ms_id != 0)
			query +=  " WHERE ms_id='" + ms_id + "'";
		ObservableList<Object_Memo> theseMemos = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(query + ";");
			while (rs.next()) {
				theseMemos.add(new Object_Memo( // why do I keep gettin a nullpointer exception here?
						rs.getInt("MEMO_ID"), 
						rs.getInt("MS_ID"), 
						rs.getString("MEMO_DATE"), 
						rs.getString("MEMO"),
						rs.getInt("MONEY_ID"),
						rs.getString("CATEGORY")));
			}
			return theseMemos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return theseMemos;
	}
	
	public static ObservableList<Object_Memo2> getAllMemosForTabNotes(String year) {
		ObservableList<Object_Memo2> theseMemos = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("select * from memo \n"
					+ "left join membership_id id on memo.ms_id=id.ms_id\n"
					+ "where year(memo_date)='"+year+"' and id.fiscal_year='"+year+"';");
			while (rs.next()) {
				theseMemos.add(new Object_Memo2( // why do I keep gettin a nullpointer exception here?
						rs.getString("MEMBERSHIP_ID"),
						rs.getInt("MEMO_ID"), 
						rs.getInt("MS_ID"), 
						rs.getString("MEMO_DATE"), 
						rs.getString("MEMO"),
						rs.getInt("MONEY_ID"),
						rs.getString("CATEGORY")));
			}
			return theseMemos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return theseMemos;
	}
	
	public static Object_Memo getMemos(Object_PaidDues dues) {
		String query = "select * from memo where money_id=" + dues.getMoney_id();
		System.out.println("select * from memo where money_id=" + dues.getMoney_id());
		Object_Memo thisMemo = null;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(query + ";");
			while (rs.next()) {
				thisMemo = new Object_Memo( // why do I keep gettin a nullpointer exception here?
						rs.getInt("MEMO_ID"), 
						rs.getInt("MS_ID"), 
						rs.getString("MEMO_DATE"), 
						rs.getString("MEMO"),
						rs.getInt("MONEY_ID"),
						rs.getString("CATEGORY"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		//System.out.println(thisMemo);
		return thisMemo;
	}
	
	public static ObservableList<Object_Slip> getSlips() {
		ObservableList<Object_Slip> slips = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from slip;");
			while (rs.next()) {
				slips.add(new Object_Slip(rs.getInt("SLIP_ID")
						, rs.getInt("MS_ID")
						, rs.getString("SLIP_NUM"),
						rs.getInt("SUBLEASED_TO")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return slips;
	}
	
	public static ObservableList<Object_MembershipId> getIds() {
		ObservableList<Object_MembershipId> ids = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from membership_id;");
			while (rs.next()) {
				ids.add(new Object_MembershipId(
						rs.getInt("MID")
						, rs.getString("FISCAL_YEAR")
						, rs.getInt("MS_ID")
						, rs.getString("MEMBERSHIP_ID")
						, rs.getBoolean("RENEW")
						, rs.getString("MEM_TYPE")
						, rs.getBoolean("SELECTED")
						, rs.getBoolean("LATE_RENEW")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return ids;
	}
	
	public static ObservableList<Object_MembershipId> getIds(int ms_id) {
		ObservableList<Object_MembershipId> ids = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from membership_id where ms_id=" +ms_id + ";");
			while (rs.next()) {
				ids.add(new Object_MembershipId(
						rs.getInt("MID")
						, rs.getString("FISCAL_YEAR")
						, rs.getInt("MS_ID")
						, rs.getString("MEMBERSHIP_ID")
						, rs.getBoolean("RENEW")
						, rs.getString("MEM_TYPE")
						, rs.getBoolean("SELECTED")
						, rs.getBoolean("LATE_RENEW")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return ids;
	}
	
	public static String getId(int ms_id) {
		Object_MembershipId id = null;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from membership_id where ms_id=" +ms_id + ";");
			while (rs.next()) {
				id = new Object_MembershipId(
						rs.getInt("MID")
						, rs.getString("FISCAL_YEAR")
						, rs.getInt("MS_ID")
						, rs.getString("MEMBERSHIP_ID")
						, rs.getBoolean("RENEW")
						, rs.getString("MEM_TYPE")
						, rs.getBoolean("SELECTED")
						, rs.getBoolean("LATE_RENEW"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return id.getMembership_id();
	}
	
	// was a list
	public static ObservableList<Object_BoatOwner> getBoatOwners() {
		ObservableList<Object_BoatOwner> thisBoatOwner = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisBoatOwner;
	}
	
	public static ObservableList<Object_Boat> getBoats() {
		ObservableList<Object_Boat> thisBoat = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from boat;"));
			while (rs.next()) {
				thisBoat.add(new Object_Boat(
						rs.getInt("BOAT_ID"), 0, // because Object_Boat has an ms-id variable but database does not
						rs.getString("MANUFACTURER"), // might be the best note I have ever left ^^ lol
						rs.getString("MANUFACTURE_YEAR"), 
						rs.getString("REGISTRATION_NUM"), 
						rs.getString("MODEL"),
						rs.getString("BOAT_NAME"), 
						rs.getString("SAIL_NUMBER"), 
						rs.getBoolean("HAS_TRAILER"),
						rs.getString("LENGTH"), 
						rs.getString("WEIGHT"), 
						rs.getString("KEEL"),
						rs.getString("PHRF"), 
						rs.getString("DRAFT"), 
						rs.getString("BEAM"),
						rs.getString("LWL")));
						
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisBoat;
	}
	
	public static ObservableList<Object_BoatList> getBoatsWithOwners() {
		ObservableList<Object_BoatList> thisBoat = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(
					"select id.MEMBERSHIP_ID,id.MS_ID, p.L_NAME, p.F_NAME, "
					+ "b.* from boat b left join boat_owner bo on "
					+ "b.BOAT_ID=bo.BOAT_ID left join membership_id id "
					+ "on bo.MS_ID=id.MS_ID left join membership m on "
					+ "id.MS_ID=m.MS_ID left join person p on m.P_ID=p.P_ID "
					+ "where id.RENEW=true and id.FISCAL_YEAR='2021'"));
			while (rs.next()) {
				thisBoat.add(new Object_BoatList(
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
						rs.getString("PHRF"), 
						rs.getString("DRAFT"), 
						rs.getString("BEAM"),
						rs.getString("LWL"),
						rs.getInt("MEMBERSHIP_ID"),
						rs.getString("L_NAME"),
						rs.getString("F_NAME")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisBoat;
	}
	
	public static ArrayList<Integer> getBoatIds(int ms_id) {
		ArrayList<Integer> boats = new ArrayList<Integer>();	
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
		Statement stmt = ConnectDatabase.sqlConnection.createStatement();
		ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select b.BOAT_ID, bo.MS_ID, b.MANUFACTURER"
				+ ", b.MANUFACTURE_YEAR, b.REGISTRATION_NUM, b.MODEL, b.BOAT_NAME, b.SAIL_NUMBER"
				+ ", b.HAS_TRAILER, b.LENGTH, b.WEIGHT, b.KEEL, b.PHRF, b.DRAFT, b.BEAM, b.LWL from boat b inner join boat_owner bo using (boat_id) where ms_id='" + ms_id + "';"));
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
					rs.getString("PHRF"), 
					rs.getString("DRAFT"), 
					rs.getString("BEAM"),
					rs.getString("LWL")));
		}
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisBoat;
	}
	
	public static Object_Boat getBoatbyBoatId(int boat_id) { // overload but must be separate
		Object_Boat thisBoat = null;
		try {
		Statement stmt = ConnectDatabase.sqlConnection.createStatement();
		ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select b.BOAT_ID, bo.MS_ID, b.MANUFACTURER"
				+ ", b.MANUFACTURE_YEAR, b.REGISTRATION_NUM, b.MODEL, b.BOAT_NAME, b.SAIL_NUMBER"
				+ ", b.HAS_TRAILER, b.LENGTH, b.WEIGHT, b.KEEL, b.PHRF, b.DRAFT, b.BEAM, b.LWL from boat b inner join boat_owner bo using (boat_id) where boat_id='" + boat_id + "';"));
		while (rs.next()) {
			thisBoat = new Object_Boat(
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
					rs.getString("PHRF"), 
					rs.getString("DRAFT"), 
					rs.getString("BEAM"),
					rs.getString("LWL"));
		}
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisBoat;
	}
	
	public static List<Object_Boat> getBoatsForPdf() {
		List<Object_Boat> thisBoat = new ArrayList<Object_Boat>();

		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select b.BOAT_ID, bo.MS_ID, b.MANUFACTURER"
					+ ", b.MANUFACTURE_YEAR, b.REGISTRATION_NUM, b.MODEL, b.BOAT_NAME, b.SAIL_NUMBER"
					+ ", b.HAS_TRAILER, b.LENGTH, b.WEIGHT, b.KEEL, b.PHRF from boat b inner join boat_owner bo using (boat_id);"));
			while (rs.next()) {

				thisBoat.add(new Object_Boat(rs.getInt("BOAT_ID"), rs.getInt("MS_ID"), rs.getString("MANUFACTURER"),
						rs.getString("MANUFACTURE_YEAR"), rs.getString("REGISTRATION_NUM"), rs.getString("MODEL"),
						rs.getString("BOAT_NAME"), rs.getString("SAIL_NUMBER"), rs.getBoolean("HAS_TRAILER"),
						rs.getString("LENGTH"), rs.getString("WEIGHT"), rs.getString("KEEL"),
						rs.getString("PHRF"), 
						rs.getString("DRAFT"), 
						rs.getString("BEAM"),
						rs.getString("LWL")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}

		return thisBoat;
	}
	
	public static ObservableList<Object_Phone> getPhone(int p_id) {  // if p_id = 0 then select all
		String query = "select * from phone";
		if(p_id != 0)
			query += " WHERE p_id='" + p_id + "'";
		ObservableList<Object_Phone> thisPhone = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(query + ";"));
			while (rs.next()) {
				thisPhone.add(new Object_Phone(rs.getInt("PHONE_ID"), rs.getInt("P_ID"), rs.getBoolean("PHONE_LISTED"),
						rs.getString("PHONE"), rs.getString("PHONE_TYPE")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisPhone;
	}
	
	public static ArrayList<Object_Phone> getPhone(Object_Person p) {  // if p_id = 0 then select all
		ArrayList<Object_Phone> thisPhone = new ArrayList<Object_Phone>();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("SELECT * from phone Where P_ID=" + p.getP_id() + ""));
			while (rs.next()) {
				thisPhone.add(new Object_Phone(rs.getInt("PHONE_ID"), rs.getInt("P_ID"), rs.getBoolean("PHONE_LISTED"),
						rs.getString("PHONE"), rs.getString("PHONE_TYPE")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisPhone;
	}

	public static String getPhone(Object_Person p, String type) {  // if p_id = 0 then select all
		String phone = "";
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from phone where P_ID=" + p.getP_id() + " and PHONE_LISTED=true and PHONE_TYPE='" + type + "'"));
			rs.next();
			phone = rs.getString("PHONE");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return phone;
	}
	
	public static Object_DefinedFee selectDefinedFees(int year) {
		Statement stmt;
		Object_DefinedFee definedFee = null;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
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
						rs.getInt("KAYAK_SHED"), rs.getInt("kAYAK_SHED_KEY"), rs.getInt("WORK_CREDIT"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
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
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
					rs.getBoolean("IS_ACTIVE"),
					rs.getString("NICK_NAME")));
			}
		}
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thesepeople;
	}
	
	public static ArrayList<Object_Person> getDependants(Object_Membership m) {  
		String query = "SELECT * FROM person WHERE ms_id= '" + m.getMsid() + "' and MEMBER_TYPE=3";
		ArrayList<Object_Person> thesepeople = new ArrayList<Object_Person>();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
					rs.getBoolean("IS_ACTIVE"),
					rs.getString("NICK_NAME")));
			}
		}
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thesepeople;
	}
	
	public static ObservableList<Object_Person> getPeople() {  
		String query = "SELECT * FROM person";
		ObservableList<Object_Person> thesepeople = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
					rs.getBoolean("IS_ACTIVE"),
					rs.getString("NICK_NAME")));
		}
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thesepeople;
	}
	
	public static ObservableList<Object_MembershipId> getMembershipIds(String year) {
		ObservableList<Object_MembershipId> theseIds = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
		    ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from membership_id where fiscal_year=" + year));
		while (rs.next()) {
			theseIds.add(new Object_MembershipId(
					rs.getInt("MID"),
					rs.getString("FISCAL_YEAR"),
					rs.getInt("MS_ID"), 		
					rs.getString("MEMBERSHIP_ID"),
					rs.getBoolean("RENEW"),
					rs.getString("MEM_TYPE"),
					rs.getBoolean("SELECTED"),
				    rs.getBoolean("LATE_RENEW")));
		}
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return theseIds;
	}
	
	public static ArrayList<Object_Stats> getStatistics() {
		ArrayList<Object_Stats> stats = new ArrayList<Object_Stats>();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
		    ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from stats"));
		while (rs.next()) {
			stats.add(new Object_Stats(
					rs.getInt("STAT_ID"),
					rs.getInt("FISCAL_YEAR"),
					rs.getInt("ACTIVE_MEMBERSHIPS"),
					rs.getInt("NON_RENEW"),
					rs.getInt("RETURN_MEMBERS"),
					rs.getInt("NEW_MEMBERS"),
					rs.getInt("SECONDARY_MEMBERS"),
					rs.getInt("DEPENDANTS"),
					rs.getInt("NUMBER_OF_BOATS"),
					rs.getInt("FAMILY"),
					rs.getInt("REGULAR"),
					rs.getInt("SOCIAL"),
					rs.getInt("LAKEASSOCIATES"),
					rs.getInt("LIFEMEMBERS"),
					rs.getInt("RACEFELLOWS"),
					rs.getInt("STUDENT"),
					rs.getDouble("DEPOSITS"),
					rs.getDouble("INIATION")));
		}
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return stats;
	}
	
	public static Object_Person getPersonByPid(int pid) {  
		Object_Person person = null;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select * from person WHERE p_id= '" + pid + "';"));

			while (rs.next()) {
				person = (new Object_Person(rs.getInt("P_ID"), rs.getInt("MS_ID"), rs.getInt("MEMBER_TYPE"),
						rs.getString("F_NAME"), rs.getString("L_NAME"), rs.getString("BIRTHDAY"),
						rs.getString("OCCUPATION"), rs.getString("BUISNESS"), rs.getBoolean("IS_ACTIVE"),rs.getString("NICK_NAME")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		// System.out.println(thesepeople.toString());
		return person;
	}
	
	public static Object_Person getPerson(int ms_id, int member_type) {  
		Object_Person person = null;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select * from person where MS_ID=" + ms_id + " and MEMBER_TYPE=" + member_type));
			
			while (rs.next()) {
				person = (new Object_Person(rs.getInt("P_ID"), rs.getInt("MS_ID"), rs.getInt("MEMBER_TYPE"),
						rs.getString("F_NAME"), rs.getString("L_NAME"), rs.getString("BIRTHDAY"),
						rs.getString("OCCUPATION"), rs.getString("BUISNESS"), rs.getBoolean("IS_ACTIVE"),rs.getString("NICK_NAME")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		// System.out.println(thesepeople.toString());
		return person;
	}
	
	public static Object_Person getPersonFromMembershipID(String membershipId, String year) {  
		Object_Person person = null;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select * from person where MS_ID=(select ms_id from membership_id where MEMBERSHIP_ID="+membershipId+" and FISCAL_YEAR="+year+") and MEMBER_TYPE=1"));
			
			while (rs.next()) {
				person = (new Object_Person(rs.getInt("P_ID"), rs.getInt("MS_ID"), rs.getInt("MEMBER_TYPE"),
						rs.getString("F_NAME"), rs.getString("L_NAME"), rs.getString("BIRTHDAY"),
						rs.getString("OCCUPATION"), rs.getString("BUISNESS"), rs.getBoolean("IS_ACTIVE"),rs.getString("NICK_NAME")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		// System.out.println(thesepeople.toString());
		return person;
	}
	
	
	public static int getCount(String table, String column) {  // example-> "email","email_id"
		int result = 0;
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + table + " ORDER BY " + column + " DESC LIMIT 1");
			rs.next();
			result =  rs.getInt(column);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return result;
	}
	
	public static int getHighestMembershipId(String year) {  // example-> "email","email_id"
		int result = 0;
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery("select Max(membership_id) from membership_id where fiscal_year='" + year + "'");
			rs.next();
			result =  rs.getInt("Max(membership_id)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return result;
	}
	
	public static Object_MembershipId getCount(int ms_id) {  
		Object_MembershipId thisId = null; // new Object_MembershipId();
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery("select MID, MIN(FISCAL_YEAR), MS_ID, MAX(MEMBERSHIP_ID), RENEW from membership_id where MS_ID=" + ms_id);
			while (rs.next()) {
				thisId = new Object_MembershipId(
			rs.getInt("MID")
			, rs.getString("MIN(FISCAL_YEAR)")
			, rs.getInt("MS_ID")
			, rs.getString("MAX(MEMBERSHIP_ID)")
			, rs.getBoolean("RENEW")
			, rs.getString("MEM_TYPE")
			, rs.getBoolean("SELECTED")
			, rs.getBoolean("LATE_RENEW"));
			};
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisId;
	}
	
	public static int getCount(String type) { // gives the last memo_id number
		int result = 0;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("select * from money ORDER BY " + type + " DESC LIMIT 1;");
			boolean hasResult = rs.next();
			if (hasResult)
				result = rs.getInt(type);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return result;
	}
	
	
	public static Object_Slip getSlip(int ms_id) {
		Object_Slip thisSlip = null;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from slip WHERE ms_id='" + ms_id + "'");
			while (rs.next()) {
				thisSlip = new Object_Slip(rs.getInt("SLIP_ID")
						, rs.getInt("MS_ID")
						, rs.getString("SLIP_NUM"),
						rs.getInt("SUBLEASED_TO"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisSlip;
	}
	
	public static Object_Slip getSubleasedSlip(int ms_id) {
		Object_Slip thisSlip = null;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
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
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisSlip;
	}

	public static int getMembershipIDfromMsid(int msid)  {
		int result = 0;
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select membership_id from membership_id where ms_id='" + msid + "' and fiscal_year=" + Paths.getYear() +";"));
			while(rs.next()) {
			result = rs.getInt("MEMBERSHIP_ID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return result;
	}
	
	public static int getMsidFromMembershipID(int membership_id)  {
		int result = 0;
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select ms_id from membership_id where fiscal_year='" + Paths.getYear() + "' and membership_id='" + membership_id + "';"));
			while(rs.next()) {
			result = rs.getInt("ms_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return result;
	}
	
	public static Object_Officer getOfficer(int p_id, int i) {
		Object_Officer thisOfficer = null;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;

			rs = stmt.executeQuery(Main.console
					.setRegexColor("select * from officer WHERE p_id='" + p_id + "' and off_year='" + i + "';"));
			while (rs.next()) {
				thisOfficer = new Object_Officer(rs.getInt("O_ID"), rs.getInt("P_ID"), rs.getString("BOARD_YEAR"),
						rs.getString("OFF_TYPE"), rs.getString("OFF_YEAR"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisOfficer;
	}
	
	public static Object_WorkCredit getWorkCredit(int moneyID) {
		Statement stmt;
		Object_WorkCredit workCredits = null;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(
					Main.console.setRegexColor("select * from work_credit WHERE money_id='" + moneyID + "';"));
			// if(Main.consoleVerbose) ;
			while (rs.next()) {
				workCredits = new Object_WorkCredit(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),rs.getInt("RACING"), rs.getInt("HARBOR"),
						rs.getInt("SOCIAL"), rs.getInt("OTHER"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		// System.out.println(thisMembership.toString());
		return workCredits;
	}
	
	public static int getActiveMembershipCount(String year) {  // gives the last memo_id number
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("select count(*) from membership_id where fiscal_year='" + year + "' and renew=true;");
			rs.next();
			number = rs.getInt("count(*)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
	}
	
	public static int getMembershipTypeCount(String type) {  // gives the last memo_id number
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("select count(*) from membership m "
					+ "inner join membership_id id on m.ms_id=id.ms_id "
					+ "where id.fiscal_year='" + Paths.getYear() + "' and id.mem_type='" + type + "' and id.renew=true;");
			rs.next();
			number = rs.getInt("count(*)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
	}
	
	public static int getActivePeopleCount() {  // gives the last memo_id number
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("select count(*) from person p "
					+ "inner join membership m on m.ms_id=p.ms_id "
					+ "left join membership_id id on id.ms_id=m.ms_id "
					+ "where id.fiscal_year='" + Paths.getYear() + "' and id.renew=true");
			rs.next();
			number = rs.getInt("count(*)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
	}
	
	public static int getNumberOfNewMemberships(String year) {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) from membership WHERE JOIN_DATE >= '" + year + "-01-01' and MEMBERSHIP_ID is not NULL;");
			rs.next();
			number = rs.getInt("COUNT(*)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
	}
	
	public static int getNumberOfPayments() {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("select PAY_ID from payment ORDER BY pay_id DESC LIMIT 1");
			rs.next();
			number = rs.getInt("PAY_ID");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
	}

	public static boolean isCommitted(int money_id) {
		boolean committed = false;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("select commited from money where money_id=" + money_id + ";");
			rs.next();
			committed = rs.getBoolean("commited");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}	
		return committed;
	}
	
	public static int getBatchNumber(String year) {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("SELECT MAX(batch) FROM money WHERE commited=true and fiscal_year='"+year+"'");
			rs.next();
			number = rs.getInt("MAX(batch)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
	}

	public static String getPaymentDate(int moneyid) {
		String date = "";
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("select payment_date from payment where money_id=" + moneyid);
			rs.next();
			date = rs.getString("payment_date");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		
		return date;
	}
	
	
	public static int getMoneyCount(String column, int batch) {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("Select SUM(" + column + ") from money where commited=true AND batch=" + batch + ";");
			rs.next();
			number = rs.getInt("SUM(" + column + ")");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
	}
	
	public static int getMoneyCount(String column) {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("Select SUM(" + column + ") from money where commited=true;");
			rs.next();
			number = rs.getInt("SUM(" + column + ")");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
	}
	
	public static int getCount()  {  // gives the last memo_id number
		int number = 0;
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select * from person ORDER BY p_id DESC LIMIT 1"));
			rs.next();
			number = rs.getInt("P_ID");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
	}
	
	public static int getTotalAmount(int money_id) {
		int number = 0;
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select SUM(amount) from payment where money_id=" + money_id));
			rs.next();
			number = rs.getInt("SUM(amount)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
		
	}
	
	public static String getMembershipId(String year, int ms_id) {
		String id = "";
		Statement stmt;
		
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select membership_id from membership_id where fiscal_year='" + year + "' and ms_id='" + ms_id + "'"));
			if (rs.next() == false) {
				id = "none";
			} else {
				do {
					id = rs.getString("membership_id");
				} while (rs.next());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");		
		}
		//System.out.println("For year " + year +  " ms_id=" + ms_id + " they are " + id);
		return id;
		
	}
	
	public static boolean isRenewed(int ms_id, String year)  
	{
		boolean renew = false;
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select RENEW from membership_id where fiscal_year='" + year + "' and ms_id=" + ms_id));
			rs.next();
			renew = rs.getBoolean("RENEW");
		} catch (SQLException e) {
			System.out.println("membership id record does not exist for ms_id " + ms_id + " for year " + year);
			//new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return renew;	
	}
	
	public static int getNumberOfDeposits() {
		int number = 0;
		ResultSet rs;
		try { // select PAY_ID from payment ORDER BY pay_id DESC LIMIT 1
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("select deposit_id from deposit ORDER BY deposit_id DESC LIMIT 1;");
			rs.next();
			number = rs.getInt("deposit_id");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
	}
	
	public static int getNumberOfDepositBatches(String year) {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("select max(batch) from deposit where FISCAL_YEAR=" + year);
			rs.next();
			number = rs.getInt("max(batch)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
	}
	
	//////////  FOR CHARTS /////////////
	public static int getNumberOfActiveMembershipsForYear(int year) {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("select count(*) from membership_id where fiscal_year=" + year + " and renew=true");
			rs.next();
			number = rs.getInt("count(*)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
	}
	
	public static int getNumberOfInactiveMembershipsForYear(int year) {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("select count(*) from membership_id where fiscal_year=" + year + " and renew=false");
			rs.next();
			number = rs.getInt("count(*)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
	}
	
	public static int getNumberOfNewMembershipsForYear(int year) {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("select count(*) from membership where year(join_date) =" + year);
			rs.next();
			number = rs.getInt("count(*)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
	}
	
	/// not a pure SQL FUNCTION was having difficulties narrowing it down pure SQL.
	public static int getNumberOfReturningMembershipsForYear(int fiscalYear) {
		ObservableList<Object_MembershipList> rosters = Sql_SelectMembership.getReturnMembers(fiscalYear);
		int count = 0;
		for(Object_MembershipList r: rosters) {
			if(SqlExists.memberShipIdExists(r.getMsid(), ((fiscalYear - 1) + ""))) {
				if(!isActive(r.getMsid(), ((fiscalYear - 1) + "")));
				count++;
			} else {  // record doesn't exist
			count++;	
			}
		}
		return count;
	}
	
	public static boolean isActive(int ms_id, String year) {
		Boolean result = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("Select renew from membership_id where FISCAL_YEAR='"+year+"' and MS_ID='"+ms_id+"'"));
			while(rs.next()) {
			result = rs.getBoolean("renew");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return result;
	}
	
	public static int getMid(String year, int ms_id) {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("select mid from membership_id where MS_ID=" + ms_id + " and FISCAL_YEAR=" + year);
			rs.next();
			number = rs.getInt("MID");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
	}
	
	public static int getNonRenewNumber(String year) {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("select count(*) from membership_id where FISCAL_YEAR='" + year + "' and RENEW=false");
			rs.next();
			number = rs.getInt("count(*)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		//System.out.println(number);
		return number;
	}
	
	public static int getNumberOfMembersOfType(String type, int year) {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("select count(*) from membership_id where MEM_TYPE='" + type + "' and FISCAL_YEAR='" + year + "';");
			rs.next();
			number = rs.getInt("count(*)");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		//System.out.println(number);
		return number;
	}
	
	public static int getMSID(Object_Temp t,int year, ArrayList<String> errortuples) {
		int number = 0;
		ResultSet rs;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			rs = stmt.executeQuery("select ms_id from person where L_NAME='"+t.getLname()+"' and F_NAME='"+t.getFname()+"';");
			rs.next();
			number = rs.getInt("ms_id");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//System.out.println("The person is " + t.toString() );
			errortuples.add("Failed to find " + t.toString());
			//new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return number;
		
	}
	
	public static Object_WaitList getWaitList(int ms_id) {
		Object_WaitList thisWaitList = null;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from waitlist where ms_id=" + ms_id));
			while (rs.next()) {
				thisWaitList = new Object_WaitList(
						rs.getInt("MS_ID"), 
						rs.getBoolean("SLIPWAIT"),
						rs.getBoolean("KAYAKRACKWAIT"),
						rs.getBoolean("SHEDWAIT"),
						rs.getBoolean("WANTSUBLEASE"),
						rs.getBoolean("WANTRELEASE"),
						rs.getBoolean("WANTSLIPCHANGE")
						);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisWaitList;
	}
	
	public static ArrayList<Object_WaitList> getWaitLists() {
		ArrayList<Object_WaitList> thisWaitList = new ArrayList<Object_WaitList>();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from waitlist"));
			while (rs.next()) {
				thisWaitList.add(new Object_WaitList(
						rs.getInt("MS_ID"), 
						rs.getBoolean("SLIPWAIT"),
						rs.getBoolean("KAYAKRACKWAIT"),
						rs.getBoolean("SHEDWAIT"),
						rs.getBoolean("WANTSUBLEASE"),
						rs.getBoolean("WANTRELEASE"),
						rs.getBoolean("WANTSLIPCHANGE")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisWaitList;
	}

	public static ArrayList<Object_SlipInfo> getSlipsForDock(String dock) {
		ArrayList<Object_SlipInfo> thisSlipInfo = new ArrayList<Object_SlipInfo>();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select SLIP_NUM,SUBLEASED_TO,F_NAME,L_NAME  from slip s \n"
					+ "left join membership m on s.MS_ID=m.MS_ID \n"
					+ "left join person p on m.P_ID=p.P_ID \n"
					+ "where slip_num LIKE '" +dock + "%'"));
			while (rs.next()) {
				thisSlipInfo.add(new Object_SlipInfo(
						rs.getString("SLIP_NUM"), 
						rs.getInt("SUBLEASED_TO"),
						rs.getString("F_NAME"),
						rs.getString("L_NAME")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisSlipInfo;
	}

	public static ArrayList<Object_Award> getAwards() {
		ArrayList<Object_Award> theseAwards = new ArrayList<Object_Award>();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from awards"));
			while (rs.next()) {
				theseAwards.add(new Object_Award(
						rs.getInt("AWARD_ID"),
						rs.getInt("P_ID"), 
						rs.getString("AWARD_YEAR"),
						rs.getString("AWARD_TYPE")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return theseAwards;
	}
}
