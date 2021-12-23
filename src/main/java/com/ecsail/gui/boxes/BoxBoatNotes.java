package com.ecsail.gui.boxes;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

import com.ecsail.main.EditCell;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.Object_Boat;
import com.ecsail.structures.Object_BoatMemo;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class BoxBoatNotes extends HBox {
	private ObservableList<Object_BoatMemo> memos;
	
	public BoxBoatNotes(Object_Boat b) {
		
		
		//////////// OBJECTS ///////////////
		HBox hboxGrey = new HBox();  // this is the vbox for organizing all the widgets
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		VBox buttonVBox = new VBox();
		Button add = new Button("Add");
		Button delete = new Button("Delete");
		TableView<Object_BoatMemo> memoTableView = new TableView<Object_BoatMemo>();
		
		/////////////  ATTRIBUTES /////////////
		add.setPrefWidth(60);
		delete.setPrefWidth(60);

		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(memoTableView, Priority.ALWAYS);
		VBox.setVgrow(memoTableView, Priority.ALWAYS);
		
		buttonVBox.setSpacing(5);
		buttonVBox.setPrefWidth(80);
		hboxGrey.setSpacing(10);
		this.setPrefHeight(200);

		
		hboxGrey.setPadding(new Insets(5, 5, 5, 5));
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink fram around table
		this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		
		hboxGrey.setId("box-grey");
		vboxPink.setId("box-pink");
		this.setId("box-blue");
		
		memoTableView.setEditable(true);
		memoTableView.setItems(memos);
		memoTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		memoTableView.setFixedCellSize(30);

		TableColumn<Object_BoatMemo, String> Col1 = createColumn("Date", Object_BoatMemo::memo_dateProperty);
        Col1.setOnEditCommit(
                new EventHandler<CellEditEvent<Object_BoatMemo, String>>() {
                    @Override
                    public void handle(CellEditEvent<Object_BoatMemo, String> t) {
                        ((Object_BoatMemo) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setMemo_date(t.getNewValue());
                        int memo_id = ((Object_BoatMemo) t.getTableView().getItems().get(t.getTablePosition().getRow())).getBoat_id();
                        //note.updateMemo(memo_id, "memo_date", t.getNewValue());
                    }
                }
            );
		/// editable row that writes to database when enter is hit
        
		//TableColumn<Object_BoatMemo, String> Col2 = new TableColumn<Object_BoatMemo, String>("Type");
		//Col2.setCellValueFactory(new PropertyValueFactory<Object_BoatMemo, String>("category"));
        
		TableColumn<Object_BoatMemo, String> Col3 = createColumn("Note", Object_BoatMemo::memoProperty);
		Col3.setPrefWidth(740);
        Col3.setOnEditCommit(
                new EventHandler<CellEditEvent<Object_BoatMemo, String>>() {
                    @Override
                    public void handle(CellEditEvent<Object_BoatMemo, String> t) {
                       ((Object_BoatMemo) t.getTableView().getItems().get(t.getTablePosition().getRow())).setMemo(t.getNewValue());
                       int boat_memo_id = ((Object_BoatMemo) t.getTableView().getItems().get(t.getTablePosition().getRow())).getBoat_memo_id();
                       //note.updateMemo(memo_id, "memo", t.getNewValue());
                    }
                }
            );
        
		/// sets width of columns by percentage
		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );   // Date
		//Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );    // Type
		Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 85 );   // Note

        ////////////////  LISTENERS ///////////////////
        vboxPink.heightProperty().addListener((obs, oldVal, newVal) -> {
    		System.out.println("vboxPink height=" + newVal);
    		//memoTableView.setPrefHeight(newVal.floatValue());
    		});  /// 545 start height
        
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
            	int boat_memo_id = SqlSelect.getCount("boat_memo", "boat_memo_id") + 1; // gets last memo_id number and add one
             	memos.add(new Object_BoatMemo(boat_memo_id,b.getBoat_id(), date, "new note"));
            }
        });
        
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
			    int selectedIndex = memoTableView.getSelectionModel().getSelectedIndex();
			    if (selectedIndex >= 0) {  // something is selected
			    	//memos.removeMemo(selectedIndex);
			    	memoTableView.getItems().remove(selectedIndex);
			    }
			}
		});
        
        ///////////// SET CONTENT ////////////////////
        
		memoTableView.getColumns().addAll(Arrays.asList(Col1,Col3));
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
