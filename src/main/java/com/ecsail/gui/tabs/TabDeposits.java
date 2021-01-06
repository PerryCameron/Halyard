package com.ecsail.gui.tabs;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import com.ecsail.gui.dialogues.Dialogue_DepositPDF;
import com.ecsail.main.SqlExists;
import com.ecsail.main.SqlInsert;
import com.ecsail.main.SqlSelect;
import com.ecsail.main.SqlUpdate;
import com.ecsail.main.TabLauncher;
import com.ecsail.structures.Object_DefinedFee;
import com.ecsail.structures.Object_Deposit;
import com.ecsail.structures.Object_DepositSummary;
import com.ecsail.structures.Object_PaidDues;
import com.ecsail.structures.Object_Payment;
import com.ecsail.structures.Object_DepositSummaryText;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class TabDeposits extends Tab {
	private ObservableList<Object_PaidDues> paidDues;  // starts with all paid dues for a given year, then can change to dues for a selected deposit
	private Object_DefinedFee currentDefinedFee;  // containes all the defined fees for a given year
	private Object_Deposit currentDeposit;  // contains deposit number, date, year for a selected deposit
	private Object_DepositSummaryText summaryText = new Object_DepositSummaryText();  // object of text objects for display
	private Object_DepositSummary summaryTotals = new Object_DepositSummary();  // will hold the totals of all at first and then for a selected deposit
	Text numberOfRecords = new Text("0");
	String currentDate;
	String selectedYear;

	public TabDeposits(String text) { 
		super(text);
		this.paidDues =  FXCollections.observableArrayList(new Callback<Object_PaidDues, Observable[]>() {
			@Override
			public Observable[] call(Object_PaidDues param) {
				return new Observable[] { param.isClosedProperty() };
				
			}
		});
		this.selectedYear = new SimpleDateFormat("yyyy").format(new Date());  // lets start at the current year
		this.paidDues.addAll(SqlSelect.getPaidDues(selectedYear));
		summaryTotals.setDepositNumber(SqlSelect.getBatchNumber(selectedYear));

		this.currentDefinedFee = SqlSelect.getDefinedFee(selectedYear);
		this.currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
		////////////////////// OBJECT INSTANCE //////////////////////////	
		
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "Show All",
			        "Show Selected"
			    );
		
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		HBox mainHBox = new HBox(); // this separates table content from controls
		VBox controlsVBox = new VBox(); // inner grey box
		HBox controlsHBox = new HBox(); // outer blue box
		HBox batchNumberHBox = new HBox(); //holds spinner and label
		HBox buttonHBox = new HBox();  // holds buttons
		HBox yearBatchHBox = new HBox(); // holds spinner and batchNumberHBox
		HBox gridHBox = new HBox(); // holds gridPane
		HBox remaindingRenewalHBox = new HBox();
		HBox selectionHBox = new HBox();
		HBox numberOfRecordsHBox = new HBox();
		HBox comboBoxHBox = new HBox();
		Text nonRenewed = new Text("0");
		
		final ComboBox<String> comboBox = new ComboBox<String>(options);
		GridPane gridPane = new GridPane();
		TableView<Object_PaidDues> paidDuesTableView;
		Button refreshButton = new Button("Refresh");
		Button printPdfButton = new Button("Print PDF");
		DatePicker depositDatePicker = new DatePicker();
		
		//////////////////// OBJECT ATTRIBUTES ///////////////////////////
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		numberOfRecords.setStyle("-fx-font-weight: bold;");
		vboxBlue.setId("box-blue");
		controlsHBox.setId("box-blue");
		controlsHBox.setPadding(new Insets(5,5,5,5));
		controlsVBox.setPrefWidth(342);
		controlsVBox.setSpacing(10);
		controlsVBox.setPadding(new Insets(15,5,5,5));
		controlsVBox.setId("box-grey");
		mainHBox.setSpacing(5);
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink fram around table
		vboxPink.setId("box-pink");
		batchNumberHBox.setSpacing(5);
		selectionHBox.setSpacing(30);
		depositDatePicker.setPrefWidth(123);
		yearBatchHBox.setAlignment(Pos.CENTER);
		selectionHBox.setPadding(new Insets(0,0,0,37));
		comboBoxHBox.setPadding(new Insets(0,0,0,37));
		batchNumberHBox.setAlignment(Pos.CENTER);
		gridHBox.setAlignment(Pos.CENTER);
		buttonHBox.setSpacing(10);
		numberOfRecordsHBox.setSpacing(5);
		buttonHBox.setAlignment(Pos.CENTER);
		remaindingRenewalHBox.setAlignment(Pos.CENTER);
		vboxGrey.setPrefHeight(688);
		gridPane.setVgap(5); 
	    gridPane.setHgap(50);
	    //gridPane.setPadding(new Insets(10, 10, 10, 30));
		paidDuesTableView = new TableView<Object_PaidDues>();
		paidDuesTableView.setItems(paidDues);
		paidDuesTableView.setPrefWidth(645);
		paidDuesTableView.setPrefHeight(688);
		paidDuesTableView.setFixedCellSize(30);
		paidDuesTableView.setEditable(true);
		yearBatchHBox.setSpacing(15);
		comboBox.setValue("Show All");
		
		final Spinner<Integer> yearSpinner = new Spinner<Integer>();
		SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, Integer.parseInt(selectedYear), Integer.parseInt(selectedYear));
		yearSpinner.setValueFactory(wetSlipValueFactory);
		yearSpinner.setPrefWidth(90);
		yearSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  selectedYear = yearSpinner.getEditor().getText();
				  paidDues.clear();
				  paidDues.addAll(SqlSelect.getPaidDues(selectedYear));
				  currentDefinedFee.clear();
				  currentDefinedFee = SqlSelect.getDefinedFee(selectedYear);
			  }
			});
		
		final Spinner<Integer> batchSpinner = new Spinner<Integer>();
		SpinnerValueFactory<Integer> batchValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, summaryTotals.getDepositNumber()); // 0 to batch, display batch
		batchSpinner.setValueFactory(batchValueFactory);
		batchSpinner.setPrefWidth(60);
		batchSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  batchSpinner.increment(0); // won't change value, but will commit editor
				  // create batch if it doesn't already exist
				  summaryTotals.setDepositNumber(Integer.parseInt(batchSpinner.getEditor().getText()));
				  checkForDepositAndCreateIfNotExist();
				  refreshButton.fire();
			  }
			});
		
		// example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
		TableColumn<Object_PaidDues, Boolean> Col1 = new TableColumn<Object_PaidDues, Boolean>("Select");
		Col1.setPrefWidth(50);
		Col1.setCellValueFactory(new Callback<CellDataFeatures<Object_PaidDues, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<Object_PaidDues, Boolean> param) {
            	Object_PaidDues thisPaidDues = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(thisPaidDues.isClosed());
                booleanProp.addListener(new ChangeListener<Boolean>() {
 
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                            Boolean newValue) {
                    	thisPaidDues.setClosed(newValue);  // sets checkbox value in table
                    	if(newValue) { // if checked
                    	setBatchAndClose(thisPaidDues, summaryTotals.getDepositNumber(), true);
                    	System.out.println(thisPaidDues.toString());
                    	addDepositIdToPayment(thisPaidDues);  // does lots of stuff
                    	} else { // if unchecked
                    	setBatchAndClose(thisPaidDues, 0, false);
                    	}
                    	summaryTotals.clear();
        				updateSummaryTotals();
      				  //updateCurrentMoneyTotals(); // need error check if batch doesn't exist
    				  updateMoneyTotals(gridPane);
    				  updateNonRenewed(nonRenewed);
                    }
                });
                return booleanProp;
            }
        });
		
		Col1.setCellFactory(new Callback<TableColumn<Object_PaidDues, Boolean>, //
		        TableCell<Object_PaidDues, Boolean>>() {
		            @Override
		            public TableCell<Object_PaidDues, Boolean> call(TableColumn<Object_PaidDues, Boolean> p) {
		                CheckBoxTableCell<Object_PaidDues, Boolean> cell = new CheckBoxTableCell<Object_PaidDues, Boolean>();
		                cell.setAlignment(Pos.CENTER);
		                return cell;
		            }
		        });
		
		TableColumn<Object_PaidDues, Integer> Col2 = new TableColumn<Object_PaidDues, Integer>("Batch");
		Col2.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, Integer>("batch"));
		
		TableColumn<Object_PaidDues, Integer> Col9 = new TableColumn<Object_PaidDues, Integer>("Mem ID");
		Col9.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, Integer>("membershipId"));
		
		TableColumn<Object_PaidDues, String> Col3 = new TableColumn<Object_PaidDues, String>("Last Name");
		Col3.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, String>("l_name"));
		Col3.setPrefWidth(80);
		
		TableColumn<Object_PaidDues, String> Col4 = new TableColumn<Object_PaidDues, String>("First Name");
		Col4.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, String>("f_name"));
		Col4.setPrefWidth(80);
		
		TableColumn<Object_PaidDues, String> Col10 = new TableColumn<Object_PaidDues, String>("Slip");
		Col10.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, String>("wet_slip"));
		Col10.setPrefWidth(50);
		
		TableColumn<Object_PaidDues, Integer> Col5 = new TableColumn<Object_PaidDues, Integer>("Fees");
		Col5.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, Integer>("total"));
		Col5.setPrefWidth(50);
		
		TableColumn<Object_PaidDues, Integer> Col6 = new TableColumn<Object_PaidDues, Integer>("Credit");
		Col6.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, Integer>("credit"));
		Col6.setPrefWidth(50);
		
		TableColumn<Object_PaidDues, Integer> Col7 = new TableColumn<Object_PaidDues, Integer>("Paid");
		Col7.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, Integer>("paid"));
		Col7.setPrefWidth(50);
		
		TableColumn<Object_PaidDues, Integer> Col8 = new TableColumn<Object_PaidDues, Integer>("Balance");
		Col8.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, Integer>("balance"));
		Col8.setPrefWidth(50);
		
		TableColumn<Object_PaidDues, Integer> Col11 = new TableColumn<Object_PaidDues, Integer>("Cmit");
		Col11.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, Integer>("committed"));
		Col11.setPrefWidth(50);
		
		//////////////////  LISTENERS  //////////////////////
		
		paidDuesTableView.setRowFactory(tv -> {
	        TableRow<Object_PaidDues> row = new TableRow<>();
	        row.setOnMouseClicked(event -> {
	            if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
	                 && event.getClickCount() == 2) {
	            	Object_PaidDues clickedRow = row.getItem();
					TabLauncher.createTab(clickedRow.getMs_id());
	            }
	        });
	        return row ;
	    });
		
		refreshButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				paidDues.clear();
					if(comboBox.getValue().equals("Show All")) {
					paidDues.addAll(SqlSelect.getPaidDues(selectedYear));
					} else {
					paidDues.addAll(SqlSelect.getPaidDues(selectedYear,summaryTotals.getDepositNumber()));
					}
				summaryTotals.clear();
				updateSummaryTotals();	
				updateMoneyTotals(gridPane);
				updateNonRenewed(nonRenewed);
				numberOfRecords.setText(paidDues.size() + "");
				
				if(SqlExists.ifDepositRecordExists(selectedYear + "", summaryTotals.getDepositNumber())) {
				currentDeposit = SqlSelect.getDeposit(selectedYear + "", summaryTotals.getDepositNumber());
				LocalDate date = LocalDate.parse(currentDeposit.getDepositDate(), formatter);
				depositDatePicker.setValue(date);
				}
				//check if deposit exists here

			}
			});
		
		printPdfButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				new Dialogue_DepositPDF(currentDeposit, currentDefinedFee, selectedYear);
				}
			});
		
	    comboBox.valueProperty().addListener(new ChangeListener<String>() {
	        @Override public void changed(ObservableValue ov, String oldv, String newv) {
	        	refreshButton.fire();
	        }    
	    });
	    
		EventHandler<ActionEvent> pickerEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				LocalDate date = depositDatePicker.getValue();
				SqlUpdate.updateDeposit("DEPOSIT_DATE", currentDeposit.getDeposit_id(), date);
			}
		};
		depositDatePicker.setOnAction(pickerEvent);
		
		///////////////////  SET CONTENT  ///////////////////////
		
		gridPane.add(new Text("Annual Dues:"), 0, 0);
		gridPane.add(summaryText.getDuesNumberText(), 1,0);
		GridPane.setHalignment(summaryText.getDuesNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getDuesMoneyText(), 2, 0);
		GridPane.setHalignment(summaryText.getDuesMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Winter Storage:"), 0, 1);
		gridPane.add(summaryText.getWinterStorageNumberText(), 1, 1);
		GridPane.setHalignment(summaryText.getWinterStorageNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getWinterStorageMoneyText(), 2, 1);
		GridPane.setHalignment(summaryText.getWinterStorageMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Wet Slip:"), 0, 2);
		gridPane.add(summaryText.getWetSlipNumberText(), 1, 2);
		GridPane.setHalignment(summaryText.getWetSlipNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getWetSlipMoneyText(), 2, 2);
		GridPane.setHalignment(summaryText.getWetSlipMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Beach Spot:"), 0, 3);
		gridPane.add(summaryText.getBeachSpotNumberText(), 1, 3);
		GridPane.setHalignment(summaryText.getBeachSpotNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getBeachSpotMoneyText(), 2, 3);
		GridPane.setHalignment(summaryText.getBeachSpotMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Outside Kayac Storage:"), 0, 4);
		gridPane.add(summaryText.getKayacRackNumberText(), 1, 4);
		GridPane.setHalignment(summaryText.getKayacRackNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getKayacRackMoneyText(), 2, 4);
		GridPane.setHalignment(summaryText.getKayacRackMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Inside Kayac Storage:"), 0, 5);
		gridPane.add(summaryText.getKayacShedNumberText(), 1, 5);
		GridPane.setHalignment(summaryText.getKayacShedNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getKayacShedMoneyText(), 2, 5);
		GridPane.setHalignment(summaryText.getKayacShedMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Sail Loft Access:"), 0, 6);
		gridPane.add(summaryText.getSailLoftNumberText(), 1, 6);
		GridPane.setHalignment(summaryText.getSailLoftNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getSailLoftMoneyText(), 2, 6);
		GridPane.setHalignment(summaryText.getSailLoftMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Sail School Loft Access:"), 0, 7);
		gridPane.add(summaryText.getSailSchoolLoftNumberText(), 1, 7);
		GridPane.setHalignment(summaryText.getSailSchoolLoftNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getSailSchoolLoftMoneyText(), 2, 7);
		GridPane.setHalignment(summaryText.getSailSchoolLoftMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Extra Gate Key:"), 0, 8);
		gridPane.add(summaryText.getGateKeyNumberText(), 1, 8);
		GridPane.setHalignment(summaryText.getGateKeyNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getGateKeyMoneyText(), 2, 8);
		GridPane.setHalignment(summaryText.getGateKeyMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Extra Kayak Shed Key:"), 0, 9);
		gridPane.add(summaryText.getKayakShedKeyNumberText(), 1, 9);
		GridPane.setHalignment(summaryText.getKayakShedKeyNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getKayakShedKeyMoneyText(), 2, 9);
		GridPane.setHalignment(summaryText.getKayakShedKeyMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Extra Sail Loft Key:"), 0, 10);
		gridPane.add(summaryText.getSailLoftKeyNumberText(), 1, 10);
		GridPane.setHalignment(summaryText.getSailLoftKeyNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getSailLoftKeyMoneyText(), 2, 10);
		GridPane.setHalignment(summaryText.getSailLoftKeyMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Extra Sail School Loft Key:"), 0, 11);
		gridPane.add(summaryText.getSailSchoolLoftKeyNumberText(), 1, 11);
		GridPane.setHalignment(summaryText.getSailSchoolLoftKeyNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getSailSchoolLoftKeyMoneyText(), 2, 11);
		GridPane.setHalignment(summaryText.getSailSchoolLoftKeyMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Initiation fee:"), 0, 12);
		gridPane.add(summaryText.getInitiationNumberText(), 1, 12);
		GridPane.setHalignment(summaryText.getInitiationNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getInitiationMoneyText(), 2, 12);
		GridPane.setHalignment(summaryText.getInitiationMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("YSC Donation:"), 0, 13);
		gridPane.add(summaryText.getYspDonationNumberText(), 1, 13);
		GridPane.setHalignment(summaryText.getYspDonationNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getYspDonationMoneyText(), 2, 13);
		GridPane.setHalignment(summaryText.getYspDonationMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Credits:"), 0, 14);
		gridPane.add(summaryText.getCreditsNumberText(), 1, 14);
		GridPane.setHalignment(summaryText.getCreditsNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getCreditsMoneyText(), 2, 14);
		GridPane.setHalignment(summaryText.getCreditsMoneyText(), HPos.RIGHT);
		gridPane.add(new Text("Total:"), 0, 15);
		gridPane.add(summaryText.getTotalNumberText(), 1, 15);
		GridPane.setHalignment(summaryText.getTotalNumberText(), HPos.CENTER);
		gridPane.add(summaryText.getTotalMoneyText(), 2, 15);
		GridPane.setHalignment(summaryText.getTotalMoneyText(), HPos.RIGHT);
		
		comboBoxHBox.getChildren().add(comboBox);
		numberOfRecordsHBox.getChildren().addAll(new Text("Records:"), numberOfRecords);
		selectionHBox.getChildren().addAll(depositDatePicker, numberOfRecordsHBox);
		remaindingRenewalHBox.getChildren().addAll(new Text("Memberships not yet renewed: " ),nonRenewed);
		batchNumberHBox.getChildren().addAll(new Label("Deposit Number"),batchSpinner);
		yearBatchHBox.getChildren().addAll(yearSpinner,batchNumberHBox);
		buttonHBox.getChildren().addAll(refreshButton,printPdfButton);
		gridHBox.getChildren().add(gridPane);
		controlsHBox.getChildren().add(controlsVBox);
		controlsVBox.getChildren().addAll(yearBatchHBox,selectionHBox,comboBoxHBox,gridHBox,buttonHBox,remaindingRenewalHBox);
		paidDuesTableView.getColumns().addAll(Arrays.asList(Col1,Col2,Col9,Col3,Col4,Col10,Col5,Col6,Col7,Col8,Col11));
		mainHBox.getChildren().addAll(paidDuesTableView,controlsHBox);
		vboxGrey.getChildren().add(mainHBox);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}
	
	////////////////////////CLASS METHODS //////////////////////////
	
	private void checkForDepositAndCreateIfNotExist () {
		if (SqlExists.ifDepositRecordExists(selectedYear + "", summaryTotals.getDepositNumber())) { // does a deposit exist for selected year and batch?
			System.out.println("deposit exists");
			SqlSelect.getDeposit(selectedYear + "", summaryTotals.getDepositNumber()).getDeposit_id();
		} else { // record does not exist
			System.out.println("deposit does not exist, creating record");
			createDepositRecord();
		}
	}
	
	private void addDepositIdToPayment(Object_PaidDues thisPaidDues) {
		int pay_id = getPayId(thisPaidDues); // gets relevant object_payment
		int deposit_id = getDepositId(thisPaidDues);
		System.out.println("Adding deposit id to payment tuple");
		SqlUpdate.updatePayment(pay_id, "deposit_id", deposit_id + ""); // add deposit_id to payment tuple
	}
	
	private int getDepositId(Object_PaidDues thisPaidDues) {
		int deposit_id = 0;
		if (SqlExists.ifDepositRecordExists(thisPaidDues.getFiscal_year() + "", summaryTotals.getDepositNumber())) { // does a deposit exist for selected year and batch?
			System.out.println("deposit exists");
			deposit_id = SqlSelect.getDeposit(selectedYear + "", summaryTotals.getDepositNumber()).getDeposit_id();
		} else { // record does not exist
			System.out.println("deposit does not exist, creating record");
			deposit_id = createDepositRecord();
		}
		return deposit_id;
	}
	
	private int getPayId(Object_PaidDues thisPaidDues) {
		int pay_id = 0;
		if(!SqlExists.paymentExists(thisPaidDues.getMoney_id())) {  // No payment has been recorded, we need to create a blank payment record
			pay_id = createPaymentRecord(thisPaidDues);
		} else {
			pay_id = SqlSelect.getPayment(thisPaidDues.getMoney_id()).getPay_id(); // payment record exists, lets get it's ID
		}
		return pay_id;
	}
	
	private int createPaymentRecord(Object_PaidDues thisPaidDues) {
		int pay_id = SqlSelect.getNumberOfPayments() + 1;
		Object_Payment newPayment = new Object_Payment(pay_id,thisPaidDues.getMoney_id(),"0","CH",currentDate, "0",1);
		SqlInsert.addRecord(newPayment);
		return pay_id;
	}
	
	private int createDepositRecord() {
		int deposit_id = SqlSelect.getNumberOfDeposits() + 1;
		Object_Deposit newDeposit = new Object_Deposit(deposit_id, currentDate, selectedYear, summaryTotals.getDepositNumber());
		SqlInsert.addDeposit(newDeposit);
		return deposit_id;
	}
	
	private void setBatchAndClose(Object_PaidDues thisPaidDues, int thisBatch, Boolean closed) {
		SqlUpdate.updateMoneyBatch(thisPaidDues.getMoney_id(), thisBatch);
		SqlUpdate.updateMoneyClosed(thisPaidDues.getMoney_id(), closed);
		thisPaidDues.setBatch(thisBatch);
	}
	
	private void updateNonRenewed(Text nonRenewed) {
		nonRenewed.setText(SqlSelect.getNonMembershipRenewalCount(selectedYear) + "");
	}

	private void updateMoneyTotals(GridPane gridPane) {  // need to add defined fees object
		summaryText.getDuesNumberText().setText(summaryTotals.getDuesNumber() + "");
		summaryText.getDuesMoneyText().setText("$" + summaryTotals.getDues());
		
		summaryText.getWinterStorageNumberText().setText(summaryTotals.getWinter_storageNumber() + "");
		summaryText.getWinterStorageMoneyText().setText("$" + summaryTotals.getWinter_storage());
		
		summaryText.getWetSlipNumberText().setText(summaryTotals.getWet_slipNumber() + "");
		summaryText.getWetSlipMoneyText().setText("$" + summaryTotals.getWet_slip());
		
		summaryText.getBeachSpotNumberText().setText(summaryTotals.getBeachNumber() + "");
		summaryText.getBeachSpotMoneyText().setText("$" + summaryTotals.getBeach());

		summaryText.getKayacRackNumberText().setText(summaryTotals.getKayac_rackNumber() + "");
		summaryText.getKayacRackMoneyText().setText("$" + summaryTotals.getKayac_rack());
		
		summaryText.getKayacShedNumberText().setText(summaryTotals.getKayac_shedNumber() + "");
		summaryText.getKayacShedMoneyText().setText("$" + summaryTotals.getKayac_shed());
		
		summaryText.getSailLoftNumberText().setText(summaryTotals.getSail_loftNumber() + "");
		summaryText.getSailLoftMoneyText().setText("$" + summaryTotals.getSail_loft());
		
		summaryText.getSailSchoolLoftNumberText().setText(summaryTotals.getSail_school_laser_loftNumber() + "");
		summaryText.getSailSchoolLoftMoneyText().setText("$" + summaryTotals.getSail_school_laser_loft());
		
		summaryText.getGateKeyNumberText().setText(summaryTotals.getGate_key() + "");
		summaryText.getGateKeyMoneyText().setText("$" + summaryTotals.getGate_keyNumber());
		
		summaryText.getKayakShedKeyNumberText().setText(summaryTotals.getKayac_shed_keyNumber() + "");
		summaryText.getKayakShedKeyMoneyText().setText("$" + summaryTotals.getKayac_shed_key());
		
		summaryText.getSailLoftKeyNumberText().setText(summaryTotals.getSail_loft_keyNumber() + "");
		summaryText.getSailLoftKeyMoneyText().setText("$" + summaryTotals.getSail_loft_key());
		
		summaryText.getSailSchoolLoftKeyNumberText().setText(summaryTotals.getSail_school_loft_keyNumber() + "");
		summaryText.getSailSchoolLoftKeyMoneyText().setText("$" + summaryTotals.getSail_school_loft_key());

		summaryText.getInitiationNumberText().setText(summaryTotals.getInitiationNumber() + "");
		summaryText.getInitiationMoneyText().setText("$" + summaryTotals.getInitiation());
		
		summaryText.getYspDonationNumberText().setText(summaryTotals.getYsc_donationNumber() + "");
		summaryText.getYspDonationMoneyText().setText("$" + summaryTotals.getYsc_donation());
		
		summaryText.getCreditsNumberText().setText(summaryTotals.getCreditNumber() + "");
		summaryText.getCreditsMoneyText().setText("$" + summaryTotals.getCredit());
		
		summaryText.getTotalNumberText().setText(summaryTotals.getNumberOfRecords() + "");
		summaryText.getTotalMoneyText().setText("$" + summaryTotals.getTotal());
	}
	
	private void updateSummaryTotals() {
		int numberOfRecordsCounted = 0; // number of records counted
		for (Object_PaidDues d : paidDues) {
			if (d.getBeach() != 0) { ///////// BEACH
				summaryTotals.setBeachNumber(d.getBeach() + summaryTotals.getBeachNumber());
				int totalBeachDollars = currentDefinedFee.getBeach() * d.getBeach();
				summaryTotals.setBeach(totalBeachDollars + summaryTotals.getBeach());
			}
			if (d.getCredit() != 0) {  ////////  CREDIT
				summaryTotals.setCreditNumber(1 + summaryTotals.getCreditNumber());
				summaryTotals.setCredit(d.getCredit() + summaryTotals.getCredit());
			}
			if (d.getDues() != 0) {  ////////  DUES
				summaryTotals.setDuesNumber(1 + summaryTotals.getDuesNumber());
				summaryTotals.setDues(d.getDues() + summaryTotals.getDues());
			}
			if (d.getExtra_key() != 0) { /////  EXTRA GATE KEY
				summaryTotals.setGate_keyNumber(d.getExtra_key() + summaryTotals.getGate_keyNumber());
				int totalGateKeyDollars = currentDefinedFee.getMain_gate_key() * d.getExtra_key();
				summaryTotals.setGate_key(summaryTotals.getGate_key() + totalGateKeyDollars);
			}
			if (d.getInitiation() != 0) {  /////// INITIATION
				summaryTotals.setInitiationNumber(1 + summaryTotals.getInitiationNumber());
				summaryTotals.setInitiation(d.getInitiation() + summaryTotals.getInitiation());
			}
			if (d.getKayac_rack() != 0) {  ///// KAYACK RACK FEE
				summaryTotals.setKayac_rackNumber(d.getKayac_rack() + summaryTotals.getKayac_rackNumber());
				int totalKayakRackDollars = currentDefinedFee.getKayak_rack() * d.getKayac_rack();
				summaryTotals.setKayac_rack(totalKayakRackDollars + summaryTotals.getKayac_rack());
			}
			if (d.getKayac_shed() != 0) {   //////// KAYAK SHED ACCESS
				summaryTotals.setKayac_shedNumber(d.getKayac_shed() + summaryTotals.getKayac_shed_keyNumber());
				int totalKayakShedDollars = currentDefinedFee.getKayak_shed() * d.getKayac_shed();
				summaryTotals.setKayac_shed(totalKayakShedDollars + summaryTotals.getKayac_shed());
			}
			if (d.getKayac_shed_key() != 0) {   ///// KAYAK SHED KEY
				summaryTotals.setKayac_shed_keyNumber(d.getKayac_shed_key() + summaryTotals.getKayac_shed_keyNumber());
				int totalKayakShedKeyDollars = currentDefinedFee.getKayak_shed_key() * d.getKayac_shed_key();
				summaryTotals.setKayac_shed_key(totalKayakShedKeyDollars + summaryTotals.getKayac_shed_key());
			}
			if (d.getOther() != 0) {  /////////  OTHER FEE ///////// IN DOLLARS
				summaryTotals.setOtherNumber(1 + summaryTotals.getOtherNumber());
				summaryTotals.setOther(d.getOther() + summaryTotals.getOther());
			}
			if (d.getSail_loft() != 0) {   ////////// SAIL LOFT ACCESS ///////// IN NUMBER OF
				summaryTotals.setSail_loftNumber(1 + summaryTotals.getSail_loftNumber());
				summaryTotals.setSail_loft(currentDefinedFee.getSail_loft() + summaryTotals.getSail_loft());
			}
			if (d.getSail_loft_key() != 0) {  ///////// SAIL LOFT KEY ///////// IN NUMBER OF
				summaryTotals.setSail_loft_keyNumber(d.getSail_loft_key() + summaryTotals.getSail_loft_keyNumber());
				int totalSailLoftKeyDollars = currentDefinedFee.getSail_loft_key() * d.getSail_loft_key();
				summaryTotals.setSail_loft_key(totalSailLoftKeyDollars + summaryTotals.getSail_loft_key());
			}
			if (d.getSail_school_laser_loft() != 0) {  ///////// SAIL SCHOOL LOFT ACCESS ///////// IN NUMBER OF
				summaryTotals.setSail_school_laser_loftNumber(d.getSail_school_laser_loft() + summaryTotals.getSail_school_laser_loftNumber());
				int totalSailSchoolLoftDollars = currentDefinedFee.getSail_school_laser_loft() * d.getSail_school_laser_loft();
				summaryTotals.setSail_school_laser_loft(totalSailSchoolLoftDollars + summaryTotals.getSail_school_laser_loft());
			}
			if (d.getSail_school_loft_key() != 0) {  ////////// SAIL SCHOOL LOFT KEY ///////// IN NUMBER OF
				summaryTotals.setSail_school_loft_keyNumber(d.getSail_school_loft_key() + summaryTotals.getSail_school_loft_keyNumber());
				int totalSailSchoolLoftKeyDollars = currentDefinedFee.getSail_school_loft_key() * d.getSail_school_loft_key();
				summaryTotals.setSail_school_loft_key(totalSailSchoolLoftKeyDollars + summaryTotals.getSail_school_loft_key());
			}
			if (d.getWet_slip() != 0) {  ////////// WET SLIP FEE ///////// IN DOLLARS 
				summaryTotals.setWet_slipNumber(1 + summaryTotals.getWet_slipNumber());
				summaryTotals.setWet_slip(d.getWet_slip() + summaryTotals.getWet_slip());
			}
			if (d.getWinter_storage() != 0) {  ////////  WINTER STORAGE FEE ///////// IN NUMBER OF
				summaryTotals.setWinter_storageNumber(d.getWinter_storage() + summaryTotals.getWinter_storageNumber());
				int totalWinterStorageDollars = currentDefinedFee.getWinter_storage() * d.getWinter_storage();
				summaryTotals.setWinter_storage(totalWinterStorageDollars + summaryTotals.getWinter_storage());
			}
			if (d.getYsc_donation() != 0) {  //////// YSC DONATION ///////// IN DOLLARS
				summaryTotals.setYsc_donationNumber(1 + summaryTotals.getYsc_donationNumber());
				summaryTotals.setYsc_donation(d.getYsc_donation() + summaryTotals.getYsc_donation());
			}
			numberOfRecordsCounted++;
		}
		int total = 0;
		total += summaryTotals.getBeach();
		total -= summaryTotals.getCredit();
		total += summaryTotals.getDues();
		total += summaryTotals.getGate_key();
		total += summaryTotals.getInitiation();
		total += summaryTotals.getKayac_rack();
		total += summaryTotals.getKayac_shed();
		total += summaryTotals.getKayac_shed_key();
		total += summaryTotals.getOther();
		total += summaryTotals.getSail_loft();
		total += summaryTotals.getSail_loft_key();
		total += summaryTotals.getSail_school_laser_loft();
		total += summaryTotals.getSail_school_loft_key();
		total += summaryTotals.getWet_slip();
		total += summaryTotals.getWinter_storage();
		total += summaryTotals.getYsc_donation();
		summaryTotals.setTotal(total);
		summaryTotals.setNumberOfRecords(numberOfRecordsCounted);
	}
}
