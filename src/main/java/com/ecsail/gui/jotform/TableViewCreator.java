package com.ecsail.gui.jotform;

import com.ecsail.structures.jotform.JotFormSubmissionListDTO;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;


public class TableViewCreator {
    ObservableList<JotFormSubmissionListDTO> list;

    public TableViewCreator(ObservableList<JotFormSubmissionListDTO> list)  {
        this.list = list;
    }

    public TableView<JotFormSubmissionListDTO> getContent() {
        TableView<JotFormSubmissionListDTO> tableView = new TableView<>();
        // create columns
        TableColumn<JotFormSubmissionListDTO, Boolean> Col4 = new TableColumn<>("Flagged");
        TableColumn<JotFormSubmissionListDTO, String> Col5 = new TableColumn<>("First Name");
        TableColumn<JotFormSubmissionListDTO, String> Col6 = new TableColumn<>("Last Name");
        TableColumn<JotFormSubmissionListDTO, String> Col7 = new TableColumn<>("Address");
        TableColumn<JotFormSubmissionListDTO, String> Col8 = new TableColumn<>("City");
        TableColumn<JotFormSubmissionListDTO, String> Col9 = new TableColumn<>("State");
        TableColumn<JotFormSubmissionListDTO, String> Col10 = new TableColumn<>("Zip");
        // set cell factories for content
        Col4.setCellValueFactory(new PropertyValueFactory<>("isFlagged"));
        Col5.setCellValueFactory(new PropertyValueFactory<>("primaryFirstName"));
        Col6.setCellValueFactory(new PropertyValueFactory<>("primaryLastName"));
        Col7.setCellValueFactory(new PropertyValueFactory<>("addrLine1"));
        Col8.setCellValueFactory(new PropertyValueFactory<>("city"));
        Col9.setCellValueFactory(new PropertyValueFactory<>("state"));
        Col10.setCellValueFactory(new PropertyValueFactory<>("postal"));

        tableView.setItems(list);
        tableView.setFixedCellSize(30);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );

        VBox.setVgrow(tableView, Priority.ALWAYS);

//        nmTableview.getColumns()
//                .addAll(Arrays.asList(Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11));
        tableView.getColumns()
                .addAll(Arrays.asList(Col4, Col5, Col6, Col7, Col8, Col9, Col10));
        return tableView;
    }


}
