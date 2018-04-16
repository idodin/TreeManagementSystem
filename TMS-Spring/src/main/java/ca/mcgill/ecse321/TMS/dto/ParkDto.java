package ca.mcgill.ecse321.TMS.dto;

import ca.mcgill.ecse321.TMS.model.LocationType.LandUseType;

public class ParkDto extends LocationTypeDto{

	private int parkCode;
	private String parkName;
	
	public ParkDto() {
	}
	
	public ParkDto(int parkCode, String parkName, LandUseType landUseType) {
		this.parkCode = parkCode;
		this.parkName = parkName;
		this.landUseType = landUseType;
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
