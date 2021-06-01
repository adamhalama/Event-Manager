package Shared;

import Shared.Employee.Employee;
import Shared.Event.Event;
import Shared.Messages.Message;
import Shared.Messages.MessageRoom;
import Shared.Room.Room;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface API extends Remote
{
    Employee employeeRegister(String username, String password, String name, String surname, String role)
            throws IOException, GeneralSecurityException, SQLException, RemoteException;

    Employee employeeLogin(String username, String password)
            throws IOException, GeneralSecurityException, SQLException, RemoteException;

    Employee employeeDelete(int employeeID1, int employeeID2) throws SQLException, RemoteException;

    Employee employeeRestore(int employeeID1, int employeeID2) throws SQLException, RemoteException;

    Employee employeeGetByID(int employeeID) throws SQLException, RemoteException;

    ArrayList<Employee> employeeGetByIDs(ArrayList<Integer> employeesIDs) throws RemoteException;

    ArrayList<Employee> employeeGetAll() throws RemoteException;

    Employee employeeSetName(int employeeID1, int employeeID2, String name) throws SQLException, RemoteException;

    Employee employeeSetSurname(int employeeID1, int employeeID2, String surname) throws SQLException, RemoteException;

    Employee employeeSetRole(int employeeID1, int employeeID2, String role) throws SQLException, RemoteException;

    Employee employeeSetPassword(int employeeID1, int employeeID2, String password) throws GeneralSecurityException, SQLException, IOException, RemoteException;

    Employee employeeSetUsername(int employeeID1, int employeeID2, String username) throws SQLException, RemoteException;

    Employee employeePermissionAdd(int employeeID1, int employeeID2, String permission) throws SQLException, RemoteException;

    Employee employeePermissionRemove(int employeeID1, int employeeID2, String permission) throws SQLException, RemoteException;

    Employee employeePermissionSet(int employeeID1, int employeeID2, String[] permissions) throws SQLException, RemoteException;

    Room roomGetByID(int roomID) throws SQLException, RemoteException;

    ArrayList<Room> roomGetByIDs(ArrayList<Integer> roomIDs) throws RemoteException;

    ArrayList<Room> roomGetAll() throws RemoteException;

    Room roomCreate(int employeeID1, String roomNumber, String buildingAddress, int numberOfSeats, int floor) throws SQLException, RemoteException;

    boolean roomDeleteByID(int employeeID1, int roomID) throws RemoteException;

    Room roomSetFloor(int employeeID1, int roomID, int floor) throws SQLException, RemoteException;

    Room roomSetNumberOfSeats(int employeeID1, int roomID, int numberOfSeats) throws SQLException, RemoteException;

    Room roomSetRoomNumber(int employeeID1, int roomID, String roomNumber) throws SQLException, RemoteException;

    Room roomSetBuildingAddress(int employeeID1, int roomID, String buildingAddress) throws SQLException, RemoteException;

    String[] roomEquipmentGet(int roomID) throws RemoteException;

    boolean roomEquipmentAdd(int employeeID1, int roomID, String equipment) throws SQLException, RemoteException;

    boolean roomEquipmentRemove(int employeeID1, int roomID, String equipment) throws SQLException, RemoteException;

    boolean roomEquipmentSet(int employeeID1, int roomID, String[] equipment) throws SQLException, RemoteException;

    MessageRoom messageRoomGetByID(int messageRoomID) throws SQLException, RemoteException;

    ArrayList<MessageRoom> messageRoomGetByIDs(ArrayList<Integer> messageRoomIDs) throws RemoteException;

    ArrayList<MessageRoom> messageRoomGetPrivate(int employeeID1) throws RemoteException;

    ArrayList<MessageRoom> messageRoomGetAll(int employeeID1) throws RemoteException;

    MessageRoom messageRoomCreatePrivate(int employeeID1, int employeeID2) throws SQLException, RemoteException;

    MessageRoom messageRoomSetName(int employeeID1, int messageRoomID, String name) throws SQLException, RemoteException;

    ArrayList<Message> messagesGet(int employeeID1, int messageRoomID, int offset) throws RemoteException;

    Message messagePost(int employeeID1, int messageRoomID, String message) throws SQLException, RemoteException;

    Event eventGetByID(int eventID) throws SQLException, RemoteException;

    ArrayList<Event> eventGetByIDs(ArrayList<Integer> eventIDs) throws RemoteException;

    ArrayList<Event> eventGetAll() throws RemoteException;

    Event eventCreate(int employeeID1, int messageRoomID, int roomID, int creatorID, long timeStart, long timeEnd, String title, String description, String platform, String onlineLink) throws SQLException, RemoteException;

    /*Event eventCreateOffline(int employeeID1, String title, String description, int roomID, long startTime, long endTime) throws SQLException, RemoteException;

    Event eventCreateOnline(int employeeID1,String title, String description, String platform, String url, long startTime, long endTime) throws SQLException, RemoteException;*/

    boolean eventDeleteByID(int employeeID1, int eventID) throws RemoteException;

    Event eventSetTitle(int employeeID1, int eventID, String title) throws SQLException, RemoteException;

    Event eventSetDescription(int employeeID1, int eventID, String description) throws SQLException, RemoteException;

    Event eventSetOnlineURL(int employeeID1, int eventID, String url) throws SQLException, RemoteException;

    Event eventSetPlatform(int employeeID1, int eventID, String platform) throws SQLException, RemoteException;

    Event eventSetOnlineState(int employeeID1, int eventID, boolean isOnline) throws SQLException, RemoteException;

    Event eventSetTime(int employeeID1, int eventID, long startTime, long endTime) throws SQLException, RemoteException;

    boolean eventJoin(int employeeID1, int eventID) throws SQLException, RemoteException;

    boolean eventLeave(int employeeID1, int eventID) throws SQLException, RemoteException;
}