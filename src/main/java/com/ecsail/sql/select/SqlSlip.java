package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.pdf.directory.Object_SlipInfo;
import com.ecsail.structures.SlipDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlSlip {
    public static ObservableList<SlipDTO> getSlips() {
        ObservableList<SlipDTO> slips = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from slip;");
            while (rs.next()) {
                slips.add(new SlipDTO(rs.getInt("SLIP_ID")
                        , rs.getInt("MS_ID")
                        , rs.getString("SLIP_NUM"),
                        rs.getInt("SUBLEASED_TO")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return slips;
    }

    public static SlipDTO getSlip(int ms_id) {
        SlipDTO thisSlip = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from slip WHERE ms_id='" + ms_id + "'");
            while (rs.next()) {
                thisSlip = new SlipDTO(rs.getInt("SLIP_ID")
                        , rs.getInt("MS_ID")
                        , rs.getString("SLIP_NUM"),
                        rs.getInt("SUBLEASED_TO"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisSlip;
    }

    public static SlipDTO getSubleasedSlip(int ms_id) {
        SlipDTO thisSlip = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from slip WHERE subleased_to='" + ms_id + "'");
            while (rs.next()) {
                thisSlip = new SlipDTO(
                        rs.getInt("SLIP_ID"),
                        rs.getInt("MS_ID"),
                        rs.getString("SLIP_NUM"),
                        rs.getInt("SUBLEASED_TO"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisSlip;
    }

    public static ArrayList<Object_SlipInfo> getSlipsForDock(String dock) {
        ArrayList<Object_SlipInfo> thisSlipInfo = new ArrayList<>();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor("select SLIP_NUM,SUBLEASED_TO,F_NAME,L_NAME  from slip s \n"
                    + "left join membership m on s.MS_ID=m.MS_ID \n"
                    + "left join person p on m.P_ID=p.P_ID \n"
                    + "where slip_num LIKE '" +dock + "%'"));
            while (rs.next()) {
                thisSlipInfo.add(new Object_SlipInfo(
                        rs.getString("SLIP_NUM"),
                        rs.getInt("SUBLEASED_TO"),
                        rs.getString("F_NAME"),
                        rs.getString("L_NAME")
                        ));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisSlipInfo;
    }
}
