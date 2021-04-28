package com.ecsail.gui.dialogues;

import com.ecsail.main.Paths;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.structures.Object_Stats;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dialogue_StatisticsStatusBar extends Stage {
	final ProgressBar pb;

	public Dialogue_StatisticsStatusBar() {

		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Scene scene = new Scene(vboxBlue, 600, 300);
		Button startButton = new Button("Start");

		pb = new ProgressBar(0);

		/////////////////// ATTRIBUTES ///////////////////
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		// vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		vboxGrey.setAlignment(Pos.CENTER);
		scene.getStylesheets().add("stylesheet.css");
		vboxGrey.getChildren().addAll(startButton,pb);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setTitle("Updating Statistics");
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));
		
		//////////////// LISTENERS ///////////////////
		
		startButton.setOnAction((event) -> {
			updateStats();
		});

		//////////////// ADD CONTENT ///////////////////
		getIcons().add(mainIcon);
		setScene(scene);
		show();
	}

	public void updateStats() {
		int statId = 0;
		int selectedYear = 2000;
		Object_Stats stats;
		SqlDelete.deleteStatistics();
		int numberOfYears = Integer.parseInt(Paths.getYear()) - selectedYear + 1;
		for (int i = 0; i < numberOfYears; i++) {
			stats = new Object_Stats(selectedYear);
			stats.setStatId(statId);
			stats.refreshStatsForYear(); // built in function for the object to update itself.
			SqlInsert.addStatRecord(stats);
			System.out.println("Adding " + selectedYear);
			selectedYear++;
			statId++;
			System.out.println(statId);
			pb.setProgress((double)statId/20);
		}
	}

}
