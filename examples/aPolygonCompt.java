import java.util.ArrayList;

@SuppressWarnings("serial")
public class aPolygonCompt extends aPolygon {

	public aPolygonCompt(String str, int point, int[] is, int[] is2, int i) {
		super(str, point, is, is2, i);
	}

	public boolean action(aPolygon prevArea) {
		return true;

	}
}
