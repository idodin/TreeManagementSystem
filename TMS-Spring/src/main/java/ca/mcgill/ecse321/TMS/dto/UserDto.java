package ca.mcgill.ecse321.TMS.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
	private String name;
	private List<String> roles;
	
	
	public UserDto() {
		
	}
	
	public UserDto(String name, ArrayList<String> roles) {
		this.name = name;
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
}
