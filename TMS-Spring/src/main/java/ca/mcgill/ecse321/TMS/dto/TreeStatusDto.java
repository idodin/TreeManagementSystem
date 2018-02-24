package ca.mcgill.ecse321.TMS.dto;

import java.sql.Date;
import java.util.List;

import ca.mcgill.ecse321.TMS.model.Tree;
import ca.mcgill.ecse321.TMS.model.TreeStatus.Status;

public class TreeStatusDto {
	
	//im removing the definition of the enumeration from the Dto classes
	//and instead importing the class so that we can identify only one place to change
	//in the future if we need to
	private Status status;
	private List<TreeDto> trees;
	
	
	public TreeStatusDto() {
		
	}
	
	public TreeStatusDto(Status status, List<TreeDto> trees) {
		this.status = status;
		this.trees = trees; 
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<TreeDto> getTree() {
		return trees;
	}

	public void setTree(List<TreeDto> trees) {
		this.trees = trees;
	}
	
	
}
