package com.ecsail.gui;

import com.ecsail.structures.Object_Person;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoxSearch extends HBox {
	public static TableView<Object_Person> personTableView;
	public BoxSearch(TableView<Object_Person> ptv) {
		BoxSearch.personTableView = ptv;
		
		VBox vbox1 = new VBox(); // holds email buttons
		HBox hboxGrey = new HBox(); // this is here for the grey background to make nice apperence
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Button searchRecords = new Button("Search");
		TextField searchBox = new TextField();
		
		hboxGrey.setPrefWidth(463);
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		vbox1.setSpacing(5);
		hboxGrey.setId("box-grey");
		vboxPink.setId("box-pink");
		hboxGrey.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink fram around table
		setId("box-blue");
		setPadding(new Insets(5, 5, 5, 5));
		
		searchRecords.setOnAction(new EventHandler<ActionEvent>() {
	           @Override public void handle(ActionEvent e) {
	        	   TabPeopleList.searchLastName(searchBox.getText());
	        	   //System.out.println("Text is" + searchBox.getText());
	            }
	        });
		
		searchBox.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	TabPeopleList.searchLastName(searchBox.getText());
	            }
	        }
	    });
	    
	    hboxGrey.getChildren().addAll(searchBox,searchRecords);
		getChildren().addAll(hboxGrey);
	}
	

	
}
