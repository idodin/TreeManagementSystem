/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.util.*;
import java.sql.Date;

// line 11 "../../../../../TreePLE.ump"
public class Tree
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tree Attributes
  private int height;
  private int diameter;

  //Tree Associations
  private List<TreeStatus> treeStatus;
  private TreeLocation treeLocation;
  private Species species;
  private User local;
  private TreePLE treePLE;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tree(int aHeight, int aDiameter, TreeLocation aTreeLocation, Species aSpecies, User aLocal, TreePLE aTreePLE)
  {
    height = aHeight;
    diameter = aDiameter;
    treeStatus = new ArrayList<TreeStatus>();
    if (aTreeLocation == null || aTreeLocation.getTree() != null)
    {
      throw new RuntimeException("Unable to create Tree due to aTreeLocation");
    }
    treeLocation = aTreeLocation;
    boolean didAddSpecies = setSpecies(aSpecies);
    if (!didAddSpecies)
    {
      throw new RuntimeException("Unable to create tree due to species");
    }
    boolean didAddLocal = setLocal(aLocal);
    if (!didAddLocal)
    {
      throw new RuntimeException("Unable to create tree due to local");
    }
    boolean didAddTreePLE = setTreePLE(aTreePLE);
    if (!didAddTreePLE)
    {
      throw new RuntimeException("Unable to create tree due to treePLE");
    }
  }

  public Tree(int aHeight, int aDiameter, int aLongitudeForTreeLocation, int aLatitudeForTreeLocation, Municipality aMunicipalityForTreeLocation, Species aSpecies, User aLocal, TreePLE aTreePLE)
  {
    height = aHeight;
    diameter = aDiameter;
    treeStatus = new ArrayList<TreeStatus>();
    treeLocation = new TreeLocation(aLongitudeForTreeLocation, aLatitudeForTreeLocation, aMunicipalityForTreeLocation, this);
    boolean didAddSpecies = setSpecies(aSpecies);
    if (!didAddSpecies)
    {
      throw new RuntimeException("Unable to create tree due to species");
    }
    boolean didAddLocal = setLocal(aLocal);
    if (!didAddLocal)
    {
      throw new RuntimeException("Unable to create tree due to local");
    }
    boolean didAddTreePLE = setTreePLE(aTreePLE);
    if (!didAddTreePLE)
    {
      throw new RuntimeException("Unable to create tree due to treePLE");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setHeight(int aHeight)
  {
    boolean wasSet = false;
    height = aHeight;
    wasSet = true;
    return wasSet;
  }

  public boolean setDiameter(int aDiameter)
  {
    boolean wasSet = false;
    diameter = aDiameter;
    wasSet = true;
    return wasSet;
  }

  public int getHeight()
  {
    return height;
  }

  public int getDiameter()
  {
    return diameter;
  }

  public TreeStatus getTreeStatus(int index)
  {
    TreeStatus aTreeStatus = treeStatus.get(index);
    return aTreeStatus;
  }

  public List<TreeStatus> getTreeStatus()
  {
    List<TreeStatus> newTreeStatus = Collections.unmodifiableList(treeStatus);
    return newTreeStatus;
  }

  public int numberOfTreeStatus()
  {
    int number = treeStatus.size();
    return number;
  }

  public boolean hasTreeStatus()
  {
    boolean has = treeStatus.size() > 0;
    return has;
  }

  public int indexOfTreeStatus(TreeStatus aTreeStatus)
  {
    int index = treeStatus.indexOf(aTreeStatus);
    return index;
  }

  public TreeLocation getTreeLocation()
  {
    return treeLocation;
  }

  public Species getSpecies()
  {
    return species;
  }

  public User getLocal()
  {
    return local;
  }

  public TreePLE getTreePLE()
  {
    return treePLE;
  }

  public static int minimumNumberOfTreeStatus()
  {
    return 0;
  }

  public TreeStatus addTreeStatus(Date aDateOfBirth)
  {
    return new TreeStatus(aDateOfBirth, this);
  }

  public boolean addTreeStatus(TreeStatus aTreeStatus)
  {
    boolean wasAdded = false;
    if (treeStatus.contains(aTreeStatus)) { return false; }
    Tree existingTree = aTreeStatus.getTree();
    boolean isNewTree = existingTree != null && !this.equals(existingTree);
    if (isNewTree)
    {
      aTreeStatus.setTree(this);
    }
    else
    {
      treeStatus.add(aTreeStatus);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTreeStatus(TreeStatus aTreeStatus)
  {
    boolean wasRemoved = false;
    //Unable to remove aTreeStatus, as it must always have a tree
    if (!this.equals(aTreeStatus.getTree()))
    {
      treeStatus.remove(aTreeStatus);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTreeStatusAt(TreeStatus aTreeStatus, int index)
  {  
    boolean wasAdded = false;
    if(addTreeStatus(aTreeStatus))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTreeStatus()) { index = numberOfTreeStatus() - 1; }
      treeStatus.remove(aTreeStatus);
      treeStatus.add(index, aTreeStatus);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTreeStatusAt(TreeStatus aTreeStatus, int index)
  {
    boolean wasAdded = false;
    if(treeStatus.contains(aTreeStatus))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTreeStatus()) { index = numberOfTreeStatus() - 1; }
      treeStatus.remove(aTreeStatus);
      treeStatus.add(index, aTreeStatus);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTreeStatusAt(aTreeStatus, index);
    }
    return wasAdded;
  }

  public boolean setSpecies(Species aSpecies)
  {
    boolean wasSet = false;
    if (aSpecies == null)
    {
      return wasSet;
    }

    Species existingSpecies = species;
    species = aSpecies;
    if (existingSpecies != null && !existingSpecies.equals(aSpecies))
    {
      existingSpecies.removeTree(this);
    }
    species.addTree(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setLocal(User aLocal)
  {
    boolean wasSet = false;
    if (aLocal == null)
    {
      return wasSet;
    }

    User existingLocal = local;
    local = aLocal;
    if (existingLocal != null && !existingLocal.equals(aLocal))
    {
      existingLocal.removeTree(this);
    }
    local.addTree(this);
    wasSet = true;
    return wasSet;
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
      existingTreePLE.removeTree(this);
    }
    treePLE.addTree(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (treeStatus.size() > 0)
    {
      TreeStatus aTreeStatus = treeStatus.get(treeStatus.size() - 1);
      aTreeStatus.delete();
      treeStatus.remove(aTreeStatus);
    }
    
    TreeLocation existingTreeLocation = treeLocation;
    treeLocation = null;
    if (existingTreeLocation != null)
    {
      existingTreeLocation.delete();
    }
    Species placeholderSpecies = species;
    this.species = null;
    placeholderSpecies.removeTree(this);
    User placeholderLocal = local;
    this.local = null;
    placeholderLocal.removeTree(this);
    TreePLE placeholderTreePLE = treePLE;
    this.treePLE = null;
    placeholderTreePLE.removeTree(this);
  }


  public String toString()
  {
    return super.toString() + "["+
            "height" + ":" + getHeight()+ "," +
            "diameter" + ":" + getDiameter()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "treeLocation = "+(getTreeLocation()!=null?Integer.toHexString(System.identityHashCode(getTreeLocation())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "species = "+(getSpecies()!=null?Integer.toHexString(System.identityHashCode(getSpecies())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "local = "+(getLocal()!=null?Integer.toHexString(System.identityHashCode(getLocal())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "treePLE = "+(getTreePLE()!=null?Integer.toHexString(System.identityHashCode(getTreePLE())):"null");
  }
}