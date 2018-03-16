package ca.mcgill.ecse321.TMS.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.TMS.model.LocationType;
import ca.mcgill.ecse321.TMS.model.Municipality;
import ca.mcgill.ecse321.TMS.model.Park;
import ca.mcgill.ecse321.TMS.model.Species;
import ca.mcgill.ecse321.TMS.model.Street;
import ca.mcgill.ecse321.TMS.model.Tree;
import ca.mcgill.ecse321.TMS.model.TreePLE;
import ca.mcgill.ecse321.TMS.model.TreeStatus;
import ca.mcgill.ecse321.TMS.model.User;
import ca.mcgill.ecse321.TMS.persistence.PersistenceXStream;

public class TestService {

	private TreePLE ple;
	private TreePLE ple2;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeModelManager("output"+File.separator+"data.xml");
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ple = new TreePLE();
		ple2 = new TreePLE();
		//create tree classes
		
		
	}

	@After
	public void tearDown() throws Exception {
		ple.delete();
	}
	
	@Test
	public void testCreatetree() {
		TMSService erc = new TMSService(ple);
		
		assertEquals(0, ple.getTrees().size());	 
		//create tree properties
		int height=5;
		int diameter=10;
		User user=new User("aehwany", ple);
		Species species= new Species("dandelion", 5, 4, ple);
		Municipality municipality=new Municipality(10, "rosemont", ple);
		Date datePlanted = Date.valueOf("2014-09-09");
		TreeStatus status=new TreeStatus(ple);
		LocationType location=new LocationType();
		  try {
			  erc.createTree(height, diameter, datePlanted, status, species, user, municipality, 1, 1, "right", location);
			
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			fail();
		}
		  TreePLE ple1 = ple;
		  checkResultTree(height, diameter,species, user, ple1);

		 TreePLE ple2 = (TreePLE) PersistenceXStream.loadFromXMLwithXStream();

		  // check file contents
		  //checkResultTree(height, diameter,species, user, ple2);
		  ple2.delete();
	}
	@Test
	public void testRemovetree() {
		
		//create tree properties
		int height=5;
		int diameter=10;
		User user=new User("aehwany", ple);
		Species species= new Species("dandelion", 5, 4, ple);
		Municipality municipality=new Municipality(10, "rosemont", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted=new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded=new Date(c2.getTimeInMillis());
		TreeStatus status=new TreeStatus(ple);
		Tree tree = new Tree(height, diameter, datePlanted,dateAdded, status,species, user, municipality, ple);
		
		 assertEquals(1, ple.getTrees().size()); 
 
		TMSService erc = new TMSService(ple);
		erc.removeTree(tree);

		 TreePLE ple1 = ple;
		 assertEquals(0, ple.getTrees().size());
		 TreePLE ple2 = (TreePLE) PersistenceXStream.loadFromXMLwithXStream();
		 assertEquals(0, ple.getTrees().size());
		 
		 ple1.delete();
		 ple2.delete();
		 
		
	}
	
	@Test
	public void testFindalltrees() {
		//create tree properties
		int height=5;
		int diameter=10;
		User user=new User("aehwany", ple);
		Species species= new Species("dandelion", 5, 4, ple);
		Municipality municipality=new Municipality(10, "rosemont", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted=new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded=new Date(c2.getTimeInMillis());
		TreeStatus status=new TreeStatus(ple);
		 //Create trees
		 Tree tree1 = new Tree(height, diameter, datePlanted,dateAdded, status,species, user, municipality, ple);
		 Tree tree2 = new Tree(height, diameter, datePlanted,dateAdded, status,species, user, municipality, ple);	
		 Tree tree3 = new Tree(height, diameter, datePlanted,dateAdded, status,species, user, municipality, ple);
			
		 
		 
		 List<Tree> treeList;
				TMSService erc = new TMSService(ple);
				treeList=erc.findAllTrees();
		 assertEquals(3,treeList.size());
		 assertEquals(treeList.get(0),tree1);
		 assertEquals(treeList.get(1),tree2);
		 assertEquals(treeList.get(2),tree3);
		
	}
	
	
	private void checkResultTree(int height, int diameter,Species species, User user, TreePLE ple) {
		  assertEquals(1, ple.getTrees().size());
		  assertEquals(user.getUsername(), ple.getTree(0).getLocal().getUsername());
		  assertEquals(species.getName(), ple.getTree(0).getSpecies().getName());
		  assertEquals(height, ple.getTree(0).getHeight());
		  assertEquals(diameter, ple.getTree(0).getDiameter());
	  
	}
	
	@Test
	public void TestNullTreeInputException() {
		TMSService ts = new TMSService(ple);
		
		
		Date datePlanted = Date.valueOf("2020-09-09");
		Street street=null;
		LocationType locationType1=street;
		
		
		//check1
		String error = null;
		try {
			ts.createTree(-5, -10, datePlanted, null, null, null, null, -2, -5, "left", locationType1);
		} catch (InvalidInputException e) {
		      error = e.getMessage();
		  }
		assertEquals(
				"Cannot pass negative integer! Cannot plant tree in the future! Status needs to be selected for registration! Species needs to be selected for registration! User needs to be logged in for registration! Municipality needs to be selected for registration!",
		          error);
	}
	
	@Test
	public void TestUnavailableTreeInputException() {
		TMSService ts = new TMSService(ple);
		TMSService ts2 = new TMSService(ple2);
		
		Date datePlanted = Date.valueOf("2002-02-02");
		TreeStatus status = new TreeStatus(ple2);
		Species species = new Species("daisy", 1, 1, ple2);
		User user = new User("idodin", ple2);
		Municipality municipality = new Municipality(1, "McGill", ple2);
		LocationType locationType = new Park(3, "Mt. Royal Park", ple2);
		
		String error = null;
		try {
			ts.createTree(1, 1, datePlanted, status, species, user, municipality, 1, 1, "", locationType);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Status must exist! Species must exist! User must be registered! Municipality must exist! Park must exist!", error);
		
	}
	
	@Test
	public void TestLoadFileInputSuccess() {
		// input.csv must be a correctly formatted csv file  containing a single tree with
		// the tree data that is checked afterwards.
		File input = new File("input.csv");
		
		TMSService ts = new TMSService(ple);
		ts.loadFile(input);
		
		Tree foundTree;
		//Check that Tree is in system
		for(Tree tree : ple.getTrees()) {
			if (tree.getLocal().getUsername().equals("idodin"))
				foundTree = tree;
		}
		if(foundTree == null) {
			fail("Input tree was not found within system");
		}
		
		assertEquals("daisy", foundTree.getSpecies().getName());
		assertEquals("McGill", foundTree.getMunicipality().getName());
		assertEquals(1, foundTree.getMunicipality().getIdNumber());
		assertEquals(10, foundTree.getTreeLocation().getX());
		assertEquals(11, foundTree.getTreeLocation().getY());
		assertEquals(TreeStatus.Status.Healthy, foundTree.getTreeStatus().getStatus());
		assertEquals("South", foundTree.getTreeLocation().getDescription());
		assertEquals(LocationType.LandUseType.Institutional, foundTree.getTreeLocation().getLocationType().getLandUseType());
		
		Park park;
		if(foundTree.getTreeLocation().getLocationType() instanceof Park) {
			park = (Park)foundTree.getTreeLocation().getLocationType();
		}
		else {
			fail("Location type was not a park");
		}
		
		assertEquals(9, park.getParkCode());
		assertEquals("Mt. Royal", park.getParkName());
		
	}
	
	@Test
	public void TestEmptyFileInput() {
		File input = new File("empty.csv");
		
		TMSService ts = new TMSService(ple);
		ts.loadFile(input);
		
		assertEquals(0, ple.getTrees().size());
	}
	
	@Test
	public void TestNullFileInput() {
		File input = null;
		String errorMessage = "";
		
		TMSService ts = new TMSService(ple);
		try{
			ts.loadFile(input);
		} catch(InvalidInputException e) {
			errorMessage = e.getMessage();
		}
		
		assertEquals("Must select a file to input!", errorMessage);
		
	}
	
	@Test
	public void TestBadContentFileInput() {
		File input = new File("badcontent.csv");
		String errorMessage = "";
		
		TMSService ts = new TMSService(ple);
		try {
			ts.loadFile(input);
		} catch(InvalidInputException e) {
			errorMessage = e.getMessage();
		}
		
		assertEquals("Please check contents of file!", errorMessage);
	}
	
	@Test
	public void TestBadFileFormatInput() {
		File input = new File("hello.jpeg");
		String errorMessage = "";
		
		TMSService ts = new TMSService(ple);
		try {
			ts.loadFile(input);
		} catch(InvalidInputException e) {
			errorMessage = e.getMessage();
		}
		
		assertEquals("Please ensure file is of type .csv", errorMessage);
	}
	
	@Test
	public void TestUpdateTreeSuccess() {
		TMSService ts = new TMSService(ple);
		
		Date datePlanted = Date.valueOf("2002-02-02");
		TreeStatus status = new TreeStatus(ple);
		status.setStatus(TreeStatus.Status.Healthy);
		Species species = new Species("daisy", 1, 1, ple);
		User user = new User("idodin", ple);
		Municipality municipality = new Municipality(1, "McGill", ple);
		LocationType locationType = new Park(3, "Mt. Royal Park", ple);
		
		Tree tree;
		try {
			tree = ts.createTree(10, 10, datePlanted, status, species, user, municipality, 0, 0, "north", locationType);
		} catch(InvalidInputException e) {
			fail("Error in creating tree. See testlog output.");
		}
		
		Date newDatePlanted = Date.valueOf("2010-02-02");
		Species newSpecies = new Species("clover", 2, 2, ple);
		TreeStatus newStatus = new TreeStatus(ple);
		newStatus.setStatus(TreeStatus.Status.Diseased);
		User newUser = new User("aelehwany", ple);
		Municipality newMunicipality = new Municipality(2, "Concordia", ple);
		LocationType newLocationType = new Street("Sherbrooke", ple);
		
		ts.updateTree(tree, 11, 12, newDatePlanted, newStatus, newSpecies, newUser, newMunicipality, 1, 2, "south", newLocationType);
		
		assertEquals(11, tree.getHeight());
		assertEquals(12, tree.getDiameter());
		assertEquals("2010-02-02", tree.getDatePlanted().toString());
		assertEquals("clover", tree.getSpecies().getName());
		assertEquals("aelehwany", tree.getLocal().getUsername());
		assertEquals("Concordia", tree.getMunicipality().getName());
		assertEquals(1, tree.getTreeLocation().getX());
		assertEquals(2, tree.getTreeLocation().getY());
		assertEquals("south", tree.getTreeLocation().getDescription());
		
		Street street;
		if(tree.getTreeLocation().getLocationType() instanceof Street) {
			street = (Street)tree.getTreeLocation().getLocationType();
		}
		else {
			fail("Location type was not a Street");
		}
		
		assertEquals("Sherbrooke", street.getStreetName());
	}
	
	@Test
	public void TestUnavailableUpdateTree() {
		TMSService ts = new TMSService(ple);
		String errorMessage = "";
		
		Date datePlanted = Date.valueOf("2002-02-02");
		TreeStatus status = new TreeStatus(ple);
		status.setStatus(TreeStatus.Status.Healthy);
		Species species = new Species("daisy", 1, 1, ple);
		User user = new User("idodin", ple);
		Municipality municipality = new Municipality(1, "McGill", ple);
		LocationType locationType = new Park(3, "Mt. Royal Park", ple);
		
		Tree tree;
		try {
			tree = ts.createTree(10, 10, datePlanted, status, species, user, municipality, 0, 0, "north", locationType);
		} catch(InvalidInputException e) {
			fail("Error in creating tree. See testlog output.");
		}
		
		Date newDatePlanted = Date.valueOf("2010-02-02");
		Species newSpecies = new Species("clover", 2, 2, ple2);
		TreeStatus newStatus = new TreeStatus(ple2);
		newStatus.setStatus(TreeStatus.Status.Diseased);
		User newUser = new User("aelehwany", ple2);
		Municipality newMunicipality = new Municipality(2, "Concordia", ple2);
		LocationType newLocationType = new Street("Sherbrooke", ple2);
		
		try {
			ts.updateTree(tree, 11, 12, newDatePlanted, newStatus, newSpecies, newUser, newMunicipality, 1, 2, "south", newLocationType);
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		}
		
		assertEquals("Status must exist! Species must exist! User must be registered! Municipality must exist! Street must exist!", error);
	}
	
	@Test
	public void TestNullInputUpdateTree() {
		TMSService ts = new TMSService(ple);
		String errorMessage = "";
		Date datePlanted = Date.valueOf("2100-02-02");
		
		try {
			ts.updateTree(null,-5, -10, datePlanted, null, null, null, null, null, null, null, null, null, null);
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		}
		
		assertEquals("Tree needs to be selected to be updated! Cannot pass negative integer! Cannot plant tree in the future! Status needs to be selected for registration! Species needs to be selected for registration! User needs to be logged in for registration! Municipality needs to be selected for registration!", errorMessage);
	}
	
}