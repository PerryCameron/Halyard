package com.ecsail.gui.tabs;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.ecsail.gui.dialogues.Dialogue_FiscalPDF;
import com.ecsail.main.SqlSelect;
import com.ecsail.main.SqlUpdate;
import com.ecsail.main.TabLauncher;
import com.ecsail.structures.Object_DefinedFee;
import com.ecsail.structures.Object_Money;
import com.ecsail.structures.Object_PaidDues;
import com.ecsail.structures.Object_paidDuesText;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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

public class TabBatchedPaidDues extends Tab {
	private ObservableList<Object_PaidDues> paidDues;
	private Object_Money currentMoneyTotal = new Object_Money();
	private Object_DefinedFee currentDefinedFee;
	private Object_paidDuesText tText = new Object_paidDuesText();  // object of text objects
	String selectedYear;
	int batch;
	
	public TabBatchedPaidDues(String text) { 
		super(text);
		this.paidDues =  FXCollections.observableArrayList(new Callback<Object_PaidDues, Observable[]>() {
			@Override
			public Observable[] call(Object_PaidDues param) {
				return new Observable[] { param.isClosedProperty() };
				
			}
		});
		this.selectedYear = new SimpleDateFormat("yyyy").format(new Date());  // lets start at the current year
		this.paidDues.addAll(SqlSelect.getPaidDues(selectedYear));
		this.batch = SqlSelect.getBatchNumber();
		this.currentDefinedFee = SqlSelect.getDefinedFee(selectedYear);
		
		////////////////////// OBJECT INSTANCE //////////////////////////	
		
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
		Text nonRenewed = new Text("0");

		GridPane gridPane = new GridPane();
		TableView<Object_PaidDues> paidDuesTableView;
		Button refreshButton = new Button("Refresh");
		Button printPdfButton = new Button("Print PDF");

		//////////////////// OBJECT ATTRIBUTES ///////////////////////////
		
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
		yearBatchHBox.setAlignment(Pos.CENTER);
		batchNumberHBox.setAlignment(Pos.CENTER);
		gridHBox.setAlignment(Pos.CENTER);
		buttonHBox.setSpacing(10);
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
		
		final Spinner<Integer> yearSpinner = new Spinner<Integer>();
		SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, Integer.parseInt(selectedYear), Integer.parseInt(selectedYear));
		yearSpinner.setValueFactory(wetSlipValueFactory);
		yearSpinner.setPrefWidth(90);
		yearSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  selectedYear = yearSpinner.getEditor().getText();
			  }
			});
		
		final Spinner<Integer> batchSpinner = new Spinner<Integer>();
		SpinnerValueFactory<Integer> batchValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, batch); // 0 to batch, display batch
		batchSpinner.setValueFactory(batchValueFactory);
		batchSpinner.setPrefWidth(60);
		batchSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  batchSpinner.increment(0); // won't change value, but will commit editor
				  batch = Integer.parseInt(batchSpinner.getEditor().getText());
				  System.out.println("Batch is now " + batchSpinner.getEditor().getText());
				  // public static Object_DefinedFee getDefinedFee(String year, Object_DefinedFee thisDefinedFee)
				  // clear and update paidDues
				  // refresh the grid
				  updateCurrentMoneyTotals(); // need error check if batch doesn't exist
				  updateMoneyTotals(gridPane);
				  updateNonRenewed(nonRenewed);
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
                    	SqlUpdate.updateMoney(thisPaidDues.getMoney_id(), batch);
                    	SqlUpdate.updateMoney(thisPaidDues.getMoney_id(), true);
                    	thisPaidDues.setBatch(batch);
                    	} else { // if unchecked
                    	SqlUpdate.updateMoney(thisPaidDues.getMoney_id(), 0);
                    	SqlUpdate.updateMoney(thisPaidDues.getMoney_id(), false);
                    	thisPaidDues.setBatch(0);
                    	}
      				  updateCurrentMoneyTotals(); // need error check if batch doesn't exist
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
		
		TableColumn<Object_PaidDues, Integer> Col9 = new TableColumn<Object_PaidDues, Integer>("Membership ID");
		Col9.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, Integer>("membershipId"));
		
		TableColumn<Object_PaidDues, String> Col3 = new TableColumn<Object_PaidDues, String>("Last Name");
		Col3.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, String>("l_name"));
		Col3.setPrefWidth(120);
		
		TableColumn<Object_PaidDues, String> Col4 = new TableColumn<Object_PaidDues, String>("First Name");
		Col4.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, String>("f_name"));
		Col4.setPrefWidth(120);
		
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
				System.out.println("refresh");
				paidDues.clear();
				paidDues.addAll(SqlSelect.getPaidDues(selectedYear));
				updateNonRenewed(nonRenewed);
				}
			});
		
		printPdfButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				new Dialogue_FiscalPDF(selectedYear);
				}
			});
		
		///////////////////  SET CONTENT  ///////////////////////
		
		gridPane.add(new Text("Dues:"), 0, 0);
		gridPane.add(tText.getDuesMoneyText(), 2, 0);
		gridPane.add(new Text("Keys:"), 0, 1);
		gridPane.add(tText.getKeyText(), 1, 1);
		gridPane.add(tText.getKeyMoneyText(), 2, 1);
		gridPane.add(new Text("Wetslips:"), 0, 2);
		gridPane.add(tText.getWetSlipText(), 1, 2);
		gridPane.add(tText.getWetSlipMoneyText(), 2, 2);
		gridPane.add(new Text("Kayac Rack:"), 0, 3);
		gridPane.add(tText.getKayacRackText(), 1, 3);
		gridPane.add(tText.getKayacRackMoneyText(), 2, 3);
		gridPane.add(new Text("Kayac Shed:"), 0, 4);
		gridPane.add(tText.getKayacShedText(), 1, 4);
		gridPane.add(tText.getKayacShedMoneyText(), 2, 4);
		gridPane.add(new Text("Sail Loft:"), 0, 5);
		gridPane.add(tText.getSailLoftText(), 1, 5);
		gridPane.add(tText.getSailLoftMoneyText(), 2, 5);
		gridPane.add(new Text("Winter Storage:"), 0, 6);
		gridPane.add(tText.getWinterStorageText(), 1, 6);
		gridPane.add(tText.getWinterStorageMoneyText(), 2, 6);
		gridPane.add(new Text("Credits:"), 0, 7);
		gridPane.add(tText.getCreditsMoneyText(), 2, 7);
		gridPane.add(new Text("Total:"), 0, 8);
		gridPane.add(tText.getTotalMoneyText(), 2, 8);
		
		remaindingRenewalHBox.getChildren().addAll(new Text("Memberships not yet renewed: " ),nonRenewed);
		batchNumberHBox.getChildren().addAll(new Label("Batch Number"),batchSpinner);
		yearBatchHBox.getChildren().addAll(yearSpinner,batchNumberHBox);
		buttonHBox.getChildren().addAll(refreshButton,printPdfButton);
		gridHBox.getChildren().add(gridPane);
		controlsHBox.getChildren().add(controlsVBox);
		controlsVBox.getChildren().addAll(yearBatchHBox,gridHBox,buttonHBox,remaindingRenewalHBox);
		paidDuesTableView.getColumns().addAll(Arrays.asList(Col1,Col2,Col9,Col3,Col4,Col5,Col6,Col7,Col8));
		mainHBox.getChildren().addAll(paidDuesTableView,controlsHBox);
		vboxGrey.getChildren().add(mainHBox);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}
	
	////////////////////////CLASS METHODS //////////////////////////
	
	private void updateNonRenewed(Text nonRenewed) {
		nonRenewed.setText(SqlSelect.getNonMembershipRenewalCount(selectedYear) + "");
	}
	
	private void updateCurrentMoneyTotals() {
		currentMoneyTotal.setDues(SqlSelect.getMoneyCount("DUES", batch));
		currentMoneyTotal.setExtra_key(SqlSelect.getMoneyCount("KAYAK_SHED_KEY+SAIL_LOFT_KEY+SAIL_SCHOOL_LOFT_KEY+EXTRA_KEY", batch));
		currentMoneyTotal.setWet_slip(SqlSelect.getMoneyCount("WET_SLIP", batch));
		currentMoneyTotal.setKayac_rack(SqlSelect.getMoneyCount("KAYAK_RACK", batch));
		currentMoneyTotal.setKayac_shed(SqlSelect.getMoneyCount("KAYAK_SHED", batch));
		currentMoneyTotal.setSail_loft(SqlSelect.getMoneyCount("SAIL_LOFT+SAIL_SCHOOL_LASER_LOFT", batch));
		currentMoneyTotal.setWinter_storage(SqlSelect.getMoneyCount("WINTER_STORAGE", batch));
		currentMoneyTotal.setCredit(SqlSelect.getMoneyCount("CREDIT", batch));
		currentMoneyTotal.setTotal(SqlSelect.getMoneyCount("TOTAL", batch));
	}
	
	private void updateMoneyTotals(GridPane gridPane) {  // need to add defined fees object
		tText.changeDuesMoneyText(currentMoneyTotal.getDues() + "");
		tText.changeKeyText(currentMoneyTotal.getExtra_key() + "");
		tText.changeKeyMoneyText((currentMoneyTotal.getExtra_key() * currentDefinedFee.getMain_gate_key()) + "");
		tText.changeWetSlipText(currentMoneyTotal.getWet_slip() + "");
		tText.changeWetSlipMoneyText((currentMoneyTotal.getWet_slip() * currentDefinedFee.getWet_slip()) + "");
		tText.changeKayacRackText(currentMoneyTotal.getKayac_rack() + "");
		tText.changeKayacRackMoneyText((currentMoneyTotal.getKayac_rack() * currentDefinedFee.getKayak_rack()) + "");
		tText.changeKayacShedText(currentMoneyTotal.getKayac_shed() + "");
		tText.changeKayacShedMoneyText((currentMoneyTotal.getKayac_shed() * currentDefinedFee.getKayak_shed()) + "");
		tText.changeSailLoftText(currentMoneyTotal.getSail_loft() + "");
		tText.changeSailLoftMoneyText((currentMoneyTotal.getSail_loft() * currentDefinedFee.getSail_loft()) +"");
		tText.changeWinterStorageText(currentMoneyTotal.getWinter_storage() + "");
		tText.changeWinterStorageMoneyText((currentMoneyTotal.getWinter_storage() * currentDefinedFee.getWinter_storage()) + "");
		tText.changeCreditsMoneyText(currentMoneyTotal.getCredit() + "");
		tText.changeTotalText(currentMoneyTotal.getTotal() + "");
	}
	
	
}
