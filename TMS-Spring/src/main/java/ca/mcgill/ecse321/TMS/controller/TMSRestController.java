package ca.mcgill.ecse321.TMS.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.TMS.dto.LocationTypeDto;
import ca.mcgill.ecse321.TMS.dto.MunicipalityDto;
import ca.mcgill.ecse321.TMS.dto.SpeciesDto;
import ca.mcgill.ecse321.TMS.dto.TreeDto;
import ca.mcgill.ecse321.TMS.dto.TreeLocationDto;
import ca.mcgill.ecse321.TMS.dto.TreeStatusDto;
import ca.mcgill.ecse321.TMS.dto.UserDto;
import ca.mcgill.ecse321.TMS.model.Local;
import ca.mcgill.ecse321.TMS.model.LocationType;
import ca.mcgill.ecse321.TMS.model.Municipality;
import ca.mcgill.ecse321.TMS.model.Specialist;
import ca.mcgill.ecse321.TMS.model.Species;
import ca.mcgill.ecse321.TMS.model.Tree;
import ca.mcgill.ecse321.TMS.model.TreeLocation;
import ca.mcgill.ecse321.TMS.model.TreePLE;
import ca.mcgill.ecse321.TMS.model.TreeStatus;
import ca.mcgill.ecse321.TMS.model.User;
import ca.mcgill.ecse321.TMS.model.UserRole;
import ca.mcgill.ecse321.TMS.service.TMSService;

@RestController
public class TMSRestController {
	
	@Autowired
	private TreePLE treePLE; 
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@RequestMapping("/")
	public String index() {
		return "TreePLE application root. Use the REST API to manage trees.\n";
	}
	
	@Autowired
	private TMSService service;
	
	/*
	 * Here will have get and post requests
	 */
	
	//TODO Conversion methods
	
	
	
	private MunicipalityDto convertToDto(Municipality m) {
		return modelMapper.map(m, MunicipalityDto.class);
	}
	
	private TreeLocationDto convertToDto(TreeLocation tl) {
		TreeLocationDto tlDto = modelMapper.map(tl, TreeLocationDto.class);
		tlDto.setLocationType(convertToDto(tl.getLocationType()));
		return tlDto;
	}
	private LocationTypeDto convertToDto(LocationType lt) {
		return modelMapper.map(lt, LocationTypeDto.class);
	}
	
	private TreeStatusDto convertToDto(TreeStatus ts) {
		return modelMapper.map(ts, TreeStatusDto.class);
	}
	
	private SpeciesDto convertToDto(Species s) {
		return modelMapper.map(s, SpeciesDto.class);
	}
	
	private UserDto convertToDto(User user) {
		ArrayList<String> roles = new ArrayList<String>();
		UserDto usD = modelMapper.map(user, UserDto.class);
		for(UserRole role : user.getUserRoles()) {
			if(role instanceof Local) {
				roles.add("local");
			} if (role instanceof Specialist) {
				roles.add("specialist");
			}
		}
		usD.setRoles(roles);
		return usD;
	}
	
	private TreeDto convertToDto(Tree t) {
		TreeDto treeDto = modelMapper.map(t, TreeDto.class);
		treeDto.setLocation(convertToDto(t.getTreeLocation()));
		treeDto.setStatus(convertToDto(t.getTreeStatus()));
		treeDto.setSpecies(convertToDto(t.getSpecies()));
		treeDto.setUser(convertToDto(t.getLocal()));
		treeDto.setMunicipality(convertToDto(t.getMunicipality()));
		return treeDto;
	}
	
	
	
	
	
}
