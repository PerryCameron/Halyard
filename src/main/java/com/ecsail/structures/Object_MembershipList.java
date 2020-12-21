package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Object_MembershipList extends Object_Membership {

	private StringProperty lname;
	private StringProperty fname;
	private StringProperty slip;
	private IntegerProperty subleaser;
	
	public Object_MembershipList(Integer msid, Integer pid, Integer membershipId, String joinDate, Boolean activeMembership, String memType,
			String slip, String lname, String fname, Integer subleaser, String address, String city, String state, String zip) {
		super(msid, pid, membershipId, joinDate, activeMembership, memType, address, city, state, zip);
		this.lname = new SimpleStringProperty(lname);
		this.fname = new SimpleStringProperty(fname);
		this.slip = new SimpleStringProperty(slip);
		this.subleaser = new SimpleIntegerProperty(subleaser);
	}

	

	public Object_MembershipList() {
		// TODO Auto-generated constructor stub
	}



	public void setLname(StringProperty lname) {
		this.lname = lname;
	}



	public void setFname(StringProperty fname) {
		this.fname = fname;
	}



	public void setSlip(StringProperty slip) {
		this.slip = slip;
	}



	public void setSubleaser(IntegerProperty subleaser) {
		this.subleaser = subleaser;
	}



	public final StringProperty lnameProperty() {
		return this.lname;
	}
	


	public final String getLname() {
		return this.lnameProperty().get();
	}
	


	public final void setLname(final String lname) {
		this.lnameProperty().set(lname);
	}
	


	public final StringProperty fnameProperty() {
		return this.fname;
	}
	


	public final String getFname() {
		return this.fnameProperty().get();
	}
	


	public final void setFname(final String fname) {
		this.fnameProperty().set(fname);
	}


	public final StringProperty slipProperty() {
		return this.slip;
	}
	



	public final String getSlip() {
		return this.slipProperty().get();
	}
	



	public final void setSlip(final String slip) {
		this.slipProperty().set(slip);
	}



	public final IntegerProperty subleaserProperty() {
		return this.subleaser;
	}
	



	public final int getSubleaser() {
		return this.subleaserProperty().get();
	}
	



	public final void setSubleaser(final int subleaser) {
		this.subleaserProperty().set(subleaser);
	}



	@Override
	public String toString() {
		return "Object_MembershipList [lname=" + lname + ", fname=" + fname + ", slip=" + slip + ", subleaser="
				+ subleaser + ", msidProperty()=" + msidProperty() + ", getMsid()=" + getMsid() + ", pidProperty()="
				+ pidProperty() + ", getPid()=" + getPid() + ", membershipIdProperty()=" + membershipIdProperty()
				+ ", getMembershipId()=" + getMembershipId() + ", joinDateProperty()=" + joinDateProperty()
				+ ", getJoinDate()=" + getJoinDate() + ", memTypeProperty()=" + memTypeProperty() + ", getMemType()="
				+ getMemType() + ", activeMembershipProperty()=" + activeMembershipProperty()
				+ ", isActiveMembership()=" + isActiveMembership() + ", addressProperty()=" + addressProperty()
				+ ", getAddress()=" + getAddress() + ", cityProperty()=" + cityProperty() + ", getCity()=" + getCity()
				+ ", stateProperty()=" + stateProperty() + ", getState()=" + getState() + ", zipProperty()="
				+ zipProperty() + ", getZip()=" + getZip() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	
	
}
