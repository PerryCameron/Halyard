package com.ecsail.gui.boxes;

import java.util.function.Function;

import com.ecsail.main.EditCell;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlSelect;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.Object_Email;
import com.ecsail.structures.Object_Person;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class BoxEmail extends HBox {
	
	private Object_Person person;
	private ObservableList<Object_Email> email;
	private TableView<Object_Email> emailTableView;
	
	@SuppressWarnings("unchecked")
	public BoxEmail(Object_Person p) {
		this.person = p;
		this.email =  FXCollections.observableArrayList(new Callback<Object_Email, Observable[]>() {
			@Override
			public Observable[] call(Object_Email param) {
				return new Observable[] { param.isPrimaryUseProperty() };
				
			}
		});
		this.email.addAll(SqlSelect.getEmail(person.getP_id()));
		
		//////////////// OBJECTS //////////////////////
		
		Button emailAdd = new Button("Add");
		Button emailDelete = new Button("Delete");
		VBox vbox1 = new VBox(); // holds email buttons
		HBox hboxGrey = new HBox(); // this is here for the grey background to make nice apperence
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		

		/////////////////  ATTRIBUTES  /////////////////////
		emailAdd.setPrefWidth(60);
		emailDelete.setPrefWidth(60);
		hboxGrey.setPrefWidth(480);
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		vbox1.setSpacing(5);
		hboxGrey.setId("box-grey");
		vboxPink.setId("box-pink");
		hboxGrey.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink fram around table
		setId("box-blue");
		
		///////////////// TABLE VIEW ///////////////////////
		
			emailTableView = new TableView<Object_Email>();
			emailTableView.setItems(email);
			emailTableView.setPrefWidth(320);
			emailTableView.setPrefHeight(140);
			emailTableView.setFixedCellSize(30);
			emailTableView.setEditable(true);
			
			TableColumn<Object_Email, String> Col1 = createColumn("Email", Object_Email::emailProperty);	
			Col1.setPrefWidth(137);
			Col1.setOnEditCommit(new EventHandler<CellEditEvent<Object_Email, String>>() {
				@Override
				public void handle(CellEditEvent<Object_Email, String> t) {
					((Object_Email) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setEmail(t.getNewValue());
					int email_id = ((Object_Email) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.getEmail_id();
					SqlUpdate.updateEmail(email_id, t.getNewValue());
				}
			});
			
			// example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
			TableColumn<Object_Email, Boolean> Col2 = new TableColumn<Object_Email, Boolean>("Primary");
			Col2.setPrefWidth(90);
			Col2.setCellValueFactory(new Callback<CellDataFeatures<Object_Email, Boolean>, ObservableValue<Boolean>>() {
	            @Override
	            public ObservableValue<Boolean> call(CellDataFeatures<Object_Email, Boolean> param) {
	            	Object_Email email = param.getValue();
	                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(email.isIsPrimaryUse());
	                // Note: singleCol.setOnEditCommit(): Not work for
	                // CheckBoxTableCell.
	 
	                // When "Primary Use?" column change.
	                booleanProp.addListener(new ChangeListener<Boolean>() {
	 
	                    @Override
	                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
	                            Boolean newValue) {
	                        email.setIsPrimaryUse(newValue);
	                        SqlUpdate.updateEmail("primary_use",email.getEmail_id(), newValue);
	                    }
	                });
	                return booleanProp;
	            }
	        });
	 
			Col2.setCellFactory(new Callback<TableColumn<Object_Email, Boolean>, //
	        TableCell<Object_Email, Boolean>>() {
	            @Override
	            public TableCell<Object_Email, Boolean> call(TableColumn<Object_Email, Boolean> p) {
	                CheckBoxTableCell<Object_Email, Boolean> cell = new CheckBoxTableCell<Object_Email, Boolean>();
	                cell.setAlignment(Pos.CENTER);
	                return cell;
	            }
	        });
			
			// example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
			TableColumn<Object_Email, Boolean> Col3 = new TableColumn<Object_Email, Boolean>("Listed");
			Col3.setPrefWidth(90);
			Col3.setCellValueFactory(new Callback<CellDataFeatures<Object_Email, Boolean>, ObservableValue<Boolean>>() {
	            @Override
	            public ObservableValue<Boolean> call(CellDataFeatures<Object_Email, Boolean> param) {
	            	Object_Email email = param.getValue();
	                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(email.isIsListed());
	                // Note: singleCol.setOnEditCommit(): Not work for
	                // CheckBoxTableCell.
	 
	                // When "Listed?" column change.
	                booleanProp.addListener(new ChangeListener<Boolean>() {
	 
	                    @Override
	                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
	                            Boolean newValue) {
	                        email.setIsListed(newValue);
	                        SqlUpdate.updateEmail("email_listed",email.getEmail_id(), newValue);
	                    }
	                });
	                return booleanProp;
	            }
	        });
	 
			Col3.setCellFactory(new Callback<TableColumn<Object_Email, Boolean>, //
	        TableCell<Object_Email, Boolean>>() {
	            @Override
	            public TableCell<Object_Email, Boolean> call(TableColumn<Object_Email, Boolean> p) {
	                CheckBoxTableCell<Object_Email, Boolean> cell = new CheckBoxTableCell<Object_Email, Boolean>();
	                cell.setAlignment(Pos.CENTER);
	                return cell;
	            }
	        });
			
			/////////////////  LISTENERS ////////////////////////

	        emailAdd.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	int email_id = SqlSelect.getCount("email","email_id"); // gets last memo_id number
					email_id++; // lets select next number
	            	email.add(new Object_Email(email_id,person.getP_id(),true,"new email",true)); // lets add it to our list
						SqlInsert.addRecord(email_id,person.getP_id(),true,"new email",true); // lets add it to our database
	            }
	        });
	        
	        emailDelete.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	        	    int selectedIndex = emailTableView.getSelectionModel().getSelectedIndex();
	        	    	if(selectedIndex >= 0) // make sure something is selected
	        	    		if(SqlDelete.deleteEmail(email.get(selectedIndex)))  // if deleted in database
	        	    			emailTableView.getItems().remove(selectedIndex); // remove from GUI 
	            }
	        });
	        
	        ///////////////////  SET CONTENT ////////////////////
	        
			vbox1.getChildren().addAll(emailAdd, emailDelete);
			emailTableView.getColumns().addAll(Col1,Col2,Col3);
			vboxPink.getChildren().add(emailTableView);
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
