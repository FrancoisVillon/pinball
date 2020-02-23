package com.ensim.flipper.indicators;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.ensim.flipper.Main;

public class IndicatorLeveled
{
	private final JPanel jpanel;
	public final int posX;
	public final int posY;
	private final ImageIcon[] imgs;
	private final int maxLevel;
	
	public ImageIcon currentImg;
	private int level = 0;	
	
	
	/**
	 * @param jpanel : JPanel that contains the indicator
	 * @param posX : X coordinate of the indicator
	 * @param posY : Y coordinate of the indicator
	 * @param maxLevel : The maximum level the indicator can reach
	 * @param imgs : List of images (one for each level of the indicator)
	 */
	public IndicatorLeveled(JPanel jpanel, int posX, int posY, int maxLevel, ImageIcon... imgs)
	{
		this.jpanel = jpanel;
		this.posX = posX;
		this.posY = posY;
		this.imgs = imgs;
		this.maxLevel = maxLevel;
		this.currentImg = imgs[0];
	}
	
	/**
	 * Set the level of the indicator, with security tests
	 * @param level : the level that is attributed to the indicator
	 */
	public void setLevel(int level)
	{
		this.level = level < 0 ? 0 : (level > this.maxLevel ? this.maxLevel : level);
		this.currentImg = this.imgs[level];
		Main.repaint();
	}
	
	/**
	 * @return The indicator's current level
	 */
	public int getLevel()
	{
		return this.level;
	}
	
	public boolean isInPanel(JPanel jpanel)
	{
		return this.jpanel.equals(jpanel);
	}
}
