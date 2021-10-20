package com.ecsail.structures;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Object_InvoiceNodes {
    private GridPane gridPane;
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

    // VBoxes for totals
    private VBox vboxDues;
    private VBox vboxBeach;
    private VBox vboxKayak;
    private VBox vboxKayakShed;
    private VBox vboxSailLoft;
    private VBox vboxSailSchoolLoft;
    private VBox vboxWetSlip;
    private VBox vboxWinterStorage;
    private VBox vboxGateKey;
    private VBox vboxSailLoftKey;
    private VBox vboxKayakShedKey;
    private VBox vboxSailSchoolLoftKey;
    private VBox vboxWorkCredits;
    private VBox vboxYSC;
    private VBox vboxInitiation;
    private VBox vboxOther;
    private VBox vboxOtherCredit;
    private VBox vboxPositionCredit;

    // VBoxes for multipliers
    private VBox vboxBeachFee;
    private VBox vboxKayakFee;
    private VBox vboxKayakShedFee;
    private VBox vboxSailLoftFee;
    private VBox vboxSailSchoolLoftFee;
    private VBox vboxWetSlipFee;
    private VBox vboxWinterStorageFee;
    private VBox vboxGateKeyFee;
    private VBox vboxSailLoftKeyFee;
    private VBox vboxKayakShedKeyFee;
    private VBox vboxSailSchoolLoftKeyFee;
    private VBox vboxWorkCreditsFee;
    private VBox vboxTitlePrice;
    private VBox vboxTitleTotal;
    private VBox vboxButtons;
    private VBox vboxPink; // this creates a pink border around the table
    private VBox vboxCommitButton;
    private Button buttonAdd;
    private Button buttonDelete;
    private Button commitButton;
    private CheckBox renewCheckBox;

    private Object_DefinedFee definedFees;


    public Object_InvoiceNodes(TextField duesTextField, Object_DefinedFee definedFees, TableView paymentTableView) {

        this.buttonAdd = new Button("Add");
        this.buttonDelete = new Button("Delete");
        this.commitButton = new Button("Commit");
        this.renewCheckBox = new CheckBox("Renew");
        this.definedFees = definedFees;
        this.gridPane = new GridPane();
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

        // VBoxes for totals
        this.vboxDues = new VBox();
        this.vboxBeach = new VBox();
        this.vboxKayak = new VBox();
        this.vboxKayakShed = new VBox();
        this.vboxSailLoft = new VBox();
        this.vboxSailSchoolLoft = new VBox();
        this.vboxWetSlip = new VBox();
        this.vboxWinterStorage = new VBox();
        this.vboxGateKey = new VBox();
        this.vboxSailLoftKey = new VBox();
        this.vboxKayakShedKey = new VBox();
        this.vboxSailSchoolLoftKey = new VBox();
        this.vboxWorkCredits = new VBox();
        this.vboxYSC = new VBox();
        this.vboxInitiation = new VBox();
        this.vboxOther = new VBox();
        this.vboxOtherCredit = new VBox();
        this.vboxPositionCredit = new VBox();

        // VBoxes for multipliers
        this.vboxBeachFee = new VBox();
        this.vboxKayakFee = new VBox();
        this.vboxKayakShedFee = new VBox();
        this.vboxSailLoftFee = new VBox();
        this.vboxSailSchoolLoftFee = new VBox();
        this.vboxWetSlipFee = new VBox();
        this.vboxWinterStorageFee = new VBox();
        this.vboxGateKeyFee = new VBox();
        this.vboxSailLoftKeyFee = new VBox();
        this.vboxKayakShedKeyFee = new VBox();
        this.vboxSailSchoolLoftKeyFee = new VBox();
        this.vboxWorkCreditsFee = new VBox();
        this.vboxTitlePrice = new VBox();
        this.vboxTitleTotal = new VBox();
        this.vboxButtons = new VBox();
        this.vboxPink = new VBox(); // this creates a pink border around the table
        this.vboxCommitButton = new VBox();

        // ATTRIBUTES

        vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink frame around table
        vboxPink.setId("box-pink");
        HBox.setHgrow(vboxPink, Priority.ALWAYS);
        vboxButtons.setSpacing(5);
        vboxCommitButton.setSpacing(10);

        HBox.setHgrow(gridPane,Priority.ALWAYS);
        gridPane.setHgap(25);
        gridPane.setVgap(5);

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

        renewCheckBox.setSelected(true);
        buttonAdd.setPrefWidth(60);
        buttonDelete.setPrefWidth(60);

        vboxDues.getChildren().add(duesText);
        vboxDues.setAlignment(Pos.CENTER_RIGHT);
        vboxBeach.getChildren().add(beachText);
        vboxBeach.setAlignment(Pos.CENTER_RIGHT);
        vboxKayak.getChildren().add(kayakRackText);
        vboxKayak.setAlignment(Pos.CENTER_RIGHT);
        vboxKayakShed.getChildren().add(kayakShedText);
        vboxKayakShed.setAlignment(Pos.CENTER_RIGHT);
        vboxSailLoft.getChildren().add(sailLoftText);
        vboxSailLoft.setAlignment(Pos.CENTER_RIGHT);
        vboxSailSchoolLoft.getChildren().add(sailSchoolLoftText);
        vboxSailSchoolLoft.setAlignment(Pos.CENTER_RIGHT);
        vboxWetSlip.getChildren().add(wetSlipText);
        vboxWetSlip.setAlignment(Pos.CENTER_RIGHT);
        vboxWinterStorage.getChildren().add(winterStorageText);
        vboxWinterStorage.setAlignment(Pos.CENTER_RIGHT);
        vboxGateKey.getChildren().add(gateKeyText);
        vboxGateKey.setAlignment(Pos.CENTER_RIGHT);
        vboxSailLoftKey.getChildren().add(sailLKeyText);
        vboxSailLoftKey.setAlignment(Pos.CENTER_RIGHT);
        vboxKayakShedKey.getChildren().add(kayakSKeyText);
        vboxKayakShedKey.setAlignment(Pos.CENTER_RIGHT);
        vboxSailSchoolLoftKey.getChildren().add(sailSSLKeyText);
        vboxSailSchoolLoftKey.setAlignment(Pos.CENTER_RIGHT);
        vboxPositionCredit.getChildren().add(positionCreditText);
        vboxPositionCredit.setAlignment(Pos.CENTER_RIGHT);
        vboxWorkCredits.getChildren().add(workCreditsText);
        vboxWorkCredits.setAlignment(Pos.CENTER_RIGHT);

        vboxYSC.getChildren().add(yspText);
        vboxYSC.setAlignment(Pos.CENTER_RIGHT);
        vboxInitiation.getChildren().add(initiationText);
        vboxInitiation.setAlignment(Pos.CENTER_RIGHT);
        vboxOther.getChildren().add(otherFeeText);
        vboxOther.setAlignment(Pos.CENTER_RIGHT);
        vboxOtherCredit.getChildren().add(otherCreditText);
        vboxOtherCredit.setAlignment(Pos.CENTER_RIGHT);

        vboxBeachFee.getChildren().add(new Text(String.valueOf(definedFees.getBeach())));
        vboxBeachFee.setAlignment(Pos.CENTER_RIGHT);
        vboxKayakFee.getChildren().add(new Text(String.valueOf(definedFees.getKayak_rack())));
        vboxKayakFee.setAlignment(Pos.CENTER_RIGHT);
        vboxKayakShedFee.getChildren().add(new Text(String.valueOf(definedFees.getKayak_shed())));
        vboxKayakShedFee.setAlignment(Pos.CENTER_RIGHT);
        vboxSailLoftFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_loft())));
        vboxSailLoftFee.setAlignment(Pos.CENTER_RIGHT);
        vboxSailSchoolLoftFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_school_laser_loft())));
        vboxSailSchoolLoftFee.setAlignment(Pos.CENTER_RIGHT);
        vboxWinterStorageFee.getChildren().add(new Text(String.valueOf(definedFees.getWinter_storage())));
        vboxWinterStorageFee.setAlignment(Pos.CENTER_RIGHT);
        vboxGateKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getMain_gate_key())));
        vboxGateKeyFee.setAlignment(Pos.CENTER_RIGHT);
        vboxSailLoftKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_loft_key())));
        vboxSailLoftKeyFee.setAlignment(Pos.CENTER_RIGHT);
        vboxKayakShedKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getKayak_shed_key())));
        vboxKayakShedKeyFee.setAlignment(Pos.CENTER_RIGHT);
        vboxSailSchoolLoftKeyFee.getChildren().add(new Text(String.valueOf(definedFees.getSail_school_loft_key())));
        vboxSailSchoolLoftKeyFee.setAlignment(Pos.CENTER_RIGHT);
        vboxWorkCreditsFee.getChildren().add(new Text(String.valueOf(definedFees.getWork_credit())));
        vboxWorkCreditsFee.setAlignment(Pos.CENTER_RIGHT);
        vboxTitlePrice.setAlignment(Pos.CENTER_RIGHT);
        vboxTitleTotal.setAlignment(Pos.CENTER_RIGHT);
        vboxWetSlipFee.getChildren().add(wetslipTextFee);
        vboxWetSlipFee.setAlignment(Pos.CENTER_RIGHT);
        vboxCommitButton.getChildren().addAll(renewCheckBox,commitButton);
        vboxPink.getChildren().add(paymentTableView);
        vboxButtons.getChildren().addAll(buttonAdd, buttonDelete);
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public Button getButtonAdd() {
        return buttonAdd;
    }

    public void setButtonAdd(Button buttonAdd) {
        this.buttonAdd = buttonAdd;
    }

    public Button getButtonDelete() {
        return buttonDelete;
    }

    public void setButtonDelete(Button buttonDelete) {
        this.buttonDelete = buttonDelete;
    }

    public Button getCommitButton() {
        return commitButton;
    }

    public void setCommitButton(Button commitButton) {
        this.commitButton = commitButton;
    }

    public CheckBox getRenewCheckBox() {
        return renewCheckBox;
    }

    public void setRenewCheckBox(CheckBox renewCheckBox) {
        this.renewCheckBox = renewCheckBox;
    }

    public Object_DefinedFee getDefinedFees() {
        return definedFees;
    }

    public void setDefinedFees(Object_DefinedFee definedFees) {
        this.definedFees = definedFees;
    }

    public VBox getVboxDues() {
        return vboxDues;
    }

    public void setVboxDues(VBox vboxDues) {
        this.vboxDues = vboxDues;
    }

    public VBox getVboxBeach() {
        return vboxBeach;
    }

    public void setVboxBeach(VBox vboxBeach) {
        this.vboxBeach = vboxBeach;
    }

    public VBox getVboxKayak() {
        return vboxKayak;
    }

    public void setVboxKayak(VBox vboxKayak) {
        this.vboxKayak = vboxKayak;
    }

    public VBox getVboxKayakShed() {
        return vboxKayakShed;
    }

    public void setVboxKayakShed(VBox vboxKayakShed) {
        this.vboxKayakShed = vboxKayakShed;
    }

    public VBox getVboxSailLoft() {
        return vboxSailLoft;
    }

    public void setVboxSailLoft(VBox vboxSailLoft) {
        this.vboxSailLoft = vboxSailLoft;
    }

    public VBox getVboxSailSchoolLoft() {
        return vboxSailSchoolLoft;
    }

    public void setVboxSailSchoolLoft(VBox vboxSailSchoolLoft) {
        this.vboxSailSchoolLoft = vboxSailSchoolLoft;
    }

    public VBox getVboxWetSlip() {
        return vboxWetSlip;
    }

    public void setVboxWetSlip(VBox vboxWetSlip) {
        this.vboxWetSlip = vboxWetSlip;
    }

    public VBox getVboxWinterStorage() {
        return vboxWinterStorage;
    }

    public void setVboxWinterStorage(VBox vboxWinterStorage) {
        this.vboxWinterStorage = vboxWinterStorage;
    }

    public VBox getVboxGateKey() {
        return vboxGateKey;
    }

    public void setVboxGateKey(VBox vboxGateKey) {
        this.vboxGateKey = vboxGateKey;
    }

    public VBox getVboxSailLoftKey() {
        return vboxSailLoftKey;
    }

    public void setVboxSailLoftKey(VBox vboxSailLoftKey) {
        this.vboxSailLoftKey = vboxSailLoftKey;
    }

    public VBox getVboxKayakShedKey() {
        return vboxKayakShedKey;
    }

    public void setVboxKayakShedKey(VBox vboxKayakShedKey) {
        this.vboxKayakShedKey = vboxKayakShedKey;
    }

    public VBox getVboxSailSchoolLoftKey() {
        return vboxSailSchoolLoftKey;
    }

    public void setVboxSailSchoolLoftKey(VBox vboxSailSchoolLoftKey) {
        this.vboxSailSchoolLoftKey = vboxSailSchoolLoftKey;
    }

    public VBox getVboxWorkCredits() {
        return vboxWorkCredits;
    }

    public void setVboxWorkCredits(VBox vboxWorkCredits) {
        this.vboxWorkCredits = vboxWorkCredits;
    }

    public VBox getVboxYSC() {
        return vboxYSC;
    }

    public void setVboxYSC(VBox vboxYSC) {
        this.vboxYSC = vboxYSC;
    }

    public VBox getVboxInitiation() {
        return vboxInitiation;
    }

    public void setVboxInitiation(VBox vboxInitiation) {
        this.vboxInitiation = vboxInitiation;
    }

    public VBox getVboxOther() {
        return vboxOther;
    }

    public void setVboxOther(VBox vboxOther) {
        this.vboxOther = vboxOther;
    }

    public VBox getVboxOtherCredit() {
        return vboxOtherCredit;
    }

    public void setVboxOtherCredit(VBox vboxOtherCredit) {
        this.vboxOtherCredit = vboxOtherCredit;
    }

    public VBox getVboxPositionCredit() {
        return vboxPositionCredit;
    }

    public void setVboxPositionCredit(VBox vboxPositionCredit) {
        this.vboxPositionCredit = vboxPositionCredit;
    }

    public VBox getVboxBeachFee() {
        return vboxBeachFee;
    }

    public void setVboxBeachFee(VBox vboxBeachFee) {
        this.vboxBeachFee = vboxBeachFee;
    }

    public VBox getVboxKayakFee() {
        return vboxKayakFee;
    }

    public void setVboxKayakFee(VBox vboxKayakFee) {
        this.vboxKayakFee = vboxKayakFee;
    }

    public VBox getVboxKayakShedFee() {
        return vboxKayakShedFee;
    }

    public void setVboxKayakShedFee(VBox vboxKayakShedFee) {
        this.vboxKayakShedFee = vboxKayakShedFee;
    }

    public VBox getVboxSailLoftFee() {
        return vboxSailLoftFee;
    }

    public void setVboxSailLoftFee(VBox vboxSailLoftFee) {
        this.vboxSailLoftFee = vboxSailLoftFee;
    }

    public VBox getVboxSailSchoolLoftFee() {
        return vboxSailSchoolLoftFee;
    }

    public void setVboxSailSchoolLoftFee(VBox vboxSailSchoolLoftFee) {
        this.vboxSailSchoolLoftFee = vboxSailSchoolLoftFee;
    }

    public VBox getVboxWetSlipFee() {
        return vboxWetSlipFee;
    }

    public void setVboxWetSlipFee(VBox vboxWetSlipFee) {
        this.vboxWetSlipFee = vboxWetSlipFee;
    }

    public VBox getVboxWinterStorageFee() {
        return vboxWinterStorageFee;
    }

    public void setVboxWinterStorageFee(VBox vboxWinterStorageFee) {
        this.vboxWinterStorageFee = vboxWinterStorageFee;
    }

    public VBox getVboxGateKeyFee() {
        return vboxGateKeyFee;
    }

    public void setVboxGateKeyFee(VBox vboxGateKeyFee) {
        this.vboxGateKeyFee = vboxGateKeyFee;
    }

    public VBox getVboxSailLoftKeyFee() {
        return vboxSailLoftKeyFee;
    }

    public void setVboxSailLoftKeyFee(VBox vboxSailLoftKeyFee) {
        this.vboxSailLoftKeyFee = vboxSailLoftKeyFee;
    }

    public VBox getVboxKayakShedKeyFee() {
        return vboxKayakShedKeyFee;
    }

    public void setVboxKayakShedKeyFee(VBox vboxKayakShedKeyFee) {
        this.vboxKayakShedKeyFee = vboxKayakShedKeyFee;
    }

    public VBox getVboxSailSchoolLoftKeyFee() {
        return vboxSailSchoolLoftKeyFee;
    }

    public void setVboxSailSchoolLoftKeyFee(VBox vboxSailSchoolLoftKeyFee) {
        this.vboxSailSchoolLoftKeyFee = vboxSailSchoolLoftKeyFee;
    }

    public VBox getVboxWorkCreditsFee() {
        return vboxWorkCreditsFee;
    }

    public void setVboxWorkCreditsFee(VBox vboxWorkCreditsFee) {
        this.vboxWorkCreditsFee = vboxWorkCreditsFee;
    }

    public VBox getVboxTitlePrice() {
        return vboxTitlePrice;
    }

    public void setVboxTitlePrice(VBox vboxTitlePrice) {
        this.vboxTitlePrice = vboxTitlePrice;
    }

    public VBox getVboxTitleTotal() {
        return vboxTitleTotal;
    }

    public void setVboxTitleTotal(VBox vboxTitleTotal) {
        this.vboxTitleTotal = vboxTitleTotal;
    }

    public VBox getVboxButtons() {
        return vboxButtons;
    }

    public void setVboxButtons(VBox vboxButtons) {
        this.vboxButtons = vboxButtons;
    }

    public VBox getVboxPink() {
        return vboxPink;
    }

    public void setVboxPink(VBox vboxPink) {
        this.vboxPink = vboxPink;
    }

    public VBox getVboxCommitButton() {
        return vboxCommitButton;
    }

    public void setVboxCommitButton(VBox vboxCommitButton) {
        this.vboxCommitButton = vboxCommitButton;
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



    public void populateUncommitted() {
        Font font = Font.font("Verdana", FontWeight.BOLD, 16);
        Text text1 = new Text("Fee");
        Text text2 = new Text("Price");
        Text text3 = new Text("Total");
        text1.setFont(font);
        text2.setFont(font);
        text3.setFont(font);
        int row = 0;
//		/// header
        gridPane.add(text1 , 0, row, 1, 1);
        gridPane.add(new Text(""), 1, row, 1, 1);
        gridPane.add(new Text(""), 2, row, 1, 1);
        gridPane.add(vboxTitlePrice, 3, row, 1, 1);
        gridPane.add(vboxTitleTotal, 4, row, 1, 1);

        /// Row 1
        row++;
        gridPane.add(new Label("Dues:"), 0, row, 1, 1);
        gridPane.add(duesTextField, 1, row, 1, 1);
        gridPane.add(new Label(""), 2, row, 1, 1);
        gridPane.add(new Label(""), 3, row, 1, 1);
        gridPane.add(vboxDues, 4, row, 1, 1);

        /// Row 2
        row++;
        gridPane.add(new Label("Beach Spot:"), 0, row, 1, 1);
        gridPane.add(beachSpinner, 1, row, 1, 1);
        gridPane.add(new Label("X"), 2, row, 1, 1);
        gridPane.add(vboxBeachFee, 3, row, 1, 1);
        gridPane.add(vboxBeach, 4, row, 1, 1);
        /// Row 3
        row++;
        gridPane.add(new Label("Kayak Rack:"), 0, row, 1, 1);
        gridPane.add(kayakRackSpinner, 1, row, 1, 1);
        gridPane.add(new Label("X"), 2, row, 1, 1);
        gridPane.add(vboxKayakFee, 3, row, 1, 1);
        gridPane.add(vboxKayak, 4, row, 1, 1);
        /// Row 5
        row++;
        gridPane.add(new Label("Kayak Shed:"), 0, row, 1, 1);
        gridPane.add(kayakShedSpinner, 1, row, 1, 1);
        gridPane.add(new Label("X"), 2, row, 1, 1);
        gridPane.add(vboxKayakShedFee, 3, row, 1, 1);
        gridPane.add(vboxKayakShed, 4, row, 1, 1);
        /// Row 6
        row++;
        gridPane.add(new Label("Sail Loft:"), 0, row, 1, 1);
        gridPane.add(sailLoftSpinner, 1, row, 1, 1);
        gridPane.add(new Label("X"), 2, row, 1, 1);
        gridPane.add(vboxSailLoftFee, 3, row, 1, 1);
        gridPane.add(vboxSailLoft, 4, row, 1, 1);
        /// Row 7
        row++;
        gridPane.add(new Label("Sail School Loft:"), 0, row, 1, 1);
        gridPane.add(sailSchoolLoftSpinner, 1, row, 1, 1);
        gridPane.add(new Label("X"), 2, row, 1, 1);
        gridPane.add(vboxSailSchoolLoftFee, 3, row, 1, 1);
        gridPane.add(vboxSailSchoolLoft, 4, row, 1, 1);
        /// Row 8
        row++;
        gridPane.add(new Label("Wet Slip:"), 0, row, 1, 1);
        gridPane.add(wetSlipSpinner, 1, row, 1, 1);
        gridPane.add(new Label("X"), 2, row, 1, 1);
        gridPane.add(vboxWetSlipFee, 3, row, 1, 1);
        gridPane.add(vboxWetSlip, 4, row, 1, 1);
        /// Row 9
        row++;
        gridPane.add(new Label("Winter Storage:"), 0, row, 1, 1);
        gridPane.add(winterStorageSpinner, 1, row, 1, 1);
        gridPane.add(new Label("X"), 2, row, 1, 1);
        gridPane.add(vboxWinterStorageFee, 3, row, 1, 1);
        gridPane.add(vboxWinterStorage, 4, row, 1, 1);
        /// Row 10
        row++;
        gridPane.add(new Label("Gate Key:"), 0, row, 1, 1);
        gridPane.add(gateKeySpinner, 1, row, 1, 1);
        gridPane.add(new Label("X"), 2, row, 1, 1);
        gridPane.add(vboxGateKeyFee, 3, row, 1, 1);
        gridPane.add(vboxGateKey, 4, row, 1, 1);
        /// Row 11
        row++;
        gridPane.add(new Label("Sail Loft Key:"), 0, row, 1, 1);
        gridPane.add(sailLKeySpinner, 1, row, 1, 1);
        gridPane.add(new Label("X"), 2, row, 1, 1);
        gridPane.add(vboxSailLoftKeyFee, 3, row, 1, 1);
        gridPane.add(vboxSailLoftKey, 4, row, 1, 1);
        /// Row 12
        row++;
        gridPane.add(new Label("Kayak Shed Key:"), 0, row, 1, 1);
        gridPane.add(kayakSKeySpinner, 1, row, 1, 1);
        gridPane.add(new Label("X"), 2, row, 1, 1);
        gridPane.add(vboxKayakShedKeyFee, 3, row, 1, 1);
        gridPane.add(vboxKayakShedKey, 4, row, 1, 1);
        /// Row 10
        row++;
        gridPane.add(new Label("Sail School Loft Key:"), 0, row, 1, 1);
        gridPane.add(sailSSLKeySpinner, 1, row, 1, 1);
        gridPane.add(new Label("X"), 2, row, 1, 1);
        gridPane.add(vboxSailSchoolLoftKeyFee, 3, row, 1, 1);
        gridPane.add(vboxSailSchoolLoftKey, 4, row, 1, 1);
        /// Row 13
        row++;
        gridPane.add(new Label("YSP Donation:"), 0, row, 1, 1);
        gridPane.add(yscTextField, 1, row, 1, 1);
        gridPane.add(new Label(""), 2, row, 1, 1);
        gridPane.add(new Label(""), 3, row, 1, 1);
        gridPane.add(vboxYSC, 4, row, 1, 1);
        /// Row 14
        row++;
        gridPane.add(new Label("Initiation:"), 0, row, 1, 1);
        gridPane.add(initiationTextField, 1, row, 1, 1);
        gridPane.add(new Label(""), 2, row, 1, 1);
        gridPane.add(new Label(""), 3, row, 1, 1);
        gridPane.add(vboxInitiation, 4, row, 1, 1);
        /// Row 15
        row++;
        gridPane.add(new Label("Other Fee:"), 0, row, 1, 1);
        gridPane.add(otherTextField, 1, row, 1, 1);
        gridPane.add(new Label(""), 2, row, 1, 1);
        gridPane.add(new Label(""), 3, row, 1, 1);
        gridPane.add(vboxOther, 4, row, 1, 1);
        /// Row 16
        row++;
        gridPane.add(new Label("Work Credits:"), 0, row, 1, 1);
        gridPane.add(workCreditSpinner, 1, row, 1, 1);
        gridPane.add(new Label("X"), 2, row, 1, 1);
        gridPane.add(vboxWorkCreditsFee, 3, row, 1, 1);
        gridPane.add(vboxWorkCredits, 4, row, 1, 1);
        row++;
        gridPane.add(new Label("Other Credit:"), 0, row, 1, 1);
        gridPane.add(otherCreditTextField, 1, row, 1, 1);
        gridPane.add(new Label(""), 2, row, 1, 1);
        gridPane.add(new Label(""), 3, row, 1, 1);
        gridPane.add(vboxOtherCredit, 4, row, 1, 1);
        // Spacer
        row++;
        Region spacer = new Region();
        spacer.setPrefHeight(25);
        gridPane.add(new Label("Position Credit:"), 0, row, 1, 1);
        gridPane.add(spacer, 1, row, 1, 1);
        gridPane.add(new Label(""), 2, row, 1, 1);
        gridPane.add(new Label(""), 3, row, 1, 1);
        gridPane.add(vboxPositionCredit, 4, row, 1, 1);
        row++;
        gridPane.add(new Label(""), 0, row, 5, 1);
        // Table
        row++;
        gridPane.add(vboxPink, 0, row, 4, 1);
        gridPane.add(vboxButtons, 4, row, 1, 1);
        // spacer
        row++;
        gridPane.add(new Label(""), 0, row, 5, 1);
        // final portion
        row++;
        gridPane.add(new Label("Total Fees:"), 0, row, 1, 1);
        gridPane.add(totalFeesText, 1, row, 3, 1);
        gridPane.add(vboxCommitButton, 4, row, 1, 4);
        row++;
        gridPane.add(new Label("Total Credit:"), 0, row, 3, 1);
        gridPane.add(totalCreditText, 1, row, 3, 1);
        row++;
        gridPane.add(new Label("Payment:"), 0, row, 3, 1);
        gridPane.add(totalPaymentText, 1, row, 3, 1);
        row++;
        gridPane.add(new Label("Balance:"), 0, row, 3, 1);
        gridPane.add(totalBalanceText, 1, row, 3, 1);
        row++;
        gridPane.add(new Label(""), 0, row, 5, 1);

        vboxTitlePrice.getChildren().add(text2);
        vboxTitleTotal.getChildren().add(text3);
    }


}
