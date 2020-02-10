package com.ensim.flipper.area;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.ensim.flipper.indicators.Indicator;

public class AreaNeedPrev extends Area 
{
	private static final long serialVersionUID = 1L;
	
	private Area[] prevAreaList;	

	/**
	 * 
	 * @param jpanel : The JPanel where the area is drawn
	 * @param phrase : The name of the area
	 * @param points : The points to add to the total score 
	 * @param xPoints : The array of X coordinates
	 * @param yPoints : The array of Y coordinates
	 * @param nbPoints : The total number of points (coordinates)
	 * @param posX : x top coordinate of the polygon
	 * @param posY : y top coordinate of the polygon
	 * @param prevAreaList : All the previous areas that are needed to enter this area correctly 
	 * @param imgActive : image displayed where the area is when it's active
	 * @param imgInactive : image displayed where the area is when it's inactive
	 */
	public AreaNeedPrev(JPanel jpanel, String phrase, int points, int[] xPoints, int[] yPoints, int nbPoints, int posX, int posY, ImageIcon imgActive, ImageIcon imgInactive, Indicator indicator, Area... prevAreaList)
	{
		super(jpanel, phrase, points, xPoints, yPoints, nbPoints, posX, posY, imgActive, imgInactive, indicator);
		this.prevAreaList = prevAreaList;
	}

	public boolean canPerformAction(Area prevArea)
	{
		if(prevArea != null)
		{
			for(int i=0;i<this.prevAreaList.length;i++)
			{
				if(prevArea.equals(this.prevAreaList[i]))
				{
					return true;
				}
			}
		}
			
		return false;
	}
}
