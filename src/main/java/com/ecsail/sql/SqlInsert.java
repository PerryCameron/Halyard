package com.ecsail.sql;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.Halyard;
import com.ecsail.main.SqlScriptMaker;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.*;

import java.sql.SQLException;

public class SqlInsert {
	
	///////////////  CLASS OF STATIC PURE FUNCTIONS /////////////////////////////
	
	// add phone record
	public static boolean addPhoneRecord(int phone_id, int pid , Boolean listed, String phone, String type) {
		boolean noError = false;
		String query = "INSERT INTO phone () VALUES (" + phone_id + "," + pid + ",'" + phone + "','" + type + "'," + listed + ")";
		try {
			Halyard.getConnect().executeQuery(query);
			noError = true;
		
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
		return noError;  // return true if insert performed without error
	}
	
	// add email record
	public static boolean addEmailRecord(int email_id, int pid, Boolean primary, String email, Boolean listed) {
		boolean noError = false;
		String query = "INSERT INTO email () VALUES (" + email_id + "," + pid + ","
				+ primary + ",'" + email + "'," + listed + ")";
		try {
			Halyard.getConnect().executeQuery(query);
			noError = true;
		 }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
		return noError;  // return true if insert performed without error
	}
	
	public static boolean addOfficerRecord(int officer_id, int pid , String board_year, String officer, int year) {
		boolean noError = false;
		String query = "INSERT INTO officer () VALUES (" + officer_id + "," + pid + "," + board_year + ",\"" + officer + "\"," + year + ")";
		try {
			Halyard.getConnect().executeQuery(query);
			noError = true;
		 }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
		return noError;  // return true if insert performed without error
	}
	
	public static void addPaymentRecord(PaymentDTO op) {
		String query = "INSERT INTO payment () VALUES (" + op.getPay_id() + ","
				+ op.getMoney_id() + "," + op.getCheckNumber() + ",'" + op.getPaymentType() + "','"
				+ op.getPaymentDate() + "','" + op.getPaymentAmount() + "','" + op.getDeposit_id() + "')";
		try {
		Halyard.getConnect().executeQuery(query);
		 }
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addAwardRecord(AwardDTO a) {
		String query = "INSERT INTO awards () VALUES (" + a.getAwardId() + ","
				+ a.getPid() + ",'" + a.getAwardYear() + "','" + a.getAwardType() + "')";
		try {
			Halyard.getConnect().executeQuery(query);
		} 
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static boolean addBoatRecord(BoatDTO b, int msid) {
		boolean noError = false;
		String query = "INSERT INTO boat () VALUES (" + b.getBoat_id() + ",null,null,null,null,null,null,true,null,null,null,null,null,null,null,false)";
		String query1 = "INSERT INTO boat_owner () VALUES (" + msid + "," + b.getBoat_id() + ")";
		try {
			Halyard.getConnect().executeQuery(query);
			Halyard.getConnect().executeQuery(query1);
			noError = true;
		 } 
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
		return noError;  // return true if insert performed without error
	}
	
	public static void addPersonRecord(PersonDTO person) {
		String query = "INSERT INTO person () VALUES ("
				+ person.getP_id() + "," + person.getMs_id() + ",'" + person.getMemberType() + "','" + person.getFname()
				+ "','" + person.getLname() + "'," + SqlScriptMaker.getCorrectString(person.getBirthday())
				+ ",'" + person.getOccupation() + "','" + person.getBuisness() +"',true,"+person.getNname()+",'"+person.getNname()+"',"+person.getOldMsid()+")";
		try {
			
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addWorkCreditRecord(int moneyId, MembershipDTO membership) {
		String query = "INSERT INTO work_credit () VALUES ("
				+ moneyId + ","
				+ membership.getMsid() + ",0,0,0,0)";
		try {
			Halyard.getConnect().executeQuery(query);
		 } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addMoneyRecord(MoneyDTO m) {
		String query = "INSERT INTO money () VALUES ("
				+ m.getMoney_id() + "," + m.getMs_id() + "," + m.getFiscal_year() + "," + m.getBatch()
				+ "," + m.getOfficer_credit() + "," + m.getExtra_key() + "," + m.getKayac_shed_key()
				+ "," + m.getSail_loft_key() + "," + m.getSail_school_loft_key() + "," + m.getBeach()
				+ "," + m.getWet_slip() + "," + m.getKayac_rack() + "," + m.getKayac_shed()
				+ "," + m.getSail_loft() + "," + m.getSail_school_laser_loft() + "," + m.getWinter_storage()
				+ "," + m.getYsc_donation() + "," + m.getPaid() + "," + m.getTotal()
				+ "," + m.getCredit() + "," + m.getBalance() + "," + m.getDues()
				+  "," + m.isCommitted() + "," + m.isClosed() + "," + m.getOther() + ","
				+ m.getInitiation() + "," + m.isSupplemental() + "," + m.getWork_credit()
				+ "," + m.getOther_credit() + "," + m.getKayak_beach_rack() + ")";
		try {
			Halyard.getConnect().executeQuery(query);
		 } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addDefinedFeeRecord(DefinedFeeDTO d) {
		String query = "INSERT INTO defined_fee () VALUES ("
				+ d.getFiscal_year() + "," + d.getDues_regular() + "," + d.getDues_family() + "," + d.getDues_lake_associate()
				+ "," + d.getDues_social() + "," + d.getInitiation() + "," + d.getWet_slip() + "," + d.getBeach()
				+ "," + d.getWinter_storage() + "," + d.getMain_gate_key() + "," + d.getSail_loft() + "," + d.getSail_loft_key()
				+ "," + d.getSail_school_laser_loft() + "," + d.getSail_school_loft_key() + "," + d.getKayak_rack()
				+ "," + d.getKayak_shed() + "," + d.getKayak_shed_key() + "," + d.getWork_credit()
				+")";
		try {
			Halyard.getConnect().executeQuery(query);
		 } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static boolean addMembershipIsSucessful(MembershipListDTO nm) {
		boolean updateIsSucessful = false;
		String query = "INSERT INTO membership () VALUES (" + nm.getMsid() + ",'" + nm.getPid() + "','" + nm.getJoinDate() + "','FM','','','IN','')";
		try {
			Halyard.getConnect().executeQuery(query);
			updateIsSucessful = true;
		 } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
		return updateIsSucessful;
	}
	
	public static void addMemo(MemoDTO m) {
		String query = "INSERT INTO memo () VALUES (" + m.getMemo_id() + "," + m.getMsid() + ",'" + m.getMemo_date() + "','" + m.getMemo() + "',"
				+ m.getMoney_id() + ",'" + m.getCategory() + "');";
		try {
			Halyard.getConnect().executeQuery(query);
		 } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addDeposit(DepositDTO d) {
		String query = "INSERT INTO deposit () VALUES (" + d.getDeposit_id() + ",'" + d.getDepositDate() + "','" + d.getFiscalYear() + "'," + d.getBatch() + ");";
		try {
			Halyard.getConnect().executeQuery(query);
		 } catch (SQLException e) {
		e.printStackTrace();
		}
	}
	
	public static void addMembershipId(MembershipIdDTO id) {
		String query = "INSERT INTO membership_id () VALUES (" + id.getMid() + ",'" + id.getFiscal_Year() + "','" + id.getMs_id()
				+ "'," + id.getMembership_id() + "," + id.isRenew() + ",'" + id.getMem_type()+ "'," + id.isSelected() + "," + id.isLateRenew() + ")";
		try {
			Halyard.getConnect().executeQuery(query);
		 } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addWaitList(WaitListDTO w) {
		String query = "INSERT INTO waitlist () VALUES ("
				+ w.getMs_id() + ","
				+ w.isSlipWait() + ","
				+ w.isKayakWait() + ","
				+ w.isShedWait() + ","
				+ w.isWantToSublease() + ","
				+ w.isWantsRelease() + ","
				+ w.isWantSlipChange() + ")";
		try {
			Halyard.getConnect().executeQuery(query);
		 } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addStatRecord(StatsDTO s) {
		String query = "INSERT INTO stats () VALUES ("
				+ s.getStatId() + ","
				+ s.getFiscalYear() + ","
				+ s.getActiveMemberships() + ","
				+ s.getNonRenewMemberships() + ","
				+ s.getReturnMemberships() + ","
				+ s.getNewMemberships() + ","
				+ s.getSecondaryMembers() + ","
				+ s.getDependants() + ","
				+ s.getNumberOfBoats() + ","
				+ s.getFamily() + ","
				+ s.getRegular() + ","
				+ s.getSocial() + ","
				+ s.getLakeAssociates() + ","
				+ s.getLifeMembers() + ","
				+ s.getRaceFellows() + ","
				+ s.getStudent() + ","
				+ s.getDeposits() + ","
				+ s.getInitiation() + ")";
		try {
			Halyard.getConnect().executeQuery(query);
		 } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addBoatOwner(int boat_id,int ms_id) {
		String query = "INSERT INTO boat_owner () VALUES (" + ms_id + "," + boat_id +")";
		try {
			Halyard.getConnect().executeQuery(query);
		 } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}

	public static void addNewFee(FeeDTO feeDTO) {
		String query = "INSERT INTO fee () VALUES (" + feeDTO.getFeeId() + ",'" + feeDTO.getFieldName() + "'," + feeDTO.getFieldValue()
				+ "," + feeDTO.getFieldQuantity() + "," + feeDTO.getFeeYear() + ",'" + feeDTO.getDescription() + "')";
		try {
			Halyard.getConnect().executeQuery(query);
		 } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}

	public static PersonDTO createUser(int msid) {
		// create a main person for the membership
		int pid = SqlSelect.getNextAvailablePrimaryKey("person","p_id");
		String query = "INSERT INTO person () VALUES (" + pid  +"," + msid + ",1,'','',null,'','',true,null,null,null)";
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new PersonDTO(pid,msid,1,"","",null,"","",true,null,0);
	}
}
