package com.ecsail.gui.boxes;

import java.util.Collections;
import java.util.Comparator;
import java.util.function.Function;

import com.ecsail.main.EditCell;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlEmail;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.EmailDTO;
import com.ecsail.structures.Object_Person;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class HBoxEmail extends HBox {
	
	private Object_Person person;
	private ObservableList<EmailDTO> email;
	private TableView<EmailDTO> emailTableView;
	
	@SuppressWarnings("unchecked")
	public HBoxEmail(Object_Person p) {
		this.person = p;
		this.email =  FXCollections.observableArrayList(new Callback<EmailDTO, Observable[]>() {
			@Override
			public Observable[] call(EmailDTO param) {
				return new Observable[] { param.isPrimaryUseProperty() };
				
			}
		});
		this.email.addAll(SqlEmail.getEmail(person.getP_id()));
		
		//////////////// OBJECTS //////////////////////
		
		Button emailAdd = new Button("Add");
		Button emailDelete = new Button("Delete");
		VBox vboxButtons = new VBox(); // holds email buttons
		HBox hboxGrey = new HBox(); // this is here for the grey background to make nice apperence
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		emailTableView = new TableView<EmailDTO>();

		/////////////////  ATTRIBUTES  /////////////////////
		emailAdd.setPrefWidth(60);
		emailDelete.setPrefWidth(60);
		vboxButtons.setPrefWidth(80);

		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(emailTableView, Priority.ALWAYS);
		VBox.setVgrow(emailTableView, Priority.ALWAYS);
		
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		vboxButtons.setSpacing(5);
		
		hboxGrey.setId("box-grey");
		vboxPink.setId("box-pink");
		this.setId("box-blue");		
		
		hboxGrey.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink fram around table

		
		///////////////// TABLE VIEW ///////////////////////
		
			
			emailTableView.setItems(email);
			emailTableView.setFixedCellSize(30);
			emailTableView.setEditable(true);
			emailTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
			
			TableColumn<EmailDTO, String> Col1 = createColumn("Email", EmailDTO::emailProperty);
			Col1.setPrefWidth(137);
			Col1.setOnEditCommit(new EventHandler<CellEditEvent<EmailDTO, String>>() {
				@Override
				public void handle(CellEditEvent<EmailDTO, String> t) {
					((EmailDTO) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setEmail(t.getNewValue());
					int email_id = ((EmailDTO) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.getEmail_id();
					SqlUpdate.updateEmail(email_id, t.getNewValue());
				}
			});
			
			// example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
			TableColumn<EmailDTO, Boolean> Col2 = new TableColumn<EmailDTO, Boolean>("Primary");
			Col2.setCellValueFactory(new Callback<CellDataFeatures<EmailDTO, Boolean>, ObservableValue<Boolean>>() {
	            @Override
	            public ObservableValue<Boolean> call(CellDataFeatures<EmailDTO, Boolean> param) {
	            	EmailDTO email = param.getValue();
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
	 
			Col2.setCellFactory(new Callback<TableColumn<EmailDTO, Boolean>, //
	        TableCell<EmailDTO, Boolean>>() {
	            @Override
	            public TableCell<EmailDTO, Boolean> call(TableColumn<EmailDTO, Boolean> p) {
	                CheckBoxTableCell<EmailDTO, Boolean> cell = new CheckBoxTableCell<EmailDTO, Boolean>();
	                cell.setAlignment(Pos.CENTER);
	                return cell;
	            }
	        });
			
			// example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
			TableColumn<EmailDTO, Boolean> Col3 = new TableColumn<EmailDTO, Boolean>("Listed");
			Col3.setCellValueFactory(new Callback<CellDataFeatures<EmailDTO, Boolean>, ObservableValue<Boolean>>() {
	            @Override
	            public ObservableValue<Boolean> call(CellDataFeatures<EmailDTO, Boolean> param) {
	            	EmailDTO email = param.getValue();
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
	 
			Col3.setCellFactory(new Callback<TableColumn<EmailDTO, Boolean>, //
	        TableCell<EmailDTO, Boolean>>() {
	            @Override
	            public TableCell<EmailDTO, Boolean> call(TableColumn<EmailDTO, Boolean> p) {
	                CheckBoxTableCell<EmailDTO, Boolean> cell = new CheckBoxTableCell<EmailDTO, Boolean>();
	                cell.setAlignment(Pos.CENTER);
	                return cell;
	            }
	        });
			
			/// sets width of columns by percentage
			Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 50);   // Phone
			Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Type
			Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Listed
			
			/////////////////  LISTENERS ////////////////////////

	        emailAdd.setOnAction((event) -> {
				// get the next available primary key for table email
				int email_id = SqlSelect.getCount("email","email_id") + 1; // gets last memo_id number
				// add record to SQL and return sucess or not
				if(SqlInsert.addEmailRecord(email_id,person.getP_id(),true,"new email",true))
					// if we have added it to SQL we need to create a new row in tableview to match
	            	email.add(new EmailDTO(email_id,person.getP_id(),true,"new email",true));
				// Now we will sort it to the top
				Collections.sort(email, Comparator.comparing(EmailDTO::getEmail_id).reversed());
				// this line prevents strange buggy behaviour
				emailTableView.layout();
				// edit the phone number cell after creating
				emailTableView.edit(0, Col1);
	        });
	        
	        emailDelete.setOnAction((event) -> {
	        	    int selectedIndex = emailTableView.getSelectionModel().getSelectedIndex();
	        	    	if(selectedIndex >= 0) // make sure something is selected
	        	    		if(SqlDelete.deleteEmail(email.get(selectedIndex)))  // if deleted in database
	        	    			emailTableView.getItems().remove(selectedIndex); // remove from GUI 
	            });
	        
	        ///////////////////  SET CONTENT ////////////////////
	        
			vboxButtons.getChildren().addAll(emailAdd, emailDelete);
			emailTableView.getColumns().addAll(Col1,Col2,Col3);
			vboxPink.getChildren().add(emailTableView);
			hboxGrey.getChildren().addAll(vboxPink,vboxButtons);
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
