package com.ecsail.gui.boxes;

import com.ecsail.gui.dialogues.*;
import com.ecsail.main.*;
import com.ecsail.excel.Xls_email_list;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VBoxToolBar extends VBox {

	public VBoxToolBar(Stage primaryStage) {
		
		//HBox toolBar = new HBox();
		MenuBar menuBar = new MenuBar();
		Menu menu1 = new Menu("File");
		Menu menu2 = new Menu("Search");
		Menu menu3 = new Menu("Membership");
		Menu menu4 = new Menu("Reports");
		Menu menu5 = new Menu("About");
		//Menu menu6 = new Menu("Directory");

		/////// Menu 1 "File" ///////

		Menu subMenu1_1 = new Menu("Export");
			MenuItem m1_1_1 = new MenuItem("Create SQL Script");
//			MenuItem m1_3 = new MenuItem("Update Statistics");
			MenuItem m1_4 = new MenuItem("Close connection");


//		MenuItem m3_5 = new MenuItem("Membership by MSID");

		/////// Menu 2 "Membership" ///////

		MenuItem m2_1 = new MenuItem("by ID");
		MenuItem m2_2 = new MenuItem("by MSID");

		/////// Menu 3 "Membership" ///////

		// Submenu
		Menu subMenu3_1 = new Menu("Tabs");
			MenuItem m3_1_1 = new MenuItem("Rosters");
			MenuItem m3_1_2 = new MenuItem("Board Of Directors");
			MenuItem m3_1_3 = new MenuItem("People");
			MenuItem m3_1_4 = new MenuItem("Boats");
			MenuItem m3_1_5 = new MenuItem("Slips");
			MenuItem m3_1_6 = new MenuItem("Deposits");
			MenuItem m3_1_7 = new MenuItem("Fees");
			MenuItem m3_1_8 = new MenuItem("Fees (experimental)");
			MenuItem m3_1_9 = new MenuItem("Notes");
			MenuItem m3_2_0 = new MenuItem("Jotform");
		// submenu
		Menu subMenu3_2 = new Menu("Create");
			MenuItem m3_2_1 = new MenuItem("New Membership");
			MenuItem m3_2_2 = new MenuItem("Envelopes");

		MenuItem m3_3 = new MenuItem("New Year Generator");
		MenuItem m3_4 = new MenuItem("Create non renew members");

		/////// Menu 4 "Reports" ///////
		
		Menu subMenu4_1 = new Menu("Create");
			MenuItem m4_1_2 = new MenuItem("Boat Report");
			MenuItem m4_1_3 = new MenuItem("Membership Report");
			MenuItem m4_1_4 = new MenuItem("Email List");
//			MenuItem m4_1_5 = new MenuItem("Window Stub");
//			MenuItem m4_1_6 = new MenuItem("Tab Stub");
			MenuItem m4_1_8 = new MenuItem("Renewal Forms");
			MenuItem m4_1_10 = new MenuItem("Directory");

		//// Menu 1 "File" Listeners ///
		m1_1_1.setOnAction((event) -> SqlScriptMaker.createSql());
		m1_4.setOnAction((event) -> closeConnection(primaryStage));
		//// Menu 2 "Search" Listeners ///
		m2_1.setOnAction((event -> new Dialogue_MembershipIdSearch()));
		m2_2.setOnAction((event) -> new Dialogue_Msid());

		//// Menu 3 "Membership" Listeners ///

		m3_2_1.setOnAction((event) -> CreateMembership.Create());
		m3_3.setOnAction((event) ->  new Dialogue_NewYearGenerator());
		// Sub menu "Membership > Tabs"
			m3_1_1.setOnAction((event) -> Launcher.openRosterTab());
			m3_1_2.setOnAction((event -> Launcher.openBoardTab()));
			m3_1_3.setOnAction((event) -> Launcher.openPeopleTab());
			m3_1_4.setOnAction((event) -> Launcher.openBoatsTab());
			m3_1_5.setOnAction((event) -> Launcher.openSlipsTab());
			m3_1_6.setOnAction((event) -> Launcher.openDepositsTab());
			m3_1_7.setOnAction((event) -> Launcher.openFeeTab());
			m3_1_8.setOnAction((event) -> Launcher.openFeeTab2());
			m3_1_9.setOnAction((event) -> Launcher.openNotesTab());
			m3_2_0.setOnAction((event) -> Launcher.openJotFormTab());
		// submenu "Reports > Create"
			m4_1_2.setOnAction((event) -> Launcher.createBoatReport());
			m4_1_3.setOnAction((event) -> Launcher.createMembershipReport());
			m4_1_4.setOnAction((event) -> Xls_email_list.createSpreadSheet());
//			m4_1_5.setOnAction((event) -> new Dialogue_Stub());
//			m4_1_6.setOnAction((event) -> Launcher.openTabStub());
			m4_1_8.setOnAction((event) -> Launcher.createRenewalForms());
			m3_2_2.setOnAction((event) -> Launcher.openEnvelopesDialogue());
			m3_4.setOnAction((event) -> Launcher.createNonRenews());
        
        m4_1_10.setOnAction((event) -> new Dialogue_DirectoryCreation());
        
        subMenu1_1.getItems().addAll(m1_1_1);
        subMenu3_1.getItems().addAll(m3_1_1,m3_1_2,m3_1_3,m3_1_4,m3_1_5,m3_1_6,m3_1_7,m3_1_8,m3_1_9,m3_2_0);  // add list items
		subMenu3_2.getItems().addAll(m3_2_1,m3_2_2);
		subMenu4_1.getItems().addAll(m4_1_2,m4_1_3,m4_1_4,m4_1_8,m4_1_10);

        menu1.getItems().addAll(subMenu1_1,m1_4);
		menu2.getItems().addAll(m2_1,m2_2);
        menu3.getItems().addAll(subMenu3_1,subMenu3_2,m3_3,m3_4);
        menu4.getItems().add(subMenu4_1);

        menuBar.getMenus().addAll(menu1,menu2,menu3,menu4,menu5);
		getChildren().addAll(menuBar);	
	}

	private void closeConnection(Stage primaryStage) {
		Halyard.closeDatabaseConnection();
		Launcher.closeTabs();
		primaryStage.setTitle("ECSC Membership Database (not connected)");
		Halyard.connectDatabase();
	}
}
