package utils.code_generators;

import model.labels.Label;

public class LabelCodeGenerator extends CodeGenerator {

  private static LabelCodeGenerator INSTANCE;

  public LabelCodeGenerator() {
    super(Label.LABEL);
  }

  public static LabelCodeGenerator getInstance() {
    if (INSTANCE == null)
      INSTANCE = new LabelCodeGenerator();
    return INSTANCE;
  }
}
