package utils;

import model.Box;
import model.Form;
import model.Row;

import java.util.ArrayList;
import java.util.List;

public class Divider {

  public List<Row> getRows(List<Form> formsList) {
    List<Form> forms = new ArrayList<>(formsList);
    List<Row> rows = new ArrayList<>();
    while (!forms.isEmpty()) {
      Form form = forms.get(0);
      Row row = new Row();
      row.addForm(form);
      for (int i=1; i<forms.size(); i++) {
        Form other = forms.get(i);
        if (isIntersection(form, other)) {
          row.addForm(other);
        }
      }
      forms.removeAll(row.getForms());
      rows.add(row);
    }
    return rows;
  }

  private boolean isIntersection(Form f1, Form f2) {
    Box box1 = f1.getBox();
    Box box2 = f2.getBox();
    return isIntersection(box1, box2) || isIntersection(box2, box1);
  }

  private boolean isIntersection(Box box1, Box box2) {
    return box1.getYmin() >= box2.getYmin() && box1.getYmin() <= box2.getYmax();
  }
}