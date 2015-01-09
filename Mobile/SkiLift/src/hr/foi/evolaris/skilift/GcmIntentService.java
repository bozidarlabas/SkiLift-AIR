package hr.foi.evolaris.skilift;

import hr.foi.evolaris.skilift.swcontrols.GalleryTestControl;
import hr.foi.evolaris.skilift.swcontrols.ListControlExtension;

import java.util.HashMap;
import java.util.Iterator;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;


public class GcmIntentService extends IntentService {

	HashMap<String, String> mapa = new HashMap<>();
	HashMap<String, String> mapa2 = new HashMap<>();

	int skiLiftNumber = 10;

	public static final int NOTIFICATION_ID = 1;
	private static final String TAG = "GcmIntentService";
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		//dobivam podatke u obliku json-a
		Bundle extras = intent.getExtras();
		for (String key : extras.keySet()) {
			Object value = extras.get(key);
			Log.d("value", value.toString());
			Log.d("key", key);
			// Log.d("moje", String.format("%s %s (%s)", key, value.toString(),
			// value.getClass().getName()));
			if (!key.equals("from") && !key.equals("collapse_key")
					&& !key.equals("android.support.content.wakelockid")) {
				mapa.put(key, value.toString());
			}
		}
		if (mapa == mapa2) {
			return;
		}

		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				//ListControlExtension.mListContent = mapa;
				//sendNotification("Send error: " + extras.toString(), mapa);

			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				//sendNotification(
				//		"Deleted messages on server: " + extras.toString(),
				//		mapa);
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {
				// Post notification of received message.
				getData(mapa);
				Log.i(TAG, "Received: " + extras.toString());
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}
	
	private void getData(HashMap<String, String> mapa){
		String data[] = new String[10];
		String liftsNumber[] = new String[10];
		int i = 0;
		Iterator it = mapa.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry pairs = (HashMap.Entry) it.next();
			//Log.d("kiki", pairs.getValue().toString());
			data[i] = pairs.getValue().toString();
			liftsNumber[i++] = pairs.getKey().toString();
			it.remove(); // avoids a ConcurrentModificationException
		}
		mapa2 = mapa;
		//Main List (Display color and lift)
		ListControlExtension.mListContent = data;
		ListControlExtension.LiftsNumber = liftsNumber;
		//Detail List(Display capacity of clicked lift)
		GalleryTestControl.liftsNumber = data;
		GalleryTestControl.liftsName = liftsNumber;
		
		AdvancedLayoutsExtensionService.sm.resume();
	}
}