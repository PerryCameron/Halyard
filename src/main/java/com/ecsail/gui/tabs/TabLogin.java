package com.ecsail.gui.tabs;

import com.ecsail.main.Main;
import com.ecsail.main.Paths;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/// this tab is for visual purposes when logging on.  it goes away after logon
public class TabLogin extends Tab {

	public TabLogin(String text) {
		super(text);
		double titleBarHeight = Main.getPrimaryStage().getHeight() - Main.getPrimaryScene().getHeight();
		VBox inner = new VBox();
		HBox vboxGrey = new HBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		if(Paths.isWindows())
		vboxBlue.setPrefHeight(Main.getPrimaryScene().getHeight() - titleBarHeight - Main.getToolBarHeight());
		else
		vboxBlue.setPrefHeight(Main.getPrimaryScene().getHeight() - titleBarHeight - Main.getToolBarHeight() -24);
		vboxGrey.setPrefWidth(1003);

		vboxPink.setPrefHeight(695);
		vboxGrey.getChildren().add(inner);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}
	
}