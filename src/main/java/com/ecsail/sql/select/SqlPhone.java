package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.structures.Object_Person;
import com.ecsail.structures.Object_Phone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlPhone {
    public static ObservableList<Object_Phone> getPhoneByPid(int p_id) {  // if p_id = 0 then select all
        String query = "select * from phone";
        if(p_id != 0)
            query += " WHERE p_id='" + p_id + "'";
        ObservableList<Object_Phone> thisPhone = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor(query + ";"));
            while (rs.next()) {
                thisPhone.add(new Object_Phone(rs.getInt("PHONE_ID"), rs.getInt("P_ID"), rs.getBoolean("PHONE_LISTED"),
                        rs.getString("PHONE"), rs.getString("PHONE_TYPE")));
            }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPhone;
    }

    public static ArrayList<Object_Phone> getPhoneByPerson(Object_Person p) {  // if p_id = 0 then select all
        ArrayList<Object_Phone> thisPhone = new ArrayList<>();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor("SELECT * from phone Where P_ID=" + p.getP_id()));
            while (rs.next()) {
                thisPhone.add(new Object_Phone(rs.getInt("PHONE_ID"), rs.getInt("P_ID"), rs.getBoolean("PHONE_LISTED"),
                        rs.getString("PHONE"), rs.getString("PHONE_TYPE")));
            }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPhone;
    }

    public static String getListedPhoneByType(Object_Person p, String type) {  // if p_id = 0 then select all
        String phone = "";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor("select * from phone where P_ID=" + p.getP_id() + " and PHONE_LISTED=true and PHONE_TYPE='" + type + "'"));
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
            rs = stmt.executeQuery(Main.console.setRegexColor("select * from phone where P_ID=" + pid + " and PHONE_TYPE='" + type + "'"));
            rs.next();
            phone = rs.getString("PHONE");

        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return phone;
    }
}
