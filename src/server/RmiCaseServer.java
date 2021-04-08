package server;

import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

public class RmiCaseServer implements TextConverter {
  public void start() throws RemoteException, MalformedURLException {
    UnicastRemoteObject.exportObject(this, 0);
    Naming.rebind("Case", this);
  }

  @Override public String toUpperCase(String text) {
    return text.toUpperCase();
  }

  @Override public String capitalize(String text) {
    return Character.toUpperCase(text.charAt(0)) + text.substring(1)
        .toLowerCase();
  }
}