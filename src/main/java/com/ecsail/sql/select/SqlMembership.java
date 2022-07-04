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
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
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
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        return memberships;
    }
}
