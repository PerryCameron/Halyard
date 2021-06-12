package com.ecsail.gui.boxes;

import java.util.Optional;

import com.ecsail.main.Launcher;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlSelect;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Person;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

///  this class is for the properties tab in membership view
public class BoxProperties extends HBox {
	private Object_MembershipList membership;
	//private TextField duesText;
	public BoxProperties(Object_MembershipList m, Tab membershipTab) {
		super();
		this.membership = m;
		//this.duesText = dt;
		//////////// OBJECTS ///////////////
		HBox hboxGrey = new HBox();  // this is the vbox for organizing all the widgets
		VBox leftVBox = new VBox(); // contains viewable children
		VBox rightVBox = new VBox();
        HBox hbox1 = new HBox();  // holds membershipID, Type and Active
        HBox hbox2 = new HBox();  // holds PersonVBoxes (2 instances require a genereic HBox
        HBox hbox3 = new HBox();  // holds address, city, state, zip
        HBox hbox4 = new HBox();  // holds membership type
        HBox hbox5 = new HBox();  // holds delete membership
		Button removeMembershipButton = new Button("Delete");
		Alert alert = new Alert(AlertType.CONFIRMATION);

		/////////////  ATTRIBUTES /////////////

        hbox1.setSpacing(5);  // membership HBox
        hbox2.setSpacing(5);  // membership HBox
        hbox4.setSpacing(5);  // membership HBox
        hbox5.setSpacing(5);  // membership HBox
        leftVBox.setSpacing(10);
        this.setSpacing(10);
        
        hbox1.setAlignment(Pos.CENTER_LEFT);
        hbox2.setAlignment(Pos.CENTER_LEFT);
        hbox4.setAlignment(Pos.CENTER_LEFT);
        hbox5.setAlignment(Pos.CENTER_LEFT);

		hboxGrey.setPadding(new Insets(5, 5, 5, 10));
		this.setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		
		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		
		this.setId("box-blue");
		hboxGrey.setId("box-grey");
		
		///////////// LISTENERS ////////////

		removeMembershipButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
        		alert.setTitle("Remove Membership");
        		alert.setHeaderText("Membership " + membership.getMsid());
        		alert.setContentText("Are sure you want to delete this membership?");
        		Optional<ButtonType> result = alert.showAndWait();
        		if (result.get() == ButtonType.OK){
        		   deleteMembership(membership.getMsid());
        		} 
            }
        });
		
		///////////// SET CONTENT ////////////////////

		hbox5.getChildren().addAll(new Label("Remove Membership"),removeMembershipButton);
		leftVBox.getChildren().addAll(hbox2,hbox3,hbox5);
		hboxGrey.getChildren().addAll(leftVBox,rightVBox);
		getChildren().add(hboxGrey);
	}
	
	private void deleteMembership(int ms_id) {
		if (!SqlExists.paymentsExistForMembership(ms_id)) {
			SqlDelete.deleteBoatOwner(ms_id);
			SqlDelete.deleteMemos(ms_id);
			SqlDelete.deleteWorkCredits(ms_id);
			SqlDelete.deleteMonies(ms_id);
			SqlDelete.deleteWaitList(ms_id);
			SqlDelete.deleteMembershipId(ms_id); // removes all entries
			ObservableList<Object_Person> people = SqlSelect.getPeople(ms_id);
			for (Object_Person p : people) {
				SqlDelete.deletePhones(p.getP_id());
				SqlDelete.deleteEmail(p.getP_id());
				SqlDelete.deleteOfficer(p.getP_id());
				SqlDelete.deletePerson(p.getP_id());
			}
			SqlDelete.deleteMembership(ms_id);
			Launcher.removeMembershipRow(ms_id);
			Launcher.closeActiveTab();
			System.out.println("Deleting Membership.");
		} else {
			// do not delete the membership
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("There is a problem");
			alert.setHeaderText("This membership contains payment entries");
			alert.setContentText("Before deleting this membership you need to manually remove the payment entries.");
			alert.showAndWait();
		}
	}
}
