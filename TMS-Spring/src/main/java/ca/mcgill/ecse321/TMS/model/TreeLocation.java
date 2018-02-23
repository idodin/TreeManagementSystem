/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.sql.Date;

// line 31 "../../../../../TreePLE.ump"
public class TreeLocation
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TreeLocation Attributes
  private int x;
  private int y;
  private String description;

  //TreeLocation Associations
  private Tree tree;
  private LocationType locationType;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetTree;
  private boolean canSetLocationType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreeLocation(int aX, int aY, String aDescription, Tree aTree, LocationType aLocationType)
  {
    cachedHashCode = -1;
    canSetTree = true;
    canSetLocationType = true;
    x = aX;
    y = aY;
    description = aDescription;
    if (aTree == null || aTree.getTreeLocation() != null)
    {
      throw new RuntimeException("Unable to create TreeLocation due to aTree");
    }
    tree = aTree;
    if (aLocationType == null || aLocationType.getTreeLocation() != null)
    {
      throw new RuntimeException("Unable to create TreeLocation due to aLocationType");
    }
    locationType = aLocationType;
  }

  public TreeLocation(int aX, int aY, String aDescription, int aIdForTree, int aHeightForTree, int aDiameterForTree, Date aDatePlantedForTree, Date aDateAddedForTree, TreeStatus aTreeStatusForTree, Species aSpeciesForTree, User aLocalForTree, Municipality aMunicipalityForTree, TreePLE aTreePLEForTree)
  {
    x = aX;
    y = aY;
    description = aDescription;
    tree = new Tree(aIdForTree, aHeightForTree, aDiameterForTree, aDatePlantedForTree, aDateAddedForTree, aTreeStatusForTree, aSpeciesForTree, aLocalForTree, aMunicipalityForTree, aTreePLEForTree, this);
    locationType = new LocationType(this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setX(int aX)
  {
    boolean wasSet = false;
    x = aX;
    wasSet = true;
    return wasSet;
  }

  public boolean setY(int aY)
  {
    boolean wasSet = false;
    y = aY;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public String getDescription()
  {
    return description;
  }

  public Tree getTree()
  {
    return tree;
  }

  public LocationType getLocationType()
  {
    return locationType;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    TreeLocation compareTo = (TreeLocation)obj;
  
    if (getTree() == null && compareTo.getTree() != null)
    {
      return false;
    }
    else if (getTree() != null && !getTree().equals(compareTo.getTree()))
    {
      return false;
    }

    if (getLocationType() == null && compareTo.getLocationType() != null)
    {
      return false;
    }
    else if (getLocationType() != null && !getLocationType().equals(compareTo.getLocationType()))
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    if (cachedHashCode != -1)
    {
      return cachedHashCode;
    }
    cachedHashCode = 17;
    if (getTree() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getTree().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (getLocationType() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getLocationType().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetTree = false;
    canSetLocationType = false;
    return cachedHashCode;
  }

  public void delete()
  {
    Tree existingTree = tree;
    tree = null;
    if (existingTree != null)
    {
      existingTree.delete();
    }
    LocationType existingLocationType = locationType;
    locationType = null;
    if (existingLocationType != null)
    {
      existingLocationType.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "x" + ":" + getX()+ "," +
            "y" + ":" + getY()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "tree = "+(getTree()!=null?Integer.toHexString(System.identityHashCode(getTree())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "locationType = "+(getLocationType()!=null?Integer.toHexString(System.identityHashCode(getLocationType())):"null");
  }
}