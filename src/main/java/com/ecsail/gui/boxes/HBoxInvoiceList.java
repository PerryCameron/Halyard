package com.ecsail.gui.boxes;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;

import com.ecsail.main.Note;
import com.ecsail.main.HalyardPaths;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlDefinedFee;
import com.ecsail.sql.select.SqlMoney;
import com.ecsail.structures.*;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class HBoxInvoiceList extends HBox {
	
	private static ObservableList<Object_Money> fiscals = null;
	private static TabPane parentTabPane;
	private static MembershipDTO membership;
	private static ObservableList<Object_Person> people;
	private static Note note;
	private static TextField duesText;
	
	String currentYear;

	public HBoxInvoiceList(MembershipDTO membership, TabPane t, ObservableList<Object_Person> p, Note n, TextField dt) {
		super();
		HBoxInvoiceList.membership = membership;
		this.currentYear = HalyardPaths.getYear();
		HBoxInvoiceList.duesText = dt;
		HBoxInvoiceList.note = n;
		HBoxInvoiceList.parentTabPane = t;
		HBoxInvoiceList.people = p;
		
		////////////////////////  OBJECTS   ///////////////////////////////
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
        HBox hbox1 = new HBox();  // holds membershipID, Type and Active
        VBox vboxPink = new VBox(); // this creates a pink border around the table
        HBox deleteButtonHBox = new HBox();
		Button addFiscalRecord = new Button("Add");
		Button deleteFiscalRecord = new Button("Delete");
//		final Spinner<Integer> yearSpinner = new Spinner<Integer>();
//		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 2100, Integer.parseInt(currentYear));
		HBoxInvoiceList.fiscals = SqlMoney.getMonies(membership.getMsid());
//		Object_DefinedFee definedFees = SqlSelect.selectDefinedFees(Integer.parseInt(currentYear));
		TableView<Object_Money> fiscalTableView = new TableView<Object_Money>();
		TableColumn<Object_Money, Integer> Col1 = new TableColumn<Object_Money, Integer>("Year");
		TableColumn<Object_Money, Integer> Col2 = new TableColumn<Object_Money, Integer>("Fees");
		TableColumn<Object_Money, Integer> Col3 = new TableColumn<Object_Money, Integer>("Credit");
		TableColumn<Object_Money, Integer> Col4 = new TableColumn<Object_Money, Integer>("Paid");
		TableColumn<Object_Money, Integer> Col5 = new TableColumn<Object_Money, Integer>("Balance");
		ComboBox<Integer> comboBox = new ComboBox();
		for(int i = Integer.parseInt(HalyardPaths.getYear()) + 1; i > 1969; i--) {
			comboBox.getItems().add(i);
		}
		comboBox.getSelectionModel().select(1);
		///////////////////// SORT ///////////////////////////////////////////
		Collections.sort(HBoxInvoiceList.fiscals, (p1, p2) -> Integer.compare(p2.getFiscal_year(), (p1.getFiscal_year())));
		
		///////////////////// ATTRIBUTES /////////////////////////////////////

		fiscalTableView.setEditable(false);
		fiscalTableView.setFixedCellSize(30);
		//fiscalTableView.minHeightProperty().bind(vboxGrey.prefHeightProperty());
		//fiscalTableView.maxHeightProperty().bind(vboxGrey.prefHeightProperty());
		fiscalTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		
		Col1.setCellValueFactory(new PropertyValueFactory<Object_Money, Integer>("fiscal_year"));
		Col2.setCellValueFactory(new PropertyValueFactory<Object_Money, Integer>("total"));
		Col3.setCellValueFactory(new PropertyValueFactory<Object_Money, Integer>("credit"));
		Col4.setCellValueFactory(new PropertyValueFactory<Object_Money, Integer>("paid"));
		Col5.setCellValueFactory(new PropertyValueFactory<Object_Money, Integer>("balance"));

		Col2.setStyle( "-fx-alignment: CENTER;");
		Col2.setStyle( "-fx-alignment: CENTER-RIGHT;");
		Col3.setStyle( "-fx-alignment: CENTER-RIGHT;");
		Col4.setStyle( "-fx-alignment: CENTER-RIGHT;");
		Col5.setStyle( "-fx-alignment: CENTER-RIGHT;");
		
		/////////////  ATTRIBUTES /////////////
		fiscalTableView.getColumns().addAll(Col1, Col2, Col3, Col4, Col5);
		fiscalTableView.getSortOrder().add(Col1);  // start sorted by membershipID
		fiscalTableView.sort();

		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );   // Year
		Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );  // Mem Id
		Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );   // Mem Type
		Col4.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );   // Renewed
		Col5.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );   // Renew Late
		
		vboxGrey.setPrefWidth(460);
		comboBox.setPrefWidth(80);
		
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink frame around table
		vboxGrey.setPadding(new Insets(10, 10, 10, 10));
		deleteButtonHBox.setPadding(new Insets(0,0,0,40));
		setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		
		hbox1.setSpacing(5);  // membership HBox
        vboxGrey.setSpacing(10);
        hbox1.setAlignment(Pos.CENTER_LEFT);
        
		vboxPink.setId("box-pink");
		vboxGrey.setId("box-grey");
		setId("box-blue");
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		VBox.setVgrow(fiscalTableView, Priority.ALWAYS);
		HBox.setHgrow(fiscalTableView, Priority.ALWAYS);
		HBox.setHgrow(vboxGrey, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		
		
//		yearSpinner.setValueFactory(valueFactory);

        ////////////////  LISTENERS ///////////////////
		addFiscalRecord.setOnAction((event) -> {
				// get the next available key for money_id table
				int moneyId = SqlMoney.getCount("money_id") + 1;
				// create appropriate money object for this membership
				Object_Money newMoney = new Object_Money(moneyId, membership.getMsid(),
						comboBox.getValue(), 0, "0.00", 0, 0, 0, 0, 0, "0.00", 0, 0, 0, 0, 0,
						"0.00", "0.00", "0.00", "0.00", "0.00", String.valueOf(getDues(comboBox.getValue())), false, false, "0.00", "0.00", false,0, "0.00");
				// if a record already exists for this year then this is a supplemental record
				if (SqlExists.moneyExists(String.valueOf(comboBox.getValue()), membership)) {
					newMoney.setSupplemental(true);
					newMoney.setDues("0.00");
				}
				// insert the new record into the SQL database
				SqlInsert.addMoneyRecord(newMoney);
				// insert the work credit information (This may be deprecated. I haven't made up my mind yet)
				SqlInsert.addWorkCreditRecord(moneyId, membership);
				// add new money row to tableview
				fiscals.add(newMoney);
				// send new money row to top
				fiscals.sort(Comparator.comparing(Object_Money::getFiscal_year).reversed());
				// open a tab for the year we just created
				createTabByYear(newMoney);
		});
        
		deleteFiscalRecord.setOnAction((event) -> {
				int selectedIndex = fiscalTableView.getSelectionModel().getSelectedIndex();
				System.out.println("deleting fiscal record " + selectedIndex);
				SqlDelete.deletePaymentByMoneyID(fiscals.get(selectedIndex).getMoney_id());
				SqlDelete.deleteWorkCreditsByMoneyID(fiscals.get(selectedIndex).getMoney_id());
				SqlDelete.deleteMoneyByMoneyID(fiscals.get(selectedIndex).getMoney_id());
				fiscals.remove(selectedIndex);
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
        deleteButtonHBox.getChildren().add(deleteFiscalRecord);
        vboxPink.getChildren().add(fiscalTableView);
		hbox1.getChildren().addAll(new Label("Create new Fiscal Year Record:"),comboBox,addFiscalRecord,deleteButtonHBox);
		vboxGrey.getChildren().addAll(hbox1,vboxPink);
		getChildren().addAll(vboxGrey);	
	}



	/////////////////////  CLASS METHODS /////////////////////////////


	
	private BigDecimal getDues(int year) {  // takes the membership type and gets the dues
		DefinedFeeDTO selectedDefinedFee = SqlDefinedFee.getDefinedFeeByYear(String.valueOf(year));
		BigDecimal dues = BigDecimal.valueOf(0);
		if(membership.getMemType() != null) {
		  switch(membership.getMemType()) 
	        { 
	            case "RM": 
	                dues = selectedDefinedFee.getDues_regular();
	                break; 
	            case "SO": 
	            	dues = selectedDefinedFee.getDues_social();
	                break; 
	            case "FM": 
	            	dues = selectedDefinedFee.getDues_family();
	                break; 
	            case "LA": 
	            	dues = selectedDefinedFee.getDues_lake_associate();
	                break; 
	            case "LM": 
	            	dues = BigDecimal.valueOf(0);  // life members have no dues
	                break; 
	            case "SM": 
	            	dues = BigDecimal.valueOf(0);  // purdue sailing club dues
	                break; 
	            default: 
	            	dues = BigDecimal.valueOf(0);
	        } 
		} else dues = BigDecimal.valueOf(0);
		return dues;
	}
	
	private static void createTab(int rowIndex) {
		parentTabPane.getTabs().add(new Tab(fiscals.get(rowIndex).getFiscal_year() + "", new HBoxInvoice(membership, people, fiscals, rowIndex, note))); // current year tab
		for(Tab tab: parentTabPane.getTabs()) {
			if(tab.getText().equals(fiscals.get(rowIndex).getFiscal_year() + ""))
		parentTabPane.getSelectionModel().select(tab);
		}
	}

	private static void createTabByYear(Object_Money money) {
		// create a tab with the correct year
		Tab newTab = new Tab(String.valueOf(money.getFiscal_year()));
		// add tab to pane
		parentTabPane.getTabs().add(newTab);
		// find the index value of the correct Object_Money in fiscals ArrayList
		int fiscalsIndex = getFiscalIndexByYear(money.getFiscal_year());
		// add appropritate invoice to the tab using the index of fiscals
		newTab.setContent(new HBoxInvoice(membership, people, fiscals, fiscalsIndex, note));
		// open the correct tab
		parentTabPane.getSelectionModel().select(newTab);
	}

	private static int getFiscalIndexByYear(int year) {
		int index = 0;
		for(int i =0; i < fiscals.size(); i++) {
			// find index that matches the year
			if(fiscals.get(i).getFiscal_year() == year)
				index = i;
		}
		return index;
	}

}
