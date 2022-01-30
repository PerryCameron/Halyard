package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlSelect {

	public static int getNextAvailablePrimaryKey(String table, String column) {  // example-> "email","email_id"
		int result = 0;
		Statement stmt;
		try {
			stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + table + " ORDER BY " + column + " DESC LIMIT 1");
			rs.next();
			result =  rs.getInt(column);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return result;
	}

}
