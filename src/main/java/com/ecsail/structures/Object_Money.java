package com.ecsail.structures;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Object_Money {
	
	private IntegerProperty money_id;
	private IntegerProperty ms_id;
	private IntegerProperty fiscal_year;
	private IntegerProperty batch;
	private IntegerProperty officer_credit;
	private IntegerProperty extra_key;
	private IntegerProperty kayac_shed_key;
	private IntegerProperty sail_loft_key;
	private IntegerProperty sail_school_loft_key;
	private IntegerProperty beach;
	private IntegerProperty wet_slip;
	private IntegerProperty kayac_rack;
	private IntegerProperty kayac_shed;
	private IntegerProperty sail_loft;
	private IntegerProperty sail_school_laser_loft;
	private IntegerProperty winter_storage;
	private IntegerProperty ysc_donation;
	private IntegerProperty paid;
	private IntegerProperty total;
	private IntegerProperty credit;
	private IntegerProperty balance;
	private IntegerProperty dues;
	private BooleanProperty committed;
	private BooleanProperty closed;
	private IntegerProperty other;
	private IntegerProperty initiation;
	
	public Object_Money(Integer money_id, Integer ms_id, Integer fiscal_year, Integer batch,
			Integer officer_credit, Integer extra_key, Integer kayac_shed_key,
			Integer sail_loft_key, Integer sail_school_loft_key, Integer beach,
			Integer wet_slip, Integer kayac_rack, Integer kayac_shed, Integer sail_loft,
			Integer sail_school_laser_loft, Integer winter_storage, Integer ysc_donation,
			Integer paid, Integer total, Integer credit, Integer balance, Integer dues, 
			Boolean committed, Boolean closed, Integer other, Integer initiation) {

		this.money_id = new SimpleIntegerProperty(money_id);
		this.ms_id = new SimpleIntegerProperty(ms_id);
		this.fiscal_year = new SimpleIntegerProperty(fiscal_year);
		this.batch = new SimpleIntegerProperty(batch);
		this.officer_credit = new SimpleIntegerProperty(officer_credit);
		this.extra_key = new SimpleIntegerProperty(extra_key);
		this.kayac_shed_key = new SimpleIntegerProperty(kayac_shed_key);
		this.sail_loft_key = new SimpleIntegerProperty(sail_loft_key);
		this.sail_school_loft_key = new SimpleIntegerProperty(sail_school_loft_key);
		this.beach = new SimpleIntegerProperty(beach);
		this.wet_slip = new SimpleIntegerProperty(wet_slip);
		this.kayac_rack = new SimpleIntegerProperty(kayac_rack);
		this.kayac_shed = new SimpleIntegerProperty(kayac_shed);
		this.sail_loft = new SimpleIntegerProperty(sail_loft);
		this.sail_school_laser_loft = new SimpleIntegerProperty(sail_school_laser_loft);
		this.winter_storage = new SimpleIntegerProperty(winter_storage);
		this.ysc_donation = new SimpleIntegerProperty(ysc_donation);
		this.paid = new SimpleIntegerProperty(paid);
		this.total = new SimpleIntegerProperty(total);
		this.credit = new SimpleIntegerProperty(credit);
		this.balance = new SimpleIntegerProperty(balance);
		this.dues = new SimpleIntegerProperty(dues);
		this.committed = new SimpleBooleanProperty(committed);
		this.closed = new SimpleBooleanProperty(closed);
		this.other = new SimpleIntegerProperty(other);
		this.initiation = new SimpleIntegerProperty(initiation);
	}

	public Object_Money() {
		this.money_id = new SimpleIntegerProperty(0);
		this.ms_id = new SimpleIntegerProperty(0);
		this.fiscal_year = new SimpleIntegerProperty(0);
		this.batch = new SimpleIntegerProperty(0);
		this.officer_credit = new SimpleIntegerProperty(0);
		this.extra_key = new SimpleIntegerProperty(0);
		this.kayac_shed_key = new SimpleIntegerProperty(0);
		this.sail_loft_key = new SimpleIntegerProperty(0);
		this.sail_school_loft_key = new SimpleIntegerProperty(0);
		this.beach = new SimpleIntegerProperty(0);
		this.wet_slip = new SimpleIntegerProperty(0);
		this.kayac_rack = new SimpleIntegerProperty(0);
		this.kayac_shed = new SimpleIntegerProperty(0);
		this.sail_loft = new SimpleIntegerProperty(0);
		this.sail_school_laser_loft = new SimpleIntegerProperty(0);
		this.winter_storage = new SimpleIntegerProperty(0);
		this.ysc_donation = new SimpleIntegerProperty(0);
		this.paid = new SimpleIntegerProperty(0);
		this.total = new SimpleIntegerProperty(0);
		this.credit = new SimpleIntegerProperty(0);
		this.balance = new SimpleIntegerProperty(0);
		this.dues = new SimpleIntegerProperty(0);
		this.committed = new SimpleBooleanProperty(false);
		this.closed = new SimpleBooleanProperty(false);
		this.other = new SimpleIntegerProperty(0);
		this.initiation = new SimpleIntegerProperty(0);
	}

	public final IntegerProperty money_idProperty() {
		return this.money_id;
	}
	

	public final int getMoney_id() {
		return this.money_idProperty().get();
	}
	

	public final void setMoney_id(final int money_id) {
		this.money_idProperty().set(money_id);
	}
	

	public final IntegerProperty ms_idProperty() {
		return this.ms_id;
	}
	

	public final int getMs_id() {
		return this.ms_idProperty().get();
	}
	

	public final void setMs_id(final int ms_id) {
		this.ms_idProperty().set(ms_id);
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
	

	public final IntegerProperty extra_keyProperty() {
		return this.extra_key;
	}
	

	public final int getExtra_key() {
		return this.extra_keyProperty().get();
	}
	

	public final void setExtra_key(final int extra_key) {
		this.extra_keyProperty().set(extra_key);
	}
	

	public final IntegerProperty kayac_shed_keyProperty() {
		return this.kayac_shed_key;
	}
	

	public final int getKayac_shed_key() {
		return this.kayac_shed_keyProperty().get();
	}
	

	public final void setKayac_shed_key(final int kayac_shed_key) {
		this.kayac_shed_keyProperty().set(kayac_shed_key);
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
	

	public final IntegerProperty sail_school_loft_keyProperty() {
		return this.sail_school_loft_key;
	}
	

	public final int getSail_school_loft_key() {
		return this.sail_school_loft_keyProperty().get();
	}
	

	public final void setSail_school_loft_key(final int sail_school_loft_key) {
		this.sail_school_loft_keyProperty().set(sail_school_loft_key);
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
	

	public final IntegerProperty wet_slipProperty() {
		return this.wet_slip;
	}
	

	public final int getWet_slip() {
		return this.wet_slipProperty().get();
	}
	

	public final void setWet_slip(final int wet_slip) {
		this.wet_slipProperty().set(wet_slip);
	}
	

	public final IntegerProperty kayac_rackProperty() {
		return this.kayac_rack;
	}
	

	public final int getKayac_rack() {
		return this.kayac_rackProperty().get();
	}
	

	public final void setKayac_rack(final int kayac_rack) {
		this.kayac_rackProperty().set(kayac_rack);
	}
	

	public final IntegerProperty kayac_shedProperty() {
		return this.kayac_shed;
	}
	

	public final int getKayac_shed() {
		return this.kayac_shedProperty().get();
	}
	

	public final void setKayac_shed(final int kayac_shed) {
		this.kayac_shedProperty().set(kayac_shed);
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
	

	public final IntegerProperty sail_school_laser_loftProperty() {
		return this.sail_school_laser_loft;
	}
	

	public final int getSail_school_laser_loft() {
		return this.sail_school_laser_loftProperty().get();
	}
	

	public final void setSail_school_laser_loft(final int sail_school_laser_loft) {
		this.sail_school_laser_loftProperty().set(sail_school_laser_loft);
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
	

	public final IntegerProperty ysc_donationProperty() {
		return this.ysc_donation;
	}
	

	public final int getYsc_donation() {
		return this.ysc_donationProperty().get();
	}
	

	public final void setYsc_donation(final int ysc_donation) {
		this.ysc_donationProperty().set(ysc_donation);
	}
	

	public final IntegerProperty paidProperty() {
		return this.paid;
	}
	

	public final int getPaid() {
		return this.paidProperty().get();
	}
	

	public final void setPaid(final int paid) {
		this.paidProperty().set(paid);
	}
	

	public final IntegerProperty totalProperty() {
		return this.total;
	}
	

	public final int getTotal() {
		return this.totalProperty().get();
	}
	

	public final void setTotal(final int total) {
		this.totalProperty().set(total);
	}
	

	public final IntegerProperty creditProperty() {
		return this.credit;
	}
	

	public final int getCredit() {
		return this.creditProperty().get();
	}
	

	public final void setCredit(final int credit) {
		this.creditProperty().set(credit);
	}
	

	public final IntegerProperty balanceProperty() {
		return this.balance;
	}
	

	public final int getBalance() {
		return this.balanceProperty().get();
	}
	

	public final void setBalance(final int balance) {
		this.balanceProperty().set(balance);
	}
	

	public final IntegerProperty duesProperty() {
		return this.dues;
	}
	

	public final int getDues() {
		return this.duesProperty().get();
	}
	

	public final void setDues(final int dues) {
		this.duesProperty().set(dues);
	}

	public final BooleanProperty committedProperty() {
		return this.committed;
	}
	

	public final boolean isCommitted() {
		return this.committedProperty().get();
	}
	

	public final void setCommitted(final boolean committed) {
		this.committedProperty().set(committed);
	}
	

	public final BooleanProperty isClosedProperty() {
		return this.closed;
	}
	

	public final boolean isClosed() {
		return this.isClosedProperty().get();
	}
	

	public final void setClosed(final boolean closed) {
		this.isClosedProperty().set(closed);
	}

	public final IntegerProperty officer_creditProperty() {
		return this.officer_credit;
	}
	

	public final int getOfficer_credit() {
		return this.officer_creditProperty().get();
	}
	

	public final void setOfficer_credit(final int officer_credit) {
		this.officer_creditProperty().set(officer_credit);
	}
	

	public final IntegerProperty batchProperty() {
		return this.batch;
	}

	public final int getBatch() {
		return this.batchProperty().get();
	}

	public final void setBatch(final int batch) {
		this.batchProperty().set(batch);
	}
	
	public final IntegerProperty otherProperty() {
		return this.other;
	}
	

	public final int getOther() {
		return this.otherProperty().get();
	}
	

	public final void setOther(final int other) {
		this.otherProperty().set(other);
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

	@Override
	public String toString() {
		return "Object_Money [money_id=" + money_id + ", ms_id=" + ms_id + ", fiscal_year=" + fiscal_year + ", batch="
				+ batch + ", officer_credit=" + officer_credit + ", extra_key=" + extra_key + ", kayac_shed_key="
				+ kayac_shed_key + ", sail_loft_key=" + sail_loft_key + ", sail_school_loft_key=" + sail_school_loft_key
				+ ", beach=" + beach + ", wet_slip=" + wet_slip + ", kayac_rack=" + kayac_rack + ", kayac_shed="
				+ kayac_shed + ", sail_loft=" + sail_loft + ", sail_school_laser_loft=" + sail_school_laser_loft
				+ ", winter_storage=" + winter_storage + ", ysc_donation=" + ysc_donation + ", paid=" + paid
				+ ", total=" + total + ", credit=" + credit + ", balance=" + balance + ", dues=" + dues + ", committed="
				+ committed + ", closed=" + closed + ", other=" + other + ", initiation=" + initiation + "]";
	}
	
}
