package com.ecsail.gui.tabs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.ecsail.main.ImageViewPane;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ImageResizer extends Application {
    @Override
    public void start(Stage primaryStage) {
        MenuBar menuBar=new MenuBar();
        Menu menuGame = new Menu("Game");
        MenuItem newGame = new MenuItem("New Game                F1");
        MenuItem exit = new MenuItem("Exit                            F2");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
        menuGame.getItems().addAll(newGame,new SeparatorMenuItem(),exit);
        menuBar.getMenus().addAll(menuGame);
		FileInputStream input = null;
		try {
			input = new FileInputStream("C:/Users/pcame/Documents/ECSC/Boats/msid100/IMG_1115.jpeg");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String localUrl = "C:/Users/pcame/Documents/ECSC/Boats/msid100/IMG_1115.jpeg";
		//localUrl = file.toURI().toURL().toString();
		Image image = new Image(input);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setPreserveRatio(true);

        ImageViewPane viewPane = new ImageViewPane(imageView);

        VBox vbox=new VBox();
        StackPane root=new StackPane();
        root.getChildren().addAll(viewPane);

        vbox.getChildren().addAll(menuBar,root);
        VBox.setVgrow(root, Priority.ALWAYS);

        Scene scene= new Scene(vbox,200,200);
        primaryStage.setScene(scene);

        //primaryStage.setMaxHeight(400);
        //primaryStage.setMinHeight(200);
        //primaryStage.setMaxWidth(500);
        //primaryStage.setMinWidth(400);

        primaryStage.setTitle("Minesweeper");
        primaryStage.show();

    }
    public static void main(String[] args) { launch(); }
}





