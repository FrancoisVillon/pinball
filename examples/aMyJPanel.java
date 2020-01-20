import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class aMyJPanel extends JPanel {
	BufferedImage image;
	 int x = 0, y = 0;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
}