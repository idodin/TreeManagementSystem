/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.util.*;

// line 32 "../../../../../TreePLE.ump"
public class LandUse
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, LandUse> landusesByLandUseType = new HashMap<String, LandUse>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //LandUse Attributes
  private String landUseType;

  //LandUse Associations
  private List<Tree> trees;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetLandUseType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LandUse(String aLandUseType)
  {
    cachedHashCode = -1;
    canSetLandUseType = true;
    if (!setLandUseType(aLandUseType))
    {
      throw new RuntimeException("Cannot create due to duplicate landUseType");
    }
    trees = new ArrayList<Tree>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLandUseType(String aLandUseType)
  {
    boolean wasSet = false;
    if (!canSetLandUseType) { return false; }
    String anOldLandUseType = getLandUseType();
    if (hasWithLandUseType(aLandUseType)) {
      return wasSet;
    }
    landUseType = aLandUseType;
    wasSet = true;
    if (anOldLandUseType != null) {
      landusesByLandUseType.remove(anOldLandUseType);
    }
    landusesByLandUseType.put(aLandUseType, this);
    return wasSet;
  }

  public String getLandUseType()
  {
    return landUseType;
  }

  public static LandUse getWithLandUseType(String aLandUseType)
  {
    return landusesByLandUseType.get(aLandUseType);
  }

  public static boolean hasWithLandUseType(String aLandUseType)
  {
    return getWithLandUseType(aLandUseType) != null;
  }

  public Tree getTree(int index)
  {
    Tree aTree = trees.get(index);
    return aTree;
  }

  public List<Tree> getTrees()
  {
    List<Tree> newTrees = Collections.unmodifiableList(trees);
    return newTrees;
  }

  public int numberOfTrees()
  {
    int number = trees.size();
    return number;
  }

  public boolean hasTrees()
  {
    boolean has = trees.size() > 0;
    return has;
  }

  public int indexOfTree(Tree aTree)
  {
    int index = trees.indexOf(aTree);
    return index;
  }

  public static int minimumNumberOfTrees()
  {
    return 0;
  }

  public Tree addTree(int aLongitude, int aLatitude, int aHeight, int aDiameter, Status aStatus, Municipality aMunicipality, Species aSpecies, User aUsers, TreePLE aTreePLE)
  {
    return new Tree(aLongitude, aLatitude, aHeight, aDiameter, aStatus, this, aMunicipality, aSpecies, aUsers, aTreePLE);
  }

  public boolean addTree(Tree aTree)
  {
    boolean wasAdded = false;
    if (trees.contains(aTree)) { return false; }
    LandUse existingLanduse = aTree.getLanduse();
    boolean isNewLanduse = existingLanduse != null && !this.equals(existingLanduse);
    if (isNewLanduse)
    {
      aTree.setLanduse(this);
    }
    else
    {
      trees.add(aTree);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTree(Tree aTree)
  {
    boolean wasRemoved = false;
    //Unable to remove aTree, as it must always have a landuse
    if (!this.equals(aTree.getLanduse()))
    {
      trees.remove(aTree);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTreeAt(Tree aTree, int index)
  {  
    boolean wasAdded = false;
    if(addTree(aTree))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTrees()) { index = numberOfTrees() - 1; }
      trees.remove(aTree);
      trees.add(index, aTree);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTreeAt(Tree aTree, int index)
  {
    boolean wasAdded = false;
    if(trees.contains(aTree))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTrees()) { index = numberOfTrees() - 1; }
      trees.remove(aTree);
      trees.add(index, aTree);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTreeAt(aTree, index);
    }
    return wasAdded;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    LandUse compareTo = (LandUse)obj;
  
    if (getLandUseType() == null && compareTo.getLandUseType() != null)
    {
      return false;
    }
    else if (getLandUseType() != null && !getLandUseType().equals(compareTo.getLandUseType()))
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
    if (getLandUseType() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getLandUseType().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetLandUseType = false;
    return cachedHashCode;
  }

  public void delete()
  {
    landusesByLandUseType.remove(getLandUseType());
    for(int i=trees.size(); i > 0; i--)
    {
      Tree aTree = trees.get(i - 1);
      aTree.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "landUseType" + ":" + getLandUseType()+ "]";
  }
}