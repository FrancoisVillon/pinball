package com.ensim.flipper.mission;

import com.ensim.flipper.area.Area;

public class Step
{
	Area[] areas;
	String instructions;
	int points;

	/**
	 * 
	 * @param instructions : The instructions that will be displayed on screen
	 * @param points : Points earned by finishing the step
	 * @param areas : List of areas that have to be reached to finish the step (only one of them needs to be reached)
	 */
	public Step(String instructions, int points, Area... areas)
	{
		this.areas = areas;
		this.instructions = instructions;
		this.points = points;
	}
	
	/**
	 * @param areaChecked : The area where the ball is
	 * @return true if the ball is in one the areas needed by the step, false otherwise
	 */
	public boolean containsArea(Area areaChecked)
	{
		for(Area area : this.areas)
		{
			if(area.equals(areaChecked))
			{
				this.activateIndicators(false);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * (De)activates all indicators corresponding to the areas needed by the step
	 * @param activate : true --> activate, false --> deactivate indicators
	 */
	public void activateIndicators(boolean activate)
	{
		for(Area area : areas)
		{
			if(area.indicator != null)
			{
				area.indicator.setActive(activate);
			}
		}
	}
}
