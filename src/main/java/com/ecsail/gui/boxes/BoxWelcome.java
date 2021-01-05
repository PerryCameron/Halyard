package com.ecsail.gui.boxes;

import com.ecsail.main.CreateMembership;
import com.ecsail.main.SqlSelect;
import com.ecsail.main.TabLauncher;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
// this is the contents inside tabWelcome() launched from ConnectDatabase() about line 229
public class BoxWelcome extends HBox {
	
	public BoxWelcome() {
		int width = 400;
		int height = 70;
		VBox vboxLeft = new VBox();
		VBox vboxRight = new VBox();
		//Button rosterButton = new Button("Rosters");
		Button peopleListButton = new Button("People");
		Button slipListButton = new Button("Slips");
		Button bodButton = new Button("Board of Directors");
		Button newButton = new Button("Create New Membership");
		Button batchesButton = new Button("Deposits");
		Button rosterButton = new Button("Rosters");
		
		int activeMembership = SqlSelect.getActiveMembershipCount();
		int activePeople = SqlSelect.getActivePeopleCount();
		//int familyMembership = SqlSelect.getMembershipTypeCount("FM");
		Text activeText = new Text("There are currently " + activeMembership + " active memberships");
		Text familyMembershipText = new Text("	-" + SqlSelect.getMembershipTypeCount("FM") + " family memberships");
		Text regularMembershipText = new Text("	-" + SqlSelect.getMembershipTypeCount("RM") + " regular memberships");
		Text socialMembershipText = new Text("	-" + SqlSelect.getMembershipTypeCount("SO") + " social memberships");
		Text lakeAssociateMembershipText = new Text("	-" + SqlSelect.getMembershipTypeCount("LA") + " lake associates");
		Text lifeMembershipText = new Text("	-" + SqlSelect.getMembershipTypeCount("LM") + " life memberships");
		Text activepeopleText = new Text("There are currently " + activePeople + " people attached to active memberships");
	
		////////////////  ATTRIBUTES //////////////////////////////
		
		activeText.setStyle("-fx-font: 14 Helvetica;");
		familyMembershipText.setStyle("-fx-font: 12 Helvetica;");
		regularMembershipText.setStyle("-fx-font: 12 Helvetica;");
		socialMembershipText.setStyle("-fx-font: 12 Helvetica;");
		lakeAssociateMembershipText.setStyle("-fx-font: 12 Helvetica;");
		lifeMembershipText.setStyle("-fx-font: 12 Helvetica;");
		activepeopleText.setStyle("-fx-font: 14 Helvetica;");
		
		newButton.setId("bigbuttontext");
		bodButton.setId("bigbuttontext");
		//rosterButton.setId("bigbuttontext");
		peopleListButton.setId("bigbuttontext");
		slipListButton.setId("bigbuttontext");
		batchesButton.setId("bigbuttontext");
		rosterButton.setId("bigbuttontext");
		activeText.setId("");
		vboxRight.setSpacing(10);
		vboxRight.setPadding(new Insets(30,0,0,200));
		//rosterButton.setPrefSize(width, height);
		peopleListButton.setPrefSize(width, height);
		slipListButton.setPrefSize(width, height);
		bodButton.setPrefSize(width, height);
		newButton.setPrefSize(width, height);
		batchesButton.setPrefSize(width, height);
		rosterButton.setPrefSize(width, height);
		
		///////////////// LISTENERS  /////////////////////////
		
		rosterButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				TabLauncher.openMembershipListTab();
			}
		});
		
		peopleListButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				TabLauncher.openPeopleTab();
			}
		});
		
		slipListButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				TabLauncher.openSlipsTab();
			}
		});
		
		bodButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				TabLauncher.openBoardTab();
			}
		});
		
		newButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				CreateMembership.Create();
			}
		});
		
		batchesButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				TabLauncher.openTabBatchedPaidDues();
			}
		});
		
		
		
		////////////////  SET CONTENT ////////////////////////
		vboxLeft.getChildren().addAll(activeText,familyMembershipText,regularMembershipText,socialMembershipText,lakeAssociateMembershipText,lifeMembershipText,activepeopleText);
		vboxRight.getChildren().addAll(rosterButton,peopleListButton,slipListButton,bodButton,newButton,batchesButton);
		getChildren().addAll(vboxLeft,vboxRight);
		
	}

}
