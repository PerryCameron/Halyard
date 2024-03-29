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
        String query = "SELECT * FROM phone";
        if(p_id != 0)
            query += " WHERE p_id=" + p_id;
        ObservableList<PhoneDTO> thisPhone = FXCollections.observableArrayList();
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                thisPhone.add(new PhoneDTO(rs.getInt("PHONE_ID"), rs.getInt("p_id"), rs.getBoolean("phone_listed"),
                        rs.getString("PHONE"), rs.getString("phone_type")));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPhone;
    }

    public static ArrayList<PhoneDTO> getPhoneByPerson(PersonDTO p) {  // if p_id = 0 then select all
        ArrayList<PhoneDTO> thisPhone = new ArrayList<>();
        String query = "SELECT * FROM phone WHERE p_id=" + p.getP_id();
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                thisPhone.add(new PhoneDTO(rs.getInt("PHONE_ID"), rs.getInt("p_id"), rs.getBoolean("phone_listed"),
                        rs.getString("PHONE"), rs.getString("phone_type")));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPhone;
    }

    public static String getListedPhoneByType(PersonDTO p, String type) {  // if p_id = 0 then select all
        String phone = "";
        String query = "SELECT * FROM phone WHERE p_id=" + p.getP_id() + " AND phone_listed=true AND phone_type='" + type + "'";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            rs.next();
            phone = rs.getString("PHONE");
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return phone;
    }

    public static String getPhoneByType(String pid, String type) {  // if p_id = 0 then select all
        String phone = "";
        String query = "SELECT * FROM phone WHERE p_id=" + pid + " AND phone_type='" + type + "'";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            rs.next();
            phone = rs.getString("PHONE");
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return phone;
    }
}
