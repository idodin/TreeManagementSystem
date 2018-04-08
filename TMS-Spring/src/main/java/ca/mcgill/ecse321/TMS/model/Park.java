/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.util.*;

// line 59 "../../../../../TreePLE.ump"
public class Park extends LocationType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Park Attributes
  private int parkCode;
  private String parkName;

  //Park Associations
  private TreePLE treePLE;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Park(int aParkCode, String aParkName, TreePLE aTreePLE)
  {
    super();
    parkCode = aParkCode;
    parkName = aParkName;
    boolean didAddTreePLE = setTreePLE(aTreePLE);
    if (!didAddTreePLE)
    {
      throw new RuntimeException("Unable to create park due to treePLE");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setParkCode(int aParkCode)
  {
    boolean wasSet = false;
    parkCode = aParkCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setParkName(String aParkName)
  {
    boolean wasSet = false;
    parkName = aParkName;
    wasSet = true;
    return wasSet;
  }

  public int getParkCode()
  {
    return parkCode;
  }

  public String getParkName()
  {
    return parkName;
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
      existingTreePLE.removePark(this);
    }
    treePLE.addPark(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    TreePLE placeholderTreePLE = treePLE;
    this.treePLE = null;
    if(placeholderTreePLE != null)
    {
      placeholderTreePLE.removePark(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "parkCode" + ":" + getParkCode()+ "," +
            "parkName" + ":" + getParkName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "treePLE = "+(getTreePLE()!=null?Integer.toHexString(System.identityHashCode(getTreePLE())):"null");
  }
}