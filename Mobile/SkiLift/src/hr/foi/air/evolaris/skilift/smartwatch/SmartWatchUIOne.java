package hr.foi.air.evolaris.skilift.smartwatch;

import hr.foi.air.evolaris.skilift.data.Lift;
import hr.foi.air.evolaris.skilift.interfaces.OnUserInterfaceChanged;
import hr.foi.evolaris.skilift.R;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sonyericsson.extras.liveware.aef.control.Control;
import com.sonyericsson.extras.liveware.extension.util.control.ControlListItem;

public class SmartWatchUIOne extends ManagedControlExtension implements
		OnUserInterfaceChanged {

	protected int mLastKnowPosition = 0;
	ArrayList<Lift> liftsData;

	public SmartWatchUIOne(Context context, String hostAppPackageName,
			ControlManagerSmartWatch2 controlManager, Intent intent,
			ArrayList<Lift> liftsData) {
		super(context, hostAppPackageName, controlManager, intent);
		
		
		this.liftsData = liftsData;
	}
	
	@Override
	public int drawUserInterface(int capacity) {
		if (checkIndicatorColor(capacity, 0, 25))
			return R.layout.item_list_green;
		else if (checkIndicatorColor(capacity, 25, 75))
			return R.layout.item_list_yellow;
		else if (checkIndicatorColor(capacity, 75, 100))
			return R.layout.item_list_orange;
		else
			return R.layout.item_list_red;
	}


	@Override
	public void onResume() {
		showLayout(R.layout.layout_test_list, null);
		sendListCount(R.id.listView, liftsData.size()); // Change to dinamic

		int startPosition = getIntent().getIntExtra(
				GalleryTestControl.EXTRA_INITIAL_POSITION, 0);
		mLastKnowPosition = startPosition;
		sendListPosition(R.id.listView, startPosition);
	}

	@Override
	public void onPause() {
		super.onPause();
		// Position is saved into Control's Intent, possibly to be used later.
		getIntent().putExtra(GalleryTestControl.EXTRA_INITIAL_POSITION,
				mLastKnowPosition);
	}

	@Override
	public void onRequestListItem(final int layoutReference,
			final int listItemPosition) {

		if (layoutReference != -1 && listItemPosition != -1
				&& layoutReference == R.id.listView) {
			ControlListItem item = createControlListItem(listItemPosition);
			if (item != null) {
				sendListItem(item);
			}
		}
	}

	@Override
	public void onListItemSelected(ControlListItem listItem) {
		super.onListItemSelected(listItem);
		mLastKnowPosition = listItem.listItemPosition;
	}

	@Override
	public void onListItemClick(final ControlListItem listItem,
			final int clickType, final int itemLayoutReference) {

		if (clickType == Control.Intents.CLICK_TYPE_SHORT) {
			Intent intent = new Intent(mContext, GalleryTestControl.class);
			intent.putExtra(GalleryTestControl.EXTRA_INITIAL_POSITION,
					listItem.listItemPosition);
			mControlManager.startControl(intent);
		}
	}

	protected ControlListItem createControlListItem(int position) {
		ControlListItem item = new ControlListItem();
		int capacity = liftsData.get(position).getLiftCapacity(); // capacity

		item.dataXmlLayout = drawUserInterface(capacity);
		item.layoutReference = R.id.listView;
		item.listItemPosition = position;
		item.listItemId = position;
		item.layoutData = addDataToList(position);

		return item;
	}
	


	public boolean checkIndicatorColor(int liftNumber, int lowerLimit,
			int upperLimit) {
		if (liftNumber >= lowerLimit && liftNumber <= upperLimit)
			return true;
		else
			return false;
	}

	public Bundle[] addDataToList(int position) {
		ControlListItem item = new ControlListItem();
		Bundle bodyBundle = new Bundle();
		item.layoutData = new Bundle[2];
		bodyBundle.putInt(Control.Intents.EXTRA_LAYOUT_REFERENCE, R.id.body);
		bodyBundle.putString(Control.Intents.EXTRA_TEXT, liftsData
				.get(position).getLiftName());
		item.layoutData[0] = bodyBundle;
		return item.layoutData;
	}



}
