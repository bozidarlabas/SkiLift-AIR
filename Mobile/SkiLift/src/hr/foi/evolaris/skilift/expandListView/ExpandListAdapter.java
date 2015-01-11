package hr.foi.evolaris.skilift.expandListView;

import hr.foi.evolaris.skilift.AdvancedLayoutsExtensionService;
import hr.foi.evolaris.skilift.model.Lift;
import hr.foi.evolaris.skilift.model.LiftDetail;
import hr.foi.evolaris.skilift.swcontrols.ListControlExtension;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.sonymobile.smartextension.hellonotification.R;



public class ExpandListAdapter extends BaseExpandableListAdapter {

	public static ArrayList<Lift> lifts = new ArrayList<>();
	public static ArrayList<Lift> liftsBuffer = new ArrayList<>();
	
	private LayoutInflater inflater;
	
	private Context context;
	private ArrayList<Lift> groups;

	public ExpandListAdapter(Context context, ArrayList<Lift> groups) {
		// Create Layout Inflator
		inflater = LayoutInflater.from(context);
		
		this.context = context;
		this.groups = groups;
	}

	// This Function used to inflate parent rows view

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parentView) {
		final Lift group = groups.get(groupPosition);

		// Inflate grouprow.xml file for parent rows
		convertView = inflater
				.inflate(R.layout.grouprow, parentView, false);

		// Get grouprow.xml file elements and set values
		((TextView) convertView.findViewById(R.id.tvGroup)).setText(group.getName());
		


		// Get grouprow.xml file checkbox elements
		CheckBox checkbox = (CheckBox) convertView
				.findViewById(R.id.checkbox);
		checkbox.setChecked(load(group.getName()));

		// Set CheckUpdateListener for CheckBox (see below
		// CheckUpdateListener class)
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
		LiftDetail child = (LiftDetail) getChild(groupPosition,
				childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.childrow, null);
		}
		TextView tv = (TextView) view.findViewById(R.id.tvChild);
		tv.setText(child.getName().toString());
		tv.setTag(child.getTag());
		// TODO Auto-generated method stub
		return view;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// Log.i("Childs", groupPosition+"=  getChild =="+childPosition);
		ArrayList<LiftDetail> chList = groups.get(groupPosition)
				.getItems();
		return chList.get(childPosition);
	}

	// Call when child row clicked
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		ArrayList<LiftDetail> chList = groups.get(groupPosition)
				.getItems();

		return chList.size();
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
			
			String liftName = parent.getName();
			save(liftName,isChecked);
			
			Log.i("onCheckedChanged", "isChecked: " + isChecked);
			//parent.setShowLift(isChecked);
			
			if(isChecked){
				changeList(parent);
			}
			/*else{
				for (Lift lift : liftsBuffer) {
					if(lift.getName().equals(parent.getName())){
						ListControlExtension.lifts.add(parent);
						liftsBuffer.remove(lift);
					}
				}
			}*/
			
			
			
			AdvancedLayoutsExtensionService.sm.resume();
			//test.ExpAdapter.notifyDataSetChanged();
			//AdvancedLayoutsExtensionService.sm.resume();
			

		}
		
		
		
		public void save(String key, boolean value){
			SharedPreferences sharedPreferences = context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor =  sharedPreferences.edit();
			editor.putBoolean(key, value);
			editor.commit();
		}
		
		private void changeList(Lift parent){
			ArrayList<Lift> liftCopy = new ArrayList<>();
			for (Lift lift : ListControlExtension.lifts) {
				if(!lift.getName().equals(parent.getName())){
					liftCopy.add(lift);
				}
			}
			ListControlExtension.lifts.clear();
			ListControlExtension.lifts = new ArrayList<Lift>(liftCopy);
			//ListControlExtension.lifts.addAll(liftCopy);
			//ListControlExtension.lifts = liftCopy;
		}
	}
	/***********************************************************************/

}


