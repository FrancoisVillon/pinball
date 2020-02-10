package com.ensim.flipper.mission;

import java.util.ArrayList;

import com.ensim.flipper.Main;
import com.ensim.flipper.area.Area;

public class Mission
{
	private final String name;
	private ArrayList<Step> steps = new ArrayList<>();
	private int scoreEnd = 1200;
	private int stepsIndex = 0;

	/**
	 * 
	 * @param name : Name of the mission (is displayed on screen)
	 * @param scorePreStep : Points earned by starting the mission
	 * @param scoreEnd : Points earned by finishing the mission
	 */
	public Mission(String name, int scorePreStep, int scoreEnd)
	{
		this(name, new ArrayList<>(), scorePreStep, scoreEnd);
	}

	private Mission(String name, ArrayList<Step> list, int scorePreStep, int scoreEnd)
	{
		this.name = name;
		this.scoreEnd = scoreEnd;
		this.steps = list;
	}

	public void addStep(String str, int score, Area... areas)
	{
		this.steps.add(new Step(str, score, areas));
	}

	public int validate(Area area)
	{
		if (this.steps.get(stepsIndex).containsArea(area))
		{
			this.stepsIndex++;
			
			if (this.stepsIndex >= this.steps.size())
			{
				Main.refreshLabel();
				Main.game.setReplayBall(true);
				this.stepsIndex = 0;
				System.out.println("[Mission] Replay ball");
				return this.scoreEnd;
			}
			
			Main.refreshLabel();
			return this.steps.get(stepsIndex - 1).points;
		}
		
		return 0;
	}
	
	public Step getCurrentStep()
	{
		return this.steps.get(stepsIndex);
	}

	public String getScoreInfo()
	{
		if (stepsIndex == 0)
		{
			return "";
		}
		return (steps.get(stepsIndex - 1).points * Main.game.multiplier) + " points";
	}
	
	public int getEndScore()
	{
		return this.scoreEnd;
	}

	public String getEndMissionScoreInfo()
	{
		return "Mission done : " + (scoreEnd * Main.game.multiplier) + " points";
	}

	public String getInstructions()
	{
		return steps.get(stepsIndex).instructions;
	}
	
	public String getName()
	{
		return this.name;
	}
}
