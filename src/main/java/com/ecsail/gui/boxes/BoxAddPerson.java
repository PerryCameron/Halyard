package com.ecsail.gui.boxes;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import com.ecsail.enums.MemberType;
import com.ecsail.main.Note;
import com.ecsail.main.SqlExists;
import com.ecsail.main.SqlInsert;
import com.ecsail.main.SqlSelect;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Person;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class BoxAddPerson extends VBox {
	
	int ms_id;
	private Label titleLabel;
	private TabPane peopleTabPane; // a reference so we can open up the newly created pane
	private Note note;
	private Object_MembershipList membership;
	private Object_Person person;
	private final int PRIMARY = 1;
	private final int SECONDARY = 2;
	private final int DEPENDANT = 3;
	private Boolean hasError = false;
	
	public BoxAddPerson(TabPane tp, Note n, Object_MembershipList me) {
		this.note = n;
		this.ms_id = n.getMsid();
		this.peopleTabPane = tp;
		this.membership = me;
		
		////// OBJECTS //////
		VBox vboxGrey = new VBox();
		Button addButton = new Button("Add");
		Button populateButton = new Button("Populate");
		this.titleLabel = new Label("Add New Member");
		Label fnameLabel = new Label("First Name");
		Label lnameLabel = new Label("Last Name");
		Label occupationLabel = new Label("Occupation");
		Label businessLabel = new Label("Business");
		Label birthdayLabel = new Label("Birthday");
		Label addFromPidLabel = new Label("Populate fields from existing PID");
		Label memberTypeLabel = new Label("Member Type");
		TextField populateTextField = new TextField();
		TextField fnameTextField = new TextField();
		TextField lnameTextField = new TextField();
		TextField businessTextField = new TextField();
		TextField occupationTextField = new TextField();
		DatePicker birthdayDatePicker = new DatePicker();
		final ComboBox<MemberType> memberType = new ComboBox<MemberType>();
		HBox hboxTitle = new HBox(); // Title
		HBox hbox1 = new HBox(); // first name
		HBox hbox2 = new HBox(); // last name
		HBox hbox3 = new HBox(); // Occupation
		HBox hbox4 = new HBox(); // Business
		HBox hbox5 = new HBox(); // Birthday
		HBox hbox6 = new HBox(); // Member Type
		HBox hbox7 = new HBox(); // Add Button
		HBox hbox8 = new HBox(); // Populate Title
		HBox hbox9 = new HBox(); // populate
		
		///////////////  ATTRIBUTES ////////////////
		setPrefWidth(460);
		fnameTextField.setPrefSize(240, 10);
		lnameTextField.setPrefSize(240, 10);
		businessTextField.setPrefSize(240, 10);
		occupationTextField.setPrefSize(240, 10);
		populateTextField.setPrefWidth(45);
		populateTextField.setPrefHeight(25);
		memberType.getItems().setAll(MemberType.values());
		memberType.setValue(MemberType.getByCode(1)); // sets to primary
		addButton.setPrefWidth(80);
		populateButton.setPrefWidth(80);

		hboxTitle.setPadding(new Insets(5, 15, 5, 15));  // first Name
		hboxTitle.setAlignment(Pos.CENTER);
		hboxTitle.setSpacing(13);
		// Insets(double top, double right, double bottom, double left)
		hbox1.setPadding(new Insets(5, 15, 5, 60));  // first Name
		hbox1.setAlignment(Pos.CENTER_LEFT);
		hbox1.setSpacing(13);

		hbox2.setPadding(new Insets(5, 15, 5, 60));  // last name
		hbox2.setAlignment(Pos.CENTER_LEFT);
		hbox2.setSpacing(14.5);

		hbox3.setPadding(new Insets(5, 15, 5, 60));  // occupation
		hbox3.setAlignment(Pos.CENTER_LEFT);
		hbox3.setSpacing(10);

		hbox4.setPadding(new Insets(5, 15, 5, 60));  // business
		hbox4.setAlignment(Pos.CENTER_LEFT);
		hbox4.setSpacing(25);

		hbox5.setPadding(new Insets(5, 15, 5, 60));  // birthday
		hbox5.setAlignment(Pos.CENTER_LEFT);
		hbox5.setSpacing(25);
		
		hbox6.setPadding(new Insets(5, 15, 5, 60));  // member type
		hbox6.setAlignment(Pos.CENTER_LEFT);
		hbox6.setSpacing(25);
		
		hbox7.setPadding(new Insets(5, 100, 5, 5));  // add button
		hbox7.setAlignment(Pos.CENTER_RIGHT);
		hbox7.setSpacing(25);
		
		hbox8.setPadding(new Insets(60, 5, 5, 5));  // Populate title
		hbox8.setAlignment(Pos.CENTER);

		hbox9.setPadding(new Insets(20, 15, 5, 5));  // populate field and button
		hbox9.setAlignment(Pos.CENTER);
		hbox9.setSpacing(15);
		
		setPadding(new Insets(5, 5, 5, 5));
		setId("box-blue");
		vboxGrey.setId("box-grey");
		
		/////////////////  LISTENERS  /////////////////////
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	int pid = SqlSelect.getCount("person","p_id") + 1;
            	person = new Object_Person(pid, ms_id, memberType.getValue().getCode(), fnameTextField.getText(), 
            			lnameTextField.getText(), getBirthday(birthdayDatePicker.getValue()), 
            			occupationTextField.getText(), businessTextField.getText(), true);

					// if adding member succeeds, clear the form
					if(!setNewMember(person)) {
						String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
						note.add("New person: " + fnameTextField.getText() + " " + lnameTextField.getText() + " - (PID " + pid + ") added as " + memberType.getValue().toString() + ".",date,0,"N");
						fnameTextField.setText("");
						lnameTextField.setText("");
						businessTextField.setText("");
						occupationTextField.setText("");
						birthdayDatePicker.setValue(null);
						memberType.setValue(MemberType.getByCode(1)); // sets to primary
					}
            }
        });
		
		hboxTitle.getChildren().addAll(titleLabel);
		hbox1.getChildren().addAll(fnameLabel, fnameTextField);
		hbox2.getChildren().addAll(lnameLabel, lnameTextField);
		hbox3.getChildren().addAll(occupationLabel, occupationTextField);
		hbox4.getChildren().addAll(businessLabel, businessTextField);
		hbox5.getChildren().addAll(birthdayLabel, birthdayDatePicker);
		hbox6.getChildren().addAll(memberTypeLabel,memberType);
		hbox7.getChildren().addAll(addButton);
		hbox8.getChildren().addAll(addFromPidLabel);
		hbox9.getChildren().addAll(populateTextField,populateButton);
		vboxGrey.getChildren().addAll(hboxTitle, hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7, hbox8, hbox9);
		getChildren().add(vboxGrey);
	}
	
	private String getBirthday(LocalDate birthday) {
		String date;
		if(birthday == null) {  /// we don't have to have a date
			date = null;
		} else {
			date = birthday.toString();
		}
		return date;
	}
	
	private Boolean setNewMember(Object_Person person) {  // gives the last memo_id number
		String memberStringType = getMemberType(person,hasError);
		checkName(person.getFname());
		checkName(person.getLname());
		if(!hasError) {
			addPerson(memberStringType);
		}
		return hasError;
	}
	
	private void checkName(String name) {
		if(name.equals("")) {
			hasError = true;
			printErrorMessage("Must have a name");
		}
	}
	
	private String getMemberType(Object_Person person, Boolean hasError) {
		String memberType = null;
		switch(person.getMemberType()) 
        { 
            case PRIMARY: 
            	memberType = "Primary";
            	if(SqlExists.personExists(person.getP_id(),PRIMARY,ms_id))
            		printErrorMessage("A primary member already exists for this account");
                break; 
            case SECONDARY: 
            	memberType = "Secondary";
            	if(SqlExists.personExists(person.getP_id(),SECONDARY,ms_id)) {
            		printErrorMessage("A secondary member already exists for this account");
            	}
                break; 
            case DEPENDANT: 
            	memberType = "Dependant";
                break; 
            default: 
                System.out.println("no match"); 
        } 
		return memberType;
	}
	
	private void printErrorMessage(String message) {
		titleLabel.setText(message);
		titleLabel.setTextFill(Color.RED);
		hasError = true;
	}
	
	private void addPerson(String memberType) {
		SqlInsert.addRecord(person);
		peopleTabPane.getTabs().add(new Tab(memberType, new BoxPerson(person, membership)));
    	titleLabel.setText("Add New Member");
		titleLabel.setTextFill(Color.BLACK);
	}
	

}
