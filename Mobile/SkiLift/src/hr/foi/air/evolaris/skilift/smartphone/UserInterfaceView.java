package hr.foi.air.evolaris.skilift.smartphone;

import hr.foi.air.evolaris.skilift.LiftDataManager;
import hr.foi.air.evolaris.skilift.smartphone.Framework.OnSwipeCreate;
import hr.foi.air.evolaris.skilift.smartphone.Framework.OnTabCreate;
import hr.foi.air.evolaris.skilift.smartphone.Framework.SwipeTabManager;
import hr.foi.air.evolaris.skilift.smartwatch.SmartWatchUIOne;
import hr.foi.air.evolaris.skilift.smartwatch.SmartWatchUITwo;
import hr.foi.evolaris.skilift.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

public class UserInterfaceView extends SwipeTabManager implements OnTabCreate, OnSwipeCreate{

	LiftDataManager liftDataManager = LiftDataManager.getInstance();
	UserInterfaceController userInterfaceController = UserInterfaceController.getInstance();
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.user_interface_manager);
		createSwipe();
		createTab();
	}
	
	
	//OnClick Event
	public void changeUserInterface(View v){
		
		Intent intent = null;
	
		switch(selectedUIIndex){
		case 0:
			intent = new Intent(this, SmartWatchUIOne.class);
			break;
		case 1:
			intent = new Intent(this, SmartWatchUITwo.class);
			break;
		}
		userInterfaceController.changeUserInterface(intent);
	}
	
	@Override
	public void createTab() {
		setTab("ui 1");
		setTab("ui 2");
	}

	@Override
	public void createSwipe() {
		addFragment(new FragmentA());
		addFragment(new FragmentB());
		viewPager = (ViewPager)findViewById(R.id.pager);
		setViewPager(viewPager);
	}
	
}