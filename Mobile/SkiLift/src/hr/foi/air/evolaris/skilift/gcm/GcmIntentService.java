package hr.foi.air.evolaris.skilift.gcm;

import hr.foi.air.evolaris.skilift.LiftDataManager;
import hr.foi.air.evolaris.skilift.smartphone.UserInterfaceController;
import android.app.IntentService;
import android.content.Intent;

public class GcmIntentService extends IntentService {

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		// Parse gcm data to array
		LiftDataManager liftDataManager = LiftDataManager.getInstance();
		liftDataManager.parseFromBundleToArray(intent.getExtras());

		// Refresh data when gcm push notification
		UserInterfaceController userIterfaceController = UserInterfaceController
				.getInstance();
		
		Intent initialIntent = userIterfaceController.getCurrentIntent();
		userIterfaceController.changeUserInterface(initialIntent);

		
		// test
		//for (Lift lift : liftDataManager.getLiftData()) {
		//	Log.d("namee2", lift.getLiftName());
		//}
	}
}