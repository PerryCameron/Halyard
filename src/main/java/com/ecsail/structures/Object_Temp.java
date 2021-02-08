package com.ecsail.structures;

public class Object_Temp {  // used in TabNewYearGenerator
	int membership_id;
	String fname;
	String lname;
	String wname;
	
	public Object_Temp(int membership_id, String fname, String lname, String wname) {
		this.membership_id = membership_id;
		this.fname = fname;
		this.lname = lname;
		this.wname = wname;
	}

	public Object_Temp() {
		// TODO Auto-generated constructor stub
	}

	public int getMembership_id() {
		return membership_id;
	}

	public void setMembership_id(int membership_id) {
		this.membership_id = membership_id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getWname() {
		return wname;
	}

	public void setWname(String wname) {
		this.wname = wname;
	}

	@Override
	public String toString() {
		return "Object_Temp [membership_id=" + membership_id + ", fname=" + fname + ", lname=" + lname + ", wname="
				+ wname + "]";
	}
	
	
	
}
