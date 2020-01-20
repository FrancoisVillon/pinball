import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Target extends aPolygon {

	public Target(String str,int point, int[] is, int[] is2, int i, int x, int y) {
		super(str, "Hit target : "+point+" pts", point, is, is2, i, x, y, new ImageIcon("/home/nicolas/Bureau/target.png"));
	}
	

	public void active() {
		if(isActive()) {
			eteindre();
		}else {
			allumer();
		}
	}
}
