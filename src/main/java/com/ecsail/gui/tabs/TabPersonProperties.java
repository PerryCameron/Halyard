package com.ecsail.gui.tabs;

import com.ecsail.enums.MemberType;
import com.ecsail.gui.boxes.VBoxPersonMove;
import com.ecsail.gui.dialogues.Dialogue_Delete;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.BooleanDTO;
import com.ecsail.structures.PersonDTO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TabPersonProperties extends Tab {
	private PersonDTO person;  // this is the person we are focused on.
	private ObservableList<PersonDTO> people;  // this is only for updating people list when in people list mode
	private BooleanDTO isDeleted;
	public TabPersonProperties(PersonDTO p, ObservableList<PersonDTO> pe, TabPane personTabPane) {
		super("Properties");
		this.person = p;
		this.people = pe;
		this.isDeleted = new BooleanDTO(false);
		
		//////////// OBJECTS /////////////////
		HBox hboxMain = new HBox();
		VBox vBoxLeft = new VBox(); // holds all content
		VBox vBoxRight = new VBox();
		HBox hboxGrey = new HBox(); // this is here for the grey background to make nice apperence
		HBox hboxMemberType = new HBox();
		HBox hboxDelete = new HBox();
		Button delButton = new Button("Delete");
		CheckBox activeCheckBox = new CheckBox("Is Active");
		ComboBox<MemberType> combo_box = new ComboBox<MemberType>();
		
		/////////////////  ATTRIBUTES  /////////////////////
		
        activeCheckBox.setSelected(person.isActive());
		
        HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		hboxMain.setId("box-blue");
		hboxGrey.setId("box-grey");
		combo_box.setValue(MemberType.getByCode(person.getMemberType()));
		
//		hboxGrey.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
		hboxMain.setPadding(new Insets(5, 5, 5, 5));
		
		hboxMain.setSpacing(5);
		vBoxLeft.setSpacing(5);
		hboxDelete.setSpacing(5);
		hboxMemberType.setSpacing(5);
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		
		hboxMain.setAlignment(Pos.CENTER);
		hboxDelete.setAlignment(Pos.CENTER_LEFT);
		hboxMemberType.setAlignment(Pos.CENTER_LEFT);

		vBoxLeft.setId("box-pink");
		vBoxLeft.setPadding(new Insets(5,5,5,5));
		//////////  LISTENERS /////
		
//		delButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override public void handle(ActionEvent e) {
//            	new Dialogue_Delete(p, isDeleted);
//             }
//          });
         
        activeCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               //activeCheckBox.setSelected(!newValue);
            	SqlUpdate.updatePerson("IS_ACTIVE",person.getP_id(),newValue);
            	if(people != null)  // this updates the people list if in people mode
        			people.get(TabPeople.getIndexByPid(person.getP_id())).setActive(newValue);
            }
        });
	    
	    isDeleted.xBooleanProperty().addListener((obs, wasDeleted, isDeleted) -> {
	    	System.out.println("isDeleted=" + isDeleted);
	    	removeTab(personTabPane);
        	if(people != null)  // this updates the people list if in people mode
    			people.remove(people.get(TabPeople.getIndexByPid(person.getP_id())));
		});
		
		//////////////// SET  CONTENT ////////////////
		combo_box.getItems().setAll(MemberType.values());
		hboxMemberType.getChildren().addAll(new Label("Member Type: "), combo_box);
		hboxDelete.getChildren().addAll(new Label("Delete Person: "), delButton);
		vBoxLeft.getChildren().addAll(
				new Label("Person ID: " + person.getP_id()),
				new Label("MSID: " + person.getMs_id())
//				hboxMemberType

//				hboxDelete,
//				activeCheckBox
				);
		vBoxRight.getChildren().add(new VBoxPersonMove(person));
		hboxGrey.getChildren().addAll(vBoxLeft,vBoxRight);
		hboxMain.getChildren().add(hboxGrey);
		hboxMain.setId("box-blue");
		setContent(hboxMain);
	}
	
	///////////////// CLASS METHODS /////////////////////
	

	private void removeTab(TabPane personTabPane) {
		Tab thisPersonTab = personTabPane.getSelectionModel().getSelectedItem();
		personTabPane.getTabs().remove(thisPersonTab);
	}
}
