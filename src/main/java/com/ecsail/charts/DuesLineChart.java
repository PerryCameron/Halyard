package com.ecsail.charts;

import com.ecsail.sql.SqlSelect;
import com.ecsail.structures.Object_DefinedFee;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

public class DuesLineChart extends LineChart<String, Number> {
	ObservableList<Object_DefinedFee> definedFees;

	public DuesLineChart() {
	super(new CategoryAxis(), new NumberAxis());
	this.definedFees =  SqlSelect.getDefinedFees();

		setTitle("Annual Dues by Year");
		populateChart();
	}
	
	public void refreshChart() {
		getData().clear();
		populateChart();
	}
	
	public void populateChart() {
		Series<String,Number> seriesDues = new Series<String, Number>();
//		Series<String,Number> seriesNewMembers = new Series<String, Number>();
//		Series<String,Number> seriesReturnMembers = new Series<String, Number>();
		seriesDues.setName("Dues");
//		seriesNewMembers.setName("New");
//		seriesReturnMembers.setName("Return");
		for (Object_DefinedFee d: definedFees) {
        seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getDues_regular()));
//        seriesNewMembers.getData().add(new Data<String, Number>(s.getFiscalYear() + "", s.getNewMemberships()));
//        seriesReturnMembers.getData().add(new Data<String, Number>(s.getFiscalYear() + "", s.getReturnMemberships()));
		}
        getData().addAll(Arrays.asList(seriesDues));
	}
}

