package ca.mcgill.ecse321.TMS.service;

import java.io.File;
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
import ca.mcgill.ecse321.TMS.model.TreeStatus.Status;
import ca.mcgill.ecse321.TMS.persistence.PersistenceXStream;


@Service
public class TMSService {
	
	private TreePLE tp;
	
	public TMSService(TreePLE tp) {
		this.tp=tp;
	}
	
	/////////////////////	TREES  /////////////////////
	public Tree createTree(int aHeight, int aDiameter,
			Date aDatePlanted, TreeStatus aTreeStatus,
			Species aSpecies, User aLocal, Municipality aMunicipality,
			int x, int y, String description, LocationType locationType) throws InvalidInputException{
		
		Date aDateAdded = new Date(Calendar.getInstance().getTime().getTime());
		description = checkTreeInputException(aHeight, aDiameter, aDatePlanted, aTreeStatus, aSpecies, aLocal,
				aMunicipality, x, y, description, locationType, aDateAdded);
		Tree tree = tp.addTree(aHeight, aDiameter, aDatePlanted, aDateAdded, aTreeStatus, aSpecies, aLocal, aMunicipality);
		new TreeLocation(x, y, description, tree, locationType);
		PersistenceXStream.saveToXMLwithXStream(tp);
		return tree;
	}
	
	public Tree removeTree(Tree aTree) {
		aTree.delete();
		PersistenceXStream.saveToXMLwithXStream(tp);
		return aTree;
	}
	
	public List<Tree> findAllTrees(){
		return tp.getTrees();
	}
	
	
	/////////////////////	SPECIES  /////////////////////
	public List<Species> findAllSpecies() {
		return tp.getSpecies();
	}
	
	public Species createSpecies(String name, int carbonConsumption, int oxygenProduction) {
		Species sp = new Species(name.toLowerCase(), carbonConsumption, oxygenProduction, tp);
		PersistenceXStream.saveToXMLwithXStream(tp);
		return sp;
	}
	
	public Species getSpeciesByName(String name) {
		List<Species> species = tp.getSpecies();
		for (Species sp: species) {
			if (sp.getName().equals(name)) return sp;
		}
		return null;
	}
	
	
	/////////////////////	MUNICIPALITIES  /////////////////////
	public List<Municipality> findAllMunicipalities() {
		return tp.getMunicipalities();
	}
	
	public Municipality createMunicipality(String name, int id) {
		Municipality m = new Municipality(id, name, tp);
		PersistenceXStream.saveToXMLwithXStream(tp);
		return m;
	}
	
	public Municipality getMunicipalityByName(String name) {
		List<Municipality> municipalities = tp.getMunicipalities();
		for (Municipality m: municipalities) {
			if (m.getName().equals(name)) return m;
		}
		return null;
	}
	

	/////////////////////	OTHER  /////////////////////
	
	public String checkTreeInputException(int aHeight, int aDiameter, Date aDatePlanted,
			TreeStatus aTreeStatus, Species aSpecies, User aLocal, Municipality aMunicipality, int x, int y,
			String description, LocationType locationType, Date aDateAdded) throws InvalidInputException {
		String errormsg = "";
		Boolean errorthrown = false;
		

		if (aHeight < 0 || aDiameter < 0 || x < 0 || y < 0) {
			errormsg = "Cannot pass negative integer! ";
			errorthrown = true;
		}
		
		if (description == null || description.trim().length()==0) {
			description = "";
		}
		
		if (aDatePlanted.after(aDateAdded)) {
			errormsg = errormsg + "Cannot plant tree in the future! ";
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
			errormsg = errormsg + "Municipality needs to be selected for registration! ";
			errorthrown = true;
		}
		
		if(errorthrown) {
			errormsg = errormsg.trim();
			throw new InvalidInputException(errormsg);
		}
		
		if (tp.indexOfStatus(aTreeStatus) == -1 ){
			errormsg = errormsg + "Status must exist! ";
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
			errormsg = errormsg.trim();
			throw new InvalidInputException(errormsg);
		}
		return description;
	}
	
	public int calculateOxygenProduction(List<Tree> treeList) throws InvalidInputException {
		int total=0;
		if(treeList.size()==0) {
			throw new InvalidInputException("Please enter a list of trees");
		}
		for(Tree tree: treeList) {
			if(tree.getTreeStatus().getStatus()!=Status.Cut) {
				if(tree.getTreeStatus().getStatus()==Status.Diseased) {
					int index=tree.getSpecies().getOxygenProduction();
					total+=index/2;
				}
				else {
					int index=tree.getSpecies().getOxygenProduction();
					total+=index;
				}
			}
		}
		return total;
	}
	
	public int calculateCarbonConsumption(List<Tree> treeList) throws InvalidInputException {
		int total=0;
		if(treeList.size()==0) {
			throw new InvalidInputException("Please enter a list of trees");
		}
		
		for(Tree tree: treeList) {
			if(tree.getTreeStatus().getStatus()!=Status.Cut) {
				if(tree.getTreeStatus().getStatus()==Status.Diseased) {
					int index=tree.getSpecies().getCarbonConsumption();
					total+=index/2;
				}
				else {
					int index=tree.getSpecies().getCarbonConsumption();
					total+=index;
				}
			}
		}
		return total;
	}
	
	public int bioIndexCalculator(List<Tree> treeList) throws InvalidInputException{
		int index = 0;
		if(treeList == null) {
			throw new InvalidInputException("List cannot be null");
		}
		if(treeList.size()==0) {
			throw new InvalidInputException("List cannot be empty");
		}
		for(Tree tree: treeList) {
			if(tree == null) {
				throw new InvalidInputException("The list contains a null entry");
			}
		}
		
		return 4*2;
	}
	
	public int bioForecast(List<Tree> treeList) throws InvalidInputException{
		int forecast = 0;
		if(treeList == null) {
			throw new InvalidInputException("List cannot be null");
		}
		if(treeList.size()==0) {
			throw new InvalidInputException("List cannot be empty");
		}
		for(Tree tree: treeList) {
			if(tree == null) {
				throw new InvalidInputException("The list contains a null entry");
			}
		}
		
		return 3*2;
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

	
	

	public List<Tree> getTreesForMunicipality(Municipality m) {
		return m.getTrees();
	}


	public List<Tree> getTreesForSpecies(Species s) {
		return s.getTrees();
	}
	
	public Tree markDiseased(Tree tree) throws InvalidInputException{
		// TODO implement method
		return null;
	}
	
	public Tree markToBeCut(Tree tree) throws InvalidInputException {
		// TODO implement method
		return null;
	}

	public int calcChangeOxygenProd(List<Tree> treeList, String string) throws InvalidInputException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int calcChangeCarbonConsump(List<Tree> treeList, String string) throws InvalidInputException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void loadFile(File input) throws InvalidInputException{
		// TODO Auto-generated method stub
		
	}

	public void updateTree(Tree tree, int i, int j, Date newDatePlanted, TreeStatus newStatus, Species newSpecies,
			User newUser, Municipality newMunicipality, int k, int l, String string, LocationType newLocationType) throws InvalidInputException{
		// TODO Auto-generated method stub
		
	}

}