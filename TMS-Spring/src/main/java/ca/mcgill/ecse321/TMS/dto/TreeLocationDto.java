package ca.mcgill.ecse321.TMS.dto;

import ca.mcgill.ecse321.TMS.model.LocationType;
import ca.mcgill.ecse321.TMS.model.Municipality;
import ca.mcgill.ecse321.TMS.model.Tree;

public class TreeLocationDto {
	
	
	private int x;
	private int y;
	private String description;
	
	
	//private Tree tree;
	private LocationTypeDto locationType;
	
	
	
	public TreeLocationDto() {
		
	}
	
	public TreeLocationDto(int x, int y, String description, Tree tree, LocationTypeDto locationType) {
		this.x = x; 
		this.y = y; 
		this.description = description;
		this.locationType = locationType;
	}

	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public LocationTypeDto getLandLocationType() {
		return locationType;
	}

	public void setLocationType(LocationTypeDto locationType) {
		this.locationType = locationType;
	}

	
}
