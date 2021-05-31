package server;

import Shared.API;
import Shared.Employee.Employee;
import Shared.Event.Event;
import Shared.Messages.Message;
import Shared.Messages.MessageRoom;
import Shared.Room.Room;
import server.APIMethods.Utils.Crypt;
import server.APIMethods.Utils.ObjectInfo;
import server.DatabaseModel.DatabaseHandler;

import java.io.IOException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.*;

import static server.DatabaseModel.models.Model.formatStringValues;

public class RmiServer implements API
{
    private DatabaseHandler databaseHandler;

    public void start(DatabaseHandler databaseHandler) throws RemoteException, MalformedURLException
    {
        this.databaseHandler = databaseHandler;
        UnicastRemoteObject.exportObject(this, 0);
        Naming.rebind("API", this);
    }

    private void checkPermission(int employeeID, String permission)
        throws SQLException
    {
        if(!this.databaseHandler.employeePermission.exists(permission, employeeID)) {
            throw new SQLException("Not enough permissions!");
        }
    }


    /*--Employee--*/

    @Override
    public Employee employeeRegister(String username, String password, String name, String surname, String role)
        throws GeneralSecurityException, IOException, SQLException
    {
        String encryptedPassword = Crypt.encryptPassword(password);
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.create(username, encryptedPassword, name, surname, role), this.databaseHandler);
    }

    @Override
    public Employee employeeLogin(String username, String password)
        throws GeneralSecurityException, IOException, SQLException
    {
        String encryptedPassword = Crypt.encryptPassword(password);
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.getByUsernameAndPassword(username, encryptedPassword), this.databaseHandler);
    }

    @Override
    public Employee employeeDelete(int employeeID1, int employeeID2)
        throws SQLException
    {
        if(employeeID1 != employeeID2) {
            this.checkPermission(employeeID1, "employees_create_edit");
        }
        Employee employee = this.databaseHandler.employee.deleteByID(employeeID2);
        return ObjectInfo.getFullEmployee(employee, this.databaseHandler);
    }

    @Override
    public Employee employeeRestore(int employeeID1, int employeeID2)
        throws SQLException
    {
        if(employeeID1 != employeeID2) {
            this.checkPermission(employeeID1, "employees_create_edit");
        }
        Employee employee = this.databaseHandler.employee.restoreByID(employeeID2);
        return ObjectInfo.getFullEmployee(employee, this.databaseHandler);
    }

    @Override
    public Employee employeeGetByID(int employeeID) throws SQLException
    {
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.getByID(employeeID), this.databaseHandler);
    }

    @Override
    public ArrayList<Employee> employeeGetByIDs(ArrayList<Integer> employeesIDs) {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            for(Integer employeeID : employeesIDs) {
                ObjectInfo.getFullEmployee(this.databaseHandler.employee.getByID(employeeID), this.databaseHandler);
            }
        } catch (Error | SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public ArrayList<Employee> employeeGetAll() {
        ArrayList<Employee> employees = this.databaseHandler.employee.getAll();
        try {
            for(Employee employee : employees) {
                ObjectInfo.getFullEmployee(employee, this.databaseHandler);
            }
        } catch (Error e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee employeeSetName(int employeeID1, int employeeID2, String name) throws SQLException
    {
        if(employeeID1 != employeeID2) {
            this.checkPermission(employeeID1, "employees_create_edit");
        }
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.editByID(new String[] {"name"}, formatStringValues(new String[] {name}), employeeID2), this.databaseHandler);
    }

    @Override
    public Employee employeeSetSurname(int employeeID1, int employeeID2, String surname) throws SQLException
    {
        if(employeeID1 != employeeID2) {
            this.checkPermission(employeeID1, "employees_create_edit");
        }
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.editByID(new String[] {"surname"}, formatStringValues(new String[] {surname}), employeeID2), this.databaseHandler);
    }

    @Override
    public Employee employeeSetRole(int employeeID1, int employeeID2, String role) throws SQLException
    {
        this.checkPermission(employeeID1, "employees_create_edit");
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.editByID(new String[] {"role"}, formatStringValues(new String[] {role}), employeeID2), this.databaseHandler);
    }

    @Override
    public Employee employeeSetPassword(int employeeID1, int employeeID2, String password)
        throws GeneralSecurityException, IOException, SQLException
    {
        if(employeeID1 != employeeID2) {
            this.checkPermission(employeeID1, "employees_create_edit");
        }
        String encryptedPassword = Crypt.encryptPassword(password);
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.editByID(new String[] {"password"}, formatStringValues(new String[] {encryptedPassword}), employeeID2), this.databaseHandler);
    }

    @Override
    public Employee employeePermissionAdd(int employeeID1, int employeeID2, String permission) throws SQLException
    {
        this.checkPermission(employeeID1, "employees_create_edit");
        this.databaseHandler.employeePermission.create(permission, employeeID2);
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.getByID(employeeID2), this.databaseHandler);
    }

    @Override
    public Employee employeePermissionRemove(int employeeID1, int employeeID2, String permission) throws SQLException
    {
        this.checkPermission(employeeID1, "employees_create_edit");
        this.databaseHandler.employeePermission.delete(permission, employeeID2);
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.getByID(employeeID2), this.databaseHandler);
    }

    @Override
    public Employee employeePermissionSet(int employeeID1, int employeeID2, String[] permissions) throws SQLException
    {
        this.checkPermission(employeeID1, "employees_create_edit");
        this.databaseHandler.employeePermission.deleteAll(employeeID2);
        for(String permission : permissions) {
            this.databaseHandler.employeePermission.create(permission, employeeID2);
        }
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.getByID(employeeID2), this.databaseHandler);
    }


    /*--Room--*/

    @Override
    public Room roomGetByID(int roomID) throws SQLException
    {
        return ObjectInfo.getFullRoom(this.databaseHandler.room.getByID(roomID), this.databaseHandler);
    }

    @Override
    public ArrayList<Room> roomGetByIDs(ArrayList<Integer> roomIDs) {
        ArrayList<Room> employees = new ArrayList<>();
        try {
            for(Integer roomID : roomIDs) {
                employees.add(ObjectInfo.getFullRoom(this.databaseHandler.room.getByID(roomID), this.databaseHandler));
            }
        } catch (Error | SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public ArrayList<Room> roomGetAll() {
        ArrayList<Room> rooms = this.databaseHandler.room.getAll();
        try {
            for(Room room : rooms) {
                ObjectInfo.getFullRoom(room, this.databaseHandler);
            }
        } catch (Error e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public Room roomCreate(int employeeID1, String roomNumber, String buildingAddress, int numberOfSeats, int floor) throws SQLException
    {
        this.checkPermission(employeeID1, "room_create_edit");
        Room room = this.databaseHandler.room.create(roomNumber, buildingAddress, numberOfSeats, floor);
        return ObjectInfo.getFullRoom(room, this.databaseHandler);
    }

    @Override
    public boolean roomDeleteByID(int employeeID1, int roomID)
    {
        try {
            this.checkPermission(employeeID1, "room_create_edit");
            return this.databaseHandler.room.deleteByID(roomID);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Room roomSetFloor(int employeeID1, int roomID, int floor) throws SQLException
    {
        this.checkPermission(employeeID1, "room_create_edit");
        return ObjectInfo.getFullRoom(
            this.databaseHandler.room.editByID(
                new String[] {"floor"},
                new String[] {String.valueOf(floor)},
                roomID
            ),
            this.databaseHandler
        );
    }

    @Override
    public Room roomSetNumberOfSeats(int employeeID1, int roomID, int numberOfSeats) throws SQLException
    {
        this.checkPermission(employeeID1, "room_create_edit");
        return ObjectInfo.getFullRoom(
            this.databaseHandler.room.editByID(
                new String[] {"number_of_seats"},
                new String[] {String.valueOf(numberOfSeats)},
                roomID
            ),
            this.databaseHandler
        );
    }

    @Override
    public Room roomSetRoomNumber(int employeeID1, int roomID, String roomNumber) throws SQLException
    {
        this.checkPermission(employeeID1, "room_create_edit");
        return ObjectInfo.getFullRoom(
            this.databaseHandler.room.editByID(
                new String[] {"room_no"},
                new String[] {String.valueOf(roomNumber)},
                roomID
            ),
            this.databaseHandler
        );
    }

    @Override
    public Room roomSetBuildingAddress(int employeeID1, int roomID, String buildingAddress) throws SQLException
    {
        this.checkPermission(employeeID1, "room_create_edit");
        return ObjectInfo.getFullRoom(
            this.databaseHandler.room.editByID(
                new String[] {"building_address"},
                new String[] {String.valueOf(buildingAddress)},
                roomID
            ),
            this.databaseHandler
        );
    }


    /*--Room Equipment--*/

    @Override
    public String[] roomEquipmentGet(int roomID)
    {
        return this.databaseHandler.roomEquipment.getAllByID(roomID);
    }

    @Override
    public boolean roomEquipmentAdd(int employeeID1, int roomID, String equipment)
        throws SQLException
    {
        this.checkPermission(employeeID1, "room_create_edit");
        return this.databaseHandler.roomEquipment.create(equipment, roomID);
    }

    @Override
    public boolean roomEquipmentRemove(int employeeID1, int roomID, String equipment)
        throws SQLException
    {
        this.checkPermission(employeeID1, "room_create_edit");
        return this.databaseHandler.roomEquipment.delete(equipment, roomID);
    }

    @Override
    public boolean roomEquipmentSet(int employeeID1, int roomID, String[] equipment)
        throws SQLException
    {
        this.checkPermission(employeeID1, "room_create_edit");
        this.databaseHandler.roomEquipment.deleteAll(roomID);
        for(String item : equipment) {
            this.databaseHandler.roomEquipment.create(item, roomID);
        }
        return true;
    }


    /*--Message Room--*/

    @Override
    public MessageRoom messageRoomGetByID(int messageRoomID) throws SQLException
    {
        return ObjectInfo.getFullMessageRoom(this.databaseHandler.messageRoom.getByID(messageRoomID), this.databaseHandler);
    }

    @Override
    public ArrayList<MessageRoom> messageRoomGetByIDs(ArrayList<Integer> messageRoomIDs) {
        ArrayList<MessageRoom> rooms = new ArrayList<>();
        try {
            for(Integer roomID : messageRoomIDs) {
                rooms.add(ObjectInfo.getFullMessageRoom(this.databaseHandler.messageRoom.getByID(roomID), this.databaseHandler));
            }
        } catch (Error | SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public ArrayList<MessageRoom> messageRoomGetPrivate(int employeeID1) {
        int[] employee1MessageRooms = this.databaseHandler.messageRoomParticipant.getRooms(employeeID1);
        ArrayList<MessageRoom> rooms = new ArrayList<>();
        try {
            for(Integer roomID : employee1MessageRooms) {
                MessageRoom room = this.databaseHandler.messageRoom.getByID(roomID);
                if(room.isPrivate()) {
                    rooms.add(ObjectInfo.getFullMessageRoom(room, this.databaseHandler));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public MessageRoom messageRoomCreatePrivate(int employeeID1, int employeeID2) throws SQLException
    {
        int[] employee1MessageRooms = this.databaseHandler.messageRoomParticipant.getRooms(employeeID1);
        int[] employee2MessageRooms = this.databaseHandler.messageRoomParticipant.getRooms(employeeID2);
        Map<Integer, MessageRoom> rooms = new HashMap<>();
        for(Integer roomID : employee1MessageRooms) {
            if(rooms.containsKey(roomID)) {continue;}
            rooms.put(roomID, this.databaseHandler.messageRoom.getByID(roomID));
        }
        for(Integer roomID : employee2MessageRooms) {
            if(rooms.containsKey(roomID)) {continue;}
            rooms.put(roomID, this.databaseHandler.messageRoom.getByID(roomID));
        }
        for (Map.Entry<Integer, MessageRoom> entry : rooms.entrySet()) {
            if(entry.getValue().isPrivate()) {
                Set<Integer> employeeIDs = new HashSet<Integer>(entry.getValue().getUsersIDs());
                if(employeeIDs.contains(employeeID1) && employeeIDs.contains(employeeID2)) {
                    return ObjectInfo.getFullMessageRoom(entry.getValue(), this.databaseHandler);
                }
            }
        }
        Employee employee1 = this.databaseHandler.employee.getByID(employeeID1);
        Employee employee2 = this.databaseHandler.employee.getByID(employeeID2);
        MessageRoom messageRoom = this.databaseHandler.messageRoom.create(employee1.getName() + " - " + employee2.getName(), true);
        this.databaseHandler.messageRoomParticipant.create(messageRoom.getId(), employeeID1);
        this.databaseHandler.messageRoomParticipant.create(messageRoom.getId(), employeeID2);
        messageRoom.addUser(employeeID1);
        messageRoom.addUser(employeeID2);
        return ObjectInfo.getFullMessageRoom(messageRoom, this.databaseHandler);
    }

    @Override
    public MessageRoom messageRoomSetName(int employeeID1, int messageRoomID, String name) throws SQLException
    {
        this.checkPermission(employeeID1, "chat_rooms_create_edit");
        return ObjectInfo.getFullMessageRoom(this.databaseHandler.messageRoom.editName(name, messageRoomID), this.databaseHandler);
    }


    /*--Message--*/

    @Override
    public ArrayList<Message> messagesGet(int employeeID1, int messageRoomID, int offset)
    {
        return databaseHandler.message.getByRoomID(messageRoomID, 40, offset);
        // 40 messages are loaded for each request
    }

    @Override
    public Message messagePost(int employeeID1, int messageRoomID, String message)
        throws SQLException
    {
        if(this.databaseHandler.messageRoomParticipant.exists(messageRoomID, employeeID1)) {
            return this.databaseHandler.message.create(messageRoomID, employeeID1, message);
        } else {
            throw new SQLException("User didn't join the message room");
        }
    }


    /*--Event--*/

    @Override
    public Event eventGetByID(int eventID) throws SQLException
    {
        return ObjectInfo.getFullEvent(this.databaseHandler.event.getByID(eventID), this.databaseHandler);
    }

    @Override
    public ArrayList<Event> eventGetByIDs(ArrayList<Integer> eventIDs) {
        ArrayList<Event> events = new ArrayList<>();
        try {
            for(Integer eventID : eventIDs) {
                events.add(ObjectInfo.getFullEvent(this.databaseHandler.event.getByID(eventID), this.databaseHandler));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public ArrayList<Event> eventGetAll() {
        ArrayList<Event> events = this.databaseHandler.event.getAll();
        try {
            for(Event event : events) {
                ObjectInfo.getFullEvent(event, this.databaseHandler);
            }
        } catch (Error e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public Event eventCreateOffline(int employeeID1, String title, String description, int roomID, long startTime, long endTime) throws SQLException
    {
        this.checkPermission(employeeID1, "event_create");
        MessageRoom messageRoom = this.databaseHandler.messageRoom.create("Event - " + title, false);
        this.databaseHandler.messageRoomParticipant.create(messageRoom.getId(), employeeID1);
        Event event = this.databaseHandler.event.create(title, description, roomID, employeeID1, messageRoom.getId(), startTime, endTime);
        return ObjectInfo.getFullEvent(event, this.databaseHandler);
    }

    @Override
    public Event eventCreateOnline(int employeeID1,String title, String description, String platform, String url, long startTime, long endTime) throws SQLException
    {
        this.checkPermission(employeeID1, "event_create");
        MessageRoom messageRoom = this.databaseHandler.messageRoom.create("Event - " + title, false);
        this.databaseHandler.messageRoomParticipant.create(messageRoom.getId(), employeeID1);
        Event event = this.databaseHandler.event.create(title, description, platform, url, employeeID1, messageRoom.getId(), startTime, endTime);
        return ObjectInfo.getFullEvent(event, this.databaseHandler);
    }

    @Override
    public boolean eventDeleteByID(int employeeID1, int eventID)
    {
        try {
            this.checkPermission(employeeID1, "room_create_edit");
            return this.databaseHandler.event.deleteByID(eventID);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private Event eventSetStringValue(int employeeID1, int eventID, String name, String value) throws SQLException
    {
        this.checkPermission(employeeID1, "event_edit");
        return ObjectInfo.getFullEvent(
            this.databaseHandler.event.editByID(
                new String[] {name},
                formatStringValues(new String[] {value}),
                eventID
            ),
            this.databaseHandler
        );
    }

    @Override
    public Event eventSetTitle(int employeeID1, int eventID, String title) throws SQLException
    {
        return this.eventSetStringValue(employeeID1, eventID, "title", title);
    }

    @Override
    public Event eventSetDescription(int employeeID1, int eventID, String description) throws SQLException
    {
        return this.eventSetStringValue(employeeID1, eventID, "description", description);
    }

    @Override
    public Event eventSetOnlineURL(int employeeID1, int eventID, String url) throws SQLException
    {
        return this.eventSetStringValue(employeeID1, eventID, "url", url);
    }

    @Override
    public Event eventSetPlatform(int employeeID1, int eventID, String platform) throws SQLException
    {
        return this.eventSetStringValue(employeeID1, eventID, "platform", platform);
    }

    @Override
    public Event eventSetOnlineState(int employeeID1, int eventID, boolean isOnline) throws SQLException
    {
        this.checkPermission(employeeID1, "event_edit");
        return ObjectInfo.getFullEvent(
            this.databaseHandler.event.editByID(
                new String[] {"is_online"},
                new String[] {String.valueOf(isOnline)},
                eventID
            ),
            this.databaseHandler
        );
    }

    @Override
    public Event eventSetTime(int employeeID1, int eventID, long startTime, long endTime) throws SQLException
    {
        this.checkPermission(employeeID1, "event_edit");
        return ObjectInfo.getFullEvent(
            this.databaseHandler.event.editByID(
                new String[] {"start_time", "end_time"},
                new String[] {String.valueOf(startTime), String.valueOf(endTime)},
                eventID
            ),
            this.databaseHandler
        );
    }

    @Override
    public boolean eventJoin(int employeeID1, int eventID) throws SQLException
    {
        this.checkPermission(employeeID1, "event_join");
        Event event = this.databaseHandler.event.getByID(eventID);
        this.databaseHandler.messageRoomParticipant.create(event.getMessageRoomID(), employeeID1);
        return this.databaseHandler.eventParticipant.create(eventID, employeeID1);
    }

    @Override
    public boolean eventLeave(int employeeID1, int eventID) throws SQLException
    {
        Event event = this.databaseHandler.event.getByID(eventID);
        this.databaseHandler.messageRoomParticipant.delete(event.getMessageRoomID(), employeeID1);
        return this.databaseHandler.eventParticipant.delete(eventID, employeeID1);
    }
}