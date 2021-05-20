package server.DatabaseModel.models;

import Shared.Employee.Employee;
import server.DatabaseModel.Utils.DBResponse;
import server.DatabaseModel.Utils.ResponseRow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel extends Model
{

  public EmployeeModel(Connection connection) {
    super(connection, "employee");
  }

  private ArrayList<Employee> getEmployeesFromResponse(DBResponse dbResponse) {
    ArrayList<ResponseRow> rows = dbResponse.getRows();
    ArrayList<Employee> employees = new ArrayList<>();
    for(ResponseRow row : rows) {
      int id = Integer.parseInt(row.getField("id"));
      String username = row.getField("username");
      String name = row.getField("name");
      String surname = row.getField("surname");
      String role = row.getField("role");
      employees.add(new Employee(id, username, name, surname, role));
    }
    return employees;
  }

  public ArrayList<Employee> getAll()
  {
    return this.getAll(null, null, 0, 0);
  }
  public ArrayList<Employee> getAll(String order)
  {
    return this.getAll(order);
  }
  public ArrayList<Employee> getAll(String order, int limit, int offset)
  {
    return this.getAll(order, null, limit, offset);
  }
  public ArrayList<Employee> getAllWhere(String where)
  {
    return this.getAll(null, where, 0, 0);
  }
  public ArrayList<Employee> getAllWhere(String where, int limit, int offset)
  {
    return this.getAll(null, where, limit, offset);
  }
  public ArrayList<Employee> getAll(String order, String where, int limit, int offset)
  {
    ArrayList<Employee> employees = new ArrayList<>();
    try
    {
      DBResponse dbResponse = super.modelGetAll(order, where, limit, offset);
      employees = getEmployeesFromResponse(dbResponse);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return employees;
  }

  public Employee getOne(String where)
      throws SQLException
  {
    return this.getOne(where, null);
  }
  public Employee getOne(String where, String order)
      throws SQLException
  {
    DBResponse dbResponse = super.modelGetOne(where, order);
    return getEmployeesFromResponse(dbResponse).get(0);
  }

  public Employee create(String username, String password, String name, String surname, String role)
      throws SQLException
  {
    DBResponse dbResponse = super.modelInsert(
        new String[] {
            "username", "password", "name", "surname", "role"
        },
        formatStringValues(new String[] {
            username, password, name, surname, role
        })
    );
    return getEmployeesFromResponse(dbResponse).get(0);
  }

  public ArrayList<Employee> edit(String[] fields, String[] values, String where)
      throws SQLException
  {
    DBResponse dbResponse = super.modelUpdate(fields, values, where);
    return getEmployeesFromResponse(dbResponse);
  }
}
