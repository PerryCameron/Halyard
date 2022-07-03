package com.ecsail.sql.select;


import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Halyard;
import com.ecsail.structures.FeeDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlFee {

        public static ArrayList<FeeDTO> getFeesFromYear(String year) {  //p_id
            ArrayList<FeeDTO> thisAwards = new ArrayList<>();
            String query = "select * from fee where FEE_YEAR=" + year;
            try {
                Statement stmt = ConnectDatabase.sqlConnection.createStatement();
                ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt, query);
                while (rs.next()) {
                    thisAwards.add(new FeeDTO(
                            rs.getInt("FEE_ID"),
                            rs.getString("FIELD_NAME"),
                            rs.getBigDecimal("FIELD_VALUE"),
                            rs.getInt("FIELD_QTY"),
                            rs.getInt("FEE_YEAR"),
                            rs.getString("DESCRIPTION")
                    ));
                }
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return thisAwards;
        }

    public static ArrayList<FeeDTO> getAllFees() {  //p_id
        ArrayList<FeeDTO> thisAwards = new ArrayList<>();
        String query = "select * from fee";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt, query);
            while (rs.next()) {
                thisAwards.add(new FeeDTO(
                        rs.getInt("FEE_ID"),
                        rs.getString("FIELD_NAME"),
                        rs.getBigDecimal("FIELD_VALUE"),
                        rs.getInt("FIELD_QTY"),
                        rs.getInt("FEE_YEAR"),
                        rs.getString("DESCRIPTION")
                ));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thisAwards;
    }

    public static ObservableList<FeeDTO> getAllFeesByDescription(String description) {  //p_id
        ObservableList<FeeDTO> feeDTOS = FXCollections.observableArrayList();
        String query = "select * from fee where description='" + description + "'";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt, query);
            while (rs.next()) {
                feeDTOS.add(new FeeDTO(
                        rs.getInt("FEE_ID"),
                        rs.getString("FIELD_NAME"),
                        rs.getBigDecimal("FIELD_VALUE"),
                        rs.getInt("FIELD_QTY"),
                        rs.getInt("FEE_YEAR"),
                        rs.getString("DESCRIPTION")
                ));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feeDTOS;
    }
    
}
