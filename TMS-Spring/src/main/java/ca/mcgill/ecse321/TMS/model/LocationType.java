/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.util.*;

// line 41 "../../../../../TreePLE.ump"
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
  private List<TreeLocation> treeLocations;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LocationType()
  {
    treeLocations = new ArrayList<TreeLocation>();
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

  public TreeLocation getTreeLocation(int index)
  {
    TreeLocation aTreeLocation = treeLocations.get(index);
    return aTreeLocation;
  }

  public List<TreeLocation> getTreeLocations()
  {
    List<TreeLocation> newTreeLocations = Collections.unmodifiableList(treeLocations);
    return newTreeLocations;
  }

  public int numberOfTreeLocations()
  {
    int number = treeLocations.size();
    return number;
  }

  public boolean hasTreeLocations()
  {
    boolean has = treeLocations.size() > 0;
    return has;
  }

  public int indexOfTreeLocation(TreeLocation aTreeLocation)
  {
    int index = treeLocations.indexOf(aTreeLocation);
    return index;
  }

  public static int minimumNumberOfTreeLocations()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TreeLocation addTreeLocation(int aX, int aY, String aDescription, Tree aTree)
  {
    return new TreeLocation(aX, aY, aDescription, aTree, this);
  }

  public boolean addTreeLocation(TreeLocation aTreeLocation)
  {
    boolean wasAdded = false;
    if (treeLocations.contains(aTreeLocation)) { return false; }
    LocationType existingLocationType = aTreeLocation.getLocationType();
    boolean isNewLocationType = existingLocationType != null && !this.equals(existingLocationType);
    if (isNewLocationType)
    {
      aTreeLocation.setLocationType(this);
    }
    else
    {
      treeLocations.add(aTreeLocation);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTreeLocation(TreeLocation aTreeLocation)
  {
    boolean wasRemoved = false;
    //Unable to remove aTreeLocation, as it must always have a locationType
    if (!this.equals(aTreeLocation.getLocationType()))
    {
      treeLocations.remove(aTreeLocation);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTreeLocationAt(TreeLocation aTreeLocation, int index)
  {  
    boolean wasAdded = false;
    if(addTreeLocation(aTreeLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTreeLocations()) { index = numberOfTreeLocations() - 1; }
      treeLocations.remove(aTreeLocation);
      treeLocations.add(index, aTreeLocation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTreeLocationAt(TreeLocation aTreeLocation, int index)
  {
    boolean wasAdded = false;
    if(treeLocations.contains(aTreeLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTreeLocations()) { index = numberOfTreeLocations() - 1; }
      treeLocations.remove(aTreeLocation);
      treeLocations.add(index, aTreeLocation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTreeLocationAt(aTreeLocation, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=treeLocations.size(); i > 0; i--)
    {
      TreeLocation aTreeLocation = treeLocations.get(i - 1);
      aTreeLocation.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "landUseType" + "=" + (getLandUseType() != null ? !getLandUseType().equals(this)  ? getLandUseType().toString().replaceAll("  ","    ") : "this" : "null");
  }
}