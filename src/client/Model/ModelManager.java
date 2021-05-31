package client.Model;

import Shared.Employee.Employee;
import Shared.Employee.EmployeeList;
import Shared.Event.Event;
import Shared.Event.EventList;
import Shared.Messages.Message;
import Shared.Messages.MessageRoom;
import Shared.Messages.MessageRoomList;
import Shared.Room.Room;
import Shared.Room.RoomList;
import client.RmiClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


//TODO Add database updating

public class ModelManager implements Model
{

    private EmployeeList employeeList;
    private Event event;
    private EventList eventList;
    private RoomList roomList;
    private MessageRoomList messageRoomList;
    private ArrayList<Integer> idT;
    private ArrayList<Employee> employeesT;
    private Model model;

    private Employee loggedEmployee;

    private RmiClient api;

    public ModelManager(RmiClient client)
    {
        this.api = client;

        this.event = new Event();
        this.eventList = new EventList();
        this.roomList = new RoomList();
        this.employeeList = new EmployeeList();
        this.messageRoomList = new MessageRoomList();
        this.idT = new ArrayList<>();
        this.employeesT = new ArrayList<>();

        employeeList.setMessageRoomList(messageRoomList);
        employeeList.setEventList(eventList);
        messageRoomList.setEmployeeList(employeeList);


        //todo REMOVE THIS

        /*employeeList.addEmployee("Adam123", "Adam", "Halama", "CFO");
        employeeList.addEmployee("Klaudi123", "klaudi", "Var", "CBO");


        messageRoomList.addMessageRoom("Test room t1");
        messageRoomList.getMessageRoomByID(1).addUser(1);
        messageRoomList.getMessageRoomByID(1).addMessage(new Message(1, (System.currentTimeMillis()) , "Helooooo"));


        messageRoomList.addPrivateMessageRoom("Private message room t2", 1, 2);*/

        /*try
        {
            System.out.println(api.employeeRegister("admin", "admin", "Admin", "Admin", "Admin"));
        } catch (GeneralSecurityException | IOException | SQLException e)
        {
            e.printStackTrace();
        }*/

//        loggedClientID = 1;

        //logging in works, but i dont want to go to the login view every time


//       loggedEmployee = new Employee(7, "admin", "Admin", "Admin", "Admin", false);
//        employeeList.addEmployee(loggedEmployee);


        try
        {
//            addEmployee("admin", "admin", "Admin", "Admin" , "test admin");


            this.login("admin", "admin");
        } catch (SQLException | GeneralSecurityException | IOException throwables)
        {
            throwables.printStackTrace();
        }

    }

    @Override
    public void login(String username, String password) throws SQLException, GeneralSecurityException, IOException
    {
        loggedEmployee = api.employeeLogin(username, password);

        employeeList.addEmployee(loggedEmployee);
    }

    @Override
    public void logOut()
    {
        loggedEmployee = null;
    }


    @Override
    public int getLoggedEmployeeID()
    {
        if (loggedEmployee == null)
            return 0;
        return loggedEmployee.getId();
    }

    @Override
    public Employee getLoggedEmployee()
    {
        return loggedEmployee;
    }

    @Override
    public void addMessageRoom(String name)
    {
        messageRoomList.addMessageRoom(name);
    }

    @Override
    public MessageRoom messageRoomCreatePrivate(int employeeID2) throws SQLException, RemoteException
    {
        return api.messageRoomCreatePrivate(getLoggedEmployeeID(), employeeID2);
    }

    @Override
    public void addMessageRoom(String name, ArrayList<Integer> usersIDs)
    {
        messageRoomList.addMessageRoom(name, usersIDs);
    }

    @Override
    public MessageRoom messageRoomSetName(int messageRoomID, String name) throws SQLException, RemoteException
    {
        return api.messageRoomSetName(getLoggedEmployeeID(), messageRoomID, name);
    }

    @Override
    public ArrayList<Message> messagesGet(int messageRoomID, int offset) throws RemoteException
    {
        return api.messagesGet(getLoggedEmployeeID(), messageRoomID, offset);
    }

    @Override
    public Message sendMessage(int messageRoomID, String message) throws SQLException, RemoteException
    {
        return api.messagePost(getLoggedEmployeeID(), messageRoomID, message);
    }


    @Override
    public void removeMessageRoomFromEmployee(int messageRoomID)
    {
        messageRoomList.removeMessageRoom(messageRoomID);
    }

    @Override
    public void removeMessageRoomFromEmployee(MessageRoom room)
    {
        messageRoomList.removeMessageRoom(room);
    }

    @Override
    public ArrayList<MessageRoom> getMessageRoomsByEmployeeID(int employeeID)
    {
        return messageRoomList.getMessageRoomsByEmployeeID(employeeID);
    }

    @Override
    public ArrayList<MessageRoom> messageRoomGetPrivate() throws RemoteException
    {
        ArrayList<MessageRoom> messageRooms = api.messageRoomGetPrivate(getLoggedEmployeeID());
        messageRoomList.addRoomList(messageRooms);
        return messageRooms;
    }

    @Override
    public ArrayList<MessageRoom> getMessageRoomsByAnything(String keyword) throws RemoteException
    {
        messageRoomGetPrivate();
        return messageRoomList.getMessageRoomsByAnything(keyword);
    }

    @Override
    public ArrayList<MessageRoom> getMessageRooms()
    {
        return messageRoomList.getMessageRooms();
    }

    @Override
    public String getSenderAndBody(Message message)
    {
        if (message == null)
            return "";
        return employeeList.getEmployeeByID(message.getUserID()).getFullName() + ": " + message.getMessage();
    }

    @Override
    public ArrayList<String> getMessageRoomParticipantNames(MessageRoom messageRoom) throws RemoteException
    {
        ArrayList<String> participants = new ArrayList<>();

        getEmployees();

        for (int userID :
                messageRoom.getUsersIDs())
        {
            participants.add(employeeList.getEmployeeByID(userID).getName());
        }

        return participants;
    }

    @Override
    public MessageRoom getMessageRoomByID(int id) throws SQLException, RemoteException
    {
        MessageRoom messageRoom = api.getMessageRoomByID(id);
        if(messageRoomList.getMessageRoomByID(id) == null)
        {
            messageRoomList.addMessageRoom(messageRoom);
        }
        return messageRoom;
    }


    @Override
    public void addEmployee(Employee employee)
    {
        employeeList.addEmployee(employee);
        //TODO probably delete the whole method
    }

    @Override
    public void addEmployee(String username, String password, String name, String surname, String role) throws SQLException, GeneralSecurityException, IOException
    {
        employeeList.addEmployee(api.employeeRegister(username, password, name, surname, role));
    }

    @Override
    public void addEmployee(String username, String password, String name, String surname,
                            String role, ArrayList<String> permissions) throws SQLException, GeneralSecurityException, IOException
    {
        Employee employee = api.employeeRegister(username, password, name, surname, role);
        employeeList.addEmployee(employee);
        api.setPermissions(getLoggedEmployeeID(), employee.getId(), permissions.toArray(new String[0]));
        employee.setPermissions(permissions);
    }

    @Override
    public Employee removePermission(int employeeID2, String permission) throws SQLException, RemoteException
    {
        return api.removePermission(getLoggedEmployeeID(), employeeID2, permission);
    }

    @Override
    public Employee setPermissions(int employeeID2, String[] permissions) throws SQLException, RemoteException
    {
        return api.setPermissions(getLoggedEmployeeID(), employeeID2, permissions);
    }


    @Override
    public Employee removeEmployee(int employeeID) throws SQLException, RemoteException
    {
        employeeList.removeEmployee(employeeID);
        return api.employeeDelete(getLoggedEmployeeID(), employeeID);
    }

    @Override
    public Employee employeeRestore(int employeeID) throws SQLException, RemoteException
    {
        if (!employeeList.restoreEmployee(employeeID))
        {
            getEmployeeByID(employeeID); // getting the emp from the server, if he is not in the list
            employeeList.restoreEmployee(employeeID);
        }
        return api.employeeRestore(getLoggedEmployeeID(), employeeID);
    }

    @Override
    public ArrayList<Employee> getEmployees() throws RemoteException
    {
        employeeList.addAllEmployees(api.getAllEmployees());
        return employeeList.getEmployees();
    }

    @Override
    public ArrayList<Employee> getEmployees(ArrayList<Integer> employeesIDs) throws RemoteException
    {
        return api.employeeGetByIDs(employeesIDs);
    }

    @Override
    public ArrayList<Employee> getEmployeesByMessageRoom(int messageRoom)
    {
        return employeeList.getEmployeesByMessageRoom(messageRoom);
    }

    @Override
    public ArrayList<Employee> getEmployeesByEvent(int eventID)
    {
        return employeeList.getEmployeesByEvent(eventID);
    }

    @Override
    public ArrayList<Employee> getEmployeesByRole(String role)
    {
        return employeeList.getEmployeesByRole(role);
    }

    @Override
    public ArrayList<Employee> getEmployeesByText(String text)
    {
        return employeeList.getEmployeesByText(text);
    }

    @Override
    public ArrayList<Employee> getEmployeesByAnything(String keyword)
    {
        return employeeList.getEmployeesByAnything(keyword);
    }

    @Override
    public Employee getEmployeeByID(int ID) throws SQLException, RemoteException
    {
        if (employeeList.getEmployeeByID(ID) == null)
            employeeList.addEmployee(api.getEmployeeByID(ID));

        return api.getEmployeeByID(ID);
    }

    @Override
    public Employee employeeSetName(int employeeID2, String name) throws SQLException, RemoteException
    {
        return api.employeeSetName(getLoggedEmployeeID(), employeeID2, name);
    }

    @Override
    public Employee employeeSetSurname(int employeeID2, String surname) throws SQLException, RemoteException
    {
        return api.employeeSetSurname(getLoggedEmployeeID(), employeeID2, surname);
    }

    @Override
    public Employee employeeSetPassword(int employeeID2, String password) throws GeneralSecurityException, SQLException, IOException, RemoteException
    {
        return api.employeeSetPassword(getLoggedEmployeeID(), employeeID2, password);
    }

    @Override
    public Employee employeeSetUsername(int employeeID2, String username) throws SQLException, RemoteException
    {
        return api.employeeSetSurname(getLoggedEmployeeID(), employeeID2, username);
    }

    @Override
    public Employee employeeSetRole(int employeeID2, String role) throws SQLException, RemoteException
    {
        return api.employeeSetRole(getLoggedEmployeeID(), employeeID2, role);
    }

    /**
     * Removes the employee from an event.
     *
     * @param employeeID An integer containing the Employee's ID
     * @param eventID    An integer containing the event ID.
     */
    @Override
    public void removeEventFromEmployee(int employeeID, int eventID) throws SQLException, RemoteException
    {
        Employee employeeByID = getEmployeeByID(employeeID);
        ArrayList<Integer> eventIDs = employeeByID.getEvents();

        for (int i = 0; i < eventIDs.size(); i++)
        {
            if (eventIDs.get(i) == eventID)
            {
                employeeByID.removeEvent(i);
                return;
            }
        }

    }

    /**
     * Removes the employee from a message room.
     *
     * @param employeeID    An integer containing the Employee's ID
     * @param messageRoomID An integer containing the message room's ID.
     */
    @Override
    public void removeMessageRoomFromEmployee(int employeeID, int messageRoomID) throws SQLException, RemoteException
    {
        Employee employeeByID = getEmployeeByID(employeeID);
        ArrayList<Integer> messageRoomIDs = employeeByID.getMessageRooms();

        for (int i = 0; i < messageRoomIDs.size(); i++)
        {
            if (messageRoomIDs.get(i) == messageRoomID)
            {
                employeeByID.removeMessageRoom(i);
                return;
            }
        }

    }

    @Override
    public void addRoom(String roomNumber, String buildingAddress, int numberOfSeats, int floor) throws SQLException, RemoteException
    {
        Room room = api.createRoom(getLoggedEmployeeID(), roomNumber, buildingAddress, numberOfSeats, floor);
        roomList.addRoom(room);
    }

    @Override
    public void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment) throws SQLException, RemoteException
    {
        Room room = api.createRoom(getLoggedEmployeeID(), roomCode, buildingAddress, numberOfSeats, floor);
        roomList.addRoom(room);
        api.roomEquipmentSet(getLoggedEmployeeID(), room.getRoomID(), equipment.toArray(new String[0]));
    }


    @Override
    public void removeRoom(int roomID) throws RemoteException
    {
        roomList.removeRoom(roomID);
        api.roomDeleteByID(getLoggedEmployeeID(), roomID);
    }

    @Override
    public void removeRoom(Room room) throws RemoteException
    {
        int roomID = room.getRoomID();
        roomList.removeRoom(roomID);
        api.roomDeleteByID(getLoggedEmployeeID(), roomID);
    }

    @Override
    public void modifyRoom(int roomID, String roomCode, String buildingAddress, int numberOfSeats, int floor) throws SQLException, RemoteException
    {
        roomList.modifyRoom(roomID, roomCode, buildingAddress, numberOfSeats, floor);
        api.roomSetFloor(getLoggedEmployeeID(), roomID, floor);
        api.roomSetNumberOfSeats(getLoggedEmployeeID(), roomID, numberOfSeats);
        api.roomSetRoomNumber(getLoggedEmployeeID(), roomID, roomCode);
        api.roomSetBuildingAddress(getLoggedEmployeeID(), roomID, buildingAddress);
    }

    @Override
    public boolean roomEquipmentAdd(int roomID, String equipment) throws RemoteException, SQLException
    {
        getRoomByID(roomID).addEquipment(equipment);
        return api.roomEquipmentAdd(getLoggedEmployeeID(), roomID, equipment);
    }

    @Override
    public boolean roomEquipmentRemove(int roomID, String equipment) throws RemoteException, SQLException
    {
        getRoomByID(roomID).removeEquipment(equipment);
        return api.roomEquipmentRemove(getLoggedEmployeeID(), roomID, equipment);
    }

    @Override
    public boolean roomEquipmentSet(int roomID, String[] equipment) throws SQLException, RemoteException
    {
        return api.roomEquipmentSet(getLoggedEmployeeID(), roomID, equipment);
    }

    @Override
    public String[] roomEquipmentGet(int roomID) throws RemoteException
    {
        return api.roomEquipmentGet(roomID);
    }

    @Override
    public void modifyRoom(Room room, String roomCode, String buildingAddress, int numberOfSeats, int floor)
    {
        roomList.modifyRoom(room, roomCode, buildingAddress, numberOfSeats, floor);
    }

    @Override
    public int getRoomsCreated()
    {
        return RoomList.getRoomsCreated();
    }

    @Override
    public ArrayList<Room> getRooms() throws RemoteException
    {
        roomList.addAllRooms(api.getAllRooms());
        return roomList.getRooms();
    }

    @Override
    public ArrayList<Room> getRoomsByAnything(String keyword) throws RemoteException
    {
        getRooms();
        return roomList.getRoomsByAnything(keyword);
    }

    @Override
    public Room getRoomByID(int roomID) throws SQLException, RemoteException
    {
        Room room = api.getRoomByID(roomID);
        roomList.addRoom(room);
        return room;
    }



    //EVENT

    @Override
    public Event eventCreateOffline(String title, String description, int roomID, long startTime, long endTime) throws SQLException, RemoteException
    {
        return api.eventCreateOffline(getLoggedEmployeeID(), title, description, roomID, startTime, endTime);
    }

    @Override
    public Event eventCreateOnline(String title, String description, String platform, String url, long startTime, long endTime) throws SQLException, RemoteException
    {
        return api.eventCreateOnline(getLoggedEmployeeID(), title, description, platform, url, startTime, endTime);
    }
    

    @Override
    public boolean removeByEventID(int id) throws RemoteException
    {
        eventList.removeByEventID(id);
        return api.eventDeleteByID(getLoggedEmployeeID(), id);
    }


    @Override
    public Event eventSetTitle(int eventID, String title) throws SQLException, RemoteException
    {
        return api.eventSetTitle(getLoggedEmployeeID(), eventID, title);
    }

    @Override
    public Event eventSetDescription(int eventID, String description) throws SQLException, RemoteException
    {
        return api.eventSetDescription(getLoggedEmployeeID(), eventID, description);
    }

    @Override
    public Event eventSetOnlineURL(int eventID, String url) throws SQLException, RemoteException
    {
        return api.eventSetOnlineURL(getLoggedEmployeeID(), eventID, url);
    }

    @Override
    public Event eventSetPlatform(int eventID, String platform) throws SQLException, RemoteException
    {
        return api.eventSetPlatform(getLoggedEmployeeID(), eventID, platform);
    }

    @Override
    public Event eventSetOnlineState(int eventID, boolean isOnline) throws SQLException, RemoteException
    {
        return api.eventSetOnlineState(getLoggedEmployeeID(), eventID, isOnline);
    }

    @Override
    public Event eventSetTime(int eventID, long startTime, long endTime) throws SQLException, RemoteException
    {
        return api.eventSetTime(getLoggedEmployeeID(), eventID, startTime, endTime);
    }

    @Override
    public boolean eventJoin(int eventID) throws SQLException, RemoteException
    {
        return api.eventJoin(getLoggedEmployeeID(), eventID);
    }

    @Override
    public boolean eventLeave(int eventID) throws SQLException, RemoteException
    {
        return api.eventLeave(getLoggedEmployeeID(), eventID);
    }

    @Override
    public void setOnline(boolean isOnline)
    {
        //TODO add int eventID, in the parameter
//        api.eventSetOnlineState(getLoggedEmployeeID(), eventID, isOnline);
        event.setOnline(isOnline);
    }

    @Override
    public ArrayList<Integer> getParticipants()
    {
        return event.getParticipants();
    }

    @Override
    public void setParticipants(ArrayList<Integer> employees)
    {
        event.setParticipants(employees);
    }

    @Override
    public void setRoom(int room)
    {
        event.setRoom(room);
        //not used
    }

    @Override
    public int getEvent_id()
    {
        return event.getEvent_id();
        //used but can be changed
    }

    @Override
    public String getTitle()
    {
        return event.getTitle();
        //not used
    }

    @Override
    public void setTitle(String title)
    {
        event.setTitle(title);
        //not used
    }

    @Override
    public String getPlatform()
    {
        return event.getPlatform();
    }

    @Override
    public void setPlatform(String platform)
    {
        event.setPlatform(platform);
        //not used
    }

    @Override
    public int getRoomID()
    {
        return event.getRoomID();
        //not used
    }

    @Override
    public boolean isOnline()
    {
        return event.isOnline();
    }


    @Override
    public long getStartTime()
    {
        return event.getStartTime();
    }

    @Override
    public long getEndTime()
    {
        return event.getEndTime();
    }

    @Override
    public long getCreateTime()
    {
        return event.getCreateTime();
    }

    @Override
    public void add(Event event) throws IllegalArgumentException
    {
        eventList.add(event);
    }

    @Override
    public ArrayList<Event> getEvents()
    {
        return eventList.getEvents();
    }

    @Override
    public ArrayList<Event> getEventByAnything(String s, String d)
    {
        return eventList.getEventByAnything(s, d);
    }

    @Override
    public ArrayList<Event> getEventExceptDate(String s)
    {
        return eventList.getEventExceptDate(s);
    }

    @Override
    public ArrayList<Event> getEventOnlyDate(String date)
    {
        return eventList.getEventOnlyDate(date);
    }

    @Override
    public String getFormattedDateTime(long timestamp)
    {
        return new SimpleDateFormat("HH:mm:ss MM.dd.yyyy").format(timestamp);
    }

    @Override
    public ArrayList<Event> getEventsByRoom(int roomID)
    {
        return eventList.getEventsByRoom(roomID);
    }

    @Override
    public Event getEventByIndex(int index)
    {
        return eventList.getEventByIndex(index);
    }

    @Override
    public Event getEventByID(int id)
    {
        return eventList.getEventByID(id);
    }
    
    
    

    @Override
    public int getSize()
    {
        return eventList.getSize();
    }

    @Override
    public ArrayList<Integer> getParticipantsIDT()
    {
        return idT;
    }

    @Override
    public ArrayList<Employee> getParticipantsT()
    {
        return employeesT;
    }

    @Override
    public void addIDT(int id)
    {
        idT.add(id);
        addEmployeeT(id);
    }

    @Override
    public void addEmployeeT(int id)
    {
        employeesT.add(employeeList.getEmployeeByID(id));
    }

    @Override
    public void removeEmployeeT(int id)
    {
        for (int i = 0; i < employeesT.size(); i++)
        {
            if (employeesT.get(i).getId() == id)
            {
                employeesT.remove(i);
            }
        }
    }

    @Override
    public void clearTemporary()
    {
        employeesT.clear();
        idT.clear();
    }

}
