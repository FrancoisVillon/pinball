import java.util.ArrayList;

public class MissionClass {

	ArrayList<Mission> listMissions = new ArrayList<>();
	String desc;
	
	public MissionClass(ArrayList<Mission> listMissions , String str) {
		this.listMissions = listMissions;
		desc = str;
	}
	

}
