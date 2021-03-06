package model;

import java.util.ArrayList;
import java.util.List;

public class Row {

  private List<FormWithText> forms;

  public Row() {
    this.forms = new ArrayList<>();
  }

  public List<FormWithText> getForms() {
    return forms;
  }

  public void addForm(FormWithText form) {
    if (form != null)
      forms.add(form);
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (int i=0; i<forms.size(); i++) {
      s.append(forms.get(i).toString());
      if (i != forms.size() - 1) {
        s.append(", ");
      }
    }
    return s.toString();
  }
}
