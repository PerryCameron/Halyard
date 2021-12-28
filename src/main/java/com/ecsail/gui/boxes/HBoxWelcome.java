package com.ecsail.gui.boxes;

import com.ecsail.charts.MembershipLineChart;
import com.ecsail.charts.MembershipStackedBarChart;
import com.ecsail.main.CreateMembership;
import com.ecsail.main.Launcher;
import com.ecsail.main.Statistics;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
// this is the contents inside tabWelcome() launched from ConnectDatabase() about line 229
public class HBoxWelcome extends HBox {
	private VBox vboxLeft;
	private Statistics dbStats;
	
	public HBoxWelcome() {
		this.dbStats = new Statistics();
		this.vboxLeft = new VBox();
		
		int width = 400;
		int height = 70;
		
		VBox vboxRight = new VBox();
		Button peopleListButton = new Button("People");
		Button slipListButton = new Button("Slips");
		Button bodButton = new Button("Board of Directors");
		Button newButton = new Button("Create New Membership");
		Button batchesButton = new Button("Deposits");
		Button rosterButton = new Button("Rosters");
		Button boatsButton = new Button("Boats");
		Button notesButton = new Button("Notes");
		MembershipStackedBarChart membershipsByYearChart = new MembershipStackedBarChart(dbStats.getStats());
		MembershipLineChart membershipStatisticsChart = new MembershipLineChart(dbStats.getStats());
		
		////////////////  ATTRIBUTES //////////////////////////////
		//vboxLeft.setStyle("-fx-background-color: #e83115;");  // red
		//vboxRight.setStyle("-fx-background-color: #feffab;");  // purble
		vboxRight.setPrefWidth(width);
		vboxRight.setMinWidth(350);
		vboxLeft.setMinWidth(350);
		vboxLeft.setMaxWidth(1400);
		vboxLeft.setPrefWidth(Double.MAX_VALUE);
		vboxLeft.setPrefHeight(1200);
		membershipsByYearChart.setMaxHeight(700);
		notesButton.setId("bigbuttontext");
		boatsButton.setId("bigbuttontext");
		newButton.setId("bigbuttontext");
		bodButton.setId("bigbuttontext");
		peopleListButton.setId("bigbuttontext");
		slipListButton.setId("bigbuttontext");
		batchesButton.setId("bigbuttontext");
		rosterButton.setId("bigbuttontext");
		vboxRight.setSpacing(10);
		vboxRight.setPadding(new Insets(30,0,0,0));
		this.setPadding(new Insets(0,10,0,0));
		this.setSpacing(10);
		VBox.setVgrow(vboxRight, Priority.ALWAYS);
		VBox.setVgrow(vboxLeft, Priority.ALWAYS);
		notesButton.setMaxWidth(Double.MAX_VALUE);
		boatsButton.setMaxWidth(Double.MAX_VALUE);
		peopleListButton.setMaxWidth(Double.MAX_VALUE);
		slipListButton.setMaxWidth(Double.MAX_VALUE);
		bodButton.setMaxWidth(Double.MAX_VALUE);
		newButton.setMaxWidth(Double.MAX_VALUE);
		batchesButton.setMaxWidth(Double.MAX_VALUE);
		rosterButton.setMaxWidth(Double.MAX_VALUE);
		
		notesButton.setPrefHeight(height);
		boatsButton.setPrefHeight(height);
		peopleListButton.setPrefHeight(height);
		slipListButton.setPrefHeight(height);
		bodButton.setPrefHeight(height);
		newButton.setPrefHeight(height);
		batchesButton.setPrefHeight(height);
		rosterButton.setPrefHeight(height);
		
		///////////////// LISTENERS  /////////////////////////

		boatsButton.setOnAction((event) -> Launcher.openBoatsTab());
		notesButton.setOnAction((event) -> Launcher.openNotesTab());
		rosterButton.setOnAction((event) -> Launcher.openRosterTab());
		peopleListButton.setOnAction((event) -> Launcher.openPeopleTab());
		slipListButton.setOnAction((event) -> Launcher.openSlipsTab());
		bodButton.setOnAction((event) -> Launcher.openBoardTab());
		newButton.setOnAction((event) -> CreateMembership.Create());
		batchesButton.setOnAction((event) -> Launcher.openTabBatchedPaidDues());

		////////////////  SET CONTENT ////////////////////////
		vboxRight.getChildren().addAll(rosterButton,peopleListButton,slipListButton,bodButton,newButton,batchesButton,boatsButton,notesButton);
		vboxLeft.getChildren().addAll(membershipsByYearChart,membershipStatisticsChart);

		//vboxLeft.getChildren().addAll(Charts.getLineChart());
		getChildren().addAll(vboxLeft,vboxRight);
	}

	public VBox getVboxLeft() {
		return vboxLeft;
	}

	public void setVboxLeft(VBox vboxLeft) {
		this.vboxLeft = vboxLeft;
	}

	public Statistics getDbStats() {
		return dbStats;
	}
}

