/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package ca.mcgill.ecse321.TMS.model;

// line 59 "../../../../../TreePLE.ump"
public class UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UserRole Associations
  private User user;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserRole(User aUser)
  {
    boolean didAddUser = setUser(aUser);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create userRole due to user");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public User getUser()
  {
    return user;
  }

  public boolean setUser(User aUser)
  {
    boolean wasSet = false;
    //Must provide user to userRole
    if (aUser == null)
    {
      return wasSet;
    }

    //user already at maximum (2)
    if (aUser.numberOfUserRoles() >= User.maximumNumberOfUserRoles())
    {
      return wasSet;
    }
    
    User existingUser = user;
    user = aUser;
    if (existingUser != null && !existingUser.equals(aUser))
    {
      boolean didRemove = existingUser.removeUserRole(this);
      if (!didRemove)
      {
        user = existingUser;
        return wasSet;
      }
    }
    user.addUserRole(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    User placeholderUser = user;
    this.user = null;
    placeholderUser.removeUserRole(this);
  }

}