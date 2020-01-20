import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

public class aGame {

	final int valR = 222;
	final int valG = 120;
	final int valB = 66;
	final int seuilR = 45;
	final int seuilG = 60;
	final int seuilB = 35;

	final int SCORE_TRIPLE_TARGET = 500;

	private int[] loc = new int[2];
	private aPolygon prev_area = null;

	int score = 0;

	private int nonAreaCount = 0;

	HashMap<String, aPolygon> map;

	public aGame() {
		map = aMyTest.panelA.map;
		initTarget();
		loc[0] = 0;
		loc[1] = 0;

	}

	public void searchNplay(BufferedImage image) {
		loc = searchByPrevious(image, loc[0], loc[1]);
		if (loc[0] == -1 && loc[1] == -1) {
			// System.out.println("Not found");
		} else {
			// System.out.println(" - > Found (x : " + loc[0] + ", y : " + loc[1]);
			aMyTest.drawPosBalle(loc[0], loc[1]);
			if (verify(loc[0], loc[1])) {// TODO finir
				aMyTest.refreshLabel();

			}

			// TODO ? actionSpeciale(prev_area);
		}

	}

	public void waitPartie(BufferedImage image) {
		loc = searchByPrevious(image, loc[0], loc[1]);
		if (loc[0] == -1 && loc[1] == -1) {
		} else {
			aMyTest.drawPosBalle(loc[0], loc[1]);
			if (loc[0] < 360 && loc[1] < 360) {
				aMyTest.nbBalle = 3;
				System.out.println("balles : "+aMyTest.nbBalle);
				score = 0;
				aMyTest.labelM.setText("New game !");
				aMyTest.refreshLabel();
			}

			// TODO ? actionSpeciale(prev_area);
		}
	}

	// Verif zones

	public static boolean targetAff = false;

	public boolean verify(int x, int y) {
		fuite.draw(aMyTest.label.getGraphics());
		for (Entry<String, aPolygon> entry : map.entrySet()) {
			aPolygon pol = entry.getValue();

			if (pol.contains(x, y)) {
				nonAreaCount = 0;
				System.out.println(pol.descr);

				if (pol.descr.contains("start") && prev_area != pol) {

					System.out.println(pol.descr);
					actionStart();
					prev_area = pol;
					return true;
				}
				if(!pol.descr.contains("start")) {
					System.out.println(pol.descr);
				}

				if (pol.action(prev_area)) {
					pol.active();

					System.out.println(pol.points + " points ! " + pol.descr);
					aMyTest.labelM.setText(pol.phrase);
					aMyTest.refreshLabel();
					actionSpeciale(pol);

					aMyTest.panelA.paintComponent(null);
					score += pol.points;
					prev_area = pol;
					return true;
				}
				prev_area = pol;
				return false;
			}

		}
		if (prev_area == null || !prev_area.descr.contains("start")) {
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

		aMyTest.panelA.paintComponent(null);
		aMyTest.panelA.repaint();
	}

	private void actionStart() {

		aMyTest.nbBalle--;

		System.out.println("ball -- ("+aMyTest.nbBalle+")");
		if (aMyTest.nbBalle == 0) {
			aMyTest.labelM.setText("GAME OVER");
		} else {
			aMyTest.labelM.setText("Next ball !\n Ball n°" + aMyTest.nbBalle);
		}
		aMyTest.refreshLabel();
		// score = 0;
		for (Entry<String, aPolygon> entry : map.entrySet()) {
			aPolygon pol2 = entry.getValue();
			pol2.eteindre();
		}

	}

	private void actionSpeciale(aPolygon pol) {

		if (pol == null) {
			System.out.println("null");
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

			if (pol.descr.contains("hyperspace")) {
				aMyTest.nbBalle++;
				System.out.println("ball ++ ("+aMyTest.nbBalle+")");
			}

			if (map.get("target gauche 1").isActive() && map.get("target gauche 2").isActive()
					&& map.get("target gauche 3").isActive()) {
				map.get("target gauche 1").eteindre();
				map.get("target gauche 2").eteindre();
				map.get("target gauche 3").eteindre();
				score += SCORE_TRIPLE_TARGET;
				System.out.println("TRIPLE !!");
				aMyTest.labelM.setText("Triple target ! " + SCORE_TRIPLE_TARGET + "pts");
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

		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				// if(ligne == 410 && col > 325 && col<335)
				// printf("ligne : %d, col : %d (%d,%d,%d)\n",ligne,
				// col,img[((larg*ligne)+col)*3],img[((larg*ligne)+col)*3+1],img[((larg*ligne)+col)*3+2]);

				if (search(img.getRGB(i, j))) {
					ret[0] = i;
					ret[1] = j;
					return ret;
				}

				/*
				 * if (r > valR - seuilR && r < valR + seuilR && g > valG - seuilG && g < valG +
				 * seuilG && b > valB - seuilB && b < valB + seuilB) { // printf("find : ");
				 * ret[0] = i; ret[1] = j; return ret; }
				 */
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

		Target targetG1 = new Target("target gauche 1", 100, new int[] { 743, 743, 777, 777 },
				new int[] { 530, 495, 495, 525 }, 4, 220, 40);
		Target targetG2 = new Target("target gauche 2", 100, new int[] { 810, 810, 777, 777 },
				new int[] { 530, 495, 495, 525 }, 4, 225, 215);
		Target targetG3 = new Target("target gauche 3", 100, new int[] { 810, 810, 850, 850 },
				new int[] { 520, 495, 495, 520 }, 4, 230, 385);
		Target targetD = new Target("target droit", 100, new int[] { 725, 725, 810, 810 },
				new int[] { 220, 260, 260, 220 }, 4, 1690, 35);

		aPolygonCompt rampe = new aPolygonCompt("rampe", "Launch ramp : +20 pts", 20,
				new int[] { 725, 680, 650, 625, 625, 635, 666, 693, 710, 685, 685, 702, 721, 756 },
				new int[] { 380, 422, 470, 540, 583, 620, 652, 666, 600, 585, 534, 507, 484, 462 }, 14, 715, 20,
				imgFleche);

		aPolygon plateforme = new aPolygon("plateforme", "On the platform ramp : 500 pts", 500,
				new int[] { 708, 720, 760, 780, 873, 925, 920, 700 },
				new int[] { 602, 608, 540, 532, 526, 570, 665, 670 }, 8, 780, 160, imgFleche);

		aPolygonNeedPrev trou = new aPolygonNeedPrev("trou", "Pass in hole : 250 pts", 250,
				new int[] { 930, 960, 975, 970, 950, 930, 925 }, new int[] { 580, 580, 600, 625, 630, 625, 600 }, 7, -1,
				-1, null, new ArrayList<aPolygon>() {
					{
						add(plateforme);
					}
				});

		fuite = new aPolygonCompt("fuite", "Goodbye my ball", 0, new int[] { 910, 910, 1045, 1090, 1063, 1010 },
				new int[] { 212, 164, 168, 260, 307, 210 }, 6, 1635, 625, imgGdeFleche);

		aPolygon start = new aPolygon("start", "", 500, new int[] { 1150, 1150, 1224, 1224 },
				new int[] { 145, 175, 175, 145 }, 4, -1, -1, null);

		aPolygon hyperspace = new aPolygon("hyperspace", "Extra ball !", 500, new int[] { 400, 480, 500, 460, 405 },
				new int[] { 230, 230, 210, 176, 214 }, 5, -1, -1, null);
		
		aPolygon hyperspace_enter = new aPolygon("hyperspace_enter", "ENter in hyperspace ! 250pts", 250, new int[] { 610,610, 565, 565 },
				new int[] { 160, 200, 200, 160  }, 4, -1, -1, null);

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

	}

	aPolygonCompt fuite;
}
