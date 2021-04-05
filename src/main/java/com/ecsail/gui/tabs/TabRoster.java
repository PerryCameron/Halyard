package com.ecsail.gui.tabs;

import java.util.Arrays;

import com.ecsail.excel.Xls_roster;
import com.ecsail.gui.tabs.roster.TabKayakLists;
import com.ecsail.gui.tabs.roster.TabSlipOptions;
import com.ecsail.gui.tabs.roster.TabStandard;
import com.ecsail.main.Main;
import com.ecsail.main.Paths;
import com.ecsail.main.TabLauncher;
import com.ecsail.sql.SQL_SelectMembership;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_RosterRadioButtons;
import com.ecsail.structures.Object_RosterSelect;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TabRoster extends Tab {

	private ObservableList<Object_MembershipList> rosters;
	private TableView<Object_MembershipList> rosterTableView = new TableView<>();
	private Object_RosterSelect printChoices;
	private Object_RosterRadioButtons rb;
	String selectedYear;

	public TabRoster(ObservableList<Object_MembershipList> a, String sy) {
		super();
		this.rosters = a;
		this.selectedYear = sy;
		this.setText("Roster");
		this.rb = new Object_RosterRadioButtons();
		this.printChoices = new Object_RosterSelect(sy, false, true, false, false, false, true, true, true, false,
				false, false, false, false, false, false, false, false, false);
		System.out.println("size=" + rosters.size());

		/////////////////// OBJECTS //////////////////////////
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox(); // inter vbox
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

		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		vboxSpinnerLabel.setSpacing(10);
		titledPane.setText("Roster " + selectedYear);
		controlsHbox.setStyle("-fx-background-color:#e2e3de");
		records.setText(rosters.size() + " Records");
		rb.setSameToggleGroup();
		tabPane.setSide(Side.LEFT);
		tabPane.setId("roster-tab-pane");
		rb.getRadioActive().setSelected(true);
		c1.setSelected(true);
		c2.setSelected(true);
		c3.setSelected(true);
		vbox1.setId("box-blue");
		vbox2.setId("box-pink");
		hboxExportFrame.setId("box-blue");
		hboxExport.setId("box-pink");
		hboxExport.setSpacing(10);
		hboxExportFrame.setPadding(new Insets(2, 2, 2, 2));
		hboxExport.setPadding(new Insets(5, 5, 5, 5));
		// vboxCheckBox1.setSpacing(2);
		// vboxCheckBox2.setSpacing(2);
		vboxCheckBox4.setSpacing(5);
		controlsHbox.setSpacing(10);
		vboxRadioButton1.setSpacing(3);
		vboxRadioButton2.setSpacing(3);
		vbox1.setPadding(new Insets(10, 10, 10, 10));
		vbox2.setPadding(new Insets(3, 3, 5, 3));
		vboxRadioButton1.setPadding(new Insets(5, 5, 5, 5));
		vboxRadioButton2.setPadding(new Insets(5, 5, 5, 5));
		vbox1.setAlignment(Pos.TOP_CENTER);
		vbox1.setPrefHeight(900);

		setOnClosed(null);
		rosterTableView.setItems(rosters);
		// rosterTableView.setPrefWidth(1000); //Windows
		rosterTableView.setMaxWidth(1000);
		// rosterTableView.setPrefWidth(940);
		rosterTableView.setFixedCellSize(30);
		// rosterTableView.setPrefHeight(555); //Windows
		rosterTableView.setPrefHeight(545);

		TableColumn<Object_MembershipList, Integer> Col1 = new TableColumn<Object_MembershipList, Integer>("MEM");
		Col1.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, Integer>("membershipId"));

		TableColumn<Object_MembershipList, String> Col2 = new TableColumn<Object_MembershipList, String>("JOIN_DATE");
		Col2.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("joinDate"));
		Col2.setPrefWidth(110);

		TableColumn<Object_MembershipList, String> Col3 = new TableColumn<Object_MembershipList, String>("Type");
		Col3.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("memType"));

		TableColumn<Object_MembershipList, String> Col4 = new TableColumn<Object_MembershipList, String>("Slip");
		Col4.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("slip"));

		TableColumn<Object_MembershipList, String> Col5 = new TableColumn<Object_MembershipList, String>("First Name");
		Col5.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("fname"));
		Col5.setPrefWidth(120);

		TableColumn<Object_MembershipList, String> Col6 = new TableColumn<Object_MembershipList, String>("Last Name");
		Col6.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("lname"));
		Col6.setPrefWidth(120);

		TableColumn<Object_MembershipList, String> Col7 = new TableColumn<Object_MembershipList, String>("Address");
		Col7.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("address"));
		Col7.setPrefWidth(180);

		TableColumn<Object_MembershipList, String> Col8 = new TableColumn<Object_MembershipList, String>("City");
		Col8.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("city"));
		Col8.setPrefWidth(120);

		TableColumn<Object_MembershipList, String> Col9 = new TableColumn<Object_MembershipList, String>("State");
		Col9.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("state"));

		TableColumn<Object_MembershipList, String> Col10 = new TableColumn<Object_MembershipList, String>("Zip");
		Col10.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("zip"));

		TableColumn<Object_MembershipList, String> Col11 = new TableColumn<Object_MembershipList, String>("MSID");
		Col11.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("msid"));

		rosterTableView.getColumns()
				.addAll(Arrays.asList(Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11));

		final Spinner<Integer> yearSpinner = new Spinner<Integer>();
		SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970,
				Integer.parseInt(selectedYear), Integer.parseInt(selectedYear));
		yearSpinner.setValueFactory(wetSlipValueFactory);
		yearSpinner.setPrefWidth(110);
		yearSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				printChoices.setYear(yearSpinner.getEditor().getText());
				selectedYear = yearSpinner.getEditor().getText(); /// kept this for clarity, could have used
																	/// printChoices.getYear()
				rosters.clear();
				if (rb.getRadioActive().isSelected())
					rosters.addAll(SQL_SelectMembership.getRoster(selectedYear, true));
				if (rb.getRadioNonRenew().isSelected())
					rosters.addAll(SQL_SelectMembership.getRoster(selectedYear, false));
				if (rb.getRadioNewMembers().isSelected())
					rosters.addAll(SQL_SelectMembership.getNewMemberRoster(selectedYear));
				if (rb.getRadioNewReturnMembers().isSelected())
					rosters.addAll(SQL_SelectMembership.getFullNewMemberRoster(selectedYear));
				titledPane.setText("Roster " + selectedYear);
				records.setText(rosters.size() + " Records");
				rosterTableView.sort();
			}
		});

		//////////////////// LISTENERS //////////////////////////

		Main.getPrimaryStage().heightProperty().addListener((obs, oldVal, newVal) -> {
		    	 rosterTableView.setPrefHeight(545.0 + (double)newVal - 796.0);// 796 is start height of bottom of window
		});  /// 545 start height of rosterTableView
		
		buttonXLS.setOnAction((event) -> new Xls_roster(rosters, printChoices));

		c1.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				printChoices.setMembership_id(observable.getValue());
			}
		});

		c2.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				printChoices.setLastName(observable.getValue());
			}
		});

		c3.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				printChoices.setFirstName(observable.getValue());
			}
		});

		c4.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				printChoices.setJoinDate(observable.getValue());
			}
		});

		c5.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				printChoices.setStreetAddress(observable.getValue());
			}
		});

		c6.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				printChoices.setCity(observable.getValue());
			}
		});

		c7.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				printChoices.setState(observable.getValue());
			}
		});

		c8.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				printChoices.setZip(observable.getValue());
			}
		});

		c9.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				printChoices.setMemtype(observable.getValue());
			}
		});

		c10.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				printChoices.setSlip(observable.getValue());
			}
		});

		c11.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				printChoices.setPhone(observable.getValue());
			}
		});

		c12.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				printChoices.setEmail(observable.getValue());
			}
		});

		c13.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				printChoices.setSubleasedto(observable.getValue());
			}
		});
		

		rosterTableView.setRowFactory(tv -> {
			TableRow<Object_MembershipList> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					// int rowIndex = row.getIndex();
					Object_MembershipList clickedRow = row.getItem();
					createTab(clickedRow);
				}
			});
			return row;
		});

		rb.getRadioActive().selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					setListType("active");
					rosters.clear();
					rosters.addAll(SQL_SelectMembership.getRoster(selectedYear, true));
					records.setText(rosters.size() + " Records");
					rosterTableView.sort();
				}
			}
		});

		rb.getRadioAll().selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					setListType("all");
					rosters.clear();
					rosters.addAll(SQL_SelectMembership.getRosterOfAll(selectedYear));
					records.setText(rosters.size() + " Records");
					rosterTableView.sort();
				} else {
					// ...
				}
			}
		});

		rb.getRadioNonRenew().selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					setListType("non-renew");
					rosters.clear();
					rosters.addAll(SQL_SelectMembership.getRoster(selectedYear, false));
					records.setText(rosters.size() + " Records");
					rosterTableView.sort();
				} else {
					// ...
				}
			}
		});

		rb.getRadioNewMembers().selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					setListType("new-members");
					rosters.clear();
					rosters.addAll(SQL_SelectMembership.getNewMemberRoster(selectedYear));
					records.setText(rosters.size() + " Records");
					rosterTableView.sort();
				} else {
					// ...
				}
			}
		});

		rb.getRadioNewReturnMembers().selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					setListType("new-and-return");
					rosters.clear();
					rosters.addAll(SQL_SelectMembership.getFullNewMemberRoster(selectedYear));
					records.setText(rosters.size() + " Records");
					rosterTableView.sort();
				} else {
					// ...
				}
			}
		});

		rb.getRadioSlipWaitList().selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					setListType("slip-waitlist");
					rosters.clear();
					rosters.addAll(SQL_SelectMembership.getWaitListRoster("slipwait"));
					records.setText(rosters.size() + " Records");
					rosterTableView.sort();
				} else {
					// ...
				}
			}
		});

		rb.getRadioOpenSlips().selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					setListType("wantsrelease");
					rosters.clear();
					rosters.addAll(SQL_SelectMembership.getWaitListRoster("wantrelease"));
					records.setText(rosters.size() + " Records");
					rosterTableView.sort();
				} else {
					// ...
				}
			}
		});

		rb.getRadioSlip().selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					setListType("slipOwners");
					rosters.clear();
					rosters.addAll(SQL_SelectMembership.getRosterOfSlipOwners(Paths.getYear()));
					records.setText(rosters.size() + " Records");
					rosterTableView.sort();
				} else {
					// ...
				}
			}
		});

		rb.getRadioWantsToSublease().selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					setListType("wantsublease");
					rosters.clear();
					rosters.addAll(SQL_SelectMembership.getWaitListRoster("wantsublease"));
					records.setText(rosters.size() + " Records");
					rosterTableView.sort();
				} else {
					// ...
				}
			}
		});

		rb.getRadioSlipChange().selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					setListType("wantsublease");
					rosters.clear();
					rosters.addAll(SQL_SelectMembership.getWaitListRoster("wantslipchange"));
					records.setText(rosters.size() + " Records");
					rosterTableView.sort();
				} else {
					// ...
				}
			}
		});

		rb.getRadioSubLeasedSlips().selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					setListType("subleasedslips");
					rosters.clear();
					rosters.addAll(SQL_SelectMembership.getRosterOfSubleasedSlips());
					records.setText(rosters.size() + " Records");
					rosterTableView.sort();
				} else {
					// ...
				}
			}
		});

		rb.getRadioShedOwner().selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					setListType("shedowners");
					rosters.clear();
					rosters.addAll(SQL_SelectMembership.getRosterOfKayakShedOwners(Paths.getYear()));
					records.setText(rosters.size() + " Records");
					rosterTableView.sort();
				} else {
					// ...
				}
			}
		});

		rb.getRadioKayakRackOwners().selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					setListType("rackowners");
					rosters.clear();
					rosters.addAll(SQL_SelectMembership.getRosterOfKayakRackOwners(Paths.getYear()));
					records.setText(rosters.size() + " Records");
					rosterTableView.sort();
					System.out.println("Roster of those with a kayak rack");
				} else {
					// ...
				}
			}
		});

		rb.getRadioAllActiveMembers().selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					setListType("all-active-members");
					rosters.clear();
					rosters.addAll(SQL_SelectMembership.getRosterOfAllActiveMembers(Paths.getYear()));
					records.setText(rosters.size() + " Records");
					rosterTableView.sort();
					System.out.println("Roster of those with a kayak rack");
				} else {
					// ...
				}
			}
		});

		titledPane.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
			if (isNowExpanded) {
				// System.out.println("Title Pane Expanded");
				rosterTableView.setPrefHeight(555);
			}
			if (wasExpanded) {
				// System.out.println("Title Pane collapsed");
				rosterTableView.setPrefHeight(655);
			}
		});

		//////////////////// SET CONTENT //////////////////////
		vboxCheckBox1.getChildren().addAll(c1, c2, c3, c4);
		vboxCheckBox2.getChildren().addAll(c5, c6, c7, c8);
		vboxCheckBox3.getChildren().addAll(c9, c10, c11, c12);
		// vboxCheckBox4.getChildren().addAll(c13); /// this adds the subleased to check
		// box but makes it too wide
		vboxCheckBox5.getChildren().addAll(buttonXLS);
		hboxExportFrame.getChildren().add(hboxExport);
		hboxExport.getChildren().addAll(vboxCheckBox1, vboxCheckBox2, vboxCheckBox3, vboxCheckBox4, vboxCheckBox5);
		// vboxRadioButton1.getChildren().addAll(rb.getRadioActive(),rb.,r3,r4);
		// vboxRadioButton2.getChildren().addAll(r5,r6);
		tabPane.getTabs().addAll(new TabStandard(rb), new TabSlipOptions(rb), new TabKayakLists(rb));
		vboxSpinnerLabel.getChildren().addAll(yearSpinner, records);
		// vboxRadioButton1,vboxRadioButton2
		controlsHbox.getChildren().addAll(vboxSpinnerLabel, tabPane, hboxExportFrame);
		titledPane.setContent(controlsHbox);
		vbox1.getChildren().add(vbox2);
		vbox2.getChildren().addAll(titledPane,rosterTableView);
		setContent(vbox1);
	}

	//// Class Methods ////

	private void setListType(String type) {
		switch (type) {
		case "active":
			printChoices.setActive(true);
			printChoices.setNonRenew(false);
			printChoices.setNewMembers(false);
			printChoices.setNewAndReturnd(false);
			printChoices.setSlipwait(false);
			break;
		case "non-renew":
			printChoices.setActive(false);
			printChoices.setNonRenew(true);
			printChoices.setNewMembers(false);
			printChoices.setNewAndReturnd(false);
			printChoices.setSlipwait(false);
			break;
		case "new-members":
			printChoices.setActive(false);
			printChoices.setNonRenew(false);
			printChoices.setNewMembers(true);
			printChoices.setNewAndReturnd(false);
			printChoices.setSlipwait(false);
			break;
		case "new-and-return":
			printChoices.setActive(false);
			printChoices.setNonRenew(false);
			printChoices.setNewMembers(false);
			printChoices.setNewAndReturnd(true);
			printChoices.setSlipwait(false);
			break;
		case "slip-waitlist":
			printChoices.setActive(false);
			printChoices.setNonRenew(false);
			printChoices.setNewMembers(false);
			printChoices.setNewAndReturnd(false);
			printChoices.setSlipwait(true);
			break;
		}
	}

	private static void createTab(Object_MembershipList clickedRow) {
		TabLauncher.createTab(clickedRow.getMembershipId(), clickedRow.getMsid());
	}
}
