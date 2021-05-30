package com.ecsail.main;

import java.sql.SQLException;

import com.ecsail.gui.boxes.BoxToolBar;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_TupleCount;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
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
static Launcher vboxMain;
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
	launch(args);
}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//////////////////  OBJECTS  //////////////////////
		mainPane = new BorderPane();
		vboxMain = new Launcher();  // This one is for a single membership
		pStage = primaryStage;
		//Pane topPane = new Pane();
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));
		Main.mainScene = new Scene(mainPane, 1028, 768, Color.GREEN);
		
		/////////////////  LISTENERS ///////////////////////
		
		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						System.out.println("Application Closed by click to Close Button(X)");
						// close MySql connection
						connect.close();
						// close ssh Tunnel connection
						System.exit(0);
					}
				});
			}
		});
				
		//mainScene.heightProperty().addListener((obs, oldVal, newVal) -> {
		//	System.out.println("Scene height=" + newVal);
		//	System.out.println("TabPane height=" + Launcher.getTabPane().getHeight());
		//});  /// 545 start height
		
		/////////////////   ATTRIBUTES /////////////////////
		vboxMain.setStyle("-fx-background-color: #e83115;");  // red
		mainPane.setStyle("-fx-background-color: #feffab;");  // yellow
		mainScene.getStylesheets().add("stylesheet.css");
		toolbar.setPrefWidth(1029);
		toolbar.setId("toolbar-box");
		toolbar.setPrefHeight(10);
		primaryStage.setTitle("ECSC Membership Database (not connected)");
		
		////////////////   SET CONTENT ////////////////////
		
		primaryStage.getIcons().add(mainIcon);
		mainPane.setCenter(vboxMain);
		mainPane.setTop(toolbar);
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
