import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class aMyTest {

	static List<Dimension> myCoord = new ArrayList<Dimension>();
	static int count = 0;
	static WebcamPanel panel;
	static aMyJPanel panelD, panelA;
	static JLabel label;
	static aGame game;

	public static void main(String[] args) {

		game = new aGame();

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
		window2.setVisible(true);

		// Fenetre affichage
		JFrame window3 = new JFrame("Affichage");
		panelA = new aMyJPanel();

		ImageIcon fond = new ImageIcon("/home/nicolas/Bureau/aff.png");
		BufferedImage bimgf = new BufferedImage(fond.getIconWidth(), fond.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D ggf = bimgf.createGraphics();
		ggf.drawImage(fond.getImage(), 0, 0, fond.getImageObserver());
		panelA.setImage(bimgf);
		panelA.paintComponent(ggf);

		// Afficahge score
		label = new JLabel("Score : 0000000000" + game.score);
		label.setForeground(Color.white);
		label.setFont(new Font("squaredance00", label.getFont().getStyle(), 40));
		label.setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4,
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 2 / 3);
		label.setSize(label.getPreferredSize());
		panelA.setLayout(null);
		panelA.add(label);

		window3.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//window3.setUndecorated(true);
		window3.add(panelA);
		window3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window3.pack();
		window3.setVisible(true);

		label.setText("Score : " + game.score);
		panelA.repaint();

		// init localisation balle
		int[] loc = new int[2];
		loc[0] = 0;
		loc[1] = 0;

		while (true) {

			try {
				game.searchNplay(webcam.getImage());
				/*
				 * loc = game.searchByPrevious(webcam.getImage(), loc[0], loc[1]); if (loc[0] ==
				 * -1 && loc[1] == -1) { // System.out.println("Not found"); } else { //
				 * System.out.println(" - > Found (x : " + loc[0] + ", y : " + loc[1]);
				 * panel.getGraphics().drawRect(loc[0], loc[1], 10, 10); drawOnScreen(panelD,
				 * loc[0], loc[1]); if (game.verify(loc[0], loc[1])) {
				 * System.out.println("repaint"); label.setText("Score : " + game.score);
				 * panelA.repaint();
				 * 
				 * } }
				 */

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

	public static void refreshScoreLabel() {

		label.setText("Score : " + game.score);
		panelA.repaint();
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
		for( Webcam webcam : Webcam.getWebcams()) {
			if(webcam.toString().contains("Razer")) {
				return webcam;
			}
		}
		System.err.println("Webcam Razer not found");
		System.out.println("Webcam Razer not found");
		return Webcam.getWebcams().get(0);
	}
}
