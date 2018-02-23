/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TMS.model;

// line 40 "../../../../../TreePLE.ump"
public class LocationType
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum LandUseType { Residential, Institutional, Municipal }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //LocationType Attributes
  private LandUseType landUseType;

  //LocationType Associations
  private TreeLocation treeLocation;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LocationType(TreeLocation aTreeLocation)
  {
    if (aTreeLocation == null || aTreeLocation.getLocationType() != null)
    {
      throw new RuntimeException("Unable to create LocationType due to aTreeLocation");
    }
    treeLocation = aTreeLocation;
  }

  public LocationType(int aXForTreeLocation, int aYForTreeLocation, String aDescriptionForTreeLocation, Tree aTreeForTreeLocation)
  {
    treeLocation = new TreeLocation(aXForTreeLocation, aYForTreeLocation, aDescriptionForTreeLocation, aTreeForTreeLocation, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLandUseType(LandUseType aLandUseType)
  {
    boolean wasSet = false;
    landUseType = aLandUseType;
    wasSet = true;
    return wasSet;
  }

  public LandUseType getLandUseType()
  {
    return landUseType;
  }

  public TreeLocation getTreeLocation()
  {
    return treeLocation;
  }

  public void delete()
  {
    TreeLocation existingTreeLocation = treeLocation;
    treeLocation = null;
    if (existingTreeLocation != null)
    {
      existingTreeLocation.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "landUseType" + "=" + (getLandUseType() != null ? !getLandUseType().equals(this)  ? getLandUseType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "treeLocation = "+(getTreeLocation()!=null?Integer.toHexString(System.identityHashCode(getTreeLocation())):"null");
  }
}