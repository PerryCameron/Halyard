package com.ecsail.gui.boxes;

import com.ecsail.charts.MembershipBarChart;
import com.ecsail.charts.MembershipLineChart;
import com.ecsail.charts.MembershipStackedBarChart;
import com.ecsail.gui.dialogues.Dialogue_LoadNewStats;
import com.ecsail.main.HalyardPaths;
import com.ecsail.sql.select.SqlStats;
import com.ecsail.structures.StatsDTO;
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
    int startYear = 2002;
    int numbOfYears = 22;

    public VBoxCharts() {
        this.currentYear = Integer.parseInt(HalyardPaths.getYear());
        this.startYear = currentYear - 20;
        this.stats = SqlStats.getStatistics(startYear, startYear + numbOfYears);
        MembershipStackedBarChart membershipsByYearChart = new MembershipStackedBarChart(stats);
        MembershipLineChart membershipStatisticsChart = new MembershipLineChart(stats);
        MembershipBarChart membershipBarChart = new MembershipBarChart(new CategoryAxis(),new NumberAxis(),stats,1);
        HBox hBoxControlBar = new HBox();
        VBox vBoxCharts = new VBox();
        Button refreshButton = new Button("Refresh Data");
        Button populateCharte = new Button("Populate");
        ComboBox<Integer> comboBoxStartYear = new ComboBox<>();
        ComboBox<Integer> comboBoxYears = new ComboBox<>();
        ComboBox<String> comboBoxBottomChartSelection = new ComboBox<>();

        HBox hBoxStart = new HBox();
        HBox hBoxStop = new HBox();
        HBox hBoxTop = new HBox();
        comboBoxBottomChartSelection.getItems().addAll("Non-Renew","New","Return");

        populateComboBoxWithYears(comboBoxStartYear);
        populateComboBoxWithNumberOfYears(comboBoxYears);
//        membershipsByYearChart.setMaxHeight(700);
        this.setMinWidth(350);
        this.setMaxWidth(1400);
        this.setPrefWidth(Double.MAX_VALUE);
        this.setPrefHeight(1200);
        comboBoxStartYear.setValue(startYear);
        comboBoxYears.setValue(numbOfYears);
        comboBoxBottomChartSelection.setValue("Non-Renew");
        hBoxControlBar.setPadding(new Insets(5,0,5,5));
        comboBoxStartYear.setValue(startYear);
        comboBoxYears.setValue(15);
        hBoxStart.setSpacing(5);
        hBoxStop.setSpacing(5);
        hBoxStart.setAlignment(Pos.CENTER_LEFT);
        hBoxStop.setAlignment(Pos.CENTER_LEFT);
        hBoxControlBar.setSpacing(10);
        hBoxTop.setSpacing(5);
        hBoxTop.setAlignment(Pos.CENTER_LEFT);
        hBoxControlBar.setAlignment(Pos.CENTER);

        ///////// LISTENERS ////////////
        refreshButton.setOnAction((event)-> {
            System.out.println("Start " + stats.size());
            new Dialogue_LoadNewStats();

            System.out.println("Stop " + stats.size());
        });

        populateCharte.setOnAction((event) -> {
            membershipsByYearChart.refreshChart();
            membershipStatisticsChart.refreshChart();
        });

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
            numbOfYears = newValue;
            reloadStats();
            membershipsByYearChart.refreshChart();
            membershipBarChart.refreshChart();
        });

        comboBoxStartYear.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            startYear = newValue;
            reloadStats();
            membershipsByYearChart.refreshChart();
            membershipBarChart.refreshChart();
        });



        hBoxStart.getChildren().addAll(new Label("Start"),comboBoxStartYear);
        hBoxStop.getChildren().addAll(new Label("Year Span"),comboBoxYears);
        hBoxTop.getChildren().addAll(new Label("Bottom"),comboBoxBottomChartSelection);
        hBoxControlBar.getChildren().addAll(hBoxStart,hBoxStop,hBoxTop,refreshButton);
//        vBoxCharts.getChildren().addAll(membershipsByYearChart,membershipStatisticsChart);
        vBoxCharts.getChildren().addAll(membershipsByYearChart,membershipBarChart);

        this.getChildren().addAll(vBoxCharts,hBoxControlBar);
    }

    private void reloadStats() {
        int endYear = startYear + numbOfYears;
        if(endYear > currentYear) endYear = currentYear;
        this.stats.clear();
        this.stats.addAll(SqlStats.getStatistics(startYear, endYear));
    }

    private void populateComboBoxWithYears(ComboBox<Integer> comboBox) {
        for(int i = Integer.parseInt(HalyardPaths.getYear()) + 1; i > 1969; i--) {
            comboBox.getItems().add(i);
        }
    }

    private void populateComboBoxWithNumberOfYears(ComboBox<Integer> comboBox) {
        for(int i = 1; i < 50; i++) {
            comboBox.getItems().add(i);
        }
    }


}
