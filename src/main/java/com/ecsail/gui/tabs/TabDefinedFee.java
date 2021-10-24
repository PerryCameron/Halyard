package com.ecsail.gui.tabs;

import com.ecsail.charts.DuesLineChart;
import com.ecsail.main.HalyardPaths;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlSelect;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.Object_DefinedFee;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;

public class TabDefinedFee extends Tab {
	int fieldWidth = 60;
	String selectedYear;
	Object_DefinedFee selectedFees;
	TextField duesRegularTextField = new TextField();
	TextField duesFamilyTextField = new TextField();
	TextField duesLakeAssociateTextField = new TextField();
	TextField duesSocialTextField = new TextField();
	TextField iniationTextField = new TextField();
	TextField wetSlipTextField = new TextField();
	TextField beachTextField = new TextField();
	TextField winterStorageTextField = new TextField();
	TextField gateKeyTextField = new TextField();
	TextField sailLoftAccessTextField = new TextField();
	TextField sailLoftKeyTextField = new TextField();
	TextField sailSchoolLoftAccessTextField = new TextField();
	TextField sailSchoolLoftKeyTextField = new TextField();
	TextField kayakRackTextField = new TextField();
	TextField kayakShedTextField = new TextField();
	TextField kayakShedKeyTextField = new TextField();
	TextField workCreditTextField = new TextField();
	
	public TabDefinedFee(String text) {
		super(text);
		this.selectedYear = HalyardPaths.getYear();
		this.selectedFees = SqlSelect.selectDefinedFees(Integer.parseInt(selectedYear));
		populateFields();
		
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		HBox hboxGrey = new HBox();
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		GridPane gridPane = new GridPane();

		ComboBox comboBox = new ComboBox();
		for(int i = Integer.parseInt(HalyardPaths.getYear()); i > 1969; i--) {
			comboBox.getItems().add(i);
		}
		comboBox.getSelectionModel().selectFirst();
		comboBox.getStyleClass().add("bigbox");


//		final Spinner<Integer> yearSpinner = new Spinner<Integer>();
//		SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, Integer.parseInt(selectedYear) + 1, Integer.parseInt(selectedYear));
//		yearSpinner.setValueFactory(wetSlipValueFactory);
//		yearSpinner.setPrefWidth(90);
//		yearSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
//		yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
//			if (!newValue) {
//				selectedYear = yearSpinner.getEditor().getText();
//				selectedFees.clear();
//				if (SqlExists.definedFeeExists(selectedYear)) {
//					selectedFees = SqlSelect.selectDefinedFees(Integer.parseInt(selectedYear));
//				} else {
//					Object_DefinedFee newFee = new Object_DefinedFee(Integer.parseInt(selectedYear),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
//					SqlInsert.addRecord(newFee);
//					selectedFees = SqlSelect.selectDefinedFees(Integer.parseInt(selectedYear));
//				}
//				populateFields();
//			}
//		});

        gridPane.add(duesRegularTextField, 0, 0, 1, 1);
        gridPane.add(duesFamilyTextField, 0, 1, 1, 1);
        gridPane.add(duesLakeAssociateTextField, 0, 2, 1, 1);
        gridPane.add(duesSocialTextField, 0, 3, 1, 1);
        gridPane.add(iniationTextField, 0, 4, 1, 1);
        gridPane.add(wetSlipTextField, 0, 5, 1, 1);
        gridPane.add(beachTextField, 0, 6, 1, 1);
        gridPane.add(winterStorageTextField, 0, 7, 1, 1);
        gridPane.add(gateKeyTextField, 0, 8, 1, 1);
        gridPane.add(sailLoftAccessTextField, 0, 9, 1, 1);
        gridPane.add(sailLoftKeyTextField, 0, 10, 1, 1);
        gridPane.add(sailSchoolLoftAccessTextField, 0, 11, 1, 1);
        gridPane.add(sailSchoolLoftKeyTextField, 0, 12, 1, 1);
        gridPane.add(kayakRackTextField, 0, 13, 1, 1);
        gridPane.add(kayakShedTextField, 0, 14, 1, 1);
        gridPane.add(kayakShedKeyTextField, 0, 15, 1, 1);
        gridPane.add(workCreditTextField, 0, 16, 1, 1);

        gridPane.add(new Label("Regular Membership Dues"), 1, 0, 1, 1);
        gridPane.add(new Label("Family Membership Dues"), 1, 1, 1, 1);
        gridPane.add(new Label("Lake Associate Dues"), 1, 2, 1, 1);
        gridPane.add(new Label("Social Membership Dues"), 1, 3, 1, 1);
        gridPane.add(new Label("Initiation Fee"), 1, 4, 1, 1);
        gridPane.add(new Label("Wet Slip Fee"), 1, 5, 1, 1);
        gridPane.add(new Label("Beach Spot Fee"), 1, 6, 1, 1);
        gridPane.add(new Label("Winter Storage Fee"), 1, 7, 1, 1);
        gridPane.add(new Label("Gate Key Fee"), 1, 8, 1, 1);
        gridPane.add(new Label("Sail Loft Access Fee"), 1, 9, 1, 1);
        gridPane.add(new Label("Sail Loft Key Fee"), 1, 10, 1, 1);
        gridPane.add(new Label("Sail School Loft Access Fee"), 1, 11, 1, 1);
        gridPane.add(new Label("Sail School Loft Key Fee"), 1, 12, 1, 1);
        gridPane.add(new Label("Kayak Rack Fee"), 1, 13, 1, 1);
        gridPane.add(new Label("Kayak Inside Storage Fee"), 1, 14, 1, 1);
        gridPane.add(new Label("Kayak Inside Storage Key Fee"), 1, 15, 1, 1);
        gridPane.add(new Label("Work Credit Amount"), 1, 16, 1, 1);

        
        /////// ATTRIBUTES //////////
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
		duesRegularTextField.setPrefWidth(fieldWidth);
		duesFamilyTextField.setPrefWidth(fieldWidth);
		duesLakeAssociateTextField.setPrefWidth(fieldWidth);
		duesSocialTextField.setPrefWidth(fieldWidth);
		iniationTextField.setPrefWidth(fieldWidth);
		wetSlipTextField.setPrefWidth(fieldWidth);
		beachTextField.setPrefWidth(fieldWidth);
		winterStorageTextField.setPrefWidth(fieldWidth);
		gateKeyTextField.setPrefWidth(fieldWidth);
		sailLoftAccessTextField.setPrefWidth(fieldWidth);
		sailLoftKeyTextField.setPrefWidth(fieldWidth);
		sailSchoolLoftAccessTextField.setPrefWidth(fieldWidth);
		sailSchoolLoftKeyTextField.setPrefWidth(fieldWidth);
		kayakRackTextField.setPrefWidth(fieldWidth);
		kayakShedTextField.setPrefWidth(fieldWidth);
		kayakShedKeyTextField.setPrefWidth(fieldWidth);
		workCreditTextField.setPrefWidth(fieldWidth);
        
        vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxGrey.setPadding(new Insets(10,10,10,10));
		vboxPink.setId("box-pink");
		//vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		vboxGrey.setSpacing(15);

		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		
        /////////////////// LISTENERS /////////////////////////

		comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			selectedYear = newValue.toString();
			selectedFees.clear();
			if (SqlExists.definedFeeExists(selectedYear)) {
				selectedFees = SqlSelect.selectDefinedFees(Integer.parseInt(selectedYear));
			} else {
				Object_DefinedFee newFee = new Object_DefinedFee(Integer.parseInt(selectedYear), BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
				SqlInsert.addRecord(newFee);
				selectedFees = SqlSelect.selectDefinedFees(Integer.parseInt(selectedYear));
			}
			populateFields();
		});

		duesRegularTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "dues_regular", duesRegularTextField.getText());
	            }
	        }
	    });
		
		duesFamilyTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "dues_family", duesFamilyTextField.getText());
	            }
	        }
	    });
		
		duesLakeAssociateTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "dues_lake_associate", duesLakeAssociateTextField.getText());
	            }
	        }
	    });
		
		duesSocialTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "dues_social", duesSocialTextField.getText());
	            }
	        }
	    });
		
		iniationTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "initiation", iniationTextField.getText());
	            }
	        }
	    });
		
		wetSlipTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "wet_slip", wetSlipTextField.getText());
	            }
	        }
	    });
		
		beachTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "beach", beachTextField.getText());
	            }
	        }
	    });
		
		winterStorageTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "winter_storage", winterStorageTextField.getText());
	            }
	        }
	    });
		
		gateKeyTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "main_gate_key", gateKeyTextField.getText());
	            }
	        }
	    });
		
		sailLoftAccessTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "sail_loft", sailLoftAccessTextField.getText());
	            }
	        }
	    });
		
		sailLoftKeyTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "sail_loft_key", sailLoftKeyTextField.getText());
	            }
	        }
	    });
		
		sailSchoolLoftAccessTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "sail_school_laser_loft", sailSchoolLoftAccessTextField.getText());
	            }
	        }
	    });
		
		sailSchoolLoftKeyTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "sail_school_loft_key", sailSchoolLoftKeyTextField.getText());
	            }
	        }
	    });
		
		kayakRackTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "kayak_rack", kayakRackTextField.getText());
	            }
	        }
	    });
		
		kayakShedTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "kayak_shed", kayakShedTextField.getText());
	            }
	        }
	    });
		
		kayakShedKeyTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "kayak_shed_key", kayakShedKeyTextField.getText());
	            }
	        }
	    });
		
		workCreditTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            //focus out
	            if (oldValue) {  // we have focused and unfocused
	            	SqlUpdate.updateDefinedFee(selectedYear, "work_credit", workCreditTextField.getText());
	            }
	        }
	    });
		
		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		vboxGrey.getChildren().addAll(comboBox,gridPane);
		hboxGrey.getChildren().addAll(vboxGrey, new DuesLineChart());
		vboxPink.getChildren().add(hboxGrey);
		vboxBlue.getChildren().add(vboxPink);
		setContent(vboxBlue);
		
	}
	
	////////////////////  CLASS METHODS ////////////////////
	
	private void populateFields() {
		duesRegularTextField.setText(selectedFees.getDues_regular() + "");
		duesFamilyTextField.setText(selectedFees.getDues_family() + "");
		duesLakeAssociateTextField.setText(selectedFees.getDues_lake_associate() + "");
		duesSocialTextField.setText(selectedFees.getDues_social() + "");
		iniationTextField.setText(selectedFees.getInitiation() + "");
		wetSlipTextField.setText(selectedFees.getWet_slip() + "");
		beachTextField.setText(selectedFees.getBeach() + "");
		winterStorageTextField.setText(selectedFees.getWinter_storage() + "");
		gateKeyTextField.setText(selectedFees.getMain_gate_key() + "");
		sailLoftAccessTextField.setText(selectedFees.getSail_loft() + "");
		sailLoftKeyTextField.setText(selectedFees.getSail_loft_key() + "");
		sailSchoolLoftAccessTextField.setText(selectedFees.getSail_school_laser_loft() + "");
		sailSchoolLoftKeyTextField.setText(selectedFees.getSail_school_loft_key() + "");
		kayakRackTextField.setText(selectedFees.getKayak_rack() + "");
		kayakShedTextField.setText(selectedFees.getKayak_shed() + "");
		kayakShedKeyTextField.setText(selectedFees.getKayak_shed_key() + "");
		workCreditTextField.setText(selectedFees.getWork_credit() + "");
	}
	
}
