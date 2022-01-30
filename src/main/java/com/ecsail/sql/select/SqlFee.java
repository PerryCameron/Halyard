package com.ecsail.sql.select;


import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.structures.FeeDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlFee {

        public static ArrayList<FeeDTO> getFeesFromYear(String Year) {  //p_id
            ArrayList<FeeDTO> thisAwards = new ArrayList<>();
            try {
                Statement stmt = ConnectDatabase.sqlConnection.createStatement();
                ResultSet rs = stmt
                        .executeQuery(Main.console.setRegexColor("select * from fee where FEE_YEAR=2022"));
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return thisAwards;
        }
    
}
