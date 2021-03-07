package com.ecsail.gui.tabs;

import com.ecsail.main.Paths;
import com.ecsail.structures.Object_Memo;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class TabNotes extends Tab {
	private TableView<Object_Memo> notesTableView = new TableView<>();
	private ObservableList<Object_Memo> memos;
	String selectedYear;
	
	public TabNotes(String text) {
		super(text);
		this.selectedYear = Paths.getYear();
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		TitledPane titledPane = new TitledPane();
		HBox controlsHbox = new HBox();

		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		//vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		
		final Spinner<Integer> yearSpinner = new Spinner<Integer>();
		SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, Integer.parseInt(selectedYear), Integer.parseInt(selectedYear));
		yearSpinner.setValueFactory(wetSlipValueFactory);
		yearSpinner.setPrefWidth(110);
		yearSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  selectedYear = yearSpinner.getEditor().getText();  /// kept this for clarity, could have used printChoices.getYear()

			  }
			});
		
		notesTableView.setItems(memos);
		notesTableView.setPrefWidth(1000);
		notesTableView.setFixedCellSize(30);
		notesTableView.setPrefHeight(555);
		
		
		controlsHbox.getChildren().add(yearSpinner);
		titledPane.setContent(controlsHbox);
		vboxGrey.getChildren().addAll(titledPane,notesTableView);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
		
	}
	
}
