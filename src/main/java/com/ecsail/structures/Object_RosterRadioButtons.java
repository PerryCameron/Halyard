package com.ecsail.structures;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class Object_RosterRadioButtons {
	RadioButton radioAll = new RadioButton("All");
	RadioButton radioActive = new RadioButton("Active"); 
    RadioButton radioNonRenew = new RadioButton("Non-Renew"); 
    RadioButton radioNewMembers = new RadioButton("New Members");
    RadioButton radioReturnMembers = new RadioButton("Return Members");
    RadioButton radioLatePaymentMembers = new RadioButton("Late Dues");
    
    RadioButton radioSlipWaitList = new RadioButton("Slip Waitlist");
    RadioButton radioSlip = new RadioButton("Slip Owners");
    RadioButton radioWantsToSublease = new RadioButton("Slips for Sublease");
    RadioButton radioOpenSlips = new RadioButton("Open Slips");
    RadioButton radioSlipChange = new RadioButton("Wants New Slip");
    RadioButton radioSubLeasedSlips = new RadioButton("Subleased Slips");
    
    RadioButton radioKayakRackWaitList = new RadioButton("Kayak Rack WaitList");
    RadioButton radioKayakRackOwners = new RadioButton("Kayak Rack");
    RadioButton radioShedWaitList = new RadioButton("Kayak Shed WaitList");
    RadioButton radioShedOwner = new RadioButton("Kayak Shed");
    ToggleGroup tg1 = new ToggleGroup();
    
	public Object_RosterRadioButtons(RadioButton radioAll, RadioButton radioActive, RadioButton radioNonRenew,
			RadioButton radioNewMembers, RadioButton radioNewReturnMembers, RadioButton radioAllActiveMembers, RadioButton radioSlipWaitList,
			RadioButton radioSlip, RadioButton radioWantsToSublease, RadioButton radioOpenSlips,
			RadioButton radioSlipChange, RadioButton radioSubLeasedSlips, RadioButton radioKayakRackWaitList,
			RadioButton radioKayakRackOwners, RadioButton radioShedWaitList, RadioButton radioShedOwner) {
		this.radioAll = radioAll;
		this.radioActive = radioActive;
		this.radioNonRenew = radioNonRenew;
		this.radioNewMembers = radioNewMembers;
		this.radioReturnMembers = radioNewReturnMembers;
		this.radioLatePaymentMembers = radioLatePaymentMembers;
		this.radioSlipWaitList = radioSlipWaitList;
		this.radioSlip = radioSlip;
		this.radioWantsToSublease = radioWantsToSublease;
		this.radioOpenSlips = radioOpenSlips;
		this.radioSlipChange = radioSlipChange;
		this.radioSubLeasedSlips = radioSubLeasedSlips;
		this.radioKayakRackWaitList = radioKayakRackWaitList;
		this.radioKayakRackOwners = radioKayakRackOwners;
		this.radioShedWaitList = radioShedWaitList;
		this.radioShedOwner = radioShedOwner;
	}
	
	public Object_RosterRadioButtons() {
		// default constructor
	}
	
	public void setSameToggleGroup () {
		radioAll.setToggleGroup(tg1);
		radioActive.setToggleGroup(tg1);
		radioNonRenew.setToggleGroup(tg1);
		radioNewMembers.setToggleGroup(tg1);
		radioReturnMembers.setToggleGroup(tg1);
		radioLatePaymentMembers.setToggleGroup(tg1);
		radioSlipWaitList.setToggleGroup(tg1);
		radioSlip.setToggleGroup(tg1);
		radioWantsToSublease.setToggleGroup(tg1);
		radioOpenSlips.setToggleGroup(tg1);
		radioSlipChange.setToggleGroup(tg1);
		radioSubLeasedSlips.setToggleGroup(tg1);
		radioKayakRackWaitList.setToggleGroup(tg1);
		radioKayakRackOwners.setToggleGroup(tg1);
		radioShedWaitList.setToggleGroup(tg1);
		radioShedOwner.setToggleGroup(tg1);
	}
	
	public RadioButton getRadioAll() {
		return radioAll;
	}

	public void setRadioAll(RadioButton radioAll) {
		this.radioAll = radioAll;
	}

	public RadioButton getRadioActive() {
		return radioActive;
	}

	public void setRadioActive(RadioButton radioActive) {
		this.radioActive = radioActive;
	}

	public RadioButton getRadioNonRenew() {
		return radioNonRenew;
	}

	public void setRadioNonRenew(RadioButton radioNonRenew) {
		this.radioNonRenew = radioNonRenew;
	}

	public RadioButton getRadioNewMembers() {
		return radioNewMembers;
	}

	public void setRadioNewMembers(RadioButton radioNewMembers) {
		this.radioNewMembers = radioNewMembers;
	}

	public RadioButton getRadioReturnMembers() {
		return radioReturnMembers;
	}

	public void setRadioReturnMembers(RadioButton radioNewReturnMembers) {
		this.radioReturnMembers = radioNewReturnMembers;
	}

	public RadioButton getRadioSlipWaitList() {
		return radioSlipWaitList;
	}

	public void setRadioSlipWaitList(RadioButton radioSlipWaitList) {
		this.radioSlipWaitList = radioSlipWaitList;
	}

	public RadioButton getRadioSlip() {
		return radioSlip;
	}

	public void setRadioSlip(RadioButton radioSlip) {
		this.radioSlip = radioSlip;
	}

	public RadioButton getRadioWantsToSublease() {
		return radioWantsToSublease;
	}

	public void setRadioWantsToSublease(RadioButton radioWantsToSublease) {
		this.radioWantsToSublease = radioWantsToSublease;
	}

	public RadioButton getRadioOpenSlips() {
		return radioOpenSlips;
	}

	public void setRadioOpenSlips(RadioButton radioOpenSlips) {
		this.radioOpenSlips = radioOpenSlips;
	}

	public RadioButton getRadioSlipChange() {
		return radioSlipChange;
	}

	public void setRadioSlipChange(RadioButton radioSlipChange) {
		this.radioSlipChange = radioSlipChange;
	}

	public RadioButton getRadioSubLeasedSlips() {
		return radioSubLeasedSlips;
	}

	public void setRadioSubLeasedSlips(RadioButton radioSubLeasedSlips) {
		this.radioSubLeasedSlips = radioSubLeasedSlips;
	}

	public RadioButton getRadioKayakRackWaitList() {
		return radioKayakRackWaitList;
	}

	public void setRadioKayakRackWaitList(RadioButton radioKayakRackWaitList) {
		this.radioKayakRackWaitList = radioKayakRackWaitList;
	}

	public RadioButton getRadioKayakRackOwners() {
		return radioKayakRackOwners;
	}

	public void setRadioKayakRackOwners(RadioButton radioKayakRackOwners) {
		this.radioKayakRackOwners = radioKayakRackOwners;
	}

	public RadioButton getRadioShedWaitList() {
		return radioShedWaitList;
	}

	public void setRadioShedWaitList(RadioButton radioShedWaitList) {
		this.radioShedWaitList = radioShedWaitList;
	}

	public RadioButton getRadioShedOwner() {
		return radioShedOwner;
	}

	public void setRadioShedOwner(RadioButton radioShedOwner) {
		this.radioShedOwner = radioShedOwner;
	}

	public RadioButton getRadioLatePaymentMembers() {
		return radioLatePaymentMembers;
	}

	public void setRadioLatePaymentMembers(RadioButton radioAllActiveMembers) {
		this.radioLatePaymentMembers = radioAllActiveMembers;
	}

	@Override
	public String toString() {
		return "Object_RosterRadioButtons [radioAll=" + radioAll + ", radioActive=" + radioActive + ", radioNonRenew="
				+ radioNonRenew + ", radioNewMembers=" + radioNewMembers + ", radioNewReturnMembers="
				+ radioReturnMembers + ", radioAllActiveMembers=" + radioLatePaymentMembers + ", radioSlipWaitList="
				+ radioSlipWaitList + "]";
	}
	
	
	
}
