package com.ecsail.sql.select;

import com.ecsail.main.Halyard;
import com.ecsail.structures.IdChangeDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlIdChange {
    public static ArrayList<IdChangeDTO> getAllChangedIds() {  //p_id
        ArrayList<IdChangeDTO> thisAwards = new ArrayList<>();
        String query = "select * from id_change";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                thisAwards.add(new IdChangeDTO(
                        rs.getInt("CHANGE_ID"),
                        rs.getInt("ID_YEAR"),
                        rs.getBoolean("CHANGED")
                ));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thisAwards;
    }
}
