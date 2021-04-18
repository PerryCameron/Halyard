package com.ecsail.gui.boxes;

import java.util.ArrayList;
import java.util.Arrays;

import com.ecsail.main.Paths;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlSelect;
import com.ecsail.structures.Object_Stats;

import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class Charts {
	public static ArrayList<Object_Stats> stats;
	
	public static void populateCharts() {
		stats = SqlSelect.getStatistics();
	}
	
	public static void reload() {
		stats.clear();
		stats = SqlSelect.getStatistics();
	}

	static public void updateStats() {
			int statId = 0;
			int selectedYear = 2000;
			Object_Stats stats;
			SqlDelete.deleteStatistics();
			int numberOfYears = Integer.parseInt(Paths.getYear()) - selectedYear + 1;
			for (int i = 0; i < numberOfYears; i++) {
				stats = new Object_Stats(selectedYear);
				stats.setStatId(statId);
				stats.refreshStatsForYear();  // built in function for the object to update itself.
				SqlInsert.addStatRecord(stats);
				System.out.println("Adding " + selectedYear);
				selectedYear++;
				statId++;
			}
	}
	
	static public LineChart<String, Number> getLineChart() {
        final Axis<String> xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
		final LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);
		lineChart.setTitle("Non-renewed, New, and Return Memberships");
		XYChart.Series<String,Number> seriesNonRenew = new Series<String, Number>();
		XYChart.Series<String,Number> seriesNewMembers = new Series<String, Number>();
		XYChart.Series<String,Number> seriesReturnMembers = new Series<String, Number>();
		seriesNonRenew.setName("Non-Renew");
		seriesNewMembers.setName("New");
		seriesReturnMembers.setName("Return");
		for (Object_Stats s: stats) {
        seriesNonRenew.getData().add(new Data<String, Number>(s.getFiscalYear() + "", s.getNonRenewMemberships()));
        seriesNewMembers.getData().add(new Data<String, Number>(s.getFiscalYear() + "", s.getNewMemberships()));
        seriesReturnMembers.getData().add(new Data<String, Number>(s.getFiscalYear() + "", s.getReturnMemberships()));
		}
        lineChart.getData().addAll(Arrays.asList(seriesNonRenew,seriesNewMembers,seriesReturnMembers));
        return lineChart;
	}
	
	static public BarChart<String,Number> getBarChart() {
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
        for (Object_Stats s: stats) {
        series1.getData().add(new Data<String,Number>(s.getFiscalYear() + "", s.getActiveMemberships()));
        }
		bc.getData().addAll(Arrays.asList(series1));
		return bc;
	}
}
