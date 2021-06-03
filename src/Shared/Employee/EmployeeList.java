package Shared.Employee;

import Shared.Event.EventList;
import Shared.Messages.MessageRoom;
import Shared.Messages.MessageRoomList;

import java.util.ArrayList;

public class EmployeeList
{
    private static int employeesCreated = 0;
    private ArrayList<Employee> employees;
    private EventList eventList;
    private MessageRoomList messageRoomList;

    public EmployeeList()
    {
        this.employees = new ArrayList<>();
    }

    public void addAllEmployees(ArrayList<Employee> newEmployeeList)
    {
        this.employees = newEmployeeList;
    }

    public void addEmployee(Employee employee)
    {
        for (Employee e:
             employees)
        {
            if (e.getId() == employee.getId())
                return;
        }
        employees.add(employee);
    }

    public void addEmployee(String username, String name, String surname, String role)
    {
        employees.add(new Employee(employeesCreated + 1, username, name, surname, role, false));
        employeesCreated++;
    }

    public void addEmployee(String username, String name, String surname, ArrayList<Integer> events,
                            ArrayList<Integer> messageRooms, String role, ArrayList<String> permissions)
    {
        employees.add(new Employee(employeesCreated + 1, username, name, surname, events, messageRooms, role, permissions, false));
        employeesCreated++;
    }

    public Employee getEmployeeByID(int ID)
    {
        for (Employee e :
                employees)
        {
            if (e.getId() == ID)
                return e;
        }
        return null;
    }


    public boolean removeEmployee(int employeeID)
    {
        for (Employee employee : employees)
        {
            if (employee.getId() == employeeID)
            {
                employee.setDeleted(true);
                return true;
            }
        }
        return false;
    }

    public boolean restoreEmployee(int employeeID)
    {
        for (Employee employee : employees)
        {
            if (employee.getId() == employeeID)
            {
                employee.setDeleted(false);
                return true;
            }
        }
        return false;
    }


    public ArrayList<Employee> getEmployees()
    {
        ArrayList<Employee> activeEmployees = new ArrayList<>();

        for (Employee e :
                employees)
        {
            if (!e.isDeleted())
                activeEmployees.add(e);
        }
        return activeEmployees;
    }

    public ArrayList<Employee> getDeletedEmployees()
    {
        ArrayList<Employee> deletedEmployees = new ArrayList<>();

        for (Employee e :
                employees)
        {
            if (e.isDeleted())
                deletedEmployees.add(e);
        }
        return deletedEmployees;
    }

    public ArrayList<Employee> getEmployeesByMessageRoom(int messageRoom)
    {
        ArrayList<Employee> employees = new ArrayList<>();

        for (int i = 0; i < getEmployees().size(); i++)
        {
            if (this.getEmployees().get(i).getMessageRooms().contains(messageRoom))
                employees.add(this.getEmployees().get(i));
        }

        return employees;
    }

    public ArrayList<Employee> getEmployeesByEvent(int eventID)
    {
        ArrayList<Employee> employees = new ArrayList<>();

        for (int i = 0; i < getEmployees().size(); i++)
        {
            if (this.getEmployees().get(i).getEvents().contains(eventID))
                employees.add(this.getEmployees().get(i));
        }

        return employees;
    }

    public ArrayList<Employee> getEmployeesByRole(String role)
    {
        ArrayList<Employee> employees = new ArrayList<>();

        for (int i = 0; i < getEmployees().size(); i++)
        {
            if (this.getEmployees().get(i).getRole().equals(role))
                employees.add(this.getEmployees().get(i));
        }

        return employees;
    }

    public ArrayList<Employee> getEmployeesByText(String text)
    {
        ArrayList<Employee> employees = new ArrayList<>();

        for (int i = 0; i < getEmployees().size(); i++)
        {
            String employeeName = getEmployees().get(i).getName() + " " + getEmployees().get(i).getSurname();

            if (getEmployees().contains(text)) employees.add(getEmployees().get(i));
        }

        return employees;
    }

    public ArrayList<Employee> getEmployeesByAnything(String keyword)
    {
        ArrayList<Employee> picked = new ArrayList<>();

        for1:
        for (Employee e :
                this.getEmployees())
        {
            if (String.valueOf(e.getId()).contains(keyword) ||
                    e.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    e.getSurname().toLowerCase().contains(keyword.toLowerCase()) ||
                    e.getRole().toLowerCase().contains(keyword.toLowerCase()))
            {
                picked.add(e);
            }
            else
            {
                for (Integer evt :
                        e.getEvents())
                {
                    if (eventList.getByID(evt).getTitle().toLowerCase()
                            .contains(keyword.toLowerCase()))
                    {
                        picked.add(e);
                        continue for1;
                    }
                }

                for (String perm :
                        e.getPermissions())
                {
                    if (perm.toLowerCase().contains(keyword.toLowerCase()))
                    {
                        picked.add(e);
                        continue for1;
                    }
                }

                for (MessageRoom room :
                        messageRoomList.getMessageRoomsByEmployeeID(e.getId()))
                {
                    if (!room.isPrivate() && room.getName().toLowerCase().contains(keyword.toLowerCase()))
                    {
                        picked.add(e);
                        continue for1;
                    }
                }
            }
        }
        return picked;
    }

    public ArrayList<Employee> getEmployeesByAnythingAlsoDeleted(String keyword)
    {
        ArrayList<Employee> picked = new ArrayList<>();

        for1:
        for (Employee e :
                employees)
        {
            if (String.valueOf(e.getId()).contains(keyword) ||
                    e.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    e.getSurname().toLowerCase().contains(keyword.toLowerCase()) ||
                    e.getRole().toLowerCase().contains(keyword.toLowerCase()))
            {
                picked.add(e);
                continue for1;
            }
            else
            {
                for (Integer evt :
                        e.getEvents())
                {
                    if (eventList.getByID(evt).getTitle().toLowerCase()
                            .contains(keyword.toLowerCase()))
                    {
                        picked.add(e);
                        continue for1;
                    }
                }

                for (String perm :
                        e.getPermissions())
                {
                    if (perm.toLowerCase().contains(keyword.toLowerCase()))
                    {
                        picked.add(e);
                        continue for1;
                    }
                }

                for (MessageRoom room :
                        messageRoomList.getMessageRoomsByEmployeeID(e.getId()))
                {
                    if (!room.isPrivate() && room.getName().toLowerCase().contains(keyword.toLowerCase()))
                    {
                        picked.add(e);
                        continue for1;
                    }
                }
            }
        }
        return picked;
    }

    public void setEventList(EventList eventList)
    {
        this.eventList = eventList;
    }

    public void setMessageRoomList(MessageRoomList messageRoomList)
    {
        this.messageRoomList = messageRoomList;
    }
}
