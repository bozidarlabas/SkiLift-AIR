package hr.foi.evolaris.skilift.expandListView;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.sonymobile.smartextension.hellonotification.R;

public class ExpandListActivity extends Activity {

	public static String[] mListContent = { "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "10" };

	public static String[] LiftsNumber = { "Lift1", "Lift2", "Lift3", "Lift4",
			"Lift5", "Lift6", "Lift7", "Lift8", "Lift9", "Lift10" };
	

	/** Called when the activity is first created. */
	private ExpandListAdapter ExpAdapter;
	private ArrayList<ExpandListGroup> ExpListItems;
	private ExpandableListView ExpandList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandable_main);
		/*
		
		ExpandList = (ExpandableListView) findViewById(R.id.ExpList);
		
		
	    
		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandListAdapter(ExpandListActivity.this, ExpListItems);
		ExpandList.setAdapter(ExpAdapter);
		*/
	}

	
public ArrayList<ExpandListGroup> SetStandardGroups() {
		
		ArrayList<ExpandListGroup> list = new ArrayList<ExpandListGroup>();
		ArrayList<ExpandListChild> list2 = new ArrayList<ExpandListChild>();
		for (int i = 0; i < LiftsNumber.length; i++) {
			ExpandListGroup gru1 = new ExpandListGroup();
			gru1.setName(LiftsNumber[i]);
			
			ExpandListChild ch1_1 = new ExpandListChild();
			ch1_1.setName(mListContent[i]);
			ch1_1.setTag("" + i);
			list2.add(ch1_1);
			
			ExpandListChild ch1_2 = new ExpandListChild();
			ch1_2.setName("Distance: ");
			ch1_2.setTag("" + i);
			list2.add(ch1_2);
			
			gru1.setItems(list2);
			list.add(gru1);
			
			list2 = new ArrayList<ExpandListChild>();
		}

		return list;
	}


}