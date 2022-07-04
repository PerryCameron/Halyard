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
        String query = "select * from awards where P_ID=" + p.getP_id();
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                thisAwards.add(new AwardDTO(
                        rs.getInt("AWARD_ID"),
                        rs.getInt("P_ID"),
                        rs.getString("AWARD_YEAR"),
                        rs.getString("AWARD_TYPE")));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisAwards;
    }

    public static ArrayList<AwardDTO> getAwards() {
        ArrayList<AwardDTO> theseAwards = new ArrayList<>();
        String query = "select * from awards";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                theseAwards.add(new AwardDTO(
                        rs.getInt("AWARD_ID"),
                        rs.getInt("P_ID"),
                        rs.getString("AWARD_YEAR"),
                        rs.getString("AWARD_TYPE")
                        ));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseAwards;
    }
}
