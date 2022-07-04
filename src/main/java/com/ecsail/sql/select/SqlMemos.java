package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Halyard;
import com.ecsail.structures.MemoDTO;
import com.ecsail.structures.Memo2DTO;
import com.ecsail.structures.PaidDuesDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlMemos {
    public static ObservableList<MemoDTO> getMemos(int ms_id) {
        String query = "SELECT * FROM memo";
        if(ms_id != 0)
            query +=  " WHERE ms_id='" + ms_id + "'";
        ObservableList<MemoDTO> theseMemos = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            while (rs.next()) {
                theseMemos.add(new MemoDTO( // why do I keep gettin a nullpointer exception here?
                        rs.getInt("MEMO_ID"),
                        rs.getInt("MS_ID"),
                        rs.getString("MEMO_DATE"),
                        rs.getString("MEMO"),
                        rs.getInt("MONEY_ID"),
                        rs.getString("CATEGORY")));
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseMemos;
    }

    public static ObservableList<Memo2DTO> getAllMemosForTabNotes(String year, String category) {
        ObservableList<Memo2DTO> theseMemos = FXCollections.observableArrayList();
        String query = "select * from memo "
                + "left join membership_id id on memo.ms_id=id.ms_id "
                + "where year(memo_date)='"+year+"' and id.fiscal_year='"+year+"' and memo.CATEGORY IN("+category+")";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            while (rs.next()) {
                theseMemos.add(new Memo2DTO( // why do I keep gettin a nullpointer exception here?
                        rs.getString("MEMBERSHIP_ID"),
                        rs.getInt("MEMO_ID"),
                        rs.getInt("MS_ID"),
                        rs.getString("MEMO_DATE"),
                        rs.getString("MEMO"),
                        rs.getInt("MONEY_ID"),
                        rs.getString("CATEGORY")));
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseMemos;
    }

    public static MemoDTO getMemos(PaidDuesDTO dues, String category) {
        String query = "select * from memo where money_id=" + dues.getMoney_id() + " and category='" + category + "'";
        MemoDTO thisMemo = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            rs.next();
                thisMemo = new MemoDTO( // why do I keep gettin a nullpointer exception here?
                        rs.getInt("MEMO_ID"),
                        rs.getInt("MS_ID"),
                        rs.getString("MEMO_DATE"),
                        rs.getString("MEMO"),
                        rs.getInt("MONEY_ID"),
                        rs.getString("CATEGORY"));
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisMemo;
    }
}
