package com.ecsail.main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Light.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
 
public class TestClass3 extends Application {
     
    public static void main(String[] args) {
        launch();
    }
 //192 x 222
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        WebView wv = new WebView();
        final Rectangle selection = new Rectangle();
        final Point anchor = new Point();
        //Image image = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/1/14/Gatto_europeo4.jpg/1024px-Gatto_europeo4.jpg");
        //ImageView imageView = new ImageView(image);
        //Group wv = new Group();
        //wv.getChildren().add( imageView);
        
        wv.setOnMousePressed(event -> {
            anchor.setX(event.getX());
            anchor.setY(event.getY());
            selection.setX(event.getX());
            selection.setY(event.getY());
            selection.setFill(null); // transparent 
            selection.setStroke(Color.RED); // border
            
            selection.getStrokeDashArray().add(5.0);
          root.getChildren().add(selection);
        });
         
       // wv.setOnMouseDragged(event -> {
       //     selection.setWidth(Math.abs(event.getX() - anchor.getX()));
       //     selection.setHeight(Math.abs(event.getY() - anchor.getY()));
       //     selection.setX(Math.min(anchor.getX(), event.getX()));
       //     selection.setY(Math.min(anchor.getY(), event.getY()));
       // });
        
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