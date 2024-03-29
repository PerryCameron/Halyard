package com.ecsail.gui.dialogues;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;

import com.ecsail.main.HalyardPaths;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.*;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.DefinedFeeDTO;
import com.ecsail.structures.MembershipIdDTO;
import com.ecsail.structures.MembershipListDTO;
import com.ecsail.structures.MoneyDTO;
import com.ecsail.structures.PersonDTO;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class Dialogue_NewYearGenerator extends Stage {
	int previousYear;
	int selectedYear;
	int stage = 1;
	Boolean makeNewId = true;
	private ObservableList<MembershipListDTO> memberships;  // will be inactive when filled
	private DefinedFeeDTO definedFee;
	public Dialogue_NewYearGenerator() {
		//this.selectedYear = returnNextRecordYear();
		this.selectedYear = Integer.parseInt(HalyardPaths.getYear()) + 1;  // this will need to be able to change with a spinner
		this.previousYear = selectedYear -1;
		this.definedFee = SqlDefinedFee.getDefinedFeeByYear(String.valueOf(selectedYear)); // this will have to update as well
		Button generateButton = new Button("Yes");
		Button cancelButton = new Button("Cancel");
		Button keepSameButton = new Button("Keep Old");
		//ToggleGroup tg1 = new ToggleGroup();  
		//RadioButton r1 = new RadioButton("Generate new year records for " + selectedYear +"?"); 
        //RadioButton r2 = new RadioButton("Generate information for "); 
		Label generateLable = new Label();
 
		HBox hboxButtons = new HBox();
		HBox hboxGrey = new HBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		VBox vboxColumn1 = new VBox();
		VBox vboxColumn2 = new VBox();
		
		Scene scene = new Scene(vboxBlue, 600, 300);
		final Spinner<Integer> yearSpinner = new Spinner<Integer>();
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));
		
		
		SpinnerValueFactory<Integer> yearValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(selectedYear - 1, selectedYear + 1, selectedYear);
		yearSpinner.setValueFactory(yearValueFactory);
		yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  yearSpinner.increment(0); // won't change value, but will commit editor
				  selectedYear = Integer.parseInt(yearSpinner.getEditor().getText());
			  }
			});
		
		/////////////////// ATTRIBUTES ///////////////////
		yearSpinner.getValueFactory().setValue(selectedYear + 1);
		//r1.setToggleGroup(tg1); 
        //r2.setToggleGroup(tg1); 
        //r1.setSelected(true);
        //batchSpinner.setPadding(new Insets(0,0,0,10));
        hboxGrey.setPadding(new Insets(5,0,0,5));
        hboxGrey.setSpacing(5);
        yearSpinner.setPrefWidth(80);
        vboxColumn1.setSpacing(5);
        vboxColumn2.setSpacing(15);
        hboxButtons.setSpacing(5);
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		// vboxGrey.setId("slip-box");
		hboxGrey.setPrefHeight(688);
		scene.getStylesheets().add("stylesheet.css");
		setTitle("Print to PDF");
		////////////  Check to see if batch exists first////////////
		

		generateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(stage == 1) {
					//yes we want to generate a new year
					vboxColumn1.getChildren().clear();
					vboxColumn1.getChildren().addAll(new Label("Have you put in the Board Of Directors for " + selectedYear + "?"), hboxButtons);
					stage=2;
				} else if (stage == 2) {
					//yes we are happy with all the officers
					vboxColumn1.getChildren().clear();
					hboxButtons.getChildren().clear();
					generateButton.setText("Generate New");
					hboxButtons.getChildren().addAll(generateButton, keepSameButton);
					vboxColumn1.getChildren().addAll(new Label("Would you like to generate new numbers or keep last years?"), hboxButtons);
					stage=3;
				} else if (stage ==3) {
					// we chose to generate new numbers
					generateRecords(true);
				} 
			}
		});
		
		keepSameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// we chose to keep old numbers
				generateRecords(false);
			}
		});
		
		//////////////// DO STUFF /////////////////
		generateLable.setText("Generate new year records for " + selectedYear + "?");

		//////////////// ADD CONTENT ///////////////////
		
		hboxButtons.getChildren().addAll(generateButton,cancelButton);
		vboxColumn1.getChildren().addAll(generateLable, hboxButtons);
		hboxGrey.getChildren().addAll(vboxColumn1,vboxColumn2);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(hboxGrey);
		getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
	
	/////////////////// CLASS METHODS /////////////////////

	private void generateRecords(Boolean makeNewNumbers) {
		this.memberships = SqlMembershipList.getRoster(String.valueOf(previousYear), true);
		System.out.println("selectedYear= " + selectedYear);
		if(makeNewNumbers) {
			System.out.println("Creating new numbers");
		createNewNumbers();
		} else {
			System.out.println("Creating old numbers");
		createOldNumbers();
		}
//		generateMoneyRecords();
	}
	
	private void generateMoneyRecords() {
		MoneyDTO oldMoney = null;
		MoneyDTO currentMoney = null; // this is for adding values of supplemental instead of overwriting
		MoneyDTO newMoney = null;
		int lastYear = selectedYear - 1;
		for (MembershipListDTO membership : memberships) {
			if (SqlExists.moneyExists(membership.getMsid(), String.valueOf(selectedYear))) { // preserve values incase supplemental overwright
				currentMoney = SqlMoney.getMoneyRecordByMsidAndYear(membership.getMsid(), String.valueOf(selectedYear));
			}
			if (SqlExists.moneyExists(membership.getMsid(), lastYear + "")) { // last years record exists
				oldMoney = SqlMoney.getMoneyRecordByMsidAndYear(membership.getMsid(), lastYear + "");
				newMoney = setNewMoneyValues(oldMoney, currentMoney, membership);  // newMoney/oldMoney for clarity
				updateSql(newMoney, membership);
			} else {  // last years money record does not exist
				createSql(membership, currentMoney);
			}
			printRecord(newMoney, membership);
		}
	}
	
	private void createSql(MembershipListDTO ml, MoneyDTO currentMoney) {
		MoneyDTO oldMoney = null;
		MoneyDTO newMoney = null;
		int moneyId = SqlSelect.getNextAvailablePrimaryKey("money","money_id");
		oldMoney = new MoneyDTO(moneyId, ml.getMsid(),selectedYear, 0, "0.00", 0, 0, 0, 0, 0, "0.00", 0, 0, 0,0, 0, 0,"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", false, false, "0.00", "0.00", false, 0, "0.00");
		newMoney = setNewMoneyValues(oldMoney, currentMoney, ml);
		System.out.print(" No money records for " + (newMoney.getFiscal_year() - 1));
			SqlUpdate.updateMoney(newMoney);
	}
	
	private void updateSql(MoneyDTO newMoney, MembershipListDTO ml) {
		if (SqlExists.moneyExists(newMoney.getMoney_id()))  { // this years record exists
			SqlUpdate.updateMoney(newMoney);
			System.out.print(" Update " + newMoney.getFiscal_year() + " record for " + newMoney.getMs_id());
		} else { // this years record does not exist
			
			SqlInsert.addMoneyRecord(newMoney); // add money record
			System.out.print(" Adding Money Record " + newMoney.getFiscal_year() + " record for " + newMoney.getMs_id());
			if(!SqlExists.workCreditExists(newMoney.getMoney_id())) { // if the work credit doesn't exist
			SqlInsert.addWorkCreditRecord(newMoney.getMoney_id(), ml); // add blank work credit record
			System.out.println(" Adding Work Credit " + newMoney.getFiscal_year() + " credit for " + newMoney.getMs_id());
			}
		}
	}
	
	private MoneyDTO setNewMoneyValues(MoneyDTO oldMoney, MoneyDTO currentMoney, MembershipListDTO membership) {
		ObservableList<PersonDTO> people = SqlPerson.getPeople(oldMoney.getMs_id());
		MoneyDTO newMoney = new MoneyDTO();
		if(currentMoney.getMoney_id() != 0) {  // if money record exists lets use its money_id
		newMoney.setMoney_id(currentMoney.getMoney_id());
		} else {  // if money record does not exist lets create a new money_id for it
			newMoney.setMoney_id(SqlSelect.getNextAvailablePrimaryKey("money","money_id"));
		}
		
		newMoney.setMs_id(oldMoney.getMs_id());
		newMoney.setFiscal_year(selectedYear);
		/// adding oldMoney and newMoney because supplemental records would erase entries from
		/// earlier updates
		//newMoney.setWinter_storage(oldMoney.getWinter_storage() + currentMoney.getWinter_storage());
		newMoney.setKayac_rack(oldMoney.getKayac_rack() + currentMoney.getKayac_rack());
		newMoney.setKayac_shed(oldMoney.getKayac_shed() + currentMoney.getKayac_shed());
		newMoney.setBeach(oldMoney.getBeach() + currentMoney.getBeach());
		newMoney.setSail_loft(oldMoney.getSail_loft() + currentMoney.getSail_loft());
		newMoney.setSail_school_laser_loft(oldMoney.getSail_school_laser_loft() + currentMoney.getSail_school_laser_loft());
		/// set officer credit here
		for(PersonDTO p: people) {
			if(p.getMemberType() != 3) {  // if membership has a primary or secondary member
				if(SqlExists.isOfficer(p,selectedYear)) {  // person is an officer
					if(membership.getMemType().equals("RM"))
				    newMoney.setCredit(String.valueOf(definedFee.getDues_regular()));
					else if(membership.getMemType().contentEquals("FM"))
					newMoney.setCredit(String.valueOf(definedFee.getDues_family()));
					else if(membership.getMemType().contentEquals("SO"))
					System.out.println("Social members can't be officers");
					else if(membership.getMemType().contentEquals("LA"))
					System.out.println("Lake Associates can't be officers");
				System.out.println("Membership " + p.getFname() + " " + p.getLname() + " is of type " + p.getMemberType() + " and an officer getting credit of " + newMoney.getCredit());
				}
			}
		}
		// set slip if owned
		if(SqlExists.slipExists(membership.getMsid()))
			newMoney.setWet_slip(String.valueOf(definedFee.getWet_slip()));
		
		// set dues by membership type
		if(membership.getMemType().equals("RM"))
		    newMoney.setDues(String.valueOf(definedFee.getDues_regular()));
			else if(membership.getMemType().contentEquals("FM"))
			newMoney.setDues(String.valueOf(definedFee.getDues_family()));
			else if(membership.getMemType().contentEquals("SO"))
			newMoney.setDues(String.valueOf(definedFee.getDues_social()));
			else if(membership.getMemType().contentEquals("LA"))
			newMoney.setDues(String.valueOf(definedFee.getDues_lake_associate()));
		
		// calculate total
		newMoney.setTotal(String.valueOf(calculateFees(newMoney)));

		/// calculate balance
		BigDecimal total = new BigDecimal(newMoney.getTotal());
		BigDecimal credit = new BigDecimal(newMoney.getCredit());
		newMoney.setBalance(String.valueOf(total.subtract(credit)));
		
		return newMoney;
	}
	
	private void printRecord(MoneyDTO newMoney, MembershipListDTO membership) {
		System.out.println(" Membership ID " + membership.getMembershipId());
		System.out.println(" Money_id=" + newMoney.getMoney_id());
		System.out.print(" Dues=" + newMoney.getDues());
		System.out.print(" beach=" +newMoney.getBeach());
		System.out.print(" kayak rack=" + newMoney.getKayac_rack());
		System.out.print(" kayak shed=" + newMoney.getKayac_shed());
		System.out.print(" slip=" + newMoney.getWet_slip());
		System.out.println(" Laser loft fee=" + newMoney.getSail_school_laser_loft());
		System.out.print(" Total fees=" + newMoney.getTotal());
		System.out.print(" Credit=" + newMoney.getCredit());
		System.out.print(" Balance=" + newMoney.getBalance());
		System.out.println("----------------------");
	}
	
	private BigDecimal calculateFees(MoneyDTO newMoney) {
		BigDecimal result = new BigDecimal("0.00");

		BigDecimal dues = new BigDecimal(newMoney.getDues());
		BigDecimal kayakRack = new BigDecimal(newMoney.getKayac_rack());
		BigDecimal beach = new BigDecimal(newMoney.getBeach());
		BigDecimal kayakShed = new BigDecimal(newMoney.getKayac_shed());
		BigDecimal wetSlip = new BigDecimal(newMoney.getWet_slip());
		BigDecimal sailLoft = new BigDecimal(newMoney.getSail_loft());
		BigDecimal laserLoft = new BigDecimal(newMoney.getSail_school_laser_loft());

		result = dues.add(kayakRack.multiply(definedFee.getKayak_rack()))
				.add(beach.multiply(definedFee.getBeach()))
				.add(kayakShed.multiply(definedFee.getKayak_shed()))
				.add(wetSlip)
				.add(sailLoft)
				.add(laserLoft.multiply(definedFee.getSail_school_laser_loft()));

//		result += newMoney.getDues();
//		result += newMoney.getKayac_rack() * definedFee.getKayak_rack();
//		result += newMoney.getBeach() * definedFee.getBeach();
//		result += newMoney.getKayac_shed() * definedFee.getKayak_shed();
//		result += newMoney.getWet_slip();
//		result += newMoney.getSail_loft();
//		result += newMoney.getSail_school_laser_loft() * definedFee.getSail_school_laser_loft();
		return result;		
	}
	
	private void createNewNumbers() {
		int count = 1;
		int mid = SqlSelect.getNextAvailablePrimaryKey("membership_id", "mid");
		Collections.sort(memberships, Comparator.comparing(MembershipListDTO::getMembershipId));
		for (MembershipListDTO ml : memberships) {
			if(!SqlExists.memberShipIdExists(ml.getMsid(), String.valueOf(selectedYear))) {
				//System.out.println("Membership " + ml.getMsid() + " exists, Creating new");
//			SqlInsert.addMembershipId(
			System.out.println(		new MembershipIdDTO(mid, String.valueOf(selectedYear), ml.getMsid(), count + "",false,ml.getMemType(),false,false));
			mid++;
			count++; // new membership id
			} else {
				//System.out.println("Membership " + ml.getMsid() + " exists");
			}
		}
	}
	
	private void createOldNumbers() {
		System.out.println("memberships size= " + memberships.size());
		int mid = SqlSelect.getNextAvailablePrimaryKey("membership_id", "mid");
		Collections.sort(memberships, Comparator.comparing(MembershipListDTO::getMembershipId));
		for (MembershipListDTO ml : memberships) {
			if (!SqlExists.memberShipIdExists(ml.getMsid(), String.valueOf(selectedYear))) {
				SqlInsert.addMembershipId(new MembershipIdDTO(mid, String.valueOf(selectedYear), ml.getMsid(), ml.getMembershipId() + "", false,ml.getMemType(),false,false));
				mid++;
			} else {
				System.out.println("Membership " + ml.getMsid() + " exists");
			}
		}
	}
}
