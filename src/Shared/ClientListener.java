package Shared;

import Shared.Employee.Employee;
import Shared.Event.Event;
import Shared.Messages.Message;
import Shared.Messages.MessageRoom;
import Shared.Room.Room;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientListener extends Remote
{
  public void employeeUpdate(Employee employee) throws RemoteException;
  public void employeeDelete(int employeeID) throws RemoteException;

  public void roomUpdate(Room room) throws RemoteException;
  public void roomDelete(int roomID) throws RemoteException;

  public void roomEquipmentUpdate(int roomID, String[] equipment) throws RemoteException;

  public void messageRoomUpdate(MessageRoom messageRoom) throws RemoteException;
  public void messageRoomDelete(int messageRoomID) throws RemoteException;
  public void messageRoomNotify(int messageRoomID, Message message) throws RemoteException;

  public void eventUpdate(Event event) throws RemoteException;
  public void eventDelete(int eventID) throws RemoteException;
}
