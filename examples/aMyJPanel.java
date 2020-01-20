import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;

import sun.java2d.pipe.DrawImage;

@SuppressWarnings("serial")
class aMyJPanel extends JPanel {
	BufferedImage image;
	int x = 0, y = 0;

	// ArrayList<aPolygon> list = new ArrayList<>();
	HashMap<String, aPolygon> map = new HashMap<>();

	public void paintComponent(Graphics g) {
		if (!(g == null)) {
			super.paintComponent(g);
			if (image != null) {
				g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		}
		try {
			for (Entry<String, aPolygon> entry : map.entrySet()) {
				aPolygon pol = entry.getValue();
				// for (aPolygon pol : map) {
				if (pol.isActive() && pol.x != -1) {
					//System.out.println(pol.img.equals(null));
					g.drawImage(pol.img.getImage(), pol.x, pol.y, pol.img.getIconWidth(), pol.img.getIconHeight(),
							pol.img.getImageObserver());// 125, 30, 39,85
					//g.drawRect(pol.x, pol.y, pol.img.getIconWidth(), pol.img.getIconHeight());
				}
			}
		} catch (Exception e) {
			/*System.out.println("ERR : ");
			e.printStackTrace();*/
		}

	}

	public void repaint() {
		super.repaint();
		/*
		 * try { for (aPolygon pol : list) { if (pol.isActive() && pol.x != -1) {
		 * getGraphics().drawImage(pol.img.getImage(), pol.x, pol.y, 52, 113,
		 * pol.img.getImageObserver()); getGraphics().drawRect(pol.x, pol.y, 43, 110); }
		 * } } catch (Exception e) { System.out.println("ERR"); }
		 */
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
}