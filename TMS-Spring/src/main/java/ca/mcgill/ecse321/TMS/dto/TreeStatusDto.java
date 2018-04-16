package ca.mcgill.ecse321.TMS.dto;

import ca.mcgill.ecse321.TMS.model.TreeStatus.Status;

public class TreeStatusDto {
	
	private Status status;
	
	public TreeStatusDto() {
	}
	
	public TreeStatusDto(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
