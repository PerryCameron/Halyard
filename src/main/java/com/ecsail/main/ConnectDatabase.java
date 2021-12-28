package com.ecsail.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ecsail.gui.boxes.HBoxWelcome;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.structures.Object_Login;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

	public static Connection sqlConnection;
	public PortForwardingL sshConnection;
	private double titleBarHeight;
	private Object_Login currentLogon;
	private String port; 
	private boolean connectionSucess; 
	private ObservableList<String> choices = FXCollections.observableArrayList();
	private String exception = "";
	// used in class methods
	CheckBox defaultCheck;
	CheckBox useSshTunnel;
	ComboBox<String> hostName;
	TextField portText;
	TextField hostNameField;
	TextField sshUser;
	TextField sshPass;
	TextField userName;
	TextField passWord;

	public ConnectDatabase(Stage primaryStage) {
		if (FileIO.hostFileExists()) {
			FileIO.openLoginObjects();
			this.currentLogon = FileIO.logins.get(0);
			this.port = currentLogon.getPort();
			loadHostsInComboBox();
		}
		Launcher.openLoginTab(); // makes it look nice, tab not for anything useful
		displayLogOn(primaryStage);
	}
	
	public ConnectDatabase() { // default constructor
	}

	public static Connection getSqlConnection() {
		return sqlConnection;
	}

	public PortForwardingL getSshConnection() {
		return sshConnection;
	}


	public void displayLogOn(Stage primaryStage) {
		int width = 500;
		int height = 200;
		Stage logonStage = new Stage();
		logonStage.setTitle("Login");
		Image ecscLogo = new Image(getClass().getResourceAsStream(HalyardPaths.LOGO));
		ImageView logo = new ImageView(ecscLogo);
		VBox vboxBlue = new VBox();
		VBox vboxLeft = new VBox(); // this creates a pink border around the table
		Pane loginPane = new Pane();
		Scene secondScene = new Scene(loginPane, width, height);
		
		HBox errorHBox = new HBox();  // for displaying errors above
		HBox vboxUserLabel = new HBox();
		HBox vboxUserText = new HBox();
		HBox vboxPassLabel = new HBox();
		HBox vboxPassText = new HBox();
		HBox vboxHostLabel = new HBox();
		HBox vboxHostText = new HBox();
		HBox vboxHostLabel2 = new HBox();
		HBox vboxHostText2 = new HBox();
		HBox vboxSshUserLable = new HBox();
		HBox vboxSshUserText = new HBox();
		HBox vboxSshPassLable = new HBox();
		HBox vboxSshPassText = new HBox();
		HBox vboxPortLabel = new HBox();
		HBox vboxPortText = new HBox();
		HBox infoBox1 = new HBox();
		HBox infoBox2 = new HBox(); 
		HBox infoBox3 = new HBox();
		HBox infoBox4 = new HBox();
		HBox infoBox5 = new HBox();	
		HBox infoBox6 = new HBox();
		HBox infoBox7 = new HBox();	
		HBox infoBox8 = new HBox();
		HBox buttonBox1 = new HBox(); // for login and cancel buttons
		HBox buttonBox2 = new HBox(); // for save and cancel buttons
		HBox buttonBox3 = new HBox(); // for 
		HBox addBox = new HBox();
		HBox mainHBox = new HBox();
		VBox vboxRight = new VBox();
		
		this.hostName = new ComboBox<String>(choices);
		this.defaultCheck = new CheckBox("Default Login");
		this.useSshTunnel = new CheckBox("Use ssh tunnel");
		this.portText = new TextField();
		this.hostNameField = new TextField();
		this.sshUser = new TextField();
		this.sshPass = new PasswordField();
		this.userName = new TextField();
		this.passWord = new PasswordField();
		Button loginButton = new Button("Login");
		Button cancelButton1 = new Button("Cancel");
		Button cancelButton2 = new Button("Cancel");
		Button cancelButton3 = new Button("Cancel");
		Button saveButton1 = new Button("Save");
		Button saveButton2 = new Button("Save");
		Button deleteButton = new Button("Delete");
		Text newConnectText = new Text("New");
		Text editConnectText = new Text("Edit");
		
		///////////////////// ATTRIBUTES //////////////////////////
		
		/*  // for testing
		infoBox8.setStyle("-fx-background-color: #c5c7c1;");  // gray
		infoBox1.setStyle("-fx-background-color: #4d6955;");  //green
		infoBox2.setStyle("-fx-background-color: #feffab;");  // yellow
		infoBox3.setStyle("-fx-background-color: #e83115;");  // red
		infoBox4.setStyle("-fx-background-color: #201ac9;");  // blue
		infoBox5.setStyle("-fx-background-color: #e83115;");  // purble
		infoBox6.setStyle("-fx-background-color: #15e8e4;");  // light blue
		infoBox7.setStyle("-fx-background-color: #e89715;");  // orange
		*/
		
		infoBox1.setPadding(new Insets(20,5,5,5));
		infoBox2.setPadding(new Insets(5,5,5,5));
		infoBox3.setPadding(new Insets(5,5,5,5));
		infoBox8.setPadding(new Insets(15,5,25,5));
		buttonBox1.setPadding(new Insets(0,0,0,35));
		buttonBox2.setPadding(new Insets(0,0,0,60));
		vboxRight.setPadding(new Insets(20,20,0,20));
		vboxLeft.setPadding(new Insets(0,0,0,15));
		vboxBlue.setPadding(new Insets(10,10,10,10));
		infoBox5.setPadding(new Insets(5,5,5,5));  
		vboxSshUserLable.setPadding(new Insets(5,5,5,5));
		vboxSshUserText.setPadding(new Insets(5,5,5,5));
		vboxSshPassLable.setPadding(new Insets(5,5,5,5));
		vboxSshPassText.setPadding(new Insets(5,5,5,5));
		vboxPortText.setPadding(new Insets(5,5,5,0));
		vboxPortLabel.setPadding(new Insets(5,5,5,5));
		
		vboxUserLabel.setAlignment(Pos.CENTER_LEFT);
		vboxPassLabel.setAlignment(Pos.CENTER_LEFT);
		vboxHostLabel.setAlignment(Pos.CENTER_LEFT);
		vboxHostLabel2.setAlignment(Pos.CENTER_LEFT);
		vboxSshUserLable.setAlignment(Pos.CENTER_LEFT);
		vboxSshPassLable.setAlignment(Pos.CENTER_LEFT);
		vboxPortLabel.setAlignment(Pos.CENTER_LEFT);
		vboxPortText.setAlignment(Pos.CENTER_LEFT);
		infoBox4.setAlignment(Pos.CENTER_LEFT);
		addBox.setAlignment(Pos.CENTER_LEFT);
		infoBox5.setAlignment(Pos.CENTER);

		userName.setPromptText("Username");
		passWord.setPromptText("Password");

		//Pane secondaryLayout = new Pane();
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));

		portText.setText("3306");
		vboxUserLabel.setPrefWidth(90);
		vboxPassLabel.setPrefWidth(90);
		vboxHostLabel.setPrefWidth(90);
		vboxHostLabel2.setPrefWidth(90);
		vboxSshUserLable.setPrefWidth(90);
		vboxSshPassLable.setPrefWidth(90);
		vboxPortLabel.setPrefWidth(90);
		portText.setPrefWidth(60);
		
		userName.setPrefWidth(200);
		passWord.setPrefWidth(200);
		hostName.setPrefWidth(200);
		hostNameField.setPrefWidth(200);
		sshUser.setPrefWidth(200);
		sshPass.setPrefWidth(200);
		vboxPortText.setPrefWidth(200);
		vboxBlue.setPrefWidth(width);

		buttonBox1.setSpacing(10);
		buttonBox2.setSpacing(10);
		addBox.setSpacing(15);
		vboxPortText.setSpacing(20);	
		logonStage.setAlwaysOnTop(true);
		newConnectText.setFill(Color.CORNFLOWERBLUE);
		editConnectText.setFill(Color.CORNFLOWERBLUE);
		
		if(currentLogon != null) {  // only true if starting for first time
			populateFields();
		}
		secondScene.getStylesheets().add("login.css");
		mainHBox.setId("box-pink");
		vboxBlue.setId("box-blue");
		
		Platform.runLater(() -> userName.requestFocus());
		
		///////////////////// LISTENERS //////////////////////////  
		
		vboxBlue.heightProperty().addListener((obs, oldVal, newVal) -> {
			logonStage.setHeight((double)newVal + titleBarHeight);
			//System.out.println("vboxBlue=" + newVal + " set stage to" + logonStage.getHeight() + "scene=" + secondScene.getHeight());
		});  
		
		setMouseListener(newConnectText);
		
		setMouseListener(editConnectText);
		
		// when host name combo box changes
		hostName.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			currentLogon = FileIO.logins.get(FileIO.getSelectedHost(options.getValue()));
			populateFields();
        });
		
		// creates screen to add new host
		newConnectText.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1) {
				clearFields();
				infoBox3.getChildren().clear();
				infoBox3.getChildren().addAll(vboxHostLabel2, vboxHostText2);
				infoBox4.getChildren().addAll(vboxPortLabel, vboxPortText);
				infoBox5.getChildren().add(useSshTunnel);
				infoBox6.getChildren().addAll(vboxSshUserLable,vboxSshUserText);
				infoBox7.getChildren().addAll(vboxSshPassLable,vboxSshPassText);
				infoBox8.getChildren().clear();
				infoBox8.getChildren().add(buttonBox3);
			}
		});
		
		// edits currently selected host
		editConnectText.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1) {
					//infoBox5.setPadding(new Insets(5,5,5,5));
					infoBox3.getChildren().clear();
					infoBox3.getChildren().addAll(vboxHostLabel2, vboxHostText2);
					infoBox4.getChildren().addAll(vboxPortLabel, vboxPortText);
					infoBox5.getChildren().add(useSshTunnel);
					infoBox6.getChildren().addAll(vboxSshUserLable,vboxSshUserText);
					infoBox7.getChildren().addAll(vboxSshPassLable,vboxSshPassText);
					infoBox8.getChildren().clear();
					infoBox8.getChildren().add(buttonBox2);
			}
		});
		
		// takes you back to original screen
		cancelButton2.setOnAction((event) -> {
			populateFields();
			infoBox3.getChildren().clear();
			infoBox3.getChildren().addAll(vboxHostLabel, vboxHostText);
			infoBox4.getChildren().clear();
			infoBox5.getChildren().clear();
			infoBox5.setPadding(Insets.EMPTY);
			infoBox6.getChildren().clear();
			infoBox7.getChildren().clear();
			infoBox8.getChildren().clear();
			infoBox8.getChildren().addAll(addBox, buttonBox1);
		});
		
		/// duplicated cancelButton 3 for simplicity
		cancelButton3.setOnAction((event) -> {
			infoBox3.getChildren().clear();
		    infoBox3.getChildren().addAll(vboxHostLabel, vboxHostText);
			infoBox4.getChildren().clear();
			infoBox5.getChildren().clear();
			infoBox5.setPadding(Insets.EMPTY);
			infoBox6.getChildren().clear();
			infoBox7.getChildren().clear();
			infoBox8.getChildren().clear();
			infoBox8.getChildren().addAll(addBox, buttonBox1);
		});
		
        loginButton.setOnAction((event) -> {
        		String user = userName.getText();
        		String pass = passWord.getText();
        		String host = hostName.getValue();
        		System.out.println("Host is " + host);
        		String sUser = sshUser.getText();
        		String sPass = sshPass.getText();
        		String loopback = "127.0.0.1";
        		// create ssh tunnel
        		if(currentLogon.isSshForward()) {
        			System.out.println("SSH tunnel enabled - test");
        			this.sshConnection = new PortForwardingL(host,loopback,3306,3306,sUser,sPass); 
        		} else System.out.println("SSH connection is not being used");
        		// create mysql login
        		if(createConnection(user, pass, loopback, port)) {
        		Main.activememberships = SqlMembershipList.getRoster(Main.selectedYear, true);
        		logonStage.close();
        		primaryStage.setTitle("ECSC Membership Database (connected) " + host);
        		} else {
        			primaryStage.setTitle("ECSC Membership Database (not connected)");
        			System.out.println(exception);
        		}
        });
        
        // deletes log on from list
		deleteButton.setOnAction((event) -> {
				int element = FileIO.getSelectedHost(currentLogon.getHost());
				if (element >= 0) {
					FileIO.logins.remove(element);
					FileIO.saveLoginObjects();
					removeHostFromComboBox(hostNameField.getText());
					// should probably set combo box to default here
	            	cancelButton2.fire(); // refresh login back to original
				} else {
					System.out.println("need to build error for removing element");
				}
			});
		
		// saves new login object
        saveButton1.setOnAction((event) -> {
            	FileIO.logins.add(new Object_Login(portText.getText(), hostNameField.getText(), userName.getText(), passWord.getText(), sshUser.getText(),sshPass.getText(), defaultCheck.isSelected(), useSshTunnel.isSelected()));
            	FileIO.saveLoginObjects();
            	choices.add(hostNameField.getText());  // add new host name into combo box
            	hostName.setValue(hostNameField.getText());  // set combo box default to new host name
            	cancelButton2.fire(); // refresh login back to original
            });
        
		// saves changes to existing login object
        saveButton2.setOnAction((event) -> {
            	int element = FileIO.getSelectedHost(currentLogon.getHost());  // get element number
            	String oldHost = currentLogon.getHost(); // save hostname for later
            	if(element >= 0) {  // the element exists, why wouldn't it exist
            		// change the specific login in the login list
            		FileIO.logins.get(element).setHost(hostNameField.getText());
            		FileIO.logins.get(element).setUser(userName.getText());
            		FileIO.logins.get(element).setPasswd(passWord.getText());
            		FileIO.logins.get(element).setPort(portText.getText());
            		FileIO.logins.get(element).setSshUser(sshUser.getText());
            		FileIO.logins.get(element).setSshPass(sshPass.getText());
            		FileIO.logins.get(element).setDefault(defaultCheck.isSelected());
            		FileIO.logins.get(element).setSshForward(useSshTunnel.isSelected());
            		
            		FileIO.saveLoginObjects();
            		updateHostInComboBox(oldHost, hostNameField.getText());
            		hostName.setValue(hostNameField.getText());
            		cancelButton2.fire(); // refresh login back to original
            	} else {
            		System.out.println("need to build error for non matching host here");
            	}
            });
        
        // exits program 
        cancelButton1.setOnAction((event) -> System.exit(0));

        /////////////// SET CONTENT /////////////////////
		logonStage.getIcons().add(mainIcon);
		
        vboxUserLabel.getChildren().add(new Label("Username:"));
        vboxPassLabel.getChildren().add(new Label("Password:"));
        vboxHostLabel.getChildren().add(new Label("Hostname:"));
        vboxHostLabel2.getChildren().add(new Label("Hostname:"));
        vboxSshUserLable.getChildren().add(new Label("ssh user:"));
        vboxSshPassLable.getChildren().add(new Label("ssh pass:"));
		vboxPortLabel.getChildren().add(new Label("Port:"));
		vboxPortText.getChildren().addAll(portText, defaultCheck);
        vboxUserText.getChildren().add(userName);
        vboxPassText.getChildren().add(passWord);
        vboxHostText.getChildren().add(hostName);
        vboxHostText2.getChildren().add(hostNameField);
        vboxSshUserText.getChildren().add(sshUser);
		vboxSshPassText.getChildren().add(sshPass);
		addBox.getChildren().addAll(newConnectText, editConnectText);
        buttonBox1.getChildren().addAll(loginButton,cancelButton1);
        buttonBox2.getChildren().addAll(saveButton2,deleteButton,cancelButton3);
        buttonBox3.getChildren().addAll(saveButton1,cancelButton2);
        
		infoBox1.getChildren().addAll(vboxUserLabel, vboxUserText);
		infoBox2.getChildren().addAll(vboxPassLabel, vboxPassText);
		infoBox3.getChildren().addAll(vboxHostLabel, vboxHostText);
        infoBox8.getChildren().addAll(addBox, buttonBox1);
        
        vboxLeft.getChildren().addAll(errorHBox,infoBox1, infoBox2, infoBox3, infoBox4, infoBox5, infoBox6, infoBox7, infoBox8);
        vboxRight.getChildren().addAll(logo);
        
		logonStage.setX(primaryStage.getX() + 260);
		logonStage.setY(primaryStage.getY() + 300);
		mainHBox.getChildren().addAll(vboxLeft, vboxRight);
		vboxBlue.getChildren().add(mainHBox);
		loginPane.getChildren().add(vboxBlue);
		logonStage.setScene(secondScene);
		logonStage.show();
		float windowsOffsetHeight = 0.0f;
		if(HalyardPaths.isWindows()) {
			windowsOffsetHeight = -10.0f;
			vboxBlue.setPrefWidth(width - windowsOffsetHeight);
		}
		System.out.println(HalyardPaths.getOperatingSystem());
		this.titleBarHeight = logonStage.getHeight() - secondScene.getHeight();
		logonStage.setHeight(vboxBlue.getHeight() + titleBarHeight + windowsOffsetHeight);
		logonStage.setResizable(false);
	}
	
	///////////////  CLASS METHODS ///////////////////
	private void populateFields() {
		userName.setText(currentLogon.getUser());
		passWord.setText(currentLogon.getPasswd());
		hostName.setValue(currentLogon.getHost());
		hostNameField.setText(currentLogon.getHost());
		sshUser.setText(currentLogon.getSshUser());
		sshPass.setText(currentLogon.getSshPass());
		useSshTunnel.setSelected(currentLogon.isSshForward());
		defaultCheck.setSelected(currentLogon.isDefault());
	}
	
	private void clearFields() {
		userName.setText("");
		passWord.setText("");
		//hostName.setValue("");
		hostNameField.setText("");
		sshUser.setText("");
		sshPass.setText("");
		useSshTunnel.setSelected(true);
		defaultCheck.setSelected(false); // this needs to check other logons first
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
		String server = "jdbc:mysql://" + ip + ":" + port + "/ECSC_SQL?autoReconnect=true&useSSL=true&serverTimezone=UTC";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Connection: " + server);
			sqlConnection = DriverManager.getConnection(server, user, password);
			Launcher.closeActiveTab();
			//vboxGrey.getChildren().add();
			Launcher.openWelcomeTab(new HBoxWelcome());
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
			stmt = sqlConnection.createStatement();
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
			sqlConnection.close();
			System.out.println("SQL Connection to " + currentLogon.getHost() + " closed.");
//			sshConnection.getFtp().closeSession();
//			System.out.println("SFTP Connection closed");
//			sshConnection.getSession();
//			System.out.println("SSH Tunnel closed");
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

	public static Connection getConnection() {
		return sqlConnection;
	}

	public static void setConnection(Connection connection) {
		ConnectDatabase.sqlConnection = connection;
	}

	public PortForwardingL getForwardedConnection() {
		return sshConnection;
	}

	public void setForwardedConnection(PortForwardingL forwardedConnection) {
		this.sshConnection = forwardedConnection;
	}
	
	
}
