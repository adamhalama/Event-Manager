package client;

import Shared.ClientListener;
import Shared.Employee.Employee;
import Shared.Event.Event;
import Shared.Messages.Message;
import Shared.Messages.MessageRoom;
import Shared.Room.Room;
import client.Model.Model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiListener implements ClientListener
{
  private Model model;
  public RmiListener(Model model) throws RemoteException {
    this.model = model;
    UnicastRemoteObject.exportObject(this, 0);
  }

  @Override
  public void employeeUpdate(Employee employee) throws RemoteException {
    model.updateLocalEmployee(employee);
  }
  @Override
  public void employeeDelete(int employeeID) throws RemoteException {
    model.removeLocalEmployee(employeeID);
  }

  @Override
  public void roomUpdate(Room room) throws RemoteException {}
  @Override
  public void roomDelete(int roomID) throws RemoteException {}

  @Override
  public void roomEquipmentUpdate(int roomID, String[] equipment) throws RemoteException {}

  @Override
  public void messageRoomUpdate(MessageRoom messageRoom) throws RemoteException {}
  @Override
  public void messageRoomDelete(int messageRoomID) throws RemoteException {}
  @Override
  public void messageRoomNotify(int messageRoomID, Message message) throws RemoteException {}

  @Override
  public void eventUpdate(Event event) throws RemoteException {}
  @Override
  public void eventDelete(int eventID) throws RemoteException {}
}
