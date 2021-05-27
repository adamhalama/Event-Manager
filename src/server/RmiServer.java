package server;

import Shared.API;
import Shared.Employee.Employee;
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

    @Override
    public Employee registerEmployee(String username, String password, String name, String surname, String role)
        throws GeneralSecurityException, IOException, SQLException
    {
        String encryptedPassword = Crypt.encryptPassword(password);
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.create(username, encryptedPassword, name, surname, role), this.databaseHandler);
    }

    @Override
    public Employee loginEmployee(String username, String password)
        throws GeneralSecurityException, IOException, SQLException
    {
        String encryptedPassword = Crypt.encryptPassword(password);
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.getByUsernameAndPassword(username, encryptedPassword), this.databaseHandler);
    }

    @Override
    public Employee getEmployee(int employeeID) throws SQLException
    {
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.getByID(employeeID), this.databaseHandler);
    }

    @Override
    public ArrayList<Employee> getEmployees(ArrayList<Integer> employeesIDs) {
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
    public Employee employeePermissionAdd(int employeeID1, int employeeID2, String permission) throws SQLException
    {
        this.checkPermission(employeeID1, "employees_create_edit");
        this.databaseHandler.employeePermission.create(permission, employeeID2);
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.getByID(employeeID2), this.databaseHandler);
    }

    @Override
    public Room getRoom(int roomID) throws SQLException
    {
        return ObjectInfo.getFullRoom(this.databaseHandler.room.getByID(roomID), this.databaseHandler);
    }

    @Override
    public ArrayList<Room> getRooms(ArrayList<Integer> roomIDs) {
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
    public Room roomCreate(int employeeID1, String roomNumber, String buildingAddress, int numberOfSeats, int floor) throws SQLException
    {
        this.checkPermission(employeeID1, "room_create_edit");
        Room room = this.databaseHandler.room.create(roomNumber, buildingAddress, numberOfSeats, floor);
        return ObjectInfo.getFullRoom(room, this.databaseHandler);
    }

    @Override
    public MessageRoom getMessageRoom(int messageRoomID) throws SQLException
    {
        return this.databaseHandler.messageRoom.getByID(messageRoomID);
    }

    @Override
    public ArrayList<MessageRoom> getMessageRooms(ArrayList<Integer> messageRoomIDs) {
        ArrayList<MessageRoom> rooms = new ArrayList<>();
        try {
            for(Integer roomID : messageRoomIDs) {
                rooms.add(this.databaseHandler.messageRoom.getByID(roomID));
            }
        } catch (Error | SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public MessageRoom createPrivateMessageRoom(int employeeID1, int employeeID2) throws SQLException
    {
        this.checkPermission(employeeID1, "chat_rooms_create_edit");
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
                    return entry.getValue();
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
        return messageRoom;
    }
}