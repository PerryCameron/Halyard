package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Halyard;
import com.ecsail.pdf.directory.PDF_Object_Officer;
import com.ecsail.structures.OfficerDTO;
import com.ecsail.structures.OfficerWithNameDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlOfficer {

    public static ObservableList<OfficerDTO> getOfficers() {
        ObservableList<OfficerDTO> thisOfficer = FXCollections.observableArrayList();
        String query = "select * from officer";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                thisOfficer.add(new OfficerDTO(
                        rs.getInt("O_ID"),
                        rs.getInt("P_ID"),
                        rs.getString("BOARD_YEAR"), // beginning of board term
                        rs.getString("OFF_TYPE"),
                        rs.getString("OFF_YEAR")));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisOfficer;
    }

    public static ArrayList<PDF_Object_Officer> getOfficersByYear(String selectedYear) {
        ArrayList<PDF_Object_Officer> officers = new ArrayList<>();
        String query = "select * from officer o left join person p on o.P_ID=p.P_ID where OFF_YEAR=" + selectedYear;
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                officers.add(new PDF_Object_Officer(
                        rs.getString("F_NAME"),
                        rs.getString("L_NAME"),
                        rs.getString("OFF_TYPE"),
                        rs.getString("BOARD_YEAR"), // beginning of board term
                        rs.getString("OFF_YEAR")));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return officers;
    }

    public static ObservableList<OfficerDTO> getOfficer(String field, int attribute) {  //p_id
        ObservableList<OfficerDTO> thisOfficer = FXCollections.observableArrayList();
        String query = "select * from officer WHERE " + field + "='" + attribute + "'";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                thisOfficer.add(new OfficerDTO(
                        rs.getInt("O_ID"),
                        rs.getInt("P_ID"),
                        rs.getString("BOARD_YEAR"),
                        rs.getString("OFF_TYPE"),
                        rs.getString("OFF_YEAR")));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisOfficer;
    }

    public static ArrayList<OfficerWithNameDTO> getOfficersWithNames(String type) {
        ArrayList<OfficerWithNameDTO> theseOfficers = new ArrayList<>();
        String query = "select F_NAME,L_NAME,OFF_YEAR from officer o left join person p on o.P_ID=p.P_ID where OFF_TYPE='"+type+"'";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                theseOfficers.add(new OfficerWithNameDTO(
                        rs.getString("L_NAME"),
                        rs.getString("F_NAME"),
                        rs.getString("OFF_YEAR")
                        ));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseOfficers;
    }
}
