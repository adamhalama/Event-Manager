package server;

import Shared.ClientListener;
import Shared.Employee.Employee;
import Shared.Event.Event;
import Shared.Messages.Message;
import Shared.Messages.MessageRoom;
import Shared.Room.Room;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RmiNotificator
{
  private ArrayList<ClientListener> clients = new ArrayList<>();
  Map<Integer, ArrayList<ClientListener>> messageRoomFollows = new HashMap<>();

  public RmiNotificator() {}


  /*--Core--*/

  /**
   * Add client to the list of notified clients
   * @param client
   */
  public void addClient(ClientListener client) {
    clients.add(client);
  }

  /**
   * Removes client from the list of notified clients
   * @param client
   */
  public void removeClient(ClientListener client) {
    clients.remove(client);
  }


  /*--Employee--*/

  /**
   * Send created or edited employee to the client
   * @param employee
   */
  public void employeeUpdate(Employee employee)
  {
    for(ClientListener client : clients) {
      try
      {
        client.employeeUpdate(employee);
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * Send employee delete event to the client
   * @param employeeID
   */
  public void employeeDelete(int employeeID)
  {
    for(ClientListener client : clients) {
      try
      {
        client.employeeDelete(employeeID);
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    }
  }


  /*--Room--*/

  /**
   * Send created or edited room to the client
   * @param room
   */
  public void roomUpdate(Room room)
  {
    for(ClientListener client : clients) {
      try
      {
        client.roomUpdate(room);
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * Send room delete event to the client
   * @param roomID
   */
  public void roomDelete(int roomID)
  {
    for(ClientListener client : clients) {
      try
      {
        client.roomDelete(roomID);
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    }
  }


  /*--Room Equipment--*/

  /**
   * Send room equipment update to the client
   * @param roomID
   * @param equipment
   */
  public void roomEquipmentUpdate(int roomID, String[] equipment)
  {
    for(ClientListener client : clients) {
      try
      {
        client.roomEquipmentUpdate(roomID, equipment);
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    }
  }


  /*--Message Room--*/

  /**
   * Send created or edited message room to the client
   * @param messageRoom
   */
  public void messageRoomUpdate(MessageRoom messageRoom)
  {
    for(ClientListener client : clients) {
      try
      {
        client.messageRoomUpdate(messageRoom);
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * Send message room delete event to the client
   * @param messageRoomID
   */
  public void messageRoomDelete(int messageRoomID)
  {
    for(ClientListener client : clients) {
      try
      {
        client.messageRoomDelete(messageRoomID);
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * Subscribe for new messages for specific message room
   * @param client
   * @param messageRoomID
   */
  public void messageRoomFollow(ClientListener client, int messageRoomID)
  {
    if(!messageRoomFollows.containsKey(messageRoomID)) {
      messageRoomFollows.put(messageRoomID, new ArrayList<>());
    }
    messageRoomFollows.get(messageRoomID).add(client);
  }

  /**
   * Unsubscribe from new messages
   * @param client
   * @param messageRoomID
   */
  public void messageRoomUnfollow(ClientListener client, int messageRoomID)
  {
    if(messageRoomFollows.containsKey(messageRoomID)) {
      messageRoomFollows.get(messageRoomID).remove(client);

      if(messageRoomFollows.get(messageRoomID).size()==0) {
        messageRoomFollows.remove(messageRoomID);
      }
    }
  }

  /**
   * Send new message to the subscribed clients
   * @param messageRoomID
   * @param message
   */
  public void messageRoomNotify(int messageRoomID, Message message) {
    if(!messageRoomFollows.containsKey(messageRoomID)) {
      return;
    }
    for(ClientListener client : messageRoomFollows.get(messageRoomID)) {
      try
      {
        client.messageRoomNotify(messageRoomID, message);
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    }
  }


  /*--Event--*/

  /**
   * Send created or edited event to the client
   * @param event
   */
  public void eventUpdate(Event event)
  {
    for(ClientListener client : clients) {
      try
      {
        client.eventUpdate(event);
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * Send event delete event to the client
   * @param eventID
   */
  public void eventDelete(int eventID)
  {
    for(ClientListener client : clients) {
      try
      {
        client.eventDelete(eventID);
      }
      catch (RemoteException e)
      {
        e.printStackTrace();
      }
    }
  }
}
