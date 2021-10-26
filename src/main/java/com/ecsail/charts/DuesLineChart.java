package com.ecsail.charts;

import com.ecsail.structures.Object_DefinedFee;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.util.Arrays;

public class DuesLineChart extends LineChart<String, Number> {
	ObservableList<Object_DefinedFee> definedFees;


	public DuesLineChart(ObservableList<Object_DefinedFee> definedFees) {
	super(new CategoryAxis(), new NumberAxis());
	Series<String,Number> seriesDues = new Series<String, Number>();
	this.definedFees = definedFees;
		populateChart("Initiation Fee", seriesDues);

	}
	
	public void refreshChart(String type) {
//		seriesDues.getData().clear();
		this.getData().clear();
//		System.out.println("cleared size=" + seriesDues.getData().size());
		Series<String,Number> series = new Series<String, Number>();
		populateChart(type, series);

	}
	
	public void populateChart(String type, Series<String, Number> seriesDues) {
//		Series<String,Number> seriesDues = new Series<String, Number>();
//		Series<String,Number> seriesNewMembers = new Series<String, Number>();
//		Series<String,Number> seriesReturnMembers = new Series<String, Number>();

//		seriesNewMembers.setName("New");
//		seriesReturnMembers.setName("Return");
		for (Object_DefinedFee d: definedFees) {
			if(type.equals("Regular Dues"))
        	seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getDues_regular()));
			seriesDues.set
			if(type.equals("Family Dues"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getDues_family()));
			if(type.equals("Lake Associate Dues"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getDues_lake_associate()));
			if(type.equals("Social Membership Dues"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getDues_social()));
			if(type.equals("Initiation Fee"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getInitiation()));
			System.out.println(d.getFiscal_year() + "," + d.getInitiation());
			if(type.equals("Wetslip"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getWet_slip()));
			if(type.equals("Beach Parking"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getBeach()));
			if(type.equals("Winter Storage"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getWinter_storage()));
			if(type.equals("Extra Gate Key Fee"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getMain_gate_key()));
			if(type.equals("Sail Loft Access"))
				seriesDues.getData().add(new Data<String, Number>(d.getFiscal_year() + "", d.getSail_loft()));

		}
		seriesDues.setName(type);
		setTitle(type + " by Year");
		getData().addAll(Arrays.asList(seriesDues));
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

