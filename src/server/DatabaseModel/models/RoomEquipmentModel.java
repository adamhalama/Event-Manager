package server.DatabaseModel.models;

import Shared.Room.Room;
import server.DatabaseModel.Utils.DBResponse;
import server.DatabaseModel.Utils.ResponseRow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomEquipmentModel extends Model
{

  public RoomEquipmentModel(Connection connection) {
    super(connection, "room_equipment");
  }

  public boolean exists(String equipment, int roomID)
  {
    try
    {
      DBResponse dbResponse = super.modelGetAll(null, "equipment = '" + equipment + "' AND room_id = " + roomID , 1, 0);
      return dbResponse.getRawRows().size() >= 1;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }

  public String[] getAllByID(int roomID)
  {
    ArrayList<String> equipment = new ArrayList<>();
    try
    {
      DBResponse dbResponse = super.modelGetAll(null, "room_id = " + roomID, 0, 0);
      ArrayList<ResponseRow> responseRows = dbResponse.getRows();
      for(ResponseRow row : responseRows)
      {
        equipment.add(row.getField("equipment"));
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return equipment.toArray(new String[0]);
  }

  public boolean create(String equipment, int roomID)
  {
    try
    {
      DBResponse dbResponse = super.modelInsert(
          new String[] {"equipment", "room_id"},
          new String[] {"'" + equipment + "'", String.valueOf(roomID)}
      );
      return dbResponse.getRawRows().size() >= 1;
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }
}
