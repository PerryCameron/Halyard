package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.structures.Object_Board;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlBoard {
    // select p.P_ID, p.MS_ID, o.O_ID, p.F_NAME, p.L_NAME, o.OFF_YEAR, o.BOARD_YEAR, o.OFF_TYPE  from person p inner join officer o on p.p_id = o.p_id where o.off_year='2020';
    public static ObservableList<Object_Board> getBoard(String currentYear) {  //p_id
        ObservableList<Object_Board> thisBoardMember = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt
                    .executeQuery(Main.console.setRegexColor("select p.P_ID, p.MS_ID, o.O_ID, p.F_NAME, p.L_NAME, o.OFF_YEAR, o.BOARD_YEAR, o.OFF_TYPE  from person p inner join officer o on p.p_id = o.p_id where o.off_year='" + currentYear + "';"));

            while (rs.next()) {
                thisBoardMember.add(new Object_Board(
                        rs.getInt("P_ID"),
                        rs.getInt("MS_ID"),
                        rs.getInt("O_ID"),
                        rs.getString("F_NAME"),
                        rs.getString("L_NAME"),
                         rs.getString("OFF_YEAR"),
                        rs.getString("BOARD_YEAR"),
                        rs.getString("OFF_TYPE")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoardMember;
    }
}
