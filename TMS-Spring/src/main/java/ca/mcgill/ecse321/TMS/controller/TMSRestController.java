package ca.mcgill.ecse321.TMS.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.TMS.dto.LocationTypeDto;

import com.google.common.collect.Lists;
import com.thoughtworks.xstream.XStream;

import ca.mcgill.ecse321.TMS.dto.MunicipalityDto;
import ca.mcgill.ecse321.TMS.dto.SpeciesDto;
import ca.mcgill.ecse321.TMS.dto.TreeDto;

import ca.mcgill.ecse321.TMS.dto.TreeLocationDto;
import ca.mcgill.ecse321.TMS.dto.TreeStatusDto;
import ca.mcgill.ecse321.TMS.dto.UserDto;
import ca.mcgill.ecse321.TMS.model.Local;

import ca.mcgill.ecse321.TMS.model.LocationType;
import ca.mcgill.ecse321.TMS.model.LocationType.LandUseType;
import ca.mcgill.ecse321.TMS.model.Municipality;
import ca.mcgill.ecse321.TMS.model.Specialist;
import ca.mcgill.ecse321.TMS.model.Species;
import ca.mcgill.ecse321.TMS.model.Tree;
import ca.mcgill.ecse321.TMS.model.TreeLocation;
import ca.mcgill.ecse321.TMS.model.TreePLE;


import ca.mcgill.ecse321.TMS.model.TreeStatus;
import ca.mcgill.ecse321.TMS.model.TreeStatus.Status;
import ca.mcgill.ecse321.TMS.model.User;
import ca.mcgill.ecse321.TMS.model.UserRole;
import ca.mcgill.ecse321.TMS.persistence.PersistenceXStream;
import ca.mcgill.ecse321.TMS.model.TreeStatus;
import ca.mcgill.ecse321.TMS.model.User;


import ca.mcgill.ecse321.TMS.service.InvalidInputException;

import ca.mcgill.ecse321.TMS.service.TMSService;

@RestController
public class TMSRestController {

	@Autowired
	private TreePLE treePLE;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TMSService service;
	
	@RequestMapping("/")
	public String index() {
		return "TreePLE application root. Use the REST API to manage trees.\n";
	}

	///////////////	TO DO LIST ///////////////
	// Create municipality, if time select municipalities in specific area
	// How can we pass coordinates?
	
	//tree id are all 1 for me, something with persistence
	//deleting a tree
	//update a tree, i.e. information or mark status change
	//if local then can only mark his tree as cut down
	// 
	
	
	
	
	
	
	
	
	
	///////////////	HTTP REQUESTS ///////////////
	@PostMapping(value = {"/trees/"})
	public TreeDto createTree(@RequestParam int height,
			@RequestParam int diameter, 
			@RequestParam Date datePlanted, 
			@RequestParam int x,
			@RequestParam int y, 
			@RequestParam String description,
			@RequestParam String location,
			@RequestParam String status,
			@RequestParam String species,
			@RequestParam String municipality) throws InvalidInputException {	
		Species aSpecies = service.getSpeciesByName(species);
		if (aSpecies == null) throw new InvalidInputException("Could not find species");
		Municipality aMunicipality = service.getMunicipalityByName(municipality);
		if (aMunicipality == null) throw new InvalidInputException("Could not find municipality");
		
		TreeStatus aStatus = new TreeStatus(treePLE); 
		switch(status.toLowerCase()) {
		case "healthy": 
			aStatus.setStatus(Status.Healthy);
			break;
		case "diseased":
			aStatus.setStatus(Status.Diseased);
			break;
		case "cut": 
			aStatus.setStatus(Status.Cut);
			break;
		default:
			treePLE.removeStatus(aStatus);
			throw new InvalidInputException("Must select status");
		}
		LocationType aLocationType = new LocationType(); 
		switch(location.toLowerCase()) {
		case "residential": 
			aLocationType.setLandUseType(LandUseType.Residential);
			break;
		case "institutional":
			aLocationType.setLandUseType(LandUseType.Institutional);
			break;
		case "municipal": 
			aLocationType.setLandUseType(LandUseType.Municipal);
			break;
		default:
			throw new InvalidInputException("Must select location type");
		}
		User testUser;
		if(treePLE.getUsers().size()==0) {
			testUser = treePLE.addUser("Imad");
		}
		else {
			testUser = treePLE.getUser(0);
		}
		Tree tree = service.createTree(height, diameter, datePlanted, aStatus, aSpecies, testUser, aMunicipality, x, y, description, aLocationType);
		System.out.println(tree.getId());
		return convertToDto(tree);
	}

	@GetMapping(value = { "/trees", "/trees/" })
	public List<TreeDto> findAllTrees() {
		List<TreeDto> trees = Lists.newArrayList();
		for (Tree tree : service.findAllTrees()) {
			trees.add(convertToDto(tree));
		}
		return trees;
	}

	@PostMapping(value = { "/removeTree/{id}", "/removeTree/{id}/" })
	public TreeDto removeTree(@PathVariable ("id") int id) throws InvalidInputException {
		Tree t = service.getTreeById(id);
		TreeDto treeDto = convertToDto(t);
		service.removeTree(t);
		return treeDto;
	}
	
	@PostMapping(value = { "/species/{name}", "/species/{name}/" })
	public SpeciesDto createSpecies(
			@PathVariable("name") String name,
			@RequestParam int carbonConsumption,
			@RequestParam int oxygenProduction) throws InvalidInputException {
		return convertToDto(service.createSpecies(name, carbonConsumption, oxygenProduction));
	}
	
	@GetMapping(value = { "/species", "/species/" })
	public List<SpeciesDto> findAllSpecies() {
		List<SpeciesDto> species = Lists.newArrayList();
		for (Species sp : service.findAllSpecies()) {
			species.add(convertToDto(sp));
		}
		return species;
	}
	
	@GetMapping(value = { "/municipalities", "/municipalities/" })
	public List<MunicipalityDto> findAllMunicipalities() {
		List<MunicipalityDto> municipalities = Lists.newArrayList();
		for (Municipality m : service.findAllMunicipalities()) {
			municipalities.add(convertToDto(m));
		}
		return municipalities;
	}
	
	@PostMapping(value = { "/municipalities/{name}", "/municipalities/{name}/" })
	public MunicipalityDto createMunicipality(
			@PathVariable("name") String name,
			@RequestParam int id) throws InvalidInputException {
		return convertToDto(service.createMunicipality(name, id));
	}
	
	
	///////////////	DTO CONVERSION METHODS ///////////////
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
		for (UserRole role : user.getUserRoles()) {
			if (role instanceof Local) {
				roles.add("local");
			}
			if (role instanceof Specialist) {
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