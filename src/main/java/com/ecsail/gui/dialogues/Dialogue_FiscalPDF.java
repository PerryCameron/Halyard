package com.ecsail.gui.dialogues;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dialogue_FiscalPDF extends Stage {

	public Dialogue_FiscalPDF() {

		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Scene scene = new Scene(vboxBlue, 600, 300);
		final Spinner<Integer> batchSpinner = new Spinner<Integer>();
		Button createPDF = new Button();
		
		SpinnerValueFactory<Integer> batchSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0);
		batchSpinner.setValueFactory(batchSlipValueFactory);
		batchSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  batchSpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(batchSpinner.getEditor().getText());
				  System.out.println(fieldValue);
			  }
			});
		
		/////////////////// ATTRIBUTES ///////////////////
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		// vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		scene.getStylesheets().add("stylesheet.css");
		vboxGrey.getChildren().add(new Label("Stubbed in Window"));
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setTitle("Window Stub");
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));
		
		//////////////// ADD CONTENT ///////////////////
		getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
}
