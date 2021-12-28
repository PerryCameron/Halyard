package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.structures.Object_Membership;
import com.ecsail.structures.Object_Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlPerson {
    public static ObservableList<Object_Person> getPeople(int ms_id) {
        String query = "SELECT * FROM person WHERE ms_id= '" + ms_id + "'";
        ObservableList<Object_Person> thesepeople = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor(query + ";"));
        while (rs.next()) {
            if(rs.getBoolean("IS_ACTIVE")) {  // only add active people
            thesepeople.add(new Object_Person(
                    rs.getInt("P_ID"),
                    rs.getInt("MS_ID"),
                    rs.getInt("MEMBER_TYPE"),
                    rs.getString("F_NAME"),
                    rs.getString("L_NAME"),
                    rs.getString("BIRTHDAY"),
                    rs.getString("OCCUPATION"),
                    rs.getString("BUISNESS"),
                    rs.getBoolean("IS_ACTIVE"),
                    rs.getString("NICK_NAME")));
            }
        }
        stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thesepeople;
    }

    public static ArrayList<Object_Person> getDependants(Object_Membership m) {
        String query = "SELECT * FROM person WHERE ms_id= '" + m.getMsid() + "' and MEMBER_TYPE=3";
        ArrayList<Object_Person> thesepeople = new ArrayList<>();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor(query + ";"));
        while (rs.next()) {
            if(rs.getBoolean("IS_ACTIVE")) {  // only add active people
            thesepeople.add(new Object_Person(
                    rs.getInt("P_ID"),
                    rs.getInt("MS_ID"),
                    rs.getInt("MEMBER_TYPE"),
                    rs.getString("F_NAME"),
                    rs.getString("L_NAME"),
                    rs.getString("BIRTHDAY"),
                    rs.getString("OCCUPATION"),
                    rs.getString("BUISNESS"),
                    rs.getBoolean("IS_ACTIVE"),
                    rs.getString("NICK_NAME")));
            }
        }
        stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thesepeople;
    }

    public static ObservableList<Object_Person> getPeople() {
        String query = "SELECT * FROM person";
        ObservableList<Object_Person> thesepeople = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor(query + ";"));
        while (rs.next()) {
            thesepeople.add(new Object_Person(
                    rs.getInt("P_ID"),
                    rs.getInt("MS_ID"),
                    rs.getInt("MEMBER_TYPE"),
                    rs.getString("F_NAME"),
                    rs.getString("L_NAME"),
                    rs.getString("BIRTHDAY"),
                    rs.getString("OCCUPATION"),
                    rs.getString("BUISNESS"),
                    rs.getBoolean("IS_ACTIVE"),
                    rs.getString("NICK_NAME")));
        }
        stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thesepeople;
    }

    public static Object_Person getPersonByPid(int pid) {
        Object_Person person = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt
                    .executeQuery(Main.console.setRegexColor("select * from person WHERE p_id= '" + pid + "';"));

            while (rs.next()) {
                person = (new Object_Person(rs.getInt("P_ID"), rs.getInt("MS_ID"), rs.getInt("MEMBER_TYPE"),
                        rs.getString("F_NAME"), rs.getString("L_NAME"), rs.getString("BIRTHDAY"),
                        rs.getString("OCCUPATION"), rs.getString("BUISNESS"), rs.getBoolean("IS_ACTIVE"),rs.getString("NICK_NAME")));
            }
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        // System.out.println(thesepeople.toString());
        return person;
    }

    public static Object_Person getPerson(int ms_id, int member_type) {
        Object_Person person = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt
                    .executeQuery(Main.console.setRegexColor("select * from person where MS_ID=" + ms_id + " and MEMBER_TYPE=" + member_type));

            while (rs.next()) {
                person = (new Object_Person(rs.getInt("P_ID"), rs.getInt("MS_ID"), rs.getInt("MEMBER_TYPE"),
                        rs.getString("F_NAME"), rs.getString("L_NAME"), rs.getString("BIRTHDAY"),
                        rs.getString("OCCUPATION"), rs.getString("BUISNESS"), rs.getBoolean("IS_ACTIVE"),rs.getString("NICK_NAME")));
            }
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        // System.out.println(thesepeople.toString());
        return person;
    }

    public static Object_Person getPersonFromMembershipID(String membershipId, String year) {
        Object_Person person = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt
                    .executeQuery(Main.console.setRegexColor("select * from person where MS_ID=(select ms_id from membership_id where MEMBERSHIP_ID="+membershipId+" and FISCAL_YEAR="+year+") and MEMBER_TYPE=1"));

            while (rs.next()) {
                person = (new Object_Person(rs.getInt("P_ID"), rs.getInt("MS_ID"), rs.getInt("MEMBER_TYPE"),
                        rs.getString("F_NAME"), rs.getString("L_NAME"), rs.getString("BIRTHDAY"),
                        rs.getString("OCCUPATION"), rs.getString("BUISNESS"), rs.getBoolean("IS_ACTIVE"),rs.getString("NICK_NAME")));
            }
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        // System.out.println(thesepeople.toString());
        return person;
    }

    public static int getCount()  {  // gives the last memo_id number
        int number = 0;
        Statement stmt;
        try {
            stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select * from person ORDER BY p_id DESC LIMIT 1"));
            rs.next();
            number = rs.getInt("P_ID");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }
}