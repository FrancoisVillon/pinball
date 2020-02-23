package com.ensim.flipper;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.ensim.flipper.area.Area;
import com.ensim.flipper.indicators.Indicator;
import com.ensim.flipper.indicators.IndicatorLeveled;

public class PinballBasePanel extends JPanel
{
	protected static final long serialVersionUID = 1L;
	
	private BufferedImage background;
	@SuppressWarnings("unused")
	private int x = 0, y = 0;

	/**
	 * Draw all areas and indicators of the panel
	 */
	public void paintComponent(Graphics g)
	{
		if (g != null)
		{
			super.paintComponent(g);
			
			if (background != null)
			{
				g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		}
		
		try
		{
			for (Area area : Game.AREAS)
			{
				if (g != null && area.isInPanel(this) && area.x != -1 && area.img != null)
				{
					g.drawImage(area.img.getImage(), area.x, area.y, area.img.getIconWidth(), area.img.getIconHeight(), area.img.getImageObserver());
				}
			}
			
			for(Indicator indicator : Game.INDICATORS)
			{
				if (g != null && indicator.isInPanel(this) && indicator.currentImg != null)
				{
					g.drawImage(indicator.currentImg.getImage(), indicator.posX, indicator.posY,indicator.currentImg.getIconWidth(), indicator.currentImg.getIconHeight(), indicator.currentImg.getImageObserver());
				}
			}
			
			for(IndicatorLeveled indicator : Game.INDICATORS_LEVELED)
			{
				if (g != null && indicator.isInPanel(this) && indicator.currentImg != null)
				{
					g.drawImage(indicator.currentImg.getImage(), indicator.posX, indicator.posY, indicator.currentImg.getIconWidth(), indicator.currentImg.getIconHeight(), indicator.currentImg.getImageObserver());
				}
			}
		}
		
		catch (Exception e) //The try-catch may be useless, but still here just in case
		{
			e.printStackTrace();
		}
	}

	/**
	 * Set a background image to the panel
	 * @param image : The image set as background
	 */
	public void setBackroundImg(BufferedImage image)
	{
		this.background = image;
	}

	/**
	 * Set the X and Y coordinates (only used by tracePanel)
	 * @param x : The X coordinate
	 * @param y : The Y coordinate
	 */
	public void setXY(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}