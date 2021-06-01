package client;

import Shared.ClientListener;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiListener implements ClientListener
{
  public RmiListener() throws RemoteException {
    UnicastRemoteObject.exportObject(this, 0);
  }

  @Override
  public void doSomething(String asd) throws RemoteException {
    System.out.println("Server invoked doSomething()");
  }
}
