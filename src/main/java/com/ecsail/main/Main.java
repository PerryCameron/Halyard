package com.ecsail.main;

import java.io.File;
import java.sql.SQLException;

import com.ecsail.gui.BoxToolBar;
import com.ecsail.structures.Object_MembershipList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;


public class Main extends Application {
static ConnectDatabase connect;
public static ObservableList<Object_MembershipList> activememberships;
static BorderPane mainPane;
static TabLauncher mainViewPane;
public static BoxConsole console;
static String ipaddress;

public static void main(String[] args) throws SQLException {
        System.out.println("Starting application...");
        setUpForFirstTime();
        console = new BoxConsole();
        // this is the tree trunk to the entire program
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		mainPane = new BorderPane();
		mainViewPane = new TabLauncher();  // This one is for a single membership
		Pane topPane = new Pane();
		VBox toolbar = new BoxToolBar();
		
		//////////////////  OBJECTS  //////////////////////
		final Scene scene = new Scene(root, 1028, 768, Color.WHITE);

		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {

	         @Override
	         public void handle(WindowEvent event) {
	             Platform.runLater(new Runnable() {
	                 @Override
	                 public void run() {
	                     System.out.println("Application Closed by click to Close Button(X)");
	                     connect.close();
	                     System.exit(0);
	                 }
	             });
	         }
	     });
		
		/////////////////   ATTRIBUTES /////////////////////
		scene.getStylesheets().add("stylesheet.css");

		////////////////   SET CONTENT ////////////////////
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));
		primaryStage.setTitle("ECSC Membership Database (not connected)");
		primaryStage.getIcons().add(mainIcon);
		toolbar.setId("toolbar-box");
		toolbar.setPrefSize(1028, 10);
		topPane.getChildren().add(toolbar);
		mainPane.setTop(topPane);
		mainPane.setCenter(mainViewPane);
		mainPane.setPrefWidth(Double.MAX_VALUE);
		root.getChildren().addAll(mainPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		connect = new ConnectDatabase(primaryStage);
	}
	
	public static Window getPrimaryStage() {  // this is used for alerts
		return null;
	}
	
	public static void setUpForFirstTime() {
		File recordsDir = new File(System.getProperty("user.home"), "/.ecsc/scripts");
		if (!recordsDir.exists()) {
			System.out.println("Creating dir: " + System.getProperty("user.home") + "/.ecsc/scripts"); // USERFILETEMPLATE
		    recordsDir.mkdirs();
		}
	}

}
