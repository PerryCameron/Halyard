package com.ecsail.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.Light.Point;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
 
public class TestClass3 extends Application {
     
    public static void main(String[] args) {
        launch();
    }
 
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        WebView wv = new WebView();
        final Rectangle selection = new Rectangle();
        final Point anchor = new Point();
 
        wv.setOnMousePressed(event -> {
            anchor.setX(event.getX());
            anchor.setY(event.getY());
            selection.setX(event.getX());
            selection.setY(event.getY());
            selection.setFill(null); // transparent 
            selection.setStroke(Color.BLACK); // border
            selection.getStrokeDashArray().add(10.0);
          root.getChildren().add(selection);
        });
         
        wv.setOnMouseDragged(event -> {
            selection.setWidth(Math.abs(event.getX() - anchor.getX()));
            selection.setHeight(Math.abs(event.getY() - anchor.getY()));
            selection.setX(Math.min(anchor.getX(), event.getX()));
            selection.setY(Math.min(anchor.getY(), event.getY()));
        });
         
        wv.setOnMouseReleased(event -> {
            // Do what you want with selection's properties here
            System.out.printf("X: %.2f, Y: %.2f, Width: %.2f, Height: %.2f%n", 
                    selection.getX(), selection.getY(), selection.getWidth(), selection.getHeight());
            root.getChildren().remove(selection);
            selection.setWidth(0);
            selection.setHeight(0);
        });
             
        root.getChildren().add(wv);
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Primary Stage");
        primaryStage.show();
    }
     
}