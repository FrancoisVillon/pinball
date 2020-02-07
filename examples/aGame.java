import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class aGame {

	final int valR = 222;
	final int valG = 120;
	final int valB = 66;
	final int seuilR = 45;
	final int seuilG = 60;
	final int seuilB = 35;

	final int SCORE_TRIPLE_TARGET = 1000;
	final int SCORE_TRIPLE_REENTRY = 1500;
	final int SCORE_LIMIT_REDP = 199;
	int multiplier = 1;

	private int[] loc = new int[2];
	private aPolygon prev_area = null;

	int score_total, score = 0;

	private int nonAreaCount = 0;

	HashMap<String, aPolygon> map;

	MissionManager missionManager;

	public aGame() {
		map = aMyTest.map;
		initTarget();
		missionManager = new MissionManager();

		prev_area = map.get("start");
		loc[0] = 0;
		loc[1] = 0;

	}

	public void searchNplay(BufferedImage image) {

		loc = searchByPrevious(image, loc[0], loc[1]);
		// fuite.draw(aMyTest.panel.getGraphics());
		if (loc[0] == -1 && loc[1] == -1) {
			// System.out.println("Not found");
		} else {
			// System.out.println(" - > Found (x : " + loc[0] + ", y : " + loc[1]);
			aMyTest.drawPosBalle(loc[0], loc[1]);

			if (verify(loc[0], loc[1])) {// TODO finir
				aMyTest.refreshLabel();

				missionManager.actionMission(loc[0], loc[1], prev_area);
			}

		}

		targetD.draw(aMyTest.panel.getGraphics());
		start.draw(aMyTest.panel.getGraphics());
		// TODO draw

	}

	public void waitPartie(BufferedImage image) {
		loc = searchByPrevious(image, loc[0], loc[1]);
		if (loc[0] == -1 && loc[1] == -1) {
		} else {
			aMyTest.drawPosBalle(loc[0], loc[1]);
			if (map.get("launch").contains(loc[0], loc[1])) {
				// if (loc[0] < 360 && loc[1] < 360) {
				prev_area = map.get("launch");
				aMyTest.nbBalle = 3;
				//System.out.println("balles : " + aMyTest.nbBalle);
				System.out.println("NEW GAME !");
				resetPartie();
				aMyTest.labelM.setText("New game !");
				aMyTest.labelMP.setText("");
				aMyTest.refreshLabel();
			}

		}
	}

	// Verif zones

	public static boolean targetAff = false;

	public boolean verify(int x, int y) {
		for (Entry<String, aPolygon> entry : map.entrySet()) {
			aPolygon pol = entry.getValue();

			if (pol.contains(x, y)) {
				nonAreaCount = 0;
				// System.out.println(pol.descr);

				if (missionManager.isDone()) {
					// TODO indexMission++;
					//System.out.println("mm is finished ! (useless)");
				}

				if (pol.descr.contains("start") && !pol.equals(prev_area)) {
					//System.out.println("+" + pol.descr);
					actionStart();
					prev_area = pol;
					return true;
				}

				if (!pol.descr.contains("start")) {
				//	System.out.println("-" + pol.descr);
				}

				if (pol.action(prev_area)) {
					pol.active();

					aMyTest.labelM.setText(pol.phrase);
					aMyTest.labelMP.setText(pol.getPhraseScore(multiplier));
					aMyTest.refreshLabel();
					actionSpeciale(pol);
					aMyTest.repaint();
					addPoints(pol.points);
					prev_area = pol;
					return true;
				}
				prev_area = pol;
				return false;
			}

		}
		if (prev_area == null || (!prev_area.descr.contains("start") && !prev_area.descr.contains("launch"))) {
			nonAreaCount++;
		}
		if (nonAreaCount > 5) {
			prev_area = null;
			actionPerm();
		}
		return false;
	}

	private void actionPerm() {
		map.get("rampe").eteindre();
		map.get("plateforme").eteindre();
		map.get("fuite").eteindre();

		aMyTest.repaint();
	}

	private void actionStart() {
		System.out.println("action start");

		/*if (prev_area == null) {
			System.out.println("prev area = null");
		}
		System.out.println("score : " + score);
*/
		if ((prev_area != null && prev_area.descr.contains("launch")) || score < SCORE_LIMIT_REDP) {
			aMyTest.labelM.setText("re Deploy");
			aMyTest.labelMP.setText("");
			multiplier = 1;
			aMyTest.refreshLabel();
		}

		if ((prev_area == null || !prev_area.descr.contains("launch")) && score > SCORE_LIMIT_REDP) {
			resetBall();

		}

	}

	private void actionSpeciale(aPolygon pol) {

		if (pol == null) {
			//System.out.println("null");
			map.get("rampe").eteindre();
			map.get("plateforme").eteindre();
			map.get("fuite").eteindre();
		} else {

			if (pol.descr.contains("plateforme")) {
				map.get("rampe").allumer();
			} else {
				map.get("plateforme").eteindre();
				if (!pol.descr.contains("rampe")) {
					map.get("rampe").eteindre();
				}
			}

			if (!pol.descr.contains("fuite")) {
				map.get("fuite").eteindre();
			}

			if (pol.descr.contains("multi")) {
				if (multiplier < 4) {
					multiplier++;
				}
			}

			if (pol.descr.contains("hyperspace_ball")) {
				aMyTest.nbBalle++;
				System.out.println("ball ++ (" + aMyTest.nbBalle + ")");
			}

			if (map.get("target gauche 1").isActive() && map.get("target gauche 2").isActive()
					&& map.get("target gauche 3").isActive()) {
				map.get("target gauche 1").eteindre();
				map.get("target gauche 2").eteindre();
				map.get("target gauche 3").eteindre();
				addPoints(SCORE_TRIPLE_TARGET);
				System.out.println("TRIPLE target !!");
				aMyTest.labelM.setText("Triple target !");
				aMyTest.labelMP.setText(SCORE_TRIPLE_TARGET * multiplier + "pts");
				aMyTest.refreshLabel();
			}

			if (map.get("re-entry 1").isActive() && map.get("re-entry 2").isActive()
					&& map.get("re-entry 3").isActive()) {
				map.get("re-entry 1").eteindre();
				map.get("re-entry 2").eteindre();
				map.get("re-entry 3").eteindre();
				addPoints(SCORE_TRIPLE_REENTRY);
				System.out.println("Re-entry full !!");
				aMyTest.labelM.setText("TRe-entry full !!");
				aMyTest.labelMP.setText(SCORE_TRIPLE_REENTRY * multiplier + "pts");
				aMyTest.refreshLabel();
			}
		}
	}

	// Recherche balle

	public int[] searchByPrevious(BufferedImage img, int prevX, int prevY) {
		int[] ret = new int[2];

		for (int i = 0; i < 200; i++) {
			for (int difX = -1; difX < 2; difX++) {
				for (int difY = -1; difY < 2; difY++) {
					try {
						if (search(img.getRGB(prevX + difX, prevY + difY))) {
							ret[0] = prevX + difX;
							ret[1] = prevY + difY;
							return ret;
						}
					} catch (Exception e) {
					}
				}
			}
		}

		return search_first(img);
	}

	public int[] search_first(BufferedImage img) {
		// 250, g:107, b:0
		int[] ret = new int[2];
		if (img != null) {
			for (int i = 0; i < img.getWidth(); i++) {
				for (int j = 0; j < img.getHeight(); j++) {
					if (search(img.getRGB(i, j))) {
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

	private boolean search(int clr) {

		int r = (clr & 0x00ff0000) >> 16;
		int g = (clr & 0x0000ff00) >> 8;
		int b = clr & 0x000000ff;
		if (r > valR - seuilR && r < valR + seuilR && g > valG - seuilG && g < valG + seuilG && b > valB - seuilB
				&& b < valB + seuilB) {
			return true;
		}
		return false;
	}

	// init

	@SuppressWarnings("serial")
	private void initTarget() {

		ImageIcon imgFleche = new ImageIcon("/home/nicolas/Bureau/fleche.png");
		ImageIcon imgGdeFleche = new ImageIcon("/home/nicolas/Bureau/flecheGde.png");
		ImageIcon imgWormhole = new ImageIcon("/home/nicolas/Bureau/woormhole.png");
		JPanel jpaB = aMyTest.panelA;
		JPanel jpaH = aMyTest.panelA2;

		Target targetG1 = new Target(jpaB, "target gauche 1", 100, new int[] { 743, 743, 777, 777 },
				new int[] { 530, 495, 495, 525 }, 4, 220, 40);
		Target targetG2 = new Target(jpaB, "target gauche 2", 100, new int[] { 810, 810, 777, 777 },
				new int[] { 530, 495, 495, 525 }, 4, 225, 215);
		Target targetG3 = new Target(jpaB, "target gauche 3", 100, new int[] { 810, 810, 850, 850 },
				new int[] { 520, 495, 495, 520 }, 4, 230, 385);
		targetD = new Target(jpaB, "target droit", 100, new int[] { 725, 725, 810, 810 },
				new int[] { 220, 260, 260, 220 }, 4, 1690, 35);

		aPolygonCompt rampe = new aPolygonCompt(jpaB, "rampe", "Launch ramp", "+20 pts", 20,
				new int[] { 725, 680, 650, 625, 625, 635, 666, 693, 710, 685, 685, 702, 721, 756 },
				new int[] { 380, 422, 470, 540, 583, 620, 652, 666, 600, 585, 534, 507, 484, 462 }, 14, 715, 20,
				imgFleche);

		aPolygon plateforme = new aPolygon(jpaB, "plateforme", "On the platform", "500 pts", 500,
				new int[] { 708, 720, 760, 780, 873, 925, 920, 700 },
				new int[] { 602, 608, 540, 532, 526, 570, 665, 670 }, 8, 780, 160, imgFleche);

		aPolygonNeedPrev trou = new aPolygonNeedPrev(jpaB, "trou", "Pass in hole", "250 pts", 250,
				new int[] { 930, 960, 975, 970, 950, 930, 925 }, new int[] { 580, 580, 600, 625, 630, 625, 600 }, 7, -1,
				-1, null, new ArrayList<aPolygon>() {
					{
						add(plateforme);
					}
				});

		aPolygonCompt fuite = new aPolygonCompt(jpaB, "fuite", "Goodbye my ball", "bonus points +5", 5,
				new int[] { 910, 910, 1045, 1090, 1063, 1010 }, new int[] { 212, 164, 168, 260, 307, 210 }, 6, 1635,
				625, imgGdeFleche);

		start = new aPolygon(jpaB, "start", "", "", 500, new int[] { 1150, 1150, 1224, 1224 },
				new int[] { 145, 175, 175, 145 }, 4, -1, -1, null);

		aPolygon hyperspace = new aPolygon(jpaB, "hyperspace_ball", "Extra ball !", "", 500,
				new int[] { 400, 480, 500, 460, 405 }, new int[] { 230, 230, 210, 176, 214 }, 5, -1, -1, null);

		aPolygon hyperspace_center = new aPolygon(jpaB, "hyperspace_center", "Hyperspace", "500pts", 500,
				new int[] { 485, 485, 550, 550 }, new int[] { 162, 204, 204, 162 }, 4, -1, -1, null);

		aPolygon hyperspace_enter = new aPolygon(jpaB, "hyperspace_enter", "Enter in hyperspace !", " 250pts", 250,
				new int[] { 610, 610, 565, 565 }, new int[] { 160, 200, 200, 160 }, 4, -1, -1, null);

		aPolygon launch = new aPolygon(jpaB, "launch", "launch !", "", 0, new int[] { 370, 370, 850, 850 },
				new int[] { 0, 158, 158, 0 }, 4, -1, -1, null);

		// aPolygon multi = new aPolygon("multi", "Field Multiplier !", "", 0, new int[]
		// { 815, 815, 880, 880 },new int[] { 222, 165, 165, 222 }, 4, -1, -1, null);

		// Affichage haut

		aPolygonCompt wormhole = new aPolygonCompt(jpaH, "wormhole", "wormhole", "+10 pts", 10,
				new int[] { 488, 488, 580, 580 }, new int[] { 607, 660, 660, 607 }, 4, 528, 647, imgWormhole);

		ReEntry reentry3 = new ReEntry(jpaH, "re-entry 3", 50, new int[] { 398, 398, 466, 466 },
				new int[] { 251, 308, 308, 251 }, 4, 1734 + 10, 104 - 15);
		ReEntry reentry2 = new ReEntry(jpaH, "re-entry 2", 50, new int[] { 398, 398, 466, 466 },
				new int[] { 308, 380, 380, 308 }, 4, 1376 + 20, 103 - 15);
		ReEntry reentry1 = new ReEntry(jpaH, "re-entry 1", 50, new int[] { 398, 398, 466, 466 },
				new int[] { 380, 452, 452, 380 }, 4, 992 + 30, 102 - 15);

		aPolygon multi = new aPolygon(jpaH, "multi", "Field Multiplier !", "", 0, new int[] { 460, 490, 512, 490 },
				new int[] { 460, 460, 512, 530 }, 4, 672, 433, imgFleche);

		map.put(targetG1.descr, targetG1);
		map.put(targetG2.descr, targetG2);
		map.put(targetG3.descr, targetG3);
		map.put(targetD.descr, targetD);
		map.put(rampe.descr, rampe);
		map.put(plateforme.descr, plateforme);
		map.put(trou.descr, trou);
		map.put(start.descr, start);
		map.put(fuite.descr, fuite);
		map.put(hyperspace.descr, hyperspace);
		map.put(hyperspace_enter.descr, hyperspace_enter);
		map.put(launch.descr, launch);
		map.put(wormhole.descr, wormhole);
		map.put(hyperspace_center.descr, hyperspace_center);
		map.put(multi.descr, multi);
		map.put(reentry3.descr, reentry3);
		map.put(reentry2.descr, reentry2);
		map.put(reentry1.descr, reentry1);

	}
	aPolygon start;
	Target targetD;

	private void addPoints(int nbPoints) {
		score_total += nbPoints * multiplier;
		score += nbPoints * multiplier;
		aMyTest.refreshLabel();
	}

	private void resetPartie() {
		multiplier = 1;
		score_total = 0;
		missionManager.resetMission();
	}

	private void resetBall() {

		System.out.println("reset ball");

		multiplier = 1;
		score = 0;
		aMyTest.nbBalle--;
		//System.out.println("ball -- (" + aMyTest.nbBalle + ")");
		if (aMyTest.nbBalle == 0) {
			aMyTest.labelM.setText("GAME OVER");
			aMyTest.labelMP.setText("");
			aMyTest.labelMiss.setText("");
			aMyTest.refreshLabel();
		} else {
			aMyTest.labelM.setText("Next ball !");
			aMyTest.labelMP.setText("Ball n'" + aMyTest.nbBalle);
		}
		aMyTest.refreshLabel();
		for (Entry<String, aPolygon> entry : map.entrySet()) {
			aPolygon pol2 = entry.getValue();
			pol2.eteindre();
		}

	}

	/*
	 * public String getPhraseMission() {
	 * 
	 * if (indexMission < listMissions.size()) { return
	 * listMissions.get(indexMission).phrase; } return "Game is done"; }
	 * 
	 * public String getPointMission() { // System.out.println("get phrase point");
	 * if (indexMission < listMissions.size()) { // System.out.println("phrase : //
	 * "+listMissions.get(indexMission).getPhrasePoint()); return
	 * listMissions.get(indexMission).getPhrasePoint(); } return ""; }
	 * 
	 * private void resetMission() { indexMission = 0; for (Mission m :
	 * listMissions) { m.init(); }
	 * 
	 * }
	 */
}
