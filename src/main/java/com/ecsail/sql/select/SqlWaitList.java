package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.structures.Object_WaitList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlWaitList {
    public static Object_WaitList getWaitList(int ms_id) {
        Object_WaitList thisWaitList = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor("select * from waitlist where ms_id=" + ms_id));
            while (rs.next()) {
                thisWaitList = new Object_WaitList(
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

    public static ArrayList<Object_WaitList> getWaitLists() {
        ArrayList<Object_WaitList> thisWaitList = new ArrayList<>();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor("select * from waitlist"));
            while (rs.next()) {
                thisWaitList.add(new Object_WaitList(
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
