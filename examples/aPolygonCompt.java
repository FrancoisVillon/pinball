import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class aPolygonCompt extends aPolygon {

	public aPolygonCompt(JPanel jpa, String str, String phrase, String phraseScore,int point, int[] is, int[] is2, int i, int x, int y, ImageIcon img) {
		super(jpa,str,phrase,phraseScore, point, is, is2, i, x, y, img);
	}

	public boolean action(aPolygon prevArea) {
		return true;

	}
}
