package org.ecsail.widgetfx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

public class TextFieldFx {

    public static TextField textFieldOf(double width, String prompt) {
       TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setPrefWidth(width);
        return textField;
    }

    public static TextField textFieldOf(double width, Property<?> property) {
        TextField textField = new TextField();
        textField.setPrefWidth(width);
        if (property instanceof StringProperty) {
            textField.textProperty().bindBidirectional((StringProperty) property);
        } else if (property instanceof IntegerProperty) {
            textField.textProperty().bindBidirectional((IntegerProperty) property, new NumberStringConverter());
        } else {
            throw new IllegalArgumentException("Unsupported property type: " + property.getClass());
        }
        return textField;
    }

    public static PasswordField passwordFieldOf(double width, String prompt) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(prompt);
        passwordField.setPrefWidth(width);
        return passwordField;
    }


}
