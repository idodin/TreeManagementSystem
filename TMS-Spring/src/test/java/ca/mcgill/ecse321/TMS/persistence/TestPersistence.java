package ca.mcgill.ecse321.TMS.persistence;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.TMS.model.Municipality;
import ca.mcgill.ecse321.TMS.model.Species;
import ca.mcgill.ecse321.TMS.model.Tree;
import ca.mcgill.ecse321.TMS.model.TreeLocation;
import ca.mcgill.ecse321.TMS.model.TreePLE;
import ca.mcgill.ecse321.TMS.model.User;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import java.io.File;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestPersistence {
private TreePLE ple;
@Before
public void setUp() throws Exception {
	ple = new TreePLE();
	
    // create trees
	User user=new User("aehwany", ple);
	Species species= new Species("dandelion", 5, 4, ple);
	Municipality municipality=new Municipality("Rosement", 10, ple);
    Tree tree = new Tree(5, 2, species , user, ple);
    TreeLocation location=new TreeLocation(5,7, municipality , tree);
    
    User user2=new User("karim", ple);
	Species species2= new Species("poppy", 5, 4, ple);
	Municipality municipality2=new Municipality("Saintlaurent", 10, ple);
	Tree tree2 = new Tree(9, 4, species2 , user2, ple);
	TreeLocation location2= new TreeLocation(10, 5,municipality2, tree2);
    
   
   
}

@After
public void tearDown() throws Exception {
	ple.delete();
}

@Test
public void test() {
	// initialize model file
    PersistenceXStream.initializeModelManager("output"+File.separator+"data.xml");
    // save model that is loaded during test setup
    if (!PersistenceXStream.saveToXMLwithXStream(ple))
        fail("Could not save file.");

    // clear the model in memory
    ple.delete();
    assertEquals(0, ple.getLocals().size());
    assertEquals(0, ple.getMunicipalities().size());
    assertEquals(0, ple.numberOfTrees());

    // load model
    ple = (TreePLE) PersistenceXStream.loadFromXMLwithXStream();
    if (ple == null)
        fail("Could not load file.");

    // check tree
    assertEquals(2, ple.numberOfTrees());
    assertEquals(2, ple.getLocals().size());
    assertEquals(2, ple.getSpecies().size());
    assertEquals(2, ple.getMunicipalities().size());
    
    assertEquals("aehwany", ple.getTree(0).getLocal().getUsername());
    assertEquals("dandelion" , ple.getTree(0).getSpecies().getName());
    assertEquals(5 , ple.getTree(0).getHeight());
    assertEquals("Rosement" , ple.getTree(0).getTreeLocation().getMunicipality().getName());
    
    assertEquals("karim", ple.getTree(1).getLocal().getUsername());
    assertEquals("poppy" , ple.getTree(1).getSpecies().getName());
    assertEquals(9 , ple.getTree(1).getHeight());
    assertEquals("Saintlaurent" , ple.getTree(1).getTreeLocation().getMunicipality().getName());

    
   
    
}
}
