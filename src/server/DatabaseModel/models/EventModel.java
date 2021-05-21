package server.DatabaseModel.models;

import Shared.Event.Event;
import server.DatabaseModel.Utils.DBResponse;
import server.DatabaseModel.Utils.ResponseRow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventModel extends Model
{

  public EventModel(Connection connection) {
    super(connection, "event");
  }

  private ArrayList<Event> getEmployeesFromResponse(DBResponse dbResponse) {
    ArrayList<ResponseRow> rows = dbResponse.getRows();
    ArrayList<Event> events = new ArrayList<>();
    for(ResponseRow row : rows) {
      int id = Integer.parseInt(row.getField("id"));
      int roomID = Integer.parseInt(row.getField("room_id"));
      int creatorID = Integer.parseInt(row.getField("creator"));
      int messageRoomID = Integer.parseInt(row.getField("message_room_id"));
      long createTime = Integer.parseInt(row.getField("create_time"));
      long startTime = Integer.parseInt(row.getField("start_time"));
      long endTime = Integer.parseInt(row.getField("end_time"));
      String title = row.getField("title");
      String description = row.getField("description");
      boolean isOnline = Boolean.parseBoolean(row.getField("is_online"));
      String platform = row.getField("platform");
      String url = row.getField("url");
      // TODO: 5/21/2021 Fill correct values after event class is refactored
      events.add(new Event());
    }
    return events;
  }

  public ArrayList<Event> getAll()
  {
    return this.getAll(null, null, 0, 0);
  }
  public ArrayList<Event> getAll(String order)
  {
    return this.getAll(order);
  }
  public ArrayList<Event> getAll(String order, int limit, int offset)
  {
    return this.getAll(order, null, limit, offset);
  }
  public ArrayList<Event> getAllWhere(String where)
  {
    return this.getAll(null, where, 0, 0);
  }
  public ArrayList<Event> getAllWhere(String where, int limit, int offset)
  {
    return this.getAll(null, where, limit, offset);
  }
  public ArrayList<Event> getAll(String order, String where, int limit, int offset)
  {
    ArrayList<Event> events = new ArrayList<>();
    try
    {
      DBResponse dbResponse = super.modelGetAll(order, where, limit, offset);
      events = getEmployeesFromResponse(dbResponse);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return events;
  }

  public Event getByID(int id) throws SQLException
  {
    return this.getOne("id = " + id, null);
  }
  public Event getOne(String where)
      throws SQLException
  {
    return this.getOne(where, null);
  }
  public Event getOne(String where, String order)
      throws SQLException
  {
    DBResponse dbResponse = super.modelGetOne(where, order);
    return getEmployeesFromResponse(dbResponse).get(0);
  }

  public Event create()
      throws SQLException
  {
    // TODO: 5/21/2021 Fill correct fields and values after event class is refactored
    DBResponse dbResponse = super.modelInsert(
        new String[] {
            /*"username", "password", "name", "surname", "role"*/
        },
        formatStringValues(new String[] {
            /*username, password, name, surname, role*/
        })
    );
    return getEmployeesFromResponse(dbResponse).get(0);
  }

  public ArrayList<Event> edit(String[] fields, String[] values, String where)
      throws SQLException
  {
    DBResponse dbResponse = super.modelUpdate(fields, values, where);
    return getEmployeesFromResponse(dbResponse);
  }
}
