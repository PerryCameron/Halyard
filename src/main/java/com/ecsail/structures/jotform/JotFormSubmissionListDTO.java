package com.ecsail.structures.jotform;

import javafx.beans.property.*;

public class JotFormSubmissionListDTO {
    BooleanProperty isRead;
    BooleanProperty isFlagged;
    StringProperty notes;
    StringProperty updatedAt;
    StringProperty ip;
    LongProperty formId;
    StringProperty createdAt;
    LongProperty submissionId;
    StringProperty status;
    StringProperty primaryFirstName;
    StringProperty primaryLastName;
    StringProperty addrLine1;
    StringProperty addrLine2;
    StringProperty city;
    StringProperty state;
    StringProperty postal;
    StringProperty memType;

    public JotFormSubmissionListDTO(Boolean isRead, Boolean isFlagged, String notes, String updatedAt, String ip, Long formId, String createdAt, Long submissionId, String status, String primaryFirstName, String primaryLastName, String addrLine1, String addrLine2, String city, String state, String postal, String memType) {
        this.isRead = new SimpleBooleanProperty(isRead);
        this.isFlagged = new SimpleBooleanProperty(isFlagged);
        this.notes = new SimpleStringProperty(notes);
        this.updatedAt = new SimpleStringProperty(updatedAt);
        this.ip = new SimpleStringProperty(ip);
        this.formId = new SimpleLongProperty(formId);
        this.createdAt = new SimpleStringProperty(createdAt);
        this.submissionId = new SimpleLongProperty(submissionId);
        this.status = new SimpleStringProperty(status);
        this.primaryFirstName = new SimpleStringProperty(primaryFirstName);
        this.primaryLastName = new SimpleStringProperty(primaryLastName);
        this.addrLine1 = new SimpleStringProperty(addrLine1);
        this.addrLine2 = new SimpleStringProperty(addrLine2);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.postal = new SimpleStringProperty(postal);
        this.memType = new SimpleStringProperty(memType);
    }

    public final StringProperty memTypeProperty() {
        return this.memType;
    }
    public final String getMemType() {
        return this.memTypeProperty().get();
    }
    public final void setMemType(final String memType) {
        this.memTypeProperty().set(memType);
    }

    public final BooleanProperty isReadProperty() {return this.isRead;}
    public final Boolean getIsRead() {
        return this.isReadProperty().get();
    }
    public final void setIsRead(final Boolean isRead) {
        this.isReadProperty().set(isRead);
    }

    public final BooleanProperty isFlaggedProperty() {return this.isFlagged;}
    public final Boolean getIsFlagged() {
        return this.isFlaggedProperty().get();
    }
    public final void setIsFlagged(final Boolean isFlagged) {
        this.isFlaggedProperty().set(isFlagged);
    }

    public final StringProperty getNotesProperty() {
        return this.notes;
    }
    public final String getNotes() {
        return this.getNotesProperty().get();
    }
    public final void setNotes(final String notes) {
        this.getNotesProperty().set(notes);
    }

    public final StringProperty getUpdateAtProperty() {
        return this.updatedAt;
    }
    public final String getUpdatedAt() {
        return this.getUpdateAtProperty().get();
    }
    public final void setUpdatedAt(final String updatedAt) {
        this.getUpdateAtProperty().set(updatedAt);
    }

    public final StringProperty getIpProperty() {
        return this.ip;
    }
    public final String getIp() {
        return this.getIpProperty().get();
    }
    public final void setIp(final String ip) {
        this.getIpProperty().set(ip);
    }

    public final LongProperty getFormIdProperty() {
        return this.formId;
    }
    public final Long getFormId() {
        return this.getFormIdProperty().get();
    }
    public final void setFormId(final Long formId) {
        this.getFormIdProperty().set(formId);
    }

    public final StringProperty getCreatedAtProperty() {
        return this.createdAt;
    }
    public final String getCreatedAt() {
        return this.getCreatedAtProperty().get();
    }
    public final void setCreatedAt(final String createdAt) {
        this.getCreatedAtProperty().set(createdAt);
    }

    public final LongProperty getSubmissionIdProperty() {
        return this.submissionId;
    }
    public final Long getSubmissionId() {
        return this.getSubmissionIdProperty().get();
    }
    public final void setSubmissionId(final Long submissionId) {
        this.getSubmissionIdProperty().set(submissionId);
    }

    public final StringProperty getStatusProperty() {
        return this.status;
    }
    public final String getStatus() {
        return this.getStatusProperty().get();
    }
    public final void setStatus(final String status) {
        this.getStatusProperty().set(status);
    }

    public final StringProperty getPrimaryFirstNameProperty() {
        return this.primaryFirstName;
    }
    public final String getPrimaryFirstName() {
        return this.getPrimaryFirstNameProperty().get();
    }
    public final void setPrimaryFirstName(final String primaryFirstName) {
        this.getPrimaryFirstNameProperty().set(primaryFirstName);
    }

    public final StringProperty getPrimaryLastNameProperty() {
        return this.primaryLastName;
    }
    public final String getPrimaryLastName() {
        return this.getPrimaryLastNameProperty().get();
    }
    public final void setPrimaryLastName(final String primaryLastName) {
        this.getPrimaryLastNameProperty().set(primaryLastName);
    }

    public final StringProperty getAddressLine1Property() {
        return this.addrLine1;
    }
    public final String getAddressLine1() {
        return this.getAddressLine1Property().get();
    }
    public final void setAddressLine1(final String addrLine1) {
        this.getAddressLine1Property().set(addrLine1);
    }

    public final StringProperty getAddressLine2Property() {
        return this.addrLine2;
    }
    public final String getAddressLine2() {
        return this.getAddressLine2Property().get();
    }
    public final void setAddressLine2(final String addrLine2) {
        this.getAddressLine2Property().set(addrLine2);
    }

    public final StringProperty getCityProperty() {
        return this.city;
    }
    public final String getCity() {
        return this.getCityProperty().get();
    }
    public final void setCity(final String city) {
        this.getCityProperty().set(city);
    }

    public final StringProperty getStateProperty() {
        return this.state;
    }
    public final String getState() {
        return this.getStateProperty().get();
    }
    public final void setState(final String state) {
        this.getStateProperty().set(state);
    }

    public final StringProperty getPostalProperty() {
        return this.postal;
    }
    public final String getPostal() {
        return this.getStateProperty().get();
    }
    public final void setPostal(final String postal) {
        this.getStateProperty().set(postal);
    }

}
