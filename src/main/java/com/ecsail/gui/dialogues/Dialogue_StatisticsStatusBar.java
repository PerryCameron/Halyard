package com.ecsail.gui.dialogues;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.ecsail.main.Paths;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.structures.Object_Stats;
import javafx.concurrent.Task;
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
	private int statId = 0;
	private int selectedYear = 2000;
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
		pb.setPrefHeight(20);
		pb.setPrefWidth(100);
		vboxGrey.setPrefHeight(688);
		vboxGrey.setAlignment(Pos.CENTER);
		scene.getStylesheets().add("stylesheet.css");
		vboxGrey.getChildren().addAll(pb,startButton);
		vboxGrey.setSpacing(20);
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
		
		SqlDelete.deleteStatistics();
		int numberOfYears = Integer.parseInt(Paths.getYear()) - selectedYear + 1;
	    Task<String> task = new Task<String>(){
	        @Override
	        protected String call() {
	        for (int i = 0; i < numberOfYears; i++) {
	        Object_Stats stats = new Object_Stats(selectedYear);
			stats.setStatId(statId);
			stats.refreshStatsForYear(); // built in function for the object to update itself.
			SqlInsert.addStatRecord(stats);
			System.out.println("Adding " + selectedYear);
			selectedYear++;
			statId++;
			System.out.println(statId);
			pb.setProgress((double)statId/20);
		}
			return null;
	        	
	        	
	        }
	    };
	    task.setOnScheduled(e -> { System.out.println("scheduled");});
	    task.setOnSucceeded(e -> { 
	    	//textArea.setText((String) e.getSource().getValue()); 
	    	System.out.println("Finished making directory");});
	    task.setOnFailed(e -> { System.out.println("This failed"); });
	    exec.execute(task);
	    

	}
	
	private final Executor exec = Executors.newCachedThreadPool(runnable -> {
	    Thread t = new Thread(runnable);
	    t.setDaemon(true);
	    return t ;
	});

}
