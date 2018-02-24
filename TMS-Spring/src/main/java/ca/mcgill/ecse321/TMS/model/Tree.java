/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.sql.Date;

// line 13 "../../../../../TreePLE.ump"
public class Tree
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tree Attributes
  private int id;
  private int height;
  private int diameter;
  private Date datePlanted;
  private Date dateAdded;

  //Tree Associations
  private TreeStatus treeStatus;
  private Species species;
  private User local;
  private Municipality municipality;
  private TreePLE treePLE;
  private TreeLocation treeLocation;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tree(int aId, int aHeight, int aDiameter, Date aDatePlanted, Date aDateAdded, TreeStatus aTreeStatus, Species aSpecies, User aLocal, Municipality aMunicipality, TreePLE aTreePLE)
  {
    id = aId;
    height = aHeight;
    diameter = aDiameter;
    datePlanted = aDatePlanted;
    dateAdded = aDateAdded;
    boolean didAddTreeStatus = setTreeStatus(aTreeStatus);
    if (!didAddTreeStatus)
    {
      throw new RuntimeException("Unable to create tree due to treeStatus");
    }
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
    boolean didAddMunicipality = setMunicipality(aMunicipality);
    if (!didAddMunicipality)
    {
      throw new RuntimeException("Unable to create tree due to municipality");
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

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

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

  public boolean setDatePlanted(Date aDatePlanted)
  {
    boolean wasSet = false;
    datePlanted = aDatePlanted;
    wasSet = true;
    return wasSet;
  }

  public boolean setDateAdded(Date aDateAdded)
  {
    boolean wasSet = false;
    dateAdded = aDateAdded;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public int getHeight()
  {
    return height;
  }

  public int getDiameter()
  {
    return diameter;
  }

  public Date getDatePlanted()
  {
    return datePlanted;
  }

  public Date getDateAdded()
  {
    return dateAdded;
  }

  public TreeStatus getTreeStatus()
  {
    return treeStatus;
  }

  public Species getSpecies()
  {
    return species;
  }

  public User getLocal()
  {
    return local;
  }

  public Municipality getMunicipality()
  {
    return municipality;
  }

  public TreePLE getTreePLE()
  {
    return treePLE;
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

  public boolean setTreeStatus(TreeStatus aTreeStatus)
  {
    boolean wasSet = false;
    if (aTreeStatus == null)
    {
      return wasSet;
    }

    TreeStatus existingTreeStatus = treeStatus;
    treeStatus = aTreeStatus;
    if (existingTreeStatus != null && !existingTreeStatus.equals(aTreeStatus))
    {
      existingTreeStatus.removeTree(this);
    }
    treeStatus.addTree(this);
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

  public boolean setMunicipality(Municipality aMunicipality)
  {
    boolean wasSet = false;
    if (aMunicipality == null)
    {
      return wasSet;
    }

    Municipality existingMunicipality = municipality;
    municipality = aMunicipality;
    if (existingMunicipality != null && !existingMunicipality.equals(aMunicipality))
    {
      existingMunicipality.removeTree(this);
    }
    municipality.addTree(this);
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

  public void delete()
  {
    TreeStatus placeholderTreeStatus = treeStatus;
    this.treeStatus = null;
    placeholderTreeStatus.removeTree(this);
    Species placeholderSpecies = species;
    this.species = null;
    placeholderSpecies.removeTree(this);
    User placeholderLocal = local;
    this.local = null;
    placeholderLocal.removeTree(this);
    Municipality placeholderMunicipality = municipality;
    this.municipality = null;
    placeholderMunicipality.removeTree(this);
    TreePLE placeholderTreePLE = treePLE;
    this.treePLE = null;
    placeholderTreePLE.removeTree(this);
    TreeLocation existingTreeLocation = treeLocation;
    treeLocation = null;
    if (existingTreeLocation != null)
    {
      existingTreeLocation.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "height" + ":" + getHeight()+ "," +
            "diameter" + ":" + getDiameter()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "datePlanted" + "=" + (getDatePlanted() != null ? !getDatePlanted().equals(this)  ? getDatePlanted().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "dateAdded" + "=" + (getDateAdded() != null ? !getDateAdded().equals(this)  ? getDateAdded().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "treeStatus = "+(getTreeStatus()!=null?Integer.toHexString(System.identityHashCode(getTreeStatus())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "species = "+(getSpecies()!=null?Integer.toHexString(System.identityHashCode(getSpecies())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "local = "+(getLocal()!=null?Integer.toHexString(System.identityHashCode(getLocal())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "municipality = "+(getMunicipality()!=null?Integer.toHexString(System.identityHashCode(getMunicipality())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "treePLE = "+(getTreePLE()!=null?Integer.toHexString(System.identityHashCode(getTreePLE())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "treeLocation = "+(getTreeLocation()!=null?Integer.toHexString(System.identityHashCode(getTreeLocation())):"null");
  }
}