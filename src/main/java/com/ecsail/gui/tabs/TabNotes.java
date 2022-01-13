package com.ecsail.gui.tabs;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import com.ecsail.main.HalyardPaths;
import com.ecsail.sql.select.SqlMemos;
import com.ecsail.structures.Memo2DTO;

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
	private TableView<Memo2DTO> notesTableView = new TableView<>();
	private ObservableList<Memo2DTO> memos;
	String selectedYear;
	String options;
	Boolean n = true;
	Boolean o = true;
	Boolean p = true;
	
	public TabNotes(String text) {
		super(text);
		this.selectedYear = HalyardPaths.getYear();
		this.memos = SqlMemos.getAllMemosForTabNotes(HalyardPaths.getYear(),setOptions());
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		TitledPane titledPane = new TitledPane();
		HBox controlsHbox = new HBox();

		CheckBox oCheckBox = new CheckBox("Other");
		CheckBox nCheckBox = new CheckBox("Normal");
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
		oCheckBox.setSelected(true);
		nCheckBox.setSelected(true);
		pCheckBox.setSelected(true);
		
		final Spinner<Integer> yearSpinner = new Spinner<Integer>();
		SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, Integer.parseInt(selectedYear), Integer.parseInt(selectedYear));
		yearSpinner.setValueFactory(wetSlipValueFactory);
		yearSpinner.setPrefWidth(110);
		yearSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  selectedYear = yearSpinner.getEditor().getText();  /// kept this for clarity, could have used printChoices.getYear()
				  memos.clear();
				  memos.addAll(SqlMemos.getAllMemosForTabNotes(selectedYear, setOptions()));
			  }
			});
		
		notesTableView.setItems(memos);
		notesTableView.setFixedCellSize(30);
		notesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		
		TableColumn<Memo2DTO, String> Col1 = new TableColumn<Memo2DTO, String>("MEM");
		Col1.setCellValueFactory(new PropertyValueFactory<Memo2DTO, String>("membershipId"));
		
		TableColumn<Memo2DTO, String> Col2 = new TableColumn<Memo2DTO, String>("DATE");
		Col2.setCellValueFactory(new PropertyValueFactory<Memo2DTO, String>("memo_date"));
		
		TableColumn<Memo2DTO, String> Col3 = new TableColumn<Memo2DTO, String>("TYPE");
		Col3.setCellValueFactory(new PropertyValueFactory<Memo2DTO, String>("category"));
		
		TableColumn<Memo2DTO, String> Col4 = new TableColumn<Memo2DTO, String>("NOTE");
		Col4.setCellValueFactory(new PropertyValueFactory<Memo2DTO, String>("memo"));

		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // Mem 5%
		Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // Join Date 15%
		Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // Type
		Col4.setMaxWidth( 1f * Integer.MAX_VALUE * 80 );   // Slip

		Collections.sort(memos, Comparator.comparing(Memo2DTO::getMemo_date).reversed());


		oCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            	o = newValue;
				memos.clear();
				memos.addAll(SqlMemos.getAllMemosForTabNotes(selectedYear, setOptions()));
            }
        });
		
		nCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            	n = newValue;
				memos.clear();
				memos.addAll(SqlMemos.getAllMemosForTabNotes(selectedYear, setOptions()));
            }
        });
		
		pCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            	p = newValue;
				memos.clear();
				memos.addAll(SqlMemos.getAllMemosForTabNotes(selectedYear, setOptions()));
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
	
	private String setOptions() {
		String result = "";
		boolean oneb4 = false;
		if(n) {
			result = "'N'";
			oneb4=true;
		}
		if(p) {
			if(oneb4) result+=",";
			oneb4=true;
			result += "'P'";
		}
		if(o) {
			if(oneb4) result+=",";
			oneb4=true;
			result += "'O'";
		}
		System.out.println(result);
		return result;
	}
	
}
