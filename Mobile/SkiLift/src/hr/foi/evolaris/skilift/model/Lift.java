package hr.foi.evolaris.skilift.model;


public class Lift {

	private String name;
	private String capacity;
	private boolean filter;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCapacity(){
		return this.capacity;
	}
	
	public void setCapacity(String capacity){
		this.capacity = capacity;
	}
	
	public Boolean getFilter(){
		return this.filter;
	}
	
	public void setFilter(Boolean filter){
		this.filter = filter;
	}
	
	
	
}
