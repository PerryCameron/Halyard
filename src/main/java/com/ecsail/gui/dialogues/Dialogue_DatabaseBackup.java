package com.ecsail.gui.dialogues;

import java.util.List;

import com.ecsail.main.FileIO;
import com.ecsail.structures.Object_TupleCount;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
		Scene scene = new Scene(vboxBlue, 400, 300);
		
		/////////////////// ATTRIBUTES ///////////////////
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		// vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		scene.getStylesheets().add("stylesheet.css");
		setTitle("Database BackUp");
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
		GridPane pane = new GridPane();
		int r = tuples.size() - 1;
		Font font1 = Font.font("Verdana", FontWeight.BOLD, 14);
		Text text;
		pane.setHgap(25);
		
		iBox.getChildren().add(new Text("Database sucessfully backed up"));
		
		////////// HEADERS //////////////
		text = new Text("Record Type");
		text.setFont(font1);	
		pane.add(text, 0, 0);
		
		text = new Text("New");
		text.setFont(font1);	
		pane.add(text, 1, 0);
		
		text = new Text("Modified");
		text.setFont(font1);	
		pane.add(text, 2, 0);
		
		text = new Text("Total");
		text.setFont(font1);	
		pane.add(text, 3, 0);
		
		////////// ROW 1 ////////////
		
		text = new Text("Membership");	
		pane.add(text, 0, 1);
		
		text = new Text("0");	
		pane.add(text, 1, 1);
		
		text = new Text("0");
		pane.add(text, 2, 1);
		
		text = new Text(tuples.get(r).getMembershipSize() + "");	
		pane.add(text, 3, 1);
		
		////////// ROW 2 ////////////
		
		text = new Text("Id");	
		pane.add(text, 0, 2);
		
		text = new Text("0");	
		pane.add(text, 1, 2);
		
		text = new Text("0");
		pane.add(text, 2, 2);
		
		text = new Text(tuples.get(r).getIdSize() + "");	
		pane.add(text, 3, 2);
		
		////////// ROW 3 ////////////
		
		text = new Text("People");	
		pane.add(text, 0, 3);
		
		text = new Text("0");	
		pane.add(text, 1, 3);
		
		text = new Text("0");
		pane.add(text, 2, 3);
		
		text = new Text(tuples.get(r).getPeopleSize() + "");	
		pane.add(text, 3, 3);
		
		////////// ROW 4 ////////////
		
		text = new Text("Phone");	
		pane.add(text, 0, 4);
		
		text = new Text("0");	
		pane.add(text, 1, 4);
		
		text = new Text("0");
		pane.add(text, 2, 4);
		
		text = new Text(tuples.get(r).getPhoneSize() + "");	
		pane.add(text, 3, 4);
		
		////////// ROW 5 ////////////
		
		text = new Text("Boat");	
		pane.add(text, 0, 5);
		
		text = new Text("0");	
		pane.add(text, 1, 5);
		
		text = new Text("0");
		pane.add(text, 2, 5);
		
		text = new Text(tuples.get(r).getBoatSize() + "");	
		pane.add(text, 3, 5);
		
		////////// ROW 6 ////////////
		
		text = new Text("Boat Owner");	
		pane.add(text, 0, 6);
		
		text = new Text("0");	
		pane.add(text, 1, 6);
		
		text = new Text("0");
		pane.add(text, 2, 6);
		
		text = new Text(tuples.get(r).getBoatOwnerSize() + "");	
		pane.add(text, 3, 6);
		
		////////// ROW 7 ////////////
		
		text = new Text("Slip");	
		pane.add(text, 0, 7);
		
		text = new Text("0");	
		pane.add(text, 1, 7);
		
		text = new Text("0");
		pane.add(text, 2, 7);
		
		text = new Text(tuples.get(r).getSlipsSize() + "");	
		pane.add(text, 3, 7);
		
		////////// ROW 8 ////////////
		
		text = new Text("Memo");	
		pane.add(text, 0, 8);
		
		text = new Text("0");	
		pane.add(text, 1, 8);
		
		text = new Text("0");
		pane.add(text, 2, 8);
		
		text = new Text(tuples.get(r).getMemosSize() + "");	
		pane.add(text, 3, 8);
		
		////////// ROW 9 ////////////
		
		text = new Text("Email");	
		pane.add(text, 0, 9);
		
		text = new Text("0");	
		pane.add(text, 1, 9);
		
		text = new Text("0");
		pane.add(text, 2, 9);
		
		text = new Text(tuples.get(r).getEmailSize() + "");	
		pane.add(text, 3, 9);
		
		////////// ROW 10 ////////////
		
		text = new Text("Money");	
		pane.add(text, 0, 10);
		
		text = new Text("0");	
		pane.add(text, 1, 10);
		
		text = new Text("0");
		pane.add(text, 2, 10);
		
		text = new Text(tuples.get(r).getMoniesSize() + "");	
		pane.add(text, 3, 10);
		
		////////// ROW 11 ////////////
		
		text = new Text("Deposit");	
		pane.add(text, 0, 11);
		
		text = new Text("0");	
		pane.add(text, 1, 11);
		
		text = new Text("0");
		pane.add(text, 2, 11);
		
		text = new Text(tuples.get(r).getDepositsSize() + "");	
		pane.add(text, 3, 11);
		
		////////// ROW 12 ////////////
		
		text = new Text("Payment");	
		pane.add(text, 0, 12);
		
		text = new Text("0");	
		pane.add(text, 1, 12);
		
		text = new Text("0");
		pane.add(text, 2, 12);
		
		text = new Text(tuples.get(r).getPaymentsSize() + "");	
		pane.add(text, 3, 12);
		
		////////// ROW 13 ////////////
		
		text = new Text("Defined Fee");	
		pane.add(text, 0, 13);
		
		text = new Text("0");	
		pane.add(text, 1, 13);
		
		text = new Text("0");
		pane.add(text, 2, 13);
		
		text = new Text(tuples.get(r).getDefinedFeesSize() + "");	
		pane.add(text, 3, 13);
		
		////////// ROW 14 ////////////
		
		text = new Text("Work Credit");	
		pane.add(text, 0, 14);
		
		text = new Text("0");	
		pane.add(text, 1, 14);
		
		text = new Text("0");
		pane.add(text, 2, 14);
		
		text = new Text(tuples.get(r).getWorkCreditsSize() + "");	
		pane.add(text, 3, 14);
		
		iBox.getChildren().add(pane);
		return iBox;
	}
	

}
