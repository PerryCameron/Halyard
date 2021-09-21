package com.ecsail.gui.boxes;

import java.util.Collections;
import com.ecsail.main.Note;
import com.ecsail.main.HalyardPaths;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlSelect;
import com.ecsail.structures.Object_DefinedFee;
import com.ecsail.structures.Object_Membership;
import com.ecsail.structures.Object_Money;
import com.ecsail.structures.Object_Person;

import javafx.collections.ObservableList;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class BoxPaymentList extends HBox {
	
	private static ObservableList<Object_Money> fiscals = null;
	private static TabPane parentTabPane;
	private static Object_Membership membership;
	private static ObservableList<Object_Person> people;
	private static Note note;
	private static TextField duesText;
	
	String currentYear;
	@SuppressWarnings("unchecked")
	public BoxPaymentList(Object_Membership membership, TabPane t, ObservableList<Object_Person> p, Note n, TextField dt) {
		super();
		BoxPaymentList.membership = membership;
		this.currentYear = HalyardPaths.getYear();
		BoxPaymentList.duesText = dt;
		BoxPaymentList.note = n;
		BoxPaymentList.parentTabPane = t;
		BoxPaymentList.people = p;
		
		////////////////////////  OBJECTS   ///////////////////////////////
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
        HBox hbox1 = new HBox();  // holds membershipID, Type and Active
        VBox vboxPink = new VBox(); // this creates a pink border around the table
        HBox deleteButtonHBox = new HBox();
		Button addFiscalRecord = new Button("Add");
		Button deleteFiscalRecord = new Button("Delete");
		final Spinner<Integer> yearSpinner = new Spinner<Integer>();
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 2100, Integer.parseInt(currentYear));
		BoxPaymentList.fiscals = SqlSelect.getMonies(membership.getMsid());
		Object_DefinedFee definedFees = SqlSelect.selectDefinedFees(Integer.parseInt(currentYear));
		TableView<Object_Money> fiscalTableView = new TableView<Object_Money>();
		TableColumn<Object_Money, Integer> Col1 = new TableColumn<Object_Money, Integer>("Year");
		TableColumn<Object_Money, Integer> Col2 = new TableColumn<Object_Money, Integer>("Fees");
		TableColumn<Object_Money, Integer> Col3 = new TableColumn<Object_Money, Integer>("Credit");
		TableColumn<Object_Money, Integer> Col4 = new TableColumn<Object_Money, Integer>("Paid");
		TableColumn<Object_Money, Integer> Col5 = new TableColumn<Object_Money, Integer>("Balance");
				
		///////////////////// SORT ///////////////////////////////////////////
		Collections.sort(BoxPaymentList.fiscals, (p1,p2) -> Integer.compare(p2.getFiscal_year(), (p1.getFiscal_year())));
		
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
		yearSpinner.setPrefWidth(80);
		
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
		
		
		yearSpinner.setValueFactory(valueFactory);

        ////////////////  LISTENERS ///////////////////
		addFiscalRecord.setOnAction((event) -> {
				int moneyId = SqlSelect.getCount("money_id") + 1;
				Object_Money newMoney = new Object_Money(moneyId, membership.getMsid(),
						Integer.parseInt(yearSpinner.getEditor().getText()), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, getDues(definedFees), false, false, 0, 0, false);
				if (SqlExists.moneyExists(yearSpinner.getEditor().getText(), membership)) {
					newMoney.setSupplemental(true);
					newMoney.setDues(0);
				}
				SqlInsert.addRecord(newMoney);
				SqlInsert.addRecord(moneyId, membership);
				fiscals.add(newMoney);
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
		hbox1.getChildren().addAll(new Label("Create new Fiscal Year Record:"),yearSpinner,addFiscalRecord,deleteButtonHBox);
		vboxGrey.getChildren().addAll(hbox1,vboxPink);
		getChildren().addAll(vboxGrey);	
	}
	
	/////////////////////  CLASS METHODS /////////////////////////////
	
	private int getDues(Object_DefinedFee definedFees) {  // takes the membership type and gets the dues
		int dues = 0;
		if(membership.getMemType() != null) {
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
		} else dues = 0;
		return dues;
	}
	
// sweet method, perhaps I should set to static
//	private void createCurrentFiscalRecord(Object_DefinedFee definedFees) {  // creates a current fiscal record if none exists
//		System.out.println("Creating fiscal Record because it doesn't exist");
//		int addSlip = 0;
//		if(SqlExists.ownsSlip(membership.getMsid()) || SqlExists.subleasesSlip(membership.getMsid())) addSlip = 1;  // member has a slip for current year
//		int moneyId = SqlSelect.getCount("money_id") + 1;
//		if(!SqlExists.moneyExists(currentYear,membership)) {  // record doesn't exist
//			Object_Money newMoney = new Object_Money(moneyId,membership.getMsid(),Integer.parseInt(currentYear),0,getDues(definedFees),0,0,0,0,0,addSlip,0,0,0,0,0,0,0,0,0,0,getDues(definedFees),false,false,0,0,false);
//			SqlInsert.addRecord(newMoney);
//			SqlInsert.addRecord(moneyId,membership);
//			fiscals.add(newMoney);
//		} 
//	}
	
	private static void createTab(int rowIndex) {
		parentTabPane.getTabs().add(new Tab(fiscals.get(rowIndex).getFiscal_year() + "", new BoxFiscal(membership, people, fiscals, rowIndex, note, duesText))); // current year tab
		for(Tab tab: parentTabPane.getTabs()) {
			if(tab.getText().equals(fiscals.get(rowIndex).getFiscal_year() + ""))
		parentTabPane.getSelectionModel().select(tab);
		}

	}
}
