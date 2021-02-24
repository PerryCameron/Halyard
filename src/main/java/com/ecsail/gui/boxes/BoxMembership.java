package com.ecsail.gui.boxes;

import com.ecsail.enums.MembershipType;
import com.ecsail.main.Paths;
import com.ecsail.sql.SqlSelect;
import com.ecsail.structures.Object_MemLabels;
import com.ecsail.structures.Object_MembershipList;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BoxMembership extends HBox {
	
	private Object_MembershipList membership;
	private Object_MemLabels labels;

	public BoxMembership(Object_MembershipList m, Object_MemLabels ml) {
		this.membership =  m;
		this.labels = ml;
		
		//////////// OBJECTS //////////
		HBox hbox1 = new HBox(); // sub HBox1 a
        HBox hbox2 = new HBox(); // sub HBox1 b
        HBox hbox3 = new HBox();
        HBox hbox4 = new HBox();
        HBox hbox5 = new HBox();

        labels.getSelectedYear().setText(membership.getSelectedYear());
        labels.getJoinDate().setText(membership.getJoinDate());
        labels.getMemberID().setText("" + membership.getMembershipId());
        labels.getMemberType().setText("" + MembershipType.getByCode(membership.getMemType()));
        labels.getStatus().setText(getStatus());
     
        ///////////// ATTRIBUTES ///////////
        labels.getSelectedYear().setStyle("-fx-font-weight: bold;");
        labels.getJoinDate().setStyle("-fx-font-weight: bold;");
        labels.getMemberID().setStyle("-fx-font-weight: bold;");
        labels.getMemberType().setStyle("-fx-font-weight: bold;");
        labels.getStatus().setStyle("-fx-font-weight: bold;");
        
        hbox5.getChildren().addAll(new Label("Record Year: "),labels.getSelectedYear());
        hbox1.getChildren().addAll(new Label("Membership ID: "),labels.getMemberID());
        hbox2.getChildren().addAll(new Label("Membership Type: "),labels.getMemberType());
        hbox3.getChildren().addAll(new Label("Join Date: "), labels.getJoinDate());
        hbox4.getChildren().addAll(new Label("Status: "),labels.getStatus());
        
        hbox1.setSpacing(5);
        hbox2.setSpacing(5);
        hbox3.setSpacing(5);
        hbox4.setSpacing(5);
        hbox5.setSpacing(5);
        
        hbox1.setAlignment(Pos.CENTER_LEFT);
        hbox2.setAlignment(Pos.CENTER_LEFT);
        hbox3.setAlignment(Pos.CENTER_LEFT);
        hbox4.setAlignment(Pos.CENTER_LEFT);
        hbox5.setAlignment(Pos.CENTER_LEFT);
    
        getChildren().addAll(hbox5,hbox1,hbox2,hbox3,hbox4);
        
	} // CONSTRUCTOR END
	
	private String getStatus() {
		String result = "not active";
		if(SqlSelect.isRenewed(membership.getMsid(), Paths.getYear())) {
			result = "active";
		}
		return result;
	}

} // CLASS END
