package hr.foi.evolaris.skilift.smartwatch;

import hr.foi.evolaris.skilift.fragments.FragmentA;
import hr.foi.evolaris.skilift.fragments.FragmentB;
import hr.foi.evolaris.skilift.interfaces.OnSetSmartWatchUI;
import hr.foi.evolaris.skilift.utils.Constants;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.Button;

import com.example.sonymobile.smartextension.hellonotification.R;


public class SmartwatchUserInterface extends FragmentActivity implements TabListener, OnSetSmartWatchUI{

	ActionBar actionBar;
	ViewPager viewPager;
	private Button btnUIName;
	int smartwatchNumOfUI = 2, selectedFragmentIndex;
	public static int selectedSmartWatchUIIndex;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.smartwatchui);
		
		selectedSmartWatchUIIndex = 0;
		selectedFragmentIndex = 0;
		
		btnUIName = (Button) findViewById(R.id.btnUIName);
		
		viewPager = (ViewPager)findViewById(R.id.pager);
		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
				btnUIName.setText(Constants.uiNames[position]);
				selectedFragmentIndex = position;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		ActionBar.Tab tab1 = actionBar.newTab();
		tab1.setText(Constants.uiNames[0]);
		tab1.setTabListener(this);
		
		ActionBar.Tab tab2 = actionBar.newTab();
		tab2.setText(Constants.uiNames[1]);
		tab2.setTabListener(this);
		
		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
	}

	
	//TAB METHODS
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}
	
	//SWIPE METHODS
	
	class MyAdapter extends FragmentPagerAdapter
	{

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		//dohvacanje fragmenta na dobivenoj poziciji
		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment = null;

			switch (arg0) {
			case 0:
				fragment = new FragmentA();
				break;
			case 1:
				fragment = new FragmentB();
				break;
			default:
				break;
			}

			return fragment;
		}

		//odgovara koliko stranica postoji
		@Override
		public int getCount() {
			return smartwatchNumOfUI;
		}
	}

	public void selectUI(View v){
		setUserInterface();
	}
	
	@Override
	public void setUserInterface() {
		selectedSmartWatchUIIndex = selectedFragmentIndex;
		SMartWatchExtensionService.sm.resume();
	}


	@Override
	public int returnCurrentUserInterface() {
		// TODO Auto-generated method stub
		return selectedSmartWatchUIIndex;
	}
	
	public void goBack(View v){
		this.onBackPressed();
	}
	
}
