package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.structures.WaitListDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlWaitList {
    public static WaitListDTO getWaitList(int ms_id) {
        WaitListDTO thisWaitList = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor("select * from waitlist where ms_id=" + ms_id));
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
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisWaitList;
    }

    public static ArrayList<WaitListDTO> getWaitLists() {
        ArrayList<WaitListDTO> thisWaitList = new ArrayList<>();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor("select * from waitlist"));
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
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisWaitList;
    }
}
