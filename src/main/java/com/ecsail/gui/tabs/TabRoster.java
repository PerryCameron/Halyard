package com.ecsail.gui.tabs;

import com.ecsail.excel.Xls_roster;
import com.ecsail.gui.tabs.roster.TabKayakLists;
import com.ecsail.gui.tabs.roster.TabSlipOptions;
import com.ecsail.gui.tabs.roster.TabStandard;
import com.ecsail.main.HalyardPaths;
import com.ecsail.main.Launcher;
import com.ecsail.main.rosterContextMenu;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.structures.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Comparator;

public class TabRoster extends Tab {

	private final ObservableList<MembershipListDTO> rosters;
	private final TableView<MembershipListDTO> rosterTableView = new TableView<>();
	private final RosterSelectDTO printChoices;
	private final RosterRadioButtonsDTO rb;
	String selectedYear;

	public TabRoster(ObservableList<MembershipListDTO> a, String sy) {
		super();
		this.rosters = a;
		this.selectedYear = sy;
		this.setText("Roster");
		this.rb = new RosterRadioButtonsDTO();
		this.printChoices = new RosterSelectDTO(sy, false, false, true, false, false, false, false, true, true, true, false,
				false, false, false, false, false, false, false, false, false);

		/////////////////// OBJECTS //////////////////////////
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // inter vbox
		VBox vboxTableBox = new VBox();
		VBox vboxRadioButton1 = new VBox();
		VBox vboxRadioButton2 = new VBox();
		VBox vboxSpinnerLabel = new VBox();
		VBox vboxCheckBox1 = new VBox();
		VBox vboxCheckBox2 = new VBox();
		VBox vboxCheckBox3 = new VBox();
		VBox vboxCheckBox4 = new VBox();
		VBox vboxCheckBox5 = new VBox();
		HBox hboxExport = new HBox();
		HBox hboxExportFrame = new HBox();
		HBox controlsHbox = new HBox();
		TitledPane titledPane = new TitledPane();
		TabPane tabPane = new TabPane();
		Label records = new Label();
		CheckBox c1 = new CheckBox("Membership Id");
		CheckBox c2 = new CheckBox("Last Name");
		CheckBox c3 = new CheckBox("First Name");
		CheckBox c4 = new CheckBox("Join Date");
		CheckBox c5 = new CheckBox("Address");
		CheckBox c6 = new CheckBox("City");
		CheckBox c7 = new CheckBox("State");
		CheckBox c8 = new CheckBox("Zip");
		CheckBox c9 = new CheckBox("Membership Type");
		CheckBox c10 = new CheckBox("Slip");
		CheckBox c11 = new CheckBox("Phone");
		CheckBox c12 = new CheckBox("Email");
		CheckBox c13 = new CheckBox("Subleased To");
		Button buttonXLS = new Button("Export XLS");
//		final Spinner<Integer> yearSpinner = new Spinner<Integer>();
		TableColumn<MembershipListDTO, Integer> Col1 = new TableColumn<>("ID");
		TableColumn<MembershipListDTO, String> Col2 = new TableColumn<>("Join Date");
		TableColumn<MembershipListDTO, String> Col3 = new TableColumn<>("Type");
		TableColumn<MembershipListDTO, String> Col4 = new TableColumn<>("Slip");
		TableColumn<MembershipListDTO, String> Col5 = new TableColumn<>("First Name");
		TableColumn<MembershipListDTO, String> Col6 = new TableColumn<>("Last Name");
		TableColumn<MembershipListDTO, String> Col7 = new TableColumn<>("Address");
		TableColumn<MembershipListDTO, String> Col8 = new TableColumn<>("City");
		TableColumn<MembershipListDTO, String> Col9 = new TableColumn<>("State");
		TableColumn<MembershipListDTO, String> Col10 = new TableColumn<>("Zip");
		TableColumn<MembershipListDTO, String> Col11 = new TableColumn<>("MSID");

		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		titledPane.setText("Roster " + selectedYear);
		controlsHbox.setStyle("-fx-background-color:#e2e3de");
		records.setText(rosters.size() + " Records");
		rb.setSameToggleGroup();
		
		rb.getRadioActive().setSelected(true);
		c1.setSelected(true);
		c2.setSelected(true);
		c3.setSelected(true);
		
		tabPane.setId("roster-tab-pane");
		vboxBlue.setId("box-blue");
		vboxPink.setId("box-pink");
		hboxExportFrame.setId("box-blue");
		hboxExport.setId("box-pink");
		
		hboxExport.setSpacing(10);
		vboxCheckBox4.setSpacing(5);
		controlsHbox.setSpacing(10);
		vboxRadioButton1.setSpacing(3);
		vboxRadioButton2.setSpacing(3);
		vboxSpinnerLabel.setSpacing(10);
		
		hboxExportFrame.setPadding(new Insets(2, 2, 2, 2));
		hboxExport.setPadding(new Insets(5, 5, 5, 5));
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 5, 3));
		vboxRadioButton1.setPadding(new Insets(5, 5, 5, 5));
		vboxRadioButton2.setPadding(new Insets(5, 5, 5, 5));
		
		tabPane.setSide(Side.LEFT);
		VBox.setVgrow(vboxBlue, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		VBox.setVgrow(vboxTableBox, Priority.ALWAYS);
		VBox.setVgrow(rosterTableView, Priority.ALWAYS);
		HBox.setHgrow(rosterTableView, Priority.ALWAYS);

		vboxSpinnerLabel.setAlignment(Pos.CENTER);

		setOnClosed(null);
		rosterTableView.setItems(rosters);
		rosterTableView.setFixedCellSize(30);
		rosterTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );

		Col1.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
		Col2.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
		Col3.setCellValueFactory(new PropertyValueFactory<>("memType"));
		Col4.setCellValueFactory(new PropertyValueFactory<>("slip"));
		Col5.setCellValueFactory(new PropertyValueFactory<>("fname"));
		Col6.setCellValueFactory(new PropertyValueFactory<>("lname"));
		Col7.setCellValueFactory(new PropertyValueFactory<>("address"));
		Col8.setCellValueFactory(new PropertyValueFactory<>("city"));
		Col9.setCellValueFactory(new PropertyValueFactory<>("state"));
		Col10.setCellValueFactory(new PropertyValueFactory<>("zip"));
		Col11.setCellValueFactory(new PropertyValueFactory<>("msid"));
		
		/// sets width of columns by percentage
		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // Mem 5%
		Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );  // Join Date 15%
		Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // Type
		Col4.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // Slip
		Col5.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );   // First Name
		Col6.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // Last Name
		Col7.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );  // Address
		Col8.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );  // City
		Col9.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );  // State
		Col10.setMaxWidth( 1f * Integer.MAX_VALUE * 10 ); // Zip
		Col11.setMaxWidth( 1f * Integer.MAX_VALUE * 5 ); // MSID

		rosterTableView.getColumns()
				.addAll(Arrays.asList(Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11));

		ComboBox<Integer> comboBox = new ComboBox<>();
		for(int i = Integer.parseInt(HalyardPaths.getYear()) + 1; i > 1969; i--) {
			comboBox.getItems().add(i);
		}
		comboBox.getSelectionModel().select(1);
		comboBox.getStyleClass().add("bigbox");
		//////////////////// LISTENERS //////////////////////////

		comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
				selectedYear = newValue.toString();
				printChoices.setYear(selectedYear);
				changeSelectedRoster();
				titledPane.setText("Roster " + selectedYear);
				records.setText(rosters.size() + " Records");
				rosterTableView.sort();
		});
		
		buttonXLS.setOnAction((event) -> new Xls_roster(rosters, printChoices));

		c1.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setMembership_id(observable.getValue()));

		c2.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setLastName(observable.getValue()));
		
		c3.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setFirstName(observable.getValue()));
		
		c4.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
				printChoices.setJoinDate(observable.getValue()));

		c5.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setStreetAddress(observable.getValue()));

		c6.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setCity(observable.getValue()));
		
		c7.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setState(observable.getValue()));
		
		c8.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setZip(observable.getValue()));
		
		c9.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setMemtype(observable.getValue()));
		
		c10.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setSlip(observable.getValue()));

		c11.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setPhone(observable.getValue()));

		c12.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setEmail(observable.getValue()));

		c13.selectedProperty().addListener(
				(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> 
				printChoices.setSubleasedto(observable.getValue()));

		
		rosterTableView.setRowFactory(tv -> {
			TableRow<MembershipListDTO> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					// int rowIndex = row.getIndex();
					MembershipListDTO clickedRow = row.getItem();
					Launcher.createMembershipTabForRoster(clickedRow.getMembershipId(), clickedRow.getMsid());
				}
				if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
				row.setContextMenu(new rosterContextMenu(row.getItem(), selectedYear));
				}
			});
			return row;
		});


		rb.getRadioActive().selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
			if (isNowSelected) {
				setListType("active");
				rosters.clear();
				rosters.addAll(SqlMembershipList.getRoster(selectedYear, true));

				records.setText(rosters.size() + " Records");
				//rosterTableView.sort();
				rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
			}
		});

		rb.getRadioAll().selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
			if (isNowSelected) {
				setListType("all");
				rosters.clear();
				rosters.addAll(SqlMembershipList.getRosterOfAll(selectedYear));
				records.setText(rosters.size() + " Records");
				//rosterTableView.sort();
				rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
			}
		});

		rb.getRadioNonRenew().selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
			if (isNowSelected) {
				setListType("non-renew");
				rosters.clear();
				rosters.addAll(SqlMembershipList.getRoster(selectedYear, false));
				records.setText(rosters.size() + " Records");
				//rosterTableView.sort();
				rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
			}
		});

		rb.getRadioNewMembers().selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
			if (isNowSelected) {
				setListType("new-members");
				rosters.clear();
				rosters.addAll(SqlMembershipList.getNewMemberRoster(selectedYear));
				records.setText(rosters.size() + " Records");
				//rosterTableView.sort();
				rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
			}
		});

		rb.getRadioReturnMembers().selectedProperty().addListener((ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) -> {
				if (isNowSelected) {
					ObservableList<MembershipListDTO> keepers = FXCollections.observableArrayList();
					setListType("return");
					rosters.clear();
					rosters.addAll(SqlMembershipList.getReturnMembers(Integer.parseInt(selectedYear)));
//					for (MembershipListDTO roster : rosters) {
//						if (!SqlExists.paidLate(roster)) {
//							keepers.add(roster);
//						}
//					}
//					rosters.clear();
//					rosters.addAll(keepers);
//					keepers.clear();
					records.setText(rosters.size() + " Records");
					//rosterTableView.sort();
					rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
				}
		});

		rb.getRadioSlipWaitList().selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
			if (isNowSelected) {
				setListType("slip-waitlist");
				rosters.clear();
				rosters.addAll(SqlMembershipList.getWaitListRoster("slipwait"));
				records.setText(rosters.size() + " Records");
				//rosterTableView.sort();
				rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
			}
		});

		rb.getRadioOpenSlips().selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
			if (isNowSelected) {
				setListType("wantsrelease");
				rosters.clear();
				rosters.addAll(SqlMembershipList.getWaitListRoster("wantrelease"));
				records.setText(rosters.size() + " Records");
				//rosterTableView.sort();
				rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
			}
		});

		rb.getRadioSlip().selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
			if (isNowSelected) {
				setListType("slipOwners");
				rosters.clear();
				rosters.addAll(SqlMembershipList.getRosterOfSlipOwners(HalyardPaths.getYear()));
				records.setText(rosters.size() + " Records");
				//rosterTableView.sort();
				rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
			}
		});

		rb.getRadioWantsToSublease().selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
			if (isNowSelected) {
				setListType("wantsublease");
				rosters.clear();
				rosters.addAll(SqlMembershipList.getWaitListRoster("wantsublease"));
				records.setText(rosters.size() + " Records");
				//rosterTableView.sort();
				rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
			}
		});

		rb.getRadioSlipChange().selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
			if (isNowSelected) {
				setListType("wantsublease");
				rosters.clear();
				rosters.addAll(SqlMembershipList.getWaitListRoster("wantslipchange"));
				records.setText(rosters.size() + " Records");
				//rosterTableView.sort();
				rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
			}
		});

		rb.getRadioSubLeasedSlips().selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
			if (isNowSelected) {
				setListType("subleasedslips");
				rosters.clear();
				rosters.addAll(SqlMembershipList.getRosterOfSubleasedSlips());
				records.setText(rosters.size() + " Records");
				//rosterTableView.sort();
				rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
			}
		});

		rb.getRadioShedOwner().selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
			if (isNowSelected) {
				setListType("shedowners");
				rosters.clear();
				rosters.addAll(SqlMembershipList.getRosterOfKayakShedOwners(HalyardPaths.getYear()));
				records.setText(rosters.size() + " Records");
				//rosterTableView.sort();
				rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
			}
		});

		rb.getRadioKayakRackOwners().selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
			if (isNowSelected) {
				setListType("rackowners");
				rosters.clear();
				rosters.addAll(SqlMembershipList.getRosterOfKayakRackOwners(HalyardPaths.getYear()));
				records.setText(rosters.size() + " Records");
				//rosterTableView.sort();
				rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
			}
		});

		rb.getRadioLatePaymentMembers().selectedProperty().addListener((ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
				Boolean isNowSelected) -> {
			if (isNowSelected) {
				ObservableList<MembershipListDTO> keepers = FXCollections.observableArrayList();
				setListType("return");
				rosters.clear();
				rosters.addAll(SqlMembershipList.getFullNewMemberRoster(selectedYear));
				for (MembershipListDTO roster : rosters) {
					// if they didn't pay late
					if (SqlExists.paidLate(roster)) {
						keepers.add(roster);

					}
				}
				rosters.clear();
				rosters.addAll(keepers);
				keepers.clear();
				records.setText(rosters.size() + " Records");
				rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
			}
	});

		//////////////////// SET CONTENT //////////////////////
		
		vboxCheckBox1.getChildren().addAll(c1, c2, c3, c4);
		vboxCheckBox2.getChildren().addAll(c5, c6, c7, c8);
		vboxCheckBox3.getChildren().addAll(c9, c10, c11, c12);
		vboxCheckBox5.getChildren().addAll(buttonXLS);
		hboxExportFrame.getChildren().add(hboxExport);
		hboxExport.getChildren().addAll(vboxCheckBox1, vboxCheckBox2, vboxCheckBox3, vboxCheckBox4, vboxCheckBox5);
		tabPane.getTabs().addAll(new TabStandard(rb), new TabSlipOptions(rb), new TabKayakLists(rb));
		vboxSpinnerLabel.getChildren().addAll(comboBox, records);
		vboxTableBox.getChildren().add(rosterTableView);
		controlsHbox.getChildren().addAll(vboxSpinnerLabel, tabPane, hboxExportFrame);
		titledPane.setContent(controlsHbox);
		vboxPink.getChildren().addAll(titledPane,vboxTableBox);
		vboxBlue.getChildren().add(vboxPink);
		setContent(vboxBlue);
	}

	/// this only changes when the year spinner changes
	private void changeSelectedRoster() {
		rosters.clear();
		if (rb.getRadioActive().isSelected())
			rosters.addAll(SqlMembershipList.getRoster(selectedYear, true));
		if (rb.getRadioNonRenew().isSelected())
			rosters.addAll(SqlMembershipList.getRoster(selectedYear, false));
		if (rb.getRadioNewMembers().isSelected())
			rosters.addAll(SqlMembershipList.getNewMemberRoster(selectedYear));
		if (rb.getRadioReturnMembers().isSelected())
			rosters.addAll(SqlMembershipList.getFullNewMemberRoster(selectedYear));
		if (rb.getRadioAll().isSelected())
			rosters.addAll(SqlMembershipList.getRosterOfAll(selectedYear));
		rosters.sort(Comparator.comparing(MembershipListDTO::getMembershipId));
	}

	//// Class Methods ////
	private void setListType(String type) {
		switch (type) {
		case "all":
			printChoices.setAll(true);
			printChoices.setActive(false);
			printChoices.setNonRenew(false);
			printChoices.setNewMembers(false);
			printChoices.setNewAndReturnd(false);
			printChoices.setSlipwait(false);
		case "active":
			printChoices.setAll(false);
			printChoices.setActive(true);
			printChoices.setNonRenew(false);
			printChoices.setNewMembers(false);
			printChoices.setNewAndReturnd(false);
			printChoices.setSlipwait(false);
			break;
		case "non-renew":
			printChoices.setAll(false);
			printChoices.setActive(false);
			printChoices.setNonRenew(true);
			printChoices.setNewMembers(false);
			printChoices.setNewAndReturnd(false);
			printChoices.setSlipwait(false);
			break;
		case "new-members":
			printChoices.setAll(false);
			printChoices.setActive(false);
			printChoices.setNonRenew(false);
			printChoices.setNewMembers(true);
			printChoices.setNewAndReturnd(false);
			printChoices.setSlipwait(false);
			break;
		case "return":
			printChoices.setAll(false);
			printChoices.setActive(false);
			printChoices.setNonRenew(false);
			printChoices.setNewMembers(false);
			printChoices.setNewAndReturnd(true);
			printChoices.setSlipwait(false);
			break;
		case "slip-waitlist":
			printChoices.setAll(false);
			printChoices.setActive(false);
			printChoices.setNonRenew(false);
			printChoices.setNewMembers(false);
			printChoices.setNewAndReturnd(false);
			printChoices.setSlipwait(true);
			break;
		}
	}
}
