/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.sql.Date;

// line 11 "../../../../../TreePLE.ump"
public class Tree
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tree Attributes
  private int height;
  private int diameter;

  //Autounique Attributes
  private int id;

  //Tree Associations
  private TreeStatus treeStatus;
  private TreeLocation treeLocation;
  private Species species;
  private User local;
  private TreePLE treePLE;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tree(int aHeight, int aDiameter, Species aSpecies, User aLocal, TreePLE aTreePLE)
  {
    height = aHeight;
    diameter = aDiameter;
    id = nextId++;
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

  public int getId()
  {
    return id;
  }

  public TreeStatus getTreeStatus()
  {
    return treeStatus;
  }

  public boolean hasTreeStatus()
  {
    boolean has = treeStatus != null;
    return has;
  }

  public TreeLocation getTreeLocation()
  {
    return treeLocation;
  }

  public boolean hasTreeLocation()
  {
    boolean has = treeLocation != null;
    return has;
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

  public boolean setTreeStatus(TreeStatus aNewTreeStatus)
  {
    boolean wasSet = false;
    if (treeStatus != null && !treeStatus.equals(aNewTreeStatus) && equals(treeStatus.getTree()))
    {
      //Unable to setTreeStatus, as existing treeStatus would become an orphan
      return wasSet;
    }

    treeStatus = aNewTreeStatus;
    Tree anOldTree = aNewTreeStatus != null ? aNewTreeStatus.getTree() : null;

    if (!this.equals(anOldTree))
    {
      if (anOldTree != null)
      {
        anOldTree.treeStatus = null;
      }
      if (treeStatus != null)
      {
        treeStatus.setTree(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public boolean setTreeLocation(TreeLocation aNewTreeLocation)
  {
    boolean wasSet = false;
    if (treeLocation != null && !treeLocation.equals(aNewTreeLocation) && equals(treeLocation.getTree()))
    {
      //Unable to setTreeLocation, as existing treeLocation would become an orphan
      return wasSet;
    }

    treeLocation = aNewTreeLocation;
    Tree anOldTree = aNewTreeLocation != null ? aNewTreeLocation.getTree() : null;

    if (!this.equals(anOldTree))
    {
      if (anOldTree != null)
      {
        anOldTree.treeLocation = null;
      }
      if (treeLocation != null)
      {
        treeLocation.setTree(this);
      }
    }
    wasSet = true;
    return wasSet;
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
    TreeStatus existingTreeStatus = treeStatus;
    treeStatus = null;
    if (existingTreeStatus != null)
    {
      existingTreeStatus.delete();
      existingTreeStatus.setTree(null);
    }
    TreeLocation existingTreeLocation = treeLocation;
    treeLocation = null;
    if (existingTreeLocation != null )
    {
      existingTreeLocation.delete();
      existingTreeLocation.setTree(null);
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
            "id" + ":" + getId()+ "," +
            "height" + ":" + getHeight()+ "," +
            "diameter" + ":" + getDiameter()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "treeStatus = "+(getTreeStatus()!=null?Integer.toHexString(System.identityHashCode(getTreeStatus())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "treeLocation = "+(getTreeLocation()!=null?Integer.toHexString(System.identityHashCode(getTreeLocation())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "species = "+(getSpecies()!=null?Integer.toHexString(System.identityHashCode(getSpecies())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "local = "+(getLocal()!=null?Integer.toHexString(System.identityHashCode(getLocal())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "treePLE = "+(getTreePLE()!=null?Integer.toHexString(System.identityHashCode(getTreePLE())):"null");
  }
}