package hr.foi.evolaris.skilift.model;

import java.util.ArrayList;


public class Lift {

	private String name;
	private boolean showLift;
	private ArrayList<LiftDetail> Items;

	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<LiftDetail> getItems() {
		return Items;
	}
	public void setItems(ArrayList<LiftDetail> Items) {
		this.Items = Items;
	}
	
	public boolean getShowLift() {
		return this.showLift;
	}
	
	public void setShowLift(boolean showLift) {
		this.showLift = showLift;
	}
	
	
	
	
	
}
