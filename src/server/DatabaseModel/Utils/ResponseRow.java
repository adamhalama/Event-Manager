package server.DatabaseModel.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class ResponseRow
{
  private ArrayList<String> fields = new ArrayList<>();
  private ArrayList<String> values = new ArrayList<>();

  public ResponseRow(ArrayList<String> fields, String[] values) {
    this.fields = fields;
    this.values.addAll(Arrays.asList(values));
  }

  public String getField(String fieldName) {
    for(int a=0; a<this.fields.size(); a++) {
      if(this.fields.get(a).equals(fieldName)) {
        return this.values.get(a);
      }
    }
    return null;
  }

  @Override public String toString()
  {
    return "{\n" + "\tfields=[" + String.join(", ", fields.toArray(new String[0])) + "],\n\tvalues=[" + String.join(", ", values.toArray(new String[0])) + "]\n}";
  }
}
