package com.ecsail.gui.dialogues;

import java.util.Collections;

import com.ecsail.main.Main;
import com.ecsail.main.Paths;
import com.ecsail.main.SortByMembershipId;
import com.ecsail.main.SqlExists;
import com.ecsail.main.SqlInsert;
import com.ecsail.main.SqlSelect;
import com.ecsail.structures.Object_MembershipId;
import com.ecsail.structures.Object_MembershipList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class Dialogue_NewYearGenerator extends Stage {
	String nextYear;
	int stage = 1;
	Boolean makeNewId = true;
	public Dialogue_NewYearGenerator() {
		this.nextYear = returnNextRecordYear();
		
		Button generateButton = new Button("Yes");
		Button cancelButton = new Button("Cancel");
		Button keepSameButton = new Button("Keep Old");
		//ToggleGroup tg1 = new ToggleGroup();  
		//RadioButton r1 = new RadioButton("Generate new year records for " + nextYear +"?"); 
        //RadioButton r2 = new RadioButton("Generate information for "); 
		Label generateLable = new Label();
 
		HBox hboxButtons = new HBox();
		HBox hboxGrey = new HBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		VBox vboxColumn1 = new VBox();
		VBox vboxColumn2 = new VBox();
		
		Scene scene = new Scene(vboxBlue, 600, 300);
		final Spinner<Integer> yearSpinner = new Spinner<Integer>();
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));
		
		
		SpinnerValueFactory<Integer> yearValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, 3000, 0);
		yearSpinner.setValueFactory(yearValueFactory);
		yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  yearSpinner.increment(0); // won't change value, but will commit editor
				  nextYear = yearSpinner.getEditor().getText();	
			  }
			});
		
		/////////////////// ATTRIBUTES ///////////////////
		yearSpinner.getValueFactory().setValue(Integer.parseInt(nextYear) + 1);	
		//r1.setToggleGroup(tg1); 
        //r2.setToggleGroup(tg1); 
        //r1.setSelected(true);
        //batchSpinner.setPadding(new Insets(0,0,0,10));
        hboxGrey.setPadding(new Insets(5,0,0,5));
        hboxGrey.setSpacing(5);
        yearSpinner.setPrefWidth(80);
        vboxColumn1.setSpacing(5);
        vboxColumn2.setSpacing(15);
        hboxButtons.setSpacing(5);
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		// vboxGrey.setId("slip-box");
		hboxGrey.setPrefHeight(688);
		scene.getStylesheets().add("stylesheet.css");
		setTitle("Print to PDF");
		////////////  Check to see if batch exists first////////////
		
		
		generateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(stage == 1) {
					//yes we want to generate a new year
					vboxColumn1.getChildren().clear();
					vboxColumn1.getChildren().addAll(new Label("Have you put in the Board Of Directors for " + nextYear + "?"), hboxButtons);
					stage=2;
				} else if (stage == 2) {
					//yes we are happy with all the officers
					vboxColumn1.getChildren().clear();
					hboxButtons.getChildren().clear();
					generateButton.setText("Generate New");
					hboxButtons.getChildren().addAll(generateButton, keepSameButton);
					vboxColumn1.getChildren().addAll(new Label("Would you like to generate new numbers or keep last years?"), hboxButtons);
					stage=3;
				} else if (stage ==3) {
					// we chose to generate new numbers
					generateRecords(true);
				} 
			}
		});
		
		keepSameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// we chose to keep old numbers
				generateRecords(false);
			}
		});
		
		//////////////// DO STUFF /////////////////
		generateLable.setText("Generate new year records for " + nextYear + "?");


		
		//////////////// ADD CONTENT ///////////////////
		
		hboxButtons.getChildren().addAll(generateButton,cancelButton);
		vboxColumn1.getChildren().addAll(generateLable, hboxButtons);
		hboxGrey.getChildren().addAll(vboxColumn1,vboxColumn2);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(hboxGrey);
		getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
	
	/////////////////// CLASS METHODS /////////////////////
	
	
	
	private void generateRecords(Boolean makeNewNumbers) {
		if(makeNewNumbers) {
		createNewNumbers();
		} else {
		createOldNumbers();
		}

	// create money record
		// get next money_id available
	    // add whether they are an officer
		// add last years membership type
		// add last years slip
		// add last years beach spot
		// add last years kayak and inside storage
		// winter storage and work credits will be put in manually
		
	// create work_credit record
		// attach to money record with same money_id 1:1 relation
//}
	}
	
	private void createNewNumbers() {
		int count = 1;
	int mid = SqlSelect.getCount("membership_id", "mid") + 1;
		Collections.sort(Main.activememberships, new SortByMembershipId());
		for (Object_MembershipList ml : Main.activememberships) {
			SqlInsert.addMembershipId(new Object_MembershipId(mid, nextYear, ml.getMsid(), count + ""));
			mid++;
			count++;
		}
	}
	
	private void createOldNumbers() {
		int mid = SqlSelect.getCount("membership_id", "mid") + 1;
		Collections.sort(Main.activememberships, new SortByMembershipId());
		for (Object_MembershipList ml : Main.activememberships) {
			SqlInsert.addMembershipId(new Object_MembershipId(mid, nextYear, ml.getMsid(), ml.getMembershipId() + ""));
			mid++;
		}
	}
	
	
	private String returnNextRecordYear() {
		int nextRecordYear = Integer.parseInt(Paths.getYear());  // start with current year
		Boolean hasRecords = true;
		while(hasRecords) {
			for(Object_MembershipList ml: Main.activememberships) {
				// if there are missing records we will say the year has not been generated
			if(!SqlExists.fiscalRecordExists(ml, nextRecordYear)) hasRecords = false;		
			}
			if(hasRecords) nextRecordYear++;  // if we found all the records then go to next year
		}
		return nextRecordYear + "";
	}
	
	
}
