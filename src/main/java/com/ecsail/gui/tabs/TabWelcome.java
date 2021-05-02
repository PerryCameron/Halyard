package com.ecsail.gui.tabs;

import com.ecsail.charts.MembershipLineChart;
import com.ecsail.charts.MembershipStackedBarChart;
import com.ecsail.gui.boxes.BoxWelcome;
import com.ecsail.main.Main;
import com.ecsail.main.Paths;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class TabWelcome extends Tab {
	
	public TabWelcome(BoxWelcome boxWelcome) {  // check boxWelcome
		super();
		setText("Welcome");
		double titleBarHeight = Main.getPrimaryStage().getHeight() - Main.getPrimaryScene().getHeight();
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		vboxBlue.setPrefWidth(1028);
		boxWelcome.setPrefHeight(680);
		if(Paths.isWindows())
		vboxBlue.setPrefHeight(Main.getPrimaryScene().getHeight() - titleBarHeight - Main.getToolBarHeight());
		else
		vboxBlue.setPrefHeight(Main.getPrimaryScene().getHeight() - titleBarHeight - Main.getToolBarHeight() -24);
		vboxPink.setPrefHeight(695);
		
		Main.getPrimaryStage().heightProperty().addListener((obs, oldVal, newVal) -> {
			 //System.out.println((double)newVal);
			// System.out.println(vboxPink.getHeight());
			 vboxPink.setPrefHeight(686 + (double)newVal - 796);// 570 is start height
		});
		
		
		this.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
		    if (newValue) {  // focus Gained
		    	boxWelcome.getVboxLeft().getChildren().clear();
		    	boxWelcome.getDbStats().reload();
		    	boxWelcome.getVboxLeft().getChildren().addAll(new MembershipStackedBarChart(boxWelcome.getDbStats().getStats()),new MembershipLineChart(boxWelcome.getDbStats().getStats()));
		    	
		    }
		});
		
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(boxWelcome);
		setContent(vboxBlue);
		
	}
}
