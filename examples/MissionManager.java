import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MissionManager {

	ArrayList<MissionClass> listClassMissions;
	int indexStart = -2;
	int rang = 0;
	int scorePreStep = 300;
	Mission mission = null;

	String phrase;
	String phrasePoint;

	public MissionManager() {
		//resetMission();
	}

	public int actionMission(int i, int j, aPolygon area) {
		// System.out.println(" - "+mission);
		// System.out.println(" - "+indexStart);
		if(isDone()) {
			phrase = "Game is done";
			phrasePoint = "";
			aMyTest.refreshLabel();
		}else {
		if (mission == null) {
			if (indexStart == -2) { // Attente choix mission : target
				if (area.descr.contains("target gauche")) {
					//System.out.println("\tchoix mission");
					phrase = "Launch in ramp to validate";
					phrasePoint = "Won " + (scorePreStep * aMyTest.game.multiplier) + "pts";
					aMyTest.refreshLabel();
					choixMission(Integer.parseInt(area.descr.substring(area.descr.length() - 1, area.descr.length())));
					indexStart++;
					return scorePreStep;
				}
			} else {
				System.out.println("probleme : mission non choisie (ou fin)");
			}
		} else {
			if (indexStart == -1) { // Attente validation : rampe
				if (area.descr.contains("ramp")) {
					System.out.println("\tvalidation mission");
					indexStart++;
					phrase = mission.getPhrase();
					phrasePoint = "Won " + (scorePreStep * aMyTest.game.multiplier) + "pts";
					aMyTest.refreshLabel();
					return scorePreStep;
				}
			} else {
				//System.out.println("\tmission");
				int points = mission.validate(area);
				if (points == mission.scoreEnd) {
					phrasePoint = mission.getFinalPhrasePoint();
					listClassMissions.get(rang).listMissions.remove(mission);
					mission = null;
					if (listClassMissions.get(rang).listMissions.size() == 0) {
						nextRang();
					} else {
						nextMission();
					}
				} else {
					phrase = mission.getPhrase();
					phrasePoint = mission.getPhrasePoint();
				}
				aMyTest.refreshLabel();
				return points;

			}
		}
		}

		return 0;
	}

	private void nextMission() {

		indexStart = -2;
		phrase = "Select mission with left traget";
		// phrasePoint = "";
		System.out.println("Next mission");
	}

	private void nextRang() {

		rang++;
		nextMission();
		phrasePoint = "Next Rang !";
		System.out.println("Rang ++ (" + rang + ")");
		System.out.println(listClassMissions.size());
		System.out.println(rang > listClassMissions.size());

	}

	private void choixMission(int choix) {
		choix--;
		if (choix > listClassMissions.get(rang).listMissions.size() - 1) {
			choix = listClassMissions.get(rang).listMissions.size() - 1;
		}
		System.out.println("choix : " + (choix+1) + " / " + (listClassMissions.get(rang).listMissions.size()));
		mission = listClassMissions.get(rang).listMissions.get(choix);

	}

	/*
	 * 
	 * if (indexMission < listClassMissions.size()) {
	 * System.out.println("index mission : " + indexMission);
	 * System.out.println(prev_area.descr); int scoreRet =
	 * listClassMissions.get(indexMission).isValidate(prev_area);
	 * aMyTest.affPointsMission(); // if (scoreRet ==
	 * listMissions.get(indexMission).SCORE_END) { // } addPoints(scoreRet); }
	 * 
	 * }
	 */

	AffMission missionTargetG, missionTargetD, missionRamp;
	
	public void initMission() {
		System.out.println("passé");
		

		listClassMissions = new ArrayList<>();
		
		
		aMyJPanel jpaH = aMyTest.panelA2;
		aMyJPanel jpaB = aMyTest.panelA;

		missionTargetD = new AffMission(1500,80,jpaB, new ImageIcon("/home/nicolas/Bureau/targetDroitMission.png"));
		missionTargetG = new AffMission(310, 150, jpaB, new ImageIcon("/home/nicolas/Bureau/targetGaucheMission.png"));
		missionRamp = new AffMission( 800,280,jpaB, new ImageIcon("/home/nicolas/Bureau/rampMission.png"));
		AffMission missionHole = new AffMission(100,770,jpaB, new ImageIcon("/home/nicolas/Bureau/holeMission.png"));
		AffMission missionHyperspace = new AffMission( 1380,50,jpaB, new ImageIcon("/home/nicolas/Bureau/hyperspaceMission.png"));
		AffMission missionMulti = new AffMission( 720,440,jpaH, new ImageIcon("/home/nicolas/Bureau/rampMission.png"));
		AffMission missionReEntry = new AffMission( 1380,250,jpaH, new ImageIcon("/home/nicolas/Bureau/reentryMission.png"));
		
		listClassMissions = new ArrayList<>();
		ArrayList<Mission> listMissions = new ArrayList<>();

		
		
		
		Mission m = new Mission(300, 1500);
        m.add("target droit", "Hit right target", 500, missionTargetD);
        m.add("rampe", "Launch in ramp", 500, missionRamp);
        m.add("hyperspace_enter", "enter in hyperspace", 500, missionHyperspace);
        m.addFinal(600);
        listMissions.add(m);
		

        m = new Mission(300, 2500);
        m.add("target droit", "Hit right target", 500, missionTargetD);
        m.add("target gauche", "Hit left target", 500, missionTargetG);
        m.addFinal(600);
        listMissions.add(m);
		


        MissionClass mc = new MissionClass(listMissions, "Level 1");
        listClassMissions.add(mc);


		
		
		m = new Mission(300, 1500);
        m.add("target droit", "Hit right target", 500, missionTargetD);
        m.add("rampe", "Launch in ramp", 500, missionRamp);
        m.add("trou", "Put in hole", 500, missionHole);
        m.add("hyperspace_enter", "enter in hyperspace", 500, missionHyperspace);
        m.addFinal(600);
        listMissions.add(m);
		

        m = new Mission(300, 2500);
        m.add("trou", "Put in hole", 100, missionHole);
        m.add("target gauche", "Hit left target", 500, missionTargetG);
        m.addFinal(600);
        listMissions.add(m);
		


        MissionClass mc1 = new MissionClass(listMissions, "Level 1b");
        listClassMissions.add(mc1);
        
        ArrayList<Mission> listMissions2 = new ArrayList<>();

        m = new Mission(300, 5000);
        m.add("re-entry", "Pass in Re-entries (3)", 100, missionReEntry);
        m.add("re-entry", "Pass in Re-entries (2)", 100, missionReEntry);
        m.add("re-entry", "Pass in Re-entry", 100, missionReEntry);
        m.addFinal(600);
        listMissions2.add(m);
        


        m = new Mission(300, 7500);
        m.add("target droit", "Hit right target", 100, missionTargetD);
        m.add("target gauche", "Hit left target (3)", 100, missionTargetG);
        m.add("target gauche", "Hit left target (2)", 100, missionTargetG);
        m.add("target gauche", "Hit left target", 100, missionTargetG);
        m.add("multi", "Hit multiplier", 100, missionMulti);
        m.add("hyperspace_enter", "enter in hyperspace", 500, missionHyperspace);
        m.addFinal(600);        
        listMissions2.add(m);

        MissionClass mc2 = new MissionClass(listMissions2, "Level 2");
        listClassMissions.add(mc2);
        
        ArrayList<Mission> listMissions4 = new ArrayList<>();
       
        m = new Mission(300, 10000);

        m.add("target droit", "Hit right target", 500, missionTargetD);
        m.add("hyperspace", "enter in hyperspace (3)", 500, missionHyperspace);
        m.add("hyperspace", "enter in hyperspace (2)", 500, missionHyperspace);
        m.add("hyperspace", "enter in hyperspace", 500, missionHyperspace);
        m.add("re-entry", "Pass in Re-entries (3)", 100, missionReEntry);
        m.add("re-entry", "Pass in Re-entries (2)", 100, missionReEntry);
        m.add("re-entry", "Pass in Re-entry", 100, missionReEntry);
        m.add("multi", "Hit multiplier", 100, missionMulti);
        m.add("rampe", "Launch in ramp", 500, missionRamp);
        m.add("trou", "Put in hole", 100, missionHole);
        m.add("target gauche", "Hit left target (3)", 100, missionTargetG);
        m.add("target gauche", "Hit left target (2)", 100, missionTargetG);
        m.add("target gauche", "Hit left target", 100, missionTargetG);
        m.addFinal(600);
		listMissions4.add(m);
		
		

        m = new Mission(300, 10000);

        m.add("target droit", "Hit right target", 500, missionTargetD);
        m.add("hyperspace_enter", "enter in hyperspace", 500, missionHyperspace);
        m.add("target droit", "Hit right target", 500, missionTargetD);
        m.add("hyperspace_center", "Go to center to hyperspace", 500, missionHyperspace);
        m.add("target droit", "Hit right target", 500, missionTargetD);
        m.add("hyperspace", "Go all the way to hyperspace", 500, missionHyperspace);
        m.add("rampe", "Launch in ramp", 500, missionRamp);
        m.add("trou", "Put in hole", 100, missionHole);
        m.add("target gauche", "Hit left target (3)", 100, missionTargetG);
        m.add("target gauche", "Hit left target (2)", 100, missionTargetG);
        m.add("target gauche", "Hit left target", 100, missionTargetG);
        m.addFinal(600);
		listMissions4.add(m);
		
		/* hyperspace = new aPolygon(jpaB, "hyperspace_ball", "Extra ball !", "", 500,
				new int[] { 400, 480, 500, 460, 405 }, new int[] { 230, 230, 210, 176, 214 }, 5, -1, -1, null);

		aPolygon hyperspace_center = new aPolygon(jpaB, "hyperspace_center", "Hyperspace", "500pts", 500,
				new int[] { 485, 485, 550, 550 }, new int[] { 162, 204, 204, 162 }, 4, -1, -1, null);

		aPolygon hyperspace_enter*/

        MissionClass mc3 = new MissionClass(listMissions4, "Level 3");
        listClassMissions.add(mc3);
        /*
		
		
		Mission m = new Mission(300, 1500);
		m.add("target droit", "Hit right target", 500, missionTargetD);
		m.add("rampe", "Launch in ramp", 500,missionRamp);
		m.add("trou", "Put in hole", 500, missionHole);
		m.add("hyperspace_enter", "enter in hyperspace", 500,missionHyperspace);
		m.addFinal(600);
		listMissions.add(m);

		m = new Mission(300, 1500);
		m.add("target droit", "Hit right target", 250, missionTargetD);
		m.add("ramp", "Launch on platform 3", 250, missionRamp);
		m.add("plateforme", "Launch on platform 3", 250, missionRamp);
		m.add("ramp", "Launch on platform 2", 250, missionRamp);
		m.add("plateforme", "Launch on platform 2", 250, missionRamp);
		m.add("ramp", "Launch on platform 1", 250, missionRamp);
		m.add("plateforme", "Launch on platform 1", 250, missionRamp);
		m.add("trou", "Put in hole", 500, missionHole);
		m.add("target gauche", "Hit left target", 500, missionTargetG);
		m.add("target droit", "Hit right target", 500, missionTargetD);
	//	m.add("target gauche", "Hit left target", 500, missionTargetG);
	//	m.add("target droit", "Hit right target", 500, missionTargetD);
		m.add("target gauche", "Hit left target", 500, missionTargetG);
		m.addFinal(600);
		listMissions.add(m);

		MissionClass mc = new MissionClass(listMissions, "Level 1");
		listClassMissions.add(mc);

		ArrayList<Mission> listMissions2 = new ArrayList<>();

		m = new Mission(300, 1500);
		m.add("target droit", "Hit right target", 500, missionTargetD);
		m.add("rampe", "Launch in ramp", 500, missionRamp);
		m.add("trou", "Put in hole", 500, missionHole);
		m.add("target droit", "Hit right target", 500, missionTargetD);
		m.addFinal(600);
		listMissions2.add(m);

		m = new Mission(300, 1500);
		m.add("target gauche", "Hit left target", 500, missionTargetG);
		m.add("rampe", "Launch in ramp", 500, missionRamp);
		m.add("trou", "Put in hole", 500, missionHole);
		m.add("target gauche", "Hit left target", 500, missionTargetG);
		m.addFinal(600);
		listMissions2.add(m);

		MissionClass mc2 = new MissionClass(listMissions2, "Level 2");
		listClassMissions.add(mc2);
		*/
		System.out.println("size mission list : "+listClassMissions.size());
	}

	public boolean isDone() {
		return rang >= listClassMissions.size();
	}

	public void resetMission() {
		rang = 0;
		indexStart = -2;
		phrase = "Select mission with left traget";
		phrasePoint = "";
		mission = null;
		listClassMissions = new ArrayList<>();
		initMission();
	}
	public String getRang() {
		switch (rang) {
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
	
	public int getImgX() {
		if(isDone()) {
			return -1;
		}
		if(indexStart == -2) {
			return missionTargetG.x;
		}
		if(indexStart == -1) {
			return missionRamp.x;
		}
		return mission.getImgX();
		
	}
	
	public int getImgY() {
		if(isDone()) {
			return -1;
		}
		if(indexStart == -2) {
			return missionTargetG.y;
		}
		if(indexStart == -1) {
			return missionRamp.y;
		}
		return mission.getImgY();
		
	}
	
	public ImageIcon getImg() {
		if(isDone()) {
			return null;
		}
		if(indexStart == -2) {
			return missionTargetG.img;
		}
		if(indexStart == -1) {
			return missionRamp.img;
		}
		return mission.getImg();
	}
	

	public JPanel getJPanel() {
		if(isDone()) {
			return null;
		}
		if(indexStart == -2) {
			return missionTargetG.jpa;
		}
		if(indexStart == -1) {
			return missionRamp.jpa;
		}
		return mission.getJPanel();
	}

}
