package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Halyard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlSelect {

	public static int getNextAvailablePrimaryKey(String table, String column) {  // example-> "email","email_id"
		int result = 0;
		String query = "SELECT " + column + " FROM " + table + " ORDER BY " + column + " DESC LIMIT 1";
		try {
			ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
			rs.next();
			result =  rs.getInt(column);
			Halyard.getConnect().closeResultSet(rs);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return result + 1;
	}

}
