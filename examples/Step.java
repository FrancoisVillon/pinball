import javax.swing.JPanel;

public class Step {

	String area;
	String phrase;
	int points, x, y;
	JPanel jpa;
	
	public Step(String area, String phrase, int points, int x, int y, JPanel jpa) {
		this.area = area;
		this.phrase = phrase;
		this.points = points;
		this.x = x;
		this.y = y;
		this.jpa = jpa;
	}
	
	public Step(String area, String phrase, int points) {
		this(area, phrase, points, 50, 50, aMyTest.panelA2);

	}
	
}
