package com.ecsail.gui.tabs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.ArrayList;

import com.ecsail.connection.Sftp;
import com.ecsail.enums.KeelType;
import com.ecsail.gui.boxes.HBoxBoatNotes;
import com.ecsail.gui.dialogues.Dialogue_ChooseMember;
import com.ecsail.main.ImageViewPane;
import com.ecsail.main.LoadFileChooser;
import com.ecsail.main.Halyard;
import com.ecsail.main.HalyardPaths;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.structures.BoatDTO;
import com.ecsail.structures.MembershipListDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class TabBoatView extends Tab {
	private ObservableList<MembershipListDTO> boatOwners;
	private int pictureNumber = 0;
	private File imagePath;
	private Sftp ftp;
	private ArrayList<String> localImageFiles = null;
	private ArrayList<String> remoteImageFiles = null;
	private BoatDTO b;
	/// need to add history to boat_owner table

	public TabBoatView(String text, BoatDTO b) {
		super(text);
		this.b = b;
		this.boatOwners = SqlMembershipList.getBoatOwnerRoster(b.getBoat_id());
//		this.ftp = Halyard.getConnect().getForwardedConnection().getFtp();
//		checkRemoteFiles();
		// make sure directory exists, and create it if it does not
		HalyardPaths.checkPath(HalyardPaths.BOATDIR + "/" + b.getBoat_id() + "/");
		this.imagePath = new File(HalyardPaths.BOATDIR + "/" + b.getBoat_id() + "/");
		this.localImageFiles = HalyardPaths.listFilesForFolder(imagePath);
		Image image = null;
		if (localImageFiles.size() > 0)
			image = getImage(HalyardPaths.BOATDIR + "/" + b.getBoat_id() + "/" + localImageFiles.get(pictureNumber));
//		checkIfLocalandRemoteDirectoriesMatch();

		TableView<MembershipListDTO> boatOwnerTableView = new TableView<>();
		VBox vboxGrey = new VBox(); // this is the hbox for holding all content
		VBox vboxBlue = new VBox(); // creates blue boarder around content
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		HBox hboxContainer = new HBox();
		VBox vboxLeftContainer = new VBox(); // contains boxes on left side
		VBox vboxRightContainer = new VBox(); // contains boxes on left side
		VBox vboxButtons = new VBox(); // holds phone buttons
		VBox vboxTableFrame = new VBox(); // holds table
		VBox vboxInformationBackgroundColor = new VBox();
		VBox vboxTableBackgroundColor = new VBox();

		HBox hbox1 = new HBox(); // Boat Name
		HBox hbox2 = new HBox(); // Manufacturer
		HBox hbox3 = new HBox(); // Year
		HBox hbox4 = new HBox(); // Model
		HBox hbox5 = new HBox(); // Registration
		HBox hbox6 = new HBox(); // Sail Number
		HBox hbox7 = new HBox(); // PHRF
		HBox hbox8 = new HBox(); // Length
		HBox hbox9 = new HBox(); // Weight
		HBox hbox10 = new HBox(); // Has Triler
		HBox hbox11 = new HBox(); // Keel Type
		HBox hbox12 = new HBox(); // Draft
		HBox hbox13 = new HBox(); // Beam
		HBox hbox14 = new HBox(); // lwl

		HBox hboxTable = new HBox();

		VBox vboxBnameLabel = new VBox();
		VBox vboxManufacturerLabel = new VBox();
		VBox vboxYearLabel = new VBox();
		VBox vboxModelLabel = new VBox();
		VBox vboxRegistrationLabel = new VBox();
		VBox vboxSailNumbeLabel = new VBox();
		VBox vboxphrfLabel = new VBox();
		VBox vboxlengthLabel = new VBox();
		VBox vboxweightLabel = new VBox();
		VBox vboxtrailerLabel = new VBox();
		VBox vboxkeelLabel = new VBox();
		VBox vboxDraftLabel = new VBox();
		VBox vboxBeamLabel = new VBox();
		VBox vboxLwlLabel = new VBox();

		VBox vboxBnameBox = new VBox();
		VBox vboxManufacturerBox = new VBox();
		VBox vboxYearBox = new VBox();
		VBox vboxModelBox = new VBox();
		VBox vboxRegistrationBox = new VBox();
		VBox vboxSailNumbeBox = new VBox();
		VBox vboxphrfBox = new VBox();
		VBox vboxlengthBox = new VBox();
		VBox vboxweightBox = new VBox();
		VBox vboxtrailerBox = new VBox();
		VBox vboxkeelBox = new VBox();
		VBox vboxDraftBox = new VBox();
		VBox vboxBeamBox = new VBox();
		VBox vboxLwlBox = new VBox();

		TextField bnameTextField = new TextField();
		TextField manufacturerTextField = new TextField();
		TextField yearTextField = new TextField();
		TextField modelTextField = new TextField();
		TextField registrationTextField = new TextField();
		TextField sailNumberTextField = new TextField();
		TextField phrfTextField = new TextField();
		TextField lengthTextField = new TextField();
		TextField weightTextField = new TextField();
		CheckBox trailerCheckBox = new CheckBox("Has Trailer");
		ComboBox<KeelType> keelComboBox = new ComboBox<KeelType>();
		TextField draftTextField = new TextField();
		TextField beamTextField = new TextField();
		TextField lwlTextField = new TextField();
		TitledPane ownerTitlePane = new TitledPane();
		TitledPane boatInfoTitlePane = new TitledPane();
		VBox vboxPicture = new VBox();
		HBox hboxPictureControls = new HBox();
		ImageView imageView = new ImageView();
		ImageViewPane viewPane = new ImageViewPane(imageView);
		Button buttonForward = new Button(">");
		Button buttonReverse = new Button("<");
		Button buttonAddPicture = new Button("Add");
		Button buttonDelete = new Button("Delete");
		
		TableColumn<MembershipListDTO, Integer> col1 = new TableColumn<MembershipListDTO, Integer>("MEM");
		TableColumn<MembershipListDTO, String> col2 = new TableColumn<MembershipListDTO, String>("Last Name");
		TableColumn<MembershipListDTO, String> col3 = new TableColumn<MembershipListDTO, String>("First Name");
		Button boatOwnerAdd = new Button("Add");
		Button boatOwnerDelete = new Button("Delete");

		/////////////// ATTRIBUTES ////////////////

		boatOwnerAdd.setPrefWidth(60);
		boatOwnerDelete.setPrefWidth(60);
		vboxButtons.setPrefWidth(80);
		vboxLeftContainer.setMaxWidth(350);
		hboxPictureControls.setPrefHeight(40);
		vboxPicture.setPrefWidth(630);
		vboxPicture.setPrefHeight(489);

		vboxBlue.setId("box-blue");
		vboxPink.setId("box-pink");
		vboxTableBackgroundColor.setId("box-grey");
		vboxInformationBackgroundColor.setId("box-grey");
		vboxTableFrame.setId("box-pink");

		// imageView.maxWidth(630);
		// imageView.setFitWidth(700);

		imageView.setSmooth(true);
		imageView.setPreserveRatio(true);
		imageView.setCache(true);

		// vboxGrey.setId("slip-box");
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		HBox.setHgrow(vboxGrey, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(boatOwnerTableView, Priority.ALWAYS);
		VBox.setVgrow(ownerTitlePane, Priority.ALWAYS);
		HBox.setHgrow(vboxRightContainer, Priority.ALWAYS);
		HBox.setHgrow(vboxPicture, Priority.ALWAYS);
		VBox.setVgrow(vboxPicture, Priority.ALWAYS);

		// vboxPicture.setStyle("-fx-background-color: #e83115;");
		// hboxPictureControls.setStyle("-fx-background-color: #201ac9;"); // blue

		// spacer.setPrefHeight(50);
		vboxLeftContainer.setSpacing(10);
		vboxButtons.setSpacing(5); // spacing between buttons
		vboxGrey.setSpacing(10);
		hboxPictureControls.setSpacing(10);

		hboxPictureControls.setAlignment(Pos.CENTER);
		// sets size of table
		// ownerTitlePane.setPrefHeight(130);
		bnameTextField.setPrefSize(150, 10);
		manufacturerTextField.setPrefSize(150, 10);
		yearTextField.setPrefSize(150, 10);
		modelTextField.setPrefSize(150, 10);
		registrationTextField.setPrefSize(150, 10);
		sailNumberTextField.setPrefSize(150, 10);
		phrfTextField.setPrefSize(150, 10);
		lengthTextField.setPrefSize(150, 10);
		weightTextField.setPrefSize(150, 10);
		keelComboBox.setPrefSize(150, 10);
		draftTextField.setPrefSize(150, 10);
		beamTextField.setPrefSize(150, 10);
		lwlTextField.setPrefSize(150, 10);

		bnameTextField.setText(b.getBoat_name());
		manufacturerTextField.setText(b.getManufacturer());
		yearTextField.setText(b.getManufacture_year());
		modelTextField.setText(b.getModel());
		registrationTextField.setText(b.getRegistration_num());
		sailNumberTextField.setText(b.getSail_number());
		phrfTextField.setText(b.getPhrf());
		lengthTextField.setText(b.getLength());
		weightTextField.setText(b.getWeight());
		trailerCheckBox.setSelected(b.isHasTrailer());
		keelComboBox.getItems().setAll(KeelType.values());
		keelComboBox.setValue(KeelType.getByCode(b.getKeel()));
		draftTextField.setText(b.getDraft());
		beamTextField.setText(b.getBeam());
		lwlTextField.setText(b.getLwl());
		ownerTitlePane.setText("Owner(s)");
		boatInfoTitlePane.setText("Boat Information");
		// need to continue with labels
		vboxBnameLabel.setPrefWidth(90);
		vboxManufacturerLabel.setPrefWidth(90);
		vboxYearLabel.setPrefWidth(90);
		vboxModelLabel.setPrefWidth(90);
		vboxRegistrationLabel.setPrefWidth(90);
		vboxSailNumbeLabel.setPrefWidth(90);
		vboxphrfLabel.setPrefWidth(90);
		vboxlengthLabel.setPrefWidth(90);
		vboxweightLabel.setPrefWidth(90);
		vboxtrailerLabel.setPrefWidth(90);
		vboxkeelLabel.setPrefWidth(90);
		vboxDraftLabel.setPrefWidth(90);
		vboxBeamLabel.setPrefWidth(90);
		vboxLwlLabel.setPrefWidth(90);

		vboxBnameLabel.setAlignment(Pos.CENTER_LEFT);
		vboxManufacturerLabel.setAlignment(Pos.CENTER_LEFT);
		vboxYearLabel.setAlignment(Pos.CENTER_LEFT);
		vboxModelLabel.setAlignment(Pos.CENTER_LEFT);
		vboxRegistrationLabel.setAlignment(Pos.CENTER_LEFT);
		vboxSailNumbeLabel.setAlignment(Pos.CENTER_LEFT);
		vboxphrfLabel.setAlignment(Pos.CENTER_LEFT);
		vboxlengthLabel.setAlignment(Pos.CENTER_LEFT);
		vboxweightLabel.setAlignment(Pos.CENTER_LEFT);
		vboxtrailerLabel.setAlignment(Pos.CENTER_LEFT);
		vboxkeelLabel.setAlignment(Pos.CENTER_LEFT);
		vboxDraftLabel.setAlignment(Pos.CENTER_LEFT);
		vboxBeamLabel.setAlignment(Pos.CENTER_LEFT);
		vboxLwlLabel.setAlignment(Pos.CENTER_LEFT);

		// vboxFieldsContainer.setStyle("-fx-background-color: #201ac9;"); // blue

		hbox1.setPadding(new Insets(10, 5, 5, 15));
		hbox2.setPadding(new Insets(0, 5, 5, 15));
		hbox3.setPadding(new Insets(0, 5, 5, 15));
		hbox4.setPadding(new Insets(0, 5, 5, 15));
		hbox5.setPadding(new Insets(0, 5, 5, 15));
		hbox6.setPadding(new Insets(0, 5, 5, 15));
		hbox7.setPadding(new Insets(0, 5, 5, 15));
		hbox8.setPadding(new Insets(0, 5, 5, 15));
		hbox9.setPadding(new Insets(0, 5, 5, 15));
		hbox10.setPadding(new Insets(0, 5, 5, 15));
		hbox11.setPadding(new Insets(0, 5, 5, 15));
		hbox12.setPadding(new Insets(0, 5, 5, 15));
		hbox13.setPadding(new Insets(0, 5, 5, 15));
		hbox14.setPadding(new Insets(0, 5, 5, 15));
		vboxButtons.setPadding(new Insets(0, 0, 0, 5));
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxTableBackgroundColor.setPadding(new Insets(10, 10, 10, 10));
		vboxTableFrame.setPadding(new Insets(2, 2, 2, 2));

		boatOwnerTableView.setItems(boatOwners);
		boatOwnerTableView.setFixedCellSize(30);
		boatOwnerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		boatOwnerTableView.setPrefHeight(90);

		col1.setCellValueFactory(new PropertyValueFactory<MembershipListDTO, Integer>("membershipId"));
		col2.setCellValueFactory(new PropertyValueFactory<MembershipListDTO, String>("lname"));
		col3.setCellValueFactory(new PropertyValueFactory<MembershipListDTO, String>("fname"));

		/// sets width of columns by percentage
		col1.setMaxWidth(1f * Integer.MAX_VALUE * 20); // Mem 5%
		col2.setMaxWidth(1f * Integer.MAX_VALUE * 40); // Join Date 15%
		col3.setMaxWidth(1f * Integer.MAX_VALUE * 40); // Type

		/////////////// LISTENERS ////////////////////
		
		imageView.setOnDragOver(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        /* data is dragged over the target */
		        /* accept it only if it is not dragged from the same node 
		         * and if it has a string data */
		        if (event.getGestureSource() != imageView &&
		                event.getDragboard().hasFiles()) {
		            /* allow for both copying and moving, whatever user chooses */
		            //event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
		        	event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
		        }
		        
		        event.consume();
		    }
		});
		
		imageView.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    //File fileName = db.getFiles().get(0);
                    String filename = getNewName(db.getFiles().get(0));
                    File newImage = new File(imagePath, filename);
                    copyFile(db.getFiles().get(0), newImage);
                    ftp.sendFile(imagePath + "/" + filename, "/home/pcameron/Documents/ECSC/Boats/" + b.getBoat_id() + "/" + filename);
        			localImageFiles.add(newImage.getName().toString());
                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });
		
		buttonDelete.setOnAction((event) -> {
			
		});

		buttonAddPicture.setOnAction((event) -> {
			LoadFileChooser fc = new LoadFileChooser(System.getProperty("user.home"));
			System.out.println(fc.getFile().toString());
			String filename = getNewName(fc.getFile());
			File newImage = new File(imagePath, filename);
			copyFile(fc.getFile(), newImage);
			ftp.sendFile(imagePath + "/" + filename, "/home/pcameron/Documents/ECSC/Boats/" + b.getBoat_id() + "/" + filename);
			localImageFiles.add(newImage.getName().toString());
		});

		buttonForward.setOnAction((event) -> {
			pictureNumber++;
			if (pictureNumber == localImageFiles.size())
				pictureNumber = 0;
			Image newImage = getImage(HalyardPaths.BOATDIR + "/" + b.getBoat_id() + "/" + localImageFiles.get(pictureNumber));
			imageView.setImage(newImage);
		});

		buttonReverse.setOnAction((event) -> {
			pictureNumber--;
			if (pictureNumber < 0)
				pictureNumber = localImageFiles.size() - 1;
			Image newImage = getImage(HalyardPaths.BOATDIR + "/" + b.getBoat_id() + "/" + localImageFiles.get(pictureNumber));
			imageView.setImage(newImage);
		});

		boatOwnerAdd.setOnAction((event) -> {
			// int phone_id = SqlSelect.getCount("phone", "phone_id"); // get last phone_id
			// number
			// phone_id++; // increase to first available number
			// if (SqlInsert.addRecord(phone_id, person.getP_id(), true, "new phone", ""))
			// // if added with no errors
			// phone.add(new Object_Phone(phone_id, person.getP_id(), true, "new phone",
			// "")); // lets add it to our GUI
			new Dialogue_ChooseMember(boatOwners, b.getBoat_id());
			// boatOwners.add(new Object_MembershipList());
		});

		boatOwnerDelete.setOnAction((event) -> {
			int selectedIndex = boatOwnerTableView.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0)
			if (SqlDelete.deleteBoatOwner(b.getBoat_id(), boatOwners.get(selectedIndex).getMsid())) // if it is																						// our database
				boatOwnerTableView.getItems().remove(selectedIndex); // remove it from our GUI
		});

		bnameTextField.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
					// focus out
					if (oldValue) { // we have focused and unfocused
						// SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
						SqlUpdate.updateBoat("BOAT_NAME", b.getBoat_id(), bnameTextField.getText());
					}
				});

		manufacturerTextField.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
					// focus out
					if (oldValue) { // we have focused and unfocused
						// SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
						SqlUpdate.updateBoat("MANUFACTURER", b.getBoat_id(), manufacturerTextField.getText());
					}
				});

		yearTextField.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
					// focus out
					if (oldValue) { // we have focused and unfocused
						// SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
						SqlUpdate.updateBoat("MANUFACTURE_YEAR", b.getBoat_id(), yearTextField.getText());
					}
				});

		modelTextField.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
					// focus out
					if (oldValue) { // we have focused and unfocused
						// SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
						SqlUpdate.updateBoat("MODEL", b.getBoat_id(), modelTextField.getText());
					}
				});

		registrationTextField.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
					// focus out
					if (oldValue) { // we have focused and unfocused
						// SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
						SqlUpdate.updateBoat("REGISTRATION_NUM", b.getBoat_id(), registrationTextField.getText());
					}
				});

		sailNumberTextField.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
					// focus out
					if (oldValue) { // we have focused and unfocused
						// SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
						SqlUpdate.updateBoat("SAIL_NUMBER", b.getBoat_id(), sailNumberTextField.getText());
					}
				});

		phrfTextField.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
					// focus out
					if (oldValue) { // we have focused and unfocused
						// SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
						SqlUpdate.updateBoat("PHRF", b.getBoat_id(), phrfTextField.getText());
					}
				});

		lengthTextField.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
					// focus out
					if (oldValue) { // we have focused and unfocused
						// SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
						SqlUpdate.updateBoat("LENGTH", b.getBoat_id(), lengthTextField.getText());
					}
				});

		weightTextField.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
					// focus out
					if (oldValue) { // we have focused and unfocused
						// SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
						SqlUpdate.updateBoat("WEIGHT", b.getBoat_id(), weightTextField.getText());
					}
				});

		trailerCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				SqlUpdate.updateBoat(b.getBoat_id(), isNowSelected);
			}
		});

		keelComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			SqlUpdate.updateBoat(b.getBoat_id(), newValue.getCode());
			// System.out.println("changed combo to " + newValue.getCode());
		});

		draftTextField.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
					// focus out
					if (oldValue) { // we have focused and unfocused
						// SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
						SqlUpdate.updateBoat("DRAFT", b.getBoat_id(), draftTextField.getText());
					}
				});

		beamTextField.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
					// focus out
					if (oldValue) { // we have focused and unfocused
						// SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
						SqlUpdate.updateBoat("BEAM", b.getBoat_id(), beamTextField.getText());
					}
				});

		lwlTextField.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
					// focus out
					if (oldValue) { // we have focused and unfocused
						// SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
						SqlUpdate.updateBoat("LWL", b.getBoat_id(), lwlTextField.getText());
					}
				});

		/////////////// SET CONTENT //////////////////

		/////////////////////// LEFT CONTAINER /////////////////////
		boatOwnerTableView.getColumns().addAll(Arrays.asList(col1, col2, col3));
		vboxTableFrame.getChildren().add(boatOwnerTableView);
		vboxButtons.getChildren().addAll(boatOwnerAdd, boatOwnerDelete);
		hboxTable.getChildren().addAll(vboxTableFrame, vboxButtons);
		vboxTableBackgroundColor.getChildren().add(hboxTable);
		ownerTitlePane.setContent(vboxTableBackgroundColor);
		vboxBnameLabel.getChildren().add(new Label("Boat Name"));
		vboxManufacturerLabel.getChildren().add(new Label("Manufacturer"));
		vboxYearLabel.getChildren().add(new Label("Year"));
		vboxModelLabel.getChildren().add(new Label("Model"));
		vboxRegistrationLabel.getChildren().add(new Label("Registration"));
		vboxSailNumbeLabel.getChildren().add(new Label("Sail Number"));
		vboxphrfLabel.getChildren().add(new Label("PHRF"));
		vboxlengthLabel.getChildren().add(new Label("Length"));
		vboxweightLabel.getChildren().add(new Label("Weight"));
		vboxtrailerLabel.getChildren().add(new Label("Has Trailer"));
		vboxkeelLabel.getChildren().add(new Label("Keel Type"));
		vboxDraftLabel.getChildren().add(new Label("Draft"));
		vboxBeamLabel.getChildren().add(new Label("Beam"));
		vboxLwlLabel.getChildren().add(new Label("LWL"));

		vboxBnameBox.getChildren().add(bnameTextField);
		vboxManufacturerBox.getChildren().add(manufacturerTextField);
		vboxYearBox.getChildren().add(yearTextField);
		vboxModelBox.getChildren().add(modelTextField);
		vboxRegistrationBox.getChildren().add(registrationTextField);
		vboxSailNumbeBox.getChildren().add(sailNumberTextField);
		vboxphrfBox.getChildren().add(phrfTextField);
		vboxlengthBox.getChildren().add(lengthTextField);
		vboxweightBox.getChildren().add(weightTextField);
		vboxtrailerBox.getChildren().add(trailerCheckBox);
		vboxkeelBox.getChildren().add(keelComboBox);
		vboxDraftBox.getChildren().add(draftTextField);
		vboxBeamBox.getChildren().add(beamTextField);
		vboxLwlBox.getChildren().add(lwlTextField);

		hbox1.getChildren().addAll(vboxBnameLabel, vboxBnameBox); // first name
		hbox2.getChildren().addAll(vboxManufacturerLabel, vboxManufacturerBox);
		hbox3.getChildren().addAll(vboxYearLabel, vboxYearBox);
		hbox4.getChildren().addAll(vboxModelLabel, vboxModelBox);
		hbox5.getChildren().addAll(vboxRegistrationLabel, vboxRegistrationBox);
		hbox6.getChildren().addAll(vboxSailNumbeLabel, vboxSailNumbeBox);
		hbox7.getChildren().addAll(vboxphrfLabel, vboxphrfBox);
		hbox8.getChildren().addAll(vboxlengthLabel, vboxlengthBox);
		hbox9.getChildren().addAll(vboxweightLabel, vboxweightBox);
		hbox10.getChildren().addAll(vboxtrailerLabel, vboxtrailerBox);
		hbox11.getChildren().addAll(vboxkeelLabel, vboxkeelBox);
		hbox12.getChildren().addAll(vboxDraftLabel, vboxDraftBox);
		hbox13.getChildren().addAll(vboxBeamLabel, vboxBeamBox);
		hbox14.getChildren().addAll(vboxLwlLabel, vboxLwlBox);
		vboxInformationBackgroundColor.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7, hbox8,
				hbox9, hbox10, hbox11, hbox12, hbox13, hbox14);
		boatInfoTitlePane.setContent(vboxInformationBackgroundColor);
		vboxLeftContainer.getChildren().addAll(boatInfoTitlePane, ownerTitlePane);

		/////////////////////// RIGHT CONTAINER /////////////////////
		imageView.setImage(image);
		// imageView sent to viewPane through constructor of ImageViewPane
		vboxPicture.getChildren().add(viewPane);
		hboxPictureControls.getChildren().addAll(buttonReverse, buttonForward, buttonAddPicture);
		vboxRightContainer.getChildren().addAll(hboxPictureControls, vboxPicture);

		hboxContainer.getChildren().addAll(vboxLeftContainer, vboxRightContainer);
		vboxGrey.getChildren().addAll(hboxContainer, new HBoxBoatNotes(b));
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}

//	private void checkIfLocalandRemoteDirectoriesMatch() {
//		ArrayList<String> remoteMissingImages = new ArrayList<String>();
//		ArrayList<String> localMissingImages = new ArrayList<String>();
//		for(String l: localImageFiles) {
//			boolean missing = true;
//			for(String r: remoteImageFiles) {
//				if(l.equals(r)) missing = false;
//			}
//			if(missing) remoteMissingImages.add(l);
//		}
//		for(String r: remoteImageFiles) {
//			boolean missing = true;
//			for(String l: localImageFiles) {
//				if(r.equals(l)) missing = false;
//			}
//			if(missing) localMissingImages.add(r);
//		}
//		System.out.println("Remote missing images:");
//		for(String rmm: remoteMissingImages) {
//			ftp.sendFile(HalyardPaths.BOATDIR + "/" + b.getBoat_id() + "/" + rmm, "/home/pcameron/Documents/ECSC/Boats/" + b.getBoat_id() + "/" + rmm);
//		}
//		System.out.println("Local missing images:");
//		for(String lmm: localMissingImages) {
//			ftp.getFile("/home/pcameron/Documents/ECSC/Boats/" + b.getBoat_id() + "/" + lmm, HalyardPaths.BOATDIR + "/" + b.getBoat_id() + "/" + lmm);
//			localImageFiles.add(lmm);
//		}
//	}

//	private void checkRemoteFiles() {
//		boolean hasDirectory = false;
//		ArrayList<String> remoteImageDirectories = ftp.ls("/home/pcameron/Documents/ECSC/Boats"); // prints files from directory
//		for(String fn: remoteImageDirectories) {
//			System.out.println(fn);
//			if(fn.equals(b.getBoat_id() + "")) {
//				hasDirectory = true;
//			}
//		}
//		if(!hasDirectory) {  // if the directory doesn't exist create it
//			ftp.mkdir("/home/pcameron/Documents/ECSC/Boats/" + b.getBoat_id());
//		} else {  // else put file names in that directory into a string array
//			remoteImageFiles = ftp.ls("/home/pcameron/Documents/ECSC/Boats/" + b.getBoat_id());
//		}
//	}

	private String getNewName(File originalFile) {
		return "B" + b.getBoat_id() + "_IMG_" + (localImageFiles.size() + 1) + HalyardPaths.getFileExtension(originalFile);
	}

	public Image getImage(String file) {
		FileInputStream input = null;
		try {
			// System.out.println("pictureNumber=" + pictureNumber);
			input = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image image = new Image(input);
		return image;
	}

	private void copyFile(File srcFile, File destFile) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(srcFile);
			os = new FileOutputStream(destFile);
			byte[] buffer = new byte[8192];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
