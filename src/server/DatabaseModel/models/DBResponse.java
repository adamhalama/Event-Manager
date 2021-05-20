package server.DatabaseModel.models;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBResponse
{
  private ArrayList<String> fields = new ArrayList<>();
  private ArrayList<String[]> rows = new ArrayList<>();

  public DBResponse()
  {

  }

  public void addField(String field)
  {
    this.fields.add(field);
  }

  public void addFields(ResultSet response) throws SQLException
  {
    ResultSetMetaData resmd = response.getMetaData();
    int columnCount = resmd.getColumnCount();
    for (int i = 1; i <= columnCount; i++ ) {
      this.addField(resmd.getColumnName(i));
    }
  }

  public void addRows(ResultSet response) throws SQLException
  {
    while (response.next())
    {
      ArrayList<String> values = new ArrayList<>();
      for(String column : fields) {
        values.add(response.getString(column));
      }
      addRow(values.toArray(new String[0]));
    }
  }

  public void addRow(String[] row)
  {
    this.rows.add(row);
  }

  public ArrayList<String> getFields()
  {
    return this.fields;
  }

  public ArrayList<String[]> getRawRows()
  {
    return this.rows;
  }

  public ArrayList<ResponseRow> getRows()
  {
    ArrayList<ResponseRow> responseRows = new ArrayList<>();
    for(String[] row : this.rows) {
      responseRows.add(new ResponseRow(this.fields, row));
    }
    return responseRows;
  }

  @Override public String toString()
  {
    return "[\n" + String.join("\n\t", this.getRows().toArray(new String[0])) +"\n]";
  }
}
