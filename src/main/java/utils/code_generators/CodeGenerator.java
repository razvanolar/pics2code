package utils.code_generators;

import model.FormWithText;
import model.labels.Label;

public abstract class CodeGenerator {

  protected String template;
  protected Label label;

  private int ID = 0;

  CodeGenerator(Label label) {
    this.label = label;
    template = generateTemplate();
  }

  public String generate(FormWithText form) {
    String text = form.getText();
    String rez = template.replace("{1}", generateId());
    rez = rez.replace("{2}", text != null ? text : "");
    rez = rez.replace("{3}", String.valueOf(form.getForm().getBias().getHorizontal()));
    rez = rez.replace("{4}", String.valueOf(form.getForm().getBias().getVertical()));
    return rez;
  }

  private String generateTemplate() {
    return "<" + label.getTag() + "\n" + "android:id=\"@+id/{1}\"\n" +
        "android:layout_width=\"wrap_content\"\n" +
        "android:layout_height=\"wrap_content\"\n" +
        "android:text=\"{2}\"\n" +
        "app:layout_constraintBottom_toBottomOf=\"parent\"\n" +
        "app:layout_constraintEnd_toEndOf=\"parent\"\n" +
        "app:layout_constraintHorizontal_bias=\"{3}\"\n" +
        "app:layout_constraintStart_toStartOf=\"parent\"\n" +
        "app:layout_constraintTop_toTopOf=\"parent\"\n" +
        "app:layout_constraintVertical_bias=\"{4}\"" +
        otherOptions() +
        " />\n";
  }

  private String generateId() {
    return label.getIdPrefix() + "_" + getNextId();
  }

  protected String otherOptions() {
    return "";
  }

  private int getNextId() {
    return ID++;
  }
}
