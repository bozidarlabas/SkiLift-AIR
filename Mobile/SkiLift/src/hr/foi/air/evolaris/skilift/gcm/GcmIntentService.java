package hr.foi.air.evolaris.skilift.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class GcmIntentService extends IntentService {

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("lala", "kiki");
	}
}