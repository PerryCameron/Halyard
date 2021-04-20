package com.ecsail.charts;

import java.util.ArrayList;
import java.util.Arrays;

import com.ecsail.structures.Object_Stats;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class MembershipLineChart extends LineChart<String, Number> {
	ArrayList<Object_Stats> stats;
	
	public MembershipLineChart(ArrayList<Object_Stats> stats) {
	super(new CategoryAxis(), new NumberAxis());
	this.stats = stats;	

		setTitle("Non-renewed, New, and Return Memberships");
		populateChart();
	}
	
	public void refreshChart() {
		getData().clear();
		populateChart();
	}
	
	public void populateChart() {
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
        getData().addAll(Arrays.asList(seriesNonRenew,seriesNewMembers,seriesReturnMembers));
	}
}

