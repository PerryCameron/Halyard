package com.ecsail.structures;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class BooleanDTO {
	private BooleanProperty xBoolean;

	public BooleanDTO(Boolean xBoolean) {
		this.xBoolean = new SimpleBooleanProperty(xBoolean);
	}

	public final BooleanProperty xBooleanProperty() {
		return this.xBoolean;
	}
	

	public final boolean isXBoolean() {
		return this.xBooleanProperty().get();
	}
	

	public final void setXBoolean(final boolean xBoolean) {
		this.xBooleanProperty().set(xBoolean);
	}
	
}
