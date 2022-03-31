package com.ecsail.main;

import com.ecsail.sql.select.SqlStats;
import com.ecsail.structures.StatsDTO;

import java.util.ArrayList;

public class Statistics {
	public ArrayList<StatsDTO> stats;
	
	public Statistics() {
		this.stats = SqlStats.getStatistics();
	}

	
	public void reload() {
		stats.clear();
		stats = SqlStats.getStatistics();
	}


	public ArrayList<StatsDTO> getStats() {
		return stats;
	}
}
