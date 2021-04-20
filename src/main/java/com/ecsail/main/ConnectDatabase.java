package com.ecsail.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ecsail.gui.boxes.BoxWelcome;
import com.ecsail.sql.Sql_SelectMembership;
import com.ecsail.structures.Object_Login;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConnectDatabase {

	public static Connection connection;
	int thisLogon = FileIO.getDefaultLogon();
	Object_Login currentLogon;
	String port; 
	private boolean connectionSucess; 
	ObservableList<String> choices = FXCollections.observableArrayList();
	protected String exception = "";
	boolean toggle = true;

	public ConnectDatabase(Stage primaryStage) {
		if (FileIO.hostFileExists()) {
			FileIO.openLoginObjects();
			this.currentLogon = FileIO.logins.get(0);
			this.port = currentLogon.getPort();
			loadHostsInComboBox();
		}

		//Launcher.openWelcomeTab(vboxGrey);
		Launcher.openLoginTab();
		
		displayLogOn(primaryStage);
	}
	
	public ConnectDatabase() { // default constructor
	}
	
	public void displayLogOn(Stage primaryStage) {
		int width = 490;
		int height = 200;
		Stage logonStage = new Stage();
		logonStage.setTitle("Login");
		VBox vboxBlue = new VBox();
		VBox vboxLeft = new VBox(); // this creates a pink border around the table
		Pane loginPane = new Pane();
		Scene secondScene = new Scene(loginPane, width, height);
		HBox errorHBox = new HBox();  // for displaying errors above
		HBox infoBox1 = new HBox();
		HBox infoBox2 = new HBox(); 
		HBox infoBox3 = new HBox();
		HBox infoBox4 = new HBox();
		HBox infoBox5 = new HBox();		
		HBox buttonBox = new HBox(); // for login and cancel buttons
		HBox buttonBox2 = new HBox(); // for save and cancel buttons
		HBox addBox = new HBox();
		HBox mainHBox = new HBox();
		VBox vboxRight = new VBox();
		
		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");
		ComboBox<String> hostName = new ComboBox<String>(choices);
		CheckBox defaultCheck = new CheckBox("Default");
		TextField portText = new TextField();
		TextField hostnameField = new TextField();
		Button loginButton = new Button("Login");
		Button cancelButton1 = new Button("Cancel");
		Button cancelButton2 = new Button("Cancel");
		Button saveButton1 = new Button("Save");
		Button saveButton2 = new Button("Save");
		Button deleteButton = new Button("Delete");
		Text newConnectText = new Text("New");
		Text editConnectText = new Text("Edit");
		//Pane secondaryLayout = new Pane();
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));
		logonStage.getIcons().add(mainIcon);
		///////////////////// ATTRIBUTES //////////////////////////
		

		infoBox4.setAlignment(Pos.CENTER_LEFT);
		portText.setText("3306");
		infoBox1.setSpacing(17);
		infoBox2.setSpacing(19);
		infoBox3.setSpacing(11);
		infoBox4.setSpacing(45.5);
		buttonBox2.setSpacing(5);
		portText.setPrefWidth(60);
		vboxLeft.setSpacing(10);
		vboxLeft.setPadding(new Insets(0,0,0,15));
		setMouseListener(newConnectText);
		setMouseListener(editConnectText);
		logonStage.setAlwaysOnTop(true);
		Image ecscLogo = new Image(getClass().getResourceAsStream(Paths.LOGO));
		ImageView logo = new ImageView(ecscLogo);
		addBox.setPadding(new Insets(0,0,0,20));
		addBox.setSpacing(15);
		buttonBox.setPadding(new Insets(0,0,0,35));
		buttonBox2.setPadding(new Insets(0,0,0,110));
		vboxRight.setPadding(new Insets(20,20,0,20));
		infoBox5.setPadding(new Insets(10,0,0,0));
		buttonBox.setSpacing(10);
		newConnectText.setFill(Color.CORNFLOWERBLUE);
		editConnectText.setFill(Color.CORNFLOWERBLUE);
		if(currentLogon != null) {  // only true if starting for first time
		username.setText(currentLogon.getUser());
		password.setText(currentLogon.getPasswd());
		hostName.setValue(currentLogon.getHost());
		}
		secondScene.getStylesheets().add("stylesheet.css");
		mainHBox.setId("box-pink");
		vboxBlue.setId("box-blue");
		//vboxRight.setId("box-grey");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxBlue.setPrefWidth(width);
		vboxBlue.setPrefHeight(height);
		vboxLeft.setPrefHeight(height - 20);
		infoBox5.setSpacing(10);
		username.setPrefWidth(200);
		password.setPrefWidth(200);
		hostName.setPrefWidth(200);
		hostnameField.setPrefWidth(200);
		
		Platform.runLater(() -> username.requestFocus());
		
		// when host name combo box changes
		hostName.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			currentLogon = FileIO.logins.get(FileIO.getSelectedHost(options.getValue()));
			//System.out.println(currentLogon.toString());
			username.setText(currentLogon.getUser());
			password.setText(currentLogon.getPasswd());
        });
		
		// creates screen to add new host
		newConnectText.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1) {
					infoBox4.getChildren().addAll(new Label("Port:"), portText);
					logonStage.setHeight(height + 69);
					vboxBlue.setPrefHeight(height + 30);
					vboxLeft.setPrefHeight(height + 30);
					username.setText("");
					password.setText("");
					buttonBox2.getChildren().clear();
					buttonBox2.getChildren().addAll(saveButton1,cancelButton2);
					infoBox5.getChildren().clear();
					infoBox5.getChildren().add(buttonBox2);
					infoBox3.getChildren().clear();
					infoBox3.getChildren().addAll(new Label("Hostname: "), hostnameField);
			}
		});
		
		// edits currently selected host
		editConnectText.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1) {
					infoBox4.getChildren().addAll(new Label("Port:"), portText, defaultCheck);
					logonStage.setHeight(height + 69);
					vboxBlue.setPrefHeight(height + 30);
					vboxLeft.setPrefHeight(height + 30);
					buttonBox2.getChildren().clear();
					buttonBox2.getChildren().addAll(saveButton2,deleteButton,cancelButton2);
					infoBox5.getChildren().clear();
					infoBox5.getChildren().add(buttonBox2);
					infoBox3.getChildren().clear();
					infoBox3.getChildren().addAll(new Label("Hostname: "), hostnameField);
					hostnameField.setText(currentLogon.getHost());
			}
		});
		
		// takes you back to original screen
		cancelButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
				infoBox4.getChildren().clear();
				logonStage.setHeight(height + 38);
				vboxBlue.setPrefHeight(200);
				vboxLeft.setPrefHeight(180);
				infoBox3.getChildren().clear();
				infoBox3.getChildren().addAll(new Label("Hostname: "), hostName);
				infoBox5.getChildren().clear();
				infoBox5.getChildren().addAll(addBox,buttonBox);
            }
        });
		
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
        		String user = username.getText();
        		String pass = password.getText();
        		String ip = hostName.getValue();
        		if(createConnection(user, pass, ip, port)) {
        		Main.activememberships = Sql_SelectMembership.getRoster(Main.selectedYear, true);
        		logonStage.close();
        		primaryStage.setTitle("ECSC Membership Database (connected) " + ip);
        		} else {
        			primaryStage.setTitle("ECSC Membership Database (not connected)");
        			System.out.println(exception);
        		}
            }
        });
        
        // deletes log on from list
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				int element = FileIO.getSelectedHost(currentLogon.getHost());
				if (element >= 0) {
					FileIO.logins.remove(element);
					FileIO.saveLoginObjects();
					removeHostFromComboBox(hostnameField.getText());
					// should probably set combo box to default here
	            	cancelButton2.fire(); // refresh login back to original
				} else {
					System.out.println("need to build error for removing element");
				}
			}
		});
		
		// saves new login object
        saveButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	FileIO.logins.add(new Object_Login(portText.getText(), hostnameField.getText(), username.getText(), password.getText(), false));
            	FileIO.saveLoginObjects();
            	choices.add(hostnameField.getText());  // add new host name into combo box
            	hostName.setValue(hostnameField.getText());  // set combo box default to new host name
            	cancelButton2.fire(); // refresh login back to original
            }
        });
        
		// saves changes to existing login object
        saveButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	int element = FileIO.getSelectedHost(currentLogon.getHost());  // get element number
            	String oldHost = currentLogon.getHost(); // save hostname for later
            	if(element >= 0) {  // the element exists, why wouldn't it exist
            		// change the specific login in the login list
            		FileIO.logins.get(element).setHost(hostnameField.getText());
            		FileIO.logins.get(element).setUser(username.getText());
            		FileIO.logins.get(element).setPasswd(password.getText());
            		FileIO.logins.get(element).setPort(portText.getText());
            		FileIO.saveLoginObjects();
            		updateHostInComboBox(oldHost, hostnameField.getText());
            		hostName.setValue(hostnameField.getText());
            		cancelButton2.fire(); // refresh login back to original
            	} else {
            		System.out.println("need to build error for non matching host here");
            	}
            }
        });
        
        // exits program 
        cancelButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	System.exit(0);
            }
        });
        
		infoBox1.getChildren().addAll(new Label("Username:"), username);
		infoBox2.getChildren().addAll(new Label("Password:"), password);
		infoBox3.getChildren().addAll(new Label("Hostname: "), hostName);
        vboxRight.getChildren().addAll(logo);
        buttonBox.getChildren().addAll(loginButton,cancelButton1);
        addBox.getChildren().addAll(newConnectText, editConnectText);
        infoBox5.getChildren().addAll(addBox,buttonBox);
        vboxLeft.getChildren().addAll(errorHBox,infoBox1, infoBox2, infoBox3, infoBox4, infoBox5);
		logonStage.setX(primaryStage.getX() + 260);
		logonStage.setY(primaryStage.getY() + 300);
		mainHBox.getChildren().addAll(vboxLeft, vboxRight);
		vboxBlue.getChildren().add(mainHBox);
		loginPane.getChildren().add(vboxBlue);
		logonStage.setScene(secondScene);
		logonStage.show();
	}
	
	private void updateHostInComboBox(String host, String newHost) {
		int count = 0;
		int choiceWanted = 0;
		for(String ho: choices) {
			if(host.equals(ho)) {
				choiceWanted = count;
			}
			count++;
		}
		choices.set(choiceWanted, newHost);
	}
	
	private void removeHostFromComboBox(String host) {
		int count = 0;
		int choiceWanted = 0;
		for(String ho: choices) {
			if(host.equals(ho)) {
				choiceWanted = count;
			}
			count++;
		}
		choices.remove(choiceWanted);
	}
	
	private void loadHostsInComboBox() {
		for (Object_Login l : FileIO.logins) {
			this.choices.add(l.getHost());
			//System.out.println("added: " + l);
		}
	}

	protected Boolean createConnection(String user, String password, String ip, String port) {
		Boolean sucessful = false;
		String server = "jdbc:mysql://" + ip + ":" + port + "/ECSC_SQL?autoReconnect=true&useSSL=true";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(server, user, password);
			Launcher.closeActiveTab();
			//vboxGrey.getChildren().add();
			Launcher.openWelcomeTab(new BoxWelcome());
			
			showStatus();
			sucessful = true;
			// Creating a Statement object
		} catch (ClassNotFoundException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
			exception = e.toString();
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
			//e.printStackTrace();
			exception = e.toString();
		}
		return sucessful;
	}
	
	private void setMouseListener(Text text) {
		text.setOnMouseExited(ex -> {
			text.setFill(Color.CORNFLOWERBLUE);
		});
		text.setOnMouseEntered(en -> {
			text.setFill(Color.RED);
		});
	}
	
	private void showStatus() {
		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SHOW SESSION STATUS LIKE \'Ssl_cipher\'");
		while (rs.next()) {
			System.out.print("Using " + rs.getString(2) + " encryption");
			System.out.println();
		}
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Retrieving the data
	}
	
	void close() {
		try {
			connection.close();
			System.out.println("Connection to " + currentLogon.getHost() + " closed.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Disconnection from " + currentLogon.getHost() + " Failed.");
			e.printStackTrace();
		}
	}

	public boolean isConnectionSucess() {
		return connectionSucess;
	}

	public void setConnectionSucess(boolean connectionSucess) {
		this.connectionSucess = connectionSucess;
	}
}
