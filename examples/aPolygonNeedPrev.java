import java.util.ArrayList;

@SuppressWarnings("serial")
public class aPolygonNeedPrev extends aPolygon {

	private ArrayList<aPolygon> prevAreaList;

	public aPolygonNeedPrev(String str, int point, int[] is, int[] is2, int i, ArrayList<aPolygon> prevAreaList) {
		super(str, point, is, is2, i);
		this.prevAreaList = prevAreaList;
	}

	public boolean action(aPolygon prevArea) {
		System.out.println(descr);
		System.out.println(prevAreaList);
		System.out.println(prevAreaList.contains(prevArea));
		return prevAreaList.contains(prevArea);

	}

}
