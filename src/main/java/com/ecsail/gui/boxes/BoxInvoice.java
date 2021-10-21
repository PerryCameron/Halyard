package com.ecsail.gui.boxes;

import com.ecsail.enums.PaymentType;
import com.ecsail.main.EditCell;
import com.ecsail.main.Note;
import com.ecsail.sql.*;
import com.ecsail.structures.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;


// replaces tabCredit, tabBalance, tabPayment, BoxFiscal2 also need to delete Object_Integer
// this class is for buisness logic, Object_invoiceNodes handles most of the GUI

public class BoxInvoice extends HBox {
	private final ObservableList<Object_Money> fiscals;
	private ObservableList<Object_Payment> payments;
	private Object_Money invoice;
	private final Object_InvoiceNodes fnode;
	Object_Membership membership;
	Object_DefinedFee definedFees;
	Object_WorkCredit selectedWorkCreditYear;
	Object_Officer officer;
	final private ObservableList<Object_Person> people;
	boolean hasOfficer;
	final private int rowIndex;
	String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
	private final TableView<Object_Payment> paymentTableView = new TableView<>();
	boolean isCommitted;
	Button addWetSlip = new Button();
	
	public BoxInvoice(Object_Membership m, ObservableList<Object_Person> p, ObservableList<Object_Money> o, int r, Note note, TextField dt) {
		this.membership = m;
		this.people = p;
		this.rowIndex = r;
		this.fiscals = o;
		this.definedFees = SqlSelect.selectDefinedFees(fiscals.get(rowIndex).getFiscal_year());
		this.invoice = fiscals.get(rowIndex);
		this.fnode = new Object_InvoiceNodes(invoice, definedFees, paymentTableView);
		this.selectedWorkCreditYear = SqlSelect.getWorkCredit(fiscals.get(rowIndex).getMoney_id());
		this.hasOfficer = membershipHasOfficer();

		this.isCommitted = fiscals.get(rowIndex).isCommitted();

		///////////// ACTION ///////////////
		getPayment();

		////////////// OBJECTS /////////////////////
		ScrollPane scrollPane = new ScrollPane();

		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox mainVbox = new VBox();
		HBox mainHbox = new HBox();
		VBox vboxTabPanes = new VBox();
		VBox vboxSpinners = new VBox();
		HBox hboxButtonCommit = new HBox();

		/////////////// TABLE ///////////////////
		TableColumn<Object_Payment, String> col1 = createColumn("Amount", Object_Payment::PaymentAmountProperty);
		col1.setPrefWidth(60);
		col1.setStyle( "-fx-alignment: CENTER-RIGHT;");
		col1.setOnEditCommit(
				t -> {
					t.getTableView().getItems().get(
							t.getTablePosition().getRow()).setPaymentAmount(String.valueOf(new BigDecimal(t.getNewValue()).setScale(2, RoundingMode.CEILING)));
					int pay_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPay_id();
					BigDecimal amount = new BigDecimal(t.getNewValue());
					SqlUpdate.updatePayment(pay_id, "amount", String.valueOf(amount.setScale(2, RoundingMode.HALF_UP)));
					// SQL Query getTotalAmount() adds all the payments for us
					BigDecimal totalPaidAmount = BigDecimal.valueOf(SqlSelect.getTotalAmount(fiscals.get(rowIndex).getMoney_id()));
					fnode.getTotalPaymentText().setText(String.valueOf(totalPaidAmount.setScale(2, RoundingMode.HALF_UP)));
					fiscals.get(rowIndex).setPaid(String.valueOf(totalPaidAmount.setScale(2, RoundingMode.HALF_UP)));
					updateBalance();
				}
		);


		// example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
		ObservableList<PaymentType> paymentTypeList = FXCollections.observableArrayList(PaymentType.values());
		TableColumn<Object_Payment, PaymentType> col2 = new TableColumn<>("Type");

		col2.setPrefWidth(55);
		col2.setStyle( "-fx-alignment: CENTER;");
		col2.setCellValueFactory(param -> {
			Object_Payment thisPayment = param.getValue();
			String paymentCode = thisPayment.getPaymentType();
			PaymentType paymentType = PaymentType.getByCode(paymentCode);
			return new SimpleObjectProperty<>(paymentType);
		});

		col2.setCellFactory(ComboBoxTableCell.forTableColumn(paymentTypeList));

		col2.setOnEditCommit((TableColumn.CellEditEvent<Object_Payment, PaymentType> event) -> {
			TablePosition<Object_Payment, PaymentType> pos = event.getTablePosition();
			PaymentType newPaymentType = event.getNewValue();
			int row = pos.getRow();
			Object_Payment thisPayment = event.getTableView().getItems().get(row);
			SqlUpdate.updatePayment(thisPayment.getPay_id(), "payment_type", newPaymentType.getCode());
			// need to update paid from here
			thisPayment.setPaymentType(newPaymentType.getCode());
		});

		TableColumn<Object_Payment, String> col3 = createColumn("Check #", Object_Payment::checkNumberProperty);
		col3.setPrefWidth(55);
		col3.setStyle( "-fx-alignment: CENTER-LEFT;");
		col3.setOnEditCommit(
				t -> {
					t.getTableView().getItems().get(
							t.getTablePosition().getRow()).setCheckNumber(t.getNewValue());
					int pay_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPay_id();
					SqlUpdate.updatePayment(pay_id, "CHECKNUMBER", t.getNewValue());
					//	SqlUpdate.updatePhone("phone", phone_id, t.getNewValue());
				}
		);

		TableColumn<Object_Payment, String> col4 = createColumn("Date", Object_Payment::paymentDateProperty);
		col4.setPrefWidth(70);
		col4.setStyle( "-fx-alignment: CENTER-LEFT;");
		col4.setOnEditCommit(
				t -> {
					t.getTableView().getItems().get(
							t.getTablePosition().getRow()).setPaymentDate(t.getNewValue());
					int pay_id = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPay_id();
					SqlUpdate.updatePayment(pay_id, "payment_date", t.getNewValue());
					//	SqlUpdate.updatePhone("phone", phone_id, t.getNewValue());
				}
		);

		col1.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Boat Name
		col2.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Manufacturer
		col3.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );   // Year
		col4.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Model

		//////////////// ATTRIBUTES ///////////////////

		paymentTableView.setItems(payments);
		HBox.setHgrow(paymentTableView,Priority.ALWAYS);
		paymentTableView.setPrefHeight(115);
		paymentTableView.setFixedCellSize(30);
		paymentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		addWetSlip.setPrefWidth(25);
		addWetSlip.setPrefHeight(25);

		vboxTabPanes.setAlignment(Pos.CENTER);
		vboxSpinners.setAlignment(Pos.CENTER);

		vboxSpinners.setSpacing(5);
		mainHbox.setSpacing(10);

		this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		vboxGrey.setPadding(new Insets(8, 5, 0, 15));
		hboxButtonCommit.setPadding(new Insets(5, 0, 5, 170));
		
		setId("box-blue");
		vboxGrey.setId("box-grey");
		HBox.setHgrow(vboxGrey, Priority.ALWAYS);

		paymentTableView.setEditable(!fiscals.get(rowIndex).isCommitted());

		//////////////// LISTENER //////////////////
		fnode.getButtonAdd().setOnAction(e -> {
			int pay_id = SqlSelect.getNumberOfPayments() + 1; // get last pay_id number
			payments.add(new Object_Payment(pay_id,fiscals.get(rowIndex).getMoney_id(),null,"CH",date, "0",1)); // let's add it to our GUI
			SqlInsert.addPaymentRecord(payments.get(payments.size() -1));
		});

		fnode.getButtonDelete().setOnAction(e -> {
			int selectedIndex = paymentTableView.getSelectionModel().getSelectedIndex();
			if(selectedIndex >= 0) // is something selected?
			SqlDelete.deletePayment(payments.get(selectedIndex));
			paymentTableView.getItems().remove(selectedIndex); // remove it from our GUI
			// SQL Query getTotalAmount() recalculates the payments for us
			BigDecimal totalPaidAmount = BigDecimal.valueOf(SqlSelect.getTotalAmount(fiscals.get(rowIndex).getMoney_id()));
			fnode.getTotalPaymentText().setText(String.valueOf(totalPaidAmount.setScale(2, RoundingMode.HALF_UP)));
			fiscals.get(rowIndex).setPaid(String.valueOf(totalPaidAmount.setScale(2, RoundingMode.HALF_UP)));
			updateBalance();
		});

		// this is only called if you change membership type or open a record or manually type in
		fnode.getDuesTextField().textProperty().addListener((observable, oldValue, newValue) -> {
			if (!SqlSelect.isCommitted(fiscals.get(rowIndex).getMoney_id())) {
				fnode.getDuesText().setText(newValue);
				System.out.println(" dues text field set to " + newValue);
				fiscals.get(rowIndex).setDues(newValue);
				updateBalance();
			} else {
				System.out.println("Record is committed, no changes made");
			}
		});
		
		fnode.getDuesTextField().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	if(!isNumeric(fnode.getDuesTextField().getText())) {
						fnode.getDuesTextField().setText("0");
	            	}
	            	BigDecimal dues = new BigDecimal(fnode.getDuesTextField().getText());
	            	updateItem(dues,"dues");
					fnode.getDuesTextField().setText(String.valueOf(dues.setScale(2, RoundingMode.HALF_UP)));
	            	updateBalance();
	            }
	        });

		SpinnerValueFactory<Integer> beachValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getBeach());
		fnode.getBeachSpinner().setValueFactory(beachValueFactory);
		fnode.getBeachSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setBeach(newValue);
			fnode.getBeachText().setText(String.valueOf(definedFees.getBeach().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> kayakRackValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getKayac_rack());
		fnode.getKayakRackSpinner().setValueFactory(kayakRackValueFactory);
		fnode.getKayakRackSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setKayac_rack(newValue);
			fnode.getKayakRackText().setText(String.valueOf(definedFees.getKayak_rack().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> kayakShedValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getKayac_shed());
		fnode.getKayakShedSpinner().setValueFactory(kayakShedValueFactory);
		fnode.getKayakShedSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setKayac_shed(newValue);
			fnode.getKayakShedText().setText(String.valueOf(definedFees.getKayak_shed().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> sailLoftValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, fiscals.get(rowIndex).getSail_loft());
		fnode.getSailLoftSpinner().setValueFactory(sailLoftValueFactory);
		fnode.getSailLoftSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setSail_loft(newValue);
			fnode.getSailLoftText().setText(String.valueOf(definedFees.getSail_loft().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> sailSchoolLoftValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, fiscals.get(rowIndex).getSail_school_laser_loft());
		fnode.getSailSchoolLoftSpinner().setValueFactory(sailSchoolLoftValueFactory);
		fnode.getSailSchoolLoftSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setSail_school_laser_loft(newValue);
			fnode.getSailSchoolLoftText().setText(String.valueOf(definedFees.getSail_school_laser_loft().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, getInitialWetSlipValue(fiscals.get(rowIndex).getWet_slip()));
		fnode.getWetSlipSpinner().setValueFactory(wetSlipValueFactory);
		fnode.getWetSlipSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			String wetSlip = String.valueOf(new BigDecimal(fnode.getWetslipTextFee().getText()).multiply(BigDecimal.valueOf(newValue)));
			fiscals.get(rowIndex).setWet_slip(wetSlip);
			fnode.getWetSlipText().setText(wetSlip);
			updateBalance();
		});

		// this is for changing the value from default
		fnode.getWetslipTextField().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!isNumeric(fnode.getWetslipTextField().getText())) {
					fnode.getWetslipTextField().setText("0.00");
				}
				BigDecimal slip = new BigDecimal(fnode.getWetslipTextField().getText());
				fnode.getWetSlipText().setText(String.valueOf(slip.multiply(BigDecimal.valueOf(fnode.getWetSlipSpinner().getValue()))));
				updateItem(slip,"wetslip");
				fnode.getWetslipTextField().setText(String.valueOf(slip.setScale(2, RoundingMode.HALF_UP)));
				fnode.getWetslipTextFee().setText(String.valueOf(slip.setScale(2, RoundingMode.HALF_UP)));
				updateBalance();
				fnode.getVboxWetSlip().getChildren().clear();
				fnode.getVboxWetSlipFee().getChildren().add(fnode.getWetslipTextFee());
			}
		});

		SpinnerValueFactory<Integer> winterStorageValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, fiscals.get(rowIndex).getWinter_storage());
		fnode.getWinterStorageSpinner().setValueFactory(winterStorageValueFactory);
		fnode.getWinterStorageSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setWinter_storage(newValue);
			fnode.getWinterStorageText().setText(String.valueOf(definedFees.getWinter_storage().multiply(BigDecimal.valueOf(newValue))));
			updateBalance();
		});

		SpinnerValueFactory<Integer> gateKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getExtra_key());
		fnode.getGateKeySpinner().setValueFactory(gateKeyValueFactory);
		fnode.getGateKeySpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fnode.getGateKeyText().setText(String.valueOf(definedFees.getMain_gate_key().multiply(BigDecimal.valueOf(newValue))));
			fiscals.get(rowIndex).setExtra_key(newValue);
			updateBalance();
		});

		SpinnerValueFactory<Integer> sailLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getSail_loft_key());
		fnode.getSailLKeySpinner().setValueFactory(sailLKeyValueFactory);
		fnode.getSailLKeySpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fnode.getSailLKeyText().setText(String.valueOf(definedFees.getSail_loft_key().multiply(BigDecimal.valueOf(newValue))));
			fiscals.get(rowIndex).setSail_loft_key(newValue);
			updateBalance();
		});

		SpinnerValueFactory<Integer> kayakSKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getKayac_shed_key());
		fnode.getKayakSKeySpinner().setValueFactory(kayakSKeyValueFactory);
		fnode.getKayakSKeySpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fnode.getKayakSKeyText().setText(String.valueOf(definedFees.getKayak_shed_key().multiply(BigDecimal.valueOf(newValue))));
			fiscals.get(rowIndex).setKayac_shed_key(newValue);
			updateBalance();
		});

		SpinnerValueFactory<Integer> sailSSLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getSail_school_loft_key());
		fnode.getSailSSLKeySpinner().setValueFactory(sailSSLKeyValueFactory);
		fnode.getSailSSLKeySpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fnode.getSailSSLKeyText().setText(String.valueOf(definedFees.getSail_school_loft_key().multiply(BigDecimal.valueOf(newValue))));
			fiscals.get(rowIndex).setSail_school_loft_key(newValue);
			updateBalance();
		});

		fnode.getYscTextField().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!isNumeric(fnode.getYscTextField().getText())) {
					fnode.getYscTextField().setText("0.00");
				}
				BigDecimal ysc = new BigDecimal(fnode.getYscTextField().getText());
				fnode.getYspText().setText(String.valueOf(ysc.setScale(2, RoundingMode.HALF_UP)));
				updateItem(ysc.setScale(2, RoundingMode.HALF_UP), "ysc");
				fnode.getYscTextField().setText(String.valueOf(ysc.setScale(2, RoundingMode.HALF_UP)));
				updateBalance();
			}
		});

		fnode.getInitiationTextField().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!isNumeric(fnode.getInitiationTextField().getText())) {
					fnode.getInitiationTextField().setText("0.00");
				}
				BigDecimal initiation = new BigDecimal(fnode.getInitiationTextField().getText());
				updateItem(initiation.setScale(2, RoundingMode.HALF_UP), "initiation");
				fnode.getInitiationTextField().setText(String.valueOf(initiation.setScale(2, RoundingMode.HALF_UP)));
				fnode.getInitiationText().setText(String.valueOf(initiation.setScale(2, RoundingMode.HALF_UP)));
				updateBalance();
			}
		});

		fnode.getOtherTextField().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!isNumeric(fnode.getOtherTextField().getText())) {
					fnode.getOtherTextField().setText("0.00");
				}
				BigDecimal other = new BigDecimal(fnode.getOtherTextField().getText());
				fnode.getOtherTextField().setText(String.valueOf(other.setScale(2, RoundingMode.HALF_UP)));
				updateItem(other.setScale(2, RoundingMode.HALF_UP),"other");
				updateBalance();
			}
		});

		SpinnerValueFactory<Integer> workCreditValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 40, fiscals.get(rowIndex).getWork_credit());
		fnode.getWorkCreditSpinner().setValueFactory(workCreditValueFactory);
		fnode.getWorkCreditSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setWork_credit(newValue);
			String workCredits = String.valueOf(definedFees.getWork_credit().multiply(BigDecimal.valueOf(newValue)));
			fnode.getWorkCreditsText().setText(workCredits);
			updateBalance();
		});

		fnode.getOtherCreditTextField().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!isNumeric(fnode.getOtherCreditTextField().getText())) {
					fnode.getOtherCreditTextField().setText("0.00");
				}
				BigDecimal otherCredit = new BigDecimal(fnode.getOtherCreditTextField().getText());
				fnode.getOtherCreditTextField().setText(String.valueOf(otherCredit.setScale(2, RoundingMode.HALF_UP)));
				fiscals.get(rowIndex).setOther_credit(String.valueOf(otherCredit));
				fnode.getOtherCreditTextField().setText(String.valueOf(otherCredit.setScale(2, RoundingMode.HALF_UP)));
				updateBalance();
			}
		});

		fnode.getOtherCreditTextField().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//focus out
			if (oldValue) {  // we have focused and unfocused
				if(!isNumeric(fnode.getOtherCreditTextField().getText())) {
					fnode.getOtherCreditTextField().setText("0.00");
				}
				BigDecimal otherCredit = new BigDecimal(fnode.getOtherCreditTextField().getText());
//				updateItem(otherCredit.setScale(2, RoundingMode.HALF_UP), "other_credit");
				fnode.getOtherCreditTextField().setText(String.valueOf(otherCredit.setScale(2, RoundingMode.HALF_UP)));
				fnode.getOtherCreditText().setText(String.valueOf(otherCredit.setScale(2, RoundingMode.HALF_UP)));
				updateBalance();
			}
		});

		fnode.getCommitButton().setOnAction((event) -> {
			if (!fiscals.get(rowIndex).isCommitted()) {
				if (!fnode.getTotalBalanceText().getText().equals("0.00"))
					fnode.getTotalBalanceText().setStyle("-fx-background-color: #f23a50");
				System.out.println("total at commit is " + fiscals.get(rowIndex).getTotal());
				SqlUpdate.updateMoney(fiscals.get(rowIndex));
				SqlUpdate.commitFiscalRecord(fiscals.get(rowIndex).getMoney_id(), true);// this could be placed in line above
				String date = SqlSelect.getPaymentDate(fiscals.get(rowIndex).getMoney_id()); // dates note to check
				// update membership_id record to renew or non-renew
				SqlUpdate.updateMembershipId(fiscals.get(rowIndex).getMs_id(), fiscals.get(rowIndex).getFiscal_year(), fnode.getRenewCheckBox().isSelected());
				fiscals.get(rowIndex).setCommitted(true);
				addPaidNote(date);
				// if we put an amount in other we need to make a note
				if(new BigDecimal(fiscals.get(rowIndex).getOther()).compareTo(BigDecimal.ZERO) != 0) {
					// make sure the memo doesn't already exist
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

		fnode.getWetslipTextFee().setOnMouseClicked(e -> {
			System.out.println("you clicked on the text");
			fnode.getVboxWetSlipFee().getChildren().clear();
			fnode.getVboxWetSlipFee().getChildren().add(fnode.getWetslipTextField());
		});

		fnode.getWetslipTextFee().setFill(Color.BLUE);
		fnode.getWetslipTextFee().setOnMouseEntered(en -> fnode.getWetslipTextFee().setFill(Color.RED));
		fnode.getWetslipTextFee().setOnMouseExited(ex -> fnode.getWetslipTextFee().setFill(Color.BLUE));

		if (fiscals.get(rowIndex).isSupplemental()) { // have we already created a record for this year?
			fnode.getDuesTextField().setEditable(true);
			//duesTextField.setText("0");
		} else {
			if (hasOfficer) { // has officer and not
				System.out.println("Member is an officer");
				fiscals.get(rowIndex).setOfficer_credit(String.valueOf(definedFees.getDues_regular()));
				if(!SqlSelect.isCommitted(fiscals.get(rowIndex).getMoney_id()))	{	// is not committed
					System.out.println("Record is not committed");
				}
			} else {
				System.out.println("Member is not an officer of the club");
				fiscals.get(rowIndex).setOfficer_credit("0.00");
			}
		}
		updateBalance(); // updates and saves
		//////////////// SETTING CONTENT //////////////
		fnode.getDuesText().setText(String.valueOf(fiscals.get(rowIndex).getDues()));
		fnode.getDuesTextField().setText(String.valueOf(fiscals.get(rowIndex).getDues()));
		fnode.getYscTextField().setText(String.valueOf(fiscals.get(rowIndex).getYsc_donation()));
		fnode.getOtherTextField().setText(String.valueOf(fiscals.get(rowIndex).getOther()));
		fnode.getInitiationTextField().setText(String.valueOf(fiscals.get(rowIndex).getInitiation()));
		fnode.getWetslipTextField().setText(String.valueOf(definedFees.getWet_slip()));
		fnode.getOtherCreditTextField().setText(String.valueOf(fiscals.get(rowIndex).getOther_credit()));
		fnode.getYspText().setText(fiscals.get(rowIndex).getYsc_donation());
		fnode.getInitiationText().setText(fiscals.get(rowIndex).getInitiation());
		fnode.getOtherFeeText().setText(fiscals.get(rowIndex).getOther());

		fnode.getWorkCreditsText().setText(String.valueOf(countWorkCredits()));

		fnode.getOtherCreditText().setText(fiscals.get(rowIndex).getOther_credit());
		fnode.getTotalBalanceText().setText(fiscals.get(rowIndex).getCredit());
		fnode.getTotalCreditText().setText(fiscals.get(rowIndex).getCredit());
		fnode.getTotalPaymentText().setText(fiscals.get(rowIndex).getPaid());
		fnode.getWetslipTextFee().setText(String.valueOf(definedFees.getWet_slip()));
		fnode.getBeachText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getBeach()).multiply(definedFees.getBeach())));
		fnode.getKayakRackText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getKayac_rack()).multiply(definedFees.getKayak_rack())));
		fnode.getKayakShedText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getKayac_shed()).multiply(definedFees.getKayak_shed())));
		fnode.getSailLoftText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getSail_loft()).multiply(definedFees.getSail_loft())));
		fnode.getSailSchoolLoftText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getSail_school_laser_loft()).multiply(definedFees.getSail_school_laser_loft())));
		fnode.getWetSlipText().setText(fiscals.get(rowIndex).getWet_slip());
		fnode.getWinterStorageText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getWinter_storage()).multiply(definedFees.getWinter_storage())));

		fnode.getGateKeyText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getExtra_key()).multiply(definedFees.getMain_gate_key())));
		fnode.getSailLKeyText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getSail_loft_key()).multiply(definedFees.getSail_loft_key())));
		fnode.getKayakSKeyText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getKayac_shed_key()).multiply(definedFees.getKayak_shed_key())));
		fnode.getSailSSLKeyText().setText(String.valueOf(BigDecimal.valueOf(fiscals.get(rowIndex).getSail_school_loft_key()).multiply(definedFees.getSail_school_loft_key())));
		fnode.getPositionCreditText().setText(fiscals.get(rowIndex).getOfficer_credit());


		setEditable(!fiscals.get(rowIndex).isCommitted());

		updateBalance();

		paymentTableView.getColumns().addAll(Arrays.asList(col1,col2,col3,col4));
		scrollPane.setContent(fnode.getGridPane());
		mainVbox.getChildren().addAll(scrollPane);  // add error HBox in first
		vboxGrey.getChildren().addAll(mainVbox);
		getChildren().addAll(vboxGrey);
	}

	//////////////////////  CLASS METHODS ///////////////////////////
	private int getInitialWetSlipValue(String wet_slip) {
		int startpoint = 1;
		BigDecimal wetSlip = new BigDecimal(wet_slip);
		if(wetSlip.compareTo(BigDecimal.ZERO) == 0) startpoint = 0;
		return startpoint;
	}

	private void getPayment() {
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
	}


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
		fnode.clearGridPane();
		if(isEditable)  {
			fnode.populateUncommitted();
		} else {
			fnode.populateCommitted();
		}
		System.out.println("setting committed");
	}
	
	private void updateBalance() {
		  // writes total to money object, then displays on screen
		  fiscals.get(rowIndex).setTotal(String.valueOf(updateTotalFeeField()));
		  fnode.getTotalFeesText().setText(String.valueOf(fiscals.get(rowIndex).getTotal()));
		  // writes credit to money object, then displays on screen
		  fiscals.get(rowIndex).setCredit(String.valueOf(countTotalCredit()));
		  fnode.getTotalCreditText().setText(fiscals.get(rowIndex).getCredit());
		  // writes balance to money object, then displays on screen
		  fiscals.get(rowIndex).setBalance(String.valueOf(getBalance()));
		  fnode.getTotalBalanceText().setText(fiscals.get(rowIndex).getBalance());
		  // prints money object to console and then updates to database
		  System.out.println(fiscals.get(rowIndex).toString());
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
		return extraKey.add(sailLoftKey).add(kayakShedKey).add(sailSchoolLoftKey).add(beachSpot).add(kayakRack).add(kayakShed)
				.add(sailLoft).add(sailSchoolLoft).add(wetSlip).add(winterStorage).add(yscDonation).add(dues).add(other).add(initiation);
	}

	private BigDecimal getBalance() {
		BigDecimal total = new BigDecimal(fiscals.get(rowIndex).getTotal());
		BigDecimal paid = new BigDecimal(fiscals.get(rowIndex).getPaid());
		BigDecimal credit = new BigDecimal(fiscals.get(rowIndex).getCredit());
		BigDecimal balance = total.subtract(paid).subtract(credit);
		System.out.println("total balance=" + balance);
		return balance;
	}

	// counts work credit or position credit and adds it to other credit
	private BigDecimal countCredit() {
		BigDecimal credit;
		if(membershipHasOfficer()) {
			credit = new BigDecimal(fiscals.get(rowIndex).getOfficer_credit());  // inserts credit for member type into fiscal
			//System.out.println("Has an officer credit changed to=" + credit);
		} else {
			credit = countWorkCredits();
		}
		return credit;
	}

	private BigDecimal countWorkCredits() {
		BigDecimal credit = definedFees.getWork_credit().multiply(BigDecimal.valueOf(fiscals.get(rowIndex).getWork_credit()));
		return credit;
	}

	private BigDecimal countTotalCredit() {
		BigDecimal normalCredit = countCredit();
		// this if then is to fix older records with a different format
		BigDecimal otherCredit;
		if(isNull(fiscals.get(rowIndex).getOther_credit())) {
			otherCredit = new BigDecimal("0.00");
		} else {
			otherCredit = new BigDecimal(fiscals.get(rowIndex).getOther_credit());
		}
		BigDecimal totalCredit = normalCredit.add(otherCredit);
		System.out.println("total credit=" + totalCredit);
		return totalCredit;
	}

	private boolean isNull(String testcase) {
		return testcase == null;
	}
	
	private Boolean membershipHasOfficer() {
		Boolean isOfficer;
		boolean finalResult = false;
		for (Object_Person per : people) {
			isOfficer = SqlExists.isOfficer(per, fiscals.get(rowIndex).getFiscal_year());
			if(isOfficer) {  // we will add in pid here if need be
				finalResult = true;
//				isOfficer = false;  // reset for next iteration
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
	
}
