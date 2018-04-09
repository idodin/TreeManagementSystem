/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.util.*;
import java.sql.Date;

// line 3 "../../../../../TreePLE.ump"
public class TreePLE
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TreePLE Associations
  private List<Tree> trees;
  private List<Municipality> municipalities;
  private List<Species> species;
  private List<User> users;
  private List<Park> parks;
  private List<Street> streets;
  private List<TreeStatus> statuses;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreePLE()
  {
    trees = new ArrayList<Tree>();
    municipalities = new ArrayList<Municipality>();
    species = new ArrayList<Species>();
    users = new ArrayList<User>();
    parks = new ArrayList<Park>();
    streets = new ArrayList<Street>();
    statuses = new ArrayList<TreeStatus>();
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public Municipality getMunicipality(int index)
  {
    Municipality aMunicipality = municipalities.get(index);
    return aMunicipality;
  }

  public List<Municipality> getMunicipalities()
  {
    List<Municipality> newMunicipalities = Collections.unmodifiableList(municipalities);
    return newMunicipalities;
  }

  public int numberOfMunicipalities()
  {
    int number = municipalities.size();
    return number;
  }

  public boolean hasMunicipalities()
  {
    boolean has = municipalities.size() > 0;
    return has;
  }

  public int indexOfMunicipality(Municipality aMunicipality)
  {
    int index = municipalities.indexOf(aMunicipality);
    return index;
  }

  public Species getSpecies(int index)
  {
    Species aSpecies = species.get(index);
    return aSpecies;
  }

  public List<Species> getSpecies()
  {
    List<Species> newSpecies = Collections.unmodifiableList(species);
    return newSpecies;
  }

  public int numberOfSpecies()
  {
    int number = species.size();
    return number;
  }

  public boolean hasSpecies()
  {
    boolean has = species.size() > 0;
    return has;
  }

  public int indexOfSpecies(Species aSpecies)
  {
    int index = species.indexOf(aSpecies);
    return index;
  }

  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }

  public Park getPark(int index)
  {
    Park aPark = parks.get(index);
    return aPark;
  }

  public List<Park> getParks()
  {
    List<Park> newParks = Collections.unmodifiableList(parks);
    return newParks;
  }

  public int numberOfParks()
  {
    int number = parks.size();
    return number;
  }

  public boolean hasParks()
  {
    boolean has = parks.size() > 0;
    return has;
  }

  public int indexOfPark(Park aPark)
  {
    int index = parks.indexOf(aPark);
    return index;
  }

  public Street getStreet(int index)
  {
    Street aStreet = streets.get(index);
    return aStreet;
  }

  public List<Street> getStreets()
  {
    List<Street> newStreets = Collections.unmodifiableList(streets);
    return newStreets;
  }

  public int numberOfStreets()
  {
    int number = streets.size();
    return number;
  }

  public boolean hasStreets()
  {
    boolean has = streets.size() > 0;
    return has;
  }

  public int indexOfStreet(Street aStreet)
  {
    int index = streets.indexOf(aStreet);
    return index;
  }

  public TreeStatus getStatus(int index)
  {
    TreeStatus aStatus = statuses.get(index);
    return aStatus;
  }

  public List<TreeStatus> getStatuses()
  {
    List<TreeStatus> newStatuses = Collections.unmodifiableList(statuses);
    return newStatuses;
  }

  public int numberOfStatuses()
  {
    int number = statuses.size();
    return number;
  }

  public boolean hasStatuses()
  {
    boolean has = statuses.size() > 0;
    return has;
  }

  public int indexOfStatus(TreeStatus aStatus)
  {
    int index = statuses.indexOf(aStatus);
    return index;
  }

  public static int minimumNumberOfTrees()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Tree addTree(double aHeight, double aDiameter, Date aDatePlanted, Date aDateAdded, TreeStatus aTreeStatus, Species aSpecies, User aLocal, Municipality aMunicipality)
  {
    return new Tree(aHeight, aDiameter, aDatePlanted, aDateAdded, aTreeStatus, aSpecies, aLocal, aMunicipality, this);
  }

  public boolean addTree(Tree aTree)
  {
    boolean wasAdded = false;
    if (trees.contains(aTree)) { return false; }
    TreePLE existingTreePLE = aTree.getTreePLE();
    boolean isNewTreePLE = existingTreePLE != null && !this.equals(existingTreePLE);
    if (isNewTreePLE)
    {
      aTree.setTreePLE(this);
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
    //Unable to remove aTree, as it must always have a treePLE
    if (!this.equals(aTree.getTreePLE()))
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

  public static int minimumNumberOfMunicipalities()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Municipality addMunicipality(int aIdNumber, String aName)
  {
    return new Municipality(aIdNumber, aName, this);
  }

  public boolean addMunicipality(Municipality aMunicipality)
  {
    boolean wasAdded = false;
    if (municipalities.contains(aMunicipality)) { return false; }
    TreePLE existingTreePLE = aMunicipality.getTreePLE();
    boolean isNewTreePLE = existingTreePLE != null && !this.equals(existingTreePLE);
    if (isNewTreePLE)
    {
      aMunicipality.setTreePLE(this);
    }
    else
    {
      municipalities.add(aMunicipality);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMunicipality(Municipality aMunicipality)
  {
    boolean wasRemoved = false;
    //Unable to remove aMunicipality, as it must always have a treePLE
    if (!this.equals(aMunicipality.getTreePLE()))
    {
      municipalities.remove(aMunicipality);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addMunicipalityAt(Municipality aMunicipality, int index)
  {  
    boolean wasAdded = false;
    if(addMunicipality(aMunicipality))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMunicipalities()) { index = numberOfMunicipalities() - 1; }
      municipalities.remove(aMunicipality);
      municipalities.add(index, aMunicipality);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMunicipalityAt(Municipality aMunicipality, int index)
  {
    boolean wasAdded = false;
    if(municipalities.contains(aMunicipality))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMunicipalities()) { index = numberOfMunicipalities() - 1; }
      municipalities.remove(aMunicipality);
      municipalities.add(index, aMunicipality);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMunicipalityAt(aMunicipality, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfSpecies()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Species addSpecies(String aName, int aCarbonConsumption, int aOxygenProduction)
  {
    return new Species(aName, aCarbonConsumption, aOxygenProduction, this);
  }

  public boolean addSpecies(Species aSpecies)
  {
    boolean wasAdded = false;
    if (species.contains(aSpecies)) { return false; }
    TreePLE existingTreePLE = aSpecies.getTreePLE();
    boolean isNewTreePLE = existingTreePLE != null && !this.equals(existingTreePLE);
    if (isNewTreePLE)
    {
      aSpecies.setTreePLE(this);
    }
    else
    {
      species.add(aSpecies);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSpecies(Species aSpecies)
  {
    boolean wasRemoved = false;
    //Unable to remove aSpecies, as it must always have a treePLE
    if (!this.equals(aSpecies.getTreePLE()))
    {
      species.remove(aSpecies);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addSpeciesAt(Species aSpecies, int index)
  {  
    boolean wasAdded = false;
    if(addSpecies(aSpecies))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSpecies()) { index = numberOfSpecies() - 1; }
      species.remove(aSpecies);
      species.add(index, aSpecies);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSpeciesAt(Species aSpecies, int index)
  {
    boolean wasAdded = false;
    if(species.contains(aSpecies))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSpecies()) { index = numberOfSpecies() - 1; }
      species.remove(aSpecies);
      species.add(index, aSpecies);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSpeciesAt(aSpecies, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public User addUser(String aUsername, String aPassword)
  {
    return new User(aUsername, aPassword, this);
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    TreePLE existingTreePLE = aUser.getTreePLE();
    boolean isNewTreePLE = existingTreePLE != null && !this.equals(existingTreePLE);
    if (isNewTreePLE)
    {
      aUser.setTreePLE(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a treePLE
    if (!this.equals(aUser.getTreePLE()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfParks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Park addPark(int aParkCode, String aParkName)
  {
    return new Park(aParkCode, aParkName, this);
  }

  public boolean addPark(Park aPark)
  {
    boolean wasAdded = false;
    if (parks.contains(aPark)) { return false; }
    TreePLE existingTreePLE = aPark.getTreePLE();
    boolean isNewTreePLE = existingTreePLE != null && !this.equals(existingTreePLE);
    if (isNewTreePLE)
    {
      aPark.setTreePLE(this);
    }
    else
    {
      parks.add(aPark);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePark(Park aPark)
  {
    boolean wasRemoved = false;
    //Unable to remove aPark, as it must always have a treePLE
    if (!this.equals(aPark.getTreePLE()))
    {
      parks.remove(aPark);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addParkAt(Park aPark, int index)
  {  
    boolean wasAdded = false;
    if(addPark(aPark))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfParks()) { index = numberOfParks() - 1; }
      parks.remove(aPark);
      parks.add(index, aPark);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveParkAt(Park aPark, int index)
  {
    boolean wasAdded = false;
    if(parks.contains(aPark))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfParks()) { index = numberOfParks() - 1; }
      parks.remove(aPark);
      parks.add(index, aPark);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addParkAt(aPark, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfStreets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Street addStreet(String aStreetName)
  {
    return new Street(aStreetName, this);
  }

  public boolean addStreet(Street aStreet)
  {
    boolean wasAdded = false;
    if (streets.contains(aStreet)) { return false; }
    TreePLE existingTreePLE = aStreet.getTreePLE();
    boolean isNewTreePLE = existingTreePLE != null && !this.equals(existingTreePLE);
    if (isNewTreePLE)
    {
      aStreet.setTreePLE(this);
    }
    else
    {
      streets.add(aStreet);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeStreet(Street aStreet)
  {
    boolean wasRemoved = false;
    //Unable to remove aStreet, as it must always have a treePLE
    if (!this.equals(aStreet.getTreePLE()))
    {
      streets.remove(aStreet);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addStreetAt(Street aStreet, int index)
  {  
    boolean wasAdded = false;
    if(addStreet(aStreet))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStreets()) { index = numberOfStreets() - 1; }
      streets.remove(aStreet);
      streets.add(index, aStreet);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStreetAt(Street aStreet, int index)
  {
    boolean wasAdded = false;
    if(streets.contains(aStreet))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStreets()) { index = numberOfStreets() - 1; }
      streets.remove(aStreet);
      streets.add(index, aStreet);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStreetAt(aStreet, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfStatuses()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TreeStatus addStatus()
  {
    return new TreeStatus(this);
  }

  public boolean addStatus(TreeStatus aStatus)
  {
    boolean wasAdded = false;
    if (statuses.contains(aStatus)) { return false; }
    TreePLE existingTreePLE = aStatus.getTreePLE();
    boolean isNewTreePLE = existingTreePLE != null && !this.equals(existingTreePLE);
    if (isNewTreePLE)
    {
      aStatus.setTreePLE(this);
    }
    else
    {
      statuses.add(aStatus);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeStatus(TreeStatus aStatus)
  {
    boolean wasRemoved = false;
    //Unable to remove aStatus, as it must always have a treePLE
    if (!this.equals(aStatus.getTreePLE()))
    {
      statuses.remove(aStatus);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addStatusAt(TreeStatus aStatus, int index)
  {  
    boolean wasAdded = false;
    if(addStatus(aStatus))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStatuses()) { index = numberOfStatuses() - 1; }
      statuses.remove(aStatus);
      statuses.add(index, aStatus);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStatusAt(TreeStatus aStatus, int index)
  {
    boolean wasAdded = false;
    if(statuses.contains(aStatus))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStatuses()) { index = numberOfStatuses() - 1; }
      statuses.remove(aStatus);
      statuses.add(index, aStatus);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStatusAt(aStatus, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (trees.size() > 0)
    {
      Tree aTree = trees.get(trees.size() - 1);
      aTree.delete();
      trees.remove(aTree);
    }
    
    while (municipalities.size() > 0)
    {
      Municipality aMunicipality = municipalities.get(municipalities.size() - 1);
      aMunicipality.delete();
      municipalities.remove(aMunicipality);
    }
    
    while (species.size() > 0)
    {
      Species aSpecies = species.get(species.size() - 1);
      aSpecies.delete();
      species.remove(aSpecies);
    }
    
    while (users.size() > 0)
    {
      User aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
    while (parks.size() > 0)
    {
      Park aPark = parks.get(parks.size() - 1);
      aPark.delete();
      parks.remove(aPark);
    }
    
    while (streets.size() > 0)
    {
      Street aStreet = streets.get(streets.size() - 1);
      aStreet.delete();
      streets.remove(aStreet);
    }
    
    while (statuses.size() > 0)
    {
      TreeStatus aStatus = statuses.get(statuses.size() - 1);
      aStatus.delete();
      statuses.remove(aStatus);
    }
    
  }

  // line 13 "../../../../../TreePLE.ump"
   public void reinitialize(){
    Tree.reinitializeAutouniqueID(this.getTrees());
  }

}