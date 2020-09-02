package com.ecsail.gui.dialogues;

import com.ecsail.main.SqlUpdate;
import com.ecsail.structures.Object_WorkCredit;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dialogue_WorkCredits extends Stage {
	
		private final Spinner<Integer> racingSpinner = new Spinner<Integer>();
		private final Spinner<Integer> harborSpinner = new Spinner<Integer>();
		private final Spinner<Integer> socialSpinner = new Spinner<Integer>();
		private final Spinner<Integer> otherSpinner = new Spinner<Integer>();
		private final TextField totalWCText = new TextField();
		Stage wcstage = this;
		Object_WorkCredit selectedWorkCreditYear;
		TextField totalWorkCreditsTextField;
		private final String disabledColor = "-fx-background-color: #d5dade";
	public Dialogue_WorkCredits(Object_WorkCredit swc, TextField twc) {
		this.selectedWorkCreditYear = swc;
		this.totalWorkCreditsTextField = twc;
		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Scene scene = new Scene(vboxBlue, 220, 240);
		HBox hboxRacing = new HBox();
		HBox hboxHarbor = new HBox();
		HBox hboxSocial = new HBox();
		HBox hboxOther = new HBox();
		HBox hboxtotalWC = new HBox();
		HBox hboxButton = new HBox();
		Button cancelButton = new Button("Cancel");
		Button saveButton = new Button("Save");
		/////////////////// ATTRIBUTES ///////////////////
		wcstage.setAlwaysOnTop(true);
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		// vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		vboxGrey.setSpacing(5);
		hboxButton.setSpacing(5);
		vboxGrey.setPadding(new Insets(10,5,5,25));
		hboxButton.setPadding(new Insets(10,0,0,30));
		setTitle("Credits");
		racingSpinner.setPrefWidth(60);
		harborSpinner.setPrefWidth(60);
		socialSpinner.setPrefWidth(60);
		otherSpinner.setPrefWidth(60);
		totalWCText.setPrefWidth(60);
		totalWCText.setStyle(disabledColor);
		totalWCText.setEditable(false);
		hboxRacing.setSpacing(30);
		hboxHarbor.setSpacing(28);
		hboxSocial.setSpacing(35);
		hboxOther.setSpacing(35);
		hboxtotalWC.setSpacing(36.5);
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));
		
		///////////// LISTENERS /////////////////////
		
		SpinnerValueFactory<Integer> racingValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedWorkCreditYear.getRacing());
		racingSpinner.setValueFactory(racingValueFactory);
		racingSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			//	  SqlUpdate.updateWorkCredit(fieldValue,"racing",selectedWorkCreditYear);
				  selectedWorkCreditYear.setRacing(newValue);
				  totalWCText.setText(countWorkCredits() + "");
			});
		
		SpinnerValueFactory<Integer> harborValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedWorkCreditYear.getHarbor());
		harborSpinner.setValueFactory(harborValueFactory);
		harborSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			//	  SqlUpdate.updateWorkCredit(fieldValue,"harbor",selectedWorkCreditYear);
				  selectedWorkCreditYear.setHarbor(newValue);
				  totalWCText.setText(countWorkCredits() + "");
			});
		
		SpinnerValueFactory<Integer> socialValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedWorkCreditYear.getSocial());
		socialSpinner.setValueFactory(socialValueFactory);
		socialSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			//	  SqlUpdate.updateWorkCredit(fieldValue,"social",selectedWorkCreditYear);
				  selectedWorkCreditYear.setSocial(newValue);
				  totalWCText.setText(countWorkCredits() + "");
			});
		
		SpinnerValueFactory<Integer> otherValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedWorkCreditYear.getOther());
		otherSpinner.setValueFactory(otherValueFactory);
		otherSpinner.valueProperty().addListener((obs, oldValue, newValue) ->  {
			//	  SqlUpdate.updateWorkCredit(fieldValue,"other",selectedWorkCreditYear);
				  selectedWorkCreditYear.setOther(newValue);
				  totalWCText.setText(countWorkCredits() + "");  /// total work credit

		});
		
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	wcstage.close();
            }
        });
        
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	totalWorkCreditsTextField.setText(countWorkCredits() + "");
            	SqlUpdate.updateWorkCredit(selectedWorkCreditYear);
            	wcstage.close();
            }
        });
		
		//////////////// ADD CONTENT ///////////////////
		
		totalWCText.setText(countWorkCredits() + "");
		scene.getStylesheets().add("stylesheet.css");
		hboxButton.getChildren().addAll(saveButton, cancelButton);
		hboxRacing.getChildren().addAll(new Label("Racing"),racingSpinner);
		hboxHarbor.getChildren().addAll(new Label("Harbor"),harborSpinner);
		hboxSocial.getChildren().addAll(new Label("Social"),socialSpinner);
		hboxOther.getChildren().addAll(new Label("Other"),otherSpinner);
		hboxtotalWC.getChildren().addAll(new Label("Total:"),totalWCText);
		vboxGrey.getChildren().addAll(hboxRacing, hboxHarbor,hboxSocial,hboxOther,hboxtotalWC,hboxButton);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
	
	/////////////////// CLASS METHODS //////////////////////////
	
	private int countWorkCredits() {
		int total = selectedWorkCreditYear.getRacing() + selectedWorkCreditYear.getSocial()
				+ selectedWorkCreditYear.getOther()+ selectedWorkCreditYear.getHarbor();
		//if(total >= 15) total = 15;  // we allow no more than 15 wc per year and no carry over.
		return total;
	}
}
