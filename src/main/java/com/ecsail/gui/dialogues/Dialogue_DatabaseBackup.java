package com.ecsail.gui.dialogues;

import java.util.List;

import com.ecsail.main.FileIO;
import com.ecsail.structures.Object_TupleCount;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Dialogue_DatabaseBackup extends Stage {
	private List<Object_TupleCount> tuples;
	public Dialogue_DatabaseBackup(Object_TupleCount newTupleCount) {
		this.tuples = FileIO.openTupleCountObjects();
		tuples.add(newTupleCount);
		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Scene scene = new Scene(vboxBlue, 600, 300);
		
		/////////////////// ATTRIBUTES ///////////////////
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		// vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		scene.getStylesheets().add("stylesheet.css");
		setTitle("Window Stub");
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));
		
		//////////////// ADD CONTENT ///////////////////
		
		
		
		vboxGrey.getChildren().add(addInformationVBox());
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
	
	private VBox addInformationVBox() {
		VBox iBox = new VBox();
		iBox.getChildren().add(new Label("Database backed up"));
		iBox.getChildren().addAll(new Text("Membership records: " + tuples.get(tuples.size() - 1).getMembershipSize()));
		iBox.getChildren().addAll(new Text("Id records: " + tuples.get(tuples.size() - 1).getIdSize()));
		iBox.getChildren().addAll(new Text("People records: " + tuples.get(tuples.size() - 1).getPeopleSize()));
		iBox.getChildren().addAll(new Text("Phone records: " + tuples.get(tuples.size() - 1).getPhoneSize()));
		iBox.getChildren().addAll(new Text("Boat records: " + tuples.get(tuples.size() - 1).getBoatSize()));
		iBox.getChildren().addAll(new Text("Boat Owner records: " + tuples.get(tuples.size() - 1).getBoatOwnerSize()));
		iBox.getChildren().addAll(new Text("Slip records: " + tuples.get(tuples.size() - 1).getSlipsSize()));
		iBox.getChildren().addAll(new Text("Memo records: " + tuples.get(tuples.size() - 1).getMemosSize()));
		iBox.getChildren().addAll(new Text("Email records: " + tuples.get(tuples.size() - 1).getEmailSize()));
		iBox.getChildren().addAll(new Text("Money records: " + tuples.get(tuples.size() - 1).getMoniesSize()));
		iBox.getChildren().addAll(new Text("Deposit records: " + tuples.get(tuples.size() - 1).getDepositsSize()));
		iBox.getChildren().addAll(new Text("Payment records: " + tuples.get(tuples.size() - 1).getPaymentsSize()));
		iBox.getChildren().addAll(new Text("Defined Fee records: " + tuples.get(tuples.size() - 1).getDefinedFeesSize()));
		iBox.getChildren().addAll(new Text("Work Credit records: " + tuples.get(tuples.size() - 1).getWorkCreditsSize()));
		return iBox;
	}
}
