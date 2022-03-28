package com.ecsail.sql.select;

import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.structures.FeeDTO;
import com.ecsail.structures.IdChangeDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlIdChange {
    public static ArrayList<IdChangeDTO> getAllChangedIds() {  //p_id
        ArrayList<IdChangeDTO> thisAwards = new ArrayList<>();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt
                    .executeQuery(Main.console.setRegexColor("select * from id_change"));
            while (rs.next()) {
                thisAwards.add(new IdChangeDTO(
                        rs.getInt("CHANGE_ID"),
                        rs.getInt("ID_YEAR"),
                        rs.getBoolean("CHANGED")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thisAwards;
    }
}
