/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.util.*;

// line 36 "../../../../../TreePLE.ump"
public class Municipality
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Municipality> municipalitysByName = new HashMap<String, Municipality>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Municipality Attributes
  private String name;
  private int bioDiversityIndex;

  //Municipality Associations
  private TreePLE treePLE;
  private List<TreeLocation> treeLocations;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Municipality(String aName, int aBioDiversityIndex, TreePLE aTreePLE)
  {
    bioDiversityIndex = aBioDiversityIndex;
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name");
    }
    boolean didAddTreePLE = setTreePLE(aTreePLE);
    if (!didAddTreePLE)
    {
      throw new RuntimeException("Unable to create municipality due to treePLE");
    }
    treeLocations = new ArrayList<TreeLocation>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    String anOldName = getName();
    if (hasWithName(aName)) {
      return wasSet;
    }
    name = aName;
    wasSet = true;
    if (anOldName != null) {
      municipalitysByName.remove(anOldName);
    }
    municipalitysByName.put(aName, this);
    return wasSet;
  }

  public boolean setBioDiversityIndex(int aBioDiversityIndex)
  {
    boolean wasSet = false;
    bioDiversityIndex = aBioDiversityIndex;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public static Municipality getWithName(String aName)
  {
    return municipalitysByName.get(aName);
  }

  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
  }

  public int getBioDiversityIndex()
  {
    return bioDiversityIndex;
  }

  public TreePLE getTreePLE()
  {
    return treePLE;
  }

  public TreeLocation getTreeLocation(int index)
  {
    TreeLocation aTreeLocation = treeLocations.get(index);
    return aTreeLocation;
  }

  public List<TreeLocation> getTreeLocations()
  {
    List<TreeLocation> newTreeLocations = Collections.unmodifiableList(treeLocations);
    return newTreeLocations;
  }

  public int numberOfTreeLocations()
  {
    int number = treeLocations.size();
    return number;
  }

  public boolean hasTreeLocations()
  {
    boolean has = treeLocations.size() > 0;
    return has;
  }

  public int indexOfTreeLocation(TreeLocation aTreeLocation)
  {
    int index = treeLocations.indexOf(aTreeLocation);
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
      existingTreePLE.removeMunicipality(this);
    }
    treePLE.addMunicipality(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfTreeLocations()
  {
    return 0;
  }

  public TreeLocation addTreeLocation(int aLongitude, int aLatitude, Tree aTree)
  {
    return new TreeLocation(aLongitude, aLatitude, this, aTree);
  }

  public boolean addTreeLocation(TreeLocation aTreeLocation)
  {
    boolean wasAdded = false;
    if (treeLocations.contains(aTreeLocation)) { return false; }
    Municipality existingMunicipality = aTreeLocation.getMunicipality();
    boolean isNewMunicipality = existingMunicipality != null && !this.equals(existingMunicipality);
    if (isNewMunicipality)
    {
      aTreeLocation.setMunicipality(this);
    }
    else
    {
      treeLocations.add(aTreeLocation);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTreeLocation(TreeLocation aTreeLocation)
  {
    boolean wasRemoved = false;
    //Unable to remove aTreeLocation, as it must always have a municipality
    if (!this.equals(aTreeLocation.getMunicipality()))
    {
      treeLocations.remove(aTreeLocation);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTreeLocationAt(TreeLocation aTreeLocation, int index)
  {  
    boolean wasAdded = false;
    if(addTreeLocation(aTreeLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTreeLocations()) { index = numberOfTreeLocations() - 1; }
      treeLocations.remove(aTreeLocation);
      treeLocations.add(index, aTreeLocation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTreeLocationAt(TreeLocation aTreeLocation, int index)
  {
    boolean wasAdded = false;
    if(treeLocations.contains(aTreeLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTreeLocations()) { index = numberOfTreeLocations() - 1; }
      treeLocations.remove(aTreeLocation);
      treeLocations.add(index, aTreeLocation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTreeLocationAt(aTreeLocation, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    municipalitysByName.remove(getName());
    TreePLE placeholderTreePLE = treePLE;
    this.treePLE = null;
    placeholderTreePLE.removeMunicipality(this);
    for(int i=treeLocations.size(); i > 0; i--)
    {
      TreeLocation aTreeLocation = treeLocations.get(i - 1);
      aTreeLocation.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "bioDiversityIndex" + ":" + getBioDiversityIndex()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "treePLE = "+(getTreePLE()!=null?Integer.toHexString(System.identityHashCode(getTreePLE())):"null");
  }
}