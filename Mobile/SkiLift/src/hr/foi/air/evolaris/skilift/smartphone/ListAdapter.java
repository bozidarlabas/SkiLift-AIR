package hr.foi.air.evolaris.skilift.smartphone;

import hr.foi.air.evolaris.skilift.LiftDataManager;
import hr.foi.air.evolaris.skilift.data.Lift;
import hr.foi.evolaris.skilift.R;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;



public class ListAdapter extends BaseExpandableListAdapter {

	private LayoutInflater inflater;
	private Context context;
	private ArrayList<Lift> groups;
	UserInterfaceController userIterfaceController = UserInterfaceController
			.getInstance();


	public ListAdapter(Context context, ArrayList<Lift> groups) {
		// Create Layout Inflator
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.groups = groups;
	}


	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parentView) {
		final Lift group = groups.get(groupPosition);

		convertView = inflater
				.inflate(R.layout.grouprow, parentView, false);

		((TextView) convertView.findViewById(R.id.tvGroup)).setText(group.getLiftName());
		
		CheckBox checkbox = (CheckBox) convertView
				.findViewById(R.id.checkbox);
		checkbox.setChecked(true);
		

		checkbox.setOnCheckedChangeListener(new CheckUpdateListener(group));

		return convertView;
	}
	
	public boolean load(String key){
		SharedPreferences sharedPreferences = context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
		boolean showLift = sharedPreferences.getBoolean(key,false);
		return showLift;
	}

	// This Function used to inflate child rows view
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parentView) {
		Lift child = (Lift) getChild(groupPosition,
				childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.childrow, null);
		}
		TextView tv = (TextView) view.findViewById(R.id.tvChild);
		tv.setText(child.getLiftName());
		tv.setTag("1");
		// TODO Auto-generated method stub
		return view;
	}

	
	// Call when child row clicked
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}


	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	// Call when parent row clicked
	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public void notifyDataSetChanged() {
		// Refresh List rows
		super.notifyDataSetChanged();
	}

	@Override
	public boolean isEmpty() {
		return ((groups == null) || groups.isEmpty());
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	/******************* Checkbox Checked Change Listener ********************/

	private final class CheckUpdateListener implements
			OnCheckedChangeListener {
		private final Lift parent;
		private CheckUpdateListener(Lift parent) {
			this.parent = parent;
		}

		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			parent.setVisibility(isChecked);
			save(parent.getLiftName(), isChecked);
			updateSmartWatch();
		}
		
		public void save(String key, boolean value){
			SharedPreferences sharedPreferences = context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor =  sharedPreferences.edit();
			editor.putBoolean(key, value);
			editor.commit();
		}
		private void updateSmartWatch(){
			Intent initialIntent = userIterfaceController.getCurrentIntent();
			userIterfaceController.changeUserInterface(initialIntent);
		}
		
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		  @Override
		  public void onReceive(Context context, Intent intent) {
		    String message = intent.getStringExtra("message");
		    Log.d("receiver", "Got message: " + message);
		  }
		};

}


