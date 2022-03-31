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


	public ArrayList<StatsDTO> getStats() {
		return stats;
	}
}
