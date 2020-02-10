package com.ensim.flipper.area;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.ensim.flipper.indicators.Indicator;

public class ReEntry extends Area
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
	 * @param posX : x top coordinate of the polygon
	 * @param posY : y top coordinate of the polygon
	 * @param indicator : the indicator linked to the target
	 */
	public ReEntry(JPanel jpanel, int points, int[] xPoints, int[] yPoints, int nbPoints, int posX, int posY, Indicator indicator)
	{
		super(jpanel, "Re-entry", points, xPoints, yPoints, nbPoints, posX, posY, ACTIVE, INACTIVE, indicator);
	}
	
	public void activate()
	{
		this.setActive(!isActive());
	}
}
