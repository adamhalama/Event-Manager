package client;

import Shared.API;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class RmiClient
{
  private API server;

  public RmiClient()
  {
    try
    {
      server = (API) Naming.lookup("rmi://localhost:1099/Case");
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
      System.out.println(server.getEmployee(1));
    } catch (SQLException throwables)
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
}
