package com.ecsail.charts;

import com.ecsail.structures.Object_DefinedFee;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.util.Arrays;

public class DuesLineChart extends LineChart<String, Number> {
	ObservableList<Object_DefinedFee> definedFees;
	Series<String,Number> seriesDues = new Series<String, Number>();

	public DuesLineChart(ObservableList<Object_DefinedFee> definedFees) {
	super(new CategoryAxis(), new NumberAxis());
	this.definedFees = definedFees;
		setTitle("Annual Dues by Year");
		populateChart("regular");
		getData().addAll(Arrays.asList(seriesDues));
	}
	
	public void refreshChart(String type) {
		seriesDues.getData().clear();
		populateChart(type);
	}
	
	public void populateChart(String type) {
//		Series<String,Number> seriesDues = new Series<String, Number>();
//		Series<String,Number> seriesNewMembers = new Series<String, Number>();
//		Series<String,Number> seriesReturnMembers = new Series<String, Number>();
		seriesDues.setName("Dues");
//		seriesNewMembers.setName("New");
//		seriesReturnMembers.setName("Return");
		for (Object_DefinedFee d: definedFees) {
			if(type.equals("regular"))
        	seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getDues_regular()));
			if(type.equals("family"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getDues_family()));
			if(type.equals("lakeassociate"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getDues_lake_associate()));
			if(type.equals("social"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getDues_social()));
			if(type.equals("initiation"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getInitiation()));
			if(type.equals("wetslip"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getWet_slip()));
			if(type.equals("beachspot"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getBeach()));
			if(type.equals("winter"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getWinter_storage()));
			if(type.equals("gatekey"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getMain_gate_key()));
			if(type.equals("sailloftaccess"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getSail_loft()));

		}

	}

//		update = "wetslip";
//	} else if (initiationRadioButton.isSelected()) {
//		update = "beachspot";
//	} else if (initiationRadioButton.isSelected()) {
//		update = "winter";
//	} else if (initiationRadioButton.isSelected()) {
//		update = "gatekey";
//	} else if (initiationRadioButton.isSelected()) {
//		update = "sailloftaccess";
//	}

}

