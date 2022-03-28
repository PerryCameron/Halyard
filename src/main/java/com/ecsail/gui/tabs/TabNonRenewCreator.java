package com.ecsail.gui.tabs;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.HalyardPaths;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlMembership_Id;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.MembershipIdDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TabNonRenewCreator extends Tab {
	ObservableList<MembershipIdDTO> ids;
	boolean idChanged;
	public TabNonRenewCreator(String text) {
		super(text);
		
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table



		ComboBox<Integer> comboBox = new ComboBox<>();
		for(int i = Integer.parseInt(HalyardPaths.getYear()) + 1; i > 1969; i--) {
			comboBox.getItems().add(i);
		}
		comboBox.getSelectionModel().select(1);

		Button button = new Button("Insert Records");
		Text changedIndicator = new Text("");

		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		//vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		vboxGrey.setSpacing(5);




		/// LISTENERS ///
		button.setOnAction((event) -> {
			for(MembershipIdDTO id: ids) {
				SqlInsert.addMembershipId(id);
				System.out.println("added " + id.getMembership_id() + " with msid=" + id.getMs_id());
			}
		} );

		comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			changeId(changedIndicator, newValue);
			ids.clear();
			if(idChanged) {
				System.out.println("The Numbers Changed for this year");
				getNonRenewListOnChangedYear(comboBox.getValue());
			} else {
				ids = getNonRenewListOnUnchangedYear(comboBox.getValue());
			}
			printResultList();
		});

		ids = getNonRenewListOnUnchangedYear(comboBox.getValue());
		changeId(changedIndicator, comboBox.getValue());
		vboxGrey.getChildren().addAll(changedIndicator,comboBox,button);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
		
	}

	private void getNonRenewListOnChangedYear(Integer year) {
		ObservableList<MembershipIdDTO> oldYear = SqlMembership_Id.getActiveMembershipIdsByYear(String.valueOf(year -1));
		ObservableList<MembershipIdDTO> newYear = SqlMembership_Id.getAllMembershipIdsByYear(String.valueOf(year));
		System.out.println("old year=" + (year - 1));
		// must compact old to match new

		boolean matches = false;
		for(MembershipIdDTO id: oldYear) {
			for(MembershipIdDTO newId: newYear) {
				if(id.getMs_id() == newId.getMs_id()) {
					matches = true;
				}
			}
			if(!matches) { // there was not a match so we add to list
				id.setMem_type("NR");
				id.setFiscal_Year(String.valueOf(year));
				ids.add(id);
			}
			matches = false;
		}

	}



	private void printResultList() {
		for(MembershipIdDTO id: ids) {
			System.out.print("MID:" + id.getMid());
			System.out.print(" FISCAL_YEAR:" + id.getFiscal_Year());
			System.out.print(" MSID:" + id.getMs_id());
			System.out.print(" MembershipID:" + id.getMembership_id());
			System.out.print(" Renew:" + id.isRenew());
			System.out.print(" Mem_type:" + id.getMem_type());
			System.out.print(" Selected:" + id.isSelected());
			System.out.println(" Late:" + id.isLateRenew());
		}
	}

	//////////////////////// CLASS METHODS //////////////////////////

	private void changeId(Text changedIndicator, Integer newValue) {
		idChanged = numberWasChangedForThisYear(newValue);
		changedIndicator.setText("Year Change: " + idChanged);
	}

	private ObservableList<MembershipIdDTO> getNonRenewListOnUnchangedYear(Integer year) {
		int mid = SqlSelect.getNextAvailablePrimaryKey("membership_id","mid") + 1;
//      #Will give all non-renewed members using previous year if numbers didn't change
		ObservableList<MembershipIdDTO> ids = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from membership_id id " +
					"where id.RENEW=true " +
					"and id.FISCAL_YEAR=" + (year - 1) + " " +
					"and (select MS_ID from membership_id " +
					"where FISCAL_YEAR=" + year + " and MS_ID=id.MS_ID) IS NULL");
			while (rs.next()) {
				ids.add(new MembershipIdDTO(
						mid
						, String.valueOf(year)
						, rs.getInt("MS_ID")
						, rs.getString("MEMBERSHIP_ID")
						, false
						, "NR"
						, rs.getBoolean("SELECTED")
						, rs.getBoolean("LATE_RENEW")));
				mid++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return ids;
	}

	public static Boolean numberWasChangedForThisYear(int year) {
		boolean answer = false;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery("select CHANGED from id_change ic where ID_YEAR=" + year);
			rs.next();
			answer = rs.getBoolean("CHANGED");
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if exists","See below for details");

		}
		return answer;
	}

}


