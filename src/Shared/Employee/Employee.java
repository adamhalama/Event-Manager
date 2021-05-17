package Shared.Employee;

import java.util.ArrayList;

public class Employee
{
    private int id;
    private String username;
    private String name;
    private String surname;
    private ArrayList<Integer> events;
    private ArrayList<Integer> messageRooms;
    private String role;
    private ArrayList<String> permissions;

    public Employee(int id, String username, String name, String surname, ArrayList<Integer> events,
                    ArrayList<Integer> messageRooms, String role, ArrayList<String> permissions)
    {
        this.id = id;
        this.name = name;
        this.username = username;
        this.surname = surname;
        this.events = events;
        this.messageRooms = messageRooms;
        this.role = role;
        this.permissions = permissions;
    }

    public Employee(int id, String username, String name, String surname, String role)
    {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.events = new ArrayList<>();
        this.messageRooms = new ArrayList<>();
        this.role = role;
        this.permissions = new ArrayList<>();
    }

    public void addEvent(int event)
    {
        events.add(event);
    }

    public void removeEvent(int event)
    {
        events.remove(event);
    }

    public void addMessageRoom(int messageRoom)
    {
        messageRooms.add(messageRoom);
    }

    public void removeMessageRoom(int messageRoom)
    {
        for (int i = 0; i < messageRooms.size(); i++)
        {
            if (messageRooms.get(i).equals(messageRoom))
            {
                messageRooms.remove(i);
                break;
            }
        }

    }

    public void setPermissions(ArrayList<String> permissions)
    {
        this.permissions = permissions;
    }

    public void addPermission(String permission)
    {
        permissions.add(permission);
    }

    public void removePermission(String permission)
    {
        permissions.remove(permission);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public ArrayList<Integer> getEvents()
    {
        return events;
    }

    public ArrayList<Integer> getMessageRooms()
    {
        return messageRooms;
    }

    public ArrayList<String> getPermissions()
    {
        return permissions;
    }

    public String getRole()
    {
        return role;
    }


}
