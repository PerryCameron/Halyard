package com.ecsail.gui.tabs;

import com.ecsail.structures.Object_Integer;
import com.ecsail.structures.Object_Money;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TabKey extends Tab {
	//private final String disabledColor = "-fx-background-color: #d5dade";
	Object_Money selectedFiscalYear;
	private final Spinner<Integer> extraKeySpinner = new Spinner<Integer>();
	private final Spinner<Integer> sailLKeySpinner = new Spinner<Integer>();
	private final Spinner<Integer> kayakSKeySpinner = new Spinner<Integer>();
	private final Spinner<Integer> sailSSLKeySpinner = new Spinner<Integer>();
	Object_Integer numberOfKeys;
	
	public TabKey(String text, Object_Money sm, Object_Integer wc) {
		super(text);
		this.selectedFiscalYear = sm;
		this.numberOfKeys = wc;
		

		
		VBox vboxTextFieldFrame = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxTextField = new VBox();
		VBox vboxBlue = new VBox();
		HBox hboxGrey = new HBox();
		HBox hboxKey = new HBox();
		HBox hboxSLKey = new HBox();
		HBox hboxKSKey = new HBox();
		HBox hboxSSLKey = new HBox();

		////////////////ATTRIBUTES ///////////////////
		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(5,5,5,5));
		hboxGrey.setPadding(new Insets(5,0,5,5)); // spacing to make pink from around table
		hboxGrey.setId("box-grey");
		vboxTextFieldFrame.setPrefWidth(240);
		vboxTextField.setSpacing(5);
		vboxTextFieldFrame.setId("box-pink");
		vboxTextField.setId("box-lightgrey");
		vboxTextField.setPrefHeight(139);
		vboxTextFieldFrame.setPadding(new Insets(3,3,3,3));
		vboxTextField.setPadding(new Insets(12,0,7,10));
		extraKeySpinner.setPrefWidth(60);
		sailLKeySpinner.setPrefWidth(60);
		kayakSKeySpinner.setPrefWidth(60);
		sailSSLKeySpinner.setPrefWidth(60);

		hboxKey.setSpacing(58.5);
		hboxSLKey.setSpacing(42.5);
		hboxKSKey.setSpacing(24);
		hboxSSLKey.setSpacing(4);
		
		//////////////// LISTENERS ///////////////////
		
		SpinnerValueFactory<Integer> extraKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedFiscalYear.getExtra_key());
		extraKeySpinner.setValueFactory(extraKeyValueFactory);
		extraKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			selectedFiscalYear.setExtra_key(newValue);
			countKeys();
			});

		SpinnerValueFactory<Integer> sailLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedFiscalYear.getSail_loft_key());
		sailLKeySpinner.setValueFactory(sailLKeyValueFactory);
		sailLKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			selectedFiscalYear.setSail_loft_key(newValue);
			countKeys();
			});
		
		SpinnerValueFactory<Integer> kayakSKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedFiscalYear.getKayac_shed_key());
		kayakSKeySpinner.setValueFactory(kayakSKeyValueFactory);
		kayakSKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			selectedFiscalYear.setKayac_shed_key(newValue);
			countKeys();
			});
		
		SpinnerValueFactory<Integer> sailSSLKeyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedFiscalYear.getSail_school_loft_key());
		sailSSLKeySpinner.setValueFactory(sailSSLKeyValueFactory);
		sailSSLKeySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			selectedFiscalYear.setSail_school_loft_key(newValue);
			countKeys();
			});

		////////////////SETTING CONTENT //////////////
		
		hboxKey.getChildren().addAll(new Label("Gate Key"),extraKeySpinner);
		hboxSLKey.getChildren().addAll(new Label("Sail Loft Key"),sailLKeySpinner);
		hboxKSKey.getChildren().addAll(new Label("Kayak Shed Key"),kayakSKeySpinner);
		hboxSSLKey.getChildren().addAll(new Label("Sail School Loft Key"),sailSSLKeySpinner);
		vboxTextField.getChildren().addAll(hboxKey, hboxSLKey,hboxKSKey,hboxSSLKey);
		vboxTextFieldFrame.getChildren().add(vboxTextField);
		vboxBlue.getChildren().add(hboxGrey);
		hboxGrey.getChildren().addAll(vboxTextFieldFrame);
		setContent(vboxBlue);
	}
	
	private void countKeys() {
		numberOfKeys.setInteger(selectedFiscalYear.getExtra_key() + selectedFiscalYear.getSail_loft_key()
				+ selectedFiscalYear.getKayac_shed_key() + selectedFiscalYear.getSail_school_loft_key());
	}
}
