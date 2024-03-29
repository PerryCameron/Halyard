package com.ecsail.gui.boxes;

import com.ecsail.charts.MembershipBarChart;
import com.ecsail.charts.MembershipStackedBarChart;
import com.ecsail.gui.dialogues.Dialogue_LoadNewStats;
import com.ecsail.main.HalyardPaths;
import com.ecsail.sql.select.SqlStats;
import com.ecsail.structures.StatsDTO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class VBoxCharts extends VBox {
    public static final int NONRENEW = 1;
    public static final int NEWMEMBER = 2;
    public static final int RETURNMEMBER = 3;
    public ArrayList<StatsDTO> stats;
    int currentYear;
    int defaultStartYear;
    int defaultNumbOfYears = 20;
    int totalNumbOfYears;
    BooleanProperty dataBaseStatisticsRefreshed = new SimpleBooleanProperty(false);

    public VBoxCharts() {
        this.currentYear = Integer.parseInt(HalyardPaths.getYear());
        this.defaultStartYear = currentYear - 20;
        // todo I think this needs to be changed to reload stats
        this.stats = SqlStats.getStatistics(defaultStartYear, defaultStartYear + defaultNumbOfYears);
        // problem is that the object hasn't been created yet
//        reloadStats();
        this.totalNumbOfYears = SqlStats.getNumberOfStatYears();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        MembershipStackedBarChart membershipsByYearChart = new MembershipStackedBarChart(stats, xAxis,yAxis);
        MembershipBarChart membershipBarChart = new MembershipBarChart(new CategoryAxis(),new NumberAxis(),stats,1);
        HBox hBoxControlBar = new HBox();
        VBox vBoxCharts = new VBox();
        Button refreshButton = new Button("Refresh Data");
        ComboBox<Integer> comboBoxStartYear = new ComboBox<>();
        ComboBox<Integer> comboBoxYears = new ComboBox<>();
        ComboBox<String> comboBoxBottomChartSelection = new ComboBox<>();

        HBox hBoxStart = new HBox();
        HBox hBoxStop = new HBox();
        HBox hBoxTop = new HBox();
        comboBoxBottomChartSelection.getItems().addAll("Non-Renew","New","Return");

        populateComboBoxWithYears(comboBoxStartYear);
        populateComboBoxWithNumberOfYears(comboBoxYears);
        this.setMinWidth(350);
        this.setMaxWidth(1400);
        this.setPrefWidth(Double.MAX_VALUE);
        this.setPrefHeight(1200);
        comboBoxStartYear.setValue(defaultStartYear);
        comboBoxYears.setValue(defaultNumbOfYears +1);
        comboBoxBottomChartSelection.setValue("Non-Renew");
        hBoxControlBar.setPadding(new Insets(5,0,5,5));
        comboBoxStartYear.setValue(defaultStartYear);
        hBoxStart.setSpacing(5);
        hBoxStop.setSpacing(5);
        hBoxStart.setAlignment(Pos.CENTER_LEFT);
        hBoxStop.setAlignment(Pos.CENTER_LEFT);
        hBoxControlBar.setSpacing(10);
        hBoxTop.setSpacing(5);
        hBoxTop.setAlignment(Pos.CENTER_LEFT);
        hBoxControlBar.setAlignment(Pos.CENTER);

        ///////// LISTENERS ////////////

        comboBoxBottomChartSelection.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            switch (newValue) {
                case "Non-Renew":
                    membershipBarChart.changeData(NONRENEW);
                    break;
                case "New":
                    membershipBarChart.changeData(NEWMEMBER);
                    break;
                case "Return":
                    membershipBarChart.changeData(RETURNMEMBER);
            }
        });

        comboBoxYears.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            defaultNumbOfYears = newValue;
            reloadStats();
            refreshCharts(membershipsByYearChart, membershipBarChart);
        });

        comboBoxStartYear.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            defaultStartYear = newValue;
            reloadStats();
            refreshCharts(membershipsByYearChart, membershipBarChart);
        });

        refreshButton.setOnAction((event)-> {
            new Dialogue_LoadNewStats(dataBaseStatisticsRefreshed);
        });

        // this waits for the database to update on another thread then refreshes the charts
        dataBaseStatisticsRefreshed.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                reloadStats();
                refreshCharts(membershipsByYearChart, membershipBarChart);
                setDataBaseStatisticsRefreshed(false);
            }
        });

        hBoxStart.getChildren().addAll(new Label("Start"),comboBoxStartYear);
        hBoxStop.getChildren().addAll(new Label("Year Span"),comboBoxYears);
        hBoxTop.getChildren().addAll(new Label("Bottom"),comboBoxBottomChartSelection);
        hBoxControlBar.getChildren().addAll(hBoxStart,hBoxStop,hBoxTop,refreshButton);
        vBoxCharts.getChildren().addAll(membershipsByYearChart,membershipBarChart);

        this.getChildren().addAll(vBoxCharts,hBoxControlBar);
    }

    private void refreshCharts(MembershipStackedBarChart membershipsByYearChart, MembershipBarChart membershipBarChart) {
        membershipsByYearChart.refreshChart();
        membershipBarChart.refreshChart();
    }

    private void reloadStats() {
        int endYear = defaultStartYear + defaultNumbOfYears;
        if(endYear > currentYear) endYear = currentYear;
        this.stats.clear();
        this.stats.addAll(SqlStats.getStatistics(defaultStartYear, endYear));
    }

    private void populateComboBoxWithYears(ComboBox<Integer> comboBox) {
        for(int i = currentYear -10; i > 1969; i--) {
            comboBox.getItems().add(i);
        }
    }

    private void populateComboBoxWithNumberOfYears(ComboBox<Integer> comboBox) {
        for(int i = 10; i < totalNumbOfYears; i++) {
            comboBox.getItems().add(i);
        }
    }

    public void setDataBaseStatisticsRefreshed(boolean dataBaseStatisticsRefreshed) {
        this.dataBaseStatisticsRefreshed.set(dataBaseStatisticsRefreshed);
    }
}
