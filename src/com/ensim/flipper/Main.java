package com.ensim.flipper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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

import com.ensim.flipper.area.Area;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class Main
{
	private static final ImageIcon WINDOW_UP_BACKGROUND = new ImageIcon("resources/background/background.png");
	private static final ImageIcon WINDOW_DOWN_BACKGROUND = new ImageIcon("resources/background/background_bottom.png");
	private static final ImageIcon ICON = new ImageIcon("resources/icon/icon_32.png");
	private static final Color BLACK = new Color(0);
	private static final Color RED = new Color(200, 0, 0);
	private static final  int WIDTH = 1920, HEIGHT = 1080;
	private static final int NB_MAX_BALL_POS = 25;
	
	//Labels must be initialized with the maximum of char they can contains, that's why FULL_ZEROS is here
	private static final String FULL_ZEROS = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000";

	private static List<Point> ballCoordinates = new ArrayList<>();
	
	private static Webcam webcam;
	
	private static WebcamPanel webcamPanel;
	public static PinballBasePanel downDisplayPanel = new PinballBasePanel();
	public static PinballBasePanel upDisplayPanel = new PinballBasePanel();
	public static JLabel scoreLabel, messageLabel, nbBallLabel, multiplierLabel, missionLabel, missionPointsLabel, rankLabel;
	public static Game game;

	static int nbBalle = 0;

	public static void main(String[] args)
	{
		initAllWindows();
		
		game = new Game();
		Game.ARROW_MISSION.setActive(true);
		
		while (true)
		{
			if (nbBalle > 0)
			{
				BallPositionUtil.searchNplay(webcam.getImage());
				Main.game.updateTicks();
			}
			else
			{
				BallPositionUtil.waitForNewGame(webcam.getImage());
			}

			drawBallTrace(webcamPanel.getGraphics());
			
			if(webcamPanel.getGraphics() != null)
			{
				webcamPanel.getGraphics().setColor(BLACK);
			}
			
			for(Area area : Game.AREAS)
			{
				area.draw(Main.webcamPanel.getGraphics());
			}
			
			AreaMaker.drawPoints(webcamPanel.getGraphics());
		}
	}
	
	/**
	 * Create and initialize all windows and labels
	 */
	private static void initAllWindows()
	{
		nbBallLabel = createLabel(FULL_ZEROS, WIDTH * 1 / 20, HEIGHT * 8 / 9, 40);
		scoreLabel = createLabel("Score : 00000000000000000000", WIDTH * 5 / 20, HEIGHT * 8 / 9, 40);
		multiplierLabel = createLabel(FULL_ZEROS, WIDTH * 11 / 21, HEIGHT * 8 / 9, 40);
		rankLabel = createLabel(FULL_ZEROS, WIDTH * 16 / 20, HEIGHT * 8 / 9, 40);
		
		missionLabel = createLabel(FULL_ZEROS, WIDTH / 3, HEIGHT / 3, 30);
		missionPointsLabel = createLabel(FULL_ZEROS, WIDTH / 3, HEIGHT / 3 + 60, 30);
		
		messageLabel = createLabel(FULL_ZEROS, WIDTH / 3, HEIGHT / 3 + 180, 30);
		
		// Webcam config
		webcam = chooseWebcam();
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
		
		// Webcam window
		JFrame cameraWindow = new JFrame("Camera");
		cameraWindow.add(webcamPanel);
		cameraWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cameraWindow.setIconImage(ICON.getImage());
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

		messageLabel.setText("Pinball Space K'det");		
		missionPointsLabel.setText("");
		
		nbBallLabel.setText("");
		rankLabel.setText("");
		scoreLabel.setText("Score : 0");		
		missionLabel.setText("Launch to start new game !");		
		multiplierLabel.setText("");
		
		downDisplayPanel.repaint();
	}

	/**
	 * Refresh labels' content
	 */
	public static void refreshLabel()
	{
		if(Main.nbBalle != 0)
		{
			missionLabel.setText(Game.missionManager.instructions);
			missionPointsLabel.setText(Game.missionManager.scoreInfos);
		}
		
		multiplierLabel.setText("Multiplier : x" + game.multiplier);
		scoreLabel.setText("Score : " + game.getScore());
		nbBallLabel.setText(nbBalle + " Balls left");
		rankLabel.setText(Game.missionManager.getRank());
		
		downDisplayPanel.repaint();
	}

	/**
	 * Draw ball's coordinates on screen
	 * @param x, y : coordinates of the ball
	 */
	public static void drawBallPos(int x, int y)
	{
		webcamPanel.getGraphics().setColor(RED);
		drawAndUpdateBallTrace(webcamPanel.getGraphics(), x, y);

		webcamPanel.getGraphics().drawRect(x, y, 10, 10);
	}

	/**
	 * Draw ball's new coordinates on screen, and remove the older ones
	 * @param panel : The panel where coordinates are drawn
	 * @param x, y : coordinates of the ball
	 */
	private static void drawAndUpdateBallTrace(Graphics g, int x, int y)
	{
		ballCoordinates.add(new Point(x, y));
		if (ballCoordinates.size() > NB_MAX_BALL_POS)
		{
			ballCoordinates.remove(0);
		}
		drawBallTrace(g);
	}

	/**
	 * Draw ball's trace on screen
	 * @param panel : The panel where coordinates are drawn
	 */
	private static void drawBallTrace(Graphics g)
	{
		if(g != null)
		{
			g.setColor(RED);
			
			if (ballCoordinates.size() > 1)
			{
				g.fillOval(ballCoordinates.get(0).x, ballCoordinates.get(0).y, 8, 8);
				
				for (int i = 1; i < ballCoordinates.size(); i++)
				{
					g.drawLine(ballCoordinates.get(i - 1).x, ballCoordinates.get(i - 1).y, ballCoordinates.get(i).x, ballCoordinates.get(i).y);
					g.fillOval(ballCoordinates.get(i).x, ballCoordinates.get(i).y, 8, 8);
				}
			}
		}
	}

	/**
	 * @return The chosen webcam
	 */
	private static Webcam chooseWebcam()
	{
		for(Webcam webcam : Webcam.getWebcams())
		{
			if(webcam.toString().toLowerCase().contains("razer"))
			{
				return webcam;
			}
		}
		
		System.out.println("[Main/Warning] Webcam Razer not found");
		return Webcam.getWebcams().get(0);
	}

	/**
	 * Refresh game screens
	 */
	public static void repaint()
	{
		Main.downDisplayPanel.repaint();
		Main.upDisplayPanel.repaint();
	}
	
	/**
	 * Create a label
	 * @param text : text displayed on the label
	 * @param posX : X coordinate of the label
	 * @param posY : Y coordinate of the label
	 * @param size : size of text's font
	 * @return
	 */
	private static JLabel createLabel(String text, int posX, int posY, int size)
	{
		JLabel label = new JLabel(text);
		label.setForeground(Color.white);
		label.setFont(new Font("squaredance00", label.getFont().getStyle(), size));
		label.setLocation(posX, posY);
		label.setSize(label.getPreferredSize());
		
		return label;
	}

	/**
	 * Create a window
	 * 
	 * @param name : Window's name
	 * @param background : Background image of the window
	 * @param panel : Panel of the window
	 * @return The window
	 */
	private static JFrame createWindow(String name, ImageIcon background, PinballBasePanel panel)
	{
		JFrame window = new JFrame(name);
		BufferedImage img = new BufferedImage(background.getIconWidth(), background.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = img.createGraphics();
		graphics.drawImage(background.getImage(), 0, 0, background.getImageObserver());
		panel.setBackroundImg(img);
		panel.paintComponent(graphics);
		window.add(panel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setIconImage(ICON.getImage());
		window.setPreferredSize(new Dimension(1880, 1400));
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.pack();
		window.setVisible(true);
		return window;
	}
}
