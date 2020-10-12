package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Object_Payment {
	private IntegerProperty pay_id;
	private IntegerProperty money_id;
	private IntegerProperty checkNumber;
	private IntegerProperty cash;
	private IntegerProperty other;
	private IntegerProperty amount;
	
	public Object_Payment(Integer pay_id, Integer money_id, Integer checkNumber, Integer cash, Integer other,
			Integer amount) {
		this.pay_id = new SimpleIntegerProperty(pay_id);
		this.money_id = new SimpleIntegerProperty(money_id);
		this.checkNumber = new SimpleIntegerProperty(checkNumber);
		this.cash = new SimpleIntegerProperty(cash);
		this.other = new SimpleIntegerProperty(other);
		this.amount = new SimpleIntegerProperty(amount);
	}

	public final IntegerProperty pay_idProperty() {
		return this.pay_id;
	}
	

	public final int getPay_id() {
		return this.pay_idProperty().get();
	}
	

	public final void setPay_id(final int pay_id) {
		this.pay_idProperty().set(pay_id);
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
	

	public final IntegerProperty checkNumberProperty() {
		return this.checkNumber;
	}
	

	public final int getCheckNumber() {
		return this.checkNumberProperty().get();
	}
	

	public final void setCheckNumber(final int checkNumber) {
		this.checkNumberProperty().set(checkNumber);
	}
	

	public final IntegerProperty cashProperty() {
		return this.cash;
	}
	

	public final int getCash() {
		return this.cashProperty().get();
	}
	

	public final void setCash(final int cash) {
		this.cashProperty().set(cash);
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
	

	public final IntegerProperty amountProperty() {
		return this.amount;
	}
	

	public final int getAmount() {
		return this.amountProperty().get();
	}
	

	public final void setAmount(final int amount) {
		this.amountProperty().set(amount);
	}

	@Override
	public String toString() {
		return "Object_Payment [pay_id=" + pay_id + ", money_id=" + money_id + ", checkNumber=" + checkNumber
				+ ", cash=" + cash + ", other=" + other + ", amount=" + amount + "]";
	}

}
