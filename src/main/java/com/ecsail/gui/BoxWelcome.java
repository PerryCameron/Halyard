package com.ecsail.gui;

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
		Button activeMemListButton = new Button("Active Membership List");
		Button peopleListButton = new Button("People");
		Button slipListButton = new Button("Slips");
		Button bodButton = new Button("Board of Directors");
		Button newButton = new Button("New Membership");
		
		int activeMembership = SqlSelect.getActiveMembershipCount();
		int activePeople = SqlSelect.getActivePeopleCount();
		Text activeText = new Text("There are currently " + activeMembership + " active memberships");
		Text activepeopleText = new Text("There are currently " + activePeople + " people attached to active memberships");
	
		////////////////  ATTRIBUTES //////////////////////////////
		
		newButton.setId("bigbuttontext");
		bodButton.setId("bigbuttontext");
		activeMemListButton.setId("bigbuttontext");
		peopleListButton.setId("bigbuttontext");
		slipListButton.setId("bigbuttontext");
		activeText.setId("");
		vboxRight.setSpacing(10);
		vboxRight.setPadding(new Insets(30,0,0,250));
		activeMemListButton.setPrefSize(width, height);
		peopleListButton.setPrefSize(width, height);
		slipListButton.setPrefSize(width, height);
		bodButton.setPrefSize(width, height);
		newButton.setPrefSize(width, height);
		
		///////////////// LISTENERS  /////////////////////////
		
		activeMemListButton.setOnAction(new EventHandler<ActionEvent>() {
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
		
		////////////////  SET CONTENT ////////////////////////
		vboxLeft.getChildren().addAll(activeText,activepeopleText);
		vboxRight.getChildren().addAll(activeMemListButton,peopleListButton,slipListButton,bodButton,newButton);
		getChildren().addAll(vboxLeft,vboxRight);
		
	}

}
