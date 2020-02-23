package com.ensim.flipper;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.ensim.flipper.area.Area;
import com.ensim.flipper.area.AreaBonusPoints;
import com.ensim.flipper.area.AreaNeedPrev;
import com.ensim.flipper.area.ReEntry;
import com.ensim.flipper.area.Target;
import com.ensim.flipper.indicators.Indicator;
import com.ensim.flipper.indicators.IndicatorLeveled;
import com.ensim.flipper.mission.MissionManager;

public class Game
{
	private static final ImageIcon IMG_TIME_WARP = new ImageIcon("resources/indicator/time_warp_inactive.png");
	private static final ImageIcon IMG_TIME_WARP_ACTIVE = new ImageIcon("resources/indicator/time_warp_active.png");
	private static final ImageIcon GATE_CLOSED = new ImageIcon("resources/indicator/gate_closed.png");	
	private static final ImageIcon ARROW_LEFT_TOP = new ImageIcon("resources/indicator/arrow_left_top.png");
	private static final ImageIcon ARROW_LEFT_BOTTOM = new ImageIcon("resources/indicator/arrow_left_bottom.png");
	private static final ImageIcon ARROW_RIGHT_TOP = new ImageIcon("resources/indicator/arrow_right_top.png");
	private static final ImageIcon ARROW_FULL_SHOT = new ImageIcon("resources/indicator/arrow_full_shot.png");
	private static final ImageIcon LIGHT = new ImageIcon("resources/indicator/replay_ball_light.png");
	private static final ImageIcon HYPERSPACE_0 = new ImageIcon("resources/indicator/hyperspace_0.png");
	private static final ImageIcon HYPERSPACE_1 = new ImageIcon("resources/indicator/hyperspace_1.png");
	private static final ImageIcon HYPERSPACE_2 = new ImageIcon("resources/indicator/hyperspace_2.png");
	private static final ImageIcon HYPERSPACE_3 = new ImageIcon("resources/indicator/hyperspace_3.png");
	private static final ImageIcon HYPERSPACE_4 = new ImageIcon("resources/indicator/hyperspace_4.png");
	
	public static final Indicator ARROW_REENTRY = new Indicator(Main.upDisplayPanel, ARROW_LEFT_TOP, null, 1396, 350);
	public static final Indicator ARROW_MISSION = new Indicator(Main.downDisplayPanel, ARROW_LEFT_TOP, null, 400, 275);
	public static final Indicator ARROW_FLAG = new Indicator(Main.downDisplayPanel, ARROW_RIGHT_TOP, null, 1500, 200);
	public static final Indicator ARROW_SHOT_RAMP = new Indicator(Main.downDisplayPanel, ARROW_LEFT_TOP, null, 750, 100);
	public static final Indicator ARROW_SHOT_HOLE = new Indicator(Main.downDisplayPanel, ARROW_FULL_SHOT, null, 750, 100);
	public static final Indicator ARROW_HYPERSPACE = new Indicator(Main.downDisplayPanel, ARROW_RIGHT_TOP, null, 1400, 15);
	public static final Indicator ARROW_TIME_WARP = new Indicator(Main.upDisplayPanel, ARROW_LEFT_BOTTOM, null, 275, 300);
	public static final Indicator ARROW_MULTIPLIER = new Indicator(Main.upDisplayPanel, ARROW_RIGHT_TOP, null, 630, 600);
	public static final Indicator ARROW_HAZARD = new Indicator(Main.upDisplayPanel, ARROW_RIGHT_TOP, null, 1700, 750);
	public static final Indicator LIGHT_REDEPLOY = new Indicator(Main.downDisplayPanel, LIGHT, null, 1600, 850);
	public static final Indicator LIGHT_REPLAYBALL = new Indicator(Main.downDisplayPanel, LIGHT, null, 100, 850);
	public static final Indicator LIGHT_EXTRABALL = new Indicator(Main.downDisplayPanel, LIGHT, null, 930, 850);
	public static final IndicatorLeveled LIGHT_HYPERSPACE = new IndicatorLeveled(Main.downDisplayPanel, 1400, 40, 4, HYPERSPACE_0, HYPERSPACE_1, HYPERSPACE_2, HYPERSPACE_3, HYPERSPACE_4);
	
	public static final Target TOP_MISSION_TARGET = new Target(Main.downDisplayPanel, "Mission Target",  75, new int[] { 765, 765, 790, 790 }, new int[] { 510, 475, 475, 505 }, 220, 40, ARROW_MISSION);
	public static final Target MIDDLE_MISSION_TARGET = new Target(Main.downDisplayPanel, "Mission Target", 75, new int[] { 820, 820, 795, 795 }, new int[] { 510, 475, 475, 505 }, 225, 215, ARROW_MISSION);
	public static final Target BOTTOM_MISSION_TARGET = new Target(Main.downDisplayPanel, "Mission Target", 75, new int[] { 825, 825, 850, 850 }, new int[] { 500, 475, 475, 500 }, 230, 385, ARROW_MISSION);
	
	public static final Target TOP_FLAG_TARGET = new Target(Main.downDisplayPanel, "Flag Target", 75, new int[] { 745, 745, 770, 770 }, new int[] { 220, 260, 260, 220 }, 1620, 35, ARROW_FLAG);
	public static final Target BOTTOM_FLAG_TARGET = new Target(Main.downDisplayPanel, "Flag Target", 75, new int[] { 785, 785, 810, 810 }, new int[] { 220, 260, 260, 220 }, 1620, 205, ARROW_FLAG);

	public static final Target TOP_HAZARD_TARGET = new Target(Main.upDisplayPanel, "Hazard Target", 75, new int[] { 520, 520, 550, 550 }, new int[] { 220, 260, 260, 220 }, 1775, 600, ARROW_HAZARD);
	public static final Target MIDDLE_HAZARD_TARGET = new Target(Main.upDisplayPanel, "Hazard Target", 75, new int[] { 550, 550, 580, 580 }, new int[] { 220, 260, 260, 220 }, 1775, 750, ARROW_HAZARD);
	public static final Target BOTTOM_HAZARD_TARGET = new Target(Main.upDisplayPanel, "Hazard Target", 75, new int[] { 580, 580, 610, 610 }, new int[] { 220, 260, 260, 220 }, 1775, 900, ARROW_HAZARD);

	public static final Area SHOT_RAMP = new Area(Main.downDisplayPanel, "Shot Ramp", 0, new int[] { 729, 696, 659, 637, 631, 638, 660, 679, 696, 671, 664, 674, 692, 718, 752 }, new int[] { 387, 414, 462, 514, 563, 600, 634, 650, 611, 592, 556, 523, 497, 477, 457 }, 715, 20, null, null, ARROW_SHOT_RAMP);
	public static final Area SHOT_SURFACE = new Area(Main.downDisplayPanel, "", 0, new int[] { 711, 722, 766, 881, 912, 932, 697 }, new int[] { 615, 613, 551, 544, 579, 650, 653 }, 780, 160, null, null, ARROW_SHOT_RAMP);
	public static final AreaNeedPrev SHOT_HOLE = new AreaNeedPrev(Main.downDisplayPanel, "Full Shot", 500, new int[] { 930, 960, 975, 970, 950, 930, 925 }, new int[] { 580, 580, 600, 625, 630, 625, 600 }, -1, -1, null, null, ARROW_SHOT_HOLE, SHOT_SURFACE);

	public static final AreaBonusPoints GATE_LANE = new AreaBonusPoints(Main.downDisplayPanel, "Gate", 5, new int[] { 910, 910, 1045, 1090, 1063, 1010 }, new int[] { 190, 164, 168, 260, 280, 210 }, 1800, 700, GATE_CLOSED, null, null);

	public static final Area HYPERSPACE_END = new Area(Main.downDisplayPanel, "Hyperspace", 250, new int[] { 400, 480, 500, 460, 405 }, new int[] { 220, 220, 200, 166, 204 }, -1, -1, null, null, ARROW_HYPERSPACE);
	public static final AreaBonusPoints HYPERSPACE_ENTRY = new AreaBonusPoints(Main.downDisplayPanel, "Gate of Hyperspace", 10,	new int[] { 610, 610, 565, 565 }, new int[] { 150, 190, 190, 150 }, -1, -1, null, null, null);

	public static final Area START_ZONE = new Area(Main.downDisplayPanel, "", 0, new int[] { 1150, 1150, 1224, 1224 }, new int[] { 145, 175, 175, 145 }, -1, -1, null, null, null);
	public static final Area LAUNCH_RAMP = new Area(Main.downDisplayPanel, "Launch", 0, new int[] { 370, 370, 850, 850 }, new int[] { 0, 140, 140, 0 }, -1, -1, null, null, null);

	public static final Area TIME_WARP = new Area(Main.upDisplayPanel, "Time Warp", 250, new int[] { 488, 488, 580, 580 }, new int[] { 550, 610, 610, 550 }, 75, 528, IMG_TIME_WARP_ACTIVE, IMG_TIME_WARP, ARROW_TIME_WARP);

	public static final ReEntry REENTRY_LEFT = new ReEntry(Main.upDisplayPanel, 25, new int[] { 390, 390, 458, 458 }, new int[] { 236, 293, 293, 236 }, 1744, 89, ARROW_REENTRY);
	public static final ReEntry REENTRY_MIDDLE = new ReEntry(Main.upDisplayPanel, 25, new int[] { 390, 390, 458, 458 }, new int[] { 293, 355, 355, 293 }, 1396, 88, ARROW_REENTRY);
	public static final ReEntry REENTRY_RIGHT = new ReEntry(Main.upDisplayPanel, 25, new int[] { 390, 390, 458, 458 }, new int[] { 355, 427, 427, 355 }, 1022, 87, ARROW_REENTRY);

	public static final Target MULTIPLIER_LEFT = new Target(Main.upDisplayPanel, "Multiplier Target", 0, new int[] { 466, 491, 502, 472 }, new int[] { 447, 440, 465, 483 }, 570, 450, ARROW_MULTIPLIER);
	public static final Target MULTIPLIER_RIGHT = new Target(Main.upDisplayPanel, "Multiplier Target", 0, new int[] { 473, 504, 513, 484 }, new int[] { 484, 475, 497, 513 }, 720, 400, ARROW_MULTIPLIER);

	public static final Area RESET = new Area(Main.downDisplayPanel, "Reset", 0, new int[] { 1160, 1280, 1280, 1160 }, new int[] { 575, 575, 720, 720 }, -1, -1, null, null, null);

	
	public static final Area[] ALL_TARGETS = new Area[] {TOP_MISSION_TARGET, MIDDLE_MISSION_TARGET, BOTTOM_MISSION_TARGET, TOP_FLAG_TARGET, BOTTOM_FLAG_TARGET, TOP_HAZARD_TARGET, MIDDLE_HAZARD_TARGET, BOTTOM_HAZARD_TARGET, MULTIPLIER_LEFT, MULTIPLIER_RIGHT};
	public static final Area[] MISSION_TARGETS = new Area[] {TOP_MISSION_TARGET, MIDDLE_MISSION_TARGET, BOTTOM_MISSION_TARGET};
	
	
	private static final int SCORE_TRIPLE_TARGET = 250;
	private static final int SCORE_TRIPLE_REENTRY = 1000;
	
	private static final long MAX_TIME_REDEPLOY = 10000;
	private static final long MAX_TIME_HYPERSPACE = 60000;
	private static final long MAX_TIME_MULTIPLIER = 60000;
	private static long timeSinceLaunch = 0;
	private static long timerHyperspace = 0;
	private static long timerMultiplier = 0;
	
	private int hyperspaceLevel = 0;
	public int multiplier = 1;
	
	private boolean replayBall;
	private boolean extraBall;
	private boolean redeploy;
	
	private boolean gateClosed;
	
	private boolean passedInGateLane = false;

	private Area prevArea = null;

	private int score;

	private int nonAreaTimer = 0;

	public static final List<Area> AREAS = new ArrayList<>();
	public static final List<Indicator> INDICATORS = new ArrayList<>();
	public static final List<IndicatorLeveled> INDICATORS_LEVELED = new ArrayList<>();
	
	public static MissionManager missionManager;

	public Game()
	{
		initTarget();
		missionManager = new MissionManager();		
		this.setGateOpen(true);		
		this.prevArea = Game.START_ZONE;		
		Main.repaint();
	}

	/**
	 * Check if ballPosition is in an area, and perform actions depending of the area
	 * @param ballPosition : The ball's position
	 * @return true if ballPosition is in an area, false otherwise
	 */
	public boolean verify(Point ballPosition)
	{
		for (Area area : AREAS)
		{
			if (area.contains(ballPosition))
			{
				this.nonAreaTimer = 0;

				if (area.equals(START_ZONE) && !area.equals(getPrevArea()))
				{
					actionStart();
					this.prevArea = area;
					return true;
				}
				
				if(LAUNCH_RAMP.equals(area) && (this.getPrevArea() == null || !this.getPrevArea().equals(LAUNCH_RAMP)))
				{
					timeSinceLaunch = System.currentTimeMillis();
					LIGHT_REDEPLOY.setActive(true);
					this.redeploy = true;
					System.out.println("[Game] Current time : " + timeSinceLaunch);
				}

				if (area.canPerformAction(getPrevArea()))
				{
					area.activate();
					
					Main.messageLabel.setText("");
					
					Main.refreshLabel();
					actionSpeciale(area);
					Main.repaint();
					addPoints(area.getPoints());
					this.prevArea = area;
					return true;
				}
				
				this.prevArea = area;
				return false;
			}
		}
		
		if (getPrevArea() == null || (!getPrevArea().equals(START_ZONE) && !getPrevArea().equals(LAUNCH_RAMP)))
		{
			this.nonAreaTimer++;
		}
		
		if (this.nonAreaTimer > 5)
		{
			this.prevArea = null;
			desactivateTimeWarpIndicator();
		}
		
		return false;
	}
	
	/**
	 * Update all graphical elements
	 */
	public void updateTicks()
	{
		this.redeploy = System.currentTimeMillis() - timeSinceLaunch <= MAX_TIME_REDEPLOY;
		
		if(hyperspaceLevel > 0 && System.currentTimeMillis() - timerHyperspace > MAX_TIME_HYPERSPACE)
		{
			hyperspaceLevel--;
			LIGHT_HYPERSPACE.setLevel(hyperspaceLevel);
			timerHyperspace = System.currentTimeMillis();
		}
		
		if(multiplier > 1 && System.currentTimeMillis() - timerMultiplier > MAX_TIME_MULTIPLIER)
		{
			multiplier --;
			timerMultiplier = System.currentTimeMillis();
		}
		
		LIGHT_REDEPLOY.setActive(this.redeploy);
		LIGHT_REPLAYBALL.setActive(this.replayBall);
		LIGHT_EXTRABALL.setActive(this.extraBall);
		
		if(missionManager.indexStart == -2)
		{
			Game.ARROW_MISSION.setActive(true);
		}
		
		if(missionManager.indexStart == -1)
		{
			Game.ARROW_SHOT_RAMP.setActive(true);
		}
		
		if(missionManager.mission != null && missionManager.mission.getCurrentStep() != null && missionManager.indexStart >= 0)
		{
			missionManager.mission.getCurrentStep().activateIndicators(true);
		}
	}

	/**
	 * Desactivate Time Warp indicator
	 */
	private void desactivateTimeWarpIndicator()
	{
		TIME_WARP.setActive(false);		
		Main.repaint();
	}
	
	/**
	 * Open or close the gate
	 * @param open : true --> open the gate, false --> close the gate
	 */
	private void setGateOpen(boolean open)
	{
		this.gateClosed = !open;
		GATE_LANE.setActive(this.gateClosed);
	}

	/**
	 * Check if the ball counter needs to be decremented
	 * Called when the ball is in the START area
	 */
	private void actionStart()
	{
		System.out.println("[Game] actionStart");		
		System.out.println("[Game] Gate " + (this.gateClosed ? "closed" : "opened"));
		

		if ((getPrevArea() != null && getPrevArea().equals(LAUNCH_RAMP)) || System.currentTimeMillis() - timeSinceLaunch <= MAX_TIME_REDEPLOY)
		{
			if(this.passedInGateLane && !this.gateClosed)
			{
				this.setGateOpen(false);
				Main.messageLabel.setText("Re Deploy \t Gate closed");
				Main.refreshLabel();
				System.out.println("[Game] Redployment + Gate closed");
			}
			
			else
			{
				Main.messageLabel.setText("Re Deploy");
				Main.refreshLabel();
				System.out.println("[Game] Redeployement");
			}
		}
		else if(this.passedInGateLane && !this.gateClosed)
		{
			this.setGateOpen(false);
			Main.messageLabel.setText("Launch this ball again \t Gate closed");
			this.passedInGateLane = false;
			System.out.println("[Game] Gate Closed : Play this ball again");
		}
		else if (replayBall)
		{
			replayBall = false;
			Main.messageLabel.setText("Replay Ball");
			Main.refreshLabel();
			Main.repaint();
			System.out.println("[Game] Replay Ball used");
		}
		else if(extraBall)
		{
			this.extraBall = false;
			Main.messageLabel.setText("Play an Extra ball");
			Main.refreshLabel();
			this.resetAreasAndIndicators();
			System.out.println("[Game] Extra Ball used");
		}
		else
		{
			resetBall();
		}
	}

	/**
	 * Perform a special action depending of the area where the ball is
	 * @param area : The area where the ball is
	 */
	private void actionSpeciale(Area area)
	{
		if (area == null) // Is this really useful ???
		{
			desactivateTimeWarpIndicator();
			System.out.println("[Game] <actionSpeciale()> area == null");
		}
		else
		{
			if(area.equals(RESET))
			{
				this.resetGame();
			}
			
			if (area.equals(GATE_LANE) && !this.passedInGateLane)
			{
				this.passedInGateLane = true;
				System.out.println("[Game] Passed in gate lane");
			}

			if (area.equals(HYPERSPACE_END))
			{
				hyperspaceLevel++;
				timerHyperspace = System.currentTimeMillis();
				
				if(hyperspaceLevel >= 5)
				{
					hyperspaceLevel = 0;
					extraBall = true;
				}
				
				LIGHT_HYPERSPACE.setLevel(hyperspaceLevel);
			}

			if (TOP_MISSION_TARGET.isActive() && MIDDLE_MISSION_TARGET.isActive() && BOTTOM_MISSION_TARGET.isActive())
			{
				TOP_MISSION_TARGET.setActive(false);
				MIDDLE_MISSION_TARGET.setActive(false);
				BOTTOM_MISSION_TARGET.setActive(false);
				addPoints(SCORE_TRIPLE_TARGET);
				System.out.println("[Game] Mission bank full");
				Main.messageLabel.setText("Mission bank full !");
				Main.refreshLabel();
			}

			if (REENTRY_LEFT.isActive() && REENTRY_MIDDLE.isActive() && REENTRY_RIGHT.isActive())
			{
				REENTRY_LEFT.setActive(false);
				REENTRY_MIDDLE.setActive(false);
				REENTRY_RIGHT.setActive(false);
				addPoints(SCORE_TRIPLE_REENTRY);
				System.out.println("[Game] Re-entry full");
				Main.messageLabel.setText("Re-entry full !");
				Main.refreshLabel();
			}
			
			if (TOP_HAZARD_TARGET.isActive() && MIDDLE_HAZARD_TARGET.isActive() && BOTTOM_HAZARD_TARGET.isActive())
			{
				TOP_HAZARD_TARGET.setActive(false);
				MIDDLE_HAZARD_TARGET.setActive(false);
				BOTTOM_HAZARD_TARGET.setActive(false);
				addPoints(SCORE_TRIPLE_TARGET);
				
				if(!this.gateClosed)
				{
					Main.messageLabel.setText("Hazard bank full !");
					System.out.println("[Game] Hazard bank full !");
				}
				else
				{
					Main.messageLabel.setText("Gate Opened !");
					System.out.println("[Game] Gate Opened");
					this.setGateOpen(true);
				}				
				
				Main.refreshLabel();
			}
			
			if (TOP_FLAG_TARGET.isActive() && BOTTOM_FLAG_TARGET.isActive())
			{
				TOP_FLAG_TARGET.setActive(false);
				BOTTOM_FLAG_TARGET.setActive(false);
				addPoints(SCORE_TRIPLE_TARGET);
				System.out.println("[Game] Flags full");
				Main.messageLabel.setText("Flags full !");
				Main.refreshLabel();
			}
			
			if(MULTIPLIER_LEFT.isActive() && MULTIPLIER_RIGHT.isActive())
			{
				MULTIPLIER_LEFT.setActive(false);
				MULTIPLIER_RIGHT.setActive(false);
				System.out.println("[Game] Multiplier bank full");
				Main.messageLabel.setText("Multiplier Upgraded !");
				timerMultiplier = System.currentTimeMillis();
				
				if (multiplier < 4)
				{
					multiplier++;
				}
			}
		}
	}
	
	/**
	 * Give or take Replay Ball
	 * @param grant : true --> grant Replay Ball, false --> remove Replay Ball
	 */
	public void setReplayBall(boolean grant)
	{
		this.replayBall = grant;
		Main.repaint();
	}

	/**
	 * Initialize all targets and indicators
	 * Called only once, when the program starts
	 */
	private void initTarget()
	{
		AREAS.add(TOP_MISSION_TARGET);
		AREAS.add(MIDDLE_MISSION_TARGET);
		AREAS.add(BOTTOM_MISSION_TARGET);
		AREAS.add(TOP_FLAG_TARGET);
		AREAS.add(BOTTOM_FLAG_TARGET);
		AREAS.add(TOP_HAZARD_TARGET);
		AREAS.add(MIDDLE_HAZARD_TARGET);
		AREAS.add(BOTTOM_HAZARD_TARGET);
		AREAS.add(SHOT_RAMP);
		AREAS.add(SHOT_SURFACE);
		AREAS.add(SHOT_HOLE);
		AREAS.add(START_ZONE);
		AREAS.add(GATE_LANE);
		AREAS.add(HYPERSPACE_END);
		AREAS.add(HYPERSPACE_ENTRY);
		AREAS.add(LAUNCH_RAMP);
		AREAS.add(TIME_WARP);
		AREAS.add(MULTIPLIER_LEFT);
		AREAS.add(MULTIPLIER_RIGHT);
		AREAS.add(REENTRY_LEFT);
		AREAS.add(REENTRY_MIDDLE);
		AREAS.add(REENTRY_RIGHT);
		AREAS.add(RESET);
		
		for(Area target : ALL_TARGETS)
		{
			target.setActive(false);
		}
		
		INDICATORS.add(ARROW_FLAG);
		INDICATORS.add(ARROW_HAZARD);
		INDICATORS.add(ARROW_HYPERSPACE);
		INDICATORS.add(ARROW_MISSION);
		INDICATORS.add(ARROW_MULTIPLIER);
		INDICATORS.add(ARROW_REENTRY);
		INDICATORS.add(ARROW_SHOT_HOLE);
		INDICATORS.add(ARROW_SHOT_RAMP);
		INDICATORS.add(ARROW_TIME_WARP);
		INDICATORS.add(LIGHT_REDEPLOY);
		INDICATORS.add(LIGHT_REPLAYBALL);
		INDICATORS.add(LIGHT_EXTRABALL);
		
		INDICATORS_LEVELED.add(LIGHT_HYPERSPACE);
		
		/*
		//XXX Uncomment to display all indicators (Debug only)
		for(Indicator i : INDICATORS)
		{
			i.setActive(true);
		}/**/
	}

	/**
	 * Increment score
	 * @param nbPoints : nb of points to add to the score
	 */
	void addPoints(int nbPoints)
	{
		this.score += nbPoints * multiplier;
		Main.refreshLabel();
	}
	
	/**
	 * Turn off all indicators and areas
	 * Called on new game, new ball or Replay Ball
	 */
	private void resetAreasAndIndicators()
	{
		this.multiplier = 1;
		this.hyperspaceLevel = 0;				
		this.replayBall = false;
		this.extraBall = false;
		this.passedInGateLane = false;
		this.setGateOpen(true);
		
		for(Area area : AREAS)
		{
			area.setActive(false);
		}
		
		for(Indicator indicator : INDICATORS)
		{
			indicator.setActive(false);
		}
		
		LIGHT_HYPERSPACE.setLevel(hyperspaceLevel);
		Main.refreshLabel();
	}

	/**
	 * Reset the game
	 * Called when waiting for a new game
	 */
	void resetGame()
	{
		this.prevArea = Game.LAUNCH_RAMP;
		System.out.println("[Game] Reset game");
		this.score = 0;
		Main.nbBalle = 3;
		missionManager.resetAllMissions();
		this.resetAreasAndIndicators();
		Game.timeSinceLaunch = System.currentTimeMillis();
	}

	/**
	 * Move to next ball
	 * Called when a ball is lost
	 */
	private void resetBall()
	{
		System.out.println("[Game] Reset ball");

		Main.nbBalle--;

		if (Main.nbBalle == 0)
		{
			for(Indicator i : INDICATORS)
			{
				i.setActive(false);
			}
			
			Main.missionLabel.setText("Game Over");
			Main.missionPointsLabel.setText("");
			Main.refreshLabel();
		}
		else
		{
			Main.missionLabel.setText("Next ball !");
		}
		
		this.resetAreasAndIndicators();
	}

	public int getScore()
	{
		return score;
	}

	public Area getPrevArea()
	{
		return this.prevArea;
	}
}
