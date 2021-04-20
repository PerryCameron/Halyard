package com.ecsail.charts;

import java.util.ArrayList;
import java.util.Arrays;

import com.ecsail.structures.Object_Stats;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

public class MembershipStackedBarChart extends StackedBarChart<String,Number> {
	ArrayList<Object_Stats> stats;
	
	public MembershipStackedBarChart(ArrayList<Object_Stats> stats) {
		super(new CategoryAxis(), new NumberAxis());
		this.stats = stats;


	        //this.xAxis.setLabel("Year");
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
	        for (Object_Stats s: stats) {
	    		seriesFamily.getData().add(new XYChart.Data<>(s.getFiscalYear() + "",s.getFamily()));
	    		seriesRegular.getData().add(new XYChart.Data<>(s.getFiscalYear() + "",s.getRegular()));
	    		seriesSocial.getData().add(new XYChart.Data<>(s.getFiscalYear() + "",s.getSocial()));
	    		seriesLakeAssociate.getData().add(new XYChart.Data<>(s.getFiscalYear() + "",s.getLakeAssociates()));
	    		seriesLifeMember.getData().add(new XYChart.Data<>(s.getFiscalYear() + "",s.getLifeMembers()));
	        }
	        getData().addAll(Arrays.asList(seriesFamily,seriesRegular,seriesSocial,seriesLakeAssociate,seriesLifeMember));

	}
}
