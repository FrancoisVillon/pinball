package com.ensim.flipper.area;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.ensim.flipper.indicators.Indicator;

public class AreaBonusPoints extends Area
{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param jpanel : The JPanel where the area is drawn
	 * @param name : The name of the area
	 * @param points : The points to add to the total score 
	 * @param xPoints : The array of X coordinates
	 * @param yPoints : The array of Y coordinates
	 * @param posX : x top coordinate of the graphical area
	 * @param posY : y top coordinate of the graphical area
	 * @param imgActive : image displayed where the area is when it's active
	 * @param imgInactive : image displayed where the area is when it's inactive
	 */
	public AreaBonusPoints(JPanel jpanel, String name, int points, int[] xPoints, int[] yPoints, int posX, int posY, ImageIcon imgActive, ImageIcon imgInactive, Indicator indicator)
	{
		super(jpanel, name, points, xPoints, yPoints, posX, posY, imgActive, imgInactive, indicator, null);
	}

	/**
	 * Check if the area can be activated
	 * @param prev_area
	 * @return always true
	 */
	public boolean canPerformAction(Area prevArea)
	{
		return true;
	}
}
