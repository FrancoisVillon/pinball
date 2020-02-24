package com.ensim.flipper.area;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.ensim.flipper.indicators.Indicator;

public class Target extends Area
{
	private static final long serialVersionUID = 1L;
	
	private static final ImageIcon ACTIVE = new ImageIcon("resources/indicator/target_active.png");
	private static final ImageIcon INACTIVE = new ImageIcon("resources/indicator/target_inactive.png");
	
	/**
	 * 
	 * @param jpanel : The JPanel where the area is drawn
	 * @param name : The name of the target
	 * @param points : The points to add to the total score 
	 * @param xPoints : The array of X coordinates
	 * @param yPoints : The array of Y coordinates
	 * @param nbPoints : The total number of points (coordinates)
	 * @param posX : x top coordinate of the graphical area
	 * @param posY : y top coordinate of the graphical area
	 * @param indicator : the indicator linked to the target
	 */
	public Target(JPanel jpanel, String name, int points, int[] xPoints, int[] yPoints, int posX, int posY, Indicator indicator)
	{
		super(jpanel, name, points, xPoints, yPoints, posX, posY, ACTIVE, INACTIVE, indicator, "target_hit");
	}
}
