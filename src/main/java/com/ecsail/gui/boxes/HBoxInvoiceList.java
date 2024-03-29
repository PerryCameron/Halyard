package com.ecsail.gui.boxes;

import com.ecsail.gui.dialogues.HalyardAlert;
import com.ecsail.main.HalyardPaths;
import com.ecsail.main.Note;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlDefinedFee;
import com.ecsail.sql.select.SqlMoney;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.DefinedFeeDTO;
import com.ecsail.structures.MembershipDTO;
import com.ecsail.structures.MoneyDTO;
import com.ecsail.structures.PersonDTO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class HBoxInvoiceList extends HBox {
	
	private static ObservableList<MoneyDTO> fiscals = null;
	private static TabPane parentTabPane;
	private static MembershipDTO membership;
	private static ObservableList<PersonDTO> people;
	private static Note note;

	String currentYear;

	public HBoxInvoiceList(MembershipDTO membership, TabPane t, ObservableList<PersonDTO> p, Note n) {
		super();
		HBoxInvoiceList.membership = membership;
		this.currentYear = HalyardPaths.getYear();
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
		HBoxInvoiceList.fiscals = SqlMoney.getMoniesByMsid(membership.getMsid());
		TableView<MoneyDTO> fiscalTableView = new TableView<>();
		TableColumn<MoneyDTO, Integer> Col1 = new TableColumn<>("Year");
		TableColumn<MoneyDTO, Integer> Col2 = new TableColumn<>("Fees");
		TableColumn<MoneyDTO, Integer> Col3 = new TableColumn<>("Credit");
		TableColumn<MoneyDTO, Integer> Col4 = new TableColumn<>("Paid");
		TableColumn<MoneyDTO, Integer> Col5 = new TableColumn<>("Balance");
		HalyardAlert conformation = new HalyardAlert(HalyardAlert.AlertType.CONFIRMATION);
		ComboBox<Integer> comboBox = new ComboBox<>();
		populateComboBox(comboBox);
		comboBox.getSelectionModel().select(1);
		///////////////////// SORT ///////////////////////////////////////////
		HBoxInvoiceList.fiscals.sort((p1, p2) -> Integer.compare(p2.getFiscal_year(), (p1.getFiscal_year())));
		
		///////////////////// ATTRIBUTES /////////////////////////////////////

		fiscalTableView.setEditable(false);
		fiscalTableView.setFixedCellSize(30);
		fiscalTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		
		Col1.setCellValueFactory(new PropertyValueFactory<>("fiscal_year"));
		Col2.setCellValueFactory(new PropertyValueFactory<>("total"));
		Col3.setCellValueFactory(new PropertyValueFactory<>("credit"));
		Col4.setCellValueFactory(new PropertyValueFactory<>("paid"));
		Col5.setCellValueFactory(new PropertyValueFactory<>("balance"));

		Col2.setStyle( "-fx-alignment: CENTER;");
		Col2.setStyle( "-fx-alignment: CENTER-RIGHT;");
		Col3.setStyle( "-fx-alignment: CENTER-RIGHT;");
		Col4.setStyle( "-fx-alignment: CENTER-RIGHT;");
		Col5.setStyle( "-fx-alignment: CENTER-RIGHT;");
		
		/////////////  ATTRIBUTES /////////////
		fiscalTableView.getColumns().addAll(Arrays.asList(Col1, Col2, Col3, Col4, Col5));
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

        ////////////////  LISTENERS ///////////////////
		addFiscalRecord.setOnAction((event) -> {
				// get the next available key for money_id table
				int moneyId = SqlSelect.getNextAvailablePrimaryKey("money","money_id");
				// create appropriate money object for this membership
				MoneyDTO newMoney = new MoneyDTO(moneyId, membership.getMsid(),
						comboBox.getValue(), 0, "0.00", 0, 0, 0, 0, 0, "0.00", 0, 0 ,0, 0, 0,0,
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
				System.out.println("the size of the Arraylist before inserting record=" + fiscals.size());
				fiscals.add(newMoney);
				System.out.println("the size of the Arraylist after inserting record=" + fiscals.size());
				// send new money row to top
				fiscals.sort(Comparator.comparing(MoneyDTO::getFiscal_year).reversed());
				// open a tab for the year we just created
				System.out.println("calling method createTabByYear and passing ");
				createTabByYear(newMoney);
		});
        
		deleteFiscalRecord.setOnAction((event) -> {
			int selectedIndex = fiscalTableView.getSelectionModel().getSelectedIndex();
			conformation.setTitle("Remove Invoice");
			conformation.setHeaderText("Invoice #" + fiscals.get(selectedIndex).getMoney_id());
			conformation.setContentText("Are sure you want to delete this " + fiscals.get(selectedIndex).getFiscal_year() + " invoice?");
			Optional<ButtonType> result = conformation.showAndWait();
			if (result.get() == ButtonType.OK){
				System.out.println("deleting fiscal record " + selectedIndex);
				SqlDelete.deletePaymentByMoneyID(fiscals.get(selectedIndex).getMoney_id());
				SqlDelete.deleteWorkCreditsByMoneyID(fiscals.get(selectedIndex).getMoney_id());
				SqlDelete.deleteMoneyByMoneyID(fiscals.get(selectedIndex).getMoney_id());
				fiscals.remove(selectedIndex);
			}
		});
		
		fiscalTableView.setRowFactory(tv -> {
			TableRow<MoneyDTO> row = new TableRow<>();
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
	private void populateComboBox(ComboBox<Integer> comboBox) {
		for(int i = Integer.parseInt(HalyardPaths.getYear()) + 1; i > 1969; i--) {
			comboBox.getItems().add(i);
		}
	}

	private BigDecimal getDues(int year) {  // takes the membership type and gets the dues
		DefinedFeeDTO selectedDefinedFee = SqlDefinedFee.getDefinedFeeByYear(String.valueOf(year));
		BigDecimal dues;
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

	private static void createTabByYear(MoneyDTO money) {
		System.out.println("inside method createTabByYear");
		// create a tab with the correct year
		Tab newTab = new Tab(String.valueOf(money.getFiscal_year()));
		// add tab to pane
		parentTabPane.getTabs().add(newTab);
		// find the index value of the correct Object_Money in fiscals ArrayList
		int fiscalsIndex = getFiscalIndexByYear(money.getFiscal_year());
		// add appropriate invoice to the tab using the index of fiscals
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
