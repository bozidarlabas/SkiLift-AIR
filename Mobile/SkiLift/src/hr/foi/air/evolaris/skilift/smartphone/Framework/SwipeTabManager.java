package hr.foi.air.evolaris.skilift.smartphone.Framework;


import java.util.ArrayList;

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

public class SwipeTabManager extends FragmentActivity implements TabListener,
		OnSetTabListener, OnSetSwipeListener {

	protected ActionBar actionBar;
	protected ViewPager viewPager;
	ArrayList<Fragment> fragments = new ArrayList<>();
	int smartwatchNumOfUI = 2; // change this
	protected int selectedUIIndex;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		selectedUIIndex = 0;
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTab(String name) {
		Tab tab = actionBar.newTab();
		tab.setText(name);
		tab.setTabListener(this);
		actionBar.addTab(tab);
	}

	@Override
	public void setTab(ArrayList<String> tabNames) {
		for (String name : tabNames) {
			Tab tab = actionBar.newTab();
			tab.setText(name);
			tab.setTabListener(this);
			actionBar.addTab(tab);
		}
	}

	@Override
	public void setViewPager(ViewPager viewPager) {
		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
				// btnUIName.setText("btn");
				selectedUIIndex = position;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	protected void addFragment(Fragment fragment){
		this.fragments.add(fragment);
	}
	
	protected void removeFragment(Fragment removeFragment){
		for (Fragment fragment : fragments) {
			if(fragment.equals(removeFragment))
				fragments.remove(fragment);
		}
	}

	class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		// dohvacanje fragmenta na dobivenoj poziciji
		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment = null;
			
			fragment = fragments.get(arg0);
			

			return fragment;
		}

		// odgovara koliko stranica postoji
		@Override
		public int getCount() {
			return smartwatchNumOfUI;
		}
	}

	public void selectUI(View v) {
		// setUserInterface();
	}

	public void goBack(View v) {
		this.onBackPressed();
	}

}
