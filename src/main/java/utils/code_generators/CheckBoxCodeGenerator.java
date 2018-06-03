package utils.code_generators;

import model.FormWithText;
import model.labels.Label;

public class CheckBoxCodeGenerator extends CodeGenerator {

  private static CheckBoxCodeGenerator INSTANCE;

  private CheckBoxCodeGenerator() {
    super(Label.CHECKED_CHECK_BOX);
  }

  @Override
  public String generate(FormWithText form) {
    String code = super.generate(form);
    boolean isChecked = form.getForm().getLabel() == Label.CHECKED_CHECK_BOX;
    return code.replace("{5}", String.valueOf(isChecked));
  }

  @Override
  protected String otherOptions() {
    return "\nandroid:checked=\"{5}\"\n";
  }

  public static CodeGenerator getInstance() {
    if (INSTANCE == null)
      INSTANCE = new CheckBoxCodeGenerator();
    return INSTANCE;
  }
}
