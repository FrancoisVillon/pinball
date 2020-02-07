import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Step {

	String area;
	String phrase;
	int points, x, y;
	JPanel jpa;
	ImageIcon img;
	
	
	public Step(String area, String phrase, int points, int x, int y, JPanel jpa, ImageIcon img) {
		this.area = area;
		this.phrase = phrase;
		this.points = points;
		this.x = x;
		this.y = y;
		this.jpa = jpa;
		this.img = img;
	}
	
	public Step(String  area, String phrase, int points, AffMission miss) {
		this(area, phrase, points, miss.x, miss.y, miss.jpa, miss.img);
	}
	
	public Step(String area, String phrase, int points) {
		this(area, phrase, points, 50, 50, aMyTest.panelA2, new ImageIcon("/home/nicolas/Bureau/mission.png"));

	}
	
}
