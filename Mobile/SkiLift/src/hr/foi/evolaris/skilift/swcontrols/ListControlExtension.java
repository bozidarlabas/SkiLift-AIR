/*
Copyright (c) 2011, Sony Ericsson Mobile Communications AB
Copyright (c) 2011-2013, Sony Mobile Communications AB

 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 * Neither the name of the Sony Ericsson Mobile Communications AB / Sony Mobile
 Communications AB nor the names of its contributors may be used to endorse or promote
 products derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package hr.foi.evolaris.skilift.swcontrols;

import hr.foi.evolaris.skilift.AdvancedLayoutsExtensionService;
import hr.foi.evolaris.skilift.SmartwatchUserInterface;
import hr.foi.evolaris.skilift.interfaces.OnChangeSmartWatchLayout;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.sonymobile.smartextension.hellonotification.R;
import com.sonyericsson.extras.liveware.aef.control.Control;
import com.sonyericsson.extras.liveware.extension.util.control.ControlListItem;

/**
 * ListControlExtension displays a scrollable list, based on a string array.
 * Tapping on list items opens a swipable detail view.
 */

public class ListControlExtension extends ManagedControlExtension implements
		OnChangeSmartWatchLayout {

	/**
	 * String array with sample data to be displayed in list.
	 */

	public static String[] mListContent = { "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "10" };

	public static String[] LiftsNumber = { "Lift1", "Lift2", "Lift3", "Lift4", "Lift5", "Lift6", "Lift7",
		"Lift8", "Lift9", "Lift10" };

	protected int mLastKnowPosition = 0;

	/**
	 * @see ManagedControlExtension#ManagedControlExtension(Context, String,
	 *      ControlManagerCostanza, Intent)
	 */

	public ListControlExtension(Context context, String hostAppPackageName,
			ControlManagerSmartWatch2 controlManager, Intent intent) {
		super(context, hostAppPackageName, controlManager, intent);
		Log.d(AdvancedLayoutsExtensionService.LOG_TAG,
				"ListControl constructor");
	}

	@Override
	public void onResume() {
		Log.d(AdvancedLayoutsExtensionService.LOG_TAG, "onResume");
		showLayout(R.layout.layout_test_list, null);
		sendListCount(R.id.listView, mListContent.length);

		// If requested, move to the correct position in the list.
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
		Log.d(AdvancedLayoutsExtensionService.LOG_TAG,
				"onRequestListItem() - position " + listItemPosition);
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
		// We save the last "selected" position, this is the current visible
		// list item index. The position can later be used on resume
		mLastKnowPosition = listItem.listItemPosition;
	}

	@Override
	public void onListItemClick(final ControlListItem listItem,
			final int clickType, final int itemLayoutReference) {
		Log.d(AdvancedLayoutsExtensionService.LOG_TAG,
				"Item clicked. Position "
						+ listItem.listItemPosition
						+ ", itemLayoutReference "
						+ itemLayoutReference
						+ ". Type was: "
						+ (clickType == Control.Intents.CLICK_TYPE_SHORT ? "SHORT"
								: "LONG"));

		if (clickType == Control.Intents.CLICK_TYPE_SHORT) {
			Intent intent = new Intent(mContext, GalleryTestControl.class);
			// Here we pass the item position to the next control. It would
			// also be possible to put some unique item id in the list item and
			// pass listItem.listItemId here.
			intent.putExtra(GalleryTestControl.EXTRA_INITIAL_POSITION,
					listItem.listItemPosition);
			mControlManager.startControl(intent);
		}
	}

	/**
	 * Creates a list item containing an icon, a title and a body text.
	 * 
	 * @param position
	 *            The position of the item in the list.
	 * @return The list item.
	 */

	protected ControlListItem createControlListItem(int position) {

		ControlListItem item = new ControlListItem();
		int numberIndicator = Integer.parseInt(mListContent[position]);

		item.dataXmlLayout = changeSmartWatchLayout(numberIndicator, item);
		item.layoutReference = R.id.listView;
		item.listItemPosition = position;
		item.listItemId = position;

		item.layoutData = addDataToList(position);

		return item;
	}

	@Override
	public boolean checkIndicatorColor(int liftNumber, int lowerLimit,
			int upperLimit) {
		if (liftNumber >= lowerLimit && liftNumber <= upperLimit)
			return true;
		else
			return false;
	}

	@Override
	public int changeSmartWatchLayout(int liftNumber, ControlListItem item) {
		// int selectedSmartWatchUI =
		// SmartwatchUserInterface.selectedSmartWatchUI;

		switch (SmartwatchUserInterface.selectedSmartWatchUIIndex) {
		case 0:
			return selectUIOne(liftNumber);
		case 1:
			return selectUITwo(liftNumber);
		default:
			return 0;
		}

	}

	@Override
	public int selectUIOne(int liftNumber) {
		if (checkIndicatorColor(liftNumber, 0, 25))
			return R.layout.item_list_green;
		else if (checkIndicatorColor(liftNumber, 25, 75))
			return R.layout.item_list_yellow;
		else if (checkIndicatorColor(liftNumber, 75, 100))
			return R.layout.item_list_orange;
		else
			return R.layout.item_list_red;
		// jos crvena ide ovdje, to vidi sa smrkijem

	}

	@Override
	public int selectUITwo(int liftNumber) {
		if (checkIndicatorColor(liftNumber, 0, 25))
			return R.layout.ui2_item_green;
		else if (checkIndicatorColor(liftNumber, 25, 75))
			return R.layout.ui2_item_yellow;
		else if (checkIndicatorColor(liftNumber, 75, 100))
			return R.layout.ui2_item_orange;
		else
			return R.layout.ui2_item_red;
	}

	@Override
	public Bundle[] addDataToList(int position) {

		// Data which all UI will have
		ControlListItem item = new ControlListItem();
		Bundle bodyBundle = new Bundle();
		item.layoutData = new Bundle[2];
		bodyBundle.putInt(Control.Intents.EXTRA_LAYOUT_REFERENCE, R.id.body);
		// ////////////////////////////

		switch (SmartwatchUserInterface.selectedSmartWatchUIIndex) {
		case 0:
			bodyBundle.putString(Control.Intents.EXTRA_TEXT, LiftsNumber[position]);
			item.layoutData[0] = bodyBundle;
			return item.layoutData;
		case 1:
			//Left Circle Lift Number
			bodyBundle.putString(Control.Intents.EXTRA_TEXT, LiftsNumber[position].substring(4));
			item.layoutData[0] = bodyBundle;
			
			Bundle availabilityBundle = new Bundle();
			availabilityBundle.putInt(Control.Intents.EXTRA_LAYOUT_REFERENCE, R.id.tvavailability);
			availabilityBundle.putString(Control.Intents.EXTRA_TEXT, "Availability: " + mListContent[position] + "%");

			item.layoutData[1] = availabilityBundle;
			return item.layoutData;
		default:
			return null;
		}
	}

}
