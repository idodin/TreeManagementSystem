package ca.mcgill.ecse321.TMS.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.TMS.model.TreePLE;

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
	
	/*
	 * Here will have get and post requests
	 */
	
	//TODO Conversion methods	
	
}
