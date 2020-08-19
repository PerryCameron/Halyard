package com.ecsail.gui;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.ecsail.main.SqlSelect;
import com.ecsail.structures.Object_PaidDues;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class TabBatchedPaidDues extends Tab {
	private ObservableList<Object_PaidDues> paidDues; 
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
		this.paidDues.addAll(SqlSelect.getPaidDues());
		this.selectedYear = new SimpleDateFormat("yyyy").format(new Date());  // lets start at the current year
		this.batch = SqlSelect.getBatchNumber();
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		HBox mainHBox = new HBox(); // this separates table content from controls
		VBox controlsVBox = new VBox(); // inner grey box
		HBox controlsHBox = new HBox(); // outer blue box
		HBox batchNumberHBox = new HBox();
		TableView<Object_PaidDues> paidDuesTableView;
		// Button batchButton = new Button("Submit Batch");

		
		
		vboxBlue.setId("box-blue");
		controlsHBox.setId("box-blue");
		controlsHBox.setPadding(new Insets(5,5,5,5));
		controlsVBox.setPrefWidth(342);
		controlsVBox.setSpacing(10);
		controlsVBox.setPadding(new Insets(5,5,5,5));
		controlsVBox.setId("box-grey");
		mainHBox.setSpacing(5);
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink fram around table
		vboxPink.setId("box-pink");
		batchNumberHBox.setSpacing(5);
		batchNumberHBox.setAlignment(Pos.CENTER_LEFT);
		vboxGrey.setPrefHeight(688);
		
		paidDuesTableView = new TableView<Object_PaidDues>();
		paidDuesTableView.setItems(paidDues);
		paidDuesTableView.setPrefWidth(645);
		paidDuesTableView.setPrefHeight(688);
		paidDuesTableView.setFixedCellSize(30);
		paidDuesTableView.setEditable(true);
		
		final Spinner<Integer> yearSpinner = new Spinner<Integer>();
		SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, Integer.parseInt(selectedYear), Integer.parseInt(selectedYear));
		yearSpinner.setValueFactory(wetSlipValueFactory);
		yearSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  selectedYear = yearSpinner.getEditor().getText();
				  // do stuff here
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
                    	thisPaidDues.setClosed(newValue);
                    	System.out.println("Closed set to=" + newValue);
                    	if(newValue) {
                    	System.out.println("Batch number will be " + SqlSelect.getBatchNumber());
                    	thisPaidDues.setBatch(batch);
                    	} else {
                    	System.out.println("Batch number will be 0");
                    	thisPaidDues.setBatch(0);
                    	}
                       // email.setIsPrimaryUse(newValue);
                       // SqlUpdate.updateEmail("primary_use",email.getEmail_id(), newValue);
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
		
		batchNumberHBox.getChildren().addAll(new Label("Batch Number"),batchSpinner);
		controlsHBox.getChildren().add(controlsVBox);
		controlsVBox.getChildren().addAll(yearSpinner,batchNumberHBox);
		paidDuesTableView.getColumns().addAll(Arrays.asList(Col1,Col2,Col9,Col3,Col4,Col5,Col6,Col7,Col8));
		mainHBox.getChildren().addAll(paidDuesTableView,controlsHBox);
		vboxGrey.getChildren().add(mainHBox);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}
	
	
}
