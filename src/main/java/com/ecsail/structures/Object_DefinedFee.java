package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Object_DefinedFee {

	private IntegerProperty fiscal_year;
	private IntegerProperty dues_regular;
	private IntegerProperty dues_family;
	private IntegerProperty dues_lake_associate;
	private IntegerProperty dues_social;
	private IntegerProperty initiation;
	private IntegerProperty wet_slip;
	private IntegerProperty beach;
	private IntegerProperty winter_storage;
	private IntegerProperty main_gate_key;
	private IntegerProperty sail_loft;
	private IntegerProperty sail_loft_key;
	private IntegerProperty sail_school_laser_loft;
	private IntegerProperty sail_school_loft_key;
	private IntegerProperty kayak_rack;
	private IntegerProperty kayak_shed;
	private IntegerProperty kayak_shed_key;
	private IntegerProperty work_credit;
	
	public Object_DefinedFee(Integer fiscal_year, Integer dues_regular, Integer dues_family,
			Integer dues_lake_associate, Integer dues_social, Integer initiation,
			Integer wet_slip, Integer beach, Integer winter_storage,
			Integer main_gate_key, Integer sail_loft, Integer sail_loft_key,
			Integer sail_school_laser_loft, Integer sail_school_loft_key, Integer kayak_rack,
			Integer kayak_shed, Integer kayak_shed_key, Integer work_credit) {

		this.fiscal_year = new SimpleIntegerProperty(fiscal_year);
		this.dues_regular = new SimpleIntegerProperty(dues_regular);
		this.dues_family = new SimpleIntegerProperty(dues_family);
		this.dues_lake_associate = new SimpleIntegerProperty(dues_lake_associate);
		this.dues_social = new SimpleIntegerProperty(dues_social);
		this.initiation = new SimpleIntegerProperty(initiation);
		this.wet_slip = new SimpleIntegerProperty(wet_slip);
		this.beach = new SimpleIntegerProperty(beach);
		this.winter_storage = new SimpleIntegerProperty(winter_storage);
		this.main_gate_key = new SimpleIntegerProperty(main_gate_key);
		this.sail_loft = new SimpleIntegerProperty(sail_loft);
		this.sail_loft_key = new SimpleIntegerProperty(sail_loft_key);
		this.sail_school_laser_loft = new SimpleIntegerProperty(sail_school_laser_loft);
		this.sail_school_loft_key = new SimpleIntegerProperty(sail_school_loft_key);
		this.kayak_rack = new SimpleIntegerProperty(kayak_rack);
		this.kayak_shed = new SimpleIntegerProperty(kayak_shed);
		this.kayak_shed_key = new SimpleIntegerProperty(kayak_shed_key);
		this.work_credit = new SimpleIntegerProperty(work_credit);
	}
	
	public Object_DefinedFee() {
		// default constructor
	}

	public final IntegerProperty fiscal_yearProperty() {
		return this.fiscal_year;
	}
	

	public final int getFiscal_year() {
		return this.fiscal_yearProperty().get();
	}
	

	public final void setFiscal_year(final int fiscal_year) {
		this.fiscal_yearProperty().set(fiscal_year);
	}
	

	public final IntegerProperty dues_regularProperty() {
		return this.dues_regular;
	}
	

	public final int getDues_regular() {
		return this.dues_regularProperty().get();
	}
	

	public final void setDues_regular(final int dues_regular) {
		this.dues_regularProperty().set(dues_regular);
	}
	

	public final IntegerProperty dues_familyProperty() {
		return this.dues_family;
	}
	

	public final int getDues_family() {
		return this.dues_familyProperty().get();
	}
	

	public final void setDues_family(final int dues_family) {
		this.dues_familyProperty().set(dues_family);
	}
	

	public final IntegerProperty dues_lake_associateProperty() {
		return this.dues_lake_associate;
	}
	

	public final int getDues_lake_associate() {
		return this.dues_lake_associateProperty().get();
	}
	

	public final void setDues_lake_associate(final int dues_lake_associate) {
		this.dues_lake_associateProperty().set(dues_lake_associate);
	}
	

	public final IntegerProperty dues_socialProperty() {
		return this.dues_social;
	}
	

	public final int getDues_social() {
		return this.dues_socialProperty().get();
	}
	

	public final void setDues_social(final int dues_social) {
		this.dues_socialProperty().set(dues_social);
	}
	

	public final IntegerProperty initiationProperty() {
		return this.initiation;
	}
	

	public final int getInitiation() {
		return this.initiationProperty().get();
	}
	

	public final void setInitiation(final int initiation) {
		this.initiationProperty().set(initiation);
	}
	

	public final IntegerProperty wet_slipProperty() {
		return this.wet_slip;
	}
	

	public final int getWet_slip() {
		return this.wet_slipProperty().get();
	}
	

	public final void setWet_slip(final int wet_slip) {
		this.wet_slipProperty().set(wet_slip);
	}
	

	public final IntegerProperty beachProperty() {
		return this.beach;
	}
	

	public final int getBeach() {
		return this.beachProperty().get();
	}
	

	public final void setBeach(final int beach) {
		this.beachProperty().set(beach);
	}
	

	public final IntegerProperty winter_storageProperty() {
		return this.winter_storage;
	}
	

	public final int getWinter_storage() {
		return this.winter_storageProperty().get();
	}
	

	public final void setWinter_storage(final int winter_storage) {
		this.winter_storageProperty().set(winter_storage);
	}
	

	public final IntegerProperty main_gate_keyProperty() {
		return this.main_gate_key;
	}
	

	public final int getMain_gate_key() {
		return this.main_gate_keyProperty().get();
	}
	

	public final void setMain_gate_key(final int main_gate_key) {
		this.main_gate_keyProperty().set(main_gate_key);
	}
	

	public final IntegerProperty sail_loftProperty() {
		return this.sail_loft;
	}
	

	public final int getSail_loft() {
		return this.sail_loftProperty().get();
	}
	

	public final void setSail_loft(final int sail_loft) {
		this.sail_loftProperty().set(sail_loft);
	}
	

	public final IntegerProperty sail_loft_keyProperty() {
		return this.sail_loft_key;
	}
	

	public final int getSail_loft_key() {
		return this.sail_loft_keyProperty().get();
	}
	

	public final void setSail_loft_key(final int sail_loft_key) {
		this.sail_loft_keyProperty().set(sail_loft_key);
	}
	

	public final IntegerProperty sail_school_laser_loftProperty() {
		return this.sail_school_laser_loft;
	}
	

	public final int getSail_school_laser_loft() {
		return this.sail_school_laser_loftProperty().get();
	}
	

	public final void setSail_school_laser_loft(final int sail_school_laser_loft) {
		this.sail_school_laser_loftProperty().set(sail_school_laser_loft);
	}
	

	public final IntegerProperty sail_school_loft_keyProperty() {
		return this.sail_school_loft_key;
	}
	

	public final int getSail_school_loft_key() {
		return this.sail_school_loft_keyProperty().get();
	}
	

	public final void setSail_school_loft_key(final int sail_school_loft_key) {
		this.sail_school_loft_keyProperty().set(sail_school_loft_key);
	}
	

	public final IntegerProperty kayak_rackProperty() {
		return this.kayak_rack;
	}
	

	public final int getKayak_rack() {
		return this.kayak_rackProperty().get();
	}
	

	public final void setKayak_rack(final int kayak_rack) {
		this.kayak_rackProperty().set(kayak_rack);
	}
	

	public final IntegerProperty kayak_shedProperty() {
		return this.kayak_shed;
	}
	

	public final int getKayak_shed() {
		return this.kayak_shedProperty().get();
	}
	

	public final void setKayak_shed(final int kayak_shed) {
		this.kayak_shedProperty().set(kayak_shed);
	}
	

	public final IntegerProperty kayak_shed_keyProperty() {
		return this.kayak_shed_key;
	}
	

	public final int getKayak_shed_key() {
		return this.kayak_shed_keyProperty().get();
	}
	

	public final void setKayak_shed_key(final int kayak_shed_key) {
		this.kayak_shed_keyProperty().set(kayak_shed_key);
	}
	
	public final IntegerProperty work_creditProperty() {
		return this.work_credit;
	}
	

	public final int getWork_credit() {
		return this.work_creditProperty().get();
	}
	

	public final void setWork_credit(final int work_credit) {
		this.work_creditProperty().set(work_credit);
	}
	
	public final void set(int fiscal_year,int dues_regular,int dues_family,
			int dues_lake_associate,int dues_social,int initiation,int wet_slip,int beach,
			int winter_storage,int main_gate_key,int sail_loft,int sail_loft_key,int sail_school_laser_loft,
			int sail_school_loft_key,int kayak_rack,int kayak_shed,int kayak_shed_key, int work_credit) {
		
		setFiscal_year(fiscal_year);
		setDues_regular(dues_regular);
		setDues_family(dues_family);
		setDues_lake_associate(dues_lake_associate);
		setDues_social(dues_social);
		setInitiation(initiation);
		setWet_slip(wet_slip);
		setBeach(beach);
		setWinter_storage(winter_storage);
		setMain_gate_key(main_gate_key);
		setSail_loft(sail_loft);
		setSail_loft_key(sail_loft_key);
		setSail_school_laser_loft(sail_school_laser_loft);
		setSail_school_loft_key(sail_school_loft_key);
		setKayak_rack(kayak_rack);
		setKayak_shed(kayak_shed);
		setKayak_shed_key(kayak_shed_key);
		setWork_credit(work_credit);
	}
	
	public final void clear() {
		setFiscal_year(0);
		setDues_regular(0);
		setDues_family(0);
		setDues_lake_associate(0);
		setDues_social(0);
		setInitiation(0);
		setWet_slip(0);
		setBeach(0);
		setWinter_storage(0);
		setMain_gate_key(0);
		setSail_loft(0);
		setSail_loft_key(0);
		setSail_school_laser_loft(0);
		setSail_school_loft_key(0);
		setKayak_rack(0);
		setKayak_shed(0);
		setKayak_shed_key(0);
		setWork_credit(0);
	}

}
