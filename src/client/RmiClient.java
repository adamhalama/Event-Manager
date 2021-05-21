package client;

import server.API;
import java.rmi.Naming;
import java.rmi.RemoteException;

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
