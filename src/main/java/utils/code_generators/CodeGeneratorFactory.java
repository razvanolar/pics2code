package utils.code_generators;

import model.labels.Label;

public class CodeGeneratorFactory {

  public CodeGenerator getCodeGenerator(Label formLabel) {
    switch (formLabel) {
      case BUTTON:
        return ButtonCodeGenerator.getInstance();
      case LABEL:
        return LabelCodeGenerator.getInstance();
      case CHECKED_CHECK_BOX:
        return CheckBoxCodeGenerator.getInstance();
      case UNCHECKED_CHECK_BOX:
        return CheckBoxCodeGenerator.getInstance();
    }
    return null;
  }
}
