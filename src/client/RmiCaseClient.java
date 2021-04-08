package client;

import server.TextConverter;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class RmiCaseClient
{
  private TextConverter server;

  public RmiCaseClient()
  {
    try
    {
      server = (TextConverter) Naming.lookup("rmi://localhost:1099/Case");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public String convert(String text, boolean upper) throws RemoteException
  {
    if (upper)
    {
      return server.toUpperCase(text);
    }
    return server.capitalize(text);
  }
}
