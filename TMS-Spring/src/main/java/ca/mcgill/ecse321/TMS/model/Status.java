/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.util.*;

// line 25 "../../../../../TreePLE.ump"
public class Status
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TreeStatus { Healthy, Diseased, ToBeCut, Cut }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Status Attributes
  private TreeStatus treeStatus;
  private String dateOfBirth;
  private String dateOfDeath;

  //Status Associations
  private TreePLE treePLE;
  private List<Tree> trees;

  //Helper Variables
  private boolean canSetDateOfDeath;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Status(TreeStatus aTreeStatus, String aDateOfBirth, TreePLE aTreePLE)
  {
    treeStatus = aTreeStatus;
    dateOfBirth = aDateOfBirth;
    canSetDateOfDeath = true;
    boolean didAddTreePLE = setTreePLE(aTreePLE);
    if (!didAddTreePLE)
    {
      throw new RuntimeException("Unable to create status due to treePLE");
    }
    trees = new ArrayList<Tree>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTreeStatus(TreeStatus aTreeStatus)
  {
    boolean wasSet = false;
    treeStatus = aTreeStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setDateOfDeath(String aDateOfDeath)
  {
    boolean wasSet = false;
    if (!canSetDateOfDeath) { return false; }
    canSetDateOfDeath = false;
    dateOfDeath = aDateOfDeath;
    wasSet = true;
    return wasSet;
  }

  public TreeStatus getTreeStatus()
  {
    return treeStatus;
  }

  public String getDateOfBirth()
  {
    return dateOfBirth;
  }

  public String getDateOfDeath()
  {
    return dateOfDeath;
  }

  public TreePLE getTreePLE()
  {
    return treePLE;
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
      existingTreePLE.removeStatus(this);
    }
    treePLE.addStatus(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfTrees()
  {
    return 0;
  }

  public Tree addTree(int aLongitude, int aLatitude, int aHeight, int aDiameter, LandUse aLanduse, Municipality aMunicipality, Species aSpecies, User aUsers, TreePLE aTreePLE)
  {
    return new Tree(aLongitude, aLatitude, aHeight, aDiameter, this, aLanduse, aMunicipality, aSpecies, aUsers, aTreePLE);
  }

  public boolean addTree(Tree aTree)
  {
    boolean wasAdded = false;
    if (trees.contains(aTree)) { return false; }
    Status existingStatus = aTree.getStatus();
    boolean isNewStatus = existingStatus != null && !this.equals(existingStatus);
    if (isNewStatus)
    {
      aTree.setStatus(this);
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
    //Unable to remove aTree, as it must always have a status
    if (!this.equals(aTree.getStatus()))
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

  public void delete()
  {
    TreePLE placeholderTreePLE = treePLE;
    this.treePLE = null;
    placeholderTreePLE.removeStatus(this);
    for(int i=trees.size(); i > 0; i--)
    {
      Tree aTree = trees.get(i - 1);
      aTree.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "dateOfBirth" + ":" + getDateOfBirth()+ "," +
            "dateOfDeath" + ":" + getDateOfDeath()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "treeStatus" + "=" + (getTreeStatus() != null ? !getTreeStatus().equals(this)  ? getTreeStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "treePLE = "+(getTreePLE()!=null?Integer.toHexString(System.identityHashCode(getTreePLE())):"null");
  }
}