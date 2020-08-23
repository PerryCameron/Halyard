package com.ecsail.gui.boxes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.ecsail.gui.tabs.TabPeopleList;
import com.ecsail.main.SqlUpdate;
import com.ecsail.main.TabLauncher;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Person;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class BoxPerson extends HBox {
	private Object_Person person;
	private Object_MembershipList membership;
	private ObservableList<Object_Person> people;  // this is only for updating people list when in people list mode
	//private ObservableList<Object_Email> email;
	
	public BoxPerson(Object_Person p, Object_MembershipList me) {
		this.person = p;
		this.membership = me;
		
		if(TabLauncher.tabOpen("People List")) {
			this.people = TabPeopleList.people;
		} else {
			this.people = null;
		}
		///////////// OBJECTS /////////////////
		
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxInfoGrey = new VBox();
		HBox hboxTitle = new HBox();
		
		HBox hbox1 = new HBox(); // first name
		HBox hbox2 = new HBox(); // last name
		HBox hbox3 = new HBox(); // Occupation
		HBox hbox4 = new HBox(); // Business
		HBox hbox5 = new HBox(); // Birthday
		HBox hbox6 = new BoxPhone(person); // Phone
		HBox hbox7 = new BoxEmail(person); // Email
		HBox hbox8 = new BoxOfficer(person); // Officer
		HBox hbox9 = new BoxPersonProperties(person, people);

		Label fnameLabel = new Label("First Name");
		Label lnameLabel = new Label("Last Name");
		Label occupationLabel = new Label("Occupation");
		Label businessLabel = new Label("Business");
		Label birthdayLabel = new Label("Birthday");
		TextField fnameTextField = new TextField();
		TextField lnameTextField = new TextField();
		TextField businessTextField = new TextField();
		TextField occupationTextField = new TextField();
		DatePicker birthdayDatePicker = new DatePicker();
		TabPane infoTabPane = new TabPane();
		
		///////////////  ATTRIBUTES ////////////////
		
		infoTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		fnameTextField.setPrefSize(200, 10);
		lnameTextField.setPrefSize(200, 10);
		businessTextField.setPrefSize(200, 10);
		occupationTextField.setPrefSize(200, 10);
		
		hbox1.setAlignment(Pos.CENTER_LEFT);
		hbox2.setAlignment(Pos.CENTER_LEFT);
		hbox3.setAlignment(Pos.CENTER_LEFT);
		hbox4.setAlignment(Pos.CENTER_LEFT);

		hboxTitle.setAlignment(Pos.TOP_RIGHT);
		hboxTitle.setPadding(new Insets(5, 5, 0, 0));
		hbox1.setPadding(new Insets(5, 15, 5, 15));  // first Name
		hbox1.setAlignment(Pos.CENTER_LEFT);
		hbox1.setSpacing(13);

		hbox2.setPadding(new Insets(5, 15, 5, 15));  // last name
		hbox2.setAlignment(Pos.CENTER_LEFT);
		hbox2.setSpacing(14.5);

		hbox3.setPadding(new Insets(5, 15, 5, 15));  // occupation
		hbox3.setAlignment(Pos.CENTER_LEFT);
		hbox3.setSpacing(10);

		hbox4.setPadding(new Insets(5, 15, 5, 15));  // business
		hbox4.setAlignment(Pos.CENTER_LEFT);
		hbox4.setSpacing(25);

		hbox5.setPadding(new Insets(5, 15, 5, 15));
		hbox5.setAlignment(Pos.CENTER_LEFT);
		hbox5.setSpacing(25);

		hbox6.setPadding(new Insets(5, 5, 5, 5));
		hbox6.setAlignment(Pos.CENTER);
		hbox6.setSpacing(5);
		hbox6.setId("box-blue");

		hbox7.setPadding(new Insets(5, 5, 5, 5));
		hbox7.setAlignment(Pos.CENTER);
		hbox7.setSpacing(5);
		hbox7.setId("box-blue");
		
		hbox8.setPadding(new Insets(5, 5, 5, 5));
		hbox8.setAlignment(Pos.CENTER);
		hbox8.setSpacing(5);
		hbox8.setId("box-blue");
		
		hbox9.setPadding(new Insets(5, 5, 5, 5));
		hbox9.setAlignment(Pos.CENTER);
		hbox9.setSpacing(5);
		hbox9.setId("box-blue");
		
		vboxInfoGrey.setPadding(new Insets(25, 5, 5, 5)); // creates space for inner tabpane
		
		setPrefWidth(472);
		setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		
		setId("box-blue");
		vboxGrey.setId("box-grey");
		
		if(person.getBirthday() == null) {
			// do nothing because why not?
		} else {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(person.getBirthday(), formatter);
		birthdayDatePicker.setValue(date);
		}
		
		/////////////// LISTENERS //////////////////////////
		
		fnameTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused

						SqlUpdate.updateFirstName(fnameTextField.getText(),person);
						if(person.getMemberType()==1)  // only update table if this is the primary member
							membership.setFname(fnameTextField.getText());
							if(people != null)  // this updates the people list if in people mode
								people.get(TabPeopleList.getIndexByPid(person.getP_id())).setFname(fnameTextField.getText());
	            }
	        }
	    });

		lnameTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            		SqlUpdate.updateLastName(lnameTextField.getText(),person);
	            		if(person.getMemberType()==1)  // only update table if this is the primary member
	            			membership.setLname(lnameTextField.getText());
	            			if(people != null)  // this updates the people list if in people mode
	            				people.get(TabPeopleList.getIndexByPid(person.getP_id())).setLname(lnameTextField.getText());
	            }
	        }
	    });

		occupationTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            		SqlUpdate.updateOccupation(occupationTextField.getText(),person);
	            		if(people != null)  // this updates the people list if in people mode
	            			people.get(TabPeopleList.getIndexByPid(person.getP_id())).setOccupation(occupationTextField.getText());
	            }
	        }
	    });
		
		businessTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            		SqlUpdate.updateBuisness(businessTextField.getText(), person);
	            		if(people != null)  // this updates the people list if in people mode
	            			people.get(TabPeopleList.getIndexByPid(person.getP_id())).setBuisness(businessTextField.getText());
	            }
	        }
	    });
		
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// get the date picker value
				LocalDate i = birthdayDatePicker.getValue();
					SqlUpdate.updateBirthday(i, person);
			}
		};
		birthdayDatePicker.setOnAction(event);
		
		/////////////// SETTING CONTENT /////////////////////
		
		fnameTextField.setText(person.getFname());
		lnameTextField.setText(person.getLname());
		businessTextField.setText(person.getBuisness());
		occupationTextField.setText(person.getOccupation());
		infoTabPane.getTabs().add(new Tab("Phone", hbox6));
		infoTabPane.getTabs().add(new Tab("Email", hbox7));
		infoTabPane.getTabs().add(new Tab("Officer", hbox8));
		infoTabPane.getTabs().add(new Tab("Properties",hbox9));
		vboxInfoGrey.getChildren().add(infoTabPane);
		hbox1.getChildren().addAll(fnameLabel, fnameTextField);
		hbox2.getChildren().addAll(lnameLabel, lnameTextField);
		hbox3.getChildren().addAll(occupationLabel, occupationTextField);
		hbox4.getChildren().addAll(businessLabel, businessTextField);
		hbox5.getChildren().addAll(birthdayLabel, birthdayDatePicker);
		vboxGrey.getChildren().addAll(hboxTitle, hbox1, hbox2, hbox3, hbox4, hbox5, vboxInfoGrey);
		getChildren().add(vboxGrey);
	} // constructor end
	
}  // class end
