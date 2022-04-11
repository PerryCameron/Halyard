package com.ecsail.gui.jotform;

import com.ecsail.structures.jotform.JotFormSubmissionListDTO;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

public class HBoxNewMembershipRow extends HBox {
    JotFormSubmissionListDTO s;

    public HBoxNewMembershipRow(JotFormSubmissionListDTO jotFormSubmissionDTO) {
        this.s = jotFormSubmissionDTO;
        this.setPrefHeight(20);
        this.setSpacing(5);
        HBox.setHgrow(this, Priority.ALWAYS);
        this.getChildren().addAll(
                new Text(s.getCreatedAt()),
                new Text(s.getPrimaryFirstName()),
                new Text(s.getPrimaryLastName()),
                new Text(s.getAddressLine1()),
                new Text(s.getCity()),
                new Text(s.getState()),
                new Text(s.getPostal())
        );
//        new Text(s)),
//        new Text(s)),

    }




}
