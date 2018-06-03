package utils;

import model.FormWithText;
import utils.code_generators.CodeGenerator;
import utils.code_generators.CodeGeneratorFactory;

import java.util.List;

public class CodeGeneratorUtils {

  public static String generate(List<FormWithText> forms) {
    CodeGeneratorFactory factory = new CodeGeneratorFactory();
    StringBuilder builder = new StringBuilder();
    builder.append("\n");
    for (FormWithText form : forms) {
      CodeGenerator generator = factory.getCodeGenerator(form.getForm().getLabel());
      if (generator != null) {
        builder.append(generator.generate(form)).append("\n\n");
      }
    }

    String template = generateScheme();
    return template.replace("{1}", builder.toString());
  }

  private static String generateScheme() {
    return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
        "<android.support.constraint.ConstraintLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
        "    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n" +
        "    xmlns:tools=\"http://schemas.android.com/tools\"\n" +
        "    android:layout_width=\"match_parent\"\n" +
        "    android:layout_height=\"match_parent\"\n" +
        "    app:layout_behavior=\"@string/appbar_scrolling_view_behavior\"\n" +
        "    tools:context=\".MainActivity\"\n" +
        "    tools:showIn=\"@layout/app_bar_main\">\n" +
        "\n{1}" +
        "</android.support.constraint.ConstraintLayout>";
  }
}
