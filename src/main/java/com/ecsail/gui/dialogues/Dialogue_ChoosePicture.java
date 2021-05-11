package com.ecsail.gui.dialogues;

import java.io.File;

import com.ecsail.main.Main;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Dialogue_ChoosePicture extends Stage {
	File choosenFile;
	public Dialogue_ChoosePicture() {

		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Button browseButton = new Button("Browse");
		Scene scene = new Scene(vboxBlue, 600, 300);
		FileChooser fileChooser = new FileChooser();
		/////////////////// ATTRIBUTES ///////////////////
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		fileChooser.setTitle("View Pictures");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
			    new FileChooser.ExtensionFilter("All Images", "*.*"),
			    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
			    new FileChooser.ExtensionFilter("GIF", "*.gif"),
			    new FileChooser.ExtensionFilter("BMP", "*.bmp"),
			    new FileChooser.ExtensionFilter("PNG", "*.png")
			);
		// vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		scene.getStylesheets().add("stylesheet.css");
		vboxGrey.getChildren().add(browseButton);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setTitle("Window Stub");
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));
		setAlwaysOnTop(true);
		
		//////////////// LISTENERS ///////////////////
		
        browseButton.setOnAction((event) -> {
        	setAlwaysOnTop(false);
        	File file = fileChooser.showOpenDialog(Main.getPrimaryStage());
    		if (file != null) {
                System.out.println(file.toString());
            }
        });
		
		//////////////// ADD CONTENT ///////////////////
		getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
	
}
