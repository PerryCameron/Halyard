package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Object_MembershipId {
	private IntegerProperty mid;
	private IntegerProperty fiscal_Year;
	private IntegerProperty ms_id;
	private IntegerProperty membership_id;
	
	public Object_MembershipId(Integer mid, Integer fiscal_Year, Integer ms_id, Integer membership_id) {
		this.mid = new SimpleIntegerProperty(mid);
		this.fiscal_Year = new SimpleIntegerProperty(fiscal_Year);
		this.ms_id = new SimpleIntegerProperty(ms_id);
		this.membership_id = new SimpleIntegerProperty(membership_id);
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
	

	public final IntegerProperty fiscal_YearProperty() {
		return this.fiscal_Year;
	}
	

	public final int getFiscal_Year() {
		return this.fiscal_YearProperty().get();
	}
	

	public final void setFiscal_Year(final int fiscal_Year) {
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
	

	public final IntegerProperty membership_idProperty() {
		return this.membership_id;
	}
	

	public final int getMembership_id() {
		return this.membership_idProperty().get();
	}
	
	public final void setMembership_id(final int membership_id) {
		this.membership_idProperty().set(membership_id);
	}
}
