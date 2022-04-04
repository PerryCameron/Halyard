package com.ecsail.charts;

import com.ecsail.structures.StatsDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;


import java.util.ArrayList;
import java.util.Arrays;

public class MembershipStackedBarChart extends StackedBarChart<String,Number> {
	ArrayList<StatsDTO> stats;
	ObservableList<StackedBarChart.Data<String,Number>> familyData = FXCollections.observableArrayList();
	ObservableList<StackedBarChart.Data<String,Number>> regularData = FXCollections.observableArrayList();
	ObservableList<StackedBarChart.Data<String,Number>> socialData = FXCollections.observableArrayList();
	ObservableList<StackedBarChart.Data<String,Number>> lakeAssociateData = FXCollections.observableArrayList();
	ObservableList<StackedBarChart.Data<String,Number>> lifeMemberData = FXCollections.observableArrayList();

	StackedBarChart.Series<String,Number> seriesFamily = new StackedBarChart.Series<>();
	StackedBarChart.Series<String,Number> seriesRegular = new StackedBarChart.Series<>();
	StackedBarChart.Series<String,Number> seriesSocial = new StackedBarChart.Series<>();
	StackedBarChart.Series<String,Number> seriesLakeAssociate = new StackedBarChart.Series<>();
	StackedBarChart.Series<String,Number> seriesLifeMember = new StackedBarChart.Series<>();

	public MembershipStackedBarChart(ArrayList<StatsDTO> stats) {
		super(new CategoryAxis(), new NumberAxis());
		this.stats = stats;
	        setTitle("Active Memberships By Year");
			setNames();
			addData();
		getData().addAll(Arrays.asList(seriesFamily,seriesRegular,seriesSocial,seriesLakeAssociate,seriesLifeMember));
	}

	public void setNames() {
		seriesFamily.setName("Family");
		seriesRegular.setName("Regular");
		seriesSocial.setName("Social");
		seriesLakeAssociate.setName("Lake Associate");
		seriesLifeMember.setName("Life Member");
	}

	public void addData() {
		for (StatsDTO s: stats) {
			familyData.add(new StackedBarChart.Data<String,Number>(String.valueOf(s.getFiscalYear()),s.getFamily()));
			regularData.add(new StackedBarChart.Data<String,Number>(String.valueOf(s.getFiscalYear()),s.getRegular()));
			socialData.add(new StackedBarChart.Data<String,Number>(String.valueOf(s.getFiscalYear()),s.getSocial()));
			lakeAssociateData.add(new StackedBarChart.Data<String,Number>(String.valueOf(s.getFiscalYear()),s.getLakeAssociates()));
			lifeMemberData.add(new StackedBarChart.Data<String,Number>(String.valueOf(s.getFiscalYear()),s.getLifeMembers()));
		}
		setData();
	}

	private void setData() {
		seriesFamily.setData(familyData);
		seriesRegular.setData(regularData);
		seriesSocial.setData(socialData);
		seriesLakeAssociate.setData(lakeAssociateData);
		seriesLifeMember.setData(lifeMemberData);
	}

	public void clearData() {
			familyData.clear();
			regularData.clear();
			socialData.clear();
			lakeAssociateData.clear();
			lifeMemberData.clear();
	}

	public void refreshChart() {
		clearData();
		addData();
		setData(FXCollections.observableArrayList(seriesFamily,seriesRegular,seriesSocial,seriesLakeAssociate,seriesLifeMember));
	}
}
