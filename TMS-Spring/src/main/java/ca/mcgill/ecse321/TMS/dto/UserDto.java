package ca.mcgill.ecse321.TMS.dto;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse321.TMS.model.User.UserType;



public class UserDto {
	
	
	private String username;
	private UserType userType;
	
	//will have service method to convert class names to string list
	private List<String> roles;
	private List<TreeDto> trees;
	
	
	public UserDto() {
		
	}
	
	public UserDto(String username) {
		this.username = username;
	}
	public UserDto(String username, ArrayList<String> roles) {
		this.username = username;
		this.roles = roles;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}
	
	public UserType getUserType() {
		return userType;
	}
	
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public List<TreeDto> getTrees() {
		return trees;
	}
	
	public void setTrees(List<TreeDto> trees) {
		this.trees = trees;
	}
	
	
}
