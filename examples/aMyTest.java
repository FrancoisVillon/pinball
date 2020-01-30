import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class aMyTest {

	static int WIDTH = 1920, HEIGHT = 1080;

	static HashMap<String, aPolygon> map = new HashMap<>();

	static List<Dimension> myCoord = new ArrayList<Dimension>();
	static int count = 0;
	static WebcamPanel panel;
	static aMyJPanel panelD, panelA, panelA2;
	static JLabel labelS, labelM, labelMP, labelB, labelMulti, labelMiss, labelMissP, labelR;
	static aGame game;

	static int nbBalle = 0;

	public static void main(String[] args) {

		// Webcam configuration
		Webcam webcam = chooseWebcam();
		Dimension dim = new Dimension(1280, 720);
		webcam.getDevice().setResolution(dim);

		// Jpanel webcam
		panel = new WebcamPanel(webcam);
		panel.setFPSDisplayed(true);
		panel.setFillArea(true);

		// Fenetre webcam
		JFrame window = new JFrame("Camera"); //
		window.add(panel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);

		// Fenetre tracé/suivi balle
		JFrame window2 = new JFrame("Tracé");
		panelD = new aMyJPanel();

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
		// window2.setVisible(true);

		// Fenetre affichage2 (haut)
		JFrame window4 = new JFrame("Affichage haut");
		panelA2 = new aMyJPanel();
		panelA2.setLayout(null);


		ImageIcon fond2 = new ImageIcon("/home/nicolas/Bureau/aff2.png");
		BufferedImage bimgf2 = new BufferedImage(fond2.getIconWidth(), fond2.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics2D ggf2 = bimgf2.createGraphics();
		ggf2.drawImage(fond2.getImage(), 0, 0, fond2.getImageObserver());
		panelA2.setImage(bimgf2);
		panelA2.paintComponent(ggf2);

		// Fenetre affichage (bas)
		JFrame window3 = new JFrame("Affichage bas");
		panelA = new aMyJPanel();

		ImageIcon fond = new ImageIcon("/home/nicolas/Bureau/aff.png");
		BufferedImage bimgf = new BufferedImage(fond.getIconWidth(), fond.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D ggf = bimgf.createGraphics();
		ggf.drawImage(fond.getImage(), 0, 0, fond.getImageObserver());
		panelA.setImage(bimgf);
		panelA.paintComponent(ggf);

		// Afficahge score
		labelS = new JLabel("Score : 0000000000000000000000");
		labelS.setForeground(Color.white);
		labelS.setFont(new Font("squaredance00", labelS.getFont().getStyle(), 40));
		labelS.setLocation(WIDTH / 4, HEIGHT * 2 / 3);
		labelS.setSize(labelS.getPreferredSize());
		panelA.setLayout(null);
		panelA.add(labelS);

		// Affichage mission
		labelMiss = new JLabel("0000000000000000000000000000000000000000000000000000000000000000000000000000000000");
		labelMiss.setForeground(Color.white);
		labelMiss.setFont(new Font("squaredance00", labelMiss.getFont().getStyle(), 30));
		labelMiss.setLocation(WIDTH / 2, HEIGHT / 3);
		labelMiss.setSize(labelMiss.getPreferredSize());
		panelA.add(labelMiss);

		// Affichage points mission
		labelMissP = new JLabel("0000000000000000000000000000000000000000000000000000000000000000000000000000000000");
		labelMissP.setForeground(Color.white);
		labelMissP.setFont(new Font("squaredance00", labelMissP.getFont().getStyle(), 30));
		labelMissP.setLocation(WIDTH / 2, HEIGHT / 3 + 2 * labelMissP.getFont().getSize());
		labelMissP.setSize(labelMissP.getPreferredSize());
		panelA.add(labelMissP);

		// Afficahge message
		labelM = new JLabel("0000000000000000000000000000000000000000000000000000000000000000000000000000000000");
		labelM.setForeground(Color.white);
		labelM.setFont(new Font("squaredance00", labelM.getFont().getStyle(), 30));
		labelM.setLocation(WIDTH * 2 / 3, HEIGHT / 2);
		labelM.setSize(labelM.getPreferredSize());
		panelA2.add(labelM);

		// Afficahge message points
		labelMP = new JLabel("0000000000000000000000000000000000000000000000000000000000000000000000000000000000");
		labelMP.setForeground(Color.white);
		labelMP.setFont(new Font("squaredance00", labelMP.getFont().getStyle(), 30));
		labelMP.setLocation(WIDTH * 2 / 3, HEIGHT / 2 + labelMP.getFont().getSize() * 4);
		labelMP.setSize(labelMP.getPreferredSize());
		panelA2.add(labelMP);

		// Afficahge nb balle
		labelB = new JLabel("0000000000000000000000000000000000000000000000000000000000000000000000000000000000");
		labelB.setForeground(Color.white);
		labelB.setFont(new Font("squaredance00", labelB.getFont().getStyle(), 30));
		labelB.setLocation(WIDTH * 1 / 20, HEIGHT * 8 / 9);
		labelB.setSize(labelB.getPreferredSize());
		panelA.add(labelB);

		// Afficahge rang
		labelR = new JLabel("0000000000000000000000000000000000000000000000000000000000000000000000000000000000");
		labelR.setForeground(Color.white);
		labelR.setFont(new Font("squaredance00", labelR.getFont().getStyle(), 30));
		labelR.setLocation(WIDTH * 17 / 20, HEIGHT * 8 / 9);
		labelR.setSize(labelR.getPreferredSize());
		panelA2.add(labelR);

		// Afficahge mulitplier
		labelMulti = new JLabel("0000000000000000000000000000000000000000000000000000000000000000000000000000000000");
		labelMulti.setForeground(Color.white);
		labelMulti.setFont(new Font("squaredance00", labelMulti.getFont().getStyle(), 30));
		labelMulti.setLocation(WIDTH * 18 / 20, HEIGHT * 8 / 9);
		labelMulti.setSize(labelMulti.getPreferredSize());
		panelA.add(labelMulti);


		
		window4.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// window4.setUndecorated(true);
		window4.add(panelA2);
		window4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window4.pack();
		window4.setVisible(true);
		
		
		window3.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// window3.setUndecorated(true);
		window3.add(panelA);
		window3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window3.pack();
		window3.setVisible(true);
		
		
		game = new aGame();

		// label.setText("Score : 0");
		labelS.setText("");
		labelM.setText("Pinball Space semi cadet");
		labelMP.setText("");
		// labelB.setText(""+nbBalle);
		// labelMulti.setText(game.multiplier+"x");
		labelB.setText("");
		labelR.setText("");
		labelS.setText("Let's go !");
		labelMiss.setText("");
		labelMissP.setText("");
		labelMulti.setText("");
		panelA.repaint();

		// init localisation balle
		int[] loc = new int[2];
		loc[0] = 0;
		loc[1] = 0;
		
		while (true) {

			try {
				if (nbBalle > 0) {
					game.searchNplay(webcam.getImage());
				} else {
					game.waitPartie(webcam.getImage());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			drawOnScreen(panelD);
		}

	}

	public static void drawPosBalle(int x, int y) {
		panel.getGraphics().drawRect(x, y, 10, 10);
		drawOnScreen(panelD, x, y);
	}

	public static void drawTarget(int x, int y, boolean active) {
		if (active) {
			/*
			 * ImageIcon imic = new ImageIcon("/home/nicolas/Bureau/target.png");
			 * panelA.getGraphics().drawImage(imic.getImage(), 220, 40, 52, 113,
			 * imic.getImageObserver());// 125, 30, 39,85 panelA.getGraphics().drawRect(223,
			 * 45, 43, 110);
			 */
		} else {
			panelA.repaint();
		}
	}

	public static void refreshLabel() {
		// System.out.println("je suis passé...");
		// labelMissP.setText("");
		labelR.setText(game.missionManager.getRang());
		labelMissP.setText(game.missionManager.phrasePoint);
		labelMiss.setText(game.missionManager.phrase);
		labelMulti.setText(game.multiplier + "x");
		labelS.setText("Score : " + game.score_total);
		if (game.score < game.SCORE_LIMIT_REDP) {
			labelB.setText("" + nbBalle + " (D)");
		} else {
			labelB.setText("" + nbBalle);
		}
		panelA.repaint();
	}

	public static void affPointsMission() {
		// System.out.println("points mission affichees");
		// labelMissP.setText(game.getPointMission());
		// panelA.repaint();
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

	@SuppressWarnings("unused")
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

	private static Webcam chooseWebcam() {
		for (Webcam webcam : Webcam.getWebcams()) {
			if (webcam.toString().contains("Razer")) {
				return webcam;
			}
		}
		System.err.println("Webcam Razer not found");
		System.out.println("Webcam Razer not found");
		return Webcam.getWebcams().get(0);
	}

	public static void repaint() {

		aMyTest.panelA.paintComponent(null);
		aMyTest.panelA.repaint();
		aMyTest.panelA2.paintComponent(null);
		aMyTest.panelA2.repaint();

		// System.out.println("multi : "+panel);

	}
}
