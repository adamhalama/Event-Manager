package Shared.Employee;

import Shared.Event.EventList;
import Shared.Messages.MessageRoom;
import Shared.Messages.MessageRoomList;

import java.util.ArrayList;

public class EmployeeList
{
    /**
     * An integer storing how many employees has been created. Initial 0 in the beginning.
     */
    private static int employeesCreated = 0;
    /**
     * An arraylist containing all the employees.
     */
    private ArrayList<Employee> employees;
    /**
     * An EventList object for reading events.
     */
    private EventList eventList;
    /**
     * A MessageRoomList for adding employees into the message rooms.
     */
    private MessageRoomList messageRoomList;

    /**
     * A zero - argument constructor for initial the employees arraylist.
     */
    public EmployeeList()
    {
        this.employees = new ArrayList<>();
    }

    /**
     * Add a list of employees into the list.
     * @param newEmployeeList An arraylist of new employee list.
     */
    public void addAllEmployees(ArrayList<Employee> newEmployeeList)
    {
        this.employees = newEmployeeList;
    }

    /**
     * Add a single employee if it doesn't already exist.
     * @param employee An Employee object.
     */
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

    // For test users in ModelManager
    public void addEmployee(String username, String name, String surname, String role)
    {
        employees.add(new Employee(employeesCreated + 1, username, name, surname, role, false));
        employeesCreated++;
    }
    // For test users in ModelManager
    public void addEmployee(String username, String name, String surname, ArrayList<Integer> events,
                            ArrayList<Integer> messageRooms, String role, ArrayList<String> permissions)
    {
        employees.add(new Employee(employeesCreated + 1, username, name, surname, events, messageRooms, role, permissions, false));
        employeesCreated++;
    }

    /**
     * Gets employee by employeeID.
     * @param ID An integer storing the ID of employee want to search.
     * @return An Employee object as matched employee or null means no matched employee found.
     */
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


    /**
     * Remove an employee by its ID.
     * @param employeeID An integer storing the employee ID.
     * @return True - the employee has been removed; False - the employee hasn't been removed.
     */
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

    /**
     * Restore an employee by its ID.
     * @param employeeID An integer storing the employee ID.
     * @return True - the employee has been restored; False - the employee hasn't been restored.
     */
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


    /**
     * Gets all the employees.
     * @return An arraylist containing all the employees.
     */
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

    /**
     * Gets all the employees in a specific message room.
     * @param messageRoom An integer storing the message room ID.
     * @return An arraylist of all the matched employees.
     */
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

    /**
     * Gets all the employees in a specific event.
     * @param eventID An integer storing the event ID.
     * @return An arraylist of all the matched employees.
     */
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

    /**
     * Gets all the employees by a specific role.
     * @param role A string storing the employee role.
     * @return An arraylist of all the matched employees.
     */
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

    /**
     * Gets all the employees by a specific text
     * @param text A string storing searching text.
     * @return An arraylist of matched employees.
     */
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

    /**
     * Gets employees by any key words.
     * @param keyword A string storing the key words.
     * @return An arraylist containing the matched employees.
     */
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

    /**
     * Sets the new event list.
     * @param eventList An EventList object containing a list of events need to add.
     */
    public void setEventList(EventList eventList)
    {
        this.eventList = eventList;
    }

    /**
     * Sets new message room list.
     * @param messageRoomList A MessageRoomList object containing a list of message rooms need to add.
     */
    public void setMessageRoomList(MessageRoomList messageRoomList)
    {
        this.messageRoomList = messageRoomList;
    }
}
