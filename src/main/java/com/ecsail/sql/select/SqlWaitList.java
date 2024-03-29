package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.Halyard;
import com.ecsail.structures.WaitListDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlWaitList {
    public static WaitListDTO getWaitList(int ms_id) {
        WaitListDTO thisWaitList = null;
        String query = "SELECT * FROM waitlist WHERE ms_id=" + ms_id;
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                thisWaitList = new WaitListDTO(
                        rs.getInt("MS_ID"),
                        rs.getBoolean("SLIPWAIT"),
                        rs.getBoolean("KAYAKRACKWAIT"),
                        rs.getBoolean("SHEDWAIT"),
                        rs.getBoolean("WANTSUBLEASE"),
                        rs.getBoolean("WANTRELEASE"),
                        rs.getBoolean("WANTSLIPCHANGE")
                        );
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisWaitList;
    }

    public static ArrayList<WaitListDTO> getWaitLists() {
        ArrayList<WaitListDTO> thisWaitList = new ArrayList<>();
        String query = "SELECT * FROM waitlist";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                thisWaitList.add(new WaitListDTO(
                        rs.getInt("MS_ID"),
                        rs.getBoolean("SLIPWAIT"),
                        rs.getBoolean("KAYAKRACKWAIT"),
                        rs.getBoolean("SHEDWAIT"),
                        rs.getBoolean("WANTSUBLEASE"),
                        rs.getBoolean("WANTRELEASE"),
                        rs.getBoolean("WANTSLIPCHANGE")
                        ));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisWaitList;
    }
}
