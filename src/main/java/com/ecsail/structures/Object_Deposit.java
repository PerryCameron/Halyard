package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Object_Deposit {
	private IntegerProperty deposit_id;
	private StringProperty depositDate;
	private StringProperty fiscalYear;
	private IntegerProperty batch;
	
	public Object_Deposit(Integer deposit_id, String depositDate, String fiscalYear,
			Integer batch) {
		this.deposit_id = new SimpleIntegerProperty(deposit_id);
		this.depositDate = new SimpleStringProperty(depositDate);
		this.fiscalYear = new SimpleStringProperty(fiscalYear);
		this.batch = new SimpleIntegerProperty(batch);
	}

	public final IntegerProperty deposit_idProperty() {
		return this.deposit_id;
	}
	

	public final int getDeposit_id() {
		return this.deposit_idProperty().get();
	}
	

	public final void setDeposit_id(final int deposit_id) {
		this.deposit_idProperty().set(deposit_id);
	}
	

	public final StringProperty depositDateProperty() {
		return this.depositDate;
	}
	

	public final String getDepositDate() {
		return this.depositDateProperty().get();
	}
	

	public final void setDepositDate(final String depositDate) {
		this.depositDateProperty().set(depositDate);
	}
	

	public final StringProperty fiscalYearProperty() {
		return this.fiscalYear;
	}
	

	public final String getFiscalYear() {
		return this.fiscalYearProperty().get();
	}
	

	public final void setFiscalYear(final String fiscalYear) {
		this.fiscalYearProperty().set(fiscalYear);
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
	
	public final void clear() {
		deposit_id = null;
		depositDate = null;
		fiscalYear = null;
		batch = null;
	}

	@Override
	public String toString() {
		return "Object_Deposit [deposit_id=" + deposit_id + ", depositDate=" + depositDate + ", fiscalYear="
				+ fiscalYear + ", batch=" + batch + "]";
	}
	
	
	
}
