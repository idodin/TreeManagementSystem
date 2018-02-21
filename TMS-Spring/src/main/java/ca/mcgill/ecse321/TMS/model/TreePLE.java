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
  private List<Municipality> municipalities;
  private List<Species> species;
  private List<User> locals;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreePLE()
  {
    trees = new ArrayList<Tree>();
    municipalities = new ArrayList<Municipality>();
    species = new ArrayList<Species>();
    locals = new ArrayList<User>();
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

  public User getLocal(int index)
  {
    User aLocal = locals.get(index);
    return aLocal;
  }

  public List<User> getLocals()
  {
    List<User> newLocals = Collections.unmodifiableList(locals);
    return newLocals;
  }

  public int numberOfLocals()
  {
    int number = locals.size();
    return number;
  }

  public boolean hasLocals()
  {
    boolean has = locals.size() > 0;
    return has;
  }

  public int indexOfLocal(User aLocal)
  {
    int index = locals.indexOf(aLocal);
    return index;
  }

  public static int minimumNumberOfTrees()
  {
    return 0;
  }

  public Tree addTree(int aHeight, int aDiameter, Species aSpecies, User aLocal)
  {
    return new Tree(aHeight, aDiameter, aSpecies, aLocal, this);
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

  public static int minimumNumberOfLocals()
  {
    return 0;
  }

  public User addLocal(String aUsername)
  {
    return new User(aUsername, this);
  }

  public boolean addLocal(User aLocal)
  {
    boolean wasAdded = false;
    if (locals.contains(aLocal)) { return false; }
    TreePLE existingTreePLE = aLocal.getTreePLE();
    boolean isNewTreePLE = existingTreePLE != null && !this.equals(existingTreePLE);
    if (isNewTreePLE)
    {
      aLocal.setTreePLE(this);
    }
    else
    {
      locals.add(aLocal);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLocal(User aLocal)
  {
    boolean wasRemoved = false;
    //Unable to remove aLocal, as it must always have a treePLE
    if (!this.equals(aLocal.getTreePLE()))
    {
      locals.remove(aLocal);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addLocalAt(User aLocal, int index)
  {  
    boolean wasAdded = false;
    if(addLocal(aLocal))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLocals()) { index = numberOfLocals() - 1; }
      locals.remove(aLocal);
      locals.add(index, aLocal);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLocalAt(User aLocal, int index)
  {
    boolean wasAdded = false;
    if(locals.contains(aLocal))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLocals()) { index = numberOfLocals() - 1; }
      locals.remove(aLocal);
      locals.add(index, aLocal);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLocalAt(aLocal, index);
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
    
    while (locals.size() > 0)
    {
      User aLocal = locals.get(locals.size() - 1);
      aLocal.delete();
      locals.remove(aLocal);
    }
    
  }

}