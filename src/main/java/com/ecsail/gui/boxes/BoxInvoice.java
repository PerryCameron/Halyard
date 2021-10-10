package com.ecsail.gui.boxes;

import com.ecsail.enums.PaymentType;
import com.ecsail.main.EditCell;
import com.ecsail.main.Note;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlSelect;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.*;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;


// replaces tabCredit, tabBalance, tabPayment, BoxFiscal2

public class BoxInvoice extends HBox {
	private ObservableList<Object_Money> fiscals = null;
	private ObservableList<Object_Payment> payments;

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
	String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));

//	Object_BalanceText textFields = new Object_BalanceText();
	GridPane gridPane = new GridPane();
	private TableView<Object_Payment> paymentTableView = new TableView<Object_Payment>();
	private final TextField yscTextField = new TextField();
	private final TextField duesTextField;
//	private final TextField slipTextField = new TextField();
	private final TextField otherTextField = new TextField();
	private final TextField initiationTextField = new TextField();
	private final TextField wetslipTextField = new TextField();
	private final TextField otherCreditTextField = new TextField();


	private final Spinner<Integer> beachSpinner = new Spinner<Integer>();
	private final Spinner<Integer> kayakRackSpinner = new Spinner<Integer>();
	private final Spinner<Integer> kayakShedSpinner = new Spinner<Integer>();
	private final Spinner<Integer> sailLoftSpinner = new Spinner<Integer>();
	private final Spinner<Integer> sailSchoolLoftSpinner = new Spinner<Integer>();
	private final Spinner<Integer> winterStorageSpinner = new Spinner<Integer>();
	private final Spinner<Integer> wetSlipSpinner = new Spinner<Integer>();
	private final Spinner<Integer> workCreditSpinner = new Spinner<Integer>();
	private final Spinner<Integer> gateKeySpinner = new Spinner<Integer>();
	private final Spinner<Integer> sailLKeySpinner = new Spinner<Integer>();
	private final Spinner<Integer> kayakSKeySpinner = new Spinner<Integer>();
	private final Spinner<Integer> sailSSLKeySpinner = new Spinner<Integer>();

	private final Text totalFeesText = new Text();
	private final Text totalCreditText = new Text();
	private final Text totalPaymentText = new Text();
	private final Text totalBalanceText = new Text();

	private final String disabledColor = "-fx-background-color: #d5dade";
	boolean isCommited;
	Button addWetSlip = new Button();
	
	public BoxInvoice(Object_Membership m, ObservableList<Object_Person> p, ObservableList<Object_Money> o, int r, Note n, TextField dt) {
		this.membership = m;
		this.people = p;
		this.rowIndex = r;
		this.fiscals = o;
		this.note = n;
		this.duesTextField = dt;
		this.selectedWorkCreditYear = SqlSelect.getWorkCredit(fiscals.get(rowIndex).getMoney_id());
		this.definedFees = SqlSelect.selectDefinedFees(fiscals.get(rowIndex).getFiscal_year());
		this.hasOfficer = membershipHasOfficer();
		this.isCommited = fiscals.get(rowIndex).isCommitted();
		this.numberOfKeys = new Object_Integer(0);
		this.workCredits = new Object_Integer(0);
		///////////// ACTION ///////////////
		if(SqlExists.paymentExists(fiscals.get(rowIndex).getMoney_id())) {
			this.payments = SqlSelect.getPayments(fiscals.get(rowIndex).getMoney_id());
			System.out.println("A record for money_id=" + fiscals.get(rowIndex).getMoney_id() + " exists. Opening Payment");
			System.out.println("Payment has " + payments.size() + " entries");
			// pull up payments from database
		} else {  // if not create one
			this.payments = FXCollections.observableArrayList();
			System.out.println("Creating a new entry");
			int pay_id = SqlSelect.getNumberOfPayments() + 1;
			payments.add(new Object_Payment(pay_id,fiscals.get(rowIndex).getMoney_id(),"0","CH",date, "0",1));
			SqlInsert.addPaymentRecord(payments.get(payments.size() - 1));
			System.out.println(payments.get(0).toString());
		}


		////////////// OBJECTS /////////////////////


		ScrollPane scrollPane = new ScrollPane();

		Text duesText = new Text();
		Text beachText = new Text();
		Text kayakRackText = new Text();
		Text kayakShedText = new Text();
		Text sailLoftText = new Text();
		Text sailSchoolLoftText = new Text();
		Text wetSlipText = new Text();
		Text winterStorageText = new Text();
		Text yspText = new Text();
		Text initiationText = new Text();
		Text otherFeeText = new Text();
		Text workCreditsText = new Text();
		Text gateKeyText = new Text();
		Text sailLKeyText = new Text();
		Text kayakSKeyText = new Text();
		Text sailSSLKeyText = new Text();
		Text otherCreditText = new Text();
		Text wetslipTextFee = new Text();


		duesText.setText(fiscals.get(rowIndex).getDues());
		beachText.setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getBeach()).multiply(definedFees.getBeach())));
		kayakRackText.setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getKayac_rack()).multiply(definedFees.getKayak_rack())));
		kayakShedText.setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getKayac_shed()).multiply(definedFees.getKayak_shed())));
		sailLoftText.setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getSail_loft()).multiply(definedFees.getSail_loft())));
		sailSchoolLoftText.setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getSail_school_laser_loft()).multiply(definedFees.getSail_school_laser_loft())));
		wetSlipText.setText(fiscals.get(rowIndex).getWet_slip());
		winterStorageText.setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getWinter_storage()).multiply(definedFees.getWinter_storage())));

		gateKeyText.setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getExtra_key()).multiply(definedFees.getMain_gate_key())));
		sailLKeyText.setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getSail_loft_key()).multiply(definedFees.getSail_loft_key())));
		kayakSKeyText.setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getKayac_shed_key()).multiply(definedFees.getKayak_shed_key())));
		sailSSLKeyText.setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getSail_school_loft_key()).multiply(definedFees.getSail_school_loft_key())));



		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox mainVbox = new VBox();
		HBox mainHbox = new HBox();
		VBox vboxTabPanes = new VBox();
		VBox vboxSpinners = new VBox();
		HBox hboxButtonCommit = new HBox();
		HBox hboxSlip = new HBox();

		// vboxes for totals
		VBox vboxDues = new VBox();
		VBox vboxBeach = new VBox();
		VBox vboxKayak = new VBox();
		VBox vboxKayakShed = new VBox();
		VBox vboxSailLoft = new VBox();
		VBox vboxSailSchoolLoft = new VBox();
		VBox vboxWetSlip = new VBox();
		VBox vboxWinterStorage = new VBox();
		VBox vboxGateKey = new VBox();
		VBox vboxSailLoftKey = new VBox();
		VBox vboxKayakShedKey = new VBox();
		VBox vboxSailSchoolLoftKey = new VBox();
		VBox vboxWorkCredits = new VBox();
		VBox vboxYSC = new VBox();
		VBox vboxInitiation = new VBox();
		VBox vboxOther = new VBox();
		VBox vboxOtherCredit = new VBox();

		// vboxes for multipliers
		VBox vboxBeachFee = new VBox();
		VBox vboxKayakFee = new VBox();
		VBox vboxKayakShedFee = new VBox();
		VBox vboxSailLoftFee = new VBox();
		VBox vboxSailSchoolLoftFee = new VBox();
		VBox vboxWetSlipFee = new VBox();
		VBox vboxWinterStorageFee = new VBox();
		VBox vboxGateKeyFee = new VBox();
		VBox vboxSailLoftKeyFee = new VBox();
		VBox vboxKayakShedKeyFee = new VBox();
		VBox vboxSailSchoolLoftKeyFee = new VBox();
		VBox vboxWorkCreditsFee = new VBox();

		VBox vboxTitlePrice = new VBox();
		VBox vboxTitleTotal = new VBox();

		Button buttonAdd = new Button("Add");
		Button buttonDelete = new Button("Delete");
		Button commitButton = new Button("Commit");
		CheckBox renewCheckBox = new CheckBox("Renew");
		VBox vboxButtons = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		VBox vboxCommitButton = new VBox();

		/////////////// TABLE ///////////////////
		TableColumn<Object_Payment, String> col1 = createColumn("Amount", Object_Payment::PaymentAmountProperty);
		col1.setPrefWidth(60);
		col1.setStyle( "-fx-alignment: CENTER-RIGHT;");
		col1.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<Object_Payment, String>>() {
					@Override
					public void handle(TableColumn.CellEditEvent<Object_Payment, String> t) {
						((Object_Payment) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
						).setPaymentAmount(String.valueOf(new BigDecimal(t.getNewValue()).setScale(2)));
						int pay_id = ((Object_Payment) t.getTableView().getItems().get(t.getTablePosition().getRow())).getPay_id();
						BigDecimal amount = new BigDecimal(t.getNewValue());
						SqlUpdate.updatePayment(pay_id, "amount", String.valueOf(amount.setScale(2)));
						BigDecimal totalAmount = BigDecimal.valueOf(SqlSelect.getTotalAmount(fiscals.get(rowIndex).getMoney_id()));
						totalPaymentText.setText(totalAmount.setScale(2) + "");
						fiscals.get(rowIndex).setPaid(String.valueOf(totalAmount.setScale(2)));
					}
				}
		);


		// example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
		ObservableList<PaymentType> paymentTypeList = FXCollections.observableArrayList(PaymentType.values());
		TableColumn<Object_Payment, PaymentType> col2 = new TableColumn<Object_Payment, PaymentType>("Type");

		col2.setPrefWidth(55);
		col2.setStyle( "-fx-alignment: CENTER;");
		col2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object_Payment, PaymentType>, ObservableValue<PaymentType>>() {

			@Override
			public ObservableValue<PaymentType> call(TableColumn.CellDataFeatures<Object_Payment, PaymentType> param) {
				Object_Payment thisPayment = param.getValue();
				String paymentCode = thisPayment.getPaymentType();
				PaymentType paymentType = PaymentType.getByCode(paymentCode);
				return new SimpleObjectProperty<PaymentType>(paymentType);
			}
		});

		col2.setCellFactory(ComboBoxTableCell.forTableColumn(paymentTypeList));

		col2.setOnEditCommit((TableColumn.CellEditEvent<Object_Payment, PaymentType> event) -> {
			TablePosition<Object_Payment, PaymentType> pos = event.getTablePosition();
			PaymentType newPaymentType = event.getNewValue();
			int row = pos.getRow();
			Object_Payment thisPayment = event.getTableView().getItems().get(row);
			SqlUpdate.updatePayment(thisPayment.getPay_id(), "payment_type", newPaymentType.getCode());
			// need to update paid from here
			//SqlUpdate.updatePhone("phone_type", thisPhone.getPhone_ID(), newPhoneType.getCode());
			thisPayment.setPaymentType(newPaymentType.getCode());
		});

		TableColumn<Object_Payment, String> col3 = createColumn("Check #", Object_Payment::checkNumberProperty);
		col3.setPrefWidth(55);
		col3.setStyle( "-fx-alignment: CENTER-LEFT;");
		col3.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<Object_Payment, String>>() {
					@Override
					public void handle(TableColumn.CellEditEvent<Object_Payment, String> t) {
						((Object_Payment) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
						).setCheckNumber(t.getNewValue());
						int pay_id = ((Object_Payment) t.getTableView().getItems().get(t.getTablePosition().getRow())).getPay_id();
						SqlUpdate.updatePayment(pay_id, "CHECKNUMBER", t.getNewValue());
						//	SqlUpdate.updatePhone("phone", phone_id, t.getNewValue());
					}
				}
		);

		TableColumn<Object_Payment, String> col4 = createColumn("Date", Object_Payment::paymentDateProperty);
		col4.setPrefWidth(70);
		col4.setStyle( "-fx-alignment: CENTER-LEFT;");
		col4.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<Object_Payment, String>>() {
					@Override
					public void handle(TableColumn.CellEditEvent<Object_Payment, String> t) {
						((Object_Payment) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
						).setPaymentDate(t.getNewValue());
						int pay_id = ((Object_Payment) t.getTableView().getItems().get(t.getTablePosition().getRow())).getPay_id();
						SqlUpdate.updatePayment(pay_id, "payment_date", t.getNewValue());
						//	SqlUpdate.updatePhone("phone", phone_id, t.getNewValue());
					}
				}
		);

		col1.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Boat Name
		col2.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Manufacturer
		col3.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );   // Year
		col4.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Model

		//////////////// ATTRIBUTES ///////////////////
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink fram around table
		vboxPink.setId("box-pink");
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		renewCheckBox.setSelected(true);
		buttonAdd.setPrefWidth(60);
		buttonDelete.setPrefWidth(60);
		vboxButtons.setSpacing(5);

		paymentTableView.setItems(payments);
		HBox.setHgrow(paymentTableView,Priority.ALWAYS);
		//paymentTableView.setPrefWidth(225);
		paymentTableView.setPrefHeight(115);
		paymentTableView.setFixedCellSize(30);
		paymentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );

		if(fiscals.get(rowIndex).isCommitted()) {
			paymentTableView.setEditable(false);
//			paymentAdd.setDisable(true);
//			paymentDelete.setDisable(true);
		} else {
			paymentTableView.setEditable(true);
//			paymentAdd.setDisable(false);
//			paymentDelete.setDisable(false);
		}

		int width = 100;
		
		winterStorageSpinner.setPrefWidth(65);
		wetSlipSpinner.setPrefWidth(65);
		workCreditSpinner.setPrefWidth(65);
		kayakRackSpinner.setPrefWidth(65);
		addWetSlip.setPrefWidth(25);
		addWetSlip.setPrefHeight(25);
		yscTextField.setPrefWidth(65);
		otherTextField.setPrefWidth(65);
		otherCreditTextField.setPrefWidth(65);
		initiationTextField.setPrefWidth(65);
		wetslipTextField.setPrefWidth(65);
		duesTextField.setPrefWidth(65);
		beachSpinner.setPrefWidth(65);
		kayakShedSpinner.setPrefWidth(65);
		sailLoftSpinner.setPrefWidth(65);
		sailSchoolLoftSpinner.setPrefWidth(65);
		gateKeySpinner.setPrefWidth(65);
		sailLKeySpinner.setPrefWidth(65);
		sailSSLKeySpinner.setPrefWidth(65);
		kayakSKeySpinner.setPrefWidth(65);

		vboxTabPanes.setAlignment(Pos.CENTER);
		vboxSpinners.setAlignment(Pos.CENTER);

		vboxCommitButton.setSpacing(10);
		vboxSpinners.setSpacing(5);
		mainHbox.setSpacing(10);

		this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		vboxGrey.setPadding(new Insets(8, 5, 0, 15));
		hboxButtonCommit.setPadding(new Insets(5, 0, 5, 170));	
		
		setId("box-blue");
		vboxGrey.setId("box-grey");

		HBox.setHgrow(vboxGrey, Priority.ALWAYS);

		//////////////// LISTENER //////////////////

		
		// this is only called if you changer membership type or open a record or manually type in
		duesTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!SqlSelect.isCommitted(fiscals.get(rowIndex).getMoney_id())) {
				String newDues = newValue;
				duesText.setText(newDues);
				System.out.println(" dues textfield set to " + newValue);
				fiscals.get(rowIndex).setDues(newDues);
				updateBalance();
				SqlUpdate.updateMoney(fiscals.get(rowIndex));
			} else {
				System.out.println("Record is commited, no changes made");
			}
		});
		
		duesTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	if(!isNumeric(duesTextField.getText())) {
	            		duesTextField.setText("0");
	            	}
	            	BigDecimal dues = new BigDecimal(duesTextField.getText());
	            	updateItem(dues,"dues");
					duesTextField.setText(String.valueOf(dues.setScale(2)));
	            	updateBalance();
	            }
	        });

		SpinnerValueFactory<Integer> beachValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getBeach());
		beachSpinner.setValueFactory(beachValueFactory);
		beachSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setBeach(newValue);
			beachText.setText(String.valueOf(definedFees.getBeach().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> kayacRackValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getKayac_rack());
		kayakRackSpinner.setValueFactory(kayacRackValueFactory);
		kayakRackSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setKayac_rack(newValue);
			kayakRackText.setText(String.valueOf(definedFees.getKayak_rack().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> kayakShedValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getKayac_shed());
		kayakShedSpinner.setValueFactory(kayakShedValueFactory);
		kayakShedSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setKayac_shed(newValue);
			kayakShedText.setText(String.valueOf(definedFees.getKayak_shed().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> sailLoftValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, fiscals.get(rowIndex).getSail_loft());
		sailLoftSpinner.setValueFactory(sailLoftValueFactory);
		sailLoftSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setSail_loft(newValue);
			sailLoftText.setText(String.valueOf(definedFees.getSail_loft().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> sailSchoolLoftValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, fiscals.get(rowIndex).getSail_school_laser_loft());
		sailSchoolLoftSpinner.setValueFactory(sailSchoolLoftValueFactory);
		sailSchoolLoftSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setSail_school_laser_loft(newValue);
			sailSchoolLoftText.setText(String.valueOf(definedFees.getSail_school_laser_loft().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, 0);
		wetSlipSpinner.setValueFactory(wetSlipValueFactory);
		wetSlipSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			String wetSlip = String.valueOf(new BigDecimal(wetslipTextFee.getText()).multiply(BigDecimal.valueOf(newValue)));
			System.out.println("Wetslip=" + wetSlip);
			fiscals.get(rowIndex).setWet_slip(wetSlip);
			wetSlipText.setText(wetSlip);
			updateBalance();
		});

		// this is for changing the value from default
		wetslipTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!isNumeric(otherTextField.getText())) {
					otherTextField.setText("0.00");
				}
				BigDecimal slip = new BigDecimal(wetslipTextField.getText());
				wetSlipText.setText(String.valueOf(slip.multiply(BigDecimal.valueOf(wetSlipSpinner.getValue()))));
				slip.setScale(2);
				updateItem(slip,"wetslip");
				wetslipTextField.setText(String.valueOf(slip.setScale(2)));
				wetslipTextFee.setText(String.valueOf(slip.setScale(2)));
				updateBalance();
				vboxWetSlipFee.getChildren().clear();
				vboxWetSlipFee.getChildren().add(wetslipTextFee);

			}
		});

		SpinnerValueFactory<Integer> winterStorageValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getWinter_storage());
		winterStorageSpinner.setValueFactory(winterStorageValueFactory);
		winterStorageSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setWinter_storage(newValue);
			winterStorageText.setText(String.valueOf(definedFees.getWinter_storage().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
//			SqlUpdate.updateMoney(fiscals.get(rowIndex));
		});

		SpinnerValueFactory<Integer> gateKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getExtra_key());
		gateKeySpinner.setValueFactory(gateKeyValueFactory);
		gateKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			gateKeyText.setText(String.valueOf(definedFees.getMain_gate_key().multiply(BigDecimal.valueOf(newValue))));
			fiscals.get(rowIndex).setExtra_key(newValue);
			updateBalance();
		});

		SpinnerValueFactory<Integer> sailLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getSail_loft_key());
		sailLKeySpinner.setValueFactory(sailLKeyValueFactory);
		sailLKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			sailLKeyText.setText(String.valueOf(definedFees.getSail_loft_key().multiply(BigDecimal.valueOf(newValue))));
			fiscals.get(rowIndex).setSail_loft_key(newValue);
			updateBalance();
		});

		SpinnerValueFactory<Integer> kayakSKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getKayac_shed_key());
		kayakSKeySpinner.setValueFactory(kayakSKeyValueFactory);
		kayakSKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			kayakSKeyText.setText(String.valueOf(definedFees.getKayak_shed_key().multiply(BigDecimal.valueOf(newValue))));
			fiscals.get(rowIndex).setKayac_shed_key(newValue);
			updateBalance();
		});

		SpinnerValueFactory<Integer> sailSSLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getSail_school_loft_key());
		sailSSLKeySpinner.setValueFactory(sailSSLKeyValueFactory);
		sailSSLKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			sailSSLKeyText.setText(String.valueOf(definedFees.getSail_school_loft_key().multiply(BigDecimal.valueOf(newValue))));
			fiscals.get(rowIndex).setSail_school_loft_key(newValue);
			updateBalance();
		});

		yscTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!isNumeric(yscTextField.getText())) {
					yscTextField.setText("0.00");
				}
				BigDecimal ysc = new BigDecimal(yscTextField.getText());
				yspText.setText(String.valueOf(ysc.setScale(2)));
				updateItem(ysc, "ysc");
				yscTextField.setText(String.valueOf(ysc.setScale(2)));
				updateBalance();
			}
		});

		initiationTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!isNumeric(initiationTextField.getText())) {
					initiationTextField.setText("0.00");
				}
				BigDecimal initiation = new BigDecimal(initiationTextField.getText());
				updateItem(initiation, "initiation");
				initiationTextField.setText(String.valueOf(initiation.setScale(2)));
				initiationText.setText(String.valueOf(initiation.setScale(2)));
				updateBalance();
			}
		});

		otherTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!isNumeric(otherTextField.getText())) {
					otherTextField.setText("0.00");
				}
				BigDecimal other = new BigDecimal(otherTextField.getText());
				otherFeeText.setText(String.valueOf(other.setScale(2)));
				updateItem(other,"other");
				otherTextField.setText(String.valueOf(other.setScale(2)));
				updateBalance();
			}
		});

		otherCreditTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!isNumeric(otherCreditTextField.getText())) {
					otherCreditTextField.setText("0.00");
				}
				BigDecimal other = new BigDecimal(otherCreditTextField.getText());
				otherCreditText.setText(String.valueOf(other.setScale(2)));
				updateItem(other,"other_credit");
				otherCreditTextField.setText(String.valueOf(other.setScale(2)));
				updateBalance();
			}
		});

		SpinnerValueFactory<Integer> workCreditValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 40, fiscals.get(rowIndex).getWork_credit());
		workCreditSpinner.setValueFactory(workCreditValueFactory);
		workCreditSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setWork_credit(newValue);
			String workCredits = String.valueOf(definedFees.getWork_credit().multiply(BigDecimal.valueOf(newValue)));
			workCreditsText.setText(workCredits);
			totalCreditText.setText(workCredits);
			updateBalance();
		});

//		totalPaymentText.textProperty().addListener((obd, oldValue, newValue) -> {
//        	if(!isNumeric(totalPaymentText.getText())) {  // we should move this to amount in TabPayment
//				totalPaymentText.setText("0.00");
//        	}
//        	BigDecimal newTotalValue = new BigDecimal(totalPaymentText.getText());
//        	fiscals.get(rowIndex).setPaid(totalPaymentText.getText());
//        	SqlUpdate.updateField(newTotalValue, "money", "paid",fiscals,rowIndex);
//        	BigDecimal balance = getBalance();
//			totalPaymentText.setText(balance + "");
//        	SqlUpdate.updateField(getBalance(), "money", "balance",fiscals,rowIndex);
//        	fiscals.get(rowIndex).setBalance(String.valueOf(getBalance()));
//		});

		numberOfKeys.integerProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
		        updateBalance();
		        SqlUpdate.updateMoney(fiscals.get(rowIndex));
		    });
		
		workCredits.integerProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
		    	fiscals.get(rowIndex).setCredit(String.valueOf(countCredit((int)newValue)));
				totalCreditText.setText(String.valueOf(countCredit((int)newValue)));
		    	totalBalanceText.setText(String.valueOf(getBalance()));  // sets balance textfield in balance tab
				fiscals.get(rowIndex).setBalance(String.valueOf(getBalance()));  // sets focused object
			workCreditsText.setText(String.valueOf(definedFees.getWork_credit().multiply(BigDecimal.valueOf((int)newValue))));
		        updateBalance();
		        SqlUpdate.updateMoney(fiscals.get(rowIndex));
		    });

		commitButton.setOnAction((event) -> {
			if (!fiscals.get(rowIndex).isCommitted()) {
				if (!totalBalanceText.getText().equals("0"))
					totalBalanceText.setStyle("-fx-background-color: #f23a50");
				System.out.println("total at commit is " + fiscals.get(rowIndex).getTotal());
				SqlUpdate.updateMoney(fiscals.get(rowIndex));
				SqlUpdate.commitFiscalRecord(fiscals.get(rowIndex).getMoney_id(), true);// this could be placed in line above
				String date = SqlSelect.getPaymentDate(fiscals.get(rowIndex).getMoney_id()); // dates note to check
				// update membership_id record to renew or non-renew
				SqlUpdate.updateMembershipId(fiscals.get(rowIndex).getMs_id(), fiscals.get(rowIndex).getFiscal_year(), renewCheckBox.isSelected());
				fiscals.get(rowIndex).setCommitted(true);
				addPaidNote(date);
				// if we put an amount in other we need to make a note
				if(new BigDecimal(fiscals.get(rowIndex).getOther()).compareTo(BigDecimal.ZERO) != 0) {
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

		wetslipTextFee.setOnMouseClicked(e -> {
			System.out.println("you clicked on the text");
			vboxWetSlipFee.getChildren().clear();
			vboxWetSlipFee.getChildren().add(wetslipTextField);
		});

		wetslipTextFee.setFill(Color.BLUE);
		wetslipTextFee.setOnMouseEntered(en -> {
			wetslipTextFee.setFill(Color.RED);
		});
		wetslipTextFee.setOnMouseExited(ex -> {
			wetslipTextFee.setFill(Color.BLUE);
		});
		//////////////// SETTING CONTENT //////////////

//		slipTextField.setText(String.valueOf(fiscals.get(rowIndex).getWet_slip()));
		duesTextField.setText(String.valueOf(fiscals.get(rowIndex).getDues()));
		yscTextField.setText(String.valueOf(fiscals.get(rowIndex).getYsc_donation()));
		otherTextField.setText(String.valueOf(fiscals.get(rowIndex).getOther()));
		initiationTextField.setText(String.valueOf(fiscals.get(rowIndex).getInitiation()));
		wetslipTextField.setText(String.valueOf(definedFees.getWet_slip()));
		otherCreditTextField.setText(String.valueOf(fiscals.get(rowIndex).getOther_credit()));

		yspText.setText(fiscals.get(rowIndex).getYsc_donation());
		initiationText.setText(fiscals.get(rowIndex).getInitiation());
		otherFeeText.setText(fiscals.get(rowIndex).getOther());
		workCreditsText.setText(fiscals.get(rowIndex).getCredit());
		otherCreditText.setText(fiscals.get(rowIndex).getOther_credit());
		System.out.println("paid=" + fiscals.get(rowIndex).getPaid());
		totalBalanceText.setText(fiscals.get(rowIndex).getCredit());
		totalCreditText.setText(fiscals.get(rowIndex).getCredit());
		totalPaymentText.setText(fiscals.get(rowIndex).getPaid());
		wetslipTextFee.setText(String.valueOf(definedFees.getWet_slip()));

		
		if (fiscals.get(rowIndex).isSupplemental()) {
			duesTextField.setEditable(true);
			//duesTextField.setText("0");
		} else {
			if (hasOfficer) { // has officer and not
				System.out.println("Member is an officer");
					if(!SqlSelect.isCommitted(fiscals.get(rowIndex).getMoney_id()))	{	// is not committed	
						// committed
//						textFields.getCreditText().setText(duesTextField.getText()); // gets the dues and gives that amount of credit for being an officer
						SqlUpdate.updateField(new BigDecimal(duesTextField.getText()), "money", "credit", fiscals, rowIndex); // updates SQL
						fiscals.get(rowIndex).setCredit(duesTextField.getText());  // sets credit for what dues are
						System.out.println("Record is not committed");
					}
			} else {
				System.out.println("Member is not an officer of the club");
			}
		}
		if(fiscals.get(rowIndex).isCommitted()) setEditable(false);	

		updateBalance();
//		hboxSlip.getChildren().addAll(slipTextField,addWetSlip);

		HBox.setHgrow(gridPane,Priority.ALWAYS);
		gridPane.setHgap(25);
		gridPane.setVgap(5);


		Font font = Font.font("Verdana", FontWeight.BOLD, 16);
		Text text1 = new Text("Fee");
		Text text2 = new Text("Price");
		Text text3 = new Text("Total");
		text1.setFont(font);
		text2.setFont(font);
		text3.setFont(font);

		vboxDues.getChildren().add(duesText);
		vboxDues.setAlignment(Pos.CENTER_RIGHT);
		vboxBeach.getChildren().add(beachText);
		vboxBeach.setAlignment(Pos.CENTER_RIGHT);
		vboxKayak.getChildren().add(kayakRackText);
		vboxKayak.setAlignment(Pos.CENTER_RIGHT);
		vboxKayakShed.getChildren().add(kayakShedText);
		vboxKayakShed.setAlignment(Pos.CENTER_RIGHT);
		vboxSailLoft.getChildren().add(sailLoftText);
		vboxSailLoft.setAlignment(Pos.CENTER_RIGHT);
		vboxSailSchoolLoft.getChildren().add(sailSchoolLoftText);
		vboxSailSchoolLoft.setAlignment(Pos.CENTER_RIGHT);
		vboxWetSlip.getChildren().add(wetSlipText);
		vboxWetSlip.setAlignment(Pos.CENTER_RIGHT);
		vboxWinterStorage.getChildren().add(winterStorageText);
		vboxWinterStorage.setAlignment(Pos.CENTER_RIGHT);
		vboxGateKey.getChildren().add(gateKeyText);
		vboxGateKey.setAlignment(Pos.CENTER_RIGHT);
		vboxSailLoftKey.getChildren().add(sailLKeyText);
		vboxSailLoftKey.setAlignment(Pos.CENTER_RIGHT);
		vboxKayakShedKey.getChildren().add(kayakSKeyText);
		vboxKayakShedKey.setAlignment(Pos.CENTER_RIGHT);
		vboxSailSchoolLoftKey.getChildren().add(sailSSLKeyText);
		vboxSailSchoolLoftKey.setAlignment(Pos.CENTER_RIGHT);

		vboxWorkCredits.getChildren().add(workCreditsText);
		vboxWorkCredits.setAlignment(Pos.CENTER_RIGHT);

		vboxYSC.getChildren().add(yspText);
		vboxYSC.setAlignment(Pos.CENTER_RIGHT);
		vboxInitiation.getChildren().add(initiationText);
		vboxInitiation.setAlignment(Pos.CENTER_RIGHT);
		vboxOther.getChildren().add(otherFeeText);
		vboxOther.setAlignment(Pos.CENTER_RIGHT);
		vboxOtherCredit.getChildren().add(otherCreditText);
		vboxOtherCredit.setAlignment(Pos.CENTER_RIGHT);

		vboxBeachFee.getChildren().add(new Text(String.valueOf(definedFees.getBeach())));
		vboxBeachFee.setAlignment(Pos.CENTER_RIGHT);
		vboxKayakFee.getChildren().add(new Text(String.valueOf(definedFees.getKayak_rack())));
		vboxKayakFee.setAlignment(Pos.CENTER_RIGHT);
		vboxKayakShedFee.getChildren().add(new Text(String.valueOf(definedFees.getKayak_shed())));
		vboxKayakShedFee.setAlignment(Pos.CENTER_RIGHT);
		vboxSailLoftFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_loft())));
		vboxSailLoftFee.setAlignment(Pos.CENTER_RIGHT);
		vboxSailSchoolLoftFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_school_laser_loft())));
		vboxSailSchoolLoftFee.setAlignment(Pos.CENTER_RIGHT);
		vboxWinterStorageFee.getChildren().add(new Text(String.valueOf(definedFees.getWinter_storage())));
		vboxWinterStorageFee.setAlignment(Pos.CENTER_RIGHT);
		vboxGateKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getMain_gate_key())));
		vboxGateKeyFee.setAlignment(Pos.CENTER_RIGHT);
		vboxSailLoftKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_loft_key())));
		vboxSailLoftKeyFee.setAlignment(Pos.CENTER_RIGHT);
		vboxKayakShedKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getKayak_shed_key())));
		vboxKayakShedKeyFee.setAlignment(Pos.CENTER_RIGHT);
		vboxSailSchoolLoftKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_school_loft_key())));
		vboxSailSchoolLoftKeyFee.setAlignment(Pos.CENTER_RIGHT);

		vboxWorkCreditsFee.getChildren().add(new Text(String.valueOf(definedFees.getWork_credit())));
		vboxWorkCreditsFee.setAlignment(Pos.CENTER_RIGHT);


		vboxTitlePrice.getChildren().add(text2);
		vboxTitlePrice.setAlignment(Pos.CENTER_RIGHT);
		vboxTitleTotal.getChildren().add(text3);
		vboxTitleTotal.setAlignment(Pos.CENTER_RIGHT);
		vboxWetSlipFee.getChildren().add(wetslipTextFee);
		vboxWetSlipFee.setAlignment(Pos.CENTER_RIGHT);


		int row = 0;
//		/// header
		gridPane.add(text1 , 0, row, 1, 1);
		gridPane.add(new Text(""), 1, row, 1, 1);
		gridPane.add(new Text(""), 2, row, 1, 1);
		gridPane.add(vboxTitlePrice, 3, row, 1, 1);
		gridPane.add(vboxTitleTotal, 4, row, 1, 1);

		/// Row 1
		row++;
		gridPane.add(new Label("Dues:"), 0, row, 1, 1);
		gridPane.add(duesTextField, 1, row, 1, 1);
		gridPane.add(new Label(""), 2, row, 1, 1);
		gridPane.add(new Label(""), 3, row, 1, 1);
		gridPane.add(vboxDues, 4, row, 1, 1);

		/// Row 2
		row++;
		gridPane.add(new Label("Beach Spot:"), 0, row, 1, 1);
		gridPane.add(beachSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(vboxBeachFee, 3, row, 1, 1);
		gridPane.add(vboxBeach, 4, row, 1, 1);
		/// Row 3
		row++;
		gridPane.add(new Label("Kayak Rack:"), 0, row, 1, 1);
		gridPane.add(kayakRackSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(vboxKayakFee, 3, row, 1, 1);
		gridPane.add(vboxKayak, 4, row, 1, 1);
		/// Row 5
		row++;
		gridPane.add(new Label("Kayak Shed:"), 0, row, 1, 1);
		gridPane.add(kayakShedSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(vboxKayakShedFee, 3, row, 1, 1);
		gridPane.add(vboxKayakShed, 4, row, 1, 1);
		/// Row 6
		row++;
		gridPane.add(new Label("Sail Loft:"), 0, row, 1, 1);
		gridPane.add(sailLoftSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(vboxSailLoftFee, 3, row, 1, 1);
		gridPane.add(vboxSailLoft, 4, row, 1, 1);
		/// Row 7
		row++;
		gridPane.add(new Label("Sail School Loft:"), 0, row, 1, 1);
		gridPane.add(sailSchoolLoftSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(vboxSailSchoolLoftFee, 3, row, 1, 1);
		gridPane.add(vboxSailSchoolLoft, 4, row, 1, 1);
		/// Row 8
		row++;
		gridPane.add(new Label("Wet Slip:"), 0, row, 1, 1);
		gridPane.add(wetSlipSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(vboxWetSlipFee, 3, row, 1, 1);
		gridPane.add(vboxWetSlip, 4, row, 1, 1);
		/// Row 9
		row++;
		gridPane.add(new Label("Winter Storage:"), 0, row, 1, 1);
		gridPane.add(winterStorageSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(vboxWinterStorageFee, 3, row, 1, 1);
		gridPane.add(vboxWinterStorage, 4, row, 1, 1);
		/// Row 10
		row++;
		gridPane.add(new Label("Gate Key:"), 0, row, 1, 1);
		gridPane.add(gateKeySpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(vboxGateKeyFee, 3, row, 1, 1);
		gridPane.add(vboxGateKey, 4, row, 1, 1);
		/// Row 11
		row++;
		gridPane.add(new Label("Sail Loft Key:"), 0, row, 1, 1);
		gridPane.add(sailLKeySpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(vboxSailLoftKeyFee, 3, row, 1, 1);
		gridPane.add(vboxSailLoftKey, 4, row, 1, 1);
		/// Row 12
		row++;
		gridPane.add(new Label("Kayak Shed Key:"), 0, row, 1, 1);
		gridPane.add(kayakSKeySpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(vboxKayakShedKeyFee, 3, row, 1, 1);
		gridPane.add(vboxKayakShedKey, 4, row, 1, 1);
		/// Row 10
		row++;
		gridPane.add(new Label("Sail School Loft Key:"), 0, row, 1, 1);
		gridPane.add(sailSSLKeySpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(vboxSailSchoolLoftKeyFee, 3, row, 1, 1);
		gridPane.add(vboxSailSchoolLoftKey, 4, row, 1, 1);
		/// Row 13
		row++;
		gridPane.add(new Label("YSP Donation:"), 0, row, 1, 1);
		gridPane.add(yscTextField, 1, row, 1, 1);
		gridPane.add(new Label(""), 2, row, 1, 1);
		gridPane.add(new Label(""), 3, row, 1, 1);
		gridPane.add(vboxYSC, 4, row, 1, 1);
		/// Row 14
		row++;
		gridPane.add(new Label("Initiation:"), 0, row, 1, 1);
		gridPane.add(initiationTextField, 1, row, 1, 1);
		gridPane.add(new Label(""), 2, row, 1, 1);
		gridPane.add(new Label(""), 3, row, 1, 1);
		gridPane.add(vboxInitiation, 4, row, 1, 1);
		/// Row 15
		row++;
		gridPane.add(new Label("Other Fee:"), 0, row, 1, 1);
		gridPane.add(otherTextField, 1, row, 1, 1);
		gridPane.add(new Label(""), 2, row, 1, 1);
		gridPane.add(new Label(""), 3, row, 1, 1);
		gridPane.add(vboxOther, 4, row, 1, 1);
		/// Row 16
		row++;
		gridPane.add(new Label("Work Credits:"), 0, row, 1, 1);
		gridPane.add(workCreditSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(vboxWorkCreditsFee, 3, row, 1, 1);
		gridPane.add(vboxWorkCredits, 4, row, 1, 1);
		row++;
		gridPane.add(new Label("Other Credit:"), 0, row, 1, 1);
		gridPane.add(otherCreditTextField, 1, row, 1, 1);
		gridPane.add(new Label(""), 2, row, 1, 1);
		gridPane.add(new Label(""), 3, row, 1, 1);
		gridPane.add(vboxOtherCredit, 4, row, 1, 1);
		// Spacer
		row++;
		gridPane.add(new Label(""), 0, row, 5, 1);
		// Table
		row++;
		gridPane.add(vboxPink, 0, row, 4, 1);
		gridPane.add(vboxButtons, 4, row, 1, 1);
		// spacer
		row++;
		gridPane.add(new Label(""), 0, row, 5, 1);
		// final portion
		row++;
		gridPane.add(new Label("Total Fees:"), 0, row, 1, 1);
		gridPane.add(totalFeesText, 1, row, 3, 1);
		gridPane.add(vboxCommitButton, 4, row, 1, 4);
		row++;
		gridPane.add(new Label("Total Credit:"), 0, row, 3, 1);
		gridPane.add(totalCreditText, 1, row, 3, 1);
		row++;
		gridPane.add(new Label("Payment:"), 0, row, 3, 1);
		gridPane.add(totalPaymentText, 1, row, 3, 1);
		row++;
		gridPane.add(new Label("Balance:"), 0, row, 3, 1);
		gridPane.add(totalBalanceText, 1, row, 3, 1);
		row++;
		gridPane.add(new Label(""), 0, row, 5, 1);
		// final portion
//		gridPane.setGridLinesVisible(true);



		vboxCommitButton.getChildren().addAll(renewCheckBox,commitButton);
		vboxPink.getChildren().add(paymentTableView);
		vboxButtons.getChildren().addAll(buttonAdd, buttonDelete);
		paymentTableView.getColumns().addAll(Arrays.asList(col1,col2,col3,col4));
		scrollPane.setContent(gridPane);
		mainVbox.getChildren().addAll(scrollPane);  // add error hbox in first
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
		case "other_credit":
			fiscals.get(rowIndex).setOther_credit(String.valueOf(newTotalValue));
			break;
		}

		fiscals.get(rowIndex).setTotal(String.valueOf(updateTotalFeeField()));
	}
	
	
	private void addPaidNote(String date) {
//		note.add("Paid $" + textFields.getPaidText().getText() + " leaving a balance of $" + textFields.getBalanceText().getText() + " for "
//				+ fiscals.get(rowIndex).getFiscal_year(), date,0,"P");
	}
	
	private void setEditable(boolean isEditable) {
		changeState(yscTextField,isEditable,true);
		changeState(duesTextField,isEditable,true);
		changeState(otherTextField,isEditable,true);
		changeState(initiationTextField,isEditable,true);
//		changeState(slipTextField,isEditable,true);
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
		  fiscals.get(rowIndex).setTotal(String.valueOf(updateTotalFeeField()));

		  System.out.println(fiscals.get(rowIndex).toString());
		  totalFeesText.setText(String.valueOf(fiscals.get(rowIndex).getTotal()));
		  totalBalanceText.setText(String.valueOf(getBalance()));
		  System.out.println("setting balance=" + getBalance());
		  fiscals.get(rowIndex).setBalance(String.valueOf(getBalance()));
		  SqlUpdate.updateMoney(fiscals.get(rowIndex));  // saves to database
	}
	
	private BigDecimal updateTotalFeeField() {
		BigDecimal dues = new BigDecimal(fiscals.get(rowIndex).getDues());
		BigDecimal beachSpot = new BigDecimal(fiscals.get(rowIndex).getBeach()).multiply(definedFees.getBeach());
		BigDecimal kayakRack = new BigDecimal(fiscals.get(rowIndex).getKayac_rack()).multiply(definedFees.getKayak_rack());
		BigDecimal kayakShed = new BigDecimal(fiscals.get(rowIndex).getKayac_shed()).multiply(definedFees.getKayak_shed());
		BigDecimal sailLoft = new BigDecimal(fiscals.get(rowIndex).getSail_loft()).multiply(definedFees.getSail_loft());
		BigDecimal sailSchoolLoft = new BigDecimal(fiscals.get(rowIndex).getSail_school_laser_loft()).multiply(definedFees.getSail_school_laser_loft());
		BigDecimal wetSlip = new BigDecimal(fiscals.get(rowIndex).getWet_slip());
		BigDecimal winterStorage = new BigDecimal(fiscals.get(rowIndex).getWinter_storage()).multiply(definedFees.getWinter_storage());
		BigDecimal extraKey = new BigDecimal(fiscals.get(rowIndex).getExtra_key()).multiply(definedFees.getMain_gate_key());
		BigDecimal sailLoftKey = new BigDecimal(fiscals.get(rowIndex).getSail_loft_key()).multiply(definedFees.getSail_loft_key());
		BigDecimal kayakShedKey = new BigDecimal(fiscals.get(rowIndex).getKayac_shed_key()).multiply(definedFees.getKayak_shed_key());
		BigDecimal sailSchoolLoftKey = new BigDecimal(fiscals.get(rowIndex).getSail_school_loft_key()).multiply(definedFees.getSail_school_loft_key());
		BigDecimal yscDonation = new BigDecimal(fiscals.get(rowIndex).getYsc_donation());
		BigDecimal other = new BigDecimal(fiscals.get(rowIndex).getOther());
		BigDecimal initiation = new BigDecimal(fiscals.get(rowIndex).getInitiation());
		BigDecimal total = extraKey.add(sailLoftKey).add(kayakShedKey).add(sailSchoolLoftKey).add(beachSpot).add(kayakRack).add(kayakShed)
				.add(sailLoft).add(sailSchoolLoft).add(wetSlip).add(winterStorage).add(yscDonation).add(dues).add(other).add(initiation);
		return total;
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
		System.out.println("Total credit is " + credit);
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

	private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
		TableColumn<T, String> col = new TableColumn<>(title);
		col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
		col.setCellFactory(column -> EditCell.createStringEditCell());
		return col ;
	}
	
} // updateKayakShed
