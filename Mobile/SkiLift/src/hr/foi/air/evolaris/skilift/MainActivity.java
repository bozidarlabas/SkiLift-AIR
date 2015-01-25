package hr.foi.air.evolaris.skilift;

import hr.foi.air.evolaris.skilift.gcm.RegisterAppGcm;
import hr.foi.evolaris.skilift.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    //Register to GCM
    public void registerToGCM(View v){
    	new RegisterAppGcm(getApplicationContext());
    }
}