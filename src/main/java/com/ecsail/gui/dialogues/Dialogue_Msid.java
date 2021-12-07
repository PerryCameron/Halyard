package com.ecsail.gui.dialogues;

import com.ecsail.main.Launcher;
import com.ecsail.pdf.PDF_Envelope;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Dialogue_Msid extends Stage {

	public Dialogue_Msid() {

		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Scene scene = new Scene(vboxBlue, 600, 300);
		
		/////////////////// ATTRIBUTES ///////////////////
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");

		TextField msidTextField = new TextField();
		Button submitButton = new Button("Submit");
		// vboxGrey.setId("slip-box");
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		scene.getStylesheets().add("stylesheet.css");

		setTitle("Window Stub");
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));


		/////////////// Listener ///////////////////

		submitButton.setOnAction((event) -> {
			Launcher.createMembershipTabFromPeopleList(Integer.parseInt(msidTextField.getText()));
		});
		
		//////////////// ADD CONTENT ///////////////////
		vboxGrey.getChildren().addAll(msidTextField,submitButton);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);

		getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
}
