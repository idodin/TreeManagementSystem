package ca.mcgill.ecse321.TMS.dto;
import ca.mcgill.ecse321.TMS.model.Tree;

public class TreeLocationDto {
	
	private double x;
	private double y;
	private String description;
	
	private LocationTypeDto locationType;
	
	public TreeLocationDto() {
	}
	
	public TreeLocationDto(double x, double y, String description, Tree tree, LocationTypeDto locationType) {
		this.x = x; 
		this.y = y; 
		this.description = description;
		this.locationType = locationType;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
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
