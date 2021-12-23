package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.structures.Object_WorkCredit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlWorkCredit {
    public static ObservableList<Object_WorkCredit> getWorkCredits() {
        ObservableList<Object_WorkCredit> thisWorkCredit = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor("select * from work_credit;"));
            while (rs.next()) {
                thisWorkCredit.add(new Object_WorkCredit(
                        rs.getInt("MONEY_ID"),
                        rs.getInt("MS_ID"),
                        rs.getInt("RACING"),
                        rs.getInt("HARBOR"),
                        rs.getInt("SOCIAL"),
                        rs.getInt("OTHER")
                        ));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisWorkCredit;
    }
}
