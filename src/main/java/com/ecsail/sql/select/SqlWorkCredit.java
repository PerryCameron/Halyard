package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Halyard;
import com.ecsail.structures.WorkCreditDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlWorkCredit {
    public static ObservableList<WorkCreditDTO> getWorkCredits() {
        ObservableList<WorkCreditDTO> thisWorkCredit = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Halyard.console.setRegexColor("select * from work_credit;"));
            while (rs.next()) {
                thisWorkCredit.add(new WorkCreditDTO(
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
