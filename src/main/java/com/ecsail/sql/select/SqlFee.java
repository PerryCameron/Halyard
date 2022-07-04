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
                ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
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
                Halyard.getConnect().closeResultSet(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return thisAwards;
        }

    public static ArrayList<FeeDTO> getAllFees() {  //p_id
        ArrayList<FeeDTO> thisAwards = new ArrayList<>();
        String query = "select * from fee";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
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
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thisAwards;
    }

    public static ObservableList<FeeDTO> getAllFeesByDescription(String description) {  //p_id
        ObservableList<FeeDTO> feeDTOS = FXCollections.observableArrayList();
        String query = "select * from fee where description='" + description + "'";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
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
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feeDTOS;
    }
}
