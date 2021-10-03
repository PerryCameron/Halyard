package com.ecsail.gui.boxes;

import com.ecsail.gui.tabs.TabBalance;
import com.ecsail.gui.tabs.TabCredit;
import com.ecsail.gui.tabs.TabKey;
import com.ecsail.gui.tabs.TabPayment;
import com.ecsail.main.Note;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlSelect;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;

public class BoxFiscalOriginal extends HBox {
	private ObservableList<Object_Money> fiscals = null;
	Object_Membership membership;
	Object_DefinedFee definedFees;
	Object_WorkCredit selectedWorkCreditYear;
	Object_Officer officer;
	private ObservableList<Object_Person> people;
	boolean hasOfficer;
	Object_Integer numberOfKeys;
	Object_Integer workCredits;
	private int rowIndex;
	private Note note;
	private final TextField yscText = new TextField();
	Object_BalanceText textFields = new Object_BalanceText();

	private final TextField duesText;
	private final TextField slipText = new TextField();
	private final TextField otherText = new TextField();
	private final TextField initiationText = new TextField();
	private final Spinner<Integer> beachSpinner = new Spinner<Integer>();
	private final Spinner<Integer> kayakRackSpinner = new Spinner<Integer>();
	private final Spinner<Integer> kayakShedSpinner = new Spinner<Integer>();
	private final Spinner<Integer> sailLoftSpinner = new Spinner<Integer>();
	private final Spinner<Integer> sailSchoolLoftSpinner = new Spinner<Integer>();
	private final Spinner<Integer> winterStorageSpinner = new Spinner<Integer>();
	private final String disabledColor = "-fx-background-color: #d5dade";
	boolean isCommited;
	Button addWetSlip = new Button();

	public BoxFiscalOriginal(Object_Membership m, ObservableList<Object_Person> p, ObservableList<Object_Money> o, int r, Note n, TextField dt) {
		this.membership = m;
		this.people = p;
		this.rowIndex = r;
		this.fiscals = o;
		this.note = n;
		this.duesText = dt;
		this.selectedWorkCreditYear = SqlSelect.getWorkCredit(fiscals.get(rowIndex).getMoney_id());
		this.definedFees = SqlSelect.selectDefinedFees(fiscals.get(rowIndex).getFiscal_year());
		this.hasOfficer = membershipHasOfficer();
		this.isCommited = fiscals.get(rowIndex).isCommitted();
		this.numberOfKeys = new Object_Integer(0);
		this.workCredits = new Object_Integer(0);
		////////////// OBJECTS /////////////////////
		TabPane MoneyTabPane = new TabPane();
		TabPane keysAndCreditsTabPane = new TabPane();
		//System.out.println("Getting balanceText() :" + textFields.getBalanceText());
		TabBalance moneyTab = new TabBalance("Balance", textFields);
		TabPayment paymentTab = new TabPayment("Payment",fiscals.get(rowIndex),textFields);
		TabKey keyTab = new TabKey("Keys",fiscals.get(rowIndex), numberOfKeys);
		TabCredit creditTab = new TabCredit("Work Credits", selectedWorkCreditYear, workCredits);
		
		Image image = new Image(getClass().getResourceAsStream("/Arrow.png"));
		Label workCreditsLabel = new Label("Work Credits");
		Label feesLabel = new Label("Fees");
		Label BalanceLabel = new Label("Balance");
		Label keysLabel = new Label("Keys");

		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox mainVbox = new VBox();
		HBox mainHbox = new HBox();
		VBox vboxTabPanes = new VBox();
		VBox vboxSpinners = new VBox();
		HBox hboxButtonCommit = new HBox();
		HBox hboxSlip = new HBox();
		
		
		HBox hboxDues = new HBox();
		HBox hboxBeach = new HBox();
		HBox hboxKayac = new HBox();
		HBox hboxKayakShed = new HBox();
		HBox hboxSailLoft = new HBox();
		HBox hboxSailSchoolLoft = new HBox();
		HBox hboxWetSlip = new HBox();
		HBox hboxWinterStorage = new HBox();
		HBox hboxYSC = new HBox();
		HBox hboxInitiation = new HBox();
		HBox hboxOther = new HBox();
		
		VBox vboxDuesLabel = new VBox();
		VBox vboxBeachLabel = new VBox();
		VBox vboxKayacLabel = new VBox();
		VBox vboxKayakShedLabel = new VBox();
		VBox vboxSailLoftLabel = new VBox();
		VBox vboxSailSchoolLoftLabel = new VBox();
		VBox vboxWetSlipLabel = new VBox();
		VBox vboxWinterStorageLabel = new VBox();
		VBox vboxYSCLabel = new VBox();
		VBox vboxInitiationLabel = new VBox();
		VBox vboxOtherLabel = new VBox();
		
		VBox vboxDuesBox = new VBox();
		VBox vboxBeachBox = new VBox();
		VBox vboxKayacBox = new VBox();
		VBox vboxKayakShedBox = new VBox();
		VBox vboxSailLoftBox = new VBox();
		VBox vboxSailSchoolLoftBox = new VBox();
		VBox vboxWetSlipBox = new VBox();
		VBox vboxWinterStorageBox = new VBox();
		VBox vboxYSCBox = new VBox();
		VBox vboxInitiationBox = new VBox();
		VBox vboxOtherBox = new VBox();
		Button addWetSlip = new Button();
		Region spacer1 = new Region();

		//////////////// ATTRIBUTES ///////////////////
		MoneyTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		workCreditsLabel.setId("bold-label");
		feesLabel.setId("bold-label");
		BalanceLabel.setId("bold-label");
		keysLabel.setId("bold-label");
		addWetSlip.setId("default-button");
		addWetSlip.setGraphic(new ImageView(image));
		
		int width = 100;
		vboxDuesLabel.setPrefWidth(width);
		vboxBeachLabel.setPrefWidth(width);
		vboxKayacLabel.setPrefWidth(width);
		vboxKayakShedLabel.setPrefWidth(width);
		vboxSailLoftLabel.setPrefWidth(width);
		vboxSailSchoolLoftLabel.setPrefWidth(width);
		vboxWetSlipLabel.setPrefWidth(width);
		vboxWinterStorageLabel.setPrefWidth(width);
		vboxYSCLabel.setPrefWidth(width);
		vboxInitiationLabel.setPrefWidth(width);
		vboxOtherLabel.setPrefWidth(width);
		
		vboxDuesLabel.setAlignment(Pos.CENTER_LEFT);
		vboxBeachLabel.setAlignment(Pos.CENTER_LEFT);
		vboxKayacLabel.setAlignment(Pos.CENTER_LEFT);
		vboxKayakShedLabel.setAlignment(Pos.CENTER_LEFT);
		vboxSailLoftLabel.setAlignment(Pos.CENTER_LEFT);
		vboxSailSchoolLoftLabel.setAlignment(Pos.CENTER_LEFT);
		vboxWetSlipLabel.setAlignment(Pos.CENTER_LEFT);
		vboxWinterStorageLabel.setAlignment(Pos.CENTER_LEFT);
		vboxYSCLabel.setAlignment(Pos.CENTER_LEFT);
		vboxInitiationLabel.setAlignment(Pos.CENTER_LEFT);
		vboxOtherLabel.setAlignment(Pos.CENTER_LEFT);
		
		winterStorageSpinner.setPrefWidth(65);
		kayakRackSpinner.setPrefWidth(65);
		slipText.setPrefWidth(40);
		addWetSlip.setPrefWidth(25);
		addWetSlip.setPrefHeight(25);
		yscText.setPrefWidth(65);
		otherText.setPrefWidth(65);
		initiationText.setPrefWidth(65);
		duesText.setPrefWidth(65);
		beachSpinner.setPrefWidth(65);
		kayakShedSpinner.setPrefWidth(65);
		sailLoftSpinner.setPrefWidth(65);
		sailSchoolLoftSpinner.setPrefWidth(65);
		duesText.setStyle(disabledColor);		
		spacer1.setPrefWidth(40);
		
		vboxTabPanes.setAlignment(Pos.CENTER);
		vboxSpinners.setAlignment(Pos.CENTER);
		
		vboxTabPanes.setSpacing(5);
		vboxSpinners.setSpacing(5);
		mainHbox.setSpacing(10);
		
		
		
		
		textFields.getPaidText().setEditable(false);
		textFields.getCreditText().setEditable(false);
		textFields.getTotalFeesText().setEditable(false);
		
		BalanceLabel.setPadding(new Insets(20, 0, 0, 0));
		workCreditsLabel.setPadding(new Insets(20, 0, 0, 0));
		this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		vboxGrey.setPadding(new Insets(8, 5, 0, 15));
		hboxButtonCommit.setPadding(new Insets(5, 0, 5, 170));	
		
		setId("box-blue");
		vboxGrey.setId("box-grey");

		HBox.setHgrow(vboxGrey, Priority.ALWAYS);
		HBox.setHgrow(spacer1, Priority.ALWAYS);
		//////////////// LISTENER //////////////////
			
		textFields.getBalanceText().textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("0"))
				textFields.getBalanceText().setStyle("-fx-background-color: #30e65a");
		});
		
		// this is only called if you changer membership type or open a record or manually type in
		duesText.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!SqlSelect.isCommitted(fiscals.get(rowIndex).getMoney_id())) {
				String newDues = newValue;
				System.out.println(" dues textfield set to " + newValue);
				fiscals.get(rowIndex).setDues(newDues);
				updateBalance();
				SqlUpdate.updateMoney(fiscals.get(rowIndex));
			} else {
				System.out.println("Record is commited, no changes made");
			}
		});
		
		duesText.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	if(!isNumeric(duesText.getText())) {
	            		duesText.setText("0");
	            	}
	            	BigDecimal dues = new BigDecimal(duesText.getText());
	            	updateItem(dues,"dues");
					duesText.setText(String.valueOf(dues.setScale(2)));
	            	updateBalance();
	            }
	        });

        textFields.getCommitButton().setOnAction((event) -> {
            	if (!fiscals.get(rowIndex).isCommitted()) { 
            		if (!textFields.getBalanceText().getText().equals("0"))
					textFields.getBalanceText().setStyle("-fx-background-color: #f23a50");
            		System.out.println("total at commit is " + fiscals.get(rowIndex).getTotal());
            		SqlUpdate.updateMoney(fiscals.get(rowIndex)); 
            		SqlUpdate.commitFiscalRecord(fiscals.get(rowIndex).getMoney_id(), true);// this could be placed in line above
            		String date = SqlSelect.getPaymentDate(fiscals.get(rowIndex).getMoney_id()); // dates note to check
            		// update membership_id record to renew or non-renew
            		SqlUpdate.updateMembershipId(fiscals.get(rowIndex).getMs_id(), fiscals.get(rowIndex).getFiscal_year(), textFields.getRenewCheckBox().isSelected());
            		fiscals.get(rowIndex).setCommitted(true);
            		addPaidNote(date);
            		// if we put an amount in other we need to make a note
            		if(Integer.parseInt(fiscals.get(rowIndex).getOther()) != 0) {
            			// make sure the memo dosen't already exist
            			if(!SqlExists.memoExists(fiscals.get(rowIndex).getMoney_id())) 
            			note.add("Other expense: ",date,fiscals.get(rowIndex).getMoney_id(),"O");
            		}
            		setEditable(false);
            	} else {
				setEditable(true);
				fiscals.get(rowIndex).setCommitted(false);
				SqlUpdate.commitFiscalRecord(fiscals.get(rowIndex).getMoney_id(), false);
            	}
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
				  SqlUpdate.updateMoney(fiscals.get(rowIndex));
			});
		
		textFields.getPaidText().textProperty().addListener((obd, oldValue, newValue) -> {
        	if(!isNumeric(textFields.getPaidText().getText())) {  // we should move this to amount in TabPayment
        		textFields.getPaidText().setText("0.00");
        	}
        	BigDecimal newTotalValue = new BigDecimal(textFields.getPaidText().getText());
        	fiscals.get(rowIndex).setPaid(textFields.getPaidText().getText());
        	SqlUpdate.updateField(newTotalValue, "money", "paid",fiscals,rowIndex);
        	BigDecimal balance = getBalance();
        	textFields.getBalanceText().setText(balance + "");
        	SqlUpdate.updateField(getBalance(), "money", "balance",fiscals,rowIndex);
        	fiscals.get(rowIndex).setBalance(String.valueOf(getBalance()));
		});
		
		yscText.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	if(!isNumeric(yscText.getText())) {
	            		yscText.setText("0.00");
	            	}
					BigDecimal ysc = new BigDecimal(yscText.getText());
	            	updateItem(ysc, "ysc");
					yscText.setText(String.valueOf(ysc.setScale(2)));
	            	updateBalance();
	            }
	        });
		
		slipText.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	if(!isNumeric(otherText.getText())) {
	            		otherText.setText("0.00");
	            	}
					BigDecimal slip = new BigDecimal(slipText.getText());
					slip.setScale(2);
	            	updateItem(slip,"wetslip");
					slipText.setText(String.valueOf(slip.setScale(2)));
	            	updateBalance();
	            }
	        });
		
		otherText.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	if(!isNumeric(otherText.getText())) {
	            		otherText.setText("0.00");
	            	}
					BigDecimal other = new BigDecimal(otherText.getText());
	            	updateItem(other,"other");
					otherText.setText(String.valueOf(other.setScale(2)));
	            	updateBalance();
	            }
	        });
		
		initiationText.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	if(!isNumeric(initiationText.getText())) {
	            		initiationText.setText("0.00");
	            	}
					BigDecimal initiation = new BigDecimal(initiationText.getText());
	            	updateItem(initiation, "initiation");
					initiationText.setText(String.valueOf(initiation.setScale(2)));
	            	updateBalance();
	            }
	        });
		
		numberOfKeys.integerProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
		        updateBalance();
		        SqlUpdate.updateMoney(fiscals.get(rowIndex));
		    });
		
		workCredits.integerProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
		    	fiscals.get(rowIndex).setCredit(String.valueOf(countCredit((int)newValue)));
		    	textFields.getCreditText().setText(countCredit((int)newValue) + "");
		    	textFields.getBalanceText().setText(getBalance() + "");  // sets balance textfield in balance tab
				fiscals.get(rowIndex).setBalance(String.valueOf(getBalance()));  // sets focused object
		        updateBalance();
		        SqlUpdate.updateMoney(fiscals.get(rowIndex));
		    });
        
        addWetSlip.setOnAction((event) -> {
            	slipText.setText(definedFees.getWet_slip() + "");
            	updateItem(new BigDecimal(slipText.getText()),"wetslip");
            	updateBalance();
            	SqlUpdate.updateMoney(fiscals.get(rowIndex));
            });
		
		//////////////// SETTING CONTENT //////////////
		
		textFields.getCreditText().setText(fiscals.get(rowIndex).getCredit() + "");
		textFields.getTotalFeesText().setText(fiscals.get(rowIndex).getTotal() + "");
		textFields.getPaidText().setText(fiscals.get(rowIndex).getPaid() + "");
		slipText.setText(fiscals.get(rowIndex).getWet_slip() +"");
		duesText.setText(fiscals.get(rowIndex).getDues() + "");
		yscText.setText(fiscals.get(rowIndex).getYsc_donation() + "");
		otherText.setText(fiscals.get(rowIndex).getOther() + "");
		initiationText.setText(fiscals.get(rowIndex).getInitiation() + "");
		
		if (fiscals.get(rowIndex).isSupplemental()) {
			duesText.setEditable(true);
			//duesText.setText("0");
		} else {
			if (hasOfficer) { // has officer and not
				System.out.println("Member is an officer");
					if(!SqlSelect.isCommitted(fiscals.get(rowIndex).getMoney_id()))	{	// is not committed	
						// committed
						textFields.getCreditText().setText(duesText.getText()); // gets the dues and gives that amount of credit for being an officer
						SqlUpdate.updateField(new BigDecimal(duesText.getText()), "money", "credit", fiscals, rowIndex); // updates SQL
						fiscals.get(rowIndex).setCredit(duesText.getText());  // sets credit for what dues are
						System.out.println("Record is not committed");
					}
			} else {
				System.out.println("Member is not an officer of the club");
			}
		}
		if(fiscals.get(rowIndex).isCommitted()) setEditable(false);	

		updateBalance();
		textFields.getBalanceText().setText(getBalance() + "");	
		MoneyTabPane.getTabs().addAll(moneyTab,paymentTab);
		keysAndCreditsTabPane.getTabs().addAll(keyTab, creditTab);
		hboxSlip.getChildren().addAll(slipText,addWetSlip);
		
		vboxDuesLabel.getChildren().add(new Label("Dues:"));
		vboxBeachLabel.getChildren().add(new Label("Beach Spot:"));
		vboxKayacLabel.getChildren().add(new Label("Kayak Rack:"));
		vboxKayakShedLabel.getChildren().add(new Label("Kayak Shed:"));
		vboxSailLoftLabel.getChildren().add(new Label("Sail Loft:"));
		vboxSailSchoolLoftLabel.getChildren().add(new Label("Sail School Loft:"));
		vboxWetSlipLabel.getChildren().add(new Label("Wet Slip:"));
		vboxWinterStorageLabel.getChildren().add(new Label("Winter Storage:"));
		vboxYSCLabel.getChildren().add(new Label("YSP Donation:"));
		vboxInitiationLabel.getChildren().add(new Label("Initiation:"));
		vboxOtherLabel.getChildren().add(new Label("Other:"));
		
		vboxDuesBox.getChildren().add(duesText);
		vboxBeachBox.getChildren().add(beachSpinner);
		vboxKayacBox.getChildren().add(kayakRackSpinner);
		vboxKayakShedBox.getChildren().add(kayakShedSpinner);
		vboxSailLoftBox.getChildren().add(sailLoftSpinner);
		vboxSailSchoolLoftBox.getChildren().add(sailSchoolLoftSpinner);
		vboxWetSlipBox.getChildren().add(hboxSlip);
		vboxWinterStorageBox.getChildren().add(winterStorageSpinner);
		vboxYSCBox.getChildren().add(yscText);
		vboxInitiationBox.getChildren().add(initiationText);
		vboxOtherBox.getChildren().add(otherText);
		
		hboxDues.getChildren().addAll(vboxDuesLabel,vboxDuesBox);
		hboxBeach.getChildren().addAll(vboxBeachLabel,vboxBeachBox);
		hboxKayac.getChildren().addAll(vboxKayacLabel,vboxKayacBox);
		hboxKayakShed.getChildren().addAll(vboxKayakShedLabel,vboxKayakShedBox);
		hboxSailLoft.getChildren().addAll(vboxSailLoftLabel,vboxSailLoftBox);
		hboxSailSchoolLoft.getChildren().addAll(vboxSailSchoolLoftLabel,vboxSailSchoolLoftBox);
		hboxWetSlip.getChildren().addAll(vboxWetSlipLabel,vboxWetSlipBox);
		hboxWinterStorage.getChildren().addAll(vboxWinterStorageLabel,vboxWinterStorageBox);
		hboxYSC.getChildren().addAll(vboxYSCLabel,vboxYSCBox);
		hboxInitiation.getChildren().addAll(vboxInitiationLabel,vboxInitiationBox);
		hboxOther.getChildren().addAll(vboxOtherLabel,vboxOtherBox);
		
		vboxTabPanes.getChildren().addAll(keysAndCreditsTabPane,MoneyTabPane);
		vboxSpinners.getChildren().addAll(feesLabel,hboxDues,hboxBeach,hboxKayac,hboxKayakShed,hboxSailLoft,hboxSailSchoolLoft,hboxWetSlip,hboxWinterStorage,hboxYSC,hboxInitiation, hboxOther);
		mainHbox.getChildren().addAll(vboxSpinners,spacer1,vboxTabPanes);
		mainVbox.getChildren().addAll(mainHbox);  // add error hbox in first
		vboxGrey.getChildren().addAll(mainVbox);
		getChildren().addAll(vboxGrey);
	}
	
	//////////////////////  CLASS METHODS ///////////////////////////
	private void updateItem(BigDecimal newTotalValue, String type) {
		switch(type) {
		case "initiation":
			fiscals.get(rowIndex).setInitiation(String.valueOf(newTotalValue));
			break;
		case "other":
			fiscals.get(rowIndex).setOther(String.valueOf(newTotalValue));
			break;
		case "ysc":
			fiscals.get(rowIndex).setYsc_donation(String.valueOf(newTotalValue));
			break;
		case "dues":
			fiscals.get(rowIndex).setDues(String.valueOf(newTotalValue));
			break;
		case "wetslip":
			fiscals.get(rowIndex).setWet_slip(String.valueOf(newTotalValue));
			break;
		}
		fiscals.get(rowIndex).setTotal(String.valueOf(updateTotalFeeFields()));
		textFields.getTotalFeesText().setText(fiscals.get(rowIndex).getTotal() + "");
    	textFields.getBalanceText().setText(getBalance() + "");
	}
	
	
	private void addPaidNote(String date) {
		note.add("Paid $" + textFields.getPaidText().getText() + " leaving a balance of $" + textFields.getBalanceText().getText() + " for "
				+ fiscals.get(rowIndex).getFiscal_year(), date,0,"P");
	}
	
	private void setEditable(boolean isEditable) {
		changeState(yscText,isEditable,true);
		changeState(duesText,isEditable,true);
		changeState(otherText,isEditable,true);
		changeState(initiationText,isEditable,true);
		changeState(slipText,isEditable,true);
		changeState(addWetSlip,isEditable);
		changeState(beachSpinner,isEditable);
		changeState(kayakRackSpinner,isEditable);
		changeState(kayakShedSpinner,isEditable);
		changeState(sailLoftSpinner,isEditable);
		changeState(sailSchoolLoftSpinner,isEditable);
		changeState(winterStorageSpinner,isEditable);
	}
	
	private void changeState(Button button, boolean isEditable) {
		button.setDisable(!isEditable);
		if(isEditable) {
			button.setStyle(null);
			button.setOpacity(1);
		} else {
			button.setStyle(disabledColor);
			button.setOpacity(0.5);
		}
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
		  fiscals.get(rowIndex).setTotal(String.valueOf(updateTotalFeeFields()));
		  System.out.println("total at update balance is " + fiscals.get(rowIndex).getTotal());
		  textFields.getTotalFeesText().setText(fiscals.get(rowIndex).getTotal() + "");
		  textFields.getBalanceText().setText(getBalance() + "");
		  fiscals.get(rowIndex).setBalance(String.valueOf(getBalance()));
	}
	
	private BigDecimal updateTotalFeeFields() {
		BigDecimal dues = new BigDecimal(fiscals.get(rowIndex).getDues());
		BigDecimal extraKey = new BigDecimal(fiscals.get(rowIndex).getExtra_key()).multiply(definedFees.getMain_gate_key());
		BigDecimal sailLoftKey = new BigDecimal(fiscals.get(rowIndex).getSail_loft_key()).multiply(definedFees.getSail_loft_key());
		BigDecimal kayakShedKey = new BigDecimal(fiscals.get(rowIndex).getKayac_shed_key()).multiply(definedFees.getKayak_shed_key());
		BigDecimal sailSchoolLoftKey = new BigDecimal(fiscals.get(rowIndex).getSail_school_loft_key()).multiply(definedFees.getSail_school_loft_key());
		BigDecimal beachSpot = new BigDecimal(fiscals.get(rowIndex).getBeach()).multiply(definedFees.getBeach());
		BigDecimal kayakRack = new BigDecimal(fiscals.get(rowIndex).getKayac_rack()).multiply(definedFees.getKayak_rack());
		BigDecimal kayakShed = new BigDecimal(fiscals.get(rowIndex).getKayac_shed()).multiply(definedFees.getKayak_shed());
		BigDecimal sailLoft = new BigDecimal(fiscals.get(rowIndex).getSail_loft()).multiply(definedFees.getSail_loft());
		BigDecimal sailSchoolLoft = new BigDecimal(fiscals.get(rowIndex).getSail_school_laser_loft()).multiply(definedFees.getSail_school_laser_loft());
		BigDecimal wetSlip = new BigDecimal(fiscals.get(rowIndex).getWet_slip());
		BigDecimal winterStorage = new BigDecimal(fiscals.get(rowIndex).getWinter_storage()).multiply(definedFees.getWinter_storage());
		BigDecimal yscDonation = new BigDecimal(fiscals.get(rowIndex).getYsc_donation());
		BigDecimal other = new BigDecimal(fiscals.get(rowIndex).getOther());
		BigDecimal initiation = new BigDecimal(fiscals.get(rowIndex).getInitiation());
		return extraKey.add(sailLoftKey).add(kayakShedKey).add(sailSchoolLoftKey).add(beachSpot).add(kayakRack).add(kayakShed).add(sailLoft).add(sailSchoolLoft).add(wetSlip).add(winterStorage).add(yscDonation).add(dues).add(other).add(initiation);
	}

	private BigDecimal getBalance() {
		BigDecimal total = new BigDecimal(fiscals.get(rowIndex).getTotal());
		BigDecimal paid = new BigDecimal(fiscals.get(rowIndex).getPaid());
		BigDecimal credit = new BigDecimal(fiscals.get(rowIndex).getCredit());
		return total.subtract(paid).subtract(credit);
	}

	private BigDecimal countCredit(int workCredits) {
		BigDecimal credit;
		if(membershipHasOfficer()) {
			credit = new BigDecimal(fiscals.get(rowIndex).getOfficer_credit());  // inserts credit for member type into fiscal
			//System.out.println("Has an officer credit changed to=" + credit);
		} else {
			credit = definedFees.getWork_credit().multiply(BigDecimal.valueOf(workCredits));
		}
		//System.out.println("Credit is " + credit);
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

	public static boolean isNumeric(String str) {
		try {
			new BigDecimal(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
} // updateKayakShed
