package com.ecsail.structures;

import javafx.scene.text.Text;

public class Object_paidDuesText {
	Text keyText = new Text();
	Text wetSlipText = new Text();
	Text kayacRackText = new Text();
	Text KayacShedText = new Text();
	Text sailLoftText = new Text();
	Text winterStorageText = new Text();
	Text beachSpotText = new Text();
	Text duesMoneyText = new Text();
	Text keyMoneyText = new Text();
	Text wetSlipMoneyText = new Text();
	Text kayacRackMoneyText = new Text();
	Text kayacShedMoneyText = new Text();
	Text sailLoftMoneyText = new Text();
	Text winterStorageMoneyText = new Text();
	Text beachSpotMoneyText = new Text();
	Text creditsMoneyText = new Text();
	Text initiationText = new Text();
	Text initiationMoneyText = new Text();
	Text totalMoneyText = new Text();
	Text yspDonationText = new Text();
	Text yspDonationMoneyText = new Text();
	
	public Object_paidDuesText() {
		setZeroText();
	}

	public void changeKeyText(String key) {
		this.keyText.setText(key);
	}

	public void changeWetSlipText(String wetSlipText) {
		this.wetSlipText.setText(wetSlipText);
	}

	public void changeKayacRackText(String kayacRackText) {
		this.kayacRackText.setText(kayacRackText);
	}

	public void changeKayacShedText(String kayacShedText) {
		KayacShedText.setText(kayacShedText);
	}

	public void changeSailLoftText(String sailLoft) {
		sailLoftText.setText(sailLoft);
	}

	public void changeWinterStorageText(String winterStorage) {
		winterStorageText.setText(winterStorage);
	}
	
	public void changeBeachSpotText(String beachSpot) {
		beachSpotText.setText(beachSpot);
	}
	
	public void changeInitiationText(String initiation) {
		initiationText.setText(initiation);
	}
	
	public void changeYspDonationText(String yspDonation) {
		yspDonationText.setText(yspDonation);
	}

	public void changeTotalText(String totalMoney) {
		this.totalMoneyText.setText(" $" + totalMoney);
	}

	public void changeKeyMoneyText(String keyMoney) {
		this.keyMoneyText.setText(" $" + keyMoney);
	}

	public void changeWetSlipMoneyText(String wetSlipMoney) {
		this.wetSlipMoneyText.setText(" $" + wetSlipMoney);
	}

	public void changeKayacRackMoneyText(String kayacRackMoney) {
		this.kayacRackMoneyText.setText(" $" + kayacRackMoney);
	}

	public void changeKayacShedMoneyText(String kayacShedMoney) {
		kayacShedMoneyText.setText(" $" + kayacShedMoney);
	}

	public void changeSailLoftMoneyText(String sailLoftMoney) {
		sailLoftMoneyText.setText(" $" + sailLoftMoney);
	}

	public void changeWinterStorageMoneyText(String winterStorageMoney) {
		winterStorageMoneyText.setText(" $" + winterStorageMoney);
	}
	
	public void changeBeachSpotMoneyText(String beachSpotMoney) {
		beachSpotMoneyText.setText(" $" + beachSpotMoney);
	}

	public void changeCreditsMoneyText(String creditsMoney) {
		creditsMoneyText.setText("-$" + creditsMoney);
	}

	public void changeDuesMoneyText(String duesMoney) {
		duesMoneyText.setText(" $" + duesMoney);
	}
	
	public void changeInitiationMoneyText(String initiationMoney) {
		initiationMoneyText.setText(" $" + initiationMoney);
	}
	
	public void changeYspDonationMoneyText(String yspDonationMoney) {
		yspDonationMoneyText.setText(" $" + yspDonationMoney);
	}

	public void setZeroText() {
	changeDuesMoneyText("0");
	changeKeyText("0");
	changeKeyMoneyText("0");
	changeWetSlipText("0");
	changeWetSlipMoneyText("0");
	changeKayacRackText("0");
	changeKayacRackMoneyText("0");
	changeKayacShedText("0");
	changeKayacShedMoneyText("0");
	changeSailLoftText("0");
	changeSailLoftMoneyText("0");
	changeWinterStorageText("0");
	changeWinterStorageMoneyText("0");
	changeCreditsMoneyText("0");
	changeTotalText("0");
	changeInitiationText("0");
	changeInitiationMoneyText("0");
	changeBeachSpotMoneyText("0");
	changeYspDonationText("0");
	changeYspDonationMoneyText("0");
	
	}

	public Text getKeyText() {
		return keyText;
	}

	public Text getWetSlipText() {
		return wetSlipText;
	}

	public Text getKayacRackText() {
		return kayacRackText;
	}

	public Text getKayacShedText() {
		return KayacShedText;
	}

	public Text getSailLoftText() {
		return sailLoftText;
	}

	public Text getWinterStorageText() {
		return winterStorageText;
	}
	
	public Text getBeachSpotText() {
		return beachSpotText;
	}

	public Text getDuesMoneyText() {
		return duesMoneyText;
	}

	public Text getKeyMoneyText() {
		return keyMoneyText;
	}

	public Text getWetSlipMoneyText() {
		return wetSlipMoneyText;
	}

	public Text getKayacRackMoneyText() {
		return kayacRackMoneyText;
	}

	public Text getKayacShedMoneyText() {
		return kayacShedMoneyText;
	}

	public Text getSailLoftMoneyText() {
		return sailLoftMoneyText;
	}

	public Text getWinterStorageMoneyText() {
		return winterStorageMoneyText;
	}
	
	public Text getBeachSpotMoneyText() {
		return beachSpotMoneyText;
	}

	public Text getCreditsMoneyText() {
		return creditsMoneyText;
	}

	public Text getTotalMoneyText() {
		return totalMoneyText;
	}
	
	public Text getInitiationText() {
		return initiationText;
	}
	
	public Text getInitiationMoneyText() {
		return initiationMoneyText;
	}
	
	public Text getYspDonationText() {
		return yspDonationText;
	}
	
	public Text getYspDonationMoneyText() {
		return yspDonationMoneyText;
	}
}
