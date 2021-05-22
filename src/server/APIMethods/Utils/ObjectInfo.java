package server.APIMethods.Utils;

import Shared.Employee.Employee;
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
}
