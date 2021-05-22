package Shared;

import Shared.Employee.Employee;
import Shared.Room.Room;

import java.io.IOException;
import java.rmi.Remote;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface API extends Remote {
  Employee registerEmployee(String username, String password, String name, String surname, String role)
      throws IOException, GeneralSecurityException, SQLException;
  Employee loginEmployee(String username, String password)
      throws IOException, GeneralSecurityException, SQLException;
  Employee getEmployee(int employeesID) throws SQLException;
  ArrayList<Employee> getEmployees(ArrayList<Integer> employeesIDs);
  Room getRoom(int employeesID) throws SQLException;
  ArrayList<Room> getRooms(ArrayList<Integer> employeesIDs);
}