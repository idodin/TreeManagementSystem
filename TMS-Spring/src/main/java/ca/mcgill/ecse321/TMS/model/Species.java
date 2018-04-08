/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse321.TMS.model;
import java.util.*;
import java.sql.Date;

// line 76 "../../../../../TreePLE.ump"
public class Species
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Species> speciessByName = new HashMap<String, Species>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Species Attributes
  private String name;
  private int carbonConsumption;
  private int oxygenProduction;

  //Species Associations
  private TreePLE treePLE;
  private List<Tree> trees;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Species(String aName, int aCarbonConsumption, int aOxygenProduction, TreePLE aTreePLE)
  {
    carbonConsumption = aCarbonConsumption;
    oxygenProduction = aOxygenProduction;
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name");
    }
    boolean didAddTreePLE = setTreePLE(aTreePLE);
    if (!didAddTreePLE)
    {
      throw new RuntimeException("Unable to create species due to treePLE");
    }
    trees = new ArrayList<Tree>();
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
      speciessByName.remove(anOldName);
    }
    speciessByName.put(aName, this);
    return wasSet;
  }

  public boolean setCarbonConsumption(int aCarbonConsumption)
  {
    boolean wasSet = false;
    carbonConsumption = aCarbonConsumption;
    wasSet = true;
    return wasSet;
  }

  public boolean setOxygenProduction(int aOxygenProduction)
  {
    boolean wasSet = false;
    oxygenProduction = aOxygenProduction;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public static Species getWithName(String aName)
  {
    return speciessByName.get(aName);
  }

  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
  }

  public int getCarbonConsumption()
  {
    return carbonConsumption;
  }

  public int getOxygenProduction()
  {
    return oxygenProduction;
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
      existingTreePLE.removeSpecies(this);
    }
    treePLE.addSpecies(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfTrees()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Tree addTree(double aHeight, double aDiameter, Date aDatePlanted, Date aDateAdded, TreeStatus aTreeStatus, User aLocal, Municipality aMunicipality, TreePLE aTreePLE)
  {
    return new Tree(aHeight, aDiameter, aDatePlanted, aDateAdded, aTreeStatus, this, aLocal, aMunicipality, aTreePLE);
  }

  public boolean addTree(Tree aTree)
  {
    boolean wasAdded = false;
    if (trees.contains(aTree)) { return false; }
    Species existingSpecies = aTree.getSpecies();
    boolean isNewSpecies = existingSpecies != null && !this.equals(existingSpecies);
    if (isNewSpecies)
    {
      aTree.setSpecies(this);
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
    //Unable to remove aTree, as it must always have a species
    if (!this.equals(aTree.getSpecies()))
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
    speciessByName.remove(getName());
    TreePLE placeholderTreePLE = treePLE;
    this.treePLE = null;
    if(placeholderTreePLE != null)
    {
      placeholderTreePLE.removeSpecies(this);
    }
    for(int i=trees.size(); i > 0; i--)
    {
      Tree aTree = trees.get(i - 1);
      aTree.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "carbonConsumption" + ":" + getCarbonConsumption()+ "," +
            "oxygenProduction" + ":" + getOxygenProduction()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "treePLE = "+(getTreePLE()!=null?Integer.toHexString(System.identityHashCode(getTreePLE())):"null");
  }
}