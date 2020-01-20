import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class aPolygon extends Polygon {

	private boolean active = false;
	String descr = "";
	int points = 1;
	int x, y;
	ImageIcon img;

	public aPolygon(String str, int point, int[] is, int[] is2, int i, int x, int y, ImageIcon img) {
		super(is, is2, i);
		// aMyTest.panelA.map.put(str, false);
		descr = str;
		this.img = img;
		this.points = point;
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "[" + descr + "] " + super.toString();
	}

	public boolean action(aPolygon prev_area) {
		return !this.equals(prev_area);
	}

	public void active() {
		if (active) {
			active = false;
		} else {
			active = true;
		}
	}

	public void allumer() {
		active = true;
	}
	public void eteindre() {
		active = false;
	}

	public void draw(Graphics g) {
		g.drawOval(xpoints[0], ypoints[0], 10, 10);
		for (int i = 1; i < xpoints.length; i++) {
			g.drawOval(xpoints[i], ypoints[i], 10, 10);
			g.drawLine(xpoints[i - 1], ypoints[i - 1], xpoints[i], ypoints[i]);
		}
	}

	public boolean isActive() {
		return active;
	}

}
