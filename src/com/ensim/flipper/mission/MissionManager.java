package com.ensim.flipper.mission;

import java.util.ArrayList;

import com.ensim.flipper.Game;
import com.ensim.flipper.Main;
import com.ensim.flipper.area.Area;

public class MissionManager
{
	ArrayList<MissionListForRank> listMissionForRank;
	public int indexStart = -2;
	private int rank = 0;
	private int scorePreStep = 300;
	public Mission mission = null;

	public String instructions;
	public String scoreInfos;

	public int actionMission(int i, int j, Area area)
	{
		if (this.isDone())
		{
			this.instructions = "All missions done";
			this.scoreInfos = "Congratulations";
			Main.refreshLabel();
		}
		else
		{
			if (this.mission == null)
			{
				if (this.indexStart == -2)
				{ // Attente choix mission : target					
					int missionIndex;
					
					if ((missionIndex = Game.getMissionTargetIndex(area)) > 0)
					{
						// System.out.println("\tchoix mission");
						Game.ARROW_MISSION.setActive(false);
						this.choixMission(missionIndex);
						this.instructions = "Shot to accept " + this.mission.getName() + " Mission";
						this.scoreInfos = "Earn " + (scorePreStep * Main.game.multiplier) + "pts";
						Main.refreshLabel();
						
						this.indexStart++;
						return this.scorePreStep;
					}
				}
				else
				{
					System.out.println("[Mission Manager] probleme : mission non choisie (ou fin)");
				}
			}
			else
			{
				if (this.indexStart == -1)
				{ // Attente validation : rampe
					
					if (area.equals(Game.SHOT_RAMP))
					{
						System.out.println("[Mission Manager] validation mission");
						indexStart++;
						this.instructions = this.mission.getInstructions();
						this.scoreInfos = "Earn " + (this.scorePreStep * Main.game.multiplier) + "pts";
						Game.ARROW_SHOT_RAMP.setActive(false);
						Main.refreshLabel();
						return scorePreStep;
					}
				}
				else
				{
					int points = this.mission.validate(area);
					
					if (points == this.mission.getEndScore())
					{
						this.scoreInfos = this.mission.getEndMissionScoreInfo();
						this.listMissionForRank.get(this.rank).listMissions.remove(this.mission);
						this.mission = null;
						if (this.listMissionForRank.get(this.rank).listMissions.size() == 0)
						{
							this.nextRang();
						}
						else
						{
							this.nextMission();
						}
					}
					else
					{
						this.instructions = this.mission.getInstructions();
						this.scoreInfos = this.mission.getScoreInfo();
					}
					Main.refreshLabel();
					return points;
				}
			}
		}

		return 0;
	}

	private void nextMission()
	{
		this.indexStart = -2;
		this.instructions = "Select mission with Mission Targets";
		System.out.println("[Mission Manager] Next mission");
	}

	private void nextRang()
	{
		this.rank++;
		nextMission();
		this.scoreInfos = "Rank Up !";
		System.out.println("[Mission Manager] Rang ++ (" + this.rank + ")");
	}

	private void choixMission(int choix)
	{
		choix--;
		
		if (choix > this.listMissionForRank.get(this.rank).listMissions.size() - 1)
		{
			choix = this.listMissionForRank.get(this.rank).listMissions.size() - 1;
		}
		
		System.out.println("[Mission Manager] choix de mission " + (choix + 1) + " / " + (this.listMissionForRank.get(this.rank).listMissions.size()) + " au rang " + this.getRang());
		this.mission = listMissionForRank.get(this.rank).listMissions.get(choix);
		System.out.println("[Mission Manager] Mission : " + this.mission.getInstructions());
	}

	public void initMission()
	{
		System.out.println("[Mission Manager] init missions");
		this.listMissionForRank = new ArrayList<>();
		ArrayList<Mission> listMissionRank1 = new ArrayList<>();
		
		Mission mission = new Mission("Full Shot Training", 300, 5000);
		mission.addStep("Do 1 Full Shot", 100, Game.SHOT_HOLE);
		mission.addStep("Hit Time Warp", 100, Game.TIME_WARP);
		listMissionRank1.add(mission);
		
		mission = new Mission("Re-entries Training", 300, 5000);
		mission.addStep("Pass 3 Re-entries", 100, Game.REENTRY_LEFT, Game.REENTRY_MIDDLE, Game.REENTRY_RIGHT);
		mission.addStep("Pass 2 Re-entries", 100, Game.REENTRY_LEFT, Game.REENTRY_MIDDLE, Game.REENTRY_RIGHT);
		mission.addStep("Pass 1 Re-entry", 100, Game.REENTRY_LEFT, Game.REENTRY_MIDDLE, Game.REENTRY_RIGHT);
		listMissionRank1.add(mission);
		
		mission = new Mission("Burst Shot Training", 300, 5000);
		mission.addStep("Do 3 Shots", 100, Game.SHOT_RAMP);
		mission.addStep("Do 2 Shots", 100, Game.SHOT_RAMP);
		mission.addStep("Do 1 Shot", 100, Game.SHOT_RAMP);
		listMissionRank1.add(mission);

		this.listMissionForRank.add(new MissionListForRank(listMissionRank1, "Level 1"));

		ArrayList<Mission> listMissionRank2 = new ArrayList<>();
		ArrayList<Mission> listMissionRank3 = new ArrayList<>();
		
		mission = new Mission("Bug Hunt", 300, 7500);
		mission.addStep("Hit 7 targets", 100, Game.ALL_TARGETS);
		mission.addStep("Hit 6 targets", 100, Game.ALL_TARGETS);
		mission.addStep("Hit 5 targets", 100, Game.ALL_TARGETS);
		mission.addStep("Hit 4 targets", 100, Game.ALL_TARGETS);
		mission.addStep("Hit 3 targets", 100, Game.ALL_TARGETS);
		mission.addStep("Hit 2 targets", 100, Game.ALL_TARGETS);
		mission.addStep("Hit 1 target", 100, Game.ALL_TARGETS);
		mission.addStep("Hit Time Warp", 100, Game.TIME_WARP);
		listMissionRank2.add(mission);
		listMissionRank3.add(mission);
		
		mission = new Mission("Hyperspace Travel", 300, 7500);
		mission.addStep("Hit 3 Flag Targets", 100, Game.TOP_FLAG_TARGET);
		mission.addStep("Hit 2 Flag Targets", 100, Game.TOP_FLAG_TARGET);
		mission.addStep("Hit 1 Flag Target", 100, Game.TOP_FLAG_TARGET);
		mission.addStep("Enter Hyperspace", 100, Game.HYPERSPACE_END);
		listMissionRank2.add(mission);
		listMissionRank3.add(mission);
		
		mission = new Mission("Throught Time and Space", 300, 7500);
		mission.addStep("Pass 3 Re-entries", 100, Game.REENTRY_LEFT, Game.REENTRY_MIDDLE, Game.REENTRY_RIGHT);
		mission.addStep("Pass 2 Re-entries", 100, Game.REENTRY_LEFT, Game.REENTRY_MIDDLE, Game.REENTRY_RIGHT);
		mission.addStep("Pass 1 Re-entry", 100, Game.REENTRY_LEFT, Game.REENTRY_MIDDLE, Game.REENTRY_RIGHT);
		mission.addStep("Hit Time Warp", 100, Game.TIME_WARP);
		listMissionRank2.add(mission);
		listMissionRank3.add(mission);
		
		this.listMissionForRank.add(new MissionListForRank(listMissionRank2, "Level 2"));		
		this.listMissionForRank.add(new MissionListForRank(listMissionRank3, "Level 3"));
		
		ArrayList<Mission> listMissionRank4 = new ArrayList<>();
		ArrayList<Mission> listMissionRank5 = new ArrayList<>();
		
		mission = new Mission("Satellite Assault", 300, 10000);
		mission.addStep("Hit 2 Multiplier Fields", 100, Game.MULTIPLIER_LEFT);
		mission.addStep("Hit 1 Multiplier Field", 100, Game.MULTIPLIER_LEFT);
		listMissionRank4.add(mission);
		listMissionRank5.add(mission);
		
		mission = new Mission("Hyperspace Multi-jump", 300, 10000);
		mission.addStep("Enter Hyperspace 3 times", 100, Game.HYPERSPACE_END);
		mission.addStep("Enter Hyperspace 2 times", 100, Game.HYPERSPACE_END);
		mission.addStep("Enter Hyperspace 1 more time", 100, Game.HYPERSPACE_END);
		listMissionRank4.add(mission);
		listMissionRank5.add(mission);
		
		mission = new Mission("Open the Gate", 300, 10000);
		mission.addStep("Hit Hazard Target 3 times", 100, Game.TOP_HAZARD_TARGET, Game.MIDDLE_HAZARD_TARGET, Game.BOTTOM_HAZARD_TARGET);
		mission.addStep("Hit Hazard Target 2 times", 100, Game.TOP_HAZARD_TARGET, Game.MIDDLE_HAZARD_TARGET, Game.BOTTOM_HAZARD_TARGET);
		mission.addStep("Hit Hazard Target 1 times", 100, Game.TOP_HAZARD_TARGET, Game.MIDDLE_HAZARD_TARGET, Game.BOTTOM_HAZARD_TARGET);
		listMissionRank4.add(mission);
		listMissionRank5.add(mission);

		
		listMissionForRank.add(new MissionListForRank(listMissionRank4, "Level 4"));
		listMissionForRank.add(new MissionListForRank(listMissionRank5, "Level 5"));
		
		ArrayList<Mission> listMissionRank6 = new ArrayList<>();
		
		mission = new Mission("Maelstorm", 300, 20000);
		mission.addStep("Hit 3 Flag Targets", 100, Game.TOP_FLAG_TARGET);
		mission.addStep("Hit 2 Flag Targets", 100, Game.TOP_FLAG_TARGET);
		mission.addStep("Hit 1 Flag Target", 100, Game.TOP_FLAG_TARGET);
		mission.addStep("Hit 3 Mission Targets", 100, Game.MISSION_TARGETS);
		mission.addStep("Hit 2 Mission Targets", 100, Game.MISSION_TARGETS);
		mission.addStep("Hit 1 Mission Target", 100, Game.MISSION_TARGETS);
		mission.addStep("Pass 3 Re-entries", 100, Game.REENTRY_LEFT, Game.REENTRY_MIDDLE, Game.REENTRY_RIGHT);
		mission.addStep("Pass 2 Re-entries", 100, Game.REENTRY_LEFT, Game.REENTRY_MIDDLE, Game.REENTRY_RIGHT);
		mission.addStep("Pass 1 Re-entry", 100, Game.REENTRY_LEFT, Game.REENTRY_MIDDLE, Game.REENTRY_RIGHT);
		mission.addStep("Hit Time Warp", 100, Game.TIME_WARP);
		mission.addStep("Do a Full Shot", 100, Game.SHOT_HOLE);
		mission.addStep("Enter Hyperspace", 100, Game.HYPERSPACE_END);
		listMissionRank6.add(mission);
		
		this.listMissionForRank.add(new MissionListForRank(listMissionRank6, "Level 6"));
	}

	public boolean isDone()
	{
		return this.rank >= this.listMissionForRank.size();
	}

	public void resetMission()
	{
		this.mission = null;
		initMission();
		this.rank = 0;
		this.indexStart = -2;
		this.instructions = "Select mission with Mission Targets";
		this.scoreInfos = "";
	}

	public String getRang()
	{
		switch (this.rank)
		{
		case 0:
			return "Cadet";
		case 1:
			return "Lieutenant";
		case 2:
			return "Captain";
		case 3:
			return "Commander";
		case 4:
			return "Admiral";
		case 5:
			return "Fleet Admiral";
		default:
			return "";
		}
	}
}
