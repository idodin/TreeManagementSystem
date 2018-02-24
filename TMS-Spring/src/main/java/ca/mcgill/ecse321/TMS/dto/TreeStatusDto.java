package ca.mcgill.ecse321.TMS.dto;

import java.sql.Date;
import java.util.List;

import ca.mcgill.ecse321.TMS.model.Tree;
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
