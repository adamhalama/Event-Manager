package server.DatabaseModel.models;

import Shared.Event.Event;
import server.DatabaseModel.Utils.DBResponse;
import server.DatabaseModel.Utils.ResponseRow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class EventModel extends Model
{

  public EventModel(Connection connection) {
    super(connection, "event");
  }

  private ArrayList<Event> getEventsFromResponse(DBResponse dbResponse) {
    ArrayList<ResponseRow> rows = dbResponse.getRows();
    ArrayList<Event> events = new ArrayList<>();
    for(ResponseRow row : rows) {
      int id = Integer.parseInt(row.getField("id"));
      String title = row.getField("title");
      String description = row.getField("description");
      String platform = row.getField("platform");
      String url = row.getField("url");
      int roomID = row.getField("room_id") == null ? -1 : Integer.parseInt(row.getField("room_id"));
      int creatorID = Integer.parseInt(row.getField("creator"));
      int messageRoomID = Integer.parseInt(row.getField("message_room_id"));
      long createTime = Long.parseLong(row.getField("create_time"));
      long startTime = Long.parseLong(row.getField("start_time"));
      long endTime = Long.parseLong(row.getField("end_time"));
      boolean isOnline = Boolean.parseBoolean(row.getField("is_online")) || row.getField("is_online").equals("t");
      if(isOnline) { // online
        events.add(new Event(id, title, description, platform, url, creatorID, roomID, messageRoomID, createTime, startTime, endTime));
      } else { //offline
        events.add(new Event(id, title, description, creatorID, roomID, messageRoomID, createTime, startTime, endTime));
      }
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
      events = getEventsFromResponse(dbResponse);
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
    return getEventsFromResponse(dbResponse).get(0);
  }

  public Event create(String title, String description, int roomID, int creator, int messageRoomID, long startTime, long endTime)
      throws SQLException
  {
    return this.create(title, description, null, null, false, roomID, creator, messageRoomID, startTime, endTime);
  }
  public Event create(String title, String description, String platform, String url, int creator, int messageRoomID, long startTime, long endTime)
      throws SQLException
  {
    return this.create(title, description, platform, url, true, -1, creator, messageRoomID, startTime, endTime);
  }
  public Event create(String title, String description, String platform, String url, boolean isOnline, int roomID, int creator, int messageRoomID, long startTime, long endTime)
      throws SQLException
  {
    String[] stringValues = formatStringValues(new String[] {
        title, description
    });
    ArrayList<String> values = new ArrayList<>(Arrays.asList(stringValues));
    if(platform != null) {
      values.add("'" + platform + "'");
    } else {
      values.add(null);
    }
    if(url != null) {
      values.add("'" + url + "'");
    } else {
      values.add(null);
    }
    values.add(String.valueOf(isOnline));
    values.add(String.valueOf(roomID == -1 ? null : roomID));
    values.add(String.valueOf(creator));
    values.add(String.valueOf(messageRoomID));
    values.add(String.valueOf(System.currentTimeMillis()));
    values.add(String.valueOf(startTime));
    values.add(String.valueOf(endTime));
    String finalValues[] = new String[values.size()];
    for (int j = 0; j < values.size(); j++) {
      finalValues[j] = values.get(j);
    }
    DBResponse dbResponse = super.modelInsert(
        new String[] {
            "title", "description", "platform", "url", "is_online", "room_id", "creator", "message_room_id", "create_time", "start_time", "end_time"
        },
        finalValues
    );
    return getEventsFromResponse(dbResponse).get(0);
  }

  public ArrayList<Event> edit(String[] fields, String[] values, String where)
      throws SQLException
  {
    DBResponse dbResponse = super.modelUpdate(fields, values, where);
    return getEventsFromResponse(dbResponse);
  }

  public Event editByID(String[] fields, String[] values, int eventID)
      throws SQLException
  {
    DBResponse dbResponse = super.modelUpdate(fields, values, "id = " + eventID);
    return getEventsFromResponse(dbResponse).get(0);
  }

  public boolean deleteByID(int eventID)
  {
    try
    {
      return super.modelDelete("id = " + eventID);
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }
}
