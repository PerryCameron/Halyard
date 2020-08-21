package com.ecsail.gui;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ecsail.main.Note;
import com.ecsail.main.SqlExists;
import com.ecsail.main.SqlInsert;
import com.ecsail.main.SqlSelect;
import com.ecsail.structures.Object_DefinedFee;
import com.ecsail.structures.Object_Membership;
import com.ecsail.structures.Object_Money;
import com.ecsail.structures.Object_Person;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoxFiscalList extends HBox {
	
	private static ObservableList<Object_Money> fiscals = null;
	private static TabPane parentTabPane;
	private static Object_Membership membership;
	private static ObservableList<Object_Person> people;
	private static Note note;
	private static TextField duesText;
	
	String currentYear;
	@SuppressWarnings("unchecked")
	public BoxFiscalList(Object_Membership membership, TabPane t, ObservableList<Object_Person> p, Note n, TextField dt) {
		super();
		BoxFiscalList.membership = membership;
		this.currentYear = new SimpleDateFormat("yyyy").format(new Date());
		BoxFiscalList.duesText = dt;
		BoxFiscalList.note = n;
		BoxFiscalList.parentTabPane = t;
		BoxFiscalList.people = p;
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
        HBox hbox1 = new HBox();  // holds membershipID, Type and Active
		Button addFiscalRecord = new Button("Add");
		final Spinner<Integer> yearSpinner = new Spinner<Integer>();
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 2100, Integer.parseInt(currentYear));
		BoxFiscalList.fiscals = SqlSelect.getMonies(membership.getMsid());
		Boolean currentFiscalRecordExists = SqlExists.recordExists(currentYear,membership);
		Object_DefinedFee definedFees = SqlSelect.selectDefinedFees(Integer.parseInt(currentYear));
		
		if(!currentFiscalRecordExists) {
			createCurrentFiscalRecord(definedFees);  // if no money records exist, create one
		} 

		VBox vboxPink = new VBox(); // this creates a pink border around the table
		TableView<Object_Money> fiscalTableView = new TableView<Object_Money>();
		fiscalTableView.setEditable(false);
		fiscalTableView.setPrefHeight(340);
		fiscalTableView.setFixedCellSize(30);
		
		TableColumn<Object_Money, Integer> Col1 = new TableColumn<Object_Money, Integer>("Year");
		Col1.setCellValueFactory(new PropertyValueFactory<Object_Money, Integer>("fiscal_year"));
		
		TableColumn<Object_Money, Integer> Col2 = new TableColumn<Object_Money, Integer>("Fees");
		Col2.setCellValueFactory(new PropertyValueFactory<Object_Money, Integer>("total"));
		
		TableColumn<Object_Money, Integer> Col3 = new TableColumn<Object_Money, Integer>("Credit");
		Col3.setCellValueFactory(new PropertyValueFactory<Object_Money, Integer>("credit"));
		
		TableColumn<Object_Money, Integer> Col4 = new TableColumn<Object_Money, Integer>("Paid");
		Col4.setCellValueFactory(new PropertyValueFactory<Object_Money, Integer>("paid"));
		
		TableColumn<Object_Money, Integer> Col5 = new TableColumn<Object_Money, Integer>("Balance");
		Col5.setCellValueFactory(new PropertyValueFactory<Object_Money, Integer>("balance"));
		
		/////////////  ATTRIBUTES /////////////
		fiscalTableView.getColumns().addAll(Col1, Col2, Col3, Col4, Col5);
		fiscalTableView.getSortOrder().add(Col1);  // start sorted by membershipID
		fiscalTableView.sort();

		Col1.setPrefWidth(86);
		Col2.setPrefWidth(86);
		Col3.setPrefWidth(87);
		Col4.setPrefWidth(87);
		Col5.setPrefWidth(87);
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink frame around table
		vboxPink.setId("box-pink");
		yearSpinner.setValueFactory(valueFactory);
		setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		setId("box-blue");
		vboxGrey.setId("box-grey");
		vboxGrey.setPadding(new Insets(10, 10, 10, 10));
		vboxGrey.setPrefWidth(460);
        hbox1.setSpacing(5);  // membership HBox
        vboxGrey.setSpacing(10);
        hbox1.setAlignment(Pos.CENTER_LEFT);
        yearSpinner.setPrefWidth(80);
        
        ////////////////  LISTENERS ///////////////////
        addFiscalRecord.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	int moneyId = SqlSelect.getCount("money_id") + 1;
            	Object_Money newMoney = new Object_Money(moneyId,membership.getMsid(),Integer.parseInt(yearSpinner.getEditor().getText()),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,getDues(definedFees),false,false);
            	if(!SqlExists.recordExists(yearSpinner.getEditor().getText(),membership)) {
					SqlInsert.addRecord(newMoney);
					SqlInsert.addRecord(moneyId,membership);
					fiscals.add(newMoney);
            	} else {
            		System.out.println("There is alread a record for " + yearSpinner.getEditor().getText());
            	}
            }
        });
        
		fiscalTableView.setRowFactory(tv -> {
			TableRow<Object_Money> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					int rowIndex = row.getIndex();
					createTab(rowIndex);
				}
			});
			return row;
		});
        
		///////////// SET CONTENT ////////////////////
        fiscalTableView.setItems(fiscals);
        vboxPink.getChildren().add(fiscalTableView);
		hbox1.getChildren().addAll(new Label("Create new Fiscal Year Record:"),yearSpinner,addFiscalRecord);
		vboxGrey.getChildren().addAll(hbox1,vboxPink);
		getChildren().addAll(vboxGrey);	
	}
	
	/////////////////////  CLASS METHODS /////////////////////////////
	
	private int getDues(Object_DefinedFee definedFees) {  // takes the membership type and gets the dues
		int dues = 0;
		  switch(membership.getMemType()) 
	        { 
	            case "RM": 
	                dues = definedFees.getDues_regular(); 
	                break; 
	            case "SO": 
	            	dues = definedFees.getDues_social(); 
	                break; 
	            case "FM": 
	            	dues = definedFees.getDues_family();  
	                break; 
	            case "LA": 
	            	dues = definedFees.getDues_lake_associate();  
	                break; 
	            case "LM": 
	            	dues = 0;  // life members have no dues
	                break; 
	            case "SM": 
	            	dues = 0;  // purdue sailing club dues
	                break; 
	            default: 
	            	dues = 0; 
	        } 
		return dues;
	}
	
	private void createCurrentFiscalRecord(Object_DefinedFee definedFees) {  // creates a current fiscal record if none exists
		int addSlip = 0;
		if(SqlExists.ownsSlip(membership.getMsid()) || SqlExists.subleasesSlip(membership.getMsid())) addSlip = 1;  // member has a slip for current year
		int moneyId = SqlSelect.getCount("money_id") + 1;
		if(!SqlExists.recordExists(currentYear,membership)) {  // record doesn't exist
			Object_Money newMoney = new Object_Money(moneyId,membership.getMsid(),Integer.parseInt(currentYear),0,getDues(definedFees),0,0,0,0,0,addSlip,0,0,0,0,0,0,0,0,0,0,getDues(definedFees),false,false);
			SqlInsert.addRecord(newMoney);
			SqlInsert.addRecord(moneyId,membership);
			fiscals.add(newMoney);
		} 
	}
	
	private static void createTab(int rowIndex) {
		parentTabPane.getTabs().add(new Tab(fiscals.get(rowIndex).getFiscal_year() + "", new BoxFiscal(membership, people, fiscals, rowIndex,note, duesText))); // current year tab
	}
}
