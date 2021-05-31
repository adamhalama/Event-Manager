package server.DatabaseModel.models;

import server.DatabaseModel.Utils.DBResponse;
import server.DatabaseModel.Utils.SQLBuilder;

import java.sql.*;
import java.util.ArrayList;

import static server.DatabaseModel.Utils.SQLBuilder.Operations.*;

public class Model
{
  private Connection connection;
  private String table;

  public static String[] formatStringValues(String value) {
    return formatStringValues(new String[] {value});
  }
  public static String[] formatStringValues(String[] values) {
    // When we insert strings in SQL query they should be in ' ' and numbers are without it.
    ArrayList<String> formattedValues = new ArrayList<>();
    for(String value : values) {
      formattedValues.add("'" + value + "'");
    }
    return formattedValues.toArray(new String[0]);
  }

  public Model(Connection connection, String table)
  {
    this.connection = connection;
    this.table = table;
  }

  private DBResponse getDataFromResponse(ResultSet response) throws SQLException
  {
    DBResponse dbResponse = new DBResponse();
    dbResponse.addFields(response);
    dbResponse.addRows(response);
    return dbResponse;
  }
  private DBResponse getDataFromResponse(PreparedStatement statement) throws SQLException
  {
    ResultSet resultSet = statement.getGeneratedKeys();
    DBResponse dbResponse = new DBResponse();
    dbResponse.addFields(resultSet);
    dbResponse.addRows(resultSet);
    return dbResponse;
  }

  public DBResponse modelGetAll() throws SQLException {
    return this.modelGetAll(null, null, 0, 0);
  }
  public DBResponse modelGetAll(String order) throws SQLException {
    return this.modelGetAll(order, null, 0, 0);
  }
  public DBResponse modelGetAll(String order, int limit, int offset) throws SQLException {
    return this.modelGetAll(order, null, limit, offset);
  }
  public DBResponse modelGetAllWhere(String where) throws SQLException {
    return this.modelGetAll(null, where, 0, 0);
  }
  public DBResponse modelGetAllWhere(String where, int limit, int offset) throws SQLException {
    return this.modelGetAll(null, where, limit, offset);
  }
  public DBResponse modelGetAll(String order, String where, int limit, int offset) throws SQLException
  {
    String sql = (new SQLBuilder(SELECT, this.table).order(order).where(where).offset(offset).limit(limit).build());
    Statement statement = this.connection.createStatement();
    ResultSet response = statement.executeQuery(sql);
    DBResponse dbResponse = getDataFromResponse(response);
    response.close();
    statement.close();
    return dbResponse;
  }

  public DBResponse modelGetOne(String where) throws SQLException
  {
    return this.modelGetOne(where, null);
  }
  public DBResponse modelGetOne(String where, String order) throws SQLException
  {
    String sql = (new SQLBuilder(SELECT, this.table).where(where).order(order).limit(1).build());
    Statement statement = this.connection.createStatement();
    ResultSet response = statement.executeQuery(sql);
    DBResponse dbResponse = getDataFromResponse(response);
    response.close();
    statement.close();
    return dbResponse;
  }

  public DBResponse modelInsert(String[] fields, String[] values) throws SQLException
  {
    String sql = (new SQLBuilder(INSERT, this.table).addFields(fields, values).build());
    PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    statement.executeUpdate();
    DBResponse dbResponse = getDataFromResponse(statement);
    statement.close();
    return dbResponse;
  }

  public DBResponse modelUpdate(String[] fields, String[] values, String where) throws SQLException
  {
    String sql = (new SQLBuilder(UPDATE, this.table).addFields(fields, values).where(where).build());
    PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    statement.executeUpdate();
    DBResponse dbResponse = getDataFromResponse(statement);
    statement.close();
    return dbResponse;
  }

  public boolean modelDelete(String where) throws SQLException
  {
    String sql = (new SQLBuilder(DELETE, this.table).where(where).build());
    PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    int changes = statement.executeUpdate();
    statement.close();
    return changes>=1;
  }
}
