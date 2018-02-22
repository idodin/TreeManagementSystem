package ca.mcgill.ecse321.TMS.dto;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.TMS.model.TreeLocation;

public class MunicipalityDto {
	String name;
	int bioDiv;
	private List<TreeLocation> treeLocations;
	
	public MunicipalityDto() {
		
	}
	
	public MunicipalityDto(String name, int bioDiv, ArrayList<TreeLocation> treeLocations) {
		this.name = name;
		this.bioDiv = bioDiv;
		this.treeLocations = treeLocations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBioDiv() {
		return bioDiv;
	}

	public void setBioDiv(int bioDiv) {
		this.bioDiv = bioDiv;
	}

	public List<TreeLocation> getTreeLocations() {
		return treeLocations;
	}

	public void setTreeLocations(List<TreeLocation> treeLocations) {
		this.treeLocations = treeLocations;
	}
	
	
}
