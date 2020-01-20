import java.awt.Polygon;

@SuppressWarnings("serial")
public class aPolygon extends Polygon{

	boolean active = false;
	String descr = "";
	int points = 1 ;
	
	public aPolygon(String str, int point, int[] is, int[] is2, int i) {
		super(is, is2, i);
		descr = str;
		this.points = point;
	}
	
	public String toString() {
		return "["+descr+"] "+super.toString();
	}

}
