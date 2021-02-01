package com.ecsail.gui.tabs;

import java.util.Arrays;

import com.ecsail.excel.Xls_roster;
import com.ecsail.main.SqlSelect;
import com.ecsail.main.TabLauncher;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_RosterSelect;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TabRoster extends Tab {
	
	private ObservableList<Object_MembershipList> rosters;
	private TableView<Object_MembershipList> rosterTableView = new TableView<>();
	private Object_RosterSelect printChoices;
	String selectedYear;
	
	public TabRoster(ObservableList<Object_MembershipList> a, String sy) {
		super();
		this.rosters = a;
		this.selectedYear = sy;
		this.setText("Roster");
		this.printChoices = new Object_RosterSelect(sy, true, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false, false);
		System.out.println("size=" + rosters.size());
		
		///////////////////  OBJECTS //////////////////////////
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();  // inter vbox
		VBox vboxRadioButtons = new VBox();
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
		
		ToggleGroup tg1 = new ToggleGroup();  
		RadioButton r1 = new RadioButton("Active"); 
        RadioButton r2 = new RadioButton("Non-Renew"); 
        RadioButton r3 = new RadioButton("New Members");
        RadioButton r4 = new RadioButton("New/Return Members");
        //Button buttonPDF = new Button("Export PDF");
        Button buttonXLS = new Button("Export XLS");
        
        vboxSpinnerLabel.setSpacing(10);
        titledPane.setText("Roster " + selectedYear);
        controlsHbox.setStyle("-fx-background-color:#e2e3de");
        records.setText(rosters.size() + " Records");
		r1.setToggleGroup(tg1); 
        r2.setToggleGroup(tg1);
        r3.setToggleGroup(tg1);
        r4.setToggleGroup(tg1);
        r1.setSelected(true);
        c1.setSelected(true);
        c2.setSelected(true);
        c3.setSelected(true);
		vbox1.setId("box-blue");
		vbox2.setId("box-pink");
		hboxExportFrame.setId("box-blue");
		hboxExport.setId("box-pink");
		hboxExport.setSpacing(10);
		hboxExportFrame.setPadding(new Insets(2,2,2,2));
		hboxExport.setPadding(new Insets(5,5,5,5));
		//vboxCheckBox1.setSpacing(2);
		//vboxCheckBox2.setSpacing(2);
		vboxCheckBox4.setSpacing(5);
		controlsHbox.setSpacing(10);
		vboxRadioButtons.setSpacing(3);
		vbox1.setPadding(new Insets(12,12,15,12));
		vbox2.setPadding(new Insets(3,3,5,3));
		vboxRadioButtons.setPadding(new Insets(5,5,5,5));
		vbox1.setAlignment(Pos.TOP_CENTER);
		vbox1.setPrefHeight(768);

			setOnClosed(null);
			rosterTableView.setItems(rosters);
			rosterTableView.setPrefWidth(1000);
			rosterTableView.setFixedCellSize(30);
			rosterTableView.setPrefHeight(555);
			
			TableColumn<Object_MembershipList, Integer> Col3 = new TableColumn<Object_MembershipList, Integer>("MEM");
			Col3.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, Integer>("membershipId"));
			
			TableColumn<Object_MembershipList, String> Col4 = new TableColumn<Object_MembershipList, String>("JOIN_DATE");
			Col4.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("joinDate"));
			Col4.setPrefWidth(110);
			
			TableColumn<Object_MembershipList, String> Col5a = new TableColumn<Object_MembershipList, String>("Type");
			Col5a.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("memType"));

			TableColumn<Object_MembershipList, String> Col6 = new TableColumn<Object_MembershipList, String>("Slip");
			Col6.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("slip"));

			TableColumn<Object_MembershipList, String> Col6a = new TableColumn<Object_MembershipList, String>("First Name");
			Col6a.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("fname"));
			Col6a.setPrefWidth(120);
			
			TableColumn<Object_MembershipList, String> Col6b = new TableColumn<Object_MembershipList, String>("Last Name");
			Col6b.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("lname"));
			Col6b.setPrefWidth(120);
			
			TableColumn<Object_MembershipList, String> Col7 = new TableColumn<Object_MembershipList, String>("Address");
			Col7.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("address"));
			Col7.setPrefWidth(200);
			
			TableColumn<Object_MembershipList, String> Col8 = new TableColumn<Object_MembershipList, String>("City");
			Col8.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("city"));
			Col8.setPrefWidth(120);
			
			TableColumn<Object_MembershipList, String> Col9 = new TableColumn<Object_MembershipList, String>("State");
			Col9.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("state"));

			TableColumn<Object_MembershipList, String> Col10 = new TableColumn<Object_MembershipList, String>("Zip");
			Col10.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("zip"));
			
			TableColumn<Object_MembershipList, String> Col11 = new TableColumn<Object_MembershipList, String>("MSID");
			Col11.setCellValueFactory(new PropertyValueFactory<Object_MembershipList, String>("msid"));

			rosterTableView.getColumns().addAll(Arrays.asList(Col3, Col4, Col5a, Col6, Col6a, Col6b, Col7, Col8, Col9, Col10, Col11));
			rosterTableView.getSortOrder().add(Col3);  // start sorted by membershipID
			rosterTableView.sort();
			
			final Spinner<Integer> yearSpinner = new Spinner<Integer>();
			SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, Integer.parseInt(selectedYear), Integer.parseInt(selectedYear));
			yearSpinner.setValueFactory(wetSlipValueFactory);
			yearSpinner.setPrefWidth(110);
			yearSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
			yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
				  if (!newValue) {
					  printChoices.setYear(yearSpinner.getEditor().getText());
					  selectedYear = yearSpinner.getEditor().getText();  /// kept this for clarity, could have used printChoices.getYear()
					  rosters.clear();
					  if(r1.isSelected())
						  rosters.addAll(SqlSelect.getRoster(selectedYear,true));
					  if(r2.isSelected())
						  rosters.addAll(SqlSelect.getRoster(selectedYear,false));
					  if(r3.isSelected())
						  rosters.addAll(SqlSelect.getNewMemberRoster(selectedYear));
					  if(r4.isSelected())
						  rosters.addAll(SqlSelect.getFullNewMemberRoster(selectedYear));
					  titledPane.setText("Roster " + selectedYear);
					  records.setText(rosters.size() + " Records"); 
					  rosterTableView.sort();
				  }
				});
			
			////////////////////  LISTENERS //////////////////////////
			
			buttonXLS.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					new Xls_roster(rosters,printChoices);
				}
				});
			
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
		            if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
		                 && event.getClickCount() == 2) {
		            	//int rowIndex = row.getIndex();
		                Object_MembershipList clickedRow = row.getItem();
						createTab(clickedRow);
		            }
		        });
		        return row ;
		    });
		    
	  		r1.selectedProperty().addListener(new ChangeListener<Boolean>() {
	  		    @Override
	  		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
	  		        if (isNowSelected) { 
	  		          setListType("active");
	  		          rosters.clear();
	  		          rosters.addAll(SqlSelect.getRoster(selectedYear, true));
	  		          records.setText(rosters.size() + " Records");
	  		          rosterTableView.sort();
	  		        } 
	  		    }
	  		});
	  		
	  		r2.selectedProperty().addListener(new ChangeListener<Boolean>() {
	  		    @Override
	  		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
	  		        if (isNowSelected) { 
	  		        	setListType("non-renew");
		  		          rosters.clear();
		  		          rosters.addAll(SqlSelect.getRoster(selectedYear, false));
		  		          records.setText(rosters.size() + " Records");
		  		          rosterTableView.sort();
	  		        } else {
	  		            // ...
	  		        }
	  		    }
	  		});
	  		
	  		r3.selectedProperty().addListener(new ChangeListener<Boolean>() {
	  		    @Override
	  		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
	  		        if (isNowSelected) { 
	  		        	setListType("new-members");
	  		            rosters.clear();
	  		            rosters.addAll(SqlSelect.getNewMemberRoster(selectedYear));
	  		          records.setText(rosters.size() + " Records");
	  		        rosterTableView.sort();
	  		        } else {
	  		            // ...
	  		        }
	  		    }
	  		});
	  		
	  		r4.selectedProperty().addListener(new ChangeListener<Boolean>() {
	  		    @Override
	  		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
	  		        if (isNowSelected) { 
	  		        	setListType("new-and-return");
	  		            rosters.clear();
	  		            rosters.addAll(SqlSelect.getFullNewMemberRoster(selectedYear));
	  		          records.setText(rosters.size() + " Records");
	  		        rosterTableView.sort();
	  		        } else {
	  		            // ...
	  		        }
	  		    }
	  		});
	  		
	  		titledPane.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
	  		        if (isNowExpanded) {
	  		        	//System.out.println("Title Pane Expanded");
	  		        	rosterTableView.setPrefHeight(555);
	  		        }
	  		        if(wasExpanded) {
	  		        	//System.out.println("Title Pane collapsed");
	  		        	rosterTableView.setPrefHeight(655);
	  		        }
	  		    });

		    
		    //////////////////// SET CONTENT //////////////////////
	  		vboxCheckBox1.getChildren().addAll(c1,c2,c3,c4);
	  		vboxCheckBox2.getChildren().addAll(c5,c6,c7,c8);
	  		vboxCheckBox3.getChildren().addAll(c9,c10,c11,c12);
	  		vboxCheckBox4.getChildren().add(c13);
	  		vboxCheckBox5.getChildren().addAll(buttonXLS);
	  		hboxExportFrame.getChildren().add(hboxExport);
	  		hboxExport.getChildren().addAll(vboxCheckBox1,vboxCheckBox2,vboxCheckBox3,vboxCheckBox4,vboxCheckBox5);
		    vboxRadioButtons.getChildren().addAll(r1,r2,r3,r4);
		    vboxSpinnerLabel.getChildren().addAll(yearSpinner, records);
		    controlsHbox.getChildren().addAll(vboxSpinnerLabel,vboxRadioButtons,hboxExportFrame);
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
                 break;
        case "non-renew":
        	printChoices.setActive(false);
        	printChoices.setNonRenew(true);
        	printChoices.setNewMembers(false);
        	printChoices.setNewAndReturnd(false);
                 break;
        case "new-members":
        	printChoices.setActive(false);
        	printChoices.setNonRenew(false);
        	printChoices.setNewMembers(true);
        	printChoices.setNewAndReturnd(false);
                 break;
        case "new-and-return":
        	printChoices.setActive(false);
        	printChoices.setNonRenew(false);
        	printChoices.setNewMembers(false);
        	printChoices.setNewAndReturnd(true);
        		break;
        }
	}

	private static void createTab(Object_MembershipList clickedRow)  {
		TabLauncher.createTab(clickedRow.getMembershipId(),clickedRow.getMsid());
	}
}
