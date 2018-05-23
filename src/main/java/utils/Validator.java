package utils;

import model.Box;
import model.Form;

import java.io.File;

public class Validator {

  public static String PNG_EXTENSION = ".png";
  public static String XML_EXTENSION = ".xml";

  public static boolean isNullOrEmpty(String value) {
    return value == null || value.isEmpty();
  }

  public static boolean isFloat(String value) {
    return !isNullOrEmpty(value) && value.trim().matches("^([+-]?(\\d+\\.)?\\d+)$");
  }

  public static boolean isValidForm(Form f) {
    if (f == null)
      return false;

    Box box = f.getBox();
    if (box == null)
      return false;

    return box.getXmin() < box.getXmax() && box.getYmin() < box.getYmax();
  }

  public static boolean isImageFile(String path) {
    return isFileWithExtension(path, PNG_EXTENSION);
  }

  public static boolean isXMLFile(String path) {
    return isFileWithExtension(path, XML_EXTENSION);
  }

  private static boolean isFileWithExtension(String path, String extension) {
    if (isNullOrEmpty(path))
      return false;
    File file = new File(path);
    return file.exists() && path.endsWith(extension);
  }
}
