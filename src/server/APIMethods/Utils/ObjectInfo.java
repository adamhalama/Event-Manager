package server.APIMethods.Utils;

import Shared.Employee.Employee;
import Shared.Event.Event;
import Shared.Messages.MessageRoom;
import Shared.Room.Room;
import server.DatabaseModel.DatabaseHandler;

import java.util.ArrayList;
import java.util.Arrays;

public class ObjectInfo
{
  private static ArrayList<Integer> intToIntegerArrList(int[] arr) {
    ArrayList<Integer> list = new ArrayList<>(arr.length);
    for (int i: arr) {
      list.add(Integer.valueOf(i));
    }
    return list;
  }
  public static Employee getFullEmployee(Employee employee, DatabaseHandler databaseHandler) {
    String[] permissions = databaseHandler.employeePermission.getAllByID(employee.getId());
    int[] events = databaseHandler.eventParticipant.getEvents(employee.getId());
    int[] rooms = databaseHandler.messageRoomParticipant.getRooms(employee.getId());
    employee.setPermissions(new ArrayList<>(Arrays.asList(permissions)));
    employee.setEvents(intToIntegerArrList(events));
    employee.setMessageRooms(intToIntegerArrList(rooms));
    return employee;
  }
  public static Room getFullRoom(Room room, DatabaseHandler databaseHandler) {
    String[] equipment = databaseHandler.employeePermission.getAllByID(room.getRoomID());
    room.setEquipment(new ArrayList<>(Arrays.asList(equipment)));
    return room;
  }
  public static MessageRoom getFullMessageRoom(MessageRoom messageRoom, DatabaseHandler databaseHandler) {
    int[] participants = databaseHandler.messageRoomParticipant.getParticipants(messageRoom.getId());
    messageRoom.setUsersIDs(intToIntegerArrList(participants));
    return messageRoom;
  }
  public static Event getFullEvent(Event event, DatabaseHandler databaseHandler) {
    int[] participants = databaseHandler.eventParticipant.getParticipants(event.getEvent_id());
    event.setParticipants(intToIntegerArrList(participants));
    return event;
  }
}
