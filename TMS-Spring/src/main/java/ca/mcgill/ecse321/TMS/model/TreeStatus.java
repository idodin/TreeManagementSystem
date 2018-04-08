/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.util.*;
import java.sql.Date;

// line 39 "../../../../../TreePLE.ump"
public class TreeStatus
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Status { Healthy, Diseased, Cut, ToBeCut }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TreeStatus Attributes
  private Status status;

  //TreeStatus Associations
  private TreePLE treePLE;
  private List<Tree> trees;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreeStatus(TreePLE aTreePLE)
  {
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

  public boolean setStatus(Status aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public Status getStatus()
  {
    return status;
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
  /* Code from template association_AddManyToOne */
  public Tree addTree(double aHeight, double aDiameter, Date aDatePlanted, Date aDateAdded, Species aSpecies, User aLocal, Municipality aMunicipality, TreePLE aTreePLE)
  {
    return new Tree(aHeight, aDiameter, aDatePlanted, aDateAdded, this, aSpecies, aLocal, aMunicipality, aTreePLE);
  }

  public boolean addTree(Tree aTree)
  {
    boolean wasAdded = false;
    if (trees.contains(aTree)) { return false; }
    TreeStatus existingTreeStatus = aTree.getTreeStatus();
    boolean isNewTreeStatus = existingTreeStatus != null && !this.equals(existingTreeStatus);
    if (isNewTreeStatus)
    {
      aTree.setTreeStatus(this);
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
    //Unable to remove aTree, as it must always have a treeStatus
    if (!this.equals(aTree.getTreeStatus()))
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
    if(placeholderTreePLE != null)
    {
      placeholderTreePLE.removeStatus(this);
    }
    for(int i=trees.size(); i > 0; i--)
    {
      Tree aTree = trees.get(i - 1);
      aTree.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "treePLE = "+(getTreePLE()!=null?Integer.toHexString(System.identityHashCode(getTreePLE())):"null");
  }
}