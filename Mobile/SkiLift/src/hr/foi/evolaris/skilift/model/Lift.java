package hr.foi.evolaris.skilift.model;


public class Lift {

	private String name;
	private int capacity;
	private boolean filter;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCapacity(){
		return this.capacity;
	}
	
	public void setCapacity(int capacity){
		this.capacity = capacity;
	}
	
	public Boolean getFilter(){
		return this.filter;
	}
	
	public void setFilter(Boolean filter){
		this.filter = filter;
	}
	
	
	
}
