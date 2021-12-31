package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.structures.Object_Membership;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlMembership {

    public static ObservableList<Object_Membership> getMemberships() {  /// for SQL Script Maker
        ObservableList<Object_Membership> memberships = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor("select * from membership;"));
            while (rs.next()) {
                memberships.add(new Object_Membership(
                        rs.getInt("MS_ID"),
                        rs.getInt("P_ID"),
                        rs.getString("JOIN_DATE"),
                        rs.getString("MEM_TYPE"),
                        rs.getString("ADDRESS"),
                        rs.getString("CITY"),
                        rs.getString("STATE"),
                        rs.getString("ZIP")));
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        return memberships;
    }

    public static int getNumberOfNewMembershipsForYear(int year) {
        int number = 0;
        ResultSet rs;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            rs = stmt.executeQuery("select count(*) from membership m " +
                    "inner join membership_id id on id.ms_id=m.ms_id " +
                    "where YEAR(JOIN_DATE)="+year+" and id.FISCAL_YEAR=" + year);
            rs.next();
            number = rs.getInt("count(*)");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }
}
