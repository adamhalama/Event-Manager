package server;

import Shared.Employee.Employee;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

public interface API extends Remote {
  Employee registerEmployee(String username, String password, String name, String surname, String role)
      throws IOException, GeneralSecurityException, SQLException;
  Employee loginEmployee(String username, String password)
      throws IOException, GeneralSecurityException, SQLException;
}