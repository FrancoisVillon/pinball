import java.util.ArrayList;
import java.util.Map.Entry;

public class Mission {
	ArrayList<Step> list = new ArrayList<>();
	int index;
	String phrase;
	String phrasePoint;
	boolean isDone;

	int scorePreStep = 300;
	int scoreStep = 500;
	int scoreEnd = 1200;

	public Mission(int score1, int score2, int score3) {
		this( new ArrayList<>(), score1, score2, score3);
	}

	public Mission(ArrayList<Step> list, int scorePreStep, int scoreStep, int scoreEnd) {
		this.scorePreStep = scorePreStep;
		this.scoreStep = scoreStep;
		this.scoreEnd = scoreEnd;
		init();
		this.list = list;
	}

	public void add(Step s) {
		list.add(s);
	}

	public void init() {
		index = -2;
		phrase = "Select mission with left traget";
		phrasePoint = "";
		isDone = false;
	}

	public void addFinal() {
		add("target droit", "Hit right target to conclude");
	}

	public void add(String area, String str) {
		if (validateArea(area)) {
			list.add(new Step(area, str));
		}else {
			System.out.println("Ajout impossible, msg : "+area);
		}
	}

	private boolean validateArea(String area) {
		for (Entry<String, aPolygon> entry : aMyTest.panelA.map.entrySet()) {
			aPolygon pol = entry.getValue();
			if(pol.descr.contains(area)) {
				return true;
			}
		}
		return false;
	}

	public int isValidate(aPolygon pol) {
		System.out.println("-> validate...");
		System.out.println("-> pol : " + pol.descr);
		System.out.println("-> index : " + index);
		if (index == -2) {
			if (pol.descr.contains("target gauche")) {
				phrase = "Launch in ramp to validate";
				aMyTest.refreshLabel();
				index++;
				return scorePreStep;
			}
		} else if (index == -1) {
			if (pol.descr.contains("ramp")) {
				index++;
				phrase = list.get(index).phrase;
				aMyTest.refreshLabel();
				return scorePreStep;
			}
		} else {
			if (pol.descr.contains(list.get(index).area)) {
				index++;
				if (index >= list.size()) {
					phrase = "Congratulations";
					aMyTest.refreshLabel();
					isDone = true;
					return scoreEnd;
				}
				phrase = list.get(index).phrase;
				aMyTest.refreshLabel();
				return scoreStep;
			}
		}
		return 0;
	}

	public String getPhrasePoint() {
		System.out.println("index : " + index);
		if (index == -2) {
			return "";
		} else if (index >= list.size()) {
			return "Mission done : " + scoreEnd + "pts";
		} else if (index <= 0) {
			return "Won " + scorePreStep + "pts";
		} else {
			return "Won " + scoreStep + "pts";
		}
	}

}
