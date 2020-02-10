package com.ensim.flipper.area;

import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.ensim.flipper.Main;
import com.ensim.flipper.indicators.Indicator;

public class Area extends Polygon
{
	protected static final long serialVersionUID = 1L;
	
	protected boolean active = false;	
	private JPanel jpanel;
	private int points = 1;
	public ImageIcon img;
	public final String name;
	public final int x, y;
	private final ImageIcon imgActive;
	private final ImageIcon imgInactive;
	public final Indicator indicator;

	/**
	 * 
	 * @param jpanel : The JPanel where the area is drawn
	 * @param name : The name of the area
	 * @param points : The points to add to the total score 
	 * @param xPoints : The array of X coordinates
	 * @param yPoints : The array of Y coordinates
	 * @param nbPoints : The total number of points (coordinates)
	 * @param posX : x top coordinate of the polygon
	 * @param posY : y top coordinate of the polygon
	 * @param imgActive : image displayed where the area is when it's active
	 * @param imgInactive : image displayed where the area is when it's inactive
	 * @param indicator : the indicator linked to the area
	 */
	public Area(JPanel jpanel, String name, int points, int[] xPoints, int[] yPoints, int nbPoints, int posX, int posY, ImageIcon imgActive, ImageIcon imgInactive, Indicator indicator)
	{
		super(xPoints, yPoints, nbPoints);
		this.jpanel = jpanel;
		this.img = imgInactive;
		this.points = points;
		this.x = posX;
		this.y = posY;
		this.name = name;
		this.imgActive = imgActive;
		this.imgInactive = imgInactive;
		this.indicator = indicator;
	}

	public void draw(Graphics g)
	{
		g.drawOval(xpoints[0], ypoints[0], 10, 10);
		for (int i = 1; i < xpoints.length; i++)
		{
			g.drawOval(xpoints[i], ypoints[i], 10, 10);
			g.drawLine(xpoints[i - 1], ypoints[i - 1], xpoints[i], ypoints[i]);
		}
	}
	
	public String toString()
	{
		return "[" + this.name + "] " + super.toString();
	}

	public boolean canPerformAction(Area prev_area)
	{
		return !this.equals(prev_area);
	}
	
	public void activate()
	{
		this.setActive(true);
	}

	public void setActive(boolean active)
	{
		this.active = active;
		this.img = this.active ? this.imgActive : this.imgInactive;
		Main.repaint();
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public boolean isInPanel(JPanel jpanel)
	{
		return this.jpanel.equals(jpanel);
	}
	
	public int getPoints()
	{
		return this.points;
	}

	public String getPhraseScore(int multiplier)
	{
		return this.npoints > 0 ? points * multiplier + " Points" : "";
	}
}
