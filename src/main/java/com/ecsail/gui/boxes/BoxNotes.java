package com.ecsail.gui.boxes;


import java.util.function.Function;

import com.ecsail.main.EditCell;
import com.ecsail.main.Note;
import com.ecsail.structures.Object_Memo;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoxNotes extends HBox {
	private Note note;
	
	@SuppressWarnings("unchecked")
	public BoxNotes(Note n) {
		this.note = n;
		
		//////////// OBJECTS ///////////////
		HBox hboxGrey = new HBox();  // this is the vbox for organizing all the widgets
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		VBox buttonVBox = new VBox();
		Button add = new Button("Add");
		Button delete = new Button("Delete");
		
		/////////////  ATTRIBUTES /////////////
		add.setPrefWidth(60);
		delete.setPrefWidth(60);
		buttonVBox.setSpacing(5);
		hboxGrey.setSpacing(10);
		hboxGrey.setPadding(new Insets(5, 5, 5, 5));
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink fram around table
		hboxGrey.setId("box-grey");
		vboxPink.setId("box-pink");
		setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		setId("box-blue");
		hboxGrey.setPrefWidth(942);
		
		TableView<Object_Memo> memoTableView = new TableView<Object_Memo>();
		memoTableView.setEditable(true);
		memoTableView.setItems(note.getMemos());
		memoTableView.setPrefWidth(850);
		memoTableView.setPrefHeight(140);
		memoTableView.setFixedCellSize(30);

		TableColumn<Object_Memo, String> Col1 = createColumn("Date", Object_Memo::memo_dateProperty);
        Col1.setOnEditCommit(
                new EventHandler<CellEditEvent<Object_Memo, String>>() {
                    @Override
                    public void handle(CellEditEvent<Object_Memo, String> t) {
                        ((Object_Memo) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setMemo_date(t.getNewValue());
                        int memo_id = ((Object_Memo) t.getTableView().getItems().get(t.getTablePosition().getRow())).getMemo_id();
                        note.updateMemo(memo_id, "memo_date", t.getNewValue());
                    }
                }
            );
		/// editable row that writes to database when enter is hit
        
		TableColumn<Object_Memo, String> Col2 = createColumn("Note", Object_Memo::memoProperty);
		Col2.setPrefWidth(770);
        Col2.setOnEditCommit(
                new EventHandler<CellEditEvent<Object_Memo, String>>() {
                    @Override
                    public void handle(CellEditEvent<Object_Memo, String> t) {
                       ((Object_Memo) t.getTableView().getItems().get(t.getTablePosition().getRow())).setMemo(t.getNewValue());
                       int memo_id = ((Object_Memo) t.getTableView().getItems().get(t.getTablePosition().getRow())).getMemo_id();
                       note.updateMemo(memo_id, "memo", t.getNewValue());
                    }
                }
            );
        
        memoTableView.getSortOrder().addAll(Col1);
        memoTableView.sort();
		
        ////////////////  LISTENERS ///////////////////
        
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
             	note.add("new memo");
            }
        });
        
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
			    int selectedIndex = memoTableView.getSelectionModel().getSelectedIndex();
			    if (selectedIndex >= 0) {  // something is selected
			    	note.removeMemo(selectedIndex);
			    	memoTableView.getItems().remove(selectedIndex);
			    }
			}
		});
        
        ///////////// SET CONTENT ////////////////////
        
		memoTableView.getColumns().addAll(Col1,Col2);
		buttonVBox.getChildren().addAll(add,delete);
		vboxPink.getChildren().add(memoTableView);
		hboxGrey.getChildren().addAll(vboxPink,buttonVBox);
		getChildren().add(hboxGrey);
	}
	
	// This allows out of focus committ
    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }
}
