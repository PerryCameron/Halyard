package com.ecsail.gui.boxes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.ecsail.gui.dialogues.Dialogue_ChoosePicture;
import com.ecsail.gui.tabs.TabPeople;
import com.ecsail.gui.tabs.TabPersonProperties;
import com.ecsail.main.Launcher;
import com.ecsail.main.Paths;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Person;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class BoxPerson extends HBox {
	private Object_Person person;
	private Object_MembershipList membership;
	private ObservableList<Object_Person> people;  // this is only for updating people list when in people list mode
	private ImageView photo;
	//private ObservableList<Object_Email> email;
	
	public BoxPerson(Object_Person p, Object_MembershipList me, TabPane personTabPane) {
		this.person = p;
		this.membership = me;
		
		if(Launcher.tabOpen("People List")) {
			this.people = TabPeople.people;
		} else {
			this.people = null;
		}
		
		this.photo = getMemberPhoto();
		///////////// OBJECTS /////////////////
		
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxInfoGrey = new VBox();
		HBox hboxTitle = new HBox();
		VBox vboxMemberInfo = new VBox();
		VBox vboxPicture = new VBox();
		HBox hboxMemberInfoAndPicture = new HBox();
		HBox hboxPictureFrame = new HBox();
		
		HBox hbox1 = new HBox(); // first name
		HBox hbox2 = new HBox(); // last name
		HBox hbox2b = new HBox();
		HBox hbox3 = new HBox(); // Occupation
		HBox hbox4 = new HBox(); // Business
		HBox hbox5 = new HBox(); // Birthday
		
		VBox vbLnameLabel = new VBox();
		VBox vbFnameLabel = new VBox();
		VBox vbNnameLabel = new VBox();
		VBox vbOccupationLabel = new VBox();
		VBox vbBuisnessLabel = new VBox();
		VBox vbBirthdayLabel = new VBox();
		
		VBox vbLnameBox = new VBox();
		VBox vbFnameBox = new VBox();
		VBox vbNnameBox = new VBox();
		VBox vbOccupationBox = new VBox();
		VBox vbBuisnessBox = new VBox();
		VBox vbBirthdayBox = new VBox();
		
		HBox hbox6 = new BoxPhone(person); // Phone
		HBox hbox7 = new BoxEmail(person); // Email
		HBox hbox8 = new BoxOfficer(person); // Officer
		HBox hbox9 = new BoxAward(person);

		Label fnameLabel = new Label("First Name");
		Label lnameLabel = new Label("Last Name");
		Label nnameLabel = new Label("Nickname");
		Label occupationLabel = new Label("Occupation");
		Label businessLabel = new Label("Business");
		Label birthdayLabel = new Label("Birthday");
		TextField fnameTextField = new TextField();
		TextField lnameTextField = new TextField();
		TextField nnameTextField = new TextField();
		TextField businessTextField = new TextField();
		TextField occupationTextField = new TextField();
		DatePicker birthdayDatePicker = new DatePicker();
		TabPane infoTabPane = new TabPane();
		
		///////////////  ATTRIBUTES ////////////////
		
		//vboxPicture.setStyle("-fx-background-color: #201ac9;");  // blue
		infoTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		fnameTextField.setPrefSize(150, 10);
		lnameTextField.setPrefSize(150, 10);
		businessTextField.setPrefSize(150, 10);
		occupationTextField.setPrefSize(150, 10);
		nnameTextField.setPrefSize(150, 10);
		birthdayDatePicker.setPrefSize(150, 10);
		
		hbox1.setAlignment(Pos.CENTER_LEFT);
		hbox2.setAlignment(Pos.CENTER_LEFT);
		hbox2b.setAlignment(Pos.CENTER_LEFT);
		hbox3.setAlignment(Pos.CENTER_LEFT);
		hbox4.setAlignment(Pos.CENTER_LEFT);

		hboxTitle.setAlignment(Pos.TOP_RIGHT);
		hboxTitle.setPadding(new Insets(9, 5, 0, 0));
		hbox1.setPadding(new Insets(7, 5, 5, 15));  // first Name
		hbox2.setPadding(new Insets(7, 5, 5, 15));  // last name
		hbox2b.setPadding(new Insets(7, 5, 5, 15)); // Nickname
		hbox3.setPadding(new Insets(7, 5, 5, 15));  // occupation
		hbox4.setPadding(new Insets(7, 5, 5, 15));  // business
		hbox5.setPadding(new Insets(7, 5, 5, 15));
		hboxPictureFrame.setPadding(new Insets(2, 2, 2, 2));
		vboxPicture.setPadding(new Insets(12, 5, 0, 7));

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
		
		vbLnameLabel.setPrefWidth(75);
		vbFnameLabel.setPrefWidth(75);
		vbNnameLabel.setPrefWidth(75);
		vbOccupationLabel.setPrefWidth(75);
		vbBuisnessLabel.setPrefWidth(75);
		vbBirthdayLabel.setPrefWidth(75);
		hboxPictureFrame.setPrefSize(196, 226);
		
		vbLnameLabel.setAlignment(Pos.CENTER_LEFT);
		vbFnameLabel.setAlignment(Pos.CENTER_LEFT);
		vbNnameLabel.setAlignment(Pos.CENTER_LEFT);
		vbOccupationLabel.setAlignment(Pos.CENTER_LEFT);
		vbBuisnessLabel.setAlignment(Pos.CENTER_LEFT);
		vbBirthdayLabel.setAlignment(Pos.CENTER_LEFT);
		
		
		vboxInfoGrey.setPadding(new Insets(10, 5, 5, 5)); // creates space for inner tabpane
		
		setPrefWidth(472);
		setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		
		hboxPictureFrame.setId("box-blue");
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

		
		fnameTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused

						SqlUpdate.updateFirstName(fnameTextField.getText(),person);
						if(person.getMemberType()==1)  // only update table if this is the primary member
							membership.setFname(fnameTextField.getText());
							if(people != null)  // this updates the people list if in people mode
								people.get(TabPeople.getIndexByPid(person.getP_id())).setFname(fnameTextField.getText());
	            }});

		lnameTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            		SqlUpdate.updateLastName(lnameTextField.getText(),person);
	            		if(person.getMemberType()==1)  // only update table if this is the primary member
	            			membership.setLname(lnameTextField.getText());
	            			if(people != null)  // this updates the people list if in people mode
	            				people.get(TabPeople.getIndexByPid(person.getP_id())).setLname(lnameTextField.getText());
	            }
	    });

		occupationTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            		SqlUpdate.updateOccupation(occupationTextField.getText(),person);
	            		if(people != null)  // this updates the people list if in people mode
	            			people.get(TabPeople.getIndexByPid(person.getP_id())).setOccupation(occupationTextField.getText());
	            }
	    });
		
		businessTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            		SqlUpdate.updateBuisness(businessTextField.getText(), person);
	            		if(people != null)  // this updates the people list if in people mode
	            			people.get(TabPeople.getIndexByPid(person.getP_id())).setBuisness(businessTextField.getText());
	            }
	    });
		
		nnameTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
            		SqlUpdate.updateNickName(nnameTextField.getText(), person);
            		if(people != null)  // this updates the people list if in people mode
            			people.get(TabPeople.getIndexByPid(person.getP_id())).setBuisness(businessTextField.getText());
            }
		});
		
		photo.setOnMouseClicked((e) -> {
			if (e.getClickCount() == 1) {
				new Dialogue_ChoosePicture();
			}
		});
		
		photo.setOnMouseExited(ex -> {
			hboxPictureFrame.setStyle("-fx-background-color: #9fc0c7;");
		});
		
		photo.setOnMouseEntered(en -> {
			hboxPictureFrame.setStyle("-fx-background-color: #201ac9;");
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
		
		vboxMemberInfo.getChildren().addAll(hboxTitle, hbox1, hbox2, hbox2b, hbox3, hbox4, hbox5);
		hboxMemberInfoAndPicture.getChildren().addAll(vboxMemberInfo,vboxPicture);
		hboxPictureFrame.getChildren().add(photo);
		vboxPicture.getChildren().add(hboxPictureFrame);
		
		fnameTextField.setText(person.getFname());
		lnameTextField.setText(person.getLname());
		businessTextField.setText(person.getBuisness());
		occupationTextField.setText(person.getOccupation());
		nnameTextField.setText(person.getNname());
		infoTabPane.getTabs().add(new Tab("Phone", hbox6));
		infoTabPane.getTabs().add(new Tab("Email", hbox7));
		infoTabPane.getTabs().add(new Tab("Officer", hbox8));
		infoTabPane.getTabs().add(new TabPersonProperties(p, people, personTabPane));
		infoTabPane.getTabs().add(new Tab("Awards", hbox9));
		vboxInfoGrey.getChildren().add(infoTabPane);
		
		vbLnameLabel.getChildren().add(lnameLabel);
		vbFnameLabel.getChildren().add(fnameLabel);
		vbNnameLabel.getChildren().add(nnameLabel);
		vbOccupationLabel.getChildren().add(occupationLabel);
		vbBuisnessLabel.getChildren().add(businessLabel);
		vbBirthdayLabel.getChildren().add(birthdayLabel);
		
		vbLnameBox.getChildren().add(lnameTextField);
		vbFnameBox.getChildren().add(fnameTextField);
		vbNnameBox.getChildren().add(nnameTextField);
		vbOccupationBox.getChildren().add(occupationTextField);
		vbBuisnessBox.getChildren().add(businessTextField);
		vbBirthdayBox.getChildren().add(birthdayDatePicker);
		
		hbox1.getChildren().addAll(vbFnameLabel,vbFnameBox);
		hbox2.getChildren().addAll(vbLnameLabel,vbLnameBox);
		hbox2b.getChildren().addAll(vbNnameLabel, vbNnameBox);
		hbox3.getChildren().addAll(vbOccupationLabel,vbOccupationBox);
		hbox4.getChildren().addAll(vbBuisnessLabel,vbBuisnessBox);
		hbox5.getChildren().addAll(vbBirthdayLabel,vbBirthdayBox);
		vboxGrey.getChildren().addAll(hboxMemberInfoAndPicture, vboxInfoGrey);
		getChildren().add(vboxGrey);
	} // constructor end
	
	
	private ImageView getMemberPhoto() {
		Image memberPhoto = new Image(getClass().getResourceAsStream(Paths.DEFAULTPHOTO));
		ImageView photo = new ImageView(memberPhoto);
		return photo;
	}
	
}  // class end
