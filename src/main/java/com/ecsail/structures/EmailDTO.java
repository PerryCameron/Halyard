package com.ecsail.structures;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;

public class EmailDTO {
	
	private IntegerProperty email_id;
	private IntegerProperty pid;
	private BooleanProperty isPrimaryUse;
	private StringProperty email;
	private BooleanProperty isListed;

	
	public EmailDTO(Integer email_id, Integer pid, Boolean primaryUse, String email, Boolean isListed) {
		this.email_id = new SimpleIntegerProperty(email_id);
		this.pid = new SimpleIntegerProperty(pid);
		this.isPrimaryUse = new SimpleBooleanProperty(primaryUse);
		this.email = new SimpleStringProperty(email);
		this.isListed = new SimpleBooleanProperty(isListed);
	}


	public final IntegerProperty email_idProperty() {
		return this.email_id;
	}
	


	public final int getEmail_id() {
		return this.email_idProperty().get();
	}
	


	public final void setEmail_id(final int email_id) {
		this.email_idProperty().set(email_id);
	}
	


	public final IntegerProperty pidProperty() {
		return this.pid;
	}
	


	public final int getPid() {
		return this.pidProperty().get();
	}
	


	public final void setPid(final int pid) {
		this.pidProperty().set(pid);
	}
	


	public final BooleanProperty isPrimaryUseProperty() {
		return this.isPrimaryUse;
	}
	


	public final boolean isIsPrimaryUse() {
		return this.isPrimaryUseProperty().get();
	}
	


	public final void setIsPrimaryUse(final boolean isPrimaryUse) {
		this.isPrimaryUseProperty().set(isPrimaryUse);
	}
	


	public final StringProperty emailProperty() {
		return this.email;
	}
	


	public final String getEmail() {
		return this.emailProperty().get();
	}
	


	public final void setEmail(final String email) {
		this.emailProperty().set(email);
	}
	


	public final BooleanProperty isListedProperty() {
		return this.isListed;
	}
	


	public final boolean isIsListed() {
		return this.isListedProperty().get();
	}
	


	public final void setIsListed(final boolean isListed) {
		this.isListedProperty().set(isListed);
	}	
	
}
