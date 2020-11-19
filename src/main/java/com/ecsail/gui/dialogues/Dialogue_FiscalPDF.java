package com.ecsail.gui.dialogues;

import java.io.IOException;

import com.ecsail.pdf.Pdf_TreasurerReport;
import com.ecsail.structures.Object_DefinedFee;
import com.ecsail.structures.Object_Deposit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dialogue_FiscalPDF extends Stage {
	private Object_Deposit currentDeposit;
	private Object_DefinedFee currentDefinedFee;
	public Dialogue_FiscalPDF(Object_Deposit cd, Object_DefinedFee cdf) {
		this.currentDeposit = cd;
		this.currentDefinedFee = cdf;
		
		Button createPDFbutton = new Button("Create PDF");
		ToggleGroup tg1 = new ToggleGroup(); 
		ToggleGroup tg2 = new ToggleGroup(); 
		RadioButton r1 = new RadioButton("Treasurer Report"); 
        RadioButton r2 = new RadioButton("Deposit Report"); 
        RadioButton r3 = new RadioButton("Detailed");
        RadioButton r4 = new RadioButton("Summary"); 
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
				  System.out.println(fieldValue);
			  }
			});
		
		/////////////////// ATTRIBUTES ///////////////////
		r1.setToggleGroup(tg1); 
        r2.setToggleGroup(tg1); 
        r3.setToggleGroup(tg2);
        r4.setToggleGroup(tg2);
        r1.setSelected(true);
        r3.setSelected(true);
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
		
		createPDFbutton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					new Pdf_TreasurerReport(currentDeposit, currentDefinedFee);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// launch sub menu here
				}
			});
		
		
		//////////////// ADD CONTENT ///////////////////
		vboxColumn1.getChildren().addAll(r1,r2,batchSpinner,r3,r4);
		vboxColumn2.getChildren().addAll(pdfImage,createPDFbutton);
		hboxGrey.getChildren().addAll(vboxColumn1,vboxColumn2);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(hboxGrey);
		getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
}
