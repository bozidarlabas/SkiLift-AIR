package hr.foi.evolaris.skilift.gcm;

import hr.foi.evolaris.skilift.expandListView.ExpandListAdapter;
import hr.foi.evolaris.skilift.expandListView.ExpandableListActivity;
import hr.foi.evolaris.skilift.model.Lift;
import hr.foi.evolaris.skilift.model.LiftDetail;
import hr.foi.evolaris.skilift.smartwatch.SMartWatchExtensionService;
import hr.foi.evolaris.skilift.swcontrols.ListControlExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
		// dobivam podatke u obliku json-a
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
				// ListControlExtension.mListContent = mapa;
				// sendNotification("Send error: " + extras.toString(), mapa);

			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				// sendNotification(
				// "Deleted messages on server: " + extras.toString(),
				// mapa);
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

	private void getData(HashMap<String, String> mapa) {
		String data = new String();
		String liftName = new String();
		int i = 0;
		Iterator it = mapa.entrySet().iterator();
		ArrayList<Lift> liftCopy = new ArrayList<>();
		while (it.hasNext()) {

			ArrayList<LiftDetail> list2 = new ArrayList<LiftDetail>();
			HashMap.Entry pairs = (HashMap.Entry) it.next();

			data = pairs.getValue().toString();
			Log.d("poz", "" + i);
			// liftsnumber = name

			
			
			liftName = pairs.getKey().toString();
			if(!load(liftName)){
				Lift lift = new Lift();
				lift.setName(liftName);
				LiftDetail liftDetail = new LiftDetail();
				liftDetail.setName(data);
				liftDetail.setTag("" + i);
				list2.add(liftDetail);

				LiftDetail liftDetail2 = new LiftDetail();
				liftDetail2.setName("" + i);
				liftDetail2.setTag("" + i);
				list2.add(liftDetail2);

				lift.setItems(list2);// set capacity
				Log.d("redd", "" + load(liftName));
				liftCopy.add(lift);
			}
			
			
			i++;
			it.remove(); // avoids a ConcurrentModificationException
		}
		ListControlExtension.lifts.clear();
		ListControlExtension.lifts = liftCopy;
		
		// Main List (Display color and lift)
		// ListControlExtension.LiftsNumber = liftsNumber;
		// Detail List(Display capacity of clicked lift)
		// GalleryTestControl.liftsNumber = data;
		// GalleryTestControl.liftsName = liftsNumber;
		SMartWatchExtensionService.sm.resume();

	}

	public boolean load(String key) {
		SharedPreferences sharedPreferences = this.getSharedPreferences(
				"MyData", Context.MODE_PRIVATE);
		boolean showLift = sharedPreferences.getBoolean(key, false);
		return showLift;
	}
}