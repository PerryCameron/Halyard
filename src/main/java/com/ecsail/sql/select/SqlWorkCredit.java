package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.Halyard;
import com.ecsail.structures.WorkCreditDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlWorkCredit {
    public static ObservableList<WorkCreditDTO> getWorkCredits() {
        ObservableList<WorkCreditDTO> thisWorkCredit = FXCollections.observableArrayList();
        String query = "SELECT * FROM work_credit";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
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
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisWorkCredit;
    }
}
