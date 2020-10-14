package com.ecsail.gui.tabs;

import com.ecsail.structures.Object_BalanceText;

import javafx.geometry.Insets;
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
		
		////////////////ATTRIBUTES ///////////////////
		
		hboxPaid.setSpacing(43);
		hboxTotalFees.setSpacing(12);
		hboxCredit.setSpacing(34);
		hboxBalance.setSpacing(25);
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(5,5,5,5));
		hboxGrey.setPadding(new Insets(5,0,5,5)); // spacing to make pink from around table
		//hboxGrey.setSpacing(5);
		hboxGrey.setId("box-grey");
		vboxTextFieldFrame.setPrefWidth(150);
		vboxTextField.setSpacing(5);
		vboxTextFieldFrame.setId("box-pink");
		vboxTextField.setId("box-lightgrey");
		vboxCommitButton.setPrefWidth(95);
		vboxTextFieldFrame.setPadding(new Insets(3,3,3,3));
		vboxTextField.setPadding(new Insets(8,0,7,10));
		vboxCommitButton.setPadding(new Insets(105,0,0,20));
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
		textFields.getTotalFeesText().setStyle(disabledColor);
		
		////////////////SETTING CONTENT //////////////
		
		vboxCommitButton.getChildren().add(textFields.getCommitButton());
		hboxPaid.getChildren().addAll(new Label("Paid"),textFields.getPaidText());
		hboxTotalFees.getChildren().addAll(new Label("Total Fees"),textFields.getTotalFeesText());
		hboxCredit.getChildren().addAll(new Label("Credit"),textFields.getCreditText());
		hboxBalance.getChildren().addAll(new Label("Balance"),textFields.getBalanceText());
		vboxTextField.getChildren().addAll(hboxTotalFees,hboxCredit,hboxPaid,hboxBalance);
		vboxTextFieldFrame.getChildren().add(vboxTextField);
		vboxBlue.getChildren().add(hboxGrey);
		hboxGrey.getChildren().addAll(vboxTextFieldFrame,vboxCommitButton);
		setContent(vboxBlue);
		
	}
	
}
