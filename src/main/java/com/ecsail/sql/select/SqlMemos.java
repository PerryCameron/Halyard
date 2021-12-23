package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.structures.Object_Memo;
import com.ecsail.structures.Object_Memo2;
import com.ecsail.structures.Object_PaidDues;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlMemos {
    public static ObservableList<Object_Memo> getMemos(int ms_id) {
        String query = "SELECT * FROM memo";
        if(ms_id != 0)
            query +=  " WHERE ms_id='" + ms_id + "'";
        ObservableList<Object_Memo> theseMemos = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(query + ";");
            while (rs.next()) {
                theseMemos.add(new Object_Memo( // why do I keep gettin a nullpointer exception here?
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

    public static ObservableList<Object_Memo2> getAllMemosForTabNotes(String year, String category) {
        ObservableList<Object_Memo2> theseMemos = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("select * from memo \n"
                    + "left join membership_id id on memo.ms_id=id.ms_id\n"
                    + "where year(memo_date)='"+year+"' and id.fiscal_year='"+year+"' and memo.CATEGORY IN("+category+")");
            while (rs.next()) {
                theseMemos.add(new Object_Memo2( // why do I keep gettin a nullpointer exception here?
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

    public static Object_Memo getMemos(Object_PaidDues dues, String category) {
        String query = "select * from memo where money_id=" + dues.getMoney_id() + " and category='" + category + "'";
        System.out.println("select * from memo where money_id=" + dues.getMoney_id());
        Object_Memo thisMemo = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(query + ";");
            while (rs.next()) {
                thisMemo = new Object_Memo( // why do I keep gettin a nullpointer exception here?
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
