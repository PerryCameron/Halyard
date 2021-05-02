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
	
	public Dialogue_StatisticsStatusBar() {
		stopYear=Integer.parseInt(Paths.getYear());
		startYear=2000;
		
		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Scene scene = new Scene(vboxBlue, 600, 300);
		Button startButton = new Button("Start");
		
		HBox hboxYearChoose = new HBox();
        TextField startYearTextField = new TextField();
        TextField stopYearTextField = new TextField();
		pb = new ProgressBar(0);

		/////////////////// ATTRIBUTES ///////////////////
		hboxYearChoose.setSpacing(10);
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		// vboxGrey.setId("slip-box");
		
		pb.setPrefHeight(20);
		pb.setPrefWidth(100);
		startYearTextField.setPrefWidth(80);
		stopYearTextField.setPrefWidth(80);
		vboxGrey.setPrefHeight(688);
		vboxGrey.setAlignment(Pos.CENTER);
		scene.getStylesheets().add("stylesheet.css");
		hboxYearChoose.getChildren().addAll(startYearTextField, stopYearTextField);
		vboxGrey.getChildren().addAll(hboxYearChoose,pb,startButton);
		vboxGrey.setSpacing(20);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
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
			System.out.println("Adding " + startYear);
			startYear++;
			statId++;
			System.out.println(statId);
			pb.setProgress((double)statId/statNumber);
		}
			return null;
	        		
	        }
	    };
	    task.setOnSucceeded(e -> { 
	    	//textArea.setText((String) e.getSource().getValue()); 
	    	System.out.println("Finished updating Statistics");});
	    task.setOnFailed(e -> { System.out.println("This failed"); });
	    exec.execute(task);
	    

	}
	
	private final Executor exec = Executors.newCachedThreadPool(runnable -> {
	    Thread t = new Thread(runnable);
	    t.setDaemon(true);
	    return t ;
	});

}
