package hr.foi.evolaris.skilift;

import hr.foi.evolaris.skilift.fragments.FragmentA;
import hr.foi.evolaris.skilift.fragments.FragmentB;
import hr.foi.evolaris.skilift.utils.Constants;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.example.sonymobile.smartextension.hellonotification.R;


public class SmartwatchUserInterface extends FragmentActivity implements TabListener{

	ActionBar actionBar;
	ViewPager viewPager;
	int smartwatchNumOfUI = 2;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.smartwatchui);
		
		viewPager = (ViewPager)findViewById(R.id.pager);
		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
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
		tab1.setText(Constants.uiNameOne);
		tab1.setTabListener(this);
		
		ActionBar.Tab tab2 = actionBar.newTab();
		tab2.setText(Constants.uiNameTwo);
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
	
}