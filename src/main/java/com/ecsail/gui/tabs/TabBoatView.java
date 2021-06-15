package com.ecsail.gui.tabs;

import java.util.Arrays;

import com.ecsail.enums.KeelType;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.Sql_SelectMembership;
import com.ecsail.structures.Object_Boat;
import com.ecsail.structures.Object_MembershipList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class TabBoatView extends Tab {
	private ObservableList<Object_MembershipList> boatOwners;
	/// need to add history to boat_owner table
	
	public TabBoatView(String text, Object_Boat b) {
		super(text);
		//this.boatOwners = Sql_SelectMembership.getBoatOwners(b.getBoat_id());
		this.boatOwners = Sql_SelectMembership.getBoatOwnerRoster(b.getBoat_id());
		TableView<Object_MembershipList> boatOwnerTableView = new TableView<>();
		
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		VBox vboxFieldsContainer = new VBox();
		VBox vboxButtons = new VBox(); // holds phone buttons
		
		HBox hbox1 = new HBox(); // first name
		HBox hbox2 = new HBox(); // last name
		HBox hbox3 = new HBox(); // Occupation
		HBox hbox4 = new HBox(); // Business
		HBox hbox5 = new HBox(); // Birthday
		HBox hbox6 = new HBox(); // first name
		HBox hbox7 = new HBox(); // last name
		HBox hbox8 = new HBox(); // Occupation
		HBox hbox9 = new HBox(); // Business
		HBox hbox10 = new HBox(); // Birthday
		HBox hbox11 = new HBox(); // Birthday
		Region spacer = new Region();
		HBox hboxTable = new HBox();
		
		VBox vboxBnameLabel = new VBox();
		VBox vboxManufacturerLabel = new VBox();
		VBox vboxYearLabel = new VBox();
		VBox vboxModelLabel = new VBox();
		VBox vboxRegistrationLabel = new VBox();
		VBox vboxSailNumbeLabel = new VBox();
		VBox vboxphrfLabel = new VBox();
		VBox vboxlengthLabel = new VBox();
		VBox vboxweightLabel = new VBox();
		VBox vboxtrailerLabel = new VBox();
		VBox vboxkeelLabel = new VBox();
		
		VBox vboxBnameBox = new VBox();
		VBox vboxManufacturerBox = new VBox();
		VBox vboxYearBox = new VBox();
		VBox vboxModelBox = new VBox();
		VBox vboxRegistrationBox = new VBox();
		VBox vboxSailNumbeBox = new VBox();
		VBox vboxphrfBox = new VBox();
		VBox vboxlengthBox = new VBox();
		VBox vboxweightBox = new VBox();
		VBox vboxtrailerBox = new VBox();
		VBox vboxkeelBox = new VBox();
		
		TextField bnameTextField = new TextField();
		TextField manufacturerTextField = new TextField();
		TextField yearTextField = new TextField();
		TextField modelTextField = new TextField();
		TextField registrationTextField = new TextField();
		TextField sailNumberTextField = new TextField();
		TextField phrfTextField = new TextField();
		TextField lengthTextField = new TextField();
		TextField weightTextField = new TextField();
		CheckBox  trailerCheckBox = new CheckBox("Has Trailer");
		ComboBox<KeelType>keelComboBox = new ComboBox<KeelType>();
		
		TableColumn<Object_MembershipList, Integer> col1 = new TableColumn<Object_MembershipList, Integer>("MEM");
		TableColumn<Object_MembershipList, String> col2 = new TableColumn<Object_MembershipList, String>("Last Name");
		TableColumn<Object_MembershipList, String> col3 = new TableColumn<Object_MembershipList, String>("First Name");
		Button boatOwnerAdd = new Button("Add");
		Button boatOwnerDelete = new Button("Delete");

		///////////////  ATTRIBUTES ////////////////
		boatOwnerAdd.setPrefWidth(60);
		boatOwnerDelete.setPrefWidth(60);
		vboxButtons.setPrefWidth(80);
		vboxButtons.setSpacing(5); // spacing between buttons
		
		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		//vboxGrey.setId("slip-box");
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		HBox.setHgrow(vboxGrey, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(boatOwnerTableView, Priority.ALWAYS);
		
		spacer.setPrefHeight(50);
		
		bnameTextField.setPrefSize(150, 10);
		manufacturerTextField.setPrefSize(150, 10);
		yearTextField.setPrefSize(150, 10);
		modelTextField.setPrefSize(150, 10);
		registrationTextField.setPrefSize(150, 10);
		sailNumberTextField.setPrefSize(150, 10);
		phrfTextField.setPrefSize(150, 10);
		lengthTextField.setPrefSize(150, 10);
		weightTextField.setPrefSize(150, 10);
		keelComboBox.setPrefSize(150, 10);
		
		bnameTextField.setText(b.getBoat_name());
		manufacturerTextField.setText(b.getManufacturer());
		yearTextField.setText(b.getManufacture_year());
		modelTextField.setText(b.getModel());
		registrationTextField.setText(b.getRegistration_num());
		sailNumberTextField.setText(b.getSail_number());
		phrfTextField.setText(b.getPhrf());
		lengthTextField.setText(b.getLength());
		weightTextField.setText(b.getWeight());
		trailerCheckBox.setSelected(b.isHasTrailer());
		keelComboBox.getItems().setAll(KeelType.values());
		keelComboBox.setValue(KeelType.getByCode(b.getKeel()));
		
		vboxBnameLabel.setPrefWidth(90);
		vboxManufacturerLabel.setPrefWidth(90);
		vboxYearLabel.setPrefWidth(90);
		vboxModelLabel.setPrefWidth(90);
		vboxRegistrationLabel.setPrefWidth(90);
		vboxSailNumbeLabel.setPrefWidth(90);
		vboxphrfLabel.setPrefWidth(90);
		vboxlengthLabel.setPrefWidth(90);
		vboxweightLabel.setPrefWidth(90);
		vboxtrailerLabel.setPrefWidth(90);
		vboxkeelLabel.setPrefWidth(90);
		
		vboxBnameLabel.setAlignment(Pos.CENTER_LEFT);
		vboxManufacturerLabel.setAlignment(Pos.CENTER_LEFT);
		vboxYearLabel.setAlignment(Pos.CENTER_LEFT);
		vboxModelLabel.setAlignment(Pos.CENTER_LEFT);
		vboxRegistrationLabel.setAlignment(Pos.CENTER_LEFT);
		vboxSailNumbeLabel.setAlignment(Pos.CENTER_LEFT);
		vboxphrfLabel.setAlignment(Pos.CENTER_LEFT);
		vboxlengthLabel.setAlignment(Pos.CENTER_LEFT);
		vboxweightLabel.setAlignment(Pos.CENTER_LEFT);
		vboxtrailerLabel.setAlignment(Pos.CENTER_LEFT);
		vboxkeelLabel.setAlignment(Pos.CENTER_LEFT);
		
		hbox1.setPadding(new Insets(10, 5, 5, 15));
		hbox2.setPadding(new Insets(0, 5, 5, 15));
		hbox3.setPadding(new Insets(0, 5, 5, 15));
		hbox4.setPadding(new Insets(0, 5, 5, 15)); 
		hbox5.setPadding(new Insets(0, 5, 5, 15));
		hbox6.setPadding(new Insets(0, 5, 5, 15));
		hbox7.setPadding(new Insets(0, 5, 5, 15));
		hbox8.setPadding(new Insets(0, 5, 5, 15));
		hbox9.setPadding(new Insets(0, 5, 5, 15)); 
		hbox10.setPadding(new Insets(0, 5, 5, 15));
		hbox11.setPadding(new Insets(0, 5, 5, 15));
		
		boatOwnerTableView.setItems(boatOwners);
		boatOwnerTableView.setFixedCellSize(30);
		boatOwnerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		
		col1.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, Integer>("membershipId"));
		col2.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("lname"));
		col3.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("fname"));
		
		/// sets width of columns by percentage
		col1.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );   // Mem 5%
		col2.setMaxWidth( 1f * Integer.MAX_VALUE * 45 );  // Join Date 15%
		col3.setMaxWidth( 1f * Integer.MAX_VALUE * 45 );   // Type

		
		/////////////// LISTENERS ////////////////////
		
		boatOwnerAdd.setOnAction((event) -> {
			//	int phone_id = SqlSelect.getCount("phone", "phone_id"); // get last phone_id number
			//	phone_id++; // increase to first available number
			//	if (SqlInsert.addRecord(phone_id, person.getP_id(), true, "new phone", "")) // if added with no errors
			//		phone.add(new Object_Phone(phone_id, person.getP_id(), true, "new phone", "")); // lets add it to our GUI
			boatOwners.add(new Object_MembershipList());
			});
        
        boatOwnerDelete.setOnAction((event) -> {
            //	int selectedIndex = phoneTableView.getSelectionModel().getSelectedIndex();
            //		if(selectedIndex >= 0)
            //			if(SqlDelete.deletePhone(phone.get(selectedIndex)))  // if it is properly deleted in our database
            //				phoneTableView.getItems().remove(selectedIndex); // remove it from our GUI
        });
		
		bnameTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
            		//SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
            	SqlUpdate.updateBoat("BOAT_NAME", b.getBoat_id(), bnameTextField.getText());
            }
        });
		
		manufacturerTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
            		//SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
            	SqlUpdate.updateBoat("MANUFACTURER", b.getBoat_id(), manufacturerTextField.getText());
            }
        });
		
		yearTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
            		//SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
            	SqlUpdate.updateBoat("MANUFACTURE_YEAR", b.getBoat_id(), yearTextField.getText());
            }
        });

		modelTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
            		//SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
            	SqlUpdate.updateBoat("MODEL", b.getBoat_id(), modelTextField.getText());
            }
        });
		
		registrationTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
            		//SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
            	SqlUpdate.updateBoat("REGISTRATION_NUM", b.getBoat_id(), registrationTextField.getText());
            }
        });
		
		sailNumberTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
            		//SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
            	SqlUpdate.updateBoat("SAIL_NUMBER", b.getBoat_id(), sailNumberTextField.getText());
            }
        });
		
		phrfTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
            		//SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
            	SqlUpdate.updateBoat("PHRF", b.getBoat_id(), phrfTextField.getText());
            }
        });
		
		lengthTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
            		//SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
            	SqlUpdate.updateBoat("LENGTH", b.getBoat_id(), lengthTextField.getText());
            }
        });
		
		weightTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
            		//SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
            	SqlUpdate.updateBoat("WEIGHT", b.getBoat_id(), weightTextField.getText());
            }
        });
		
		trailerCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
					SqlUpdate.updateBoat(b.getBoat_id(), isNowSelected);
			}
		});
		
		keelComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			SqlUpdate.updateBoat(b.getBoat_id(), newValue.getCode());
			System.out.println("changed combo to " + newValue.getCode());
        }); 
		
		/////////////// SET CONTENT //////////////////
		boatOwnerTableView.getColumns()
		.addAll(Arrays.asList(col1, col2, col3));
		vboxButtons.getChildren().addAll(boatOwnerAdd,boatOwnerDelete);
		hboxTable.getChildren().addAll(boatOwnerTableView,vboxButtons);
		
		vboxBnameLabel.getChildren().add(new Label("Boat Name"));
		vboxManufacturerLabel.getChildren().add(new Label("Manufacturer"));
		vboxYearLabel.getChildren().add(new Label("Year"));
		vboxModelLabel.getChildren().add(new Label("Model"));
		vboxRegistrationLabel.getChildren().add(new Label("Registration"));
		vboxSailNumbeLabel.getChildren().add(new Label("Sail Number"));
		vboxphrfLabel.getChildren().add(new Label("PHRF"));
		vboxlengthLabel.getChildren().add(new Label("Length"));
		vboxweightLabel.getChildren().add(new Label("Weight"));
		vboxtrailerLabel.getChildren().add(new Label("Has Trailer"));
		vboxkeelLabel.getChildren().add(new Label("Keel Type"));
		
		vboxBnameBox.getChildren().add(bnameTextField);
		vboxManufacturerBox.getChildren().add(manufacturerTextField);
		vboxYearBox.getChildren().add(yearTextField);
		vboxModelBox.getChildren().add(modelTextField);
		vboxRegistrationBox.getChildren().add(registrationTextField);
		vboxSailNumbeBox.getChildren().add(sailNumberTextField);
		vboxphrfBox.getChildren().add(phrfTextField);
		vboxlengthBox.getChildren().add(lengthTextField);
		vboxweightBox.getChildren().add(weightTextField);
		vboxtrailerBox.getChildren().add(trailerCheckBox);
		vboxkeelBox.getChildren().add(keelComboBox);
		
		hbox1.getChildren().addAll(vboxBnameLabel,vboxBnameBox); // first name
		hbox2.getChildren().addAll(vboxManufacturerLabel,vboxManufacturerBox);
		hbox3.getChildren().addAll(vboxYearLabel,vboxYearBox);
		hbox4.getChildren().addAll(vboxModelLabel,vboxModelBox);
		hbox5.getChildren().addAll(vboxRegistrationLabel,vboxRegistrationBox);
		hbox6.getChildren().addAll(vboxSailNumbeLabel,vboxSailNumbeBox);
		hbox7.getChildren().addAll(vboxphrfLabel,vboxphrfBox);
		hbox8.getChildren().addAll(vboxlengthLabel,vboxlengthBox);
		hbox9.getChildren().addAll(vboxweightLabel,vboxweightBox);
		hbox10.getChildren().addAll(vboxtrailerLabel,vboxtrailerBox);
		hbox11.getChildren().addAll(vboxkeelLabel,vboxkeelBox);

		vboxFieldsContainer.getChildren().addAll(hbox1,hbox2,hbox3,hbox4,hbox5,hbox6,hbox7,hbox8,hbox9,hbox10,hbox11,spacer,hboxTable);
		vboxGrey.getChildren().add(vboxFieldsContainer);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}
}
