package ca.mcgill.ecse321.TMS.dto;

import java.sql.Date;

public class TreeDto {
	
	private int id; 
	private int height;
	private int diameter;
	private Date datePlanted;
	private Date dateAdded;
	
	private TreeLocationDto location;
	private TreeStatusDto status;
	private SpeciesDto species;
	private UserDto local;
	private MunicipalityDto municipality;
	
	
	public TreeDto() {
	}

	public TreeDto(int height, int diameter, int id, Date datePlanted, Date dateAdded,
			TreeLocationDto location, TreeStatusDto status, SpeciesDto species,
			UserDto local, MunicipalityDto municipality) {
		this.id = id; 
		this.height = height;
		this.diameter = diameter;
		this.datePlanted = datePlanted;
		this.dateAdded = dateAdded;
		this.species = species; 
		this.location = location;
		this.status = status;
		this.local = local;
		this.municipality = municipality;
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
	
	public Date getDatePlanted() {
		return datePlanted;
	}
	
	public void setDatePlanted(Date datePlanted) {
		this.datePlanted = datePlanted;
	}
	
	public Date getDateAdded() {
		return dateAdded;
	}
	
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
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
	
	public UserDto getUser() {
		return local;
	}
	
	public void setUser(UserDto local) {
		this.local = local;
	}
	
	public MunicipalityDto getMunicipality() {
		return municipality;
	}
	
	public void setMunicipality(MunicipalityDto municipality) {
		this.municipality = municipality;
	}
	
}
