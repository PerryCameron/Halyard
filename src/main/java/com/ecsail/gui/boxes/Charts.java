package com.ecsail.gui.boxes;

import java.util.Arrays;

import com.ecsail.main.Paths;
import com.ecsail.sql.SqlSelect;

import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class Charts {

	static public LineChart<String, Number> getLineChart() {
		int startYear = 2000;
		int numberOfYears = Integer.parseInt(Paths.getYear()) - startYear + 1;
        final Axis<String> xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
		//xAxis.setLabel("Years");
		final LineChart<String,Number> lineChart = 
                new LineChart<String,Number>(xAxis,yAxis);
		
		//int activeMembership = SqlSelect.getActiveMembershipCount(Paths.getYear());
		lineChart.setTitle("Non-renewed, New, and Return Memberships");
		//lineChart.setPrefHeight(200);
		//XYChart.Series<String,Number> seriesActive = new Series<String, Number>();
		XYChart.Series<String,Number> seriesNonRenew = new Series<String, Number>();
		XYChart.Series<String,Number> seriesNewMembers = new Series<String, Number>();
		//seriesActive.setName("Active");
		seriesNonRenew.setName("Non-Renew");
		seriesNewMembers.setName("New");
        //populating the series with data
		for (int i = 0; i < numberOfYears; i++) {
		//int activeMembers = SqlSelect.getNumberOfActiveMembershipsForYear(startYear);
		int nonRenewMembers = SqlSelect.getNumberOfInactiveMembershipsForYear(startYear);
		int newMembers = SqlSelect.getNumberOfNewMembershipsForYear(startYear);
        //seriesActive.getData().add(new Data<String, Number>(startYear + "", activeMembers));
        seriesNonRenew.getData().add(new Data<String, Number>(startYear + "", nonRenewMembers));
        seriesNewMembers.getData().add(new Data<String, Number>(startYear + "", newMembers));
        startYear++;
		}
        lineChart.getData().addAll(Arrays.asList(seriesNonRenew,seriesNewMembers));
        return lineChart;
	}
	
	static public BarChart<String,Number> getBarChart() {
		int startYear = 2000;
		int numberOfYears = Integer.parseInt(Paths.getYear()) - startYear + 1;
		final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Number of Memberships by year");
        bc.setLegendVisible(false);
        //bc.setPrefHeight(200);
        xAxis.setLabel("Year");       
        //yAxis.setLabel("Number of Members");
        XYChart.Series<String,Number> series1 = new Series<String,Number>();
        //series1.setName("2003");
        for (int i = 0; i < numberOfYears; i++) {
        	int activeMembers = SqlSelect.getNumberOfActiveMembershipsForYear(startYear);
        series1.getData().add(new Data<String,Number>(startYear + "", activeMembers));
        startYear++;
        }
		bc.getData().addAll(Arrays.asList(series1));
		return bc;
	}
}
