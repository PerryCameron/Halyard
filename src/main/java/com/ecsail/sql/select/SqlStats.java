package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.structures.StatsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlStats {
    public static ArrayList<StatsDTO> getStatistics() {
        ArrayList<StatsDTO> stats = new ArrayList<>();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor("select * from stats"));
        while (rs.next()) {
            stats.add(new StatsDTO(
                    rs.getInt("STAT_ID"),
                    rs.getInt("FISCAL_YEAR"),
                    rs.getInt("ACTIVE_MEMBERSHIPS"),
                    rs.getInt("NON_RENEW"),
                    rs.getInt("RETURN_MEMBERS"),
                    rs.getInt("NEW_MEMBERS"),
                    rs.getInt("SECONDARY_MEMBERS"),
                    rs.getInt("DEPENDANTS"),
                    rs.getInt("NUMBER_OF_BOATS"),
                    rs.getInt("FAMILY"),
                    rs.getInt("REGULAR"),
                    rs.getInt("SOCIAL"),
                    rs.getInt("LAKEASSOCIATES"),
                    rs.getInt("LIFEMEMBERS"),
                    rs.getInt("RACEFELLOWS"),
                    rs.getInt("STUDENT"),
                    rs.getDouble("DEPOSITS"),
                    rs.getDouble("INIATION")));
        }
        stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return stats;
    }
}
