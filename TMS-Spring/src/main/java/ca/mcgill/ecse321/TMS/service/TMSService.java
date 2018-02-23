package ca.mcgill.ecse321.TMS.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

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
	private TreePLE tp;
	
	public TMSService(TreePLE tp) {
		this.tp=tp;
	}
	
	public Tree createTree(int aId, int aHeight, int aDiameter,
			Date aDatePlanted, TreeStatus aTreeStatus,
			Species aSpecies, User aLocal, Municipality aMunicipality,
			int x, int y, String description, LocationType locationType) throws InvalidInputException{
		
		Date aDateAdded = new Date(Calendar.getInstance().getTime().getTime());
		
		description = checkTreeInputException(aId, aHeight, aDiameter, aDatePlanted, aTreeStatus, aSpecies, aLocal,
				aMunicipality, x, y, description, locationType, aDateAdded);
		
		Tree tree = tp.addTree(aId, aHeight, aDiameter, aDatePlanted, aDateAdded, aTreeStatus, aSpecies, aLocal, aMunicipality);
		TreeLocation location = new TreeLocation(x, y, description, tree, locationType);
		return tree;
		
	}

	private String checkTreeInputException(int aId, int aHeight, int aDiameter, Date aDatePlanted,
			TreeStatus aTreeStatus, Species aSpecies, User aLocal, Municipality aMunicipality, int x, int y,
			String description, LocationType locationType, Date aDateAdded) throws InvalidInputException {
		String errormsg = "";
		Boolean errorthrown = false;
		
		if (aId <= 0 || aHeight <= 0 || aDiameter <= 0 || x <= 0 || y <= 0) {
			errormsg = "Cannot pass negative integer! ";
			errorthrown = true;
		}
		
		if (description == null || description.trim().length()==0) {
			description = "";
		}
		
		if(this.getTreeById(aId) != null) {
			errormsg = errormsg + "Tree with that ID already exists! ";
			errorthrown = true;
		}
		
		if (aDatePlanted.before(aDateAdded)) {
			errormsg = errormsg + "Cannot plant tree in the future!";
			errorthrown = true;
		}
		
		if (aTreeStatus == null) {
			errormsg = errormsg + "Status needs to be selected for registration! ";
			errorthrown = true;
		}
		
		if (aSpecies == null) {
			errormsg = errormsg + "Species needs to be selected for registration! ";
			errorthrown = true;
		}
		
		if (aLocal == null) {
			errormsg = errormsg + "User needs to be logged in for registration! ";
			errorthrown = true;
		}
		
		if (aMunicipality == null) {
			errormsg = errormsg + "Municipality needs to be selected for registration!";
			errorthrown = true;
		}
		
		if(errorthrown) {
			errormsg = errormsg.trim();
			throw new InvalidInputException(errormsg);
		}
		
		if (tp.indexOfStatus(aTreeStatus) == -1){
			errormsg = "Status must exist! ";
			errorthrown = true;
		}
		
		if (tp.indexOfSpecies(aSpecies) == -1) {
			errormsg = errormsg + "Species must exist! ";
			errorthrown = true;
		}
		
		if (tp.indexOfUser(aLocal) == -1) {
			errormsg = errormsg + "User must be registered! ";
			errorthrown = true;
		}
		
		if (tp.indexOfMunicipality(aMunicipality) == -1) {
			errormsg = errormsg + "Municipality must exist! ";
			errorthrown = true;
		}
		
		if(locationType instanceof Park) {
			if(tp.indexOfPark((Park)locationType)==-1){
				errormsg = errormsg + "Park must exist! ";
				errorthrown = true;
			}
		}
		
		if(locationType instanceof Street) {
			if(tp.indexOfStreet((Street)locationType) == -1) {
				errormsg = errormsg + "Street must exist! ";
				errorthrown = true;
			}
		}
		
		if(errorthrown) {
			throw new InvalidInputException(errormsg);
		}
		return description;
	}
	
	public Tree getTreeById(int aId) {
		List<Tree> trees = tp.getTrees();
		for(Tree tree : trees) {
			if(tree.getId() == aId) {
				return tree;
			}
		}
		return null;
	}
}

