package utils;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Properties;

public class ProjectUtils {

  private static String PICS2CODE_ENV_VAR_NAME = "PICS2CODE";
  private static String PICS2CODE_PROP_FILE_NAME = "pics2code.properties";

  private static String ANDROID_PROJECT_PROPERTY = "ANDROID_PROJECT_PATH";

  private static String IDEA_DIR = ".idea\\";
  private static String GRAGLE_DIR = ".gradle\\";
  private static String ANDROID_CONTENT_MAIN_XML_RELATIVE_PATH = "app\\src\\main\\res\\layout\\content_main.xml";

  private File pics2codeDir;
  private File propsFile;

  private File androidProjectDir;

  public ProjectUtils() throws Exception {
    // check for PICS2CODE env variable
    String var = System.getenv(PICS2CODE_ENV_VAR_NAME);
    if (Validator.isNullOrEmpty(var)) {
      throw new Exception(PICS2CODE_ENV_VAR_NAME + " system variable is not defined.");
    }

    // test if the value defined for PICS2CODE is a valid directory
    this.pics2codeDir = new File(var);
    if (!pics2codeDir.exists()) {
      throw new Exception("The path: '" + var + "' defined for " + PICS2CODE_ENV_VAR_NAME + " env variable does not exist.");
    }
    if (!pics2codeDir.isDirectory()) {
      throw new Exception("The path: '" + var + "' defined for " + PICS2CODE_ENV_VAR_NAME + " env variable does not point to a directory.");
    }

    // check if pics2code.properties exists in the specified directory
    File[] files = pics2codeDir.listFiles((dir, name) -> dir.equals(pics2codeDir) && name.equals(PICS2CODE_PROP_FILE_NAME));
    if (files == null || files.length == 0) {
      throw new Exception(PICS2CODE_PROP_FILE_NAME + " was not find in '" + var + "'.");
    }
    propsFile = files[0];

    // load and check properties
    Properties p = new Properties();
    p.load(new FileInputStream(propsFile));
    String androidProject = p.getProperty(ANDROID_PROJECT_PROPERTY);
    if (Validator.isNullOrEmpty(androidProject)) {
      throw new Exception(ANDROID_PROJECT_PROPERTY + " is not defined in " + PICS2CODE_PROP_FILE_NAME + ".");
    }
    androidProject = replaceOsSeparator(androidProject);
    androidProjectDir = new File(androidProject);
    if (!androidProjectDir.exists()) {
      throw new Exception("'" + androidProject + "' does not exist.");
    }
    if (!androidProjectDir.isDirectory()) {
      throw new Exception("'" + androidProject + "' is not a directory.");
    }
  }

  public void createAndroidProject(File outputDirectory) throws Exception {
    FileUtils.cleanDirectory(outputDirectory);
    FileUtils.copyDirectory(androidProjectDir, outputDirectory);
    String dirPath = getPath(outputDirectory);
    String idea = dirPath + IDEA_DIR;
    String gradle = dirPath + GRAGLE_DIR;
    File ideaDir = new File(idea);
    File gradleDir = new File(gradle);
    if (ideaDir.exists())
      FileUtils.deleteDirectory(ideaDir);
    if (gradleDir.exists())
      FileUtils.deleteDirectory(gradleDir);
  }

  public void writeContentXml(File outputDirectory, String code) throws Exception {
    String projectPath = getPath(outputDirectory);
    String contentXMLPath = replaceOsSeparator(projectPath + ANDROID_CONTENT_MAIN_XML_RELATIVE_PATH);
    File contentXML = new File(contentXMLPath);
    try (PrintWriter writer = new PrintWriter(contentXML)) {
      writer.write(code);
      writer.flush();
    }
  }

  private String getPath(File file) {
    return addSeparator(replaceOsSeparator(file.getAbsolutePath()));
  }

  private String replaceOsSeparator(String path) {
    return path.replace("\\", File.separator);
  }

  private String addSeparator(String path) {
    return !path.endsWith(File.separator) ? path + File.separator : path;
  }
}
