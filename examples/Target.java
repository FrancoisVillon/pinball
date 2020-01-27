import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Target extends aPolygon {

	public Target(JPanel jpa, String str,int point, int[] is, int[] is2, int i, int x, int y) {
		super(jpa, str, "Hit target",point+" pts", point, is, is2, i, x, y, new ImageIcon("/home/nicolas/Bureau/target.png"));
	}
	public Target(JPanel jpa, String str,int point, int[] is, int[] is2, int i, int x, int y, int imgNum) {
		super(jpa, str, "Hit target",point+" pts", point, is, is2, i, x, y, new ImageIcon("/home/nicolas/Bureau/target"+imgNum+".png"));
	}
	

	public void active() {
		if(isActive()) {
			eteindre();
		}else {
			allumer();
		}
	}
}
