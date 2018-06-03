package utils.code_generators;

import model.labels.Label;

public class LabelCodeGenerator extends CodeGenerator {

  private static LabelCodeGenerator INSTANCE;

  private LabelCodeGenerator() {
    super(Label.LABEL);
  }

  public static CodeGenerator getInstance() {
    if (INSTANCE == null)
      INSTANCE = new LabelCodeGenerator();
    return INSTANCE;
  }
}
