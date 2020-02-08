import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Mission {
	ArrayList<Step> list = new ArrayList<>();
	int index = 0;
	boolean isDone;
	private String phrase, phrasePoint;

	int scoreEnd = 1200;

	public Mission(int scorePreStep, int scoreEnd) {
		this(new ArrayList<>(), scorePreStep, scoreEnd);
	}

	public Mission(ArrayList<Step> list, int scorePreStep, int scoreEnd) {
		// this.scorePreStep = scorePreStep;
		this.scoreEnd = scoreEnd;
		init();
		this.list = list;
	}

	public void add(Step s) {
		list.add(s);
	}

	public void init() {
		phrase = "Select mission with left target";
		phrasePoint = "";
		isDone = false;
	}

	public void addFinal(int score) {
		add("target droit", "Hit right target to conclude", score, aMyTest.game.missionManager.missionTargetD);
	}

	public void add(String area, String str, int score, int x, int y, JPanel jpa, ImageIcon img) {
		if (validateArea(area)) {
			list.add(new Step(area, str, score, x, y, jpa, img));
		} else {
			System.out.println("Ajout impossible, msg : " + area);
		}
	}

	@Deprecated
	public void add(String area, String str, int score) {
		if (validateArea(area)) {
			list.add(new Step(area, str, score));
		} else {
			System.out.println("Ajout impossible, msg : " + area);
		}
	}

	public void add(String area, String str, int score, AffMission aff) {
		if (validateArea(area)) {
			list.add(new Step(area, str, score, aff));
		} else {
			System.out.println("Ajout impossible, msg : " + area);
		}
	}

	private boolean validateArea(String area) {
		for (Entry<String, aPolygon> entry : aMyTest.map.entrySet()) {
			aPolygon pol = entry.getValue();
			if (pol.descr.contains(area)) {
				return true;
			}
		}
		return false;
	}

	public int validate(aPolygon pol) {
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
			return list.get(index - 1).points;
		}
		return 0;
	}

	/*
	 * public int isValidate(aPolygon pol) { System.out.println("-> validate...");
	 * System.out.println("-> pol : " + pol.descr); System.out.println("-> index : "
	 * + index); if (index == -2) { if (pol.descr.contains("target gauche")) {
	 * phrase = "Launch in ramp to validate"; aMyTest.refreshLabel(); index++;
	 * return scorePreStep; } } else if (index == -1) { if
	 * (pol.descr.contains("ramp")) { index++; phrase = list.get(index).phrase;
	 * aMyTest.refreshLabel(); return scorePreStep; } } else { if
	 * (pol.descr.contains(list.get(index).area)) { index++; if (index >=
	 * list.size()) { phrase = "Congratulations"; aMyTest.refreshLabel(); isDone =
	 * true; return scoreEnd; } phrase = list.get(index).phrase;
	 * aMyTest.refreshLabel(); return list.get(index-1).points; } } return 0; }
	 */

	public String getPhrasePoint() {
		if (index == 0) {
			return "";
		}
		return "Won " + (list.get(index - 1).points * aMyTest.game.multiplier) + "pts";
	}

	public String getFinalPhrasePoint() {
		return "Mission done : " + (scoreEnd * aMyTest.game.multiplier) + "pts";
	}

	public String getPhrase() {
		return list.get(index).phrase;
	}

	public int getImgX() {
		return list.get(index).x;
	}

	public int getImgY() {
		return list.get(index).y;
	}

	public ImageIcon getImg() {
		return list.get(index).img;
	}

	public JPanel getJPanel() {
		return list.get(index).jpa;
	}

	public int getPoint() {
		try {
			return list.get(index).points;
		} catch (Exception e) {
		}
		return 0;
	}

}
