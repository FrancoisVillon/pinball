import java.util.ArrayList;

public class Mission {
	ArrayList<Step> list = new ArrayList<>();
	int index = -2;
	String phrase = "Hit left traget";

	final int SCORE_PRE_STEP = 300;
	final int SCORE_STEP = 500;
	final int SCORE_END = 1200;

	public Mission() {
	}
	
	public Mission(ArrayList<Step> list) {
		this.list = list;
	}

	public void add(Step s) {
		list.add(s);
	}
	
	public void add(aPolygon pol, String str) {
		list.add(new Step(pol, str));
	}
	
	public int isValidate(aPolygon pol) {
		System.out.println("validate...");
		System.out.println("pol : "+pol.descr);
		System.out.println("index : "+index);
		if(index == -2) {
			if(pol.descr.contains("target gauche")) {
				phrase = "launch in ramp";
				aMyTest.refreshLabel();
				index++;
				return SCORE_PRE_STEP;
			}
		}else if(index == -1){
			if(pol.descr.contains("ramp")) {
				index++;
				phrase = list.get(index).phrase;
				aMyTest.refreshLabel();
				return SCORE_PRE_STEP;
			}
		}else {
			if(pol.descr.contains(list.get(index).area.descr)) {
				index++;
				if(index>=list.size()) {
					phrase = "Congratulations";
					aMyTest.refreshLabel();
					return SCORE_END;
				}
				phrase = list.get(index).phrase;
				aMyTest.refreshLabel();
				return SCORE_STEP;
			}
		}
		return -1;
	}

}
