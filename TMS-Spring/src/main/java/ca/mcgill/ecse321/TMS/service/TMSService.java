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
		if(this.getTreeById(aId) == null) {
			throw new InvalidInputException("Tree with that ID already exists!");
		}
		else if (aId <= 0 || aHeight <= 0 || aDiameter <= 0 || x <= 0 || y <= 0) {
			throw new InvalidInputException("Cannot pass negative integer!");
		}
		else if (aDatePlanted.before(aDateAdded)) {
			throw new InvalidInputException("Cannot plant tree in the future!");
		}
		else if (aTreeStatus == null) {
			throw new InvalidInputException("Status needs to be selected for registration!");
		}
		else if (aSpecies == null) {
			throw new InvalidInputException("Species needs to be selected for registration!");
		}
		else if (aLocal == null) {
			throw new InvalidInputException("You need to be logged in!");
		}
		else if (aMunicipality == null) {
			throw new InvalidInputException("Municipality needs to be selected for registration!");
		}
		else if (tp.indexOfStatus(aTreeStatus) == -1) {
			throw new InvalidInputException("Status must exist!");
		}
		else if (tp.indexOfSpecies(aSpecies) == -1) {
			throw new InvalidInputException("Species must exist!");
		}
		else if (tp.indexOfUser(aLocal) == -1) {
			throw new InvalidInputException("User must be registered!");
		}
		else if (tp.indexOfMunicipality(aMunicipality) == -1) {
			throw new InvalidInputException("Municipality must exist!");
		}
		if(locationType instanceof Park) {
			if(tp.indexOfPark((Park)locationType)==-1){
				throw new InvalidInputException("Park must exist!");
			}
		}
		if(locationType instanceof Street) {
			if(tp.indexOfStreet((Street)locationType) == -1) {
				throw new InvalidInputException("Street must exist!");
			}
		}
		
		Tree tree = tp.addTree(aId, aHeight, aDiameter, aDatePlanted, aDateAdded, aTreeStatus, aSpecies, aLocal, aMunicipality);
		TreeLocation location = new TreeLocation(x, y, description, tree, locationType);
		return tree;
		
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

