package server.DatabaseModel.models;

import Shared.Room.Room;
import server.DatabaseModel.Utils.DBResponse;
import server.DatabaseModel.Utils.ResponseRow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomModel extends Model
{

  public RoomModel(Connection connection)
  {
    super(connection, "room");
  }

  private ArrayList<Room> getRoomsFromResponse(DBResponse dbResponse)
  {
    ArrayList<ResponseRow> rows = dbResponse.getRows();
    ArrayList<Room> rooms = new ArrayList<>();
    for (ResponseRow row : rows)
    {
      int id = Integer.parseInt(row.getField("id"));
      int floor = Integer.parseInt(row.getField("floor"));
      int numberOfSeats = Integer.parseInt(row.getField("number_of_seats"));
      String roomNo = row.getField("room_no");
      String buildingAddress = row.getField("building_address");
      rooms.add(new Room(id, roomNo, buildingAddress, numberOfSeats, floor));
    }
    return rooms;
  }

  public ArrayList<Room> getAll()
  {
    return this.getAll(null, null, 0, 0);
  }

  public ArrayList<Room> getAll(String order)
  {
    return this.getAll(order);
  }

  public ArrayList<Room> getAll(String order, int limit, int offset)
  {
    return this.getAll(order, null, limit, offset);
  }

  public ArrayList<Room> getAllWhere(String where)
  {
    return this.getAll(null, where, 0, 0);
  }

  public ArrayList<Room> getAllWhere(String where, int limit, int offset)
  {
    return this.getAll(null, where, limit, offset);
  }

  public ArrayList<Room> getAll(String order, String where, int limit,
      int offset)
  {
    ArrayList<Room> rooms = new ArrayList<>();
    try
    {
      DBResponse dbResponse = super.modelGetAll(order, where, limit, offset);
      rooms = getRoomsFromResponse(dbResponse);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return rooms;
  }

  public Room getByID(int id) throws SQLException
  {
    return this.getOne("id = " + id, null);
  }

  public Room getOne(String where) throws SQLException
  {
    return this.getOne(where, null);
  }

  public Room getOne(String where, String order) throws SQLException
  {
    DBResponse dbResponse = super.modelGetOne(where, order);
    return getRoomsFromResponse(dbResponse).get(0);
  }

  public Room create(String roomNumber, String buildingAddress, int numberOfSeats, int floor) throws SQLException
  {
    DBResponse dbResponse = super.modelInsert(
        new String[] {"room_no", "floor", "building_address", "number_of_seats"},
        new String[] {
            "'" + roomNumber+ "'",
            String.valueOf(floor),
            "'" + buildingAddress+ "'",
            String.valueOf(numberOfSeats)
        }
    );
    return getRoomsFromResponse(dbResponse).get(0);
  }

  public ArrayList<Room> edit(String[] fields, String[] values, String where) throws SQLException
  {
    DBResponse dbResponse = super.modelUpdate(fields, values, where);
    return getRoomsFromResponse(dbResponse);
  }
}
