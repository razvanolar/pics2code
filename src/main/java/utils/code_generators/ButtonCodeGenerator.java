package utils.code_generators;

import model.labels.Label;

public class ButtonCodeGenerator extends CodeGenerator {

  private static ButtonCodeGenerator INSTANCE = null;

  public ButtonCodeGenerator() {
    super(Label.BUTTON);
  }

  public static ButtonCodeGenerator getInstance() {
    if (INSTANCE == null)
      INSTANCE = new ButtonCodeGenerator();
    return INSTANCE;
  }
}
