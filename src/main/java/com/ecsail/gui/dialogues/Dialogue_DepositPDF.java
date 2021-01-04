package com.ecsail.gui.dialogues;

import com.ecsail.main.SqlExists;
import com.ecsail.main.SqlSelect;
import com.ecsail.pdf.PDF_DepositReport;
import com.ecsail.structures.Object_DefinedFee;
import com.ecsail.structures.Object_Deposit;
import com.ecsail.structures.Object_DepositPDF;

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

public class Dialogue_DepositPDF extends Stage {
	private Object_Deposit currentDeposit;
	private Object_DefinedFee currentDefinedFee;
	private Object_DepositPDF pdfOptions;
	String selectedYear;
	
	public Dialogue_DepositPDF(Object_Deposit cd, Object_DefinedFee cdf, String y) {
		this.currentDeposit = cd;
		this.currentDefinedFee = cdf;
		this.pdfOptions = new Object_DepositPDF();
		this.selectedYear = y;
		
		Button createPDFbutton = new Button("Create PDF");
		ToggleGroup tg1 = new ToggleGroup();  
		RadioButton r1 = new RadioButton("Print All Deposits"); 
        RadioButton r2 = new RadioButton("Print Only Deposit Number"); 
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
				  		if(SqlExists.ifDepositRecordExists(currentDeposit.getFiscalYear(), fieldValue))  // deposit exists
				  		currentDeposit.setBatch(fieldValue);
				  		else
				  		currentDeposit.setBatch(1);	
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
		
  		if(currentDeposit == null) { // deposit does not exist
  		currentDeposit = SqlSelect.getDeposit(selectedYear, 1);
		batchSpinner.getValueFactory().setValue(1);
  		} else {
  		batchSpinner.getValueFactory().setValue(currentDeposit.getBatch());	
  		}
		
  		/////////////// LISTENERS ///////////////////////
  		
  		
		createPDFbutton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(c1.isSelected()) pdfOptions.setIncludesDetailedReport(true);
				if(!c1.isSelected()) pdfOptions.setIncludesDetailedReport(false);
				if(c2.isSelected()) pdfOptions.setIncludesSummaryReport(true);
				if(!c2.isSelected()) pdfOptions.setIncludesSummaryReport(false);
				if(r2.isSelected()) pdfOptions.setSingleDeposit(true);
				if(!r2.isSelected()) pdfOptions.setSingleDeposit(false);
				pdfOptions.setDepositNumber(currentDeposit.getBatch());
				new PDF_DepositReport(currentDeposit, currentDefinedFee, pdfOptions);  // makes the PDF
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
