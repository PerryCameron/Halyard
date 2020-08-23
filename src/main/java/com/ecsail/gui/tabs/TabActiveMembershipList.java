package com.ecsail.gui.tabs;

import java.util.Arrays;

import com.ecsail.main.TabLauncher;
import com.ecsail.structures.Object_MembershipList;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

public class TabActiveMembershipList extends Tab {
	
	private ObservableList<Object_MembershipList> activememberships;
	private TableView<Object_MembershipList> activeMembershipTableView = new TableView<>();
	
	public TabActiveMembershipList(ObservableList<Object_MembershipList> a) {
		super();
		this.activememberships = a;
		this.setText("Membership List (Active)");
		
		///////////////////  OBJECTS //////////////////////////
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();  // inter vbox
		vbox1.setId("box-blue");
		vbox2.setId("box-pink");
		vbox1.setPadding(new Insets(12,12,15,12));
		vbox2.setPadding(new Insets(3,3,5,3));
		vbox1.setAlignment(Pos.TOP_CENTER);
		vbox1.setPrefHeight(768);

			setOnClosed(null);
			activeMembershipTableView.setItems(activememberships);
			activeMembershipTableView.setPrefWidth(1000);
			activeMembershipTableView.setFixedCellSize(30);
			activeMembershipTableView.setPrefHeight(680);
			
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

			activeMembershipTableView.getColumns().addAll(Arrays.asList(Col3, Col4, Col5a, Col6, Col6a, Col6b, Col7, Col8, Col9, Col10, Col11));
			activeMembershipTableView.getSortOrder().add(Col3);  // start sorted by membershipID
			activeMembershipTableView.sort();
			
			////////////////////  LISTENERS //////////////////////////
		    activeMembershipTableView.setRowFactory(tv -> {
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
		    
		    //////////////////// SET CONTENT //////////////////////
			vbox1.getChildren().add(vbox2);
			vbox2.getChildren().add(activeMembershipTableView);
			setContent(vbox1);
	}

	private static void createTab(Object_MembershipList clickedRow)  {
		TabLauncher.createTab(clickedRow.getMembershipId(),clickedRow.getMsid());
	}
}
