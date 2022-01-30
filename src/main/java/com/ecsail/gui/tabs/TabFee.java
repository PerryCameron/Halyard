package com.ecsail.gui.tabs;

import com.ecsail.charts.FeesLineChart;
import com.ecsail.datacheck.NumberCheck;
import com.ecsail.datacheck.StringCheck;
import com.ecsail.main.HalyardPaths;
import com.ecsail.sql.SqlDelete;
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
import java.util.*;

public class TabFee extends Tab {
	String selectedYear;
	ArrayList<FeeDTO> feeDTOS;
	// current index selected from feeDTOS
	int selectedKey = 0;
	FeesLineChart duesLineChart;
	ToggleGroup radioGroup;

    // use radio button to get id
	HashMap<RadioButton, Integer> radioHashMap;
	HashMap<TextField, Integer> textFieldHashMap;
	// use id to get controls
	HashMap<Integer, Label> labelHashMap;
	HashMap<Integer, HBox> hboxHashMap;
	NumberCheck numberCheck = new NumberCheck();
	StringCheck stringCheck = new StringCheck();

	public TabFee(String text) {
		super(text);
		this.selectedYear = HalyardPaths.getYear();
		this.feeDTOS = SqlFee.getFeesFromYear(selectedYear);
		this.radioGroup = new ToggleGroup();
		this.radioHashMap = new HashMap<>();
		this.textFieldHashMap = new HashMap<>();
		this.labelHashMap = new HashMap<>();
		this.hboxHashMap = new HashMap<>();


		ComboBox comboBox = addComboBox();
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		HBox hboxGrey = new HBox();
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		VBox vboxFeeRow = createControlsVBox();
		Button addButton = new Button("New");
		Button delButton = new Button("Delete");
		Button editButton = new Button(("Edit"));
		HBox hboxControls = new HBox();

		////////////////////// ADD PROPERTIES TO OBJECTS //////////////
		hboxControls.setSpacing(10);
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxGrey.setPadding(new Insets(10,10,10,10));
		vboxPink.setId("box-pink");
		vboxGrey.setPrefHeight(688);
		vboxGrey.setSpacing(15);

		//////////////// LISTENERS ///////////////////
		// gives primary key to selected radio button
		radioGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> selectedKey = radioHashMap.get(new_toggle));
		// add listener to each text field

		addButton.setOnAction((event) -> {
			addNewRow(vboxFeeRow);
				});

		delButton.setOnAction((event) -> {
			deleteRowIn(vboxFeeRow);
		});

		editButton.setOnAction((event) -> {
			openEditRow(vboxFeeRow);
		});

		//////////// SETTING CONTENT /////////////
		// if we already have entries set buttons add, edit, delete
		if(feeDTOS.size() > 0)
		hboxControls.getChildren().addAll(comboBox, addButton,editButton,delButton);
		// if we donn't have entries set buttons add, copy fees
		else
		hboxControls.getChildren().addAll(comboBox, copyFees());


		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		vboxGrey.getChildren().addAll(hboxControls,vboxFeeRow);
		hboxGrey.getChildren().addAll(vboxGrey);
		vboxPink.getChildren().add(hboxGrey);
		vboxBlue.getChildren().add(vboxPink);
		setContent(vboxBlue);
	}

	private Button copyFees() {
		Button copyFeesBtn = new Button("Copy Fees");
		addCopyFeesButtonListener(copyFeesBtn);
		return copyFeesBtn;
	}

	private void addCopyFeesButtonListener(Button copyFeesBtn) {
		copyFeesBtn.setOnAction((event) -> {
			System.out.println("Here we go");
		});
	}

	private void openEditRow(VBox vboxFeeRow) {
		if(selectedKey == 0) System.out.println("You need to select an index first");
		else {
			createEditHBox(hboxHashMap.get(selectedKey));
		}
	}

	// java fx controls for editing, no business logic
	private void createEditHBox(HBox hbox) {
		hbox.getChildren().clear();
		Button saveButton = new Button("Save");
		Label description = new Label("Description:");
		Label fieldName = new Label("Field Name");
		TextField descriptionText = new TextField(getDTOByID(selectedKey).getDescription());
		TextField fieldNameText = new TextField(getDTOByID(selectedKey).getFieldName());
		VBox vboxEditBox = new VBox();
		HBox hboxRow1 = new HBox();
		HBox hboxRow2 = new HBox();
		vboxEditBox.setSpacing(5);
		hboxRow1.setSpacing(5);
		hboxRow2.setSpacing(5);
		hboxRow1.getChildren().addAll(descriptionText, description);
		hboxRow2.getChildren().addAll(fieldNameText, fieldName);
		vboxEditBox.getChildren().addAll(hboxRow1,hboxRow2,saveButton);
		hbox.getChildren().add(vboxEditBox);
		addButtonListener(saveButton,fieldNameText,descriptionText);
	}

	private void addButtonListener(Button saveButton, TextField fieldNameText, TextField descriptionText) {
		saveButton.setOnAction((event) -> {
			// update selected object
			getDTOByID(selectedKey).setDescription(descriptionText.getText());
			getDTOByID(selectedKey).setFieldName(stringCheck.checkForProperSQLTableName(fieldNameText.getText()));
			// write object to sql
			SqlUpdate.updateFeeRecord(getDTOByID(selectedKey));
			// clear hbox
			hboxHashMap.get(selectedKey).getChildren().clear();
			// update contents of hbox
			hboxFeeItems(getDTOByID(selectedKey), hboxHashMap.get(selectedKey));
		});
	}

	private void deleteRowIn(VBox vboxFeeRow) {
		if(selectedKey == 0) System.out.println("You need to select an index first");
		else {
			// remove from database
			SqlDelete.deleteFee(feeDTOS.get(getListIndexByKey(selectedKey)));
			// remove from list
			feeDTOS.remove(getListIndexByKey(selectedKey));
			// clear HBoxes from column
			vboxFeeRow.getChildren().remove(hboxHashMap.get(selectedKey));
		}
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
		vboxFeeRow.getChildren().add(hboxFeeItems(feeDTO, new HBox()));
//		vboxFeeRow.getChildren().add(hboxFeeItems(createEditHBox(new HBox())));

	}

	private void addTextListener(TextField t) {
		System.out.println("text listener added for " + t);
		t.focusedProperty().addListener((observable, exitField, enterField) -> {
			// changed textField value and left field
			if(exitField) {
				// checks value entered, saves it to sql, and updates field (as currency)
				saveFieldValue(numberCheck, t);
			}
		});
	}

	private void saveFieldValue(NumberCheck check, TextField t) {
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

	// create our controls vbox and add hbox rows to it
	private VBox createControlsVBox() {
		VBox feeBox = new VBox();
		feeBox.setSpacing(5);
		addHBoxRows(feeBox);
		return feeBox;
	}

	// used to initially place hbox rows into vbox
	private void addHBoxRows(VBox feeBox) {
		for(FeeDTO fee: feeDTOS) {
			feeBox.getChildren().add(hboxFeeItems(fee, new HBox()));
		}
	}

	// this is a row with a radio button, textbox, label
	private HBox hboxFeeItems(FeeDTO feeDTO, HBox feeBox) {
		feeBox.setSpacing(15);
		feeBox.setAlignment(Pos.CENTER_LEFT);
		hboxHashMap.put(feeDTO.getFeeId(),feeBox);
		feeBox.getChildren().addAll(addRadioButton(feeDTO),addTextField(feeDTO),createLabel(feeDTO));
		return feeBox;
	}

	private Label createLabel(FeeDTO feeDTO) {
		Label descriptionLabel = new Label(feeDTO.getDescription());
		labelHashMap.put(feeDTO.getFeeId(),descriptionLabel);
		return descriptionLabel;
	}

	// creates radio button, puts in hashmap
	private RadioButton addRadioButton(FeeDTO feeDTO) {
		RadioButton feeRadioButton = new RadioButton();
		// put radio button into a hashmap with value of the fee key
		radioHashMap.put(feeRadioButton,feeDTO.getFeeId());
		feeRadioButton.setToggleGroup(radioGroup);
		feeRadioButton.setSelected(true);
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

	private int getListIndexByKey(int key) {
		for(int i = 0; i < feeDTOS.size(); i++) {
			if(feeDTOS.get(i).getFeeId() == key) return i;
		}
		return 99; // 99 is error
	}

}
