/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.util.*;

// line 51 "../../../../../TreePLE.ump"
public class Street extends LocationType
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Street> streetsByStreetName = new HashMap<String, Street>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Street Attributes
  private String streetName;

  //Street Associations
  private TreePLE treePLE;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Street(TreeLocation aTreeLocation, String aStreetName, TreePLE aTreePLE)
  {
    super(aTreeLocation);
    if (!setStreetName(aStreetName))
    {
      throw new RuntimeException("Cannot create due to duplicate streetName");
    }
    boolean didAddTreePLE = setTreePLE(aTreePLE);
    if (!didAddTreePLE)
    {
      throw new RuntimeException("Unable to create street due to treePLE");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStreetName(String aStreetName)
  {
    boolean wasSet = false;
    String anOldStreetName = getStreetName();
    if (hasWithStreetName(aStreetName)) {
      return wasSet;
    }
    streetName = aStreetName;
    wasSet = true;
    if (anOldStreetName != null) {
      streetsByStreetName.remove(anOldStreetName);
    }
    streetsByStreetName.put(aStreetName, this);
    return wasSet;
  }

  public String getStreetName()
  {
    return streetName;
  }

  public static Street getWithStreetName(String aStreetName)
  {
    return streetsByStreetName.get(aStreetName);
  }

  public static boolean hasWithStreetName(String aStreetName)
  {
    return getWithStreetName(aStreetName) != null;
  }

  public TreePLE getTreePLE()
  {
    return treePLE;
  }

  public boolean setTreePLE(TreePLE aTreePLE)
  {
    boolean wasSet = false;
    if (aTreePLE == null)
    {
      return wasSet;
    }

    TreePLE existingTreePLE = treePLE;
    treePLE = aTreePLE;
    if (existingTreePLE != null && !existingTreePLE.equals(aTreePLE))
    {
      existingTreePLE.removeStreet(this);
    }
    treePLE.addStreet(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    streetsByStreetName.remove(getStreetName());
    TreePLE placeholderTreePLE = treePLE;
    this.treePLE = null;
    placeholderTreePLE.removeStreet(this);
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "streetName" + ":" + getStreetName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "treePLE = "+(getTreePLE()!=null?Integer.toHexString(System.identityHashCode(getTreePLE())):"null");
  }
}