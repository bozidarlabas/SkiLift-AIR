package hr.foi.evolaris.skilift.expandListView;

import hr.foi.evolaris.skilift.model.Lift;
import hr.foi.evolaris.skilift.swcontrols.ListControlExtension;

import java.util.ArrayList;
import java.util.List;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.sonymobile.smartextension.hellonotification.R;
import com.touchmenotapps.widget.radialmenu.menu.v1.RadialMenuItem;
import com.touchmenotapps.widget.radialmenu.menu.v1.RadialMenuWidget;

public class ExpandableListActivity extends FragmentActivity {

	private RadialMenuWidget pieMenu;
	public RadialMenuItem menuItem, menuCloseItem, menuExpandItem,
			menuExpandItem2;
	public RadialMenuItem firstChildItemCapacity, secondChildItemCapacity;
	public RadialMenuItem firstChildItemDistance, secondChildItemDistance;
	private List<RadialMenuItem> children = new ArrayList<RadialMenuItem>();
	private List<RadialMenuItem> children2 = new ArrayList<RadialMenuItem>();

	/** Called when the activity is first created. */
	public static ExpandListAdapter ExpAdapter;
	private ArrayList<Lift> ExpListItems;
	public static ExpandableListView ExpandList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandable_main);

		ExpandList = (ExpandableListView) findViewById(R.id.ExpList);

		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandListAdapter(ExpandableListActivity.this,
				ExpListItems);
		ExpandList.setAdapter(ExpAdapter);

		setButtonPie();
	}

	private void setButtonPie() {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
				&& Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR2) {
			setTheme(android.R.style.Theme_Holo_Light);
			getActionBar().setDisplayShowHomeEnabled(true);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			setTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
			getWindow().setUiOptions(
					ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
			getActionBar().setDisplayShowHomeEnabled(true);
		} else {
			setTheme(R.style.RadialMenuLegacyTitleBar);
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.layout_appbar);
			TextView barHeader = (TextView) findViewById(R.id.appbar_title_text);
			barHeader.setText(R.string.app_name);
		}


		pieMenu = new RadialMenuWidget(this);
		menuCloseItem = new RadialMenuItem(getString(R.string.close), null);
		menuCloseItem
				.setDisplayIcon(android.R.drawable.ic_menu_close_clear_cancel);


		// /////////////////////FIRST ITEM/////////////////////////
		firstChildItemCapacity = new RadialMenuItem(
				getString(R.string.sortMaxMin), getString(R.string.sortMaxMin));
		firstChildItemCapacity
				.setOnMenuItemPressed(new RadialMenuItem.RadialMenuItemClickListener() {
					@Override
					public void execute() {

						pieMenu.dismiss();
					}
				});

		secondChildItemCapacity = new RadialMenuItem(
				getString(R.string.sortMinMax), getString(R.string.sortMinMax));
		secondChildItemCapacity
				.setOnMenuItemPressed(new RadialMenuItem.RadialMenuItemClickListener() {
					@Override
					public void execute() {

						pieMenu.dismiss();
					}
				});

		menuExpandItem = new RadialMenuItem(getString(R.string.expandable),
				getString(R.string.expandable));
		children.add(firstChildItemCapacity);
		children.add(secondChildItemCapacity);
		menuExpandItem.setMenuChildren(children);

		menuExpandItem = new RadialMenuItem(getString(R.string.expandable),
				getString(R.string.expandable));
		menuExpandItem.setMenuChildren(children);
		///////////////////////////////////////////////////////////
		
		// /////////////////////SECOND ITEM/////////////////////////
		firstChildItemDistance = new RadialMenuItem(
				getString(R.string.sortMaxMin), getString(R.string.sortMaxMin));
		firstChildItemDistance
				.setOnMenuItemPressed(new RadialMenuItem.RadialMenuItemClickListener() {
					@Override
					public void execute() {

						pieMenu.dismiss();
					}
				});

		secondChildItemDistance = new RadialMenuItem(
				getString(R.string.sortMinMax), getString(R.string.sortMinMax));
		// secondChildItemCapacity.setDisplayIcon(R.drawable.ic_launcher);
		secondChildItemDistance
				.setOnMenuItemPressed(new RadialMenuItem.RadialMenuItemClickListener() {
					@Override
					public void execute() {

						pieMenu.dismiss();
					}
				});

		menuExpandItem2 = new RadialMenuItem(getString(R.string.expandable2),
				getString(R.string.expandable2));
		children2.add(firstChildItemCapacity);
		children2.add(secondChildItemCapacity);
		menuExpandItem2.setMenuChildren(children);


		menuExpandItem = new RadialMenuItem(getString(R.string.expandable),
				getString(R.string.expandable));
		menuExpandItem.setMenuChildren(children);
		// ////////////////////////////////////////////////////////////////////////////////

		menuCloseItem
				.setOnMenuItemPressed(new RadialMenuItem.RadialMenuItemClickListener() {
					@Override
					public void execute() {
						// menuLayout.removeAllViews();
						pieMenu.dismiss();
					}
				});

		// pieMenu.setDismissOnOutsideClick(true);
		pieMenu.setAnimationSpeed(0L);
		pieMenu.setSourceLocation(0, 0);
		pieMenu.setIconSize(15, 30);
		pieMenu.setAlpha(1);
		pieMenu.setTextSize(13);
		pieMenu.setOutlineColor(Color.BLACK, 225);
		pieMenu.setInnerRingColor(0x8f8e8e, 180);
		pieMenu.setOuterRingColor(0x0099CC, 180);
		// pieMenu.setHeader("Test Menu", 20);
		pieMenu.setCenterCircle(menuCloseItem);

		pieMenu.addMenuEntry(new ArrayList<RadialMenuItem>() {
			{
				add(menuExpandItem);
				add(menuExpandItem2);
			}
		});


		Button testButton = (Button) this.findViewById(R.id.setup_macroSavebtn);
		testButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pieMenu.show(v);

				View v2 = findViewById(R.id.root);
				AlphaAnimation alpha = new AlphaAnimation(1F, 0.7F);
				alpha.setDuration(0); // Make animation instant
				alpha.setFillAfter(true); // Tell it to persist after the
											// animation ends
				v2.startAnimation(alpha);

			}
		});

	}

	public ArrayList<Lift> SetStandardGroups() {
		return ListControlExtension.lifts2;
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Init with home fragment
		/*
		 * getSupportFragmentManager().popBackStack(null,
		 * FragmentManager.POP_BACK_STACK_INCLUSIVE);
		 * getSupportFragmentManager() .beginTransaction()
		 * .replace(mFragmentContainer.getId(), new
		 * RadialMenuMainFragment()).commit();
		 */
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
		Log.d("kuku", "luku");
	}

}
