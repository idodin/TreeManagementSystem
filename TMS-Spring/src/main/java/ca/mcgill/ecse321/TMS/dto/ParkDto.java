package ca.mcgill.ecse321.TMS.dto;

import java.util.List;

import ca.mcgill.ecse321.TMS.model.LocationType.LandUseType;

public class ParkDto extends LocationTypeDto{

	
	private int parkCode;
	private String parkName;
	
	public ParkDto() {
	}
	
	public ParkDto(int parkCode, String parkName, LandUseType landUseType, List<TreeLocationDto> treeLocations) {
		this.parkCode = parkCode;
		this.parkName = parkName;
		this.landUseType = landUseType;
		this.treeLocations = treeLocations;
	}
	
	public int getParkCode() {
		return parkCode;
	}
	
	public void setParkCode(int parkCode) {
		this.parkCode = parkCode;
	}
	
	public String getParkName() {
		return parkName;
	}
	
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

}
