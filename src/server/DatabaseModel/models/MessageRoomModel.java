package server.DatabaseModel.models;

import Shared.Messages.MessageRoom;
import server.DatabaseModel.Utils.DBResponse;
import server.DatabaseModel.Utils.ResponseRow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageRoomModel extends Model
{

  public MessageRoomModel(Connection connection) {
    super(connection, "message_room");
  }

  private ArrayList<MessageRoom> getMessageRoomsFromResponse(DBResponse dbResponse) {
    ArrayList<ResponseRow> rows = dbResponse.getRows();
    ArrayList<MessageRoom> messages = new ArrayList<>();
    for(ResponseRow row : rows) {
      int userID = Integer.parseInt(row.getField("id"));
      String name = row.getField("name");
      boolean isPrivate = Boolean.parseBoolean(row.getField("is_private"));
      messages.add(new MessageRoom(userID, name, isPrivate));
    }
    return messages;
  }

  public MessageRoom getByID(int id)
      throws SQLException
  {
    DBResponse dbResponse = super.modelGetOne("id = " + id, null);
    return getMessageRoomsFromResponse(dbResponse).get(0);
  }

  public MessageRoom editName(String name, int id)
      throws SQLException
  {
    DBResponse dbResponse = super.modelUpdate(
        new String[] {"name"},
        new String[] {"'"+name+"'"},
        "id = " + id
    );
    return getMessageRoomsFromResponse(dbResponse).get(0);
  }

  public MessageRoom create(String name, boolean isPrivate)
      throws SQLException
  {
    DBResponse dbResponse = super.modelInsert(
        new String[] {"name", "is_private"},
        new String[] {"'"+name+"'", "" + isPrivate + ""}
    );
    return getMessageRoomsFromResponse(dbResponse).get(0);
  }
}
