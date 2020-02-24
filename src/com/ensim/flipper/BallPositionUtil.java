package com.ensim.flipper;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class BallPositionUtil
{
	private static final int RED_VALUE = 222;
	private static final int GREEN_VALUE = 120;
	private static final int BLUE_VALUE = 66;
	private static final int RED_THRESHOLD = 45;
	private static final int GREEN_THRESHOLD = 60;
	private static final int BLUE_THRESHOLD = 35;	
	private static final Point INVISIBLE_POINT = new Point(-1, -1);
	
	private static Point ballPosition = INVISIBLE_POINT;
	
	/**
	 * Perform actions depending on the ball's position 
	 * @param image : The image get by the webcam
	 */
	public static void searchNplay(BufferedImage image)
	{
		ballPosition = searchByPrevious(image, ballPosition);

		if(isBallVisible(ballPosition))
		{
			Main.drawBallPos(ballPosition.x, ballPosition.y);

			if (Main.game.verify(ballPosition))
			{
				Main.refreshLabel();
				Main.game.addPoints(Game.missionManager.updateMission(Main.game.getPrevArea()));
			}
		}
	}

	/**
	 * Wait for a new game until the LAUNCH_RAMP area is reached
	 * @param image : The image get by the webcam
	 */
	public static void waitForNewGame(BufferedImage image)
	{
		ballPosition = searchByPrevious(image, ballPosition);

		if(isBallVisible(ballPosition))
		{
			Main.drawBallPos(ballPosition.x, ballPosition.y);
			
			if (Game.LAUNCH_RAMP.contains(ballPosition))
			{
				Main.game.resetGame();
			}
		}
	}
	
	/**
	 * Look for the ball using a spiral research around the previous ball's position
	 * @param img : The image get by the webcam
	 * @param prevPoint : The previous ball's position
	 * @return Current ball's position
	 */
	public static Point searchByPrevious(BufferedImage img, Point prevPoint)
	{
		if(img != null)
		{
			if(isBallVisible(prevPoint))
			{
				for (int i = 0; i < 200; i++)
				{
					for (int difX = -1; difX < 2; difX++)
					{
						for (int difY = -1; difY < 2; difY++)
						{
							if (isColorOK(img.getRGB(prevPoint.x + difX, prevPoint.y + difY)))
							{
								prevPoint.translate(difX, difX);
								return prevPoint;
							}
						}
					}
				}
			}

			return searchFirst(img);
		}
		
		else
		{
			return INVISIBLE_POINT;
		}
	}

	/**
	 * 
	 * @param img : The image get by the webcam
	 * @return Ball's current position
	 */
	private static Point searchFirst(BufferedImage img)
	{
		for (int i = 0; i < img.getWidth(); i++)
		{
			for (int j = 0; j < img.getHeight(); j++)
			{
				if (isColorOK(img.getRGB(i, j)))
				{
					return new Point(i, j);
				}
			}
		}

		return INVISIBLE_POINT;
	}

	/**
	 * Check the color of one pixel
	 * @param color : The color of the pixel
	 * @return true if the color of the pixel is near to ball's registered color, false otherwise
	 */
	private static boolean isColorOK(int color)
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
	
	/**
	 * Check if ball's coordinates are on the image
	 * @param point
	 * @return true if ball's coordinates are on the image, false otherwise
	 */
	private static boolean isBallVisible(Point point)
	{
		return point.getX() > 0 && point.getY() > 0;
	}
}
