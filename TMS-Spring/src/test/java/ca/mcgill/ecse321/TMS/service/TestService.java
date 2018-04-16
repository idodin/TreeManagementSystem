package ca.mcgill.ecse321.TMS.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.assertj.core.util.Lists;
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
import ca.mcgill.ecse321.TMS.model.TreeStatus.Status;
import ca.mcgill.ecse321.TMS.model.User;
import ca.mcgill.ecse321.TMS.persistence.PersistenceXStream;

public class TestService {

	private TreePLE ple;
	private TreePLE ple2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		PersistenceXStream.initializeModelManager("output" + File.separator + "data.xml");

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ple = new TreePLE();
		ple2 = new TreePLE();

		// create tree classes

	}

	@After
	public void tearDown() throws Exception {
		ple.delete();
		ple2.delete();
	}
	
	
	//create a valid tree
	@Test
	public void testCreatetree() {
		TMSService erc = new TMSService(ple);

		assertEquals(0, ple.getTrees().size());
		// create tree properties
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", "ecse321", ple);
		Species species = new Species("dandelion", 5, 4, ple);
		Municipality municipality = new Municipality(10, "rosemont", ple);
		Date datePlanted = Date.valueOf("2014-09-09");
		TreeStatus status = new TreeStatus(ple);
		LocationType location = new LocationType();

		try {
			erc.createTree(height, diameter, datePlanted, status, species, user, municipality, -74.0, 45.0, "right", location);

		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			fail();
		}
		TreePLE ple1 = ple;

		checkResultTree(height, diameter, species, user, ple1);

		TreePLE ple2 = (TreePLE) PersistenceXStream.loadFromXMLwithXStream();

		// check file contents

		// checkResultTree(height, diameter,species, user, ple2);

		ple2.delete();
	}

	@Test
	public void testRemovetree() {

		// create tree properties
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", "ecse321", ple);
		Species species = new Species("dandelion", 5, 4, ple);
		Municipality municipality = new Municipality(10, "rosemont", ple);

		Calendar c1 = Calendar.getInstance();
		Date datePlanted = new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();

		Date dateAdded = new Date(c2.getTimeInMillis());
		TreeStatus status = new TreeStatus(ple);
		Tree tree = new Tree(height, diameter, datePlanted, dateAdded, status, species, user, municipality, ple);

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
	//returns all trees from the persistence
	@Test
	public void testFindalltrees() {
		// create tree properties
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", "ecse321", ple);
		Species species = new Species("dandelion", 5, 4, ple);
		Municipality municipality = new Municipality(10, "rosemont", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted = new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();

		Date dateAdded = new Date(c2.getTimeInMillis());
		TreeStatus status = new TreeStatus(ple);
		// Create trees
		Tree tree1 = new Tree(height, diameter, datePlanted, dateAdded, status, species, user, municipality, ple);
		Tree tree2 = new Tree(height, diameter, datePlanted, dateAdded, status, species, user, municipality, ple);
		Tree tree3 = new Tree(height, diameter, datePlanted, dateAdded, status, species, user, municipality, ple);

		List<Tree> treeList;
		TMSService erc = new TMSService(ple);
		treeList = erc.findAllTrees();
		assertEquals(3, treeList.size());
		assertEquals(treeList.get(0), tree1);
		assertEquals(treeList.get(1), tree2);
		assertEquals(treeList.get(2), tree3);
	}
	
	/////////////////////	CREATE TREE  /////////////////////
	//helper method for checks on creating a tree
	private void checkResultTree(int height, int diameter, Species species, User user, TreePLE ple) {

		assertEquals(1, ple.getTrees().size());
		assertEquals(user.getUsername(), ple.getTree(0).getLocal().getUsername());
		assertEquals(species.getName(), ple.getTree(0).getSpecies().getName());
		assertEquals(height, ple.getTree(0).getHeight(), 0.001);
		assertEquals(diameter, ple.getTree(0).getDiameter(), 0.001);

	}


	@Test
	public void TestNullTreeInputException() {
		TMSService ts = new TMSService(ple);

		Date datePlanted = Date.valueOf("2020-09-09");
		Street street = null;
		LocationType locationType1 = street;

		// check1
		String error = null;
		try {
			ts.createTree(-5, -10, datePlanted, null, null, null, null, -74.0, 45.0, "left", locationType1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals(
				"Height and diameter cannot be negative! Cannot plant tree in the future! Status needs to be selected for registration! Species needs to be selected for registration! User needs to be logged in for registration! Municipality needs to be selected for registration!",
				error);
	}

	@Test
	public void TestUnavailableTreeInputException() {
		TMSService ts = new TMSService(ple);
		TMSService ts2 = new TMSService(ple2);

		Date datePlanted = Date.valueOf("2002-02-02");
		TreeStatus status = new TreeStatus(ple2);
		Species species = new Species("daisy", 1, 1, ple2);
		User user = new User("idodin", "ecse321", ple2);
		Municipality municipality = new Municipality(1, "McGill", ple2);
		LocationType locationType = new Park(3, "Mt. Royal Park", ple2);

		String error = null;
		try {
			ts.createTree(1, 1, datePlanted, status, species, user, municipality, -74.0, 45.0, "", locationType);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals(
				"Status must exist! Species must exist! User must be registered! Municipality must exist! Park must exist!",
				error);

	}

	/////////////////////	UPDATE TREE  /////////////////////
	@Test
	public void TestUpdateTreeSuccess() {
		TMSService ts = new TMSService(ple);

		Date datePlanted = Date.valueOf("2002-02-02");
		Date dateAdded = new Date(Calendar.getInstance().getTime().getTime());
		TreeStatus status = new TreeStatus(ple);
		status.setStatus(TreeStatus.Status.Healthy);
		Species species = new Species("daisy", 1, 1, ple);
		User user = new User("idodin", "ecse321", ple);
		Municipality municipality = new Municipality(1, "McGill", ple);
		LocationType locationType = new Park(3, "Mt. Royal Park", ple);

		Tree tree = new Tree(10, 11, datePlanted, dateAdded, status, species, user, municipality, ple);

		Date newDatePlanted = Date.valueOf("2010-02-02");
		Species newSpecies = new Species("clover", 2, 2, ple);
		TreeStatus newStatus = new TreeStatus(ple);
		newStatus.setStatus(TreeStatus.Status.Diseased);
		User newUser = new User("aelehwany", "ecse321", ple);
		Municipality newMunicipality = new Municipality(2, "Concordia", ple);
		LocationType newLocationType = new Street("Sherbrooke", ple);
		try {
			ts.updateTree(tree, 11, 12, newDatePlanted, newStatus, newSpecies, newUser, newMunicipality, 1, 2, "south",
					newLocationType);
		} catch (InvalidInputException e) {
			fail("Error in updating tree!");
		}
		double t = 11.0;
		assertEquals(t, tree.getHeight(), 0.001);
		assertEquals(12.0, tree.getDiameter(), 0.001);
		assertEquals("2010-02-02", tree.getDatePlanted().toString());
		assertEquals("clover", tree.getSpecies().getName());
		assertEquals("aelehwany", tree.getLocal().getUsername());
		assertEquals("Concordia", tree.getMunicipality().getName());
		assertEquals(1.0, tree.getTreeLocation().getX(), 0.001);
		assertEquals(2.0, tree.getTreeLocation().getY(), 0.001);
		assertEquals("south", tree.getTreeLocation().getDescription());

		Street street = null;
		if (tree.getTreeLocation().getLocationType() instanceof Street) {
			street = (Street) tree.getTreeLocation().getLocationType();
		} else {
			fail("Location type was not a Street");
		}

		assertEquals("Sherbrooke", street.getStreetName());
	}

	@Test
	public void TestUnavailableUpdateTree() {
		TMSService ts = new TMSService(ple);
		String errorMessage = "";

		Date datePlanted = Date.valueOf("2002-02-02");
		Date dateAdded = new Date(Calendar.getInstance().getTime().getTime());
		TreeStatus status = new TreeStatus(ple);
		status.setStatus(TreeStatus.Status.Healthy);
		Species species = new Species("newdaisy", 1, 1, ple);
		User user = new User("idodin", "ecse321", ple);
		Municipality municipality = new Municipality(1, "McGill", ple);
		LocationType locationType = new Park(3, "Mt. Royal Park", ple);

		Tree tree = new Tree(10, 11, datePlanted, dateAdded, status, species, user, municipality, ple);

		Date newDatePlanted = Date.valueOf("2010-02-02");
		Species newSpecies = new Species("clover", 2, 2, ple2);
		TreeStatus newStatus = new TreeStatus(ple2);
		newStatus.setStatus(TreeStatus.Status.Diseased);
		User newUser = new User("aelehwany", "ecse321", ple2);
		Municipality newMunicipality = new Municipality(2, "Concordia", ple2);
		LocationType newLocationType = new Street("Sherbrooke", ple2);

		try {
			ts.updateTree(tree, 11, 12, newDatePlanted, newStatus, newSpecies, newUser, newMunicipality, 1, 2, "south",
					newLocationType);
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		}

		assertEquals(
				"Status must exist! Species must exist! User must be registered! Municipality must exist! Street must exist!",
				errorMessage);
	}

	@Test
	public void TestNullInputUpdateTree() {
		TMSService ts = new TMSService(ple);
		String errorMessage = "";
		Date datePlanted = Date.valueOf("2100-02-02");

		try {
			ts.updateTree(null, -4, -4, null, null, null, null, null, -3, -3, null, null);
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		}


		assertEquals(
				"Tree needs to be selected to be updated! Cannot pass negative integer! Cannot plant tree in the future! Status needs to be selected for registration! Species needs to be selected for registration! User needs to be logged in for registration! Municipality needs to be selected for registration!",
				errorMessage);

	}

/////////////////////	SUSTAINABILITY ATTRIBUTES  /////////////////////
	// Calculate current oxygen production of a list of trees.
	@Test
	public void TestCalculateOxygenProduction() {
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", "ecse321", ple);
		Species species1 = new Species("dandelion", 18, 10, ple);
		Species species2 = new Species("Coconut", 8, 2, ple);
		Species species3 = new Species("Pine", 10, 6, ple);
		Municipality municipality = new Municipality(10, "rosemont", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted = new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded = new Date(c2.getTimeInMillis());
		TreeStatus status1 = new TreeStatus(ple);
		TreeStatus status2 = new TreeStatus(ple);
		TreeStatus status3 = new TreeStatus(ple);
		status1.setStatus(Status.Healthy);
		status2.setStatus(Status.Cut);
		status3.setStatus(Status.Diseased);
		// Create trees
		Tree tree1 = new Tree(height, diameter, datePlanted, dateAdded, status1, species1, user, municipality, ple);
		Tree tree2 = new Tree(height, diameter, datePlanted, dateAdded, status2, species2, user, municipality, ple);
		Tree tree3 = new Tree(height, diameter, datePlanted, dateAdded, status3, species3, user, municipality, ple);

		List<Tree> treeList = new java.util.ArrayList<>();
		treeList.add(tree1);
		treeList.add(tree2);
		treeList.add(tree3);
		int production = 0;
		TMSService erc = new TMSService(ple);
		try {
			production = erc.oxygenForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(38, production);

	}

	// testing if calculateOxygenProduction is called with empty list of trees.
	@Test
	public void TestEmptyListOxygenProduction() {
		String error = "";
		List<Tree> treeList = new java.util.ArrayList<>();
		TMSService erc = new TMSService(ple);
		try {
			int production = erc.oxygenForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("Please enter a list of trees", error);
		}

	}

	// Calculate current carbon consumption of a list of trees.
	@Test
	public void TestCalculateCarbonConsumption() {
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", "ecse321", ple);
		Species species1 = new Species("dandelion", 18, 10, ple);
		Species species2 = new Species("Coconut", 8, 2, ple);
		Species species3 = new Species("Pine", 10, 6, ple);
		Municipality municipality = new Municipality(10, "rosemont", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted = new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded = new Date(c2.getTimeInMillis());
		TreeStatus status1 = new TreeStatus(ple);
		TreeStatus status2 = new TreeStatus(ple);
		TreeStatus status3 = new TreeStatus(ple);
		status1.setStatus(Status.Healthy);
		status2.setStatus(Status.Cut);
		status3.setStatus(Status.Diseased);
		// Create trees
		Tree tree1 = new Tree(height, diameter, datePlanted, dateAdded, status1, species1, user, municipality, ple);
		Tree tree2 = new Tree(height, diameter, datePlanted, dateAdded, status2, species2, user, municipality, ple);
		Tree tree3 = new Tree(height, diameter, datePlanted, dateAdded, status3, species3, user, municipality, ple);

		List<Tree> treeList = new java.util.ArrayList<>();
		treeList.add(tree1);
		treeList.add(tree2);
		treeList.add(tree3);
		int consumption = 0;
		TMSService erc = new TMSService(ple);
		try {
			consumption = erc.calcCarbonConsump(treeList);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(23, consumption);

	}

	// testing if calculateCarbonConsumption is called with empty list of trees.
	@Test
	public void TestEmptyListCarbonConsumption() {
		String error = "";
		List<Tree> treeList = new java.util.ArrayList<>();
		TMSService erc = new TMSService(ple);
		try {
			int consumption = erc.calcCarbonConsump(treeList);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("Please enter a list of trees", error);
		}

	}

	// test null input for bioForecast
	@Test
	public void testNullListBioForecast() {
		String error = "";
		List<Tree> treeList = null;
		TMSService erc = new TMSService(ple);
		try {
			int bioForecast = erc.bioForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be null", error);
		}
	}
/////////////////////	MARK TREES  /////////////////////
	@Test
	public void testMarkDiseasedSuccess() {
		TMSService ts = new TMSService(ple);

		Date datePlanted = Date.valueOf("2002-02-02");
		Date dateAdded = new Date(Calendar.getInstance().getTime().getTime());
		TreeStatus status = new TreeStatus(ple);
		status.setStatus(TreeStatus.Status.Healthy);
		Species species = new Species("daisy", 1, 1, ple);
		User user = new User("idodin", "ecse321", ple);
		Municipality municipality = new Municipality(1, "McGill", ple);

		Tree tree = new Tree(1, 1, datePlanted, dateAdded, status, species, user, municipality, ple);

		try {
			ts.markDiseased(tree);
		} catch (InvalidInputException e) {
			fail("Tree couldn't be marked as diseased due to invalid input");
		}

		assertEquals(TreeStatus.Status.Diseased, tree.getTreeStatus().getStatus());
	}

	@Test
	public void testMarkDiseasedNull() {
		TMSService ts = new TMSService(ple);
		String error = "";

		Tree tree = null;

		try {
			ts.markDiseased(tree);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals("Tree needs to be selected to be marked as diseased.", error);
	}

	@Test
	public void testMarkDiseasedAlready() {
		TMSService ts = new TMSService(ple);
		String error = "";

		Date datePlanted = Date.valueOf("2002-02-02");
		Date dateAdded = new Date(Calendar.getInstance().getTime().getTime());
		TreeStatus status = new TreeStatus(ple);
		status.setStatus(TreeStatus.Status.Diseased);
		Species species = new Species("daisy", 1, 1, ple);
		User user = new User("idodin", "ecse321", ple);
		Municipality municipality = new Municipality(1, "McGill", ple);

		Tree tree = new Tree(1, 1, datePlanted, dateAdded, status, species, user, municipality, ple);

		try {
			ts.markDiseased(tree);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals("Tree was already diseased!", error);
	}

	@Test
	public void testMarkToBeCutSuccess() {
		TMSService ts = new TMSService(ple);
		String error = "";

		Date datePlanted = Date.valueOf("2002-02-02");
		Date dateAdded = new Date(Calendar.getInstance().getTime().getTime());
		TreeStatus status = new TreeStatus(ple);
		status.setStatus(TreeStatus.Status.Diseased);
		Species species = new Species("daisy", 1, 1, ple);
		User user = new User("idodin", "ecse321", ple);
		Municipality municipality = new Municipality(1, "McGill", ple);

		Tree tree = new Tree(1, 1, datePlanted, dateAdded, status, species, user, municipality, ple);

		try {
			ts.markToBeCut(tree);
		} catch (InvalidInputException e) {
			fail("Tree couldn't be marked as to be cut due to Invalid Input.");
		}

		assertEquals(true, tree.getTreeStatus().setStatus(Status.ToBeCut));
	}

	@Test
	public void testMarkToBeCutNull() {
		TMSService ts = new TMSService(ple);
		String error = "";

		Tree tree = null;

		try {
			ts.markToBeCut(tree);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals("Tree needs to be selected to be mark as to be cut.", error);
	}

	@Test
	public void testMarkToBeCutAlready() {
		TMSService ts = new TMSService(ple);
		String error = "";

		Date datePlanted = Date.valueOf("2002-02-02");
		Date dateAdded = new Date(Calendar.getInstance().getTime().getTime());
		TreeStatus status = new TreeStatus(ple);
		status.setStatus(TreeStatus.Status.Cut);
		Species species = new Species("daisy", 1, 1, ple);
		User user = new User("idodin", "ecse321", ple);
		Municipality municipality = new Municipality(1, "McGill", ple);

		Tree tree = new Tree(1, 1, datePlanted, dateAdded, status, species, user, municipality, ple);

		try {
			ts.markToBeCut(tree);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals("Tree was already cut down!", error);
	}
	
	/////////////////////	BIOFORECAST  /////////////////////
	// test null list entry between valid list entries
	public void testNullEntryBioForecast() {
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", "ecse321", ple);
		Species species1 = new Species("dandelion", 18, 10, ple);
		Species species2 = new Species("Coconut", 8, 2, ple);
		Species species3 = new Species("Pine", 10, 6, ple);
		Municipality municipality = new Municipality(10, "rosemont", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted = new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded = new Date(c2.getTimeInMillis());
		TreeStatus status1 = new TreeStatus(ple);
		TreeStatus status2 = new TreeStatus(ple);
		TreeStatus status3 = new TreeStatus(ple);
		status1.setStatus(Status.Healthy);
		status2.setStatus(Status.Cut);
		status3.setStatus(Status.Diseased);
		// Create trees
		Tree tree1 = new Tree(height, diameter, datePlanted, dateAdded, status1, species1, user, municipality, ple);
		Tree tree2 = new Tree(height, diameter, datePlanted, dateAdded, status2, species2, user, municipality, ple);
		Tree tree3 = new Tree(height, diameter, datePlanted, dateAdded, status3, species3, user, municipality, ple);

		List<Tree> treeList = new java.util.ArrayList<>();
		treeList.add(tree1);
		treeList.add(tree2);
		treeList.add(tree3);
		treeList.add(null);
		TMSService erc = new TMSService(ple);
		String error = "";
		try {
			int bioForecast = erc.bioForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("The list contains a null entry", error);
		}
	}

	// test empty list input for bioForecast
	@Test
	public void testEmptyListBioForecast() {
		String error = "";
		List<Tree> treeList = new java.util.ArrayList<>();
		TMSService erc = new TMSService(ple);
		try {
			int bioForecast = erc.bioForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be empty", error);
		}
	}

	// test valid input for BioForecast
	@Test
	public void testValidInputBioForecast() {
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", "ecse321", ple);
		Species species1 = new Species("dandelion", 18, 10, ple);
		Species species2 = new Species("Coconut", 8, 2, ple);
		Species species3 = new Species("Pine", 10, 6, ple);
		Municipality municipality = new Municipality(10, "rosemont", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted = new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded = new Date(c2.getTimeInMillis());
		TreeStatus status1 = new TreeStatus(ple);
		TreeStatus status2 = new TreeStatus(ple);
		TreeStatus status3 = new TreeStatus(ple);
		status1.setStatus(Status.Healthy);
		status2.setStatus(Status.Cut);
		status3.setStatus(Status.Diseased);
		// Create trees
		Tree tree1 = new Tree(height, diameter, datePlanted, dateAdded, status1, species1, user, municipality, ple);
		Tree tree2 = new Tree(height, diameter, datePlanted, dateAdded, status2, species2, user, municipality, ple);
		Tree tree3 = new Tree(height, diameter, datePlanted, dateAdded, status3, species3, user, municipality, ple);

		List<Tree> treeList = new java.util.ArrayList<>();
		treeList.add(tree1);
		treeList.add(tree2);
		treeList.add(tree3);

		int bioForecast = 0;
		TMSService erc = new TMSService(ple);
		try {
			bioForecast = erc.bioForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(1, bioForecast);
	}

	// test null input for bioIndexCalculator
	@Test
	public void testNullListBioIndex() {
		String error = "";
		List<Tree> treeList = null;
		TMSService erc = new TMSService(ple);
		try {
			int bioForecast = erc.bioIndexCalculator(treeList);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be null", error);
		}
	}

	@Test
	// test null list entry between valid list entries
	public void testNullEntryBioIndex() {
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", "ecse321", ple);
		Species species1 = new Species("dandelion", 18, 10, ple);
		Species species2 = new Species("Coconut", 8, 2, ple);
		Species species3 = new Species("Pine", 10, 6, ple);
		Municipality municipality = new Municipality(10, "rosemont", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted = new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded = new Date(c2.getTimeInMillis());
		TreeStatus status1 = new TreeStatus(ple);
		TreeStatus status2 = new TreeStatus(ple);
		TreeStatus status3 = new TreeStatus(ple);
		status1.setStatus(Status.Healthy);
		status2.setStatus(Status.Cut);
		status3.setStatus(Status.Diseased);
		// Create trees
		Tree tree1 = new Tree(height, diameter, datePlanted, dateAdded, status1, species1, user, municipality, ple);
		Tree tree2 = new Tree(height, diameter, datePlanted, dateAdded, status2, species2, user, municipality, ple);
		Tree tree3 = new Tree(height, diameter, datePlanted, dateAdded, status3, species3, user, municipality, ple);

		List<Tree> treeList = new java.util.ArrayList<>();
		treeList.add(tree1);
		treeList.add(tree2);
		treeList.add(tree3);
		treeList.add(null);
		TMSService erc = new TMSService(ple);
		String error = "";
		try {
			int bioForecast = erc.bioIndexCalculator(treeList);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("The list contains a null entry", error);
		}
	}

	// test empty list input for bioIndexCalculator
	@Test
	public void testEmptyListBioIndex() {
		String error = "";
		List<Tree> treeList = new java.util.ArrayList<>();
		TMSService erc = new TMSService(ple);
		try {
			int bioForecast = erc.bioIndexCalculator(treeList);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be empty", error);
		}
	}

	// test valid input for bioIndexCalculator
	@Test
	public void testValidInputBioIndex() {
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", "ecse321", ple);
		Species species1 = new Species("dandelion", 18, 10, ple);
		Species species2 = new Species("Coconut", 8, 2, ple);
		Species species3 = new Species("Pine", 10, 6, ple);
		Municipality municipality = new Municipality(10, "rosemont", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted = new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded = new Date(c2.getTimeInMillis());
		TreeStatus status1 = new TreeStatus(ple);
		TreeStatus status2 = new TreeStatus(ple);
		TreeStatus status3 = new TreeStatus(ple);
		status1.setStatus(Status.Healthy);
		status2.setStatus(Status.Cut);
		status3.setStatus(Status.Diseased);
		// Create trees
		Tree tree1 = new Tree(height, diameter, datePlanted, dateAdded, status1, species1, user, municipality, ple);
		Tree tree2 = new Tree(height, diameter, datePlanted, dateAdded, status2, species2, user, municipality, ple);
		Tree tree3 = new Tree(height, diameter, datePlanted, dateAdded, status3, species3, user, municipality, ple);

		List<Tree> treeList = new java.util.ArrayList<>();
		treeList.add(tree1);
		treeList.add(tree2);
		treeList.add(tree3);

		int bioForecast = 0;
		TMSService erc = new TMSService(ple);
		try {
			bioForecast = erc.bioIndexCalculator(treeList);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(2, bioForecast);
	}

/////////////////////	OXYGEN PRODUCTION /////////////////////
	// Test for valid calculation of a change in oxygen production
	// for list of trees
	@Test
	public void testValidChangeInOxygenProduction() {
		int height = 4; 
		int diameter = 11;
		User user1 = new User("fouad", "ecse321", ple);
		User user2 = new User("bitar", "ecse321", ple);
		Species species1 = new Species("dandelion", 18, 10, ple);
		Species species2 = new Species("Coconut", 8, 2, ple);
		Species species3 = new Species("Pine", 10, 6, ple);
		Municipality municipality1 = new Municipality(223, "countySquare", ple);
		Municipality municipality2 = new Municipality(133, "countyCircle", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted1 = new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded1 = new Date(c2.getTimeInMillis());
		Calendar c3 = Calendar.getInstance();
		Date datePlanted2 = new Date(c3.getTimeInMillis());
		Calendar c4 = Calendar.getInstance();
		Date dateAdded2 = new Date(c4.getTimeInMillis());
		TreeStatus status1 = new TreeStatus(ple);
		TreeStatus status2 = new TreeStatus(ple);
		TreeStatus status3 = new TreeStatus(ple);
		status1.setStatus(Status.Healthy);
		status2.setStatus(Status.Cut);
		status3.setStatus(Status.Diseased);

		Tree tree1 = new Tree(height - 1, diameter, datePlanted1, dateAdded1, status2, species1, user1, municipality1,
				ple);
		Tree tree2 = new Tree(height + 1, diameter + 1, datePlanted2, dateAdded2, status3, species1, user1,
				municipality2, ple);
		Tree tree3 = new Tree(height - 2, diameter + 2, datePlanted1, dateAdded2, status3, species2, user2,
				municipality1, ple);
		Tree tree4 = new Tree(height, diameter + 2, datePlanted1, dateAdded2, status3, species3, user2, municipality1,
				ple);
		Tree tree5 = new Tree(height + 2, diameter + 2, datePlanted1, dateAdded2, status1, species1, user2,
				municipality2, ple);
		Tree tree6 = new Tree(height, diameter + 2, datePlanted1, dateAdded2, status2, species2, user2, municipality1,
				ple);
		Tree tree7 = new Tree(height, diameter + 2, datePlanted1, dateAdded2, status1, species3, user2, municipality2,
				ple);

		List<Tree> treeList = new java.util.ArrayList<>();
		treeList.add(tree1);
		treeList.add(tree2);
		treeList.add(tree3);
		treeList.add(tree4);
		treeList.add(tree5);
		treeList.add(tree6);
		treeList.add(tree7);

		int changeInOxygenProduction = 0;
		TMSService erc = new TMSService(ple);
		try {
			changeInOxygenProduction = erc.oxygenForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(84, changeInOxygenProduction);
		try {
			changeInOxygenProduction = erc.oxygenForecast(treeList, "Cut");
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(-100, changeInOxygenProduction);
		try {
			changeInOxygenProduction = erc.oxygenForecast(treeList, "Diseased");
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(-8, changeInOxygenProduction);
	}

	@Test
	public void testNullInputOxygenProd() {
		String error = "";
		List<Tree> treeList = null;
		TMSService erc = new TMSService(ple);
		try {
			int changeInOxygenProduction = erc.oxygenForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be null", error);
		}
		try {
			int changeInOxygenProduction = erc.oxygenForecast(treeList, "Cut");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be null", error);
		}
		try {
			int changeInOxygenProduction = erc.oxygenForecast(treeList, "Diseased");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be null", error);
		}
	}

	@Test
	public void testStringErrorInput() {
		String error = "";
		int height = 4;
		int diameter = 11;
		User user1 = new User("fouad", "ecse321", ple);
		Species species1 = new Species("dandelion", 18, 10, ple);
		Calendar c1 = Calendar.getInstance();
		Municipality municipality1 = new Municipality(223, "countySquare", ple);
		Date datePlanted1 = new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded1 = new Date(c2.getTimeInMillis());
		TreeStatus status1 = new TreeStatus(ple);
		status1.setStatus(Status.Cut);
		Tree tree1 = new Tree(height - 1, diameter, datePlanted1, dateAdded1, status1, species1, user1, municipality1,
				ple);
		List<Tree> treeList = new java.util.ArrayList<>();
		treeList.add(tree1);
		TMSService erc = new TMSService(ple);
		try {
			int changeInOxygenProduction = erc.oxygenForecast(treeList, null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("String cannot be null", error);
		}
		try {
			int changeInOxygenProduction = erc.oxygenForecast(treeList, "hug");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("Please enter a valid tree status", error);
		}
	}

	@Test
	public void testNullEntryOxygenProd() {
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", "ecse321", ple);
		Species species1 = new Species("dandelion", 18, 10, ple);
		Species species2 = new Species("Coconut", 8, 2, ple);
		Species species3 = new Species("Pine", 10, 6, ple);
		Municipality municipality = new Municipality(10, "rosemont", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted = new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded = new Date(c2.getTimeInMillis());
		TreeStatus status1 = new TreeStatus(ple);
		TreeStatus status2 = new TreeStatus(ple);
		TreeStatus status3 = new TreeStatus(ple);
		status1.setStatus(Status.Healthy);
		status2.setStatus(Status.Cut);
		status3.setStatus(Status.Diseased);
		// Create trees
		Tree tree1 = new Tree(height, diameter, datePlanted, dateAdded, status1, species1, user, municipality, ple);
		Tree tree2 = new Tree(height, diameter, datePlanted, dateAdded, status2, species2, user, municipality, ple);
		Tree tree3 = new Tree(height, diameter, datePlanted, dateAdded, status3, species3, user, municipality, ple);

		List<Tree> treeList = new java.util.ArrayList<>();
		treeList.add(tree1);
		treeList.add(tree2);
		treeList.add(null);
		treeList.add(tree3);
		TMSService erc = new TMSService(ple);
		String error = "";
		try {
			int changeInOxygenProduction = erc.oxygenForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("Cannot have null entry for tree in list", error);
		}
	}

/////////////////////	CARBON CONSUMPTION  /////////////////////
	@Test
	public void testValidChangeInCarbonConsumption() {
		int height = 4;
		int diameter = 11;
		User user1 = new User("fouad", "ecse321", ple);
		User user2 = new User("bitar", "ecse321", ple);
		Species species1 = new Species("dandelion", 18, 10, ple);
		Species species2 = new Species("Coconut", 8, 2, ple);
		Species species3 = new Species("Pine", 10, 6, ple);
		Municipality municipality1 = new Municipality(223, "countySquare", ple);
		Municipality municipality2 = new Municipality(133, "countyCircle", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted1 = new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded1 = new Date(c2.getTimeInMillis());
		Calendar c3 = Calendar.getInstance();
		Date datePlanted2 = new Date(c3.getTimeInMillis());
		Calendar c4 = Calendar.getInstance();
		Date dateAdded2 = new Date(c4.getTimeInMillis());
		TreeStatus status1 = new TreeStatus(ple);
		TreeStatus status2 = new TreeStatus(ple);
		TreeStatus status3 = new TreeStatus(ple);
		status1.setStatus(Status.Healthy);
		status2.setStatus(Status.Cut);
		status3.setStatus(Status.Diseased);

		Tree tree1 = new Tree(height - 1, diameter, datePlanted1, dateAdded1, status2, species1, user1, municipality1,
				ple);
		Tree tree2 = new Tree(height + 1, diameter + 1, datePlanted2, dateAdded2, status3, species1, user1,
				municipality2, ple);
		Tree tree3 = new Tree(height - 2, diameter + 2, datePlanted1, dateAdded2, status3, species2, user2,
				municipality1, ple);
		Tree tree4 = new Tree(height, diameter + 2, datePlanted1, dateAdded2, status3, species3, user2, municipality1,
				ple);
		Tree tree5 = new Tree(height + 2, diameter + 2, datePlanted1, dateAdded2, status1, species1, user2,
				municipality2, ple);
		Tree tree6 = new Tree(height, diameter + 2, datePlanted1, dateAdded2, status2, species2, user2, municipality1,
				ple);
		Tree tree7 = new Tree(height, diameter + 2, datePlanted1, dateAdded2, status1, species3, user2, municipality2,
				ple);

		List<Tree> treeList = new java.util.ArrayList<>();
		treeList.add(tree1);
		treeList.add(tree2);
		treeList.add(tree3);
		treeList.add(tree4);
		treeList.add(tree5);
		treeList.add(tree6);
		treeList.add(tree7);

		int changeInCarbonConsumption = 0;
		TMSService erc = new TMSService(ple);
		try {
			changeInCarbonConsumption = erc.carbonForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(95, changeInCarbonConsumption);
		try {
			changeInCarbonConsumption = erc.carbonForecast(treeList, "Cut");
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(-100, changeInCarbonConsumption);
		try {
			changeInCarbonConsumption = erc.carbonForecast(treeList, "Diseased");
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(-2, changeInCarbonConsumption);
	}

	@Test
	public void testNullInputCarbonConsump() {
		String error = "";
		List<Tree> treeList = null;
		TMSService erc = new TMSService(ple);
		try {
			int changeInCarbonConsumption = erc.carbonForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be null", error);
		}
		try {
			int changeInCarbonConsumption = erc.carbonForecast(treeList, "Cut");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be null", error);
		}
		try {
			int changeInCarbonConsumption = erc.carbonForecast(treeList, "Diseased");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be null", error);
		}
	}

	@Test
	public void testStringErrorInputCarbonConsump() {
		String error = "";
		int height = 4;
		int diameter = 11;
		User user1 = new User("fouad", "ecse321", ple);
		Species species1 = new Species("dandelion", 18, 10, ple);
		Calendar c1 = Calendar.getInstance();
		Municipality municipality1 = new Municipality(223, "countySquare", ple);
		Date datePlanted1 = new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded1 = new Date(c2.getTimeInMillis());
		TreeStatus status1 = new TreeStatus(ple);
		status1.setStatus(Status.Cut);
		Tree tree1 = new Tree(height - 1, diameter, datePlanted1, dateAdded1, status1, species1, user1, municipality1,
				ple);
		List<Tree> treeList = new java.util.ArrayList<>();
		treeList.add(tree1);
		TMSService erc = new TMSService(ple);
		try {
			int changeInCarbonConsumption = erc.carbonForecast(treeList, null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("String cannot be null", error);
		}
		try {
			int changeInCarbonConsumption = erc.carbonForecast(treeList, "hug");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("Please enter a valid tree status", error);
		}
	}

	@Test
	public void testNullEntryCarbonConsump() {
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", "ecse321", ple);
		Species species1 = new Species("dandelion", 18, 10, ple);
		Species species2 = new Species("Coconut", 8, 2, ple);
		Species species3 = new Species("Pine", 10, 6, ple);
		Municipality municipality = new Municipality(10, "rosemont", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted = new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded = new Date(c2.getTimeInMillis());
		TreeStatus status1 = new TreeStatus(ple);
		TreeStatus status2 = new TreeStatus(ple);
		TreeStatus status3 = new TreeStatus(ple);
		status1.setStatus(Status.Healthy);
		status2.setStatus(Status.Cut);
		status3.setStatus(Status.Diseased);
		// Create trees
		Tree tree1 = new Tree(height, diameter, datePlanted, dateAdded, status1, species1, user, municipality, ple);
		Tree tree2 = new Tree(height, diameter, datePlanted, dateAdded, status2, species2, user, municipality, ple);
		Tree tree3 = new Tree(height, diameter, datePlanted, dateAdded, status3, species3, user, municipality, ple);

		List<Tree> treeList = new java.util.ArrayList<>();
		treeList.add(tree1);
		treeList.add(tree2);
		treeList.add(null);
		treeList.add(tree3);
		TMSService erc = new TMSService(ple);
		String error = "";
		try {
			int changeInCarbonConsumption = erc.carbonForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("Cannot have null entry for tree in list", error);
		
		}
		}

/////////////////////	LOGIN AND REGISTER CHECKS  /////////////////////
	@Test
	public void testValidLogin() {
		TMSService erc = new TMSService(ple);
		ple.addUser("Ahmed", "ecse321p/");
		User loggedUser=null;
		try {
			loggedUser=erc.login("Ahmed", "ecse321p/");
		}catch(InvalidInputException e) {
			fail();
		}
		assertEquals("Ahmed", loggedUser.getUsername());
		assertEquals("ecse321p/", loggedUser.getPassword());
	}
	
	@Test
	public void testValidRegister() {
		TMSService erc = new TMSService(ple);
		User newUser=null;
		try {
			newUser=erc.register("Ahmed","ecse321p/", false);
		}catch(InvalidInputException e) {
			fail();
		}
		List<User> users= ple.getUsers();
		User foundUser=null;
		for(User user: users) {
			if("Ahmed".equals(user.getUsername()) && ("ecse321p/".equals(user.getPassword()))) {
				foundUser=user;
			}
		}
		assertEquals("Ahmed",foundUser.getUsername());
		assertEquals("ecse321p/", foundUser.getPassword());
	}
	
	@Test
	public void testNullLoginAndRegister() {
		TMSService erc = new TMSService(ple);
		String error="";
		try {
			erc.login("","");
		}catch(InvalidInputException e) {
			error = e.getMessage();
			assertEquals("Please enter a username and password", error);	
		}
		error="";
		try {
			erc.register("","",false);
		}catch(InvalidInputException e) {
			error = e.getMessage();
			assertEquals("Please enter a username and password", error);	
		}
	}
	
	@Test
	public void testInvalidLogin() {
		TMSService erc = new TMSService(ple);
		String error="";
		ple.addUser("Ahmed", "ecse321p/");
		ple.addUser("Karim", "ecse321p/");
		try {
			erc.login("Bahaa","ecse321p/");
		}catch(InvalidInputException e) {
			error = e.getMessage();
			assertEquals("username not found", error);	
		}
	}
	
	@Test
	public void testLoginInvalidPassword() {
		TMSService erc = new TMSService(ple);
		String error="";
		ple.addUser("Ahmed", "ecse321");
		ple.addUser("Karim", "ecse321p/");
		try {
			erc.login("Ahmed","ecse321p/");
		}catch(InvalidInputException e) {
			error = e.getMessage();
			assertEquals("Please re-enter your password", error);	
		}
	}
	
	@Test
	public void testRegisterExistingUsername() {
		TMSService erc = new TMSService(ple);
		String error="";
		ple.addUser("Ahmed", "ecse321p/");
		ple.addUser("Karim", "ecse321foo");
		try {
			erc.register("Ahmed","ecse321p/", false);
		}catch(InvalidInputException e) {
			error = e.getMessage();
			assertEquals("username already exists, please try another one", error);	
		}
	}
	

}

