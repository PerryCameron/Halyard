package com.ecsail.gui.boxes;

import com.ecsail.enums.MemberType;
import com.ecsail.gui.dialogues.Dialogue_Delete;
import com.ecsail.gui.tabs.TabPeopleList;
import com.ecsail.main.SqlDelete;
import com.ecsail.main.SqlUpdate;
import com.ecsail.structures.Object_Person;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoxPersonProperties extends HBox {
	private Object_Person person;
	private ObservableList<Object_Person> people;  // this is only for updating people list when in people list mode
	public BoxPersonProperties(Object_Person p, ObservableList<Object_Person> pe) {
		super();
		this.person = p;
		this.people = pe;
		//////////// OBJECTS /////////////////
		
		VBox vbox1 = new VBox(); // holds all content
		HBox hbox1 = new HBox(); // holds remove member features
		HBox hboxGrey = new HBox(); // this is here for the grey background to make nice apperence
		Button delButton = new Button("Delete");
		CheckBox activeCheckBox = new CheckBox("Active");

		//////////  LISTENERS /////
		
      delButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	new Dialogue_Delete(p);
            	//if(deleting)
	  			//SqlDelete.deletePerson(p);
             }
          });
         
        activeCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               //activeCheckBox.setSelected(!newValue);
            	SqlUpdate.updatePerson("IS_ACTIVE",person.getP_id(),newValue);
            	if(people != null)  // this updates the people list if in people mode
        			people.get(TabPeopleList.getIndexByPid(person.getP_id())).setActive(newValue);
            }
        });

		/////////////////  ATTRIBUTES  /////////////////////
        activeCheckBox.setSelected(person.isActive());
		hboxGrey.setPrefWidth(480);
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		hbox1.setSpacing(5);
		hbox1.setAlignment(Pos.CENTER_LEFT);
		vbox1.setSpacing(5);
		hboxGrey.setId("box-grey");

		hboxGrey.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
		hbox1.setPadding(new Insets(25, 0, 0, 15));
		
		//////////////// SET  CONTENT ////////////////
		hbox1.getChildren().add(delButton);
		vbox1.getChildren().addAll(new Label("Person ID: " + person.getP_id()), 
				new Label("Member Type: " + MemberType.getByCode(person.getMemberType())), // writes type instead of integer
				activeCheckBox,
				new Label("MSID: " + person.getMs_id()));
		vbox1.getChildren().add(hbox1);
		hboxGrey.getChildren().add(vbox1);
		getChildren().add(hboxGrey);
		setId("box-blue");
	}
}
