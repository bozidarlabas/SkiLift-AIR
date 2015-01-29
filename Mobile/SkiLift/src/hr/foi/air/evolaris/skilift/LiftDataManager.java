package hr.foi.air.evolaris.skilift;

import hr.foi.air.evolaris.skilift.data.Lift;
import hr.foi.air.evolaris.skilift.interfaces.OnUserInterfaceChanged;
import hr.foi.air.evolaris.skilift.smartphone.UserInterfaceController;
import hr.foi.air.evolaris.skilift.smartwatch.ControlManagerSmartWatch2;
import hr.foi.air.evolaris.skilift.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Intent;
import android.os.Bundle;

public class LiftDataManager {

	public static LiftDataManager instance;
	private ArrayList<Lift> liftsData;
	private OnUserInterfaceChanged onUserInterfaceChanged;
	private ControlManagerSmartWatch2 controlSWManager;
	public boolean sortAscending = false;
	private boolean sortDescending = false;
	UserInterfaceController userInterfaceController = UserInterfaceController
			.getInstance();

	private LiftDataManager() {
		liftsData = new ArrayList<>();
		// setDefaultVlues();
	}

	// Singleton pattern
	public static LiftDataManager getInstance() {
		if (instance == null)
			instance = new LiftDataManager();
		return instance;
	}

	public ArrayList<Lift> getLiftData() {
		return liftsData;
	}

	public ArrayList<Lift> getLiftDataWatch() {
		ArrayList<Lift> liftDataWatch = new ArrayList<Lift>();
		for (Lift lift : liftsData) {
			if (lift.isVisibility()) {
				liftDataWatch.add(lift);
			}
		}
		return liftDataWatch;
	}

	private void setLiftData(ArrayList<Lift> liftsData) {
		this.liftsData = liftsData;
	}

	// Observer pattern
	public void setOnDataChanged(OnUserInterfaceChanged onUserInterfaceChanged) {
		this.onUserInterfaceChanged = onUserInterfaceChanged;
	}


	public void sortAscending() {
		sortAscending = true;
		sortDescending = false;
		Collections.sort(liftsData, FruitNameComparator);
		Intent initialIntent = userInterfaceController.getCurrentIntent();
		userInterfaceController.changeUserInterface(initialIntent);
	}
	
	public void sortDescending() {
		sortAscending = false;
		sortDescending = true;
		Collections.sort(liftsData, FruitNameComparator);
		Intent initialIntent = userInterfaceController.getCurrentIntent();
		userInterfaceController.changeUserInterface(initialIntent);
	}

	public void parseFromBundleToArray(Bundle liftsBundle) {
		if (liftsData.isEmpty()) {
			ArrayList<Lift> liftsData = new ArrayList<>();
			for (String key : liftsBundle.keySet()) {
				Object value = liftsBundle.get(key);
				if (key.startsWith(Constants.LIFT_TAG)) {
					Lift liftData = new Lift();
					liftData.setLiftName(key);
					liftData.setLiftCapacity(Integer.parseInt(value.toString()));
					liftData.setVisibility(true);
					liftsData.add(liftData);
				}
			}
			this.setLiftData(liftsData);
		} else {
			for (Lift lift : liftsData) {
				for (String key : liftsBundle.keySet()) {
					if (lift.getLiftName().equals(key)) {
						Object value = liftsBundle.get(key);
						lift.setLiftCapacity(Integer.parseInt(value.toString()));
					}
				}
			}
		}
		if(sortAscending)
			Collections.sort(liftsData, FruitNameComparator);
		else if(sortDescending)
			Collections.sort(liftsData, FruitNameComparator);
	}

	public static Comparator<Lift> FruitNameComparator = new Comparator<Lift>() {

		public int compare(Lift fruit1, Lift fruit2) {

			Integer lift1 = fruit1.getLiftCapacity();
			Integer lift2 = fruit2.getLiftCapacity();;

			// ascending order
			if(LiftDataManager.getInstance().sortAscending)
				return lift1.compareTo(lift2);
			else
				return lift2.compareTo(lift1);
		}

	};

}
