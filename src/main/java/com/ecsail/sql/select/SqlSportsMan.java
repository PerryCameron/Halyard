package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Halyard;
import com.ecsail.pdf.directory.Object_Sportsmen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlSportsMan {
    public static ArrayList<Object_Sportsmen> getSportsManAwardNames() {
        ArrayList<Object_Sportsmen> theseOfficers = new ArrayList<>();
        String query = "select AWARD_YEAR,F_NAME,L_Name from awards a left join person p on a.P_ID=p.P_ID";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                theseOfficers.add(new Object_Sportsmen(
                        rs.getString("AWARD_YEAR"),
                        rs.getString("F_NAME"),
                        rs.getString("L_NAME")
                ));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
        }
        return theseOfficers;
    }
}
