package client;

import Shared.API;
import Shared.Employee.Employee;

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

  public Employee loginEmployee(String username, String password)
      throws GeneralSecurityException, IOException, SQLException
  {
    return server.loginEmployee(username, password);
  }
}
