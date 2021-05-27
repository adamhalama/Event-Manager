package Shared;

import Shared.Employee.Employee;
import Shared.Messages.MessageRoom;
import Shared.Room.Room;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface API extends Remote {
  Employee registerEmployee(String username, String password, String name, String surname, String role)
      throws IOException, GeneralSecurityException, SQLException, RemoteException;
  Employee loginEmployee(String username, String password)
      throws IOException, GeneralSecurityException, SQLException, RemoteException;
  Employee getEmployee(int employeeID) throws SQLException, RemoteException;
  ArrayList<Employee> getEmployees(ArrayList<Integer> employeesIDs) throws RemoteException;
  Employee employeeSetName(int employeeID1, int employeeID2, String name) throws SQLException, RemoteException;
  Employee employeeSetSurname(int employeeID1, int employeeID2, String surname) throws SQLException, RemoteException;
  Employee employeeSetRole(int employeeID1, int employeeID2, String role) throws SQLException, RemoteException;
  Employee employeePermissionAdd(int employeeID1, int employeeID2, String permission) throws SQLException, RemoteException;
  Room getRoom(int roomID) throws SQLException, RemoteException;
  ArrayList<Room> getRooms(ArrayList<Integer> roomIDs) throws RemoteException;
  Room roomCreate(int employeeID1, String roomNumber, String buildingAddress, int numberOfSeats, int floor) throws SQLException, RemoteException;
  MessageRoom getMessageRoom(int messageRoomID) throws SQLException, RemoteException;
  ArrayList<MessageRoom> getMessageRooms(ArrayList<Integer> messageRoomIDs) throws RemoteException;
  MessageRoom createPrivateMessageRoom(int employeeID1, int employeeID2) throws SQLException, RemoteException;
}