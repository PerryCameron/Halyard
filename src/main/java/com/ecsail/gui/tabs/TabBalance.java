package com.ecsail.gui.tabs;

import com.ecsail.structures.Object_BalanceText;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class TabBalance extends Tab {
	private Object_BalanceText textFields;
	private final String disabledColor = "-fx-background-color: #d5dade";
	public TabBalance(String text, Object_BalanceText bt) {
		super(text);
		this.textFields = bt;
		VBox vboxTextFieldFrame = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxTextField = new VBox();
		VBox vboxBlue = new VBox();
		HBox hboxGrey = new HBox(); 
		HBox hboxTotalFees = new HBox();
		HBox hboxCredit = new HBox();
		HBox hboxBalance = new HBox();
		VBox vboxCommitButton = new VBox();
		HBox hboxPaid = new HBox();
		
		VBox vboxTotalFeesLabel = new VBox();
		VBox vboxCreditLabel = new VBox();
		VBox vboxBalanceLabel = new VBox();
		VBox vboxPaidLabel = new VBox();
		
		VBox vboxTotalFeesBox = new VBox();
		VBox vboxCreditBox = new VBox();
		VBox vboxBalanceBox = new VBox();
		VBox vboxPaidBox = new VBox();
		
		////////////////ATTRIBUTES ///////////////////
		

		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(5,5,5,5));
		hboxGrey.setPadding(new Insets(5,0,5,5)); // spacing to make pink from around table
		//hboxGrey.setSpacing(5);
		hboxGrey.setId("box-grey");
		vboxTextFieldFrame.setPrefWidth(150);
		vboxTextField.setSpacing(5);
		vboxTextFieldFrame.setId("box-pink");
		vboxTextField.setId("box-lightgrey");
		vboxTextField.setPrefHeight(139);
		vboxCommitButton.setPrefWidth(95);
		vboxCommitButton.setSpacing(10);
		vboxTextFieldFrame.setPadding(new Insets(3,3,3,3));
		vboxTextField.setPadding(new Insets(12,0,7,10));
		vboxCommitButton.setPadding(new Insets(100,0,0,20));
		vboxTotalFeesLabel.setPrefWidth(70);
		vboxCreditLabel.setPrefWidth(70);
		vboxBalanceLabel.setPrefWidth(70);
		vboxPaidLabel.setPrefWidth(70);
		vboxTotalFeesLabel.setAlignment(Pos.CENTER_LEFT);
		vboxCreditLabel.setAlignment(Pos.CENTER_LEFT);
		vboxBalanceLabel.setAlignment(Pos.CENTER_LEFT);
		vboxPaidLabel.setAlignment(Pos.CENTER_LEFT);
		//vboxCommitButton.setId("box-test");
		
		textFields.getCreditText().setPrefWidth(60);
		textFields.getPaidText().setPrefWidth(60);
		textFields.getCreditText().setStyle(disabledColor);
		textFields.getCreditText().setEditable(false);
		textFields.getBalanceText().setPrefWidth(60);
		textFields.getBalanceText().setStyle("-fx-background-color: #9fc0c7");
		textFields.getBalanceText().setEditable(false);
		textFields.getTotalFeesText().setPrefWidth(60);
		textFields.getTotalFeesText().setEditable(false);
		textFields.getPaidText().setEditable(false);
		textFields.getTotalFeesText().setStyle(disabledColor);
		textFields.getPaidText().setStyle(disabledColor);
		textFields.getRenewCheckBox().setSelected(true);
		
		////////////////SETTING CONTENT //////////////
		
		vboxTotalFeesLabel.getChildren().add(new Label("Total Fees"));
		vboxCreditLabel.getChildren().add(new Label("Credit"));
		vboxBalanceLabel.getChildren().add(new Label("Balance"));
		vboxPaidLabel.getChildren().add(new Label("Paid"));
		
		vboxTotalFeesBox.getChildren().add(textFields.getTotalFeesText());
		vboxCreditBox.getChildren().add(textFields.getCreditText());
		vboxBalanceBox.getChildren().add(textFields.getBalanceText());
		vboxPaidBox.getChildren().add(textFields.getPaidText());
		
		vboxCommitButton.getChildren().addAll(textFields.getRenewCheckBox(), textFields.getCommitButton());
		hboxPaid.getChildren().addAll(vboxPaidLabel,vboxPaidBox);
		hboxTotalFees.getChildren().addAll(vboxTotalFeesLabel,vboxTotalFeesBox);
		hboxCredit.getChildren().addAll(vboxCreditLabel,vboxCreditBox);
		hboxBalance.getChildren().addAll(vboxBalanceLabel,vboxBalanceBox);
		vboxTextField.getChildren().addAll(hboxTotalFees,hboxCredit,hboxPaid,hboxBalance);
		vboxTextFieldFrame.getChildren().add(vboxTextField);
		vboxBlue.getChildren().add(hboxGrey);
		hboxGrey.getChildren().addAll(vboxTextFieldFrame,vboxCommitButton);
		setContent(vboxBlue);
		
	}
	
}
