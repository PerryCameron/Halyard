package com.ecsail.gui;

import com.ecsail.main.SqlSelect;
import com.ecsail.structures.Object_PaidDues;
import com.ecsail.structures.Object_Phone;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class TabBatchedPaidDues extends Tab {
	private ObservableList<Object_PaidDues> paidDues; 
	public TabBatchedPaidDues(String text) { 
		super(text);
		this.paidDues =  FXCollections.observableArrayList(new Callback<Object_PaidDues, Observable[]>() {
			@Override
			public Observable[] call(Object_PaidDues param) {
				return new Observable[] { param.isClosedProperty() };
				
			}
		});
		this.paidDues.addAll(SqlSelect.getPaidDues());
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		TableView<Object_PaidDues> paidDuesTableView;
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink fram around table
		vboxPink.setId("box-pink");
		//vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		
		paidDuesTableView = new TableView<Object_PaidDues>();
		paidDuesTableView.setItems(paidDues);
		paidDuesTableView.setPrefWidth(700);
		paidDuesTableView.setPrefHeight(140);
		paidDuesTableView.setFixedCellSize(30);
		paidDuesTableView.setEditable(true);
		
		// example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
		TableColumn<Object_PaidDues, Boolean> Col1 = new TableColumn<Object_PaidDues, Boolean>("Select");
		Col1.setPrefWidth(90);
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
		
		TableColumn<Object_PaidDues, String> Col3 = new TableColumn<Object_PaidDues, String>("Last Name");
		Col3.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, String>("l_name"));
		Col3.setPrefWidth(120);
		
		TableColumn<Object_PaidDues, String> Col4 = new TableColumn<Object_PaidDues, String>("First Name");
		Col4.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, String>("f_name"));
		Col4.setPrefWidth(120);
		
		TableColumn<Object_PaidDues, Integer> Col5 = new TableColumn<Object_PaidDues, Integer>("Batch");
		Col5.setCellValueFactory(new PropertyValueFactory<Object_PaidDues, Integer>("batch"));
		
		paidDuesTableView.getColumns().addAll(Col1,Col2,Col3,Col4,Col5);
		
		vboxGrey.getChildren().add(paidDuesTableView);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}
	
	
}
