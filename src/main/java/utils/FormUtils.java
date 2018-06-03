package utils;

import model.Bias;
import model.Box;
import model.Form;

import java.util.List;

public class FormUtils {

  public static List<Form> computeBias(List<Form> forms, int imgWidth, int imgHeight) {
    for (Form form : forms) {
      Box box = form.getBox();
      int leftMargin = (int) box.getXmin();
      int rightMargin = imgWidth - (int) box.getXmax();
      int topMargin = (int) box.getYmin();
      int bottomMargin = imgHeight - (int) box.getYmax();

      form.setBias(computeBias(leftMargin, rightMargin, topMargin, bottomMargin));
    }
    return forms;
  }

  private static Bias computeBias(int leftMargin, int rightMargin, int topMargin, int bottomMargim) {
    return new Bias(
        computeBias(leftMargin, rightMargin),
        computeBias(topMargin, bottomMargim));
  }

  private static float computeBias(int firstMargin, int secondMargin) {
    return ((firstMargin * 100f) / (float) (firstMargin + secondMargin)) / 100f;
  }
}
