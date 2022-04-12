package com.ecsail.gui.jotform;

import com.ecsail.structures.jotform.JotFormSubmissionListDTO;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;


public class TableViewCreator {
    ObservableList<JotFormSubmissionListDTO> list;

    public TableViewCreator(ObservableList<JotFormSubmissionListDTO> list)  {
        this.list = list;
    }

    public TableView<JotFormSubmissionListDTO> getContent() {
        TableView<JotFormSubmissionListDTO> tableView = new TableView<>();
        // create columns
        TableColumn<JotFormSubmissionListDTO, String> Col1 = new TableColumn<>("Created");
        TableColumn<JotFormSubmissionListDTO, String> Col2 = new TableColumn<>("Status");
        TableColumn<JotFormSubmissionListDTO, Boolean> newCol = new TableColumn<>("Viewed");
        TableColumn<JotFormSubmissionListDTO, Boolean> flagCol = new TableColumn<>("Flagged");
        TableColumn<JotFormSubmissionListDTO, String> Col5 = new TableColumn<>("First Name");
        TableColumn<JotFormSubmissionListDTO, String> Col6 = new TableColumn<>("Last Name");
        TableColumn<JotFormSubmissionListDTO, String> Col7 = new TableColumn<>("Address");
        TableColumn<JotFormSubmissionListDTO, String> Col8 = new TableColumn<>("City");
        TableColumn<JotFormSubmissionListDTO, String> Col9 = new TableColumn<>("State");
        TableColumn<JotFormSubmissionListDTO, String> Col10 = new TableColumn<>("Zip");
        // set cell factories for content
        newCol.setCellValueFactory(cellData -> cellData.getValue().isNewProperty());
        flagCol.setCellValueFactory(cellData -> cellData.getValue().isNewProperty());

        Col1.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        Col2.setCellValueFactory(new PropertyValueFactory<>("status"));

        newCol.setCellFactory(col -> {
            TableCell<JotFormSubmissionListDTO, Boolean> cell = new TableCell<>();
            cell.itemProperty().addListener((obs, old, newVal) -> {
                if (newVal != null) {
                    Node centreBox = createPriorityGraphic(newVal);
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));
                }

            });
            return cell;
        });
        Col1.setCellFactory(col -> {
            TableCell<JotFormSubmissionListDTO, String> cell = new TableCell<>();
            cell.itemProperty().addListener((obs, old, newVal) -> {
                if (newVal != null) {
                    Node centreBox = createText(newVal);
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));
                }

            });
            return cell;
        });
        flagCol.setCellValueFactory(new PropertyValueFactory<>("isFlagged"));
        Col5.setCellValueFactory(new PropertyValueFactory<>("primaryFirstName"));
        Col6.setCellValueFactory(new PropertyValueFactory<>("primaryLastName"));
        Col7.setCellValueFactory(new PropertyValueFactory<>("address"));
        Col8.setCellValueFactory(new PropertyValueFactory<>("city"));
        Col9.setCellValueFactory(new PropertyValueFactory<>("state"));
        Col10.setCellValueFactory(new PropertyValueFactory<>("postal"));

        tableView.setItems(list);
        tableView.setFixedCellSize(30);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
        VBox.setVgrow(tableView, Priority.ALWAYS);

        /// sets width of columns by percentage
        Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );   // Created
        Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );  // Status
        newCol.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // new?
        flagCol.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // flagged?
        Col5.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );   // First Name
        Col6.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // Last Name
        Col7.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );  // Address
        Col8.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // City
        Col9.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );  // State
        Col10.setMaxWidth( 1f * Integer.MAX_VALUE * 10 ); // Zip


        tableView.setRowFactory(tv -> {
            TableRow<JotFormSubmissionListDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    // int rowIndex = row.getIndex();
                    JotFormSubmissionListDTO clickedRow = row.getItem();
                    System.out.println(clickedRow.getAddress());
                }
//                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
//                    row.setContextMenu(new rosterContextMenu(row.getItem(), selectedYear));
//                }
            });
            return row;
        });

        tableView.getColumns()
                .addAll(Arrays.asList(newCol, Col1, Col2, flagCol, Col5, Col6, Col7, Col8, Col9, Col10));
        return tableView;
    }

    private Node createPriorityGraphic(Boolean isPriority){
        HBox graphicContainer = new HBox();
        graphicContainer.setAlignment(Pos.CENTER);
        if(isPriority){
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/icons/new.png")));
            imageView.setFitHeight(22);
            imageView.setPreserveRatio(true);
            graphicContainer.getChildren().add(imageView);
        }
        graphicContainer.setAlignment(Pos.CENTER);
//        graphicContainer.setStyle("-fx-background-color: #feffab;");  // yellow
        return graphicContainer;
    }

    private Node createText(String text) {
        String[] result = text.split(" ");
        HBox textContainer = new HBox();
        Text dateText = new Text(result[0]);
        Text timeText = new Text(result[1]);
        dateText.setFill(Color.BLUE);
//        timeText.setFill(Color.CORNFLOWERBLUE);
        textContainer.getChildren().addAll(dateText,timeText);
        textContainer.setAlignment(Pos.CENTER);
        textContainer.setSpacing(3);
        return textContainer;
    }



}
