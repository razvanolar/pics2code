package utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ParametersHandler {

  private static String INPUT_ARGUMENT_LONG = "--input";
  private static String INPUT_ARGUMENT_SHORT = "-i";
  private static String OUTPUT_DIR_ARGUMENT_LONG = "--output";
  private static String OUTPUT_DIR_ARGUMENT_SHORT = "-o";

  private List<String> argsList;

  private File inputImage;
  private File inputXML;
  private File outDirectory;

  public ParametersHandler(String[] args) {
    this.argsList = Arrays.asList(args);
  }

  public void parseParameters() throws Exception {
    if (argsList.isEmpty())
      throw new Exception("Missing arguments.");
    int in_index = findFirstIndex(INPUT_ARGUMENT_LONG, INPUT_ARGUMENT_SHORT);
    if (in_index < 0)
      throw new Exception("Missing input argument.");
    int out_index = findFirstIndex(OUTPUT_DIR_ARGUMENT_LONG, OUTPUT_DIR_ARGUMENT_SHORT);
    if (out_index < 0)
      throw new Exception("Missing output argument");
    if (Math.abs(in_index - out_index) != 2)
      throw new Exception("--input and --output arguments were incorrectly provided.");
    if (argsList.size() != 4)
      throw new Exception("--input or --output value was not provided.");

    String input_image = getArgumentForPosition(in_index + 1);
    if (!Validator.isImageFile(input_image))
      throw new Exception(String.format("%s is not a valid input image.", input_image));
    String output_directory = getArgumentForPosition(out_index + 1);
    if (!createOutputDirIfNotExist(output_directory))
      throw new Exception(String.format("Unable to create output directory named %s", output_directory));

    String imageName = getNameWithoutExtension(input_image);
    if (imageName == null)
      throw new Exception("Unable to extract image name.");

    String input_xml = imageName + Validator.XML_EXTENSION;
    if (!Validator.isXMLFile(input_xml))
      throw new Exception("Unable to find the corresponding xml for the inout image.");

    inputImage = new File(input_image);
    inputXML = new File(input_xml);
    outDirectory = new File(output_directory);
  }

  public File getInputImage() {
    return inputImage;
  }

  public File getInputXML() {
    return inputXML;
  }

  public File getOutDirectory() {
    return outDirectory;
  }

  private  int findFirstIndex(String... arguments) {
    for (String s : arguments) {
      int index = argsList.indexOf(s);
      if (index >= 0) {
        return index;
      }
    }
    return -1;
  }

  private String getArgumentForPosition(int index) {
    if (index < 0 || index >= argsList.size())
      return null;
    return argsList.get(index);
  }

  private boolean createOutputDirIfNotExist(String output_directory) {
    File file = new File(output_directory);
    return (file.exists() && file.isDirectory()) || (!file.exists() && !file.mkdirs());
  }

  private String getNameWithoutExtension(String fileName) {
    if (Validator.isNullOrEmpty(fileName))
      return null;
    int index = fileName.lastIndexOf('.');
    if (index < 0)
      return null;
    return fileName.substring(0, index);
  }
}
