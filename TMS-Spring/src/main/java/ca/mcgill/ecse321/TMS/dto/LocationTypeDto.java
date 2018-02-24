package ca.mcgill.ecse321.TMS.dto;

import java.util.List;

import ca.mcgill.ecse321.TMS.model.LocationType.LandUseType;

public class LocationTypeDto {

	protected LandUseType landUseType;
	
	public LocationTypeDto() {	
	}
	
	public LocationTypeDto(LandUseType landUseType, TreeLocationDto treeLocation) {
		this.landUseType = landUseType;
	}
	
	public LandUseType getLandUseType() {
		return landUseType;
	}
	
	public void setLandUseType(LandUseType landUseType) {
		this.landUseType = landUseType;
	}
	
}
