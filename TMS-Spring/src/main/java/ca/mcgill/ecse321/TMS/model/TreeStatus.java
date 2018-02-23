/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.sql.Date;

// line 21 "../../../../../TreePLE.ump"
public class TreeStatus
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Status { Healthy, Diseased, ToBeCut, Cut }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TreeStatus Attributes
  private Status status;
  private Date dateOfBirth;
  private Date dateOfDeath;

  //TreeStatus Associations
  private Tree tree;

  //Helper Variables
  private boolean canSetDateOfDeath;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreeStatus(Date aDateOfBirth, Tree aTree)
  {
    dateOfBirth = aDateOfBirth;
    canSetDateOfDeath = true;
    boolean didAddTree = setTree(aTree);
    if (!didAddTree)
    {
      throw new RuntimeException("Unable to create treeStatus due to tree");
    }
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

  public boolean setDateOfDeath(Date aDateOfDeath)
  {
    boolean wasSet = false;
    if (!canSetDateOfDeath) { return false; }
    canSetDateOfDeath = false;
    dateOfDeath = aDateOfDeath;
    wasSet = true;
    return wasSet;
  }

  public Status getStatus()
  {
    return status;
  }

  public Date getDateOfBirth()
  {
    return dateOfBirth;
  }

  public Date getDateOfDeath()
  {
    return dateOfDeath;
  }

  public Tree getTree()
  {
    return tree;
  }

  public boolean setTree(Tree aNewTree)
  {
    boolean wasSet = false;
    if (aNewTree == null)
    {
      //Unable to setTree to null, as treeStatus must always be associated to a tree
      return wasSet;
    }
    
    TreeStatus existingTreeStatus = aNewTree.getTreeStatus();
    if (existingTreeStatus != null && !equals(existingTreeStatus))
    {
      //Unable to setTree, the current tree already has a treeStatus, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Tree anOldTree = tree;
    tree = aNewTree;
    tree.setTreeStatus(this);

    if (anOldTree != null)
    {
      anOldTree.setTreeStatus(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Tree existingTree = tree;
    tree = null;
    if (existingTree != null)
    {
      existingTree.setTreeStatus(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "dateOfBirth" + "=" + (getDateOfBirth() != null ? !getDateOfBirth().equals(this)  ? getDateOfBirth().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "dateOfDeath" + "=" + (getDateOfDeath() != null ? !getDateOfDeath().equals(this)  ? getDateOfDeath().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "tree = "+(getTree()!=null?Integer.toHexString(System.identityHashCode(getTree())):"null");
  }
}