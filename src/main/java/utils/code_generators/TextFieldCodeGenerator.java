package utils.code_generators;

import model.labels.Label;

public class TextFieldCodeGenerator extends CodeGenerator {

  private static TextFieldCodeGenerator INSTANCE;

  public TextFieldCodeGenerator() {
    super(Label.TEXT_FIELD);
  }

  @Override
  protected String otherOptions() {
    return "\nandroid:ems=\"10\"\n" +
        "android:inputType=\"textPersonName\"";
  }

  public static TextFieldCodeGenerator getInstance() {
    if (INSTANCE == null)
      INSTANCE = new TextFieldCodeGenerator();
    return INSTANCE;
  }
}
