package ca.mcgill.ecse321.TMS.service;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Time;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.TMS.model.LocationType;
import ca.mcgill.ecse321.TMS.model.Municipality;
import ca.mcgill.ecse321.TMS.model.Species;
import ca.mcgill.ecse321.TMS.model.Street;
import ca.mcgill.ecse321.TMS.model.Tree;
import ca.mcgill.ecse321.TMS.model.TreeLocation;
import ca.mcgill.ecse321.TMS.model.TreePLE;
import ca.mcgill.ecse321.TMS.model.TreeStatus;
import ca.mcgill.ecse321.TMS.model.TreeStatus.Status;
import ca.mcgill.ecse321.TMS.model.User;
import ca.mcgill.ecse321.TMS.persistence.PersistenceXStream;

public class TestService {

	private TreePLE ple;
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
		//create tree classes
		
	}

	@After
	public void tearDown() throws Exception {
		ple.delete();
	}
	
	  
	
	@Test
	public void testCreatetree() {
		assertEquals(0, ple.getTrees().size());	 
		//create tree properties
		int height=5;
		int diameter=10;
		User user=new User("aehwany", ple);
		Species species= new Species("dandelion", 5, 4, ple);
		Municipality municipality=new Municipality("Rosement", "T10", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted=new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded=new Date(c2.getTimeInMillis());
		TreeStatus status=new TreeStatus(ple);
		LocationType location=new LocationType();
		  try {
			TMSService erc = new TMSService(ple);
			erc.createTree(20, height, diameter, datePlanted, status,species, user, municipality,
					3, 8, "description", location);
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
		Municipality municipality=new Municipality("Rosement", "T10", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted=new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded=new Date(c2.getTimeInMillis());
		TreeStatus status=new TreeStatus(ple);
		Tree tree = new Tree(20, height, diameter, datePlanted,dateAdded, status,species, user, municipality, ple);
		
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
		Municipality municipality=new Municipality("Rosement", "T10", ple);
		Calendar c1 = Calendar.getInstance();
		Date datePlanted=new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		Date dateAdded=new Date(c2.getTimeInMillis());
		TreeStatus status=new TreeStatus(ple);
		 //Create trees
		 Tree tree1 = new Tree(20, height, diameter, datePlanted,dateAdded, status,species, user, municipality, ple);
		 Tree tree2 = new Tree(20, height, diameter, datePlanted,dateAdded, status,species, user, municipality, ple);	
		 Tree tree3 = new Tree(20, height, diameter, datePlanted,dateAdded, status,species, user, municipality, ple);
			
		 
		 
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
	public void TestcheckTreeInputException() {
		int id=-20;
		int height=-5;
		int diameter=-10;
		int x=-2;
		int y=-5;
		User user=new User("aehwany", ple);
		Species species= new Species("dandelion", 5, 4, ple);
		Municipality municipality=new Municipality("Rosement", "T10", ple);
		Calendar c1 = Calendar.getInstance();
		c1.set(2017, Calendar.MARCH, 16, 10, 30, 0);
		Date datePlanted=new Date(c1.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		c2.set(2017, Calendar.MARCH, 14, 9, 0, 0);
		Date dateAdded=new Date(c2.getTimeInMillis());
		TreeStatus status=new TreeStatus(ple);
		Street street=null;
		LocationType locationType1=street;
		
		Tree tree1 = new Tree(id, height, diameter, datePlanted,dateAdded, status,species, user, municipality, ple);
		//Tree tree2 = new Tree(id, height, diameter, datePlanted,dateAdded, status,species, user, municipality, ple);
		
		//TreeLocation location = new TreeLocation(x, y, "    ", tree1, locationType1);
		
		//check1
		String error = null;
		TMSService erc = new TMSService(ple);
		try {
			erc.createTree();
		} catch (InvalidInputException e) {
		      error = e.getMessage();
		  }
		assertEquals(
				"Cannot pass negative integer!Tree with that ID already exists!Cannot plant tree in the future!Status needs to be selected for registration!Species needs to be selected for registration!User needs to be logged in for registration!Municipality needs to be selected for registration!",
		          error);
	}

	
}