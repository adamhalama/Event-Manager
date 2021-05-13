package Shared.Employee;

import java.util.ArrayList;

public class EmployeeList
{
    private ArrayList<Employee> employees;
    private static int employeesCreated;

    public EmployeeList()
    {
        this.employees = new ArrayList<>();
    }

    public void addEmployee(String name, String surname, String role, ArrayList<String> permissions)
    {
        employeesCreated++;
        employees.add(new Employee(employeesCreated, name, surname, role, permissions));
    }

    public void removeEmployee(int employeeID)
    {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == employeeID)
            {
                employees.remove(i);
                break;
            }
        }
    }

    public void modifyEmployee(int employeeID, String name, String surname, String role, ArrayList<String> permissions)
    {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == employeeID)
            {
                employees.get(i).setName(name);
                employees.get(i).setSurname(surname);
                employees.get(i).setRole(role);
                employees.get(i).setPermissions(permissions);
                break;
            }
        }
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public ArrayList<Employee> getEmployeesByMessageRoom(int messageRoom)
    {
        ArrayList<Employee> e = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getMessageRooms().contains(messageRoom))
                e.add(employees.get(i));
        }

        return e;
    }

    public ArrayList<Employee> getEmployeesByEvent(int event)
    {
        ArrayList<Employee> e = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEvents().contains(event))
                e.add(employees.get(i));
        }

        return e;
    }

    public ArrayList<Employee> getEmployeesByRole(String role)
    {
        ArrayList<Employee> e = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getRole().equals(role))
                e.add(employees.get(i));
        }

        return e;
    }

    public ArrayList<Employee> getEmployeesByName(String name)
    {
        ArrayList<Employee> e = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getName().equals(name))
                e.add(employees.get(i));
        }

        return e;
    }

    public ArrayList<Employee> getEmployeesBySurname(String surname)
    {
        ArrayList<Employee> e = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getSurname().equals(surname))
                e.add(employees.get(i));
        }

        return e;
    }

}
