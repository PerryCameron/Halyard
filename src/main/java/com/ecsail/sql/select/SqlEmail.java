package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.HalyardPaths;
import com.ecsail.main.Main;
import com.ecsail.structures.Object_Email;
import com.ecsail.structures.Object_Email_Information;
import com.ecsail.structures.Object_Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlEmail {
    public static ObservableList<Object_Email_Information> getEmailInfo() {
        ObservableList<Object_Email_Information> thisEmailInfo = FXCollections.observableArrayList();

        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
                    "select id.MEMBERSHIP_ID,m.JOIN_DATE,p.L_NAME,p.F_NAME,EMAIL,PRIMARY_USE "
                    + "from email e "
                    + "inner join person p ON p.P_ID=e.P_ID "
                    + "inner join membership m ON m.ms_id=p.ms_id "
                    + "inner join membership_id id ON id.ms_id=m.ms_id "
                    + "where id.fiscal_year='" + HalyardPaths.getYear()
                    + "' and id.renew=true"

                    + " order by id.MEMBERSHIP_ID"));

            while (rs.next()) {
                thisEmailInfo.add(new Object_Email_Information(rs.getInt("MEMBERSHIP_ID"), rs.getString("JOIN_DATE"),
                        rs.getString("L_NAME"), rs.getString("F_NAME"), rs.getString("EMAIL"),
                        rs.getBoolean("PRIMARY_USE")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisEmailInfo;
    }

    public static ObservableList<Object_Email> getEmail(int p_id) {
        String query = "SELECT * FROM email";
        if(p_id != 0)
            query += " WHERE p_id='" + p_id + "'";
        ObservableList<Object_Email> email = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query + ";");
            while (rs.next()) {
                email.add(new Object_Email(
                        rs.getInt("EMAIL_ID")
                        ,rs.getInt("P_ID")
                        ,rs.getBoolean("PRIMARY_USE")
                        ,rs.getString("EMAIL")
                        ,rs.getBoolean("EMAIL_LISTED")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return email;
    }

    public static String getEmail(Object_Person person) {
        //System.out.println(person);
        Object_Email email = null;
        String returnEmail = "";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from email where P_ID=" + person.getP_id() +" and PRIMARY_USE=true");
            while (rs.next()) {
                email = new Object_Email(
                        rs.getInt("EMAIL_ID")
                        ,rs.getInt("P_ID")
                        ,rs.getBoolean("PRIMARY_USE")
                        ,rs.getString("EMAIL")
                        ,rs.getBoolean("EMAIL_LISTED"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        if(email.getEmail() != null) {
            returnEmail = email.getEmail();
        }
        return returnEmail;
    }
}
