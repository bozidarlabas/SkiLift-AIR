package hr.foi.air.evolaris.skilift;

import hr.foi.air.evolaris.skilift.data.Lift;
import hr.foi.air.evolaris.skilift.interfaces.OnUserInterfaceChanged;

import java.util.ArrayList;

public class LiftDataManager {
	
	public static LiftDataManager instance;
	private ArrayList<Lift> liftData;
	private OnUserInterfaceChanged onUserInterfaceChanged;
	
	private LiftDataManager(){
		liftData = new ArrayList<>();
	}
	
	//Singleton pattern
	public static LiftDataManager getInstance(){
		if(instance == null)
			instance = new LiftDataManager();
		return instance;
	}

	public ArrayList<Lift> getLiftData() {
		return liftData;
	}

	public void setLiftData(ArrayList<Lift> liftData) {
		this.liftData = liftData; //change that this method saves only changed liftData
	}
	
	private void setOnDataChanged(OnUserInterfaceChanged onUserInterfaceChanged) {
		this.onUserInterfaceChanged = onUserInterfaceChanged;
	}
	
	public void updateUserInterface() {
		if(onUserInterfaceChanged != null) {
			onUserInterfaceChanged.drawUserInterface(liftData);
		}
	}
	
	
	
}
