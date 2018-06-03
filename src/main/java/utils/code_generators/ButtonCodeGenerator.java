package utils.code_generators;

import model.labels.Label;

public class ButtonCodeGenerator extends CodeGenerator {

  private static ButtonCodeGenerator INSTANCE = null;

  private ButtonCodeGenerator() {
    super(Label.BUTTON);
  }

  public static CodeGenerator getInstance() {
    if (INSTANCE == null)
      INSTANCE = new ButtonCodeGenerator();
    return INSTANCE;
  }
}
