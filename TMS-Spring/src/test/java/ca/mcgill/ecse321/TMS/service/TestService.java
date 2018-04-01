package ca.mcgill.ecse321.TMS.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Date;
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

	@Test
	public void testCreatetree() {
		TMSService erc = new TMSService(ple);

		assertEquals(0, ple.getTrees().size());
		// create tree properties
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", ple);
		Species species = new Species("dandelion", 5, 4, ple);
		Municipality municipality = new Municipality(10, "rosemont", ple);
		Date datePlanted = Date.valueOf("2014-09-09");
		TreeStatus status = new TreeStatus(ple);
		LocationType location = new LocationType();

		try {
			erc.createTree(height, diameter, datePlanted, status, species, user, municipality, 1, 1, "right", location);

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
		User user = new User("aehwany", ple);
		Species species = new Species("dandelion", 5, 4, ple);
		Municipality municipality = new Municipality(10, "rosemont", ple);

		Calendar c1 = Calendar.getInstance();
		Date datePlanted = new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();

		Date dateAdded = new Date(c2.getTimeInMillis());
		TreeStatus status = new TreeStatus(ple);
		status.setStatus(Status.Healthy);
		Tree tree = new Tree(height, diameter, datePlanted, dateAdded, status, species, user, municipality, ple);

		assertEquals(Status.Healthy, ple.getTree(0).getTreeStatus().getStatus());

		TMSService erc = new TMSService(ple);
		erc.removeTree(tree);

		TreePLE ple1 = ple;
		assertEquals(Status.Cut, ple1.getTree(0).getTreeStatus().getStatus());
		TreePLE ple2 = (TreePLE) PersistenceXStream.loadFromXMLwithXStream();
		assertEquals(Status.Cut, ple2.getTree(0).getTreeStatus().getStatus());

		ple1.delete();
		ple2.delete();

	}

	@Test
	public void testFindalltrees() {
		// create tree properties
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", ple);
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

	private void checkResultTree(int height, int diameter, Species species, User user, TreePLE ple) {

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
		Street street = null;
		LocationType locationType1 = street;

		// check1
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
		assertEquals(
				"Status must exist! Species must exist! User must be registered! Municipality must exist! Park must exist!",
				error);

	}

//	@Test
//	public void TestLoadFileInputSuccess() {
//		// input.csv must be a correctly formatted csv file containing a single tree
//		// with
//		// the tree data that is checked afterwards.
//		File input = new File("input.csv");
//
//		TMSService ts = new TMSService(ple);
//
//		try {
//			ts.loadFile(input);
//		} catch (InvalidInputException e) {
//			fail("Error in loading file!");
//		}
//
//		Tree foundTree = null;
//		// Check that Tree is in system
//		for (Tree tree : ple.getTrees()) {
//			if (tree.getLocal().getUsername().equals("idodin"))
//				foundTree = tree;
//		}
//		if (foundTree == null) {
//			fail("Input tree was not found within system");
//		}
//
//		assertEquals("daisy", foundTree.getSpecies().getName());
//		assertEquals("McGill", foundTree.getMunicipality().getName());
//		assertEquals(1, foundTree.getMunicipality().getIdNumber());
//		assertEquals(10, foundTree.getTreeLocation().getX());
//		assertEquals(11, foundTree.getTreeLocation().getY());
//		assertEquals(TreeStatus.Status.Healthy, foundTree.getTreeStatus().getStatus());
//		assertEquals("South", foundTree.getTreeLocation().getDescription());
//		assertEquals(LocationType.LandUseType.Institutional,
//				foundTree.getTreeLocation().getLocationType().getLandUseType());
//
//		Park park = null;
//		if (foundTree.getTreeLocation().getLocationType() instanceof Park) {
//			park = (Park) foundTree.getTreeLocation().getLocationType();
//		} else {
//			fail("Location type was not a park");
//		}
//
//		assertEquals(9, park.getParkCode());
//		assertEquals("Mt. Royal", park.getParkName());
//
//	}
//
//	@Test
//	public void TestEmptyFileInput() {
//		File input = new File("empty.csv");
//
//		TMSService ts = new TMSService(ple);
//		try {
//			ts.loadFile(input);
//		} catch (InvalidInputException e) {
//			fail("Error in loading file!");
//		}
//
//		assertEquals(0, ple.getTrees().size());
//	}
//
//	@Test
//	public void TestNullFileInput() {
//		File input = null;
//		String errorMessage = "";
//
//		TMSService ts = new TMSService(ple);
//		try {
//			ts.loadFile(input);
//		} catch (InvalidInputException e) {
//			errorMessage = e.getMessage();
//		}
//
//		assertEquals("Must select a file to input!", errorMessage);
//
//	}
//
//	@Test
//	public void TestBadContentFileInput() {
//		File input = new File("badcontent.csv");
//		String errorMessage = "";
//
//		TMSService ts = new TMSService(ple);
//		try {
//			ts.loadFile(input);
//		} catch (InvalidInputException e) {
//			errorMessage = e.getMessage();
//		}
//
//		assertEquals("Please check contents of file!", errorMessage);
//	}
//
//	@Test
//	public void TestBadFileFormatInput() {
//		File input = new File("hello.jpeg");
//		String errorMessage = "";
//
//		TMSService ts = new TMSService(ple);
//		try {
//			ts.loadFile(input);
//		} catch (InvalidInputException e) {
//			errorMessage = e.getMessage();
//		}
//
//		assertEquals("Please ensure file is of type .csv", errorMessage);
//	}
//
//	@Test
//	public void TestUpdateTreeSuccess() {
//		TMSService ts = new TMSService(ple);
//
//		Date datePlanted = Date.valueOf("2002-02-02");
//		Date dateAdded = new Date(Calendar.getInstance().getTime().getTime());
//		TreeStatus status = new TreeStatus(ple);
//		status.setStatus(TreeStatus.Status.Healthy);
//		Species species = new Species("daisy", 1, 1, ple);
//		User user = new User("idodin", ple);
//		Municipality municipality = new Municipality(1, "McGill", ple);
//		LocationType locationType = new Park(3, "Mt. Royal Park", ple);
//
//		Tree tree = new Tree(10, 11, datePlanted, dateAdded, status, species, user, municipality, ple);
//
//		Date newDatePlanted = Date.valueOf("2010-02-02");
//		Species newSpecies = new Species("clover", 2, 2, ple);
//		TreeStatus newStatus = new TreeStatus(ple);
//		newStatus.setStatus(TreeStatus.Status.Diseased);
//		User newUser = new User("aelehwany", ple);
//		Municipality newMunicipality = new Municipality(2, "Concordia", ple);
//		LocationType newLocationType = new Street("Sherbrooke", ple);
//		try {
//			ts.updateTree(tree, 11, 12, newDatePlanted, newStatus, newSpecies, newUser, newMunicipality, 1, 2, "south",
//					newLocationType);
//		} catch (InvalidInputException e) {
//			fail("Error in updating tree!");
//		}
//
//		assertEquals(11, tree.getHeight());
//		assertEquals(12, tree.getDiameter());
//		assertEquals("2010-02-02", tree.getDatePlanted().toString());
//		assertEquals("clover", tree.getSpecies().getName());
//		assertEquals("aelehwany", tree.getLocal().getUsername());
//		assertEquals("Concordia", tree.getMunicipality().getName());
//		assertEquals(1, tree.getTreeLocation().getX());
//		assertEquals(2, tree.getTreeLocation().getY());
//		assertEquals("south", tree.getTreeLocation().getDescription());
//
//		Street street = null;
//		if (tree.getTreeLocation().getLocationType() instanceof Street) {
//			street = (Street) tree.getTreeLocation().getLocationType();
//		} else {
//			fail("Location type was not a Street");
//		}
//
//		assertEquals("Sherbrooke", street.getStreetName());
//	}
//
//	@Test
//	public void TestUnavailableUpdateTree() {
//		TMSService ts = new TMSService(ple);
//		String errorMessage = "";
//
//		Date datePlanted = Date.valueOf("2002-02-02");
//		Date dateAdded = new Date(Calendar.getInstance().getTime().getTime());
//		TreeStatus status = new TreeStatus(ple);
//		status.setStatus(TreeStatus.Status.Healthy);
//		Species species = new Species("daisy", 1, 1, ple);
//		User user = new User("idodin", ple);
//		Municipality municipality = new Municipality(1, "McGill", ple);
//		LocationType locationType = new Park(3, "Mt. Royal Park", ple);
//
//		Tree tree = new Tree(10, 11, datePlanted, dateAdded, status, species, user, municipality, ple);
//
//		Date newDatePlanted = Date.valueOf("2010-02-02");
//		Species newSpecies = new Species("clover", 2, 2, ple2);
//		TreeStatus newStatus = new TreeStatus(ple2);
//		newStatus.setStatus(TreeStatus.Status.Diseased);
//		User newUser = new User("aelehwany", ple2);
//		Municipality newMunicipality = new Municipality(2, "Concordia", ple2);
//		LocationType newLocationType = new Street("Sherbrooke", ple2);
//
//		try {
//			ts.updateTree(tree, 11, 12, newDatePlanted, newStatus, newSpecies, newUser, newMunicipality, 1, 2, "south",
//					newLocationType);
//		} catch (InvalidInputException e) {
//			errorMessage = e.getMessage();
//		}
//
//		assertEquals(
//				"Status must exist! Species must exist! User must be registered! Municipality must exist! Street must exist!",
//				errorMessage);
//	}
//
//	@Test
//	public void TestNullInputUpdateTree() {
//		TMSService ts = new TMSService(ple);
//		String errorMessage = "";
//		Date datePlanted = Date.valueOf("2100-02-02");
//
//		try {
//			ts.updateTree(null, -4, -4, null, null, null, null, null, -3, -3, null, null);
//		} catch (InvalidInputException e) {
//			errorMessage = e.getMessage();
//		}
//
//		assertEquals(
//				"Tree needs to be selected to be updated! Cannot pass negative integer! Cannot plant tree in the future! Status needs to be selected for registration! Species needs to be selected for registration! User needs to be logged in for registration! Municipality needs to be selected for registration!",
//				errorMessage);
//
//		assertEquals(
//				"Status must exist! Species must exist! User must be registered! Municipality must exist! Park must exist!",
//				errorMessage);
//
//	}

	// Calculate current oxygen production of a list of trees.
	@Test
	public void TestCalculateOxygenProduction() {
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", ple);
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
			production = erc.calcOxygenProd(treeList);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(13, production);

	}

	// testing if calculateOxygenProduction is called with empty list of trees.
	@Test
	public void TestEmptyListOxygenProduction() {
		String error = "";
		List<Tree> treeList = new java.util.ArrayList<>();
		TMSService erc = new TMSService(ple);
		try {
			erc.calcOxygenProd(treeList);
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
		User user = new User("aehwany", ple);
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
			erc.calcCarbonConsump(treeList);
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
			erc.bioForecast(treeList);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be null", error);
		}
	}

	@Test
	public void testMarkDiseasedSuccess() {
		TMSService ts = new TMSService(ple);

		Date datePlanted = Date.valueOf("2002-02-02");
		Date dateAdded = new Date(Calendar.getInstance().getTime().getTime());
		TreeStatus status = new TreeStatus(ple);
		status.setStatus(TreeStatus.Status.Healthy);
		Species species = new Species("daisy", 1, 1, ple);
		User user = new User("idodin", ple);
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
		User user = new User("idodin", ple);
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

		Date datePlanted = Date.valueOf("2002-02-02");
		Date dateAdded = new Date(Calendar.getInstance().getTime().getTime());
		TreeStatus status = new TreeStatus(ple);
		status.setStatus(TreeStatus.Status.Diseased);
		Species species = new Species("daisy", 1, 1, ple);
		User user = new User("idodin", ple);
		Municipality municipality = new Municipality(1, "McGill", ple);

		Tree tree = new Tree(1, 1, datePlanted, dateAdded, status, species, user, municipality, ple);

		try {
			ts.markToBeCut(tree);
		} catch (InvalidInputException e) {
			fail("Tree couldn't be marked as to be cut due to Invalid Input.");
		}

		assertEquals(true, tree.getTreeStatus().getToBeCut());
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
		User user = new User("idodin", ple);
		Municipality municipality = new Municipality(1, "McGill", ple);

		Tree tree = new Tree(1, 1, datePlanted, dateAdded, status, species, user, municipality, ple);

		try {
			ts.markToBeCut(tree);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals("Tree was already cut down!", error);
	}

	@Test
	// test null list entry between valid list entries
	public void testNullEntryBioForecast() {
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", ple);
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
			erc.bioForecast(treeList);
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
			erc.bioForecast(treeList);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be empty", error);
		}
	}

	 //test validInputBioForecast
	@Test
	public void testValidInputBioForecast() {
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", ple);
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
			bioForecast = erc.bioForecast(treeList);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(-2, bioForecast);
	}

	// test null input for bioIndexCalculator
	@Test
	public void testNullListBioIndex() {
		String error = "";
		List<Tree> treeList = null;
		TMSService erc = new TMSService(ple);
		try {
			erc.bioIndexCalculator(treeList);
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
		User user = new User("aehwany", ple);
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
			erc.bioIndexCalculator(treeList);
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
			erc.bioIndexCalculator(treeList);
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
		User user = new User("aehwany", ple);
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

		int bioIndex = 0;
		TMSService erc = new TMSService(ple);
		try {
			bioIndex = erc.bioIndexCalculator(treeList);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(2, bioIndex);
	}

	// Test for valid calculation of a change in oxygen production
	// for list of trees
	@Test
	public void testValidChangeInOxygenProduction() {
		int height = 4;
		int diameter = 11;
		User user1 = new User("fouad", ple);
		User user2 = new User("bitar", ple);
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
		assertEquals(21, changeInOxygenProduction);
		try {
			changeInOxygenProduction = erc.oxygenForecast(treeList, "Cut");
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(-25, changeInOxygenProduction);
		try {
			changeInOxygenProduction = erc.oxygenForecast(treeList, "Diseased");
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(-2, changeInOxygenProduction);
	}

	@Test
	public void testNullInputOxygenProd() {
		String error = "";
		List<Tree> treeList = null;
		TMSService erc = new TMSService(ple);
		try {
			erc.oxygenForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be null", error);
		}
		try {
			erc.oxygenForecast(treeList, "Cut");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be null", error);
		}
		try {
			erc.oxygenForecast(treeList, "Diseased");
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
		User user1 = new User("fouad", ple);
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
			erc.oxygenForecast(treeList, null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("String cannot be null", error);
		}
		try {
			erc.oxygenForecast(treeList, "hug");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("Please enter a valid tree status", error);
		}
	}

	@Test
	public void testNullEntryOxygenProd() {
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", ple);
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
			erc.oxygenForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("Cannot have null entry for tree in list", error);
		}
	}

	@Test
	public void testValidChangeInCarbonConsumption() {
		int height = 4;
		int diameter = 11;
		User user1 = new User("fouad", ple);
		User user2 = new User("bitar", ple);
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
		assertEquals(44, changeInCarbonConsumption);
		try {
			changeInCarbonConsumption=0;
			changeInCarbonConsumption = erc.carbonForecast(treeList, "Cut");
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(-46, changeInCarbonConsumption);
		try {
			changeInCarbonConsumption=0;
			changeInCarbonConsumption = erc.carbonForecast(treeList, "Diseased");
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(-1, changeInCarbonConsumption);
	}

	@Test
	public void testNullInputCabronConsump() {
		String error = "";
		List<Tree> treeList = null;
		TMSService erc = new TMSService(ple);
		try {
			erc.carbonForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be null", error);
		}
		try {
			erc.carbonForecast(treeList, "Cut");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("List cannot be null", error);
		}
		try {
			erc.carbonForecast(treeList, "Diseased");
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
		User user1 = new User("fouad", ple);
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
			erc.carbonForecast(treeList, null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("String cannot be null", error);
		}
		try {
			erc.carbonForecast(treeList, "hug");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("Please enter a valid tree status", error);
		}
	}

	@Test
	public void testNullEntryCarbonConsump() {
		int height = 5;
		int diameter = 10;
		User user = new User("aehwany", ple);
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
			erc.carbonForecast(treeList, "Healthy");
		} catch (InvalidInputException e) {
			error = e.getMessage();
			assertEquals("Cannot have null entry for tree in list", error);
		}
	}

}