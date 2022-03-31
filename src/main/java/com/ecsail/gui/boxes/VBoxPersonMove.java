package com.ecsail.gui.boxes;

import com.ecsail.enums.MemberType;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.PersonDTO;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VBoxPersonMove extends VBox {



    public VBoxPersonMove(PersonDTO person) {
        ToggleGroup tg = new ToggleGroup();
        RadioButton rb0 = new RadioButton("Change "+person.getFname()+"'s membership Type");
        RadioButton rb1 = new RadioButton("Remove "+person.getFname()+" from this membership");
        RadioButton rb2 = new RadioButton("Delete "+person.getFname()+" from database ");
        RadioButton rb3 = new RadioButton("Move "+person.getFname()+" to membership (MSID)");
        Button submit = new Button("Submit");
        TextField msidTextField = new TextField();
        ComboBox<MemberType> combo_box = new ComboBox<MemberType>();
        HBox hBox0 = new HBox();
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        HBox hBox3 = new HBox();
        HBox hbox4 = new HBox();
        VBox vBoxFields = new VBox();

        ///// ATTRIBUTES /////
        msidTextField.setPrefWidth(60);
        rb0.setToggleGroup(tg);
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
        rb3.setToggleGroup(tg);
//        msidTextField.setVisible(false);
//        combo_box.setVisible(false);
        this.setSpacing(5);
        this.setPadding(new Insets(5,5,5,5));
        hbox4.setPadding(new Insets(5,0,0,0));
        hbox4.setSpacing(40);
        combo_box.setValue(MemberType.getByCode(person.getMemberType()));
        vBoxFields.setPrefWidth(100);
        msidTextField.setPrefWidth(60);

        //// LISTENERS //////

        rb3.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
            if (isNowSelected) {
                vBoxFields.getChildren().add(msidTextField);
//                msidTextField.setVisible(true);
            } else {
                vBoxFields.getChildren().remove(msidTextField);
//                msidTextField.setVisible(false);
            }
        });

        rb0.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
            if (isNowSelected) {
                vBoxFields.getChildren().add(combo_box);
//                combo_box.setVisible(true);
            } else {
                vBoxFields.getChildren().remove(combo_box);
//                combo_box.setVisible(false);
            }
        });

        submit.setOnAction((event) -> {
            if(rb1.isSelected()) {
                SqlUpdate.removePersonFromMembership(person);
            }
            if(rb2.isSelected()) {
//                new Dialogue_Delete(person, isDeleted);
            }
            if(rb3.isSelected()) {
                // do this
            }
        });

        combo_box.getItems().setAll(MemberType.values());
        hBox0.getChildren().add(rb0);
        hBox1.getChildren().add(rb1);
        hBox2.getChildren().add(rb2);
        hBox3.getChildren().add(rb3);
        hbox4.getChildren().addAll(vBoxFields,submit);

        this.getChildren().addAll(hBox0,hBox1,hBox2,hBox3,hbox4);
    }
}
