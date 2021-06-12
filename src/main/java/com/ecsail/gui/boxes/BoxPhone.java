package com.ecsail.gui.boxes;

import java.util.Arrays;
import java.util.function.Function;

import com.ecsail.enums.PhoneType;
import com.ecsail.main.EditCell;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlSelect;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.Object_Person;
import com.ecsail.structures.Object_Phone;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class BoxPhone extends HBox {
	
	private Object_Person person;
	private TableView<Object_Phone> phoneTableView;
	private ObservableList<Object_Phone> phone;
	
	public BoxPhone(Object_Person p) {
		this.person = p;  // the below callback is to allow commit when focus removed, overrides FX default behavior
		this.phone = FXCollections.observableArrayList(new Callback<Object_Phone, Observable[]>() {
			@Override
			public Observable[] call(Object_Phone param) {
				return new Observable[] { param.isListedProperty() };

			}
		});
		this.phone.addAll(SqlSelect.getPhone(person.getP_id()));
		
		/////// OBJECT INSTANCE //////	
		VBox vboxButtons = new VBox(); // holds phone buttons
		Button phoneAdd = new Button("Add");
		Button phoneDelete = new Button("Delete");
		HBox hboxGrey = new HBox(); // this is here for the grey background to make nice apperence
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		phoneTableView = new TableView<Object_Phone>();

		//// OBJECT ATTRIBUTES /////
		phoneAdd.setPrefWidth(60);
		phoneDelete.setPrefWidth(60);
		vboxButtons.setPrefWidth(80);
		vboxButtons.setSpacing(5); // spacing between buttons
		//hboxGrey.setPrefWidth(480);
		
		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(phoneTableView, Priority.ALWAYS);
		VBox.setVgrow(phoneTableView, Priority.ALWAYS);
		
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		hboxGrey.setId("box-grey");
		vboxPink.setId("box-pink");
		hboxGrey.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink fram around table
		VBox.setVgrow(phoneTableView, Priority.ALWAYS);

		///// TABLEVIE INSTANCE CREATION AND ATTRIBUTES /////
		
		phoneTableView.setItems(phone);
		phoneTableView.setFixedCellSize(30);
		phoneTableView.setEditable(true);
		phoneTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
			
		// example for this column found at https://gist.github.com/james-d/be5bbd6255a4640a5357#file-editcell-java-L109
		TableColumn<Object_Phone, String> Col1 = createColumn("Phone", Object_Phone::phoneNumberProperty);
        Col1.setOnEditCommit(
                new EventHandler<CellEditEvent<Object_Phone, String>>() {
                    @Override
                    public void handle(CellEditEvent<Object_Phone, String> t) {
                        ((Object_Phone) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setPhoneNumber(t.getNewValue());
                        int phone_id = ((Object_Phone) t.getTableView().getItems().get(t.getTablePosition().getRow())).getPhone_ID();
                        	SqlUpdate.updatePhone("phone", phone_id, t.getNewValue());
                    }
                }
            );
		
        // example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
        ObservableList<PhoneType> phoneTypeList = FXCollections.observableArrayList(PhoneType.values());
		TableColumn<Object_Phone, PhoneType> Col2 = new TableColumn<Object_Phone, PhoneType>("Type");
		Col2.setCellValueFactory(new Callback<CellDataFeatures<Object_Phone, PhoneType>, ObservableValue<PhoneType>>() {
        	 
            @Override
            public ObservableValue<PhoneType> call(CellDataFeatures<Object_Phone, PhoneType> param) {
            	Object_Phone thisPhone = param.getValue();
                String phoneCode = thisPhone.getPhoneType();
                PhoneType phoneType = PhoneType.getByCode(phoneCode);
                return new SimpleObjectProperty<PhoneType>(phoneType);
            }
        });
 
		Col2.setCellFactory(ComboBoxTableCell.forTableColumn(phoneTypeList));
 
		Col2.setOnEditCommit((CellEditEvent<Object_Phone, PhoneType> event) -> {
            TablePosition<Object_Phone, PhoneType> pos = event.getTablePosition();
            PhoneType newPhoneType = event.getNewValue();
            int row = pos.getRow();
            Object_Phone thisPhone = event.getTableView().getItems().get(row);
            SqlUpdate.updatePhone("phone_type", thisPhone.getPhone_ID(), newPhoneType.getCode());
            thisPhone.setPhoneType(newPhoneType.getCode());
        });
		
		// example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
		TableColumn<Object_Phone, Boolean> Col3 = new TableColumn<Object_Phone, Boolean>("Listed");
		Col3.setCellValueFactory(new Callback<CellDataFeatures<Object_Phone, Boolean>, ObservableValue<Boolean>>() {
	            @Override
	            public ObservableValue<Boolean> call(CellDataFeatures<Object_Phone, Boolean> param) {
	            	Object_Phone phone = param.getValue();
	                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(phone.isIsListed());
	                // Note: singleCol.setOnEditCommit(): Not work for
	                // CheckBoxTableCell.
	                // When "isListed?" column change.
	                booleanProp.addListener(new ChangeListener<Boolean>() {
	                    @Override
	                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
	                            Boolean newValue) {
	                        phone.setIsListed(newValue);
	                        SqlUpdate.updateListed("phone_listed",phone.getPhone_ID(), newValue);
	                    }
	                });
	                return booleanProp;
	            }
	        });
	 
		Col3.setCellFactory(new Callback<TableColumn<Object_Phone, Boolean>, //
	        TableCell<Object_Phone, Boolean>>() {
	            @Override
	            public TableCell<Object_Phone, Boolean> call(TableColumn<Object_Phone, Boolean> p) {
	                CheckBoxTableCell<Object_Phone, Boolean> cell = new CheckBoxTableCell<Object_Phone, Boolean>();
	                cell.setAlignment(Pos.CENTER);
	                return cell;
	            }
	        });
		
		/// sets width of columns by percentage
		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 50);   // Phone
		Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 30 );  // Type
		Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );  // Listed

		
		/////////////////// LISTENERS //////////////////////////////
		
		phoneAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				int phone_id = SqlSelect.getCount("phone", "phone_id"); // get last phone_id number
				phone_id++; // increase to first available number
				if (SqlInsert.addRecord(phone_id, person.getP_id(), true, "new phone", "")) // if added with no errors
					phone.add(new Object_Phone(phone_id, person.getP_id(), true, "new phone", "")); // lets add it to our GUI
				}
			});
        
        phoneDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	int selectedIndex = phoneTableView.getSelectionModel().getSelectedIndex();
            		if(selectedIndex >= 0)
            			if(SqlDelete.deletePhone(phone.get(selectedIndex)))  // if it is properly deleted in our database
            				phoneTableView.getItems().remove(selectedIndex); // remove it from our GUI
            }
        });
		
		///////////////////  SET CONTENT  ///////////////////////
		
		phoneTableView.getColumns().addAll(Arrays.asList(Col1,Col2,Col3));
		vboxPink.getChildren().add(phoneTableView);  // adds pink border around table
		vboxButtons.getChildren().addAll(phoneAdd, phoneDelete); // lines buttons up vertically
		hboxGrey.getChildren().addAll(vboxPink,vboxButtons);
		getChildren().add(hboxGrey);
		
	}  // end of constructor
	
	////////////////////////  CLASS METHODS //////////////////////////
    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }
} 
