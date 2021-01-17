package com.ecsail.gui.boxes;

import java.io.IOException;
import com.ecsail.main.Main;
import com.ecsail.excel.Xls_email_list;
import com.ecsail.gui.dialogues.Dialogue_NewYearGenerator;
import com.ecsail.gui.dialogues.Dialogue_Stub;
import com.ecsail.main.CreateMembership;
import com.ecsail.main.SqlScriptMaker;
import com.ecsail.main.TabLauncher;
import com.ecsail.pdf.PDF_Boat;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
		
		Menu m1_1 = new Menu("Export");
		MenuItem m1_1_1 = new MenuItem("Create SQL Script");
		MenuItem m1_2 = new MenuItem("Console");
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

        m3_3_6.setOnAction(new EventHandler<ActionEvent>() {  // open active membership list
            @Override public void handle(ActionEvent e) {
            	TabLauncher.openBoardTab();
            }
        });
		
        m3_3_3.setOnAction(new EventHandler<ActionEvent>() {  // open active membership list
            @Override public void handle(ActionEvent e) {
					TabLauncher.openPeopleTab();
            }
        });
		
        m1_1_1.setOnAction(new EventHandler<ActionEvent>() {  // open active membership list
            @Override public void handle(ActionEvent e) {
            	SqlScriptMaker.createSql();
            }
        });
        
		m4_1_2.setOnAction(new EventHandler<ActionEvent>() {  // add new membership
            @Override public void handle(ActionEvent e) {
            	try {
					PDF_Boat.createPdf();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
		
		m4_1_3.setOnAction(new EventHandler<ActionEvent>() {  // add new membership
            @Override public void handle(ActionEvent e) {
            	TabLauncher.openTabBatchedPaidDues();
            }
        });
		
		m4_1_4.setOnAction(new EventHandler<ActionEvent>() {  // add new membership
            @Override public void handle(ActionEvent e) {
            	Xls_email_list.createSpreadSheet();
            }
        });
		
		m4_1_5.setOnAction(new EventHandler<ActionEvent>() {  // add new membership
            @Override public void handle(ActionEvent e) {
				new Dialogue_Stub();
            }
        });
		
		m4_1_6.setOnAction(new EventHandler<ActionEvent>() {  // add new membership
            @Override public void handle(ActionEvent e) {
            	TabLauncher.openTabStub();
            }
        });
		
		m4_1_7.setOnAction(new EventHandler<ActionEvent>() {  // add new membership
            @Override public void handle(ActionEvent e) {
            	TabLauncher.openTabNewYearGenerator();
            }
        });
		
		m4_1_8.setOnAction(new EventHandler<ActionEvent>() {  // add new membership
            @Override public void handle(ActionEvent e) {
            	TabLauncher.createRenewalForms();
            }
        });
		
		m4_1_9.setOnAction(new EventHandler<ActionEvent>() {  // add new membership
            @Override public void handle(ActionEvent e) {
            	TabLauncher.openEnvelopesDialogue();
            }
        });
		
		
        m3_3_1.setOnAction(new EventHandler<ActionEvent>() {  // open active membership list
            @Override public void handle(ActionEvent e) {
            	TabLauncher.openMembershipListTab();
            }
        });
        
        m3_3_5.setOnAction(new EventHandler<ActionEvent>() {  // open active membership list
            @Override public void handle(ActionEvent e) {
            	TabLauncher.openSlipsTab();
            }
        });
        
        m3_1.setOnAction(new EventHandler<ActionEvent>() {  // add new membership
            @Override public void handle(ActionEvent e) {
            	CreateMembership.Create();
            }
        });
        
        m3_2.setOnAction(new EventHandler<ActionEvent>() {  // add new membership
            @Override public void handle(ActionEvent e) {
            	System.out.println("Trying to launch this POS");
            	new Dialogue_NewYearGenerator();
            }
        });
        
        m3_4.setOnAction(new EventHandler<ActionEvent>() {  // add new membership
            @Override public void handle(ActionEvent e) {
            	TabLauncher.openFeeTab();
            }
        });
        
        m1_2.setOnAction(new EventHandler<ActionEvent>() {  // open console
        	 
            @Override
            public void handle(ActionEvent event) {
            	////////// OBJECTS /////////
                StackPane secondaryLayout = new StackPane();
                Scene secondScene = new Scene(secondaryLayout, 1024, 500);
                Stage newWindow = new Stage();
                secondaryLayout.getChildren().add(Main.console);
 
                ////////// ATTRIBUTES //////////
                
                newWindow.setTitle("Console");
                newWindow.setScene(secondScene);
                
 
               // Set position of second window, related to primary window.
               // newWindow.setX(primaryStage.getX() + 200);
               // newWindow.setY(primaryStage.getY() + 100);
                newWindow.show();
            }
        });
        m1_1.getItems().addAll(m1_1_1);
        m4_1.getItems().addAll(m4_1_1,m4_1_2,m4_1_3,m4_1_4,m4_1_5,m4_1_6,m4_1_7,m4_1_8,m4_1_9);
        m3_3.getItems().addAll(m3_3_1,m3_3_2,m3_3_3,m3_3_4,m3_3_5,m3_3_6);  // add list items
        menu1.getItems().addAll(m1_1,m1_2);
        menu3.getItems().addAll(m3_1,m3_2,m3_3,m3_4);
        menu4.getItems().add(m4_1);
        
        menuBar.getMenus().addAll(menu1,menu2,menu3,menu4,menu5);

        //toolBar.setAlignment(Pos.CENTER_LEFT);
		//toolBar.getChildren().addAll(searchField,membershipList,peopleList,boatList,reports,console,addNewMembership);
		getChildren().addAll(menuBar);	
	}
}
