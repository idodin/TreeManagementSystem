package ca.mcgill.ecse321.TMS.service;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
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
import ca.mcgill.ecse321.TMS.model.User.UserType;
import ca.mcgill.ecse321.TMS.model.TreeStatus.Status;
import ca.mcgill.ecse321.TMS.persistence.PersistenceXStream;


@Service
public class TMSService {
	
	private TreePLE tp;
	
	public TMSService(TreePLE tp) {
		this.tp=tp;
	}
	
	/////////////////////	TREES  /////////////////////
	public Tree createTree(double aHeight, double aDiameter,
			Date aDatePlanted, TreeStatus aTreeStatus,
			Species aSpecies, User aLocal, Municipality aMunicipality,
			double x, double y, String description, LocationType locationType) throws InvalidInputException{
		if( (x<-74.0) || (x>-73.0) ) {
			throw new InvalidInputException("Please enter longitude within montreal area, range:[-74.0 ~ -73.0]");
		}
		if( (y<45.0) || (y>46.0) ) {
			throw new InvalidInputException("Please enter latitude within montreal area, range:[45.0 ~ 46.0]");
		}
		List<Tree> trees= tp.getTrees();
		for(Tree aTree: trees) {
			if( (x==aTree.getTreeLocation().getX()) && (y==aTree.getTreeLocation().getY()) ) {
				throw new InvalidInputException("A tree already exists at the specified coordinates");			}
		}
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
	
	public List<Tree> findTreesById(Integer[] treeIds) throws InvalidInputException{
		List<Tree> trees = new ArrayList<Tree>();
		boolean wasAdded = false;
		for(Integer i: treeIds) {
			for (Tree t: tp.getTrees()) {
				if (t.getId() == i) {
					trees.add(t);
					wasAdded = true;
				}
			}
			if(wasAdded) wasAdded = false;
			else throw new InvalidInputException("One or more trees do not exist!");
		}
		return trees;
	}
	
	public void updateTree(Tree tree, int i, int j, Date newDatePlanted, TreeStatus newStatus, Species newSpecies,
			User newUser, Municipality newMunicipality, int k, int l, String string, LocationType newLocationType) throws InvalidInputException{
		// TODO Auto-generated method stub
	}
	
	
	/////////////////////	SPECIES  /////////////////////
	public List<Species> findAllSpecies() {
		return tp.getSpecies();
	}
	
	public Species createSpecies(String name, int carbonConsumption, int oxygenProduction) throws InvalidInputException{
		if( ( carbonConsumption < 0 ) || (oxygenProduction <0) ) {
			throw new InvalidInputException(" Amount of carbon consumption or oxygen production cannot be negative");
		}
		if((name==null)|| (name.trim().equals("")) ){
			throw new InvalidInputException(" Please enter a species name");
		}
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
	
	public Municipality createMunicipality(String name, int id) throws InvalidInputException{
		if((name==null)|| (name.trim().equals("")) ){
			throw new InvalidInputException(" Please enter a municiplaity name");
		}
		List<Municipality> muniList=tp.getMunicipalities();
		for(Municipality muni: muniList) {
			if(id==muni.getIdNumber()) {
				throw new InvalidInputException(" ID already exists. Try another one please.");
			}
		}
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
	
	
	/////////////////////	STATUS  /////////////////////
	public List<String> getTreeStatuses() {
		Status[] statuses = TreeStatus.Status.values();
		List<String> st = new ArrayList<String>();
		for (Status s: statuses) {
			st.add(s.toString());
		}
		return st;
	}

	
	/////////////////////	ERROR HANDLING  /////////////////////
	public String checkTreeInputException(double aHeight, double aDiameter, Date aDatePlanted,
			TreeStatus aTreeStatus, Species aSpecies, User aLocal, Municipality aMunicipality, double x, double y,
			String description, LocationType locationType, Date aDateAdded) throws InvalidInputException {
		
		String errormsg = "";
		Boolean errorthrown = false;
		if (aHeight < 0 || aDiameter < 0) {
			errormsg = "Height and diameter cannot be negative! ";
			errorthrown = true;
		}
		if ((x < -180 || x > 180) || (y < -180 || y > 180)) {
			errormsg += "Invalid coordinates! ";
			errorthrown = true;
		}
		if (description == null || description.trim().length()==0) {
			description = "";
		}
		if (aDatePlanted.after(aDateAdded)) {
			errormsg += "Cannot plant tree in the future! ";
			errorthrown = true;
		}
		if (aTreeStatus == null) {
			errormsg += "Status needs to be selected for registration! ";
			errorthrown = true;
		}
		if (aSpecies == null) {
			errormsg += "Species needs to be selected for registration! ";
			errorthrown = true;
		}
		if (aLocal == null) {
			errormsg += "User needs to be logged in for registration! ";
			errorthrown = true;
		}
		if (aMunicipality == null) {
			errormsg += "Municipality needs to be selected for registration! ";
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
		double forecast=0;
		double predicted=0;
		double current=0;
		Status status;
		if("diseased".equals(strStatus.toLowerCase())) {status=Status.Diseased;}
		else if("cut".equals(strStatus.toLowerCase())) {status=Status.Cut;}
		else if("healthy".equals(strStatus.toLowerCase())) {status=Status.Healthy;}
		else if("tobecut".equals(strStatus.toLowerCase())) {status=Status.Healthy;}
		else {
			throw new InvalidInputException("Please enter a valid tree status");
		}
		
		for(Tree tree: treeList) {
			if(tree==null) {
				throw new InvalidInputException("Cannot have null entry for tree in list");
			}
			if(status!=Status.Cut) {
				if(status==Status.Diseased) {
					double index=tree.getSpecies().getCarbonConsumption();
					predicted+=index/2;
				}
				else {
					double index=tree.getSpecies().getCarbonConsumption();
					predicted+=index;
				}
			}	
		}
		current=calcCarbonConsump(treeList);
		System.out.println("current is  "+current);
		System.out.println("predicted is  "+predicted);
		forecast=((predicted-current)/current)*100;
		
		
		return (int)forecast;
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
		double forecast=0;
		double predicted=0;
		double current=0;
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
					double index=tree.getSpecies().getOxygenProduction();
					predicted+=index/2;
				}
				else {
					double index=tree.getSpecies().getOxygenProduction();
					predicted+=index;
				}
			}	
		}
		current=calcOxygenProd(treeList);
		forecast=((predicted-current)/current)*100;
		
		
		return (int)forecast;
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
	
	public int bioForecast(List<Tree> treeList, String status) throws InvalidInputException{
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
		if(status.equals("Cut")) {
			forecast=0-bioIndexCalculator(treeList);
		}
		else {
			for(Tree tree: treeList) {
				if(tree == null) {
					throw new InvalidInputException("The list contains a null entry");
				}
				if(tree.getTreeStatus().getStatus()== Status.Cut) {
					Species newSpecies= tree.getSpecies();
					Boolean marker=true;
					for(int i=0; treeList.get(i)!=tree;i++) {
						if(treeList.get(i).getSpecies()==newSpecies) {marker=false;}
					}
					if(marker) {forecast++;}
					
				}
			}
		}
		
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
	
	public User login(String username, String password) throws InvalidInputException{
		if( ("".equals(username.trim())) || (password.trim()=="") ) {
			throw new InvalidInputException("Please enter a username and password");
		}
		List<User> users= tp.getUsers();
		for(User user: users) {
			if(username.equals(user.getUsername())) {
				if(password.equals(user.getPassword())){
					return user;
				}
				else {
					throw new InvalidInputException("Please re-enter your password");
				}
			}
		}
		throw new InvalidInputException("username not found");
	}
	
	public User register(String username, String password, Boolean isScientist) throws InvalidInputException{
		if( (username.trim()=="") || (password.trim()=="") ) {
			throw new InvalidInputException("Please enter a username and password");
		}
		List<User> users= tp.getUsers();
		for(User user: users) {
			if(username.equals(user.getUsername())) {
				throw new InvalidInputException("username already exists, please try another one");
			}
		}
		User newUser=tp.addUser(username, password);
		if(isScientist) {
			newUser.setUserType(UserType.Scientist);
		}
		PersistenceXStream.saveToXMLwithXStream(tp);
		return newUser;
	}
	
	public User getUserByName(String name) {
		List<User> users = tp.getUsers();
		for (User user: users) {
			if (user.getUsername().equals(name)) return user;
		}
		return null;
	}
	public void loadFile(File input) throws InvalidInputException{
		// TODO Auto-generated method stub
		
	}

	public List<Tree> updateTrees(List<Integer> treeIDs, Status status) throws InvalidInputException{
		List<Tree> trees=tp.getTrees();
		for(int id: treeIDs) {
			for(Tree tree: trees) {
				if(id==tree.getId()) {
					TreeStatus aTreeStatus= tree.getTreeStatus();
					aTreeStatus.setStatus(status);
				}
			}
		}
		return trees;
	}


}
