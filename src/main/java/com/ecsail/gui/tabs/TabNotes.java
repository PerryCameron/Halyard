package com.ecsail.gui.tabs;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import com.ecsail.main.Paths;
import com.ecsail.sql.SqlSelect;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Memo2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class TabNotes extends Tab {
	private TableView<Object_Memo2> notesTableView = new TableView<>();
	private ObservableList<Object_Memo2> memos;
	String selectedYear;
	String options;
	Boolean n;
	Boolean o;
	Boolean p;
	
	public TabNotes(String text) {
		super(text);
		this.selectedYear = Paths.getYear();
		this.memos = SqlSelect.getAllMemosForTabNotes(Paths.getYear());
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		TitledPane titledPane = new TitledPane();
		HBox controlsHbox = new HBox();

		CheckBox oCheckBox = new CheckBox("Other");
		CheckBox nCheckBox = new CheckBox("Regular");
		CheckBox pCheckBox = new CheckBox("Payment");
		controlsHbox.setSpacing(7);
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		//vboxGrey.setId("slip-box");
		//vboxGrey.setPrefHeight(688);
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		VBox.setVgrow(vboxPink,Priority.ALWAYS);
		VBox.setVgrow(notesTableView,Priority.ALWAYS);
		HBox.setHgrow(notesTableView,Priority.ALWAYS);
		
		final Spinner<Integer> yearSpinner = new Spinner<Integer>();
		SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, Integer.parseInt(selectedYear), Integer.parseInt(selectedYear));
		yearSpinner.setValueFactory(wetSlipValueFactory);
		yearSpinner.setPrefWidth(110);
		yearSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  selectedYear = yearSpinner.getEditor().getText();  /// kept this for clarity, could have used printChoices.getYear()
				  System.out.println(selectedYear);
				  memos.clear();
				  memos.addAll(SqlSelect.getAllMemosForTabNotes(selectedYear));
			  }
			});
		
		notesTableView.setItems(memos);
		notesTableView.setFixedCellSize(30);
		notesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		
		TableColumn<Object_Memo2, String> Col1 = new TableColumn<Object_Memo2, String>("MEM");
		Col1.setCellValueFactory(new PropertyValueFactory<Object_Memo2, String>("membershipId"));
		
		TableColumn<Object_Memo2, String> Col2 = new TableColumn<Object_Memo2, String>("DATE");
		Col2.setCellValueFactory(new PropertyValueFactory<Object_Memo2, String>("memo_date"));
		
		TableColumn<Object_Memo2, String> Col3 = new TableColumn<Object_Memo2, String>("TYPE");
		Col3.setCellValueFactory(new PropertyValueFactory<Object_Memo2, String>("category"));
		
		TableColumn<Object_Memo2, String> Col4 = new TableColumn<Object_Memo2, String>("NOTE");
		Col4.setCellValueFactory(new PropertyValueFactory<Object_Memo2, String>("memo"));

		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // Mem 5%
		Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // Join Date 15%
		Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // Type
		Col4.setMaxWidth( 1f * Integer.MAX_VALUE * 80 );   // Slip

		Collections.sort(memos, Comparator.comparing(Object_Memo2::getMemo_date).reversed());


		oCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            	o = newValue;
				System.out.println(o);
//            	setOptions();
            }
        });
		
		nCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            	n = newValue;
//            	setOptions();
            }
        });
		
		pCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            	p = newValue;
//            	setOptions();
            }
        });
		
		notesTableView.getColumns().addAll(Arrays.asList(Col1, Col2, Col3, Col4));
		controlsHbox.getChildren().addAll(yearSpinner,nCheckBox,oCheckBox,pCheckBox);
		titledPane.setContent(controlsHbox);
		vboxGrey.getChildren().addAll(titledPane,notesTableView);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
		
	}
	
	private void setOptions() {
		options = "";
		System.out.print("The answer is" + p);
		if(p) options+=" and category='P'";
		if(o) options+=" and category='O'";
		if(n) options+=" and category='N'";
		
		System.out.println("options");
	}
	
}
