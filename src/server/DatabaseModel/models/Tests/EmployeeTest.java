package server.DatabaseModel.models.Tests;

import Shared.Employee.Employee;
import server.DatabaseModel.DatabaseHandler;
import server.DatabaseModel.models.Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeTest
{
  public static void main(String[] args) throws SQLException
  {
    DatabaseHandler databaseHandler = new DatabaseHandler();

    /*--CREATE--*/
    /*Employee employee = databaseHandler.employee.create("JackHamer", "123qweasd", "Jack", "Hamer", "Admin");
    System.out.println(employee);*/

    /*--GET ONE--*/
    //Employee employee = databaseHandler.employee.getByID(1); // Get by id
    //Employee employee = databaseHandler.employee.getByUsernameAndPassword("JackHamer", "6YXQoRRvhxWpbXSmsoO1eQ==:uLGnvQ44iDBwUUQCs8YsmA=="); // Get by credentials
    //Employee employee = databaseHandler.employee.getOne("id = 1"); // Get one where
    //Employee employee = databaseHandler.employee.getOne("id = 1", "id DESC"); // Get one where and sorted
    //System.out.println(employee);

    /*--GET MANY--*/
    //ArrayList<Employee> employees = databaseHandler.employee.getAll(); // Get everything
    //ArrayList<Employee> employees = databaseHandler.employee.getAll("id DESC"); // Get ordered by descending id
    //ArrayList<Employee> employees = databaseHandler.employee.getAll("id DESC", 5, 0); // Get max 5 rows ordered by descending id
    //ArrayList<Employee> employees = databaseHandler.employee.getAllWhere("name = 'Jack'"); // Get where
    //ArrayList<Employee> employees = databaseHandler.employee.getAllWhere("name = 'Jack'", 3, 0); // Get max 3 where
    //ArrayList<Employee> employees = databaseHandler.employee.getAll("id DESC", "name = 'Jack'", 3, 0); // Get max 3 where and sorted in descending order

    /*--EDIT--*/
    /*ArrayList<Employee> employees = databaseHandler.employee.edit(
      new String[] {"name", "surname"},
      Model.formatStringValues(new String[] {"Jason","Statham"}),
      "id = 1"
    );

    for (Employee employee : employees) {
      System.out.println(employee);
    }*/

    /*--DELETE--*/
    //Employee employee = databaseHandler.employee.deleteByID(1); // Set deleted state to true for an employee
    /*Employee employee = databaseHandler.employee.restoreByID(1); // Set deleted state to false for an employee
    System.out.println(employee);*/
  }
}
