package com.ensim.flipper.mission;

import java.util.ArrayList;

public class MissionListForRank
{
	ArrayList<Mission> listMissions = new ArrayList<>();
	private String rank;

	/**
	 * @param listMissions : List of missions
	 * @param rank : Rank (only useful for debugging)
	 */
	public MissionListForRank(ArrayList<Mission> listMissions, String rank)
	{
		this.listMissions = listMissions;
		this.rank = rank;
	}
	
	public String toString()
	{
		String s = rank + "\n";
		
		for(Mission mission : this.listMissions)
		{
			s += mission.getInstructions() + "\n";
		}
		
		return s + "\n";
	}
}
