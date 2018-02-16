/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.util.*;

// line 12 "../../../../../TreePLE.ump"
public class Tree
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<int, Tree> treesByLongitude = new HashMap<int, Tree>();
  private static Map<int, Tree> treesByLatitude = new HashMap<int, Tree>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tree Attributes
  private int longitude;
  private int latitude;
  private int height;
  private int diameter;

  //Tree Associations
  private Status status;
  private LandUse landuse;
  private Municipality municipality;
  private Species species;
  private User users;
  private TreePLE treePLE;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetLongitude;
  private boolean canSetLatitude;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tree(int aLongitude, int aLatitude, int aHeight, int aDiameter, Status aStatus, LandUse aLanduse, Municipality aMunicipality, Species aSpecies, User aUsers, TreePLE aTreePLE)
  {
    cachedHashCode = -1;
    canSetLongitude = true;
    canSetLatitude = true;
    height = aHeight;
    diameter = aDiameter;
    if (!setLongitude(aLongitude))
    {
      throw new RuntimeException("Cannot create due to duplicate longitude");
    }
    if (!setLatitude(aLatitude))
    {
      throw new RuntimeException("Cannot create due to duplicate latitude");
    }
    boolean didAddStatus = setStatus(aStatus);
    if (!didAddStatus)
    {
      throw new RuntimeException("Unable to create tree due to status");
    }
    boolean didAddLanduse = setLanduse(aLanduse);
    if (!didAddLanduse)
    {
      throw new RuntimeException("Unable to create tree due to landuse");
    }
    boolean didAddMunicipality = setMunicipality(aMunicipality);
    if (!didAddMunicipality)
    {
      throw new RuntimeException("Unable to create tree due to municipality");
    }
    boolean didAddSpecies = setSpecies(aSpecies);
    if (!didAddSpecies)
    {
      throw new RuntimeException("Unable to create tree due to species");
    }
    boolean didAddUsers = setUsers(aUsers);
    if (!didAddUsers)
    {
      throw new RuntimeException("Unable to create tree due to users");
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

  public boolean setLongitude(int aLongitude)
  {
    boolean wasSet = false;
    if (!canSetLongitude) { return false; }
    int anOldLongitude = getLongitude();
    if (hasWithLongitude(aLongitude)) {
      return wasSet;
    }
    longitude = aLongitude;
    wasSet = true;
    if (anOldLongitude != null) {
      treesByLongitude.remove(anOldLongitude);
    }
    treesByLongitude.put(aLongitude, this);
    return wasSet;
  }

  public boolean setLatitude(int aLatitude)
  {
    boolean wasSet = false;
    if (!canSetLatitude) { return false; }
    int anOldLatitude = getLatitude();
    if (hasWithLatitude(aLatitude)) {
      return wasSet;
    }
    latitude = aLatitude;
    wasSet = true;
    if (anOldLatitude != null) {
      treesByLatitude.remove(anOldLatitude);
    }
    treesByLatitude.put(aLatitude, this);
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

  public int getLongitude()
  {
    return longitude;
  }

  public static Tree getWithLongitude(int aLongitude)
  {
    return treesByLongitude.get(aLongitude);
  }

  public static boolean hasWithLongitude(int aLongitude)
  {
    return getWithLongitude(aLongitude) != null;
  }

  public int getLatitude()
  {
    return latitude;
  }

  public static Tree getWithLatitude(int aLatitude)
  {
    return treesByLatitude.get(aLatitude);
  }

  public static boolean hasWithLatitude(int aLatitude)
  {
    return getWithLatitude(aLatitude) != null;
  }

  public int getHeight()
  {
    return height;
  }

  public int getDiameter()
  {
    return diameter;
  }

  public Status getStatus()
  {
    return status;
  }

  public LandUse getLanduse()
  {
    return landuse;
  }

  public Municipality getMunicipality()
  {
    return municipality;
  }

  public Species getSpecies()
  {
    return species;
  }

  public User getUsers()
  {
    return users;
  }

  public TreePLE getTreePLE()
  {
    return treePLE;
  }

  public boolean setStatus(Status aStatus)
  {
    boolean wasSet = false;
    if (aStatus == null)
    {
      return wasSet;
    }

    Status existingStatus = status;
    status = aStatus;
    if (existingStatus != null && !existingStatus.equals(aStatus))
    {
      existingStatus.removeTree(this);
    }
    status.addTree(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setLanduse(LandUse aLanduse)
  {
    boolean wasSet = false;
    if (aLanduse == null)
    {
      return wasSet;
    }

    LandUse existingLanduse = landuse;
    landuse = aLanduse;
    if (existingLanduse != null && !existingLanduse.equals(aLanduse))
    {
      existingLanduse.removeTree(this);
    }
    landuse.addTree(this);
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

  public boolean setUsers(User aUsers)
  {
    boolean wasSet = false;
    if (aUsers == null)
    {
      return wasSet;
    }

    User existingUsers = users;
    users = aUsers;
    if (existingUsers != null && !existingUsers.equals(aUsers))
    {
      existingUsers.removeTree(this);
    }
    users.addTree(this);
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

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    Tree compareTo = (Tree)obj;
  
    if (getLongitude() == null && compareTo.getLongitude() != null)
    {
      return false;
    }
    else if (getLongitude() != null && !getLongitude().equals(compareTo.getLongitude()))
    {
      return false;
    }

    if (getLatitude() == null && compareTo.getLatitude() != null)
    {
      return false;
    }
    else if (getLatitude() != null && !getLatitude().equals(compareTo.getLatitude()))
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
    if (getLongitude() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getLongitude().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    if (getLatitude() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getLatitude().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetLongitude = false;
    canSetLatitude = false;
    return cachedHashCode;
  }

  public void delete()
  {
    treesByLongitude.remove(getLongitude());
    treesByLatitude.remove(getLatitude());
    Status placeholderStatus = status;
    this.status = null;
    placeholderStatus.removeTree(this);
    LandUse placeholderLanduse = landuse;
    this.landuse = null;
    placeholderLanduse.removeTree(this);
    Municipality placeholderMunicipality = municipality;
    this.municipality = null;
    placeholderMunicipality.removeTree(this);
    Species placeholderSpecies = species;
    this.species = null;
    placeholderSpecies.removeTree(this);
    User placeholderUsers = users;
    this.users = null;
    placeholderUsers.removeTree(this);
    TreePLE placeholderTreePLE = treePLE;
    this.treePLE = null;
    placeholderTreePLE.removeTree(this);
  }


  public String toString()
  {
    return super.toString() + "["+
            "latitude" + ":" + getLatitude()+ "," +
            "longitude" + ":" + getLongitude()+ "," +
            "height" + ":" + getHeight()+ "," +
            "diameter" + ":" + getDiameter()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status = "+(getStatus()!=null?Integer.toHexString(System.identityHashCode(getStatus())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "landuse = "+(getLanduse()!=null?Integer.toHexString(System.identityHashCode(getLanduse())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "municipality = "+(getMunicipality()!=null?Integer.toHexString(System.identityHashCode(getMunicipality())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "species = "+(getSpecies()!=null?Integer.toHexString(System.identityHashCode(getSpecies())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "users = "+(getUsers()!=null?Integer.toHexString(System.identityHashCode(getUsers())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "treePLE = "+(getTreePLE()!=null?Integer.toHexString(System.identityHashCode(getTreePLE())):"null");
  }
}