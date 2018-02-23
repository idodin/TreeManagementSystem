package ca.mcgill.ecse321.TMS.service;

import java.util.List;
import java.sql.Date;
import java.util.Calendar;

import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.TMS.model.LocationType;
import ca.mcgill.ecse321.TMS.model.Municipality;
import ca.mcgill.ecse321.TMS.model.Park;
import ca.mcgill.ecse321.TMS.model.Species;
import ca.mcgill.ecse321.TMS.model.Street;
import ca.mcgill.ecse321.TMS.model.Tree;
import ca.mcgill.ecse321.TMS.model.TreeLocation;
import ca.mcgill.ecse321.TMS.model.TreePLE;
import ca.mcgill.ecse321.TMS.model.TreeStatus;
import ca.mcgill.ecse321.TMS.model.User;



@Service
public class TMSService {

	
	

	public List<Tree> getTreesForMunicipality(Municipality m) {
		return m.getTrees();
	}


	public List<Tree> getTreesForSpecies(Species s) {
		return s.getTrees();
	}

}
