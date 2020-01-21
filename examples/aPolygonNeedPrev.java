import java.util.ArrayList;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class aPolygonNeedPrev extends aPolygon {

	private ArrayList<aPolygon> prevAreaList;

	public aPolygonNeedPrev(String str, String phrase, String phraseScore, int point, int[] is, int[] is2, int i, int x, int y,
			ImageIcon img, ArrayList<aPolygon> prevAreaList) {
		super(str, phrase,phraseScore, point, is, is2, i, x, y, img);
		this.prevAreaList = prevAreaList;
	}

	public boolean action(aPolygon prevArea) {
		System.out.println(descr);
		System.out.println(prevAreaList);
		System.out.println(prevAreaList.contains(prevArea));
		return prevAreaList.contains(prevArea);

	}

}
