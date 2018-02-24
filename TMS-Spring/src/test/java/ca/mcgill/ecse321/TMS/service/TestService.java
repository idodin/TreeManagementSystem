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

import ca.mcgill.ecse321.TMS.model.Species;
import ca.mcgill.ecse321.TMS.model.Tree;
import ca.mcgill.ecse321.TMS.model.TreePLE;
import ca.mcgill.ecse321.TMS.model.User;
import ca.mcgill.ecse321.TMS.persistence.PersistenceXStream;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;

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
		String name = "aehwany";
		  int height=5;
		  int diameter=10;  
		  User user=new User(name, ple);
		  Species species= new Species("dandelion", 5, 4, ple);
		
		  try {
			TMSService erc = new TMSService(ple);
			erc.createTree(height, diameter, species, user, ple);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			fail();
		}
		  TreePLE ple1 = ple;
		  checkResultTree(height, diameter,species, user, ple1);

		 TreePLE ple2 = (TreePLE) PersistenceXStream.loadFromXMLwithXStream();

		  // check file contents
		  checkResultTree(height, diameter,species, user, ple2);
		  ple2.delete();
	}
	@Test
	public void testRemovetree() {
		
		//create tree properties
		String name = "aehwany";
		 int height=5;
		 int diameter=10;  
		 User user=new User(name, ple);
		 Species species= new Species("dandelion", 5, 4, ple);
		 Tree tree = new Tree(height, diameter, species , user, ple);
		 
		 ple.addTree(tree);
		 assertEquals(1, ple.getTrees().size()); 
			
		 try {
				TMSService erc = new TMSService(ple);
				erc.removeTree(tree, ple);
			} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
				fail();
			}
		 
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
		String name = "aehwany";
		 int height=5;
		 int diameter=10;  
		 User user=new User(name, ple);
		 Species species= new Species("dandelion", 5, 4, ple);
		 
		 //Create trees
		 Tree tree1 = new Tree(height, diameter, species , user, ple);
		 Tree tree2 = new Tree(height, diameter, species , user, ple);
		 Tree tree3 = new Tree(height, diameter, species , user, ple);
		 
		 
		 List<Tree> treeList;
		 try {
				TMSService erc = new TMSService(ple);
				treeList=erc.findAllTrees();
			} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
				fail();
			}
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
	
	
}