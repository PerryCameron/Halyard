package com.ecsail.main;

import java.util.ArrayList;

import com.ecsail.gui.dialogues.Dialogue_StatisticsStatusBar;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlStats;
import com.ecsail.structures.StatsDTO;

public class Statistics {
	public ArrayList<StatsDTO> stats;
	
	public Statistics() {
		this.stats = SqlStats.getStatistics();
	}
	
	public ArrayList<StatsDTO> populateStats() {
		ArrayList<StatsDTO> stats = SqlStats.getStatistics();
		return stats;
	}
	
	public void reload() {
		stats.clear();
		stats = SqlStats.getStatistics();
	}

	public static void updateStats(Dialogue_StatisticsStatusBar statBar) {
			int statId = 0;
			int selectedYear = 1970;
			StatsDTO stats;
			SqlDelete.deleteStatistics();
			int numberOfYears = Integer.parseInt(HalyardPaths.getYear()) - selectedYear + 1;
			for (int i = 0; i < numberOfYears; i++) {
				stats = new StatsDTO(selectedYear);
				stats.setStatId(statId);
				stats.refreshStatsForYear();  // built in function for the object to update itself.
				SqlInsert.addStatRecord(stats);
				System.out.println("Calculating statistics for " + selectedYear);
				selectedYear++;
				statId++;
			}
	}

	public ArrayList<StatsDTO> getStats() {
		return stats;
	}
}
