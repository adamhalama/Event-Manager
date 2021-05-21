package server.DatabaseModel.Utils;

import server.DatabaseModel.DatabaseCredentials;

import java.util.ArrayList;
import java.util.Arrays;

public class SQLBuilder
{
  public static enum Operations {
    SELECT, INSERT, UPDATE
  }
  private String table, sql_where, sql_order;
  private ArrayList<String> fields = new ArrayList<>();
  private ArrayList<String> values = new ArrayList<>();
  private int sql_offset = 0;
  private int sql_limit = 0;
  private Operations operation;

  public SQLBuilder(Operations operation, String table) {
    this.table = table;
    this.operation = operation;
  }

  public SQLBuilder where(String sql_where) {
    this.sql_where = sql_where;
    return this;
  }

  public SQLBuilder order(String sql_order) {
    this.sql_order = sql_order;
    return this;
  }

  public SQLBuilder offset(int sql_offset) {
    this.sql_offset = sql_offset;
    return this;
  }

  public SQLBuilder limit(int sql_limit) {
    this.sql_limit = sql_limit;
    return this;
  }

  public SQLBuilder addField(String field, String value) {
    this.fields.add(field);
    this.values.add("'"+value+"'");
    return this;
  }
  public SQLBuilder addField(String field, int value) {
    this.fields.add(field);
    this.values.add(String.valueOf(value));
    return this;
  }
  public SQLBuilder addField(String field, long value) {
    this.fields.add(field);
    this.values.add(String.valueOf(value));
    return this;
  }
  public SQLBuilder addFields(String fields[], String values[]) {
    this.fields.addAll(Arrays.asList(fields));
    this.values.addAll(Arrays.asList(values));
    return this;
  }

  public String build() {
    String sql = "";
    if(this.operation == Operations.SELECT) {
      sql += " SELECT * FROM";
    } else if(this.operation == Operations.INSERT) {
      sql += " INSERT INTO";
    } else if(this.operation == Operations.UPDATE) {
      sql += " UPDATE";
    }
    sql += " " + DatabaseCredentials.SCHEMA_NAME + "." + this.table;
    if(this.operation == Operations.INSERT) {
      sql += " (" + String.join(", ", this.fields) + ") VALUES ("+ String.join(", ", this.values) + ")";
    } else if(this.operation == Operations.UPDATE) {
      sql += " SET ";
      for(int a=0; a<this.fields.size(); a++) {
        sql += this.fields.get(a) + " = " + this.values.get(a);
        if(a < this.fields.size()-1) {
          sql += ", ";
        }
      }
    }
    if(this.sql_where != null ) {
      sql += " WHERE " + this.sql_where;
    }
    if(this.sql_order != null ) {
      sql += " ORDER BY " + this.sql_order;
    }
    if(this.sql_offset > 0) {
      sql += " OFFSET " + this.sql_offset;
    }
    if(this.sql_limit > 0) {
      sql += " LIMIT " + this.sql_limit;
    }
    sql += ";";
    System.out.println(sql);
    return sql;
  }

  @Override public String toString()
  {
    return build();
  }
}
