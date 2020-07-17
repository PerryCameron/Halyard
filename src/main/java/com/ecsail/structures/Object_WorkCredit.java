package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Object_WorkCredit {

	private IntegerProperty memo_id;
	private IntegerProperty msid;
	private IntegerProperty racing;
	private IntegerProperty harbor;
	private IntegerProperty social;
	private IntegerProperty other;
	
	public Object_WorkCredit(Integer memo_id, Integer msid, Integer racing,
			Integer harbor, Integer social, Integer other) {

		this.memo_id = new SimpleIntegerProperty(memo_id);
		this.msid = new SimpleIntegerProperty(msid);
		this.racing = new SimpleIntegerProperty(racing);
		this.harbor = new SimpleIntegerProperty(harbor);
		this.social = new SimpleIntegerProperty(social);
		this.other = new SimpleIntegerProperty(other);
	}

	public final IntegerProperty memo_idProperty() {
		return this.memo_id;
	}
	

	public final int getMemo_id() {
		return this.memo_idProperty().get();
	}
	

	public final void setMemo_id(final int memo_id) {
		this.memo_idProperty().set(memo_id);
	}
	

	public final IntegerProperty msidProperty() {
		return this.msid;
	}
	

	public final int getMsid() {
		return this.msidProperty().get();
	}
	

	public final void setMsid(final int msid) {
		this.msidProperty().set(msid);
	}
	

	public final IntegerProperty racingProperty() {
		return this.racing;
	}
	

	public final int getRacing() {
		return this.racingProperty().get();
	}
	

	public final void setRacing(final int racing) {
		this.racingProperty().set(racing);
	}
	

	public final IntegerProperty harborProperty() {
		return this.harbor;
	}
	

	public final int getHarbor() {
		return this.harborProperty().get();
	}
	

	public final void setHarbor(final int harbor) {
		this.harborProperty().set(harbor);
	}
	

	public final IntegerProperty socialProperty() {
		return this.social;
	}
	

	public final int getSocial() {
		return this.socialProperty().get();
	}
	

	public final void setSocial(final int social) {
		this.socialProperty().set(social);
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

	@Override
	public String toString() {
		return "Object_WorkCredit [memo_id=" + memo_id + ", msid=" + msid + ", racing=" + racing + ", harbor=" + harbor
				+ ", social=" + social + ", other=" + other + "]";
	}	
}
