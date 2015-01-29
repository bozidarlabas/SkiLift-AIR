package hr.foi.air.evolaris.skilift.smartwatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SmartWatchReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(final Context context, final Intent intent) {
		intent.setClass(context, SmartWatchService.class);
		context.startService(intent);
	}
}