package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.HalyardPaths;
import com.ecsail.main.Main;
import com.ecsail.structures.Object_MembershipList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlMembershipList {
    /// not a pure SQL FUNCTION was having difficulties narrowing it down pure SQL.
    public static int getNumberOfReturningMembershipsForYear(int fiscalYear) {
        ObservableList<Object_MembershipList> rosters = getReturnMembers(fiscalYear);
        return rosters.size();
    }

    public static ObservableList<Object_MembershipList> getRosterOfKayakRackOwners(String year) {
        ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
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



    public static ObservableList<Object_MembershipList> getRosterOfKayakShedOwners(String year) {
        ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
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

    public static ObservableList<Object_MembershipList> getRoster(String year, boolean isActive) {
        ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
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

    public static ObservableList<Object_MembershipList> getRosterOfAll(String year) {
        ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
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

    public static ObservableList<Object_MembershipList> getRosterOfSlipOwners(String year) {
        ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
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

    public static ObservableList<Object_MembershipList> getRosterOfSubleasedSlips() {
        ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
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

    public static Object_MembershipList getMembershipList(int ms_id, String year) {
        Object_MembershipList thisMembership = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
                    "Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,"
                    + "id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,"
                    + "m.zip from slip s right join membership m on m.MS_ID=s.MS_ID left join membership_id "
                    + "id on m.MS_ID=id.MS_ID left join person p on p.MS_ID=m.MS_ID where id.FISCAL_YEAR='" + year + "' "
                    + "and m.ms_id=" + ms_id));
            while (rs.next()) {
                thisMembership = new Object_MembershipList(
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

    public static Object_MembershipList getMembershipFromList(int ms_id, String year) {
        System.out.println("Pulling membership for ms_id=" + ms_id + " for the year year");
        Object_MembershipList thisMembership = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
                    "Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,"
                    + "id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,"
                    + "m.zip from slip s right join membership m on m.MS_ID=s.MS_ID left join membership_id "
                    + "id on m.MS_ID=id.MS_ID left join person p on p.MS_ID=m.MS_ID where id.FISCAL_YEAR='" + year + "' "
                    + "and p.MEMBER_TYPE=1 and m.ms_id=" + ms_id));
            while (rs.next()) {
                thisMembership = new Object_MembershipList(
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

    public static Object_MembershipList getMembershipFromListWithoutMembershipId(int ms_id) {
        Object_MembershipList thisMembership = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
                    "Select m.MS_ID,m.P_ID,m.JOIN_DATE,s.SLIP_NUM,p.L_NAME,"
                    + "p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip from slip s right join "
                    + "membership m on m.MS_ID=s.MS_ID left join person p on p.MS_ID=m.MS_ID where m.ms_id=" + ms_id));
            while (rs.next()) {
                thisMembership = new Object_MembershipList(
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

    public static ObservableList<Object_MembershipList> getSlipRoster(String year) {
        ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
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

    public static ObservableList<Object_MembershipList> getWaitListRoster(String waitlist) {
        ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
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

    public static ObservableList<Object_MembershipList> getNewMemberRoster(String year) {
        ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor(
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



    public static ObservableList<Object_MembershipList> getFullNewMemberRoster(String year) {
        int lastYear = Integer.parseInt(year) - 1;
        ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor(
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

    public static ObservableList<Object_MembershipList> getReturnMembers(int fiscalYear) { // and those who lost their membership number
        int lastYear = fiscalYear - 1;
        ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
                    "select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip "
                    + "from membership_id id left join membership m on m.MS_ID=id.MS_ID "
                    + "left join person p on p.P_ID=m.P_ID left join slip s on s.MS_ID=m.MS_ID "
                    + "where id.FISCAL_YEAR='" + fiscalYear + "' and YEAR(m.JOIN_DATE) < "+ fiscalYear +" and id.MEMBERSHIP_ID > ("
                    + "select membership_id from membership_id where FISCAL_YEAR='" + fiscalYear + "' and MS_ID=("
                    + "select MS_ID from membership_id where FISCAL_YEAR='" + lastYear + "' and membership_id=("
                    + "select max(membership_id) from membership_id where FISCAL_YEAR='" + lastYear + "' and membership_id < 500 and id.renew=1))) "
                    + " and id.MEMBERSHIP_ID < 500;"));
            queryToArrayList(rosters, rs);
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to select roster","See below for details");
        }
        return rosters;
    }

    public static Object_MembershipList getMembershipByMembershipId(String membership_id) {  /// for SQL Script Maker
        Object_MembershipList membership = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor(
                    "select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,"
                    + "id.MEM_TYPE,p.L_NAME,p.F_NAME,m.address,m.city,m.state,m.zip from "
                    + "membership m left join person p on m.P_ID=p.P_ID left join membership_id "
                    + "id on m.MS_ID=id.MS_ID where id.FISCAL_YEAR='2021' and membership_id='" + membership_id + "'"));
            while (rs.next()) {
                membership = new Object_MembershipList(
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

    public static ObservableList<Object_MembershipList> getBoatOwners(int boat_id) {
        ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor(
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
    public static ObservableList<Object_MembershipList> getBoatOwnerRoster(int boat_id) {
        ObservableList<Object_MembershipList> boatOwners = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
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

    private static void queryToArrayListConstant(ObservableList<Object_MembershipList> rosters, ResultSet rs) throws SQLException {
        while (rs.next()) {
            rosters.add(new Object_MembershipList(
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

    private static void queryToArrayList(ObservableList<Object_MembershipList> rosters, ResultSet rs) throws SQLException {
        while (rs.next()) {
            rosters.add(new Object_MembershipList(
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
