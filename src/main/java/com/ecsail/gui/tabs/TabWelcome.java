package com.ecsail.gui.tabs;

import com.ecsail.main.Main;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class TabWelcome extends Tab {
	
	public TabWelcome(VBox vboxBoxWelcome) {  // check boxWelcome
		super();
		setText("Welcome");
		
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		vboxBlue.setPrefWidth(1028);
		vboxBoxWelcome.setPrefHeight(680);
		// vboxGrey.setPrefHeight(688)
		vboxPink.setMaxHeight(1000);
		vboxPink.setPrefHeight(686);
		
		Main.getPrimaryStage().heightProperty().addListener((obs, oldVal, newVal) -> {
			 //System.out.println((double)newVal);
			 System.out.println(vboxPink.getHeight());
			 vboxPink.setPrefHeight(686 + (double)newVal - 796);// 570 is start height
		});
		
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxBoxWelcome);
		setContent(vboxBlue);
		
	}
}
