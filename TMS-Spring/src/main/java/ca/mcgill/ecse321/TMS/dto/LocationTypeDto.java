package ca.mcgill.ecse321.TMS.dto;

import java.util.List;

import ca.mcgill.ecse321.TMS.model.LocationType.LandUseType;

public class LocationTypeDto {

	
	
	protected LandUseType landUseType;
	protected List<TreeLocationDto> treeLocations;

	
	
	public LocationTypeDto() {	
	}
	
	public LocationTypeDto(LandUseType landUseType, List<TreeLocationDto> treeLocations) {
		this.landUseType = landUseType;
		this.treeLocations = treeLocations;
	}
	
	public LandUseType getLandUseType() {
		return landUseType;
	}
	
	public void setLandUseType(LandUseType landUseType) {
		this.landUseType = landUseType;
	}
	
	public List<TreeLocationDto> getTreeLocations() {
		return treeLocations;
	}
	
	public void setTreeLoctions(List<TreeLocationDto> treeLocations) {
		this.treeLocations = treeLocations;
	}
	
}
