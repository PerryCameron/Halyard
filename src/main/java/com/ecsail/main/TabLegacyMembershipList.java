package com.ecsail.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ecsail.structures.Object_Membership;
import com.ecsail.structures.Object_MembershipList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class TabLegacyMembershipList extends Tab {
	
	private static ObservableList<Object_Membership> legacymemberships;
	private static TableView<Object_Membership> legacyMembershipTableView = new TableView<>();

	public TabLegacyMembershipList() {
		String style = "-fx-background-color: #dfdada;"; /// style for HBoxes
		VBox vbox2 = new VBox();
		
		vbox2.setStyle(style);
		vbox2.setPadding(new Insets(15,15,15,15));
		vbox2.setAlignment(Pos.CENTER);
		
		try {
			legacymemberships = createLegacyMembershipList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setLegacyTableView();
		vbox2.getChildren().add(legacyMembershipTableView);
		setContent(vbox2);
	}
	
	@SuppressWarnings("unchecked")
	public static void setLegacyTableView() {
		legacyMembershipTableView.setItems(legacymemberships);
		legacyMembershipTableView.setPrefWidth(850);
		legacyMembershipTableView.setPrefHeight(580);
		legacyMembershipTableView.setFixedCellSize(30);
		
		TableColumn<Object_Membership, Integer> Col3 = new TableColumn<Object_Membership, Integer>("MSID");
		Col3.setCellValueFactory(new PropertyValueFactory<Object_Membership, Integer>("msid"));

		TableColumn<Object_Membership, String> Col4 = new TableColumn<Object_Membership, String>("JOIN_DATE");
		Col4.setCellValueFactory(new PropertyValueFactory<Object_Membership, String>("joinDate"));

		TableColumn<Object_Membership, String> Col5a = new TableColumn<Object_Membership, String>("Type");
		Col5a.setCellValueFactory(new PropertyValueFactory<Object_Membership, String>("memType"));

		TableColumn<Object_Membership, String> Col6a = new TableColumn<Object_Membership, String>("First Name");
		Col6a.setCellValueFactory(new PropertyValueFactory<Object_Membership, String>("fname"));

		TableColumn<Object_Membership, String> Col6b = new TableColumn<Object_Membership, String>("Last Name");
		Col6b.setCellValueFactory(new PropertyValueFactory<Object_Membership, String>("lname"));

		TableColumn<Object_Membership, String> Col7 = new TableColumn<Object_Membership, String>("Address");
		Col7.setCellValueFactory(new PropertyValueFactory<Object_Membership, String>("address"));

		TableColumn<Object_Membership, String> Col8 = new TableColumn<Object_Membership, String>("City");
		Col8.setCellValueFactory(new PropertyValueFactory<Object_Membership, String>("city"));

		TableColumn<Object_Membership, String> Col9 = new TableColumn<Object_Membership, String>("State");
		Col9.setCellValueFactory(new PropertyValueFactory<Object_Membership, String>("state"));

		TableColumn<Object_Membership, String> Col10 = new TableColumn<Object_Membership, String>("Zip");
		Col10.setCellValueFactory(new PropertyValueFactory<Object_Membership, String>("zip"));

		legacyMembershipTableView.getColumns().addAll(Col3, Col4, Col5a, Col6a, Col6b, Col7, Col8, Col9, Col10);
	}
	
	public ObservableList<Object_Membership> createLegacyMembershipList() throws SQLException {
		legacymemberships = FXCollections.observableArrayList();
		Statement stmt = ConnectDatabase.connection.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select m.MS_ID, m.P_ID, m.MEMBERSHIP_ID, m.JOIN_DATE, m.ACTIVE_MEMBERSHIP, m.MEM_TYPE, p.L_NAME, p.F_NAME,  m.ADDRESS, m.CITY, m.STATE, m.ZIP from membership m inner join person p on m.ms_id = p.ms_id WHERE MEMBER_TYPE='1' AND ACTIVE_MEMBERSHIP=false");
		while (rs.next()) {
			legacymemberships.add(new Object_MembershipList(
					rs.getInt("MS_ID"),
					rs.getInt("P_ID"),
					rs.getInt("MEMBERSHIP_ID"),
					rs.getString("JOIN_DATE"), 
					rs.getBoolean("ACTIVE_MEMBERSHIP"), 
					rs.getString("MEM_TYPE"),
					"",
					rs.getString("L_NAME"), 
					rs.getString("F_NAME"),
					rs.getInt("SUBLEASED_TO"),
					rs.getString("ADDRESS"),
					rs.getString("CITY"), 
					rs.getString("STATE"), 
					rs.getString("ZIP")));
		}
		stmt.close();
		return legacymemberships;
	}


}
