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
	 * @param name : Name of the mission (is displayed on screen)
	 * @param scoreEnd : Points earned by finishing the mission
	 */
	public Mission(String name, int scoreEnd)
	{
		this(name, new ArrayList<>(), scoreEnd);
	}

	/**
	 * @param name : Name of the mission (is displayed on screen)
	 * @param list : List of different steps of the mission
	 * @param scoreEnd : Points earned by finishing the mission
	 */
	private Mission(String name, ArrayList<Step> list, int scoreEnd)
	{
		this.name = name;
		this.scoreEnd = scoreEnd;
		this.steps = list;
	}

	/**
	 * 
	 * @param instructions : Instructions to reach the goal of the step
	 * @param score : Points earned by completing the step
	 * @param areas : List of areas needed to complete the step
	 */
	public void addStep(String instructions, int score, Area... areas)
	{
		this.steps.add(new Step(instructions, score, areas));
	}

	/**
	 * Validate a step
	 * @param area
	 * @return Points earned by completing a step 
	 */
	public int validate(Area area)
	{
		if (this.steps.get(stepsIndex).containsArea(area))
		{
			this.stepsIndex++;
			
			/*
			 * Check if the mission is completed
			 */
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
	
	/**
	 * @return The current step of the current mission
	 */
	public Step getCurrentStep()
	{
		return this.steps.get(stepsIndex);
	}

	/**
	 * @return The informations on the score (displayed by the "missionPointsLabel" label)
	 */
	public String getScoreInfo()
	{
		if (stepsIndex == 0)
		{
			return "";
		}
		return (steps.get(stepsIndex - 1).points * Main.game.multiplier) + " points";
	}
	
	/**
	 * @return The score granted when the mission is completed
	 */
	public int getEndScore()
	{
		return this.scoreEnd;
	}

	/**
	 * @return The informations on the score at the end of the mission (displayed by the "missionPointsLabel" label)
	 */
	public String getEndMissionScoreInfo()
	{
		return "Mission done : " + (scoreEnd * Main.game.multiplier) + " points";
	}

	/**
	 * @return The instructions to complete the step
	 */
	public String getInstructions()
	{
		return steps.get(stepsIndex).instructions;
	}
	
	/**
	 * @return The mission's name
	 */
	public String getName()
	{
		return this.name;
	}
}
