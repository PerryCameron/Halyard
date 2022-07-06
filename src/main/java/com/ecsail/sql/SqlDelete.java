package com.ecsail.sql;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.Halyard;
import com.ecsail.structures.*;

import java.sql.SQLException;

public class SqlDelete {


	public static boolean deleteStatistics() {
	    boolean noError = false;
		String query = "delete from stats";
		try {
			Halyard.getConnect().executeQuery(query);
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}
    return noError;
	}
	
	public static boolean deletePerson(PersonDTO p) {
	    boolean noError = false;
		String query = "delete from person where p_id=" + p.getP_id();
			try {
				Halyard.getConnect().executeQuery(query);
				noError = true;
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
			}
	    return noError;
	}

	public static void deleteFee(FeeDTO f) {
		String query = "delete from fee where fee_id=" + f.getFeeId();
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}
	}
	
	public static boolean deletePhone(PhoneDTO phone) {
	    boolean noError = false;
		String query = "delete from phone where phone_id=" + phone.getPhone_ID();
			try {
				Halyard.getConnect().executeQuery(query);
				noError = true;
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
			}
	    return noError;
	}
	
	public static boolean deleteBlankMembershipIdRow() {
	    boolean noError = false;
		String query = "delete from membership_id where fiscal_year=0 and MEMBERSHIP_ID=0";
			try {
				Halyard.getConnect().executeQuery(query);
				noError = true;
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to Delete Blank Membership ID Row","See below for details");
			}
	    return noError;
	}
	
	public static boolean deleteEmail(EmailDTO email) {
		boolean noError = false;
		String query = "delete from email where email_id=" + email.getEmail_id();
			try {
				Halyard.getConnect().executeQuery(query);
				noError = true;
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
			}
	    return noError;
	}
	
	public static boolean deleteMembershipId(MembershipIdDTO mid) {
		boolean noError = false;
		String query = "delete from membership_id where mid=" + mid.getMid();
			try {
				Halyard.getConnect().executeQuery(query);
				noError = true;
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
			}
	    return noError;
	}
	
	public static boolean deleteOfficer(OfficerDTO officer) {
		boolean noError = false;
		String query = "delete from officer where o_id=" + officer.getOfficer_id();
		try {
			Halyard.getConnect().executeQuery(query);
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}
		return noError;
	}
	
	public static boolean deleteAward(AwardDTO a) {
		boolean noError = false;
		String query = "delete from awards where award_id=" + a.getAwardId();
		try {
			Halyard.getConnect().executeQuery(query);
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}
		return noError;
	}
	
	public static boolean deleteBoat(BoatDTO boat, MembershipListDTO membership) {
		boolean noError = false;
		String query = "delete from boat_owner where ms_id=" + membership.getMsid()
				+ " AND boat_id=" + boat.getBoat_id();
		String query1 = "delete from boat where boat_id=" + boat.getBoat_id();
			try {
				Halyard.getConnect().executeQuery(query);
				Halyard.getConnect().executeQuery(query1);
				noError = true;
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
			}
			return noError;	
	}
	
	public static boolean deleteBoatOwner(int ms_id) {
		boolean noError = false;
		String query = "delete from boat_owner where ms_id=" + ms_id;
		try {
			Halyard.getConnect().executeQuery(query);
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}
		return noError;	
	}
	
	public static void deleteMemo(MemoDTO memo) {
		String query = "delete from memo where memo_id=" + memo.getMemo_id();
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteMemos(int ms_id) {
		String query = "delete from memo where ms_id=" + ms_id;
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteWorkCredits(int ms_id) {
		String query = "delete from work_credit where ms_id=" + ms_id;
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteWorkCreditsByMoneyID(int money_id) {
		String query = "delete from work_credit where money_id=" + money_id;
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteMonies(int ms_id) {
		String query = "delete from money where ms_id=" + ms_id;
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteMoneyByMoneyID(int money_id) {
		String query = "delete from money where money_id=" + money_id;
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deletePaymentByMoneyID(int money_id) {
		String query = "delete from payment where money_id=" + money_id;
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deletePhones(int p_id) {
		String query = "delete from phone where p_id=" + p_id;
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteEmail(int p_id) {
		String query = "delete from email where p_id=" + p_id;
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteOfficer(int p_id) {
		String query = "delete from officer where p_id=" + p_id;
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deletePerson(int p_id) {
		String query = "delete from person where p_id=" + p_id;
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteMembership(int ms_id) {
		String query = "delete from membership where ms_id=" + ms_id;
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteWaitList(int ms_id) {
		String query = "delete from waitlist where ms_id=" + ms_id;
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	public static void deleteMembershipId(int ms_id) {
		String query = "delete from membership_id where ms_id=" + ms_id;
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete","See below for details");
		}	
	}
	
	
	public static void deletePayment(PaymentDTO p) {
		String query = "delete from payment where pay_id=" + p.getPay_id();
		try {
			Halyard.getConnect().executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete Payment","See below for details");
		}	
	}
	
	public static boolean deleteBoatOwner(int boat_id, int ms_id) {
		boolean noError = false;
		String query = "delete from boat_owner where boat_id=" + boat_id + " and ms_id=" + ms_id;
		try {
			Halyard.getConnect().executeQuery(query);
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to Delete Boat Owner","See below for details");
		}
		return noError;	
	}
}
