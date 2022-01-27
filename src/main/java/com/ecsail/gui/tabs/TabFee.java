package com.ecsail.gui.tabs;

import com.ecsail.charts.FeesLineChart;
import com.ecsail.gui.boxes.HBoxInvoice;
import com.ecsail.main.HalyardPaths;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.sql.select.SqlDefinedFee;
import com.ecsail.sql.select.SqlFee;
import com.ecsail.structures.DefinedFeeDTO;
import com.ecsail.structures.FeeDTO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class TabFee extends Tab {
	int fieldWidth = 60;
	String selectedYear;
	ArrayList<FeeDTO> feeDTOS;
	int selectedIndex;
	FeesLineChart duesLineChart;



	public TabFee(String text) {
		super(text);
		this.selectedYear = HalyardPaths.getYear();
		this.feeDTOS = SqlFee.getFeesFromYear(selectedYear);
//		this.definedFees.get(selectedIndex)s = SqlSelect.selectDefinedFees(Integer.parseInt(selectedYear));
//		this.duesLineChart = new FeesLineChart(definedFees);
//		this.selectedIndex = getSelectedIndex(selectedYear);
//		copyObjectToFields();
//
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		HBox hboxGrey = new HBox();
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table

		hboxGrey.getChildren().addAll(vboxGrey);

		vboxPink.getChildren().add(hboxGrey);
		vboxBlue.getChildren().add(vboxPink);
		setContent(vboxBlue);

	}
	
}
