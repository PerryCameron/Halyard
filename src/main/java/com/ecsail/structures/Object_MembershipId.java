package com.ecsail.structures;

import com.mysql.cj.conf.StringProperty;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Object_MembershipId {
	private IntegerProperty mid;
	private SimpleStringProperty fiscal_Year;
	private IntegerProperty ms_id;
	private SimpleStringProperty membership_id;
	
	public Object_MembershipId(Integer mid, String fiscal_Year, Integer ms_id, String membership_id) {
		this.mid = new SimpleIntegerProperty(mid);
		this.fiscal_Year = new SimpleStringProperty(fiscal_Year);
		this.ms_id = new SimpleIntegerProperty(ms_id);
		this.membership_id = new SimpleStringProperty(membership_id);
	}

	public final IntegerProperty midProperty() {
		return this.mid;
	}
	

	public final int getMid() {
		return this.midProperty().get();
	}
	

	public final void setMid(final int mid) {
		this.midProperty().set(mid);
	}
	

	public final SimpleStringProperty fiscal_YearProperty() {
		return this.fiscal_Year;
	}
	

	public final String getFiscal_Year() {
		return this.fiscal_YearProperty().get();
	}
	

	public final void setFiscal_Year(final String fiscal_Year) {
		this.fiscal_YearProperty().set(fiscal_Year);
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
	

	public final SimpleStringProperty membership_idProperty() {
		return this.membership_id;
	}
	

	public final String getMembership_id() {
		return this.membership_idProperty().get();
	}
	

	public final void setMembership_id(final String membership_id) {
		this.membership_idProperty().set(membership_id);
	}
	

	
}
