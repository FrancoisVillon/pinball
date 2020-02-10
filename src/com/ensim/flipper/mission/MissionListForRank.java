package com.ensim.flipper.mission;

import java.util.ArrayList;

public class MissionListForRank
{
	ArrayList<Mission> listMissions = new ArrayList<>();
	String str;

	public MissionListForRank(ArrayList<Mission> listMissions, String str)
	{
		this.listMissions = listMissions;
		this.str = str;
	}
	
	public String toString()
	{
		String s = str + "\n";
		
		for(Mission mission : this.listMissions)
		{
			s += mission.getInstructions() + "\n";
		}
		
		return s + "\n";
	}
}
