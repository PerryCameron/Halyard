package com.ecsail.gui.tabs.roster;

import com.ecsail.structures.Object_RosterRadioButtons;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TabStandard extends Tab {
	
	public TabStandard(Object_RosterRadioButtons rb) {
		HBox rbHBox = new HBox();
		VBox vboxRadioButton1 = new VBox();
		VBox vboxRadioButton2 = new VBox();
		
		////////////////// ATTRIBUTES ////////////////////////
		
		vboxRadioButton1.setSpacing(3);
		vboxRadioButton2.setSpacing(3);
		vboxRadioButton1.setPadding(new Insets(5,5,5,5));
		vboxRadioButton2.setPadding(new Insets(5,5,5,5));
		setId("rostertab-pane");
		//getStyleClass().add("roster-tab-pane");
        ////////////////////SET CONTENT //////////////////////
		
		vboxRadioButton1.getChildren().addAll(rb.getRadioAll(),rb.getRadioActive(),rb.getRadioNonRenew());
		vboxRadioButton2.getChildren().addAll(rb.getRadioNewMembers(),rb.getRadioNewReturnMembers());
		rbHBox.getChildren().addAll(vboxRadioButton1,vboxRadioButton2);
		setContent(rbHBox);
	}

}
