package ca.mcgill.ecse321.TMS.dto;

import java.util.ArrayList;
import java.util.List;

public class MunicipalityDto {
	String name;
	int bioDiv;
	private List<TreeLocationDto> treeLocations;
	
	public MunicipalityDto() {
		
	}
	
	public MunicipalityDto(String name, int bioDiv, ArrayList<TreeLocationDto> treeLocations) {
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

	public List<TreeLocationDto> getTreeLocations() {
		return treeLocations;
	}

	public void setTreeLocations(List<TreeLocationDto> treeLocations) {
		this.treeLocations = treeLocations;
	}
	
	
}
