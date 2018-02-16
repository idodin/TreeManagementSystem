/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.util.*;

// line 3 "../../../../../TreePLE.ump"
public class TreePLE
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TreePLE Associations
  private List<Tree> trees;
  private List<Status> statuses;
  private List<Municipality> municipalities;
  private List<Species> species;
  private List<User> users;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreePLE()
  {
    trees = new ArrayList<Tree>();
    statuses = new ArrayList<Status>();
    municipalities = new ArrayList<Municipality>();
    species = new ArrayList<Species>();
    users = new ArrayList<User>();
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

  public Status getStatus(int index)
  {
    Status aStatus = statuses.get(index);
    return aStatus;
  }

  public List<Status> getStatuses()
  {
    List<Status> newStatuses = Collections.unmodifiableList(statuses);
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

  public int indexOfStatus(Status aStatus)
  {
    int index = statuses.indexOf(aStatus);
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

  public static int minimumNumberOfTrees()
  {
    return 0;
  }

  public Tree addTree(int aLongitude, int aLatitude, int aHeight, int aDiameter, Status aStatus, LandUse aLanduse, Municipality aMunicipality, Species aSpecies, User aUsers)
  {
    return new Tree(aLongitude, aLatitude, aHeight, aDiameter, aStatus, aLanduse, aMunicipality, aSpecies, aUsers, this);
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

  public static int minimumNumberOfStatuses()
  {
    return 0;
  }

  public Status addStatus(TreeStatus aTreeStatus, String aDateOfBirth)
  {
    return new Status(aTreeStatus, aDateOfBirth, this);
  }

  public boolean addStatus(Status aStatus)
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

  public boolean removeStatus(Status aStatus)
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

  public boolean addStatusAt(Status aStatus, int index)
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

  public boolean addOrMoveStatusAt(Status aStatus, int index)
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

  public static int minimumNumberOfMunicipalities()
  {
    return 0;
  }

  public Municipality addMunicipality(String aName, int aBioDiversityIndex)
  {
    return new Municipality(aName, aBioDiversityIndex, this);
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

  public User addUser(String aUsername)
  {
    return new User(aUsername, this);
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

  public void delete()
  {
    while (trees.size() > 0)
    {
      Tree aTree = trees.get(trees.size() - 1);
      aTree.delete();
      trees.remove(aTree);
    }
    
    while (statuses.size() > 0)
    {
      Status aStatus = statuses.get(statuses.size() - 1);
      aStatus.delete();
      statuses.remove(aStatus);
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
    
  }

}