package com.ecsail.gui;

import com.ecsail.structures.Object_Membership;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoxAttachment extends HBox {
	Object_Membership membership;

	public BoxAttachment(Object_Membership membership) {
		//super();
		this.membership = membership;

		//////////// OBJECTS ///////////////
		HBox hboxGrey = new HBox();  // this is the vbox for organizing all the widgets
		//VBox vboxPink = new VBox(); // this creates a pink border around the table
		VBox mainVBox = new VBox(); // contains viewable children

		/////////////  ATTRIBUTES /////////////

		hboxGrey.setPadding(new Insets(5, 5, 5, 5));
		hboxGrey.setPrefWidth(942);
		mainVBox.setSpacing(5);
		
		//vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink fram around table
		//yearSpinner.setPrefWidth(80);

		hboxGrey.setId("box-grey");
		//vboxPink.setId("box-pink");
		

		setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		setId("box-blue");
		//hboxGrey.setPrefWidth(942);
		
		///////////// SET CONTENT ////////////////////
		mainVBox.getChildren().addAll(new Label("Attachments will go here"));
		hboxGrey.getChildren().addAll(mainVBox);
		getChildren().add(hboxGrey);
	}
	
}
