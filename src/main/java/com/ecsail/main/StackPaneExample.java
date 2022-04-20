package com.ecsail.main;

import com.ecsail.gui.customwidgets.RoundCheckBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StackPaneExample extends Application {

    @Override
    public void start(Stage stage) {
        Pane mainPane = new BorderPane();

        // listener

        Scene scene = new Scene(mainPane, 200, 200);
        //Setting title to the Stage
        stage.setTitle("Stack Pane Example");

        RoundCheckBox roundCheckBox = new RoundCheckBox();
        roundCheckBox.isSelectedProperty().addListener((observableValue, aBoolean, t1) -> {
            System.out.println(t1);
        });
        mainPane.getChildren().add(roundCheckBox);
        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}