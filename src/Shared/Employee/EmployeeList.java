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

    public void addEmployee(Employee employee) {
        employeesCreated++;
        employees.add(employee);
    }

    public void removeEmployee(int employeeID) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == employeeID) {
                employees.remove(i);
                break;
            }
        }
    }

    public ArrayList<Employee> getEmployees()
    {
        return employees;
    }

    public ArrayList<Employee> getEmployeesByMessageRoom(int messageRoom) {
        ArrayList<Employee> employees = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getMessageRooms().contains(messageRoom)) employees.add(employees.get(i));
        }

        return employees;
    }

    public ArrayList<Employee> getEmployeesByEvent(int eventID) {
        ArrayList<Employee> employees = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEvents().contains(eventID)) employees.add(employees.get(i));
        }

        return employees;
    }

    public ArrayList<Employee> getEmployeesByRole(String role) {
        ArrayList<Employee> employees = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getRole().equals(role)) employees.add(employees.get(i));
        }

        return employees;
    }

    public ArrayList<Employee> getEmployeesByText(String text) {
        ArrayList<Employee> employees = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {
            String employeeName = employees.get(i).getName() + " " + employees.get(i).getSurname();
            if (employeeName.contains(text)) employees.add(employees.get(i));
        }

        return employees;
    }
}
