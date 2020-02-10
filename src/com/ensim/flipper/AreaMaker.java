package com.ensim.flipper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class AreaMaker
{
	private static final List<Point> COORDS = new ArrayList<>();
	
	public static void addPoint(Point p)
	{
		COORDS.add(p);
	}
	
	public static void displayPoints()
	{
		System.out.print("[AreaMaker] Coords X : ");
		
		for(Point p : COORDS)
		{
			System.out.print(p.x + ", ");
		}
		
		System.out.print("\n[AreaMaker] Coords Y : ");
		
		for(Point p : COORDS)
		{
			System.out.print(p.y + ", ");
		}
		
		System.out.println("");
	}
	
	public static void clearAllPoints()
	{
		while(COORDS.size() > 0)
		{
			COORDS.remove(0);
		}
	}
	
	public static void drawPoints(Graphics g)
	{
		if(g != null)
		{
			g.setColor(new Color(0, 0, 255));
			
			g.drawOval(COORDS.get(0).x, COORDS.get(0).y, 10, 10);
			for (int i = 1; i < COORDS.size(); i++)
			{
				g.drawOval(COORDS.get(i).x, COORDS.get(i).y, 10, 10);
				g.drawLine(COORDS.get(i-1).x, COORDS.get(i-1).y, COORDS.get(i).x, COORDS.get(i).y);
			}
		}
	}
}
