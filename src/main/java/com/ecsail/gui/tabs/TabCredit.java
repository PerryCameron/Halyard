package com.ecsail.gui.tabs;

import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.Object_Integer;
import com.ecsail.structures.Object_WorkCredit;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TabCredit extends Tab {
	//private final String disabledColor = "-fx-background-color: #d5dade";
	Object_WorkCredit selectedWorkCreditYear;
	Object_Integer workCredit;
	private final Spinner<Integer> racingSpinner = new Spinner<Integer>();
	private final Spinner<Integer> harborSpinner = new Spinner<Integer>();
	private final Spinner<Integer> socialSpinner = new Spinner<Integer>();
	private final Spinner<Integer> otherSpinner = new Spinner<Integer>();
	
	public TabCredit(String text, Object_WorkCredit swc, Object_Integer wc) {
		super(text);
		this.selectedWorkCreditYear = swc;
		this.workCredit = wc;
		
		VBox vboxTextFieldFrame = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxTextField = new VBox();
		VBox vboxBlue = new VBox();
		HBox hboxGrey = new HBox();
		HBox hboxRacing = new HBox();
		HBox hboxHarbor = new HBox();
		HBox hboxSocial = new HBox();
		HBox hboxOther = new HBox();
		
		VBox vboxRacingLabel = new VBox();
		VBox vboxHarborLabel = new VBox();
		VBox vboxSocialLabel = new VBox();
		VBox vboxOtherLabel = new VBox();
		
		VBox vboxRacingBox = new VBox();
		VBox vboxHarborBox = new VBox();
		VBox vboxSocialBox = new VBox();
		VBox vboxOtherBox = new VBox();

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
		racingSpinner.setPrefWidth(60);
		harborSpinner.setPrefWidth(60);
		socialSpinner.setPrefWidth(60);
		otherSpinner.setPrefWidth(60);
		vboxRacingLabel.setPrefWidth(130);
		vboxHarborLabel.setPrefWidth(130);
		vboxSocialLabel.setPrefWidth(130);
		vboxOtherLabel.setPrefWidth(130);
		
		vboxRacingLabel.setAlignment(Pos.CENTER_LEFT);
		vboxHarborLabel.setAlignment(Pos.CENTER_LEFT);
		vboxSocialLabel.setAlignment(Pos.CENTER_LEFT);
		vboxOtherLabel.setAlignment(Pos.CENTER_LEFT);

		//////////////// LISTENERS ///////////////////
		SpinnerValueFactory<Integer> racingValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedWorkCreditYear.getRacing());
		racingSpinner.setValueFactory(racingValueFactory);
		racingSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
				  //SqlUpdate.updateWorkCredit("racing",selectedWorkCreditYear);
				  selectedWorkCreditYear.setRacing(newValue);
				  updateCredit();
			});
		
		SpinnerValueFactory<Integer> harborValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedWorkCreditYear.getHarbor());
		harborSpinner.setValueFactory(harborValueFactory);
		harborSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
				  //SqlUpdate.updateWorkCredit(fieldValue,"harbor",selectedWorkCreditYear);
				  selectedWorkCreditYear.setHarbor(newValue);
				  updateCredit();
			});
		
		SpinnerValueFactory<Integer> socialValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15, selectedWorkCreditYear.getSocial());
		socialSpinner.setValueFactory(socialValueFactory);
		socialSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			//	  SqlUpdate.updateWorkCredit(fieldValue,"social",selectedWorkCreditYear);
				  selectedWorkCreditYear.setSocial(newValue);
				  updateCredit();
			});
		
		SpinnerValueFactory<Integer> otherValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 455, selectedWorkCreditYear.getOther());
		otherSpinner.setValueFactory(otherValueFactory);
		otherSpinner.valueProperty().addListener((obs, oldValue, newValue) ->  {
			//	  SqlUpdate.updateWorkCredit(fieldValue,"other",selectedWorkCreditYear);
				  selectedWorkCreditYear.setOther(newValue);
				  updateCredit();
		});

		vboxRacingLabel.getChildren().add(new Label("Racing"));
		vboxHarborLabel.getChildren().add(new Label("Harbor"));
		vboxSocialLabel.getChildren().add(new Label("Social"));
		vboxOtherLabel.getChildren().add(new Label("Other"));
		
		vboxRacingBox.getChildren().add(racingSpinner);
		vboxHarborBox.getChildren().add(harborSpinner);
		vboxSocialBox.getChildren().add(socialSpinner);
		vboxOtherBox.getChildren().add(otherSpinner);
		////////////////SETTING CONTENT //////////////
		
		hboxRacing.getChildren().addAll(vboxRacingLabel,vboxRacingBox);
		hboxHarbor.getChildren().addAll(vboxHarborLabel,vboxHarborBox);
		hboxSocial.getChildren().addAll(vboxSocialLabel,vboxSocialBox);
		hboxOther.getChildren().addAll(vboxOtherLabel,vboxOtherBox);
		vboxTextField.getChildren().addAll(hboxRacing, hboxHarbor,hboxSocial,hboxOther);
		vboxTextFieldFrame.getChildren().add(vboxTextField);
		vboxBlue.getChildren().add(hboxGrey);
		hboxGrey.getChildren().addAll(vboxTextFieldFrame);
		setContent(vboxBlue);
	}
	
	private void updateCredit() {
		workCredit.setInteger(selectedWorkCreditYear.getHarbor() + selectedWorkCreditYear.getRacing()
		+ selectedWorkCreditYear.getSocial() + selectedWorkCreditYear.getOther());
		SqlUpdate.updateWorkCredit(selectedWorkCreditYear);
	}
}
