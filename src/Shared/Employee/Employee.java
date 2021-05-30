package Shared.Employee;

import Shared.Event.Event;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Represents one Employee.
 *
 * @author Group 6 - 2Y ICT A21
 * @version 1.0 - May 2021
 * @since 1.0
 */
public class Employee implements Serializable
{
    private int id;
    private String username;
    private String name;
    private String surname;
    private boolean deleted;
    /**
     * ArrayList of integers representing eventID's
     */
    private ArrayList<Integer> events;
    /**
     * ArrayList of integers representing messageRoom ID, representing messageRooms that the employee is in.
     */
    private ArrayList<Integer> messageRooms;
    private String role;
    /**
     * ArrayList of predefined strings that are used for permission verification.
     */
    private ArrayList<String> permissions;

    /**
     * Eight-parameter constructor.
     * Used for adding employees from the database - the arraylists are passed and not created empty.
     *
     * @param id           An integer containing the system ID of the employee.
     * @param username     A string containing the username.
     * @param name         A string containing the first name of the employee.
     * @param surname      A string containing the surname of the employee.
     * @param events       An ArrayList of integers containing the event IDs the employee is in.
     * @param messageRooms An ArrayList of integers containing the message room IDs the employee is in.
     * @param role         A string containing the role of the employee, in a firm or a team.
     * @param permissions  An ArrayList of predefined strings that are used for permission verification.
     */
    public Employee(int id, String username, String name, String surname, ArrayList<Integer> events,
                    ArrayList<Integer> messageRooms, String role, ArrayList<String> permissions, boolean deleted)
    {
        this.id = id;
        this.name = name;
        this.username = username;
        this.surname = surname;
        this.events = events;
        this.messageRooms = messageRooms;
        this.role = role;
        this.permissions = permissions;
        this.deleted = deleted;
    }

    /**
     * Four-parameter constructor.
     * Used for creating new employees - the arraylists are created empty.
     *
     * @param id       An integer containing the system ID of the employee.
     * @param username A string containing the username.
     * @param name     A string containing the first name of the employee.
     * @param surname  A string containing the surname of the employee.
     * @param role     A string containing the role of the employee, in a firm or a team.
     */
    public Employee(int id, String username, String name, String surname, String role, boolean deleted)
    {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.events = new ArrayList<>();
        this.messageRooms = new ArrayList<>();
        this.role = role;
        this.permissions = new ArrayList<>();
        this.deleted = deleted;
    }

    /**
     * Sets the events the employee participates in.
     *
     * @param events An ArrayList of integers containing event IDs.
     */
    public void setEvents(ArrayList<Integer> events)
    {
        this.events = events;
    }

    /**
     * Adds the employee into an event.
     *
     * @param event An integer containing the event ID.
     */
    public void addEvent(int event)
    {
        events.add(event);
    }

    /**
     * Removes the employee from event based on the parameter.
     * @param atIndex An int containing the index the event is on in the ArrayList in Employee.
     */
    public void removeEvent(int atIndex)
    {
        events.remove(atIndex);
    }

    /**
     * Sets the message room IDs.
     *
     * @param rooms An ArrayList of ints containing the MessageRoom IDs
     */
    public void setMessageRooms(ArrayList<Integer> rooms)
    {
        this.messageRooms = rooms;
    }

    /**
     * Adds the employee to a specified message room.
     *
     * @param messageRoom An int containing the message room id.
     */
    public void addMessageRoom(int messageRoom)
    {
        messageRooms.add(messageRoom);
    }

    /**
     * Removes the employee from message room based on the parameter.
     * @param atIndex An int containing the index the messageRoom is on in the ArrayList in Employee.
     */
    public void removeMessageRoom(int atIndex)
    {
        messageRooms.remove(atIndex);
    }

    /**
     * Sets the permissions of the employee.
     * @param permissions An arraylist of permissions containing the new permissions for the employee.
     */
    public void setPermissions(ArrayList<String> permissions)
    {
        this.permissions = permissions;
    }

    /**
     * Adds a single permission.
     * @param permission A String containing the permission.
     */
    public void addPermission(String permission)
    {
        permissions.add(permission);
    }

    /**
     * Removes a permission from an employee.
     * @param permission A string containing the permission.
     */
    public void removePermission(String permission)
    {
        permissions.remove(permission);
    }

    /**
     * Sets the name of the employee.
     * @param name A string containing the name.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Sets the surname of the employee.
     * @param surname A string containing the surname.
     */
    public void setSurname(String surname)
    {
        this.surname = surname;
    }


    /**
     * Sets the username of the employee.
     * @param username A string containing the username.
     */
    public void setUsername(String username)
    {
        this.username = username;
    }


    /**
     * Sets the role of the employee.
     * @param role A string containing the role.
     */
    public void setRole(String role)
    {
        this.role = role;
    }

    /**
     * Sets the deleted state of the employee.
     * @param deleted A boolean if the employee is deleted.
     */
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

    /**
     * Gets the id.
     * @return an integer representing the ID.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Gets the name.
     * @return An string representing the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the name.
     * @return An string representing the surname.
     */
    public String getSurname()
    {
        return surname;
    }

    /**
     * Gets the events the employee is in, in an arraylist.
     * @return An int ArrayList representing the eventIds
     */
    public ArrayList<Integer> getEvents()
    {
        return events;
    }

    /**
     * Gets the message rooms the employee is in, in an arraylist.
     * @return An int ArrayList representing the messageRoom IDs
     */
    public ArrayList<Integer> getMessageRooms()
    {
        return messageRooms;
    }

    /**
     * Gets all the employees permissions.
     * @return A String arrayList representing the permissions.
     */
    public ArrayList<String> getPermissions()
    {
        return permissions;
    }

    /**
     * Gets the role of the employee.
     * @return A string with the role.
     */
    public String getRole()
    {
        return role;
    }

    /**
     * Gets the full name of the employee.
     * @return A string consisting of the name and the surname.
     */
    public String getFullName()
    {
        return name + " " + surname;
    }

    /**
     * Gets the deleted state of the employee.
     * @return A boolean of employee deleted state.
     */
    public boolean getDeleted()
    {
        return deleted;
    }

    /**
     * toString method containing all the non arraylist variables in one string.
     * @return A string representing the data.
     */
    @Override
    public String toString()
    {
        return "{" + "id=" + id + ", username='" + username + '\''
                + ", name='" + name + '\'' + ", surname='" + surname + '\''
                + ", role='" + role + ", deleted=" + deleted + "}";
    }
}
