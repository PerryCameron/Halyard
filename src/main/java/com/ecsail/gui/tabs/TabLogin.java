package com.ecsail.gui.tabs;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/// this tab is for visual purposes when logging on.  it goes away after logon
public class TabLogin extends Tab {

	public TabLogin(String text) {
		super(text);
		
		VBox inner = new VBox();
		HBox vboxGrey = new HBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		//vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		vboxGrey.setPrefWidth(1003);

		vboxPink.setPrefHeight(686);
		vboxGrey.getChildren().add(inner);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
		
	}
	
}
