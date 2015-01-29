package hr.foi.air.evolaris.skilift.smartphone;

import hr.foi.air.evolaris.skilift.LiftDataManager;
import hr.foi.air.evolaris.skilift.data.Lift;
import hr.foi.evolaris.skilift.R;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

public class ExpandableListActivity extends Activity {

	public static ListAdapter ExpAdapter;
	private static ArrayList<Lift> ExpListItems;
	public static ExpandableListView ExpandList;
	LiftDataManager liftDataManager = LiftDataManager.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandable_main);
		ExpandList = (ExpandableListView) findViewById(R.id.ExpList);
		ExpListItems = liftDataManager.getLiftData();
		ExpAdapter = new ListAdapter(this,
				ExpListItems);
		ExpandList.setAdapter(ExpAdapter);
	}
	
	public void onSaveButtonClick(View view){
		AlertDialog diaBox = makeAndShowDialogBox();
	    diaBox.show();
	}
	

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void goBack(View v) {
		this.onBackPressed();
	}

	public void openSettings(View v) {
		// 1. setting -> viibracija sata (on/off)
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
private AlertDialog makeAndShowDialogBox(){
    	
        AlertDialog myQuittingDialogBox = 

        	new AlertDialog.Builder(this) 
        	.setTitle(getString(R.string.alertDialogTitle)) 
        	.setMessage(getString(R.string.alertDialogText)) 
        	//.setIcon(R.drawable.logo_item)
        	
        	.setPositiveButton(getString(R.string.alertDialogButtonOne), new DialogInterface.OnClickListener() { 
        		public void onClick(DialogInterface dialog, int whichButton) { 
        			liftDataManager.sortAscending();
        		}              
        	})

        	.setNegativeButton(getString(R.string.alertDialogButtonTwo), new DialogInterface.OnClickListener() { 
        		public void onClick(DialogInterface dialog, int whichButton) { 
        			liftDataManager.sortDescending();
             } 
        	})
        	
        	.create();
        	
        	return myQuittingDialogBox;
    }

}
