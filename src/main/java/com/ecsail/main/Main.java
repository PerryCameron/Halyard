package com.ecsail.main;

import java.sql.SQLException;

import com.ecsail.gui.boxes.BoxToolBar;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_TupleCount;

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
static ConnectDatabase connect;  // mysql connection
public static Object_TupleCount edits = new Object_TupleCount();
public static ObservableList<Object_MembershipList> activememberships;
public static String selectedYear; 
static BorderPane mainPane;
static Launcher mainViewPane;
public static BoxConsole console;
private static Stage pStage;
private static Scene mainScene;
static String ipaddress;
VBox toolbar = new BoxToolBar();

public static void main(String[] args) throws SQLException {
	System.out.println("Starting application...");
	Main.selectedYear = Paths.getYear();
	Main.edits = FileIO.openTupleCountObject();
	setUpForFirstTime();
	
	console = new BoxConsole();
	// this is the tree trunk to the entire program
	launch(args);
}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		mainPane = new BorderPane();
		mainViewPane = new Launcher();  // This one is for a single membership
		pStage = primaryStage;
		Pane topPane = new Pane();
		//VBox toolbar = new BoxToolBar();
		
		//////////////////  OBJECTS  //////////////////////
		Main.mainScene = new Scene(root, 1028, 768, Color.WHITE);

		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						System.out.println("Application Closed by click to Close Button(X)");
						//close MySql connection
						connect.close();
						// close ssh Tunnel connection

						
						System.exit(0);
					}
				});
			}
		});
		

		/////////////////   ATTRIBUTES /////////////////////
		mainScene.getStylesheets().add("stylesheet.css");

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
		primaryStage.setScene(mainScene);
		primaryStage.show();
		connect = new ConnectDatabase(primaryStage);
	}
	
	//////////////  CLASS METHODS //////////////////////

	public static double getToolBarHeight() {
		return 10;
	}
	
	public static Window getPrimaryStage() {  // this is used for alerts
		return pStage;
	}
	
	public static Scene getPrimaryScene() {
		return mainScene;
	}
	
	public static void setUpForFirstTime() {
		Paths.checkPath(System.getProperty("user.home") + "/.ecsc/scripts");
	}

}
