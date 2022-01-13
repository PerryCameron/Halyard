package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
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
            ResultSet rs;
            rs = stmt.executeQuery(query + ";");
            while (rs.next()) {
                theseMemos.add(new MemoDTO( // why do I keep gettin a nullpointer exception here?
                        rs.getInt("MEMO_ID"),
                        rs.getInt("MS_ID"),
                        rs.getString("MEMO_DATE"),
                        rs.getString("MEMO"),
                        rs.getInt("MONEY_ID"),
                        rs.getString("CATEGORY")));
            }
            return theseMemos;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseMemos;
    }

    public static ObservableList<Memo2DTO> getAllMemosForTabNotes(String year, String category) {
        ObservableList<Memo2DTO> theseMemos = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("select * from memo \n"
                    + "left join membership_id id on memo.ms_id=id.ms_id\n"
                    + "where year(memo_date)='"+year+"' and id.fiscal_year='"+year+"' and memo.CATEGORY IN("+category+")");
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
            return theseMemos;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseMemos;
    }

    public static MemoDTO getMemos(PaidDuesDTO dues, String category) {
        String query = "select * from memo where money_id=" + dues.getMoney_id() + " and category='" + category + "'";
        System.out.println("select * from memo where money_id=" + dues.getMoney_id());
        MemoDTO thisMemo = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(query + ";");
            while (rs.next()) {
                thisMemo = new MemoDTO( // why do I keep gettin a nullpointer exception here?
                        rs.getInt("MEMO_ID"),
                        rs.getInt("MS_ID"),
                        rs.getString("MEMO_DATE"),
                        rs.getString("MEMO"),
                        rs.getInt("MONEY_ID"),
                        rs.getString("CATEGORY"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        //System.out.println(thisMemo);
        return thisMemo;
    }
}
