package hr.foi.air.evolaris.skilift.smartphone;

import hr.foi.air.evolaris.skilift.smartwatch.ControlManagerSmartWatch2;
import android.content.Intent;
import android.util.Log;

public class UserInterfaceController {

	private ControlManagerSmartWatch2 smartwatchControlManager;
	public static UserInterfaceController instance;
	private Intent currentIntent;

	private UserInterfaceController() {

	}

	// Singleton pattern
	public static UserInterfaceController getInstance() {
		if (instance == null)
			instance = new UserInterfaceController();
		return instance;
	}

	public ControlManagerSmartWatch2 getSmartwatchControlManager() {
		return smartwatchControlManager;
	}

	public void setSmartwatchControlManager(
			ControlManagerSmartWatch2 smartwatchControlManager) {
		this.smartwatchControlManager = smartwatchControlManager;
	}

	public void changeUserInterface(Intent intent) {
		
		smartwatchControlManager.startControl(intent);
		this.setCurrentIntent(intent);
	}

	public Intent getCurrentIntent() {
		return currentIntent;
	}

	public void setCurrentIntent(Intent currentIntent) {
		this.currentIntent = currentIntent;
	}

}
