package hr.foi.evolaris.skilift.interfaces;

import android.os.Bundle;

import com.sonyericsson.extras.liveware.extension.util.control.ControlListItem;

public interface OnChangeSmartWatchLayout {

	// What data should be showed inside list on layout (this data is saved inside Bundle)
	Bundle[] addDataToList(int position);

	// How this data will be showed; args: lower and upper Limits of Lifts availability
	boolean checkIndicatorColor(int liftNumber, int lowerLimit, int upperLimit);

	// Show data; args: liftNumber and one item of list
	int changeSmartWatchLayout(int liftNumber, ControlListItem item);
	
	
	//Method will change layout item inside list
	int selectUIOne(int liftNumber);
	int selectUITwo(int liftNumber);

}
