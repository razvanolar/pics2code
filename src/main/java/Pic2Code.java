import model.Form;
import model.FormWithText;
import model.Row;
import utils.*;
import utils.text_extractor.TextExtractor;
import utils.xml.ParserXML;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.util.List;

public class Pic2Code {

  private static Pic2Code INSTANCE;

  private ProjectUtils project;
  private ParametersHandler paramHandler;
  private final BufferedImage image;
  private List<Form> forms;

  private Pic2Code(String[] args) throws Exception {
    consoleMessage("Load project settings");
    this.project = new ProjectUtils();

    consoleMessage("Check parameters");
    paramHandler = new ParametersHandler(args);
    paramHandler.parseParameters();

    consoleMessage("Read image");
    image = ImageIO.read(paramHandler.getInputImage());

    consoleMessage("Parse XML");
    ParserXML parser = new ParserXML();
    forms = parser.parse(paramHandler.getInputXML());
    consoleMessage("Compute bias values");
    forms = FormUtils.computeBias(forms, image.getWidth(), image.getHeight());
  }

  public void handle() {
    try {
      consoleMessage("Extract text");
      TextExtractor textExtractor = new TextExtractor();
      List<FormWithText> formsWithText = textExtractor.extract(image, forms);

      // Skip divider operations for a while
//      Divider divider = new Divider();
//      List<Row> rows = divider.getRows(formsWithText);
      //

      consoleMessage("Generate code");
      String code = CodeGeneratorUtils.generate(formsWithText);
      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(code), null);

      consoleMessage("Create Android project");
      project.createAndroidProject(paramHandler.getOutDirectory());
      project.writeContentXml(paramHandler.getOutDirectory(), code);
      consoleMessage("Done");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void init(String[] args) throws Exception {
    if (INSTANCE == null) {
      INSTANCE = new Pic2Code(args);
    }
  }

  public static Pic2Code getInstance() {
    return INSTANCE;
  }

  public static void consoleMessage(String msg) {
    System.out.println(String.format("# %s", msg));
  }
}
