package com.ecsail.gui.boxes;

import java.util.Arrays;
import java.util.function.Function;

import com.ecsail.enums.KeelType;
import com.ecsail.main.EditCell;
import com.ecsail.main.Launcher;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlSelect;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.Object_Boat;
import com.ecsail.structures.Object_MembershipList;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.Observable;
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
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class BoxBoat extends HBox{
	
Object_MembershipList membership;
private ObservableList<Object_Boat> boats;  // = FXCollections.observableArrayList();
private TableView<Object_Boat> boatTableView;
	
	//@SuppressWarnings("unchecked")
	public BoxBoat(Object_MembershipList m) {
	this.membership = m;
	this.boats = FXCollections.observableArrayList(new Callback<Object_Boat, Observable[]>() {
		@Override
		public Observable[] call(Object_Boat param) {
			return new Observable[] { param.hasTrailerProperty() };
		}
	});
	this.boats.addAll(SqlSelect.getBoats(membership.getMsid()));
	this.boatTableView = new TableView<Object_Boat>();
	///////////	 OBJECTS ///////////////

	HBox hboxGrey = new HBox();  // this is the vbox for organizing all the widgets
	VBox vboxPink = new VBox(); // this creates a pink border around the table
	VBox buttonVBox = new VBox();
	Button boatAdd = new Button("Add");
	Button boatDelete = new Button("Delete");
	Button boatView = new Button("view");
	
    
    /////////////////  ATTRIBUTES  /////////////////////
	
    buttonVBox.setSpacing(5);
    hboxGrey.setSpacing(10);
    this.setSpacing(10);
    
	boatAdd.setPrefWidth(60);
	boatDelete.setPrefWidth(60);
	boatView.setPrefWidth(60);
	boatTableView.setPrefWidth(850);
	
	VBox.setVgrow(boatTableView, Priority.ALWAYS);
	HBox.setHgrow(boatTableView, Priority.ALWAYS);
	HBox.setHgrow(hboxGrey, Priority.ALWAYS);
	HBox.setHgrow(vboxPink, Priority.ALWAYS);
	
	hboxGrey.setPadding(new Insets(5, 5, 5, 5));
	vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink fram around table
	setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
	
	hboxGrey.setId("box-grey");
	vboxPink.setId("box-pink");
	this.setId("box-blue");
	
	///////////////// TABLE VIEW ///////////////////////
	
	boatTableView.setItems(boats);
	boatTableView.setFixedCellSize(30);
	boatTableView.setEditable(true);
	boatTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
	

	final TableColumn<Object_Boat, String> col1 = createColumn("Boat Name", Object_Boat::boat_nameProperty);
    col1.setOnEditCommit(
            new EventHandler<CellEditEvent<Object_Boat, String>>() {
                @Override
                public void handle(CellEditEvent<Object_Boat, String> t) {
                    ((Object_Boat) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setBoat_name(t.getNewValue());
                    int boat_id = ((Object_Boat) t.getTableView().getItems().get(t.getTablePosition().getRow())).getBoat_id();
                    SqlUpdate.updateBoat("boat_name", boat_id, t.getNewValue());
                }
            }
        );
	
	final TableColumn<Object_Boat, String> col2 = createColumn("Manufacturer", Object_Boat::manufacturerProperty);
    col2.setOnEditCommit(
            new EventHandler<CellEditEvent<Object_Boat, String>>() {
                @Override
                public void handle(CellEditEvent<Object_Boat, String> t) {
                    ((Object_Boat) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setManufacturer(t.getNewValue());
                    int boat_id = ((Object_Boat) t.getTableView().getItems().get(t.getTablePosition().getRow())).getBoat_id();
                    SqlUpdate.updateBoat("manufacturer", boat_id, t.getNewValue());
                }
            }
        );
    
    final TableColumn<Object_Boat, String> col3 = createColumn("Year", Object_Boat::manufacture_yearProperty);
    col3.setOnEditCommit(
            new EventHandler<CellEditEvent<Object_Boat, String>>() {
                @Override
                public void handle(CellEditEvent<Object_Boat, String> t) {
                    ((Object_Boat) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setManufacture_year(t.getNewValue());
                    int boat_id = ((Object_Boat) t.getTableView().getItems().get(t.getTablePosition().getRow())).getBoat_id();
                    SqlUpdate.updateBoat("manufacture_year",boat_id, t.getNewValue());
                }
            }
        );
    
	final TableColumn<Object_Boat, String> col4 = createColumn("Model", Object_Boat::modelProperty);
    col4.setOnEditCommit(
            new EventHandler<CellEditEvent<Object_Boat, String>>() {
                @Override
                public void handle(CellEditEvent<Object_Boat, String> t) {
                    ((Object_Boat) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setModel(t.getNewValue());
                    int boat_id = ((Object_Boat) t.getTableView().getItems().get(t.getTablePosition().getRow())).getBoat_id();
                    SqlUpdate.updateBoat("model",boat_id, t.getNewValue());
                }
            }
        );
    
	final TableColumn<Object_Boat, String> col5 = createColumn("Registration", Object_Boat::registration_numProperty);
	col5.setOnEditCommit(
            new EventHandler<CellEditEvent<Object_Boat, String>>() {
                @Override
                public void handle(CellEditEvent<Object_Boat, String> t) {
                    ((Object_Boat) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setRegistration_num(t.getNewValue());
                    int boat_id = ((Object_Boat) t.getTableView().getItems().get(t.getTablePosition().getRow())).getBoat_id();
                    SqlUpdate.updateBoat("registration_num",boat_id, t.getNewValue());
                }
            }
        );
    
	final TableColumn<Object_Boat, String> col6 = createColumn("Sail #", Object_Boat::sail_numberProperty);
	col6.setOnEditCommit(
            new EventHandler<CellEditEvent<Object_Boat, String>>() {
                @Override
                public void handle(CellEditEvent<Object_Boat, String> t) {
                    ((Object_Boat) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setSail_number(t.getNewValue());
                    int boat_id = ((Object_Boat) t.getTableView().getItems().get(t.getTablePosition().getRow())).getBoat_id();
                    SqlUpdate.updateBoat("sail_number",boat_id, t.getNewValue());
                }
            }
        );
	
	final TableColumn<Object_Boat, String> col7 = createColumn("PHRF", Object_Boat::phrfProperty);
	col7.setOnEditCommit(
            new EventHandler<CellEditEvent<Object_Boat, String>>() {
                @Override
                public void handle(CellEditEvent<Object_Boat, String> t) {
                    ((Object_Boat) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setPhrf(t.getNewValue());
                    int boat_id = ((Object_Boat) t.getTableView().getItems().get(t.getTablePosition().getRow())).getBoat_id();
                    SqlUpdate.updateBoat("phrf",boat_id, t.getNewValue());
                }
            }
        );
	
	final TableColumn<Object_Boat, String> col8 = createColumn("Length", Object_Boat::lengthProperty);
	col8.setOnEditCommit(
            new EventHandler<CellEditEvent<Object_Boat, String>>() {
                @Override
                public void handle(CellEditEvent<Object_Boat, String> t) {
                    ((Object_Boat) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setLength(t.getNewValue());
                    int boat_id = ((Object_Boat) t.getTableView().getItems().get(t.getTablePosition().getRow())).getBoat_id();
                    SqlUpdate.updateBoat("length",boat_id, t.getNewValue());
                }
            }
        );
	
	final TableColumn<Object_Boat, String> col9 = createColumn("Weight", Object_Boat::weightProperty);
	col9.setOnEditCommit(
            new EventHandler<CellEditEvent<Object_Boat, String>>() {
                @Override
                public void handle(CellEditEvent<Object_Boat, String> t) {
                    ((Object_Boat) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setWeight(t.getNewValue());
                    int boat_id = ((Object_Boat) t.getTableView().getItems().get(t.getTablePosition().getRow())).getBoat_id();
                    SqlUpdate.updateBoat("weight",boat_id, t.getNewValue());
                }
            }
        );
	
	// example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
	final TableColumn<Object_Boat, Boolean> col10 = new TableColumn<Object_Boat, Boolean>("Trailer");
	col10.setCellValueFactory(new Callback<CellDataFeatures<Object_Boat, Boolean>, ObservableValue<Boolean>>() {
        @Override
        public ObservableValue<Boolean> call(CellDataFeatures<Object_Boat, Boolean> param) {
        	Object_Boat boat = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(boat.isHasTrailer());
            // Note: singleCol.setOnEditCommit(): Not work for
            // CheckBoxTableCell.

            // When "has trailer?" column change.
            booleanProp.addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                        Boolean newValue) {
                    boat.setHasTrailer(newValue);
                    SqlUpdate.updateBoat(boat.getBoat_id(), newValue);
                }
            });
            return booleanProp;
        }
    });

	col10.setCellFactory(new Callback<TableColumn<Object_Boat, Boolean>, //
    TableCell<Object_Boat, Boolean>>() {
        @Override
        public TableCell<Object_Boat, Boolean> call(TableColumn<Object_Boat, Boolean> p) {
            CheckBoxTableCell<Object_Boat, Boolean> cell = new CheckBoxTableCell<Object_Boat, Boolean>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        }
    });

	//example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
    ObservableList<KeelType> keelList = FXCollections.observableArrayList(KeelType.values());
	final TableColumn<Object_Boat, KeelType> col11 = new TableColumn<Object_Boat, KeelType>("Keel");
	col11.setCellValueFactory(new Callback<CellDataFeatures<Object_Boat, KeelType>, ObservableValue<KeelType>>() {
		 
        @Override
        public ObservableValue<KeelType> call(CellDataFeatures<Object_Boat, KeelType> param) {
        	Object_Boat boat = param.getValue();
            String keelCode = boat.getKeel();
  
            KeelType keel = KeelType.getByCode(keelCode);
            return new SimpleObjectProperty<KeelType>(keel);
        }
    });

	col11.setCellFactory(ComboBoxTableCell.forTableColumn(keelList));

	col11.setOnEditCommit((CellEditEvent<Object_Boat, KeelType> event) -> {
        TablePosition<Object_Boat, KeelType> pos = event.getTablePosition();
        KeelType newKeel = event.getNewValue();
        int row = pos.getRow();
        Object_Boat boat = event.getTableView().getItems().get(row);
        SqlUpdate.updateBoat(boat.getBoat_id(), newKeel.getCode());
        boat.setKeel(newKeel.getCode());
    });
	
	/// sets width of columns by percentage
	col1.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );  // Boat Name
	col2.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // Manufacturer
	col3.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // Year
	col4.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );  // Model
	col5.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // Registration
	col6.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // Sail #
	col7.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // PHRF
	col8.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // Length
	col9.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // Weight
	col10.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );  // Trailer
	col11.setMaxWidth( 1f * Integer.MAX_VALUE * 15 ); // Keel
	/////////////// LISTENERS ////////////////////
    
    boatAdd.setOnAction((event) -> {
        	int boat_id = SqlSelect.getCount("boat", "boat_id") + 1; // gets last memo_id number and add one
        	boats.add(new Object_Boat(boat_id,membership.getMsid(),"","","","","","",true,"","","","")); // lets add it to our list
			SqlInsert.addRecord(boat_id,membership.getMsid()); // lets add it to our database
    });
    
    boatDelete.setOnAction((event) -> {
        	int selectedIndex = boatTableView.getSelectionModel().getSelectedIndex();
        		if(selectedIndex >= 0) // is a row selected?
        			if(SqlDelete.deleteBoatOwner(boats.get(selectedIndex).getBoat_id(), membership.getMsid())) // if we successfully delete from DB
        				boatTableView.getItems().remove(selectedIndex); // remove from GUI
    });
    
    boatView.setOnAction((event) -> {
    	int selectedIndex = boatTableView.getSelectionModel().getSelectedIndex();
    	Launcher.openBoatViewTab(boats.get(selectedIndex));
    });
    
	boatTableView.setRowFactory(tv -> {
		TableRow<Object_Boat> row = new TableRow<>();
		row.setOnMouseClicked(event -> {
			if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
				System.out.println("We want to do something with " + row.getItem().toString());
			}
		});
		return row;
	});
    
    /////////////////// SET CONTENT ///////////////////

	boatTableView.getColumns().addAll(Arrays.asList(col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11));
	buttonVBox.getChildren().addAll(boatAdd,boatDelete,boatView);
	vboxPink.getChildren().add(boatTableView);
	hboxGrey.getChildren().addAll(vboxPink,buttonVBox);
	getChildren().addAll(hboxGrey);
	}
	
	///////////////// CLASS METHODS /////////////////

    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }
} // CLASS END
