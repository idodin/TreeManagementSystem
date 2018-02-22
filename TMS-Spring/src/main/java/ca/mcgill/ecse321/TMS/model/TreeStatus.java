/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.sql.Date;

// line 20 "../../../../../TreePLE.ump"
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

  public boolean setTree(Tree aTree)
  {
    boolean wasSet = false;
    if (aTree == null)
    {
      return wasSet;
    }

    Tree existingTree = tree;
    tree = aTree;
    if (existingTree != null && !existingTree.equals(aTree))
    {
      existingTree.removeTreeStatus(this);
    }
    tree.addTreeStatus(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Tree placeholderTree = tree;
    this.tree = null;
    placeholderTree.removeTreeStatus(this);
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