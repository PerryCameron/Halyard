package com.ecsail.gui.tabs;

import com.ecsail.charts.MembershipLineChart;
import com.ecsail.charts.MembershipStackedBarChart;
import com.ecsail.gui.boxes.BoxWelcome;
import com.ecsail.main.Main;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class TabWelcome extends Tab {
	
	public TabWelcome(BoxWelcome boxWelcome) {  // check boxWelcome
		super();
		setText("Welcome");
		
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		vboxBlue.setPrefWidth(1028);
		boxWelcome.setPrefHeight(680);
		// vboxGrey.setPrefHeight(688)
		vboxPink.setMaxHeight(1000);
		vboxPink.setPrefHeight(686);
		
		Main.getPrimaryStage().heightProperty().addListener((obs, oldVal, newVal) -> {
			 //System.out.println((double)newVal);
			 System.out.println(vboxPink.getHeight());
			 vboxPink.setPrefHeight(686 + (double)newVal - 796);// 570 is start height
		});
		
		
		this.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
		    if (newValue) {  // focus Gained
		    	boxWelcome.getVboxLeft().getChildren().clear();
		    	boxWelcome.getVboxLeft().getChildren().addAll(new MembershipStackedBarChart(boxWelcome.getStats()),new MembershipLineChart(boxWelcome.getStats()));
		    	
		    }
		});
		
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(boxWelcome);
		setContent(vboxBlue);
		
	}
}
