package hr.foi.evolaris.skilift.expandListView;

import hr.foi.evolaris.skilift.model.Lift;
import hr.foi.evolaris.skilift.model.LiftDetail;
import hr.foi.evolaris.skilift.swcontrols.ListControlExtension;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.sonymobile.smartextension.hellonotification.R;

public class test extends Activity {

	public static String[] mListContent = { "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "10" };

	public static String[] LiftsNumber = { "Lift1", "Lift2", "Lift3", "Lift4",
			"Lift5", "Lift6", "Lift7", "Lift8", "Lift9", "Lift10" };

	/** Called when the activity is first created. */
	private ExpandListAdapter ExpAdapter;
	private ArrayList<Lift> ExpListItems;
	private ExpandableListView ExpandList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandable_main);

		ExpandList = (ExpandableListView) findViewById(R.id.ExpList);

		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandListAdapter(test.this,
				ExpListItems);
		ExpandList.setAdapter(ExpAdapter);
	}

	public ArrayList<Lift> SetStandardGroups() {
		return ListControlExtension.lifts;
/*
		ArrayList<Lift> list = new ArrayList<Lift>();
		ArrayList<LiftDetail> list2 = new ArrayList<LiftDetail>();
		for (int i = 0; i < LiftsNumber.length; i++) {
			Lift gru1 = new Lift();
			gru1.setName(LiftsNumber[i]);

			LiftDetail ch1_1 = new LiftDetail();
			ch1_1.setName(mListContent[i]);
			ch1_1.setTag("" + i);
			list2.add(ch1_1);

			LiftDetail ch1_2 = new LiftDetail();
			ch1_2.setName("Distance: ");
			ch1_2.setTag("" + i);
			list2.add(ch1_2);

			gru1.setItems(list2);
			list.add(gru1);

			list2 = new ArrayList<LiftDetail>();
		}

		return list;
		*/
	}

}
