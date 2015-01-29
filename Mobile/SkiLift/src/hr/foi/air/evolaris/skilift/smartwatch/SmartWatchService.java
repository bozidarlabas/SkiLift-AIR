package hr.foi.air.evolaris.skilift.smartwatch;

import hr.foi.air.evolaris.skilift.LiftDataManager;
import hr.foi.air.evolaris.skilift.smartphone.UserInterfaceController;
import android.content.Intent;

import com.sonyericsson.extras.liveware.extension.util.ExtensionService;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.registration.DeviceInfoHelper;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation;

public class SmartWatchService extends ExtensionService {

	public static final String LOG_TAG = "AdvancedLayoutsExtension";
	LiftDataManager liftDataManager = LiftDataManager.getInstance();

	public SmartWatchService() {
		super();
	}


	@Override
	protected RegistrationInformation getRegistrationInformation() {
		return new SmartWatchRegistrationInformation(this);
	}

	@Override
	protected boolean keepRunningWhenConnected() {
		return false;
	}

	@Override
	public ControlExtension createControlExtension(String hostAppPackageName) {
		boolean advancedFeaturesSupported = DeviceInfoHelper.isSmartWatch2ApiAndScreenDetected(this, hostAppPackageName);
		if (advancedFeaturesSupported) {
			UserInterfaceController uiController = UserInterfaceController.getInstance();
			
			Intent initialListControlIntent = uiController.getCurrentIntent();
			
			ControlManagerSmartWatch2 sm;
			if(initialListControlIntent == null){
				initialListControlIntent = new Intent(this,SmartWatchUIOne.class);
				uiController.setCurrentIntent(initialListControlIntent);
				sm = new ControlManagerSmartWatch2(this, hostAppPackageName, initialListControlIntent);
			}else{
				sm = new ControlManagerSmartWatch2(this, hostAppPackageName, initialListControlIntent);
			}
			
			uiController.setSmartwatchControlManager(sm);
			return sm;
		} else {
			throw new IllegalArgumentException("No control for: "
					+ hostAppPackageName);
		}
	}
	
	
}
