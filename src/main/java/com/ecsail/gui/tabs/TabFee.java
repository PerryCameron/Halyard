package com.ecsail.gui.tabs;

import com.ecsail.charts.FeesLineChart;
import com.ecsail.datacheck.BigDecimalCheck;
import com.ecsail.main.HalyardPaths;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlFee;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.FeeDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

public class TabFee extends Tab {
	int fieldWidth = 60;
	String selectedYear;
	ArrayList<FeeDTO> feeDTOS;
	int selectedIndex = 0;
	FeesLineChart duesLineChart;
	ToggleGroup radioGroup;
	ToggleGroup textFieldGroup;
	// these tie each control to the feeDTO id
	HashMap<RadioButton,Integer> radioHashMap;
	HashMap<TextField,Integer> textFieldHashMap;
	HashMap<Label, Integer> labelHashMap;
	BigDecimalCheck check = new BigDecimalCheck();

	public TabFee(String text) {
		super(text);
		this.selectedYear = HalyardPaths.getYear();
		this.feeDTOS = SqlFee.getFeesFromYear(selectedYear);
		this.radioGroup = new ToggleGroup();
		this.radioHashMap = new HashMap<>();
		this.textFieldHashMap = new HashMap<>();
		this.textFieldGroup = new ToggleGroup();

		ComboBox comboBox = addComboBox();
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		HBox hboxGrey = new HBox();
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		VBox vboxFeeRow = vboxFeeList();
		Button addButton = new Button("New");
		Button delButton = new Button("Delete");
		Button editButton = new Button(("Edit"));
		HBox hboxControls = new HBox();
		hboxControls.setSpacing(10);
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxGrey.setPadding(new Insets(10,10,10,10));
		vboxPink.setId("box-pink");
		vboxGrey.setPrefHeight(688);
		vboxGrey.setSpacing(15);

		// gives primary key to selected radio button
		radioGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> selectedIndex = radioHashMap.get(new_toggle));
		// add listener to each text field

		addButton.setOnAction((event) -> {
			addNewRow(vboxFeeRow);
				});

		delButton.setOnAction((event) -> {
			System.out.println(selectedIndex);
		});

		editButton.setOnAction((event) -> {
			// do something
		});

		hboxControls.getChildren().addAll(comboBox, addButton,editButton,delButton);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		vboxGrey.getChildren().addAll(hboxControls,vboxFeeRow);
		hboxGrey.getChildren().addAll(vboxGrey);
		vboxPink.getChildren().add(hboxGrey);
		vboxBlue.getChildren().add(vboxPink);
		setContent(vboxBlue);
	}

	private void addNewRow(VBox vboxFeeRow) {
		// get next key
		int key = SqlSelect.getNextAvailablePrimaryKey("fee","FEE_ID") + 1;
		// make DTO object
		FeeDTO feeDTO = new FeeDTO(key,"",new BigDecimal(0),0,Integer.parseInt(selectedYear),"Enter Description");
		// add object to database
		SqlInsert.addNewFee(feeDTO);
		// add new object to our list
		feeDTOS.add(feeDTO);
		// add hbox
		vboxFeeRow.getChildren().add(hboxFeeItems(feeDTO));
	}

	private void addTextListener(TextField t) {
		System.out.println("text listener added for " + t);
		t.focusedProperty().addListener((observable, exitField, enterField) -> {
			// changed textField value and left field
			if(exitField) {
				// checks value entered, saves it to sql, and updates field (as currency)
				saveFieldValue(check, t);
			}

		});
	}

	private void saveFieldValue(BigDecimalCheck check, TextField t) {
		// checks value of text to be valid big decimal, if not sends 0.00
		BigDecimal fieldValue = check.StringToBigDecimal(t.getText());
		// gets correct DTO and updates it
		getDTOByID(textFieldHashMap.get(t)).setFieldValue(fieldValue);
		// gets correct DTO updates SQL
		SqlUpdate.updateFeeRecord(getDTOByID(textFieldHashMap.get(t)));
		// corrects format if missing .00 in gui
		t.setText(String.valueOf(fieldValue.setScale(2, RoundingMode.HALF_UP)));
	}

	// year combo box at top
	private ComboBox addComboBox() {
		ComboBox<Integer> comboBox = new ComboBox<>();
		// creates a combo box with a list of years
		for(int i = Integer.parseInt(HalyardPaths.getYear()) + 1; i > 1969; i--) {
			comboBox.getItems().add(i);
		}
		comboBox.getSelectionModel().select(1);
		return comboBox;
	}

	// used to initially place hbox rows into vbox
	private VBox vboxFeeList() {
		VBox feeBox = new VBox();
		feeBox.setSpacing(5);
		for(FeeDTO fee: feeDTOS) {
			feeBox.getChildren().add(hboxFeeItems(fee));
		}
		return feeBox;
	}

	// this is a row with a radio button, textbox, label
	private HBox hboxFeeItems(FeeDTO feeDTO) {
		HBox feeBox = new HBox();
		feeBox.setSpacing(15);
		feeBox.setAlignment(Pos.CENTER_LEFT);
		feeBox.getChildren().addAll(addRadioButton(feeDTO),addTextField(feeDTO),new Label(feeDTO.getDescription()));
		return feeBox;
	}

	// creates radio button, puts in hashmap
	private RadioButton addRadioButton(FeeDTO feeDTO) {
		RadioButton feeRadioButton = new RadioButton();
		radioHashMap.put(feeRadioButton,feeDTO.getFeeId());
		feeRadioButton.setToggleGroup(radioGroup);
		return feeRadioButton;
	}

	private TextField addTextField(FeeDTO feeDTO) {
		TextField feeTextField = new TextField();
		textFieldHashMap.put(feeTextField,feeDTO.getFeeId());
		feeTextField.setPrefWidth(60);
		feeTextField.setText(String.valueOf(feeDTO.getFieldValue()));
		addTextListener(feeTextField);
		return feeTextField;
	}

	private FeeDTO getDTOByID(int id) {
		for(FeeDTO f: feeDTOS)
			if(f.getFeeId() == id) return f;
			return null;
	}
}
