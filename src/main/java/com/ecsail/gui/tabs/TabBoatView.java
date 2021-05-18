package com.ecsail.gui.tabs;

import com.ecsail.sql.Sql_SelectMembership;
import com.ecsail.structures.Object_Boat;
import com.ecsail.structures.Object_MembershipList;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class TabBoatView extends Tab {
	private ObservableList<Object_MembershipList> boatOwners;
	/// need to add history to boat_owner table
	
	
	public TabBoatView(String text, Object_Boat b) {
		super(text);
		this.boatOwners = Sql_SelectMembership.getBoatOwners(b.getBoat_id());
		
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		VBox vboxFieldsContainer = new VBox();
		
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
		TextField keelTextField = new TextField();
		///////////////  ATTRIBUTES ////////////////
		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		//vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		
		bnameTextField.setPrefSize(150, 10);
		manufacturerTextField.setPrefSize(150, 10);
		yearTextField.setPrefSize(150, 10);
		modelTextField.setPrefSize(150, 10);
		registrationTextField.setPrefSize(150, 10);
		sailNumberTextField.setPrefSize(150, 10);
		phrfTextField.setPrefSize(150, 10);
		lengthTextField.setPrefSize(150, 10);
		weightTextField.setPrefSize(150, 10);
		keelTextField.setPrefSize(150, 10);
		
		bnameTextField.setText(b.getBoat_name());
		manufacturerTextField.setText(b.getManufacturer());
		yearTextField.setText(b.getManufacture_year());
		modelTextField.setText(b.getModel());
		registrationTextField.setText(b.getRegistration_num());
		sailNumberTextField.setText(b.getSail_number());
		phrfTextField.setText(b.getPhrf());
		lengthTextField.setText(b.getLength());
		weightTextField.setText(b.getWeight());
		keelTextField.setText(b.getKeel());
		
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
		
		/////////////// SET CONTENT //////////////////
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
		vboxkeelBox.getChildren().add(keelTextField);
		
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

		vboxFieldsContainer.getChildren().addAll(hbox1,hbox2,hbox3,hbox4,hbox5,hbox6,hbox7,hbox8,hbox9,hbox10,hbox11);
		vboxGrey.getChildren().add(vboxFieldsContainer);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}
}
