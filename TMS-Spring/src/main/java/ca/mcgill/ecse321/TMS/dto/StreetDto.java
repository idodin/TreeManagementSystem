package ca.mcgill.ecse321.TMS.dto;

import java.util.List;

import ca.mcgill.ecse321.TMS.model.LocationType.LandUseType;

public class StreetDto extends LocationTypeDto{

	private String streetName;
	
	public StreetDto() {
		
	}
	
	public StreetDto(String streetName, LandUseType landUseType, List<TreeLocationDto> treeLocations) {
		this.streetName = streetName;
		this.landUseType = landUseType;
	}
	
	public String getStreetName() {
		return streetName;
	}
	
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
}

