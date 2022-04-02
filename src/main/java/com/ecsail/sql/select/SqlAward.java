package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Halyard;
import com.ecsail.structures.AwardDTO;
import com.ecsail.structures.PersonDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlAward {
    public static ObservableList<AwardDTO> getAwards(PersonDTO p) {  //p_id
        ObservableList<AwardDTO> thisAwards = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt
                    .executeQuery(Halyard.console.setRegexColor("select * from awards where P_ID=" + p.getP_id()));
            while (rs.next()) {
                thisAwards.add(new AwardDTO(
                        rs.getInt("AWARD_ID"),
                        rs.getInt("P_ID"),
                        rs.getString("AWARD_YEAR"),
                        rs.getString("AWARD_TYPE")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisAwards;
    }

    public static ArrayList<AwardDTO> getAwards() {
        ArrayList<AwardDTO> theseAwards = new ArrayList<>();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Halyard.console.setRegexColor("select * from awards"));
            while (rs.next()) {
                theseAwards.add(new AwardDTO(
                        rs.getInt("AWARD_ID"),
                        rs.getInt("P_ID"),
                        rs.getString("AWARD_YEAR"),
                        rs.getString("AWARD_TYPE")
                        ));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseAwards;
    }
}
