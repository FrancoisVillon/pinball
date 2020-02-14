package com.ensim.flipper;

import java.awt.image.BufferedImage;

import com.ensim.flipper.area.Area;

public class BallPositionUtil
{
	private static final int RED_VALUE = 222;
	private static final int GREEN_VALUE = 120;
	private static final int BLUE_VALUE = 66;
	private static final int RED_THRESHOLD = 45;
	private static final int GREEN_THRESHOLD = 60;
	private static final int BLUE_THRESHOLD = 35;
	
	private static int[] loc = new int[2];
	
	public static void searchNplay(BufferedImage image)
	{
		loc = searchByPrevious(image, loc[0], loc[1]);

		if (loc[0] == -1 && loc[1] == -1)
		{
			// System.out.println("Not found");
		}
		else
		{
			//System.out.println("[BallPositionUtil] Ball at (x : " + loc[0] + ", y : " + loc[1]);
			Main.drawPosBalle(loc[0], loc[1]);

			if (Main.game.verify(loc[0], loc[1]))
			{// TODO finir
				Main.refreshLabel();

				Main.game.addPoints(Game.missionManager.actionMission(loc[0], loc[1], Main.game.prev_area));
			}

		}
		
		Main.game.updateTicks();
		
		/**/
		//TODO Area Visualisation
		for(Area area : Game.AREAS)
		{
			area.draw(Main.webcamPanel.getGraphics());
		}
		/**/
	}

	public static void waitPartie(BufferedImage image)
	{
		loc = searchByPrevious(image, loc[0], loc[1]);
		if (loc[0] == -1 && loc[1] == -1)
		{
		}
		else
		{
			Main.drawPosBalle(loc[0], loc[1]);
			if (Game.LAUNCH_RAMP.contains(loc[0], loc[1]))
			{
				Main.game.resetGame();
				/*
				// if (loc[0] < 360 && loc[1] < 360) {
				Main.game.prev_area = Game.LAUNCH_RAMP;
				Main.nbBalle = 3;
				// System.out.println("balles : " + aMyTest.nbBalle);
				System.out.println("[BallPositionUtil] NEW GAME !");
				Main.game.resetGame();
				Main.refreshLabel();
				Game.timeSinceLaunch = System.currentTimeMillis();
				*/
			}
		}
	}
	
	// Recherche balle

	public static int[] searchByPrevious(BufferedImage img, int prevX, int prevY)
	{
		int[] ret = new int[2];

		for (int i = 0; i < 200; i++)
		{
			for (int difX = -1; difX < 2; difX++)
			{
				for (int difY = -1; difY < 2; difY++)
				{
					try
					{
						if (search(img.getRGB(prevX + difX, prevY + difY)))
						{
							ret[0] = prevX + difX;
							ret[1] = prevY + difY;
							return ret;
						}
					}
					catch (Exception e)
					{
					}
				}
			}
		}

		return search_first(img);
	}

	private static int[] search_first(BufferedImage img)
	{
		// 250, g:107, b:0
		int[] ret = new int[2];
		if (img != null)
		{
			for (int i = 0; i < img.getWidth(); i++)
			{
				for (int j = 0; j < img.getHeight(); j++)
				{
					if (search(img.getRGB(i, j)))
					{
						ret[0] = i;
						ret[1] = j;
						return ret;
					}
				}
			}
		}
		ret[0] = -1;
		ret[1] = -1;
		return ret;

	}

	private static boolean search(int color)
	{
		int r = (color & 0x00ff0000) >> 16;
		int g = (color & 0x0000ff00) >> 8;
		int b = (color & 0x000000ff);
		
		if (r > RED_VALUE - RED_THRESHOLD && r < RED_VALUE + RED_THRESHOLD && g > GREEN_VALUE - GREEN_THRESHOLD
				&& g < GREEN_VALUE + GREEN_THRESHOLD && b > BLUE_VALUE - BLUE_THRESHOLD
				&& b < BLUE_VALUE + BLUE_THRESHOLD)
		{
			return true;
		}
		return false;
	}
}
