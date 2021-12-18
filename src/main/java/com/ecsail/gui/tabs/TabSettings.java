package com.ecsail.gui.tabs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class TabSettings extends Tab {

	TextField emailTextField = new TextField();
	TextField passwordTextField = new TextField();
	TextField hostTextField = new TextField();
	TextField portTextField = new TextField();
	CheckBox authCheckBox = new CheckBox("auth");
	CheckBox tlsCheckBox = new CheckBox("tls");
	Button saveButton = new Button("Save");

	public TabSettings(String text) {
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

//                  properties.put("mail.smtp.username","");
//					properties.put("mail.smtp.password", "");
//					properties.setProperty("mail.smtp.host", host);
//					properties.put("mail.smtp.port", "587");
//					properties.put("mail.smtp.auth", "true");
//					properties.put("mail.smtp.starttls.enable", "true"); // TLS