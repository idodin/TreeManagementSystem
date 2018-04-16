package ca.mcgill.ecse321.TMS.dto;

public class MunicipalityDto {
	
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