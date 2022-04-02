package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Halyard;
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
            rs = stmt.executeQuery(Halyard.console.setRegexColor("select * from stats"));
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

    public static StatsDTO createStatDTO(int year, int statID) {
        StatsDTO stat = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(getStatQuery(year));
            while (rs.next()) {
                stat = new StatsDTO(
                        statID,
                        rs.getInt("YEAR"),
                        rs.getInt("ACTIVE_MEMBERSHIPS"), // active membership
                        rs.getInt("NON_RENEW"),
                        rs.getInt("RETURN_MEMBERS"),
                        rs.getInt("NEW_MEMBERS"),
                        0,
//                        rs.getInt("SECONDARY_MEMBERS"),
                        0,
//                        rs.getInt("DEPENDANTS"),
//                        rs.getInt("NUMBER_OF_BOATS"),
                        0,
                        rs.getInt("FAMILY"),
                        rs.getInt("REGULAR"),
                        rs.getInt("SOCIAL"),
                        rs.getInt("LAKEASSOCIATES"),
                        rs.getInt("LIFEMEMBERS"),
                        rs.getInt("RACEFELLOWS"),
                        rs.getInt("STUDENT"),
//                        rs.getDouble("DEPOSITS"),
                        0,
//                        rs.getDouble("INIATION"));
                        0);
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return stat;
    }

    public static String getStatQuery(int year) {
        int lastYear = year -1;
        return
                "SELECT \n" +
                        "id.FISCAL_YEAR AS 'YEAR',\n" +
                        "COUNT(DISTINCT IF(id.MEM_TYPE = 'RM' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'REGULAR',\n" +
                        "COUNT(DISTINCT IF(id.MEM_TYPE = 'FM' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'FAMILY',\n" +
                        "COUNT(DISTINCT IF(id.MEM_TYPE = 'SO' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'SOCIAL',\n" +
                        "COUNT(DISTINCT IF(id.MEM_TYPE = 'LA' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'LAKEASSOCIATES',\n" +
                        "COUNT(DISTINCT IF(id.MEM_TYPE = 'LM' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'LIFEMEMBERS',\n" +
                        "COUNT(DISTINCT IF(id.MEM_TYPE = 'SM' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'STUDENT',\n" +
                        "COUNT(DISTINCT IF(id.MEM_TYPE = 'RF' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'RACEFELLOWS',\n" +
                        "COUNT(DISTINCT IF(YEAR(m.JOIN_DATE)='"+year+"',id.MEMBERSHIP_ID, NULL)) AS 'NEW_MEMBERS',\n" +
                        "COUNT(DISTINCT IF(id.MEMBERSHIP_ID > \n" +
                        "  (\n" +
                        "  select MEMBERSHIP_ID \n" +
                        "  from membership_id \n" +
                        "  where FISCAL_YEAR="+year+" and MS_ID=\n" +
                        "     (\n" +
                        "     select MS_ID \n" +
                        "     from membership_id \n" +
                        "     where MEMBERSHIP_ID=\n" +
                        "        (\n" +
                        "        select max(membership_id) \n" +
                        "        from membership_id \n" +
                        "        where FISCAL_YEAR=" + lastYear +
                        "        and membership_id < 500 \n" +
                        "        and renew=1\n" +
                        "        ) \n" +
                        "     and FISCAL_YEAR=" + lastYear +
                        "     )\n" +
                        "   ) \n" +
                        "and id.MEMBERSHIP_ID < 500 \n" +
                        "and YEAR(m.JOIN_DATE)!='"+year+"' \n" +
                        "and (SELECT NOT EXISTS(select mid from membership_id where FISCAL_YEAR="+lastYear+" and RENEW=1 and MS_ID=id.MS_ID)), id.MEMBERSHIP_ID, NULL)) AS 'RETURN_MEMBERS', \n" +
                        "SUM(NOT RENEW) as 'NON_RENEW',\n" +
                        "SUM(RENEW) as 'ACTIVE_MEMBERSHIPS'\n" +
                        "FROM membership_id id\n" +
                        "LEFT JOIN membership m on id.MS_ID=m.MS_ID \n" +
                        "WHERE FISCAL_YEAR=" + year;
    }
}
