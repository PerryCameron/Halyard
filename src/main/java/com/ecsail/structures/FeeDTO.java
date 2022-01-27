package com.ecsail.structures;

import java.math.BigDecimal;

public class FeeDTO {
    private int feeId;
    private String feeName;
    private BigDecimal feeValue;
    private int feeQuantity;
    private int feeYear;
    private String description;

    public FeeDTO(int feeId, String feeName, BigDecimal feeValue, int feeQuantity, int feeYear, String description) {
        this.feeId = feeId;
        this.feeName = feeName;
        this.feeValue = feeValue;
        this.feeQuantity = feeQuantity;
        this.feeYear = feeYear;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFeeId() {
        return feeId;
    }

    public void setFeeId(int feeId) {
        this.feeId = feeId;
    }

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public BigDecimal getFeeValue() {
        return feeValue;
    }

    public void setFeeValue(BigDecimal feeValue) {
        this.feeValue = feeValue;
    }

    public int getFeeQuantity() {
        return feeQuantity;
    }

    public void setFeeQuantity(int feeQuantity) {
        this.feeQuantity = feeQuantity;
    }

    public int getFeeYear() {
        return feeYear;
    }

    public void setFeeYear(int feeYear) {
        this.feeYear = feeYear;
    }
}
