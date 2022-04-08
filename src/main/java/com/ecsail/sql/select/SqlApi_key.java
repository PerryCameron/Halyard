package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Halyard;
import com.ecsail.structures.ApiKeyDTO;
import com.ecsail.structures.PersonDTO;
import com.ecsail.structures.PhoneDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlApi_key {
    public static ApiKeyDTO getApiKeyByName(String name) {  // if p_id = 0 then select all
        ApiKeyDTO thisApi = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Halyard.console.setRegexColor("select * from api_key  where NAME='" + name + "'"));
            while (rs.next()) {
                thisApi = new ApiKeyDTO(rs.getInt("API_ID"), rs.getString("NAME"), rs.getString("APIKEY"),
                        rs.getString("ts"));
            }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisApi;
    }
}
