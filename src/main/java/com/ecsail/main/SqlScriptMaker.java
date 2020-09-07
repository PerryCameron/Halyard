package com.ecsail.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ecsail.structures.Object_Boat;
import com.ecsail.structures.Object_BoatOwner;
import com.ecsail.structures.Object_DefinedFee;
import com.ecsail.structures.Object_Email;
import com.ecsail.structures.Object_Membership;
import com.ecsail.structures.Object_Memo;
import com.ecsail.structures.Object_Money;
import com.ecsail.structures.Object_Officer;
import com.ecsail.structures.Object_Person;
import com.ecsail.structures.Object_Phone;
import com.ecsail.structures.Object_Slip;
import com.ecsail.structures.Object_WorkCredit;

import javafx.collections.ObservableList;

public class SqlScriptMaker {
	static ArrayList<String> tableCreation = new ArrayList<String>();
	static ObservableList<Object_Membership> memberships;
	static ObservableList<Object_Person> people;
	static ObservableList<Object_Phone> phones;
	static ObservableList<Object_Boat> boats;
	static ObservableList<Object_BoatOwner> boatowners;
	static ObservableList<Object_Slip> slips;
	static ObservableList<Object_Memo> memos;
	static ObservableList<Object_Email> email;
	static ObservableList<Object_Money> monies;
	static ObservableList<Object_Officer> officers;
	static ObservableList<Object_DefinedFee> definedfees;
	static ObservableList<Object_WorkCredit> workcredits;
	private static final int ALL = 0;
	
	public static void createSql() {
		System.out.println("Creating SQL script....");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");  
		Date date = new Date();  
		String stringDate = formatter.format(date);
		stringDate.replaceAll("\\s+", "");
		memberships = SqlSelect.getMemberships();
		people = SqlSelect.getPeople(); 
		phones = SqlSelect.getPhone(ALL);
		boats = SqlSelect.getBoats();
		boatowners = SqlSelect.getBoatOwners();
		slips = SqlSelect.getSlips();
		memos = SqlSelect.getMemos(ALL);
		email = SqlSelect.getEmail(ALL);
		monies = SqlSelect.getMonies(ALL);
		officers = SqlSelect.getOfficers();
		definedfees = SqlSelect.getDefinedFees();
		workcredits = SqlSelect.getWorkCredits();
		readFromFile(FileIO.SCRIPTS + "/ecsc_create.sql");
		writeToFile(FileIO.SCRIPTS + "/ecsc_sql_" + stringDate + ".sql");
	}
	
	public static void writeToFile(String filename) {
		try {
			File file = new File(filename);
			FileWriter writer = new FileWriter(file, true);
			// writer.write("use ECSC_SQL;" + System.lineSeparator());
			System.out.println("Table creation script is " + tableCreation.size() + " lines.");
			for (String tabe : tableCreation)
				writer.write(tabe + System.lineSeparator());
			for (Object_Membership mem : memberships)
				writer.write(getMembershipString(mem));
			for (Object_Person peo : people)
				writer.write(getPeopleString(peo));
			for (Object_Phone pho : phones)
				writer.write(getPhoneString(pho));
			for (Object_Boat boa : boats)
				writer.write(getBoatString(boa));
			for (Object_BoatOwner bos : boatowners)
				writer.write(getBoatOwnerString(bos));
			for (Object_Slip sli : slips)
				writer.write(getSlipString(sli));
			for (Object_Memo mem : memos)
				writer.write(getMemoString(mem));
			for (Object_Email eml : email)
				writer.write(getEmailString(eml));
			for (Object_Money mon : monies)
				writer.write(getMoneyString(mon));
			for (Object_Officer off : officers)
				writer.write(getOfficerString(off));
			for (Object_DefinedFee def : definedfees)
				writer.write(getDefinedFeeString(def));
			for (Object_WorkCredit woc : workcredits)
				writer.write(getWorkCreditString(woc));
			writer.close();
			System.out.println("SQL script file sucessfully made");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getWorkCreditString(Object_WorkCredit woc) {
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
	
	public static String getDefinedFeeString(Object_DefinedFee def) {
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
				+ def.getKayak_shed_key()
				+ ");\n";
	}
	
	public static String getOfficerString(Object_Officer off) {
		return 
				"INSERT INTO officer () VALUES ("
				+ off.getOfficer_id() + ","
				+ off.getPerson_id() + ","
				+ off.getBoard_year() + ",'"
				+ off.getOfficer_type() + "','"
				+ off.getFiscal_year() 
				+ "');\n";
	}
	
	public static String getMoneyString(Object_Money mon) {
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
				+ mon.getOther() 
				+ ");\n";
	}
	
	public static String getEmailString(Object_Email eml) {
		return 
				"INSERT INTO email () VALUES ("
				+ eml.getEmail_id() + ","
				+ eml.getPid() + ","
				+ eml.isIsPrimaryUse() + ",\""
				+ eml.getEmail() + "\","
				+ eml.isIsListed()
				+ ");\n";
	}
	
	public static String getMemoString(Object_Memo mem) {
		return 
				"INSERT INTO memo () VALUES ("
				+ mem.getMemo_id() + ","
				+ mem.getMsid() + ",\""
				+ mem.getMemo_date() + "\",\""
				+ mem.getMemo() + "\");\n";
	}
	
	public static String getSlipString(Object_Slip sli) {
		return 
				"INSERT INTO slip () VALUES ("
				+ sli.getSlip_id() + ","
				+ sli.getMs_id() + ",\""
				+ sli.getSlipNumber() + "\","
				+ getCorrectString(sli.getSubleased_to()) + ");\n";
	}
	
	public static String getBoatOwnerString(Object_BoatOwner bos) {
		return 
				"INSERT INTO boat_owner () VALUES ("
				+ bos.getMsid() + ","
				+ bos.getBoat_id() +");\n";

	}
	
	public static String getBoatString(Object_Boat boa) {
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
				+ getCorrectString(boa.getPhrf())
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
			result = example + ""; // print "string"
		}
		return result;
	}
	
	public static String getPhoneString(Object_Phone pho) {
		return 
				"INSERT INTO phone () VALUES ("
				+ pho.getPhone_ID() + ","
				+ pho.getPid() + ","
				+ getCorrectString(pho.getPhoneNumber()) + ","
				+ getCorrectString(pho.getPhoneType()) + ","
				+ pho.isIsListed() + ");\n";
	}
	
	public static String getPeopleString(Object_Person peo) {
		return 
				"INSERT INTO person () VALUES ("
				+ peo.getP_id() + ","
				+ peo.getMs_id() + ","
				+ getCorrectString(peo.getMemberType()) + ","
				+ getCorrectString(peo.getFname()) + ","
				+ getCorrectString(peo.getLname()) + ","
				+ getCorrectString(peo.getBirthday()) + ","
				+ getCorrectString(peo.getOccupation()) + ","
				+ getCorrectString(peo.getBuisness()) + ","
				+ peo.isActive() + ","
				+ "null);\n";  // this will be a picture or link to eventually
		
	}
	
	public static String getMembershipString(Object_Membership mem) {  // change back once done

			return "INSERT INTO membership () VALUES (" 
					+ mem.getMsid() + ","
					+ mem.getPid() + ","
					+ getCorrectString(mem.getMembershipId()) + ","	
					+ getCorrectString(mem.getJoinDate()) + ","
					+ getCorrectString(mem.getMemType()) + ","
					+ mem.isActiveMembership() + ","
					+ getCorrectString(mem.getAddress()) + ","
					+ getCorrectString(mem.getCity()) + ","
					+ getCorrectString(mem.getState()) + ","
					+ getCorrectString(mem.getZip()) + ");\n";
	}
	
	public static void readFromFile(String filename) {
		  File file = new File(filename); 
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(file));
			String st; 
		  while ((st = br.readLine()) != null) {
			  tableCreation.add(new String(st));		  
		  }
		  br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
