package com.ecsail.structures;

import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Object_InvoiceNodes {

    private TextField yscTextField;
    private TextField duesTextField;
    private TextField otherTextField;
    private TextField initiationTextField;
    private TextField wetslipTextField;
    private TextField otherCreditTextField;

    private Spinner<Integer> beachSpinner;
    private Spinner<Integer> kayakRackSpinner;
    private Spinner<Integer> kayakShedSpinner;
    private Spinner<Integer> sailLoftSpinner;
    private Spinner<Integer> sailSchoolLoftSpinner;
    private Spinner<Integer> winterStorageSpinner;
    private Spinner<Integer> wetSlipSpinner;
    private Spinner<Integer> workCreditSpinner;
    private Spinner<Integer> gateKeySpinner;
    private Spinner<Integer> sailLKeySpinner;
    private Spinner<Integer> kayakSKeySpinner;
    private Spinner<Integer> sailSSLKeySpinner;

    private Text duesText;
    private Text beachText;
    private Text kayakRackText;
    private Text kayakShedText;
    private Text sailLoftText;
    private Text sailSchoolLoftText;
    private Text wetSlipText;
    private Text winterStorageText;
    private Text yspText;
    private Text initiationText;
    private Text otherFeeText;
    private Text workCreditsText;
    private Text gateKeyText;
    private Text sailLKeyText;
    private Text kayakSKeyText;
    private Text sailSSLKeyText;
    private Text otherCreditText;
    private Text positionCreditText;
    private Text wetslipTextFee;

    private Text totalFeesText;
    private Text totalCreditText;
    private Text totalPaymentText;
    private Text totalBalanceText;

    public Object_InvoiceNodes(TextField duesTextField) {
        this.yscTextField = new TextField();
        this.duesTextField = new TextField();
        this.otherTextField = new TextField();
        this.initiationTextField = new TextField();
        this.wetslipTextField = new TextField();
        this.otherCreditTextField = new TextField();

        this.beachSpinner = new Spinner<>();
        this.kayakRackSpinner = new Spinner<>();
        this.kayakShedSpinner = new Spinner<>();
        this.sailLoftSpinner = new Spinner<>();
        this.sailSchoolLoftSpinner = new Spinner<>();
        this.winterStorageSpinner = new Spinner<>();
        this.wetSlipSpinner = new Spinner<>();
        this.workCreditSpinner = new Spinner<>();
        this.gateKeySpinner = new Spinner<>();
        this.sailLKeySpinner = new Spinner<>();
        this.kayakSKeySpinner = new Spinner<>();
        this.sailSSLKeySpinner = new Spinner<>();

        this.duesText = new Text();
        this.beachText = new Text();
        this.kayakRackText = new Text();
        this.kayakShedText = new Text();
        this.sailLoftText = new Text();
        this.sailSchoolLoftText = new Text();
        this.wetSlipText = new Text();
        this.winterStorageText = new Text();
        this.yspText = new Text();
        this.initiationText = new Text();
        this.otherFeeText = new Text();
        this.workCreditsText = new Text();
        this.gateKeyText = new Text();
        this.sailLKeyText = new Text();
        this.kayakSKeyText = new Text();
        this.sailSSLKeyText = new Text();
        this.otherCreditText = new Text();
        this.positionCreditText = new Text();
        this.wetslipTextFee = new Text();
        this.totalFeesText = new Text();
        this.totalCreditText = new Text();
        this.totalPaymentText = new Text();
        this.totalBalanceText = new Text();

        this.yscTextField.setPrefWidth(65);
        this.otherTextField.setPrefWidth(65);
        this.otherCreditTextField.setPrefWidth(65);
        this.initiationTextField.setPrefWidth(65);
        this.wetslipTextField.setPrefWidth(65);
        this.duesTextField.setPrefWidth(65);
        this.beachSpinner.setPrefWidth(65);
        this.kayakShedSpinner.setPrefWidth(65);
        this.sailLoftSpinner.setPrefWidth(65);
        this.sailSchoolLoftSpinner.setPrefWidth(65);
        this.winterStorageSpinner.setPrefWidth(65);
        this.wetSlipSpinner.setPrefWidth(65);
        this.kayakRackSpinner.setPrefWidth(65);
        this.workCreditSpinner.setPrefWidth(65);
        this.gateKeySpinner.setPrefWidth(65);
        this.sailLKeySpinner.setPrefWidth(65);
        this.sailSSLKeySpinner.setPrefWidth(65);
        this.kayakSKeySpinner.setPrefWidth(65);
    }

    public TextField getYscTextField() {
        return yscTextField;
    }

    public void setYscTextField(TextField yscTextField) {
        this.yscTextField = yscTextField;
    }

    public TextField getDuesTextField() {
        return duesTextField;
    }

    public void setDuesTextField(TextField duesTextField) {
        this.duesTextField = duesTextField;
    }

    public TextField getOtherTextField() {
        return otherTextField;
    }

    public void setOtherTextField(TextField otherTextField) {
        this.otherTextField = otherTextField;
    }

    public TextField getInitiationTextField() {
        return initiationTextField;
    }

    public void setInitiationTextField(TextField initiationTextField) {
        this.initiationTextField = initiationTextField;
    }

    public TextField getWetslipTextField() {
        return wetslipTextField;
    }

    public void setWetslipTextField(TextField wetslipTextField) {
        this.wetslipTextField = wetslipTextField;
    }

    public TextField getOtherCreditTextField() {
        return otherCreditTextField;
    }

    public void setOtherCreditTextField(TextField otherCreditTextField) {
        this.otherCreditTextField = otherCreditTextField;
    }

    public Spinner<Integer> getBeachSpinner() {
        return beachSpinner;
    }

    public void setBeachSpinner(Spinner<Integer> beachSpinner) {
        this.beachSpinner = beachSpinner;
    }

    public Spinner<Integer> getKayakRackSpinner() {
        return kayakRackSpinner;
    }

    public void setKayakRackSpinner(Spinner<Integer> kayakRackSpinner) {
        this.kayakRackSpinner = kayakRackSpinner;
    }

    public Spinner<Integer> getKayakShedSpinner() {
        return kayakShedSpinner;
    }

    public void setKayakShedSpinner(Spinner<Integer> kayakShedSpinner) {
        this.kayakShedSpinner = kayakShedSpinner;
    }

    public Spinner<Integer> getSailLoftSpinner() {
        return sailLoftSpinner;
    }

    public void setSailLoftSpinner(Spinner<Integer> sailLoftSpinner) {
        this.sailLoftSpinner = sailLoftSpinner;
    }

    public Spinner<Integer> getSailSchoolLoftSpinner() {
        return sailSchoolLoftSpinner;
    }

    public void setSailSchoolLoftSpinner(Spinner<Integer> sailSchoolLoftSpinner) {
        this.sailSchoolLoftSpinner = sailSchoolLoftSpinner;
    }

    public Spinner<Integer> getWinterStorageSpinner() {
        return winterStorageSpinner;
    }

    public void setWinterStorageSpinner(Spinner<Integer> winterStorageSpinner) {
        this.winterStorageSpinner = winterStorageSpinner;
    }

    public Spinner<Integer> getWetSlipSpinner() {
        return wetSlipSpinner;
    }

    public void setWetSlipSpinner(Spinner<Integer> wetSlipSpinner) {
        this.wetSlipSpinner = wetSlipSpinner;
    }

    public Spinner<Integer> getWorkCreditSpinner() {
        return workCreditSpinner;
    }

    public void setWorkCreditSpinner(Spinner<Integer> workCreditSpinner) {
        this.workCreditSpinner = workCreditSpinner;
    }

    public Spinner<Integer> getGateKeySpinner() {
        return gateKeySpinner;
    }

    public void setGateKeySpinner(Spinner<Integer> gateKeySpinner) {
        this.gateKeySpinner = gateKeySpinner;
    }

    public Spinner<Integer> getSailLKeySpinner() {
        return sailLKeySpinner;
    }

    public void setSailLKeySpinner(Spinner<Integer> sailLKeySpinner) {
        this.sailLKeySpinner = sailLKeySpinner;
    }

    public Spinner<Integer> getKayakSKeySpinner() {
        return kayakSKeySpinner;
    }

    public void setKayakSKeySpinner(Spinner<Integer> kayakSKeySpinner) {
        this.kayakSKeySpinner = kayakSKeySpinner;
    }

    public Spinner<Integer> getSailSSLKeySpinner() {
        return sailSSLKeySpinner;
    }

    public void setSailSSLKeySpinner(Spinner<Integer> sailSSLKeySpinner) {
        this.sailSSLKeySpinner = sailSSLKeySpinner;
    }

    public Text getDuesText() {
        return duesText;
    }

    public void setDuesText(Text duesText) {
        this.duesText = duesText;
    }

    public Text getBeachText() {
        return beachText;
    }

    public void setBeachText(Text beachText) {
        this.beachText = beachText;
    }

    public Text getKayakRackText() {
        return kayakRackText;
    }

    public void setKayakRackText(Text kayakRackText) {
        this.kayakRackText = kayakRackText;
    }

    public Text getKayakShedText() {
        return kayakShedText;
    }

    public void setKayakShedText(Text kayakShedText) {
        this.kayakShedText = kayakShedText;
    }

    public Text getSailLoftText() {
        return sailLoftText;
    }

    public void setSailLoftText(Text sailLoftText) {
        this.sailLoftText = sailLoftText;
    }

    public Text getSailSchoolLoftText() {
        return sailSchoolLoftText;
    }

    public void setSailSchoolLoftText(Text sailSchoolLoftText) {
        this.sailSchoolLoftText = sailSchoolLoftText;
    }

    public Text getWetSlipText() {
        return wetSlipText;
    }

    public void setWetSlipText(Text wetSlipText) {
        this.wetSlipText = wetSlipText;
    }

    public Text getWinterStorageText() {
        return winterStorageText;
    }

    public void setWinterStorageText(Text winterStorageText) {
        this.winterStorageText = winterStorageText;
    }

    public Text getYspText() {
        return yspText;
    }

    public void setYspText(Text yspText) {
        this.yspText = yspText;
    }

    public Text getInitiationText() {
        return initiationText;
    }

    public void setInitiationText(Text initiationText) {
        this.initiationText = initiationText;
    }

    public Text getOtherFeeText() {
        return otherFeeText;
    }

    public void setOtherFeeText(Text otherFeeText) {
        this.otherFeeText = otherFeeText;
    }

    public Text getWorkCreditsText() {
        return workCreditsText;
    }

    public void setWorkCreditsText(Text workCreditsText) {
        this.workCreditsText = workCreditsText;
    }

    public Text getGateKeyText() {
        return gateKeyText;
    }

    public void setGateKeyText(Text gateKeyText) {
        this.gateKeyText = gateKeyText;
    }

    public Text getSailLKeyText() {
        return sailLKeyText;
    }

    public void setSailLKeyText(Text sailLKeyText) {
        this.sailLKeyText = sailLKeyText;
    }

    public Text getKayakSKeyText() {
        return kayakSKeyText;
    }

    public void setKayakSKeyText(Text kayakSKeyText) {
        this.kayakSKeyText = kayakSKeyText;
    }

    public Text getSailSSLKeyText() {
        return sailSSLKeyText;
    }

    public void setSailSSLKeyText(Text sailSSLKeyText) {
        this.sailSSLKeyText = sailSSLKeyText;
    }

    public Text getOtherCreditText() {
        return otherCreditText;
    }

    public void setOtherCreditText(Text otherCreditText) {
        this.otherCreditText = otherCreditText;
    }

    public Text getPositionCreditText() {
        return positionCreditText;
    }

    public void setPositionCreditText(Text positionCreditText) {
        this.positionCreditText = positionCreditText;
    }

    public Text getWetslipTextFee() {
        return wetslipTextFee;
    }

    public void setWetslipTextFee(Text wetslipTextFee) {
        this.wetslipTextFee = wetslipTextFee;
    }

    public Text getTotalFeesText() {
        return totalFeesText;
    }

    public void setTotalFeesText(Text totalFeesText) {
        this.totalFeesText = totalFeesText;
    }

    public Text getTotalCreditText() {
        return totalCreditText;
    }

    public void setTotalCreditText(Text totalCreditText) {
        this.totalCreditText = totalCreditText;
    }

    public Text getTotalPaymentText() {
        return totalPaymentText;
    }

    public void setTotalPaymentText(Text totalPaymentText) {
        this.totalPaymentText = totalPaymentText;
    }

    public Text getTotalBalanceText() {
        return totalBalanceText;
    }

    public void setTotalBalanceText(Text totalBalanceText) {
        this.totalBalanceText = totalBalanceText;
    }
}
