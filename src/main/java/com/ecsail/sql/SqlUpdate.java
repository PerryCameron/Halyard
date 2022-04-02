package com.ecsail.sql;

import com.ecsail.gui.dialogues.Dialogue_CustomErrorMessage;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.BoxConsole;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.HalyardPaths;
import com.ecsail.main.Halyard;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.sql.select.SqlPerson;
import com.ecsail.structures.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;

public class SqlUpdate {
	
	static Alert alert = new Alert(AlertType.ERROR);

	
	public static void updateBoat(String field, int boat_id, String attribute) {
		try {			
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			if(attribute == null) 
				stmt.execute(Halyard.console.setRegexColor(
					"UPDATE boat SET " + field + "=null WHERE boat_id='" + boat_id + "';"));
			else if(attribute.equals("")) 
				stmt.execute(Halyard.console.setRegexColor(
						"UPDATE boat SET " + field + "=null WHERE boat_id='" + boat_id + "';"));
			else
			stmt.execute(Halyard.console.setRegexColor(
					"UPDATE boat SET " + field + "=\"" + attribute + "\" WHERE boat_id='" + boat_id + "';"));

			Halyard.edits.setBoatEdits(Halyard.edits.getBoatEdits() + 1);  // count number of edits.
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updateBoat(int boat_id, Boolean hasTrailer) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console
					.setRegexColor("UPDATE boat SET has_trailer=" + hasTrailer + " WHERE boat_id='" + boat_id + "';"));
			Halyard.edits.setBoatEdits(Halyard.edits.getBoatEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}

	public static void removePersonFromMembership(PersonDTO p) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute("update person set MS_ID=null, OLDMSID="+p.getMs_id()+" where P_ID=" + p.getP_id());
			Halyard.edits.setBoatEdits(Halyard.edits.getBoatEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}

	public static void updateBoat(int boat_id, String keel) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE boat SET keel=\"" + keel + "\" WHERE boat_id='" + boat_id + "';"));
			Halyard.edits.setBoatEdits(Halyard.edits.getBoatEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updateAddress(String address, MembershipListDTO membership) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE membership SET address=\"" + address
					+ "\" WHERE ms_id='" + membership.getMsid() + "';"));
			Halyard.edits.setMembershipEdits(Halyard.edits.getMembershipEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
		membership.setAddress(address);
	}
	
	public static void updateCity(String city, MembershipListDTO membership) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE membership SET city=\"" + city
					+ "\" WHERE ms_id='" + membership.getMsid() + "';"));
			Halyard.edits.setMembershipEdits(Halyard.edits.getMembershipEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
		membership.setCity(city);
	}
	
	public static void updateState(String state, MembershipListDTO membership) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE membership SET state=\"" + state
				+ "\" WHERE ms_id='" + membership.getMsid() + "';"));
			membership.setState(state);
			Halyard.edits.setMembershipEdits(Halyard.edits.getMembershipEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
		
	}
	
	public static void updateZipcode(String zip, MembershipListDTO membership) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE membership SET zip=\"" + zip
					+ "\" WHERE ms_id='" + membership.getMsid() + "';"));
			Halyard.edits.setMembershipEdits(Halyard.edits.getMembershipEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
		membership.setZip(zip);
	}

	public static Boolean updateMembership(int ms_id, String field, LocalDate date) {
		boolean noError = true;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor(
					"UPDATE membership SET " + field + "=\"" + date + "\" WHERE ms_id='" + ms_id + "';"));
			Halyard.edits.setMembershipEdits(Halyard.edits.getMembershipEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			noError = false;
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
		return noError;
	}
	
	public static Boolean updateDeposit(String field, int deposit_id, LocalDate date) {
		boolean noError = true;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor(
					"UPDATE deposit SET " + field + "=\"" + date + "\" WHERE deposit_id='" + deposit_id + "';"));
			Halyard.edits.setDepositsEdits(Halyard.edits.getDepositsEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			noError = false;
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
		return noError;
	}
	
	public static void updateListed(String field, int phone_id, Boolean attribute) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor(
					"UPDATE phone SET " + field + "=" + attribute + " WHERE phone_id='" + phone_id + "';"));
			Halyard.edits.setPhoneEdits(Halyard.edits.getPhoneEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}

	public static void updatePersonChangeMemberType(PersonDTO person, int newMemType) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor(
					"UPDATE person SET MEMBER_TYPE=" + newMemType + " WHERE P_ID=" + person.getP_id()));
			Halyard.edits.setPhoneEdits(Halyard.edits.getPhoneEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updateWaitList(int ms_id, String field, Boolean attribute) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor(
					"UPDATE waitlist SET " + field + "=" + attribute + " WHERE ms_id='" + ms_id + "';"));
			Halyard.edits.setPhoneEdits(Halyard.edits.getPhoneEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updatePhone(String field, int phone_id, String attribute) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor(
					"UPDATE phone SET " + field + "=\"" + attribute + "\" WHERE phone_id='" + phone_id + "';"));
			Halyard.edits.setPhoneEdits(Halyard.edits.getPhoneEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updateEmail(String field, int email_id, Boolean attribute) {  // overload so compact
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor(
					"UPDATE email SET " + field + "=" + attribute + " WHERE email_id='" + email_id + "';"));
			Halyard.edits.setEmailEdits(Halyard.edits.getEmailEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updateEmail(int email_id, String email) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE email SET email=\"" + email + "\" WHERE email_id='" + email_id + "';"));
			Halyard.edits.setEmailEdits(Halyard.edits.getEmailEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updateOfficer(String field, int officer_id, String attribute) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE officer SET " + field + "=\"" + attribute + "\" WHERE o_id='" + officer_id + "';"));
			Halyard.edits.setOfficersEdits(Halyard.edits.getOfficersEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Duplicate");
			alert.setContentText("Duplicate entry!");
			alert.showAndWait();
		}
	}
	
	public static void updateAward(String field, int awardId, String attribute) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE awards SET " + field + "=\"" + attribute + "\" WHERE award_id='" + awardId + "';"));
			Halyard.edits.setOfficersEdits(Halyard.edits.getOfficersEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Duplicate");
			alert.setContentText("Duplicate entry!");
			alert.showAndWait();
		}
	}
	
	public static void updateBirthday(LocalDate date, PersonDTO person) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE person SET birthday=\"" + date
					+ "\" WHERE p_id='" + person.getP_id() + "';"));
			Halyard.edits.setPeopleEdits(Halyard.edits.getPeopleEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updateNickName(String nname, PersonDTO person) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE person SET NICK_NAME=\"" + nname
					+ "\" WHERE p_id='" + person.getP_id() + "';"));
			Halyard.edits.setPeopleEdits(Halyard.edits.getPeopleEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to save nickname","");
		}
	}
	
	public static void updateBuisness(String buisness, PersonDTO person ) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE person SET buisness=\"" + buisness
					+ "\" WHERE p_id='" + person.getP_id() + "';"));
			Halyard.edits.setPeopleEdits(Halyard.edits.getPeopleEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updateOccupation(String occupation, PersonDTO person) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE person SET occupation=\"" + occupation
					+ "\" WHERE p_id='" + person.getP_id() + "';"));
			Halyard.edits.setPeopleEdits(Halyard.edits.getPeopleEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updateLastName(String lname, PersonDTO person)  { // Business
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE person SET l_name=\"" + lname
					+ "\" WHERE p_id='" + person.getP_id() + "';"));
			Halyard.edits.setPeopleEdits(Halyard.edits.getPeopleEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updateFirstName(String fname, PersonDTO person) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE person SET f_name=\"" + fname
					+ "\" WHERE p_id='" + person.getP_id() + "';"));
			Halyard.edits.setPeopleEdits(Halyard.edits.getPeopleEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updatePerson(String field, int p_id, Boolean attribute) { // updates active/inactive
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE person SET " + field + "=" + attribute + " WHERE p_id='" + p_id + "';"));
			Halyard.edits.setPeopleEdits(Halyard.edits.getPeopleEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}

	public static void updateSlip(int ms_id, MembershipListDTO membership) {  // ms_id in this case came from the text field and is converted from membership_id
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("update slip set subleased_to='" + ms_id + "' where ms_id='" + membership.getMsid() + "';"));
			membership.setSubleaser(ms_id);
			Halyard.edits.setSlipsEdits(Halyard.edits.getSlipsEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void releaseSlip(MembershipListDTO membership) {  // this releases the slip using the slip owners ms_id
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("update slip set subleased_to=null where ms_id='" + membership.getMsid() + "';"));
			BoxConsole.setInfoLine("Released sublease for slip owner " + membership.getMsid(), "orange");
			membership.setSubleaser(0);
			Halyard.edits.setSlipsEdits(Halyard.edits.getSlipsEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void subleaserReleaseSlip(int subleasee) {  // this releases the slip using the subleasee ms_id
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("update slip set subleased_to=null where subleased_to='" + subleasee + "';"));
			BoxConsole.setInfoLine("Released sublease for subleaser " + subleasee, "orange");
			MembershipListDTO ownerMembership = SqlMembershipList.getMembershipFromList(subleasee, HalyardPaths.getYear());
			ownerMembership.setSubleaser(0);
			Halyard.edits.setSlipsEdits(Halyard.edits.getSlipsEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void reAssignSlip(int ms_id, MembershipListDTO membership) {  // this reassignes the slip using the subleasee ms_id (came from text field)
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("update slip set ms_id='" + ms_id + "' where ms_id='" + membership.getMsid() + "';"));
			String slip = membership.getSlip();
			membership.setSlip("0");
			MembershipListDTO newSlipOwnerMembership = SqlMembershipList.getMembershipFromList(ms_id, HalyardPaths.getYear());
			newSlipOwnerMembership.setSlip(slip);
			Halyard.edits.setSlipsEdits(Halyard.edits.getSlipsEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void commitFiscalRecord(int money_id, Boolean commit) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console
					.setRegexColor("UPDATE money SET commited=" + commit + " WHERE money_id='" + money_id + "';"));
			Halyard.edits.setMoniesEdits(Halyard.edits.getMoniesEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}

	public static void updateMoney(MoneyDTO money) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			String query = "UPDATE money SET extra_key="
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
					+ ",other=" + money.getOther()
					+ ",initiation=" + money.getInitiation()
					+ ",work_credit=" + money.getWork_credit()
					+ ",other_credit=" + money.getOther_credit()
					+ ",kayak_beach_rack=" + money.getKayak_beach_rack()
					+ " WHERE money_id=" + money.getMoney_id() + ";";
			stmt.execute(Halyard.console.setRegexColor(query));
//			System.out.println(query);
			Halyard.edits.setMoniesEdits(Halyard.edits.getMoniesEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
		e.printStackTrace();
		new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updateWorkCredit(WorkCreditDTO swcy)  {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE work_credit SET racing=" + swcy.getRacing()
					+ ",harbor=" + swcy.getHarbor() + ",social=" + swcy.getSocial() + ",other=" + swcy.getOther() 
					+ " WHERE money_id=" + swcy.getMoney_id() + ";"));
			Halyard.edits.setWorkCreditsEdits(Halyard.edits.getWorkCreditsEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updateMoneyBatch(int money_id, int batchNumber) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE money SET batch=\"" + batchNumber
					+ "\" WHERE money_id='" + money_id + "';"));
			Halyard.edits.setMoniesEdits(Halyard.edits.getMoniesEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updateMoneyClosed(int money_id, Boolean closed) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE money SET closed=" + closed
					+ " WHERE money_id='" + money_id + "';"));
			Halyard.edits.setMoniesEdits(Halyard.edits.getMoniesEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updateMemo(int memo_id, String field, String attribute)  {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE memo SET " + field + "=\"" + attribute + "\" WHERE memo_id='" + memo_id + "';"));
			Halyard.edits.setMemosEdits(Halyard.edits.getMemosEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updatePayment(int pay_id, String field, String attribute) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE payment SET " + field + "=\"" + attribute + "\" WHERE pay_id='" + pay_id + "';"));
			Halyard.edits.setPaymentsEdits(Halyard.edits.getPaymentsEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
		
	}
	
	public static Boolean updateMembershipId(MembershipIdDTO thisId, String field, String attribute) {
		boolean noError = true;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE membership_id SET " + field + "=\"" + attribute + "\" WHERE mid=" + thisId.getMid()));
			Halyard.edits.setIdEdits(Halyard.edits.getIdEdits() + 1);
		} catch (SQLIntegrityConstraintViolationException IV) {
			PersonDTO accountHolder = SqlPerson.getPersonFromMembershipID(thisId.getMembership_id(), thisId.getFiscal_Year());
			String errorMessage = "The entry for the year " + thisId.getFiscal_Year() + " with a membership ID of " + thisId.getMembership_id() 
			+ " already exists for another member: " + accountHolder.getFname() + " " + accountHolder.getLname();
			new Dialogue_CustomErrorMessage(errorMessage, "Duplicate Entry");
				noError = false;
		} catch (SQLException e) {
				new Dialogue_ErrorSQL(e, "There was a problem with the Update", "");
		} catch (NullPointerException f) {
				new Dialogue_ErrorSQL(f, "Null pointer for MID="+thisId.getMid()+" membership ID=" + thisId.getMembership_id() + " Fiscal Year=" + thisId.getFiscal_Year(), "");
		}
		return noError;
	}

	public static Boolean updateAux(String boatId, Boolean value) {
		boolean noError = true;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("update boat set aux=" + value + " where BOAT_ID=" + boatId));
			Halyard.edits.setIdEdits(Halyard.edits.getBoatEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
			noError = false;
		}
		return noError;
	}
	
	public static Boolean updateMembershipId(int ms_id, int year, boolean value) {
		boolean noError = true;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("update membership_id set renew=" + value + " where fiscal_year='" + year + "' and ms_id='" + ms_id +"'"));
			Halyard.edits.setIdEdits(Halyard.edits.getIdEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
			noError = false;
		}
		return noError;
	}
	
	public static Boolean updateMembershipId(int mid, String field, Boolean attribute) {
		boolean noError = true;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE membership_id SET " + field + "=" + attribute + " WHERE mid=" + mid));
			Halyard.edits.setIdEdits(Halyard.edits.getIdEdits() + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
			noError = false;
		}
		return noError;
	}

	public static void updateFeeRecord(FeeDTO feeDTO) {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE fee SET FIELD_NAME='" + feeDTO.getFieldName()
					+ "', FIELD_VALUE=" + feeDTO.getFieldValue()
					+ ", FIELD_QTY=" + feeDTO.getFieldQuantity()
					+ ", FEE_YEAR=" + feeDTO.getFeeYear()
					+ ", DESCRIPTION='" + feeDTO.getDescription()
					+ "' WHERE FEE_ID=" + feeDTO.getFeeId()));
			Halyard.edits.setIdEdits(Halyard.edits.getIdEdits() + 1);
		} catch (SQLException e) {
			e.printStackTrace();
//			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}


	public static void updateDefinedFeeRecord(DefinedFeeDTO d) {
		String query;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			query = "UPDATE defined_fee SET " +
					"DUES_REGULAR=" + d.getDues_regular() + "," +
					"DUES_FAMILY=" + d.getDues_family() + "," +
					"DUES_LAKE_ASSOCIATE=" + d.getDues_lake_associate() + "," +
					"DUES_SOCIAL=" + d.getDues_social() + "," +
					"INITIATION=" + d.getInitiation() + "," +
					"WET_SLIP=" + d.getWet_slip() + "," +
					"BEACH=" + d.getBeach() + "," +
					"WINTER_STORAGE=" + d.getWinter_storage() + "," +
					"MAIN_GATE_KEY=" + d.getMain_gate_key() + "," +
					"SAIL_LOFT=" + d.getSail_loft() + "," +
					"SAIL_LOFT_KEY=" + d.getSail_loft_key() + "," +
					"SAIL_SCHOOL_LASER_LOFT=" + d.getSail_school_laser_loft() + "," +
					"SAIL_SCHOOL_LOFT_KEY=" + d.getSail_school_loft_key() + "," +
					"KAYAK_RACK=" + d.getKayak_rack()+ "," +
					"KAYAK_BEACH_RACK=" + d.getKayak_beach_rack() + "," +
					"KAYAK_SHED=" + d.getKayak_shed() + "," +
					"KAYAK_SHED_KEY=" + d.getKayak_shed_key() + "," +
					"WORK_CREDIT=" + d.getWork_credit() +
					" WHERE fiscal_year=" + d.getFiscal_year() + ";";
			stmt.execute(Halyard.console.setRegexColor(query));
			Halyard.edits.setDefinedFeesEdits(Halyard.edits.getDefinedFeesEdits() + 1);
		} catch (SQLException e) {
			e.printStackTrace();
			//			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
	
	public static void updateStatRecord(StatsDTO s)  {
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Halyard.console.setRegexColor("UPDATE stats SET " +
			"ACTIVE_MEMBERSHIPS=" + s.getActiveMemberships() + "," +
			"NON_RENEW=" + s.getNonRenewMemberships() + "," +
			"RETURN_MEMBERS=" + s.getReturnMemberships() + "," +
			"NEW_MEMBERS=" + s.getNewMemberships()+ "," +
			"SECONDARY_MEMBERS=" + s.getSecondaryMembers() + "," +
			"DEPENDANTS=" + s.getDependants() + "," +
			"NUMBER_OF_BOATS=" + s.getNumberOfBoats() + "," +
			"FAMILY=" + s.getFamily() + "," +
			"SOCIAL=" + s.getSocial() + "," +
			"LAKEASSOCIATES=" + s.getLakeAssociates() + "," +
			"LIFEMEMBERS=" + s.getLifeMembers() + "," +
			"RACEFELLOWS=" + s.getRaceFellows() + "," +
			"STUDENT=" + s.getStudent() + "," +
			"DEPOSITS=" + s.getDeposits() + "," +
			"INIATION="  + s.getInitiation() + 
			" WHERE FISCAL_YEAR='" + s.getFiscalYear() + "'"));
			Halyard.edits.setMemosEdits(Halyard.edits.getMemosEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
		}
	}
}
