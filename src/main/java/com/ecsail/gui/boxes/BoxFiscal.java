package com.ecsail.gui.boxes;

import com.ecsail.enums.PaymentType;
import com.ecsail.gui.tabs.TabBalance;
import com.ecsail.gui.tabs.TabCredit;
import com.ecsail.gui.tabs.TabKey;
import com.ecsail.gui.tabs.TabPayment;
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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

public class BoxFiscal extends HBox {
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

	Object_BalanceText textFields = new Object_BalanceText();
	GridPane gridPane = new GridPane();
	private TableView<Object_Payment> paymentTableView = new TableView<Object_Payment>();
	private final TextField yscTextField = new TextField();
	private final TextField duesTextField;
	private final TextField slipTextField = new TextField();
	private final TextField otherTextField = new TextField();
	private final TextField initiationTextField = new TextField();
	private final TextField wetslipTextField = new TextField();

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


	private final String disabledColor = "-fx-background-color: #d5dade";
	boolean isCommited;
	Button addWetSlip = new Button();
	
	public BoxFiscal(Object_Membership m, ObservableList<Object_Person> p, ObservableList<Object_Money> o, int r, Note n, TextField dt) {
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

		yspText.setText(fiscals.get(rowIndex).getYsc_donation());
		initiationText.setText(fiscals.get(rowIndex).getInitiation());
		otherFeeText.setText(fiscals.get(rowIndex).getOther());
		workCreditsText.setText(fiscals.get(rowIndex).getCredit());

		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox mainVbox = new VBox();
		HBox mainHbox = new HBox();
		VBox vboxTabPanes = new VBox();
		VBox vboxSpinners = new VBox();
		HBox hboxButtonCommit = new HBox();
		HBox hboxSlip = new HBox();

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
//		Button addWetSlip = new Button();
		Button buttonAdd = new Button("Add");
		Button buttonDelete = new Button("Delete");
		VBox vboxButtons = new VBox();

		/////////////// TABLE ///////////////////
		TableColumn<Object_Payment, String> Col1 = createColumn("Amount", Object_Payment::PaymentAmountProperty);
		Col1.setPrefWidth(60);
		Col1.setStyle( "-fx-alignment: CENTER-RIGHT;");
		Col1.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<Object_Payment, String>>() {
					@Override
					public void handle(TableColumn.CellEditEvent<Object_Payment, String> t) {
						((Object_Payment) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
						).setPaymentAmount(t.getNewValue());
						int pay_id = ((Object_Payment) t.getTableView().getItems().get(t.getTablePosition().getRow())).getPay_id();
						//System.out.println("pay_id=" + pay_id);
						//System.out.println("money_id=" + fiscalRecord.getMoney_id());
						//System.out.println("payment amount=" + t.getNewValue());
						SqlUpdate.updatePayment(pay_id, "amount", t.getNewValue());
						int totalAmount = SqlSelect.getTotalAmount(fiscals.get(rowIndex).getMoney_id());
						//System.out.println("Total Amount=" + totalAmount);
						// used balanceTextfields.getPaid() to write to.
//						balanceTextField.getPaidText().setText(totalAmount + "");
						//	SqlUpdate.updatePhone("phone", phone_id, t.getNewValue());
					}
				}
		);


		// example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
		ObservableList<PaymentType> paymentTypeList = FXCollections.observableArrayList(PaymentType.values());
		TableColumn<Object_Payment, PaymentType> Col2 = new TableColumn<Object_Payment, PaymentType>("Type");

		Col2.setPrefWidth(55);
		Col2.setStyle( "-fx-alignment: CENTER;");
		Col2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object_Payment, PaymentType>, ObservableValue<PaymentType>>() {

			@Override
			public ObservableValue<PaymentType> call(TableColumn.CellDataFeatures<Object_Payment, PaymentType> param) {
				Object_Payment thisPayment = param.getValue();
				String paymentCode = thisPayment.getPaymentType();
				PaymentType paymentType = PaymentType.getByCode(paymentCode);
				return new SimpleObjectProperty<PaymentType>(paymentType);
			}
		});

		Col2.setCellFactory(ComboBoxTableCell.forTableColumn(paymentTypeList));

		Col2.setOnEditCommit((TableColumn.CellEditEvent<Object_Payment, PaymentType> event) -> {
			TablePosition<Object_Payment, PaymentType> pos = event.getTablePosition();
			PaymentType newPaymentType = event.getNewValue();
			int row = pos.getRow();
			Object_Payment thisPayment = event.getTableView().getItems().get(row);
			SqlUpdate.updatePayment(thisPayment.getPay_id(), "payment_type", newPaymentType.getCode());
			// need to update paid from here
			//SqlUpdate.updatePhone("phone_type", thisPhone.getPhone_ID(), newPhoneType.getCode());
			thisPayment.setPaymentType(newPaymentType.getCode());
		});

		TableColumn<Object_Payment, String> Col3 = createColumn("Check #", Object_Payment::checkNumberProperty);
		Col3.setPrefWidth(55);
		Col3.setStyle( "-fx-alignment: CENTER-LEFT;");
		Col3.setOnEditCommit(
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

		TableColumn<Object_Payment, String> Col4 = createColumn("Date", Object_Payment::paymentDateProperty);
		Col4.setPrefWidth(70);
		Col4.setStyle( "-fx-alignment: CENTER-LEFT;");
		Col4.setOnEditCommit(
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


		//////////////// ATTRIBUTES ///////////////////

		buttonAdd.setPrefWidth(60);
		buttonDelete.setPrefWidth(60);
		vboxButtons.setSpacing(5);

		paymentTableView.setItems(payments);
		HBox.setHgrow(paymentTableView,Priority.ALWAYS);
		//paymentTableView.setPrefWidth(225);
		paymentTableView.setPrefHeight(115);
		paymentTableView.setFixedCellSize(30);

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
		slipTextField.setPrefWidth(40);
		addWetSlip.setPrefWidth(25);
		addWetSlip.setPrefHeight(25);
		yscTextField.setPrefWidth(65);
		otherTextField.setPrefWidth(65);
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

//		duesTextField.setStyle(disabledColor);
		
		vboxTabPanes.setAlignment(Pos.CENTER);
		vboxSpinners.setAlignment(Pos.CENTER);
		
		vboxTabPanes.setSpacing(5);
		vboxSpinners.setSpacing(5);
		mainHbox.setSpacing(10);

		textFields.getPaidText().setEditable(false);
		textFields.getCreditText().setEditable(false);
		textFields.getTotalFeesText().setEditable(false);

		this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		vboxGrey.setPadding(new Insets(8, 5, 0, 15));
		hboxButtonCommit.setPadding(new Insets(5, 0, 5, 170));	
		
		setId("box-blue");
		vboxGrey.setId("box-grey");

		HBox.setHgrow(vboxGrey, Priority.ALWAYS);

		//////////////// LISTENER //////////////////
			
		textFields.getBalanceText().textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("0"))
				textFields.getBalanceText().setStyle("-fx-background-color: #30e65a");
		});
		
		// this is only called if you changer membership type or open a record or manually type in
		duesTextField.textProperty().addListener((observable, oldValue, newValue) -> {
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

		SpinnerValueFactory<Integer> workCreditValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 40, 0);
		workCreditSpinner.setValueFactory(workCreditValueFactory);
		workCreditSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			fiscals.get(rowIndex).setBeach(newValue);
			updateBalance();
		});

		SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, 0);
		wetSlipSpinner.setValueFactory(wetSlipValueFactory);
		wetSlipSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
//			fiscals.get(rowIndex).setBeach(newValue);
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
				  SqlUpdate.updateMoney(fiscals.get(rowIndex));
			});

		SpinnerValueFactory<Integer> gateKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getExtra_key());
		gateKeySpinner.setValueFactory(gateKeyValueFactory);
		gateKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
//			selectedFiscalYear.setExtra_key(newValue);
//			countKeys();
		});

		SpinnerValueFactory<Integer> sailLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getSail_loft_key());
		sailLKeySpinner.setValueFactory(sailLKeyValueFactory);
		sailLKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
//			selectedFiscalYear.setSail_loft_key(newValue);
//			countKeys();
		});

		SpinnerValueFactory<Integer> kayakSKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getKayac_shed_key());
		kayakSKeySpinner.setValueFactory(kayakSKeyValueFactory);
		kayakSKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
//			selectedFiscalYear.setKayac_shed_key(newValue);
//			countKeys();
		});

		SpinnerValueFactory<Integer> sailSSLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, fiscals.get(rowIndex).getSail_school_loft_key());
		sailSSLKeySpinner.setValueFactory(sailSSLKeyValueFactory);
		sailSSLKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
//			selectedFiscalYear.setSail_school_loft_key(newValue);
//			countKeys();
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
		
		yscTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	if(!isNumeric(yscTextField.getText())) {
	            		yscTextField.setText("0.00");
	            	}
					BigDecimal ysc = new BigDecimal(yscTextField.getText());
	            	updateItem(ysc, "ysc");
					yscTextField.setText(String.valueOf(ysc.setScale(2)));
	            	updateBalance();
	            }
	        });
		
		slipTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	if(!isNumeric(otherTextField.getText())) {
	            		otherTextField.setText("0.00");
	            	}
					BigDecimal slip = new BigDecimal(slipTextField.getText());
					slip.setScale(2);
	            	updateItem(slip,"wetslip");
					slipTextField.setText(String.valueOf(slip.setScale(2)));
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
	            	updateItem(other,"other");
					otherTextField.setText(String.valueOf(other.setScale(2)));
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
            	slipTextField.setText(definedFees.getWet_slip() + "");
            	updateItem(new BigDecimal(slipTextField.getText()),"wetslip");
            	updateBalance();
            	SqlUpdate.updateMoney(fiscals.get(rowIndex));
            });
		
		//////////////// SETTING CONTENT //////////////
		
		textFields.getCreditText().setText(fiscals.get(rowIndex).getCredit() + "");
		textFields.getTotalFeesText().setText(fiscals.get(rowIndex).getTotal() + "");
		textFields.getPaidText().setText(fiscals.get(rowIndex).getPaid() + "");
		slipTextField.setText(fiscals.get(rowIndex).getWet_slip() +"");
		duesTextField.setText(fiscals.get(rowIndex).getDues() + "");
		yscTextField.setText(fiscals.get(rowIndex).getYsc_donation() + "");
		otherTextField.setText(fiscals.get(rowIndex).getOther() + "");
		initiationTextField.setText(fiscals.get(rowIndex).getInitiation() + "");
		wetslipTextField.setText(String.valueOf(definedFees.getWet_slip()));
		
		if (fiscals.get(rowIndex).isSupplemental()) {
			duesTextField.setEditable(true);
			//duesTextField.setText("0");
		} else {
			if (hasOfficer) { // has officer and not
				System.out.println("Member is an officer");
					if(!SqlSelect.isCommitted(fiscals.get(rowIndex).getMoney_id()))	{	// is not committed	
						// committed
						textFields.getCreditText().setText(duesTextField.getText()); // gets the dues and gives that amount of credit for being an officer
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
		textFields.getBalanceText().setText(getBalance() + "");	
//		MoneyTabPane.getTabs().addAll(moneyTab,paymentTab);
//		keysAndCreditsTabPane.getTabs().addAll(keyTab, creditTab);
		hboxSlip.getChildren().addAll(slipTextField,addWetSlip);

		HBox.setHgrow(gridPane,Priority.ALWAYS);
		gridPane.setHgap(30);
		gridPane.setVgap(5);


		Font font = Font.font("Verdana", FontWeight.BOLD, 16);
		Text text1 = new Text("Fee");
		Text text2 = new Text("Price");
		Text text3 = new Text("Total");
		text1.setFont(font);
		text2.setFont(font);
		text3.setFont(font);

		int row = 0;
//		/// header
		gridPane.add(text1 , 0, row, 1, 1);
		gridPane.add(new Text(""), 1, row, 1, 1);
		gridPane.add(new Text(""), 2, row, 1, 1);
		gridPane.add(text2, 3, row, 1, 1);
		gridPane.add(text3, 4, row, 1, 1);

		/// Row 1
		row++;
		gridPane.add(new Label("Dues:"), 0, row, 1, 1);
		gridPane.add(duesTextField, 1, row, 1, 1);
		gridPane.add(new Label(""), 2, row, 1, 1);
		gridPane.add(new Label(""), 3, row, 1, 1);
		gridPane.add(duesText, 4, row, 1, 1);

		/// Row 2
		row++;
		gridPane.add(new Label("Beach Spot:"), 0, row, 1, 1);
		gridPane.add(beachSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(new Label(String.valueOf(definedFees.getBeach())), 3, row, 1, 1);
		gridPane.add(beachText, 4, row, 1, 1);
		/// Row 3
		row++;
		gridPane.add(new Label("Kayak Rack:"), 0, row, 1, 1);
		gridPane.add(kayakRackSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(new Label(String.valueOf(definedFees.getKayak_rack())), 3, row, 1, 1);
		gridPane.add(kayakRackText, 4, row, 1, 1);
		/// Row 5
		row++;
		gridPane.add(new Label("Kayak Shed:"), 0, row, 1, 1);
		gridPane.add(kayakShedSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(new Label(String.valueOf(definedFees.getKayak_shed())), 3, row, 1, 1);
		gridPane.add(kayakShedText, 4, row, 1, 1);
		/// Row 6
		row++;
		gridPane.add(new Label("Sail Loft:"), 0, row, 1, 1);
		gridPane.add(sailLoftSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(new Label(String.valueOf(definedFees.getSail_loft())), 3, row, 1, 1);
		gridPane.add(sailLoftText, 4, row, 1, 1);
		/// Row 7
		row++;
		gridPane.add(new Label("Sail School Loft:"), 0, row, 1, 1);
		gridPane.add(sailSchoolLoftSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(new Label(String.valueOf(definedFees.getSail_school_laser_loft())), 3, row, 1, 1);
		gridPane.add(sailSchoolLoftText, 4, row, 1, 1);
		/// Row 8
		row++;
		gridPane.add(new Label("Wet Slip:"), 0, row, 1, 1);
		gridPane.add(wetSlipSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(wetslipTextField, 3, row, 1, 1);
		gridPane.add(wetSlipText, 4, row, 1, 1);
		/// Row 9
		row++;
		gridPane.add(new Label("Winter Storage:"), 0, row, 1, 1);
		gridPane.add(winterStorageSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(new Label(String.valueOf(definedFees.getWinter_storage())), 3, row, 1, 1);
		gridPane.add(winterStorageText, 4, row, 1, 1);
		/// Row 10
		row++;
		gridPane.add(new Label("Gate Key:"), 0, row, 1, 1);
		gridPane.add(gateKeySpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(new Label(String.valueOf(definedFees.getMain_gate_key())), 3, row, 1, 1);
		gridPane.add(gateKeyText, 4, row, 1, 1);
		/// Row 11
		row++;
		gridPane.add(new Label("Sail Loft Key:"), 0, row, 1, 1);
		gridPane.add(sailLKeySpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(new Label(String.valueOf(definedFees.getSail_loft_key())), 3, row, 1, 1);
		gridPane.add(sailLKeyText, 4, row, 1, 1);
		/// Row 12
		row++;
		gridPane.add(new Label("Kayak Shed Key:"), 0, row, 1, 1);
		gridPane.add(kayakSKeySpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(new Label(String.valueOf(definedFees.getKayak_shed_key())), 3, row, 1, 1);
		gridPane.add(kayakSKeyText, 4, row, 1, 1);
		/// Row 10
		row++;
		gridPane.add(new Label("Sail School Loft Key:"), 0, row, 1, 1);
		gridPane.add(sailSSLKeySpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(new Label(String.valueOf(definedFees.getSail_school_loft_key())), 3, row, 1, 1);
		gridPane.add(sailSSLKeyText, 4, row, 1, 1);
		/// Row 13
		row++;
		gridPane.add(new Label("YSP Donation:"), 0, row, 1, 1);
		gridPane.add(yscTextField, 1, row, 1, 1);
		gridPane.add(new Label(""), 2, row, 1, 1);
		gridPane.add(new Label(""), 3, row, 1, 1);
		gridPane.add(yspText, 4, row, 1, 1);
		/// Row 14
		row++;
		gridPane.add(new Label("Initiation:"), 0, row, 1, 1);
		gridPane.add(initiationTextField, 1, row, 1, 1);
		gridPane.add(new Label(""), 2, row, 1, 1);
		gridPane.add(new Label(""), 3, row, 1, 1);
		gridPane.add(initiationText, 4, row, 1, 1);
		/// Row 15
		row++;
		gridPane.add(new Label("Other Fee:"), 0, row, 1, 1);
		gridPane.add(otherTextField, 1, row, 1, 1);
		gridPane.add(new Label(""), 2, row, 1, 1);
		gridPane.add(new Label(""), 3, row, 1, 1);
		gridPane.add(otherFeeText, 4, row, 1, 1);
		/// Row 16
		row++;
		gridPane.add(new Label("Work Credits:"), 0, row, 1, 1);
		gridPane.add(workCreditSpinner, 1, row, 1, 1);
		gridPane.add(new Label("X"), 2, row, 1, 1);
		gridPane.add(new Label(String.valueOf(definedFees.getWork_credit())), 3, row, 1, 1);
		gridPane.add(workCreditsText, 4, row, 1, 1);
		// Spacer
		row++;
		gridPane.add(new Label(""), 0, row, 1, 1);
		gridPane.add(new Label(""), 1, row, 1, 1);
		gridPane.add(new Label(""), 2, row, 1, 1);
		gridPane.add(new Label(""), 3, row, 1, 1);
		gridPane.add(new Label(""), 4, row, 1, 1);
		// Table
		row++;
		gridPane.add(paymentTableView, 0, row, 4, 1);
		gridPane.add(vboxButtons, 4, row, 1, 1);

		vboxButtons.getChildren().addAll(buttonAdd, buttonDelete);
		paymentTableView.getColumns().addAll(Arrays.asList(Col1,Col2,Col3,Col4));
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
		changeState(yscTextField,isEditable,true);
		changeState(duesTextField,isEditable,true);
		changeState(otherTextField,isEditable,true);
		changeState(initiationTextField,isEditable,true);
		changeState(slipTextField,isEditable,true);
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

	private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
		TableColumn<T, String> col = new TableColumn<>(title);
		col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
		col.setCellFactory(column -> EditCell.createStringEditCell());
		return col ;
	}
	
} // updateKayakShed
