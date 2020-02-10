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
	
	public IndicatorLeveled(JPanel jpanel, int posX, int posY, int maxLevel, ImageIcon... imgs)
	{
		this.jpanel = jpanel;
		this.posX = posX;
		this.posY = posY;
		this.imgs = imgs;
		this.maxLevel = maxLevel;
		this.currentImg = imgs[0];
	}
	
	public void setLevel(int level)
	{
		this.level = level < 0 ? 0 : (level > this.maxLevel ? this.maxLevel : level);
		this.currentImg = this.imgs[level];
		Main.repaint();
	}
	
	public int getLevel()
	{
		return this.level;
	}
	
	public boolean isInPanel(JPanel jpanel)
	{
		return this.jpanel.equals(jpanel);
	}
}
