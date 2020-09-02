package com.ecsail.main;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Money;
import com.ecsail.structures.Object_Person;
import com.ecsail.structures.Object_WorkCredit;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SqlUpdate {
	
	static Alert alert = new Alert(AlertType.ERROR);

	
	public static final void updateBoat(String field, int phone_id, String attribute) {
		try {			
			Statement stmt = ConnectDatabase.connection.createStatement();
			if(attribute != null)
			stmt.execute(Main.console.setRegexColor(
					"UPDATE boat SET " + field + "=\"" + attribute + "\" WHERE boat_id='" + phone_id + "';"));
			else
				stmt.execute(Main.console.setRegexColor(
					"UPDATE boat SET " + field + "=null WHERE boat_id='" + phone_id + "';"));	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final void updateBoat(int boat_id, Boolean hasTrailer) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console
					.setRegexColor("UPDATE boat SET has_trailer=" + hasTrailer + " WHERE boat_id='" + boat_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final void updateBoat(int boat_id, String keel) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE boat SET keel=\"" + keel + "\" WHERE boat_id='" + boat_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateAddress(String address,Object_MembershipList membership) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE membership SET address=\"" + address
					+ "\" WHERE ms_id='" + membership.getMsid() + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		membership.setAddress(address);
	}
	
	public static void updateCity(String city,Object_MembershipList membership) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE membership SET city=\"" + city
					+ "\" WHERE ms_id='" + membership.getMsid() + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		membership.setCity(city);
	}
	
	public static void updateState(String state,Object_MembershipList membership) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE membership SET state=\"" + state
				+ "\" WHERE ms_id='" + membership.getMsid() + "';"));
			membership.setState(state);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void updateZipcode(String zip,Object_MembershipList membership) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE membership SET zip=\"" + zip
					+ "\" WHERE ms_id='" + membership.getMsid() + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		membership.setZip(zip);
	}
	
	public static Boolean updateMembership(String field, int ms_id, String attribute) {
		Boolean noError = true;
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			if(attribute.equals("")) {
			stmt.execute(Main.console.setRegexColor(
						"UPDATE membership SET " + field + "= null WHERE ms_id='" + ms_id + "';"));	
			} else {
			stmt.execute(Main.console.setRegexColor(
					"UPDATE membership SET " + field + "=\"" + attribute + "\" WHERE ms_id='" + ms_id + "';"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			noError = false;
			e.printStackTrace();
		}
		return noError;
	}
	
	public static Boolean updateMembership(String field, int ms_id, LocalDate date) {
		Boolean noError = true;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor(
					"UPDATE membership SET " + field + "=\"" + date + "\" WHERE ms_id='" + ms_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			noError = false;
			e.printStackTrace();
		}
		return noError;
	}
	
	public static void updateMembership(Boolean boolean_value, int msid) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE membership SET ACTIVE_MEMBERSHIP=" + boolean_value.toString()
					+ " WHERE ms_id='" + msid + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateListed(String field, int phone_id, Boolean attribute) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor(
					"UPDATE phone SET " + field + "=" + attribute + " WHERE phone_id='" + phone_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updatePhone(String field, int phone_id, String attribute) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor(
					"UPDATE phone SET " + field + "=\"" + attribute + "\" WHERE phone_id='" + phone_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateEmail(String field, int email_id, Boolean attribute) {  // overload so compact
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor(
					"UPDATE email SET " + field + "=" + attribute + " WHERE email_id='" + email_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final void updateEmail(int email_id, String email) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE email SET email=\"" + email + "\" WHERE email_id='" + email_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateOfficer(String field, int officer_id, String attribute) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE officer SET " + field + "=\"" + attribute + "\" WHERE o_id='" + officer_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Duplicate");
			alert.setContentText("Duplicate entry!");
			alert.showAndWait();
		}
	}
	
	public static void updateBirthday(LocalDate date, Object_Person person) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE person SET birthday=\"" + date
					+ "\" WHERE p_id='" + person.getP_id() + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateBuisness(String buisness, Object_Person person ) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE person SET buisness=\"" + buisness
					+ "\" WHERE p_id='" + person.getP_id() + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateOccupation(String occupation, Object_Person person) {	
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE person SET occupation=\"" + occupation
					+ "\" WHERE p_id='" + person.getP_id() + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateLastName(String lname, Object_Person person)  { // Business
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE person SET l_name=\"" + lname
					+ "\" WHERE p_id='" + person.getP_id() + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateFirstName(String fname, Object_Person person) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE person SET f_name=\"" + fname
					+ "\" WHERE p_id='" + person.getP_id() + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updatePerson(String field, int p_id, Boolean attribute) { // updates active/inactive
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE person SET " + field + "=" + attribute + " WHERE p_id='" + p_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateSlip(int ms_id, Object_MembershipList membership) {  // ms_id in this case came from the text field and is converted from membership_id
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("update slip set subleased_to='" + ms_id + "' where ms_id='" + membership.getMsid() + "';"));
			membership.setSubleaser(ms_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void releaseSlip(Object_MembershipList membership) {  // this releases the slip using the slip owners ms_id
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("update slip set subleased_to=null where ms_id='" + membership.getMsid() + "';"));
			BoxConsole.setInfoLine("Released sublease for slip owner " + membership.getMsid(), "orange");
			membership.setSubleaser(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void subleaserReleaseSlip(int subleasee) {  // this releases the slip using the subleasee ms_id
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("update slip set subleased_to=null where subleased_to='" + subleasee + "';"));
			BoxConsole.setInfoLine("Released sublease for subleaser " + subleasee, "orange");
			Object_MembershipList ownerMembership = TabLauncher.getSubleaser(subleasee);
			ownerMembership.setSubleaser(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void reAssignSlip(int ms_id, Object_MembershipList membership) {  // this reassignes the slip using the subleasee ms_id (came from text field)
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("update slip set ms_id='" + ms_id + "' where ms_id='" + membership.getMsid() + "';"));
			String slip = membership.getSlip();
			membership.setSlip("0");
			Object_MembershipList newSlipOwnerMembership = TabLauncher.getMembership(ms_id);
			newSlipOwnerMembership.setSlip(slip);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final void commitFiscalRecord(int money_id, Boolean commit) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console
					.setRegexColor("UPDATE money SET commited=" + commit + " WHERE money_id='" + money_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final void updateField(int newValue, String table, String field, ObservableList<Object_Money> fiscals, int rowIndex)  {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE " + table + " SET " + field + "=\"" + newValue
					+ "\" WHERE money_id='" + fiscals.get(rowIndex).getMoney_id() + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final void updateMoney(Object_Money money) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE money SET extra_key=" 
					+ money.getExtra_key() 
					+ ",kayak_shed_key=" + money.getKayac_shed_key() 
					+ ",sail_loft_key=" + money.getSail_loft_key()
					+ ",sail_school_loft_key=" + money.getSail_school_loft_key()
					+ ",beach=" + money.getBeach()
					+ ",wet_slip=" + money.getWet_slip()
					+ ",kayak_rack=" + money.getKayac_rack()
					+ ",kayak_shed=" + money.getKayac_shed()
					+ ",sail_loft=" + money.getSail_loft()
					+ ",sail_school_laser_loft=" + money.getSail_school_laser_loft()
					+ ",winter_storage=" + money.getWinter_storage()
					+ ",ysc_donation=" + money.getYsc_donation() 
					+ ",paid=" + money.getPaid()
					+ ",total=" + money.getTotal()
					+ ",credit=" + money.getCredit()
					+ ",balance=" + money.getBalance()
					+ ",dues=" + money.getDues()
					+ " WHERE money_id=" + money.getMoney_id() + ";"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final void updateWorkCredit(Object_WorkCredit swcy)  {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE work_credit SET racing=" + swcy.getRacing()
					+ ",harbor=" + swcy.getHarbor() + ",social=" + swcy.getSocial() + ",other=" + swcy.getOther() 
					+ " WHERE money_id=" + swcy.getMoney_id() + ";"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final void updateMoney(int money_id, int batchNumber) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE money SET batch=\"" + batchNumber
					+ "\" WHERE money_id='" + money_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final void updateMoney(int money_id, Boolean closed) {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE money SET closed=" + closed
					+ " WHERE money_id='" + money_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateMemo(int memo_id, String field, String attribute)  {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE memo SET " + field + "=\"" + attribute + "\" WHERE memo_id='" + memo_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
