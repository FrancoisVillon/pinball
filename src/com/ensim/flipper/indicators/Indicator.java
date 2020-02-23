package com.ensim.flipper.indicators;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.ensim.flipper.Main;

public class Indicator
{
	private final JPanel jpanel;
	private final ImageIcon imgActive;
	private final ImageIcon imgInactive;
	public final int posX;
	public final int posY;
	
	public ImageIcon currentImg;
	private boolean active;
	
	/**
	 * @param jpanel : JPanel that contains the indicator
	 * @param active : Image of the activate indicator
	 * @param inactive : Image of the inactive indicator
	 * @param posX : X coordinate of the indicator
	 * @param posY : Y coordinate of the indicator
	 */
	public Indicator(JPanel jpanel, ImageIcon active, ImageIcon inactive, int posX, int posY)
	{
		this.jpanel = jpanel;
		this.posX = posX;
		this.posY = posY;
		this.imgActive = active;
		this.imgInactive = inactive;
		this.currentImg = inactive;
	}
	
	public void setActive(boolean active)
	{
		this.active = active;
		this.currentImg = active ? this.imgActive : this.imgInactive;
		Main.repaint();
	}
	
	public boolean isActive()
	{
		return this.active;
	}
	
	public boolean isInPanel(JPanel jpanel)
	{
		return this.jpanel.equals(jpanel);
	}
}
