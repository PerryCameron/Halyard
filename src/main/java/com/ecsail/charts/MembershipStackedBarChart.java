package com.ecsail.charts;

import java.util.ArrayList;
import java.util.Arrays;

import com.ecsail.structures.StatsDTO;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

public class MembershipStackedBarChart extends StackedBarChart<String,Number> {
	ArrayList<StatsDTO> stats;
	
	public MembershipStackedBarChart(ArrayList<StatsDTO> stats) {
		super(new CategoryAxis(), new NumberAxis());
		this.stats = stats;
		//this.setAnimated(true);
	        setTitle("Active Memberships By Year");
			XYChart.Series<String,Number> seriesFamily = new Series<String, Number>();
			XYChart.Series<String,Number> seriesRegular = new Series<String, Number>();
			XYChart.Series<String,Number> seriesSocial = new Series<String, Number>();
			XYChart.Series<String,Number> seriesLakeAssociate = new Series<String, Number>();
			XYChart.Series<String,Number> seriesLifeMember = new Series<String, Number>();
			seriesFamily.setName("Family");
			seriesRegular.setName("Regular");
			seriesSocial.setName("Social");
			seriesLakeAssociate.setName("Lake Associate");
			seriesLifeMember.setName("Life Member");
	        for (StatsDTO s: stats) {
	    		seriesFamily.getData().add(new XYChart.Data<>(s.getFiscalYear() + "",s.getFamily()));
	    		seriesRegular.getData().add(new XYChart.Data<>(s.getFiscalYear() + "",s.getRegular()));
	    		seriesSocial.getData().add(new XYChart.Data<>(s.getFiscalYear() + "",s.getSocial()));
	    		seriesLakeAssociate.getData().add(new XYChart.Data<>(s.getFiscalYear() + "",s.getLakeAssociates()));
	    		seriesLifeMember.getData().add(new XYChart.Data<>(s.getFiscalYear() + "",s.getLifeMembers()));
	        }
	        ////////////////  LISTENERS  ///////////////
	        //seriesFamily.getNode().setOnMouseClicked(e -> 
            //System.out.println(seriesFamily.getData()));
	        //////////////// SET CONTENT /////////////
	        
	        getData().addAll(Arrays.asList(seriesFamily,seriesRegular,seriesSocial,seriesLakeAssociate,seriesLifeMember));

	
	}
}
