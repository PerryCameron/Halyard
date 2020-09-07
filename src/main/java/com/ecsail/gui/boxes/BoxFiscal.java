package com.ecsail.gui.boxes;

import com.ecsail.gui.dialogues.Dialogue_Keys;
import com.ecsail.gui.dialogues.Dialogue_WorkCredits;
import com.ecsail.main.Note;
import com.ecsail.main.SqlExists;
import com.ecsail.main.SqlSelect;
import com.ecsail.main.SqlUpdate;
import com.ecsail.structures.Object_DefinedFee;
import com.ecsail.structures.Object_Membership;
import com.ecsail.structures.Object_Money;
import com.ecsail.structures.Object_Officer;
import com.ecsail.structures.Object_Person;
import com.ecsail.structures.Object_WorkCredit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoxFiscal extends HBox {
	private ObservableList<Object_Money> fiscals = null;
	Object_Membership membership;
	Object_DefinedFee definedFees;
	Object_WorkCredit selectedWorkCreditYear;
	Object_Officer officer;
	private ObservableList<Object_Person> people;
	boolean hasOfficer;
	private int rowIndex;
	private Note note;
	private final TextField yscText = new TextField();
	private final TextField paidText = new TextField();
	private final TextField totalWorkCreditTextField = new TextField();
	private final TextField totalKeyTextField = new TextField();
	private final TextField duesText;
	private final TextField totalFeesText = new TextField();
	private final TextField creditText = new TextField();
	private final TextField balanceText = new TextField();
	private final TextField otherText = new TextField();
	private final Spinner<Integer> wetSlipSpinner = new Spinner<Integer>();
	private final Spinner<Integer> beachSpinner = new Spinner<Integer>();
	private final Spinner<Integer> kayakRackSpinner = new Spinner<Integer>();
	private final Spinner<Integer> kayakShedSpinner = new Spinner<Integer>();
	private final Spinner<Integer> sailLoftSpinner = new Spinner<Integer>();
	private final Spinner<Integer> sailSchoolLoftSpinner = new Spinner<Integer>();
	private final Spinner<Integer> winterStorageSpinner = new Spinner<Integer>();
	private final String disabledColor = "-fx-background-color: #d5dade";
	
	public BoxFiscal(Object_Membership m, ObservableList<Object_Person> p, ObservableList<Object_Money> o, int r, Note n, TextField dt) {
		this.membership = m;
		this.people = p;
		this.rowIndex = r;
		this.fiscals = o;
		this.note = n;
		this.duesText = dt;
		this.selectedWorkCreditYear = SqlSelect.getWorkCredit(fiscals.get(rowIndex).getMoney_id());
		this.definedFees = SqlSelect.selectDefinedFees(fiscals.get(rowIndex).getFiscal_year());
		this.hasOfficer = membershipHasOfficer();

		////////////// OBJECTS /////////////////////
		
		Label workCreditsLabel = new Label("Work Credits");
		Label feesLabel = new Label("Fees");
		Label BalanceLabel = new Label("Balance");
		Label keysLabel = new Label("Keys");

		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox mainVbox = new VBox();
		HBox mainHbox = new HBox();
		HBox comboHBox = new HBox();
		
		HBox hboxWinterStorage = new HBox();
		HBox hboxKayac = new HBox();
		HBox hboxWetSlip = new HBox();
		HBox hboxYSC = new HBox();
		HBox hboxPaid = new HBox();
		HBox hboxDues = new HBox();
		HBox hboxtotalWC = new HBox();
		HBox hboxtotalKey = new HBox();
		HBox hboxBeach = new HBox();
		HBox hboxKayakShed = new HBox();
		HBox hboxSailLoft = new HBox();
		HBox hboxSailSchoolLoft = new HBox();
		HBox hboxTotalFees = new HBox();
		HBox hboxCredit = new HBox();
		HBox hboxBalence = new HBox();
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		HBox hboxSubKey = new HBox(); // container for textfield and button
		HBox hboxSubWK = new HBox(); // container for textfield and button
		HBox hboxCheckBox = new HBox();
		HBox hboxOther = new HBox();
		CheckBox commitCheckBox = new CheckBox("Commit");
		Button addWorkCredits = new Button("Add");
		Button addKeys = new Button("Add");
		


		//////////////// ATTRIBUTES ///////////////////
		workCreditsLabel.setId("bold-label");
		feesLabel.setId("bold-label");
		BalanceLabel.setId("bold-label");
		keysLabel.setId("bold-label");
		hboxDues.setSpacing(78);
		
		hboxWinterStorage.setSpacing(28);
		hboxKayac.setSpacing(48.5);  // kayak rack
		hboxWetSlip.setSpacing(68.5);
		hboxYSC.setSpacing(35);
		hboxOther.setSpacing(75);
		hboxPaid.setSpacing(43);
		hboxtotalWC.setSpacing(36.5);
		hboxtotalKey.setSpacing(36.5);
		hboxBeach.setSpacing(48);
		hboxKayakShed.setSpacing(47); // kayak shed
		hboxSailLoft.setSpacing(65);  // sail loft
		hboxSailSchoolLoft.setSpacing(25.5);
		hboxTotalFees.setSpacing(12);
		hboxCredit.setSpacing(34);
		hboxBalence.setSpacing(25);
		hboxSubWK.setSpacing(5);
		hboxSubKey.setSpacing(5);
		winterStorageSpinner.setPrefWidth(60);
		kayakRackSpinner.setPrefWidth(60);
		wetSlipSpinner.setPrefWidth(60);
		yscText.setPrefWidth(60);
		otherText.setPrefWidth(60);
		paidText.setPrefWidth(60);
		totalWorkCreditTextField.setPrefWidth(60);
		duesText.setPrefWidth(60);
		beachSpinner.setPrefWidth(60);
		kayakShedSpinner.setPrefWidth(60);
		sailLoftSpinner.setPrefWidth(60);
		sailSchoolLoftSpinner.setPrefWidth(60);
		creditText.setPrefWidth(60);
		balanceText.setPrefWidth(60);
		totalFeesText.setPrefWidth(60);
		totalFeesText.setEditable(false);
		totalFeesText.setStyle(disabledColor);
		totalKeyTextField.setPrefWidth(60);
		totalKeyTextField.setEditable(false);
		totalKeyTextField.setStyle(disabledColor);
		duesText.setStyle(disabledColor);
		creditText.setStyle(disabledColor);
		totalWorkCreditTextField.setStyle(disabledColor);
		balanceText.setStyle("-fx-background-color: #9fc0c7");
		vbox1.setAlignment(Pos.CENTER);
		vbox2.setAlignment(Pos.CENTER);
		vbox1.setSpacing(5);
		vbox2.setSpacing(5);
		totalWorkCreditTextField.setEditable(false);
		duesText.setEditable(false);
		
		creditText.setEditable(false);
		balanceText.setEditable(false);
		comboHBox.setPadding(new Insets(0, 0, 10, 0));  // sets height of work credits label
		BalanceLabel.setPadding(new Insets(20, 0, 0, 0));
		workCreditsLabel.setPadding(new Insets(20, 0, 0, 0));
		comboHBox.setAlignment(Pos.TOP_RIGHT);
		commitCheckBox.setSelected(fiscals.get(rowIndex).isCommitted());
		setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		
		setId("box-blue");
		vboxGrey.setId("box-grey");
		mainHbox.setSpacing(50);
		vboxGrey.setPadding(new Insets(8, 10, 0, 30));
		hboxCheckBox.setPadding(new Insets(10, 0, 0, 0));
		vboxGrey.setPrefWidth(460);
		
		//////////////// LISTENER //////////////////
		
		balanceText.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("0"))
				balanceText.setStyle("-fx-background-color: #30e65a");
		});
		
		// this is only called if you changer membership type or open a record
		duesText.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!SqlSelect.isCommitted(fiscals.get(rowIndex).getMoney_id())) {
				int newDues = Integer.parseInt(newValue);
				System.out.println(" dues textfield set to " + newValue);
				fiscals.get(rowIndex).setDues(newDues);
				updateBalance();
			} else {
				System.out.println("Record is commited, no changes made");
			}
		});
		
		commitCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> commit, Boolean oldValue, Boolean newValue) {
				if (newValue) {  // we are commiting, not uncommiting
					if (!balanceText.getText().equals("0"))
						balanceText.setStyle("-fx-background-color: #f23a50");
					SqlUpdate.updateMoney(fiscals.get(rowIndex)); 
					SqlUpdate.commitFiscalRecord(fiscals.get(rowIndex).getMoney_id(), commit.getValue());// this could be placed in line above
					fiscals.get(rowIndex).setCommitted(commit.getValue());
					note.add("Paid $" + paidText.getText() + " leaving a balance of $" + balanceText.getText() + " for "
							+ fiscals.get(rowIndex).getFiscal_year());
					if(fiscals.get(rowIndex).getOther() != 0) note.add("Other expense: ");
				}
				setEditable(!commit.getValue());  // uses several methods to make editable or not
			}
		});
		
		SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, fiscals.get(rowIndex).getWet_slip());
		wetSlipSpinner.setValueFactory(wetSlipValueFactory);
		wetSlipSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
				  fiscals.get(rowIndex).setWet_slip(newValue);
				  updateBalance();
			});
	
		SpinnerValueFactory<Integer> beachValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getBeach());
		beachSpinner.setValueFactory(beachValueFactory);
		beachSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
				  fiscals.get(rowIndex).setBeach(newValue);
				  updateBalance();
			});
		
		SpinnerValueFactory<Integer> kayacRackValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getKayac_rack());
		kayakRackSpinner.setValueFactory(kayacRackValueFactory);
		kayakRackSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
				  fiscals.get(rowIndex).setKayac_rack(newValue);
				  updateBalance();
			});
		
		SpinnerValueFactory<Integer> kayakShedValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getKayac_shed());
		kayakShedSpinner.setValueFactory(kayakShedValueFactory);
		kayakShedSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
				  fiscals.get(rowIndex).setKayac_shed(newValue);
				  updateBalance();
			});
		
		SpinnerValueFactory<Integer> sailLoftValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, fiscals.get(rowIndex).getSail_loft());
		sailLoftSpinner.setValueFactory(sailLoftValueFactory);
		sailLoftSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
				  fiscals.get(rowIndex).setSail_loft(newValue);
				  updateBalance();
			});
		
		SpinnerValueFactory<Integer> sailSchoolLoftValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, fiscals.get(rowIndex).getSail_school_laser_loft());
		sailSchoolLoftSpinner.setValueFactory(sailSchoolLoftValueFactory);
		sailSchoolLoftSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
				  fiscals.get(rowIndex).setSail_school_laser_loft(newValue);
				  updateBalance();
			});
		
		SpinnerValueFactory<Integer> winterStorageValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getWinter_storage());
		winterStorageSpinner.setValueFactory(winterStorageValueFactory);
		winterStorageSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
				  fiscals.get(rowIndex).setWinter_storage(newValue);
				  updateBalance();
			});

		paidText.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	if(!isNumeric(paidText.getText())) {
	            		paidText.setText("0");
	            	}
	            	int newTotalValue = Integer.parseInt(paidText.getText());
	            	fiscals.get(rowIndex).setPaid(newTotalValue);
	            	SqlUpdate.updateField(newTotalValue, "money", "paid",fiscals,rowIndex);
	            	int balance = getBalance();
	            	balanceText.setText(balance + "");
	            	SqlUpdate.updateField(getBalance(), "money", "balance",fiscals,rowIndex);
	            	fiscals.get(rowIndex).setBalance(getBalance());
	            }
	        }
	    });
		
		totalWorkCreditTextField.textProperty().addListener((obs, oldText, newText) -> {
			int credit = Integer.parseInt(newText);
			fiscals.get(rowIndex).setCredit(countCredit(credit));
			creditText.setText(countCredit(credit) + "");  /// total credit
			balanceText.setText(getBalance() + "");  // sets balance textfield
			fiscals.get(rowIndex).setBalance(getBalance());  // sets focused object
		});
		
		totalKeyTextField.textProperty().addListener((obs, oldText, newText) -> {
			updateBalance();
		});
		
		yscText.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	if(!isNumeric(paidText.getText())) {
	            		yscText.setText("0");
	            	}
	            	int newTotalValue = Integer.parseInt(yscText.getText());
	            	fiscals.get(rowIndex).setYsc_donation(newTotalValue);
					fiscals.get(rowIndex).setTotal(updateTotalFeeFields());
					totalFeesText.setText(fiscals.get(rowIndex).getTotal() + "");
	            	balanceText.setText(getBalance() + "");
	            }
	        }
	    });
		
		otherText.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	if(!isNumeric(paidText.getText())) {
	            		otherText.setText("0");
	            	}
	            	int newTotalValue = Integer.parseInt(otherText.getText());
	            	fiscals.get(rowIndex).setOther(newTotalValue);
					fiscals.get(rowIndex).setTotal(updateTotalFeeFields());
					totalFeesText.setText(fiscals.get(rowIndex).getTotal() + "");
	            	balanceText.setText(getBalance() + "");
	            }
	        }
	    });
		
        addWorkCredits.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	new Dialogue_WorkCredits(selectedWorkCreditYear, totalWorkCreditTextField);
            }
        });
        
        addKeys.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	new Dialogue_Keys(fiscals.get(rowIndex), totalKeyTextField);
            }
        });
        
        //totalWorkCredits
		
		//////////////// SETTING CONTENT //////////////
		
		
		creditText.setText(fiscals.get(rowIndex).getCredit() + "");
		totalFeesText.setText(fiscals.get(rowIndex).getTotal() + "");
		paidText.setText(fiscals.get(rowIndex).getPaid() + "");
		duesText.setText(fiscals.get(rowIndex).getDues() + "");
		yscText.setText(fiscals.get(rowIndex).getYsc_donation() + "");
		otherText.setText(fiscals.get(rowIndex).getOther() + "");
		//wetSlipSpinner.setText(fiscals.get(rowIndex).getWet_slip() + "");
		
		totalWorkCreditTextField.setText(countWorkCredits() + "");
		totalKeyTextField.setText(countKeys() + "");
		if(hasOfficer && !SqlSelect.isCommitted(fiscals.get(rowIndex).getMoney_id())) { // has officer and not committed
			creditText.setText(duesText.getText()); // gets the dues and gives that amount of credit for being an officer
			SqlUpdate.updateField(Integer.parseInt(duesText.getText()), "money", "credit",fiscals,rowIndex); // updates
			fiscals.get(rowIndex).setCredit(Integer.parseInt(duesText.getText()));
			System.out.println("Has officer giving credit");
		} else {
			System.out.println("Has no officer or is committed");
		}
		updateBalance();
		balanceText.setText(getBalance() + "");
		if(fiscals.get(rowIndex).isCommitted()) setEditable(false);
		hboxDues.getChildren().addAll(new Label("Dues:"), duesText);
		hboxSubKey.getChildren().addAll(totalKeyTextField,addKeys);
		hboxSubWK.getChildren().addAll(totalWorkCreditTextField,addWorkCredits);
		hboxWinterStorage.getChildren().addAll(new Label("Winter Storage"), winterStorageSpinner);
		hboxKayac.getChildren().addAll(new Label("Kayac Rack"),kayakRackSpinner);
		hboxWetSlip.getChildren().addAll(new Label("wetSlip"),wetSlipSpinner);
		hboxYSC.getChildren().addAll(new Label("YSC Donation"),yscText);
		hboxOther.getChildren().addAll(new Label("Other:"), otherText);
		hboxPaid.getChildren().addAll(new Label("Paid"),paidText);
		hboxtotalWC.getChildren().addAll(new Label("Total:"),hboxSubWK);
		hboxtotalKey.getChildren().addAll(new Label("Total:"),hboxSubKey);
		hboxBeach.getChildren().addAll(new Label("Beach Spot"),beachSpinner);
		hboxKayakShed.getChildren().addAll(new Label("Kayak Shed"),kayakShedSpinner);
		hboxSailLoft.getChildren().addAll(new Label("Sail Loft"),sailLoftSpinner);
		hboxSailSchoolLoft.getChildren().addAll(new Label("Sail School Loft"),sailSchoolLoftSpinner);
		hboxTotalFees.getChildren().addAll(new Label("Total Fees"),totalFeesText);
		hboxCredit.getChildren().addAll(new Label("Credit"),creditText);
		hboxBalence.getChildren().addAll(new Label("Balance"),balanceText);
		hboxCheckBox.getChildren().add(commitCheckBox);
		vbox1.getChildren().addAll(comboHBox, keysLabel,hboxtotalKey, workCreditsLabel,hboxtotalWC, BalanceLabel,hboxTotalFees,hboxCredit,hboxPaid,hboxBalence,hboxCheckBox);
		vbox2.getChildren().addAll(feesLabel,hboxDues,hboxBeach,hboxKayac,hboxKayakShed,hboxSailLoft,hboxSailSchoolLoft,hboxWetSlip,hboxWinterStorage,hboxYSC,hboxOther);
		mainHbox.getChildren().addAll(vbox2,vbox1);
		mainVbox.getChildren().addAll(mainHbox);  // add error hbox in first
		vboxGrey.getChildren().addAll(mainVbox);
		getChildren().addAll(vboxGrey);
	}
	
	//////////////////////  CLASS METHODS ///////////////////////////
	
	private void setEditable(boolean isEditable) {
		changeState(yscText,isEditable,true);
		changeState(otherText,isEditable,true);
		changeState(paidText,isEditable,true);
		changeState(totalWorkCreditTextField,isEditable,false);
		changeState(totalKeyTextField,isEditable,false);
		changeState(duesText,isEditable,false);
		changeState(totalFeesText,isEditable,false);
		changeState(creditText,isEditable,false);
		//changeState(balanceText,isEditable,1);
		changeState(wetSlipSpinner,isEditable);
		changeState(beachSpinner,isEditable);
		changeState(kayakRackSpinner,isEditable);
		changeState(kayakShedSpinner,isEditable);
		changeState(sailLoftSpinner,isEditable);
		changeState(sailSchoolLoftSpinner,isEditable);
		changeState(winterStorageSpinner,isEditable);
	}
	
	private void changeState(TextField textField, boolean isEditable, boolean normallyEditable) {
		textField.setEditable(isEditable);
		if(isEditable) {
			if(normallyEditable)
			textField.setStyle(null);
			textField.setOpacity(1);
		} else {
			textField.setStyle(disabledColor);
			textField.setOpacity(0.5);
		}
	}
	
	private void changeState(Spinner<Integer> spinner, boolean isEditable) {
		spinner.setEditable(isEditable);
		if(isEditable) {
			spinner.setStyle(null);
		    spinner.setDisable(false);
		} else {
			spinner.setDisable(true);
			spinner.setStyle(disabledColor);
		}
	}
	
	private void updateBalance() {
		  fiscals.get(rowIndex).setTotal(updateTotalFeeFields());
		  totalFeesText.setText(fiscals.get(rowIndex).getTotal() + "");
		  balanceText.setText(getBalance() + "");
		  fiscals.get(rowIndex).setBalance(getBalance());
	}
	
	private int updateTotalFeeFields() {
		int dues = fiscals.get(rowIndex).getDues();
		int extraKey = fiscals.get(rowIndex).getExtra_key() * definedFees.getMain_gate_key();
		int sailLoftKey = fiscals.get(rowIndex).getSail_loft_key() * definedFees.getSail_loft_key();
		int kayakShedKey = fiscals.get(rowIndex).getKayac_shed_key() * definedFees.getKayak_shed_key();
		int sailSchoolLoftKey = fiscals.get(rowIndex).getSail_school_loft_key() * definedFees.getSail_school_loft_key();
		int beachSpot = fiscals.get(rowIndex).getBeach() * definedFees.getBeach();
		int kayakRack = fiscals.get(rowIndex).getKayac_rack() * definedFees.getKayak_rack();
		int kayakShed = fiscals.get(rowIndex).getKayac_shed() * definedFees.getKayak_shed();
		int sailLoft = fiscals.get(rowIndex).getSail_loft() * definedFees.getSail_loft();
		int sailSchoolLoft = fiscals.get(rowIndex).getSail_school_laser_loft() * definedFees.getSail_school_laser_loft();
		int wetSlip = fiscals.get(rowIndex).getWet_slip() * definedFees.getWet_slip();
		int winterStorage = fiscals.get(rowIndex).getWinter_storage() * definedFees.getWinter_storage();
		int yscDonation = fiscals.get(rowIndex).getYsc_donation();
		int other = fiscals.get(rowIndex).getOther();
		
		return extraKey + sailLoftKey + kayakShedKey + sailSchoolLoftKey + beachSpot + kayakRack
				+kayakShed + sailLoft + sailSchoolLoft + wetSlip + winterStorage + yscDonation + dues + other;
	}
	
	private int getBalance() {
		return fiscals.get(rowIndex).getTotal() - fiscals.get(rowIndex).getPaid() - fiscals.get(rowIndex).getCredit();
	}
	
	private int countCredit(int workCredits) {
		int credit = 0;
		if(membershipHasOfficer()) {
			credit = fiscals.get(rowIndex).getOfficer_credit();  // inserts credit for member type into fiscal
		} else {
			if(workCredits >= 15) {
				credit = 150;
			} else {
			credit = workCredits * 10;  // work credits are worth $10 each, this may change some day.
			// TODO need to add 10 dollar reference in defined fees
			}
		}
		return credit;
	}
	
	private Boolean membershipHasOfficer() {
		Boolean isOfficer = false;
		Boolean finalResult = false;
		for (Object_Person per : people) {
			isOfficer = SqlExists.isOfficer(per, fiscals.get(rowIndex).getFiscal_year());
			if(isOfficer) {  // we will add in pid here if need be
				finalResult = true;
				isOfficer = false;  // reset for next iteration
				this.officer = SqlSelect.getOfficer(per.getP_id(),fiscals.get(rowIndex).getFiscal_year());
			}
		}
		return finalResult;
	}
	
	private int countWorkCredits() {
		int total = selectedWorkCreditYear.getRacing() + selectedWorkCreditYear.getSocial()
				+ selectedWorkCreditYear.getOther()+ selectedWorkCreditYear.getHarbor();
		return total;
	}
	
	private int countKeys() {
		int total = fiscals.get(rowIndex).getExtra_key() + fiscals.get(rowIndex).getKayac_shed_key()
				+ fiscals.get(rowIndex).getSail_loft_key() + fiscals.get(rowIndex).getSail_school_loft_key();
		return total;
	}
	
	public boolean isStringInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) return false;
	    try {
	        Integer.parseInt(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
} // updateKayakShed
