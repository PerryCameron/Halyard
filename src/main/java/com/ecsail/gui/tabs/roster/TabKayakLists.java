package com.ecsail.gui.tabs.roster;

import com.ecsail.structures.Object_RosterRadioButtons;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TabKayakLists extends Tab {
	
	public TabKayakLists(Object_RosterRadioButtons rb) {
		HBox hboxFrame = new HBox();
		HBox hboxMain = new HBox();
		VBox vboxRadioButton1 = new VBox();
		VBox vboxRadioButton2 = new VBox();
		
		////////////////// ATTRIBUTES ////////////////////////
		hboxFrame.setId("box-blue");
		hboxFrame.setPadding(new Insets(2,2,2,2));
		hboxMain.setId("box-pink");
		vboxRadioButton1.setSpacing(5);
		vboxRadioButton2.setSpacing(5);
		vboxRadioButton1.setPadding(new Insets(8,5,5,5));
		vboxRadioButton2.setPadding(new Insets(8,5,5,5));
		setId("rostertab-pane");
		//getStyleClass().add("roster-tab-pane");
        ////////////////////SET CONTENT //////////////////////
		
		vboxRadioButton1.getChildren().addAll(rb.getRadioKayakRackOwners(),rb.getRadioKayakRackWaitList());
		vboxRadioButton2.getChildren().addAll(rb.getRadioShedOwner(),rb.getRadioShedWaitList());
		hboxMain.getChildren().addAll(vboxRadioButton1,vboxRadioButton2);
		hboxFrame.getChildren().add(hboxMain);
		setContent(hboxFrame);
	}

}
