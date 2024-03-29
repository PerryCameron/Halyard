package com.ecsail.sql;

import com.ecsail.gui.dialogues.Dialogue_CustomErrorMessage;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.HalyardPaths;
import com.ecsail.main.Halyard;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.sql.select.SqlPerson;
import com.ecsail.structures.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;

public class SqlUpdate {
	
	static Alert alert = new Alert(AlertType.ERROR);
	
	public static void updateBoat(String field, int boat_id, String attribute) {
		String query = "UPDATE boat SET " + field + "=null WHERE boat_id=" + boat_id;
		String query1 = "UPDATE boat SET " + field + "=\"" + attribute + "\" WHERE boat_id=" + boat_id;
		try {
			if(attribute == null || attribute.equals(""))
				Halyard.getConnect().executeQuery(query);
			else
				Halyard.getConnect().executeQuery(query1);
			Halyard.edits.setBoatEdits(Halyard.edits.getBoatEdits() + 1);  // count number of edits.
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateBoat(int boat_id, Boolean hasTrailer) {
		String query = "UPDATE boat SET has_trailer=" + hasTrailer + " WHERE boat_id=" + boat_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setBoatEdits(Halyard.edits.getBoatEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void removePersonFromMembership(PersonDTO p) {
		String query = "UPDATE person SET MS_ID=null, OLDMSID="+p.getMs_id()+" where P_ID=" + p.getP_id();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setBoatEdits(Halyard.edits.getBoatEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateBoat(int boat_id, String keel) {
		String query = "UPDATE boat SET keel='" + keel + "' WHERE boat_id=" + boat_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setBoatEdits(Halyard.edits.getBoatEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateAddress(MembershipListDTO membership) {
		String query = "UPDATE membership SET address='" + membership.getAddress()
				+ "' WHERE ms_id=" + membership.getMsid();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setMembershipEdits(Halyard.edits.getMembershipEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateCity(MembershipListDTO membership) {
		String query = "UPDATE membership SET city='" + membership.getCity()
				+ "' WHERE ms_id=" + membership.getMsid();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setMembershipEdits(Halyard.edits.getMembershipEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateState(MembershipListDTO membership) {
		String query = "UPDATE membership SET state='" + membership.getState()
				+ "' WHERE ms_id=" + membership.getMsid();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setMembershipEdits(Halyard.edits.getMembershipEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateZipcode(MembershipListDTO membership) {
		String query = "UPDATE membership SET zip='" + membership.getZip()
				+ "' WHERE ms_id=" + membership.getMsid();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setMembershipEdits(Halyard.edits.getMembershipEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateMembership(int ms_id, String field, LocalDate date) {
		String query = "UPDATE membership SET " + field + "='" + date + "' WHERE ms_id=" + ms_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setMembershipEdits(Halyard.edits.getMembershipEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateDeposit(String field, int deposit_id, LocalDate date) {
		String query = "UPDATE deposit SET " + field + "='" + date + "' WHERE deposit_id=" + deposit_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setDepositsEdits(Halyard.edits.getDepositsEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateListed(String field, int phone_id, Boolean attribute) {
		String query = "UPDATE phone SET " + field + "=" + attribute + " WHERE phone_id=" + phone_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setPhoneEdits(Halyard.edits.getPhoneEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updatePersonChangeMemberType(PersonDTO person, int newMemType) {
		String query = "UPDATE person SET MEMBER_TYPE=" + newMemType + " WHERE P_ID=" + person.getP_id();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setPhoneEdits(Halyard.edits.getPhoneEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateWaitList(int ms_id, String field, Boolean attribute) {
		String query = "UPDATE waitlist SET " + field + "=" + attribute + " WHERE ms_id=" + ms_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setPhoneEdits(Halyard.edits.getPhoneEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updatePhone(String field, int phone_id, String attribute) {
		String query = "UPDATE phone SET " + field + "='" + attribute + "' WHERE phone_id=" + phone_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setPhoneEdits(Halyard.edits.getPhoneEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateEmail(String field, int email_id, Boolean attribute) {  // overload so compact
		String query = "UPDATE email SET " + field + "=" + attribute + " WHERE email_id=" + email_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setEmailEdits(Halyard.edits.getEmailEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateEmail(int email_id, String email) {
		String query = "UPDATE email SET email='" + email + "' WHERE email_id=" + email_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setEmailEdits(Halyard.edits.getEmailEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateOfficer(String field, int officer_id, String attribute) {
		String query = "UPDATE officer SET " + field + "='" + attribute + "' WHERE o_id=" + officer_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setOfficersEdits(Halyard.edits.getOfficersEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			//new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Duplicate");
			alert.setContentText("Duplicate entry!");
			alert.showAndWait();
		}
	}
	
	public static void updateAward(String field, int awardId, String attribute) {
		String query = "UPDATE awards SET " + field + "='" + attribute + "' WHERE award_id=" + awardId;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setOfficersEdits(Halyard.edits.getOfficersEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			//new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Duplicate");
			alert.setContentText("Duplicate entry!");
			alert.showAndWait();
		}
	}
	
	public static void updateBirthday(LocalDate date, PersonDTO person) {
		String query = "UPDATE person SET birthday='" + date
				+ "' WHERE p_id=" + person.getP_id();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setPeopleEdits(Halyard.edits.getPeopleEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateNickName(String nname, PersonDTO person) {
		String query = "UPDATE person SET NICK_NAME='" + nname
				+ "' WHERE p_id=" + person.getP_id();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setPeopleEdits(Halyard.edits.getPeopleEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to save nickname","");
		}
	}
	
	public static void updateBuisness(String buisness, PersonDTO person ) {
		String query = "UPDATE person SET buisness='" + buisness
				+ "' WHERE p_id=" + person.getP_id();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setPeopleEdits(Halyard.edits.getPeopleEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateOccupation(String occupation, PersonDTO person) {
		String query = "UPDATE person SET occupation='" + occupation
				+ "' WHERE p_id=" + person.getP_id();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setPeopleEdits(Halyard.edits.getPeopleEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateLastName(String lname, PersonDTO person)  { // Business
		String query = "UPDATE person SET l_name='" + lname
				+ "' WHERE p_id=" + person.getP_id();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setPeopleEdits(Halyard.edits.getPeopleEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateFirstName(String fname, PersonDTO person) {
		String query = "UPDATE person SET f_name='" + fname
				+ "' WHERE p_id=" + person.getP_id();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setPeopleEdits(Halyard.edits.getPeopleEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updatePersonField(String field, int p_id, Boolean attribute) { // updates active/inactive
		String query = "UPDATE person SET " + field + "=" + attribute + " WHERE p_id=" + p_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setPeopleEdits(Halyard.edits.getPeopleEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updatePerson(PersonDTO p) { // updates active/inactive
		String query = "UPDATE person SET MEMBER_TYPE=" + p.getMemberType()
				+ ", MS_ID=" + p.getMs_id()
				+ ", F_NAME='" + p.getFname()
				+ "', L_NAME='" +p.getLname()
//					+ "', BIRTHDAY='" + p.getBirthday()
				+ "', OCCUPATION='" + p.getOccupation()
				+ "', BUISNESS='" + p.getBuisness()
				+ "', IS_ACTIVE=" + p.isActive()
				+ ", NICK_NAME='" + p.getNname()
				+ "' ,OLDMSID=" +  p.getOldMsid()
				+ " WHERE p_id=" + p.getP_id();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setPeopleEdits(Halyard.edits.getPeopleEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateSlip(int ms_id, MembershipListDTO membership) {  // ms_id in this case came from the text field and is converted from membership_id
		String query = "UPDATE slip SET subleased_to=" + ms_id + " where ms_id=" + membership.getMsid();
		try {
			Halyard.getConnect().executeQuery(query);
			membership.setSubleaser(ms_id);
			Halyard.edits.setSlipsEdits(Halyard.edits.getSlipsEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void releaseSlip(MembershipListDTO membership) {  // this releases the slip using the slip owners ms_id
		String query = "UPDATE slip SET subleased_to=null where ms_id=" + membership.getMsid();
		try {
			Halyard.getConnect().executeQuery(query);
			membership.setSubleaser(0);
			Halyard.edits.setSlipsEdits(Halyard.edits.getSlipsEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void subleaserReleaseSlip(int subleasee) {  // this releases the slip using the subleasee ms_id
		String query = "UPDATE slip SET subleased_to=null where subleased_to=" + subleasee;
		try {
			Halyard.getConnect().executeQuery(query);
			MembershipListDTO ownerMembership = SqlMembershipList.getMembershipFromList(subleasee, HalyardPaths.getYear());
			ownerMembership.setSubleaser(0);
			Halyard.edits.setSlipsEdits(Halyard.edits.getSlipsEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void reAssignSlip(int ms_id, MembershipListDTO membership) {  // this reassignes the slip using the subleasee ms_id (came from text field)
		String query = "UPDATE slip SET ms_id=" + ms_id + " where ms_id=" + membership.getMsid();
		try {
			Halyard.getConnect().executeQuery(query);
			String slip = membership.getSlip();
			membership.setSlip("0");
			MembershipListDTO newSlipOwnerMembership = SqlMembershipList.getMembershipFromList(ms_id, HalyardPaths.getYear());
			newSlipOwnerMembership.setSlip(slip);
			Halyard.edits.setSlipsEdits(Halyard.edits.getSlipsEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void commitFiscalRecord(int money_id, Boolean commit) {
		String query = "UPDATE money SET commited=" + commit + " WHERE money_id=" + money_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setMoniesEdits(Halyard.edits.getMoniesEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateMoney(MoneyDTO money) {
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
			+ " WHERE money_id=" + money.getMoney_id();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setMoniesEdits(Halyard.edits.getMoniesEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
		e.printStackTrace();
		new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateMoneyBatch(int money_id, int batchNumber) {
		String query = "UPDATE money SET batch=" + batchNumber
				+ " WHERE money_id=" + money_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setMoniesEdits(Halyard.edits.getMoniesEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateMoneyClosed(int money_id, Boolean closed) {
		String query = "UPDATE money SET closed=" + closed
				+ " WHERE money_id=" + money_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setMoniesEdits(Halyard.edits.getMoniesEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateMemo(int memo_id, String field, String attribute)  {
		String query = "UPDATE memo SET " + field + "='" + attribute + "' WHERE memo_id=" + memo_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setMemosEdits(Halyard.edits.getMemosEdits() + 1);  // update edits tracking
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updatePayment(int pay_id, String field, String attribute) {
		String query = "UPDATE payment SET " + field + "='" + attribute + "' WHERE pay_id=" + pay_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setPaymentsEdits(Halyard.edits.getPaymentsEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static Boolean updateMembershipId(MembershipIdDTO thisId, String field, String attribute) {
		boolean noError = true;
		String query = "UPDATE membership_id SET " + field + "='" + attribute + "' WHERE mid=" + thisId.getMid();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setIdEdits(Halyard.edits.getIdEdits() + 1);
		} catch (SQLIntegrityConstraintViolationException IV) {
			PersonDTO accountHolder = SqlPerson.getPersonFromMembershipID(thisId.getMembership_id(), thisId.getFiscal_Year());
			String errorMessage = "The entry for the year " + thisId.getFiscal_Year() + " with a membership ID of " + thisId.getMembership_id() 
			+ " already exists for another member: " + accountHolder.getFname() + " " + accountHolder.getLname();
			new Dialogue_CustomErrorMessage(errorMessage, "Duplicate Entry");
				noError = false;
		} catch (SQLException e) {
				new Dialogue_ErrorSQL(e, "There was a problem with the UPDATE", "");
		} catch (NullPointerException f) {
				new Dialogue_ErrorSQL(f, "Null pointer for MID="+thisId.getMid()+" membership ID=" + thisId.getMembership_id() + " Fiscal Year=" + thisId.getFiscal_Year(), "");
		}
		return noError;
	}

	public static void updateAux(String boatId, Boolean value) {
		String query = "UPDATE boat SET aux=" + value + " where BOAT_ID=" + boatId;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setIdEdits(Halyard.edits.getBoatEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateMembershipId(int ms_id, int year, boolean value) {
		String query = "UPDATE membership_id SET renew=" + value + " where fiscal_year='" + year + "' and ms_id=" + ms_id;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setIdEdits(Halyard.edits.getIdEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
	
	public static void updateMembershipId(int mid, String field, Boolean attribute) {
		String query = "UPDATE membership_id SET " + field + "=" + attribute + " WHERE mid=" + mid;
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setIdEdits(Halyard.edits.getIdEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateFeeRecord(FeeDTO feeDTO) {
		String query = "UPDATE fee SET FIELD_NAME='" + feeDTO.getFieldName()
				+ "', FIELD_VALUE=" + feeDTO.getFieldValue()
				+ ", FIELD_QTY=" + feeDTO.getFieldQuantity()
				+ ", FEE_YEAR=" + feeDTO.getFeeYear()
				+ ", DESCRIPTION='" + feeDTO.getDescription()
				+ "' WHERE FEE_ID=" + feeDTO.getFeeId();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setIdEdits(Halyard.edits.getIdEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateDefinedFeeRecord(DefinedFeeDTO d) {
		String query = "UPDATE defined_fee SET " +
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
				" WHERE fiscal_year=" + d.getFiscal_year();
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.edits.setDefinedFeesEdits(Halyard.edits.getDefinedFeesEdits() + 1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}
}
