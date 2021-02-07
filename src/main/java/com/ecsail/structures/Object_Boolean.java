package com.ecsail.structures;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Object_Boolean {
	private BooleanProperty xBoolean;

	public Object_Boolean(Boolean xBoolean) {
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
