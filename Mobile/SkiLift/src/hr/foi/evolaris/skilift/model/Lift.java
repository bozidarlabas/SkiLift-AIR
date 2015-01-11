package hr.foi.evolaris.skilift.model;

import hr.foi.evolaris.skilift.expandListView.ExpandListChild;

import java.util.ArrayList;


public class Lift {

	private String name;
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
	
	
	
	
	
}
