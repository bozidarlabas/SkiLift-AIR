package hr.foi.air.evolaris.skilift.smartphone;

import hr.foi.air.evolaris.skilift.LiftDataManager;
import hr.foi.air.evolaris.skilift.gcm.RegisterAppGcm;
import hr.foi.evolaris.skilift.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


public class MainActivity extends Activity {

	LiftDataManager liftDataManager = LiftDataManager.getInstance();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerToGCM();
    }
    
	
    //Register to GCM
    public void registerToGCM(){
    	new RegisterAppGcm(getApplicationContext());
    }
    
 // OnClick Events
 	public void changeSmartwatchUI(View v) {
 		Intent intent = new Intent(this, UserInterfaceView.class);
 		startActivity(intent);
 	}
 	
	public void filterData(View v) {
		Intent intent = new Intent(this, ExpandableListActivity.class);
		startActivity(intent);
	}

}