package com.ecsail.gui.boxes;

import com.ecsail.gui.customwidgets.BigButton;
import com.ecsail.main.CreateMembership;
import com.ecsail.main.Halyard;
import com.ecsail.main.Launcher;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

// this is the contents inside tabWelcome() launched from ConnectDatabase() about line 229
public class HBoxWelcome extends HBox {

	
	public HBoxWelcome() {
		Halyard.getLogger().info("Opening welcome tab");
		int width = 400;

		VBox vboxLeft = new VBoxCharts();
		VBox vboxRight = new VBox();

		Button peopleListButton = new BigButton("People");
		Button slipListButton = new BigButton("Slips");
		Button bodButton = new BigButton("Board of Directors");
		Button newButton = new BigButton("Create New Membership");
		Button batchesButton = new BigButton("Deposits");
		Button rosterButton = new BigButton("Rosters");
		Button boatsButton = new BigButton("Boats");
		Button notesButton = new BigButton("Notes");
		Button jotFormButton = new BigButton("JotForm");

		
		////////////////  ATTRIBUTES //////////////////////////////
		vboxRight.setPrefWidth(width);
		vboxRight.setMinWidth(350);
		vboxRight.setSpacing(10);
		vboxRight.setPadding(new Insets(15,0,0,0));
		this.setPadding(new Insets(0,10,0,0));
		this.setSpacing(10);
		VBox.setVgrow(vboxRight, Priority.ALWAYS);
		VBox.setVgrow(vboxLeft, Priority.ALWAYS);
		
		///////////////// LISTENERS  /////////////////////////

		boatsButton.setOnAction((event) -> Launcher.openBoatsTab());
		notesButton.setOnAction((event) -> Launcher.openNotesTab());
		rosterButton.setOnAction((event) -> Launcher.openRosterTab());
		peopleListButton.setOnAction((event) -> Launcher.openPeopleTab());
		slipListButton.setOnAction((event) -> Launcher.openSlipsTab());
		bodButton.setOnAction((event) -> Launcher.openBoardTab());
		newButton.setOnAction((event) -> CreateMembership.Create());
		batchesButton.setOnAction((event) -> Launcher.openDepositsTab());
		jotFormButton.setOnAction((event)-> Launcher.openJotFormTab());

		////////////////  SET CONTENT ////////////////////////

		vboxRight.getChildren().addAll(rosterButton,peopleListButton,slipListButton,bodButton,newButton,batchesButton,boatsButton,notesButton,jotFormButton);
		getChildren().addAll(vboxLeft,vboxRight);
	}

}

