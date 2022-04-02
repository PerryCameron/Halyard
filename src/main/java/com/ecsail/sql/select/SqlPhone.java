package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Halyard;
import com.ecsail.structures.PersonDTO;
import com.ecsail.structures.PhoneDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlPhone {
    public static ObservableList<PhoneDTO> getPhoneByPid(int p_id) {  // if p_id = 0 then select all
        String query = "select * from phone";
        if(p_id != 0)
            query += " WHERE p_id='" + p_id + "'";
        ObservableList<PhoneDTO> thisPhone = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Halyard.console.setRegexColor(query + ";"));
            while (rs.next()) {
                thisPhone.add(new PhoneDTO(rs.getInt("PHONE_ID"), rs.getInt("P_ID"), rs.getBoolean("PHONE_LISTED"),
                        rs.getString("PHONE"), rs.getString("PHONE_TYPE")));
            }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPhone;
    }

    public static ArrayList<PhoneDTO> getPhoneByPerson(PersonDTO p) {  // if p_id = 0 then select all
        ArrayList<PhoneDTO> thisPhone = new ArrayList<>();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Halyard.console.setRegexColor("SELECT * from phone Where P_ID=" + p.getP_id()));
            while (rs.next()) {
                thisPhone.add(new PhoneDTO(rs.getInt("PHONE_ID"), rs.getInt("P_ID"), rs.getBoolean("PHONE_LISTED"),
                        rs.getString("PHONE"), rs.getString("PHONE_TYPE")));
            }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPhone;
    }

    public static String getListedPhoneByType(PersonDTO p, String type) {  // if p_id = 0 then select all
        String phone = "";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Halyard.console.setRegexColor("select * from phone where P_ID=" + p.getP_id() + " and PHONE_LISTED=true and PHONE_TYPE='" + type + "'"));
            rs.next();
            phone = rs.getString("PHONE");

        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return phone;
    }

    public static String getPhoneByType(String pid, String type) {  // if p_id = 0 then select all
        String phone = "";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Halyard.console.setRegexColor("select * from phone where P_ID=" + pid + " and PHONE_TYPE='" + type + "'"));
            rs.next();
            phone = rs.getString("PHONE");

        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return phone;
    }
}
