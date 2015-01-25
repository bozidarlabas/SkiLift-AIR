package hr.foi.evolaris.skilift.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
	  
	static int i = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
    	
    	Log.d("ijk", Integer.toString(i));
    	if(i % 9 == 0){
    		Log.d("dolazak", GcmIntentService.class.getName());
            // Explicitly specify that GcmIntentService will handle the intent.
            ComponentName comp = new ComponentName(context.getPackageName(),
                    GcmIntentService.class.getName());
            // Start the service, keeping the device awake while it is launching.
            startWakefulService(context, (intent.setComponent(comp)));
            
            setResultCode(Activity.RESULT_OK);
    	}
    	i++;
    	if(i == 9)
    		i = 0;
    	
    }
}