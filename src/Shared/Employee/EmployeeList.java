package Shared.Employee;

import Shared.Event.EventList;
import Shared.Messages.MessageRoom;
import Shared.Messages.MessageRoomList;

import java.util.ArrayList;

public class EmployeeList
{
    private ArrayList<Employee> employees;
    private static int employeesCreated = 0;

    private EventList eventList;
    private MessageRoomList messageRoomList;

    public EmployeeList()
    {
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee)
    {
        employees.add(employee);
        employeesCreated++;
    }

    public void addEmployee(String username, String name, String surname, String role)
    {
        employees.add(new Employee(employeesCreated + 1, username, name, surname, role));
        employeesCreated++;
    }

    public void addEmployee(String username, String name, String surname, ArrayList<Integer> events,
                     ArrayList<Integer> messageRooms, String role, ArrayList<String> permissions)
    {
        employees.add(new Employee(employeesCreated + 1, username, name, surname, events, messageRooms, role, permissions));
        employeesCreated++;
    }

    public Employee getEmployeeByID(int ID)
    {
        for (Employee e:
             employees)
        {
            if(e.getId() == ID)
                return e;
        }
        return null;
    }


    public void removeEmployee(int employeeID)
    {
        for (int i = 0; i < employees.size(); i++)
        {
            if (employees.get(i).getId() == employeeID)
            {
                employees.remove(i);
                break;
            }
        }
    }

    public ArrayList<Employee> getEmployees()
    {
        return employees;
    }

    public ArrayList<Employee> getEmployeesByMessageRoom(int messageRoom)
    {
        ArrayList<Employee> employees = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++)
        {
            if (employees.get(i).getMessageRooms().contains(messageRoom)) employees.add(employees.get(i));
        }

        return employees;
    }

    public ArrayList<Employee> getEmployeesByEvent(int eventID)
    {
        ArrayList<Employee> employees = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++)
        {
            if (employees.get(i).getEvents().contains(eventID)) employees.add(employees.get(i));
        }

        return employees;
    }

    public ArrayList<Employee> getEmployeesByRole(String role)
    {
        ArrayList<Employee> employees = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++)
        {
            if (employees.get(i).getRole().equals(role)) employees.add(employees.get(i));
        }

        return employees;
    }

    public ArrayList<Employee> getEmployeesByText(String text)
    {
        ArrayList<Employee> employees = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++)
        {
            String employeeName = employees.get(i).getName() + " " + employees.get(i).getSurname();
            if (employeeName.contains(text)) employees.add(employees.get(i));
        }

        return employees;
    }

    public ArrayList<Employee> getEmployeesByAnything(String keyword)
    {
        ArrayList<Employee> picked = new ArrayList<>();

        for1:
        for (Employee e :
                employees)
        {
            if (String.valueOf(e.getId()).contains(keyword) ||
                    e.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    e.getSurname().toLowerCase().contains(keyword.toLowerCase()) ||
                    e.getRole().toLowerCase().contains(keyword.toLowerCase())
            )
            {
                picked.add(e);
            } else if (!picked.contains(e))
            {
                for (Integer evt:
                     e.getEvents())
                {
                    if (eventList.getEventByID(evt).getTitle().toLowerCase()
                            .contains(keyword.toLowerCase()))
                        picked.add(e); continue for1;
                }
            } else if(!picked.contains(e))
            {
                for (String perm:
                     e.getPermissions())
                {
                    if (perm.toLowerCase().contains(keyword.toLowerCase()))
                        picked.add(e); continue for1;
                }
            } else if(!picked.contains(e))
            {
                for (MessageRoom room:
                     messageRoomList.getMessageRoomsByEmployeeID(e.getId()))
                {
                    if (!room.isPrivate() && room.getName().toLowerCase().contains(keyword.toLowerCase()))
                        picked.add(e); continue for1;
                }
            }
        }

        return picked;
    }

    public void setEventList(EventList eventList)
    {
        this.eventList = eventList;
    }
    public void setMessageRoomList(MessageRoomList messageRoomList){this.messageRoomList = messageRoomList;}
}
