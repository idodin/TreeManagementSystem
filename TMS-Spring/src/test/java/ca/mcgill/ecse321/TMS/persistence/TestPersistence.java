package ca.mcgill.ecse321.TMS.persistence;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Date;
import java.util.Calendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.TMS.model.LocationType;
import ca.mcgill.ecse321.TMS.model.Municipality;
import ca.mcgill.ecse321.TMS.model.Species;
import ca.mcgill.ecse321.TMS.model.Tree;
import ca.mcgill.ecse321.TMS.model.TreePLE;
import ca.mcgill.ecse321.TMS.model.TreeStatus;
import ca.mcgill.ecse321.TMS.model.User;

public class TestPersistence {
private TreePLE ple;
@Before
public void setUp() throws Exception {
	ple = new TreePLE();
	
    // create trees
	User user=new User("aehwany", ple);
	Species species= new Species("dandelion", 5, 4, ple);
	Municipality municipality=new Municipality(10,"Rosement", ple);
	Calendar c1 = Calendar.getInstance();
	Date datePlanted=new Date(c1.getTimeInMillis());
	Calendar c2 = Calendar.getInstance();
	Date dateAdded=new Date(c2.getTimeInMillis());
	TreeStatus status=new TreeStatus(ple);
    Tree tree = new Tree(5, 7, datePlanted, dateAdded, status, species, user, municipality, ple);
    
    User user2=new User("karim", ple);
	Species species2= new Species("poppy", 9, 7, ple);
	Municipality municipality2=new Municipality(43,"Saintlaurent",  ple);
	Calendar c3 = Calendar.getInstance();
	Date datePlanted2=new Date(c3.getTimeInMillis());
	Calendar c4 = Calendar.getInstance();
	Date dateAdded2=new Date(c4.getTimeInMillis());
	TreeStatus status2=new TreeStatus(ple);
	Tree tree2 = new Tree(17, 10, datePlanted2, dateAdded2, status2, species2, user2, municipality2, ple);
   
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
//    if (!PersistenceXStream.saveToXMLwithXStream(ple))
//        fail("Could not save file.");

    // clear the model in memory
   /* ple.delete();
    assertEquals(0, ple.getUsers().size());
    assertEquals(0, ple.getMunicipalities().size());
    assertEquals(0, ple.numberOfTrees());
*/
    // load model
    /*ple = (TreePLE) PersistenceXStream.loadFromXMLwithXStream();
    if (ple == null)
        fail("Could not load file.");
*/
    // check tree
    assertEquals(2, ple.numberOfTrees());
    assertEquals(2, ple.getUsers().size());
    assertEquals(2, ple.getSpecies().size());
    assertEquals(2, ple.getMunicipalities().size());
    
    assertEquals("aehwany", ple.getTree(0).getLocal().getUsername());
    assertEquals("dandelion" , ple.getTree(0).getSpecies().getName());
    assertEquals(5 , ple.getTree(0).getHeight());
    assertEquals("Rosement" , ple.getTree(0).getMunicipality().getName());
    
    assertEquals("karim", ple.getTree(1).getLocal().getUsername());
    assertEquals("poppy" , ple.getTree(1).getSpecies().getName());
    assertEquals(17 , ple.getTree(1).getHeight());
    assertEquals("Saintlaurent" , ple.getTree(1).getMunicipality().getName());

    
   
    
}
}