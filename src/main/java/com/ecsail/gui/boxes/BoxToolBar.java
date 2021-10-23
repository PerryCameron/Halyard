package com.ecsail.gui.boxes;

import com.ecsail.main.*;
import com.ecsail.excel.Xls_email_list;
import com.ecsail.gui.dialogues.Dialogue_DirectoryCreation;
import com.ecsail.gui.dialogues.Dialogue_NewYearGenerator;
import com.ecsail.gui.dialogues.Dialogue_StatisticsStatusBar;
import com.ecsail.gui.dialogues.Dialogue_Stub;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BoxToolBar extends VBox {

	public BoxToolBar() {
		
		//HBox toolBar = new HBox();
		MenuBar menuBar = new MenuBar();
		Menu menu1 = new Menu("File");
		Menu menu2 = new Menu("Search");
		Menu menu3 = new Menu("Membership");
		Menu menu4 = new Menu("Reports");
		Menu menu5 = new Menu("About");
		//Menu menu6 = new Menu("Directory");
		
		Menu m1_1 = new Menu("Export");
		MenuItem m1_1_1 = new MenuItem("Create SQL Script");
		MenuItem m1_2 = new MenuItem("Console");
		MenuItem m1_3 = new MenuItem("Update Statistics");

		
		MenuItem m3_1 = new MenuItem("New Membership");
		MenuItem m3_2 = new MenuItem("New Year Generator");
		MenuItem m3_4 = new MenuItem("Fees");
		
		
		Menu m3_3 = new Menu("List");		
		MenuItem m3_3_1 = new MenuItem("Active Membership");
		MenuItem m3_3_2 = new MenuItem("Inactive Membership");
		MenuItem m3_3_3 = new MenuItem("People");
		MenuItem m3_3_4 = new MenuItem("Boats");
		MenuItem m3_3_5 = new MenuItem("Slips");
		MenuItem m3_3_6 = new MenuItem("Board Members");
		
		Menu m4_1 = new Menu("Create");	
		MenuItem m4_1_1 = new MenuItem("Treasurer's Report");
		MenuItem m4_1_2 = new MenuItem("Boat Report");
		MenuItem m4_1_3 = new MenuItem("Deposits");
		MenuItem m4_1_4 = new MenuItem("Email List");
		MenuItem m4_1_5 = new MenuItem("Window Stub");
		MenuItem m4_1_6 = new MenuItem("Tab Stub");
		MenuItem m4_1_7 = new MenuItem("Create membership id's");
		MenuItem m4_1_8 = new MenuItem("Renewal Forms");
		MenuItem m4_1_9 = new MenuItem("Create Envelopes");	
		MenuItem m4_1_10 = new MenuItem("Create Directory");

        m3_3_6.setOnAction((event) -> Launcher.openBoardTab());
        m3_3_3.setOnAction((event) -> Launcher.openPeopleTab());
        m1_1_1.setOnAction((event) -> SqlScriptMaker.createSql());
        m1_3.setOnAction((event) -> {
        new Dialogue_StatisticsStatusBar();
        });
		m4_1_3.setOnAction((event) -> Launcher.openTabBatchedPaidDues());
		m4_1_4.setOnAction((event) -> Xls_email_list.createSpreadSheet());
		m4_1_5.setOnAction((event) -> new Dialogue_Stub());
		m4_1_6.setOnAction((event) -> Launcher.openTabStub());
		m4_1_7.setOnAction((event) -> Launcher.openTabNewYearGenerator());
		m4_1_8.setOnAction((event) -> Launcher.createRenewalForms());
		m4_1_9.setOnAction((event) -> Launcher.openEnvelopesDialogue());
        m3_3_1.setOnAction((event) -> Launcher.openRosterTab());
        m3_3_5.setOnAction((event) -> Launcher.openSlipsTab());
        m3_1.setOnAction((event) -> CreateMembership.Create());
        m3_2.setOnAction((event) ->  {
            	System.out.println("Trying to launch this POS");
            	new Dialogue_NewYearGenerator();
            });
        m3_4.setOnAction((event) -> Launcher.openFeeTab());
        
        m1_2.setOnAction((event) ->  {
            	////////// OBJECTS /////////
                StackPane secondaryLayout = new StackPane();
                Scene secondScene = new Scene(secondaryLayout, 1024, 500);
                Stage newWindow = new Stage();
                secondaryLayout.getChildren().add(Main.console);
                newWindow.setTitle("Console");
                newWindow.setScene(secondScene);
                newWindow.show();
            });

        
        m4_1_10.setOnAction((event) -> new Dialogue_DirectoryCreation());
        
        m1_1.getItems().addAll(m1_1_1);
        m4_1.getItems().addAll(m4_1_1,m4_1_2,m4_1_3,m4_1_4,m4_1_5,m4_1_6,m4_1_7,m4_1_8,m4_1_9,m4_1_10);
        m3_3.getItems().addAll(m3_3_1,m3_3_2,m3_3_3,m3_3_4,m3_3_5,m3_3_6);  // add list items
        menu1.getItems().addAll(m1_1,m1_2,m1_3);
        menu3.getItems().addAll(m3_1,m3_2,m3_3,m3_4);
        menu4.getItems().add(m4_1);

        menuBar.getMenus().addAll(menu1,menu2,menu3,menu4,menu5);

        //toolBar.setAlignment(Pos.CENTER_LEFT);
		//toolBar.getChildren().addAll(searchField,membershipList,peopleList,boatList,reports,console,addNewMembership);
		getChildren().addAll(menuBar);	
	}
}
