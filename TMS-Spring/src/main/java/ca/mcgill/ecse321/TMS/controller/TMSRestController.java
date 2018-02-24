package ca.mcgill.ecse321.TMS.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.TMS.dto.MunicipalityDto;
import ca.mcgill.ecse321.TMS.dto.SpeciesDto;
import ca.mcgill.ecse321.TMS.dto.TreeDto;
import ca.mcgill.ecse321.TMS.model.Municipality;
import ca.mcgill.ecse321.TMS.model.Species;
import ca.mcgill.ecse321.TMS.model.Tree;
import ca.mcgill.ecse321.TMS.model.TreePLE;
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
	
	//do we have to send systemManager attribute?
	private MunicipalityDto convertToDto(Municipality m) {
		MunicipalityDto municipalityDto = modelMapper.map(m, MunicipalityDto.class);
		municipalityDto.setTrees(createTreeDtosForMunicipality(m));
		return municipalityDto;
	}
	
	private TreeDto convertToDto(Tree t) {
		return modelMapper.map(t, TreeDto.class);
	}
	
	private SpeciesDto convertToDto(Species s) {
		SpeciesDto speciesDto = modelMapper.map(s, SpeciesDto.class);
		speciesDto.setTrees(createTreeDtosForSpecies(s));
		return speciesDto;
	}
	
	
	
	
	
	private List<TreeDto> createTreeDtosForSpecies(Species s) {
		List<Tree> treesForSpecies = service.getTreesForSpecies(s);
		List<TreeDto> trees = new ArrayList<TreeDto>();
		for (Tree tree: treesForSpecies) {
			trees.add(convertToDto(tree));
		}
		return trees;
	}

	private List<TreeDto> createTreeDtosForMunicipality(Municipality m) {
		List<Tree> treesForMunicipality = service.getTreesForMunicipality(m);
		List<TreeDto> trees = new ArrayList<TreeDto>();
		for (Tree tree: treesForMunicipality) {
			trees.add(convertToDto(tree));
		}
		return trees;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
