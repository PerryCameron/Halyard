package com.ecsail.main;

import java.util.ArrayList;

import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlSelect;
import com.ecsail.structures.Object_Stats;

public class Statistics {
	public ArrayList<Object_Stats> stats;
	
	public Statistics() {
		this.stats = SqlSelect.getStatistics();
	}
	
	public ArrayList<Object_Stats> populateStats() {
		ArrayList<Object_Stats> stats = SqlSelect.getStatistics();
		return stats;
	}
	
	public void reload() {
		stats.clear();
		stats = SqlSelect.getStatistics();
	}

	public static void updateStats() {
			int statId = 0;
			int selectedYear = 2000;
			Object_Stats stats;
			SqlDelete.deleteStatistics();
			int numberOfYears = Integer.parseInt(Paths.getYear()) - selectedYear + 1;
			for (int i = 0; i < numberOfYears; i++) {
				stats = new Object_Stats(selectedYear);
				stats.setStatId(statId);
				stats.refreshStatsForYear();  // built in function for the object to update itself.
				SqlInsert.addStatRecord(stats);
				System.out.println("Adding " + selectedYear);
				selectedYear++;
				statId++;
			}
	}

	public ArrayList<Object_Stats> getStats() {
		return stats;
	}
}
