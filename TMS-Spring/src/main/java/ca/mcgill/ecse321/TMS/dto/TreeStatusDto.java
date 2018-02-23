package ca.mcgill.ecse321.TMS.dto;

import java.sql.Date;

import ca.mcgill.ecse321.TMS.model.Tree;

public class TreeStatusDto {
	
	public enum Status { Healthy, Diseased, ToBeCut, Cut }
	private Status status;
	private Date dateOfBirth;
	private Date dateOfDeath;
	private Tree tree;
	
	public TreeStatusDto() {
		
	}
	
	public TreeStatusDto(Status status, Date dateOfBirth, Date dateOfDeath, Tree tree) {
		this.status = status;
		this.dateOfBirth = dateOfBirth;
		this.dateOfDeath = dateOfDeath;
		this.tree = tree; 
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}
	
	
}
