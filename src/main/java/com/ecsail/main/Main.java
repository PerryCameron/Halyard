package com.ecsail.main;

import java.sql.SQLException;

import com.ecsail.gui.boxes.VBoxToolBar;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_TupleCount;

import com.jcraft.jsch.JSchException;
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
static ConnectDatabase connect;  // mysql and ssh connection
public static Object_TupleCount edits = new Object_TupleCount();
public static ObservableList<Object_MembershipList> activememberships;
public static String selectedYear; 
static BorderPane mainPane;
static Launcher vboxMain;
public static BoxConsole console;
private static Stage pStage;
private static Scene mainScene;
static String ipaddress;
VBox toolbar = new VBoxToolBar();

public static void main(String[] args) throws SQLException {
	System.out.println("Starting application...");
	Main.selectedYear = HalyardPaths.getYear();
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
		Main.mainScene = new Scene(mainPane, 1028, 830, Color.GREEN);
		
		/////////////////  LISTENERS ///////////////////////

		// closing program with x button
		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						closeDatabaseConnection();
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
//		connect = new ConnectDatabase(primaryStage);
		connectDatabase();
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
		HalyardPaths.checkPath(System.getProperty("user.home") + "/.ecsc/scripts");
	}

	public static ConnectDatabase getConnect() {
		return connect;
	}

	public static void setConnect(ConnectDatabase connect) {
		Main.connect = connect;
	}

	public static void closeDatabaseConnection() {
		try {
			ConnectDatabase.getSqlConnection().close();
			System.out.println("SQL: Connection closed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// if ssh is connected then disconnect
		if(Main.getConnect().getSshConnection() != null)
			if(Main.getConnect().getSshConnection().getSession().isConnected()) {
				try {
					Main.getConnect().getSshConnection().getSession().delPortForwardingL(3306);
					Main.getConnect().getSshConnection().getSession().disconnect();
					System.out.println("SSH: port forwarding closed");
				} catch (JSchException e) {
					e.printStackTrace();
				}
			}
	}

	public static void connectDatabase() {
	connect = new ConnectDatabase((Stage) getPrimaryStage());
	}

}
