/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse321.TMS.model;

// line 32 "../../../../../TreePLE.ump"
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
    boolean didAddTree = setTree(aTree);
    if (!didAddTree)
    {
      throw new RuntimeException("Unable to create treeLocation due to tree");
    }
    boolean didAddLocationType = setLocationType(aLocationType);
    if (!didAddLocationType)
    {
      throw new RuntimeException("Unable to create treeLocation due to locationType");
    }
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

  public boolean setTree(Tree aNewTree)
  {
    boolean wasSet = false;
    if (!canSetTree) { return false; }
    if (aNewTree == null)
    {
      //Unable to setTree to null, as treeLocation must always be associated to a tree
      return wasSet;
    }
    
    TreeLocation existingTreeLocation = aNewTree.getTreeLocation();
    if (existingTreeLocation != null && !equals(existingTreeLocation))
    {
      //Unable to setTree, the current tree already has a treeLocation, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Tree anOldTree = tree;
    tree = aNewTree;
    tree.setTreeLocation(this);

    if (anOldTree != null)
    {
      anOldTree.setTreeLocation(null);
    }
    wasSet = true;
    return wasSet;
  }

  public boolean setLocationType(LocationType aLocationType)
  {
    boolean wasSet = false;
    if (!canSetLocationType) { return false; }
    if (aLocationType == null)
    {
      return wasSet;
    }

    LocationType existingLocationType = locationType;
    locationType = aLocationType;
    if (existingLocationType != null && !existingLocationType.equals(aLocationType))
    {
      existingLocationType.removeTreeLocation(this);
    }
    if (!locationType.addTreeLocation(this))
    {
      locationType = existingLocationType;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
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
      existingTree.setTreeLocation(null);
    }
    LocationType placeholderLocationType = locationType;
    this.locationType = null;
    if(placeholderLocationType != null)
    {
      placeholderLocationType.removeTreeLocation(this);
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