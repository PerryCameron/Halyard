package com.ecsail.gui.tabs;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class TabWelcome extends Tab {
	
	public TabWelcome(VBox vboxGrey) {  // check boxWelcome
		super();
		setText("Welcome");
		
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		vboxBlue.setPrefWidth(1028);
		vboxGrey.setPrefHeight(680);
		vboxGrey.setMaxHeight(Double.MAX_VALUE);
		
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
		System.out.println("VBox Grey is" + vboxGrey.getHeight());
		
	}
}
