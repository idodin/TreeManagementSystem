package ca.mcgill.ecse321.TMS.persistence;

//added a comments
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;

import ca.mcgill.ecse321.TMS.model.Local;
import ca.mcgill.ecse321.TMS.model.LocationType;
import ca.mcgill.ecse321.TMS.model.Municipality;
import ca.mcgill.ecse321.TMS.model.Park;
import ca.mcgill.ecse321.TMS.model.Specialist;
import ca.mcgill.ecse321.TMS.model.Species;
import ca.mcgill.ecse321.TMS.model.Street;
import ca.mcgill.ecse321.TMS.model.Tree;
import ca.mcgill.ecse321.TMS.model.TreeLocation;
import ca.mcgill.ecse321.TMS.model.TreePLE;
import ca.mcgill.ecse321.TMS.model.TreeStatus;
import ca.mcgill.ecse321.TMS.model.User;
import ca.mcgill.ecse321.TMS.model.UserRole;

// The first type parameter is the domain type for wich we are creating the repository.
// The second is the key type that is used to look it up. This example will not use it.
@Repository
public class PersistenceXStream {

	private static XStream xstream = new XStream();
	private static String filename = "/treePLE/data.xml";

	// TODO create the RegistrationManager instance here (replace the void return value as well)
	public static TreePLE initializeModelManager(String fileName) {
		// Initialization for persistence
        TreePLE tp;
        setFilename(fileName);
        setAlias("user", User.class);
        setAlias("local", Local.class);
        setAlias("municipality", Municipality.class);
        setAlias("specialist", Specialist.class);
        setAlias("species", Species.class);
        setAlias("status", TreeStatus.class);
        setAlias("tree", Tree.class);
        setAlias("treePLE", TreePLE.class);
        setAlias("treeStatus", TreeStatus.class);
        setAlias("treeLocation", TreeLocation.class);
        setAlias("userRole", UserRole.class);
        setAlias("locationType", LocationType.class);
        setAlias("park", Park.class);
        setAlias("street", Street.class);
        
        

        // load model if exists, create otherwise
        File file = new File(fileName);
        if (file.exists()) {
        	tp = (TreePLE) loadFromXMLwithXStream();
        	tp.reinitialize();
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            tp = new TreePLE();
            saveToXMLwithXStream(tp);
        }
        return tp;
	}

	public static boolean saveToXMLwithXStream(Object obj) {
		xstream.setMode(XStream.ID_REFERENCES);
        String xml = xstream.toXML(obj); // save our xml file

        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(xml);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}

	public static Object loadFromXMLwithXStream() {
		xstream.setMode(XStream.ID_REFERENCES);
        try {
            FileReader fileReader = new FileReader(filename); // load our xml file
            return xstream.fromXML(fileReader);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}

	public static void setAlias(String xmlTagName, Class<?> className) {
		xstream.alias(xmlTagName, className);
	}

	public static void setFilename(String fn) {
		filename = fn;
	}

	public static String getFilename() {
		return filename;
	}

	public static void clearData() {
		File myFoo = new File(filename);
		FileWriter fooWriter;
		try {
			fooWriter = new FileWriter(myFoo, false);
			fooWriter.write("");
			fooWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
