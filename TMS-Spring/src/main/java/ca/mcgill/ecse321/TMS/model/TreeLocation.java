/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TMS.model;

// line 28 "../../../../../TreePLE.ump"
public class TreeLocation
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum LandUseType { Residential, Institutional, Park, Municipal }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TreeLocation Attributes
  private LandUseType landUseType;
  private int longitude;
  private int latitude;

  //TreeLocation Associations
  private Municipality municipality;
  private Tree tree;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreeLocation(int aLongitude, int aLatitude, Municipality aMunicipality, Tree aTree)
  {
    longitude = aLongitude;
    latitude = aLatitude;
    boolean didAddMunicipality = setMunicipality(aMunicipality);
    if (!didAddMunicipality)
    {
      throw new RuntimeException("Unable to create treeLocation due to municipality");
    }
    if (aTree == null || aTree.getTreeLocation() != null)
    {
      throw new RuntimeException("Unable to create TreeLocation due to aTree");
    }
    tree = aTree;
  }

  public TreeLocation(int aLongitude, int aLatitude, Municipality aMunicipality, int aHeightForTree, int aDiameterForTree, Species aSpeciesForTree, User aLocalForTree, TreePLE aTreePLEForTree)
  {
    longitude = aLongitude;
    latitude = aLatitude;
    boolean didAddMunicipality = setMunicipality(aMunicipality);
    if (!didAddMunicipality)
    {
      throw new RuntimeException("Unable to create treeLocation due to municipality");
    }
    tree = new Tree(aHeightForTree, aDiameterForTree, this, aSpeciesForTree, aLocalForTree, aTreePLEForTree);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLandUseType(LandUseType aLandUseType)
  {
    boolean wasSet = false;
    landUseType = aLandUseType;
    wasSet = true;
    return wasSet;
  }

  public boolean setLongitude(int aLongitude)
  {
    boolean wasSet = false;
    longitude = aLongitude;
    wasSet = true;
    return wasSet;
  }

  public boolean setLatitude(int aLatitude)
  {
    boolean wasSet = false;
    latitude = aLatitude;
    wasSet = true;
    return wasSet;
  }

  public LandUseType getLandUseType()
  {
    return landUseType;
  }

  public int getLongitude()
  {
    return longitude;
  }

  public int getLatitude()
  {
    return latitude;
  }

  public Municipality getMunicipality()
  {
    return municipality;
  }

  public Tree getTree()
  {
    return tree;
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
      existingMunicipality.removeTreeLocation(this);
    }
    municipality.addTreeLocation(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Municipality placeholderMunicipality = municipality;
    this.municipality = null;
    placeholderMunicipality.removeTreeLocation(this);
    Tree existingTree = tree;
    tree = null;
    if (existingTree != null)
    {
      existingTree.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "longitude" + ":" + getLongitude()+ "," +
            "latitude" + ":" + getLatitude()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "landUseType" + "=" + (getLandUseType() != null ? !getLandUseType().equals(this)  ? getLandUseType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "municipality = "+(getMunicipality()!=null?Integer.toHexString(System.identityHashCode(getMunicipality())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "tree = "+(getTree()!=null?Integer.toHexString(System.identityHashCode(getTree())):"null");
  }
}