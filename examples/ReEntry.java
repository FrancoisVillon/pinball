import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ReEntry extends aPolygon {

	public ReEntry(JPanel jpa, String str,int point, int[] is, int[] is2, int i, int x, int y) {
		super(jpa, str, "Pass in re-entry",point+" pts", point, is, is2, i, x, y, new ImageIcon("/home/nicolas/Bureau/reentry.png"));
	}
	

	public void active() {
		allumer();
	}
}
