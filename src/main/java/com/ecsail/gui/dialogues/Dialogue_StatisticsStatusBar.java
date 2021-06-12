package com.ecsail.gui.dialogues;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.ecsail.main.Paths;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.structures.Object_Stats;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dialogue_StatisticsStatusBar extends Stage {
	final ProgressBar pb;
	private int statId = 0;
	private int startYear;
	private int stopYear;
	Button closeButton = new Button("Close");
	HBox hboxButtonHold = new HBox();
	
	public Dialogue_StatisticsStatusBar() {
		stopYear=Integer.parseInt(Paths.getYear());
		startYear=2000;
		
		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		
		
		Scene scene = new Scene(vboxBlue, 400, 200);
		Button startButton = new Button("Start");
		
		
		HBox hboxYearChoose = new HBox();
        TextField startYearTextField = new TextField();
        TextField stopYearTextField = new TextField();
		pb = new ProgressBar(0);

		/////////////////// ATTRIBUTES ///////////////////
		hboxYearChoose.setSpacing(10);
		vboxGrey.setSpacing(20);
		
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		vboxBlue.setId("box-blue");
		// vboxGrey.setId("slip-box");
		
		pb.setPrefSize(300, 30);
		startYearTextField.setPrefWidth(80);
		stopYearTextField.setPrefWidth(80);
		vboxGrey.setPrefHeight(688);
		hboxButtonHold.setAlignment(Pos.CENTER);
		vboxGrey.setAlignment(Pos.CENTER);
		hboxYearChoose.setAlignment(Pos.CENTER);
		scene.getStylesheets().add("stylesheet.css");

		setTitle("Updating Statistics");
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));
		startYearTextField.setText(startYear + "");
		stopYearTextField.setText(stopYear + "");
		//////////////// LISTENERS ///////////////////
		
		startYearTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
            	startYear = Integer.parseInt(startYearTextField.getText());	
            }
        });
		
		stopYearTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //focus out
            if (oldValue) {  // we have focused and unfocused
            	stopYear = Integer.parseInt(stopYearTextField.getText());	
            }
        });
		
		closeButton.setOnAction((event) -> {
			this.close();
		});
		
		startButton.setOnAction((event) -> {
			updateStats();
		});

		//////////////// ADD CONTENT ///////////////////
		hboxButtonHold.getChildren().add(startButton);
		hboxYearChoose.getChildren().addAll(startYearTextField, new Label("-"), stopYearTextField);
		vboxGrey.getChildren().addAll(new Label("Date Range:"),hboxYearChoose,pb,hboxButtonHold);

		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		getIcons().add(mainIcon);
		setScene(scene);
		show();
	}

	public void updateStats() {
		SqlDelete.deleteStatistics();
		System.out.println("Deleted old statistics");
		System.out.println("Calculating new statistics...");
		int numberOfYears = stopYear - startYear + 1;
		int statNumber = numberOfYears;
	    Task<String> task = new Task<String>(){
	        @Override
	        protected String call() {
	        for (int i = 0; i < numberOfYears; i++) {
	        Object_Stats stats = new Object_Stats(startYear);
			stats.setStatId(statId);
			stats.refreshStatsForYear(); // built in function for the object to update itself.
			SqlInsert.addStatRecord(stats);
			System.out.print(startYear + " ");
			startYear++;
			statId++;
			if(statId % 10 == 0) System.out.println();
			pb.setProgress((double)statId/statNumber);
		}
			return null;
	        		
	        }
	    };
	    task.setOnSucceeded(e -> { 
	    	//textArea.setText((String) e.getSource().getValue()); 
	    	System.out.println("Finished updating Statistics");});
	    task.setOnFailed(e -> { System.out.println("Was unable to compile stats"); });
	    exec.execute(task);
		hboxButtonHold.getChildren().clear();
		hboxButtonHold.getChildren().add(closeButton);
	}
	
	private final Executor exec = Executors.newCachedThreadPool(runnable -> {
	    Thread t = new Thread(runnable);
	    t.setDaemon(true);
	    return t ;
	});

}
