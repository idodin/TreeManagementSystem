package ca.mcgill.ecse321.TMS.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Date;
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

	//Calculate current oxygen production of a list of trees.
	@Test
	public void TestCalculateOxygenProduction() {
		int height=5;
		int diameter=10;
		User user=new User("aehwany", ple);
		Species species1= new Species("dandelion", 18, 10, ple);
		Species species2= new Species("Coconut", 8, 2, ple);
		Species species3= new Species("Pine", 10, 6, ple);
		Municipality municipality=new Municipality(10, "rosemont", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted=new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded=new Date(c2.getTimeInMillis());
		TreeStatus status1=new TreeStatus(ple);
		TreeStatus status2=new TreeStatus(ple);
		TreeStatus status3=new TreeStatus(ple);
		status1.setStatus(Status.Healthy);
		status2.setStatus(Status.Cut);
		status3.setStatus(Status.Diseased);
		//Create trees
		Tree tree1 = new Tree(height, diameter, datePlanted,dateAdded, status1,species1, user, municipality, ple);
		Tree tree2 = new Tree(height, diameter, datePlanted,dateAdded, status2,species2, user, municipality, ple);	
		Tree tree3 = new Tree(height, diameter, datePlanted,dateAdded, status3,species3, user, municipality, ple);

		List<Tree> treeList=new java.util.ArrayList<>();
		treeList.add(tree1);
		treeList.add(tree2);
		treeList.add(tree3);
		int production=0;
		TMSService erc = new TMSService(ple);
		try {
			production=erc.calculateOxygenProduction(treeList);
		}catch(InvalidInputException e) {
			fail();
		}
		assertEquals(13, production);

	}
	//testing if calculateOxygenProduction is called with empty list of trees.
	@Test
	public void TestEmptyListOxygenProduction() {		
		String error="";
		List<Tree> treeList=new java.util.ArrayList<>();
		TMSService erc = new TMSService(ple);
		try {
			int production=erc.calculateOxygenProduction(treeList);
		}catch(InvalidInputException e){
			error=e.getMessage();
			assertEquals("Please enter a list of trees", error);
		}

	}

	//Calculate current carbon consumption of a list of trees.
	@Test
	public void TestCalculateCarbonConsumption() {
		int height=5;
		int diameter=10;
		User user=new User("aehwany", ple);
		Species species1= new Species("dandelion", 18, 10, ple);
		Species species2= new Species("Coconut", 8, 2, ple);
		Species species3= new Species("Pine", 10, 6, ple);
		Municipality municipality=new Municipality(10, "rosemont", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted=new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded=new Date(c2.getTimeInMillis());
		TreeStatus status1=new TreeStatus(ple);
		TreeStatus status2=new TreeStatus(ple);
		TreeStatus status3=new TreeStatus(ple);
		status1.setStatus(Status.Healthy);
		status2.setStatus(Status.Cut);
		status3.setStatus(Status.Diseased);
		//Create trees
		Tree tree1 = new Tree(height, diameter, datePlanted,dateAdded, status1,species1, user, municipality, ple);
		Tree tree2 = new Tree(height, diameter, datePlanted,dateAdded, status2,species2, user, municipality, ple);	
		Tree tree3 = new Tree(height, diameter, datePlanted,dateAdded, status3,species3, user, municipality, ple);

		List<Tree> treeList=new java.util.ArrayList<>();
		treeList.add(tree1);
		treeList.add(tree2);
		treeList.add(tree3);
		int consumption=0;
		TMSService erc = new TMSService(ple);
		try{
			consumption=erc.calculateCarbonConsumption(treeList);
		}
		catch(InvalidInputException e){
			fail();
		}
		assertEquals(23, consumption);

	}

	//testing if calculateCarbonConsumption is called with empty list of trees.
	@Test
	public void TestEmptyListCarbonConsumption() {		
		String error="";
		List<Tree> treeList=new java.util.ArrayList<>();
		TMSService erc = new TMSService(ple);
		try {
			int consumption=erc.calculateCarbonConsumption(treeList);
		}catch(InvalidInputException e){
			error=e.getMessage();
			assertEquals("Please enter a list of trees", error);
		}

	}

	//test null input for bioForecast
	@Test
	public void testNullListBioForecast() {
		String error="";
		List<Tree> treeList= null;
		TMSService erc = new TMSService(ple);
		try {
			int bioForecast = erc.bioForecast(treeList);
		}catch(InvalidInputException e){
			error=e.getMessage();
			assertEquals("List cannot be null", error);
		}
	}
	
	@Test
	//test null list entry between valid list entries
	public void testNullEntryBioForecast() {
		int height=5;
		int diameter=10;
		User user=new User("aehwany", ple);
		Species species1= new Species("dandelion", 18, 10, ple);
		Species species2= new Species("Coconut", 8, 2, ple);
		Species species3= new Species("Pine", 10, 6, ple);
		Municipality municipality=new Municipality(10, "rosemont", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted=new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded=new Date(c2.getTimeInMillis());
		TreeStatus status1=new TreeStatus(ple);
		TreeStatus status2=new TreeStatus(ple);
		TreeStatus status3=new TreeStatus(ple);
		status1.setStatus(Status.Healthy);
		status2.setStatus(Status.Cut);
		status3.setStatus(Status.Diseased);
		//Create trees
		Tree tree1 = new Tree(height, diameter, datePlanted,dateAdded, status1,species1, user, municipality, ple);
		Tree tree2 = new Tree(height, diameter, datePlanted,dateAdded, status2,species2, user, municipality, ple);	
		Tree tree3 = new Tree(height, diameter, datePlanted,dateAdded, status3,species3, user, municipality, ple);

		List<Tree> treeList=new java.util.ArrayList<>();
		treeList.add(tree1);
		treeList.add(tree2);
		treeList.add(tree3);
		treeList.add(null);
		TMSService erc = new TMSService(ple);
		String error = "";
		try {
			int bioForecast = erc.bioForecast(treeList);
		}catch(InvalidInputException e){
			error=e.getMessage();
			assertEquals("The list contains a null entry", error);
		}
	}
	
	//test empty list input for bioForecast
	@Test
	public void testEmptyListBioForecast() {
		String error="";
		List<Tree> treeList= new java.util.ArrayList<>();
		TMSService erc = new TMSService(ple);
		try {
			int bioForecast = erc.bioForecast(treeList);
		}catch(InvalidInputException e){
			error=e.getMessage();
			assertEquals("List cannot be empty", error);
		}
	}
	
	//test valid input for BioForecast
	@Test
	public void testValidInputBioForecast() {
		int height=5;
		int diameter=10;
		User user=new User("aehwany", ple);
		Species species1= new Species("dandelion", 18, 10, ple);
		Species species2= new Species("Coconut", 8, 2, ple);
		Species species3= new Species("Pine", 10, 6, ple);
		Municipality municipality=new Municipality(10, "rosemont", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted=new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded=new Date(c2.getTimeInMillis());
		TreeStatus status1=new TreeStatus(ple);
		TreeStatus status2=new TreeStatus(ple);
		TreeStatus status3=new TreeStatus(ple);
		status1.setStatus(Status.Healthy);
		status2.setStatus(Status.Cut);
		status3.setStatus(Status.Diseased);
		//Create trees
		Tree tree1 = new Tree(height, diameter, datePlanted,dateAdded, status1,species1, user, municipality, ple);
		Tree tree2 = new Tree(height, diameter, datePlanted,dateAdded, status2,species2, user, municipality, ple);	
		Tree tree3 = new Tree(height, diameter, datePlanted,dateAdded, status3,species3, user, municipality, ple);

		List<Tree> treeList=new java.util.ArrayList<>();
		treeList.add(tree1);
		treeList.add(tree2);
		treeList.add(tree3);
		
		int bioForecast=0;
		TMSService erc = new TMSService(ple);
		try{
			bioForecast = erc.bioForecast(treeList);
		}
		catch(InvalidInputException e){
			fail();
		}
		assertEquals(6, bioForecast);
	}
	
	//test null input for bioIndexCalculator
		@Test
		public void testNullListBioIndex() {
			String error="";
			List<Tree> treeList= null;
			TMSService erc = new TMSService(ple);
			try {
				int bioForecast = erc.bioIndexCalculator(treeList);
			}catch(InvalidInputException e){
				error=e.getMessage();
				assertEquals("List cannot be null", error);
			}
		}
		
		@Test
		//test null list entry between valid list entries
		public void testNullEntryBioIndex() {
			int height=5;
			int diameter=10;
			User user=new User("aehwany", ple);
			Species species1= new Species("dandelion", 18, 10, ple);
			Species species2= new Species("Coconut", 8, 2, ple);
			Species species3= new Species("Pine", 10, 6, ple);
			Municipality municipality=new Municipality(10, "rosemont", ple);
			Calendar c1 = Calendar.getInstance();
			Date datePlanted=new Date(c1.getTimeInMillis());
			Calendar c2 = Calendar.getInstance();
			Date dateAdded=new Date(c2.getTimeInMillis());
			TreeStatus status1=new TreeStatus(ple);
			TreeStatus status2=new TreeStatus(ple);
			TreeStatus status3=new TreeStatus(ple);
			status1.setStatus(Status.Healthy);
			status2.setStatus(Status.Cut);
			status3.setStatus(Status.Diseased);
			//Create trees
			Tree tree1 = new Tree(height, diameter, datePlanted,dateAdded, status1,species1, user, municipality, ple);
			Tree tree2 = new Tree(height, diameter, datePlanted,dateAdded, status2,species2, user, municipality, ple);	
			Tree tree3 = new Tree(height, diameter, datePlanted,dateAdded, status3,species3, user, municipality, ple);

			List<Tree> treeList=new java.util.ArrayList<>();
			treeList.add(tree1);
			treeList.add(tree2);
			treeList.add(tree3);
			treeList.add(null);
			TMSService erc = new TMSService(ple);
			String error = "";
			try {
				int bioForecast = erc.bioIndexCalculator(treeList);
			}catch(InvalidInputException e){
				error=e.getMessage();
				assertEquals("The list contains a null entry", error);
			}
		}
		
		//test empty list input for bioIndexCalculator
		@Test
		public void testEmptyListBioIndex() {
			String error="";
			List<Tree> treeList= new java.util.ArrayList<>();
			TMSService erc = new TMSService(ple);
			try {
				int bioForecast = erc.bioIndexCalculator(treeList);
			}catch(InvalidInputException e){
				error=e.getMessage();
				assertEquals("List cannot be empty", error);
			}
		}
		
		//test valid input for bioIndexCalculator
		@Test
		public void testValidInputBioIndex() {
			int height=5;
			int diameter=10;
			User user=new User("aehwany", ple);
			Species species1= new Species("dandelion", 18, 10, ple);
			Species species2= new Species("Coconut", 8, 2, ple);
			Species species3= new Species("Pine", 10, 6, ple);
			Municipality municipality=new Municipality(10, "rosemont", ple);
			Calendar c1 = Calendar.getInstance();
			Date datePlanted=new Date(c1.getTimeInMillis());
			Calendar c2 = Calendar.getInstance();
			Date dateAdded=new Date(c2.getTimeInMillis());
			TreeStatus status1=new TreeStatus(ple);
			TreeStatus status2=new TreeStatus(ple);
			TreeStatus status3=new TreeStatus(ple);
			status1.setStatus(Status.Healthy);
			status2.setStatus(Status.Cut);
			status3.setStatus(Status.Diseased);
			//Create trees
			Tree tree1 = new Tree(height, diameter, datePlanted,dateAdded, status1,species1, user, municipality, ple);
			Tree tree2 = new Tree(height, diameter, datePlanted,dateAdded, status2,species2, user, municipality, ple);	
			Tree tree3 = new Tree(height, diameter, datePlanted,dateAdded, status3,species3, user, municipality, ple);

			List<Tree> treeList=new java.util.ArrayList<>();
			treeList.add(tree1);
			treeList.add(tree2);
			treeList.add(tree3);
			
			int bioForecast=0;
			TMSService erc = new TMSService(ple);
			try{
				bioForecast = erc.bioIndexCalculator(treeList);
			}
			catch(InvalidInputException e){
				fail();
			}
			assertEquals(8, bioForecast);
		}
	

}