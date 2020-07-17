package com.ecsail.gui;

import com.ecsail.main.SqlUpdate;
import com.ecsail.structures.Object_MembershipList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class BoxAddress extends HBox {
private Object_MembershipList membership;
	public BoxAddress(Object_MembershipList me) {
		this.membership = me;
		ObservableList<String> states = 
			    FXCollections.observableArrayList(
			    		"AL","AK","AZ","AR","CA","CO","CT","DE","DC","FL","GA","HI","ID","IL","IN","IA","KS","KY"
			    		,"LA","ME","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","MD","MA","MI"
			    		,"MN","MS","MO","PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"
			    );

			////////////////// OBJECTS ///////////////////////////
	        final Label memAddress = new Label("Street");
	        final Label memCity = new Label("City");
	        final ComboBox<String> stateComboBox = new ComboBox<String>(states);
	        final Label memZipcode = new Label("Zipcode");
	        TextField memAddressTextField = new TextField();
	        TextField memCityTextField = new TextField();
	        TextField memZipcodeTextField = new TextField();
	        HBox hbox1 = new HBox();
	        HBox hbox2 = new HBox();
	        HBox hbox3 = new HBox();
	        
	        ///////////////// ATTRIBUTES //////////////////////////
	        stateComboBox.setValue(membership.getState());
	        hbox1.setSpacing(5);
	        hbox2.setSpacing(5);
	        hbox3.setSpacing(5);
	        hbox1.setAlignment(Pos.CENTER_LEFT);
	        hbox2.setAlignment(Pos.CENTER_LEFT);
	        hbox3.setAlignment(Pos.CENTER_LEFT);
	        memZipcodeTextField.setPrefWidth(80);
	        memAddressTextField.setText(membership.getAddress());
	        memAddressTextField.setPrefWidth(300);
	        memCityTextField.setText(membership.getCity());
	        memCityTextField.setPrefWidth(180);
	        memZipcodeTextField.setText(membership.getZip());
	        hbox1.getChildren().addAll(memAddress,memAddressTextField);
	        hbox2.getChildren().addAll(memCity,memCityTextField);
	        hbox3.getChildren().addAll(memZipcode,memZipcodeTextField);
	        getChildren().addAll(hbox1,hbox2,stateComboBox,hbox3);
	        
	        /////////////////// LISTENERS /////////////////////////
	        memAddressTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
		        @Override
		        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		            //focus out
		            if (oldValue) {  // we have focused and unfocused
		            		SqlUpdate.updateAddress(memAddressTextField.getText(),membership);
		            }
		        }
		    });
	                
	        memCityTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
		        @Override
		        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		            //focus out
		            if (oldValue) {  // we have focused and unfocused
		            		SqlUpdate.updateCity(memCityTextField.getText(),membership);
		            }
		        }
		    });
      
	        memZipcodeTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
		        @Override
		        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		            //focus out
		            if (oldValue) {  // we have focused and unfocused
		            		SqlUpdate.updateZipcode(memZipcodeTextField.getText(),membership);
		            }
		        }
		    });
	        
	        stateComboBox.valueProperty().addListener(new ChangeListener<String>() {
	            @Override 
	            public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, String oldValue, String newValue) {
	                	SqlUpdate.updateState(newValue,membership);
	            }    
	        });
	} // END OF CONSTRUCTOR
}
