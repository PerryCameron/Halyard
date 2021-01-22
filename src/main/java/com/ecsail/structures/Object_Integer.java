package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Object_Integer {
	
	private IntegerProperty integer;

	public Object_Integer(Integer integer) {
		this.integer = new SimpleIntegerProperty(integer);
	}

	public final IntegerProperty integerProperty() {
		return this.integer;
	}
	

	public final int getInteger() {
		return this.integerProperty().get();
	}
	

	public final void setInteger(final int integer) {
		this.integerProperty().set(integer);
	}
}
