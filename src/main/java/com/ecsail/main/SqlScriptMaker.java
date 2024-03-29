package com.ecsail.main;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import com.ecsail.gui.dialogues.Dialogue_DatabaseBackup;
import com.ecsail.sql.select.*;
import com.ecsail.structures.*;

import javafx.collections.ObservableList;

public class SqlScriptMaker {
	static Object_TupleCount newTupleCount;
	static ArrayList<String> tableCreation = new ArrayList<String>();
	static ObservableList<MembershipDTO> memberships;
	static ObservableList<MembershipIdDTO> ids;
	static ObservableList<PersonDTO> people;
	static ObservableList<PhoneDTO> phones;
	static ObservableList<BoatDTO> boats;
	static ObservableList<BoatOwnerDTO> boatowners;
	static ArrayList<SlipDTO> slips;
	static ObservableList<MemoDTO> memos;
	static ObservableList<EmailDTO> email;
	static ObservableList<MoneyDTO> monies;
	static ObservableList<OfficerDTO> officers;
	static ObservableList<DefinedFeeDTO> definedfees;
	static ObservableList<WorkCreditDTO> workcredits;
	static ObservableList<PaymentDTO> payments;
	static ObservableList<DepositDTO> deposits;
	static ArrayList<WaitListDTO> waitlist;
	static ArrayList<AwardDTO>awards;
	static ArrayList<HashDTO>hash;
	static ArrayList<FeeDTO>fees;
	static ArrayList<IdChangeDTO>idChanges;
	
	private static final int ALL = 0;
	
	public static void createSql() {
		//SqlScriptMaker.newTupleCount = new Object_TupleCount();
		SqlScriptMaker.newTupleCount =  Halyard.edits;
		System.out.println("Creating SQL script....");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");  
		Date date = new Date();  
		String stringDate = formatter.format(date);
		stringDate.replaceAll("\\s+", "");
		memberships = SqlMembership.getMemberships();
		ids = SqlMembership_Id.getIds();
		people = SqlPerson.getPeople();
		phones = SqlPhone.getPhoneByPid(ALL);
		boats = SqlBoat.getBoats();
		boatowners = SqlBoat.getBoatOwners();
		slips = SqlSlip.getSlips();
		memos = SqlMemos.getMemos(ALL);
		email = SqlEmail.getEmail(ALL);
		monies = SqlMoney.getAllMonies();
		officers = SqlOfficer.getOfficers();
		definedfees = SqlDefinedFee.getDefinedFees();
		workcredits = SqlWorkCredit.getWorkCredits();
		payments = SqlPayment.getPayments();
		deposits = SqlDeposit.getDeposits();
		waitlist = SqlWaitList.getWaitLists();
		awards = SqlAward.getAwards();
		hash = SqlHash.getAllHash();
		fees = SqlFee.getAllFees();
		idChanges = SqlIdChange.getAllChangedIds();
		HalyardPaths.checkPath(HalyardPaths.SQLBACKUP + "/" + HalyardPaths.getYear());
		calculateSums();
		new Dialogue_DatabaseBackup(newTupleCount);
		writeToFile(HalyardPaths.SQLBACKUP + "/" + HalyardPaths.getYear() + "/ecsc_sql_" + stringDate + ".sql");
	}
	
	public static void writeToFile(String filename) {
		try {
			File file = new File(filename);
			FileWriter writer = new FileWriter(file, true);
			// writes the schema from ecsc_create.sql located in resources/database/
			writer.write(writeSchema());
			writer.write("\n\n");
			for (MembershipDTO mem : memberships)
				writer.write(getMembershipString(mem));
			for (MembershipIdDTO mid : ids)
				writer.write(getMembershipIdString(mid));
			for (PersonDTO peo : people)
				writer.write(getPeopleString(peo));
			for (PhoneDTO pho : phones)
				writer.write(getPhoneString(pho));
			for (BoatDTO boa : boats)
				writer.write(getBoatString(boa));
			for (BoatOwnerDTO bos : boatowners)
				writer.write(getBoatOwnerString(bos));
			for (SlipDTO sli : slips)
				writer.write(getSlipString(sli));
			for (MemoDTO mem : memos)
				writer.write(getMemoString(mem));
			for (EmailDTO eml : email)
				writer.write(getEmailString(eml));
			for (MoneyDTO mon : monies)
				writer.write(getMoneyString(mon));
			for (DepositDTO dep : deposits)
				writer.write(getDepositString(dep));
			for (PaymentDTO obp : payments)
				writer.write(getPaymentString(obp));
			for (OfficerDTO off : officers)
				writer.write(getOfficerString(off));
			for (DefinedFeeDTO def : definedfees)
				writer.write(getDefinedFeeString(def));
			for (WorkCreditDTO woc : workcredits)
				writer.write(getWorkCreditString(woc));
			for (WaitListDTO wal: waitlist)
				writer.write(getWaitListString(wal));
			for (AwardDTO oa: awards)
				writer.write(getAwardsString(oa));
			for (HashDTO hd: hash)
				writer.write(getHashString(hd));
			for (FeeDTO fe: fees)
				writer.write(getFeeString(fe));
			for (IdChangeDTO idc: idChanges)
				writer.write(getIdChangeString(idc));

			clearMemory();
			writer.close();
			System.out.println("SQL script file sucessfully made");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public static void clearMemory() {
		tableCreation.clear();
		memberships.clear();
		ids.clear();
		people.clear();
		phones.clear();
		boats.clear();
		boatowners.clear();
		slips.clear();
		memos.clear();
		email.clear();
		monies.clear();
		deposits.clear();
		payments.clear();
		officers.clear();
		definedfees.clear();
		workcredits.clear();
		waitlist.clear();
		awards.clear();
		fees.clear();
		idChanges.clear();
	}
	
	// this is to calculate changes to display everytime you back up the database.
	public static void calculateSums() {
	//	System.out.println("Table creation script is " + tableCreation.size() + " lines.");
		newTupleCount.setTableCreationSize(tableCreation.size());
	//	System.out.println(memberships.size() +" memberships written");
		newTupleCount.setMembershipSize(memberships.size());
	//	System.out.println(ids.size() + " ids written");
		newTupleCount.setIdSize(ids.size());
	//	System.out.println(people.size() + " people written");
		newTupleCount.setPeopleSize(people.size());
	//	System.out.println(phones.size() + " phone numbers written");
		newTupleCount.setPhoneSize(phones.size());
	//	System.out.println(boats.size() + " boats written");
		newTupleCount.setBoatSize(boats.size());
	//	System.out.println(boatowners.size() + " boatowners written");
		newTupleCount.setBoatOwnerSize(boatowners.size());
	//	System.out.println(slips.size() + " slips written");
		newTupleCount.setSlipsSize(slips.size());
	//	System.out.println(memos.size() + " memos written");
		newTupleCount.setMemosSize(memos.size());
	//	System.out.println(email.size() + " email written");
		newTupleCount.setEmailSize(email.size());
	//	System.out.println(monies.size() + " monies written");
		newTupleCount.setMoniesSize(monies.size());
	//	System.out.println(deposits.size() + " deposits written");
		newTupleCount.setDepositsSize(deposits.size());
	//	System.out.println(payments.size() + " payments written");
		newTupleCount.setPaymentsSize(payments.size());
	//	System.out.println(officers.size() + " officers written");
		newTupleCount.setOfficersSize(officers.size());
	//	System.out.println(definedfees.size() + " definedfees written");
		newTupleCount.setDefinedFeesSize(definedfees.size());
	//	System.out.println(workcredits.size() + " workcredits written");
		newTupleCount.setWorkCreditsSize(workcredits.size());
	}

	private static String getIdChangeString(IdChangeDTO idc) {
		return
		"INSERT INTO id_change () VALUES("
		+ idc.getChangeId() + ","
		+ idc.getIdYear() + ","
		+ idc.isChanged() + ");\n";
	}

	private static String getFeeString(FeeDTO fe) {
		return
		"INSERT INTO fee () VALUES("
		+ fe.getFeeId() + ","
		+ getCorrectString(fe.getFieldName()) + ","
		+ fe.getFieldValue() + ","
		+ fe.getFieldQuantity() + ","
		+ fe.getFeeYear() + ","
		+ getCorrectString(fe.getDescription()) + ");\n";
	}

	private static String getHashString(HashDTO hd) {
		return
		"INSERT INTO form_msid_hash () VALUES("
		+hd.getHash_id() + ","
		+ hd.getHash() + ","
		+ hd.getMsid() + ");\n";
	}

	private static String getAwardsString(AwardDTO oa) {
		return
		"INSERT INTO awards () VALUES ("
		+ oa.getAwardId() + ","
		+ oa.getPid() + ",'"
		+ oa.getAwardYear() + "','"
		+ oa.getAwardType() + "');\n";
		
	}
	
	public static String getWaitListString(WaitListDTO wal) {
		return
		"INSERT INTO waitlist () VALUES ("
		+ wal.getMs_id() + ","
		+ wal.isSlipWait() + "," // stored as integer in database
		+ wal.isKayakWait() + ","
		+ wal.isShedWait() + ","
		+ wal.isWantToSublease() + ","
		+ wal.isWantsRelease() + ","
		+ wal.isWantSlipChange()
		+ ");\n"; //stored as integer in database
	}
	
	public static String getMembershipIdString(MembershipIdDTO mid) {
		return
		"INSERT INTO membership_id () VALUES ("
		+ mid.getMid() + ","
		+ mid.getFiscal_Year() + "," // stored as integer in database
		+ mid.getMs_id() + ","
		+ mid.getMembership_id() + ","
		+ mid.isRenew() + ","
		+ getCorrectString(mid.getMem_type()) + ","
		+ mid.isSelected() + ","
		+ mid.isLateRenew()
		+ ");\n"; //stored as integer in database
	}
	
	public static String getDepositString(DepositDTO d) {
		return
				"INSERT INTO deposit () VALUES ("
				+ d.getDeposit_id() + ",'"
				+ d.getDepositDate() + "',"
				+ d.getFiscalYear() + ","
				+ d.getBatch()
				+ ");\n";
	}
	
	public static String getPaymentString(PaymentDTO pay) {
		return
				"INSERT INTO payment () VALUES ("
				+ pay.getPay_id() + ","
				+ pay.getMoney_id() + ","
				+ pay.getCheckNumber() + ",'"
				+ pay.getPaymentType() + "','"
				+ pay.getPaymentDate() + "','"
				+ pay.getPaymentAmount() + "',"
				+ pay.getDeposit_id()
				+ ");\n";
	}
	
	public static String getWorkCreditString(WorkCreditDTO woc) {
		return 
				"INSERT INTO work_credit () VALUES ("
				+ woc.getMoney_id() + ","
				+ woc.getMsid() + ","
				+ woc.getRacing() + ","
				+ woc.getHarbor() + ","
				+ woc.getSocial() + ","
				+ woc.getOther()
				+ ");\n";
	}
	
	public static String getDefinedFeeString(DefinedFeeDTO def) {
		return 
				"INSERT INTO defined_fee () VALUES ("
				+ def.getFiscal_year() + ","
				+ def.getDues_regular() + ","
				+ def.getDues_family() + ","
				+ def.getDues_lake_associate() + ","
				+ def.getDues_social() + ","
				+ def.getInitiation() + ","
				+ def.getWet_slip() + ","
				+ def.getBeach() + ","
				+ def.getWinter_storage() + ","
				+ def.getMain_gate_key() + ","
				+ def.getSail_loft() + ","
				+ def.getSail_loft_key() + ","
				+ def.getSail_school_laser_loft() + ","
				+ def.getSail_school_loft_key() + ","
				+ def.getKayak_rack() + ","
				+ def.getKayak_shed() + ","
				+ def.getKayak_shed_key() + ","
				+ def.getWork_credit() + ","
				+ def.getKayak_beach_rack()
				+ ");\n";
	}
	
	public static String getOfficerString(OfficerDTO off) {
		return 
				"INSERT INTO officer () VALUES ("
				+ off.getOfficer_id() + ","
				+ off.getPerson_id() + ","
				+ off.getBoard_year() + ",'"
				+ off.getOfficer_type() + "','"
				+ off.getFiscal_year() 
				+ "');\n";
	}
	
	public static String getMoneyString(MoneyDTO mon) {
		return 
				"INSERT INTO money () VALUES ("
				+ mon.getMoney_id() + ","
				+ mon.getMs_id() + ","
				+ mon.getFiscal_year() + ","
				+ mon.getBatch() + ","
				+ mon.getOfficer_credit() + ","
				+ mon.getExtra_key() + ","				
				+ mon.getKayac_shed_key() + ","			
				+ mon.getSail_loft_key() + ","
				+ mon.getSail_school_loft_key() + ","								
				+ mon.getBeach() + ","
				+ mon.getWet_slip() + ","								
				+ mon.getKayac_rack() + ","
				+ mon.getKayac_shed() + ","
				+ mon.getSail_loft() + ","
				+ mon.getSail_school_laser_loft() + ","								
				+ mon.getWinter_storage() + ","
				+ mon.getYsc_donation() + ","								
				+ mon.getPaid() + ","
				+ mon.getTotal() + ","
				+ mon.getCredit() + ","
				+ mon.getBalance() + ","
				+ mon.getDues() + ","
				+ mon.isCommitted() + ","
				+ mon.isClosed() + ","
				+ mon.getOther() + ","
				+ mon.getInitiation() + ","
				+ mon.isSupplemental() + ","
				+ mon.getWork_credit() + ","
				+ mon.getOther_credit() + ","
				+ mon.getKayak_beach_rack()
				+ ");\n";
	}
	
	public static String getEmailString(EmailDTO eml) {
		return 
				"INSERT INTO email () VALUES ("
				+ eml.getEmail_id() + ","
				+ eml.getPid() + ","
				+ eml.isIsPrimaryUse() + ",\""
				+ eml.getEmail() + "\","
				+ eml.isIsListed()
				+ ");\n";
	}
	
	public static String getMemoString(MemoDTO mem) {
		return 
				"INSERT INTO memo () VALUES ("
				+ mem.getMemo_id() + ","
				+ mem.getMsid() + ",\""
				+ mem.getMemo_date() + "\",\""
				+ mem.getMemo() + "\","
				+ mem.getMoney_id() + ",\""
				+ mem.getCategory() + "\""
				+");\n";
	}
	
	public static String getSlipString(SlipDTO sli) {
		return 
				"INSERT INTO slip () VALUES ("
				+ sli.getSlip_id() + ","
				+ getCorrectString(sli.getMs_id()) + ",\""
				+ sli.getSlipNumber() + "\","
				+ getCorrectString(sli.getSubleased_to()) + ","
				+ getCorrectString(sli.getAltText())
				+ ");\n";
	}
	
	public static String getBoatOwnerString(BoatOwnerDTO bos) {
		return 
				"INSERT INTO boat_owner () VALUES ("
				+ bos.getMsid() + ","
				+ bos.getBoat_id() +");\n";
	}
	
	public static String getBoatString(BoatDTO boa) {
		return 
				"INSERT INTO boat () VALUES ("
				+ boa.getBoat_id() + ","
				+ getCorrectString(boa.getManufacturer()) + ","
				+ getCorrectString(boa.getManufacture_year())  + ","
				+ getCorrectString(boa.getRegistration_num()) + ","
				+ getCorrectString(boa.getModel()) + ","
				+ getCorrectString(boa.getBoat_name()) + ","
				+ getCorrectString(boa.getSail_number()) + ","
				+ boa.isHasTrailer() + ","
				+ getCorrectString(boa.getLength()) + ","
				+ getCorrectString(boa.getWeight()) + ","
				+ getCorrectString(boa.getKeel()) + ","
				+ getCorrectString(boa.getPhrf()) + ","
				+ getCorrectString(boa.getDraft()) + ","
				+ getCorrectString(boa.getBeam()) + ","
				+ getCorrectString(boa.getLwl()) + ","
				+ boa.isAux()
				+ ");\n";
	}
	
	public static String getCorrectString(String example) {
		String result = "";
		if(example == null) {  // if actually null print null
			result = "null";
		}  else {
			result = "\"" + example + "\""; // print "string"
		}
		return result;
	}
	
	public static String getCorrectString(int example) {   /// overload the method
		String result = "";
		if(example == 0) {  // if actually null print null
			result = "null";
		}  else {
			result =  String.valueOf(example); // print "string"
		}
		return result;
	}
	
	public static String getPhoneString(PhoneDTO pho) {
		return 
				"INSERT INTO phone () VALUES ("
				+ pho.getPhone_ID() + ","
				+ pho.getPid() + ","
				+ getCorrectString(pho.getPhoneNumber()) + ","
				+ getCorrectString(pho.getPhoneType()) + ","
				+ pho.isIsListed() + ");\n";
	}
	
	public static String getPeopleString(PersonDTO peo) {
		return 
				"INSERT INTO person () VALUES ("
				+ peo.getP_id() + ","
				+ getCorrectString(peo.getMs_id()) + ","
				+ getCorrectString(peo.getMemberType()) + ","
				+ getCorrectString(peo.getFname()) + ","
				+ getCorrectString(peo.getLname()) + ","
				+ getCorrectString(peo.getBirthday()) + ","
				+ getCorrectString(peo.getOccupation()) + ","
				+ getCorrectString(peo.getBuisness()) + ","
				+ peo.isActive() + ","
				+ "null," 
				+ getCorrectString(peo.getNname()) + ","
				+ getCorrectString(peo.getOldMsid()) + ");\n";  // this will be a picture or link to eventually
	}
	
	public static String getMembershipString(MembershipDTO mem) {  // change back once done

			return "INSERT INTO membership () VALUES (" 
					+ mem.getMsid() + ","
					+ mem.getPid() + ","
					+ getCorrectString(mem.getJoinDate()) + ","
					+ getCorrectString(mem.getMemType()) + ","
					+ getCorrectString(mem.getAddress()) + ","
					+ getCorrectString(mem.getCity()) + ","
					+ getCorrectString(mem.getState()) + ","
					+ getCorrectString(mem.getZip()) + ");\n";
	}

	// reads schema from resources and returns a string
	public static String writeSchema() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream io = classloader.getResourceAsStream("database/ecsc_create.sql");
		BufferedReader reader = new BufferedReader(new InputStreamReader(io));
		String result = reader.lines().collect(Collectors.joining(System.lineSeparator()));
		return result;
	}
}
