package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.HalyardPaths;
import com.ecsail.main.Main;
import com.ecsail.pdf.directory.Object_Sportsmen;
import com.ecsail.sql.SqlExists;
import com.ecsail.structures.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlSelect {


	public static ArrayList<Object_Sportsmen> getSportsManAwardNames() {
		ArrayList<Object_Sportsmen> theseOfficers = new ArrayList<>();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select AWARD_YEAR,F_NAME,L_Name from awards a left join person p on a.P_ID=p.P_ID"));
			while (rs.next()) {
				theseOfficers.add(new Object_Sportsmen(
						rs.getString("AWARD_YEAR"),
						rs.getString("F_NAME"),
						rs.getString("L_NAME") 
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return theseOfficers;
	}
	
	public static ObservableList<Object_WorkCredit> getWorkCredits() {
		ObservableList<Object_WorkCredit> thisWorkCredit = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from work_credit;"));
			while (rs.next()) {
				thisWorkCredit.add(new Object_WorkCredit(
						rs.getInt("MONEY_ID"), 
						rs.getInt("MS_ID"), 
						rs.getInt("RACING"),
						rs.getInt("HARBOR"),
						rs.getInt("SOCIAL"),
						rs.getInt("OTHER")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisWorkCredit;
	}

	// select p.P_ID, p.MS_ID, o.O_ID, p.F_NAME, p.L_NAME, o.OFF_YEAR, o.BOARD_YEAR, o.OFF_TYPE  from person p inner join officer o on p.p_id = o.p_id where o.off_year='2020';
	public static ObservableList<Object_Board> getBoard(String currentYear) {  //p_id
		ObservableList<Object_Board> thisBoardMember = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs = stmt
					.executeQuery(Main.console.setRegexColor("select p.P_ID, p.MS_ID, o.O_ID, p.F_NAME, p.L_NAME, o.OFF_YEAR, o.BOARD_YEAR, o.OFF_TYPE  from person p inner join officer o on p.p_id = o.p_id where o.off_year='" + currentYear + "';"));

			while (rs.next()) {
				thisBoardMember.add(new Object_Board(
						rs.getInt("P_ID"),
						rs.getInt("MS_ID"), 
						rs.getInt("O_ID"), 
						rs.getString("F_NAME"),
						rs.getString("L_NAME"),
 						rs.getString("OFF_YEAR"),
						rs.getString("BOARD_YEAR"),
						rs.getString("OFF_TYPE")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return thisBoardMember;
	}

	// "Boolean wantAll" decides whether we want all or just active people
	// inactive people exist and are still part of an account, but they are inactive for that account

	public static ArrayList<Object_Stats> getStatistics() {
		ArrayList<Object_Stats> stats = new ArrayList<>();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
		    ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from stats"));
		while (rs.next()) {
			stats.add(new Object_Stats(
					rs.getInt("STAT_ID"),
					rs.getInt("FISCAL_YEAR"),
					rs.getInt("ACTIVE_MEMBERSHIPS"),
					rs.getInt("NON_RENEW"),
					rs.getInt("RETURN_MEMBERS"),
					rs.getInt("NEW_MEMBERS"),
					rs.getInt("SECONDARY_MEMBERS"),
					rs.getInt("DEPENDANTS"),
					rs.getInt("NUMBER_OF_BOATS"),
					rs.getInt("FAMILY"),
					rs.getInt("REGULAR"),
					rs.getInt("SOCIAL"),
					rs.getInt("LAKEASSOCIATES"),
					rs.getInt("LIFEMEMBERS"),
					rs.getInt("RACEFELLOWS"),
					rs.getInt("STUDENT"),
					rs.getDouble("DEPOSITS"),
					rs.getDouble("INIATION")));
		}
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return stats;
	}


    public static int getCount(String table, String column) {  // example-> "email","email_id"
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
