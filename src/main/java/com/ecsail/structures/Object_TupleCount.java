package com.ecsail.structures;

import java.io.Serializable;

public class Object_TupleCount implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2849560027552636604L;
	private int tableCreation;
	private int membershipSize;
	private int idSize;
	private int peopleSize;
	private int phoneSize;
	private int boatSize;
	private int boatOwnerSize;
	private int slipsSize;
	private int memosSize;
	private int emailSize;
	private int moniesSize;
	private int depositsSize;
	private int paymentsSize;
	private int officersSize;
	private int definedFeesSize;
	private int workCreditsSize;
	
	public Object_TupleCount(int tableCreation, int membershipSize, int idSize, int peopleSize, int phoneSize,
			int boatSize, int boatOwnerSize, int slipsSize, int memosSize, int emailSize, int moniesSize,
			int depositsSize, int paymentsSize, int officersSize, int definedFeesSize, int workCreditsSize) {
		this.tableCreation = tableCreation;
		this.membershipSize = membershipSize;
		this.idSize = idSize;
		this.peopleSize = peopleSize;
		this.phoneSize = phoneSize;
		this.boatSize = boatSize;
		this.boatOwnerSize = boatOwnerSize;
		this.slipsSize = slipsSize;
		this.memosSize = memosSize;
		this.emailSize = emailSize;
		this.moniesSize = moniesSize;
		this.depositsSize = depositsSize;
		this.paymentsSize = paymentsSize;
		this.officersSize = officersSize;
		this.definedFeesSize = definedFeesSize;
		this.workCreditsSize = workCreditsSize;
	}
	
	public Object_TupleCount() {
		// default constructor
	}

	public int getTableCreation() {
		return tableCreation;
	}

	public void setTableCreation(int tableCreation) {
		this.tableCreation = tableCreation;
	}

	public int getMembershipSize() {
		return membershipSize;
	}

	public void setMembershipSize(int membershipSize) {
		this.membershipSize = membershipSize;
	}

	public int getIdSize() {
		return idSize;
	}

	public void setIdSize(int idSize) {
		this.idSize = idSize;
	}

	public int getPeopleSize() {
		return peopleSize;
	}

	public void setPeopleSize(int peopleSize) {
		this.peopleSize = peopleSize;
	}

	public int getPhoneSize() {
		return phoneSize;
	}

	public void setPhoneSize(int phoneSize) {
		this.phoneSize = phoneSize;
	}

	public int getBoatSize() {
		return boatSize;
	}

	public void setBoatSize(int boatSize) {
		this.boatSize = boatSize;
	}

	public int getBoatOwnerSize() {
		return boatOwnerSize;
	}

	public void setBoatOwnerSize(int boatOwnerSize) {
		this.boatOwnerSize = boatOwnerSize;
	}

	public int getSlipsSize() {
		return slipsSize;
	}

	public void setSlipsSize(int slipsSize) {
		this.slipsSize = slipsSize;
	}

	public int getMemosSize() {
		return memosSize;
	}

	public void setMemosSize(int memosSize) {
		this.memosSize = memosSize;
	}

	public int getEmailSize() {
		return emailSize;
	}

	public void setEmailSize(int emailSize) {
		this.emailSize = emailSize;
	}

	public int getMoniesSize() {
		return moniesSize;
	}

	public void setMoniesSize(int moniesSize) {
		this.moniesSize = moniesSize;
	}

	public int getDepositsSize() {
		return depositsSize;
	}

	public void setDepositsSize(int depositsSize) {
		this.depositsSize = depositsSize;
	}

	public int getPaymentsSize() {
		return paymentsSize;
	}

	public void setPaymentsSize(int paymentsSize) {
		this.paymentsSize = paymentsSize;
	}

	public int getOfficersSize() {
		return officersSize;
	}

	public void setOfficersSize(int officersSize) {
		this.officersSize = officersSize;
	}

	public int getDefinedFeesSize() {
		return definedFeesSize;
	}

	public void setDefinedFeesSize(int definedFeesSize) {
		this.definedFeesSize = definedFeesSize;
	}

	public int getWorkCreditsSize() {
		return workCreditsSize;
	}

	public void setWorkCreditsSize(int workCreditsSize) {
		this.workCreditsSize = workCreditsSize;
	}
	
}
