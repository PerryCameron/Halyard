package com.ecsail.gui.tabs;

import com.ecsail.main.Main;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/// this tab is for visual purposes when logging on.  it goes away after logon
public class TabLogin extends Tab {

	public TabLogin(String text) {
		super(text);
		double stageHeight = Main.getPrimaryStage().getHeight();
		double titleBarHeight = Main.getPrimaryStage().getHeight() - Main.getPrimaryScene().getHeight();
		VBox inner = new VBox();
		HBox vboxGrey = new HBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		//vboxGrey.setId("slip-box");
		vboxBlue.setPrefHeight(Main.getPrimaryScene().getHeight() - titleBarHeight - Main.getToolBarHeight());
		//vboxGrey.setPrefHeight(700);
		//vboxGrey.setPrefHeight(688);
		vboxGrey.setPrefWidth(1003);

		vboxPink.setPrefHeight(695);
		vboxGrey.getChildren().add(inner);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
		System.out.println("stage="+stageHeight+" titleBar="+ titleBarHeight+ " scene=" + Main.getPrimaryScene().getHeight());
		System.out.println("vboxBlue="+vboxBlue.getHeight()+" vboxPink="+ vboxPink.getHeight()+ " vBoxGrey=" + vboxGrey.getHeight());

	}
	
}
