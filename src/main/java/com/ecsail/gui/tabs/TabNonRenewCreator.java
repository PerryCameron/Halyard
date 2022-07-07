package com.ecsail.gui.tabs;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.HalyardPaths;
import com.ecsail.sql.select.SqlMembership_Id;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.MembershipIdDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
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

		HBox hBoxDivider = new HBox();
		VBox vBoxLeft = new VBox();
		VBox vBoxRight = new VBox();

		ComboBox<Integer> comboBox = new ComboBox<>();
		for(int i = Integer.parseInt(HalyardPaths.getYear()) + 1; i > 1969; i--) {
			comboBox.getItems().add(i);
		}
		comboBox.getSelectionModel().select(1);

		Button button = new Button("Insert Records");
		TextArea displayArea = new TextArea();
		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		//vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		VBox.setVgrow(vboxGrey,Priority.ALWAYS);
		VBox.setVgrow(displayArea,Priority.ALWAYS);
		HBox.setHgrow(vBoxRight, Priority.ALWAYS);
		VBox.setVgrow(vBoxRight,Priority.ALWAYS);
		VBox.setVgrow(vBoxLeft,Priority.ALWAYS);
		HBox.setHgrow(displayArea,Priority.ALWAYS);
		vboxGrey.setSpacing(5);
		vBoxLeft.setPrefWidth(400);
		vBoxRight.setPadding(new Insets(5,5,5,5));

		/// LISTENERS ///
		button.setOnAction((event) -> {

			System.out.println(ids.size());
		} );

		comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			ids.clear();
			displayArea.clear();
			idChanged = numberWasChangedForThisYear(newValue);
			if(idChanged) {
				displayArea.setText("***** Membership ID numbers compacted in " + comboBox.getValue() + " *******\n");
				getNonRenewListOnChangedYear(comboBox.getValue());
			} else {
				displayArea.setText("***** Membership Id numbers remain the same in " + comboBox.getValue() + " *******\n");
				ids = getNonRenewListOnUnchangedYear(comboBox.getValue());
			}
			for(MembershipIdDTO id: ids) {
				displayArea.appendText(id.toString() + "\n");
			}
		});

		ids = getNonRenewListOnUnchangedYear(comboBox.getValue());
		vBoxLeft.getChildren().addAll(comboBox,button);
		vBoxRight.getChildren().addAll(displayArea);
		hBoxDivider.getChildren().addAll(vBoxLeft,vBoxRight);
		vboxGrey.getChildren().addAll(hBoxDivider);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}

	private void getNonRenewListOnChangedYear(Integer year) {
		ObservableList<MembershipIdDTO> oldYear = SqlMembership_Id.getActiveMembershipIdsByYear(String.valueOf(year -1));
		ObservableList<MembershipIdDTO> newYear = SqlMembership_Id.getAllMembershipIdsByYear(String.valueOf(year));
		int mid = SqlSelect.getNextAvailablePrimaryKey("membership_id","mid");
		System.out.println("old year=" + (year - 1));


		boolean matches = false;
		int count = 1;
		for(MembershipIdDTO id: oldYear) {
			id.setMembership_id(String.valueOf(count)); // set membership numbers to changed year
			count++;
			for(MembershipIdDTO newId: newYear) {
				if(id.getMs_id() == newId.getMs_id()) {
					matches = true;
				}
			}
			if(!matches) { // there was not a match so we add to list
				id.setMem_type("NR"); // set as non renew
				id.setMid(mid); // put in appropriate mid
				mid++;
				id.setIsRenew(false); // make non-renew
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


	private ObservableList<MembershipIdDTO> getNonRenewListOnUnchangedYear(Integer year) {
		int mid = SqlSelect.getNextAvailablePrimaryKey("membership_id","mid");
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


