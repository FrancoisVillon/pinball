import java.util.ArrayList;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class aPolygonCompt extends aPolygon {

	public aPolygonCompt(String str, int point, int[] is, int[] is2, int i, int x, int y, ImageIcon img) {
		super(str, point, is, is2, i, x, y, img);
	}

	public boolean action(aPolygon prevArea) {
		return true;

	}
}
