package com.ecsail.pdf.directory;

import java.util.ArrayList;
import java.util.List;

import com.ecsail.sql.SqlExists;
import com.ecsail.sql.select.*;
import com.ecsail.structures.Object_Boat;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Person;

public class Object_MembershipInformation {
	Object_Person primary;
	Object_Person secondary;
	String primaryEmail;
	String secondaryEmail;
	String primaryPhone;
	String secondaryPhone;
	String emergencyPhone;
	Boolean secondaryExists;
	String children;
	String slip;
	String boats;
	
	public Object_MembershipInformation(Object_MembershipList m) {
		this.primary = SqlPerson.getPersonByPid(m.getPid());
		this.secondary = getSecondaryPerson(m);
		this.children = getChildrenString(m);
		getSecondaryPhoneAndEmail();
		getPrimaryPhoneAndEmail();
		getEmergencyPhoneString();
		this.slip = getSlipString(m);
		this.boats = getBoatsString(m);
	}
	
	private String getChildrenString(Object_MembershipList m) {
		String children = "Children: ";
		int count = 0;
		ArrayList<Object_Person> dependants = SqlPerson.getDependants(m);
		for(Object_Person d: dependants) {
			children += d.getFname();
			count++;
			if(count < dependants.size()) children += ",";
		}
		if(children.equals("Children: ")) children = "";  // take the label out if there are no children
		return children;
	}
	
	private String getSlipString(Object_MembershipList m) {
	if (m.getSlip() != null)
		slip = "Slip: " + m.getSlip();
	else
		slip = "";
	return slip;
	}
	
	
	private Object_Person getSecondaryPerson(Object_MembershipList m) {
		Object_Person s = new Object_Person();
		this.secondaryExists = false;
		if (SqlExists.activePersonExists(m.getMsid(), 2)) {
			s = SqlPerson.getPerson(m.getMsid(), 2);
			this.secondaryExists = true;
		}
		return s;
	}
	
	private void getSecondaryPhoneAndEmail() {
		this.secondaryEmail = "";
		this.secondaryPhone = "";
		if(secondaryExists) {
			if (SqlExists.emailExists(secondary))
				this.secondaryEmail = SqlEmail.getEmail(secondary);
			if (SqlExists.cellPhoneExists(secondary, "C")) 
				this.secondaryPhone = SqlPhone.getPhone(secondary, "C") + " Cell";
		}
	}
	
	private void getPrimaryPhoneAndEmail() {
		this.primaryEmail = "";
		this.primaryPhone = "";
		if (SqlExists.emailExists(primary))
			this.primaryEmail = SqlEmail.getEmail(primary);
		if (SqlExists.cellPhoneExists(primary, "C")) {
			this.primaryPhone = SqlPhone.getPhone(primary, "C") + " Cell";
		} else {
			if (SqlExists.cellPhoneExists(primary, "H")) {
				this.primaryPhone = SqlPhone.getPhone(primary, "H") + " Home";
			}
		}
	}
	
	private void getEmergencyPhoneString() {
		this.emergencyPhone = "";
		if (SqlExists.cellPhoneExists(primary, "E")) 
			this.emergencyPhone = "Emergency: " + SqlPhone.getPhone(primary, "E");
	}
	
	private String getBoatsString(Object_MembershipList m) {
		String memberBoats = "";
		List<Object_Boat> boats = new ArrayList<Object_Boat>();
		boats = SqlBoat.getBoats(m.getMsid());
		int count = 0;
		if (boats.size() > 0) {  // there are some boats
			for (Object_Boat b : boats) {
				count++;
				memberBoats += b.getModel();
				if (b.getRegistration_num() != null) {  // this boat has registration
					memberBoats += "(" + b.getRegistration_num() + ")";
				}
				if (count < boats.size()) memberBoats += ", ";
			}
		}
		return memberBoats;
	}

	public Object_Person getPrimary() {
		return primary;
	}

	public void setPrimary(Object_Person primary) {
		this.primary = primary;
	}

	public Object_Person getSecondary() {
		return secondary;
	}

	public void setSecondary(Object_Person secondary) {
		this.secondary = secondary;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getSecondaryPhone() {
		return secondaryPhone;
	}

	public void setSecondaryPhone(String secondaryPhone) {
		this.secondaryPhone = secondaryPhone;
	}

	public String getEmergencyPhone() {
		return emergencyPhone;
	}

	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}

	public Boolean getSecondaryExists() {
		return secondaryExists;
	}

	public void setSecondaryExists(Boolean secondaryExists) {
		this.secondaryExists = secondaryExists;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public String getSlip() {
		return slip;
	}

	public void setSlip(String slip) {
		this.slip = slip;
	}

	public String getBoats() {
		return boats;
	}

	public void setBoats(String boats) {
		this.boats = boats;
	}

}
