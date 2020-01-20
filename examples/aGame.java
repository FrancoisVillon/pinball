import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class aGame {

	static int valR = 222;
	static int valG = 120;
	static int valB = 66;
	static int seuilR = 45;
	static int seuilG = 60;
	static int seuilB = 35;

	private int[] loc = new int[2];
	private aPolygon prev_area = null;

	int score = 0;
	
	private int nonAreaCount = 0;

	public aGame() {
		initTarget();
		loc[0] = 0;
		loc[1] = 0;

	}
	
	

	public void searchNplay(BufferedImage image) {
		aMyTest.panelA.repaint();
		loc = searchByPrevious(image, loc[0], loc[1]);
		if (loc[0] == -1 && loc[1] == -1) {
			// System.out.println("Not found");
		} else {
			// System.out.println(" - > Found (x : " + loc[0] + ", y : " + loc[1]);
			aMyTest.drawPosBalle(loc[0], loc[1]);
			if (verify(loc[0], loc[1])) {// TODO finir
				System.out.println("repaint");
				aMyTest.refreshScoreLabel();

			}
		}

	}

	// Verif zones

	public static boolean targetAff = false; 
	public boolean verify(int x, int y) {
		for (aPolygon pol : aMyTest.panelA.list) {
			// System.out.println(x + " : " + y+ "("+pol.contains(x, y)+")");
			if (pol.contains(x, y)) {
				nonAreaCount = 0;
				System.out.println(pol.descr);
				if(pol.descr.contains("start")) {
					score = 0;
					prev_area = pol;
					return true;
				}
				
				if (pol.action(prev_area)) {
					pol.active();
					aMyTest.panelA.paintComponent(null);
//					if(pol.descr.contains("gauche")) {
//						if(targetAff) {
//							targetAff = false;
//						}else {
//							targetAff = true;
//						}
//						aMyTest.drawTarget(0,0, targetAff);
//					}
					System.out.println(pol.points + " points ! " + pol.descr);
					score += pol.points;
					prev_area = pol;
					return true;
				}
				prev_area = pol;
				return false;
			}
		}
		nonAreaCount++;
		if(nonAreaCount>5) {
			prev_area = null;
		}
		return false;
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
		
		//aMyTest.panelA.map.put("target gauche 1", false);

		ImageIcon imgTarget = new ImageIcon("/home/nicolas/Bureau/target.png");
		ImageIcon imgFleche = new ImageIcon("/home/nicolas/Bureau/fleche.png");
		
		aPolygon targetG1 = new aPolygon("target gauche 1", 100, new int[] { 743, 743, 777, 777 },
				new int[] { 530, 495, 495, 525 }, 4, 220, 40, imgTarget);
		aPolygon targetG2 = new aPolygon("target gauche 2", 100, new int[] { 810, 810, 777, 777 },
				new int[] { 530, 495, 495, 525 }, 4, 225, 215, imgTarget);
		aPolygon targetG3 = new aPolygon("target gauche 3", 100, new int[] { 810, 810, 850, 850 },
				new int[] { 520, 495, 495, 520 }, 4, 230, 385, imgTarget);
		aPolygon targetD = new aPolygon("target droit", 100, new int[] { 725, 725, 810, 810 },
				new int[] { 220, 240, 240, 220 }, 4,1690,35, imgTarget);

		aPolygon rampe = new aPolygonCompt("rampe", 20,
				new int[] { 725, 680, 650, 625, 625, 635, 666, 693, 710, 685, 685, 702, 721, 756 },
				new int[] { 380, 422, 470, 540, 583, 620, 652, 666, 600, 585, 534, 507, 484, 462 }, 14, 715,20, imgFleche);

		aPolygon plateforme = new aPolygon("plateforme", 500, new int[] { 708, 720, 760, 780, 873, 925, 920, 700 },
				new int[] { 602, 608, 540, 532, 526, 570, 665, 670 }, 8, 780,160, imgFleche);
		

		aPolygon start = new aPolygon("start", 500, new int[] { 1150,1150, 1224, 1224},
				new int[] { 145, 175, 175, 145}, 4, -1, -1, null);

		aPolygonNeedPrev trou = new aPolygonNeedPrev("trou", 250, new int[] { 930, 960, 975, 970, 950, 930, 925 },
				new int[] { 580, 580, 600, 625, 630, 625, 600 }, 7,-1,-1,null, new ArrayList<aPolygon>() {
					{
						add(plateforme);
					}
				});

		aMyTest.panelA.list.add(targetG1);
		aMyTest.panelA.list.add(targetG2);
		aMyTest.panelA.list.add(targetG3);
		aMyTest.panelA.list.add(targetD);
		aMyTest.panelA.list.add(rampe);
		aMyTest.panelA.list.add(plateforme);
		aMyTest.panelA.list.add(trou);
		aMyTest.panelA.list.add(start);
		

	}

}
