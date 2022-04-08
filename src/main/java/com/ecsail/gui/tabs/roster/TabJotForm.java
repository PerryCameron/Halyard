package com.ecsail.gui.tabs.roster;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class TabJotForm extends Tab {

	public TabJotForm(String text) {
		super(text);
		
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		//vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		
		vboxGrey.getChildren().add(new Label("Stubbed in Tab"));
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}
}
