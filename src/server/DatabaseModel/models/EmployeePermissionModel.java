package server.DatabaseModel.models;

import server.DatabaseModel.Utils.DBResponse;
import server.DatabaseModel.Utils.ResponseRow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeePermissionModel extends Model
{

  public EmployeePermissionModel(Connection connection) {
    super(connection, "permission");
  }

  public boolean exists(String permission, int userID)
  {
    try
    {
      DBResponse dbResponse = super.modelGetAll(null, "permission = '" + permission + "' AND employee_id = " + userID , 1, 0);
      return dbResponse.getRawRows().size() >= 1;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }

  public String[] getAllByID(int userID)
  {
    ArrayList<String> equipment = new ArrayList<>();
    try
    {
      DBResponse dbResponse = super.modelGetAll(null, "employee_id = " + userID, 0, 0);
      ArrayList<ResponseRow> responseRows = dbResponse.getRows();
      for(ResponseRow row : responseRows)
      {
        equipment.add(row.getField("permission"));
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return equipment.toArray(new String[0]);
  }

  public boolean delete(String permission, int userID)
  {
    try
    {
      return super.modelDelete("permission = '" + permission + "' AND employee_id = " + userID);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }

  public boolean create(String permission, int userID)
  {
    try
    {
      DBResponse dbResponse = super
          .modelInsert(new String[] {"permission", "employee_id"},
              new String[] {"'" + permission + "'", String.valueOf(userID)});
      return dbResponse.getRawRows().size() >= 1;
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }
}
