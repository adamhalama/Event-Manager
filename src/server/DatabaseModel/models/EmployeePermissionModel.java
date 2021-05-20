package server.DatabaseModel.models;

import server.DatabaseModel.Utils.DBResponse;

import java.sql.Connection;
import java.sql.SQLException;

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
