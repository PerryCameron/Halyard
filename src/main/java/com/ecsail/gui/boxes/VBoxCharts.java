package com.ecsail.gui.boxes;

import com.ecsail.charts.MembershipLineChart;
import com.ecsail.charts.MembershipStackedBarChart;
import com.ecsail.gui.dialogues.Dialogue_LoadNewStats;
import com.ecsail.sql.select.SqlStats;
import com.ecsail.structures.StatsDTO;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class VBoxCharts extends VBox {
    public ArrayList<StatsDTO> stats;
    public VBoxCharts() {
        this.stats = SqlStats.getStatistics();
        MembershipStackedBarChart membershipsByYearChart = new MembershipStackedBarChart(stats);
        MembershipLineChart membershipStatisticsChart = new MembershipLineChart(stats);
        HBox hBoxControlBar = new HBox();
        VBox vBoxCharts = new VBox();
        Button refreshButton = new Button("Refresh");
        Button populateCharte = new Button("Populate");

//        membershipsByYearChart.setMaxHeight(700);
        this.setMinWidth(350);
        this.setMaxWidth(1400);
        this.setPrefWidth(Double.MAX_VALUE);
        this.setPrefHeight(1200);

        refreshButton.setOnAction((event)-> {
            System.out.println("Start " + stats.size());
            new Dialogue_LoadNewStats();

            System.out.println("Stop " + stats.size());
        });

        populateCharte.setOnAction((event) -> {
            membershipsByYearChart.refreshChart();
            membershipStatisticsChart.refreshChart();
        });


        hBoxControlBar.getChildren().addAll(refreshButton,populateCharte);
        vBoxCharts.getChildren().addAll(membershipsByYearChart,membershipStatisticsChart);
        this.getChildren().addAll(vBoxCharts,hBoxControlBar);
    }

}
