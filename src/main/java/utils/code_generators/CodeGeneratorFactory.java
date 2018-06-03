package utils.code_generators;

import model.labels.Label;

public class CodeGeneratorFactory {

  public CodeGenerator getCodeGenerator(Label formLabel) {
    switch (formLabel) {
      case BUTTON:
        return ButtonCodeGenerator.getInstance();
      case LABEL:
        return LabelCodeGenerator.getInstance();
    }
    return null;
  }
}
