package com.ecsail.gui.dialogues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dialogue_EnvelopePDF extends Stage {

	
	public Dialogue_EnvelopePDF() {
		
		Button createPDFbutton = new Button("Create Envelope PDF");
		ToggleGroup tg1 = new ToggleGroup();  
		RadioButton r1 = new RadioButton("Print All Envelopes"); 
        RadioButton r2 = new RadioButton("Print one Envelope"); 
        CheckBox c1 = new CheckBox("Detailed Report");
        CheckBox c2 = new CheckBox("Summary"); 
		HBox hboxGrey = new HBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		VBox vboxColumn1 = new VBox();
		VBox vboxColumn2 = new VBox();
		
		Scene scene = new Scene(vboxBlue, 600, 300);
		final Spinner<Integer> batchSpinner = new Spinner<Integer>();
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));
		Image pdf = new Image(getClass().getResourceAsStream("/pdf.png"));
		ImageView pdfImage = new ImageView(pdf);
		
		SpinnerValueFactory<Integer> batchSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0);
		batchSpinner.setValueFactory(batchSlipValueFactory);
		batchSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  batchSpinner.increment(0); // won't change value, but will commit editor
				  int fieldValue = Integer.parseInt(batchSpinner.getEditor().getText());

			  }
			});
		
		/////////////////// ATTRIBUTES ///////////////////
		r1.setToggleGroup(tg1); 
        r2.setToggleGroup(tg1); 
        r2.setSelected(true);
        c1.setSelected(true);
        c2.setSelected(true);
        //batchSpinner.setPadding(new Insets(0,0,0,10));
        hboxGrey.setPadding(new Insets(5,0,0,5));
        batchSpinner.setPrefWidth(60);
        vboxColumn1.setSpacing(5);
        vboxColumn2.setSpacing(15);
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		// vboxGrey.setId("slip-box");
		hboxGrey.setPrefHeight(688);
		scene.getStylesheets().add("stylesheet.css");
		setTitle("Print to PDF");
		////////////  Check to see if batch exists first////////////
		
		
  		/////////////// LISTENERS ///////////////////////
  		
  		
		createPDFbutton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

 			}
		});
		
		//////////////// ADD CONTENT ///////////////////
		vboxColumn1.getChildren().addAll(r1,r2,batchSpinner,c1,c2);
		vboxColumn2.getChildren().addAll(pdfImage,createPDFbutton);
		hboxGrey.getChildren().addAll(vboxColumn1,vboxColumn2);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(hboxGrey);
		getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
}
