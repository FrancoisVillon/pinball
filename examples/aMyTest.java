import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDevice;
import com.github.sarxos.webcam.WebcamDriver;
import com.github.sarxos.webcam.WebcamPanel;
import com.sun.prism.image.Coords;

import javafx.scene.shape.Circle;

public class aMyTest {
	static int valR = 222;
	static int valG = 120;
	static int valB = 66;
	static int seuilR = 45;
	static int seuilG = 60;
	static int seuilB = 35;

	static List<Dimension> myCoord = new ArrayList<Dimension>();
	static int count = 0;

	static ArrayList<aPolygon> list = new ArrayList<>();
	static int score = 0;

	public static void main(String[] args) {

		initTarget();
		Webcam webcam = Webcam.getDefault();
		Dimension dim = new Dimension(1280, 720);
		webcam.getDevice().setResolution(dim);

		WebcamPanel panel = new WebcamPanel(webcam);
		panel.setFPSDisplayed(true);
		panel.setFillArea(true);

		JFrame window = new JFrame("Camera"); //
		window.add(panel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();

		window.setVisible(true);

		JFrame window2 = new JFrame("Test 2");
		aMyJPanel panelD = new aMyJPanel();

		ImageIcon imic = new ImageIcon("/home/nicolas/Bureau/img3.png");
		BufferedImage bimg = new BufferedImage(imic.getIconWidth(), imic.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D gg = bimg.createGraphics();
		gg.drawImage(imic.getImage(), 0, 0, imic.getImageObserver());
		panelD.setImage(bimg);
		panelD.setXY(5, 25);
		panelD.paintComponent(gg);

		window2.setPreferredSize(new Dimension(720 / 2, 1280 / 2));
		window2.add(panelD);
		window2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window2.pack();
		window2.setVisible(true);

		JFrame window3 = new JFrame("Test 2");
		aMyJPanel panelA = new aMyJPanel();

		ImageIcon fond = new ImageIcon("/home/nicolas/Bureau/aff.png");
		BufferedImage bimgf = new BufferedImage(fond.getIconWidth(), fond.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D ggf = bimgf.createGraphics();
		ggf.drawImage(fond.getImage(), 0, 0, fond.getImageObserver());
		panelA.setImage(bimgf);
		panelA.paintComponent(ggf);

		JLabel label = new JLabel("Score : 0000000000" + score);
		label.setForeground(Color.white);
		label.setFont(new Font("squaredance00", label.getFont().getStyle(), 40));
		label.setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4,
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 2 / 3);
		label.setSize(label.getPreferredSize());
		panelA.setLayout(null);
		panelA.add(label);

		window3.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// window3.setUndecorated(true);
		window3.add(panelA);
		window3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window3.pack();
		window3.setVisible(true);

		label.setText("Score : " + score);
		panelA.repaint();

		int[] loc = new int[2];
		loc[0] = 0;
		loc[1] = 0;

		while (true) {

			try {
				// String name = String.format("./MyTest/MyTest10-%d.jpg",
				// System.currentTimeMillis());
				// ImageIO.write(webcam.getImage(), "JPG", new File(name));
				// System.out.format("File %s has been saved\n", name);
				// loc = search_first(webcam.getImage());
				loc = searchByPrevious(webcam.getImage(), loc[0], loc[1]);
				if (loc[0] == -1 && loc[1] == -1) {
					// System.out.println("Not found");
				} else {
					// System.out.println(" - > Found (x : " + loc[0] + ", y : " + loc[1]);
					panel.getGraphics().drawRect(loc[0], loc[1], 10, 10);
					drawOnScreen(panelD, loc[0], loc[1]);
					if (verify(loc[0], loc[1])) {// TODO finir
						System.out.println("repaint");
						label.setText("Score : " + score);
						panelA.repaint();

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			drawOnScreen(panelD);
		}

	}

	private static void initTarget() {

		list.add(new aPolygon("target gauche 1", 100, new int[] { 743, 743, 777, 777 },
				new int[] { 530, 495, 495, 525 }, 4));
		list.add(new aPolygon("target gauche 2", 100, new int[] { 810, 810, 777, 777 },
				new int[] { 530, 495, 495, 525 }, 4));
		list.add(new aPolygon("target gauche 3", 100, new int[] { 810, 810, 850, 850 },
				new int[] { 520, 495, 495, 520 }, 4));

		list.add(new aPolygon("target droit", 100, new int[] { 725, 725, 810, 810},
				new int[] { 220, 240, 240, 220}, 4));

		list.add(new aPolygon("rampe", 250,
				new int[] { 725, 680, 650, 625, 625, 635, 666, 693, 710, 685, 685, 702, 721, 756 },
				new int[] { 380, 622, 470, 540, 583, 620, 652, 666, 600, 585, 534, 507, 484, 462 }, 14));
		list.add(new aPolygon("plateforme", 500, new int[] { 708, 720, 760, 780, 873, 925, 920, 700 },
				new int[] { 602, 608, 540, 532, 526, 570, 665, 670 }, 8));
		list.add(new aPolygon("trou", 250, new int[] { 930, 960, 975, 970, 950, 930, 925 },
				new int[] { 580, 580, 600, 625, 630, 625, 600 }, 7));

	}

	private static boolean verify(int x, int y) {
		for (aPolygon pol : list) {
			// System.out.println(x + " : " + y+ "("+pol.contains(x, y)+")");
			if (pol.contains(x, y)) {
				System.out.println(pol.points + " points ! " + pol.descr);
				score += pol.points;
				return true;
			}
		}

		// System.out.println(x + " : " + y + "("+(y>500 && y<530)+ " / "+(x>743 &&
		// x<777)+")");
		if (y > 500 && y < 530 && x > 743 && x < 777) {
			System.out.println("Point ! calculé a la main");
			score += 10;
			return true;
		}
		return false;
	}

	private static void drawOnScreen(JPanel panelD, int x, int y) {
		myCoord.add(new Dimension(720 - y, x));
		if (myCoord.size() > 15) {
			myCoord.remove(0);
		}
		drawOnScreen(panelD);

		if (count++ > 25) {
			panelD.repaint();
			count = 0;
			// panelD.getGraphics().drawOval(720 - 420, 540, 30, 30);
		}

	}

	private static void drawOnScreen(JPanel panelD) {
		if (myCoord.size() > 1) {
			// drawObstacles(panelD);

			panelD.getGraphics().fillOval(myCoord.get(0).width / 2, myCoord.get(0).height / 2, 10, 10);
			for (int i = 1; i < myCoord.size(); i++) {
				panelD.getGraphics().drawLine(myCoord.get(i - 1).width / 2, myCoord.get(i - 1).height / 2,
						myCoord.get(i).width / 2, myCoord.get(i).height / 2);
				panelD.getGraphics().fillOval(myCoord.get(i).width / 2, myCoord.get(i).height / 2, 10, 10);
			}
		}

	}

	private static void drawObstacles(JPanel panelD) {

		panelD.getGraphics().drawOval(480 - 435, 525, 30, 30);
		panelD.getGraphics().drawPolygon(new int[] { 480 - 295, 480 - 260, 480 - 312, 480 - 330 },
				new int[] { 390, 375, 333, 350 }, 4);
		panelD.getGraphics().drawPolygon(new int[] { 480 - 373, 480 - 382, 480 - 443, 480 - 443 },
				new int[] { 473, 400, 370, 512 }, 4);
		panelD.getGraphics().drawPolygon(new int[] { 480 - 312, 480 - 330, 480 - 400, 480 - 392 },
				new int[] { 333, 350, 323, 311 }, 4);

		panelD.getGraphics().drawPolygon(new int[] { 480 - 443, 480 - 430, 480 - 400, 480 - 392, 480 - 430 },
				new int[] { 370, 377, 323, 311, 323 }, 5);

	}

	static int[] searchByPrevious(BufferedImage img, int prevX, int prevY) {
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

	static int[] search_first(BufferedImage img) {
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

	static boolean search(int clr) {

		int r = (clr & 0x00ff0000) >> 16;
		int g = (clr & 0x0000ff00) >> 8;
		int b = clr & 0x000000ff;
		if (r > valR - seuilR && r < valR + seuilR && g > valG - seuilG && g < valG + seuilG && b > valB - seuilB
				&& b < valB + seuilB) {
			return true;
		}
		return false;
	}

}
