package com.ecsail.main;

import com.ecsail.structures.Object_DefinedFee;
import com.ecsail.structures.Object_Membership;
import com.ecsail.structures.Object_Money;
import com.ecsail.structures.Object_Officer;
import com.ecsail.structures.Object_Person;
import com.ecsail.structures.Object_WorkCredit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
	
	//private final TextField yearText = new TextField();
	private final TextField yscText = new TextField();
	private final TextField paidText = new TextField();
	private final TextField totalWCText = new TextField();
	private final TextField duesText = new TextField();
	private final TextField totalFeesText = new TextField();
	private final TextField creditText = new TextField();
	private final TextField balanceText = new TextField();
	private final Spinner<Integer> wetSlipSpinner = new Spinner<Integer>();
	private final Spinner<Integer> extraKeySpinner = new Spinner<Integer>();
	private final Spinner<Integer> sailLKeySpinner = new Spinner<Integer>();
	private final Spinner<Integer> kayakSKeySpinner = new Spinner<Integer>();
	private final Spinner<Integer> sailSSLKeySpinner = new Spinner<Integer>();
	private final Spinner<Integer> beachSpinner = new Spinner<Integer>();
	private final Spinner<Integer> kayakRackSpinner = new Spinner<Integer>();
	private final Spinner<Integer> kayakShedSpinner = new Spinner<Integer>();
	private final Spinner<Integer> sailLoftSpinner = new Spinner<Integer>();
	private final Spinner<Integer> sailSchoolLoftSpinner = new Spinner<Integer>();
	private final Spinner<Integer> winterStorageSpinner = new Spinner<Integer>();
	private final Spinner<Integer> racingSpinner = new Spinner<Integer>();
	private final Spinner<Integer> harborSpinner = new Spinner<Integer>();
	private final Spinner<Integer> socialSpinner = new Spinner<Integer>();
	private final Spinner<Integer> otherSpinner = new Spinner<Integer>();
	private final String disabledColor = "-fx-background-color: #d5dade";
	
	public BoxFiscal(Object_Membership m, ObservableList<Object_Person> p, ObservableList<Object_Money> o, int r, Note n) {
		this.membership = m;
		this.people = p;
		this.rowIndex = r;
		this.fiscals = o;
		this.note = n;
		this.selectedWorkCreditYear = SqlSelect.getWorkCredit(fiscals.get(rowIndex).getMoney_id());
		this.definedFees = SqlSelect.selectDefinedFees(fiscals.get(rowIndex).getFiscal_year());
		this.hasOfficer = membershipHasOfficer();

		////////////// OBJECTS /////////////////////
		
		Label workCreditsLabel = new Label("Work Credits");
		Label feesLabel = new Label("Fees");
		Label BalanceLabel = new Label("Balance");

		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox mainVbox = new VBox();
		HBox mainHbox = new HBox();
		HBox comboHBox = new HBox();
		HBox hboxRacing = new HBox();
		HBox hboxHarbor = new HBox();
		HBox hboxSocial = new HBox();
		HBox hboxOther = new HBox();
		//HBox hboxYear = new HBox();
		HBox hboxKey = new HBox();
		HBox hboxWinterStorage = new HBox();
		HBox hboxKayac = new HBox();
		HBox hboxWetSlip = new HBox();
		HBox hboxYSC = new HBox();
		HBox hboxPaid = new HBox();
		HBox hboxDues = new HBox();
		HBox hboxtotalWC = new HBox();
		HBox hboxSLKey = new HBox();
		HBox hboxKSKey = new HBox();
		HBox hboxSSLKey = new HBox();
		HBox hboxBeach = new HBox();
		HBox hboxKayakShed = new HBox();
		HBox hboxSailLoft = new HBox();
		HBox hboxSailSchoolLoft = new HBox();
		HBox hboxTotalFees = new HBox();
		HBox hboxCredit = new HBox();
		HBox hboxBalence = new HBox();
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		CheckBox commitCheckBox = new CheckBox("Commit");
		


		//////////////// ATTRIBUTES ///////////////////
		workCreditsLabel.setId("bold-label");
		feesLabel.setId("bold-label");
		BalanceLabel.setId("bold-label");
		
		hboxRacing.setSpacing(30);
		hboxHarbor.setSpacing(28);
		hboxSocial.setSpacing(35);
		hboxOther.setSpacing(35);
		//hboxYear.setSpacing(28);
		hboxDues.setSpacing(78);
		hboxKey.setSpacing(58.5);
		hboxWinterStorage.setSpacing(28);
		hboxKayac.setSpacing(48.5);  // kayak rack
		hboxWetSlip.setSpacing(68.5);
		hboxYSC.setSpacing(35);
		hboxPaid.setSpacing(43);
		hboxtotalWC.setSpacing(36.5);
		hboxSLKey.setSpacing(42.5);
		hboxKSKey.setSpacing(24);
		hboxSSLKey.setSpacing(4);
		hboxBeach.setSpacing(48);
		hboxKayakShed.setSpacing(47); // kayak shed
		hboxSailLoft.setSpacing(65);  // sail loft
		hboxSailSchoolLoft.setSpacing(25.5);
		hboxTotalFees.setSpacing(12);
		hboxCredit.setSpacing(34);
		hboxBalence.setSpacing(25);
		
		racingSpinner.setPrefWidth(60);
		harborSpinner.setPrefWidth(60);
		socialSpinner.setPrefWidth(60);
		otherSpinner.setPrefWidth(60);
		//yearText.setPrefWidth(60);
		winterStorageSpinner.setPrefWidth(60);
		kayakRackSpinner.setPrefWidth(60);
		wetSlipSpinner.setPrefWidth(60);
		yscText.setPrefWidth(60);
		paidText.setPrefWidth(60);
		totalWCText.setPrefWidth(60);
		duesText.setPrefWidth(60);
		sailLKeySpinner.setPrefWidth(60);
		kayakSKeySpinner.setPrefWidth(60);
		sailSSLKeySpinner.setPrefWidth(60);
		beachSpinner.setPrefWidth(60);
		kayakShedSpinner.setPrefWidth(60);
		sailLoftSpinner.setPrefWidth(60);
		sailSchoolLoftSpinner.setPrefWidth(60);
		totalFeesText.setPrefWidth(60);
		creditText.setPrefWidth(60);
		balanceText.setPrefWidth(60);
		extraKeySpinner.setPrefWidth(60);
		
		totalFeesText.setStyle(disabledColor);
		duesText.setStyle(disabledColor);
		creditText.setStyle(disabledColor);
		totalWCText.setStyle(disabledColor);
		balanceText.setStyle("-fx-background-color: #9fc0c7");
		vbox1.setAlignment(Pos.CENTER);
		vbox2.setAlignment(Pos.CENTER);
		vbox1.setSpacing(5);
		vbox2.setSpacing(5);
		//vbox2.setPadding(new Insets(2, 0, 0, 0));
		totalWCText.setEditable(false);
		duesText.setEditable(false);
		totalFeesText.setEditable(false);
		creditText.setEditable(false);
		balanceText.setEditable(false);
		comboHBox.setPadding(new Insets(0, 0, 10, 0));  // sets height of work credits label
		BalanceLabel.setPadding(new Insets(20, 0, 0, 0));
		comboHBox.setAlignment(Pos.TOP_RIGHT);
		commitCheckBox.setSelected(fiscals.get(rowIndex).isCommitted());
		setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		
		setId("box-blue");
		vboxGrey.setId("box-grey");
		mainHbox.setSpacing(50);
		vboxGrey.setPadding(new Insets(8, 10, 0, 30));
		vboxGrey.setPrefWidth(460);
		
		//////////////// LISTENER //////////////////
		
		balanceText.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("0"))
				balanceText.setStyle("-fx-background-color: #30e65a");
		    //System.out.println("textfield changed from " + oldValue + " to " + newValue);
		});
		
		commitCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> commit, Boolean oldValue, Boolean newValue) {
            	if(balanceText.getText().equals("0")) {
            	SqlUpdate.commitFiscalRecord(fiscals.get(rowIndex).getMoney_id(), commit.getValue());
            	fiscals.get(rowIndex).setCommitted(commit.getValue());
            	setEditable(!commit.getValue());
            	note.add("Paid $" + paidText.getText() + " leaving a balance of " + balanceText.getText() + " for " + fiscals.get(rowIndex).getFiscal_year());
            	} else {
            		commitCheckBox.setSelected(false);
            		balanceText.setStyle("-fx-background-color: #f23a50");
            	}
            }
        });
		
		SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, fiscals.get(rowIndex).getWet_slip());
		wetSlipSpinner.setValueFactory(wetSlipValueFactory);
		wetSlipSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  wetSlipSpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(wetSlipSpinner.getEditor().getText());
				  SqlUpdate.updateField(fieldValue,"money","wet_slip",fiscals,rowIndex);
				  fiscals.get(rowIndex).setWet_slip(fieldValue);
				  updateBalance();
			  }
			});
		
		SpinnerValueFactory<Integer> extraKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getExtra_key());
		extraKeySpinner.setValueFactory(extraKeyValueFactory);
		extraKeySpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  extraKeySpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(extraKeySpinner.getEditor().getText());
				  SqlUpdate.updateField(fieldValue,"money","extra_key",fiscals,rowIndex);
				  fiscals.get(rowIndex).setExtra_key(fieldValue);
				  updateBalance();
			  }
			});

		SpinnerValueFactory<Integer> sailLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getSail_loft_key());
		sailLKeySpinner.setValueFactory(sailLKeyValueFactory);
		sailLKeySpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  sailLKeySpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(sailLKeySpinner.getEditor().getText());
				  SqlUpdate.updateField(fieldValue,"money","sail_loft_key",fiscals,rowIndex);
				  fiscals.get(rowIndex).setSail_loft_key(fieldValue);
				  updateBalance();
			  }
			});
		
		SpinnerValueFactory<Integer> kayakSKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getKayac_shed_key());
		kayakSKeySpinner.setValueFactory(kayakSKeyValueFactory);
		kayakSKeySpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  kayakSKeySpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(kayakSKeySpinner.getEditor().getText());
				  SqlUpdate.updateField(fieldValue,"money","kayak_shed_key",fiscals,rowIndex);
				  fiscals.get(rowIndex).setKayac_shed_key(fieldValue);
				  updateBalance();
			  }
			});
		
		SpinnerValueFactory<Integer> sailSSLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getSail_school_loft_key());
		sailSSLKeySpinner.setValueFactory(sailSSLKeyValueFactory);
		sailSSLKeySpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  sailSSLKeySpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(sailSSLKeySpinner.getEditor().getText());
				  SqlUpdate.updateField(fieldValue,"money","sail_school_loft_key",fiscals,rowIndex);
				  fiscals.get(rowIndex).setSail_school_loft_key(fieldValue);
				  updateBalance();
			  }
			});
		
		SpinnerValueFactory<Integer> beachValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getBeach());
		beachSpinner.setValueFactory(beachValueFactory);
		beachSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  beachSpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(beachSpinner.getEditor().getText());
				  SqlUpdate.updateField(fieldValue,"money","beach",fiscals,rowIndex);
				  fiscals.get(rowIndex).setBeach(fieldValue);
				  updateBalance();
			  }
			});
		
		SpinnerValueFactory<Integer> kayacRackValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getKayac_rack());
		kayakRackSpinner.setValueFactory(kayacRackValueFactory);
		kayakRackSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  kayakRackSpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(kayakRackSpinner.getEditor().getText());
				  SqlUpdate.updateField(fieldValue,"money","kayak_rack",fiscals,rowIndex);
				  fiscals.get(rowIndex).setKayac_rack(fieldValue);
				  updateBalance();
			  }
			});
		
		SpinnerValueFactory<Integer> kayakShedValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getKayac_shed());
		kayakShedSpinner.setValueFactory(kayakShedValueFactory);
		kayakShedSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  kayakShedSpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(kayakShedSpinner.getEditor().getText());
				  SqlUpdate.updateField(fieldValue,"money","kayak_shed",fiscals,rowIndex);
				  fiscals.get(rowIndex).setKayac_shed(fieldValue);
				  updateBalance();
			  }
			});
		
		SpinnerValueFactory<Integer> sailLoftValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, fiscals.get(rowIndex).getSail_loft());
		sailLoftSpinner.setValueFactory(sailLoftValueFactory);
		sailLoftSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  sailLoftSpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(sailLoftSpinner.getEditor().getText());
				  SqlUpdate.updateField(fieldValue,"money","sail_loft",fiscals,rowIndex);
				  fiscals.get(rowIndex).setSail_loft(fieldValue);
				  updateBalance();
			  }
			});
		
		SpinnerValueFactory<Integer> sailSchoolLoftValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, fiscals.get(rowIndex).getSail_school_laser_loft());
		sailSchoolLoftSpinner.setValueFactory(sailSchoolLoftValueFactory);
		sailSchoolLoftSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  sailSchoolLoftSpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(sailSchoolLoftSpinner.getEditor().getText());
				  SqlUpdate.updateField(fieldValue,"money","sail_school_laser_loft",fiscals,rowIndex);
				  fiscals.get(rowIndex).setSail_school_laser_loft(fieldValue);
				  updateBalance();
			  }
			});
		
		SpinnerValueFactory<Integer> winterStorageValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getWinter_storage());
		winterStorageSpinner.setValueFactory(winterStorageValueFactory);
		winterStorageSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  winterStorageSpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(winterStorageSpinner.getEditor().getText());
				  SqlUpdate.updateField(fieldValue,"money","winter_storage",fiscals,rowIndex);
				  fiscals.get(rowIndex).setWinter_storage(fieldValue);
				  updateBalance();
			  }
			});
		
		SpinnerValueFactory<Integer> racingValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedWorkCreditYear.getRacing());
		racingSpinner.setValueFactory(racingValueFactory);
		racingSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  racingSpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(racingSpinner.getEditor().getText());
				  SqlUpdate.updateField(fieldValue,"work_credit","racing",fiscals,rowIndex);
				  selectedWorkCreditYear.setRacing(fieldValue);
				  totalWCText.setText(countWorkCredits() + "");
				  fiscals.get(rowIndex).setCredit(countCredit());
				  creditText.setText(fiscals.get(rowIndex).getCredit() + "");
				  SqlUpdate.updateField(countCredit(), "money", "credit",fiscals,rowIndex);
				  balanceText.setText(getBalance() + "");
				  SqlUpdate.updateField(getBalance(), "money", "balance",fiscals,rowIndex);
				  fiscals.get(rowIndex).setBalance(getBalance());
			  }
			});
		
		SpinnerValueFactory<Integer> harborValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedWorkCreditYear.getHarbor());
		harborSpinner.setValueFactory(harborValueFactory);
		harborSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  harborSpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(harborSpinner.getEditor().getText());
				  SqlUpdate.updateField(fieldValue,"work_credit","harbor",fiscals,rowIndex);
				  selectedWorkCreditYear.setHarbor(fieldValue);
				  totalWCText.setText(countWorkCredits() + "");
				  fiscals.get(rowIndex).setCredit(countCredit());
				  creditText.setText(fiscals.get(rowIndex).getCredit() + "");
				  SqlUpdate.updateField(countCredit(), "money", "credit",fiscals,rowIndex);
				  balanceText.setText(getBalance() + "");
				  SqlUpdate.updateField(getBalance(), "money", "balance",fiscals,rowIndex);
				  fiscals.get(rowIndex).setBalance(getBalance());
			  }
			});
		
		SpinnerValueFactory<Integer> socialValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedWorkCreditYear.getSocial());
		socialSpinner.setValueFactory(socialValueFactory);
		socialSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  socialSpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(socialSpinner.getEditor().getText());
				  SqlUpdate.updateField(fieldValue,"work_credit","social",fiscals,rowIndex);
				  selectedWorkCreditYear.setSocial(fieldValue);
				  totalWCText.setText(countWorkCredits() + "");
				  fiscals.get(rowIndex).setCredit(countCredit());
				  creditText.setText(fiscals.get(rowIndex).getCredit() + "");
				  SqlUpdate.updateField(countCredit(), "money", "credit",fiscals,rowIndex);
				  balanceText.setText(getBalance() + "");
				  SqlUpdate.updateField(getBalance(), "money", "balance",fiscals,rowIndex);
				  fiscals.get(rowIndex).setBalance(getBalance());
			  }
			});
		
		SpinnerValueFactory<Integer> otherValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedWorkCreditYear.getOther());
		otherSpinner.setValueFactory(otherValueFactory);
		otherSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  otherSpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(otherSpinner.getEditor().getText());
				  SqlUpdate.updateField(fieldValue,"work_credit","other",fiscals,rowIndex);
				  selectedWorkCreditYear.setOther(fieldValue);
				  totalWCText.setText(countWorkCredits() + "");  /// total work credit
				  fiscals.get(rowIndex).setCredit(countCredit());
				  creditText.setText(fiscals.get(rowIndex).getCredit() + "");  /// total credit
				  SqlUpdate.updateField(countCredit(), "money", "credit",fiscals,rowIndex);
				  balanceText.setText(getBalance() + "");
				  SqlUpdate.updateField(getBalance(), "money", "balance",fiscals,rowIndex);
				  fiscals.get(rowIndex).setBalance(getBalance());
				  
			  }
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
	            	SqlUpdate.updateField(newTotalValue, "money", "ysc_donation",fiscals,rowIndex);
					fiscals.get(rowIndex).setTotal(updateTotalFeeFields());
					totalFeesText.setText(fiscals.get(rowIndex).getTotal() + "");
					SqlUpdate.updateField(fiscals.get(rowIndex).getTotal(), "money", "total",fiscals,rowIndex);
	            	balanceText.setText(getBalance() + "");
	            	SqlUpdate.updateField(getBalance(), "money", "balance",fiscals,rowIndex);
	            }
	        }
	    });
		
		//////////////// SETTING CONTENT //////////////
		
		
		creditText.setText(fiscals.get(rowIndex).getCredit() + "");
		totalFeesText.setText(fiscals.get(rowIndex).getTotal() + "");
		paidText.setText(fiscals.get(rowIndex).getPaid() + "");
		duesText.setText(fiscals.get(rowIndex).getDues() + "");
		yscText.setText(fiscals.get(rowIndex).getYsc_donation() + "");
		//wetSlipSpinner.setText(fiscals.get(rowIndex).getWet_slip() + "");
		
		totalWCText.setText(countWorkCredits() + "");
		if(hasOfficer) {
			creditText.setText(duesText.getText()); // gets the dues and gives that amount of credit for being an officer
			SqlUpdate.updateField(Integer.parseInt(duesText.getText()), "money", "credit",fiscals,rowIndex); // updates
			fiscals.get(rowIndex).setCredit(Integer.parseInt(duesText.getText()));
		}
		updateBalance();
		balanceText.setText(getBalance() + "");
		if(fiscals.get(rowIndex).isCommitted()) setEditable(false);
		hboxDues.getChildren().addAll(new Label("Dues:"), duesText);
		hboxRacing.getChildren().addAll(new Label("Racing"),racingSpinner);
		hboxHarbor.getChildren().addAll(new Label("Harbor"),harborSpinner);
		hboxSocial.getChildren().addAll(new Label("Social"),socialSpinner);
		hboxOther.getChildren().addAll(new Label("Other"),otherSpinner);
		//hboxYear.getChildren().addAll(new Label("Year"),yearText);
		hboxKey.getChildren().addAll(new Label("Extra Key"),extraKeySpinner);
		hboxWinterStorage.getChildren().addAll(new Label("Winter Storage"), winterStorageSpinner);
		hboxKayac.getChildren().addAll(new Label("Kayac Rack"),kayakRackSpinner);
		hboxWetSlip.getChildren().addAll(new Label("wetSlip"),wetSlipSpinner);
		hboxYSC.getChildren().addAll(new Label("YSC Donation"),yscText);
		hboxPaid.getChildren().addAll(new Label("Paid"),paidText);
		hboxtotalWC.getChildren().addAll(new Label("Total:"),totalWCText);
		hboxSLKey.getChildren().addAll(new Label("Sail Loft Key"),sailLKeySpinner);
		hboxKSKey.getChildren().addAll(new Label("Kayak Shed Key"),kayakSKeySpinner);
		hboxSSLKey.getChildren().addAll(new Label("Sail School Loft Key"),sailSSLKeySpinner);
		hboxBeach.getChildren().addAll(new Label("Beach Spot"),beachSpinner);
		hboxKayakShed.getChildren().addAll(new Label("Kayak Shed"),kayakShedSpinner);
		hboxSailLoft.getChildren().addAll(new Label("Sail Loft"),sailLoftSpinner);
		hboxSailSchoolLoft.getChildren().addAll(new Label("Sail School Loft"),sailSchoolLoftSpinner);
		hboxTotalFees.getChildren().addAll(new Label("Total Fees"),totalFeesText);
		hboxCredit.getChildren().addAll(new Label("Credit"),creditText);
		hboxBalence.getChildren().addAll(new Label("Balance"),balanceText);
		
		
		vbox1.getChildren().addAll(comboHBox, workCreditsLabel, hboxRacing, hboxHarbor,hboxSocial,hboxOther,hboxtotalWC,BalanceLabel,hboxTotalFees,hboxCredit,hboxPaid,hboxBalence,commitCheckBox);
		vbox2.getChildren().addAll(hboxDues,hboxKey,hboxSLKey,hboxKSKey,hboxSSLKey,hboxBeach,hboxKayac,hboxKayakShed,hboxSailLoft,hboxSailSchoolLoft,hboxWetSlip,hboxWinterStorage,hboxYSC);
		
		mainHbox.getChildren().addAll(vbox2,vbox1);
		mainVbox.getChildren().addAll(mainHbox);  // add error hbox in first
		vboxGrey.getChildren().addAll(mainVbox);
		getChildren().addAll(vboxGrey);
	}
	
	//////////////////////  CLASS METHODS ///////////////////////////
	
	private void setEditable(boolean isEditable) {
		changeState(yscText,isEditable,true);
		changeState(paidText,isEditable,true);
		changeState(totalWCText,isEditable,false);
		changeState(duesText,isEditable,false);
		changeState(totalFeesText,isEditable,false);
		changeState(creditText,isEditable,false);
		//changeState(balanceText,isEditable,1);
		changeState(wetSlipSpinner,isEditable);
		changeState(extraKeySpinner,isEditable);
		changeState(sailLKeySpinner,isEditable);
		changeState(kayakSKeySpinner,isEditable);
		changeState(sailSSLKeySpinner,isEditable);
		changeState(beachSpinner,isEditable);
		changeState(kayakRackSpinner,isEditable);
		changeState(kayakShedSpinner,isEditable);
		changeState(sailLoftSpinner,isEditable);
		changeState(sailSchoolLoftSpinner,isEditable);
		changeState(winterStorageSpinner,isEditable);
		changeState(racingSpinner,isEditable);
		changeState(harborSpinner,isEditable);
		changeState(socialSpinner,isEditable);
		changeState(otherSpinner,isEditable);
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
		  SqlUpdate.updateField(fiscals.get(rowIndex).getTotal(), "money", "total",fiscals,rowIndex);
		  balanceText.setText(getBalance() + "");
		  SqlUpdate.updateField(getBalance(), "money", "balance",fiscals,rowIndex);
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
		
		return extraKey + sailLoftKey + kayakShedKey + sailSchoolLoftKey + beachSpot + kayakRack
				+kayakShed + sailLoft + sailSchoolLoft + wetSlip + winterStorage + yscDonation + dues;
	}
	
	private int getBalance() {
		return fiscals.get(rowIndex).getTotal() - fiscals.get(rowIndex).getPaid() - fiscals.get(rowIndex).getCredit();
	}
	
	private int countCredit() {
		int credit = 0;
		if(membershipHasOfficer()) {
			credit = fiscals.get(rowIndex).getOfficer_credit();  // inserts credit for member type into fiscal
		} else {
			credit = countWorkCredits() * 10;  // work credits are worth $10 each, this may change some day.
			// TODO need to add 10 dollar reference in defined fees
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
		if(total >= 15) total = 15;  // we allow no more than 15 wc per year and no carry over.
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
