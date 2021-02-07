package com.ecsail.gui.dialogues;

import com.ecsail.main.SqlDelete;
import com.ecsail.structures.Object_Person;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dialogue_Delete extends Stage {

	
	public Dialogue_Delete(Object_Person p) {

		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		HBox hboxButtons = new HBox();
		Scene scene = new Scene(vboxBlue, 320, 150);
		Button delButton = new Button("Delete");
		Button cancelButton = new Button("Cancel");
		String message = "Are you sure you want to delete "+ p.getFname() + " " + p.getLname() + "?";
		/////////////////// ATTRIBUTES ///////////////////
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxGrey.setPadding(new Insets(20,0,7,10));
		hboxButtons.setPadding(new Insets(0,0,0,65));
		vboxGrey.setSpacing(15);
		hboxButtons.setSpacing(10);
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		// vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		scene.getStylesheets().add("stylesheet.css");
		setScene(scene);
		setTitle("Delete member");
		/////////////////// LISTENERS //////////////////////
		
	      delButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					SqlDelete.deletePerson(p);
					closeDialogue();
	             }
	          });
	      
	      cancelButton.setOnAction(new EventHandler<ActionEvent>() {
	          @Override public void handle(ActionEvent e) {
	        	  	closeDialogue();
	          }
	      });

		//////////////// ADD CONTENT ///////////////////
	    hboxButtons.getChildren().addAll(delButton,cancelButton);
		vboxGrey.getChildren().addAll(new Label(message),hboxButtons);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));
		getIcons().add(mainIcon);	
		show();
	}
	
	private void closeDialogue() {
		this.close();
	}
}
