package ca.mcgill.ecse321.TMS.dto;

public class TreeDto {
	private int height;
	private int diameter;
	private int id; 
	private TreeLocationDto location;
	private TreeStatusDto status;
	private SpeciesDto species;
	
	public TreeDto() {
		
	}
	
	public TreeDto(int height, int diameter, int id, TreeLocationDto location, TreeStatusDto status, SpeciesDto species) {
		this.id = id; 
		this.height = height;
		this.diameter = diameter;
		this.species = species; 
		this.location = location;
		this.status = status;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TreeLocationDto getLocation() {
		return location;
	}

	public void setLocation(TreeLocationDto location) {
		this.location = location;
	}

	public TreeStatusDto getStatus() {
		return status;
	}

	public void setStatus(TreeStatusDto status) {
		this.status = status;
	}

	public SpeciesDto getSpecies() {
		return species;
	}

	public void setSpecies(SpeciesDto species) {
		this.species = species;
	}
	
}
