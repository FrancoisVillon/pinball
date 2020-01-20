import java.awt.Graphics;
import java.util.HashMap;

@SuppressWarnings("serial")
public class aMyBackgroundPanel extends aMyJPanel {
	HashMap<String, aPolygon> aff = new HashMap<String, aPolygon>();

	public aMyBackgroundPanel() {
		super();
		// aff.put(, new aPolygon(new int[] { 223, 266, 227, 270 }, new int[] { 43, 42,
		// 153, 152 }, 4));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}


}
