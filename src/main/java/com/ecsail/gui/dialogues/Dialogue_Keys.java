package com.ecsail.gui.dialogues;

import com.ecsail.structures.Object_Money;
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

public class Dialogue_Keys extends Stage {
	
		private final Spinner<Integer> extraKeySpinner = new Spinner<Integer>();
		private final Spinner<Integer> sailLKeySpinner = new Spinner<Integer>();
		private final Spinner<Integer> kayakSKeySpinner = new Spinner<Integer>();
		private final Spinner<Integer> sailSSLKeySpinner = new Spinner<Integer>();
		private final TextField totalKeyLocalTextField = new TextField();
		private final TextField totalKeyTextField;
		Stage keystage = this;
		Object_Money selectedFiscalYear;
		
		private final String disabledColor = "-fx-background-color: #d5dade";
	public Dialogue_Keys(Object_Money sm, TextField tk) {
		this.selectedFiscalYear = sm;
		this.totalKeyTextField = tk;
		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Scene scene = new Scene(vboxBlue, 225, 240);
		HBox hboxKey = new HBox();
		HBox hboxSLKey = new HBox();
		HBox hboxKSKey = new HBox();
		HBox hboxSSLKey = new HBox();
		HBox hboxTotalKeys = new HBox();
		HBox hboxButton = new HBox();
		Button cancelButton = new Button("Cancel");
		Button saveButton = new Button("Save");
		/////////////////// ATTRIBUTES ///////////////////
		keystage.setAlwaysOnTop(true);
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
		setTitle("Keys");
		extraKeySpinner.setPrefWidth(60);
		sailLKeySpinner.setPrefWidth(60);
		kayakSKeySpinner.setPrefWidth(60);
		sailSSLKeySpinner.setPrefWidth(60);
		totalKeyLocalTextField.setPrefWidth(60);
		totalKeyLocalTextField.setStyle(disabledColor);
		totalKeyLocalTextField.setEditable(false);
		hboxKey.setSpacing(58.5);
		hboxSLKey.setSpacing(42.5);
		hboxKSKey.setSpacing(24);
		hboxSSLKey.setSpacing(4);
		hboxTotalKeys.setSpacing(36.5);
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));

		
		///////////// LISTENERS /////////////////////
		
		SpinnerValueFactory<Integer> extraKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedFiscalYear.getExtra_key());
		extraKeySpinner.setValueFactory(extraKeyValueFactory);
		extraKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			selectedFiscalYear.setExtra_key(newValue);
			totalKeyLocalTextField.setText(countKeys() + "");
			});

		SpinnerValueFactory<Integer> sailLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedFiscalYear.getSail_loft_key());
		sailLKeySpinner.setValueFactory(sailLKeyValueFactory);
		sailLKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			selectedFiscalYear.setSail_loft_key(newValue);
			totalKeyLocalTextField.setText(countKeys() + "");
			});
		
		SpinnerValueFactory<Integer> kayakSKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedFiscalYear.getKayac_shed_key());
		kayakSKeySpinner.setValueFactory(kayakSKeyValueFactory);
		kayakSKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			selectedFiscalYear.setKayac_shed_key(newValue);
			totalKeyLocalTextField.setText(countKeys() + "");
			});
		
		SpinnerValueFactory<Integer> sailSSLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedFiscalYear.getSail_school_loft_key());
		sailSSLKeySpinner.setValueFactory(sailSSLKeyValueFactory);
		sailSSLKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			selectedFiscalYear.setSail_school_loft_key(newValue);
			totalKeyLocalTextField.setText(countKeys() + "");
			});
		
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	keystage.close();
            }
        });
        
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	totalKeyTextField.setText(countKeys() + "");
            	//SqlUpdate.updateWorkCredit(selectedWorkCreditYear);
            	keystage.close();
            }
        });
		
		//////////////// ADD CONTENT ///////////////////
		
        totalKeyLocalTextField.setText(countKeys() + "");
		scene.getStylesheets().add("stylesheet.css");
		hboxButton.getChildren().addAll(saveButton, cancelButton);
		hboxKey.getChildren().addAll(new Label("Gate Key"),extraKeySpinner);
		hboxSLKey.getChildren().addAll(new Label("Sail Loft Key"),sailLKeySpinner);
		hboxKSKey.getChildren().addAll(new Label("Kayak Shed Key"),kayakSKeySpinner);
		hboxSSLKey.getChildren().addAll(new Label("Sail School Loft Key"),sailSSLKeySpinner);
		hboxTotalKeys.getChildren().addAll(new Label("Total:"),totalKeyLocalTextField);
		vboxGrey.getChildren().addAll(hboxKey, hboxSLKey,hboxKSKey,hboxSSLKey,hboxTotalKeys,hboxButton);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
	
	/////////////////// CLASS METHODS //////////////////////////
	
	private int countKeys() {
		int total = selectedFiscalYear.getExtra_key() + selectedFiscalYear.getSail_loft_key()
				+ selectedFiscalYear.getKayac_shed_key() + selectedFiscalYear.getSail_school_loft_key();
		return total;
	}
}
