package ca.mcgill.ecse321.TMS.dto;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.TMS.model.TreeLocation;

public class MunicipalityDto {
	
	//not sure about the private part for now
	private String idNumber;
	private String name;
	
	
	
	public MunicipalityDto() {
		
	}
	
	public MunicipalityDto(String idNumber, String name) {
		this.idNumber = idNumber;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	
}
