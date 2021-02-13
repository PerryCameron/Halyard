package com.ecsail.gui.boxes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

import com.ecsail.enums.Officer;
import com.ecsail.main.EditCell;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlSelect;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.Object_Officer;
import com.ecsail.structures.Object_Person;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class BoxOfficer extends HBox {

	private Object_Person person;
	private ObservableList<Object_Officer> officer;
	private TableView<Object_Officer> officerTableView;
	private String currentYear;
	
	@SuppressWarnings("unchecked")
	public BoxOfficer(Object_Person p) {
		this.person = p;
		this.currentYear = new SimpleDateFormat("yyyy").format(new Date());
		this.officer =  SqlSelect.getOfficer("p_id",person.getP_id());
		
		///////////////// OBJECT INSTANCE ///////////////////
		Button officerAdd = new Button("Add");
		Button officerDelete = new Button("Delete");
		VBox vbox1 = new VBox(); // holds officer buttons
		HBox hboxGrey = new HBox(); // this is here for the grey background to make nice apperence
		VBox vboxPink = new VBox(); // this creates a pink border around the table

		/////////////////  ATTRIBUTES  /////////////////////
		officerAdd.setPrefWidth(60);
		officerDelete.setPrefWidth(60);
		hboxGrey.setPrefWidth(480);
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		vbox1.setSpacing(5);
		hboxGrey.setId("box-grey");
		vboxPink.setId("box-pink");
		hboxGrey.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink fram around table
		setId("box-blue");
		
		///////////////// TABLE VIEW ///////////////////////
			officerTableView = new TableView<Object_Officer>();
			officerTableView.setItems(officer);
			officerTableView.setPrefWidth(320);
			officerTableView.setPrefHeight(140);
			officerTableView.setFixedCellSize(30);
			officerTableView.setEditable(true);
			
			
			TableColumn<Object_Officer, String> Col1 = createColumn("Year", Object_Officer::fiscal_yearProperty);
			Col1.setSortType(TableColumn.SortType.DESCENDING);
			Col1.setPrefWidth(60);
	        Col1.setOnEditCommit(
	                new EventHandler<CellEditEvent<Object_Officer, String>>() {
	                    @Override
	                    public void handle(CellEditEvent<Object_Officer, String> t) {
	                        ((Object_Officer) t.getTableView().getItems().get(
	                                t.getTablePosition().getRow())
	                                ).setFiscal_year(t.getNewValue());
	                        SqlUpdate.updateOfficer("off_year",t.getRowValue().getOfficer_id(), t.getNewValue());  // have to get by money id and pid eventually
	                    }
	                }
	            );
	        
	        
	        ObservableList<Officer> officerList = FXCollections.observableArrayList(Officer.values());
	    	final TableColumn<Object_Officer, Officer> Col2 = new TableColumn<Object_Officer, Officer>("Officers, Chairs and Board");
	    	Col2.setPrefWidth(197);
	        Col2.setCellValueFactory(new Callback<CellDataFeatures<Object_Officer, Officer>, ObservableValue<Officer>>() {
	        	 
	            @Override
	            public ObservableValue<Officer> call(CellDataFeatures<Object_Officer, Officer> param) {
	                Object_Officer thisofficer = param.getValue();
	                String officerCode = thisofficer.getOfficer_type();
	                Officer type = Officer.getByCode(officerCode);
	                return new SimpleObjectProperty<Officer>(type);
	            }
	        });
	        
	        Col2.setCellFactory(ComboBoxTableCell.forTableColumn(officerList));
	 
	        Col2.setOnEditCommit((CellEditEvent<Object_Officer, Officer> event) -> {
	            TablePosition<Object_Officer, Officer> pos = event.getTablePosition();
	            Officer newOfficer = event.getNewValue();
	            int row = pos.getRow();
	            Object_Officer thisofficer = event.getTableView().getItems().get(row);
	            SqlUpdate.updateOfficer("off_type",thisofficer.getOfficer_id(), newOfficer.getCode());
	            thisofficer.setOfficer_type(newOfficer.getCode());
	        });
	        
			TableColumn<Object_Officer, String> Col3 = createColumn("Exp", Object_Officer::board_yearProperty);
			Col3.setPrefWidth(60);
	        Col3.setOnEditCommit(
	                new EventHandler<CellEditEvent<Object_Officer, String>>() {
	                    @Override
	                    public void handle(CellEditEvent<Object_Officer, String> t) {
	                        ((Object_Officer) t.getTableView().getItems().get(
	                                t.getTablePosition().getRow())
	                                ).setBoard_year(t.getNewValue());  // need to change
	                        	SqlUpdate.updateOfficer("board_year",t.getRowValue().getOfficer_id(), t.getNewValue());  // have to get by money id and pid eventually
	                    }
	                }
	            );
	        
	        ////////////////////// LISTENERS /////////////////////////
	    	    
	        officerAdd.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	int officer_id = SqlSelect.getCount("officer","o_id"); // gets last memo_id number
						officer_id++; // lets select next number
	            	officer.add(new Object_Officer(officer_id,person.getP_id(),"0","new officer",currentYear)); // lets add it to our list
						SqlInsert.addRecord(officer_id,person.getP_id(),"0","new officer",Integer.parseInt(currentYear)); // lets add it to our database
	            }
	        });
	        
	        officerDelete.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	int selectedIndex = officerTableView.getSelectionModel().getSelectedIndex();
	            	if(selectedIndex >= 0)
						if(SqlDelete.deleteOfficer(officer.get(selectedIndex)))
							officerTableView.getItems().remove(selectedIndex);
	            }
	        });
	        
	        /////////////////// SET CONTENT //////////////////
	        
			vbox1.getChildren().addAll(officerAdd, officerDelete);
			officerTableView.getColumns().addAll(Col1,Col2,Col3);
			officerTableView.getSortOrder().addAll(Col1);
			vboxPink.getChildren().add(officerTableView);
			officerTableView.sort();
			hboxGrey.getChildren().addAll(vboxPink,vbox1);
			getChildren().add(hboxGrey);
		
	} // CONSTRUCTOR END
	
	///////////////// CLASS METHODS /////////////////
	
    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }

}
