package com.ecsail.gui.boxes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.ecsail.main.Launcher;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlSelect;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.Object_MemLabels;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoxProperties extends HBox {
	private Object_MembershipList membership;
	private Object_MemLabels labels;
	//private TextField duesText;
	public BoxProperties(Object_MembershipList m, Object_MemLabels l, Tab membershipTab) {
		super();
		this.membership = m;
		this.labels = l;
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
		//ComboBox<MemberType> combo_box = new ComboBox<MemberType>();
		DatePicker joinDatePicker = new DatePicker();
		Alert alert = new Alert(AlertType.CONFIRMATION);

		/////////////  ATTRIBUTES /////////////

        hbox1.setSpacing(5);  // membership HBox
        hbox1.setAlignment(Pos.CENTER_LEFT);
        hbox2.setSpacing(5);  // membership HBox
        hbox2.setAlignment(Pos.CENTER_LEFT);
        hbox4.setSpacing(5);  // membership HBox
        hbox4.setAlignment(Pos.CENTER_LEFT);
        hbox5.setSpacing(5);  // membership HBox
        hbox5.setAlignment(Pos.CENTER_LEFT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date;
        if(membership.getJoinDate() != null) {
        date = LocalDate.parse(membership.getJoinDate(), formatter);
        } else {
        date = LocalDate.parse("1900-01-01", formatter);
        }
        joinDatePicker.setValue(date);
        //combo_box.setValue(MemberType.getByCode(membership.getMemType()));
		hboxGrey.setPadding(new Insets(5, 5, 5, 10));
		hboxGrey.setPrefWidth(942);
		leftVBox.setSpacing(10);
		setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		setId("box-blue");
		setSpacing(10);
		hboxGrey.setId("box-grey");
		//combo_box.getItems().setAll(MemberType.values());
		
		///////////// LISTENERS ////////////
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				LocalDate date = joinDatePicker.getValue();
				SqlUpdate.updateMembership(membership.getMsid(),"JOIN_DATE",date);
				membership.setJoinDate(joinDatePicker.getValue().toString());
		        labels.getJoinDate().setText(joinDatePicker.getValue().toString());
			}
		};
		joinDatePicker.setOnAction(event);
			
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
		
      /*  combo_box.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {

        });
		*/
		///////////// SET CONTENT ////////////////////

		hbox2.getChildren().addAll(new Label("Change join date:"),joinDatePicker);
		//hbox4.getChildren().addAll(new Label("Membership Type"),combo_box);
		hbox5.getChildren().addAll(new Label("Remove Membership"),removeMembershipButton);
		leftVBox.getChildren().addAll(hbox2,hbox3,hbox5);
		//centerVBox.getChildren().addAll(hbox5);
		rightVBox.getChildren().addAll(new BoxMemberID(membership));
		hboxGrey.getChildren().addAll(leftVBox,rightVBox);
		getChildren().add(hboxGrey);
	}
	
	private void deleteMembership(int ms_id) {
		SqlDelete.deleteBoatOwner(ms_id);
		SqlDelete.deleteMemos(ms_id);
		SqlDelete.deleteWorkCredits(ms_id);
		SqlDelete.deleteMonies(ms_id);
		SqlDelete.deleteWaitList(ms_id);
		SqlDelete.deleteMembershipId(ms_id);  // removes all entries
		ObservableList<Object_Person> people = SqlSelect.getPeople(ms_id);
		for(Object_Person p: people) {
			SqlDelete.deletePhones(p.getP_id());
			SqlDelete.deleteEmail(p.getP_id());
			SqlDelete.deleteOfficer(p.getP_id());
			SqlDelete.deletePerson(p.getP_id());
		}
		SqlDelete.deleteMembership(ms_id);
		Launcher.removeMembershipRow(ms_id);
		Launcher.closeActiveTab();
	}
}
