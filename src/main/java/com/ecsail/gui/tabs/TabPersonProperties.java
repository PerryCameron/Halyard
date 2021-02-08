package com.ecsail.gui.tabs;

import com.ecsail.enums.MemberType;
import com.ecsail.gui.dialogues.Dialogue_Delete;
import com.ecsail.main.SqlExists;
import com.ecsail.main.SqlSelect;
import com.ecsail.main.SqlUpdate;
import com.ecsail.structures.Object_Boolean;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TabPersonProperties extends Tab {
	private Object_Person person;  // this is the person we are focused on.
	private ObservableList<Object_Person> people;  // this is only for updating people list when in people list mode
	private Object_Boolean isDeleted;
	public TabPersonProperties(Object_Person p, ObservableList<Object_Person> pe, TabPane personTabPane) {
		super("Properties");
		this.person = p;
		this.people = pe;
		this.isDeleted = new Object_Boolean(false);
		//////////// OBJECTS /////////////////
		HBox hboxMain = new HBox();
		VBox vbox1 = new VBox(); // holds all content
		//HBox hbox1 = new HBox(); // holds remove member features
		HBox hboxGrey = new HBox(); // this is here for the grey background to make nice apperence
		HBox hboxMemberType = new HBox();
		HBox hboxDelete = new HBox();
		Button delButton = new Button("Delete");
		CheckBox activeCheckBox = new CheckBox("Is Active");
		ComboBox<MemberType> combo_box = new ComboBox<MemberType>();
		
		//////////  LISTENERS /////
		
		delButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	new Dialogue_Delete(p, isDeleted);
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
        
	    combo_box.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
	    	
	    	int ms_id = person.getMs_id();
	    	int person1Pid = person.getP_id();
	    	int person2Pid = 0;

	    	if(newValue.toString().equals("Primary")) {  // we want to change member to primary
	    		person2Pid = getPid(1); // gets pid of current primary member
	    		if(person2Pid != 0) {
	    		    SqlUpdate.updatePerson(person2Pid, "MEMBER_TYPE", 2); // change primary to secondary
	    		}
	    		SqlUpdate.updatePerson(person1Pid, "MEMBER_TYPE", 1);  // now make this person primary
	    		SqlUpdate.updateMembershipPrimary(ms_id, person1Pid);  // update the membership primary
	    	}
	    	if(newValue.toString().equals("Secondary")) {  // we want to change member to secondary
	    		person2Pid = getPid(2); // gets pid of current secondary member
	    		if(person2Pid != 0) {
	    		    SqlUpdate.updatePerson(person2Pid, "MEMBER_TYPE", 1); // change secondary to primary
	    		}
	    		SqlUpdate.updatePerson(person1Pid, "MEMBER_TYPE", 2);  // now make this person secondary
	    		SqlUpdate.updateMembershipPrimary(ms_id, person2Pid);  // update the membership primary
	    	}
	    	if(newValue.toString().equals("Dependant")) {  // not sure what to do probably dialogue saying no!
	    		System.out.println("Do something with this later");
	    	}
	    	
        });
	    
	    isDeleted.xBooleanProperty().addListener((obs, wasDeleted, isDeleted) -> {
	    	System.out.println("isDeleted=" + isDeleted);
	    	removeTab(personTabPane);
        	if(people != null)  // this updates the people list if in people mode
    			people.remove(people.get(TabPeopleList.getIndexByPid(person.getP_id())));
		});
		
		/////////////////  ATTRIBUTES  /////////////////////
		hboxMain.setPadding(new Insets(5, 5, 5, 5));
		hboxMain.setAlignment(Pos.CENTER);
		hboxMain.setSpacing(5);
		hboxMain.setId("box-blue");
        activeCheckBox.setSelected(person.isActive());
		hboxGrey.setPrefWidth(480);
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		//hbox1.setSpacing(5);
		//hbox1.setAlignment(Pos.CENTER_LEFT);
		vbox1.setSpacing(5);
		hboxGrey.setId("box-grey");
		combo_box.setValue(MemberType.getByCode(person.getMemberType()));
		hboxGrey.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
		//hbox1.setPadding(new Insets(25, 0, 0, 15));
		hboxDelete.setSpacing(5);
		hboxDelete.setAlignment(Pos.CENTER_LEFT);
		hboxMemberType.setSpacing(5);
		hboxMemberType.setAlignment(Pos.CENTER_LEFT);
		
		//////////////// SET  CONTENT ////////////////
		combo_box.getItems().setAll(MemberType.values());
		hboxMemberType.getChildren().addAll(new Label("Member Type: "), combo_box);
		hboxDelete.getChildren().addAll(new Label("Delete Person: "), delButton);
		vbox1.getChildren().addAll(
				new Label("Person ID: " + person.getP_id()),
				new Label("MSID: " + person.getMs_id()),
				hboxMemberType,
				hboxDelete,
				activeCheckBox
				);
		hboxGrey.getChildren().add(vbox1);
		hboxMain.getChildren().add(hboxGrey);
		hboxMain.setId("box-blue");
		setContent(hboxMain);
	}
	
	///////////////// CLASS METHODS /////////////////////
	
	private int getPid(int type) {
		int pid = 0;
		ObservableList<Object_Person> members = SqlSelect.getPeople(person.getMs_id());
    	for(Object_Person per: members) {
    		if(per.getMemberType() == type) // here is our current primary
    			pid = per.getP_id();
    	}
		return pid;
	}
	
	private void removeTab(TabPane personTabPane) {
		String tabName = "";
		Tab thisPersonTab = null;
		if(person.getMemberType() == 1) tabName = "Primary Member";
		if(person.getMemberType() == 2) tabName = "Secondary";
		if(person.getMemberType() == 3) tabName = "Dependent";
		for(Tab t: personTabPane.getTabs()) {
			if(t.getText().equals(tabName)) 
				thisPersonTab = t;
		}
		personTabPane.getTabs().remove(thisPersonTab);
	}
}
