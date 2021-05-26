package client;

import Shared.API;
import Shared.Employee.Employee;
import server.APIMethods.Utils.Crypt;
import server.APIMethods.Utils.ObjectInfo;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

public class RmiClient
{
  private API server;

  public RmiClient()
  {
    try
    {
      server = (API) Naming.lookup("rmi://localhost:1099/API");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public void test()
  {
    try
    {
      System.out.println(server.getEmployee(1).toString());
    } catch (SQLException | RemoteException throwables)
    {
      throwables.printStackTrace();
    }
  }

  public String convert(String text, boolean upper) throws RemoteException
  {
    /*if (upper)
    {
      return server.toUpperCase(text);
    }
    return server.capitalize(text);*/
    return "";
  }

  /**
   * Log in method for gaining access to the server.
   * @param username A string containing the username.
   * @param password A string containing the password.
   * @return An employee object of the logged in employee, if the login was successful.
   * @throws SQLException
   * @throws GeneralSecurityException
   * @throws IOException
   */
  //TODO finish the javadoc
  public Employee loginEmployee(String username, String password) throws SQLException, GeneralSecurityException, IOException
  {
    return server.loginEmployee(username, password);
  }
//TODO finish the javadoc
  /**
   * Used for registering an employee in the system.
   * @param username A string containing the username the new user will have.
   * @param password A string containing the password the new user will have.
   * @param name A string containing the name the new user will have.
   * @param surname A string containing the surname the new user will have.
   * @param role A string containing the role the new user will have.
   * @return An Employee object of the created employee in the system on the server side.
   * @throws GeneralSecurityException
   * @throws IOException
   * @throws SQLException
   */

  //TODO implement this in the model manager
  public Employee registerEmployee(String username, String password, String name, String surname, String role)
          throws GeneralSecurityException, IOException, SQLException
  {
    return server.registerEmployee(username, password, name, surname, role);
  }

}
