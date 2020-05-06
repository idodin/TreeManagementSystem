package ca.mcgill.ecse321.TMS.controller;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

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
	public String index() throws InvalidInputException{
		String[] municipalities = {"Montreal_Est", "Montreal_Ouest", "Montreal", "Westmount",
				"Hampstead", "Cte_St_Luc", "Dorval", "Point_Clair", "Ile_Dorval", "Dollard", "Kirkland",
				"Beaconsfield", "Baie_dUrfe", "Ste_Anne"};
		service.createSpecies("Maple", 10, 10);
		service.createSpecies("Oak", 4, 8);
		service.createSpecies("Elm", 12, 4);
		String[] statuses = {"healthy", "diseased", "cut", "tobecut"};
		String[] locations = {"residential", "institutional", "municipal"};
		String[] species = {"maple", "oak", "elm"};
		User admin = new User("admin", "admin", treePLE);
		for(int i = 0 ; i<municipalities.length; ++i) service.createMunicipality(municipalities[i], i+1);
		for(int i = 0; i<100; i++){
			Random rand = new Random();
			double height = rand.nextInt(100) + 1;
			double diameter = rand.nextInt(100) + 1;
			Date date = new Date(Date.from(Instant.now()).getTime());
			Species specie = service.getSpeciesByName(species[rand.nextInt(species.length)]);
			double x = -73.0 - rand.nextDouble();
			double y = 45 + rand.nextDouble();
			Municipality municipality = service.getMunicipalityByName(municipalities[rand.nextInt(municipalities.length)]);
			TreeStatus status = service.createStatus(statuses[rand.nextInt(statuses.length)]);

			LocationType location = service.createLocationType(locations[rand.nextInt(locations.length)]);

			service.createTree(height, diameter, date, status, specie, admin, municipality,
					x, y, "generated", location);
		}
		return "TreePLE application root. Use the REST API to manage trees.\n";
	}

	///////////////	HTTP REQUESTS ///////////////
	// TREES 
	@PostMapping(value = {"/trees/"})
	public TreeDto createTree(
			@RequestParam double height, @RequestParam double diameter, @RequestParam Date datePlanted, 
			@RequestParam String x, @RequestParam String y, @RequestParam String description, @RequestParam String location,
			@RequestParam String status, @RequestParam String species, @RequestParam String municipality, 
			@RequestParam String loggedUser) throws InvalidInputException {	
		
		Species aSpecies = service.getSpeciesByName(species);
		if (aSpecies == null) throw new InvalidInputException("Could not find species");
		Municipality aMunicipality = service.getMunicipalityByName(municipality);
		if (aMunicipality == null) throw new InvalidInputException("Could not find municipality");
		TreeStatus aStatus = service.createStatus(status);
		LocationType aLocationType = service.createLocationType(location);
		User user = service.getUserByName(loggedUser);
		Tree tree = service.createTree(height, diameter, datePlanted, aStatus, aSpecies, 
				user, aMunicipality, Double.parseDouble(x), Double.parseDouble(y), description, aLocationType);
		return convertToDto(tree);
	}
	
	@PostMapping(value = { "/updateTrees", "/updateTrees/" })
	public List<TreeDto> updateTree(
		@RequestParam List<Integer> treeIDs,
		@RequestParam String status) throws InvalidInputException{
		TreeStatus aStatus = service.createStatus(status);
		List <Tree> trees = service.updateTrees(treeIDs, aStatus.getStatus());
		List<TreeDto> treeDtos = new ArrayList<TreeDto>();
		for(Tree tree: trees) 
			treeDtos.add(convertToDto(tree));
		return treeDtos;
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
	
	// SPECIES 
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
	
	// STATUS
	@GetMapping(value = {"/status", "/status/"})
	public List<String> getAllTreeStatuses() {
		return service.getTreeStatuses();
	}
	
	
	// USERS 
	@PostMapping(value = { "/users/{name}", "/users/{name}/" })
	public UserDto createUser(
			@PathVariable("name") String name,
			@RequestParam String password,
			@RequestParam String inputToken) throws InvalidInputException {
		String token="123";
		if(token.equals(inputToken)) {
			return convertToDto(service.register(name, password, true));
		}
		else {
			return convertToDto(service.register(name, password, false));
		}
	}
	
	@GetMapping(value = { "/user/{name}", "/user/{name}/" })
	public UserDto login(
			@PathVariable("name") String name,
			@RequestParam String password) throws InvalidInputException {
		return convertToDto(service.login(name, password));
	}
	
	
	// MUNICIPALITIES 
	@GetMapping(value = { "/municipalities", "/municipalities/" })
	public List<MunicipalityDto> findAllMunicipalities() {
		List<MunicipalityDto> municipalities = Lists.newArrayList();
		for (Municipality m : service.findAllMunicipalities()) 
			municipalities.add(convertToDto(m));
		return municipalities;
	}
	
	@PostMapping(value = { "/municipalities/{name}", "/municipalities/{name}/" })
	public MunicipalityDto createMunicipality(
			@PathVariable("name") String name,
			@RequestParam int id) throws InvalidInputException {
		return convertToDto(service.createMunicipality(name, id));
	}
	
	
	// FORECASTS 
	@GetMapping(value = {"/forecasts/"})
	public int createCarbonForecast(
			@RequestParam Integer[] treeIDs,
			@RequestParam String status) throws InvalidInputException {
		List<Tree> trees = service.findTreesById(treeIDs);
		int result = service.carbonForecast(trees, status);
		return result;
	}
	
	@GetMapping(value = {"/oxygen/"})
	public int createOxygenForecast(
			@RequestParam Integer[] treeIDs,
			@RequestParam String status) throws InvalidInputException {
		List<Tree> trees = service.findTreesById(treeIDs);
		int result = service.oxygenForecast(trees, status);
		return result;
	}
	
	@GetMapping(value = {"/biodiversity/"})
	public int createBioForecast(
			@RequestParam Integer[] treeIDs,
			@RequestParam String status) throws InvalidInputException {
		List<Tree> trees = service.findTreesById(treeIDs);
		int result = service.bioForecast(trees, status);
		return result;
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
			if (role instanceof Local) 
				roles.add("local");
			if (role instanceof Specialist) 
				roles.add("specialist");
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
