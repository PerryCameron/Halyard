package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.HalyardPaths;
import com.ecsail.main.Halyard;
import com.ecsail.structures.MembershipListDTO;
import com.ecsail.structures.SlipDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlMembershipList {
    /// not a pure SQL FUNCTION was having difficulties narrowing it down pure SQL.
    public static int getNumberOfReturningMembershipsForYear(int fiscalYear) {
        ObservableList<MembershipListDTO> rosters = getReturnMembers(fiscalYear);
        return rosters.size();
    }

    public static ObservableList<MembershipListDTO> getRosterOfKayakRackOwners(String year) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip \n"
                    + "from slip s \n"
                    + "right join membership m on m.MS_ID=s.MS_ID \n"
                    + "left join membership_id id on m.MS_ID=id.MS_ID\n"
                    + "left join money mo on m.MS_ID=mo.MS_ID \n"
                    + "left join person p on p.MS_ID=m.MS_ID \n"
                    + "where mo.FISCAL_YEAR='"+year+"' and id.fiscal_year='"+year+"' and id.renew=1 and kayak_rack=1 and p.member_type=1 order by membership_id;"));
            queryToArrayList(rosters, rs);
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        System.out.println("Creating Roster list for " + year + "...");
        return rosters;
    }

    public static ObservableList<MembershipListDTO> getRosterOfMembershipsThatPaidLate(String year) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select distinct \n" +
                            "MAX(m.MS_ID) AS MS_ID,\n" +
                            "MAX(m.P_ID) AS P_ID,\n" +
                            "id.MEMBERSHIP_ID,\n" +
                            "id.FISCAL_YEAR,\n" +
                            "id.FISCAL_YEAR,\n" +
                            "MAX(m.JOIN_DATE) AS JOIN_DATE,\n" +
                            "MAX(id.MEM_TYPE) AS MEM_TYPE,\n" +
                            "MAX(s.SLIP_NUM) AS SLIP_NUM,\n" +
                            "MAX(p.L_NAME) AS L_NAME,\n" +
                            "MAX(p.F_NAME) AS F_NAME,\n" +
                            "MAX(s.SUBLEASED_TO) AS SUBLEASED_TO,\n" +
                            "MAX(m.address) AS address,\n" +
                            "MAX(m.city) AS city,\n" +
                            "MAX(m.state) AS state,\n" +
                            "MAX(m.zip) AS zip \n" +
                            "from membership_id id\n" +
                            "left join membership m on id.MS_ID=m.MS_ID\n" +
                            "left join slip s on id.MS_ID=s.MS_ID \n" +
                            "left join person p on m.P_ID=p.P_ID \n" +
                            "left join money mo on id.MS_ID=mo.MS_ID \n" +
                            "left join payment pa on mo.MONEY_ID=pa.MONEY_ID \n" +
                            "where id.FISCAL_YEAR=" + year + "\n" +
                            "and mo.FISCAL_YEAR=" + year + "\n" +
                            "and id.RENEW=true\n" +
                            "and mo.DUES > 0\n" +
                            "and mo.INITIATION = 0 \n" +
                            "and (select exists(select MID from membership_id where FISCAL_YEAR="+(Integer.parseInt(year) -1)+" and MS_ID=(id.MS_ID)))\n" +
                            "and DATE(pa.PAYMENT_DATE) >= '"+year+"-03-01' \n" +
                            "group by id.MEMBERSHIP_ID");
            queryToArrayList(rosters, rs);
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        System.out.println("Creating Roster list for " + year + "...");
        return rosters;
    }

    public static ObservableList<MembershipListDTO> getRosterOfKayakShedOwners(String year) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip \n"
                    + "from slip s \n"
                    + "right join membership m on m.MS_ID=s.MS_ID \n"
                    + "left join membership_id id on m.MS_ID=id.MS_ID\n"
                    + "left join money mo on m.MS_ID=mo.MS_ID \n"
                    + "left join person p on p.MS_ID=m.MS_ID \n"
                    + "where mo.FISCAL_YEAR='"+year+"' and id.fiscal_year='"+year+"' and id.renew=1 and kayak_shed=1 and p.member_type=1 order by membership_id;"));
            queryToArrayList(rosters, rs);
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        System.out.println("Creating Roster list for " + year + "...");
        return rosters;
    }

    public static ObservableList<MembershipListDTO> getRoster(String year, boolean isActive) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip "
                            + "from slip s "
                            + "right join membership m on m.MS_ID=s.MS_ID "
                            + "left join membership_id id on m.MS_ID=id.MS_ID "
                            + "left join person p on p.MS_ID=m.MS_ID "
                            + "where id.FISCAL_YEAR='" + year + "' and p.MEMBER_TYPE=1 and id.RENEW=" + isActive + " order by membership_id"));
            queryToArrayList(rosters, rs);
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        System.out.println("Creating Roster list for " + year + "...");
        return rosters;
    }

    public static ObservableList<MembershipListDTO> getRosterOfAll(String year) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip "
                            + "from slip s "
                            + "right join membership m on m.MS_ID=s.MS_ID "
                            + "left join membership_id id on m.MS_ID=id.MS_ID "
                            + "left join person p on p.MS_ID=m.MS_ID "
                            + "where id.FISCAL_YEAR='" + year + "' and p.MEMBER_TYPE=1 order by membership_id"));
            queryToArrayList(rosters, rs);
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        System.out.println("Creating Roster list for " + year + "...");
        return rosters;
    }

    public static ObservableList<MembershipListDTO> getRosterOfSlipOwners(String year) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip "
                    + "from slip s "
                    + "inner join membership m on s.ms_id=m.ms_id "
                    + "left join membership_id id on m.MS_ID=id.MS_ID "
                    + "left join person p on p.MS_ID=m.MS_ID "
                    + "where p.MEMBER_TYPE=1 and FISCAL_YEAR="+ HalyardPaths.getYear()));
            queryToArrayList(rosters, rs);
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        System.out.println("Creating Roster list for " + year + "...");
        return rosters;
    }

    public static ObservableList<MembershipListDTO> getRosterOfSubleasedSlips() {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip from slip s "
                    + "inner join membership m on s.ms_id=m.ms_id "
                    + "left join membership_id id on m.MS_ID=id.MS_ID "
                    + "left join person p on p.MS_ID=s.subleased_to "
                    + "where subleased_to IS NOT NULL and p.MEMBER_TYPE=1 and FISCAL_YEAR="+ HalyardPaths.getYear()));
            queryToArrayList(rosters, rs);
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        return rosters;
    }

    public static MembershipListDTO getMembershipList(int ms_id, String year) {
        MembershipListDTO thisMembership = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,"
                    + "id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,"
                    + "m.zip from slip s right join membership m on m.MS_ID=s.MS_ID left join membership_id "
                    + "id on m.MS_ID=id.MS_ID left join person p on p.MS_ID=m.MS_ID where id.FISCAL_YEAR='" + year + "' "
                    + "and m.ms_id=" + ms_id));
            while (rs.next()) {
                thisMembership = new MembershipListDTO(
                        rs.getInt("MS_ID"),
                        rs.getInt("P_ID"),
                        rs.getInt("MEMBERSHIP_ID"),
                        rs.getString("JOIN_DATE"),
                        rs.getString("MEM_TYPE"),
                        rs.getString("SLIP_NUM"),
                        rs.getString("L_NAME"),
                        rs.getString("F_NAME"),
                        rs.getInt("SUBLEASED_TO"),
                        rs.getString("ADDRESS"),
                        rs.getString("CITY"),
                        rs.getString("STATE"),
                        rs.getString("ZIP"),
                        rs.getString("FISCAL_YEAR"));
                }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        return thisMembership;
    }

    public static MembershipListDTO getMembershipFromList(int ms_id, String year) {
        System.out.println("Pulling membership for ms_id=" + ms_id + " for the year year");
        MembershipListDTO thisMembership = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,"
                    + "id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,"
                    + "m.zip from slip s right join membership m on m.MS_ID=s.MS_ID left join membership_id "
                    + "id on m.MS_ID=id.MS_ID left join person p on p.MS_ID=m.MS_ID where id.FISCAL_YEAR='" + year + "' "
                    + "and p.MEMBER_TYPE=1 and m.ms_id=" + ms_id));
            while (rs.next()) {
                thisMembership = new MembershipListDTO(
                        rs.getInt("MS_ID"),
                        rs.getInt("P_ID"),
                        rs.getInt("MEMBERSHIP_ID"),
                        rs.getString("JOIN_DATE"),
                        rs.getString("MEM_TYPE"),
                        rs.getString("SLIP_NUM"),
                        rs.getString("L_NAME"),
                        rs.getString("F_NAME"),
                        rs.getInt("SUBLEASED_TO"),
                        rs.getString("ADDRESS"),
                        rs.getString("CITY"),
                        rs.getString("STATE"),
                        rs.getString("ZIP"),
                        rs.getString("FISCAL_YEAR"));
                }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        return thisMembership;
    }

    public static MembershipListDTO getMembershipFromListWithoutMembershipId(int ms_id) {
        MembershipListDTO thisMembership = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "Select m.MS_ID,m.P_ID,m.JOIN_DATE,s.SLIP_NUM,p.L_NAME,"
                    + "p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip from slip s right join "
                    + "membership m on m.MS_ID=s.MS_ID left join person p on p.MS_ID=m.MS_ID where m.ms_id=" + ms_id));
            while (rs.next()) {
                thisMembership = new MembershipListDTO(
                        rs.getInt("MS_ID"),
                        rs.getInt("P_ID"),
                        0,
                        rs.getString("JOIN_DATE"),
                        null,
                        rs.getString("SLIP_NUM"),
                        rs.getString("L_NAME"),
                        rs.getString("F_NAME"),
                        rs.getInt("SUBLEASED_TO"),
                        rs.getString("ADDRESS"),
                        rs.getString("CITY"),
                        rs.getString("STATE"),
                        rs.getString("ZIP"),
                        "No Year"
                        );
                }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        return thisMembership;
    }

    public static ObservableList<MembershipListDTO> getSlipRoster(String year) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip "
                            + "from slip s inner join membership m on s.ms_id=m.ms_id inner join membership_id id on id.ms_id=m.ms_id "
                            + "inner join person p on p.p_id=m.p_id where id.fiscal_year="+year));
            queryToArrayList(rosters, rs);
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        return rosters;
    }



    public static ObservableList<MembershipListDTO> getWaitListRoster(String waitlist) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip "
                    + "from waitlist w "
                    + "inner join membership m on w.ms_id=m.ms_id "
                    + "left join membership_id id on m.MS_ID=id.MS_ID "
                    + "left join person p on p.MS_ID=m.MS_ID "
                    + "left join slip s on s.MS_ID=m.MS_ID "
                    + "where " + waitlist + "=true and id.fiscal_year='" + HalyardPaths.getYear() + "' and p.MEMBER_TYPE=1"));
            queryToArrayList(rosters, rs);
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        return rosters;
    }

    public static ObservableList<MembershipListDTO> getNewMemberRoster(String year) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "select id.membership_id, id.FISCAL_YEAR, m.JOIN_DATE, id.MEM_TYPE, m.ADDRESS, "
                    + "m.CITY, m.state,m.zip, m.p_id, p.l_name, p.f_name,m.MS_ID from membership m "
                    + "inner join person p on m.p_id=p.p_id "
                    + "inner join membership_id id on id.ms_id=m.ms_id "
                    + "where YEAR(JOIN_DATE)='" + year + "' and id.FISCAL_YEAR='" + year + "' group by m.MS_ID;"));
            queryToArrayListConstant(rosters, rs);
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        System.out.println("Creating New Member Roster list for " + year + "...");
        return rosters;
    }



    public static ObservableList<MembershipListDTO> getFullNewMemberRoster(String year) {
        int lastYear = Integer.parseInt(year) - 1;
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip\n"
                    + "from membership_id id left join membership m on m.MS_ID=id.MS_ID\n"
                    + "left join person p on p.P_ID=m.P_ID left join slip s on s.MS_ID=m.MS_ID\n"
                    + "where id.FISCAL_YEAR='"+ year +"' \n"
                    + "and YEAR(m.JOIN_DATE) < "+ year +" \n"
                    + "and id.MEMBERSHIP_ID > (\n"
                    + "select membership_id from membership_id id\n"
                    + "where FISCAL_YEAR=' "+year+ "' \n"
                    + "and MS_ID=(\n"
                    + "select MS_ID from membership_id   where FISCAL_YEAR='" + lastYear + "' and membership_id=(\n"
                    + "select max(membership_id) from membership_id where FISCAL_YEAR='" + lastYear + "' and membership_id < 500 and id.renew=1))); "));
            queryToArrayList(rosters, rs);
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        return rosters;
    }

    public static ObservableList<MembershipListDTO> getReturnMembers(int year) { // and those who lost their membership number
        int lastYear = year - 1;
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "SELECT m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip\n" +
                            "FROM membership_id id\n" +
                            "LEFT JOIN membership m on id.MS_ID=m.MS_ID\n" +
                            "LEFT JOIN person p on p.P_ID=m.P_ID \n" +
                            "LEFT JOIN slip s on s.MS_ID=m.MS_ID\n" +
                            "WHERE FISCAL_YEAR="+year+"\n" +
                            "and id.MEMBERSHIP_ID > \n" +
                            "(\n" +
                            "  select MEMBERSHIP_ID from membership_id where FISCAL_YEAR="+year+" and MS_ID=(\n" +
                            "     select MS_ID \n" +
                            "     from membership_id \n" +
                            "     where MEMBERSHIP_ID=(\n" +
                            "        select max(membership_id) \n" +
                            "        from membership_id where FISCAL_YEAR="+lastYear+" and membership_id < 500 and renew=1\n" +
                            "        ) \n" +
                            "     and FISCAL_YEAR="+lastYear+"\n" +
                            "  )\n" +
                            ")\n" +
                            "and id.MEMBERSHIP_ID < 500\n" +
                            "and YEAR(m.JOIN_DATE)!="+year+" \n" +
                            "and (SELECT NOT EXISTS(select mid \n" +
                            "\t\t\t\t\t\tfrom membership_id \n" +
                            "\t\t\t\t\t\twhere FISCAL_YEAR="+lastYear+" \n" +
                            "\t\t\t\t\t\tand RENEW=1 \n" +
                            "\t\t\t\t\t\tand MS_ID=id.MS_ID)\n" +
                            "\t)"));
            queryToArrayList(rosters, rs);
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        return rosters;
    }

    public static MembershipListDTO getMembershipByMembershipId(String membership_id) {  /// for SQL Script Maker
        MembershipListDTO membership = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,"
                    + "id.MEM_TYPE,p.L_NAME,p.F_NAME,m.address,m.city,m.state,m.zip from "
                    + "membership m left join person p on m.P_ID=p.P_ID left join membership_id "
                    + "id on m.MS_ID=id.MS_ID where id.FISCAL_YEAR='2021' and membership_id='" + membership_id + "'"));
            while (rs.next()) {
                membership = new MembershipListDTO(
                        rs.getInt("MS_ID"),
                        rs.getInt("P_ID"),
                        rs.getInt("MEMBERSHIP_ID"),
                        rs.getString("JOIN_DATE"),
                        rs.getString("MEM_TYPE"),
                        "",
                        rs.getString("L_NAME"),
                        rs.getString("F_NAME"),
                        0,
                        rs.getString("ADDRESS"),
                        rs.getString("CITY"),
                        rs.getString("STATE"),
                        rs.getString("ZIP"),
                        rs.getString("FISCAL_YEAR"));
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        return membership;
    }

    public static ObservableList<MembershipListDTO> getBoatOwners(int boat_id) {
        ObservableList<MembershipListDTO> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,p.L_NAME,p.F_NAME,m.address,m.city,m.state,m.zip "
                            + "from boat_owner bo "
                            + "left join membership m on bo.MS_ID=m.MS_ID "
                            + "left join membership_id id on m.MS_ID=id.MS_ID "
                            + "left join person p on m.P_ID=p.P_ID "
                            + "where boat_id='"+ boat_id +"' and id.FISCAL_YEAR='" + HalyardPaths.getYear() + "'"));
            queryToArrayListConstant(rosters, rs);
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve the owners of this boat","See below for details");
        }
        return rosters;
    }

    /// may be a duplicate from above
    public static ObservableList<MembershipListDTO> getBoatOwnerRoster(int boat_id) {
        ObservableList<MembershipListDTO> boatOwners = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Halyard.console.setRegexColor(
                    "select * from boat_owner bo left join membership m on "
                    + "bo.MS_ID=m.MS_ID left join membership_id id on m.MS_ID=id.MS_ID "
                    + "left join person p on m.P_ID=p.P_ID where BOAT_ID="+boat_id+" and id.FISCAL_YEAR='" + HalyardPaths.getYear() + "'"));
            queryToArrayListConstant(boatOwners, rs);
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select list of boat owners","See below for details");
        }
        return boatOwners;
    }

    /////////////////// OBJECT PACKERS /////////////////////////////

    private static void queryToArrayListConstant(ObservableList<MembershipListDTO> rosters, ResultSet rs) throws SQLException {
        while (rs.next()) {
            rosters.add(new MembershipListDTO(
                    rs.getInt("MS_ID"),
                    rs.getInt("P_ID"),
                    rs.getInt("MEMBERSHIP_ID"),
                    rs.getString("JOIN_DATE"),
                    rs.getString("MEM_TYPE"),
                    "",
                    rs.getString("L_NAME"),
                    rs.getString("F_NAME"),
                    0,
                    rs.getString("ADDRESS"),
                    rs.getString("CITY"),
                    rs.getString("STATE"),
                    rs.getString("ZIP"),
                    rs.getString("FISCAL_YEAR")));
        }
    }

    private static void queryToArrayList(ObservableList<MembershipListDTO> rosters, ResultSet rs) throws SQLException {
        while (rs.next()) {
            rosters.add(new MembershipListDTO(
                    rs.getInt("MS_ID"),
                    rs.getInt("P_ID"),
                    rs.getInt("MEMBERSHIP_ID"),
                    rs.getString("JOIN_DATE"),
                    rs.getString("MEM_TYPE"),
                    rs.getString("SLIP_NUM"),
                    rs.getString("L_NAME"),
                    rs.getString("F_NAME"),
                    rs.getInt("SUBLEASED_TO"),
                    rs.getString("ADDRESS"),
                    rs.getString("CITY"),
                    rs.getString("STATE"),
                    rs.getString("ZIP"),
                    rs.getString("FISCAL_YEAR")));
        }
    }
}
