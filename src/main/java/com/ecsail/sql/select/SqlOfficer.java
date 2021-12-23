package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.pdf.directory.PDF_Object_Officer;
import com.ecsail.structures.Object_Officer;
import com.ecsail.structures.Object_OfficerWithName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlOfficer {

    /////////////////////////// QUERIES //////////////////////////////////
    static String query01 = "select * from officer";
    static String query02 = "select * from officer o left join person p on o.P_ID=p.P_ID where OFF_YEAR=";
    static String query03 = "select * from officer WHERE ";
    static String query04 = "select * from officer WHERE p_id='";
    static String query05 = "select F_NAME,L_NAME,OFF_YEAR from officer o left join person p on o.P_ID=p.P_ID where OFF_TYPE='";


    public static ObservableList<Object_Officer> getOfficers() {
        ObservableList<Object_Officer> thisOfficer = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor(query01));
            while (rs.next()) {
                thisOfficer.add(new Object_Officer(
                        rs.getInt("O_ID"),
                        rs.getInt("P_ID"),
                        rs.getString("BOARD_YEAR"), // beginning of board term
                        rs.getString("OFF_TYPE"),
                        rs.getString("OFF_YEAR")));
            }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisOfficer;
    }

    public static ArrayList<PDF_Object_Officer> getOfficersByYear(String selectedYear) {
        ArrayList<PDF_Object_Officer> officers = new ArrayList<>();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor(query02 + selectedYear));
            while (rs.next()) {
                officers.add(new PDF_Object_Officer(
                        rs.getString("F_NAME"),
                        rs.getString("L_NAME"),
                        rs.getString("OFF_TYPE"),
                        rs.getString("BOARD_YEAR"), // beginning of board term
                        rs.getString("OFF_YEAR")));
            }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return officers;
    }

    public static ObservableList<Object_Officer> getOfficer(String field, int attribute) {  //p_id
        ObservableList<Object_Officer> thisOfficer = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt
                    .executeQuery(Main.console.setRegexColor(query03 + field + "='" + attribute + "'"));
            while (rs.next()) {
                thisOfficer.add(new Object_Officer(
                        rs.getInt("O_ID"),
                        rs.getInt("P_ID"),
                        rs.getString("BOARD_YEAR"),
                        rs.getString("OFF_TYPE"),
                        rs.getString("OFF_YEAR")));
            }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisOfficer;
    }

    public static Object_Officer getOfficer(int p_id, int i) {
		Object_Officer thisOfficer = null;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;

			rs = stmt.executeQuery(Main.console
					.setRegexColor(query04 + p_id + "' and off_year='" + i + "';"));
			while (rs.next()) {
				thisOfficer = new Object_Officer(rs.getInt("O_ID"), rs.getInt("P_ID"), rs.getString("BOARD_YEAR"),
						rs.getString("OFF_TYPE"), rs.getString("OFF_YEAR"));
			}
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisOfficer;
	}

    public static ArrayList<Object_OfficerWithName> getOfficersWithNames(String type) {
        ArrayList<Object_OfficerWithName> theseOfficers = new ArrayList<>();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor(query05 +type+ "'"));
            while (rs.next()) {
                theseOfficers.add(new Object_OfficerWithName(
                        rs.getString("L_NAME"),
                        rs.getString("F_NAME"),
                        rs.getString("OFF_YEAR")
                        ));
            }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseOfficers;
    }
}
