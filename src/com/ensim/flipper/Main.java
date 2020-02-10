package com.ensim.flipper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class Main
{
	private static final ImageIcon WINDOW_TRACE_BACKGROUND = new ImageIcon("resources/background/trace_bg.png");
	private static final ImageIcon WINDOW_UP_BACKGROUND = new ImageIcon("resources/background/background.png");
	private static final ImageIcon WINDOW_DOWN_BACKGROUND = new ImageIcon("resources/background/background_bottom.png");
	private static final  int WIDTH = 1920, HEIGHT = 1080;
	private static final int NB_MAX_BALL_POS = 25;
	
	private static final String FULL_ZEROS = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000";

	private static List<Dimension> ballCoordinates = new ArrayList<Dimension>();
	private static int nbBallPos = 0;
	
	public static WebcamPanel webcamPanel;
	private static PinballBasePanel tracePanel = new PinballBasePanel();
	public static PinballBasePanel downDisplayPanel = new PinballBasePanel();
	public static PinballBasePanel upDisplayPanel = new PinballBasePanel();
	public static JLabel scoreLabel, messageLabel, nbBallLabel, multiplierLabel, missionLabel, missionPointsLabel, rankLabel;
	public static Game game;

	static int nbBalle = 0;

	public static void main(String[] args)
	{
		nbBallLabel = createLabel(FULL_ZEROS, WIDTH * 1 / 20, HEIGHT * 8 / 9, 40);
		scoreLabel = createLabel("Score : 00000000000000000000", WIDTH * 5 / 20, HEIGHT * 8 / 9, 40);
		multiplierLabel = createLabel(FULL_ZEROS, WIDTH * 11 / 21, HEIGHT * 8 / 9, 40);
		rankLabel = createLabel(FULL_ZEROS, WIDTH * 16 / 20, HEIGHT * 8 / 9, 40);
		
		missionLabel = createLabel(FULL_ZEROS, WIDTH / 3, HEIGHT / 3, 30);
		missionPointsLabel = createLabel(FULL_ZEROS, WIDTH / 3, HEIGHT / 3 + 60, 30);
		
		messageLabel = createLabel(FULL_ZEROS, WIDTH / 3, HEIGHT / 3 + 180, 30);
		
		// Webcam configuration
		Webcam webcam = chooseWebcam();
		webcam.getDevice().setResolution(new Dimension(1280, 720));
		
		// Jpanel webcam
		webcamPanel = new WebcamPanel(webcam);
		webcamPanel.setFPSDisplayed(true);
		webcamPanel.setFillArea(true);
		
		webcamPanel.addMouseListener(new MouseListener() 
		{
			@Override
			public void mouseClicked(MouseEvent event)
			{
				AreaMaker.addPoint(new Point(event.getX(), event.getY()));
				AreaMaker.drawPoints(webcamPanel.getGraphics());
				System.out.println("[Main] posX : " + event.getX() + ", posY : " + event.getY());
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}			
		});
		
		// Fenetre webcam
		JFrame cameraWindow = new JFrame("Camera");
		cameraWindow.add(webcamPanel);
		cameraWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cameraWindow.pack();
		cameraWindow.setVisible(true);
		
		cameraWindow.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					AreaMaker.displayPoints();
					AreaMaker.clearAllPoints();
				}
				
				else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					AreaMaker.clearAllPoints();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {}
		});

		createWindow("Trace", WINDOW_TRACE_BACKGROUND, tracePanel, 720 / 2, 1280 / 2);
		createWindow("Affichage haut", WINDOW_UP_BACKGROUND, upDisplayPanel);
		createWindow("Affichage bas", WINDOW_DOWN_BACKGROUND, downDisplayPanel);

		upDisplayPanel.setLayout(null);
		
		downDisplayPanel.setLayout(null);
		downDisplayPanel.add(scoreLabel);
		downDisplayPanel.add(missionLabel);
		downDisplayPanel.add(missionPointsLabel);
		downDisplayPanel.add(nbBallLabel);
		downDisplayPanel.add(multiplierLabel);
		downDisplayPanel.add(rankLabel);
		downDisplayPanel.add(messageLabel);
		
		tracePanel.setXY(5, 25);
		
		game = new Game();

		messageLabel.setText("Pinball Space K'det");		
		missionPointsLabel.setText("");
		
		nbBallLabel.setText("");
		rankLabel.setText("");
		scoreLabel.setText("Score : 0");		
		missionLabel.setText("Launch to start new game !");		
		multiplierLabel.setText("");
		
		downDisplayPanel.repaint();

		// init localisation balle
		int[] loc = new int[2];
		loc[0] = 0;
		loc[1] = 0;
		
		Game.ARROW_MISSION.setActive(true);
		
		while (true)
		{
			if (nbBalle > 0)
			{
				BallPositionUtil.searchNplay(webcam.getImage());
			}
			else
			{
				BallPositionUtil.waitPartie(webcam.getImage());
			}

			drawOnScreen(tracePanel);
			
			Main.game.updateTicks();
		}
	}

	public static void drawPosBalle(int x, int y)
	{
		webcamPanel.getGraphics().drawRect(x, y, 10, 10);
		drawOnScreen(tracePanel, x, y);
	}

	public static void refreshLabel()
	{
		rankLabel.setText(Game.missionManager.getRang());
		missionPointsLabel.setText(Game.missionManager.scoreInfos);
		missionLabel.setText(Game.missionManager.instructions);
		multiplierLabel.setText("Score Multiplier : x" + game.multiplier);
		scoreLabel.setText("Score : " + game.scoreGame);
		nbBallLabel.setText(nbBalle + " Balls left");
		
		downDisplayPanel.repaint();
	}

	private static void drawOnScreen(JPanel panel, int x, int y)
	{
		ballCoordinates.add(new Dimension(720 - y, x));
		if (ballCoordinates.size() > 15)
		{
			ballCoordinates.remove(0);
		}
		drawOnScreen(panel);

		if (nbBallPos++ > NB_MAX_BALL_POS)
		{
			panel.repaint();
			nbBallPos = 0;
		}
	}

	private static void drawOnScreen(JPanel panel)
	{
		if (ballCoordinates.size() > 1)
		{
			panel.getGraphics().fillOval(ballCoordinates.get(0).width / 2, ballCoordinates.get(0).height / 2, 10, 10);
			
			for (int i = 1; i < ballCoordinates.size(); i++)
			{
				panel.getGraphics().drawLine(ballCoordinates.get(i - 1).width / 2, ballCoordinates.get(i - 1).height / 2, ballCoordinates.get(i).width / 2, ballCoordinates.get(i).height / 2);
				panel.getGraphics().fillOval(ballCoordinates.get(i).width / 2, ballCoordinates.get(i).height / 2, 10, 10);
			}
		}

	}

	private static Webcam chooseWebcam()
	{
		for (Webcam webcam : Webcam.getWebcams())
		{
			if (webcam.toString().toLowerCase().contains("razer"))
			{
				return webcam;
			}
		}
		
		System.err.println("Webcam Razer not found");
		return Webcam.getWebcams().get(0);
	}

	public static void repaint()
	{
		Main.downDisplayPanel.paintComponent(null);
		Main.downDisplayPanel.repaint();
		Main.upDisplayPanel.paintComponent(null);
		Main.upDisplayPanel.repaint();
	}
	
	private static JLabel createLabel(String text, int posX, int posY, int size)
	{
		JLabel label = new JLabel(text);
		label.setForeground(Color.white);
		label.setFont(new Font("squaredance00", label.getFont().getStyle(), size));
		label.setLocation(posX, posY);
		label.setSize(label.getPreferredSize());
		
		return label;
	}
	
	private static JFrame createWindowBase(String name, ImageIcon icon, PinballBasePanel panel)
	{
		JFrame window = new JFrame(name);
		BufferedImage img = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = img.createGraphics();
		graphics.drawImage(icon.getImage(), 0, 0, icon.getImageObserver());
		panel.setBackroundImg(img);
		panel.paintComponent(graphics);
		window.add(panel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		return window;
	}
	
	private static JFrame createWindow(String name, ImageIcon icon, PinballBasePanel panel, int xSize, int ySize)
	{
		JFrame window = createWindowBase(name, icon, panel);
		window.setPreferredSize(new Dimension(xSize, ySize));
		window.pack();
		window.setVisible(true);
		return window;
	}
	
	private static JFrame createWindow(String name, ImageIcon icon, PinballBasePanel panel)
	{
		JFrame window = createWindowBase(name, icon, panel);
		window.setPreferredSize(new Dimension(1880, 1400));
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.pack();
		window.setVisible(true);
		return window;
	}
}
