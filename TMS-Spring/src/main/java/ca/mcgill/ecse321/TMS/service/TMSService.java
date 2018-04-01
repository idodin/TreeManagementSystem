package ca.mcgill.ecse321.TMS.service;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
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
import ca.mcgill.ecse321.TMS.model.TreeStatus.Status;
import ca.mcgill.ecse321.TMS.dto.TreeDto;
import ca.mcgill.ecse321.TMS.persistence.PersistenceXStream;


@Service
public class TMSService {
	private TreePLE tp;
	
	public TMSService(TreePLE tp) {
		this.tp=tp;
	}
	
	public Tree createTree(int aHeight, int aDiameter,
			Date aDatePlanted, TreeStatus aTreeStatus,
			Species aSpecies, User aLocal, Municipality aMunicipality,
			int x, int y, String description, LocationType locationType) throws InvalidInputException{
		
		Date aDateAdded = new Date(Calendar.getInstance().getTime().getTime());
		
		description = checkTreeInputException(aHeight, aDiameter, aDatePlanted, aTreeStatus, aSpecies, aLocal,
				aMunicipality, x, y, description, locationType, aDateAdded);
		

		Tree tree = tp.addTree(aHeight, aDiameter, aDatePlanted, aDateAdded, aTreeStatus, aSpecies, aLocal, aMunicipality);
		PersistenceXStream.saveToXMLwithXStream(tp);
		TreeLocation location = new TreeLocation(x, y, description, tree, locationType);
		return tree;
		
	}
	
	public List<Tree> findAllTrees(){
		return tp.getTrees();
	}
	

	public Tree removeTree(Tree aTree) {
		
		aTree.getTreeStatus().setStatus(Status.Cut);
		PersistenceXStream.saveToXMLwithXStream(tp);
		return aTree;
	}


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
	
	public int calcOxygenProd(List<Tree> treeList) throws InvalidInputException {
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
	
	public int oxygenForecast(List<Tree> treeList, String strStatus) throws InvalidInputException{
		if(strStatus==null) {
			throw new InvalidInputException("String cannot be null");
		}
		if(treeList == null) {
			throw new InvalidInputException("List cannot be null");
		}
		if(treeList.size()==0) {
			throw new InvalidInputException("Please enter a list of trees");
		}
		int forecast=0;
		int predicted=0;
		int current=0;
		Status status;
		if("Diseased".equals(strStatus)) {status=Status.Diseased;}
		else if("Cut".equals(strStatus)) {status=Status.Cut;}
		else if("Healthy".equals(strStatus)) {status=Status.Healthy;}
		else if("ToBeCut".equals(strStatus)) {status=Status.Healthy;}
		else {
			throw new InvalidInputException("Please enter a valid tree status");
		}
		for(Tree tree: treeList) {
			if(tree==null) {
				throw new InvalidInputException("Cannot have null entry for tree in list");
			}
			if(status!=Status.Cut) {
				if(status==Status.Diseased) {
					int index=tree.getSpecies().getOxygenProduction();
					predicted+=index/2;
				}
				else {
					int index=tree.getSpecies().getOxygenProduction();
					predicted+=index;
				}
			}	
		}
		current=calcOxygenProd(treeList);
		forecast=predicted-current;
		
		
		return forecast;
	}
	
	public int calcCarbonConsump(List<Tree> treeList) throws InvalidInputException {
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
	
	public int carbonForecast(List<Tree> treeList, String strStatus) throws InvalidInputException{
		if(strStatus==null) {
			throw new InvalidInputException("String cannot be null");
		}
		if(treeList == null) {
			throw new InvalidInputException("List cannot be null");
		}
		if(treeList.size()==0) {
			throw new InvalidInputException("Please enter a list of trees");
		}
		int forecast=0;
		int predicted=0;
		int current=0;
		Status status;
		if("Diseased".equals(strStatus)) {status=Status.Diseased;}
		else if("Cut".equals(strStatus)) {status=Status.Cut;}
		else if("Healthy".equals(strStatus)) {status=Status.Healthy;}
		else if("ToBeCut".equals(strStatus)) {status=Status.Healthy;}
		else {
			throw new InvalidInputException("Please enter a valid tree status");
		}
		
		for(Tree tree: treeList) {
			if(tree==null) {
				throw new InvalidInputException("Cannot have null entry for tree in list");
			}
			if(status!=Status.Cut) {
				if(status==Status.Diseased) {
					int index=tree.getSpecies().getCarbonConsumption();
					predicted+=index/2;
				}
				else {
					int index=tree.getSpecies().getCarbonConsumption();
					predicted+=index;
				}
			}	
		}
		current=calcCarbonConsump(treeList);
		forecast=predicted-current;
		
		
		return forecast;
	}
	
	public int bioIndexCalculator(List<Tree> treeList) throws InvalidInputException{
		int index = 0;
		List <Species> speciesList= new ArrayList<Species>();
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
			if(tree.getTreeStatus().getStatus()!= Status.Cut) {
				Species newSpecies= tree.getSpecies();
				Boolean marker=true;
				for(int i=0; treeList.get(i)!=tree;i++) {
					if(treeList.get(i).getSpecies()==newSpecies) {marker=false;}
				}
				if(marker) {index++;}
				
			}
		}
		
		return index;
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
		forecast=0-bioIndexCalculator(treeList);
		
		return forecast;
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
		if(tree==null) {
			throw new InvalidInputException("Tree needs to be selected to be marked as diseased.");
		}
		if(tree.getTreeStatus().getStatus()== Status.Diseased) {
			throw new InvalidInputException("Tree was already diseased!");
		}
		tree.getTreeStatus().setStatus(Status.Diseased);
		return tree;
	}
	
	public Tree markToBeCut(Tree tree) throws InvalidInputException {
		if(tree==null) {
			throw new InvalidInputException("Tree needs to be selected to be mark as to be cut.");
		}
		if(tree.getTreeStatus().getStatus()==Status.Cut) {
			throw new InvalidInputException("Tree was already cut down!");
		}
		tree.getTreeStatus().setToBeCut(true);
		return tree;
	}

	

	public void loadFile(File input) throws InvalidInputException{
		// TODO Auto-generated method stub
		
	}

	public void updateTree(Tree tree, int i, int j, Date newDatePlanted, TreeStatus newStatus, Species newSpecies,
			User newUser, Municipality newMunicipality, int k, int l, String string, LocationType newLocationType) throws InvalidInputException{
		// TODO Auto-generated method stub
		
	}

}