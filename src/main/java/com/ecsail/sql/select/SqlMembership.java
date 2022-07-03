package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Halyard;
import com.ecsail.structures.MembershipDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlMembership {

    public static ObservableList<MembershipDTO> getMemberships() {  /// for SQL Script Maker
        ObservableList<MembershipDTO> memberships = FXCollections.observableArrayList();
        String query = "select * from membership";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            while (rs.next()) {
                memberships.add(new MembershipDTO(
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
        String query = "select count(*) from membership m " +
                "inner join membership_id id on id.ms_id=m.ms_id " +
                "where YEAR(JOIN_DATE)="+year+" and id.FISCAL_YEAR=" + year;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            rs.next();
            number = rs.getInt("count(*)");
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }
}
